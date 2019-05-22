package com.fycstart.service;

import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.SupportAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fycstart.web.dto.SubwayDTO;
import com.fycstart.web.dto.SupportAddressDTO;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface SupportAddressService extends IService<SupportAddress> {

    ServiceMultiResult<SupportAddressDTO> findAllCities();

    ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityEnName);

    Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);

    ServiceResult<SupportAddressDTO> findCityByCitiEnName(String cityEnName);


}
