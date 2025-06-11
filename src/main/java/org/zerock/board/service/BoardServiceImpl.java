package org.zerock.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.BoardRepository;
import org.zerock.board.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[],BoardDTO> fn = (en -> entityToDto((Board)en[0],(Member)en[1],
                (Long)en[2]));
        // 엔티티의 object 배열마다 다른 타입이 들어가 있기에 함수로 dto의 배열마다 다른 타입으로 설정

//        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO
//                .getPageable(Sort.by("bno").descending()));
        // 페이징 된 엔티티

        Page<Object[]> result = repository.searchPage(pageRequestDTO.getType()
        , pageRequestDTO.getKeyword(),pageRequestDTO.getPageable(Sort.by("bno").descending()));
        // 목록 표시

        return new PageResultDTO(result,fn); // 생성자를 통해 클래스의 필드를 반환함(클래스 타입 같음)
    }

    @Override
    public BoardDTO get(Long bno) { // 조회
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[])result;

        return entityToDto((Board)arr[0],(Member)arr[1],(Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) { // 삭제
        replyRepository.deleteByBno(bno);

        repository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getReferenceById(boardDTO.getBno());
        // 게시글 수정 시, 게시글 내용을 전부 가져올 필요가 없을 시 Reference 사용
        // 프록시(가짜 객체)를 만들어 반환

        if(board != null) {
            board.changeTitle(boardDTO.getTitle());
            board.changeContent(boardDTO.getContent());
        }

        repository.save(board);
    }


}
