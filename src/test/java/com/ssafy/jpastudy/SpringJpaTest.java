package com.ssafy.jpastudy;

import com.ssafy.jpastudy.db.entity.Board;
import com.ssafy.jpastudy.db.entity.BoardFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpringJpaTest {
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
    @Transactional
    void testInsert(){
        // given
        int boardId = 1;
        Board insertedBoard = BoardFactory.createByBoardId(boardId);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // when
        entityManager.persist(insertedBoard);
        Board selectedBoard = entityManager.find(Board.class,1);

        // then
        System.out.println("insertedBoard = " + insertedBoard);
        System.out.println("selectedBoard = " + selectedBoard);
        assertEquals(insertedBoard,selectedBoard);
    }

    @Test
    @Transactional
    void testInsertFail(){
        // given
        int boardId = 1;
        Board insertedBoard = BoardFactory.createByBoardId(boardId);
        Board updatedBoard = BoardFactory.createByBoardIdAndSubject(boardId,"updated");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // when
        entityManager.persist(updatedBoard);

        // then
        assertThrows(EntityExistsException.class,()->{
            entityManager.persist(insertedBoard);
        });
    }

    @Test
    void testUpdate(){
        // given
        int boardId = 1;
        String updateSubject = "updated ssafy";

        Board insertedBoard = BoardFactory.createByBoardId(boardId);
        Board updatedBoard = BoardFactory.createByBoardIdAndSubject(boardId,updateSubject);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();

        entityManager.persist(insertedBoard);
        entityManager.merge(updatedBoard);
        entityManager.flush();
        entityManager.clear();
        entityTransaction.commit();

        // then
        Board selectedBoard = entityManager.find(Board.class,1);
        System.out.println("selectedBoard = " + selectedBoard);

        assertEquals(updatedBoard.getSubject(),selectedBoard.getSubject());
    }

    @Test
    void testDetach(){
        // given
        int boardId = 1;
        Board insertedBoard = BoardFactory.createByBoardId(boardId);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();
        entityManager.persist(insertedBoard);
        entityManager.detach(insertedBoard);
        entityTransaction.commit();

        Board selectedBoard = entityManager.find(Board.class,boardId);

        // then
        System.out.println("selectedBoard = " + selectedBoard);
        assertNull(selectedBoard);
    }

    @Test
    void testMergeDetachedEntityException(){
        // given
        int boardId = 1;
        Board insertedBoard = BoardFactory.createByBoardId(boardId);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();
        entityManager.persist(insertedBoard);
        entityManager.detach(insertedBoard);
        entityManager.merge(insertedBoard);


        // then
        assertThrows(Exception.class,()->{
            entityTransaction.commit();
        }).printStackTrace();
    }

    @Test
    void testMergeDetachedEntity(){
        int boardId = 1;
        Board insertedBoard = BoardFactory.createByBoardId(boardId);

        assertDoesNotThrow(()->{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            entityManager.persist(insertedBoard);
            entityManager.flush();
            entityManager.detach(insertedBoard);

            entityManager.merge(insertedBoard);
            entityTransaction.commit();
        });

        assertDoesNotThrow(()->{
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();
            entityManager.persist(insertedBoard);
            entityManager.clear();

            entityManager.merge(insertedBoard);
            entityTransaction.commit();
        });
    }

    @Test
    void testtest(){
        int boardId = 1;
        Board insertedBoard = BoardFactory.createByBoardId(boardId);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        // when
        entityTransaction.begin();
        entityManager.persist(insertedBoard);
        entityTransaction.commit();

        entityTransaction.begin();
        entityManager.remove(insertedBoard);
        entityManager.detach(insertedBoard);
        entityTransaction.commit();

    }
}
