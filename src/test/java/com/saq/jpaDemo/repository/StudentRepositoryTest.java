package com.saq.jpaDemo.repository;

import com.saq.jpaDemo.JpaDemoApplication;
import com.saq.jpaDemo.entity.Passport;
import com.saq.jpaDemo.entity.Student;
import com.saq.jpaDemo.respository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class StudentRepositoryTest {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentRepository repository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional           //Persistence Context to keep track of DB changes
    public void someTest() {
        //Database Operation 1 - Retrieve student
        Student student = em.find(Student.class, 20000L);
        //Persistence Context -> student

        //Database Operation 2 - Retrieve passport
        Passport passport = student.getPassport();
        //Persistence Context -> student, passport

        //Database Operation 3 - update passport
        passport.setNumber("E123457");
        //Persistence Context -> student, passport++

        //Database Operation 4 - update student
        student.setName("Adam - updated");
        //Persistence Context -> student++, passport++
    }

    @Test
    @Transactional
    public void retrieveStudentAndPassportDetails() {
        Student student = em.find(Student.class, 20001L);
        LOGGER.info("student -> {}", student.toString());
        //student -> Student{id=20001, name='Oscar', passport=Passport{id=40001, number='E123456'}}
        LOGGER.info("Passport Details -> {}", student.getPassport());
        //Passport Details -> Passport{id=40001, number='E123456'}
    }

    @Test
    @Transactional
    public void retrievePassportAndStudentDetails() {
        Passport passport = em.find(Passport.class, 40001L);
        LOGGER.info("Passport2 -> {}", passport.toString());
        //Passport Details -> Passport{id=40001, number='E123456'}
        LOGGER.info("student2 Details -> {}", passport.getStudent());
        //student -> Student{id=20001, name='Oscar', passport=Passport{id=40001, number='E123456'}}
    }

    @Test
    @Transactional
    public void retrieveStudentAndCourses() {
        Student student = em.find(Student.class, 20001L);
        LOGGER.info("student -> {}", student.toString());
        LOGGER.info("courses -> {}", student.getCourses());
    }
}
