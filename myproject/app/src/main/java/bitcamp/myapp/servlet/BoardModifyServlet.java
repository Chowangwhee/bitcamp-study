package bitcamp.myapp.servlet;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/board/modify")
public class BoardModifyServlet extends HttpServlet {
    private String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
            int no = Integer.parseInt(request.getParameter("no"));
            Board board = boardService.get(no);
            request.setAttribute("board", board);
            request.getRequestDispatcher("/board/modifyForm.jsp").include(request, response);
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            javax.servlet.RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("exception", stringWriter.toString());   // jsp 파일로 오류 전달
            requestDispatcher.forward(request, response);   // 관련 요청, 응답 데이터 소멸처리
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 파일 업로드를 위한 설정
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            // 요청 파싱
            List<FileItem> items = upload.parseRequest(request);

            // 폼 데이터 처리
            Board board = new Board();
            List<BoardPhoto> boardPhotos = new ArrayList<>();
            for (FileItem item : items) {
                if (item.isFormField()) { // 일반 폼 데이터인 경우
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");
                    if (fieldName.equals("no")) {
                        board.setNo(Integer.parseInt(fieldValue));
                    } else if (fieldName.equals("title")) {
                        board.setTitle(fieldValue);
                    } else if (fieldName.equals("content")) {
                        board.setContent(fieldValue);
                    } else if (fieldName.startsWith("deletePhotoNo")) {
                        BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");
                        boardService.deletePhoto(Integer.parseInt(fieldValue));
                    }
                }
            }
            // 파일 업로드 처리
            for (FileItem item : items) {
                if (!item.isFormField()) { // 파일 데이터인 경우
                    String fileName = item.getName();
                    if (fileName != null && !fileName.isEmpty()) {
                        String uuidFilename = UUID.randomUUID().toString() + "_" + fileName; // Changed to filename
                        File uploadedFile = new File(uploadDir + uuidFilename); // Changed to filename

                        File dir = new File(uploadDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        item.write(uploadedFile);
                        BoardPhoto boardPhoto = new BoardPhoto();
                        boardPhoto.setFilename(uuidFilename); // Changed to setFilename
                        boardPhotos.add(boardPhoto);
                    }
                }
            }

            board.setPhotos(boardPhotos);

            BoardService boardService = (BoardService) getServletContext().getAttribute("boardService");

            if (board.getPhotos().size() > 0) {
                for (BoardPhoto boardPhoto : board.getPhotos()) {
                    boardService.addPhoto(board.getNo(), boardPhoto.getFilename()); // Changed to getFilename
                }
            }
            boardService.modify(board);

            response.sendRedirect("/board/view?no=" + board.getNo());
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);

            javax.servlet.RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("exception", stringWriter.toString());   // jsp 파일로 오류 전달
        }
    }
}