package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","spring!");
        return "hello";
    }


    @GetMapping("hello-static.html")// localhost/hello-static.html 는 기본적으로 static하위에 존재하는 정적 컨텐츠가 열리게된다
    // 하지만 매핑주소로 사용할 경우 templates에 존재하는 파일이 열린다.
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

    @ResponseBody // StringHttpMessageConverter 작동 : 기본 문자 처리
    @GetMapping("hello-string")
    public String helloString(@RequestParam("name") String name) {
        return "hello "+name; //parameter가 Spring 이라면 hello Spring 이라는 문자로 변경된다.
        // API는 View가 없다. 단순 값 반환....
    }

    @ResponseBody // MappingJackson2HttpMessageConverter 작동 : 기본 객체 처리 (jackson = Spring의 기본 채택)
    @GetMapping("hello-api")
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        // {"name": "Spring"} 과 같이 JSON 형태로 반환한다.
        // 앞의 코드와의 차이는 그냥 String을 반환한것이 아닌 Hello라는 Class의 객체를 통해 반환했다.
        // Hello클래스에는 name변수와 Setter/Getter가 존재한다.
        // setter를통해 name변수의 값을 초기화 한 뒤 그 객체를 통으로 넘기면 변수명 name이 Key가 되고 저장된 값이 Value가 된다.
        // 다시말해 {"Key" : "Value"} 형태로 값을 반환하게된다.
    }
    static class Hello {
        // 내부 클래스에서 외부 클래스의 인스턴스를 쓰지 않는다면, 내부클래스는 static으로 만든다.
        // Hello Class는 HomeController Class의 그 어떤 멤버도 사용하지 않는다. <-> 서로 독립적
        // non-static으로 정의하면 메모리 누수가 발생한다.
        // non-static은 외부클래스 즉 HomeController의 인스턴스와 생성자 정보까지 참조하게된다.
        // https://tecoble.techcourse.co.kr/post/2020-11-05-nested-class/
        // https://www.inflearn.com/questions/257297/testconfig-%ED%81%B4%EB%9E%98%EC%8A%A4%EC%97%90%EC%84%9C-static%EC%9D%84-%EB%96%BC%EB%B2%84%EB%A6%AC%EB%A9%B4
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }
}
