package com.pluralsight;

import java.sql.*;

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

            // 3. Close the connection
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

