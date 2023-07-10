package com.ssafy.jpastudy;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaTest {
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
}
