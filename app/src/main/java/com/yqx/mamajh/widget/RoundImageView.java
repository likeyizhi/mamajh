package com.yqx.mamajh.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角/圆形的ImageView
 * 
 * @author hl
 * 
 */
public class RoundImageView extends ImageView {

	/**
	 * 圆形类型
	 */
	public static final int TYPE_CIRCLE = 1;

	/**
	 * 圆角类型
	 */
	public static final int TYPE_ROUND = 2;
	/**
	 * 当前类型
	 */
	private int mType = TYPE_CIRCLE;

	private Bitmap b = null;

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化一些自定义的参数
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * 设置类型 (圆形还是圆角) TYPE_CIRCLE,TYPE_ROUND
	 * 
	 * @param type
	 */
	public void setType(int type) {
		mType = type;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		b = convertDrawable2BitmapByCanvas(getDrawable());
		if (b != null) {
			if (mType == TYPE_ROUND) {
				canvas.drawBitmap(createRoundConerImage(b, width, height), 0, 0, null);
			} else {
				canvas.drawBitmap(createCircleImage(b, Math.min(width, height)), 0, 0, null);
			}
		}
	}

	/**
	 * 根据原图和变长绘制圆形图片
	 * 
	 * @param source
	 * @param min
	 * @return
	 */
	private Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		/**
		 * 产生一个同样大小的画布
		 */
		Canvas canvas = new Canvas(target);
		/**
		 * 首先绘制圆形
		 */
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		/**
		 * 使用SRC_IN，参考上面的说明
		 */
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		/**
		 * 绘制图片
		 */
		canvas.drawBitmap(resizeBitmap(source, min, min), 0, 0, paint);
		return target;
	}

	/**
	 * 根据原图添加圆角
	 * 
	 * @param source
	 * @return
	 */
	private Bitmap createRoundConerImage(Bitmap source, int mWidth, int mHeight) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		RectF rect = new RectF(0, 0, mWidth, mHeight);
		canvas.drawRoundRect(rect, 50f, 50f, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		source = resizeBitmap(source, mWidth, mHeight);
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return bitmap;
	}

	/**
	 * Drawable->Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
		if (drawable != null && drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
					drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		}
		return null;
	}
}
