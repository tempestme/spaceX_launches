package com.example.pavel.loadmore.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.model.Launch;
import com.example.pavel.loadmore.view.DetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pavel on 28.02.18.
 */

public class LaunchAdapter extends RecyclerView.Adapter < RecyclerView.ViewHolder > {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    RecyclerView mRecyclerView;
    ArrayList<Launch> launches;
    Context context;


    public LaunchAdapter(RecyclerView mRecyclerView, ArrayList<Launch> launches, Context context) {
        this.mRecyclerView = mRecyclerView;
        this.launches = launches;
        this.context = context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }
    @Override public int getItemViewType(int position) {
        return launches.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_launch, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserViewHolder) {
            //User user = mUsers.get(position);
            //Launch launch = launches.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
//                userViewHolder.tvName.setText(user.getName());
//                userViewHolder.tvEmailId.setText(user.getEmail());

            userViewHolder.rocketName.setText(launches.get(position).getRocket().getRocketName());
            userViewHolder.launchSite.setText(launches.get(position).getLaunchSite().getSiteNameLong());
            userViewHolder.time.setText(launches.get(position).getLaunchDateUtc());
            userViewHolder.details.setText(launches.get(position).getDetails());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(context,DetailActivity.class);
                    detailIntent.putExtra("details", launches.get(position).passDetails());


                    context.startActivity(detailIntent);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override public int getItemCount() {
        return launches == null ? 0 : launches.size();
    }
    public void setLoaded() {
        isLoading = false;
    }


    static class LoadingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }

        @Override
        public void onClick(View view) {

        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvName;
//        public TextView tvEmailId;
        @BindView(R.id.detailRocketName)
        public TextView rocketName;
        @BindView(R.id.detailLaunchSite)
        public TextView launchSite;
        @BindView(R.id.detailLaunchTime)
        public TextView time;
        @BindView(R.id.detailsTv)
        public TextView details;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
