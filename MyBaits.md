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



### Mybatis使用流程

MyBatis在项目中的使用流程：

【1】导包（MyBatis和数据库驱动的jar包）。

【2】编写mybatis-config.xml和xxxMapper.xml配置文件。

注意：[1]通常一个项目对应一个数据库实例、而一个数据库实例对应一个mybatis-config.xml配置文件，而项目中的一个模块对应一个xxxMapper.xml配置文件）。

[2] xxxMapper.xml配置文件中编写模块DAO层接口中的抽象方法的sql语句。每一个方法的实际sql类型对应一个标签（select、insert、update、delete），这样做只是为了将代码语义化，方便后期代码维护。

![img](https://pic4.zhimg.com/80/v2-71e4f153b7452876d0fa2abd20ea655b_720w.jpg)

【3】借助MyBatis封装好的SqlSessionFactoryBuilder对象的build方法构建一个数据库实例的sql会话对象（sqlSession）。



注意：build方法需要一个字节输入流（InputStream），是mybatis-config.xml总配置文件的字节输入流。

【4】通过sql会话对象的getMapper方法构建某一模块DAO层的代理对象（底层实现是动态代理）。

【5】通过代理对象调用模块DAO层接口中定义的方法。

Mybatis官方配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!-- dtd约束 -->
<!DOCTYPE configuration
        PUBLIC "-//MyBatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- 根元素: 用于配置MyBatis -->
<configuration>
    <!-- 配置MyBatis的运行环境 ,可以配置多个环境,但是一次只能使用一个
        default属性 : 当前使用的环境 ,使用下面环境的id 即可  -->
    <environments default="dev_mysql">
        <!-- 环境配置  id 属性,唯一标识当前环境 -->
        <environment id="dev_mysql">
            <!-- 配置MyBatis事务管理器
                type属性 :  事物类型
                    JDBC  使用事务(正常提交commit,异常回滚事务 rollback) 默认
                    MANAGED : 不使用事务
              -->
            <transactionManager type="JDBC"/>
            <!-- 配置MyBatis的数据源
                type : 配置连接池
                    POOLED :MyBatis内置的一个连接池(默认)
                    后期都交给spring管理了,配置 dbcp连接池,阿里巴巴的 druid连接池
             -->
            <dataSource type="POOLED">
                <!-- 连接数据库的操作 -->
                <!-- 数据库驱动 -->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <!-- 连接数据库的url -->
                <property name="url" value="jdbc:mysql://localhost:3306/users"/>
                <!-- 连接数据库账号 -->
                <property name="username" value="root"/>
                <!-- 连接数据库密码 -->
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <!-- 配置映射文件 -->
    <mappers>
        <!-- <mapper resource="org/cjw/mapper/UserMapper.xml"/>--> <!--指定一个配置文件-->
        <!--<package name="org.cjw.mapper"/>--> <!-- 指定一个包，包含多个配置文件或者配置类，推荐 -->
        <mapper class="org.cjw.mapper.UserMapper" /> <!-- 指定一个配置类 -->
    </mappers>
</configuration>
```





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
          PUBLIC "-//MyBatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <!-- 配置映射
  namespace : 命名空间(通俗说法: 给当前映射文件的唯一标识:起一个唯一的名字)
   -->
  <mapper namespace="org.cjw.mapper.UserMapper">
      <!-- 新增操作
          id: 当前功能的唯一标识,和接口方法同名
          parameterType ： 参数的类型
          useGeneratedKeys：是否返回数据库生成的主键 true是/false否
          keyProperty : 数据库主键对应java的pojo对象的属性
          keyColumn : 数据表的主键列名
       -->
      <insert id="insert" parameterType="org.cjw.pojo.User" keyProperty="id" keyColumn="id">
          insert into tb_user (name, age, email, password) values (#{name}, #{age}, #{email}, #{password});
      </insert>
  </mapper>
  ```
  
  
  
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



## 3. Log4j日志框架

### 简介

log4j是一个日志输出框架，就是用于输出日志的

### 优点

1. 通过级别输出日志 （调试、信息、警告、错误、致命异常）。

2. 可以指定输出到控制台，以及输出到文件。

3. 可以设置输出的日志格式。

### 配置步骤

1. 导入jar包

2. 在src下创建一个log4j.properties文件

   ```properties
   # Global logging configuration
   log4j.rootLogger=debug, stdout
   log4j.logger.org.cjw.mapper=TRACE // 需要修改为自己的包名
   # Console output...
   log4j.appender.stdout=org.apache.log4j.ConsoleAppender
   log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
   log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
   ```

   

## 4. Mybaits完成CRUD操作

1. 单行查询

   ```xml
   <select id="selectUserById" resultType="org.cjw.pojo.User">
       select * from tb_user where id = #{id}
   </select>



2. 多行查询

   ```xml
   <!-- 多行查询
      resultType : 无论是多行查询还是单行查询,返回的结果类型都是对应的JavaBean的类型
   -->
   <select id="selectAll" resultType="org.cjw.pojo.User">
       select * from tb_user
   </select>



3. 删除操作

   ```xml
   <delete id="deleteUserById">
       delete from tb_user where id = #{id}
   </delete>



4. 修改操作

   ```xml
   <update id="updateUserById" parameterType="org.cjw.pojo.User">
       update tb_user set
       name = #{name}, age = #{age}, email = #{email}, password = #{password}
       where id = #{id}
   </update>
   ```

   

  ## 5. ResultMap手动映射

MyBatis的查询结果集都是自动映射封装的，单行查询将数据库一条数据封装成对应的Java对象。多行查询，先将每一行封装成对象，再将每个对象添加到集合中，最后返回一个List集合对象。

但是：必须保证查询结果集和pojo对象的属性名相同，否则无法自动封装。

问题： 如何解决查询结果集名称和pojo对象属性不同的映射封装？

解决方案：

1. 使用手动映射封装 <resultMap>标签。

2. 可以使用MyBatis的驼峰命名法，但是也必须遵循一定规则才行。

```xml
<select id="selectUserById" resultMap="user_map">
    select id u_id, name u_name, age u_age, email u_email, password u_password from tb_user where id = #{id}
</select>

<resultMap id="user_map" type="User">
    <id property="id" column="u_id" />
    <result property="name" column="u_name"/>
    <result property="age" column="u_age"/>
    <result property="email" column="u_email"/>
    <result property="password" column="u_password"/>
</resultMap>
```



## 6. 主配置文件说明与细节配置

<environments>：环境集标签，就是用于配置数据库的连接信息的。

<environment>：用于配置具体环境参数。

<transactionManager>：配置使用的事务类型，JDBC。

<dataSource>：配置数据源的参数，POOLED。具体参数参看PooledDataSource的set方法。

<property>：配置属性

<mappers>:配置映射文件信息的

<mapper class|resource>:配置具体指定的mapper文件

class:配置使用注解时指定有注解的映射接口

resource:指定映射文件

<package name>:配置配置文件、配置类所在的包，推荐

<properties>:MyBatis读取classpath路径下配置文件，一般用于读取db.properties。

<typeAliases>:用于配置别名。

<typeAliase type alias>

type:指定取别名的全限定名

alias:别名

<package name>

name:指定取别名的包

<typeHandlers>:用于配置自定义类型处理器。

<settings>:配置MyBatis的默认设置的（开启二级缓存、驼峰命名自动映射）。

总配置文件的标签顺序

```text
<!ELEMENT configuration (properties?, settings?, typeAliases?, typeHandlers?, 
objectFactory?, objectWrapperFactory?, reflectorFactory?, plugins?, environments?, 
databaseIdProvider?, mappers?)>
```

这句话的意思就是configuration标签下的标签的**顺序**以及**标签出现的个数**的声明。



## 7. MyBatis注解开发

MyBatis的注解开发更简洁，**只需要将对应的SQL语句的注解标注对应的功能方法上即可**，直接连 XxxMapper.xml映射文件都可以省略了。

MyBatis提供了下面注解进行映射文件配置

@Select 查询数据注解

@Insert 插入数据注解

@Delete 删除数据注解

@Update 修改数据注解

@Options 选项配置

@Results 手动映射配置

@Result： @results中的具体的某一列的映射信息配置

```java
package org.cjw.mapper;

import org.apache.ibatis.annotations.*;
import org.cjw.pojo.User;

import java.util.List;

public interface UserMapper {

    /*
        使用MyBatis的动态代理开发编写代码遵循四个原则
        1.映射文件的namespace命名空间的值必须是对应接口的全限定名。
        2.映射文件的对应功能 id值必须等于映射接口中方法的名称。
        3.映射文件的参数类型必须和接口中方法的参数类型一致。
        4.映射文件查询的返回结果类型必须和接口的方法的返回数据类型一致，
        DML操作返回的受影响的行数，除外。
     */

    @Insert("insert into tb_user (name, age, email, password) values (#{name}, #{age}, #{email}, #{password})")
    void insert(User user);

    @Select("select id u_id, name u_name, age u_age, email u_email, password u_password  from tb_user where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "u_id"),
            @Result(property = "age", column = "u_age"),
            @Result(property = "name", column = "u_name"),
            @Result(property = "email", column = "u_email"),
            @Result(property = "password", column = "u_password")
    })
    User selectUserById(Integer id);

    @Select("select * from tb_user")
    List<User> selectAll();

    @Delete("delete from tb_user where id = #{id}")
    void deleteUserById(Integer id);

    @Update("update tb_user set age = #{age}, name = #{name}, email = #{email}, password = #{password} where id = #{id}")
    void updateUserById(User user);

}
```

注解映射的配置

```xml
<mappers>
    <!-- <mapper resource="org/cjw/mapper/UserMapper.xml"/>--> <!--指定一个配置文件-->
    <!--<package name="org.cjw.mapper"/>--> <!-- 指定一个包，包含多个配置文件或者配置类，推荐 -->
    <mapper class="org.cjw.mapper.UserMapper"/> <!-- 指定一个配置类 -->
</mappers>
```

**该映射类的xml配置文件不能放置到该映射类同包环境下，否则会报映射语句已经包含的异常**



## 8. 方法多参数传递使用-@param注解

**MyBatis默认情况下是不支持传入多个参数的，只能传入一个参数。**

所谓的传入参数指的是MyBatis操作(<insert><delete><update><select>)的传入参数。

方案1：将这些参数封装到一个对象里面（JavaBean/Map），再传入。

方案2：给参数设置一个@Param注解支持，而且多参数的类型要统一。



问题：为什么不支持多个参数?

因为Java语法1.7以前，是不能通过反射技术获得方法的参数名的。

解决方案使用**@Param** 参数注解。

```java
@Select("select * from tb_user where name = #{name} and password = #{password}")
User login(User user);

@Select("select * from tb_user where name = #{name} and password = #{password}")
User login2(Map<String, Object> map);

@Select("select * from tb_user where name = #{name} and password = #{password}")
User login3(@Param("name") String name, @Param("password") String password);
```



## 9. #{}与${}的区别

在MyBatis框架中支持两种 OGNL语法

\#{}

${}

\#{}基于JDBC的PreparedStatement类，SQL语句参数使用?占位符，在运行阶段动态设置参数，但是?不能作为表名。

预编译语句对象的SQL语句只能操作DML和DQL 语句，不能操作DDL语句。

1.#{}表示设置预编译的参数,就是?的参数,所以如果要不固定的表名不能使用#{},只能使用${}

2.${}直接把值输出来,直接把参数拼接到SQL语句中.而#{}是使用?来代替. 所以${}是不安全的。

3.${}只能获得参数池的值,而#{}可以获得方法的参数值,也可以获得参数池的值,如果使用${}获得参数的值,这个参数必须要加上@Param。

如果非必要情况,不要使用${}。

问题：那么${}有什么用呢？

答：注意基于JDBC的接口的原来的表名是不可以使用?的，?只能用于传入的参数。

如果操作的涉及表名这些非参数的数据时，需要使用${}。



## 10. 总结

主要内容：MyBatis持久层框架的基本使用



1. 为什么要学习某一个框架？

   (1) 以前有类似功能，在编写代码有什么缺陷，在开发中带来什么不便。

   (2) 如：原生的JDBC 所有操作都要开发者自己处理，非常繁琐，特别是结果集的处理。

2. 寻找能够解决此类问题的框架

   (1) 持久层解决方案

   MyBatis - 目前企业主流

   1) 半自动框架

   a. 开发者可以自定义SQL语句，但是不用写实现（交给框架做），比较灵活。

   Hibernate -- 早期项目用的比较多

   2) 全自动框架

   a. 直接操作对象，开发者没有办法直接控制sql语句。

   ③ JPA Java官方推出的一种持久化API

   1) 由Hibernate框架来实现。

   ④ SpringData-JPA

   1) 支持多种类型的数据持久化解决方案

   a. 关系数据库（MySQL，Oracle）。

   b. 非关系型数据库 （NoSQL，MongDB，Redis）。

   c. 操作大数据框架。

   ⑤ Spring框架的 jdbctemplate

   1) 是Spring框架的一个功能，只是比jdbc强一点点，对jdbc进行了简单的封装。

3. Web层解决方案 -Servlet不好用

   ① SpringMVC - Spring框架的一个模块（组件）-企业主流

   ② Struts2-前几年用的多，有严重bug。

   ③ Struts -古老的。

4. MyBatis框架学习方案

   (1) 了解框架的特性。

   (2) 框架的入门案例。

   ① 直接建议参考官方文档- 一手资料

   1) 需要有一定的编码基础

   ② 论坛博客

   ③ 老师授课

5. 具体学习步骤

   1. 框架执行流程
      - 对框架执行图解分析。
      - 具体分布细节-环境准备
      - 创建项目导入依赖包
      - 准备主配置文件MyBatis-config.xml。
      - 配置数据库连接四要素db.properties。
      - 编写MyBatisUtil工具类封装公共代码。
      - 配置文件的读取-SqlSessionFactory对象创建。
      - 提供返回SqlSession 会话操作对象的方法。

   2. 入门案例

      - 创建操作表对应的实体Pojo对象 User。

      - 创建表操作的接口文件 UserMapper.java。

      - 创建接口对应的映射文件 UserMapper.xml。

      - 主配置文件引入 UserMapper.xml映射文件。

      - 代码编写

      - 接口中写具体增删改查方法。

        

   3. 映射文件写对应方法的功能标签。
      - a. <insert><delete><select><update>。
      - b. 标签有属性，属性的配置和接口的方法都遵循一定映射规则。
      - c. 方法返回类型和 resultType类型相同。
      - d. 方法参数和属性 parameterType 相同。
      - e.方法名称和 id 相同。

   4. 编写测试代码

      1) 所有操作都必须有SqlSession对象。

      2)创建SqlSession ，通过 MyBatisUtil工具直接提供方法直接获取。

      3)创建 UserMapper代理对象。

      4)执行CRUD（增删改查）具体方法。

6. MyBatis框架的细节学习配置

   ① 主配置细节配置

   1) 别名 typeAliases typeAliase (type, alias)。

   2) 数据库配置文件的读取 properties (resource)。

   ② MyBatis的结果集手动映射 resultMap,id,result。

   (5) MyBatis的注解配置

   ① @Insert @Update @Delete @Select

   ② @Results @Result @Options

   ③ 方法多参数注解 @Param

   (6) #{}和${}区别



## 11. 动态sql语句

相对与固定SQL语句。根据参数不同组成不同结构的SQL语句。这种根据参数的不同而不同的SQL语句，我们称为动态SQL语句。

### 11.1 动态sql语句作用

1. 根据条件组装不同结构的SQL语句，可以提高SQL代码的重用性。

2. 满足某些特定需求，如：条件判断查询

### 11.2 实现方式

- 基于映射文件
- 基于注解

### 11.3 基于映射文件的实现

#### 11.3.1 标签包括

<sql>：用于声明SQL语句块，在操作标签中通过<include>标签引入。

<if>：类似java if(){}，用于判断。

<foreach>：类似java的foreach循环，一般用于批量处理的SQL语句，如批量更新、插入、删除。

<trim>：切割标签，主要用于切割关键字的头和尾的字符。新版Mybatis的使用几率很少。

<set>：使用 set标签就是SQL语言的set关键字，可以在update 的时候，设置set 关键字后面的更新字段，逗号可以自动忽略。

<where>：使用where标签作为SQL语言的where关键字，如果where后面的条件都不成立,忽略where关键字。

<choose> <when><otherwise> : java的if else if... else。

接口文件

```java
package org.cjw.mapper;

import org.apache.ibatis.annotations.Param;
import org.cjw.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 根据条件查询结果
     * @param user
     * @return
     */
    List<User> selectByCondition(User user);

    /**
     * 根据条件查询结果总数
     * @param user
     * @return
     */
    Long selectTotalByCondition(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUserByNotNull(User user);

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids")Integer[] ids);

    /**
     * 批量插入
     * @param users
     * @return
     */
    int insertByBatch(@Param("users")List<User> users);
}
```

映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.cjw.mapper.UserMapper">

    <!-- 多行查询
   resultType : 只是返回的当行数据封装的对象类型，无论单行还是多行查询都必须返回对应的实体类型User
   由于配置文件中已经设置了别名，所以这里使用别名
   -->
    <select id="selectByCondition" parameterType="User" resultType="User">
        <!--
            select * from tb_user where name like concat('%',#{name},'%') or age = #{age}
            上述SQL语句的语义来说，是一个静态SQL语句，一开始已经确定SQL的语义
            不管有没有数据，都会对全部数据进行修改，如果某一个数据没有，name会自动设置为null
            不符合实际场景

            解决方案： 使用MyBatis的动态SQL语句
         -->

        select * from tb_user
        <!--
           <include refid=""></include>
           包含引入sql片段
               refid ：被引入的sql片段的id值
        -->
        <include refid="condition_sql"/>

    </select>

    <select id="selectTotalByCondition" parameterType="User" resultType="Long">
        select count(1) from tb_user
        <include refid="condition_sql"/>
    </select>

    <!--
        <sql id=""></sql>
        抽取sql片段
            id属性：片段的唯一标识，以供其他地方使用
     -->
    <sql id="condition_sql">
        <!-- 动态SQL语句
            <where>标签
                在where内部编写条件，
                1，如果只要满足一个条件<where>标签会自动拼接 WHERE 关键字拼接上对应的条件
                2，如果条件前面有 OR|AND 关键字，但是是第一个条件，那么会自动删除出这个关键字，以保证语法正确
                3，如果一个条件都没有，那么就相当于查询所有数据
             -->
        <where>
            <if test="name != '' and name != null">
                name like concat('%', #{name}, '%')
            </if>
            <if test="age != '' and age != null">
                and age = #{age}
            </if>
            <if test="email != '' and email != null">
                and email = #{email}
            </if>
            <if test="password != '' and password != null">
                and password = #{password}
            </if>
        </where>

        <!--
            另一种写法是使用trim标签：
            <trim>标签，开发者可以自定义条件，既可以指定where条件也可以指定set关键字条件

            <trim prefix="WHERE" prefixOverrides="AND | OR">
            prefix : 前缀，
                当前如果是条件就用  WERHE
                如果使用修改就用  SET
            prefixOverrides ：在 WHERE 关键字后面的第一个条件，如果是 AND|OR 会自动去掉
         -->

        <!--<trim prefix="WHERE" prefixOverrides="AND|OR">
            <if test="name != '' and name != null">
                name like concat('%', #{name}, '%')
            </if>
            <if test="age != '' and name != null">
                and age = #{age}
            </if>
            <if test="email != '' and email != null">
                and email = #{email}
            </if>
            <if test="password != '' and password != null">
                and password = #{password}
            </if>
        </trim>-->
    </sql>

    <update id="updateUserByNotNull" parameterType="User">
        <!--
            update tb_user set name = #{name}, password= #{password}, age = #{age} where id = #{id}
            上述SQL语句的语义来说，是一个静态SQL语句，一开始已经确定SQL的语义
            不管有没有数据，都会对全部数据进行修改，如果某一个数据没有，name会自动设置为null
            不符合实际场景

            解决方案： 使用MyBatis的动态SQL语句，<set>标签、<trim>标签
       -->
        <!-- set标签会自动的过滤掉多余的逗号 -->
        <!--update tb_user
        <set>
            <if test="name != '' and name != null">name = #{name},</if>
            <if test="age != '' and age != null">age = #{age},</if>
            <if test="email != '' and email != null">email = #{email},</if>
            <if test="password != '' and password != null">password = #{password}</if>
        </set>
        where id = #{id}-->

        <!--
            prefix : 前缀，
           当前如果是 条件就用  WERHE
           如果使用修改 就用  SET
           prefixOverrides ：如果在 WHRE 关子健 后面的第一个条件，如果是 AND|OR 会自动去掉
           suffixOverrides :如果是最后一个条件， 如果是多余的逗号（，） 会自动去掉
         -->
        update tb_user
        <trim prefix="SET" suffixOverrides=",">
            <if test="name != '' and name != null">name = #{name},</if>
            <if test="age != '' and age != null">age = #{age},</if>
            <if test="email != '' and email != null">email = #{email},</if>
            <if test="password != '' and password != null">password = #{password}</if>
        </trim>
        where id = #{id}
    </update>

    <delete id="deleteByIds" parameterType="List">
        <!-- delete from tb_user where id in (1,2,3) -->
        <!--
            <foreach collection="" open="" close="" item="" separator="">标签体内容</foreach>
                MyBatis的for循环标签
                collection：循环集合
                open：起始括号(
                close：结束括号 )
                item：集合每次循环的数据对应的变量
                separator：分割符号： (1,2,3) 数值与数值之间的分隔符 ，逗号
         -->
        delete from tb_user
        where id in
        <foreach collection="ids" item="id" open="(" close=")" separator=",">
            #{id}
        </foreach>
    </delete>

    <insert id="insertByBatch">
        insert into tb_user (name, age, email, password)
        values
        <foreach collection="users" item="user" separator=",">
            (#{user.name}, #{user.age}, #{user.email}, #{user.password})
        </foreach>
    </insert>
</mapper>
```



### 11.4  基于注解的实现

动态sql除了支持xml方式以外，还支持使用纯注解的方式。

五个注解对应动态sql语句的类文件。

**1.**  **@SelectProvider** 动态查询SQL语句对应注解。

**2.** **@InsertProvider** 动态插入SQL语句对应注解。

**3.** **@UpdateProvider** 动态修改SQL语句对应注解。

**4.** **@DeleteProvider** 动态删除SQL语句对应注解。

**5.** **@Param** 动态获取参数



接口映射文件

```java
package org.cjw.mapper;

import org.apache.ibatis.annotations.*;
import org.cjw.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 根据条件查询结果
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectByCondition")
    List<User> selectByCondition(User user);

    /**
     * 根据条件查询结果总数
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectTotalByCondition")
    Long selectTotalByCondition(User user);

    /**
     * 修改用户,参数不为空的数据才会修改
     *
     * @param user
     * @return
     */
    @UpdateProvider(type = UserProvider.class, method = "updateUserByNotNull")
    int updateUserByNotNull(User user);

    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    @DeleteProvider(type = UserProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") Integer[] ids);

    /**
     * 批量插入
     *
     * @param users
     * @return
     */
    @InsertProvider(type = UserProvider.class, method = "testInsertByBatch")
    int insertByBatch(@Param("users") List<User> users);
}


```

动态sql语句文件

```java
package org.cjw.mapper;

import org.apache.ibatis.annotations.*;
import org.cjw.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 根据条件查询结果
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectByCondition")
    List<User> selectByCondition(User user);

    /**
     * 根据条件查询结果总数
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectTotalByCondition")
    Long selectTotalByCondition(User user);

    /**
     * 修改用户,参数不为空的数据才会修改
     *
     * @param user
     * @return
     */
    @UpdateProvider(type = UserProvider.class, method = "updateUserByNotNull")
    int updateUserByNotNull(User user);

    /**
     * 批量删除用户
     * 在程序运行过程中已经不存在ids参数，需要如果想要使用ids这个参数中包含的数据时，需要使用@Param注解来标记参数
     * 因为在JDK1.7之前，不能通过反射获取方法参数名
     * @param ids
     * @return
     */
    @DeleteProvider(type = UserProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") Integer[] ids);

    /**
     * 批量插入
     * 在程序运行过程中已经不存在users参数，需要如果想要使用users这个参数中包含的数据时，需要使用@Param注解来标记参数
     * 因为在JDK1.7之前，不能通过反射获取方法参数名
     * @param users
     * @return
     */
    @InsertProvider(type = UserProvider.class, method = "testInsertByBatch")
    int insertByBatch(@Param("users") List<User> users);
}
package org.cjw.mapper;

import org.apache.ibatis.annotations.*;
import org.cjw.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 根据条件查询结果
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectByCondition")
    List<User> selectByCondition(User user);

    /**
     * 根据条件查询结果总数
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectTotalByCondition")
    Long selectTotalByCondition(User user);

    /**
     * 修改用户,参数不为空的数据才会修改
     *
     * @param user
     * @return
     */
    @UpdateProvider(type = UserProvider.class, method = "updateUserByNotNull")
    int updateUserByNotNull(User user);

    /**
     * 批量删除用户
     * 在程序运行过程中已经不存在ids参数，需要如果想要使用ids这个参数中包含的数据时，需要使用@Param注解来标记参数
     * 因为在JDK1.7之前，不能通过反射获取方法参数名
     * @param ids
     * @return
     */
    @DeleteProvider(type = UserProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") Integer[] ids);

    /**
     * 批量插入
     * 在程序运行过程中已经不存在users参数，需要如果想要使用users这个参数中包含的数据时，需要使用@Param注解来标记参数
     * 因为在JDK1.7之前，不能通过反射获取方法参数名
     * @param users
     * @return
     */
    @InsertProvider(type = UserProvider.class, method = "testInsertByBatch")
    int insertByBatch(@Param("users") List<User> users);
}
```

### 11.5 静态sql语句与动态sql语句的区别

静态SQL语句编码简单，但是灵活性不足。

动态SQL语句编码稍微复杂，但是灵活性强。

xml文件通过where、set、if、trim、sql、include标签来拼接动态SQL语句。

注解通过SQL类或原生Java代码来拼接动态SQL语句。

无论xml文件还是注解拼接动态SQL语句时，都需要使用OGNL语法来拼接，即xxx = #{XXX}的形式或者#{XXX}形式，切勿直接把参数值直接拼接到动态SQL上，否则会出现运行问题。

动态SQL语法仅仅只是拼接了SQL语句，在拼接完SQL语句后可以将其看做静态SQL语句，所以静态SQL语句的规范，动态SQL语句也需要遵循，即OGNL语法。

xml文件形式的动态SQL语句的拼接在XML文件中完成。

注解形式的动态SQL语句的拼接在一个java类的方法中完成。



## 12 缓存

在Mybatis里面,所谓的缓存就是将已经查询过的记录放在内存的缓冲区或文件上,这样如果再次查询，可以通过配置的策略，命中已经查询过的记录，从而提高查询的效率。因为将数据库表中的记录读取到内存中需要经过IO，而IO操作的效率是很低，在开发中应该尽量避免IO操作，所以衍生出缓存机制。

缓存作用：提高查询的效率。



### 12.1 一级缓存

级缓存：所谓的一级缓存就是会话(SqlSesion对象)级别的缓存，同一个会话中，如果已经查询过的记录会保存一份在内存中，如果会话没有关闭，再次调用同样的查询方法，不会再查询数据库，而是直接从缓存中取出之前查询的记录（类似get请求的缓存机制）。

一级缓存默认是打开的，而且是关闭不了的。

如何清空一级缓存：

1.关闭会话close()。

2.进行了DML操作，提交了事务commit()。

3.手动清除缓存clearCache()。



### 12.2 二级缓存

一级缓存是SqlSession对象级别，在每一次会话中有效。

二级缓存是 **SqlSessionFactory**级别，在整个应用都有效，可以在多个会话有效。

MyBatis本身并没有实现二级缓存二级缓存需要第三方缓存提供商的支持：

Ehcache -第三方缓存(Hibernate框架默认就是支持)



## 13 Mybatis的对象关系映射

在实际开发中，一个业务可能可能需要查询多个数据表，而多表查询就涉及连接查询(等值连接)。等值连接：表与表之间有一个外键关联。我们都知道一个表对应一个POJO对象，但是**对象与对象之间是没有外键关系的，对象和对象之间只有依赖关系。**

```
对象之间关系主要是四种：
一对一 关系  one2one
一个人对应一个身份证号，一个QQ号对应一个QQ空间。
一对多 关系  one2many
    一个部门包含多个员工
多对一 关系  many2one
   多个员工属于一个部门
多对多 关系  many2many   
   多个老师对应多个学生,多个学生选择多个课程

什么关系应该从哪个对象作为中心点来看
一对多, 以one方作为中心点
 
MyBatis框架支持多表查询封装对象之间关系，主要使用collection和associatiion标签。
 
<collection> 一对多查询
<association>多对一和一对一查询
```

### 13.1 基于xml配置的关系映射

#### 13.1.1 多对一查询

一对一查询是多对一查询的特例，使用同一套代码。

##### 13.1.1.1 N+1方式

N+1 ： N 就是当前需要查询结果对应发送的SQL语句的条数

+1 ：关联查询数据需要额外多发一条SQL语句才能查询出对应的结果

POJO

```java
package org.cjw.pojo;

public class Employee {

    private Long id;
    private String name;
    // 以员工为中心：多个员工对应一个部门，多对一关系，many2noe
    private Department dept;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
package org.cjw.pojo;

public class Department {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```

映射接口

```java
apackage org.cjw.mapper;

import org.cjw.pojo.Department;

public interface EmployeeMapper {

    Department selectEmployeeByPrimaryKey(Long id);
}
```

xml配置文件

```xmL
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.cjw.mapper.EmployeeMapper">

 <!-- 查询主表一条sql -->
 <select id="selectEmployeeByPrimaryKey" resultMap="emp_map">
 select * from employee where id = #{emp_id}
 </select>
 
    <resultMap id="emp_map" type="Employee">
        <id property="id" column="id"/>
        <result property="name" column="name" />
 <!--
            association标签的作用：将主表某一列的值作为查询条件查询副表，并将查询结果封装成对象返回
                properties属性：最后映射的对象名
                column属性：副表查询条件
         -->
 <association property="dept" column="dept_id" select="org.cjw.mapper.EmployeeMapper.selectDeptById" />
    </resultMap>

 <!-- 查询副表N条sql -->
 <select id="selectDeptById" resultType="Department">
 select * from department where id = #{dept_id}
 </select>
</mapper>
```

#### 13.1.1.2 等值连接查询

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.cjw.mapper.EmployeeMapper">

 <!-- 查询主表一条sql -->
 <select id="selectEmployeeByPrimaryKey" resultMap="emp_map">
 select e.id e_id, e.name e_name, d.id d_id, d.name d_name from employee e left join department d on e.dept_id = d.id where e.id = #{emp_id}
 </select>

    <resultMap id="emp_map" type="Employee">
        <id property="id" column="e_id"/>
        <result property="name" column="e_name" />
        <collection property="dept" ofType="Department">
            <id property="id" column="d_id" />
            <result property="name" column="d_name" />
        </collection>
    </resultMap>
</mapper>
```



### 13.2 基于注解的关系映射实现

```java
package org.cjw.mapper;

import org.apache.ibatis.annotations.*;
import org.cjw.pojo.Department;
import org.cjw.pojo.Employee;

public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "dept", column = "dept_id", many = @Many(select = "org.cjw.mapper.EmployeeMapper.selectDeptById"))
    })
    Employee selectEmployeeByPrimaryKey(Long id);

    @Select("select * from department where id = #{dept_id}")
    Department selectDeptById(Long id);

}
```



