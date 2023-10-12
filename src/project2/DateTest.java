package project2;

import static org.junit.Assert.*;

public class DateTest {

    @org.junit.Test
    public void testDaysInFeb_NonLeap() {
        Date date = new Date(2010, 2, 29);
        assertFalse(date.isValid());

    }

    @org.junit.Test
    public void testMonth_OutOfRange() {
        Date date = new Date(2012, 14, 29);
        assertFalse(date.isValid());
    }

    @org.junit.Test
    public void testDayInShortMonth_OutOfRange(){
        Date date = new Date(2023, 11, 31);
        assertFalse(date.isValid());
    }

    @org.junit.Test
    public void testDayInMonth_GreaterThanOne(){
        Date date = new Date(2023,10,-1);
        assertFalse(date.isValid());
    }

    @org.junit.Test
    public void testYearInRange_NegativeYear(){
        Date date = new Date(-1, 10, 14);
        assertFalse(date.isValid());
    }

    @org.junit.Test
    public void testDaysInFeb_Leap() {
        Date date = new Date(2008, 2, 29);
        assertTrue(date.isValid());
    }

    @org.junit.Test
    public void testDayInLongMonth_InRange() {
        Date date = new Date(2023,10,31);
        assertTrue(date.isValid());
    }


}