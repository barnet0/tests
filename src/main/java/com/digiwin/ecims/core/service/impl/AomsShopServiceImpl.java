package com.digiwin.ecims.core.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;

/**
 * 电商铺货信息服务接口实现类
 * 
 * @author hopelee
 *
 */
@Service
public class AomsShopServiceImpl implements AomsShopService {
  @Autowired
  BaseDAO baseDao;

  public List<AomsshopT> getStoreByStoreType(String storeType) {
    StringBuilder strBld = 
        new StringBuilder("SELECT s FROM AomsshopT s WHERE s.aomsshop003 = ");
    strBld.append("'").append(storeType).append("' ");
    strBld.append("AND s.aomsshop004 IS NOT NULL ");
    strBld.append("AND s.aomsshop005 IS NOT NULL ");
    // strBld.append("AND s.aomsshop006 IS NOT NULL ");
    strBld.append("AND s.aomsshop013 = '1'");
    // add on 20161110 按店铺优先级排序，数量级高的先进行资料同步
    strBld.append("ORDER BY aomsshop014 ASC ");
    return baseDao.executeQueryByHql(strBld.toString());
  }


  /**
   * 
   * @param storeId
   * @return
   */
  @Override
  public AomsshopT getStoreByStoreId(String storeId) {
    String hql = "FROM AomsshopT s WHERE s.aomsshop001 ='" + storeId + "'";
    return baseDao.findUniqueResultBySQL(hql);
  }

  /**
   * 存放電商所授權的 api 使用碼
   * 
   * @param storeId
   * @return
   */
  public ESVerification getAuthorizationByStoreId(String storeId) {
    String sql = "FROM AomsshopT WHERE aomsshop001 ='" + storeId + "'";
    AomsshopT aomsshopT = (AomsshopT) baseDao.findUniqueResultBySQL(sql);
    String appKey = aomsshopT.getAomsshop004();
    //add for test
    System.out.println("appKey:" + appKey);
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    String userShopId = aomsshopT.getAomsshop012();
    String userSellerNick = aomsshopT.getAomsshop009();

    ESVerification esv = new ESVerification(appKey, appSecret, accessToken, userShopId, userSellerNick);
    return esv;
  }

  /**
   * 存放電商所授權的 api 使用碼
   * 
   * @author Xavier
   *
   */
  public static final class ESVerification {

    private String appKey;
    private String appSecret;
    private String accessToken;
    private String userStoreId; // add by mowj 20160601 添加商家店铺在平台的ID，有时会用到
    private String userSellerNick; // add by mowj 20160804 添加店铺在平台的名称

    public ESVerification(String appKey, String appSecret, String accessToken) {
      this.appKey = appKey;
      this.appSecret = appSecret;
      this.accessToken = accessToken;
    }
    
    public ESVerification(String appKey, String appSecret, String accessToken, String userStoreId,
        String userSellerNick) {
      super();
      this.appKey = appKey;
      this.appSecret = appSecret;
      this.accessToken = accessToken;
      this.userStoreId = userStoreId;
      this.userSellerNick = userSellerNick;
    }

    public String getAppKey() {
      return appKey;
    }

    public String getAppSecret() {
      return appSecret;
    }

    public String getAccessToken() {
      return accessToken;
    }

    public String getUserStoreId() {
      return userStoreId;
    }

    public String getUserSellerNick() {
      return userSellerNick;
    }

  }

}
