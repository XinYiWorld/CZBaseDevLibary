package com.xinyi.czbasedevtool.base.utils.download;

import java.io.File;

/**
 * 用于封装每个下载任务相关的数据
 * @author Administrator
 *
 */
public class DownloadTaskInfo {
	private int id;//下载任务的唯一标识
	private String baseUrl;
	private String downloadUrl;//下载的url后缀
	private int state;//当前任务的下载状态
	private long currentLength;//当前已经下载的长度
	private long size;//总长度
	private String path;//apk文件的保存的绝对路径
	
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
		
		// /mnt/sdcard/包名/download/有缘网.apk
		downloadInfo.setPath(DownloadManager.DOWNLOAD_DIR + File.separator
				+downloadTargetInfo.getName()+".apk" );
		
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
