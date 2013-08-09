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
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.clientversion_3_1.R;

public class ChangePersonalMaterialActivity extends Activity implements
		OnClickListener {

	private static final String TAG = "ChangePersonMaterialActivity";

	private Intent intent;

	private ImageButton title_perset_ibtn_back, perset_ibtn_headgraph,title_permat_ibtn_confirm;

	public static Button change_btn_liveaddress, change_btn_workaddress;
	
	//标记选择城市的选项 0：表示居住地选择城市  1：表示工作地选择城市
	public static int ADDRESS_FLOG = 0;

	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";

	private Uri imageUri;

	private static final int TAKE_SMALL_PICTURE = 1;

	private static final int CROP_SMALL_PICTURE = 2;

	private static final int CHOOSE_SMALL_PICTURE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_personal_material);

		init();

		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
	}

	private void init() {

		title_perset_ibtn_back = (ImageButton) this
				.findViewById(R.id.title_perset_ibtn_back);
		title_permat_ibtn_confirm = (ImageButton) this.findViewById(R.id.title_permat_ibtn_confirm);
		change_btn_liveaddress = (Button) this
				.findViewById(R.id.change_btn_liveaddress);
		change_btn_workaddress = (Button) this
				.findViewById(R.id.change_btn_workaddress);
		perset_ibtn_headgraph = (ImageButton) this
				.findViewById(R.id.perset_ibtn_headgraph);

		title_perset_ibtn_back.setOnClickListener(this);
		title_permat_ibtn_confirm.setOnClickListener(this);
		change_btn_liveaddress.setOnClickListener(this);
		change_btn_workaddress.setOnClickListener(this);
		perset_ibtn_headgraph.setOnClickListener(this);	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_perset_ibtn_back:
			ChangePersonalMaterialActivity.this.finish();
			break;
			
		case R.id.title_permat_ibtn_confirm:
			ChangePersonalMaterialActivity.this.finish();
			break;

		case R.id.change_btn_liveaddress:

			intent = new Intent(ChangePersonalMaterialActivity.this,
					ProvinceListActivity.class);
			ADDRESS_FLOG = 0;
			startActivity(intent);
			break;

		case R.id.change_btn_workaddress:
			intent = new Intent(ChangePersonalMaterialActivity.this,
					ProvinceListActivity.class);
			ADDRESS_FLOG = 1;
			startActivity(intent);
			break;

		case R.id.perset_ibtn_headgraph:
			doPickPhotoAction();
		}
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
								Toast.makeText(
										ChangePersonalMaterialActivity.this,
										"没有SD卡", Toast.LENGTH_LONG).show();
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
					perset_ibtn_headgraph.setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "CROP_SMALL_PICTURE: data = " + data);
				}
				break;

			case CHOOSE_SMALL_PICTURE:
				if (data != null) {
					Bitmap bitmap = data.getParcelableExtra("data");
					perset_ibtn_headgraph.setImageBitmap(bitmap);
				} else {
					Log.e(TAG, "CHOOSE_SMALL_PICTURE: data = " + data);
				}
				break;
			}
		}
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

	protected void doTakePhoto() {
		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action is
																// capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, TAKE_SMALL_PICTURE);
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