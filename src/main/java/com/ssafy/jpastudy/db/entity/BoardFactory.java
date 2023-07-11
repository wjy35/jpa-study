package com.ssafy.jpastudy.db.entity;

public class BoardFactory {
    public static Board createByBoardId(int boardId){
        return Board.builder()
                .boardId(boardId)
                .userId("ssafy")
                .subject("hello")
                .content("jpa")
                .build();
    }

    public static Board createByBoardIdAndSubject(int boardId,String subject){
        return Board.builder()
                .boardId(boardId)
                .userId("ssafy")
                .subject(subject)
                .content("jpa")
                .build();
    }
}
