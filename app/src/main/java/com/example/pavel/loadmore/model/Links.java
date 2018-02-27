
package com.example.pavel.loadmore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("mission_patch")
    @Expose
    private String missionPatch;
    @SerializedName("reddit_campaign")
    @Expose
    private String redditCampaign;
    @SerializedName("reddit_launch")
    @Expose
    private String redditLaunch;
    @SerializedName("reddit_recovery")
    @Expose
    private Object redditRecovery;
    @SerializedName("reddit_media")
    @Expose
    private String redditMedia;
    @SerializedName("presskit")
    @Expose
    private String presskit;
    @SerializedName("article_link")
    @Expose
    private String articleLink;
    @SerializedName("video_link")
    @Expose
    private String videoLink;

    public String getMissionPatch() {
        return missionPatch;
    }

    public void setMissionPatch(String missionPatch) {
        this.missionPatch = missionPatch;
    }

    public String getRedditCampaign() {
        return redditCampaign;
    }

    public void setRedditCampaign(String redditCampaign) {
        this.redditCampaign = redditCampaign;
    }

    public String getRedditLaunch() {
        return redditLaunch;
    }

    public void setRedditLaunch(String redditLaunch) {
        this.redditLaunch = redditLaunch;
    }

    public Object getRedditRecovery() {
        return redditRecovery;
    }

    public void setRedditRecovery(Object redditRecovery) {
        this.redditRecovery = redditRecovery;
    }

    public String getRedditMedia() {
        return redditMedia;
    }

    public void setRedditMedia(String redditMedia) {
        this.redditMedia = redditMedia;
    }

    public String getPresskit() {
        return presskit;
    }

    public void setPresskit(String presskit) {
        this.presskit = presskit;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

}
