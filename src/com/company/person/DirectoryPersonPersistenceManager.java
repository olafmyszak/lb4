package com.company.person;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static com.company.person.Person.fromFile;

public class DirectoryPersonPersistenceManager implements PersonPersistenceManager {

    @Override
    public Person[] load(String path) {
        File[] files = new File(path).listFiles();
        Person[] result = new Person[0];
        if (files != null) {
            result = new Person[files.length];
        }
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                result[i] = fromFile(files[i].getPath());
            }
        }
        return result;
    }

    @Override
    public void save(String path, Person[] people) {
        File dir = new File(path);

        if (dir.exists() && dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (!file.isDirectory()) {
                    if (!file.delete()) {
                        System.out.println("Error deleting files");
                    }
                }
            }
        } else {
            try {
                Files.createDirectory(dir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Person person : people) {
            Person.toFile(path + "/" + person.name + ".txt", person);
        }
    }
}
