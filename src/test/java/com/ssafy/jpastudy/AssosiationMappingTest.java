package com.ssafy.jpastudy;

import com.ssafy.jpastudy.db.entity.Dept;
import com.ssafy.jpastudy.db.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AssosiationMappingTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void testContextLoads(){

        // given - when - then
        System.out.println("dataSource = " + dataSource);
        System.out.println("entityManagerFactory = " + entityManagerFactory);

        assertNotNull(dataSource);
        assertNotNull(entityManagerFactory);

    }

    @Test
    void testAssosiationMapping(){
        // given
        Dept dept = createDept();
        Employee employee = createEmployee(dept);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();
        entityManager.persist(dept);
        entityManager.persist(employee);
        entityTransaction.commit();

        // then
        Employee selectedEmployee = entityManager.find(Employee.class,employee.getId());
        System.out.println("selectedEmployee = " + selectedEmployee);
        assertEquals(employee.getId(),selectedEmployee.getId());
    }

    @Test
    void testPersistenceContextCascade(){
        // given
        Dept dept = createDept();
        Employee employee = createEmployee(dept);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when

        entityTransaction.begin();

        /*
            dept 는 Employee의 Cascade.PERSIST에 의해 persist 됨
         */

        entityManager.persist(employee);
        entityTransaction.commit();

        // then
        assertDoesNotThrow(()-> {
            Employee selectedEmployee = entityManager.find(Employee.class,employee.getId());
            System.out.println("selectedEmployee = " + selectedEmployee);
            assertEquals(employee.getId(),selectedEmployee.getId());
        });

        entityTransaction.begin();

        entityManager.remove(dept);

        /*
            entityManager.remove(dept);
            db의 employee table 외래키가 null이 될 수 없으므로 query를 날리지 않음
            Dept의 CascadeType.REMOVE를 추가하면 동작함
          */
        entityTransaction.commit();
        assertNotNull(entityManager.find(Dept.class,dept.getId()));
    }


    private Dept createDept(){
        return Dept.builder().build();
    }

    private Employee createEmployee(Dept dept){
        return Employee.builder().dept(dept).build();
    }
}
