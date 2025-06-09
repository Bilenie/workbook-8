package com.pluralsight;

import java.sql.*;

public class App {

    public static void main(String[] args) {
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

        Connection connection;//initialize connection variable to connect with DB.

        try{

            //connecting to the northwind DB.
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            //This like opening a new query window
            Statement statement = connection.createStatement();

            //the actual query
            String query ="SELECT ProductName FROM products";


            //Running the query
            ResultSet results = statement.executeQuery(query);


            // this is a way to view the result set but java doesn't have a spreadsheet view for us
            while (results.next()) {
                String ProductName = results.getString("ProductName"); // change it to fit the question
                System.out.println(ProductName); // this show what we want to display
            }

            // 3. Close the connection
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
