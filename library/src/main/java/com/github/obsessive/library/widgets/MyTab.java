package com.github.obsessive.library.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.obsessive.library.R;
import com.github.obsessive.library.exception.CustomException;
import com.github.obsessive.library.utils.DensityUtils;
import com.github.obsessive.library.utils.TLog;

import java.util.ArrayList;
import java.util.List;


public class MyTab extends LinearLayout implements View.OnClickListener{

	private int[] mDrawableIds = new int[] {R.drawable.bg_home, R.drawable.bg_classify, R.drawable.bg_find, R.drawable.bg_cart, R.drawable.bg_person};
	// 底部菜单的文字数组
	private CharSequence[] mLabels;
	// 回调接口，用于获取tab的选中状态
	private OnTabSelectedListener mTabListener;
	private CheckedTextView checkedTextView_home;
	private CheckedTextView checkedTextView_dev;
	private CheckedTextView checkedTextView_auditions;
	private CheckedTextView checkedTextViewe_person;
	//中间的上传按钮
	private RelativeLayout itemCam;
	// 存放指示点
	private List<ImageView> mIndicateImgs = new ArrayList<ImageView>();
	public static ImageView imageView;

	@Override
	public void onClick(View view) {
		changeColor((CheckedTextView) view);
	}

	public interface OnTabSelectedListener {
		void onTabSelected(int index);
	}

	public MyTab(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public void setOnTabSelectedListener(OnTabSelectedListener listener) {
		this.mTabListener = listener;
	}

	public MyTab(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabWidget, defStyle, 0);

		// 读取xml中，各个tab使用的文字
		mLabels = a.getTextArray(R.styleable.TabWidget_bottom_labels);

		if (null == mLabels || mLabels.length <= 0) {
			try {
				throw new CustomException("底部菜单的文字数组未添加...");
			} catch (CustomException e) {
				e.printStackTrace();
			} finally {
				TLog.i(MyTab.class.getSimpleName() + " 出错","");
			}
			a.recycle();
			return;
		}

		a.recycle();
		// 初始化控件
		init(context);
	}

	private void init( Context context) {
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundResource(R.drawable.bottom_white);
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, DensityUtils.dip2px(context, 48));
		params.gravity = Gravity.CENTER_VERTICAL;

		LayoutInflater inflater = LayoutInflater.from(context);

		View view = inflater.inflate(R.layout.mytab_item, null);
		imageView = (ImageView) view.findViewById(R.id.item_cam);
		itemCam = (RelativeLayout) view.findViewById(R.id.rl_item_cam);

		checkedTextView_home = (CheckedTextView) view.findViewById(R.id.item_name_home);
		checkedTextView_home.setCompoundDrawablesWithIntrinsicBounds( null,context
				.getResources().getDrawable(mDrawableIds[0]), null, null);
//		checkedTextView_home.setCompoundDrawablePadding(DensityUtils.dip2px(context, 2));
		checkedTextView_home.setText(mLabels[0]);
		checkedTextView_home.setTag(0);

		checkedTextView_dev = (CheckedTextView) view.findViewById(R.id.item_name_dev);
		checkedTextView_dev.setCompoundDrawablesWithIntrinsicBounds( null, context
				.getResources().getDrawable(mDrawableIds[1]), null, null);
//		checkedTextView_dev.setCompoundDrawablePadding(DensityUtils.dip2px(context, 2));
		checkedTextView_dev.setText(mLabels[1]);
		checkedTextView_dev.setTag(1);

		checkedTextView_auditions = (CheckedTextView) view.findViewById(R.id.item_name_auditions);
		checkedTextView_auditions.setCompoundDrawablesWithIntrinsicBounds( null,context
				.getResources().getDrawable(mDrawableIds[3]), null, null);
//		checkedTextView_auditions.setCompoundDrawablePadding(DensityUtils.dip2px(context, 2));
		checkedTextView_auditions.setText(mLabels[2]);
		checkedTextView_auditions.setTag(2);

		checkedTextViewe_person = (CheckedTextView) view.findViewById(R.id.item_name_person);
		checkedTextViewe_person.setCompoundDrawablesWithIntrinsicBounds( null,context
				.getResources().getDrawable(mDrawableIds[4]), null, null);
//		checkedTextViewe_person.setCompoundDrawablePadding(DensityUtils.dip2px(context, 2));
		checkedTextViewe_person.setText(mLabels[3]);
		checkedTextViewe_person.setTag(3);

		ImageView indicate_dev = (ImageView) view.findViewById(R.id.indicate_img_dev);
		ImageView indicate_auditions = (ImageView) view.findViewById(R.id.indicate_img_auditions);
		mIndicateImgs.add(indicate_dev);
		mIndicateImgs.add(indicate_auditions);

		checkedTextView_home.setOnClickListener(this);
		checkedTextView_dev.setOnClickListener(this);
		checkedTextView_auditions.setOnClickListener(this);
		checkedTextViewe_person.setOnClickListener(this);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ( mTabListener != null ){
					mTabListener.onTabSelected(4);
				}
			}
		});
		
		this.addView(view,params);
	}

	private void changeColor(CheckedTextView checkedTextView){
		checkedTextView_home.setChecked(false);
		checkedTextView_home.setTextColor(Color.rgb(51, 51, 51));
		checkedTextView_dev.setChecked(false);
		checkedTextView_dev.setTextColor(Color.rgb(51, 51, 51));
		checkedTextView_auditions.setChecked(false);
		checkedTextView_auditions.setTextColor(Color.rgb(51, 51, 51));
		checkedTextViewe_person.setChecked(false);
		checkedTextViewe_person.setTextColor(Color.rgb(51, 51, 51));
		checkedTextView.setChecked(true);
		checkedTextView.setTextColor(Color.rgb(0, 153, 255));
		if(mTabListener!=null){
			mTabListener.onTabSelected((Integer) checkedTextView.getTag());
		}
	}

	/**
	 * 设置指示点的显示
	 *
	 * @param position
	 *            显示位置
	 * @param visible
	 *            是否显示，如果false，则都不显示
	 */
	public void setIndicateDisplay(int position,
								   boolean visible) {
		int size = mIndicateImgs.size();
		if (size <= position) {
			return;
		}
		ImageView indicateImg = mIndicateImgs.get(position);
		indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	/**
	 * 切换tab标签
	 * @param index
	 */
	public void setClickItem(int index){
		if(index > mLabels.length){
			try {
				throw new CustomException("索引超出底部菜单的数量...");
			} catch (CustomException e) {
				e.printStackTrace();
			} finally {
				TLog.i( MyTab.class.getSimpleName() + " 出错","");
			}
		}
		switch (index) {
		case 0:
			checkedTextView_home.performClick();
			break;
		case 1:
			checkedTextView_dev.performClick();
			break;
		case 3:
			checkedTextView_auditions.performClick();
			break;
		case 4:
			checkedTextViewe_person.performClick();
			break;
		}
	}

	public void camItemDisplay (int visibility){
		itemCam.setVisibility(visibility);
	}

	public void changeHomeTabColor(){
		checkedTextView_dev.setChecked(false);
		checkedTextView_dev.setTextColor(Color.rgb(51, 51, 51));
		checkedTextView_auditions.setChecked(false);
		checkedTextView_auditions.setTextColor(Color.rgb(51, 51, 51));
		checkedTextViewe_person.setChecked(false);
		checkedTextViewe_person.setTextColor(Color.rgb(51, 51, 51));
		checkedTextView_home.setChecked(true);
		checkedTextView_home.setTextColor(Color.rgb(0, 153, 255));
	}
}
