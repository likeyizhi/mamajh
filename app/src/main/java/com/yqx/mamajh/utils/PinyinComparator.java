package com.yqx.mamajh.utils;

import java.util.Comparator;

import com.yqx.mamajh.bean.LocationCity;

public class PinyinComparator implements Comparator<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> {

	public int compare(LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList o1, LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList o2) {
		if (o1.getHead().equals("@")
				|| o2.getHead().equals("#")) {
			return -1;
		} else if (o1.getHead().equals("#")
				|| o2.getHead().equals("@")) {
			return 1;
		} else {
			return o1.getHead().compareTo(o2.getHead());
		}
	}

}
