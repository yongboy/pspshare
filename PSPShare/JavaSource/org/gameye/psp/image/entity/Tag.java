package org.gameye.psp.image.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Class Tags 图片的有关标签
 */
@Entity(name = "Image_Tags")
public class Tag implements Serializable {

	private static final long serialVersionUID = 8114851108962687308L;
	//
	// Fields
	//

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	private Date date;

	@ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
	private Set<Image> images;

	//
	// Constructors
	//
	public Tag() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}
}
