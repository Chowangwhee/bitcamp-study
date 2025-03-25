package bitcamp.myapp.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/home")
  public void home() {
    // RequestHandler 에서 응답에 필요한 것을 요구하지도 않고(즉, HttpServletResponse 객체를 파라미터로 요구하지 않을 때)
    // view name(JSP 파일 경로)도 리턴하지 않는다면
    // 프론트 컨트롤러는 RequestHandler 를 실행할 때 사용한 요청 URL 을 view name 으로 사용한다
  }
}