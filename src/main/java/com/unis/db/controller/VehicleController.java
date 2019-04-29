package com.unis.db.controller;

import com.unis.db.common.utils.ToolUtils;
import com.unis.db.service.MakeDataService;
import com.unis.db.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * @author xuli
 * @date 2019/4/16
 */
@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    @Autowired
    private MakeDataService makeDataService;

    @Autowired
    private VehicleService vehicleService;

    @Value("${generate.algorithm-version}")
    private String version;

    @PostMapping(value = {"/makeData"})
    public Boolean makeData() {
        return makeDataService.makeData("vehicle");
    }

    @PostMapping(value = {"/makeData/partition"})
    public Boolean makeDataByPartition() {
        return makeDataService.makeDataByPartition("vehicle");
    }

    @GetMapping(value = {"/search/recordId"})
    public double getRecordIdRandom(@RequestParam(name = "month") String month,
                                    @RequestParam(name = "days", required = false, defaultValue = "1") int days) {
        String tableName = String.format("viid_vehicle.vehiclestructured_%s_%s", version, month);
        String startDate = ToolUtils.getDate(month, days);
        String endDate = ToolUtils.getDay(startDate, days - 1);
        return vehicleService.getRecordIdPartition(tableName, startDate, endDate);
    }

    @GetMapping(value = {"/search/structured"})
    public double searchStructured(@RequestParam(name = "month") String month,
                                @RequestParam(name = "days", required = false, defaultValue = "1") int days,
                                @RequestParam(name = "number", required = false, defaultValue = "1") int number) {
        String tableName = String.format("viid_vehicle.vehiclestructured_%s_%s", version, month);
        String startDate = ToolUtils.getDate(month, days);
        String endDate = ToolUtils.getDay(startDate, days - 1);
        return vehicleService.searchStructured(tableName, startDate, endDate, number);
    }

    public void throwBusinessException(BindingResult bindingResult){

    }

}
