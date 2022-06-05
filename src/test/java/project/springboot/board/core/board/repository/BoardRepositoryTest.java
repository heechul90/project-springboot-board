package project.springboot.board.core.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.springboot.board.core.board.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BoardRepository boardRepository;

    private Board getBoard(String title, String content, String writer) {
        return Board.createBoardBuilder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

    @Test
    public void saveTest() throws Exception{
        //given
        Board board = getBoard("title", "content", "spring");

        //when
        Board savedBoard = boardRepository.save(board);

        //then
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        assertThat(findBoard.getTitle()).isEqualTo("title");
        assertThat(findBoard.getContent()).isEqualTo("content");
        assertThat(findBoard.getWriter()).isEqualTo("spring");
    }

    @Test
    @Rollback(value = false)
    public void findAllTest() throws Exception{
        //given
        Board board1 = getBoard("title1", "content", "spring");
        Board board2 = getBoard("title2", "content", "spring");
        Board board3 = getBoard("title3", "content", "spring");
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);

        //when
        List<Board> resultList = boardRepository.findAll();

        //then
        assertThat(resultList.size()).isEqualTo(3);
        assertThat(resultList).extracting("title").containsExactly("title1", "title2", "title3");
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Board board = getBoard("title", "content", "writer");
        Board savedBoard = boardRepository.save(board);
        em.flush();
        em.clear();

        //when
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        String updateTitleParam = "updateTitle";
        String updateContentParam = "updateContent";
        findBoard.updateBoardBuilder()
                .title(updateTitleParam)
                .content(updateContentParam)
                .build();
        em.flush();
        em.clear();

        //then
        Board updatedBoard = boardRepository.findById(findBoard.getId()).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        assertThat(updatedBoard.getTitle()).isEqualTo(updateTitleParam);
        assertThat(updatedBoard.getContent()).isEqualTo(updateContentParam);
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Board board = getBoard("title", "content", "writer");
        Board savedBoard = boardRepository.save(board);
        em.flush();
        em.clear();

        //when
        Long deleteId = savedBoard.getId();
        Board findBoard = boardRepository.findById(deleteId).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        boardRepository.delete(findBoard);
        em.flush();
        em.clear();

        //then
        assertThatThrownBy(() -> boardRepository.findById(deleteId).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다.")))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("데이터가 존재하지");
    }
}