/**
 * File name: Entries.java
 * Author: Willard Wang
 * Create date: Apr 16, 2013
 */
package com.ericsson.ab.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a contact address book in JSON format like
 * {
 *    "entries": {
 *        "lilei" : {
 *            "age": 27,
 *            "mobile" : "13700000000",
 *            "address" : "Earth somewhere"
 *        }, 
 *        "hanmeimei" : {
 *            "age": 26,
 *            "mobile" : "13700000001",
 *            "address" : "Earth somewhere else"
 *        }
 *    }
 * }
 * 
 * @author deathspeeder
 *
 */
public class AddressBook {
    
    private Map<String, Contact> entries = new HashMap<String, Contact>();

    public Map<String, Contact> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, Contact> entries) {
        this.entries = entries;
    }

    
}
