/**
 * File name: CatCommandExecutor.java Author: Willard Wang Create date: Apr 17,
 * 2013
 */
package com.ericsson.ab.command;

import java.util.Map;

import com.ericsson.ab.entity.Contact;
import com.google.gson.Gson;

/**
 * Represents a cat operation. use cat command to display th item data
 * 
 * ab> cat lilei
 * 
 * ab> "lilei" : { "age": 27, "mobile" : "13700000000", "address" :
 * "Earth somewhere" }
 * 
 * @author deathspeeder
 * 
 */
public class CatCommandExecutor extends CommandExecutor {

    public CatCommandExecutor(String args) {

        String[] tokens = args.split("\\s");
        if (tokens.length == 1) {// cat
            this.args = "";
        } else { // cat [contact]
            this.args = tokens[1];
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    public String call() throws Exception {
        if ("".equals(args)) {
            System.err
                    .println("Error: You cannot use `cat' without parameter!");
            return "";
        }

        if ("".equals(dbManager.getCurrentPosition())) {
            if (ROOT.equals(args)) {
                Gson gson = new Gson();
                return gson.toJson(dbManager.getCurrentAddressBook());
            } else {
                System.err.println("Error: You can only `cat entries' here!");
            }
        } else if (ROOT.equals(dbManager.getCurrentPosition())) {
            Map<String, Contact> contacts = dbManager.getCurrentAddressBook()
                    .getEntries();
            if (!contacts.keySet().contains(args)) {
                System.err.println("Error: We have no key named `" + args
                        + "'!");
                return "";
            } else {
                Gson gson = new Gson();
                return args + " : " + gson.toJson(contacts.get(args));
            }
        }
        return "";
    }

}
