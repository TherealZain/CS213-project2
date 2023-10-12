package project2;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDatabaseTest {
    @Test
    public void testClose_ValidCheckingAccount() {
        AccountDatabase testDatabase = new AccountDatabase();
        Date testDate = new Date(1979, 5, 12);
        Profile testProfile = new Profile("John", "Doe", testDate);
        Checking testChecking = new Checking(testProfile, 0.0);
        testDatabase.open(testChecking);
        assertTrue(testDatabase.close(testChecking));

    }

    @Test
    public void testClose_AccountNotInDatabase() {
        AccountDatabase testDatabase = new AccountDatabase();
        Date testDate = new Date(1979, 5, 12);
        Profile testProfile = new Profile("John", "Doe", testDate);
        Checking testChecking = new Checking(testProfile, 0.0);
        assertFalse(testDatabase.close(testChecking));

    }
}