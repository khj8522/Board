package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString(exclude = "board")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;


    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


}
