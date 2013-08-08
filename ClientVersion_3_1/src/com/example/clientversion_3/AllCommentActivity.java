package com.example.clientversion_3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clientversion_3.entity.CommentItem;
import com.example.clientversion_3_1.R;
import com.galhttprequest.GalHttpRequest;
import com.galhttprequest.GalHttpRequest.GalHttpLoadImageCallBack;

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

		for (int i = 0; i < 10; i++) {
			CommentItem commentItem = new CommentItem();
			commentItem.username = "张三" + i + "号";
			commentItem.time = "8:" + i * 2;

			if (i % 3 == 0) {
				commentItem.comment = "让更多的人看到。江西6岁的小女孩丁香小慧家住上......";
				commentItem.headgraphUrl = "http://q.qlogo.cn/qqapp/222222/8921FA65ECA420A544383EE050AEB152/40";
			} else if (i % 3 == 1) {
				commentItem.comment = "面对，不一定最难过。孤独，不一定不快乐。得到，不一定能长久。失去，不一定不再拥有。不要因为寂寞而错爱，不要因为错爱而寂寞一生。——徐志摩【早安】";
				commentItem.headgraphUrl = "http://q.qlogo.cn/qqapp/222222/359EB7CFAE1D491431624EA5A6EC2EFB/40";
			} else {
				commentItem.comment = "简单的纯色衬衫，总是能衬托出男人最帅气的一面,这件很不错。";
				commentItem.headgraphUrl = "http://q.qlogo.cn/qqapp/222222/2742189F92589C79E82264478841599D/40";
			}
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
				ci.headgraphUrl = "http://s16.sinaimg.cn/orignal/89429f6dhb99b4903ebcf&690";
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			LinearLayout view = (LinearLayout) inflater.inflate(
					R.layout.item_allcomment, null);

			final CommentItem commentItem = commentItems.get(position);
			final ImageView img_headgraph = (ImageView) view
					.findViewById(R.id.item_allcomm_headgraph);
			TextView tv_username = (TextView) view
					.findViewById(R.id.item_allcomm_username);
			TextView tv_time = (TextView) view
					.findViewById(R.id.item_allcomm_time);
			TextView tv_comment = (TextView) view
					.findViewById(R.id.item_allcomm_comment);

			request = GalHttpRequest.requestWithURL(AllCommentActivity.this,
					commentItem.headgraphUrl);
			request.startAsynRequestBitmap(new GalHttpLoadImageCallBack() {
				@Override
				public void imageLoaded(Bitmap bitmap) {
					img_headgraph.setImageBitmap(bitmap);
				}
			});

			tv_username.setText(commentItem.username);
			tv_time.setText(commentItem.time);
			tv_comment.setText(commentItem.comment);
			Log.i("ttt", "aaa");
			return view;

		}
	}
}
