package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import application.BankAPIUtils;
import application.Receipt;
import application.User;

public class bankAPITestUpdateReceipt {

	/**
	 * Updates a receipt in the bank 
	 */
	@Test
	public void updateReceiptTest()
	{
		//arrange
		List<User> result = BankAPIUtils.getBankUserList();
		String testStatus = "Approved";
		Receipt test = new Receipt(13, 550, "Pending", "");

		//Act
		BankAPIUtils.updateReceipt(test, testStatus);

		String newStatus = "";		
		for(User u : result) {
			for(Receipt r : u.getListOfReceipts()) {
				if(r.getId() == test.getId()) {
					newStatus = r.getStatus();
					break;
				}
			}
		}

		//Assert
		assertNotEquals(0, result.size());
		assertEquals(testStatus.toLowerCase(), newStatus.toLowerCase());
	}
}
