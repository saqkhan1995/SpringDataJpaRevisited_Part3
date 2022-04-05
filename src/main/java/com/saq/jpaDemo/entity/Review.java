package com.saq.jpaDemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review {

    @Id                    // primary key
    @GeneratedValue        // JPA will generate value for this
    private Long id;

    private String rating;

    private String description;

    @ManyToOne      // From Review perspective, many reviews for one course
    private Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    protected Review() {            //JPA mandates a default constructor
    }

    public Review(String rating, String description) {        //No ID field in constructor as it's a @GeneratedValue from JPA
        this.rating = rating;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Long getId() {            //No setter for ID as it's a @GeneratedValue from JPA
        return id;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating='" + rating + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
