package com.company.person;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Person implements Serializable {
    final String name;
    final LocalDate birth;
    final LocalDate death;

    Person(String name, LocalDate birth, LocalDate death) {
        this.name = name;
        this.birth = birth;
        this.death = death;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                '}';
    }

    public LocalDate getBirth() {
        return birth;
    }

    //zadanie 1
    public static Person fromFile(String path) {
        String name = null;
        LocalDate birth = null, death = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            name = reader.readLine();
            birth = parseDate(reader.readLine());
            death = parseDate(reader.readLine());
            reader.close();

        } catch (IOException | NullPointerException | DateTimeParseException e) {
           // e.printStackTrace();
        }
        return new Person(name, birth, death);
    }

    static LocalDate parseDate(String str) throws DateTimeParseException, NullPointerException {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    static String parseDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //Zadanie 3a
    public static void toFile(String path, Person person) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(person.name + "\n");
            writer.write(parseDate(person.birth) + "\n");
            if (person.death != null) {
                writer.write(parseDate(person.death) + "\n");
            }
            writer.close();
        } catch (IOException | NullPointerException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    //Zadanie 5
    public static void toBinaryFile(String path, Person[] people) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Person[] fromBinaryFile(String path) {
        Person[] result = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream iis = new ObjectInputStream(fis);
            result = (Person[]) iis.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

}
