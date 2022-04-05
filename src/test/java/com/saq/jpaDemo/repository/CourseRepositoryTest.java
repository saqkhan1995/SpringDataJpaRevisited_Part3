package com.saq.jpaDemo.repository;

import com.saq.jpaDemo.JpaDemoApplication;
import com.saq.jpaDemo.entity.Course;
import com.saq.jpaDemo.entity.Review;
import com.saq.jpaDemo.respository.CourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class CourseRepositoryTest {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager em;

    @Test
    public void testFindById() {
        LOGGER.info("Testing is running");
        Course course = repository.findById(10002L);
        assertEquals("Spring in 50 steps", course.getName());
    }

    //this unit test is actually modifying the state of the DB, hence use the annotation "@DirtiesContext" to avoid DB changes due to unit tests
    @Test
    @DirtiesContext
    public void testDeleteById() {
        repository.deleteById(10003L);
        assertNull(repository.findById(10003L));
//        Course course = repository.findById(10002L);
//        assertEquals("Spring in 50 steps", course.getName());
//        assertThatThrownBy(() -> repository.findById(10002L)).isInstanceOf(Exception.class);
    }

    //this unit test is actually modifying the state of the DB, hence use the annotation "@DirtiesContext" to avoid DB changes due to unit tests
    @Test
    @DirtiesContext
    public void testSave() {
        //get the course
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 steps", course.getName());
        //update the course
        course.setName("JPA in 50 steps updated");

        repository.save(course);

        //check the value
        Course course1 = repository.findById(10001L);
        assertEquals("JPA in 50 steps updated", course1.getName());
    }

    @Test
    @Transactional
    public void retrieveReviewsForCourse() {
        Course course = repository.findById(10001L);
        LOGGER.info("{}", course.getReviews());
    }

    @Test
    @Transactional
    public void retrieveCourseForReview() {
        Review review = em.find(Review.class, 50001L);
        LOGGER.info("{}", review.getCourse());
    }
}
