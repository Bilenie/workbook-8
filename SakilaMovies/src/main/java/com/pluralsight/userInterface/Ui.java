package com.pluralsight.userInterface;

import java.util.Scanner;

import static com.pluralsight.util.UiHelper.pauseBeforeContinuing;
import static com.pluralsight.util.UiHelper.validationString;

public class Ui {

public static Scanner myScanner = new Scanner(System.in);

    public void homeScreen() {
        System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.");
        System.out.println("｡                                          ｡");
        System.out.println("｡     ♡ Welcome to MOVIE CHOICES ♡    n    ｡");
        System.out.println("｡        ~ Home Entertainments  ~          ｡");
        System.out.println("｡                                          ｡");
        System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.\n");
        pauseBeforeContinuing(1000);

        System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
        System.out.println("   Search for Movies by your favorites Actors   ");
        System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
        pauseBeforeContinuing(1000);

        //Prompt user for first and last name of actors they like to search for movies

        boolean askName = true;

        while (askName) {

            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter first name ｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
            String firstName = myScanner.nextLine();


            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter last name ｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
            String lastName = myScanner.nextLine();

            if (!validationString(firstName) || validationString(lastName)) {
                pauseBeforeContinuing(1000);
                System.out.println("🚫 Invalid name format. Please use only letters and make sure it's 1–50 characters long.");
                pauseBeforeContinuing(1000);
                continue;
            }
            break;
        }
        boolean askId = true;

        while (askId) {
            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter Actor_id｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
           int actorID = myScanner.nextInt();

//            if (!actorID.matches("\\d+")) {
//                System.out.println("💔 Oops! That ID isn’t on the List. Please enter one from our categoryID list! 💕");
//                continue;
//            }
            break;
        }
    }

}
