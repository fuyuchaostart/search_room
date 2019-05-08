package com.fycstart.web.controller.admin;


import com.fycstart.bass.ApiResponse;
import com.fycstart.bass.ServiceMultiResult;
import com.fycstart.bass.StatusEnum;
import com.fycstart.service.SupportAddressService;
import com.fycstart.web.dto.SupportAddressDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class SupportAddressController {
    @Autowired
    private SupportAddressService addressService;

    /**
     * 获取支持城市列表
     *
     * @return
     */
    @GetMapping("/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities() {
        ServiceMultiResult<SupportAddressDTO> result = addressService.findAllCities();
        if (result.getResultSize() == 0) {
            return ApiResponse.ofStatus(StatusEnum.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

    /**
     * 获取对应城市支持区域列表
     *
     * @param cityEnName
     * @return
     */
    @GetMapping("/support/regions")
    @ResponseBody
    public ApiResponse getSupportRegions(@RequestParam(name = "city_name") String cityEnName) {
        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(cityEnName);
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            return ApiResponse.ofStatus(StatusEnum.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(addressResult.getResult());
    }


}

