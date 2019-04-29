package com.unis.db.controller;

import com.unis.db.service.impl.MakeDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuli
 * @date 2019/4/23
 */
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private MakeDataServiceImpl makeDataService;

    @PostMapping(value = {"/makeData"})
    public Boolean makeData() {
        return makeDataService.makeData("person");
    }

    @PostMapping(value = {"/makeData/partition"})
    public Boolean makeDataByPartition() {
        return makeDataService.makeDataByPartition("person");
    }
}
