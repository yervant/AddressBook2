/**
 * File name: CdCommandExecutor.java Author: Willard Wang Create date: Apr 16,
 * 2013
 */
package com.ericsson.ab.command;

/**
 * Represents a cd operation.use cd command to go to the entry like go to a
 * directory
 * 
 * ab> cd entries
 * 
 * ab> ls
 * 
 * ab> lilei hanmeimei
 * 
 * @author deathspeeder
 * 
 */
public class CdCommandExecutor extends CommandExecutor {

    public CdCommandExecutor(String args) {
        String[] tokens = args.split("\\s");
        if (tokens.length == 1) {// cd
            this.args = "";
        } else { // cd [dir]
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
            // does nothing
            return "";
        }

        if ("".equals(dbManager.getCurrentPosition())) {
            if (ROOT.equals(args)) {// only accept `cd entries'
                dbManager.setCurrentPosition(ROOT);
            } else {
                System.err.println("Error: You can only cd to entries!");
            }
        } else if (ROOT.equals(dbManager.getCurrentPosition())) {
            if ("..".equals(args)) {// cd ..
                dbManager.setCurrentPosition("");
            } else {
                // already at directory `entries'
                System.err.println("Error: You cannot cd to a contact entry!");
            }
        }
        return "";
    }

}
