package com.example.pavel.loadmore.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.controller.LaunchAdapter;
import com.example.pavel.loadmore.controller.OnLoadMoreListener;
import com.example.pavel.loadmore.controller.SpaceXInterface;
import com.example.pavel.loadmore.model.Launch;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    private LaunchAdapter mUserAdapter;
    private SpaceXInterface client;
    private ArrayList<Launch> launches;
    private static Integer current_year = 2018;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("spaceX launches");
        launches = new ArrayList<Launch>();
        /**
         * loading first data
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


        //mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter = new LaunchAdapter(mRecyclerView,launches,getApplicationContext());
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
}