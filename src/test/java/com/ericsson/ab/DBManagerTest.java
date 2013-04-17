/**
 * File name: DBManagerTest.java
 * Author: Willard Wang
 * Create date: Apr 17, 2013
 */
package com.ericsson.ab;

import com.ericsson.ab.db.DBManager;
import com.ericsson.ab.entity.AddressBook;
import com.google.gson.Gson;

import junit.framework.TestCase;

/**
 * @author deathspeeder
 *
 */
public class DBManagerTest extends TestCase {

    private static Gson gson = new Gson();
    
    /**
     * @throws java.lang.Exception
     */
    protected static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    protected static void tearDownAfterClass() throws Exception {
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.ericsson.ab.db.DBManager#instance()}.
     */
    public void testInstance() {
        assertEquals(DBManager.instance(), DBManager.instance());
    }

    /**
     * Test method for {@link com.ericsson.ab.db.DBManager#getCurrentAddressBook()}.
     */
    public void testGetCurrentAddressBook() {
        String json = 
                "{\n" 
                + "\"entries\": {\n" 
                    + "\"lilei\" : {\n"
                        + "\"age\": 27,\n" 
                        + "\"mobile\" : \"13700000000\",\n"
                        + "\"address\" : \"Earth somewhere\"\n" 
                        + "}, \n"
                    + "\"hanmeimei\" : {\n" 
                        + "\"age\": 26,\n"
                        + "\"mobile\" : \"13700000001\",\n"
                        + "\"address\" : \"Earth somewhere else\"\n" 
                        + "}\n" 
                    + "}\n"
                + "}";
        AddressBook book = gson.fromJson(json, AddressBook.class);
        DBManager.instance().setCurrentAddressBook(book);
        book = DBManager.instance().getCurrentAddressBook();
        assertNotNull(book);
        assertNotNull(book.getEntries());
        assertTrue(book.getEntries().containsKey("lilei"));
        assertTrue(book.getEntries().containsKey("hanmeimei"));
        assertEquals(27, book.getEntries().get("lilei").getAge());
        assertEquals("13700000000", book.getEntries().get("lilei").getMobile());
        assertEquals("Earth somewhere", book.getEntries().get("lilei").getAddress());
        assertEquals(26, book.getEntries().get("hanmeimei").getAge());
        assertEquals("13700000001", book.getEntries().get("hanmeimei").getMobile());
        assertEquals("Earth somewhere else", book.getEntries().get("hanmeimei").getAddress());
    }

    /**
     * Test method for {@link com.ericsson.ab.db.DBManager#setCurrentAddressBook(com.ericsson.ab.entity.AddressBook)}.
     */
    public void testSetCurrentAddressBook() {
        DBManager.instance().setCurrentAddressBook(new AddressBook());
    }

    /**
     * Test method for {@link com.ericsson.ab.db.DBManager#getCurrentPosition()}.
     */
    public void testGetCurrentPosition() {
        DBManager.instance().setCurrentPosition("entries");
        assertEquals("entries", DBManager.instance().getCurrentPosition());
    }

    /**
     * Test method for {@link com.ericsson.ab.db.DBManager#setCurrentPosition(java.lang.String)}.
     */
    public void testSetCurrentPosition() {
        DBManager.instance().setCurrentPosition("");
    }

}
