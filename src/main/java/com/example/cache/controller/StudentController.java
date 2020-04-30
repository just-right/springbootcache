package com.example.cache.controller;

import com.example.cache.entity.Student;
import com.example.cache.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Student)表控制层
 *
 * @author makejava
 * @since 2020-04-30 10:15:56
 */
@RestController
@RequestMapping("student")
public class StudentController {
    /**
     * 服务对象
     */
    @Resource
    private StudentService studentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Student selectOne(Integer id) {
        return this.studentService.queryById(id);
    }

    @PutMapping("updateOne")
    public Student updateOne(@RequestBody Student student) {
        return this.studentService.update(student);
    }

    @DeleteMapping("deleteOne")
    public boolean deleteOne(Integer id) {
        return this.studentService.deleteById(id);
    }

    @PostMapping("addOne")
    public Student addOne(@RequestBody Student student) {
        return this.studentService.insert(student);
    }
}