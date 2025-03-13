package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.BoardPhoto;

import java.util.List;

public interface BoardDao {

    List<Board> findAll();

    Board findByNo(int no);

    List<BoardPhoto> findPhotosByBoardNo(int boardNo);

    void updateViewCount(int no);

    void update(Board board);

    void delete(int no);

    void insert(Board board);

    void insertPhotos(int boardId, List<BoardPhoto> photos);

    void deletePhoto(int photoNo);

    int insertPhoto(int boardNo, String filename);
}