package org.gameye.psp.image.service;

import org.gameye.psp.image.entity.LastPlace;
import org.gameye.psp.image.entity.User;

public interface ILastPlaceService {

	void save(LastPlace lastPlace);

	LastPlace getLastTimePlace(User user);
}
