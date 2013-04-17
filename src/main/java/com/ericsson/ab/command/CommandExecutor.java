/**
 * File name: CommandExcutor.java Author: Willard Wang Create date: Apr 16, 2013
 */
package com.ericsson.ab.command;

import java.util.concurrent.Callable;

import com.ericsson.ab.db.DBManager;

/**
 * Base class for all command executors, which holds a
 * {@link com.ericsson.ab.db.DBManager} singleton instance.
 * 
 * @author deathspeeder
 * 
 */
public abstract class CommandExecutor implements Callable<String> {

    /**
     * command parameter
     */
    protected String args;

    protected static final String ROOT = "entries";

    protected DBManager dbManager = DBManager.instance();

    public CommandExecutor() {

    }

    public CommandExecutor(String args) {
        this();
        this.args = args;
    }

}
