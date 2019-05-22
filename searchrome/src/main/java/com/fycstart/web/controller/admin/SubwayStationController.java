package com.fycstart.web.controller.admin;


import com.fycstart.bass.ApiResponse;
import com.fycstart.bass.StatusEnum;
import com.fycstart.service.SubwayStationService;
import com.fycstart.web.dto.SubwayStationDTO;
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
public class SubwayStationController {

    @Autowired
    private SubwayStationService stationService;

    /**
     * 获取对应地铁线路所支持的地铁站点
     *
     * @param subwayId
     * @return
     */
    @GetMapping("/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") Long subwayId) {
        List<SubwayStationDTO> stationDTOS = stationService.findAllStationBySubway(subwayId);
        if (stationDTOS.isEmpty()) {
            return ApiResponse.ofStatus(StatusEnum.NOT_FOUND);
        }

        return ApiResponse.ofSuccess(stationDTOS);
    }

}

