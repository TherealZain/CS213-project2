package project2;

import static org.junit.Assert.*;

/**
 * This class is responsible for testing the Date class methods.
 * It contains multiple test methods to validate the functionality
 * of the Date class.
 * @author Zain Zulfiqar
 */
public class DateTest {
    /**
     * Test the number of days in February for a non-leap year.
     * Validates that February in a non-leap year does not have 29 days.
     */
    @org.junit.Test
    public void testDaysInFeb_NonLeap() {
        Date date = new Date(2010, 2, 29);
        assertFalse(date.isValid());

    }

    /**
     * Test if the month is out of range.
     * Validates that a month value greater than 12 is invalid.
     */
    @org.junit.Test
    public void testMonth_OutOfRange() {
        Date date = new Date(2012, 14, 29);
        assertFalse(date.isValid());
    }

    /**
     * Test if the day in a short month is out of range.
     * Validates that November does not have 31 days.
     */
    @org.junit.Test
    public void testDayInShortMonth_OutOfRange(){
        Date date = new Date(2023, 11, 31);
        assertFalse(date.isValid());
    }

    /**
     * Test if the day in a month is greater than one.
     * Validates that a negative day value is invalid.
     */
    @org.junit.Test
    public void testDayInMonth_GreaterThanOne(){
        Date date = new Date(2023,10,-1);
        assertFalse(date.isValid());
    }

    /**
     * Test if the year is in range with a negative year.
     * Validates that a negative year value is invalid.
     */
    @org.junit.Test
    public void testYearInRange_NegativeYear(){
        Date date = new Date(-1, 10, 14);
        assertFalse(date.isValid());
    }

    /**
     * Test the number of days in February for a leap year.
     * Validates that February in a leap year has 29 days.
     */
    @org.junit.Test
    public void testDaysInFeb_Leap() {
        Date date = new Date(2008, 2, 29);
        assertTrue(date.isValid());
    }

    /**
     * Test if the day in a long month is in range.
     * Validates that October can have 31 days.
     */
    @org.junit.Test
    public void testDayInLongMonth_InRange() {
        Date date = new Date(2023,10,31);
        assertTrue(date.isValid());
    }


}