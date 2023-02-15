package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        // 매핑을 따로 등록하지않으면 static하위의 index.html 파일을 출력하는데, 그럼 뭣하러 만드냐 거기다가 메인설정하면 되지않냐 라고 단순하게 생각하면 안된다.
        // 메인 페이지에 데이터를 뿌려야할 상황이 올 수도 있는데, (대부분이 데이터를 출력함...) 그렇게 만들수는 있겠지만 정말 데이터를 뿌려주지못하고 단순히 정적인 화면 그이상 이하도 아니다.
        return "home";
    }
}
