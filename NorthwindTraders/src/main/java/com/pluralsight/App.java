package com.pluralsight;

import java.sql.*;

public class App {

    public static void main(String[] args) {

        Connection connection;//initialize connection variable to connect with DB.

        try{

            //connecting to the northwind DB.
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "yearup");

            //This like opening a new query window
            Statement statement = connection.createStatement();

            //the actual query
            String query ="SELECT * FROM products";


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
