package com.digiwin.ecims.platforms.feiniu.util.translator;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.feiniu.open.api.sdk.bean.base.BeanVo;
import com.feiniu.open.api.sdk.bean.item.ItemInventoryItemVo;
import com.feiniu.open.api.sdk.bean.item.ItemInventoryListVo;
import com.feiniu.open.api.sdk.bean.item.ItemVo;
import com.feiniu.open.api.sdk.response.item.ItemGetResponse;
import com.feiniu.open.api.sdk.response.item.ItemInventoryGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by zaregoto on 2017/1/25.
 */
public class AomsitemTTranslator {
    private static final Logger logger = LoggerFactory.getLogger(AomsitemTTranslator.class);
    private Object listObject;
    private Object detailResponse;

    public AomsitemTTranslator(Object listObject, Object detailResponse) {
        this.listObject = listObject;
        this.detailResponse = detailResponse;
    }

    public List<AomsitemT> doTranslate(String storeId) {
        if (detailResponse instanceof ItemGetResponse) {
            return parseFeiniuItemToAomsitemT(storeId);
        } else {
            return Collections.emptyList();
        }
    }

    private List<AomsitemT> parseFeiniuItemToAomsitemT(String storeId) {
        List<AomsitemT> aomsitemTs = new ArrayList<>();
        ItemInventoryListVo listData = (ItemInventoryListVo) this.listObject;
        ItemGetResponse detailResponse = (ItemGetResponse) this.detailResponse;
        ItemVo detailData = detailResponse.getData().getMallGoods();

        if (listData.getItems() == null) {
            AomsitemT aomsitemT = new AomsitemT();

            aomsitemT.setId(CommonUtil.checkNullOrNot(listData.getGoodsId()));
            aomsitemT.setAoms003(CommonUtil.checkNullOrNot(listData.getTitle()));
            aomsitemT.setAoms004(CommonUtil.checkNullOrNot(listData.getGoodsId()));
            aomsitemT.setAoms005(CommonUtil.checkNullOrNot(listData.getMerchantCode()));
            aomsitemT.setAoms006(CommonUtil.checkNullOrNot(listData.getPropnames()));
            aomsitemT.setAoms007(listData.getStatus() == 3 ? "onsale" : "instock");
            // 提取颜色
            aomsitemT.setAoms008(CommonUtil.checkNullOrNot(listData.getPropnames()));
            // 提取尺寸
            aomsitemT.setAoms009(CommonUtil.checkNullOrNot(listData.getPropnames()));

            aomsitemT.setStoreId(storeId);
            aomsitemT.setStoreType(FeiniuCommonTool.STORE_TYPE);

            aomsitemT.setAoms014(CommonUtil.checkNullOrNot(listData.getPic01()));
            aomsitemT.setAoms015(CommonUtil.checkNullOrNot(detailData.getCreateTime()));
            aomsitemT.setAoms018(CommonUtil.checkNullOrNot(listData.getStock()));
            aomsitemT.setAoms019(CommonUtil.checkNullOrNot(detailData.getUpdateTime()));

            aomsitemT.setAoms024(CommonUtil.checkNullOrNot(listData.getMallPrice()));

            Date now = new Date();
            aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
            aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

            aomsitemTs.add(aomsitemT);

        } else {
            for (ItemInventoryItemVo detailSku: listData.getItems()) {
                AomsitemT aomsitemT = new AomsitemT();

                aomsitemT.setId(CommonUtil.checkNullOrNot(listData.getGoodsId()));
                aomsitemT.setAoms003(CommonUtil.checkNullOrNot(listData.getTitle()));
                aomsitemT.setAoms004(CommonUtil.checkNullOrNot(detailSku.getSkuId()));
                aomsitemT.setAoms005(CommonUtil.checkNullOrNot(detailSku.getBarcode()));
                aomsitemT.setAoms006(CommonUtil.checkNullOrNot(detailSku.getSalerprop()));
                aomsitemT.setAoms007(listData.getStatus() == 3 ? "onsale" : "instock");
                logger.info("goodsId: {},colorprop: {}, salerprop: {}",
                    listData.getGoodsId(), detailSku.getColorprop(), detailSku.getSalerprop());
                aomsitemT.setAoms008(CommonUtil.checkNullOrNot(
                    getColorFromColorProp(detailSku.getColorprop())));
                aomsitemT.setAoms009(CommonUtil.checkNullOrNot(
                    getSizeFromSaleProp(detailSku.getSalerprop())));

                aomsitemT.setStoreId(storeId);
                aomsitemT.setStoreType(FeiniuCommonTool.STORE_TYPE);

                aomsitemT.setAoms014(CommonUtil.checkNullOrNot(detailSku.getPic01()));
                aomsitemT.setAoms015(CommonUtil.checkNullOrNot(detailData.getCreateTime()));
                aomsitemT.setAoms018(CommonUtil.checkNullOrNot(detailSku.getNum()));
                aomsitemT.setAoms019(CommonUtil.checkNullOrNot(detailData.getUpdateTime()));

                //aomsitemT.setAoms024(CommonUtil.checkNullOrNot(listData.getMallPrice()));
                aomsitemT.setAoms024(CommonUtil.checkNullOrNot(detailSku.getPrice()));  //modifybycs at 20170417 

                Date now = new Date();
                aomsitemT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
                aomsitemT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

                aomsitemTs.add(aomsitemT);
            }
        }

        return aomsitemTs;
    }

    private String getColorFromColorProp(String colorProp) {
        if (colorProp.indexOf(FeiniuCommonTool.PROPS_DELIMITER) > -1) {
            String[] colorPropAry = colorProp.split(FeiniuCommonTool.PROPS_DELIMITER);
            if (colorPropAry.length > 1) {
                return colorPropAry[1];
            } else if (colorPropAry.length > 0){
                return colorPropAry[0];
            } else {
                return colorProp;
            }
        } else {
            return colorProp;
        }
    }

    private String getSizeFromSaleProp(String saleProp) {
        if (saleProp.indexOf(FeiniuCommonTool.PROPS_DELIMITER) > -1) {
            String[] sizePropAry = saleProp.split(FeiniuCommonTool.PROPS_DELIMITER);
            if (sizePropAry.length > 1) {
                return sizePropAry[1];
            } else if (sizePropAry.length > 0){
                return sizePropAry[0];
            } else {
                return saleProp;
            }
        } else {
            return saleProp;
        }
    }
}
