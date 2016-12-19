package com.banshion.portal.web.sys.domain;

/**
 * Created by root on 16-4-30.
 */
public class SysMenu {

    private String id;
    private String name;
    private int type;
    private String url;
    private String permission;
    private String parentId;
    private int menuOrder;
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getPermission() {
        return permission;
    }

    public String getParentId() {
        return parentId;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
