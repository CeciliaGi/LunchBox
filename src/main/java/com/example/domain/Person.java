package com.example.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Administrator on 2017-03-09.
 */

public class Person {
    private int PersonID;
    @Size(min = 1, max = 80)
    private String firstName;
    @Size(min = 1, max = 120)
    private String lastName;
    @Size(min = 1, max = 15)
    @Pattern(regexp = "^[0-9]+$")
    private String phoneNumber;

    public Person(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    public Person(int personID, String firstName, String lastName, String phoneNumber) {
        this.PersonID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public Person() {

    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {return phoneNumber;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
