package com.example.clientversion_3.view;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientversion_3_1.R;

public class RTPullListView extends ListView implements OnScrollListener {  
	private static final String TAG = "RTPullListView";

	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;

	// ʵ�ʵ�padding�ľ����������ƫ�ƾ���ı���
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;
	private RelativeLayout footerView;
	private LinearLayout footer_layout;

	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;
	private boolean isEnd = true;

//	private int headContentWidth;
	private int headContentHeight;

	private int startY;
	private int firstItemIndex;
	private int state;
	private boolean isBack;
	private OnRefreshListener refreshListener;

	private boolean isRefreshable;
	private boolean isPush;

	private int visibleLastIndex;
	private int visibleItemCount;

	public RTPullListView(Context context) {
		super(context);
		init(context);
	}

	public RTPullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context) {
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(R.layout.pulllist_head, null);
		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
//		arrowImageView.setMinimumWidth(70);
//		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
//		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();
		addHeaderView(headView, null, false);
		
		
		//����listview�ײ���ȡ���ఴť�����Զ��壩
		footer_layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.empty_main, null);
		footerView =(RelativeLayout)inflater.inflate(R.layout.list_footview, null);
		addFooterView(footer_layout);
		
		
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
		isPush = true;
	}
	
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
		firstItemIndex = firstVisiableItem;
		visibleLastIndex = firstVisiableItem + arg2 - 1; 
		visibleItemCount = arg2;
		if(firstItemIndex == 1 && !isPush){
			setSelection(0);
		}
		
		if (firstVisiableItem + arg2 >= arg3 - 1)
			isEnd = true;
		else
			isEnd = false;
		
	}
	
	public void setSelectionfoot(){
		this.setSelection(visibleLastIndex - visibleItemCount + 1);
	}

	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		
		if(isEnd && arg1 == SCROLL_STATE_IDLE) {
			loadMore();
        }
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					isPush = true;
					startY = (int) event.getY();
					Log.v(TAG, "��downʱ���¼��ǰλ�á�");
				}
				break;
			case MotionEvent.ACTION_UP:
				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
						// ʲô������
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();

						Log.v(TAG, "������ˢ��״̬����done״̬");
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();

						Log.v(TAG, "���ɿ�ˢ��״̬����done״̬");
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!isRecored && firstItemIndex == 0) {
					Log.v(TAG, "��moveʱ���¼��λ��");
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {

					// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�������Ļ�Ļ����������Ƶ�ʱ���б���ͬʱ���й���

					// ��������ȥˢ����
					if (state == RELEASE_To_REFRESH) {

						setSelection(0);

						// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
						if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

							Log.v(TAG, "���ɿ�ˢ��״̬ת�䵽����ˢ��״̬");
						}
						// һ�����Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

							Log.v(TAG, "���ɿ�ˢ��״̬ת�䵽done״̬");
						}
						// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
						else {
							// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
						}
					}
					// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
					if (state == PULL_To_REFRESH) {

						setSelection(0);

						// ���������Խ���RELEASE_TO_REFRESH��״̬
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
							Log.v(TAG, "��done��������ˢ��״̬ת�䵽�ɿ�ˢ��");
						}
						// ���Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
							isPush = false;
							Log.v(TAG, "��DOne��������ˢ��״̬ת�䵽done״̬");
						}
					}
					// done״̬��
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}
					// ����headView��size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);
					}
					// ����headView��paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
					}
				}
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	// ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText(getResources().getString(R.string.release_to_refresh));

			Log.v(TAG, "��ǰ״̬���ɿ�ˢ��");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// ����RELEASE_To_REFRESH״̬ת������
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText(getResources().getString(R.string.pull_to_refresh));
			} else {
				tipsTextview.setText(getResources().getString(R.string.pull_to_refresh));
			}
			Log.v(TAG, "��ǰ״̬������ˢ��");
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(getResources().getString(R.string.refreshing));
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			Log.v(TAG, "��ǰ״̬,����ˢ��...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.pulltorefresh);
			tipsTextview.setText(getResources().getString(R.string.pull_to_refresh));
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			Log.v(TAG, "��ǰ״̬��done");
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public interface OnRefreshListener {
		public void Refresh();
		public void loadMore();
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText(getResources().getString(R.string.updating) + new Date().toLocaleString());
		changeHeaderViewByState();
		invalidateViews();
		setSelection(0);
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.Refresh();
		}
	}
	
	private void loadMore() {
		if (refreshListener != null) {
			refreshListener.loadMore();
		}
	}
	
	public void clickToRefresh(){
		state = REFRESHING;
		changeHeaderViewByState();
	}
	
	// �˷���ֱ���հ��������ϵ�һ������ˢ�µ�demo���˴��ǡ����ơ�headView��width�Լ�height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		lastUpdatedTextView.setText(getResources().getString(R.string.updating) + new Date().toLocaleString());
		super.setAdapter(adapter);
	}
	
	public void setMore(boolean hasMore) {
		if (hasMore) {
			footer_layout.removeAllViews();
			footer_layout.addView(footerView);
		} 
		else {
			footer_layout.removeAllViews();
			Toast.makeText(getContext(), "End of list", Toast.LENGTH_SHORT).show();
		}
	}
}  