package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class SakilaMovies {


    public static Scanner myScanner = new Scanner(System.in);
    public static BasicDataSource dataSource = new BasicDataSource();// idk if its good idea


    // Configure the datasource

    public static void main(String[] args) {


        //Making sure we passed in 2 argument from the CLI when we run the app.
        //page 45

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>"
            );
            System.exit(1);//quite the program if we don't have 2 argument
        }

        //get the username and password from the command line args
        String username = args[0];
        String password = args[1];



        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);



        // ( Connection connection = null; PreparedStatement preparedStatement = null; ResultSet resultSet = null)

        try(Connection connection = dataSource.getConnection()) {

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
while(true){
            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter first name ｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
            String firstName = myScanner.nextLine();

            System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆  Enter last name ｡･:*:･ﾟ★,｡･:*:･ﾟ☆ : ");
            String lastName = myScanner.nextLine();

            //validate first and last name
            if (!firstName.matches("^[A-Za-z]{1,50}$") || !lastName.matches("^[A-Za-z]{1,50}$")) {
                System.out.println("🚫 Invalid name format. Please use only letters and make sure it's 1–50 characters long.");
                return; // exit or ask again
            }

            //query to grab the row data from the tables
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT\n" +
                            "\ta.first_name, \n" +
                            "\ta.last_name,\n" +
                            "\tf.title\n" +
                            "FROM \n" +
                            "   actor a\n" +
                            "   JOIN\n" +
                            "   film_actor fa ON ( a.actor_id = fa.actor_id)\n" +
                            "   JOIN \n" +
                            "   film f ON (f.film_id = fa.film_id) WHERE a.first_name = ? AND a.last_name = ?"
            );

            preparedStatement.setString(1, firstName);// replace the placeholder for firstName
            preparedStatement.setString(2, lastName);// replace the placeholder for lastName


            ResultSet resultSet = preparedStatement.executeQuery();//run the query

            //you don’t print headers if there's no match, and the loop runs only if results exist
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
                } while (resultSet.next());//process results

            } else {
                System.out.println(" No matches found for that actor name.");
            }
            break;
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    // finally{ no need for finally now we use try resource method down below on the methods

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
