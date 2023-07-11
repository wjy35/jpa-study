package com.ssafy.jpastudy.db.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {

    @Id
    @Column(name = "book_id")
    int bookId;

    String title;
    String content;
}
