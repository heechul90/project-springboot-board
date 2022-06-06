package project.springboot.board.core.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.springboot.board.core.board.domain.Board;
import project.springboot.board.core.board.repository.BoardRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * Board 목록 조회
     */
    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    /**
     * Board 단건 조회
     */
    @Transactional
    public Board findBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        //조회수증가
        findBoard.plusCount();
        return findBoard;
    }

    /**
     * Board 저장
     */
    @Transactional
    public Board saveBoard(Board board) {
        Board savedBoard = boardRepository.save(board);
        return savedBoard;
    }

    /**
     * Board 수정
     */
    @Transactional
    public void updateBoard(Long id, String title, String content) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        findBoard.updateBoardBuilder()
                .title(title)
                .content(content)
                .build();
    }

    /**
     * Board 삭제
     */
    @Transactional
    public void deleteBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException("데이터가 존재하지 않습니다."));
        boardRepository.delete(findBoard);
    }

}
