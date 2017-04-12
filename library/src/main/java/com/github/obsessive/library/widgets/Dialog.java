package com.github.obsessive.library.widgets;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.obsessive.library.R;


/**
 * 就是自定义的Dialog,不可back或点击外部销毁
 *
 */
public class Dialog {
	public final static int SELECT_DIALOG=1;
	public final static int RADIO_DIALOG=2;

	public static android.app.Dialog showSingleConfirm(Context context, String toast, final DialogConfirmClickListener dialogConfirmClickListener){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_singleconfirm, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.toast)).setText(toast);
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogConfirmClickListener.confirm(null);
					}
				},200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){  //横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}

	/**
	 * 创建一个内容多选对话框
	 * @param context
	 * @param title	标题
	 * @param items 数组
	 * @param dialogItemClickListener 监听点击的内容结果
	 * @return
	 */
	public static android.app.Dialog showListDialog(Context context,String title,String[] items,final DialogItemClickListener dialogItemClickListener){
		return ShowDialog(context,title,items,dialogItemClickListener);
	}
	public static android.app.Dialog showListViewDialog(Context context,String title,String[] items,final DialogItemClickListener dialogItemClickListener){
		return ShowListViewDialog(context, title, items, dialogItemClickListener);
	}
	/**
	 * 创建一个单选对话框
	 * @param context
	 * @param toast 提示消息
	 * @param dialogClickListener 点击监听
	 * @return
	 */
	public static android.app.Dialog showRadioDialog(Context context,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,context.getResources().getString(R.string.pointMessage),toast,dialogClickListener,RADIO_DIALOG);
	}
	/**
	 * 创建一个选择对话框
	 * @param context
	 * @param toast 提示消息
	 * @param dialogClickListener 点击监听
	 * @return
	 */
	public static android.app.Dialog showSelectDialog(Context context,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,context.getResources().getString(R.string.pointMessage),toast,dialogClickListener,SELECT_DIALOG);
	}
	/**
	 * 创建一个选择对话框
	 * @param context
	 * @param toast 提示消息
	 * @param dialogClickListener 点击监听
	 * @return
	 */
	public static android.app.Dialog showSelectDialogReCheck(Context context,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,context.getResources().getString(R.string.pointMessage),toast,"重新审核",dialogClickListener,SELECT_DIALOG);
	}
	/**
	 * 创建一个选择对话框
	 * @param context
	 * @param title 提示标题
	 * @param toast 提示消息
	 * @param dialogClickListener 点击监听
	 * @return
	 */
	public static android.app.Dialog showSelectDialog(Context context,String title,String toast,final DialogClickListener dialogClickListener){
		return ShowDialog(context,title,toast,dialogClickListener,SELECT_DIALOG);
	}
	private static android.app.Dialog ShowDialog(Context context,String title,String toast,final DialogClickListener dialogClickListener,int DialogType){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.point)).setText(title);
		((TextView)view.findViewById(R.id.toast)).setText(toast);
		if(DialogType==RADIO_DIALOG){
		}else{
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
		}
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.cancel();
					}
				},200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				},200);
			}
		});
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				}, 200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}
	private static android.app.Dialog ShowDialog(Context context,String title,String toast,String reCheck, final DialogClickListener dialogClickListener,int DialogType){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.point)).setText(title);
		((TextView)view.findViewById(R.id.toast)).setText(toast);
		((TextView)view.findViewById(R.id.confirm)).setText(reCheck);
		if(DialogType==RADIO_DIALOG){
		}else{
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
		}
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.cancel();
					}
				},200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				},200);
			}
		});
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				}, 200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}
	private static android.app.Dialog ShowDialog(Context context,String title,String[] items,final DialogItemClickListener dialogClickListener){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_radio, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.title)).setText(title);
		//根据items动态创建
		LinearLayout parent=(LinearLayout) view.findViewById(R.id.dialogLayout);
		parent.removeAllViews();
		int length=items.length;
		for (int i = 0; i < items.length; i++) {
			LayoutParams params1=new LayoutParams(-1,-2);
			params1.rightMargin=1;
			final TextView tv=new TextView(context);
			tv.setLayoutParams(params1);
			tv.setTextSize(18);
			tv.setText(items[i]);
			tv.setTextColor(context.getResources().getColor(R.color.dialogTxtColor));
			int pad=context.getResources().getDimensionPixelSize(R.dimen.common_loading_text_margin);
			tv.setPadding(pad,pad,pad,pad);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.CENTER);
			if(i!=length-1)
				tv.setBackgroundResource(R.drawable.menudialog_center_selector);
			else
				tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					dialogClickListener.confirm(tv.getText().toString());
				}
			});
			parent.addView(tv);
			if(i!=length-1){
				TextView divider=new TextView(context);
				LayoutParams params=new LayoutParams(-1,(int)1);
				divider.setLayoutParams(params);
				divider.setBackgroundResource(android.R.color.darker_gray);
				parent.addView(divider);
			}
		}
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width= getScreenWidth(context);
		mWindow.setGravity(Gravity.BOTTOM);
		//添加动画
		mWindow.setWindowAnimations(R.style.dialogAnim);
		mWindow.setAttributes(lp);
		dialog.show();
		return dialog;
	}
	private static android.app.Dialog ShowListViewDialog(Context context,String title,String[] items,final DialogItemClickListener dialogClickListener){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_listview, null);
		dialog.setContentView(view);
		ScrollView scrollView = (ScrollView) view.findViewById(R.id.sLayout_content);
		if (items.length >= 7) {
			LinearLayout.LayoutParams params = (LayoutParams) scrollView
					.getLayoutParams();
			params.height = getScreenHeight(context) / 2;
			scrollView.setLayoutParams(params);
		}

		((TextView) view.findViewById(R.id.title)).setText(title);
		//根据items动态创建
		LinearLayout parent=(LinearLayout) view.findViewById(R.id.dialogLayout);
		parent.removeAllViews();
		int length=items.length;
		for (int i = 0; i < items.length; i++) {
			LayoutParams params1=new LayoutParams(-1,-2);
			params1.rightMargin=1;
			final TextView tv=new TextView(context);
			tv.setLayoutParams(params1);
			tv.setTextSize(18);
			tv.setText(items[i]);
			tv.setTextColor(context.getResources().getColor(R.color.dialogTxtColor));
			int pad=context.getResources().getDimensionPixelSize(R.dimen.common_loading_text_margin);
			tv.setPadding(pad,pad,pad,pad);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.CENTER);
			if(i!=length-1)
				tv.setBackgroundResource(R.drawable.menudialog_center_selector);
			else
				tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					dialogClickListener.confirm(tv.getText().toString());
				}
			});
			parent.addView(tv);
			if(i!=length-1){
				TextView divider=new TextView(context);
				LayoutParams params=new LayoutParams(-1,(int)1);
				divider.setLayoutParams(params);
				divider.setBackgroundResource(android.R.color.darker_gray);
				parent.addView(divider);
			}
		}
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.width= getScreenWidth(context);
		mWindow.setGravity(Gravity.BOTTOM);
		//添加动画
		mWindow.setWindowAnimations(R.style.dialogAnim);
		mWindow.setAttributes(lp);
		dialog.show();
		return dialog;
	}
	/**获取屏幕分辨率宽*/
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	/**获取屏幕分辨率高*/
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	public interface DialogClickListener{
		void confirm();
		void cancel();
	}
	public interface DialogItemClickListener{
		void confirm(String result);
	}

	/**选择认证对话框**/
	public interface DialogAuthenticationClickListener{
		void anchorClicked();
		void enterpriseClicked();
		void cancel();
	}


	/******** 添加输入框的Dialog ********/
	/**
	 * 创建一个单选对话框
	 * @param context
	 * @param title 标题
	 * @param dialogConfirmClickListener 点击监听
	 * @return
	 */
	public static android.app.Dialog showEditDialog(Context context,String title,final DialogConfirmClickListener dialogConfirmClickListener){
		return ShowEditDialog(context, title, dialogConfirmClickListener, SELECT_DIALOG);
	}

/******** 添加输入框的Dialog ********/
	/**
	 * 创建一个单选对话框
	 * @param context
	 * @param title 标题
	 * @param hint 提示语
	 * @param dialogConfirmClickListener 点击监听
	 * @return
	 */
	public static android.app.Dialog showEditDialog(Context context,String title,String hint,final DialogConfirmClickListener dialogConfirmClickListener){
		return ShowEditDialog(context, title, hint, dialogConfirmClickListener, SELECT_DIALOG);
	}


	public interface DialogConfirmClickListener{
		void confirm(String result);
	}

	private static android.app.Dialog ShowEditDialog(Context context,String title,final DialogConfirmClickListener dialogConfirmClickListener,int DialogType){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.point)).setText(title);
		final EditText news = ((EditText) view.findViewById(R.id.toast));
		if(DialogType==RADIO_DIALOG){
		}else{
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
		}
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						dialogItemClickListener.cancel();
//					}
//				},200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogConfirmClickListener.confirm(news.getText().toString());
					}
				}, 200);
			}
		});
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogConfirmClickListener.confirm(news.getText().toString());
					}
				},200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}

	private static android.app.Dialog ShowEditDialog(Context context,String title,String hint, final DialogConfirmClickListener dialogConfirmClickListener,int DialogType){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.point)).setText(title);
		final EditText news = ((EditText) view.findViewById(R.id.toast));
		news.setHint(hint);
		if(DialogType==RADIO_DIALOG){
		}else{
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
		}
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						dialogItemClickListener.cancel();
//					}
//				},200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogConfirmClickListener.confirm(news.getText().toString());
					}
				}, 200);
			}
		});
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogConfirmClickListener.confirm(news.getText().toString());
					}
				},200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}

	public static android.app.Dialog showIdentityAffirmDialog(Context context,String toast,final DialogClickListener dialogClickListener){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_identity_affirm, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.toast)).setText(toast);
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.cancel();
					}
				},200);
			}
		});
		view.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm();
					}
				},200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}

	public static android.app.Dialog showAffirmDialog(Context context, final String toast, final DialogItemClickListener dialogClickListener){
		final android.app.Dialog dialog=new android.app.Dialog(context, R.style.DialogStyle);
		dialog.setCancelable(true);
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_affirm, null);
		dialog.setContentView(view);
		((TextView)view.findViewById(R.id.toast)).setText(toast);
		view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						dialogClickListener.confirm(toast);
					}
				},200);
			}
		});
		Window mWindow=dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		if(context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){//横屏
			lp.width= getScreenHeight(context)/10*8;
		}else{
			lp.width= getScreenWidth(context)/10*8;
		}
		mWindow.setAttributes(lp);
		dialog.show();

		return dialog;
	}

}
