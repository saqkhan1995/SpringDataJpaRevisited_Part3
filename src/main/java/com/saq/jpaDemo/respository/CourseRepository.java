package com.saq.jpaDemo.respository;

import com.saq.jpaDemo.entity.Course;
import com.saq.jpaDemo.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional            //To enable Transaction Management for any DB related changes , example: deleteByID(id), if something fails the changes are rolled back
public class CourseRepository {

    private Logger LOGGER = LoggerFactory.getLogger(CourseRepository.class);

    @Autowired
    EntityManager entityManager;

    // 1) public Course findById(String id);
    // 2) public Course save(Course course);   ->insert or update
    // 3) public void deleteById(String id);

    // 1) public Course findById(String id);
    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    // 3) public void deleteById(String id);
    public void deleteById(Long id) {
        //get the course
        Course course = findById(id);

        //delete operation should be done in something known as "transaction management" -> if something fails the changes are rolled back
        //To enable this -> use "@Transactional" Annotation on class level
        entityManager.remove(course);
    }

    // 2) public Course save(Course course);   ->insert or update
    public Course save(Course course) {
        if (Objects.isNull(course.getId())) {
            //insert
            entityManager.persist(course);
        } else {
            //update
            entityManager.merge(course);
        }
        return course;
    }

    //Experiment with EntityManager
    public void playWithEntityManager0() {
        Course course1 = new Course("Web Services in 100 steps");
        entityManager.persist(course1);

        Course course2 = findById(10001L);
        course2.setName("JPA in 50 steps - Updated");
        // entityManager.merge(course2); // Don't really need to call this explicitly as the @transactional takes care of this by keeping track of the obj-orm mapping

    }


    //Experiment with EntityManager
    public void playWithEntityManager() {
        Course course1 = new Course("Web Services in 100 steps");
        entityManager.persist(course1);

        course1.setName("Web Services in 100 steps - updated");
        // entityManager.merge(course); // Don't really need to call this explicitly as the @transactional takes care of this by keeping track of the obj-orm mapping

    }

    //Experiment with EntityManager  - flush(), detach(), clear()
    public void playWithEntityManager2() {
        Course course1 = new Course("Web Services in 100 steps");
        entityManager.persist(course1);
        entityManager.flush();   // "flush()" method ensure that the changes up to this point are sent out to the database

        course1.setName("Web Services in 100 steps - updated");
        entityManager.flush();
        // entityManager.merge(course); // Don't really need to call this explicitly as the @transactional takes care of this by keeping track of the obj-orm mapping

        Course course2 = new Course("Angular in 100 steps");
        entityManager.persist(course2);
        entityManager.flush();

        entityManager.detach(course2);   //Ensures changes on "course2" obj are no longer tracked by the EntityManager
        //The below code is not saved on to the DB as it's "detached" -> entityManager.detach(course);
        course2.setName("Angular in 100 steps - Updated");
        entityManager.flush();


        //Another way of detaching "all" objs is to use "clear()" method
        entityManager.clear();   //"clear()" -> clears out everything from the EntityManager so nothing is tracked anymore by the entityManager
        course1.setName("Something 1");
        entityManager.flush();
        course2.setName("Something 2");
        entityManager.flush();
        //The above code is not saved on to the DB as the EntityManager is cleared -> clear out "everything"
    }

    //Experiment with EntityManager  - refresh()
    public void playWithEntityManager3() {
        Course course1 = new Course("Web Services in 100 steps");
        entityManager.persist(course1);

        Course course2 = new Course("Angular in 100 steps");
        entityManager.persist(course2);

        entityManager.flush();   // "flush()" method ensure that the changes up to this point are sent out to the database

        course1.setName("Web Services in 100 steps - updated");
        course2.setName("Angular in 100 steps - Updated");
        // entityManager.merge(course); // Don't really need to call this explicitly as the @transactional takes care of this by keeping track of the obj-orm mapping

        entityManager.refresh(course1); //Changes done to course1 obj are lost & course1.setName(..) has no effect at this point

        entityManager.flush();

    }

    public void addReviewsForCourseHardCoded() {
        //get the course 10003
        //add 2 reviews to it
        //save it to the database

        //get the course 10003
        Course course = findById(10003L);
        LOGGER.info("course.getReviews() {}", course.getReviews());

        //add 2 reviews to it
        Review review1 = new Review("5", "Great Hands-on stuff.");
        Review review2 = new Review("5", "Hatsoff.");

        //Setting the relationships
        course.addReview(review1);
        review1.setCourse(course);

        course.addReview(review2);
        review2.setCourse(course);

        //save ti the db
        entityManager.persist(review1);
        entityManager.persist(review2);
    }

    //A more generic approach to the above method
    public void addReviewsForCourse(Long courseId, List<Review> reviews) {
        //get the course 10003
        //add 2 reviews to it
        //save it to the database

        //get the course 10003
        Course course = findById(courseId);
        LOGGER.info("course.getReviews() {}", course.getReviews());

        //add 2 reviews to it
        for (Review review:reviews) {
            course.addReview(review);
            review.setCourse(course);
            entityManager.persist(review);
        }
    }
}
