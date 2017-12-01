package com.digiwin.ecims.platforms.jingdong.service.api;

import java.io.IOException;
import java.util.List;

import com.jd.open.api.sdk.JdException;

public interface JingdongApiHelperService {

	/**
	 * 取得不同狀態清單
	 * @param status 各類狀態(分 "WAIT_BUYER_RETURN_GOODS" || "WAIT_SELLER_CONFIRM_GOODS")
	 * @return 回傳查詢後清單
	 */
	public List<Object[]> getStatusReturnGoods(String status);
	
	/**
	 * 等待买家发货 => 等待卖家收货 (全數更新)。调用京东接口：jingdong.afsservice.freightmessage.get 获取发运信息
	 * @param shop 單筆的商店對應物件 AomsshopT 物件
	 * @param objArr list 內的單筆物件
	 * @return if 成功 count += 1 else 失敗 count = 0
	 * @throws NumberFormatException
	 * @throws JdException
	 * @throws IOException
	 */
	public int updateStatusFromBuyerReturnGoods2SellerConfirmGoods(List<Object[]> dataList) throws NumberFormatException, JdException, IOException;
	
	/**
	 * 等待卖家收货 ==> 退款成功 (全數更新)。调用京东接口：jingdong.afsservice.refundinfo.get 获取退款信息
	 * @param shop 單筆的商店對應物件 AomsshopT 物件
	 * @param objArr list 內的單筆物件
	 * @return if 成功 count += 1 else 失敗 count = 0
	 * @throws NumberFormatException
	 * @throws JdException
	 * @throws IOException
	 */
	public int updateStatusFromSellerConfirmGoods2Success(List<Object[]> dataList) throws NumberFormatException, JdException, IOException;
}
