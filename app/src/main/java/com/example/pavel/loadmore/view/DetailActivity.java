package com.example.pavel.loadmore.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.loadmore.R;
import com.example.pavel.loadmore.controller.PayloadAdapter;
import com.example.pavel.loadmore.model.DetailInfo;
import com.example.pavel.loadmore.model.Payload;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detailRocketName)
    TextView name;
    @BindView(R.id.detailLaunchSite)
    TextView site;
    @BindView(R.id.detailLaunchTime)
    TextView time;
    @BindView(R.id.detailDetails)
    TextView details;
    @BindView(R.id.detailCore)
    TextView core;
    @BindView(R.id.detailCore1)
    TextView sidecore1;
    @BindView(R.id.detailCore2)
    TextView sidecore2;
    @BindView(R.id.payloadsList)
    RecyclerView payloadList;

    @BindView(R.id.articleLink)
    TextView articleLink;
    @BindView(R.id.pressLink)
    TextView pressKit;
    @BindView(R.id.redditLink)
    TextView reddit1;
    @BindView(R.id.redditLink2)
    TextView reddit2;

    Intent i;
    RecyclerView.LayoutManager layoutManager;
    PayloadAdapter payloadAdapter;
    DetailInfo detailInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

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
        if(detailInfo.isCore()==true){
            core.setText(R.string.reusable);
            core.setBackgroundResource(R.color.succeeded);
        }
        else{
            core.setText(R.string.not_reusable);
            core.setBackgroundResource(R.color.failed);
        }
        if(detailInfo.isSidecore1()==true){
            sidecore1.setText(R.string.reusable);
            sidecore1.setBackgroundResource(R.color.succeeded);
        }
        else{
            sidecore1.setText(R.string.not_reusable);
            sidecore1.setBackgroundResource(R.color.failed);
        }
        if(detailInfo.isSidecore2()==true){
            sidecore2.setText(R.string.reusable);
            sidecore2.setBackgroundResource(R.color.succeeded);
        }
        else{
            sidecore2.setText(R.string.not_reusable);
            sidecore2.setBackgroundResource(R.color.failed);
        }
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
