package org.jimo.adapter.bydelegate;

/**
 * Created by root on 17-6-10.
 * 委托方式
 */
public class BannerAdapter extends Print {

    private Banner banner;

    public BannerAdapter(String string) {
        this.banner = new Banner(string);
    }

    @Override
    void printWeak() {
        banner.showWithParen();
    }

    @Override
    void printStrong() {
        banner.showWithAster();
    }
}
