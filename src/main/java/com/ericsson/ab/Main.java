/**
 * File name: Main.java Author: Willard Wang Create date: Apr 16, 2013
 */
package com.ericsson.ab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.ericsson.ab.command.AddCommandExecutor;
import com.ericsson.ab.command.CatCommandExecutor;
import com.ericsson.ab.command.CdCommandExecutor;
import com.ericsson.ab.command.CommandExecutor;
import com.ericsson.ab.command.HelpCommandExecutor;
import com.ericsson.ab.command.LsCommandExecutor;
import com.ericsson.ab.command.RemoveCommandExecutor;

/**
 * @author deathspeeder
 * 
 */
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // create stdin to read line from System.in
        BufferedReader stdin = new BufferedReader(new InputStreamReader(
                System.in));

        // cache String for storing command line
        String input = null;

        // keep read lines if not quit
        while (true) {
            System.out.print("ab> ");
            try {
                input = stdin.readLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                continue;
            }

            CommandExecutor executor = null;

            if (null == input || "".equals(input.trim())) {
                // empty input, continue reading line from stdin.
                continue;
            } else if ("!quit".equals(input.trim())) {
                break;
            } else if ("ls".equals(input.trim())) {
                executor = new LsCommandExecutor();
            } else if (input.trim().startsWith("cd")) {
                executor = new CdCommandExecutor(input);
            } else if ("add".equals(input.trim())) {
                executor = new AddCommandExecutor();
            } else if (input.trim().startsWith("cat")) {
                executor = new CatCommandExecutor(input);
            } else if ("remove".equals(input.trim())) {
                executor = new RemoveCommandExecutor();
            } else {
                executor = new HelpCommandExecutor();
            }

            Future<String> result = executorService.submit(executor);
            try {
                String resultString = result.get();
                if (!"".equals(resultString))
                    System.out.println("ab> " + result.get());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                continue;
            }
        }
        executorService.shutdown();
    }
}
