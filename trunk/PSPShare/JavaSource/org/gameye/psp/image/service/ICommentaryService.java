package org.gameye.psp.image.service;

import java.util.List;
import java.util.Map;

import org.gameye.psp.image.entity.Commentary;

public interface ICommentaryService {

	void add(Commentary commentary);

	Map<Integer, List<Commentary>> pagedImages(int page, int size,long imageId, String order);
}
