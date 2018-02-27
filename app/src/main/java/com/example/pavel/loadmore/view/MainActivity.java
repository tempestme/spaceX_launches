package com.example.pavel.loadmore.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.controller.OnLoadMoreListener;
import com.example.pavel.loadmore.controller.SpaceXInterface;
import com.example.pavel.loadmore.model.DetailInfo;
import com.example.pavel.loadmore.model.Launch;
import com.example.pavel.loadmore.model.Payload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private SpaceXInterface client;
    private ArrayList<Launch> launches;
    private Map<Integer,Boolean> map;
    private static Integer current_year = 2018;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        map = new HashMap<>();
        map.put(2018,false);
        map.put(2017,false);
        map.put(2016,false);
        map.put(2015,false);
        map.put(2014,false);
        map.put(2013,false);
        map.put(2012,false);

        mToolbar.setTitle("spaceX launches");
        launches = new ArrayList<Launch>();
        /**
         * initial loading of data for list
         */
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SpaceXInterface.Base_url)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        client = retrofit.create(SpaceXInterface.class);

        Call<ArrayList<Launch>> call = client.getLatestLaunch(current_year.toString());

        call.enqueue(new Callback<ArrayList<Launch>>() {
            @Override
            public void onResponse(Call<ArrayList<Launch>> call, Response<ArrayList<Launch>> response) {
                ArrayList<Launch> turnedList = response.body();
                Collections.reverse(turnedList);
                launches.addAll(turnedList);
                mUserAdapter.notifyDataSetChanged();
                current_year-=1;
            }

            @Override
            public void onFailure(Call<ArrayList<Launch>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"check your internets",Toast.LENGTH_LONG).show();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new UserAdapter();
        mRecyclerView.setAdapter(mUserAdapter);
        mUserAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override public void onLoadMore() {
                Log.e("haint", "Load More");
                launches.add(null);
                mUserAdapter.notifyItemInserted(launches.size() - 1);
                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        Log.e("haint", "Load More 2");


                        //Remove loading item
                        launches.remove(launches.size() - 1);
                        mUserAdapter.notifyItemRemoved(launches.size());

                        Call<ArrayList<Launch>> call = client.getLatestLaunch(current_year.toString());
                        call.enqueue(new Callback<ArrayList<Launch>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Launch>> call, Response<ArrayList<Launch>> response) {
                                if (response.body().size()>0){
                                    ArrayList<Launch> turnedList = response.body();
                                    Collections.reverse(turnedList);
                                    launches.addAll(turnedList);
                                    current_year-=1;
                                    mUserAdapter.notifyDataSetChanged();
                                    mUserAdapter.setLoaded();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"nothing to load", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ArrayList<Launch>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"check your internets",Toast.LENGTH_LONG).show();
                            }
                        });


//                        mUserAdapter.notifyDataSetChanged();
//                        mUserAdapter.setLoaded();
                    }
                }, 5000);
            }
        });
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvName;
//        public TextView tvEmailId;

        public TextView rocketName;
        public TextView launchSite;
        public TextView time;
        public TextView details;
        public UserViewHolder(View itemView) {
            super(itemView);
//            tvName = (TextView) itemView.findViewById(R.id.tvName);
//            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
            rocketName = (TextView) itemView.findViewById(R.id.detailRocketName);
            launchSite = (TextView) itemView.findViewById(R.id.detailLaunchSite);
            time = (TextView) itemView.findViewById(R.id.detailLaunchTime);
            details = (TextView) itemView.findViewById(R.id.detailsTv);


        }
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

    class UserAdapter extends RecyclerView.Adapter < RecyclerView.ViewHolder > {
        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        private OnLoadMoreListener mOnLoadMoreListener;
        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;


        public UserAdapter() {
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
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_launch, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_loading_item, parent, false);
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
                        Intent detailIntent = new Intent(getApplicationContext(),DetailActivity.class);
                        detailIntent.putExtra("details", new DetailInfo(
                                launches.get(position).getRocket().getRocketName(),
                                launches.get(position).getLaunchSite().getSiteNameLong(),
                                launches.get(position).getLaunchDateUtc(),
                                launches.get(position).getDetails(),
                                launches.get(position).getReuse().getCore(),
                                launches.get(position).getReuse().getSideCore1(),
                                launches.get(position).getReuse().getSideCore2(),
                                (ArrayList<Payload>) launches.get(position).getRocket().getSecondStage().getPayloads(),
                                launches.get(position).getLinks().getArticleLink(),
                                launches.get(position).getLinks().getPresskit(),
                                launches.get(position).getLinks().getRedditLaunch(),
                                launches.get(position).getLinks().getRedditMedia()
                        ));


                        getApplicationContext().startActivity(detailIntent);
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
    }
}