package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class NorthwindPreparedStatement {

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
//this is the place to practice pull requeste
        //get the username and password from the command line args
        String username = args[0];
        String password = args[1];

        //Load the MySQL driver (a special train that connects Java to MySQL)
        //Class.forName("com.mysql.cj.jdbc.Driver");

        try {

            // Connect to the northwind database using the given username/password
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind", username, password
            );

            Scanner myScanner = new Scanner(System.in);

            System.out.println("What do you want to do?");
            System.out.println("Select an option please :\n" +
                    " (1)Display all product \n 2)Display all customer \n 0) Exit");

            String choice = myScanner.nextLine();

            switch (choice) {
                case "1" -> System.out.println("Display all product place holder method");
                case "2" -> System.out.println("Display all customer place holder method");
                case "0" -> System.exit(0);// exit
                default -> System.out.println(" Invalid, try again!");

            }

            //Print the current database for sanity check
            System.out.println("Connected to DB: " + connection.getCatalog());

            // Show how many products are in the table
            Statement stmt = connection.createStatement();
            ResultSet count = stmt.executeQuery("SELECT COUNT(*) FROM products");
            if (count.next()) {
                System.out.println("Total products in table: " + count.getInt(1));
            }
            count.close();
            stmt.close();

            //Start our prepared statement => command that says “Show me everything from the products table”
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ProductID, ProductName,UnitPrice, UnitsInStock FROM products"// removed the where clause
            );
            //for my query to know what to put in place of ?.
            // preparedStatement.setInt(1, 1); // or any ProductID we want to test with



            // 3. Close the connection
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        } finally {
//            // close the resources
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (preparedStatement != null) {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
    }

    public static void getAllProducts(String username, String password) throws SQLException {

        //SELECT * FROM products
        //Connect to the database server with my password and username
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username, password
        );
        //query to grab the row data from the customer table
        String sql = "SELECT * FROM products";// if it's safe or not idk
        PreparedStatement preparedStatement = connection.prepareStatement(sql
        );

        // execute the query Run the command and get back a list of products (the result set)
        ResultSet resultSet = preparedStatement.executeQuery();

        // Print a header row
        System.out.printf("%-5s %-30s %-8s %-6s\n", "Id", "Name", "Price", "Stock");
        System.out.println("----- ------------------------------ -------- ------");


        //  Go through each product in the list, one at a time
        while (resultSet.next()) {
            int id = resultSet.getInt("ProductID");
            String name = resultSet.getString("ProductName");
            double price = resultSet.getDouble("UnitPrice");
            int stock = resultSet.getInt("UnitsInStock");

            //Print product info in a neat row
            // Print product info in a neat row
            System.out.printf("%-5d %-30s %-8.2f %-6d\n", id, name, price, stock); // this show what we want to display
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    public static void getAllCustomers(String username, String password) throws SQLException {

        //SELECT ContactName,CompanyName,City,Country,Phone FROM customer ORDER BY Country
        //Connect to the database server with my password and username
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username, password
        );
        //query to grab the row data from the customer table
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ContactName,CompanyName,City,Country,Phone FROM customer ORDER BY Country"
        );
        // execute the query Run the command and get back a list of products (the result set)
        ResultSet resultSet = preparedStatement.executeQuery();

        // Print a header row
        System.out.printf("%-5s %-30s %-8s %-6s\n", "Name", "CompanyName", "City", "Country");
        System.out.println("----- ------------------------------ -------- ------");

        //  Go through each row in the list, one at a time
        while (resultSet.next()) {
            String name = resultSet.getString("ContactName");
            String companyName = resultSet.getString("CompanyName");
            String city = resultSet.getString("City");
            String country = resultSet.getString("Country");

            //Print product info in a neat row
            // Print product info in a neat row
            System.out.printf("%-5d %-30s %-8.2f %-6d\n", name, companyName, city, country); // this show what we want to display
        }
        //closing the connection
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }


}



