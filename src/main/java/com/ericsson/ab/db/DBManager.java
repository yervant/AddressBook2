/**
 * File name: DBManager.java Author: Willard Wang Create date: Apr 17, 2013
 */
package com.ericsson.ab.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ericsson.ab.entity.AddressBook;
import com.google.gson.Gson;

/**
 * @author deathspeeder
 * 
 *         A Singleton class managing JSON data as database
 */
public class DBManager {

    /**
     * The singleton instance
     */
    private static DBManager dbManager;

    /**
     * default database file name
     */
    private static String DB_NAME = "db.json";

    /**
     * Represent data in file `db.json'
     */
    private AddressBook currentAddressBook;

    /**
     * current position of the address book data structure
     */
    private String currentPosition = "";

    private Gson gson = new Gson();

    /**
     * Creates the database file if not exists
     */
    private DBManager() {
        try {
            // if we do not have the database file, then create one, and write
            // an empty address book
            File dbFile = new File(DB_NAME);
            if (!dbFile.exists()) {
                dbFile.createNewFile();
                currentAddressBook = new AddressBook();
                save();
            } else {
                load();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 
     * @return the singleton instance of DBManager
     */
    public static synchronized DBManager instance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    /**
     * Loads JSON data to {@link com.ericsson.ab.entity.AddressBook}
     */
    private void load() {
        try {
            currentAddressBook = gson.fromJson(new FileReader(DB_NAME),
                    AddressBook.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * Saves {@link com.ericsson.ab.entity.AddressBook} to JSON data
     */
    private void save() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(DB_NAME));
            out.write(gson.toJson(currentAddressBook));
            out.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public AddressBook getCurrentAddressBook() {
        load(); // default: loading before accessing data
        return currentAddressBook;
    }

    public void setCurrentAddressBook(AddressBook book) {
        currentAddressBook = book;
        save();
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String position) {
        currentPosition = position;
    }
}
