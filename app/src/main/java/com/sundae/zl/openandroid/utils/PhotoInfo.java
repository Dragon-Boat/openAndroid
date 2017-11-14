package com.sundae.zl.openandroid.utils;

import android.text.TextUtils;

import java.io.Serializable;
/**
 * Created by @author hzzhoulong
 * on 2017-10-16.
 * # Copyright 2017 netease. All rights reserved.
 */

public class PhotoInfo implements Serializable {
	public int photoId;

	public String photoPath;

//	public int width;
//
//	public int height;

	public long timestamp;

	public boolean imported;

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof PhotoInfo)) {
			return false;
		}
		PhotoInfo info = (PhotoInfo) o;
		return TextUtils.equals(info.photoPath, photoPath);
	}

	@Override
	public String toString() {
		return "PhotoInfo{" +
				"photoId=" + photoId +
				", photoPath='" + photoPath + '\'' +
				", timestamp=" + timestamp +
				", imported=" + imported +
				'}';
	}
}
