package com.example.clientversion_3.entity;

public class ProjectInfo {
	private int projectId;
	private int progressNum;
	private int reachNum;
	private int supportNum;
	private int remainTime;
	private int attentionNum;
	private int discussNum;
	private int sharedNum;
	private String promoterName;
	private String promoterId;
	private String[] descriptions;
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getPromoterName() {
		return promoterName;
	}
	public void setPromoterName(String promoterName) {
		this.promoterName = promoterName;
	}
	public String getPromoterId() {
		return promoterId;
	}
	public void setPromoterId(String promoterId) {
		this.promoterId = promoterId;
	}
	public String[] getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String[] descriptions) {
		this.descriptions = descriptions;
	}
	public int getProgressNum() {
		return progressNum;
	}
	public void setProgressNum(int progressNum) {
		this.progressNum = progressNum;
	}
	public int getReachNum() {
		return reachNum;
	}
	public void setReachNum(int reachNum) {
		this.reachNum = reachNum;
	}
	public int getSupportNum() {
		return supportNum;
	}
	public void setSupportNum(int supportNum) {
		this.supportNum = supportNum;
	}
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	public int getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(int attentionNum) {
		this.attentionNum = attentionNum;
	}
	public int getDiscussNum() {
		return discussNum;
	}
	public void setDiscussNum(int discussNum) {
		this.discussNum = discussNum;
	}
	public int getSharedNum() {
		return sharedNum;
	}
	public void setSharedNum(int sharedNum) {
		this.sharedNum = sharedNum;
	}
}
