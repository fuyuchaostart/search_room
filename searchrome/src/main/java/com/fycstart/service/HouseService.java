package com.fycstart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.House;
import com.fycstart.web.dto.HouseDTO;
import com.fycstart.web.form.DatatableSearch;
import com.fycstart.web.form.HouseForm;
import com.fycstart.web.form.RentSearch;

import java.io.IOException;

/**
 * <p>
 * 房屋信息表 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface HouseService extends IService<House> {

    ServiceResult<HouseDTO> saveHouse(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> queryHouse(DatatableSearch datatableSearch);

    ServiceMultiResult<HouseDTO> userQueryHouse(RentSearch rentSearch);

    ServiceResult updateStatus(Integer id, int value) throws IOException;

    ServiceResult<HouseDTO> queryHouseById(Long id);

    ServiceResult updateHouse(HouseForm houseForm) throws IOException;
}
