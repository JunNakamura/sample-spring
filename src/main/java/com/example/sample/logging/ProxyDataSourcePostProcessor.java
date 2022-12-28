package com.example.sample.logging;

import com.example.sample.SampleApplication;
import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.NoOpQueryExecutionListener;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.listener.logging.Log4jQueryLoggingListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProxyDataSourcePostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ProxyDataSourcePostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            DataSource dataSourceBean = (DataSource) bean;
            return ProxyDataSourceBuilder.create(dataSourceBean)
                    .listener(logQueryListener())
                    .countQuery()
                    .build();
        }
        return bean;
    }

    QueryExecutionListener logQueryListener() {
        return new NoOpQueryExecutionListener(){
            @Override
            public void afterQuery(ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
                 List<String> callTree = Arrays.stream(Thread.currentThread().getStackTrace())
                        .filter(x -> x.getClassName().startsWith(SampleApplication.class.getPackage().getName()))
                        .map(x -> x.getClassName() + "." + x.getMethodName())
                        .skip(1) // skip call tree due to this class
                        .peek(logger::debug)
                        .collect(Collectors.toList());
                queryInfoList.stream().forEach(queryInfo -> {
                    logger.info("query: {} called by {}", queryInfo.getQuery(), callTree);
                });

            }
        };
    }



}
