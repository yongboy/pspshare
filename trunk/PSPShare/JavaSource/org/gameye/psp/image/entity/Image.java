package org.gameye.psp.image.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Class Image
 */
@Entity(name = "Images")
public class Image implements Serializable {

	private static final long serialVersionUID = 3618623092837073406L;

	//
	// Fields
	//

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

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
	 * 文件长度
	 */
	@Column(nullable = false)
	private long length;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 文件的相对路径 计算规则为 年/月/日/用户ID 这里为了避免计算，耦合一下 eg : 2008/06/12/12/
	 */
	@Column(nullable = false, updatable = false)
	private String path;

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
	
//	@JoinTable(name = "Image_Tag_Reffer", 
//	joinColumns = { @JoinColumn(name = "image_id", referencedColumnName = "id") },
//	inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id")
//	}
//)
	
	@ManyToMany(cascade = { CascadeType.PERSIST,CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "Image_Tag_Reffer", 
		joinColumns = { @JoinColumn(name = "image_id") },
		inverseJoinColumns = { @JoinColumn(name = "tag_id")
		}
	)	
	private List<Tag> tags;

	//
	// Constructors
	//
	public Image() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public int getScore() {
		return score;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
}
