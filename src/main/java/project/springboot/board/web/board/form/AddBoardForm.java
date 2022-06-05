package project.springboot.board.web.board.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AddBoardForm {

    @NotEmpty(message = "제목은 필수값입니다.")
    @Length(max = 200)
    private String title;

    @NotEmpty(message = "내용은 필수값입니다.")
    @Length(max = 2000)
    private String content;

    @NotEmpty(message = "작성자는 필수값입니다.")
    @Length(max = 20)
    private String writer;

}
