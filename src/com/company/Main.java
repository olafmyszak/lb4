package com.company;

import com.company.person.DirectoryPersonPersistenceManager;
import com.company.person.Person;
import com.company.person.PersonPersistenceManager;

public class Main {

    public static void main(String[] args) {
        PersonPersistenceManager manager = new DirectoryPersonPersistenceManager();

        Person[] people = manager.load("zadanie4a");

        manager.save("data", people);
    }
}
