/**
 * File name: AddCommandExecutor.java Author: Willard Wang Create date: Apr 17,
 * 2013
 */
package com.ericsson.ab.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.ericsson.ab.entity.AddressBook;
import com.ericsson.ab.entity.Contact;
import com.google.gson.Gson;

/**
 * Represents a add operation. use add command to add new address entry to JSON
 * 
 * ab> cd entries
 * 
 * ab> add
 * 
 * key: xiaoming
 * 
 * value: { "age": 28, "mobile" : "13700000002", "address" :
 * "Earth somewhere too" }
 * 
 * address entry added
 * 
 * ab> ls
 * 
 * ab> lilei hanmeimei xiaoming
 * 
 * @author deathspeeder
 * 
 */
public class AddCommandExecutor extends CommandExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    public String call() throws Exception {
        if ("".equals(dbManager.getCurrentPosition())) {
            System.err.println("Error: You cannot add at current directory!");
            return "";
        } else if (ROOT.equals(dbManager.getCurrentPosition())) {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(
                    System.in));
            addContact(stdin);
        }

        return "";
    }

    /**
     * Creates while loops to read key and value from stdin
     * 
     * @throws IOException
     * 
     */
    private void addContact(BufferedReader stdin) throws IOException {
        String input;
        while (true) {
            System.out.print("key: ");
            input = stdin.readLine().trim();
            if ("".equals(input))
                continue;
            else {
                if (dbManager.getCurrentAddressBook().getEntries()
                        .containsKey(input)) {
                    System.err.println("Error: The key `" + input
                            + "' is existed!");
                    continue;
                } else {
                    String key = input;
                    Contact contact;
                    while (true) {
                        System.out.print("value: ");
                        input = stdin.readLine().trim();
                        Gson gson = new Gson();
                        try {
                            contact = gson.fromJson(input, Contact.class);
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                        break;
                    }
                    AddressBook book = dbManager.getCurrentAddressBook();
                    Map<String, Contact> contacts = book.getEntries();
                    try {
                        contacts.put(key, contact);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                    }
                    book.setEntries(contacts);
                    dbManager.setCurrentAddressBook(book);

                    break;
                }
            }
        }
        System.out.println("address entry added");
    }

}
