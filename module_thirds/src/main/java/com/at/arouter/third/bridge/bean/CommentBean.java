package com.at.arouter.third.bridge.bean;

import com.at.arouter.common.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018/7/9
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class CommentBean  extends BaseModel {
    /**
     * content : [{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","consult":null,"content":"评论1","parent":null,"child":[{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}},{"id":25,"member":"1113","nickName":"user3","createTime":"2018-07-09 20:58:31","content":"评论1-1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}},{"id":26,"member":"1114","nickName":"user4","createTime":"2018-07-09 21:01:45","content":"评论1-1-1-1","parent":{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}}},{"id":30,"member":"825","nickName":"Clown","createTime":"2018-07-10 10:31:46","content":"吃屎啦伱！","parent":{"id":26,"member":"1114","nickName":"user4","createTime":"2018-07-09 21:01:45","content":"评论1-1-1-1","parent":{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}}}}]},{"id":27,"member":"1115","nickName":"user5","createTime":"2018-07-09 21:03:00","consult":null,"content":"评论2","parent":null,"child":[]},{"id":28,"member":"564","nickName":"李昂","createTime":"2018-07-10 10:05:43","consult":null,"content":"滚滚滚滚滚滚","parent":null,"child":[]},{"id":29,"member":"5402","nickName":"苹果巧克力","createTime":"2018-07-10 10:26:24","consult":null,"content":"455","parent":null,"child":[]},{"id":31,"member":"5402","nickName":"苹果巧克力","createTime":"2018-07-10 10:41:16","consult":null,"content":"","parent":null,"child":[]}]
     * totalElements : 5
     * last : true
     * totalPages : 1
     * number : 0
     * size : 10
     * sort : null
     * first : true
     * numberOfElements : 5
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
         * id : 23
         * member : 1111
         * nickName : user1
         * createTime : 2018-07-09 20:57:30
         * consult : null
         * content : 评论1
         * parent : null
         * child : [{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}},{"id":25,"member":"1113","nickName":"user3","createTime":"2018-07-09 20:58:31","content":"评论1-1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}},{"id":26,"member":"1114","nickName":"user4","createTime":"2018-07-09 21:01:45","content":"评论1-1-1-1","parent":{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}}},{"id":30,"member":"825","nickName":"Clown","createTime":"2018-07-10 10:31:46","content":"吃屎啦伱！","parent":{"id":26,"member":"1114","nickName":"user4","createTime":"2018-07-09 21:01:45","content":"评论1-1-1-1","parent":{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}}}}]
         */

        private int id;
        private String member;
        private String nickName;
        private String createTime;
        private Object consult;
        private String content;
        private Object parent;
        private List<ChildBean> child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMember() {
            return member;
        }

        public void setMember(String member) {
            this.member = member;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getConsult() {
            return consult;
        }

        public void setConsult(Object consult) {
            this.consult = consult;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getParent() {
            return parent;
        }

        public void setParent(Object parent) {
            this.parent = parent;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * id : 24
             * member : 1112
             * nickName : user2
             * createTime : 2018-07-09 20:58:16
             * content : 评论1-1
             * parent : {"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}
             */

            private int id;
            private String member;
            private String nickName;
            private String createTime;
            private String content;
            private ParentBean parent;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMember() {
                return member;
            }

            public void setMember(String member) {
                this.member = member;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public ParentBean getParent() {
                return parent;
            }

            public void setParent(ParentBean parent) {
                this.parent = parent;
            }

            public static class ParentBean {
                /**
                 * id : 23
                 * member : 1111
                 * nickName : user1
                 * createTime : 2018-07-09 20:57:30
                 * content : 评论1
                 * parent : null
                 */

                private int id;
                private String member;
                private String nickName;
                private String createTime;
                private String content;
                private Object parent;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getMember() {
                    return member;
                }

                public void setMember(String member) {
                    this.member = member;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public Object getParent() {
                    return parent;
                }

                public void setParent(Object parent) {
                    this.parent = parent;
                }
            }
        }
    }
}
