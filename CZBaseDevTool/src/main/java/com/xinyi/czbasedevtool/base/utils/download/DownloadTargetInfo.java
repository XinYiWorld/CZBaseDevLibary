package com.xinyi.czbasedevtool.base.utils.download;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "downloadtargetinfo" )
public class DownloadTargetInfo {
	//一期的字段
	@Column(name = "id",isId = true,autoGen = false)
	private int id;//文件的唯一标识
	@Column(name = "des")
	private String des;//文件的描述信息
	@Column(name = "downloadUrl")
	private String downloadUrl;  //文件的下载地址的后缀
	@Column(name = "iconUrl")
	private String iconUrl;//文件图标的url地址的后缀
	@Column(name = "name")
	private String name;//文件的名称
	@Column(name = "size")
	private long size;//文件的体积大小

	public DownloadTargetInfo() {
//		 id = Integer.parseInt(UUID.randomUUID().toString().replaceAll("-", ""));
	}

	public DownloadTargetInfo(String downloadUrl, String des, String name, long size) {
		this();
		this.downloadUrl = downloadUrl;
		this.des = des;
		this.name = name;
		this.size = size;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}

}
