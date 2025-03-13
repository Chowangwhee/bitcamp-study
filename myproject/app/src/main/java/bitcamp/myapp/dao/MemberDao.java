package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Member;

public interface MemberDao {

    public Member findByEmailAndPassword(String email, String password);
}
