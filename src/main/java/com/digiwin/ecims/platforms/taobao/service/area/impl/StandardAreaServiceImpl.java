package com.digiwin.ecims.platforms.taobao.service.area.impl;

import com.digiwin.ecims.core.bean.area.AreaNode;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.dao.StandardAreaDAO;
import com.digiwin.ecims.core.model.StandardArea;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准省市区信息服务接口实现类（根据淘宝的接口）
 *
 * @author 维杰
 */
@Service public class StandardAreaServiceImpl implements StandardAreaService {

    @Autowired StandardAreaDAO standardDao;

    @Autowired TaobaoApiService taobaoAPISerivce;

    /**
     * ehcache 缓存插件
     */
    @Autowired private EhCacheCacheManager cacheManager;

    /**
     * 调用淘宝API初始化标准省市区信息
     *
     * @throws ApiException
     */
    private void saveAreas() throws ApiException {
        List<Area> taobaoAreas = taobaoAPISerivce.taobaoLogisticsAreasGet();
        List<StandardArea> standardAreas = new ArrayList<StandardArea>();
        for (Area area : taobaoAreas) {
            standardAreas.add(new StandardArea(area));
        }
        standardDao.saveByCollection(standardAreas);
    }

    @Override public AreaNode returnRootNode() throws ApiException {
        List<StandardArea> areas = standardDao.findAll(StandardArea.class);
        // 如果数据库中没有保存，则进行初始化
        if (areas == null || areas.size() == 0) {
            saveAreas();
            areas = standardDao.findAll(StandardArea.class);
        }
        // 树的根节点
        AreaNode rootNode = parseStandardAreaToAreaNode(areas);
        return rootNode;
    }

    private AreaNode parseStandardAreaToAreaNode(List<StandardArea> areaList) {
        return initRootNode(areaList);
    }

    private AreaNode initRootNode(List<StandardArea> areas) {
        AreaNode root = new AreaNode();
        root.setParentId(new Long(-1));

        // 添加国家
        for (StandardArea area : areas) {
            // if (area.getType() == 1 && area.getName().equals("中国")) {
            // // mark by mowj 20150718 需要返回所有国家
            if (area.getType() == 1) {
                AreaNode newNode = new AreaNode();
                newNode.setRootNode(root);
                newNode.setId(area.getId());
                newNode.setName(area.getName());
                newNode.setParentId(area.getParentId());
                // newNode.setType(area.getType());
                newNode.setZip(area.getZip());

                root.addChild(newNode);
                // break; // mark by mowj 20150718 需要返回所有国家
            }
        }

        // 添加省
        for (StandardArea area : areas) {
            // if (area.getType() == 2 && !area.getName().equals("海外"))
            // { // mark by mowj 20150718 需要返回所有国家
            if (area.getType() == 2) {
                AreaNode newNode = new AreaNode();
                newNode.setRootNode(root);
                newNode.setId(area.getId());
                newNode.setName(area.getName());
                newNode.setParentId(area.getParentId());
                // newNode.setType(area.getType());
                newNode.setZip(area.getZip());

                AreaNode parentNode = AreaNode.findChild(root, area.getParentId());
                if (parentNode != null) {
                    parentNode.addChild(newNode);
                }
            }
        }

        // 添加市
        for (StandardArea area : areas) {
            if (area.getType() == 3) {
                AreaNode newNode = new AreaNode();
                newNode.setRootNode(root);
                newNode.setId(area.getId());
                newNode.setName(area.getName());
                newNode.setParentId(area.getParentId());
                // newNode.setType(area.getType());
                newNode.setZip(area.getZip());

                AreaNode parentNode = AreaNode.findChild(root, area.getParentId());
                if (parentNode != null) {
                    parentNode.addChild(newNode);
                }
            }
        }

        // 添加区/县
        for (StandardArea area : areas) {
            if (area.getType() == 4) {
                AreaNode newNode = new AreaNode();
                newNode.setRootNode(root);
                newNode.setId(area.getId());
                newNode.setName(area.getName());
                newNode.setParentId(area.getParentId());
                // newNode.setType(area.getType());
                newNode.setZip(area.getZip());

                AreaNode parentNode = AreaNode.findChild(root, area.getParentId());
                if (parentNode != null) {
                    parentNode.addChild(newNode);
                }
            }
        }
        // AreaNode.printChildsAsTree(root, "├─", 1);
        return root;
    }

    @Override public boolean saveAreaToCache() {
        Cache paramCache = cacheManager.getCache("areacache");
        try {
            if (paramCache != null) {
                paramCache.clear();
            }
            AreaNode rootNode = this.returnRootNode();
            paramCache.put("AreaRoot", rootNode);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override public AreaResponse getStandardAreaNameByKeyWord(String province, String city,
        String district) {
        Cache areaCache = cacheManager.getCache("areacache");
        if (areaCache.get("AreaRoot") != null) {
            AreaNode rootNode = (AreaNode) areaCache.get("AreaRoot").get();
            String standardProvince = "";
            String standardCity = "";
            String standardDistrict = "";
            AreaNode provinceNode = null;
            AreaNode cityNode = null;
            AreaNode districtNode = null;
            // 先查找省/自治区/直辖市
            provinceNode =
                AreaNode.findProvinceNodeByKeyWord(rootNode, province == null ? "" : province);
            // 再根据省/自治区/直辖市查找其下的地区(省下面的地级市)
            cityNode = AreaNode.findCityNodeByKeyWord(provinceNode, city == null ? "" : city);
            // 如果没有在省下找到city，则有可能city的内容为“xx省直辖”，district是省直辖县，直接从省节点下查找区
            if (cityNode == null) {
                districtNode = AreaNode.findChildByKeyWord(provinceNode, district);
                // 如果有找到，证明是省直辖县
                if (districtNode != null) {
                    // 襄樊是襄阳和樊城合并后的新名字，以前襄阳和樊城虽然挨着，但是是不同的市县，解放后直接合并了，就改名襄樊市了，2010年底改回襄阳市
                    // 一号店与当当还是使用的襄樊市，所以需要特别处理
                    if (city.contains("襄樊") && district.contains("襄阳")) {
                        cityNode = new AreaNode();
                        cityNode.setName("襄樊市");
                        districtNode = new AreaNode();
                        districtNode.setName("襄州区");
                    } else {
                        // 其它情况时
                        // AreaNode cityOnDistrictNode =
                        // AreaNode.findChild(provinceNode,
                        // districtNode.getParentId());
                        // if (cityOnDistrictNode != null) {
                        // cityNode = cityOnDistrictNode;
                        // } else {
                        // cityNode = new AreaNode();
                        // cityNode.setName(districtNode.getName());
                        // }
                        // cityNode = new AreaNode();
                        // cityNode.setName(districtNode.getName());
                        // districtNode = new AreaNode();
                        // districtNode.setName("市辖区");

                        // 寻找districtNode的父节点
                        AreaNode tempParentNode =
                            AreaNode.findChild(rootNode, districtNode.getParentId());
                        if (tempParentNode != null) {
                            // 如果这个districtNode的父节点是省，证明它是县级市
                            if (tempParentNode.getName().equals(provinceNode.getName())) {
                                // 直接将这个districtNode放到市的level
                                // 并且为它赋一个“市辖区”
                                cityNode = new AreaNode();
                                cityNode.setName(districtNode.getName());
                                districtNode = new AreaNode();
                                districtNode.setName("市辖区");
                            } else {
                                // 否则将这个districtNode的父节点放在city层级
                                cityNode = tempParentNode;
                            }
                        } else {
                            cityNode = new AreaNode();
                            cityNode.setName("直辖县");
                        }

                    }
                } else {
                    // 否则没有找到
                    cityNode = new AreaNode();
                    cityNode.setName("其它市");
                    districtNode = new AreaNode();
                    districtNode.setName("其它区");
                }
            } else {
                // 最后根据地区(省下面的地级市)查找其下的县/市(县级市)/区
                if (!cityNode.existChild()) {
                    districtNode = new AreaNode();
                    districtNode.setName("市辖区");
                } else {
                    districtNode = AreaNode
                        .findDistrictNodeByKeyWord(cityNode, district == null ? "" : district);
                    if (districtNode == null) {
                        districtNode = new AreaNode();
                        districtNode.setName("其它区");
                    }
                }
            }

            standardProvince = provinceNode.getName().trim();
            standardCity = cityNode.getName().trim();
            standardDistrict = districtNode.getName().trim();

            return new AreaResponse("", standardProvince, standardCity, standardDistrict);

        } else {
            return null;
        }
    }

    /**
     * 直辖市枚举
     *
     * @author 维杰
     */
    public enum MunicipalityEnum {
        Shanghai("上海"), Beijing("北京"), Tianjing("天津"), Chongqing("重庆");

        private final String municipality;

        MunicipalityEnum(String municipality) {
            this.municipality = municipality;
        }

        public String getMunicipality() {
            return this.municipality;
        }

        /**
         * 判断一个省是不是直辖市
         *
         * @param provinceName
         * @return true表示是直辖市.false表示不是直辖市
         * @author 维杰
         * @since 2015.07.025
         */
        public static boolean isProvinceInMunicipality(String provinceName) {
            if (!isProvinceBeijing(provinceName) && !isProvinceChongqing(provinceName)
                && !isProvinceShanghai(provinceName) && !isProvinceTianjing(provinceName)) {
                return false;
            } else {
                return true;
            }
        }

        private static boolean isProvinceShanghai(String provinceName) {
            if (provinceName.contains(Shanghai.municipality) || Shanghai.municipality
                .contains(provinceName)) {
                return true;
            } else {
                return false;
            }
        }

        private static boolean isProvinceBeijing(String provinceName) {
            if (provinceName.contains(Beijing.municipality) || Beijing.municipality
                .contains(provinceName)) {
                return true;
            } else {
                return false;
            }
        }

        private static boolean isProvinceTianjing(String provinceName) {
            if (provinceName.contains(Tianjing.municipality) || Tianjing.municipality
                .contains(provinceName)) {
                return true;
            } else {
                return false;
            }
        }

        private static boolean isProvinceChongqing(String provinceName) {
            if (provinceName.contains(Chongqing.municipality) || Chongqing.municipality
                .contains(provinceName)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
