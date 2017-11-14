package com.sundae.zl.openandroid.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by @author hzzhoulong
 * on 2017-10-16.
 * # Copyright 2017 netease. All rights reserved.
 */

public class PhotoUtils {
	private static final String LOG_TAG = "wechat.PhotoUtils";

	/**
	 * 获取所有图片
	 *
	 * @param context
	 * @return
	 */
	public static List<PhotoFolderInfo> getAllPhotoFolder(Context context, List<PhotoInfo> selectPhotoMap) {
		List<PhotoFolderInfo> allFolderList = new ArrayList<>();
		final String[] projectionPhotos = {
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_ID,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DATE_TAKEN,
				MediaStore.Images.Media.ORIENTATION,
				MediaStore.Images.Thumbnails.DATA
		};
		final ArrayList<PhotoFolderInfo> allPhotoFolderList = new ArrayList<>();
		HashMap<Integer, PhotoFolderInfo> bucketMap = new HashMap<>();
		Cursor cursor = null;
		//所有图片
		PhotoFolderInfo allPhotoFolderInfo = new PhotoFolderInfo();
		allPhotoFolderInfo.folderId = 0;
		allPhotoFolderInfo.folderName = "所有照片";
		allPhotoFolderInfo.photoList = (new ArrayList<>());
		allPhotoFolderList.add(0, allPhotoFolderInfo);
		List<String> selectedList = new ArrayList<>();
		List<String> filterList = new ArrayList<>();
		try {
			cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
					, projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
			if (cursor != null) {
				int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
				final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
				while (cursor.moveToNext()) {
					int bucketId = cursor.getInt(bucketIdColumn);
					String bucketName = cursor.getString(bucketNameColumn);
					final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
					final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
					//int thumbImageColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
					final int imageId = cursor.getInt(imageIdColumn);
					final String path = cursor.getString(dataColumn);
					//final String thumb = cursor.getString(thumbImageColumn);
					File file = new File(path);
					if ((filterList == null || !filterList.contains(path)) && file.exists() && file.length() > 0) {
						final PhotoInfo photoInfo = new PhotoInfo();
						photoInfo.photoId = (imageId);
						photoInfo.photoPath = (path);
						//添加到所有图片
						allPhotoFolderInfo.photoList.add(photoInfo);

						//通过bucketId获取文件夹
						PhotoFolderInfo photoFolderInfo = bucketMap.get(bucketId);

						if (photoFolderInfo == null) {
							photoFolderInfo = new PhotoFolderInfo();
							photoFolderInfo.photoList = (new ArrayList<>());
							photoFolderInfo.folderId = (bucketId);
							photoFolderInfo.folderName = (bucketName);
							bucketMap.put(bucketId, photoFolderInfo);
							allPhotoFolderList.add(photoFolderInfo);
						}
						photoFolderInfo.photoList.add(photoInfo);

						if (selectedList != null && selectedList.size() > 0 && selectedList.contains(path)) {
							selectPhotoMap.add(photoInfo);
						}
					}
				}
			}
		} catch (Exception ex) {
			Log.w(LOG_TAG, "getAllPhotoFolder", ex);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		allFolderList.addAll(allPhotoFolderList);
//		if (selectedList != null) {
//			selectedList.clear();
//		}
		return allFolderList;
	}

	public static List<PhotoInfo> getScreenShotFiles(Context context, List<PhotoInfo> importedPhotoMap, int maxCounts) {

		List<PhotoInfo> screenShotPhotoList = new ArrayList<>(maxCounts);
		final String[] projectionPhotos = {
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DATE_TAKEN,
		};
		Cursor cursor = null;
		try {
			cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
					, projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
			if (cursor != null) {
				int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
				while (cursor.moveToNext()) {
					String bucketName = cursor.getString(bucketNameColumn);
					Log.d(LOG_TAG, "getScreenShotFiles: bucketName:" + bucketName);
					if (bucketName.equals("Screenshots")) {
						final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
						final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
						final int timestampColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);

						final int imageId = cursor.getInt(imageIdColumn);
						final String path = cursor.getString(dataColumn);
						final long timestamp = cursor.getLong(timestampColumn);

						File file = new File(path);
						if (file.exists() && file.length() > 0 && screenShotPhotoList.size() <= maxCounts) {
							final PhotoInfo photoInfo = new PhotoInfo();
							photoInfo.photoId = imageId;
							photoInfo.photoPath = path;
							photoInfo.timestamp = timestamp;
							if (importedPhotoMap != null && importedPhotoMap.contains(photoInfo)) {
								photoInfo.imported = true;
							} else {
								photoInfo.imported = false;
							}
							screenShotPhotoList.add(photoInfo);
						}
					}
				}

			}
		} catch (Exception ignored) {
			Log.w(LOG_TAG, "getAllPhotoFolder", ignored);

		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return screenShotPhotoList;
	}

}
