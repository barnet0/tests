package com.digiwin.ecims.core.service;

import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jd.open.api.sdk.JdException;
import com.taobao.api.ApiException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface CmdService {

    public static final String[] VALID_STORE_TYPES =
        {
            TaobaoCommonTool.STORE_TYPE, TaobaoCommonTool.STORE_TYPE_FX,
            JingdongCommonTool.STORE_TYPE,
            YhdCommonTool.STORE_TYPE,
            IcbcCommonTool.STORE_TYPE,
            SuningCommonTool.STORE_TYPE,
            DangdangCommonTool.STORE_TYPE,
            VipCommonTool.STORE_TYPE,
            //BaiduCommonTool.STORE_TYPE, 改成kaola
            KaolaCommonTool.STORE_TYPE,
            AliexpressCommonTool.STORE_TYPE,
            DhgateCommonTool.STORE_TYPE,
            YougouCommonTool.STORE_TYPE,
            CcbCommonTool.STORE_TYPE,
            //PddCommonTool.STORE_TYPE,改成pdd2
            Pdd2CommonTool.STORE_TYPE,
            BeibeiCommonTool.STORE_TYPE,
            FeiniuCommonTool.STORE_TYPE,
            YunjiCommonTool.STORE_TYPE        //add by xhb 20170630  
        };

    /**
     * 解析指令，執行指令，返回結果
     *
     * @param JSON 指令JSON
     * @return List<CmdRes> 所有cmd執行結果
     * @throws JsonProcessingException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws NumberFormatException
     * @throws JdException
     * @throws ApiException
     * @throws Exception
     */
    List<CmdRes> start(String JSON)
        throws JsonProcessingException, IllegalAccessException, InvocationTargetException,
        IOException, NumberFormatException, ApiException, JdException, Exception;
}
