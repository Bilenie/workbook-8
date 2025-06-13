package com.pluralsight.util;

public class UiHelper {

    //validation method
    public static boolean validationString(String name) {
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

}
