package com.jiuwu.bean;

public class DuanziBean {
	//图文ID编号/内容详情ID
	private String id;
	//所属栏目ID
	private String classid;
	private String title;
	//评论数
	private String plnum;
	//发布时间
	private String newstime;
	private String newstext;
	private String diggtop;
	private String diggdown;
	private String titlepic;
	private String smalltext;
	private String onclick;
	
	
	public String getClassid() {
		return classid;
	}
	public void setClassid(String classid) {
		this.classid = classid;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getSmalltext() {
		return smalltext;
	}
	public void setSmalltext(String smalltext) {
		this.smalltext = smalltext;
	}
	public String getTitlepic() {
		return titlepic;
	}
	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}
	public String getDiggdown() {
		return diggdown;
	}
	public void setDiggdown(String diggdown) {
		this.diggdown = diggdown;
	}
	public String getDiggtop() {
		return diggtop;
	}
	public void setDiggtop(String diggtop) {
		this.diggtop = diggtop;
	}
	public String getPlnum() {
		return plnum;
	}
	public void setPlnum(String plnum) {
		this.plnum = plnum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNewstime() {
		return newstime;
	}
	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}
	public String getNewstext() {
		return newstext;
	}
	public void setNewstext(String newstext) {
		this.newstext = newstext;
	}
	
}
