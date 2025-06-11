package org.zerock.board.dto;

import jdk.jfr.Name;
import lombok.*;

import java.time.LocalDateTime;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; // 작성자 이메일(id)

    private String writerName; // 작성자 이름

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount; // 댓글 개수
}
