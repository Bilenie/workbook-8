package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class NorthwindPreparedStatement {


    public static Scanner myScanner = new Scanner(System.in);

   public static BasicDataSource dataSource = new BasicDataSource();

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

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);



        // ( Connection connection = null; PreparedStatement preparedStatement = null; ResultSet resultSet = null)

        try {

            System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.");
            System.out.println("｡                                          ｡");
            System.out.println("｡     ♡ Welcome to DATA ON DATABASE ♡    ｡");
            System.out.println("｡              ~  SQL ~               ｡");
            System.out.println("｡                                          ｡");
            System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.\n");
            pauseBeforeContinuing(1000);

            System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
            System.out.println("         What do you want to do?     ");
            System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
            pauseBeforeContinuing(1000);

            while (true) {
                System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆ Select an option please ☆･ﾟ:*:･｡,★･ﾟ:*:･｡");
                pauseBeforeContinuing(1000);
                System.out.println("        1)Display all product");
                System.out.println("        2)Display all customer");
                System.out.println("        3)Display all categories");
                System.out.println("        0) Exit");
                System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆･ﾟ:*:･｡★｡･ﾟ:*:･ﾟ☆･ﾟ");

                String choice = myScanner.nextLine();

                switch (choice) {
                    case "1" -> getAllProducts();
                    case "2" -> getAllCustomers();
                    case "3" -> {
                        getAllCategories();
                        getProductByCategories();
                    }
                    case "0" -> {
                        pauseBeforeContinuing(1000);
                        System.out.println("Goodbye!");
                        System.exit(0);// exit
                    }
                    default -> System.out.println(" Invalid, try again!");
                }
                pauseBeforeContinuing(1000);
                System.out.println("\n:*:･ﾟ★ Press Enter to return to menu...:*:･ﾟ★,");
                myScanner.nextLine();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    // finally{ no need for finally now we use try resource method down below on the methods

    }

    public static void getAllProducts() throws SQLException {

        //SELECT * FROM products
        //Connect to the database server with my password and username
        try (Connection connection = dataSource.getConnection()) {
            //query to grab the row data from the customer table
            String sql = "SELECT * FROM products";// if it's safe
            PreparedStatement preparedStatement = connection.prepareStatement(sql
            );

            // execute the query Run the command and get back a list of products (the result set)
            ResultSet resultSet = preparedStatement.executeQuery();

            printResultSet(resultSet);

            //No need to close the connection try resource method did that automatically for us

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getLocalizedMessage());

        }

    }

    public static void getAllCustomers() throws SQLException {

        //SELECT ContactName,CompanyName,City,Country,Phone FROM customer ORDER BY Country
        //Connect to the database server with my password and username
        try (Connection connection = dataSource.getConnection()) {
            //query to grab the row data from the customer table
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ContactName,CompanyName,City,Country,Phone FROM customers ORDER BY Country"
            );

            // execute the query Run the command and get back a list of products (the result set)
            ResultSet resultSet = preparedStatement.executeQuery();

            //  Go through each row in the list, one at a time by calling this method
            printResultSet(resultSet);//taking the result set and taking the metadata . loop over the result set and metadata knows the column name

            //No need to close the connection try resource method did that automatically for us

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getLocalizedMessage());
            sqlException.printStackTrace();
        }
    }

    public static void getAllCategories() throws SQLException {

        //Connect to the database server with my password and username
        try (Connection connection = dataSource.getConnection()) {

            //query to grab the row data from the customer table
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT CategoryID,CategoryName FROM categories ORDER BY CategoryID"
            );

            ResultSet resultSet = preparedStatement.executeQuery();//run the query

            printResultSet(resultSet); //display the query

            //No need to close the connection try resource method did that automatically for us

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void getProductByCategories() throws SQLException {

        //Connect to the database server with my password and username
        try (Connection connection = dataSource.getConnection()) {
            //query to grab the row data from the customer table
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ProductName,ProductID, UnitPrice, UnitsInStock, CategoryID FROM products  WHERE CategoryID = ? "
            );

            boolean askAgain = true;
            while (askAgain) {

                pauseBeforeContinuing(1000);

                //Prompt the user to choose from the display categories
                System.out.println("enter your category Id : \n");
                String categoryChoice = myScanner.nextLine();


                if (!categoryChoice.matches("(?i)^(1|2|3|4|5|6|7|8)$")) {
                    System.out.println("💔 Oops! That ID isn’t on the List. Please enter one from our categoryID list! 💕");
                    continue;
                }

            // set the parameters for the prepared statement
            preparedStatement.setString(1, categoryChoice);// replace it with categoryChoice of usere

            ResultSet resultSet = preparedStatement.executeQuery();//run the query

            printResultSet(resultSet); //display the query
                break;
        }
            //No need to close the connection try resource method did that automatically for us


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            ;
        }
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();


        for (int i = 1; i <= columnCount; i++) {
            // 25 characters wide
            System.out.printf("%-25s", metaData.getColumnName(i));//outside the while loop => for not to repeat the column name.
        }
        System.out.println(); // new line after each row
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print("=".repeat(25));
            }
            System.out.println();

            for (int i = 1; i <= columnCount; i++) {
                String value = rs.getString(i); // generic, works for most types
                System.out.printf("%-25s", value);
            }
            System.out.println();
        }
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
