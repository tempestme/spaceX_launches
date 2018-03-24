package com.example.pavel.loadmore.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.controller.LaunchAdapter;
import com.example.pavel.loadmore.controller.OnLoadMoreListener;
import com.example.pavel.loadmore.controller.SpaceXInterface;
import com.example.pavel.loadmore.model.LaunchModel.Launch;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pavel on 24.03.18.
 */

public class FragmentRecent extends Fragment {

    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    private LaunchAdapter launchAdapter;
    private SpaceXInterface client;
    private ArrayList<Launch> launches;
    private Integer current_year;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mToolbar = (Toolbar) findViewById(R.id.toolbar);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent,container,false);
        ButterKnife.bind(getActivity());


        current_year = 2018;

        launches = new ArrayList<Launch>();
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycleView);
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
                launchAdapter.notifyDataSetChanged();
                current_year-=1;
            }
            @Override
            public void onFailure(Call<ArrayList<Launch>> call, Throwable t) {
                Toast.makeText(getActivity(),R.string.check_internet,Toast.LENGTH_LONG).show();
            }
        });

        //mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        launchAdapter = new LaunchAdapter(mRecyclerView,launches,getActivity());
        mRecyclerView.setAdapter(launchAdapter);
        launchAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override public void onLoadMore() {
                Log.e("haint", "Load More");
                launches.add(null);
                launchAdapter.notifyItemInserted(launches.size() - 1);
                //Load more data for reyclerview




                        //Remove loading item
                        launches.remove(launches.size() - 1);
                        launchAdapter.notifyItemRemoved(launches.size());

                        Call<ArrayList<Launch>> call = client.getLatestLaunch(current_year.toString());

                        call.enqueue(new Callback<ArrayList<Launch>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Launch>> call, Response<ArrayList<Launch>> response) {
                                if (response.body().size()>0){
                                    ArrayList<Launch> turnedList = response.body();
                                    Collections.reverse(turnedList);
                                    launches.addAll(turnedList);
                                    if (current_year==2012){
                                        current_year-=2;
                                    }else {
                                        current_year-=1;
                                    }
                                    launchAdapter.notifyDataSetChanged();
                                    launchAdapter.setLoaded();

                                }
                                else{
                                    Toast.makeText(getActivity(),R.string.nothing_to_load, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ArrayList<Launch>> call, Throwable t) {
                                Toast.makeText(getActivity(),R.string.check_internet,Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

        return view;
    }
}
