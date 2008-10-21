package org.gameye.psp.image.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.BatchSize;

@Entity
public class SiteBlog implements Serializable {

	private static final long serialVersionUID = -4954330932080436690L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String author;

	@Column(nullable = false)
	private String title;

	@Column(length = 4000, nullable = false)
	private String content;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "blog")
	@BatchSize(size=10)
	@OrderBy("id desc")	
	private Set<BlogReply> replys;

	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<BlogReply> getReplys() {
		return replys;
	}

	public void setReplys(Set<BlogReply> replys) {
		this.replys = replys;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
