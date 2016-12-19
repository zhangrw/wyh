package com.banshion.portal.util.domain;

import java.util.List;

/**
 *
 */

public class JqgridResponseContext {
    protected static ThreadLocal<JqgridResponse> localResponse = new ThreadLocal<JqgridResponse>();

    private JqgridResponseContext() {

    }

    public static <T> JqgridResponse<T> getJqgridResponse() {
        JqgridResponse<T> jqgridResponse = localResponse.get();
        if (jqgridResponse == null) {
            jqgridResponse = new JqgridResponse<T>();
        }
        localResponse.remove();
        return jqgridResponse;
    }

    public static <T> JqgridResponse<T> getJqgridResponse(List<T> rows) {
        if(rows == null || rows.isEmpty() ){
            return new JqgridResponse<T>();
        }
        JqgridResponse<T> jqgridResponse = (JqgridResponse<T>) getJqgridResponse();
        return jqgridResponse.setRows(rows);
    }

}
