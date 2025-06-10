package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class NorthwindPreparedStatement {


    public static Scanner myScanner = new Scanner(System.in);
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


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.");
            System.out.println("｡                                          ｡");
            System.out.println("｡     ♡ Welcome to DATA ON DATABASE ♡    ｡");
            System.out.println("｡              ~  SQL ~               ｡");
            System.out.println("｡                                          ｡");
            System.out.println(".｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡..｡*♡*｡.\n");

            System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧");
            System.out.println("         What do you want to do?     ");
            System.out.println("✧✦✧✦✧✦✧✦✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✧✦✦✧");

            while (true) {
                System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆ Select an option please ☆･ﾟ:*:･｡,★･ﾟ:*:･｡");
                System.out.println("        1)Display all product");
                System.out.println("        2)Display all customer");
                System.out.println("        3)Display all categories");
                System.out.println("        0) Exit");
                System.out.println("｡･:*:･ﾟ★,｡･:*:･ﾟ☆･ﾟ:*:･｡★｡･ﾟ:*:･ﾟ☆･ﾟ");

                String choice = myScanner.nextLine();

                switch (choice) {
                    case "1" -> getAllProducts(username, password);
                    case "2" -> getAllCustomers(username, password);
                    case "3" -> {
                        getAllCategories(username, password);
                        getProductByCategories(username, password);
                    }
                    case "0" -> {
                        System.out.println("Goodbye!");
                        System.exit(0);// exit
                    }
                    default -> System.out.println(" Invalid, try again!");
                }
                System.out.println("\n:*:･ﾟ★ Press Enter to return to menu...:*:･ﾟ★,");
                myScanner.nextLine();
            }} catch(SQLException e){
                throw new RuntimeException(e);
            } finally{
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();

                }

            }
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

        printResultSet(resultSet);

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
                "SELECT ContactName,CompanyName,City,Country,Phone FROM customers ORDER BY Country"
        );

        // execute the query Run the command and get back a list of products (the result set)
        ResultSet resultSet = preparedStatement.executeQuery();

        //  Go through each row in the list, one at a time
         printResultSet(resultSet);//taking the result set and taking the metadata . loop over the result set and metadata knows the column name
        //closing the connection
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    public static void getAllCategories(String username, String password)throws SQLException{

        //Connect to the database server with my password and username
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username, password
        );

        //query to grab the row data from the customer table
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT CategoryID,CategoryName FROM categories ORDER BY CategoryID"
        );

        ResultSet resultSet = preparedStatement.executeQuery();//run the query

        printResultSet(resultSet); //display the query

        //closing the connection
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    public static void getProductByCategories(String username, String password)throws SQLException{

        //Connect to the database server with my password and username
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username, password
        );

        //query to grab the row data from the customer table
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ProductName,ProductID, UnitPrice, UnitsInStock, CategoryID FROM products  WHERE CategoryID = ? "
        );

        //Prompt the user to choose from the display categories
        System.out.println("enter your category Id : \n");
        String categoryChoice = myScanner.nextLine();

        // set the parameters for the prepared statement
        preparedStatement.setString(1, categoryChoice);// replace it with categoryChoice of usere

        ResultSet resultSet = preparedStatement.executeQuery();//run the query

        printResultSet(resultSet); //display the query

        //closing the connection
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }


    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-30s", metaData.getColumnName(i)); // 20 characters wide
            }
            System.out.println(); // new line after each row

            for (int i = 1; i <= columnCount; i++) {
                System.out.print("=".repeat(28));
            }
            System.out.println();

            for(int i = 1;i<=columnCount;i++){
                String value = rs.getString(i); // generic, works for most types
                System.out.printf("%-30s",value);
            }
            System.out.println();
        }
    }

}
