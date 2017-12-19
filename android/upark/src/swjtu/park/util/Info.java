package swjtu.park.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import swjtu.park.R;

public class Info implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;
	private int imgId;
	private String name;
	private String distance;
	private int zan;
	
	public static List<Info> infos = 
			new ArrayList<Info>();
	
	//����һ�����ݲ�����
	static{
		infos.add(new Info(34.252625,109.971171,R.drawable.a01,"ҹ��1","����200��",1450));
		infos.add(new Info(34.242625,109.991171,R.drawable.a02,"ҹ��2","����500��",450));
		infos.add(new Info(34.252625,109.991171,R.drawable.a03,"ҹ��3","����400��",150));
		infos.add(new Info(34.242625,109.971171,R.drawable.a04,"ҹ��4","����300��",145));
	}
	
	public Info(double latitude, double longitude, int imgId, String name,
			String distance, int zan) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}
	
	
}
