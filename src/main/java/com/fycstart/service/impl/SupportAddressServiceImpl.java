package com.fycstart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.bass.ServiceResult;
import com.fycstart.entity.SupportAddress;
import com.fycstart.mapper.SupportAddressMapper;
import com.fycstart.service.SupportAddressService;
import com.fycstart.web.dto.SupportAddressDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@Service
public class SupportAddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements SupportAddressService {


    @Autowired
    private SupportAddressMapper supportAddressMapper;

    /**
     * 获取支持城市的列表
     *
     * @return
     */
    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllCities() {

        QueryWrapper<SupportAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", "city");

        ServiceMultiResult<SupportAddressDTO> dtoServiceMultiResult = getSupportAddressDTOServiceMultiResult(queryWrapper);
        return dtoServiceMultiResult;
    }

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllRegionsByCityName(String cityEnName) {

        QueryWrapper<SupportAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", "region");
        queryWrapper.eq("belong_to", cityEnName);

        ServiceMultiResult<SupportAddressDTO> dtoServiceMultiResult = getSupportAddressDTOServiceMultiResult(queryWrapper);

        return dtoServiceMultiResult;
    }

    @Override
    public Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName) {

        Map<SupportAddress.Level, SupportAddressDTO> result = new HashMap<>();

        QueryWrapper<SupportAddress> cityQueryWrapper = new QueryWrapper<>();

        cityQueryWrapper.eq(cityEnName != null, "en_name", cityEnName);

        SupportAddress cityAddress = supportAddressMapper.selectOne(cityQueryWrapper);


        QueryWrapper<SupportAddress> regionQueryWrapper = new QueryWrapper<>();

        regionQueryWrapper.eq(regionEnName != null, "cn_name", regionEnName)
                .eq("level", SupportAddress.Level.REGION.getValue());
        SupportAddress regionSupportAddress = supportAddressMapper.selectOne(regionQueryWrapper);


        SupportAddressDTO cityAddressDTO = new SupportAddressDTO();
        BeanUtils.copyProperties(cityAddress, cityAddressDTO);

        SupportAddressDTO regionAddressDTO = new SupportAddressDTO();
        BeanUtils.copyProperties(cityAddress, regionAddressDTO);

        result.put(SupportAddress.Level.REGION, regionAddressDTO);
        result.put(SupportAddress.Level.CITY, cityAddressDTO);
        return result;
    }

    @Override
    public ServiceResult<SupportAddressDTO> findCityByCitiEnName(String cityEnName) {

        QueryWrapper<SupportAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(cityEnName != null, "en_name", cityEnName);
        SupportAddress supportAddress = supportAddressMapper.selectOne(queryWrapper);
        SupportAddressDTO addressDTO = new SupportAddressDTO();
        BeanUtils.copyProperties(supportAddress, addressDTO);

        ServiceResult<SupportAddressDTO> serviceResult = new ServiceResult<>(true, "查询成功", addressDTO);

        return serviceResult;
    }




    /**
     * @return com.fycstart.bass.ServiceMultiResult<com.fycstart.web.dto.SupportAddressDTO>
     * @Author fyc
     * @Description // 根据条件获取查询结果
     * @Date 下午 4:09 2019/5/5
     * @Param [queryWrapper]
     **/
    private ServiceMultiResult<SupportAddressDTO> getSupportAddressDTOServiceMultiResult(QueryWrapper<SupportAddress> queryWrapper) {

        List<SupportAddress> supportAddresses = supportAddressMapper.selectList(queryWrapper);
        List<SupportAddressDTO> addressDTOS = new ArrayList<>(supportAddresses.size());
        supportAddresses.forEach(address -> {
            SupportAddressDTO addressDTO = new SupportAddressDTO();
            BeanUtils.copyProperties(address, addressDTO);
            addressDTOS.add(addressDTO);
        });
        //整理返回值
        return new ServiceMultiResult<>(addressDTOS.size(), addressDTOS);
    }
}
