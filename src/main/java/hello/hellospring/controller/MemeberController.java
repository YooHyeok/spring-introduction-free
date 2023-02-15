package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.domain.MemberForm;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemeberController {
    private MemberService memberService;
    @Autowired
    public MemeberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
//    public String create(@RequestParam(name = "name") String parameter) { //@RequestParam은 주로 변수명 변경, required 옵션변경시 사용한다.
    public String create(@ModelAttribute MemberForm form) {
        //@ModelAttribute 또한 name옵션을 통해 변수명 변경시 사용, thymeleaf에서 th:object 속성과 매칭시켜 변수명을 변경할때 사용한다.
        // JSP에서는 form TagLib을 사용하여 spring4 - commandName 혹은 spring5 - modelAttribute 속성에 지정해 줄 수 있다.
        Member member = new Member();
        member.setName(form.getName());
        System.out.println(member.getName());
        memberService.join(member);
        return "redirect:/";
    }
}
