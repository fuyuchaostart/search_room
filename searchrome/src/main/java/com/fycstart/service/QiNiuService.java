package com.fycstart.service;

import com.qiniu.http.Response;

import java.io.InputStream;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/5下午 5:38
 */
public interface QiNiuService {
    Response uploadFile(InputStream inputStream);
}
