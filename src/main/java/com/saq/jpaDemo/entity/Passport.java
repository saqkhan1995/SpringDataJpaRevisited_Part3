package com.saq.jpaDemo.entity;

import javax.persistence.*;

@Entity
public class Passport {

    @Id                    // primary key
    @GeneratedValue        // JPA will generate value for this
    private Long id;

    private String number;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
    private Student student;

    protected Passport() {            //JPA mandates a default constructor
    }

    public Passport(String number) {        //No ID field in constructor as it's a @GeneratedValue from JPA
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {            //No setter for ID as it's a @GeneratedValue from JPA
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /*@Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }*/

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", student=" + student +
                '}';
    }
}
