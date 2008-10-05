package org.gameye.psp.image.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Class Image
 */
@Entity(name = "Images")
public class Image implements Serializable {

	//
	// Fields
	//

	/**
	 * 主键
	 */
	@Id
	private String id;

	private String title;
	/**
	 * 上传后所保存的文件名
	 */
	private String nowName;
	/**
	 * 老的文件名
	 */
	private String oldName;
	/**
	 * 文件类型，对应于Content-Type
	 */
	private String contentType;
	/**
	 * 文件后缀
	 */
	private String postfix;
	/**
	 * 描述
	 */
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private String ip;
	/**
	 * 日期
	 */
	private Date date;

	/**
	 * 得分
	 */
	private int score;
	/**
	 * 下载
	 */
	private int down;
	/**
	 * 收藏
	 */
	private int collect;

	@ManyToOne(fetch = FetchType.LAZY)
	private Type type;

	@OneToMany(mappedBy = "image", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Tag> tags;

	//
	// Constructors
	//
	public Image() {
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

	public String getNowName() {
		return nowName;
	}

	public void setNowName(String nowName) {
		this.nowName = nowName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPostfix() {
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public int getCollect() {
		return collect;
	}

	public void setCollect(int collect) {
		this.collect = collect;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
