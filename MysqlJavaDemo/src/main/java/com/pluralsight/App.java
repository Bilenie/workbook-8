package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args)  {
//throws SQLException
        // load the MySQL Driver
        // Class.forName("com.mysql.cj.jdbc.Driver");

        // 1. open a connection to the database

        // use the database URL to point to the correct database

        //this is like opening MySQL workbench and clicking localhost.
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "yearup");

            //get connection error thrown if there is something wrong with database, no database found all the things that might be

            // create statement

            // the statement is tied to the open connection

            //This like opening a new query window
            Statement statement = connection.createStatement();

            // define your query

            // This is like typing the query in the new query window
            //String query = "SELECT Name FROM city WHERE CountryCode = 'USA'";
            String query ="SELECT Title FROM FILM";

            // 2. Execute your query

            //This like clicking the lighting bulb

            ResultSet results = statement.executeQuery(query);

            // process the results

            // this is a way to view the result set but java doesn't have a spreadsheet view for us
            while (results.next()) {
                String Title = results.getString("Title");
                System.out.println(Title);
            }
            // 3. Close the connection

            //this is like closing MySQL workbench
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            //closing mysql
//            connection.close();
//        }

    }
}
