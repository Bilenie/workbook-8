package com.pluralsight.userInterface;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import java.util.List;
import java.util.Scanner;
import static com.pluralsight.dao.SakilaDataManager.getActorsByFirstLastName;
import static com.pluralsight.dao.SakilaDataManager.getMoviesByActorId;
import static com.pluralsight.util.UiHelper.pauseBeforeContinuing;
import static com.pluralsight.util.UiHelper.validationString;

public class Ui {

    public static Scanner myScanner = new Scanner(System.in);

    // Entry point to run the app
    public static void startApp() {
        boolean keepRunning = true;

        while (keepRunning) {
            homeScreen(); // Run your current UI logic

            System.out.print("\n🔁 Would you like to search again? (Y/N): ");
            String input = myScanner.nextLine().trim().toUpperCase();

            if (!input.equals("Y")) {
                System.out.println("👋 Thank you for using ACTORS/MOVIE CHOICES! Goodbye!");
                keepRunning = false; // exit the app
            }
        }
    }


    public static void homeScreen() {

        System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.");
        System.out.println("｡                                          ｡");
        System.out.println("｡     ♡ Welcome to ACTORS/MOVIE CHOICES ♡  ｡");
        System.out.println("｡        ~ Home Entertainments  ~          ｡");
        System.out.println("｡                                          ｡");
        System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.\n");
        pauseBeforeContinuing(1000);

        System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
        System.out.println("   Search for your favorites Actors   ");
        System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
        pauseBeforeContinuing(1000);

        //Prompt user for first and last name of actors they like to search for movies

        boolean askName = true;

        while (askName) {

            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter first name ｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
            String firstName = myScanner.nextLine();


            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter last name ｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
            String lastName = myScanner.nextLine();

            // this is validating the user input => if format is correct or not

            if (!validationString(firstName) || !validationString(lastName)) {
                pauseBeforeContinuing(1000);
                System.out.println("🚫 Invalid name format. Please use only letters and make sure it's 1–50 characters long.");
                pauseBeforeContinuing(1000);
                continue;
            }

            //Create a list to hold the actors
            List<Actor> matchingActors = getActorsByFirstLastName(firstName, lastName);

            //Validating if the actors exists from the database

            if (matchingActors.isEmpty()) {
                System.out.println("😢 No match found for that actor.");
                System.out.print("🔁 Would you like to try again? (Y/N): ");
                String tryAgain = myScanner.nextLine().trim().toUpperCase();

                if (!tryAgain.equals("Y")) {
                    System.out.println("👋 Goodbye!");
                    return; // exit method or app
                }
            } else {
                System.out.println("🎉 Match found!");
                System.out.println("🎭 Matching Actor(s):");

                // ✅ Print each actor's info
                for (Actor actor : matchingActors) {
                    System.out.println("⭐ ID: " + actor.getActor_id() + " — " + actor.getFirst_name() + " " + actor.getLast_name());
                }

                askName = false;
            }

        }
        boolean askId = true;


        while (askId) {
            pauseBeforeContinuing(1000);
            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter Actor_id｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");

            String actorID = myScanner.nextLine();

            // this is validating the user input => if format is correct or not
            if (!actorID.matches("\\d+")) {
                System.out.println("💔 Oops! That ID isn’t on the List. Please enter one from our categoryID list! 💕");
                continue;
            }
            int actorId = Integer.parseInt(actorID);

            //Create a list to hold the movies
            List<Film> movies = getMoviesByActorId(actorId);

            //Validating if movies title exist from the database
            if (movies.isEmpty()) {
                System.out.println("😕 No movies found for this actor ID.");
                System.out.print("🔁 Would you like to try a different actor ID? (Y/N): ");
                String tryAgain = myScanner.nextLine().trim().toUpperCase();

                if (!tryAgain.equals("Y")) {
                    System.out.println("👋 Goodbye!");
                    return; // exit program or UI flow
                }
            } else {
                System.out.println("🎬 Here are the movies for that actor:");
                for (Film movie : movies) {
                    System.out.println("🎞️ " + movie.getTitle());
                }
                askId = false; // end loop
            }
        }

    }
}



