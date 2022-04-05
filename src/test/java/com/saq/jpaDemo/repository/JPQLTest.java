package com.saq.jpaDemo.repository;

import com.saq.jpaDemo.JpaDemoApplication;
import com.saq.jpaDemo.entity.Course;
import com.saq.jpaDemo.respository.CourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class JPQLTest {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    @Test
    public void jpql_basic() {
        LOGGER.info("Testing is running");
//        List<Course> result = entityManager.createQuery("Select c From Course c", Course.class).getResultList(); //JPQL query
        //OR
        TypedQuery<Course> query = entityManager.createQuery("Select c From Course c", Course.class);
        List<Course> result = query.getResultList();
        LOGGER.info("Select c From Course c -> {}", result);
    }

    @Test
    public void jpql_where_condition() {
        TypedQuery<Course> query =
                entityManager.createQuery("Select c From Course c where name like '%100 steps'", Course.class);
        List<Course> result = query.getResultList();
        LOGGER.info("Select c From Course c where name like %100 steps-> {}", result);
        //Select c From Course c where name like %100 steps-> [Course{id=1, name='Microservice in 100 steps'}, Course{id=10003, name='Spring Boot in 100 steps'}]
    }

    //Using @NamedQuery -> create a named query in the Entity & use that name as reference to use the query
    @Test
    public void jpql_basic_namedQuery() {
        LOGGER.info("Testing is running");
//        List<Course> result = entityManager.createQuery("Select c From Course c", Course.class).getResultList(); //JPQL query
        //OR
        TypedQuery<Course> query = entityManager.createNamedQuery("query_get_all_courses", Course.class);
        List<Course> result = query.getResultList();
        LOGGER.info("Using NamedQuery -> {}", result);
    }

    @Test
    public void jpql_where_namedQuery() {
        TypedQuery<Course> query =
                entityManager.createNamedQuery("query_courses_where", Course.class);
        List<Course> result = query.getResultList();
        LOGGER.info("Select c From Course c where name like %100 steps-> {}", result);
        //Select c From Course c where name like %100 steps-> [Course{id=1, name='Microservice in 100 steps'}, Course{id=10003, name='Spring Boot in 100 steps'}]
    }

}
