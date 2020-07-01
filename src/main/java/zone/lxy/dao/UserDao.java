package zone.lxy.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import zone.lxy.domain.User;

@Mapper
public interface UserDao {
    @Insert("insert into t_user values(null,#{username},#{realname},#{password},#{sex},#{status},#{registerTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Select("select * from t_user where username=#{username}")
    User findByName(User user);
}
