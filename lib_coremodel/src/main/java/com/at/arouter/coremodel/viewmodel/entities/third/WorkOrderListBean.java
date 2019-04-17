package com.at.arouter.coremodel.viewmodel.entities.third;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018/8/13
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class WorkOrderListBean  implements Serializable {
    /**
     * content : [{"id":8,"code":"SDTQ1","content":"我好像不能充值1","phone":"18008622878","email":"yuyidi0630@163.com","author":"a","processor":"yuyidi","attachments":null,"messages":null,"status":"ACCEPT","createTime":"2018-07-28 14:32:22","evaluate":0,"solve":false,"feedback":null},{"id":9,"code":"F20KRI","content":"123213","phone":null,"email":"13245678910","author":null,"processor":null,"attachments":null,"messages":null,"status":"UNTREATED","createTime":null,"evaluate":0,"solve":false,"feedback":null}]
     * last : true
     * totalElements : 2
     * totalPages : 1
     * number : 0
     * size : 10
     * sort : null
     * first : true
     * numberOfElements : 2
     */

    private boolean last;
    private int totalElements;
    private int totalPages;
    private int number;
    private int size;
    private Object sort;
    private boolean first;
    private int numberOfElements;
    private List<ContentBean> content;

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 8
         * code : SDTQ1
         * content : 我好像不能充值1
         * phone : 18008622878
         * email : yuyidi0630@163.com
         * author : a
         * processor : yuyidi
         * attachments : null
         * messages : null
         * status : ACCEPT
         * createTime : 2018-07-28 14:32:22
         * evaluate : 0
         * solve : false
         * feedback : null
         */

        private int id;
        private String code;
        private String content;
        private String phone;
        private String email;
        private String author;
        private String processor;
        private Object attachments;
        private Object messages;
        private String status;
        private String createTime;
        private int evaluate;
        private boolean solve;
        private Object feedback;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
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

        public Object getAttachments() {
            return attachments;
        }

        public void setAttachments(Object attachments) {
            this.attachments = attachments;
        }

        public Object getMessages() {
            return messages;
        }

        public void setMessages(Object messages) {
            this.messages = messages;
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
    }
}
