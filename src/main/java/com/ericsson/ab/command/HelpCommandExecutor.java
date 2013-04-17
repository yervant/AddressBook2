/**
 * File name: HelpCommandExecutor.java Author: Willard Wang Create date: Apr 16,
 * 2013
 */
package com.ericsson.ab.command;

/**
 * Getting help operation.
 * 
 * @author deathspeeder
 * 
 */
public class HelpCommandExecutor extends CommandExecutor {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    public String call() throws Exception {
        String result = "Commands:\n"
                + "\tls: list the items in current position\n"
                + "\tcd: go to the entry like go to a directory\n"
                + "\tcat: display th item data\n"
                + "\tadd: add new address entry to JSON\n"
                + "\tremove: remove one or more address entries\n"
                + "\t!help: get help\n" + "\t!quit: quit\n";
        return result;
    }

}
