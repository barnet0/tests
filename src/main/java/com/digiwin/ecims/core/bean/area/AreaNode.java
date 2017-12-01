package com.digiwin.ecims.core.bean.area;

import com.taobao.api.domain.Area;

import java.util.ArrayList;
import java.util.List;


/**
 * 继承淘宝的Area实体，拓展成多叉树结构
 *
 * @author 维杰
 * @since 2015.7.4
 */
public class AreaNode extends Area {
    /**
     *
     */
    private static final long serialVersionUID = -7723257347269761898L;

    private AreaNode rootNode;
    private List<AreaNode> childs = new ArrayList<AreaNode>();

    public AreaNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(AreaNode rootNode) {
        this.rootNode = rootNode;
    }

    public List<AreaNode> getChilds() {
        return childs;
    }

    public void setChilds(List<AreaNode> childs) {
        this.childs = childs;
    }

    public AreaNode() {
    }

    public void addChild(AreaNode childNode) {
        this.childs.add(childNode);
    }

    public static AreaNode findChild(AreaNode rootNode, AreaNode childNode) {
        return findChild(rootNode, childNode.getName());
    }

    public static AreaNode findChild(AreaNode rootNode, String areaName) {
        AreaNode resultNode = null;
        for (AreaNode areaNode : rootNode.getChilds()) {
            if (areaNode.getName().equals(areaName)) {
                resultNode = areaNode;
                break;
            } else {
                if (areaNode.getChilds().size() > 0) {
                    resultNode = findChild(areaNode, areaName);
                    if (resultNode != null) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return resultNode;
    }

    private static int TWO_WORDS_PROVINCE_LENGTH = 2;
    /**
     * 第一次过滤一些地区名后面的多余字。如果有遇到新的，需要在这里添加新的规则。
     *
     * @param areaKeyWord
     * @return
     */
    private static String processAreaKeyWord(String areaKeyWord) {
        String tempKeyWord = areaKeyWord;
        if (tempKeyWord.endsWith("省")) {
            tempKeyWord = tempKeyWord.replace("省", "");
        }
        else if (tempKeyWord.endsWith("市")) {
            tempKeyWord = tempKeyWord.replace("市", "");
        }else if (tempKeyWord.endsWith("州")) {
            tempKeyWord = tempKeyWord.replace("州", "");
        }else if (tempKeyWord.endsWith("地区内")) {
            tempKeyWord = tempKeyWord.replace("地区内", "");
        }else if (tempKeyWord.endsWith("地区")) {
            tempKeyWord = tempKeyWord.replace("地区", "");
        }else if (tempKeyWord.endsWith("区")) {
            tempKeyWord = tempKeyWord.replace("区", "");
        }else if (tempKeyWord.endsWith("县")) {
            tempKeyWord = tempKeyWord.replace("县", "");
        }
        // 以下情况之外才做处理：山东，广东
        else if (tempKeyWord.endsWith("东") && tempKeyWord.length() > TWO_WORDS_PROVINCE_LENGTH) {
            tempKeyWord = tempKeyWord.replace("东", "");
        }
        // 以下情况之外才做处理：河南，海南，湖南，云南
        else if (tempKeyWord.endsWith("南") && tempKeyWord.length() > TWO_WORDS_PROVINCE_LENGTH) {
            tempKeyWord = tempKeyWord.replace("南", "");
        }
        // 以下情况之外才做处理：山西，江西，陕西，广西
        else if (tempKeyWord.endsWith("西") && tempKeyWord.length() > TWO_WORDS_PROVINCE_LENGTH) {
            tempKeyWord = tempKeyWord.replace("西", "");
        }
        // 以下情况之外才做处理：河北，湖北
        else if (tempKeyWord.endsWith("北") && tempKeyWord.length() > TWO_WORDS_PROVINCE_LENGTH) {
            tempKeyWord = tempKeyWord.replace("北", "");
        }

        return tempKeyWord.trim();
    }

    /**
     * 使用省市区的关键字查询标准省市区（模糊查询）
     *
     * @param rootNode
     * @param areaKeyWord
     * @return
     */
    public static AreaNode findChildByKeyWord(AreaNode rootNode, String areaKeyWord) {
        AreaNode resultNode = null;
        String tempKeyWord = processAreaKeyWord(areaKeyWord);

        for (AreaNode areaNode : rootNode.getChilds()) {
            if (areaNode.getName().contains(tempKeyWord) || tempKeyWord
                .contains(areaNode.getName())) {
                resultNode = areaNode;
                break;
            } else {
                if (areaNode.getChilds().size() > 0) {
                    resultNode = findChildByKeyWord(areaNode, tempKeyWord);
                    if (resultNode != null) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return resultNode;
    }

    public static AreaNode findProvinceNodeByKeyWord(AreaNode rootNode, String provinceKeyWord) {
        AreaNode resultNode = null;
        AreaNode countryNode = findChildByKeyWord(rootNode, "中国");
        String tempKeyWord = processAreaKeyWord(provinceKeyWord);
        for (AreaNode areaNode : countryNode.getChilds()) {
            if (areaNode.getName().contains(tempKeyWord) || tempKeyWord
                .contains(areaNode.getName())) {
                resultNode = areaNode;
                break;
            }
        }
        return resultNode;
    }

    public static AreaNode findCityNodeByKeyWord(AreaNode provinceNode, String cityKeyWord) {
        AreaNode resultNode = null;
        String tempKeyWord = processAreaKeyWord(cityKeyWord);
        for (AreaNode areaNode : provinceNode.getChilds()) {
            if (areaNode.getName().contains(tempKeyWord) || tempKeyWord
                .contains(areaNode.getName())) {
                resultNode = areaNode;
                break;
            }
        }
        return resultNode;
    }

    public static AreaNode findDistrictNodeByKeyWord(AreaNode cityNode, String districtKeyWord) {
        AreaNode resultNode = null;
        String tempKeyWord = processAreaKeyWord(districtKeyWord);
        for (AreaNode areaNode : cityNode.getChilds()) {
            if (areaNode.getName().contains(tempKeyWord) || tempKeyWord
                .contains(areaNode.getName())) {
                resultNode = areaNode;
                break;
            }
        }
        return resultNode;
    }

    public static AreaNode findChild(AreaNode rootNode, Long parentId) {
        AreaNode resultNode = null;
        for (AreaNode areaNode : rootNode.getChilds()) {
            if (areaNode.getId().equals(parentId)) {
                resultNode = areaNode;
                break;
            } else {
                if (areaNode.getChilds().size() > 0) {
                    resultNode = findChild(areaNode, parentId);
                    if (resultNode != null) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        return resultNode;
    }

    public static void printChildsAsTree(AreaNode rootNode, String tab, int level) {
        int i = level;
        if (rootNode.getChilds().size() > 0) {
            for (AreaNode areaNode : rootNode.getChilds()) {
                System.out.print(AreaNode.getTabs(tab, level));
                System.out.println(areaNode.getName());
                if (areaNode.getChilds().size() > 0) {
                    printChildsAsTree(areaNode, tab, i + 1);
                }
            }
        }
    }

    public static void printChildsAsTable(AreaNode rootNode) {
        for (AreaNode areaNode : rootNode.getChilds()) {
            if (areaNode.getChilds().size() > 0) {
                printChildsAsTable(areaNode);
            } else {
                System.out.println(areaNode.toString());
            }
        }
    }

    public static void getAreaList(AreaNode rootNode,
        List<com.digiwin.ecims.core.bean.area.AreaResponse> areaList) {
        if (areaList == null) {
            return;
        }
        String areaName = "";

        for (AreaNode areaNode : rootNode.getChilds()) {
            if (areaNode.getChilds().size() > 0) {
                getAreaList(areaNode, areaList);
            } else {
                areaName = areaNode.toString();
                com.digiwin.ecims.core.bean.area.AreaResponse areaRes =
                    new com.digiwin.ecims.core.bean.area.AreaResponse();
                if (areaName != null && !areaName.trim().isEmpty()) {
                    // 中国
                    if (areaName.indexOf('-') > 0) {
                        String[] areaParts = areaName.split("-");
                        if (areaParts.length == 3) {
                            //							System.out.println(areaName);
                        }
                        areaRes.setCountry(areaParts[0]);
                        areaRes.setProvince(areaParts[1]);
                        areaRes.setCity(areaParts[2]);
                        if (areaParts.length == 4) {
                            areaRes.setDistrict(areaParts[3]);
                        } else {
                            areaRes.setDistrict("市辖区");
                        }
                    } else { // 非中国的国家，一般只有国家
                        areaRes.setCountry(areaName);
                        areaRes.setProvince("");
                        areaRes.setCity("");
                        areaRes.setDistrict("");
                    }
                    areaList.add(areaRes);
                }
            }
        }
    }

    private static String getTabs(String tab, int level) {
        StringBuffer result = new StringBuffer();
        for (int i = 1; i <= level; i++) {
            result.append(tab);
        }
        return result.toString();
    }

    public static long calculateTotalNodesCount(AreaNode rootNode) {
        long totalCount = 0l;
        if (rootNode.getChilds().size() == 0) {
            totalCount = 1l;
        } else {
            for (AreaNode childNode : rootNode.getChilds()) {
                totalCount++;
                if (childNode.getChilds().size() > 0) {
                    totalCount += calculateTotalNodesCount(childNode);
                }
            }
        }

        return totalCount;
    }

    public boolean existChild() {
        return !this.childs.isEmpty();
    }

    @Override public String toString() {
        AreaNode parentNode = AreaNode.findChild(this.getRootNode(), this.getParentId());
        if (parentNode != null && !parentNode.getParentId().equals(Long.valueOf(-1))) {
            return parentNode.toString() + "-" + this.getName();
        } else {
            return this.getName();
        }

        //		return this.getName();
    }

}
