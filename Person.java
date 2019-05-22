package com.example.trial4;

import java.io.Serializable;

public class Person implements Serializable {
    private int id;
    private String username;
    private String password;
    private int puzzle;

    public Person(int parseInt, String string, String string1, int int1) {
        this.id=parseInt;
        this.username=string;
        this.password=string1;
        this.puzzle=int1;

    }

    public Person(){

    }



    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public int getPuzzle() {

        return puzzle;
    }

    public void setPuzzle(int puzzle) {

        this.puzzle = puzzle;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }
}
