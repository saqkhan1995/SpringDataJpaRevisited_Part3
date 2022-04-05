package com.saq.jpaDemo;

import com.saq.jpaDemo.entity.Course;
import com.saq.jpaDemo.entity.Review;
import com.saq.jpaDemo.entity.Student;
import com.saq.jpaDemo.respository.CourseRepository;
import com.saq.jpaDemo.respository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//studentRepository.saveStudentWithPassport();

		//Demo for ManyToOne & OneToMany
		//courseRepository.addReviewsForCourseHardCoded();
		/*List<Review> reviews = new ArrayList<>();
		reviews.add(new Review("5", "Great Hands-on stuff."));
		reviews.add(new Review("5", "Hatsoff."));
		courseRepository.addReviewsForCourse(10003L, reviews);*/

		/*studentRepository.insertStudentAndCourseHardCoded();*/
		studentRepository.insertStudentAndCourse(new Student("Jack"), new Course("MicroServices in 100 Steps"));
	}
}
