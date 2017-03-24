package com.yqx.mamajh.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.github.obsessive.library.widgets.tag.Constants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SaveSerializable {

	 Context context;
	static SaveSerializable instance;
	    String name = Constants.SHAREDPREFERENCE_CONFIG_USER;
	 
	    public SaveSerializable(Context context) {
	        this.context = context;
	    }

	public static SaveSerializable getInstance(Context context) {
		if (instance == null) {
			instance = new SaveSerializable(context);
		}
		return instance;
	}
	/**
	     * 根据key和预期的value类型获取value的值
	     *
	     * @param key
	     * @param clazz
	     * @return
	     */
	    public <T> T getValue(String key, Class<T> clazz) {
	        if (context == null) {
	            throw new RuntimeException("请先调用带有context，name参数的构造！");
	        }
	        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
	        return getValue(key, clazz, sp);
	    }
	 
	    /**
	     * 针对复杂类型存储<对象>
	     *
	     * @param key
	     * @param object
	     */
	    public void setObject(String key, Object object) {
	        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
	 
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream out = null;
	        try {
	 
	            out = new ObjectOutputStream(baos);
	            out.writeObject(object);
	            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
	            Editor editor = sp.edit();
	            editor.putString(key, objectVal);
	            editor.commit();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (baos != null) {
	                    baos.close();
	                }
	                if (out != null) {
	                    out.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	 
	    @SuppressWarnings("unchecked")
	    public <T> T getObject(String key, Class<T> clazz) {
	        SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
	        if (sp.contains(key)) {
	            String objectVal = sp.getString(key, null);
	            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
	            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
	            ObjectInputStream ois = null;
	            try {
	                ois = new ObjectInputStream(bais);
	                T t = (T) ois.readObject();
	                return t;
	            } catch (StreamCorruptedException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    if (bais != null) {
	                        bais.close();
	                    }
	                    if (ois != null) {
	                        ois.close();
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        return null;
	    }
	 
	    /**
	     * 对于外部不可见的过渡方法
	     *
	     * @param key
	     * @param clazz
	     * @param sp
	     * @return
	     */
	    @SuppressWarnings("unchecked")
	    private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
	        T t;
	        try {
	 
	            t = clazz.newInstance();
	 
	            if (t instanceof Integer) {
	                return (T) Integer.valueOf(sp.getInt(key, 0));
	            } else if (t instanceof String) {
	                return (T) sp.getString(key, "");
	            } else if (t instanceof Boolean) {
	                return (T) Boolean.valueOf(sp.getBoolean(key, false));
	            } else if (t instanceof Long) {
	                return (T) Long.valueOf(sp.getLong(key, 0L));
	            } else if (t instanceof Float) {
	                return (T) Float.valueOf(sp.getFloat(key, 0L));
	            }
	        } catch (InstantiationException e) {
	            e.printStackTrace();
	            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	            Log.e("system", "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
	        }
	        Log.e("system", "无法找到" + key + "对应的值");
	        return null;
	    }
	    
	    public void removeKey(String key) {
	    	SharedPreferences sp = this.context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
	    	Editor editor = sp.edit();
	    	editor.remove(key);
	    	editor.commit();
		}

	/**
	 * 保存数据的方法
	 *
	 * @param key
	 *            键值对的key
	 * @param object
	 *            键值对的值
	 */
	public void put(String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据
	 *
	 * @param key
	 *            存数据时候的key
	 * @param defaultObject
	 *            不能为空,取什么类型的数据就应该是什么类型的变量
	 * @return
	 */
	public Object get( String key, @NonNull Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(this.name, Context.MODE_PRIVATE);
		if (defaultObject instanceof String) {
			String stringResult = sp.getString(key, (String) defaultObject);
			return stringResult;
		} else if (defaultObject instanceof Integer) {
			Integer integerResult = sp.getInt(key, (Integer) defaultObject);
			return integerResult;
		} else if (defaultObject instanceof Boolean) {
			Boolean booleanResult = sp.getBoolean(key, (Boolean) defaultObject);
			return booleanResult;
		} else if (defaultObject instanceof Float) {
			Float floatResult = sp.getFloat(key, (Float) defaultObject);
			return floatResult;
		} else if (defaultObject instanceof Long) {
			Long longResult = sp.getLong(key, (Long) defaultObject);
			return longResult;
		}

		return null;
	}

	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}
			return null;
		}

		public static void apply(Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}
}
