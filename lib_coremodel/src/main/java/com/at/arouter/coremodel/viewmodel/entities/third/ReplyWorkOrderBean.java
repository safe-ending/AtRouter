package com.at.arouter.coremodel.viewmodel.entities.third;


import java.io.Serializable;

/**
 * 作者 : Joker
 * 创建日期 : 2018/8/13
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class ReplyWorkOrderBean implements Serializable {

    /**
     * author : yuyidi
     * detail : r1
     * createTime : 2018-07-24 15:25:32
     * admin : true
     * previous : {"id":10}
     */

    private String author;
    private String detail;
    private boolean admin;
    private PreviousBean previous;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public PreviousBean getPrevious() {
        return previous;
    }

    public void setPrevious(PreviousBean previous) {
        this.previous = previous;
    }

    public static class PreviousBean {
        /**
         * id : 10
         */

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
