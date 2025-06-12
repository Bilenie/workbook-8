package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

public class SakilaMovies {

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



    }
}
