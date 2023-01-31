package application;

public class Receipt 
{
	private int id;
	private double value;
	private String status;
	private String filePath;

	/**
	 * @param id - ID of receipt
	 * @param value - the amount which was used on the receipt.
	 * @param status - status in the system. "approved", "fraud" or "pending"
	 * @param filePath - File path of the image of the receipt
	 */
	public Receipt(int id, double value, String status, String filePath) {
		this.id = id;
		this.value = value;
		this.status = status;
		this.filePath = filePath;
	}
	/**
	 * @param status - new status of the receipt
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return id of the receipt
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return value of the receipt
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @return status of the receipt
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return file path of the image of the receipt
	 */
	public String getFilePath() {
		return filePath;
	}
}