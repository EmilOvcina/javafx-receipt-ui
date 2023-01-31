package testing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import application.BankAPIUtils;
import application.Receipt;

public class bankAPITestUpdateReceiptFail
{
	/**
	 * Rule that the test must catch an exception
	 */
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * Starts to threads, one to update a receipt in the bank, the other interrupts the first.
	 */
	@Test
	public void testInterruptedTest() 
	{
		Thread t1 = new Thread(() -> {
			BankAPIUtils.updateReceipt(new Receipt(13, 3000, "Pending", ""), "Approved");
		});
		
		Thread t2 = new Thread(() -> {
			t1.interrupt();
		});
	
		t1.start();
		t2.start();
	}
}
