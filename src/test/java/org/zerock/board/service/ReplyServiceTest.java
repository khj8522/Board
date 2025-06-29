package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    private ReplyService service;

    @Test
    public void testGetList() {
        Long bno = 100L;

        List<ReplyDTO> replyDTOList = service.getList(bno); // 리파지토리를 통해 엔티티를 가져와 DTO 변환

        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }
}
