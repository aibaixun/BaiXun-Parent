package com.aibaixun.common.mybatisplus;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
public class BxtTenantLineInterceptor extends TenantLineInnerInterceptor {

    private final List<String> mapperStatements;

    public BxtTenantLineInterceptor(TenantLineHandler tenantLineHandler, List<String> mapperStatements) {
        super(tenantLineHandler);
        this.mapperStatements= mapperStatements;
    }


    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if(!mapperStatements.contains(ms.getId())){
            super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        if(!mapperStatements.contains(ms.getId())){
            super.beforePrepare(sh, connection, transactionTimeout);
        }
    }

}
