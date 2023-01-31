package application;

import java.util.ArrayList;
import java.util.List;

public class User 
{
	private int id;
	private String name;
	private List<Receipt> listOfReceipts = new ArrayList<>();
	
	/**
	 * @param id - ID of the user
	 * @param name - Name of the user
	 */
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	/**
	 * @return id of the user
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param receipt - receipt to add to the users list of receipts
	 */
	public void addReceipt(Receipt receipt)
	{
		listOfReceipts.add(receipt);
	}
	/**
	 * @param receipt - receipt to remove from the users list of receipts
	 */
	public void removeReceipt(Receipt receipt)
	{
		listOfReceipts.remove(receipt);
	}
	/**
	 * @param receipt - receipt to be updated
	 * @param newReceipt - the updated receipt
	 */
	public void updateReceipt(Receipt receipt, Receipt newReceipt)
	{
		listOfReceipts.remove(receipt);
		listOfReceipts.add(newReceipt);
	}
	/**
	 * @return name of user
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return list of receipts a user has
	 */
	public List<Receipt> getListOfReceipts() {
		return listOfReceipts;
	}
}
