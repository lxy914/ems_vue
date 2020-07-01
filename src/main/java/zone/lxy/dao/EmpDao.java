package zone.lxy.dao;

import org.apache.ibatis.annotations.*;
import zone.lxy.domain.Emp;

import java.util.List;

@Mapper
@CacheNamespace
public interface EmpDao {
    @Select("select * from t_emp")
    List<Emp> findAll();

    @Insert("insert into t_emp values(null,#{name},#{path},#{salary},#{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Emp emp);

    @Delete("delete from t_emp where id=#{id}")
    void delete(String id);

    @Select("select * from t_emp where id=#{id}")
    Emp findById(String id);

    @Update("update t_emp set name=#{name},path=#{path},salary=#{salary},age=#{age} where id=#{id}")
    void update(Emp emp);

}
