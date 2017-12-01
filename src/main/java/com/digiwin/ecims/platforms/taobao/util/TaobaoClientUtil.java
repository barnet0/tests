package com.digiwin.ecims.platforms.taobao.util;

import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryClusterTaobaoClient;
import com.taobao.api.AutoRetryTaobaoClient;
import com.taobao.api.BatchTaobaoClient;
import com.taobao.api.ClusterTaobaoClient;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;

import com.digiwin.ecims.platforms.taobao.bean.api.MyAutoRetryClusterTaobaoClient;

/**
 * 淘宝客户端获取工具。单例
 * @author 维杰
 *
 */
public final class TaobaoClientUtil {
  
  private volatile static TaobaoClientUtil taobaoClientUtil;
  
  private TaobaoClientUtil(){}
  
  public static TaobaoClientUtil getInstance() {
    if (taobaoClientUtil == null) {
      synchronized (TaobaoClientUtil.class) {
        if (taobaoClientUtil == null) {
          taobaoClientUtil = new TaobaoClientUtil();
        }
      }
    }
    return taobaoClientUtil;
  }
  
  private volatile static DefaultTaobaoClient defaultTaobaoClient;
  private volatile static AutoRetryTaobaoClient autoRetryTaobaoClient;
  private volatile static ClusterTaobaoClient clusterTaobaoClient;
  private volatile static MyAutoRetryClusterTaobaoClient myAutoRetryClusterTaobaoClient;
  private volatile static BatchTaobaoClient batchTaobaoClient;
  
  public DefaultTaobaoClient getDefaultTaobaoClient(String serverUrl, String appKey, String appSecret) {
    if (defaultTaobaoClient == null) {
      synchronized (TaobaoClientUtil.class) {
        if (defaultTaobaoClient == null) {
          defaultTaobaoClient = new DefaultTaobaoClient(serverUrl, appKey, appSecret, Constants.FORMAT_JSON);
        }
      }
    }
    return defaultTaobaoClient;
  }
  
  public AutoRetryTaobaoClient getAutoRetryTaobaoClient(String serverUrl, String appKey, String appSecret) {
    if (autoRetryTaobaoClient == null) {
      synchronized (TaobaoClientUtil.class) {
        if (autoRetryTaobaoClient == null) {
          autoRetryTaobaoClient = new AutoRetryTaobaoClient(serverUrl, appKey, appSecret, Constants.FORMAT_JSON);
        }
      }
    }
    return autoRetryTaobaoClient;
  }
  
  public ClusterTaobaoClient getClusterTaobaoClient(String serverUrl, String appKey, String appSecret) throws ApiException {
    if (clusterTaobaoClient == null) {
      synchronized (TaobaoClientUtil.class) {
        if (clusterTaobaoClient == null) {
          clusterTaobaoClient = new ClusterTaobaoClient(serverUrl, appKey, appSecret, Constants.FORMAT_JSON);
        }
      }
    }
    return clusterTaobaoClient;
  }
  
  public AutoRetryClusterTaobaoClient getMyAutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret) throws ApiException {
    if (myAutoRetryClusterTaobaoClient == null) {
      synchronized (TaobaoClientUtil.class) {
        if (myAutoRetryClusterTaobaoClient == null) {
          myAutoRetryClusterTaobaoClient = new MyAutoRetryClusterTaobaoClient(serverUrl, appKey, appSecret, Constants.FORMAT_JSON);
        }
      }
    } else {
      myAutoRetryClusterTaobaoClient.setServerUrl(serverUrl);
    }
    return myAutoRetryClusterTaobaoClient;
  }
  
  public BatchTaobaoClient getBatchTaobaoClient(String serverUrl, String appKey, String appSecret) {
    if (batchTaobaoClient == null) {
      synchronized (TaobaoClientUtil.class) {
        if (batchTaobaoClient == null) {
          batchTaobaoClient = new BatchTaobaoClient(serverUrl, appKey, appSecret, Constants.FORMAT_JSON);
        }
      }
    }
    return batchTaobaoClient;
  }
  
  
}
