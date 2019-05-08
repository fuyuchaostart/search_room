package com.fycstart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fycstart.entity.HouseDetail;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public interface HouseDetailService extends IService<HouseDetail> {

    List<HouseDetail> findAllByHouseIdIn(List<Integer> houseIds);
}
