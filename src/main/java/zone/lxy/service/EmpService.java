package zone.lxy.service;

import zone.lxy.domain.Emp;

import java.util.List;

public interface EmpService {
    List<Emp> findAll();

    void save(Emp emp);

    void delete(String id);

    Emp findById(String id);

    void update(Emp emp);
}
