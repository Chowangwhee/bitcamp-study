package bitcamp.myapp.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDao {
  Member findByEmail(String email);
  int changePassword(@Param("email") String email,
                     @Param("password") String password);

  int updateAllPasswords(String password);
}
