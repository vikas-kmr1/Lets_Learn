package com.miwok;

import android.util.Log;

public class Words<noImage> {

     private String mText;
     private String mSubText;
     private int bgId;
     private int maudioResource;
    private static final int  noImage=-1;
    private int mId=noImage;


    public Words(String mText, String mSubText,int mId,int maudioResource)
    {
       this.mText=mText;
       this.mSubText=mSubText;
        Log.v("mthistext->",this.mText+"");
        Log.v("mthissubtext->",this.mSubText+"");
        Log.v("mtext->",mText+"");
        Log.v("msubtext->",mSubText+"");
        this.mId=mId;
        this.maudioResource=maudioResource;
    }

    public Words(String mText, String mSubText,int maudioResource)
    {
        this.mText=mText;
        this.mSubText=mSubText;
        this.maudioResource=maudioResource;
    }

    public Words(int bgId)
    {
        bgId=bgId;
    }

    public String getmText()
    {
        return mText+"";
    }
    public String getmSubText()
    {
        return mSubText+"";
    }

   public int getmId()
    {
        return mId;
    }
   public int getBgId()
    {
        Log.v("number_word bgId-> ",bgId+"");
        return bgId;
    }

   public boolean hasImage()
   {
       return mId!=noImage;
   }
   public int getMaudioResource()
   {
       return maudioResource;
   }

    @Override
    public String toString() {
        return "Words{" +
                "mText='" + mText + '\'' +
                ", mSubText='" + mSubText + '\'' +
                ", bgId=" + bgId +
                ", maudioResource=" + maudioResource +
                ", mId=" + mId +
                '}';
    }
}
