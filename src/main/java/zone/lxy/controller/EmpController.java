package zone.lxy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zone.lxy.domain.Emp;
import zone.lxy.domain.Result;
import zone.lxy.service.EmpService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/emp")
@Slf4j
public class EmpController {
    @Autowired
    private EmpService empService;

    @Value("${upload.dir}")
    private String realPath;

    // 返回所有员工信息
    @GetMapping("findAll")
    public Result findAll() {
        Result result = new Result();
        result.setStatus(true);
        result.setData(empService.findAll());
        return result;
    }

    // 添加员工
    @PostMapping("/save")
    public Result save(Emp emp, MultipartFile photo) throws IOException {
        log.info("员工信息：{}", emp);
        log.info("头像信息：{}", photo.getOriginalFilename());
        Result result = new Result();

        try {
            int lastIndexOf = photo.getOriginalFilename().lastIndexOf(".");
            //获取文件的后缀名 .jpg
            String suffix = photo.getOriginalFilename().substring(lastIndexOf);

            String filename = UUID.randomUUID().toString() + suffix;
            photo.transferTo(new File(this.realPath, filename));
            emp.setPath(filename);
            empService.save(emp);
            result.setStatus(true);
            result.setMsg("员工信息保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("员工信息保存失败");
        }
        return result;
    }

    // 删除员工
    @PostMapping("/delete")
    public Result delete(String id) {
        log.info("删除员工的id：{}", id);
        Result result = new Result();
        try {
            // 删除员工头像
            delphoto(id);
            // 删除员工信息
            empService.delete(id);
            result.setStatus(true);
            result.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("删除失败");
        }
        return result;
    }

    // 获取一个员工信息
    @GetMapping("/findOne")
    public Result findOne(String id) {
        Result result = new Result();
        result.setStatus(true);
        result.setData(empService.findById(id));
        return result;
    }

    // 根据id修改员工信息
    @PostMapping("/update")
    public Result update(Emp emp, MultipartFile photo) throws IOException {
        log.info("员工信息：{}", emp);
        log.info("图片对象：{}",photo);
        Result result = new Result();
        try {
            if (photo != null && photo.getSize() != 0) {
                log.info("头像信息：{}", photo.getOriginalFilename());
                int lastIndexOf = photo.getOriginalFilename().lastIndexOf(".");
                //获取文件的后缀名 .jpg
                String suffix = photo.getOriginalFilename().substring(lastIndexOf);

                String filename = UUID.randomUUID().toString() + suffix;
                photo.transferTo(new File(this.realPath, filename));
                emp.setPath(filename);
                // 删除旧头像
                delphoto(emp.getId());
            }

            empService.update(emp);
            result.setStatus(true);
            result.setMsg("员工信息保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(false);
            result.setMsg("员工信息保存失败");
        }
        return result;
    }

    // 删除物理路径的图片
    public void delphoto(String id) {
        Emp emp = empService.findById(id);
        File file = new File(realPath, emp.getPath());
        // 删除头像
        if (file.exists()) {
            file.delete();
        }
    }
}
