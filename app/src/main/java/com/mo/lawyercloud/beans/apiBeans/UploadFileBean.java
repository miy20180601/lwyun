package com.mo.lawyercloud.beans.apiBeans;

import java.io.Serializable;

/**
 * Created by Mohaifeng on 18/6/8.
 */
public class UploadFileBean implements Serializable{

    /**
     * src : http://solicitor.51feijin.com/store/avatar/1526263513505.jpg
     * name : 1526263513505.jpg
     */

    private String src;
    private String name;
    private String path; //path是word


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
