package com.digiwin.ecims.platforms.taobao.util;

import java.util.HashMap;
import java.util.Map;

import com.taobao.api.domain.Item;
import com.taobao.api.domain.Sku;

/**
 * @author 维杰
 *
 */
public class SkuPropertiesSplitter {
	public final static String COLOR = "color";
	public final static String SIZE = "size";
	public final static String PROPERTIES_NAME = "properties_name";
	
	/**
	 * 淘宝商品颜色SKU代码
	 */
	private final static String TB_ITEM_COLOR_CATEGORY_SKU_CODE = "1627207";
	private final static String TB_ITEM_BED_SIZE_CATEGORY_SKU_CODE = "5569827";
	private final static String TB_ITEM_SIZE_CATEGORY_SKU_CODE = "21433";
	private final static String TB_ITEM_PROPERTY_DELIMITER = ";";
	private final static String TB_ITEM_PROPERTY_DETAIL_DELIMITER = ":";
	private final static int TB_ITEM_PROPERTY_DETAIL_LENGTH = 4;
	private final static int TB_ITEM_PROPERTY_ALIAS_DETAIL_LENGTH = 3;

	/**
	 * 切分淘宝商品的SKU尺寸与颜色
	 * eg.5569827:55481264:适用床尺寸:1.8m（6英尺）床;1627207:28326:颜色分类:红色
	 * 2015.09.01 更新：如果淘宝有property_alias,则sku中的尺寸或颜色都要以property_alias中设定的为准，且需要按照property_alias中的设定来给原本的property_names。
	 * eg. "property_alias": "1627207:107121:实物拍摄;5569827:10122:1.8m（6英尺）床",
	 * 因为这个是消费者在淘宝前台看到的结果
	 * @return SKU尺寸与颜色的Map
	 */
	public static Map<String, String> getTbItemColorAndSizeMap(
			Item taobaoItem, Sku taobaoSku) {
		String tbItemSkuPropertiesAlias = taobaoItem.getPropertyAlias();
		String tbItemSkuPropertiesName = taobaoSku.getPropertiesName();
		
		if (tbItemSkuPropertiesName == null || tbItemSkuPropertiesName.trim().length() == 0) {
			return null;
		} else {
			// 
			String[] tbItemSkuPropertyAliasArray = null;
			if (tbItemSkuPropertiesAlias != null && tbItemSkuPropertiesAlias.trim().length() > 0) {
				tbItemSkuPropertyAliasArray = tbItemSkuPropertiesAlias.split(TB_ITEM_PROPERTY_DELIMITER);
			}
			String newPropertiesName = "";
			
			// 按分号区分不同的属性类型字串
			String[] tbItemSkuProperties = tbItemSkuPropertiesName
					.split(TB_ITEM_PROPERTY_DELIMITER);
			// 结果Map
			Map<String, String> resultMap = new HashMap<String, String>();
			// 不同属性字串间循环
			for (String skuProperty : tbItemSkuProperties) {
				// 如果属性字串中没有冒号分隔
				if (skuProperty.indexOf(TB_ITEM_PROPERTY_DETAIL_DELIMITER) <= 0) {
					return null;
				} else {
					// 分隔出属性字串的详细内容
					String[] tbItemSkuPropertiesDetails = skuProperty
							.split(TB_ITEM_PROPERTY_DETAIL_DELIMITER);
					// 如果分隔出的长度不正确
					if (tbItemSkuPropertiesDetails.length != TB_ITEM_PROPERTY_DETAIL_LENGTH) {
						return null;
					} else {
						// sku的属性内容的值
						// eg.1627207:28326:颜色分类:红色.此处skuDesc="红色"
						String skuDesc = tbItemSkuPropertiesDetails[tbItemSkuPropertiesDetails.length - 1];
						
						switch (tbItemSkuPropertiesDetails[0]) {
						case TB_ITEM_COLOR_CATEGORY_SKU_CODE:
							// 如果没有sku属性值别名，放sku内原有的sku属性
							if (tbItemSkuPropertyAliasArray == null) {
								resultMap.put(COLOR, skuDesc);
							} else {
								for (String propertyAlias : tbItemSkuPropertyAliasArray) {
									String[] propertyAliasDetail = propertyAlias.split(TB_ITEM_PROPERTY_DETAIL_DELIMITER);
									if (propertyAliasDetail.length != TB_ITEM_PROPERTY_ALIAS_DETAIL_LENGTH) {
										break;
									} else {
										// 只有原来sku属性类别编号和sku属性编号，都与sku属性值别名中都相同，
										// 才需要替换
										if (propertyAliasDetail[0].equals(TB_ITEM_COLOR_CATEGORY_SKU_CODE)
												&& tbItemSkuPropertiesDetails[1].equals(propertyAliasDetail[1])) {
											resultMap.put(COLOR, propertyAliasDetail[2]);
											break;
										} else {
											// 不然放原来的
											resultMap.put(COLOR, skuDesc);
										}
									}
								}
							}
							break;
						case TB_ITEM_BED_SIZE_CATEGORY_SKU_CODE:
							if (tbItemSkuPropertyAliasArray == null) {
								resultMap.put(SIZE, skuDesc);
							} else {
								for (String propertyAlias : tbItemSkuPropertyAliasArray) {
									String[] propertyAliasDetail = propertyAlias.split(TB_ITEM_PROPERTY_DETAIL_DELIMITER);
									if (propertyAliasDetail.length != TB_ITEM_PROPERTY_ALIAS_DETAIL_LENGTH) {
										break;
									} else {
										if (propertyAliasDetail[0].equals(TB_ITEM_BED_SIZE_CATEGORY_SKU_CODE)
												&& tbItemSkuPropertiesDetails[1].equals(propertyAliasDetail[1])) {
											resultMap.put(SIZE, propertyAliasDetail[2]);
											break;
										} else {
											resultMap.put(SIZE, skuDesc);
										}
									}
								}
							}
							break;
						case TB_ITEM_SIZE_CATEGORY_SKU_CODE:
							if (tbItemSkuPropertyAliasArray == null) {
								resultMap.put(SIZE, skuDesc);
							} else {
								for (String propertyAlias : tbItemSkuPropertyAliasArray) {
									String[] propertyAliasDetail = propertyAlias.split(TB_ITEM_PROPERTY_DETAIL_DELIMITER);
									if (propertyAliasDetail.length != TB_ITEM_PROPERTY_ALIAS_DETAIL_LENGTH) {
										break;
									} else {
										if (propertyAliasDetail[0].equals(TB_ITEM_SIZE_CATEGORY_SKU_CODE)
												&& tbItemSkuPropertiesDetails[1].equals(propertyAliasDetail[1])) {
											resultMap.put(SIZE, propertyAliasDetail[2]);
											break;
										} else {
											resultMap.put(SIZE, skuDesc);
										}
									}
								}
							}
							break;
						default:
							break;
						}
						
						if (tbItemSkuPropertyAliasArray != null) {
							// 根据property_alias重新组成属性字串
							boolean isPropertyAliaed = false;
							for (String propertyAlias : tbItemSkuPropertyAliasArray) {
								String[] propertyAliasDetails = propertyAlias.split(TB_ITEM_PROPERTY_DETAIL_DELIMITER);
								if (tbItemSkuPropertiesDetails[0].equals(propertyAliasDetails[0])
										&& tbItemSkuPropertiesDetails[1].equals(propertyAliasDetails[1])) {
									newPropertiesName += 
											tbItemSkuPropertiesDetails[0] + TB_ITEM_PROPERTY_DETAIL_DELIMITER + 
											tbItemSkuPropertiesDetails[1] + TB_ITEM_PROPERTY_DETAIL_DELIMITER + 
											tbItemSkuPropertiesDetails[2] + TB_ITEM_PROPERTY_DETAIL_DELIMITER +
											propertyAliasDetails[2] + TB_ITEM_PROPERTY_DELIMITER;
									isPropertyAliaed = true;
									break;
								}
							}
							// 如果没有在自定义属性字串中找到，则添加当前的sku属性字串
							if (!isPropertyAliaed) {
								newPropertiesName += skuProperty + TB_ITEM_PROPERTY_DELIMITER;
							}
						} else {
							newPropertiesName += skuProperty + TB_ITEM_PROPERTY_DELIMITER;
						}
					}
				}
			}
			if (!newPropertiesName.equals("")) {
				newPropertiesName = newPropertiesName.substring(0, newPropertiesName.length() - 1);
			}
			resultMap.put(SkuPropertiesSplitter.PROPERTIES_NAME, newPropertiesName);
			
			return resultMap;
		}

	}

	private final static String FX_ITEM_COLOR_CATEGORY_SKU_CODE = "颜色";
	private final static String FX_ITEM_SIZE_CATEGORY_SKU_CODE = "尺寸";
	private final static String FX_ITEM_PROPERTY_DELIMITER = "，";
	private final static String FX_ITEM_PROPERTY_DETAIL_DELIMITER = "：";
	private final static int FX_ITEM_PROPERTY_DETAIL_LENGTH = 2;

	// 适用床尺寸：1.8m（6英尺）床，颜色分类：天蓝色(有可能只有尺寸或颜色）
	public static Map<String, String> getFxItemColorAndSizeMap(String fxItemPropertiesName) {
		if (fxItemPropertiesName == null || fxItemPropertiesName.trim().length() == 0) {
			return null;
		} else {
			String[] fxItemProperties = fxItemPropertiesName.split(FX_ITEM_PROPERTY_DELIMITER);
			Map<String, String> resultMap = new HashMap<String, String>();
			for (String fxItemProperty : fxItemProperties) {
				if (fxItemProperty.indexOf(FX_ITEM_PROPERTY_DETAIL_DELIMITER) <= 0) {
					return null;
				} else {
					String[] fxItemPropertiesDetails = fxItemProperty.split(FX_ITEM_PROPERTY_DETAIL_DELIMITER);
					if (fxItemPropertiesDetails.length != FX_ITEM_PROPERTY_DETAIL_LENGTH) {
						return null;
					} else {
						if (fxItemPropertiesDetails[0].contains(FX_ITEM_COLOR_CATEGORY_SKU_CODE)) {
							resultMap.put(COLOR, fxItemPropertiesDetails[1]);
						} else if (fxItemPropertiesDetails[0].contains(FX_ITEM_SIZE_CATEGORY_SKU_CODE)) {
							resultMap.put(SIZE, fxItemPropertiesDetails[1]);
						} else {
							
						}
					}
				}
			}
			
			return resultMap;
		}
	}

}
