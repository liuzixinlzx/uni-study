package com.lzx.uniserver.controller;


import com.lzx.uniserver.entity.Training;
import com.lzx.uniserver.service.TrainingService;
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
 * @since 2020-08-04
 */
@RestController
@RequestMapping("/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @RequestMapping("/list")
    public List<Training> getList(HttpServletRequest request) {
        return trainingService.list();
    }
}

