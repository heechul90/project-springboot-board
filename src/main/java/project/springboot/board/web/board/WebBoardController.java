package project.springboot.board.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.springboot.board.core.board.domain.Board;
import project.springboot.board.core.board.dto.BoardDto;
import project.springboot.board.core.board.service.BoardService;
import project.springboot.board.core.common.json.JsonResult;
import project.springboot.board.web.board.form.AddBoardForm;
import project.springboot.board.web.board.form.EditBoardForm;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/web/boards")
public class WebBoardController {

    private final BoardService boardService;

    /**
     * 게시판 목록
     */
    @GetMapping
    public String boards(Model model) {
        List<Board> boards = boardService.findBoards();
        List<BoardDto> resultList = boards.stream()
                .map(board -> {
                    return BoardDto.builder()
                            .boardId(board.getId())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .writer(board.getWriter())
                            .createdDate(board.getCreatedDate())
                            .count(board.getCount())
                            .build();
                })
                .collect(Collectors.toList());
        model.addAttribute("resultList", resultList);
        return "web/board/boardList";
    }

    /**
     * 게시판 등록 폼
     */
    @GetMapping(value = "/add")
    public String addBoardForm(Model model) {
        model.addAttribute("board", new AddBoardForm());
        return "web/board/addBoardForm";
    }

    /**
     * 게시판 등록
     */
    @PostMapping(value = "/add")
    public String addBoard(@Validated @ModelAttribute("board") AddBoardForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "web/board/addBoardForm";
        }

        log.info("title = {}", form.getTitle());
        log.info("content = {}", form.getContent());
        log.info("writer = {}", form.getWriter());

        Board board = Board.createBoardBuilder()
                .title(form.getTitle())
                .content(form.getContent())
                .writer(form.getWriter())
                .build();
        boardService.saveBoard(board);

        return "redirect:/web/boards";
    }

    /**
     * 게시판 상세
     */
    @GetMapping(value = "{boardId}")
    public String boardDetail(@PathVariable("boardId") Long id, Model model) {
        Board findBoard = boardService.findBoard(id);
        BoardDto board = BoardDto.builder()
                .boardId(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .writer(findBoard.getWriter())
                .createdDate(findBoard.getCreatedDate())
                .count(findBoard.getCount())
                .build();

        model.addAttribute("board", board);
        return "web/board/boardDetail";
    }

    /**
     * 게시판 수정 폼
     */
    @GetMapping(value = "{boardId}/edit")
    public String editBoardForm(@PathVariable("boardId") Long id, Model model) {
        Board findBoard = boardService.findBoard(id);
        BoardDto board = BoardDto.builder()
                .boardId(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .writer(findBoard.getWriter())
                .createdDate(findBoard.getCreatedDate())
                .count(findBoard.getCount())
                .build();

        model.addAttribute("board", board);
        return "web/board/editBoardForm";
    }

    /**
     * 게시판 수정
     */
    @PostMapping(value = "/{boardId}/edit")
    public String editBoard(@PathVariable("boardId") Long boardId,
                            @Validated @ModelAttribute("board") EditBoardForm form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "web/board/editBoardForm";
        }

        boardService.updateBoard(boardId, form.getTitle(), form.getContent());

        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/web/boards/{boardId}";
    }

    /**
     * 게시판 삭제
     */
    @PostMapping(value = "{boardId}/delete")
    @ResponseBody
    public JsonResult deleteBoard(@PathVariable("boardId") Long id) {
        boardService.deleteBoard(id);
        return new JsonResult(HttpStatus.OK);
    }

}
