package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLMemberDao implements MemberDao {

    private Connection con;
    private SqlSessionFactory sqlSessionFactory;

    public MySQLMemberDao(Connection con, SqlSessionFactory sqlSessionFactory) {
        this.con = con;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public Member findByEmailAndPassword(String email, String password){
        String sql = "SELECT m.member_id, m.name, m.email" +
                "        FROM ed_member m" +
                "        WHERE m.email = ? AND m.pwd = sha2(?, 256)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Member member = new Member();
            member.setNo(rs.getInt("member_id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));

            pstmt.close();
            rs.close();
            return member;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
