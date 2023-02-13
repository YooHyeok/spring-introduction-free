package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","spring!");
        return "hello";
    }

    @GetMapping("hello-static.html")
    public String helloStatic(Model model) {
        model.addAttribute("data","spring!");
        return "hello-static";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(name = "name", required = true, defaultValue = "메롱") String name, Model model) {
        // [required] 파라미터 요구사항 - true : 파라미터 필수조건 / false : 파라미터 선택조건
        // [defaultValue] 파라미터의 값의 기본값을 설정합니다. (required true일경우 오류방지와 false일경우 null방지)
        model.addAttribute("name",name);
        return "hello-template";
    }
}
