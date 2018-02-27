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

public class DetailActivity extends AppCompatActivity {
    TextView name,site,time,details,core,sidecore1,sidecore2;
    RecyclerView payloadList;
    RecyclerView.LayoutManager layoutManager;
    PayloadAdapter payloadAdapter;
    DetailInfo detailInfo;
    TextView articleLink, pressKit, reddit1, reddit2;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        name = (TextView)findViewById(R.id.detailRocketName);
        site = (TextView)findViewById(R.id.detailLaunchSite);
        time = (TextView)findViewById(R.id.detailLaunchTime);
        details = (TextView)findViewById(R.id.detailDetails);
        core = (TextView)findViewById(R.id.detailCore);
        sidecore1 = (TextView)findViewById(R.id.detailCore1);
        sidecore2 = (TextView)findViewById(R.id.detailCore2);
        payloadList = (RecyclerView)findViewById(R.id.payloadsList);
        articleLink = (TextView)findViewById(R.id.articleLink);
        pressKit = (TextView)findViewById(R.id.pressLink);
        reddit1 = (TextView)findViewById(R.id.redditLink);
        reddit2 = (TextView)findViewById(R.id.redditLink2);


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
            core.setText("reusable");
            core.setBackgroundResource(R.color.succeeded);
        }
        else{
            core.setText("not reusable");
            core.setBackgroundResource(R.color.failed);
        }
        if(detailInfo.isSidecore1()==true){
            sidecore1.setText("reusable");
            sidecore1.setBackgroundResource(R.color.succeeded);
        }
        else{
            sidecore1.setText("not reusable");
            sidecore1.setBackgroundResource(R.color.failed);
        }
        if(detailInfo.isSidecore2()==true){
            sidecore2.setText("reusable");
            sidecore2.setBackgroundResource(R.color.succeeded);
        }
        else{
            sidecore2.setText("not reusable");
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
                    Toast.makeText(getApplicationContext(),"link isn't there", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(getApplicationContext(),"link isn't there", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"link isn't there", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"link isn't there", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}
