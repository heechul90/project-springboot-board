package project.springboot.board.core.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.springboot.board.core.board.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BoardService boardService;

    private Board getBoard(String title, String content, String writer) {
        return Board.createBoardBuilder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

    @Test
    void findBoardsTest() {
        //given
        Board board1 = getBoard("title1", "content", "admin");
        Board board2 = getBoard("title2", "content", "admin");
        boardService.saveBoard(board1);
        boardService.saveBoard(board2);

        //when
        List<Board> resultList = boardService.findBoards();

        //then
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(resultList).extracting("title").containsExactly("title1", "title2");
    }

    @Test
    void findBoardTest() {
        //given
        Board board = getBoard("title", "content", "admin");
        boardService.saveBoard(board);

        //when
        Board findBoard = boardService.findBoard(board.getId());

        //then
        assertThat(findBoard.getTitle()).isEqualTo("title");
        assertThat(findBoard.getContent()).isEqualTo("content");
        assertThat(findBoard.getWriter()).isEqualTo("admin");
        assertThat(findBoard.getCount()).isEqualTo(1);
    }

    @Test
    void saveBoardTest() {
        //given
        Board board = getBoard("title", "content", "admin");

        //when
        Board savedBoard = boardService.saveBoard(board);

        //then
        Board findBoard = boardService.findBoard(savedBoard.getId());
        assertThat(findBoard.getTitle()).isEqualTo("title");
        assertThat(findBoard.getContent()).isEqualTo("content");
        assertThat(findBoard.getWriter()).isEqualTo("admin");
    }

    @Test
    void updateBoardTest() {
        //given
        Board board = getBoard("title", "content", "admin");
        boardService.saveBoard(board);
        em.flush();
        em.clear();

        //when
        boardService.updateBoard(board.getId(), "updateTitle", "updateContent");
        em.flush();
        em.clear();

        //then
        Board findBoard = boardService.findBoard(board.getId());
        assertThat(findBoard.getTitle()).isEqualTo("updateTitle");
        assertThat(findBoard.getContent()).isEqualTo("updateContent");
    }

    @Test
    void deleteBoardTest() {
        //given
        Board board = getBoard("title", "content", "admin");
        boardService.saveBoard(board);

        //when
        boardService.deleteBoard(board.getId());

        //then
        assertThatThrownBy(() -> boardService.findBoard(board.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("데이터가");
    }
}