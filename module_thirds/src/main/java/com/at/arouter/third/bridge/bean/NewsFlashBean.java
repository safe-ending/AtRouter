package com.at.arouter.third.bridge.bean;

import com.at.arouter.common.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018/8/25
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class NewsFlashBean extends BaseModel {
    /**
     * content : [{"id":1,"content":"【声音 | 北京市政协委员：利用区块链等科技防控市场风险】据北京日报报道，北京市政协组织部分委员围绕加强预付卡管理相关提案召开检查督促办理座谈会。委员们建议，预付卡管理重在风险防控和立法，相关部门应高度重视预付卡市场管理工作，积极推进本市预付卡立法工作，因地制宜探索利用大数据、云计算、区块链等科技手段防控市场风险。","linkName":"","link":"","grade":4,"sort":"声音","createAt":"2018-08-25 15:14:53"}]
     * totalElements : 1
     * last : true
     * totalPages : 1
     * number : 0
     * size : 10
     * sort : null
     * first : true
     * numberOfElements : 1
     */

    private int totalElements;
    private boolean last;
    private int totalPages;
    private int number;
    private int size;
    private Object sort;
    private boolean first;
    private int numberOfElements;
    private List<ContentBean> content;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
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
         * id : 1
         * content : 【声音 | 北京市政协委员：利用区块链等科技防控市场风险】据北京日报报道，北京市政协组织部分委员围绕加强预付卡管理相关提案召开检查督促办理座谈会。委员们建议，预付卡管理重在风险防控和立法，相关部门应高度重视预付卡市场管理工作，积极推进本市预付卡立法工作，因地制宜探索利用大数据、云计算、区块链等科技手段防控市场风险。
         * linkName :
         * link :
         * grade : 4
         * sort : 声音
         * createAt : 2018-08-25 15:14:53
         */

        private int id;
        private String content;
        private String linkName;
        private String link;
        private int grade;
        private String sort;
        private String createAt;
        private long timestamp;
        private boolean isCollapsed = true;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isCollapsed() {
            return isCollapsed;
        }

        public void setCollapsed(boolean collapsed) {
            isCollapsed = collapsed;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }
    }
}
