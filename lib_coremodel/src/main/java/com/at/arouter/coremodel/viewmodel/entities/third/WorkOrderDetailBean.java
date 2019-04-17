package com.at.arouter.coremodel.viewmodel.entities.third;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018/8/13
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class WorkOrderDetailBean  implements Serializable {
    /**
     * id : 14
     * code : 34J50G
     * content : i want to fuck you  every night !
     * phone : null
     * email : 123456@fuck.com
     * author : 951
     * processor : yuyidi
     * attachments : [{"id":14,"url":"http://192.168.31.76/resource/20180813/1534155200186024250.jpg","type":".jpg"}]
     * messages : [{"id":12,"author":"yuyidi","detail":"你能详细说下吗","createTime":"2018-08-13 18:20:32","admin":true,"process":false}]
     * status : 已接受
     * createTime : 2018-08-13 18:18:52
     * evaluate : 0
     * solve : false
     * feedback : null
     */

    private int id;
    private String code;
    private String content;
    private Object phone;
    private String email;
    private String author;
    private String processor;
    private String status;
    private String createTime;
    private int evaluate;
    private boolean solve;
    private Object feedback;
    private List<AttachmentsBean> attachments;
    private List<MessagesBean> messages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public boolean isSolve() {
        return solve;
    }

    public void setSolve(boolean solve) {
        this.solve = solve;
    }

    public Object getFeedback() {
        return feedback;
    }

    public void setFeedback(Object feedback) {
        this.feedback = feedback;
    }

    public List<AttachmentsBean> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentsBean> attachments) {
        this.attachments = attachments;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class AttachmentsBean {
        /**
         * id : 14
         * url : http://192.168.31.76/resource/20180813/1534155200186024250.jpg
         * type : .jpg
         */

        private int id;
        private String url;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class MessagesBean {
        /**
         * id : 12
         * author : yuyidi
         * detail : 你能详细说下吗
         * createTime : 2018-08-13 18:20:32
         * admin : true
         * process : false
         */

        private int id;
        private String author;
        private String detail;
        private String createTime;
        private boolean admin;
        private boolean process;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public boolean isProcess() {
            return process;
        }

        public void setProcess(boolean process) {
            this.process = process;
        }
    }
}
