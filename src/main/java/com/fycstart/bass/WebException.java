package com.fycstart.bass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/4/30上午 10:03
 */
@Controller
public class WebException implements ErrorController {


    private static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;

    /**
     * 返回错误是访问的url
     *
     * @return
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * 处理web页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorPageHandler(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        switch (status) {
            case 403:
                return "403";
            case 400:
                return "400";

            case 404:
                return "404";
            case 500:
                return "500";
        }
        return "index";
    }

    @RequestMapping(value = ERROR_PATH)
    public ApiResponse errorApiHandler(HttpServletRequest request) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(requestAttributes, false);
        Object attribute = request.getAttribute("javax.servlet.error.status_code");
        Long status = Long.valueOf(attribute.toString());

        if (status == null) {
            status = 500L;
        }
        ApiResponse apiResponse = new ApiResponse(status, String.valueOf(errorAttributes.getOrDefault("message", "error")), null);

        return apiResponse;
    }
}
