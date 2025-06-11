package org.zerock.board.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Member extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;
}
