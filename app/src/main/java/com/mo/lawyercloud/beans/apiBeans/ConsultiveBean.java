package com.mo.lawyercloud.beans.apiBeans;

/**
 * @author cui
 * @date 2018/6/9/009
 * @annotation
 */
public class ConsultiveBean  {
    String id;
    String name;
    String pic;
    String state;
    String content;
    String time;

    public ConsultiveBean(String id, String name, String pic, String state, String content, String time) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.state = state;
        this.content = content;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
