package com.pluralsight.util;

import com.pluralsight.userInterface.Ui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UiHelper {

    //validation method
    public static boolean validationString(String name){
        //validate first and last name
       return name.matches("^[A-Za-z]{1,50}$");

    }

    // Waits for a specific time in milliseconds (like 2000 ms = 2 seconds).Then continues automatically.
    public static void pauseBeforeContinuing(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Pause interrupted.");
        }

    }

    //Method to match if the FN and LN found in the DB

    public static void printResultSetNicely(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            // Print header once
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-25s", metaData.getColumnName(i));
            }
            System.out.println();

            // Print first row, then continue if more
            do {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("=".repeat(25));
                }
                System.out.println();

                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    System.out.printf("%-25s", value);
                }
                System.out.println();
            } while (resultSet.next());

        } else {
            System.out.println("No matches found.");
        }
    }

}
