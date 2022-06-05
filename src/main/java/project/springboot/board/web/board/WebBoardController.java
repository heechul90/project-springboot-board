package project.springboot.board.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.springboot.board.core.board.domain.Board;
import project.springboot.board.core.board.dto.BoardDto;
import project.springboot.board.core.board.service.BoardService;
import project.springboot.board.web.board.form.AddBoardForm;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/web/boards")
public class WebBoardController {

    private final BoardService boardService;

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

    @GetMapping(value = "/add")
    public String addBoardForm(Model model) {
        model.addAttribute("board", new AddBoardForm());
        return "web/board/addBoardForm";
    }

    @PostMapping(value = "/add")
    public String addBoard(@Validated @ModelAttribute("board") AddBoardForm form, BindingResult bindingResult) {
        return "redirect:/web/boards";
    }

}
