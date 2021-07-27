# SpringMVC

## 简述

SpringMVC就是一个Spring内置的MVC框架。

MVC框架，它解决WEB开发中常见的问题(参数接收、文件上传、表单验证、国际化等等)，而且使用简单，与Spring无缝集成。支持 RESTful风格的URL请求。

采用了松散耦合可插拔组件结构，比其他 MVC 框架更具扩展性和灵活性

## 作用

MVC模式(Model-View-Controller)：解决页面代码和后台代码的分离。

## 原理

在没有使用SpringMVC之前我们都是使用Servlet在做Web开发。但是使用Servlet开发在接收请求参数，数据共享，页面跳转等操作相对比较复杂。servlet是java进行web开发的标准，既然springMVC是对servlet的封装，那么很显然**SpringMVC底层就是Servlet，SpringMVC就是对Servlet进行深层次的封装**



## MVC模式

MVC分别是：模型model(javabean)、视图view(jsp/img)、控制器Controller(Action/servlet)。

C存在的目的就是为了保证M和V的一致性，当M发生改变时，C可以把M中的新内容更新到V中



## 执行流程

![](E:\文档图片\springmvc执行流程.png)

```
01、用户发送出请求被前端控制器DispatcherServlet拦截进行处理。
02、DispatcherServlet收到请求调用HandlerMapping（处理器映射器）。
03、HandlerMapping找到具体的处理器(查找xml配置或注解配置)，生成处理器对象及处理器拦截器(如果有)，再一起返回给DispatcherServlet。
04、DispatcherServlet调用HandlerAdapter（处理器适配器）。
05、HandlerAdapter经过适配调用具体的处理器（Handler/Controller）。
06、Controller执行完成返回ModelAndView对象。
07、HandlerAdapter将Controller执行结果ModelAndView返回给DispatcherServlet。
08、DispatcherServlet将ModelAndView传给ViewReslover（视图解析器）。
09、ViewReslover解析ModelAndView后返回具体View（视图）给DispatcherServlet。
10、DispatcherServlet根据View进行渲染视图（即将模型数据填充至视图中）。
11、DispatcherServlet响应View给用户。
```

涉及组件分析

```
1、前端控制器DispatcherServlet（不需要程序员开发）由框架提供，在web.xml中配置。
作用：接收请求，响应结果，相当于转发器，中央处理器。
 
2、处理器映射器HandlerMapping（不需要程序员开发）由框架提供。
作用：根据请求的url查找Handler（处理器/Controller），可以通过XML和注解方式来映射。
 
3、处理器适配器HandlerAdapter（不需要程序员开发）由框架提供。
作用：按照特定规则（HandlerAdapter要求的规则）去执行Handler中的方法。
 
4、处理器Handler（也称之为Controller，需要程序员开发）
注意：编写Handler时按照HandlerAdapter的要求去做，这样适配器才可以去正确执行Handler。
作用：接受用户请求信息，调用业务方法处理请求，也称之为后端控制器。
 
5、视图解析器ViewResolver（不需要程序员开发）由框架提供。
作用：进行视图解析，把逻辑视图解析成真正的物理视图。 
SpringMVC框架支持多种View视图技术，包括：jstlView、freemarkerView、ThymeleafView等。
 
6、视图View（需要工程师开发）
作用：把数据展现给用户的页面
View是一个接口，实现类支持不同的View技术（jsp、freemarker、pdf等）
```

