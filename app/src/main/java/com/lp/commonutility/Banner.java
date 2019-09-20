package com.lp.commonutility;

import java.util.List;

/**
 * @author Loren
 * Create_Time: 2019/9/20 17:34
 * description:
 */
public class Banner {
    private long time;

    private int type;

    private String url;

    private List<Goods> goods;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

  public static class Goods {
        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Goods(String url, String name) {
            this.url = url;
            this.name = name;
        }
    }

}
