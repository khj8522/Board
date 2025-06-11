package org.zerock.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> { // 클래스
    private List<DTO> dtoList;
    // DTO로 변환한 엔티티 리스트

    private int totalPage;

    private int page;

    private int size;

    private int start,end;

    private boolean prev,next;

    private List<Integer> pageList; // 페이지 번호

    // 위의 필드들을 반환

    // 생성자
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        // 페이징 된 엔티티 , 엔티티 -> DTO 함수
        dtoList = result.stream().map(fn).collect(Collectors.toList()); // 페이징 된 dto 반환

        totalPage = result.getTotalPages(); // 총 페이지

        makePageList(result.getPageable()); // 페이지 번호 리스트
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0부터 시작
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10; // ceil == 일의 자리 올림

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage; // 마지막 페이지 번호

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
        // 표시할 페이지 번호 개수
    }
}
