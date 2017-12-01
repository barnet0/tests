package com.digiwin.ecims.core.service.impl;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.request.CmdReqHelper;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.service.CmdService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiService;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiService;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.platforms.ccb.service.api.CcbApiService;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.platforms.dangdang.service.DangdangApiService;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.platforms.vip.service.VipApiService;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jd.open.api.sdk.JdException;
import com.taobao.api.ApiException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 执行指令
 */
@Service public class CmdServiceImpl implements CmdService {

    @Autowired private TaobaoApiService taobaoApiService;

    @Autowired private JingdongApiService jingdongApiService;

    @Autowired private YhdApiService yhdApiService;

    @Autowired private SuningApiService suningApiService;

    @Autowired private IcbcApiService icbcApiService;

    @Autowired private DangdangApiService dangdangApiService;

    @Autowired private VipApiService vipApiService;

   // @Autowired private BaiduApiService baiduApiService;  by 2017/06/06 cjp 

    @Autowired private AliexpressApiService aliexpressApiService;

    @Autowired private DhgateApiService dhgateApiService;

    @Autowired private YougouApiService yougouApiService;

    @Autowired private CcbApiService ccbApiService;

    @Autowired private PddApiService pddApiService;
    
    @Autowired private Pdd2ApiService pdd2ApiService;  // by 2017/06/22

    @Autowired private BeibeiApiService beibeiApiService;

    @Autowired private FeiniuApiService feiniuApiService;
    
    @Autowired private KaolaApiService kaolaApiService;  //by 2017/06/06 cjp 
    
    @Autowired private YunjiApiService yunjiApiService;  //by 2017/06/30 xhb 

    @Override public List<CmdRes> start(String JSON)
        throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
        IOException, NumberFormatException, ApiException, JdException, Exception {
        List<CmdRes> cmdResList = null;

        CmdReq[] cmdReqArr = null;
        // 解析指令JSON
        // cmdReqArr = new ObjectMapper().readValue(JSON, CmdReq[].class);
        // System.out.println("json ==>" + JSON);
        // 改用這方法解析 json, params 內容不受型態限制, 可自行加上新的型態（需調整 CmdReq, CmdReqHelper ）
        cmdReqArr = CmdReqHelper.getInstance().doParse(JSON);

        if (cmdReqArr == null) {
            return null;
        }

        // 針對每個指令..
        cmdResList = new ArrayList<CmdRes>();
        for (CmdReq cmdReq : cmdReqArr) {
            CmdRes cmdRes = null;
            // 驗證指令格式
            try {
                CmdReqHelper.getInstance().validateCmdReq(cmdReq);

                String ecno = cmdReq.getEcno();
                if (ecno.equals(TaobaoCommonTool.STORE_TYPE) || ecno
                    .equals(TaobaoCommonTool.STORE_TYPE_FX)) {
                    cmdRes = taobaoApiService.execute(cmdReq);
                } else if (ecno.equals(JingdongCommonTool.STORE_TYPE)) {
                    cmdRes = jingdongApiService.execute(cmdReq);
                } else if (ecno.equals(YhdCommonTool.STORE_TYPE)) {
                    cmdRes = yhdApiService.execute(cmdReq);
                } else if (ecno.equals(IcbcCommonTool.STORE_TYPE)) {
                    cmdRes = icbcApiService.execute(cmdReq);
                } else if (ecno.equals(SuningCommonTool.STORE_TYPE)) {
                    cmdRes = suningApiService.execute(cmdReq);
                } else if (ecno.equals(DangdangCommonTool.STORE_TYPE)) {
                    cmdRes = dangdangApiService.execute(cmdReq);
                } else if (ecno.equals(VipCommonTool.STORE_TYPE)) {
                    cmdRes = vipApiService.execute(cmdReq);
                } /*else if (ecno.equals(BaiduCommonTool.STORE_TYPE)) { //百度已经不用，改成网易考拉  //2017/06/6 by cjp 
                    cmdRes = baiduApiService.execute(cmdReq);
                }*/
                else if (ecno.equals(KaolaCommonTool.STORE_TYPE)) { //百度已经不用，改成网易考拉     //2017/06/06 by cjp
                cmdRes = kaolaApiService.execute(cmdReq);
                }else if (ecno.equals(AliexpressCommonTool.STORE_TYPE)) {
                    cmdRes = aliexpressApiService.execute(cmdReq);
                } else if (ecno.equals(DhgateCommonTool.STORE_TYPE)) {
                    cmdRes = dhgateApiService.execute(cmdReq);
                } else if (ecno.equals(YougouCommonTool.STORE_TYPE)) {
                    cmdRes = yougouApiService.execute(cmdReq);
                } else if (ecno.equals(CcbCommonTool.STORE_TYPE)) {
                    cmdRes = ccbApiService.execute(cmdReq);
                } //else if (ecno.equals(PddCommonTool.STORE_TYPE)) {
                    //cmdRes = pddApiService.execute(cmdReq);
                else if (ecno.equals(Pdd2CommonTool.STORE_TYPE)){
                	cmdRes = pdd2ApiService.execute(cmdReq); //by cjp 2017/06/22
                } else if (ecno.equals(BeibeiCommonTool.STORE_TYPE)) {
                    cmdRes = beibeiApiService.execute(cmdReq);
                } else if (ecno.equals(FeiniuCommonTool.STORE_TYPE)) {
                    cmdRes = feiniuApiService.execute(cmdReq);
                } else if (ecno.equals(YunjiCommonTool.STORE_TYPE)) {       // add by xhb 20170630
                	cmdRes = yunjiApiService.execute(cmdReq);				
				}

            } catch (NumberFormatException e) {
                cmdRes =
                    new CmdRes(cmdReq, false, CommonUtil.responseErrorJson("_040"), null); // add by
                // mowj
                // 20150828
                e.printStackTrace();
            } catch (NullPointerException | IllegalArgumentException | ClientProtocolException e) {
                cmdRes =
                    new CmdRes(cmdReq, false, CommonUtil.responseErrorJson(e.getMessage()), null);
                e.printStackTrace();
            } catch (IOException | ApiException | JdException e) {
                cmdRes = new CmdRes(cmdReq, false,
                    CommonUtil.noDefinitionErrorResponse("999", e.getMessage()), null);
                e.printStackTrace();
            } catch (Exception e) {
                cmdRes = new CmdRes(cmdReq, false,
                    CommonUtil.noDefinitionErrorResponse("999", e.getMessage()), null);
                e.printStackTrace();
            }
            cmdResList.add(cmdRes);
        }

        return cmdResList;
    }

}
