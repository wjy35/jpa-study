package com.ssafy.jpastudy.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Board {
    @Id
    private int boardId;

    private String userId;
    private String subject;
    private String content;
}
