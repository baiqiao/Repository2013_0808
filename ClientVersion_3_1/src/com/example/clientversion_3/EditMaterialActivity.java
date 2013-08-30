package com.example.clientversion_3;


import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientversion_3_1.R;

public class EditMaterialActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "EditMaterialActivity";
	private static final int TAKE_SMALL_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	private static final int CHOOSE_SMALL_PICTURE = 3;
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
		
	private ImageButton editmat_ibtn_topbar_left;
	private RelativeLayout editmat_rl_changeheadgrahp;
	private ImageView editmat_iv_headgraph;
	private Button editmat_btn_liveaddress;
	
	private Intent intent;
	private Context context;
	private Uri imageUri;
	
	private Button editmat_btn_liveaddress1;
	
	private Handler changeAddressHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			editmat_btn_liveaddress1 = (Button)EditMaterialActivity.this.findViewById(R.id.editmat_btn_liveaddress);
			editmat_btn_liveaddress1.setText(msg.obj.toString());
			Log.d(TAG, msg.obj.toString());
		}	
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_material);
		
		context = EditMaterialActivity.this;
		
		initView();
		
		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
		editmat_btn_liveaddress.setGravity(Gravity.LEFT);
		editmat_btn_liveaddress.setGravity(Gravity.CENTER_VERTICAL);
	}
	
	private void initView(){
		
		editmat_ibtn_topbar_left = (ImageButton)this.findViewById(R.id.editmat_ibtn_topbar_left);
		editmat_rl_changeheadgrahp = (RelativeLayout)this.findViewById(R.id.editmat_rl_changeheadgrahp);
		editmat_iv_headgraph = (ImageView)this.findViewById(R.id.editmat_iv_headgraph);
		editmat_btn_liveaddress = (Button)this.findViewById(R.id.editmat_btn_liveaddress);
		
		editmat_ibtn_topbar_left.setOnClickListener(this);
		editmat_rl_changeheadgrahp.setOnClickListener(this);
		editmat_btn_liveaddress.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editmat_ibtn_topbar_left:
			this.finish();
			break;
			
		case R.id.editmat_rl_changeheadgrahp:
			doPickPhotoAction();		
			break;
			
		case R.id.editmat_btn_liveaddress:
			intent = new Intent(context,ProvinceListActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	//封装当前类的handler
	public Handler returnHandler(){
		return changeAddressHandler;
	}
	
	private void doPickPhotoAction() {
		final Context dialogContext = new ContextThemeWrapper(this,
				android.R.style.Theme_Light);
		String cancel = "返回";
		String[] choices;
		choices = new String[2];
		choices[0] = getString(R.string.take_photo); // 拍照
		choices[1] = getString(R.string.pick_photo); // 从相册中选择
		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
				android.R.layout.simple_list_item_1, choices);
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				dialogContext);
		builder.setTitle(R.string.attachToContact);
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0: {
							String status = Environment
									.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
								doTakePhoto();// 用户点击了从照相机获取
							} else {
								Toast.makeText(context,"没有SD卡", Toast.LENGTH_LONG).show();
							}
							break;
						}
						case 1:
							doPickPhotoFromGallery();// 从相册中去获取
							break;
						}
					}
				});
		builder.setNegativeButton(cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			Log.e(TAG, "requestCode = " + requestCode);
			Log.e(TAG, "resultCode = " + resultCode);
			Log.e(TAG, "data = " + data);
			return;
		} else {
			switch (requestCode) {

			case TAKE_SMALL_PICTURE:
				Log.i(TAG, "TAKE_SMALL_PICTURE: data = " + data);
				// TODO sent to crop
				cropImageUri(imageUri, 200, 200, CROP_SMALL_PICTURE);

				break;

			case CROP_SMALL_PICTURE:
				if (imageUri != null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					editmat_iv_headgraph.setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				break;

			case CHOOSE_SMALL_PICTURE:
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					editmat_iv_headgraph.setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}
				break;
			}
		}
	}

	protected void doTakePhoto() {
		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
																// capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, TAKE_SMALL_PICTURE);
	}
	
	protected void doPickPhotoFromGallery() {
		intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, CHOOSE_SMALL_PICTURE);
	}
	
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
