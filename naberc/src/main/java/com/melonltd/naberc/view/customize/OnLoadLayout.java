package com.melonltd.naberc.view.customize;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class OnLoadLayout extends PtrFrameLayout implements AbsListView.OnScrollListener {
    private static final String TAG = OnLoadLayout.class.getSimpleName();
    private int mTouchSlop, mYDown, mLastY;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    private boolean isLoading = false;

    private PtrClassicDefaultHeader mPtrClassicHeader;

    public OnLoadLayout(Context context) {
        super(context);
        initViews();
    }

    public OnLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        initViews();
    }

    public OnLoadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

//    public OnLoadLayout(Context context, AttributeSet o) {
//    }

    private void getListView() {
        if (getChildCount() > 0) {
            View childView = searchChildView(this, 0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                mListView.setOnScrollListener(this);
            }
        }
    }

    private static View searchChildView(View view, int index) {
        if (view instanceof ListView) {
            return view;
        } else if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            View sub = null;
            for (int i = 0; i < count; i++) {
                View v = ((ViewGroup) view).getChildAt(i);
                if (v instanceof ListView) {
                    sub = v;
                }
            }
            if (sub instanceof ListView) {
                return sub;
            } else {
                return searchChildView(((ViewGroup) view).getChildAt(index), index++);
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
//                    setLoading(false);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    public boolean isTop(){
        if (mListView != null && mListView.getAdapter() != null) {
//            Log.d(TAG, "FirstVisiblePosition ::::" + mListView.getFirstVisiblePosition());
            return mListView.getFirstVisiblePosition() == 0;
        }
        return false;
    }


    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
            setLoading(false);
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
        } else {
//            PROGRESS_BAR.hide();
            mYDown = 0;
            mLastY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//            Log.d(TAG, "onScrollStateChanged");
        if (canLoad()) {
            loadData();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (canLoad()) {
            loadData();
        }
    }

    public static interface OnLoadListener {
        public void onLoad();
    }


    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}
