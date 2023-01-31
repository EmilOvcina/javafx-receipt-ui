package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
/**
 * All test classes  
 */
@SuiteClasses({bankAPITestUpdateReceipt.class, bankAPITestUpdateReceiptFail.class, bankAPITestUserListFail.class})
public class AllBankAPITests {

}
