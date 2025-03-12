package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
            List<Board> list = boardService.list();

            request.setAttribute("list", list);

            response.setContentType("text/html; charset=UTF-8");
            request.getRequestDispatcher("/board/list.jsp").include(request, response);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("exception", stringWriter.toString());   // jsp 파일로 오류 전달
            requestDispatcher.forward(request, response);   // 관련 요청, 응답 데이터 소멸처리
        }
    }

}
