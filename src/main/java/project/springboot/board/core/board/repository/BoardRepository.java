package project.springboot.board.core.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboot.board.core.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
