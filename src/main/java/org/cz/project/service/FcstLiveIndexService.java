package org.cz.project.service;

import org.cz.project.entity.table.FcstLiveIndex;

public interface FcstLiveIndexService {
	FcstLiveIndex getLastestFcstLiveIndexByType(String type);
}
