package com.banshion.portal.util;

import com.banshion.portal.util.domain.BaseFilter;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 *
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {
	

    /**
     * 拦截后要执行的方法
     */

    @SuppressWarnings({"rawtypes", "unchecked", "unused"})
    public Object intercept(Invocation invocation) throws Throwable {
    	
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) Reflections.getFieldValue(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        Object obj = boundSql.getParameterObject();
        //String sqls = boundSql.getSql();

        if (obj instanceof BaseFilter) {
            BaseFilter baseFilter = (BaseFilter) obj;
            MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate,
                    "mappedStatement");

            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();
            StringBuffer sb = new StringBuffer();
            sb.append("select * from (").append(sql).append(" ) t where ") ;
            this.setTotalRecord(baseFilter, mappedStatement, connection);
            String pageSql = this.getPageSql(baseFilter, sql);
            
            Reflections.setFieldValue(boundSql, "sql", pageSql);
            BaseFilter.setLocal(baseFilter);
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

    /**
     * 给当前的参数对象page设置总记录数
     */
    private void setTotalRecord(BaseFilter filter, MappedStatement mappedStatement, Connection connection) {
        BoundSql boundSql = mappedStatement.getBoundSql(filter);
        String sql = boundSql.getSql();
        String countSql = this.getCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                parameterMappings, boundSql.getParameterObject());
        MetaObject metaParameters = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
        Reflections.setFieldValue(countBoundSql, "metaParameters", metaParameters);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, filter,
                countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                filter.setRecords(totalRecord);
                int rows = filter.getRows();
                // 计算总页数
                int total = totalRecord / rows;
                total = totalRecord % rows == 0 ? total : total + 1;
                filter.setTotal(total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql
     * @return
     */
    private String getCountSql(String sql) {
        return "select count (1) from (" + sql + ")";
        // int index = sql.indexOf("from");
        // return "select count(1) " + sql.substring(index);
    }

    /**
     * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
     *
     * @param filter 条件对象
     * @param sql    原sql语句
     * @return
     */
    private String getPageSql(BaseFilter filter, String sql) {
        Integer page = filter.getPage() < 1 ? 1 : filter.getPage();
        Integer rows = filter.getRows();
        StringBuffer sqlBuffer = new StringBuffer(sql);
        if (page == 1 && rows == 1) {
            sqlBuffer.insert(0, "select * from (").append(") where rownum=1 ");
        } else {
            int offset = (page - 1) * rows + 1;
            sqlBuffer.insert(0, "select result_temp_table.*, rownum row_now from (")
                    .append(") result_temp_table where rownum < ").append(offset + rows);
            sqlBuffer.insert(0, "select * from (").append(") where row_now >= ").append(offset);
        }
        return sqlBuffer.toString();
    }
}
