package com.digiwin.ecims.platforms.feiniu.util;

import com.feiniu.open.api.sdk.DefaultFnClient;
import com.feiniu.open.api.sdk.FnClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zaregoto on 2017/1/26.
 */
public final class FeiniuClientUtil {

    private static volatile FeiniuClientUtil feiniuClientUtil;

    private FeiniuClientUtil () {

    }

    public static FeiniuClientUtil getInstance() {
        if (feiniuClientUtil == null) {
            synchronized (FeiniuClientUtil.class) {
                if (feiniuClientUtil == null) {
                   feiniuClientUtil = new FeiniuClientUtil();
                }
            }
        }

        return feiniuClientUtil;
    }

    private volatile static Map<String, FnClient> fnClientMap = new HashMap<>();

    public FnClient getFnClient(String serverUrl, String appKey, String appSecret, String accessToken) {
        FnClient fnClient = null;
        if (fnClientMap.containsKey(accessToken)) {
            fnClient = fnClientMap.get(accessToken);
        } else {
            fnClient = new DefaultFnClient(
                serverUrl, accessToken, appKey, appSecret,
                FeiniuCommonTool.FN_API_CONNECT_TIMEOUT,
                FeiniuCommonTool.FN_API_READ_TIMEOUT);
            fnClientMap.put(accessToken, fnClient);
        }
        return fnClient;
    }
}
