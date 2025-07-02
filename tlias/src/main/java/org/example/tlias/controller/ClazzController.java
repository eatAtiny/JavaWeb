package org.example.tlias.controller;

import org.example.tlias.pojo.Clazz;
import org.example.tlias.pojo.PageResult;
import org.example.tlias.pojo.Result;
import org.example.tlias.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/list")
    public Result findAll(){
        List<Clazz> clazzs = clazzService.findAll();
        return Result.success(clazzs);
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1", required = true) Integer page,
                       @RequestParam(defaultValue = "10", required = true) Integer pageSize,
                       String name,
                       @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate end) {
        PageResult pageResult = clazzService.page(page,pageSize,name,begin,end);
        return Result.success(pageResult);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        clazzService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Clazz clazz){
        clazzService.save(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Clazz clazz = clazzService.findById(id);
        return Result.success(clazz);
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz){
        clazzService.update(clazz);
        return Result.success();
    }
}
