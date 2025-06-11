package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> , SearchBoardRepository {
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Board b left join Reply r On r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    // board하나에 댓글 조회 == 다수의 행 == Object[] --> 조인된 대상의 수가 행 수를 결정

    @Query(value = "Select b, w, count(r)" +
            " from Board b " +
            " left join b.writer w" +
            " left join Reply r on r.board = b " +
            " group by b",
            countQuery = "select count(b) from Board b") // 페이징을 위한 전체 글 수
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b, w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" + // outer join --> 댓글이 없어도 board 반환
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
    // 보드 하나에 댓글의 갯수 == 한 행 == Object --> 집계함수 있을시 대부분 한 행

}
