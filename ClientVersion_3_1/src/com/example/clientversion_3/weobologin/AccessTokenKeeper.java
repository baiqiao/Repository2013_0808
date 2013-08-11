package com.example.clientversion_3.weobologin;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.weibo.sdk.android.Oauth2AccessToken;

/**
 * �������ڱ���Oauth2AccessToken��sharepreference�����ṩ��ȡ����
 * @author xiaowei6@staff.sina.com.cn
 *
 
*/


public class AccessTokenKeeper {
	private static final String PREFERENCES_NAME = "com_weibo_sdk_android";
	
	/**
	 * ����accesstoken��SharedPreferences
	 * @param context Activity �����Ļ���
	 * @param token Oauth2AccessToken
	*/

	public static void keepAccessToken(Context context, Oauth2AccessToken token) {
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString("token", token.getToken());
		editor.putLong("expiresTime", token.getExpiresTime());
		editor.commit();
	}
	/**
	 * ���sharepreference
	 * @param context
	 */
	public static void clear(Context context){
	    SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
	    Editor editor = pref.edit();
	    editor.clear();
	    editor.commit();
	}

	
	/**
	 * ��SharedPreferences��ȡaccessstoken
	 * @param context
	 * @return Oauth2AccessToken
	 */

	public static Oauth2AccessToken readAccessToken(Context context){
		Oauth2AccessToken token = new Oauth2AccessToken();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		token.setToken(pref.getString("token", ""));
		token.setExpiresTime(pref.getLong("expiresTime", 0));
		return token;
	}
}