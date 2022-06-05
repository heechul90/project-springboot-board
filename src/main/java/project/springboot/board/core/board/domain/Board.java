package project.springboot.board.core.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.*;

@Entity
@SequenceGenerator(
        name = "board_seq_generator",
        sequenceName = "board_seq",
        initialValue = 1, allocationSize = 50
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
    @Column(name = "seq")
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @CreatedBy
    @Column(name= "writer", length = 20, nullable = false, updatable = false)
    private String writer;

    @CreatedDate
    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "cnt")
    private int count;

    @Builder(builderMethodName = "createBoardBuilder")
    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdDate = LocalDateTime.now();
    }

    @Builder(builderMethodName = "updateBoardBuilder")
    public void updateBoard(String title, String content) {
        if (hasText(title)) {
            this.title = title;
        }
        if (hasText(content)) {
            this.content = content;
        }
    }

    public void plusCount() {
        this.count += 1;
    }
}
