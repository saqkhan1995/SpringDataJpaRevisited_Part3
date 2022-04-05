package com.saq.jpaDemo.respository;

import com.saq.jpaDemo.entity.Course;
import com.saq.jpaDemo.entity.Passport;
import com.saq.jpaDemo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
@Transactional            //To enable Transaction Management for any DB related changes , example: deleteByID(id), if something fails the changes are rolled back
public class StudentRepository {

    @Autowired
    EntityManager entityManager;

    // 1) public Student findById(String id);
    // 2) public Student save(Student student);   ->insert or update
    // 3) public void deleteById(String id);

    // 1) public Student findById(String id);
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    // 3) public void deleteById(String id);
    public void deleteById(Long id) {
        //get the student
        Student student = findById(id);

        //delete operation should be done in something known as "transaction management" -> if something fails the changes are rolled back
        //To enable this -> use "@Transactional" Annotation on class level
        entityManager.remove(student);
    }

    // 2) public Student save(Student student);   ->insert or update
    public Student save(Student student) {
        if (Objects.isNull(student.getId())) {
            //insert
            entityManager.persist(student);
        } else {
            //update
            entityManager.merge(student);
        }
        return student;
    }

    //Experiment with EntityManager
    public void saveStudentWithPassport() {

        Passport passport = new Passport("Z123456");
        entityManager.persist(passport);              //passport should first be persisted before using it in student relationship (i.e. student.setPassport(passport))

        Student student = new Student("Mike");
        student.setPassport(passport);
        entityManager.persist(student);

        /*Student student2 = findById(10001L);
        student2.setName("JPA in 50 steps - Updated");*/
        // entityManager.merge(student2); // Don't really need to call this explicitly as the @transactional takes care of this by keeping track of the obj-orm mapping

    }

    public void insertStudentAndCourseHardCoded() {
        Student student = new Student("Jack");
        Course course = new Course("Microservices in 100 Steps");

        //persist individual entities
        entityManager.persist(student);
        entityManager.persist(course);

        //persisting the relationship
        student.addCourses(course);
        course.addStudents(student);
        entityManager.persist(student);
    }

    public void insertStudentAndCourse(Student student, Course course) {

        //persisting the relationship
        student.addCourses(course);
        course.addStudents(student);
        entityManager.persist(student);
        entityManager.persist(course);
    }

}
