package com.pluralsight;

import java.sql.*;

public class App {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

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

        //load the driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        //create the connection and prepared statement
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username,password
        );

        //Start our prepared statement
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT ProductName FROM products WHERE ProductID = ?"
        );

        //set the parameter for the prepared statement
        preparedStatement.setInt(1,14);

      // preparedStatement.setString(1,"Sa%");

        // execute the query
        ResultSet resultSet = preparedStatement.executeQuery();

            // loop through the results
            while (resultSet.next()) {
                System.out.printf(
                        "productName = %s\n",
                        resultSet.getString(1)
                ); // this show what we want to display
            }

            // 3. Close the connection
            resultSet.close();
            preparedStatement.close();
            connection.close();
    }

}
