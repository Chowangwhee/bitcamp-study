package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.service.StorageService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {
    private BoardService boardService;
    private StorageService storageService;

    @Override
    public void init() {
        boardService = (BoardService) getServletContext().getAttribute("boardService");
        storageService = (StorageService) getServletContext().getAttribute("storageService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int boardNo = Integer.parseInt(request.getParameter("no"));

        try {
            Board board = boardService.get(boardNo);
            if (board == null) {
                throw new Exception("삭제하려는 게시글이 존재하지 않습니다.");
            }
            List<BoardPhoto> photos = board.getPhotos();
            if (!photos.isEmpty()) {
                for (BoardPhoto photo : photos) {
                    storageService.delete(photo.getFilename());
                }
            }
            boardService.remove(boardNo);
            response.sendRedirect("/board/list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            request.setAttribute("message", "게시글 삭제 오류!");
            request.setAttribute("refresh", "2;url=/board/list");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}