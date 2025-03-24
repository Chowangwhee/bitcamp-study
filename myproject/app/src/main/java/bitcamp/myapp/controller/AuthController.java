package bitcamp.myapp.controller;

import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

  private MemberService memberService;

  public AuthController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("login-form")
  public String form() {
    return "/auth/login-form";
  }

  @PostMapping("login")
  public String login(String email,
                      String password,
                      String saveEmail,
                      HttpSession session, HttpServletResponse resp) throws Exception {

    Member member = memberService.get(email, password);
    if (member == null) {
      return "redirect:/auth/login-form";
    }

    if (saveEmail != null) {
      Cookie emailCookie = new Cookie("email", email);
      emailCookie.setMaxAge(60 * 60 * 24 * 7);
      resp.addCookie(emailCookie);
    } else {
      Cookie emailCookie = new Cookie("email", "");
      emailCookie.setMaxAge(0);
      resp.addCookie(emailCookie);
    }

    session.setAttribute("loginUser", member);
    return "redirect:/home";
  }

  @RequestMapping("logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/home";
  }
}
