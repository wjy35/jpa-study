package com.ssafy.jpastudy;

import com.ssafy.jpastudy.db.entity.Book;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateTest {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello_jpa");

    @Test
    void testContextLoad(){
        assertNotNull(entityManagerFactory);
    }

    @Test
    void testInsert(){
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Book insertedBook = Book.builder().title("hello").content("jpa").build();

        // when
        entityTransaction.begin();
        entityManager.persist(insertedBook);
        entityTransaction.commit();

        /*
            entityManager.clear();
            추가에 따라서 db에 select query 여부가 결정됨
         */

        Book selectedBook = entityManager.find(Book.class,insertedBook.getBookId());

        // then
        System.out.println("insertedBook = " + insertedBook);
        System.out.println("selectedBook = " + selectedBook);

        assertEquals(insertedBook.getBookId(),selectedBook.getBookId());

    }

    @Test
    void testEntityManager(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        int bookId = 0;
        Book insertedBook = Book.builder().bookId(bookId).title("hello").content("jpa").build();

        entityTransaction.begin();
        entityManager.persist(insertedBook);
        entityTransaction.commit();
        boolean containsResult = entityManager.contains(insertedBook);

        System.out.println("containsResult = " + containsResult);
        assertEquals(containsResult,true);

    }

    @Test
    void testInsertAutoIncrement(){
        // given
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Book book1 = Book.builder().title("hello").content("jpa").build();
        Book book2 = Book.builder().title("hello").content("jpa").build();
        assertEquals(book1.getBookId(),book2.getBookId());

        // when
        entityTransaction.begin();
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityTransaction.commit();

        Book selectedBook1 = entityManager.find(Book.class,book1.getBookId());
        Book selectedBook2 = entityManager.find(Book.class,book2.getBookId());

        // then

        System.out.println("selectedBook1 = " + selectedBook1);
        System.out.println("selectedBook2 = " + selectedBook2);
        assertNotEquals(selectedBook1.getBookId(),selectedBook2.getBookId());
    }
}
