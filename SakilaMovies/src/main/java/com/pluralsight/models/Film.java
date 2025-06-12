package com.pluralsight.models;

public class Film {

    //set Attribute for film

    private int film_id;
    private String title;
    private String description;
    private int year;
    private int length;

    //Generate constructor

    public Film(int film_id, String title, String description, int year, int length) {
        this.film_id = film_id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.length = length;
    }


    //Generate getter and setter

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "🎬 Film ID: " + film_id +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nRelease Year: " + year +
                "\nLength: " + length;
    }
}
