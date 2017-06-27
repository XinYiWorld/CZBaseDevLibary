package com.xinyi.czbasedevtool.base.utils.download;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.File;

/**
 * 用于封装每个下载任务相关的数据
 * @author Administrator
 *
 */
@Table(name = "downloadtaskinfo" )
public class DownloadTaskInfo {
	@Column(name = "id",isId = true,autoGen = false)
	private int id;//下载任务的唯一标识
	@Column(name = "baseUrl")
	private String baseUrl;
	@Column(name = "downloadUrl")
	private String downloadUrl;//下载的url后缀
	@Column(name = "state")
	private int state;//当前任务的下载状态
	@Column(name = "currentLength")
	private long currentLength;//当前已经下载的长度
	@Column(name = "size")
	private long size;//总长度
	@Column(name = "path")
	private String path;//文件的保存的绝对路径
	
	/**
	 * 根据传入的AppInfo生成对应的DownloadInfo
	 * @param downloadTargetInfo
	 * @return
	 */
	public static DownloadTaskInfo create(DownloadTargetInfo downloadTargetInfo){
		DownloadTaskInfo downloadInfo = new DownloadTaskInfo();
		downloadInfo.setId(downloadTargetInfo.getId());//设置id
		downloadInfo.setDownloadUrl(downloadTargetInfo.getDownloadUrl());
		downloadInfo.setSize(downloadTargetInfo.getSize());
		downloadInfo.setState(DownloadManager.STATE_NONE);//未下载的状态
		downloadInfo.setCurrentLength(0);
		downloadInfo.setBaseUrl(DownloadManager.getBASE_URL());
		// /mnt/sdcard/包名/download/文件名（有后缀）
		downloadInfo.setPath(DownloadManager.DOWNLOAD_DIR + File.separator
				+downloadTargetInfo.getName());
		
		return downloadInfo;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCurrentLength() {
		return currentLength;
	}
	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
