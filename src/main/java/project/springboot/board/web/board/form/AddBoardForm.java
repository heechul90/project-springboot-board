package project.springboot.board.web.board.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBoardForm {

    private String title;
    private String content;
    private String writer;

}
