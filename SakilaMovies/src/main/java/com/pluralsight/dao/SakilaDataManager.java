package com.pluralsight.dao;

import com.pluralsight.models.Film;
import com.pluralsight.models.Actor;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SakilaDataManager {

    //create our BasicDataSource/ set attribute
    public static BasicDataSource dataSource;

    // Generate Constructor

    public SakilaDataManager(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // This class is where all the query happen
    public static List<Actor> getActorsByFirstLastName(String firstName, String lastName) {

        //create an array list to hold the products we will be returning
        List<Actor> actors = new ArrayList<>();


        try (Connection connection = dataSource.getConnection()) {

            //query to grab the row data from the tables
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT\n" +
                            "\ta.*\n" +
                            "FROM \n" +
                            "   actor a\n" +
                            "WHERE \n" +
                            "  a.first_name =?   AND a.last_name = ?"

            );

            preparedStatement.setString(1, firstName);// replace the placeholder for firstName
            preparedStatement.setString(2, lastName);// replace the placeholder for lastName

            ResultSet results = preparedStatement.executeQuery();//run the query

            // Check if results has any rows / Is there anything inside before we start looping?
            if (!results.isBeforeFirst()) { // If false, no rows returned
                return actors; // return empty list
            }

           // printResultSetNicely(results);// checking if the LN/FN exist in the database


            //loop over the results and create product objects and add the to the array list
            while (results.next()) {
                int id = results.getInt("actor_id");
                String fName = results.getString("first_name");
                String lName = results.getString("last_name");

                Actor actor = new Actor(id, fName, lName);//int actor_id, String first_name, String last_name
                actors.add(actor);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actors;
    }

    public static List<Film> getMoviesByActorId(int actor_id) {
        //create an array list to hold the products we will be returning
        List<Film> movieTitle = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {

            //query to grab the row data from the tables
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT\n" +
                            "\tf.title\n" +
                            "FROM \n" +
                            "   actor a\n" +
                            "    JOIN\n" +
                            "   film_actor fa ON ( a.actor_id = fa.actor_id)\n" +
                            "    JOIN \n" +
                            "   film f ON (f.film_id = fa.film_id)\n" +
                            "  WHERE a.actor_id = ?"

            );

            preparedStatement.setInt(1, actor_id);// replace the placeholder for firstName


            ResultSet results = preparedStatement.executeQuery();//run the query

            // Check if results has any rows / Is there anything inside before we start looping?
            if (!results.isBeforeFirst()) { // If false, no rows returned
                return movieTitle;
            }


            //printResultSetNicely(results);// checking if the id exist in the database

            //loop over the results and create product objects and add the to the array list
            while (results.next()) {

                String title = results.getString("title");

                Film movie = new Film(title);//int film_id, String title, String description, int year, int length
                movieTitle.add(movie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return movieTitle;

    }


}
