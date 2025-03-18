package bitcamp.myapp.dao;

import bitcamp.myapp.vo.BoardPhoto;
import java.util.List;

public interface BoardFileDao {
    void insert(int boardId, List<BoardPhoto> photos);
    void delete(int photoNo);
    int insert(int boardNo, String filename);
    List<BoardPhoto> findPhotosByBoardNo(int boardNo);
    BoardPhoto findByNo(int fileNo);
}