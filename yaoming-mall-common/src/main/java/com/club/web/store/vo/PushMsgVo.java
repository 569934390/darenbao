package com.club.web.store.vo;

/**
 * 消息推送对象
 * 
 * @author wqh
 * @add by 2016-05-09
 */
public class PushMsgVo {
	/**
	 * 设备号
	 */
	private String deviceId;
	/**
	 * 推送内容
	 */
	private String content;
	/**
	 * 推送标题
	 */
	private String title;
	/**
	 * 推送类型
	 */
	private String pushType;
	/**
	 * 消息类型
	 */
	private String msgType;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 流水号
	 */
	private String customerOrder;
	/**
	 * 推送渠道
	 */
	private String channel;
	/**
	 * 设置通知是否响铃
	 */
	private boolean ring = true;
	/**
	 * 设置通知是否震动
	 */
	private boolean vibrate = true;
	/**
	 * 设置通知是否可清除
	 */
	private boolean clearable = true;
	/**
	 * 设置打开的网址地址
	 */
	private String url;
	/**
	 * 配置通知栏图标
	 */
	private String logo;
	/**
	 * 配置通知栏网络图标，填写图标URL地址
	 */
	private String logoUrl;
	/**
	 * 消息离线是否存储
	 */
	private boolean offline = true;
	/**
	 * 离线有效时间，单位为毫秒
	 */
	private long offlineExpireTime;
	/**
	 * 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
	 */
	private int transmissionType = 2;
	/**
	 * 消息Id
	 * 
	 */
	private String msgId;
	private String appKey;
	private String secretKey;
	private String sign;
	private long userId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPushType() {
		return pushType;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(String customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public boolean isRing() {
		return ring;
	}

	public void setRing(boolean ring) {
		this.ring = ring;
	}

	public boolean isVibrate() {
		return vibrate;
	}

	public void setVibrate(boolean vibrate) {
		this.vibrate = vibrate;
	}

	public boolean isClearable() {
		return clearable;
	}

	public void setClearable(boolean clearable) {
		this.clearable = clearable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public long getOfflineExpireTime() {
		return offlineExpireTime;
	}

	public void setOfflineExpireTime(long offlineExpireTime) {
		this.offlineExpireTime = offlineExpireTime;
	}

	public int getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(int transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
