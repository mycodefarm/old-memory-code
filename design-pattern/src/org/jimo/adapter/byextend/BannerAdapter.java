package org.jimo.adapter.byextend;

/**
 * Created by root on 17-6-10.
 * 适配器:这里使用继承
 */
public class BannerAdapter extends Banner implements Print {
    public BannerAdapter(String string) {
        super(string);
    }

    @Override
    public void printWeak() {
        showWithParen();
    }

    @Override
    public void printStrong() {
        showWithAster();
    }
}
