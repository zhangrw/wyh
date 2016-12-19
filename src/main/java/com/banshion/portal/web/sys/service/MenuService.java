package com.banshion.portal.web.sys.service;

import com.banshion.portal.web.sys.domain.SysMenu;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.service.impl.MenuTreeNode;

import java.util.List;

/**
 * Created by root on 16-4-30.
 */
public interface MenuService {
    List<SysMenu> getAllMenu();

    List<SysMenu> getMenuByPID(String id);

    List<SysPermission> getAllPermission();

    Integer getOrderCount(String parentId);

    SysMenu checkNameExists(SysMenu menu);

    void save(SysMenu menu);

    void delete(String id);

    void update(SysMenu menu);

    void updateMenuOrder(String menuOrderParam);

    List<MenuTreeNode> getAllMenus();

    List<MenuTreeNode> getMenuByUserId(String userId);


}
