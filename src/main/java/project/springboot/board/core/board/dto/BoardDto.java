package project.springboot.board.core.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BoardDto {

    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdDate;
    private int count;
}
