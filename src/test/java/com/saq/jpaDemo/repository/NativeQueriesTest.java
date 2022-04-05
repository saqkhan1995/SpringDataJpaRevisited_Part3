package com.saq.jpaDemo.repository;

import com.saq.jpaDemo.JpaDemoApplication;
import com.saq.jpaDemo.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JpaDemoApplication.class)
public class NativeQueriesTest {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    @Test
    public void nativeQuery_basic() {
        LOGGER.info("Testing is running");
//        List<Course> result = entityManager.createQuery("Select c From Course c", Course.class).getResultList(); //JPQL query
        //OR
        Query query = entityManager.createNativeQuery("Select * From Course", Course.class);
        List<Course> result = query.getResultList();
        LOGGER.info("Select * From Course -> {}", result);
    }

    @Test
    public void nativeQuery_whereCondition_usingPlaceHoldersParameters() {
        LOGGER.info("Testing is running");
//        List<Course> result = entityManager.createQuery("Select c From Course c", Course.class).getResultList(); //JPQL query
        //OR
        Query query = entityManager.createNativeQuery("Select * From Course where id =?", Course.class);
        query.setParameter(1, 10001L);   //1 -> position of the placeholder(?), 10001L is the value
        List<Course> result = query.getResultList();
        LOGGER.info("Select * From Course -> {}", result);
    }

    @Test
    public void nativeQuery_whereCondition_usingNamedParameters() {
        LOGGER.info("Testing is running");
//        List<Course> result = entityManager.createQuery("Select c From Course c", Course.class).getResultList(); //JPQL query
        //OR
        Query query = entityManager.createNativeQuery("Select * From Course where id = :id", Course.class);
        query.setParameter("id", 10001L);   //1 -> position of the placeholder(?), 10001L is the value
        List<Course> result = query.getResultList();
        LOGGER.info("Select * From Course -> {}", result);
    }

    //Sometimes it's crucial to use DB specific features that are not available via JPA, hence in those cases we use Native quesries ex.Mass update
    @Test
    @Transactional
    public void nativeQuery_for_MassUpdate() {
        LOGGER.info("Testing is running");
//        List<Course> result = entityManager.createQuery("Select c From Course c", Course.class).getResultList(); //JPQL query
        //OR
        Query query = entityManager.createNativeQuery("Update COURSE set last_updated_date=sysdate()", Course.class);
        int noOfRowsUpdated = query.executeUpdate();   //returns no of rows updated

        LOGGER.info("No of Rows Updated -> {}", noOfRowsUpdated); //5 in my case
    }

}
