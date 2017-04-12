package com.example.hans.hansrecyclergallery.recyclerview.wrapper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.example.hans.hansrecyclergallery.recyclerview.CommonAdapter;
import com.example.hans.hansrecyclergallery.recyclerview.base.ViewHolder;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public abstract class GalleryWrappter<T> extends CommonAdapter<T> {

  private float mScale = 0.5f; // 两边视图scale
  private float mAlpha = 0.8f; // 两边视图alpha
  private int mCurrentItemOffset;
  private int mCurrentItemPos;
  private int count = 7;//

  public GalleryWrappter(Context context, int layoutId, List<T> datas) {
    super(context, layoutId, datas);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewHolder holder = super.onCreateViewHolder(parent, viewType);
    // 设置Item的宽度
    holder.getConvertView().setScaleX(mScale);
    holder.getConvertView().setScaleY(mScale);
    holder.getConvertView().setAlpha(mAlpha);
    ViewGroup.LayoutParams lp = holder.getConvertView().getLayoutParams();
    lp.width = getItemStdWidth();
    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
  }

  public  int getItemStdWidth() {
    return mContext.getResources().getDisplayMetrics().widthPixels/count;
  }

  public int getMiddlePosition() {
    return mCurrentItemPos+count/2;
  }

  //public int toPositionMiddle(int position){
  //  if (position > getMiddlePosition()){
  //    return mCurrentItemPos+count-1 +position - getMiddlePosition()<= getItemCount() ? mCurrentItemPos+count-1 +position - getMiddlePosition() : getItemCount();
  //  }else if(position < getMiddlePosition()){
  //    return mCurrentItemPos -getMiddlePosition()+position >= 0 ? mCurrentItemPos -getMiddlePosition()+position :0;
  //  }
  //  return position;
  //}

  @Override public void onAttachedToRecyclerView(RecyclerView mRecyclerView) {
    mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
      public int mMiddleItemPos;
      public View rightView;
      public View currentView;
      public View leftView;

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE){
          for (int i =0 ; i< getItemCount() ; i++){
            View v = recyclerView.getLayoutManager().findViewByPosition(i);
            if (v == null)continue;
            if (currentView != v){
              v.setScaleX(mScale);
              v.setScaleY(mScale);
            }else {
              v.setScaleX(1);
              v.setScaleY(1);
            }
          }

        }
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override public void onScrolled(RecyclerView mRecyclerView, int dx, int dy) {
        //mCurrentItemOffset += dx;
        mCurrentItemOffset = mRecyclerView.computeHorizontalScrollOffset();
        mCurrentItemPos = (int) ((mCurrentItemOffset * 1.0 / getItemStdWidth())+0.1f);
        mMiddleItemPos = getMiddlePosition();
        int offset = (mCurrentItemPos+1)*getItemStdWidth() - mCurrentItemOffset ;
        float percent = (float) Math.max(Math.abs(offset)*1.0 / getItemStdWidth(), 0.0001);

        if (mMiddleItemPos > 0) {
          leftView = mRecyclerView.getLayoutManager().findViewByPosition(mMiddleItemPos - 1);
        }
        currentView = mRecyclerView.getLayoutManager().findViewByPosition(mMiddleItemPos);
        if (mMiddleItemPos < mRecyclerView.getAdapter().getItemCount() - 1) {
          rightView = mRecyclerView.getLayoutManager().findViewByPosition(mMiddleItemPos + 1);
        }

        if (leftView != null) {
          // y = (1 - mScale)x + mScale
          leftView.setScaleY(mScale);
          leftView.setScaleX(mScale);
        }


        if (currentView != null) {
          // y = (mScale - 1)x + 1
          currentView.setScaleY((1 - mScale) * percent + mScale);
          currentView.setScaleX((1 - mScale) * percent + mScale);
          currentView.setAlpha((1 - mAlpha) * percent + mAlpha);
        }
        if (rightView != null) {
          // y = (1 - mScale)x + mScale
          rightView.setScaleY((mScale - 1) * percent + 1);
          rightView.setScaleX((mScale - 1) * percent + 1);
          rightView.setAlpha((mAlpha - 1) * percent + 1);
        }
        super.onScrolled(mRecyclerView, dx, dy);
      }
    });
    super.onAttachedToRecyclerView(mRecyclerView);
  }
}
