package com.fycstart.service.impl;

import com.fycstart.security.AuthProvider;
import com.fycstart.service.QiNiuService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/5下午 5:38
 */
@Service
public class QiNiuServiceImpl implements QiNiuService, InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(AuthProvider.class);


    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    private StringMap putPolicy;

    @Override
    public Response uploadFile(InputStream inputStream) {
        Response response = null;
        try {
            response = uploadManager.put(inputStream, null, getUploadToken(), null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);

            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
                retry++;
            }
            return response;
        } catch (QiniuException ex) {
            Response r = ex.response;
            LOG.error(r.toString());
            try {
                LOG.error(r.bodyString());
            } catch (QiniuException ex2) {
            }
        }
        return response;
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");

    }
}
