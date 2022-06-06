package project.springboot.board.web.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WebIndexController {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "redirect:/web/boards";
    }
}
