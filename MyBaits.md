# MyBaits

## 1.简介

### 什么是MyBaits

是一个支持普通SQL查询、存储过程以及高级映射的持久层框架，它消除了几乎所有的JDBC代码和参数的手动设置以及对结果集的检索，并使用简单的XML或注解进行配置和原始映射，用以将接口和Java的POJO（Plain Old Java Object，普通Java对象）映射成数据库中的记录，使得Java开发人员可以使用面向对象的编程思想来操作数据库

### 持久化

数据持久化

持久化就是将程序的数据在持久状态和瞬时状态转换的过程

### 持久层

Dao层，Service层，Controller层

- 完成持久化工作的代码块
- 层界限十分明显

### 为什么需要Mybatis

1. 传统的JDBC代码太复杂了
2. 帮助程序猿将数据存入到数据库中
3. 简单易学：本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。
4. 灵活：mybatis不会对应用程序或者数据库的现有设计强加任何影响。 sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
5. 解除sql与程序代码的耦合：通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql和代码的分离，提高了可维护性。
6. 提供映射标签，支持对象与数据库的orm字段关系映射
7. 提供对象关系映射标签，支持对象关系组建维护
8. 提供xml标签，支持编写动态sql。



## 2. 第一个Mybatis程序

思路：搭建环境-》导入mybatis->编写代码-》测试

### 2.1

1. 搭建数据库
2. 新建项目
   - 删除src目录
   - 导入maven依赖
3. 创建一个模块

### 2.2 创建一个模块

- 编写mybatis的核心配置文件：连接数据库
- 编写mybatis工具类

### 2.3 编写实体代码

- 实体类

  ```java
  package com.cwx.pojo;
  
  public class User {
  
  
      private int id;
      private String name;
      private String pwd;
  
      public User() {
      }
  
      public User(int id, String name, String pwd) {
          this.id = id;
          this.name = name;
          this.pwd = pwd;
      }
  
      public int getId() {
          return id;
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public String getPwd() {
          return pwd;
      }
  
      public void setPwd(String pwd) {
          this.pwd = pwd;
      }
  }
  
  ```

  

- DAO接口

  ```java
  package com.cwx.dao;
  
  import com.cwx.pojo.User;
  
  import java.util.List;
  
  public interface UserDao {
      List<User> getUserList();
  
  }
  
  ```

  

- DAO接口实现类

  接口实现类由原来的一个UserDaoImpl转为一个Mapper文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.cwx.dao.UserDao">
      <select id="getUserList" resultType="com.cwx.pojo.User">
          select * from mybatis.user
      </select>
  </mapper>
  ```



## 2.4 测试















  

