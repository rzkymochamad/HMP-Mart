package com.mg.shopping.interfaceutil;

public interface ViewerCallback {

    public void onSelectVideo(int position);

    public void onSelectProfile(int position);

    public void onFollow(int position);

    public void onSelectMusic(int position);

    public void onFavourite(int position);

    public void onComment(int position);

    public void onSendComment(int position,String comment);

    public void onShare(int position);


}
