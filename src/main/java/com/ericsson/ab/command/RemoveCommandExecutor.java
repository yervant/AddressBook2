/**
 * File name: RemoveCommandExecutor.java Author: Willard Wang Create date: Apr
 * 17, 2013
 */
package com.ericsson.ab.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ericsson.ab.entity.AddressBook;

/**
 * Represents a remove operation.use remove command to remove one or more
 * address entries
 * 
 * ab> cd entries
 * 
 * ab> remove
 * 
 * please give the key: xiaoming
 * 
 * xiaoming was deleted from JSON
 * 
 * @author deathspeeder
 * 
 */
public class RemoveCommandExecutor extends CommandExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    public String call() throws Exception {
        if ("".equals(dbManager.getCurrentPosition())) {
            System.err.println("Error: You cannot remove anything at here!");
            return "";
        } else if (ROOT.equals(dbManager.getCurrentPosition())) {
            BufferedReader stdin = new BufferedReader(new InputStreamReader(
                    System.in));
            String input = null;
            while (true) {
                System.out.print("please give the key: ");
                input = stdin.readLine();
                AddressBook book = dbManager.getCurrentAddressBook();
                if (!book.getEntries().keySet().contains(input)) {
                    System.err.println("Error: There is no key named `" + input
                            + "'");
                    continue;
                }
                book.getEntries().remove(input);
                dbManager.setCurrentAddressBook(book);
                System.out.println(input + " was deleted from JSON");
                break;
            }

        }
        return "";
    }

}
