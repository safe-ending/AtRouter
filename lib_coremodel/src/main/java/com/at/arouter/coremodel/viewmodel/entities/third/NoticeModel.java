package com.at.arouter.coremodel.viewmodel.entities.third;

import java.io.Serializable;

/**
 * desc:  公告模型
 * author:  yangtao
 * <p>
 * creat: 2018/8/27 16:05
 */

public class NoticeModel implements Serializable {
    /**
     * "id": 6,
     * "title": "这是标题5",
     * "content": "这是通告5",
     * "createTime": "2018-09-05 14:55:08",
     * "startTime": "2018-09-03 09:00:00",
     * "expireTime": "2018-09-22 22:05:00",
     * "status": true,
     * "lang": true
     */

    public String id;
    public String title;
    public String content;
    public String createTime;
    public String startTime;
    public String expireTime;
    public boolean status;
    public boolean lang;
    public String brief;

}
