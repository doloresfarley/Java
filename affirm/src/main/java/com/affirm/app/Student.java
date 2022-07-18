package com.affirm.app;

import java.util.List;

public class Student {
    // private fields (must be explicit in Java, unlike C#)
    // control access, enabling encapsulation
    private String name;
    private double gpa;
    private List<String> courses;

    // take care returning mutable objects from getters
    // this can break encapsulation
    // Here, of course, String is immutable...
    public String getName() {
        return name;
    }

    // primitives are returned by value (copy of value)
    // so do not break encapsulation
    public double getGpa() {
        return gpa;
    }

    // take care when returning mutable data
    // this can break encapsulation
    // This version is OK, since List.of() creates an immutable list
    public List<String> getCourses() {
        return courses;
    }

    // all-private constructors prevent subclassing
    // and force use of a factory or similar
    private Student() {}

    // Mutable data passed into our initialization process might
    // break encapsulation if the caller retains the references.
    // Immutable data/structures are OK, but consider duplicating
    // any mutable elements/structures
    // Note that variable arguments "..." are passed as arrays,
    // and if the caller creates that array, they can change
    // the contents (but not the size) of that array.
    public static Student of(String name, double gpa, List<String> courses) {
        Student self = new Student();
        self.name = name;
        self.gpa = gpa;
        self.courses =  courses; // Java 9 immutable List factory
        return self;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", gpa=" + gpa +
                ", courses=" + courses +
                '}';
    }
}
