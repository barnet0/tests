package com.digiwin.ecims.platforms.feiniu.util.translator;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.feiniu.open.api.sdk.bean.order.OrderItemVo;
import com.feiniu.open.api.sdk.bean.order.OrderVo;
import com.feiniu.open.api.sdk.response.order.TradeSoldGetOrderDetailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * Created by zaregoto on 2017/1/25.
 */
public class AomsordTTranslator {
    private static final Logger logger = LoggerFactory.getLogger(AomsordTTranslator.class);
    private Object object;
    private StandardAreaService standardAreaService =
        MySpringContext.getContext().getBean(StandardAreaService.class);
    public AomsordTTranslator() {
    }
    public AomsordTTranslator(Object object) {
        super();
        this.object = object;
    }
    public List<AomsordT> doTranslate(String storeId) {
        if (object instanceof TradeSoldGetOrderDetailResponse) {
            return parseFeiniuOrderToAomsordT(
                ((TradeSoldGetOrderDetailResponse) object).getData()
                    .getPageVoList().get(0),
                storeId);
        } else if (object instanceof OrderVo) {
            return parseFeiniuOrderToAomsordT(((OrderVo)object), storeId);
        } else {
            return Collections.emptyList();
        }
    }
    private List<AomsordT> parseFeiniuOrderToAomsordT(OrderVo orderVo, String storeId) {
        List<AomsordT> aomsordTs = new ArrayList<>();
        Integer itemQty = 0;
        Float aprnPoints = 0f;
        for (OrderItemVo orderItemVo : orderVo.getItemList()) {
            AomsordT aomsordT = new AomsordT();
            aomsordT.setId(CommonUtil.checkNullOrNot(orderVo.getOgNo()));
            aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderVo.getStatus()));
            aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderVo.getInsDt()));
            aomsordT.setModified(CommonUtil.checkNullOrNot(orderVo.getUpdateDt()));
            aomsordT.setAoms009(CommonUtil.checkNullOrNot(orderVo.getMemMsg()));
            aomsordT.setAoms012(CommonUtil.checkNullOrNot(orderVo.getMerchantRemark()));
            aomsordT.setAoms016(CommonUtil.checkNullOrNot(orderVo.getVondorName()));
            aomsordT.setAoms020(CommonUtil.checkNullOrNot(orderVo.getVoucherTotalPrice()));//modbycs 2017/3/27
            /*aomsordT.setAoms020(CommonUtil.checkNullOrNot(
                orderVo.getDiscountPrice() +
                    orderVo.getVoucherTotalPrice() +
                    orderVo.getAprnVvipTotal()));*/
            aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderVo.getPayablePrice()));
            aomsordT.setAoms023(CommonUtil.checkNullOrNot(orderVo.getPayType()));
            aomsordT.setAoms024(CommonUtil.checkNullOrNot(orderVo.getPayDt()));
            aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderVo.getMemberName()));
            aomsordT.setAoms026(CommonUtil.checkNullOrNot(orderVo.getMemberEmail()));
            aomsordT.setAoms029(CommonUtil.checkNullOrNot(orderVo.getScore()));
            aomsordT.setAoms034(CommonUtil.checkNullOrNot(orderVo.getExpressName()));
            aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderVo.getFreight()));
            aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderVo.getMemberName()));
            logger.info("订单号：{}, 省：{}，市：{}，区：{}",
                orderVo.getOgNo(),
                orderVo.getMemberProvince(),
                orderVo.getMemberCity(),
                orderVo.getMemberPostArea());
            AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(
                orderVo.getMemberProvince(),
                orderVo.getMemberCity(),
                orderVo.getMemberPostArea());
            if (standardArea != null) {
                String standardProvince = standardArea.getProvince();
                String standardCity = standardArea.getCity();
                String standardDistrict = standardArea.getDistrict();
                aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));
                aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));
                aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));
            } else {
                aomsordT.setAoms037(CommonUtil.checkNullOrNot(orderVo.getMemberProvince()));
                aomsordT.setAoms038(CommonUtil.checkNullOrNot(orderVo.getMemberCity()));
                aomsordT.setAoms039(CommonUtil.checkNullOrNot(orderVo.getMemberPostArea()));
            }
            aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderVo.getMemberAdd()));
            aomsordT.setAoms041(CommonUtil.checkNullOrNot(orderVo.getMemberPostCode()));
            aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderVo.getMemberCellphone()));
            aomsordT.setAoms043(CommonUtil.checkNullOrNot(orderVo.getMemberTel()));
            if (orderVo.getNeedInvoice().equals(1)) {
                aomsordT.setAoms053(CommonUtil.checkNullOrNot(orderVo.getInvoiceName()));
                aomsordT.setAoms054(CommonUtil.checkNullOrNot(orderVo.getInvoiceContext()));
                aomsordT.setAoms055(CommonUtil.checkNullOrNot(orderVo.getInvoiceType()));
            }
            aomsordT.setStoreId(storeId);
            aomsordT.setStoreType(FeiniuCommonTool.STORE_TYPE);
            //aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderItemVo.getOlSeq()));  modbycs 20170328
            aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderVo.getPackNo()));
            aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderItemVo.getGoodsId()));
            if (StringTool.isEmpty(orderItemVo.getSmSeq())) {
                aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderItemVo.getGoodsId()));
            } else {
                aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderItemVo.getSmSeq()));
            }
            aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderItemVo.getSalerprop() + "|"
             + orderItemVo.getColor()));
            itemQty += orderItemVo.getQty();
            aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderItemVo.getQty()));
            aomsordT.setAoms063(CommonUtil.checkNullOrNot(orderItemVo.getName()));
            aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderItemVo.getPrice()));
            aomsordT.setAoms065(CommonUtil.checkNullOrNot(orderItemVo.getPicUrl()));
            aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderItemVo.getBarcode()));
            aomsordT.setAoms068(CommonUtil.checkNullOrNot(
                orderItemVo.getPrice() * orderItemVo.getQty()));
            aomsordT.setAoms069(CommonUtil.checkNullOrNot(
                (orderItemVo.getPrice() - orderItemVo.getUnitPrice()) * orderItemVo.getQty()));
            aomsordT.setAoms071(CommonUtil.checkNullOrNot(
                orderItemVo.getUnitPrice() * orderItemVo.getQty()));
            
            aomsordT.setAoms078(CommonUtil.checkNullOrNot(orderVo.getPlatformVoucherPrice()));
            
            /*aomsordT.setAoms078(CommonUtil.checkNullOrNot(
                orderVo.getPlatformVoucherPrice() + orderVo.getAprnPointsPriceTotal()
                    + orderVo.getAprnCashCardTotal()));*/
            aomsordT.setAoms090(CommonUtil.checkNullOrNot(orderItemVo.getUnitPrice() *
                orderItemVo.getQty()));
            aomsordT.setAoms091(CommonUtil.checkNullOrNot(orderItemVo.getAprnPromote() +
                orderItemVo.getAprnVoucher() + orderItemVo.getAprnVvip()));
            
            aprnPoints += orderItemVo.getAprnPoints();
            aomsordT.setAoms094(CommonUtil.checkNullOrNot(
                orderItemVo.getAprnCashCard() + orderItemVo.getAprnVvipPoints() +
                    orderItemVo.getPlatformAprnVoucher() + orderItemVo.getAprnPoints()
            ));
            Date now = new Date();
            aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
            aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));
            aomsordTs.add(aomsordT);
        }
        for (AomsordT aomsordT : aomsordTs) {
            aomsordT.setAoms017(CommonUtil.checkNullOrNot(itemQty));
            aomsordT.setAoms030(CommonUtil.checkNullOrNot(aprnPoints/100));   //modbycs 2017 0318
        }
        return aomsordTs;
    }
}