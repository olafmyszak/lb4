package com.company.person;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static com.company.person.Person.parseDate;

public class CsvPersonPersistenceManager implements PersonPersistenceManager {
    @Override
    public Person[] load(String path) {
        List<Person> result = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (null != (line = reader.readLine())) {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");
                String name = tokenizer.nextToken();
                LocalDate birth = parseDate(tokenizer.nextToken());
                LocalDate death = null;
                if (tokenizer.hasMoreTokens())
                    death = parseDate(tokenizer.nextToken());
                result.add(new Person(name, birth, death));
            }
            reader.close();
        } catch (IOException | NullPointerException | DateTimeParseException e) {
            e.printStackTrace();
        }

        return result.toArray(new Person[0]);
    }

    @Override
    public void save(String path, Person[] people) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (Person person : people) {
                writer.write(person.name);
                writer.write(';' + parseDate(person.birth));
                writer.write(";" + (person.death == null ? "" : parseDate(person.death)));
                writer.newLine();
            }
            writer.close();
        } catch (IOException | NullPointerException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    public void sortCsv(String path) {
        Person[] people = load(path);
        Arrays.sort(people, Comparator.comparing(p -> p.birth));
        save(path, people);
    }
}
