package com.digiwin.ecims.platforms.baidu.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.MerchantStatus;
import com.digiwin.ecims.platforms.baidu.bean.domain.item.ImageInfo;
import com.digiwin.ecims.platforms.baidu.bean.domain.item.ItemInfo;
import com.digiwin.ecims.platforms.baidu.bean.domain.item.SellAttribute;
import com.digiwin.ecims.platforms.baidu.bean.domain.item.SkuInfo;
import com.digiwin.ecims.platforms.baidu.bean.domain.item.SkuPp;
import com.digiwin.ecims.platforms.baidu.bean.domain.item.UserDefinedSellAttribute;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AomsitemTTranslator {

  private Object itemResponse;

  public AomsitemTTranslator() {
    super();
  }

  public AomsitemTTranslator(Object itemResponse) {
    super();
    this.itemResponse = itemResponse;
  }

  @SuppressWarnings("unchecked")
  public List<AomsitemT> doTranslate(String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    if (itemResponse instanceof ItemInfo) {
      ItemInfo itemAllInfo = (ItemInfo) itemResponse;
      List<AomsitemT> result = parseItemInfoToAomsitemT(itemAllInfo, storeId);
      aomsitemTs.addAll(result);
    } else if (itemResponse instanceof List) {
      List<ItemInfo> itemAllInfos = (List<ItemInfo>) itemResponse;
      for (ItemInfo itemAllInfo : itemAllInfos) {
        aomsitemTs.addAll(parseItemInfoToAomsitemT(itemAllInfo, storeId));
      }
    }
    return aomsitemTs;
  }

  private List<AomsitemT> parseItemInfoToAomsitemT(ItemInfo itemAllInfo, String storeId) {
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    for (SkuInfo skuInfo : itemAllInfo.getSkuInfos()) {
      AomsitemT aomsitemT = new AomsitemT();

      aomsitemT.setId(CommonUtil.checkNullOrNot(itemAllInfo.getItemId()));
      aomsitemT.setAoms002(CommonUtil.checkNullOrNot(itemAllInfo.getOuterId()));
      aomsitemT.setAoms003(CommonUtil.checkNullOrNot(itemAllInfo.getTitle()));
      aomsitemT.setAoms004(CommonUtil.checkNullOrNot(skuInfo.getSkuId()));
      aomsitemT.setAoms005(CommonUtil.checkNullOrNot(skuInfo.getOuterId()));

      List<SellAttribute> defaultSellAttribute = skuInfo.getSellAttribute();
      List<UserDefinedSellAttribute> customSellAttribute = skuInfo.getUserDefinedSellAttribute();
      BaiduSkuColorSizeAttribute sColorSizeAttribute = getSkuColorSizeAttribute(defaultSellAttribute, customSellAttribute);
      // userDefinedSellAttribute或sellAttribute中，pid=28的为颜色属性值；pid=41998或50067的为尺寸属性值
      // eg. 1
      // "userDefinedSellAttribute": [
      // {
      // "pid": 28,
      // "vname": "扁平型"
      // },
      // {
      // "pid": 50067,
      // "vname": "48*74cm"
      // }
      // ],
      // eg. 2
      // "userDefinedSellAttribute": [
      // {
      // "pid": 28,
      // "vname": "实物拍摄"
      // }
      // ],
      // "skuId": 50321,
      // "volume": 0,
      // "sellAttribute": [
      // {
      // "pid": 41998,
      // "vname": "180*200cm",
      // "vid": 364905
      // }
      // ],
      aomsitemT.setAoms006(CommonUtil.checkNullOrNot(sColorSizeAttribute.toString()));
      // TODO 商品的颜色与尺码，从006中获取
      aomsitemT.setAoms008(CommonUtil.checkNullOrNot(sColorSizeAttribute.getColor()));
      aomsitemT.setAoms009(CommonUtil.checkNullOrNot(sColorSizeAttribute.getSize()));

      MerchantStatus status =
          MerchantStatus.getMerchantStatusByValue(itemAllInfo.getMerchantStatus());
      aomsitemT.setAoms007(CommonUtil
          .checkNullOrNot(status == MerchantStatus.ONLINE_MERCHANT ? "onsale" : "instock"));

      aomsitemT.setStoreId(storeId);
      aomsitemT.setStoreType(BaiduCommonTool.STORE_TYPE);

      List<ImageInfo> imageInfos = itemAllInfo.getImageInfos();
      aomsitemT.setAoms013(CommonUtil.checkNullOrNot(itemAllInfo.getUrl()));
      if (imageInfos != null && imageInfos.size() > 0) {
        aomsitemT.setAoms014(CommonUtil.checkNullOrNot(imageInfos.get(0).getImageBCSUrl()));
      }

      aomsitemT.setAoms018(
          CommonUtil.checkNullOrNot(getSkuStockBySkuId(skuInfo.getSkuId(), skuInfo.getSkuPps())));
      aomsitemT.setAoms019(CommonUtil.checkNullOrNot(skuInfo.getUpdateTime()));

      Date now = new Date();
      aomsitemT.setAomsstatus("0");
      aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsitemTs.add(aomsitemT);
    }

    return aomsitemTs;
  }

  private int getSkuStockBySkuId(long skuId, List<SkuPp> skuPpsList) {
    int stock = 0;
    for (SkuPp skuPps : skuPpsList) {
      if (skuPps.getSkuId() == skuId) {
        stock = skuPps.getStock();
        break;
      }
    }
    return stock;
  }

  private BaiduSkuColorSizeAttribute getSkuColorSizeAttribute(List<SellAttribute> defaultSellAttribute,
      List<UserDefinedSellAttribute> customSellAttribute) {
    String color = "";
    String size = "";
    if (defaultSellAttribute != null) {
      for (SellAttribute sellAttribute : defaultSellAttribute) {
        int pid = sellAttribute.getPid();
        if (pid == BaiduCommonTool.ITEM_COLOR_ATTR_CODE) {
          color = sellAttribute.getVname();
        }
        else if (isItemSizeAttrCode(pid)) {
          size = sellAttribute.getVname();
        }
        if (color.length() > 0 && size.length() > 0) {
          break;
        }
      }
      
    }
    if (customSellAttribute != null) {
      for (UserDefinedSellAttribute customAttribute : customSellAttribute) {
        int pid = customAttribute.getPid();
        if (pid == BaiduCommonTool.ITEM_COLOR_ATTR_CODE) {
          color = customAttribute.getVname();
        }
        else if (isItemSizeAttrCode(pid)) {
          size = customAttribute.getVname();
        }
        if (color.length() > 0 && size.length() > 0) {
          break;
        }
      }
    }

    return new BaiduSkuColorSizeAttribute(color, size);
  }
  
  private boolean isItemSizeAttrCode(int pid) {
    for (int code : BaiduCommonTool.ITEM_SIZE_ATTR_CODE) {
      if (pid == code) {
        return true;
      }
    }
    return false;
  }
}


//class SkuColorSizeAttribute {
//
//  private String color;
//
//  private String size;
//
//  public String getColor() {
//    return color;
//  }
//
//  public void setColor(String color) {
//    this.color = color;
//  }
//
//  public String getSize() {
//    return size;
//  }
//
//  public void setSize(String size) {
//    this.size = size;
//  }
//
//  public SkuColorSizeAttribute() {}
//
//  public SkuColorSizeAttribute(String color, String size) {
//    this.color = color;
//    this.size = size;
//  }
//
//  @Override
//  public String toString() {
//    return color + ";" + size;
//  }
//
//
//}
