package com.at.arouter.third.bridge.bean;

import com.at.arouter.common.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018/7/10
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class CommentDetailBean  extends BaseModel {
    /**
     * id : 23
     * member : 1111
     * nickName : user1
     * createTime : 2018-07-09 20:57:30
     * consult : null
     * content : 评论1
     * parent : null
     * child : [{"id":24,"member":"1112","nickName":"user2","createTime":"2018-07-09 20:58:16","content":"评论1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null},"child":[{"id":26,"member":"1114","nickName":"user4","createTime":"2018-07-09 21:01:45","content":"评论1-1-1-1","child":[{"id":73,"member":"1116","nickName":"user6","createTime":"2018-07-10 18:41:45","content":"10010101","child":[]},{"id":78,"member":"1117","nickName":"user7","createTime":"2018-07-10 18:44:28","content":"1111111","child":[{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]}]},{"id":73,"member":"1116","nickName":"user6","createTime":"2018-07-10 18:41:45","content":"10010101","child":[]},{"id":78,"member":"1117","nickName":"user7","createTime":"2018-07-10 18:44:28","content":"1111111","child":[{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]},{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]},{"id":25,"member":"1113","nickName":"user3","createTime":"2018-07-09 20:58:31","content":"评论1-1-1","parent":{"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null},"child":[]}]
     */

    private int id;
    private String member;
    private String nickName;
    private String createTime;
    private Object consult;
    private String content;
    private Object parent;
    private List<ChildBeanXX> child;

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

    public List<ChildBeanXX> getChild() {
        return child;
    }

    public void setChild(List<ChildBeanXX> child) {
        this.child = child;
    }

    public static class ChildBeanXX {
        /**
         * id : 24
         * member : 1112
         * nickName : user2
         * createTime : 2018-07-09 20:58:16
         * content : 评论1-1
         * parent : {"id":23,"member":"1111","nickName":"user1","createTime":"2018-07-09 20:57:30","content":"评论1","parent":null}
         * child : [{"id":26,"member":"1114","nickName":"user4","createTime":"2018-07-09 21:01:45","content":"评论1-1-1-1","child":[{"id":73,"member":"1116","nickName":"user6","createTime":"2018-07-10 18:41:45","content":"10010101","child":[]},{"id":78,"member":"1117","nickName":"user7","createTime":"2018-07-10 18:44:28","content":"1111111","child":[{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]}]},{"id":73,"member":"1116","nickName":"user6","createTime":"2018-07-10 18:41:45","content":"10010101","child":[]},{"id":78,"member":"1117","nickName":"user7","createTime":"2018-07-10 18:44:28","content":"1111111","child":[{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]},{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]
         */

        private int id;
        private String member;
        private String nickName;
        private String createTime;
        private String content;
        private ParentBean parent;
        private List<ChildBeanX> child;

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

        public List<ChildBeanX> getChild() {
            return child;
        }

        public void setChild(List<ChildBeanX> child) {
            this.child = child;
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

        public static class ChildBeanX {
            /**
             * id : 26
             * member : 1114
             * nickName : user4
             * createTime : 2018-07-09 21:01:45
             * content : 评论1-1-1-1
             * child : [{"id":73,"member":"1116","nickName":"user6","createTime":"2018-07-10 18:41:45","content":"10010101","child":[]},{"id":78,"member":"1117","nickName":"user7","createTime":"2018-07-10 18:44:28","content":"1111111","child":[{"id":83,"member":"11118","nickName":"user8","createTime":"2018-07-10 18:49:48","content":"111112222","child":[]}]}]
             */

            private int id;
            private String member;
            private String nickName;
            private String createTime;
            private String content;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 73
                 * member : 1116
                 * nickName : user6
                 * createTime : 2018-07-10 18:41:45
                 * content : 10010101
                 * child : []
                 */

                private int id;
                private String member;
                private String nickName;
                private String createTime;
                private String content;
                private List<?> child;

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

                public List<?> getChild() {
                    return child;
                }

                public void setChild(List<?> child) {
                    this.child = child;
                }
            }
        }
    }
}
