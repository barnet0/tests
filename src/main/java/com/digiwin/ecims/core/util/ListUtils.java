package com.digiwin.ecims.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 2009-12-02
 * @author liyoulong 
 *
 */
public class ListUtils {
	
	/**
	 * 传入一个list 得到一个它的随机排列的list 
	 * @param oldList 原始的list
	 * @param i 要得到的list的大小，如果i大于原始的list的size，或者小于等于0，在返回原始list长度的新list
	 * @return
	 */
	public static List randomList (List oldList,int i) {
		if (oldList == null || oldList.size() == 0){
			return null;
		}
		int listSize = oldList.size();
		int whileVale;
		if (i <= 0 || i > listSize){
			whileVale = listSize;
		} else {
			whileVale = i;
		}
		List newList = new ArrayList();
		Random r = new Random();
		while (whileVale > 0) {
			int a = r.nextInt(listSize);
			int t = 0;
			if (newList.size() != 0)
				t = r.nextInt(newList.size());
			newList.add(t,oldList.get(a));
			oldList.remove(a);				
			whileVale --;
			listSize -- ;
		}
		return newList;
	}

}
