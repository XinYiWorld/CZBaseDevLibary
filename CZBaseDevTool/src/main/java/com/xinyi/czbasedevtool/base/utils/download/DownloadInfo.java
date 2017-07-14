package com.xinyi.czbasedevtool.base.utils.download;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 用于封装每个下载任务相关的数据
 * @author Administrator
 */
@Table(name = "downloadInfo" )
public class DownloadInfo {
	//一期的字段
	@Column(name = "id",isId = true,autoGen = false)
	private int id;//下载任务的唯一标识
	@Column(name = "baseUrl")
	private String baseUrl;
	@Column(name = "des")
	private String des;//文件的描述信息
	@Column(name = "downloadUrl")
	private String downloadUrl;  //文件的下载地址的后缀
	@Column(name = "iconUrl")
	private String iconUrl;//文件图标的url地址的后缀
	@Column(name = "name")
	private String name;//文件的名称
	@Column(name = "state")
	private int state;//当前任务的下载状态
	@Column(name = "currentLength")
	private long currentLength;//当前已经下载的长度
	@Column(name = "size")
	private long size;//总长度
	@Column(name = "path")
	private String path;//文件的保存的绝对路径


	public DownloadInfo() {
	}

	public DownloadInfo(String downloadUrl, String des, String name, long size) {
		this.downloadUrl = downloadUrl;
		this.des = des;
		this.name = name;
		this.size = size;
		this.currentLength = 0;
		this.state = DownloadManager.STATE_NONE;//未下载的状态
		this.baseUrl = DownloadManager.getBASE_URL();
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public long getCurrentLength() {
		return currentLength;
	}

	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
