package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DefaultMemberDao implements MemberDao {

    private Connection con;

    public DefaultMemberDao(Connection con) {
        this.con = con;
    }

    public Member findByEmailAndPassword(String email, String password){
        String sql = "SELECT m.member_id, m.name, m.email" +
                "        FROM ed_member m" +
                "        WHERE m.email = '" + email + "' AND m.pwd = sha2('" + password + "', 256)";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.next()) {
                return null;
            }

            Member member = new Member();
            member.setNo(rs.getInt("member_id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            return member;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
