package org.cz.project.entity.bean;

public class Awards {
	private int index;
	private String res;//奖品名称
	private String color;//奖品转盘北京色
	private String img;//图片
	private boolean no_aw;//没有奖品标识
	private double ran;//概率 0..1
	private String tag="0";
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean isNo_aw() {
		return no_aw;
	}
	public void setNo_aw(boolean no_aw) {
		this.no_aw = no_aw;
	}
	public double getRan() {
		return ran;
	}
	public void setRan(double ran) {
		this.ran = ran;
	}
	
	
}
