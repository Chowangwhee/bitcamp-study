package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/board/view")
public class BoardViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
            int no = Integer.parseInt(request.getParameter("no"));
            boardService.updateViewCount(no);
            Board board = boardService.get(no);

            request.setAttribute("board", board);

            response.setContentType("text/html; charset=UTF-8");
            request.getRequestDispatcher("/board/content.jsp").include(request, response);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            javax.servlet.RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("exception", stringWriter.toString());   // jsp 파일로 오류 전달
            requestDispatcher.forward(request, response);   // 관련 요청, 응답 데이터 소멸처리
        }
    }

}
