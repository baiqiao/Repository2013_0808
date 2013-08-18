package com.example.clientversion_3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clientversion_3.entity.CommentItem;
import com.example.clientversion_3.util.Constants;
import com.example.clientversion_3_1.R;
import com.galhttprequest.GalHttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class AllCommentActivity extends Activity {

	private LayoutInflater inflater; // 布局器
	private List<CommentItem> commentItems = new ArrayList<CommentItem>();
	private AllCommentAdapter allCommentAdapter;
	private ListView mListView;
	private EditText allcomment_et_comment;
	private Button allcomment_btn_send;
	private Date currentDate;
	private SimpleDateFormat format;
	private ImageButton title_perset_ibtn_back;
	private UILApplication UilApplication; 
	
	
	/**图片加载相关因素*/
	//private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener;
	

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			allCommentAdapter.notifyDataSetChanged();
			mListView.setAdapter(allCommentAdapter);
			mListView.setSelection(commentItems.size());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allcomment);
		UilApplication = (UILApplication)getApplication();  
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.displayer(new RoundedBitmapDisplayer(10))
		.build();
		animateFirstListener = new AnimateFirstDisplayListener();

		currentDate = new Date(System.currentTimeMillis());
		format = new SimpleDateFormat("HH:mm");

		mListView = (ListView) this
				.findViewById(R.id.allcomment_listview_comment);
		allcomment_et_comment = (EditText) this
				.findViewById(R.id.allcomment_et_comment);
		allcomment_btn_send = (Button) this
				.findViewById(R.id.allcomment_btn_send);
		title_perset_ibtn_back = (ImageButton) this
				.findViewById(R.id.title_perset_ibtn_back);

		for (int i = 0; i < 39; i++) {
			CommentItem commentItem = new CommentItem();
			commentItem.username = "张三" + i + "号";
			
			if(i / 24 == 0) {
				commentItem.time = i + "小时前";
			}
			else{
				commentItem.time = i/24 + "天前";
			}

			if (i % 3 == 0) {
				commentItem.comment = "分享家园——准备在耶鲁傻愣实施的当地艺术驻地创作项目#上线了！";
				
			} else if (i % 3 == 1) {
				commentItem.comment = "新图出炉了~";
				
			} else {
				commentItem.comment = "很喜欢手环，原色的很有质感。有种皮质人生的味道。";
				}
			commentItem.headgraphUrl = Constants.IMAGES[i];
			
			commentItems.add(commentItem);
		}
		inflater = this.getLayoutInflater();
		allCommentAdapter = new AllCommentAdapter(this, commentItems);
		mListView.setAdapter(allCommentAdapter);
		allcomment_btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommentItem ci = new CommentItem();
				ci.comment = allcomment_et_comment.getText().toString();
				allcomment_et_comment.setText("");
				ci.username = "流氓会武术";
				ci.time = format.format(currentDate);
				ci.headgraphUrl = "https://lh4.googleusercontent.com/-wF2Vc9YDutw/T3R41fR2BCI/AAAAAAAAAJc/JdU1sHdMRAk/s1024/sample_image_38.jpg";
				commentItems.add(ci);
				mHandler.sendEmptyMessage(0);
			}
		});

		title_perset_ibtn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AllCommentActivity.this.finish();
			}
		});
	}

	// 自定义所有评论adapter;
	public class AllCommentAdapter extends BaseAdapter {

		GalHttpRequest request;

		List<CommentItem> commentItems;
		Context context;

		public AllCommentAdapter(Context context, List<CommentItem> commentItem) {
			this.context = context;
			this.commentItems = commentItem;
		}

		@Override
		public int getCount() {
			return commentItems.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		private class ViewHolder {
			ImageView img_headgraph;
			TextView tv_username;
			TextView tv_time;
			TextView tv_comment;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			CommentItem commentItem = commentItems.get(position);
			
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_allcomment, null);
				holder = new ViewHolder();

				holder.img_headgraph = (ImageView) convertView.findViewById(R.id.item_allcomm_headgraph);
				holder.tv_username = (TextView) convertView.findViewById(R.id.item_allcomm_username);
				holder.tv_time = (TextView) convertView.findViewById(R.id.item_allcomm_time);
				holder.tv_comment = (TextView) convertView.findViewById(R.id.item_allcomm_comment);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.tv_username.setText(commentItem.username);
			holder.tv_time.setText(commentItem.time);
			holder.tv_comment.setText(commentItem.comment);
			Log.i("ttt", "aaa");
			
			
			/*request = GalHttpRequest.requestWithURL(AllCommentActivity.this,
					commentItem.headgraphUrl);
			request.startAsynRequestBitmap(new GalHttpLoadImageCallBack() {
				@Override
				public void imageLoaded(Bitmap bitmap) {
					img_headgraph.setImageBitmap(bitmap);
				}
			});*/

			UilApplication.getImageLoader().displayImage(commentItem.headgraphUrl, holder.img_headgraph, options, animateFirstListener);
			
			return convertView;

		}
	}
	
	@Override
	public void onBackPressed() {
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}
	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
