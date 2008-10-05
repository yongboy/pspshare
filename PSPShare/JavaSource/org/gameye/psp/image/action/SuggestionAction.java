package org.gameye.psp.image.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gameye.psp.image.action.base.BaseActionSupport;
import org.gameye.psp.image.entity.Suggestion;
import org.gameye.psp.image.service.ISuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SuggestionAction extends BaseActionSupport {

	private static final long serialVersionUID = 4588119546517900939L;
	@Autowired
	private ISuggestionService suggestionService;

	@Override
	public String execute() {
		if (size < 1)
			size = 10;
		if (page < 1)
			page = 1;

		Map<Integer, List<Suggestion>> suggMap = suggestionService
				.pagedSuggestions(page, size);
		if (suggMap == null || suggMap.keySet().size() == 0) {
			total = 0;
			return SUCCESS;
		}

		for (Integer n : suggMap.keySet()) {
			total = n;
			suggestions = suggMap.get(n);
		}

		return SUCCESS;
	}

	public String Add() {
		suggestion.setIp(getServletRequest().getRemoteAddr());
		suggestion.setDate(new Date());
		suggestionService.add(suggestion);

		return SUCCESS;
	}

	private int page;
	private int size;
	private int total;

	private List<Suggestion> suggestions;

	private Suggestion suggestion;

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

	public int getTotal() {
		return total;
	}

	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	public Suggestion getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
	}

}
