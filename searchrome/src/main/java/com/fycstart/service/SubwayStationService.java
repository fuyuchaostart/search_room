package com.fycstart.service;

import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.SubwayStation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fycstart.web.dto.SubwayStationDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface SubwayStationService extends IService<SubwayStation> {

    List<SubwayStationDTO> findAllStationBySubway(Long subwayId);

    ServiceResult<SubwayStationDTO> findSubwayStation(Long subwayStationId);
}
