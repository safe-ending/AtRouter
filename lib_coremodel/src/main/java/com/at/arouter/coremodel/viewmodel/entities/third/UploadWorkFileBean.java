package com.at.arouter.coremodel.viewmodel.entities.third;

import java.io.Serializable;

/**
 * 作者 : Joker
 * 创建日期 : 2018/8/13
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class UploadWorkFileBean  implements Serializable {
    private String url;
    private String original;
    private String type;
    private String local;

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginal() {
        return original == null ? "" : original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocal() {
        return local == null ? "" : local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
