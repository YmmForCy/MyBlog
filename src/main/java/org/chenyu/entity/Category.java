package org.chenyu.entity;

import java.io.Serializable;

/**
 * Created by chenyu on 17-3-11.
 */
public class Category implements Serializable {
    private long id;
    private String name;
    private String displayName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
