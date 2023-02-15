package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemeberController {

    private final MemberService memberService;
    @Autowired
    public MemeberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
