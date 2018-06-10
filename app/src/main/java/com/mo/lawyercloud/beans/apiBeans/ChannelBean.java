package com.mo.lawyercloud.beans.apiBeans;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * Created by Mohaifeng on 18/5/29.
 * 擅长领域
 */
public class ChannelBean implements IPickerViewData {

    /**
     * id : 1
     * name : 婚姻家事
     * icon : null
     */

    private int id;
    private String name;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
