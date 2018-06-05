package com.mo.lawyercloud.beans.apiBeans;

import java.util.List;

/**
 * Created by Mohaifeng on 18/5/31.
 */
public class HomeBean {

    private List<ChannelBean> channels;
    private List<BannerBean> banners;

    public List<ChannelBean> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelBean> channels) {
        this.channels = channels;
    }

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

}
