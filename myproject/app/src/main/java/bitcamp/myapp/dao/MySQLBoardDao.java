package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBoardDao implements BoardDao {

    private Connection con;

    public MySQLBoardDao(Connection con) {
        this.con = con;
    }

    @Override
    public List<Board> findAll() {
        String sql = "SELECT b.board_id, b.title, b.create_date, b.view_count, m.member_id, m.name " +
                "FROM ed_board b " +
                "JOIN ed_member m " +
                "ON b.member_id = m.member_id " +
                "ORDER BY b.board_id DESC";
        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            ArrayList<Board> list = new ArrayList<>();
            while (rs.next()) {
                Board board = new Board();
                board.setNo(rs.getInt("board_id"));
                board.setTitle(rs.getString("title"));
                board.setCreateDate(rs.getDate("create_date"));
                board.setViewCount(rs.getInt("view_count"));

                Member member = new Member();
                member.setNo(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                board.setWriter(member);
                list.add(board);
            }
            return list;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Board findByNo(int no) {
        String sql = "SELECT b.board_id, b.title, b.content, b.create_date, b.view_count, m.member_id, m.name " +
                "FROM ed_board b " +
                "JOIN ed_member m " +
                "ON b.member_id = m.member_id " +
                "WHERE b.board_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Board board = new Board();
                board.setNo(rs.getInt("board_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreateDate(rs.getDate("create_date"));
                board.setViewCount(rs.getInt("view_count"));

                Member member = new Member();
                member.setNo(rs.getInt("member_id"));
                member.setName(rs.getString("name"));
                board.setWriter(member);
                return board;
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateViewCount(int no) {
        String sql = "UPDATE ed_board SET view_count = view_count + 1 WHERE board_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Board board) {
        String sql = "UPDATE ed_board SET title = ?, content = ? WHERE board_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getContent());
            pstmt.setInt(3, board.getNo());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int no) {
        String sql = "DELETE FROM ed_board WHERE board_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int insert(Board board) {
        String sql = "INSERT INTO ed_board(title, content, create_date, view_count, member_id) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, board.getTitle());
            stmt.setString(2, board.getContent());
            stmt.setDate(3, new Date(System.currentTimeMillis()));
            stmt.setInt(4, 0);
            stmt.setInt(5, board.getWriter().getNo());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated board ID
                } else {
                    throw new SQLException("Inserting board failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}