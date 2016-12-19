package com.banshion.portal.web.sys.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 16-5-5.
 */
public class MenuTreeNode {
    private String id ;
    private String name;
    private String url;
    private String code;
    private String parentId;
    private int type;
    private List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCode() {
        return code;
    }

    public String getId() {

        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MenuTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNode> children) {
        this.children = children;
    }

    public void addChildNode(MenuTreeNode node){
        this.getChildren().add(node);
    }

    public String toString() {
        String result = "[";
        result += this.getId()+",";
        result += this.getName()+",";
        result += this.getParentId()+",";
        result += this.getUrl()+",[";
        for (Iterator it = children.iterator(); it.hasNext();) {
            result += ((MenuTreeNode) it.next()).toString();
            result += ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }
}
