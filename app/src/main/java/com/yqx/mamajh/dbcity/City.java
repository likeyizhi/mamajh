package com.yqx.mamajh.dbcity;

import java.util.List;

public class City {
	private int status;
	private String mes;
	private List<CityRes> res;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public List<CityRes> getRes() {
		return res;
	}

	public void setRes(List<CityRes> res) {
		this.res = res;
	}

	public static class CityRes {
		private int id;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
