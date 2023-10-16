package project2;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the AccountDatabase class.
 * It contains test methods for various functionalities of the AccountDatabase class.
 * @author Zain Zulfiqar
 */
public class AccountDatabaseTest {
    /**
     * Test the close method with a valid Checking account.
     * This test case checks if the close method successfully
     * closes a valid Checking account.
     */
    @Test
    public void testClose_ValidCheckingAccount() {
        AccountDatabase testDatabase = new AccountDatabase();
        Date testDate = new Date(1979, 5, 12);
        Profile testProfile = new Profile("John", "Doe", testDate);
        Checking testChecking = new Checking(testProfile, 0.0);
        testDatabase.open(testChecking);
        assertTrue(testDatabase.close(testChecking));

    }

    /**
     * Test the close method with an account not in the database.
     * This test case checks if the close method returns false when
     * trying to close an account that is not in the database.
     */
    @Test
    public void testClose_AccountNotInDatabase() {
        AccountDatabase testDatabase = new AccountDatabase();
        Date testDate = new Date(1979, 5, 12);
        Profile testProfile = new Profile("John", "Doe", testDate);
        Checking testChecking = new Checking(testProfile, 0.0);
        assertFalse(testDatabase.close(testChecking));

    }
}