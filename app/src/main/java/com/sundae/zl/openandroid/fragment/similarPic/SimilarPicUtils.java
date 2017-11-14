package com.sundae.zl.openandroid.fragment.similarPic;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.util.Log;
/**
 * Created by @author hzzhoulong
 * on 2017-10-14.
 * # Copyright 2017 netease. All rights reserved.
 */

public final class SimilarPicUtils {
	public static final String TAG = "SimilarPicUtils";

	public static int similarDetect(Bitmap source, Bitmap sample, int width,int height) {
		Bitmap sourceResize = ThumbnailUtils.extractThumbnail(source, width, height);
		Bitmap sampleResize = ThumbnailUtils.extractThumbnail(sample, width, height);

		Bitmap sourceGrey = convertToGrey(sourceResize);
		Bitmap sampleGrey = convertToGrey(sampleResize);

		int sourceAvg = getAvg(sourceGrey);
		int sampleAvg = getAvg(sampleGrey);

		String sourceHash = binaryString2hexString(getBinary(sourceGrey, sourceAvg));
		String sampleHash = binaryString2hexString(getBinary(sampleGrey, sampleAvg));
		return diff(sourceHash,sampleHash);

	}
	public static Bitmap convertToGrey(Bitmap img) {
		int width = img.getWidth();         //获取位图的宽
		int height = img.getHeight();       //获取位图的高

		int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int original = pixels[width * i + j];
				int red = ((original & 0x00FF0000) >> 16);
				int green = ((original & 0x0000FF00) >> 8);
				int blue = (original & 0x000000FF);

				int grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}

	public static int getAvg(Bitmap img) {
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = new int[width * height];
		img.getPixels(pixels, 0, width, 0, 0, width, height);

		int avgPixel = 0;
		for (int pixel : pixels) {
			avgPixel += pixel;
		}
		return avgPixel / pixels.length;
	}

	public static String getBinary(Bitmap img, int average) {
		StringBuilder sb = new StringBuilder();

		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = new int[width * height];

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int original = pixels[width * i + j];
				if (original >= average) {
					pixels[width * i + j] = 1;
				} else {
					pixels[width * i + j] = 0;
				}
				sb.append(pixels[width * i + j]);
			}
		}
		return sb.toString();
	}

	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuilder sb = new StringBuilder();
		int iTmp;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			sb.append(Integer.toHexString(iTmp));
		}
		return sb.toString();
	}

	public static int diff(String s1, String s2) {
		char[] s1s = s1.toCharArray();
		char[] s2s = s2.toCharArray();
		int diffNum = 0;
		for (int i = 0; i<s1s.length; i++) {
			if (s1s[i] != s2s[i]) {
				diffNum++;
			}
		}
		Log.d(TAG, "diffNum="+diffNum);
		return diffNum;
	}
}
