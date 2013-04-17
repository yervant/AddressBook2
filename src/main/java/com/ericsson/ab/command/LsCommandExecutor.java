/**
 * File name: LsCommandExecutor.java Author: Willard Wang Create date: Apr 16,
 * 2013
 */
package com.ericsson.ab.command;

import java.util.Iterator;

/**
 * Represents a ls operation.use ls command to list the items in current
 * position
 * 
 * ab> ls
 * 
 * ab> entries
 * 
 * @author deathspeeder
 * 
 */
public class LsCommandExecutor extends CommandExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    public String call() throws Exception {
        if ("".equals(dbManager.getCurrentPosition()))
            return ROOT;
        if (ROOT.equals(dbManager.getCurrentPosition())) {
            StringBuilder result = new StringBuilder();
            Iterator<String> it = dbManager.getCurrentAddressBook()
                    .getEntries().keySet().iterator();
            while (it.hasNext()) {
                result.append(it.next() + " ");
            }

            // remove last space char if the result is not empty
            if (result.length() > 0)
                result.deleteCharAt(result.length() - 1);
            return result.toString();
        }

        // return nothing for other path
        return "";
    }

}
