package org.gameye.psp.image.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.entity.SiteBlog;
import org.gameye.psp.image.service.ISiteBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SiteBlogAction extends BaseActionSupport {

	private static final long serialVersionUID = -8564661213049133870L;

	@Autowired
	private ISiteBlogService siteBlogService;

	public String Welcome() {

		if (size < 1)
			size = 10;
		if (page < 1)
			page = 1;

		Map<Integer, List<SiteBlog>> blogMap = siteBlogService.pagedImages(
				page, size);
		if (blogMap != null) {
			for (Integer i : blogMap.keySet()) {
				total = i;
				blogs = blogMap.get(i);
			}
		} else {
			total = 0;
		}

		return SUCCESS;
	}
	
	public String Go2Add(){
		return SUCCESS;
	}
	
	public String Add(){
		blog.setDate(new Date());
		blog.setAuthor(getCurrUser().getNickName());
		
		siteBlogService.addBlog(blog);
		
		return SUCCESS;
	}

	private List<SiteBlog> blogs;
	
	private SiteBlog blog;

	private int total;

	private int page;
	private int size;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<SiteBlog> getBlogs() {
		return blogs;
	}

	public int getTotal() {
		return total;
	}

	public SiteBlog getBlog() {
		return blog;
	}

	public void setBlog(SiteBlog blog) {
		this.blog = blog;
	}
}
