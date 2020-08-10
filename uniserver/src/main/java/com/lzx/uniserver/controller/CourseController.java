package com.lzx.uniserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzx.uniserver.entity.Course;
import com.lzx.uniserver.service.impl.CourseServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseServiceImpl CourseService;

    @RequestMapping("/list")
    public List<Course> getList(HttpServletRequest request) {
        System.out.println("###### /course/list ######");
        String sdate = request.getParameter("date");
        LocalDate date = LocalDate.parse(sdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Course::getDate, date);
        return CourseService.list(lqw);
    }
}

