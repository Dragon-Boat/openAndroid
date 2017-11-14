package com.sundae.zl.openandroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 用于操作Bitmap的工具类
 * Created by gengxin on 2016/7/29.
 */
public class BitmapUtil {

	private static final String LOG_TAG = "BitmapUtil";

	/**
	 * 解大图内存不足时尝试10次, samplesize增大
	 *
	 * @param bytes
	 * @param max   宽或高的最大值, <= 0 , 能解多大解多大, > 0, 最大max, 内存不足解更小
	 * @return
	 */
	public static Bitmap getBitmapFromBytesLimitSize(byte[] bytes, int max) {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}

		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;

		if (max > 0) {
			options.inJustDecodeBounds = true;
			// 获取这个图片的宽和高
			bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
			options.inJustDecodeBounds = false;

			float blW = (float) options.outWidth / max;
			float blH = (float) options.outHeight / max;

			if (blW > 1 || blH > 1) {
				if (blW > blH)
					options.inSampleSize = (int) (blW + 0.9f);
				else
					options.inSampleSize = (int) (blH + 0.9f);
			}
		}
		int i = 0;
		while (i <= 10) {
			i++;
			try {
				bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
				break;
			} catch (OutOfMemoryError e) {
				System.gc();
				//BitmapCache.getInstance().clearMemCache();
				options.inSampleSize++;
				e.printStackTrace();
			}
		}
		return bm;
	}

	/**
	 * 解大图内存不足时尝试10次, samplesize增大
	 *
	 * @param filePath
	 * @param max      宽或高的最大值, <= 0 , 能解多大解多大, > 0, 最大max, 内存不足解更小
	 * @return
	 */
	public static Bitmap getBitmapFromFileLimitSize(String filePath, int max) {
		if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
			return null;
		}

		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;

		if (max > 0) {
			options.inJustDecodeBounds = true;
			// 获取这个图片的宽和高
			bm = BitmapFactory.decodeFile(filePath, options);
			options.inJustDecodeBounds = false;

			float blW = (float) options.outWidth / max;
			float blH = (float) options.outHeight / max;

			if (blW > 1 || blH > 1) {
				if (blW > blH)
					options.inSampleSize = (int) (blW + 0.9f);
				else
					options.inSampleSize = (int) (blH + 0.9f);
			}
		}
		int i = 0;
		while (i <= 10) {
			i++;
			try {
				bm = BitmapFactory.decodeFile(filePath, options);
				break;
			} catch (OutOfMemoryError e) {
				System.gc();
				options.inSampleSize++;
				e.printStackTrace();
			}
		}
		return bm;
	}

	/**
	 * 解大图内存不足时尝试10次, samplesize增大
	 *
	 * @param is
	 * @param max 宽或高的最大值, <= 0 , 能解多大解多大, > 0, 最大max, 内存不足解更小
	 * @return
	 */
	public static Bitmap getBitmapFromFileLimitSize(InputStream is, int max) {
		if (is == null) {
			return null;
		}

		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		Rect outPadding = new Rect(-1, -1, -1, -1);
		if (max > 0) {
			options.inJustDecodeBounds = true;
			// 获取这个图片的宽和高
			bm = BitmapFactory.decodeStream(is, outPadding, options);
			options.inJustDecodeBounds = false;

			float blW = (float) options.outWidth / max;
			float blH = (float) options.outHeight / max;

			if (blW > 1 || blH > 1) {
				if (blW > blH) { options.inSampleSize = (int) (blW + 0.9f); } else { options.inSampleSize = (int) (blH + 0.9f); }
			}
		}
		int i = 0;
		while (i <= 10) {
			i++;
			try {
				bm = BitmapFactory.decodeStream(is, outPadding, options);
				break;
			} catch (OutOfMemoryError e) {
				System.gc();
				options.inSampleSize++;
				e.printStackTrace();
			}
		}
		return bm;
	}

	public static int[] getBitmapSize(String filePath) {
		int[] size = new int[2];
		if (TextUtils.isEmpty(filePath) || !new File(filePath).exists()) {
			return size;
		}

		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;

		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		bm = BitmapFactory.decodeFile(filePath, options);

		int w = options.outWidth;
		int h = options.outHeight;

		size[0] = w;
		size[1] = h;
		return size;
	}

	/**
	 * 保存图片到文件
	 *
	 * @param bmp
	 * @param path
	 * @param format
	 * @return
	 */
	public static boolean saveBmpToFile(Bitmap bmp, String path, Bitmap.CompressFormat format) {
		return saveBmpToFile(bmp, path, format, 100);
	}

	public static boolean saveBmpToFile(Bitmap bmp, String path, Bitmap.CompressFormat format, int quality) {
		if (bmp == null || bmp.isRecycled())
			return false;

		OutputStream stream = null;
		try {
			File file = new File(path);
			File filePath = file.getParentFile();
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			stream = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		boolean result = bmp.compress(format, quality, stream);
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 旋转图片
	 *
	 * @param b
	 * @param degrees
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap b, int degrees) {
		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);

			try {
				Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
				if (b != b2) {
					b.recycle();
					b = b2;
				}
			} catch (OutOfMemoryError ex) {
			}
		}
		return b;
	}

	/**
	 * 缩放图片
	 *
	 * @param b
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap resize(Bitmap b, float width, float height) {
		if (width <= 0 || height <= 0) {
			return b;
		}
		if (b == null) {
			return b;
		}
		int orginalWidth = b.getWidth();
		int orginalHeight = b.getHeight();
		if (orginalWidth == width && orginalHeight == height) {
			return b;
		}

		Matrix m = new Matrix();
		m.postScale(width / orginalWidth, height / orginalHeight);

		try {
			Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
			if (b != b2) {
				b.recycle();
				b = b2;
			}
		} catch (OutOfMemoryError ex) {
		}

		return b;
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图
	 * 此方法有两点好处：
	 * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
	 * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
	 * 用这个工具生成的图像不会被拉伸。
	 *
	 * @param imagePath 图像的路径
	 * @param width     指定输出图像的宽度
	 * @param height    指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		try {
			// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
			bitmap = BitmapFactory.decodeFile(imagePath, options);
			// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		} catch (Exception e) {
			Log.w(LOG_TAG, "fail to getImageThumbnail", e);
		}
		System.gc();
		return bitmap;
	}

	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// 原始图片的宽高
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// 在保证解析出的bitmap宽高分别大于目标尺寸宽高的前提下，取可能的inSampleSize的最大值
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
	public static Bitmap decodeSampledBitmapFromFilePath(String filePath,
	                                                     int reqWidth, int reqHeight) {

		// 首先设置 inJustDecodeBounds=true 来获取图片尺寸
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// 计算 inSampleSize 的值
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// 根据计算出的 inSampleSize 来解码图片生成Bitmap
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap roundCornerBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(roundCornerBitmap);
		int color = 0xff424242;
		Paint paint = new Paint();
		paint.setColor(color);
		// 防止锯齿
		paint.setAntiAlias(true);
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		// 相当于清屏
		canvas.drawARGB(0, 0, 0, 0);
		// 先画了一个带圆角的矩形
		canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 再把原来的bitmap画到现在的bitmap！！！注意这个理解
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return roundCornerBitmap;
	}
	}
