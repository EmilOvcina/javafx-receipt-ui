package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BankAPIUtils 
{
	/**
	 * @return a list of users from the banks API
	 */
	public static List<User> getBankUserList() {
		try {
			return getUserListParseJSON(getAPIRequest(
					new String[] { "curl", "--silent", "-X", "GET", "https://finance.lutzen.dk/api/v1/customers", "-H",
							"accept: application/vnd.api+json", "-H", "Authorization: Bearer " + Main.salesManToken }));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}
	/**
	 * @param receipt - receipt to update in the system.
	 * @param status - the status which the receipt should have in the bank system.
	 */
	public static void updateReceipt(Receipt receipt, String status) {
		
		 if(getOperatingSystem().contains("Windows")) {
			try {
				patchAPIRequest(receipt.getId(), status);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				patchAPIRequestUNIX(receipt.getId(), status);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * This is for Windows system users
	 * @param id - Id of the receipt to be updated in the bank.
	 * @param status - the status which the receipt should have in the bank system.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void patchAPIRequest(int id, String status) throws IOException, InterruptedException {
		String command = "curl -X PATCH \"https://finance.lutzen.dk/api/v1/payments/" + id + "\" -H  \"accept: application/vnd.api+json\" -H  \"Authorization: Bearer cZWVAG44OPS8Vlo6EgL1695z4cbcJ3317es7kNQp\" -H  \"Content-Type: application/vnd.api+json\" -d \"{  \\\"data\\\": {    \\\"type\\\": \\\"payments\\\",    \\\"id\\\": \\\"" + id + "\\\",    \\\"attributes\\\": {      \\\"status\\\": \\\" " + status + "\\\"    }  }}\"";
		Process process = Runtime.getRuntime().exec(command);
		process.waitFor();
		process.destroy();
	}
	/**
	 * This is for UNIX system users
	 * @param id - Id of the receipt to be updated in the bank.
	 * @param status - the status which the receipt should have in the bank system.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void patchAPIRequestUNIX(int id, String status) throws IOException, InterruptedException {
		String curl = "curl -X PATCH \"https://finance.lutzen.dk/api/v1/payments/" + id + "\" -H  \"accept: application/vnd.api+json\" -H  \"Authorization: Bearer cZWVAG44OPS8Vlo6EgL1695z4cbcJ3317es7kNQp\" -H  \"Content-Type: application/vnd.api+json\" -d \"{  \\\"data\\\": {    \\\"type\\\": \\\"payments\\\",    \\\"id\\\": \\\"" + id + "\\\",    \\\"attributes\\\": {      \\\"status\\\": \\\" " + status + "\\\"    }  }}\"";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", curl);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		p.waitFor();
		p.destroy();
	}
	/**
	 * @param command - command to be run in the system. Should always be 'curl' commands
	 * @return result of the curl command in one String 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static String getAPIRequest(String[] command) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process process = pb.start();
		InputStream inputStream = process.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();

		br.lines().forEach(line -> {
			sb.append(line);
		});

		br.close();
		process.waitFor();
		process.destroy();
		return sb.toString();
	}
	/**
	 * @param string - output from 'getAPIRequest'. Result of 'curl --silent' command
	 * @return List of User objects from a JSON String.
	 */
	private static List<User> getUserListParseJSON(String string) {
		List<User> res = new ArrayList<>();
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(string);
			JSONArray jsonarray = (JSONArray) obj.get("data");

			for (int i = 0; i < jsonarray.size(); i++) {
				JSONObject jobj1 = (JSONObject) jsonarray.get(i);
				JSONObject jobj2 = (JSONObject) jobj1.get("attributes");

				int id = Integer.parseInt((String) jobj1.get("id"));
				String name = (String) jobj2.get("name");

				User tmp = new User(id, name);
				for(Receipt r : getReceiptListParseJSON(id)) 
					tmp.addReceipt(r);
				
				res.add(tmp);
			}

		} catch (ParseException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * @param id - id of user, which receipts are going to be fetched.
	 * @return List of receipt objects
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static List<Receipt> getReceiptListParseJSON(int id) throws IOException, InterruptedException 
	{
		List<Receipt> res = new ArrayList<>();
		String request = getAPIRequest(new String[] {"curl", "--silent", "-X" ,"GET" ,"https://finance.lutzen.dk/api/v1/customers/" + id + "/payments", "-H", "accept: application/vnd.api+json", "-H" ,"Authorization: Bearer " + Main.salesManToken});
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(request);
			JSONArray jsonarray = (JSONArray) obj.get("data");

			for (int i = 0; i < jsonarray.size(); i++) {
				JSONObject jobj1 = (JSONObject) jsonarray.get(i);
				JSONObject jobj2 = (JSONObject) jobj1.get("attributes");

				int idR = Integer.parseInt((String) jobj1.get("id"));
				long amount = (long) jobj2.get("amount");
				String status = (String) jobj2.get("status");
				
				res.add(new Receipt(idR, amount, status, "https://finance.lutzen.dk/api/v1/customers/" + id + "/payments"));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * @return the users OS in a String object
	 */
	private static String getOperatingSystem() {
	    String os = System.getProperty("os.name");
	    return os;
	}
}
