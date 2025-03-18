package bitcamp.myapp.service;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;

import java.util.List;

public interface BoardService {

    public List<Board> list() throws Exception;
    public Board get(int no) throws Exception;
    public void updateViewCount(int no) throws Exception;
    public void modify(Board board) throws Exception;
    public void remove(int no) throws Exception;
    public void write(Board board) throws Exception;
    void deletePhoto(int photoNo) throws Exception;
    int addPhoto(int boardNo, String filePath) throws Exception;
    BoardPhoto getBoardPhoto(int fileNo);

}