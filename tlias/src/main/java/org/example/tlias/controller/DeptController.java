package org.example.tlias.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.tlias.pojo.Dept;
import org.example.tlias.pojo.Result;
import org.example.tlias.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /*
    @RequestMapping("/depts")
    这里使用@GetMapping("/depts")和@PostMapping("/depts")是为了明确指定请求的HTTP方法。
     */

    @GetMapping
    public Result list() {
        List<Dept> depts = deptService.list();
        return Result.success(depts);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) { // @PathVariable用于获取请求路径中的参数，省略PathVariable的value属性，因为参数名和路径变量名一致
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /*
    在后端接收参数方法：
        1.可以使用HttpServletRequest对象来获取请求参数。
        2.可以使用@RequestParam注解来获取请求参数。（当请求参数名和方法参数名不一致时，需要使用@RequestParam注解来指定请求参数名，一致时可以省略）
        3.可以使用@PathVariable注解来获取请求路径中的参数。
        4.可以使用@RequestBody注解来获取请求体中的JSON数据。

     */
    @DeleteMapping
    public Result delete(Integer id) {// 此处省略@RequestParam注解，因为请求参数名和方法参数名一致
        System.out.println(id);
        return Result.success(deptService.deleteById(id));

    }

    /*
    @RequestBody注解用于将请求体中的JSON数据绑定到Java对象上。
    这通常用于处理POST请求中的JSON数据。
     */
    @PostMapping
    public Result save(@RequestBody Dept dept) {
        System.out.println(dept);
        return Result.success(deptService.save(dept));
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
        System.out.println(dept);
        return Result.success(deptService.update(dept));
    }

}
