package com.ssafy.jpastudy.db.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Dept{

    @Id
    @Column(name = "dept_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "dept",cascade = {CascadeType.REMOVE})
    private List<Employee> employees = new ArrayList<>();
}
