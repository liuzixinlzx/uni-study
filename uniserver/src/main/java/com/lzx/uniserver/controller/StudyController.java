package com.lzx.uniserver.controller;


import com.lzx.uniserver.entity.Study;
import com.lzx.uniserver.service.StudyService;
import com.lzx.uniserver.service.impl.StudyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/study")
public class StudyController {

    @Autowired
    private StudyServiceImpl studyService;

    @RequestMapping("/list")
    public List<Study> getList(HttpServletRequest request) {
        return studyService.list();
    }
}

