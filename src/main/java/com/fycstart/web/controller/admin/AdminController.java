package com.fycstart.web.controller.admin;

import com.fycstart.bass.ApiResponse;
import com.fycstart.bass.StatusEnum;
import com.fycstart.service.QiNiuService;
import com.fycstart.web.dto.QiNiuPutRet;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/4/30下午 1:52
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private Gson gson;


    /**
     * 后台管理中心
     *
     * @return
     */
    @GetMapping("/center")
    public String adminCenterPage() {
        return "admin/center";
    }

    /**
     * 欢迎页
     *
     * @return
     */
    @GetMapping("/welcome")
    public String welcomePage() {
        return "admin/welcome";
    }


    /**
     * 管理员登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String adminLoginPage() {
        return "admin/login";
    }


    /**
     * 上传图片接口
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/upload/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ApiResponse uploadPhoto(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ApiResponse.ofStatus(StatusEnum.NOT_VALID_PARAM);
        }

        try {
            InputStream inputStream = file.getInputStream();
            Response response = qiNiuService.uploadFile(inputStream);
            if (response.isOK()) {
                QiNiuPutRet ret = gson.fromJson(response.bodyString(), QiNiuPutRet.class);
                return ApiResponse.ofSuccess(ret);
            } else {
                return ApiResponse.ofMessage(Long.valueOf(response.statusCode), response.getInfo());
            }

        } catch (QiniuException e) {
            Response response = e.response;
            try {
                return ApiResponse.ofMessage(Long.valueOf(response.statusCode), response.bodyString());
            } catch (QiniuException e1) {
                e1.printStackTrace();
                return ApiResponse.ofStatus(StatusEnum.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return ApiResponse.ofStatus(StatusEnum.INTERNAL_SERVER_ERROR);
        }
    }


}
