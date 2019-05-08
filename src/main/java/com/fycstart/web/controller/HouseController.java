package com.fycstart.web.controller;


import com.fycstart.bass.*;
import com.fycstart.entity.SupportAddress;
import com.fycstart.service.HouseService;
import com.fycstart.service.SubwayService;
import com.fycstart.service.SubwayStationService;
import com.fycstart.service.SupportAddressService;
import com.fycstart.web.dto.*;
import com.fycstart.web.form.DatatableSearch;
import com.fycstart.web.form.HouseForm;
import com.fycstart.web.form.RentSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 房屋信息表 前端控制器
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
@Controller
public class HouseController {

    private final Logger logger = LoggerFactory.getLogger(HouseController.class);

    @Autowired
    private HouseService houseService;

    @Autowired
    private SupportAddressService addressService;

    @Autowired
    private SubwayService subwayService;

    @Autowired
    private SubwayStationService stationService;


    @GetMapping("/admin/add/house")
    public String hello() {
        return "admin/house-add";
    }

    @GetMapping("/admin/house/subscribe")
    public String houseSubscribe() {
        return "admin/subscribe";
    }


    /**
     * 房源列表页
     *
     * @return
     */
    @GetMapping("/admin/house/list")
    public String houseListPage() {
        return "admin/house-list";
    }

    @PostMapping("/admin/houses")
    @ResponseBody
    public ApiDataTablesResponse getHouseList(DatatableSearch datatableSearch) {

        ServiceMultiResult<HouseDTO> result = houseService.queryHouse(datatableSearch);
        ApiDataTablesResponse apiDataTablesResponse = new ApiDataTablesResponse(StatusEnum.SUCCESS);

        apiDataTablesResponse.setDraw(datatableSearch.getDraw());
        apiDataTablesResponse.setRecordsFiltered(result.getTotal());
        apiDataTablesResponse
                .setRecordsTotal(result.getTotal());
        apiDataTablesResponse.setData(result.getResult());
        return apiDataTablesResponse;
    }


    /**
     * @return com.fycstart.bass.ApiResponse
     * @Author fyc
     * @Description // 新增房源信息
     * @Date 下午 8:20 2019/5/5
     * @Param [houseForm, bindingResult]
     **/
    @PostMapping("/admin/add/house")
    @ResponseBody
    public ApiResponse addHouse(@Valid @ModelAttribute("form-house-add") HouseForm houseForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ApiResponse(Long.valueOf(HttpStatus.BAD_REQUEST.value()), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }

        if (houseForm.getPhotos() == null || houseForm.getCover() == null) {
            return ApiResponse.ofMessage(Long.valueOf(HttpStatus.BAD_REQUEST.value()), "必须上传图片");
        }

        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());
        if (addressMap.keySet().size() != 2) {
            return ApiResponse.ofStatus(StatusEnum.NOT_VALID_PARAM);
        }

        ServiceResult<HouseDTO> result = houseService.saveHouse(houseForm);
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess(result.getResult());
        }

        return ApiResponse.ofSuccess(StatusEnum.NOT_VALID_PARAM);
    }


    @GetMapping("/rent/house")
    public String rentHousePage(@ModelAttribute RentSearch rentSearch,
                                Model model, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (rentSearch.getCityEnName() == null) {
            String cityEnNameInSession = (String) session.getAttribute("cityEnName");
            if (cityEnNameInSession == null) {
                redirectAttributes.addAttribute("msg", "must_chose_city");
                return "redirect:/index";
            } else {
                rentSearch.setCityEnName(cityEnNameInSession);
            }
        } else {
            session.setAttribute("cityEnName", rentSearch.getCityEnName());
        }
        //校验是否设置城市
        ServiceResult<SupportAddressDTO> city = addressService.findCityByCitiEnName(rentSearch.getCityEnName());
        if (!city.isSuccess()) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }
        model.addAttribute("currentCity", city.getResult());

        //校验区域信息
        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(rentSearch.getCityEnName());
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }

        ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.userQueryHouse(rentSearch);

        model.addAttribute("total", serviceMultiResult.getTotal());
        model.addAttribute("houses", serviceMultiResult.getResult());

        if (rentSearch.getRegionEnName() == null) {
            rentSearch.setRegionEnName("*");
        }

        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("regions", addressResult.getResult());

        model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("areaBlocks", RentValueBlock.AREA_BLOCK);

        model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));
        model.addAttribute("currentAreaBlock", RentValueBlock.matchArea(rentSearch.getAreaBlock()));

        return "rent-list";
    }


    /**
     * 审核接口
     *
     * @param id
     * @param operation
     * @return
     */
    @PutMapping("admin/house/operate/{id}/{operation}")
    @ResponseBody
    public ApiResponse operateHouse(@PathVariable(value = "id") Integer id,
                                    @PathVariable(value = "operation") int operation) {
        if (id <= 0) {
            return ApiResponse.ofStatus(StatusEnum.NOT_VALID_PARAM);
        }
        ServiceResult result = null;
        try {
            switch (operation) {
                case HouseOperation.PASS:
                    result = this.houseService.updateStatus(id, HouseStatus.PASSES.getValue());
                    break;
                case HouseOperation.PULL_OUT:
                    result = this.houseService.updateStatus(id, HouseStatus.NOT_AUDITED.getValue());
                    break;
                case HouseOperation.DELETE:
                    result = this.houseService.updateStatus(id, HouseStatus.DELETED.getValue());
                    break;
                case HouseOperation.RENT:
                    result = this.houseService.updateStatus(id, HouseStatus.RENTED.getValue());
                    break;
                default:
                    return ApiResponse.ofStatus(StatusEnum.BAD_REQUEST);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = new ServiceResult(false);
        }
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess(null);
        }
        return ApiResponse.ofMessage(StatusEnum.BAD_REQUEST.getStatus(),
                result.getMessage());
    }


    /**
     * 房源信息编辑页
     *
     * @return
     */
    @GetMapping("admin/house/edit")
    public String houseEditPage(@RequestParam(value = "id") Long id, Model model) {

        if (id == null || id < 1) {
            return "404";
        }

        ServiceResult<HouseDTO> serviceResult = houseService.queryHouseById(id);
        if (!serviceResult.isSuccess()) {
            return "404";
        }

        HouseDTO result = serviceResult.getResult();
        model.addAttribute("house", result);

        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(result.getCityEnName(), result.getRegionEnName());
        model.addAttribute("city", addressMap.get(SupportAddress.Level.CITY));
        model.addAttribute("region", addressMap.get(SupportAddress.Level.REGION));

        HouseDetailDTO detailDTO = result.getHouseDetail();
        ServiceResult<SubwayDTO> subwayServiceResult = subwayService.findSubway(detailDTO.getSubwayLineId());
        if (subwayServiceResult.isSuccess()) {
            model.addAttribute("subway", subwayServiceResult.getResult());
        }

        ServiceResult<SubwayStationDTO> subwayStationServiceResult = stationService.findSubwayStation(detailDTO.getSubwayStationId());
        if (subwayStationServiceResult.isSuccess()) {
            model.addAttribute("station", subwayStationServiceResult.getResult());
        }
        return "admin/house-edit";
    }


    /**
     * 编辑接口
     */
    @PostMapping("admin/house/edit")
    @ResponseBody
    public ApiResponse saveHouse(@Valid @ModelAttribute("form-house-edit") HouseForm houseForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ApiResponse(StatusEnum.SUCCESS.getStatus(), bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
        }

        Map<SupportAddress.Level, SupportAddressDTO> addressMap = addressService.findCityAndRegion(houseForm.getCityEnName(), houseForm.getRegionEnName());

        if (addressMap.keySet().size() != 2) {
            return ApiResponse.ofSuccess(StatusEnum.NOT_VALID_PARAM);
        }

        ServiceResult result = null;
        try {
            result = houseService.updateHouse(houseForm);
        } catch (IOException e) {
            e.printStackTrace();
            result = new ServiceResult(false);
        }
        if (result.isSuccess()) {
            return ApiResponse.ofSuccess(null);
        }

        ApiResponse response = ApiResponse.ofStatus(StatusEnum.BAD_REQUEST);
        response.setMessage(result.getMessage());
        return response;
    }


}

