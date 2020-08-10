package com.lzx.uniserver.controller;


import com.lzx.uniserver.entity.Version;
import com.lzx.uniserver.service.impl.VersionServiceImpl;
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
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/version")
public class VersionController {
    @Autowired
    private VersionServiceImpl versionService;

    @RequestMapping("/list")
    public List<Version> getList(HttpServletRequest request) {
        return versionService.list();
    }
}

