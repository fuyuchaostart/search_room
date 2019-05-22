package com.fycstart.web.controller.admin;


import com.fycstart.bass.ApiResponse;
import com.fycstart.bass.StatusEnum;
import com.fycstart.service.SubwayService;
import com.fycstart.web.dto.SubwayDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@RestController
@RequestMapping("/address")
public class SubwayController {

    @Autowired
    private SubwayService subwayService;


    /**
     * 获取具体城市所支持的地铁线路
     *
     * @param cityEnName
     * @return
     */
    @GetMapping("/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        List<SubwayDTO> subways = subwayService.findAllSubwayByCity(cityEnName);
        if (subways.isEmpty()) {
            return ApiResponse.ofStatus(StatusEnum.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(subways);
    }

}

