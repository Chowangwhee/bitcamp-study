package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import java.util.List;

public interface BoardDao {
    List<Board> findAll();
    Board findByNo(int no);
    void updateViewCount(int no);
    void update(Board board);
    void delete(int no);
    int insert(Board board); // Returns the generated boardId
}