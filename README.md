## 百讯Parent

## 目标
1. 快速开发
2. 统一集成

## 介绍

主要分为以下几个模块

- basic:基础功能模块,引入common-lang3工具类,线程工厂,统一返回结果以及异常信息；
- spring-boot-starter: SpringBoot依赖包，依赖了Spring-boot-starter,此处主要是对SpringBoot的增强
- spring-boot-starter-db: mybatis-plus 的封装，增加租户拦截器以及自动填充
- spring-boot-starter-es: es的封装，增加es工具类
- spring-boot-starter-rpc: feign 的拦截器处理，以及注入了RestTemplate的Bean,并配置负载君恨
- spring-boot-starter-swagger: 依赖swagger包，并扫描配置的路径。
- spring-boot-starter-web: springboot-web依赖，自动配置异常拦截
- spring-boot-starter-redis: redis的封装，提供统一key前缀是项目命，spring.application.name，同时提供分布式锁实现（可用注解和编程类）和申明式缓存
- spring-boot-starter-log: 日志处理。暂时未实现
- spring-boot-starter-prometheus: prometheus的实现，提供自动暴露endpoint,统一暴露prometheus,info,health
## 使用

1. 总体使用策略是：引入依赖包，增加yml配置项

2. 配置项说明：
```yaml
bx:
# 统一异常 拦截 默认开启
    exception:
      enable: true
# db:mybatis plus 配置
    mybatis-plus:
    # 租户隔离
      tenant:
    # 是否开启默认是关闭
        enable: true
    # 忽略表名 不区分大消息
        ignoreTables:
          - t_da
    # 忽略的mapperStatement
        ignoreMapperStatement:
          - com.aibaixun.mapper.TestMapper.selectById
    # 自动填充
      fill:
    # 开启填充  默认开启
        enable: false
    # insert填充  默认开启
        enableInsertFill: false
    # update填充  默认开启
        enableUpdateFill: false
    # 创建时间字段
        createTimeField:
    # 修改时间字段
        updateTimeField:
    # 数据库类型
      dbType:
    # es 连接池 配置
    elasticsearch:
      rest-pool:
    # 以下均为默认值  
      connectTimeOut: 10000
      socketTimeOut: 30000
      connectionRequestTimeOut: 500
      maxConnectNum: 30
      maxConnectPerRoute: 10
    # redis yml缓存配置
    cache:
      cacheManages:
          -
            key: mycahe
    # 默认秒
            second: 10
    # rpc restTemplate 配置  以下为默认项
      rest-template:
        maxTotal: 200
        maxPerRoute: 50
        readTimeout: 35000
        connectTimeout: 10000
    # swagger 配置
    swagger:
    # 开关
      enable: false
      title:
      description:
      version:
      basePackage:
      path:  
```
 
## 使用说明
暂无后期补充
