package testing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import application.BankAPIUtils;

public class bankAPITestUserListFail
{
	/**
	 * Rule that the test must catch an exception
	 */
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * Starts to threads, one to get user list in the bank, the other interrupts the first.
	 */
	@Test
	public void testInterruptedTest() 
	{
		Thread t1 = new Thread(() -> {
			BankAPIUtils.getBankUserList();
		});
		
		Thread t2 = new Thread(() -> {
			t1.interrupt();
		});
		
		t1.start();
		t2.start();		
	}
}
