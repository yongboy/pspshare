package org.gameye.psp.image.service;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Suggestion;

public interface ISuggestionService {

	void add(Suggestion suggestion);

	Map<Integer, List<Suggestion>> pagedSuggestions(int page, int size);

}
