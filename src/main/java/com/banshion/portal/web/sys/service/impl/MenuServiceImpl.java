package com.banshion.portal.web.sys.service.impl;

import com.banshion.portal.exception.ServiceException;
import com.banshion.portal.web.sys.domain.SysMenuExample;
import com.banshion.portal.web.sys.domain.SysPermission;
import com.banshion.portal.web.sys.dao.SysMenuMapper;
import com.banshion.portal.web.sys.dao.SysPermissionMapper;
import com.banshion.portal.web.sys.domain.SysMenu;
import com.banshion.portal.web.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by root on 16-4-30.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysMenuMapper menumapper;

    @Autowired
    private SysPermissionMapper permissionmapper;

    public List<SysMenu> getAllMenu() {

        SysMenuExample me = new SysMenuExample();
        me.setOrderByClause(" menu_order asc");
        return menumapper.selectByExample(null);
    }

    public List<SysMenu> getMenuByPID(String id) {

        SysMenuExample se = new SysMenuExample();
        se.createCriteria().andParentIdEqualTo(id);
        se.setOrderByClause(" menu_order asc");
        return menumapper.selectByExample(se);
    }

    public List<SysPermission> getAllPermission() {
        // 此处可以增加适当到过滤条件
        return permissionmapper.selectByExample(null);
    }

    public Integer getOrderCount(String parentId) {

        menumapper.getOrderCount(parentId);

        return null;
    }

    public SysMenu checkNameExists(SysMenu menu) {
        return menumapper.checkNameExists(menu);
    }

    public void save(SysMenu menu) {
        try {
            menu.setId(UUID.randomUUID().toString());
            menumapper.insert(menu);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("保存菜单失败", e);
        }
    }

    public void delete(String id) {
        try {
            menumapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("删除菜单失败", e);
        }
    }

    public void update(SysMenu menu) {
        try {
            menumapper.updateByPrimaryKeySelective(menu);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("更新菜单失败", e);
        }
    }

    public void updateMenuOrder(String menuOrderParam) {
        try{
            String[] str = menuOrderParam.split(";");
            for(int i=0;i<str.length;i++){
                String[] menu = str[i].split(":");
                menumapper.updateMenuOder(menu[0], menu[1]);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ServiceException("更新排序失败！",e);
        }
    }

    public List<MenuTreeNode> getAllMenus() {
        try {

            SysMenuExample me = new SysMenuExample();
            me.setOrderByClause(" menu_order asc");
            List<SysMenu> list = menumapper.selectByExample(me);

            HashMap<String,MenuTreeNode> nodemap = new HashMap<String, MenuTreeNode>();

            for( SysMenu menu : list ){

                MenuTreeNode node = new MenuTreeNode();
                node.setId(menu.getId());
                node.setName(menu.getName());
                node.setParentId(menu.getParentId());
                node.setUrl(menu.getUrl());
                node.setType(menu.getType());
                nodemap.put(menu.getId() , node);

            }

            Set entrySet = nodemap.entrySet();

            MenuTreeNode root = null;

            for(Iterator it = entrySet.iterator(); it.hasNext();){
                MenuTreeNode node = (MenuTreeNode)((Map.Entry)it.next()).getValue();

                if( node.getParentId() == null || "".equals(node.getParentId())){
                    root = node;
                }else{
                    if( nodemap.get(node.getParentId()) != null ){
                        ((MenuTreeNode) nodemap.get(node.getParentId())).addChildNode(node);
                    }
                }
            }

            return root.getChildren();

        } catch (Exception e) {
            throw new ServiceException("查询菜单失败", e);
        }
    }

    public List<MenuTreeNode> getMenuByUserId(String userId) {
    try{
        List<SysMenu> list = menumapper.getMenuByUserId(userId);
        String[] idArr = new String[list.size()];

        for( int i = 0 ; i < list.size() ; i++ ){
            idArr[i] = list.get(i).getId();
        }

        list = menumapper.getMenuTreeById(idArr) == null ? list : menumapper.getMenuTreeById(idArr) ;

        HashMap<String,MenuTreeNode> nodemap = new HashMap<String, MenuTreeNode>();

        for( SysMenu menu : list ){
            MenuTreeNode node = new MenuTreeNode();
            node.setId(menu.getId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setUrl(menu.getUrl());
            node.setType(menu.getType());
            nodemap.put(menu.getId() , node);
        }

        Set entrySet = nodemap.entrySet();

        MenuTreeNode root = null;

        for(Iterator it = entrySet.iterator(); it.hasNext();){
            MenuTreeNode node = (MenuTreeNode)((Map.Entry)it.next()).getValue();

            if( node.getParentId() == null || "".equals(node.getParentId())){
                root = node;
            }else{
                if( nodemap.get(node.getParentId()) != null ){
                    ((MenuTreeNode) nodemap.get(node.getParentId())).addChildNode(node);
                }
            }
        }

        return root.getChildren();

    } catch (Exception e) {
        throw new ServiceException("查询菜单失败", e);
    }
    }


}
