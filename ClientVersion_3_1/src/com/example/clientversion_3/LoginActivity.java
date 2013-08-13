package com.example.clientversion_3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.clientversion_3.util.JsonHelper;
import com.example.clientversion_3.weobologin.AccessTokenKeeper;
import com.example.clientversion_3_1.R;
import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.Constants;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.Utility;

public class LoginActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private Button btn_weibologin;
	
	private final int SINARESULTCODE = 1;
	private final int QQRESULTCODE = 2;

	private ImageButton sina;
	private ImageButton qq;
	public Dialog dialog;
	private LayoutInflater inflater; // 布局器

	private Weibo mWeibo;
	private static final String CONSUMER_KEY = "652117364";// 替换为开发者的appkey，例如"1646212860";
	private static final String REDIRECT_URL = "http://www.sina.com";
	public static Oauth2AccessToken accessToken = null;
	public static final String TAG = "sinasdk";
	/**SsoHandler 仅当sdk支持sso时有效*/
	SsoHandler mSsoHandler;
	
	private static final String SCOPE = "all";
	private String APP_ID = "222222";
	private Tencent mTencent;
	private Handler mHandler = new Handler();
	private Dialog mProgressDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		Context ctxContext = this.getApplicationContext();
		mTencent = Tencent.createInstance(APP_ID, ctxContext);

		inflater = this.getLayoutInflater();
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);

		btn_weibologin = (Button) this.findViewById(R.id.btn_weibologin);
		btn_weibologin.setOnClickListener(this);

		LoginActivity.accessToken = AccessTokenKeeper.readAccessToken(this);
		Log.d("AAAA", accessToken.getToken() + "");

		if (LoginActivity.accessToken.isSessionValid()) {
			Weibo.isWifi = Utility.isWifi(this);
			try {
				Class sso = Class.forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话,显示api功能演示入口按钮
				btn_weibologin.setVisibility(View.VISIBLE);
			} catch (ClassNotFoundException e) {
				// e.printStackTrace();
				Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

			}
			// login_thethird.setVisibility(View.INVISIBLE);
			// ssoBtn.setVisibility(View.INVISIBLE);
			// cancelBtn.setVisibility(View.VISIBLE);
			String date = new java.text.SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
					.format(new java.util.Date(LoginActivity.accessToken
							.getExpiresTime()));
			// mText.setText("access_token 仍在有效期内,无需再次登录:  \naccess_token:" +
			// LoginActivity.accessToken.getToken() + "\n有效期：" + date);
		} else {
			// mText.setText("使用SSO登录前，请检查手机上是否已经安装新浪微博客户端，目前仅3.0.0及以上微博客户端版本支持SSO；如果未安装，将自动转为Oauth2.0进行认证");
		}
		DialogView();

	}

	private void DialogView() {

		LinearLayout li = (LinearLayout) inflater.inflate(
				R.layout.dialog_thethird, null);
		sina = (ImageButton) li.findViewById(R.id.sina);
		qq = (ImageButton) li.findViewById(R.id.qq);
		sina.setOnClickListener(this);
		sina.setOnTouchListener(this);
		qq.setOnClickListener(this);

		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window win = dialog.getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();

		lp.alpha = 1.0f;
		lp.dimAmount = 0.9f;
		lp.x = 0;
		lp.y = 0;
		lp.width = 500;
		lp.height = 300;

		win.setAttributes(lp);
		win.setGravity(Gravity.CENTER);
		li.setBackgroundColor(Color.GRAY);
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(500, 300);
		dialog.setContentView(li, ll);

	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {

			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");

			// 此处实现 WeiboDialogListener 接口，授权成功后即可在 onComplete 回调方法中 获得
			// accessToken 信息，此时可以自由保存处理。
			LoginActivity.accessToken = new Oauth2AccessToken(token, expires_in);
			if (LoginActivity.accessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(LoginActivity.accessToken
								.getExpiresTime()));

				Log.d("FFF", accessToken.getToken() + "");
				// mText.setText("认证成功: \r\n access_token: " + token + "\r\n" +
				// "expires_in: " + expires_in + "\r\n有效期" + date);
				try {
					Class sso = Class
							.forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮

					// apiBtn.setVisibility(View.VISIBLE);
				} catch (ClassNotFoundException e) {
					// e.printStackTrace();
					Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

				}
				// cancelBtn.setVisibility(View.VISIBLE);
				AccessTokenKeeper.keepAccessToken(LoginActivity.this,
						accessToken);
				Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_SHORT)
						.show();

			}
		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(),
					"Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(),
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		/**下面两个注释掉的代码，仅当sdk支持sso时有效*/
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onClick(View v) {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		if (v.getId() == R.id.btn_weibologin) {

			dialog.show();
		}

		else if (v.getId() == R.id.sina) {
			mWeibo.authorize(LoginActivity.this, new AuthDialogListener());
		} 
		else if (v.getId() == R.id.qq) {
			onClickLogin();
			v.startAnimation(shake);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		/** 处理按钮背景	 @author yl*/

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			v.setBackgroundColor(getResources().getColor(R.color.pink));
			v.invalidate();
		} else {
			v.setBackgroundColor(0);
			v.invalidate();
		}
		return false;
	}

	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					// updateLoginButton();
				}
			};
			mTencent.login(this, SCOPE, listener);

		} else {
			mTencent.logout(this);
			// updateLoginButton();
		}
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(JSONObject response) {
			onClickUserInfo();
			doComplete(response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			// showResult("onError:", "code:" + e.errorCode + ", msg:"
			// + e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			// showResult("onCancel", "");
		}
	}

	private void onClickUserInfo() {
		if (ready()) {

			mTencent.requestAsync(Constants.GRAPH_SIMPLE_USER_INFO, null,
					Constants.HTTP_GET, new BaseApiListener(
							"get_simple_userinfo", false), null);

			mProgressDialog = new ProgressDialog(LoginActivity.this);
			mProgressDialog.show();
		}
	}

	private boolean ready() {
		boolean ready = mTencent.isSessionValid()
				&& mTencent.getOpenId() != null;
		if (!ready)
			Toast.makeText(this, "login and get openId first, please!",
					Toast.LENGTH_SHORT).show();
		return ready;
	}

	private class BaseApiListener implements IRequestListener {
		private String mScope = "all";
		private Boolean mNeedReAuth = false;

		public BaseApiListener(String scope, boolean needReAuth) {
			mScope = scope;
			mNeedReAuth = needReAuth;
		}

		@Override
		public void onComplete(final JSONObject response, Object state) {

			showResult("IRequestListener.onComplete:",
					JsonHelper.getInfo(response.toString()).nickname,
					JsonHelper.getInfo(response.toString()).headGraphPath);

			// showResult(
			// "IRequestListener.onComplete:",
			// rb.returnBitMap(JsonHelper.getInfo(response.toString()).headGrahpPath));
			doComplete(response, state);
		}

		protected void doComplete(JSONObject response, Object state) {
			try {
				int ret = response.getInt("ret");
				if (ret == 100030) {
					if (mNeedReAuth) {
						Runnable r = new Runnable() {
							public void run() {
								mTencent.reAuth(LoginActivity.this, mScope,
										new BaseUiListener());
							}
						};
						LoginActivity.this.runOnUiThread(r);
					}
				}
				// azrael 2/1注释掉了, 这里为何要在api返回的时候设置token呢,
				// 如果cgi返回的值没有token, 则会清空原来的token
				// String token = response.getString("access_token");
				// String expire = response.getString("expires_in");
				// String openid = response.getString("openid");
				// mTencent.setAccessToken(token, expire);
				// mTencent.setOpenId(openid);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("toddtest", response.toString());
			}

		}

		@Override
		public void onIOException(final IOException e, Object state) {
			// showResult("IRequestListener.onIOException:", e.getMessage());
		}

		@Override
		public void onMalformedURLException(final MalformedURLException e,
				Object state) {
			// showResult("IRequestListener.onMalformedURLException",
			// e.toString());
		}

		@Override
		public void onJSONException(final JSONException e, Object state) {
			// showResult("IRequestListener.onJSONException:", e.getMessage());
		}

		@Override
		public void onConnectTimeoutException(ConnectTimeoutException arg0,
				Object arg1) {
			// showResult("IRequestListener.onConnectTimeoutException:",
			// arg0.getMessage());

		}

		@Override
		public void onSocketTimeoutException(SocketTimeoutException arg0,
				Object arg1) {
			// showResult("IRequestListener.SocketTimeoutException:",
			// arg0.getMessage());
		}

		@Override
		public void onUnknowException(Exception arg0, Object arg1) {
			// showResult("IRequestListener.onUnknowException:",
			// arg0.getMessage());
		}

		@Override
		public void onHttpStatusException(HttpStatusException arg0, Object arg1) {
			// showResult("IRequestListener.HttpStatusException:",
			// arg0.getMessage());
		}

		@Override
		public void onNetworkUnavailableException(
				NetworkUnavailableException arg0, Object arg1) {
			// showResult("IRequestListener.onNetworkUnavailableException:",
			// arg0.getMessage());
		}
	}

	// private void showResult(final String base, final Bitmap bm) {
	// mHandler.post(new Runnable() {
	//
	// @Override
	// public void run() {
	// if (mProgressDialog.isShowing())
	// mProgressDialog.dismiss();
	// //LeftFragment.iv_userhead.setImageBitmap(bm);
	// Intent intent = new Intent();
	// intent.putExtra("nickname", base);
	// LoginActivity.this.finish();
	// }
	// });
	// }

	private void showResult(final String base, final String nickname,
			final String headGraphUrl) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (mProgressDialog.isShowing())
					mProgressDialog.dismiss();
				// LeftFragment.iv_userhead.setImageBitmap(bm);
				Intent intent = new Intent();
				intent.putExtra("nickname", nickname);
				intent.putExtra("headGraphUrl", headGraphUrl);
				Log.i("ttt", nickname);
				Log.i("ttt", headGraphUrl);
				setResult(QQRESULTCODE,intent);
				dialog.dismiss();
				LoginActivity.this.finish();
			}
		});
	}

}
