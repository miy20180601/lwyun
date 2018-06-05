package com.mo.lawyercloud.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Mohaifeng on 18/5/29.
 */
public class SerializableMap implements Serializable {

    private Map<String,String> map;
    public Map<String,String> getMap()
    {
        return map;
    }
    public void setMap(Map<String,String> map)
    {
        this.map=map;
    }
}
