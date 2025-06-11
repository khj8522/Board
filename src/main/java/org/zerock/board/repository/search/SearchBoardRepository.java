package org.zerock.board.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.board.entity.Board;

public interface SearchBoardRepository { // CRUD는 JPARepository가 하지만
    // 그 외에는 커스텀으로 만들어 구현
    Board search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
