package org.example.tlias.controller;

import org.example.tlias.pojo.PageResult;
import org.example.tlias.pojo.Result;
import org.example.tlias.pojo.Student;
import org.example.tlias.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        return Result.success(student);
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1", required = true) Integer page,
                       @RequestParam(defaultValue = "10", required = true) Integer pageSize,
                       String name,
                       Integer degree,
                       Integer clazzId) {
        PageResult pageResult = studentService.page(page,pageSize,name,degree,clazzId);
        return Result.success(pageResult);
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        studentService.deleteByIds(ids);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Student student) {
        studentService.add(student);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Student student) {
        studentService.update(student);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable Integer score) {
        studentService.violation(id,score);
        return Result.success();
    }
}
