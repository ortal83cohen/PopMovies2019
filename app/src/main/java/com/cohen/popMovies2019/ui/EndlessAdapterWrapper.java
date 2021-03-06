package com.cohen.popMovies2019.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicBoolean;

public class EndlessAdapterWrapper extends AdapterWrapper {
    public static final int VIEW_TYPE_LOAD_MORE = -1;
    private AtomicBoolean keepOnAppending = new AtomicBoolean(false);
    private int mLoadMoreViewId;
    private Context mContext;

    public EndlessAdapterWrapper(RecyclerView.Adapter adapter, Context context, @LayoutRes int loadMoreViewId) {
        super(adapter);
        mLoadMoreViewId = loadMoreViewId;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        int count = mAdapter.getItemCount();
        if (keepOnAppending.get()) {
            if (count > getMinItemCount()) {
                return count + 1; // one more for loading view
            }
        }

        return count;
    }

    public int getItemViewType(int position) {
        if (position == mAdapter.getItemCount()) {
            return VIEW_TYPE_LOAD_MORE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        if (position == mAdapter.getItemCount()) {
            return position;
        }
        return super.getItemId(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOAD_MORE) {
            View view = LayoutInflater.from(mContext).inflate(mLoadMoreViewId, parent, false);
            return new FooterViewHolder(view);
        }

        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    public void setKeepOnAppending(boolean newValue) {
        boolean same = newValue == keepOnAppending.get();

        keepOnAppending.set(newValue);

        if (!same) {
            int count = mAdapter.getItemCount();
            if (newValue) {
                if (count > getMinItemCount()) {
                    notifyItemInserted(count);
                }
            } else {
                notifyItemRemoved(count + 1);
            }
        }
    }

    private int getMinItemCount() {

        return 0;
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View loadMoreView) {
            super(loadMoreView);
        }
    }
}