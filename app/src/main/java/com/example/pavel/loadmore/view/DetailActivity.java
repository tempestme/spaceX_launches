package com.example.pavel.loadmore.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.controller.PayloadAdapter;
import com.example.pavel.loadmore.model.LaunchModel.DetailInfo;
import com.example.pavel.loadmore.model.LaunchModel.Payload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detailRocketName) TextView name;
    @BindView(R.id.detailLaunchSite) TextView site;
    @BindView(R.id.detailLaunchTime) TextView time;
    @BindView(R.id.detailDetails) TextView details;
    @BindView(R.id.payloadsList) RecyclerView payloadList;
    @BindView(R.id.coreReusable) CheckBox coreCheckbox;
    @BindView(R.id.sidecore1Reusable) CheckBox sidecoreCheckbox1;
    @BindView(R.id.sidecore2Reusable) CheckBox sidecoreCheckbox2;

    @BindView(R.id.articleLink) CardView articleLink;
    @BindView(R.id.presskit) CardView pressKit;
    @BindView(R.id.redditLink) CardView reddit1;
    @BindView(R.id.redditLink2) CardView reddit2;

    Intent i;
    RecyclerView.LayoutManager layoutManager;
    PayloadAdapter payloadAdapter;
    DetailInfo detailInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        i = getIntent();
        detailInfo  = i.getParcelableExtra("details");
        layoutManager = new LinearLayoutManager(this);
        payloadList.setLayoutManager(layoutManager);
        payloadAdapter = new PayloadAdapter((ArrayList<Payload>) detailInfo.getPayloads(),getApplicationContext());
        payloadList.setAdapter(payloadAdapter);


        if (detailInfo.getRocketName()!=null){
            name.setText(detailInfo.getRocketName());

        }
        if (detailInfo.getSite()!=null){
            site.setText(detailInfo.getSite());

        }
        if (detailInfo.getDate()!=null){
            time.setText(detailInfo.getDate());
        }
        if(detailInfo.getDetails()!=null){
            details.setText(detailInfo.getDetails());
        }

        coreCheckbox.setChecked(detailInfo.isCore());
        sidecoreCheckbox1.setChecked(detailInfo.isSidecore1());
        sidecoreCheckbox2.setChecked(detailInfo.isSidecore2());

        if (detailInfo.getArticleLink()!=null){
            articleLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailInfo.getArticleLink()));
                    startActivity(intent);
                }
            });
        }
        else{
            articleLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.not_found, Toast.LENGTH_SHORT).show();

                }
            });
        }
        if (detailInfo.getPressKit()!=null){
            pressKit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailInfo.getPressKit()));
                    startActivity(intent);
                }
            });
        }
        else{
            pressKit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.not_found, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (detailInfo.getReddit()!=null){
            reddit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailInfo.getReddit()));
                    startActivity(intent);
                }
            });
        }
        else{
            reddit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.not_found, Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (detailInfo.getReddit2()!=null){
            reddit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailInfo.getReddit2()));
                    startActivity(intent);
                }
            });
        }
        else{
            reddit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),R.string.not_found, Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
