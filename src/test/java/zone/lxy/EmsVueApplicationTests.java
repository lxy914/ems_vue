package zone.lxy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zone.lxy.dao.EmpDao;
import zone.lxy.domain.Emp;

import java.util.List;

@SpringBootTest
class EmsVueApplicationTests {
    @Autowired
    private EmpDao empDao;

    @Test
    void contextLoads() {
        List<Emp> a1 = empDao.findAll();
        List<Emp> a2 = empDao.findAll();
        System.out.println(a1==a2);
        System.out.println(a1);
        System.out.println(a2);

        Emp e1 = empDao.findById("11");
        Emp e2 = empDao.findById("11");
        System.out.println(e1==e2);
        System.out.println(e1);
        System.out.println(e2);
    }

}
