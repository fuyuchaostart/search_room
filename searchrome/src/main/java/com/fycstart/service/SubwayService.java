package com.fycstart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.Subway;
import com.fycstart.web.dto.SubwayDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface SubwayService extends IService<Subway> {

    List<SubwayDTO> findAllSubwayByCity(String cityEnName);

    ServiceResult<SubwayDTO> findSubway(Long subwayLineId);
}
