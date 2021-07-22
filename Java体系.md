# Java体系

## java基础知识

### jvm vs jdk vs jre

![](E:\文档图片\Java 程序运行过程.png)

1. jvm的作用是运行java字节码的虚拟机，不同系统的jvm有自己的实现，目的是不同的jvm实现运行相同的字节码可以得到相同的结果，这是java程序可以跨平台的关键
2. jdk是 java development kit的缩写，是功能齐全的java sdk，包含jre拥有的一切，还包含javac和java doc以及jdb，它能够创建以及编译程序
3. jre是java runtime environment的缩写，它是包含java运行时程序所需所有内容的集合，包含jvm，java类库，java命令以及其他的一些工具

### 字符型常量与字符串常量的区别

1. 形式上字符型常量用单个引号括住的单个字符，而字符串常量则是双引号括住的多个字符
2. 含义：字符型常量相当于一个整形值（单个ASCII），而字符串常量代表一个地址值，表示常量在内存中的位置
3. 字符常量占两个字节，字符串常量则不定

### java中的关键字

![](E:\文档图片\QQ截图20210710134250.png)

### java泛型了解么？什么是类型擦除？介绍下常用的通配符？

java泛型提供了编译时类型安全检测机制，该机制允许程序员在编译时检测到非法的类型。泛型的本质是类型参数化，从而实现代码的复用，**java的泛型是伪泛型，因为在编译期间泛型信息被擦除了**

```java
List<Integer> list = new ArrayList<>();

list.add(12);
//这里直接添加会报错
list.add("a");
Class<? extends List> clazz = list.getClass();
Method add = clazz.getDeclaredMethod("add", Object.class);
//但是通过反射添加，是可以的
add.invoke(list, "kl");

System.out.println(list);

```

泛型一般有三种使用方式：泛型类、泛型接口、泛型方法

1. 泛型类

   ```java
   //此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
   //在实例化泛型类时，必须指定T的具体类型
   public class Generic<T> {
   
       private T key;
   
       public Generic(T key) {
           this.key = key;
       }
   
       public T getKey() {
           return key;
       }
   }
   
   ```

   如何具现化泛型类?

   ```java
   Generic<Integer> genericInteger = new Generic<Integer>(123456);
   ```

2. 泛型接口

   ```java
   public interface Generator<T> {
       public T method();
   }
   
   ```

   实现泛型接口，不指定类型

   ```java
   class GeneratorImpl<T> implements Generator<T>{
       @Override
       public T method() {
           return null;
       }
   }
   
   ```

   实现泛型接口，指定类型

   ```java
   class GeneratorImpl implements Generator<String>{
       @Override
       public String method() {
           return "hello";
       }
   }
   
   ```

3. 泛型方法

   ```java
   public static <E> void printArray(E[] inputArray) {
       for (E element : inputArray) {
           System.out.printf("%s ", element);
       }
       System.out.println();
   }
   
   ```

   常用的通配符为：T,E,K,V,?

   - ?代表不确定的类型
   - T代表type
   - E代表element
   - K，V代表Key Value

### ==和equals的区别

对于基本类型来说，==比较的是值，对于引用类型，==比较的是内存地址

**java只有值传递，对于==来说，不管比较的对象是基本类型还是引用类型，比较的都是值，因为引用类型变量存储的便是对象内存地址**

equals()作用不能作用于基本数据类型的变量，只能用于两个对象是否相等，equals()方法存在于Object类中

```java
public boolean equals(Object obj) {
     return (this == obj);
}
```

equals()使用分为两种情况

- 类没有覆盖了equals()方法

  会使用Object类的equals()方法，相当于使用“==”比较

- 类覆盖equals()方法

  会使用自定义的equals()方法

### hashCode && equals()

面试官可能会问你：“你重写过 `hashcode` 和 `equals`么，为什么重写 `equals` 时必须重写 `hashCode` 方法？”

如果两个对象相等，那么其hashcode值也是相等的，并且equals()也会返回true。但是两个对象hashCode值相等，也不能说明这两个对象相等，因此重写了equals()方法也需要重写hashCode方法。

**hashCode()方法默认是对堆上的对象产生独特值，如果没有重写hashCode()，那么堆上的两个对象怎么都不可能相等，即使两个对象指向相同的数据**

为什么两个对象有相同的hashCode值也可能不相等？

hashCode()使用哈希算法来得到某个值，对于多个不同对象可能计算得到相同的hashCode值，即发生碰撞，因此需要用equals()来判断是否真的相等。hashCode()相当于快速缩小范围的一种方法。

### 基本数据类型

#### java中的基本数据类型有哪些？对应的包装类型是啥？占几个字节？

java有8种基本数据类型

1. 6种数字类型：int,byte,double,float,short,long
2. 1种布尔类型：boolean
3. 1种字符类型：char

| 基本数据类型 | 位数 | 字节 | 默认值  | 对应的包装类型 |
| ------------ | ---- | ---- | ------- | -------------- |
| int          | 32   | 4    | 0       | Integer        |
| short        | 16   | 2    | 0       | Short          |
| long         | 64   | 8    | 0L      | Long           |
| byte         | 8    | 1    | 0       | Byte           |
| char         | 16   | 2    | ‘u0000' | Character      |
| float        | 32   | 4    | 0f      | Float          |
| double       | 64   | 8    | 0d      | Double         |
| boolean      | 1    |      | false   | Boolean        |

java基本类型的包装类的大部分都实现了常量池技术，Byte，Short，Integer，Long这4种包装类默认创建了数值[-128,127]的相应类型的缓存数据，Character创建了数值在[0,127]范围的缓存数据，Boolean直接返回True or False

Integer缓存源码

```java
/**

*此方法将始终缓存-128 到 127（包括端点）范围内的值，并可以缓存此范围之外的其他值。

*/

public static Integer valueOf(int i) {

    if (i >= IntegerCache.low && i <= IntegerCache.high)

      return IntegerCache.cache[i + (-IntegerCache.low)];

    return new Integer(i);

}

private static class IntegerCache {

    static final int low = -128;

    static final int high;

    static final Integer cache[];

}

```

Character缓存源码

```java
public static Character valueOf(char c) {

    if (c <= 127) { // must cache

      return CharacterCache.cache[(int)c];

    }

    return new Character(c);

}



private static class CharacterCache {

    private CharacterCache(){}



    static final Character cache[] = new Character[127 + 1];

    static {

        for (int i = 0; i < cache.length; i++)

            cache[i] = new Character((char)i);

    }

}

```

Boolean缓存源码

```java
public static Boolean valueOf(boolean b) {

    return (b ? TRUE : FALSE);

}

```

**所有整型包装类对象之间值的比较，全部使用equals方法比较**

![](E:\文档图片\20210422164544846.png)

### java是传值还是传引用？



### java重载与重写的区别

| 区别点     | 重载方法 | 重写方法                                                     |
| ---------- | -------- | ------------------------------------------------------------ |
| 发生范围   | 同一个类 | 子类                                                         |
| 参数列表   | 必须修改 | 一定不能修改                                                 |
| 返回类型   | 相同     | 子类方法返回值类型应比父类方法返回值类型更小或者相等         |
| 异常       | 可修改   | 子类方法声明抛出的异常类型应比父类方法声明抛出的异常类更小或相等 |
| 访问修饰符 | 可修改   | 不能做更严格的限制                                           |
| 发生阶段   | 编译期   | 运行期                                                       |



### 深拷贝 vs浅拷贝

1. 浅拷贝：对基本数据类型进行值传递，对引用类型进行引用传递般的拷贝，此为浅拷贝
2. 深拷贝：对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容，此为深拷贝



### String StringBuffer和StringBuilder的区别是什么？String为什么是不可变的？

#### 可变性

String类中使用final关键字修饰字符数组来保存字符串，private final char value[]，所以String对象是不可变的

StringBuilder与StringBuffer都继承自AbstractStringBuilder类，在AbstractStringBuilder中也是用字符数组保存字符串，但是没有用final关键字修饰，所以这两种对象都是可变的

`StringBuilder` 与 `StringBuffer` 的构造方法都是调用父类构造方法也AbstractStringBuilder`实现的

```java
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    /**
     * The value is used for character storage.
     */
    char[] value;

    /**
     * The count is the number of characters used.
     */
    int count;

    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }}

```

#### 线程安全性

String中的对象是不可变的，也就可以理解为常量，线程安全。StringBuffer对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。StringBuilder并没有对方法加同步锁，所以是非线程安全的。

#### 性能

每次对String类型进行改变的时候，都会生成一个新的String对象，然后将指针指向新的String对象，StringBuffer每次都会对StringBuffer对象本身进行操作，而不是生成新的对象并改变对象引用，相同情况下使用StringBuilder相比使用StringBuffer仅能获得10%~15%左右的性能提升，但却有多线程不安全的风险。

#### 三者使用总结

1. 操作少量数据，使用String
2. 单线程操作字符串缓冲区下大量数据：StringBuilder
3. 多线程操作字符串缓冲区下操作大量数据：StringBuffer

### Object类的常见方法总结

```java
public final native Class<?> getClass()//native方法，用于返回当前运行时对象的Class对象，使用了final关键字修饰，故不允许子类重写。

public native int hashCode() //native方法，用于返回对象的哈希码，主要使用在哈希表中，比如JDK中的HashMap。
public boolean equals(Object obj)//用于比较2个对象的内存地址是否相等，String类对该方法进行了重写用户比较字符串的值是否相等。

protected native Object clone() throws CloneNotSupportedException//naitive方法，用于创建并返回当前对象的一份拷贝。一般情况下，对于任何对象 x，表达式 x.clone() != x 为true，x.clone().getClass() == x.getClass() 为true。Object本身没有实现Cloneable接口，所以不重写clone方法并且进行调用的话会发生CloneNotSupportedException异常。

public String toString()//返回类的名字@实例的哈希码的16进制的字符串。建议Object所有的子类都重写这个方法。

public final native void notify()//native方法，并且不能重写。唤醒一个在此对象监视器上等待的线程(监视器相当于就是锁的概念)。如果有多个线程在等待只会任意唤醒一个。

public final native void notifyAll()//native方法，并且不能重写。跟notify一样，唯一的区别就是会唤醒在此对象监视器上等待的所有线程，而不是一个线程。

public final native void wait(long timeout) throws InterruptedException//native方法，并且不能重写。暂停线程的执行。注意：sleep方法没有释放锁，而wait方法释放了锁 。timeout是等待时间。

public final void wait(long timeout, int nanos) throws InterruptedException//多了nanos参数，这个参数表示额外时间（以毫微秒为单位，范围是 0-999999）。 所以超时的时间还需要加上nanos毫秒。

public final void wait() throws InterruptedException//跟之前的2个wait方法一样，只不过该方法一直等待，没有超时时间这个概念

protected void finalize() throws Throwable { }//实例被垃圾回收器回收的时候触发的操作

```



### 反射

#### 何为反射？

反射赋予了我们在运行时分析类以及执行类中方法的能力，通过反射可以获取任意一个类的所有属性和方法

#### 为什么需要反射？

- 反射让开发人员可以通过外部类的全路径名创建对象，并使用这些类，实现一些扩展的功能。
- 反射让开发人员可以枚举出类的全部成员，包括构造函数、属性、方法。以帮助开发者写出正确的代码。
- 测试时可以利用反射 API 访问类的私有成员，以保证测试代码覆盖率

#### 反射API

java类的成员包括属性字段、构造函数、方法，反射的API也是与这几个成员相关

- Field类：提供有关类的属性信息，以及对它的动态访问权限，它是一个封装反射类的属性的类
- Constructror类：提供有关类的构造方法的信息，以及对它的动态访问权限，它是一个封装反射类的构造方法的类
- Method类：提供关于类的方法的信息，包括抽象方法，它是用来封装反射类方法的一个类
- Class类：表示正在运行的java应用程序中的类的实例
- Object类：Object是所有java类的父类，所有对象都默认实现了Object类的方法



#### 反射的应用场景

正是因为反射，你才能这么轻松地使用各种框架。像 Spring/Spring Boot、MyBatis 等等框架中都大量使用了反射机制

#### 反射获取Class对象的4种方式

1. 知道具体类的情况下可以使用

   ```java
   Class alunbarClass = TargetObject.class;
   
   ```

2. 通过class.forName()传入类的路径

   ```java
   Class alunbarClass1 = Class.forName("cn.javaguide.TargetObject");
   
   ```

3. 通过对象实例来获取

   ```java
   TargetObject o = new TargetObject();
   Class alunbarClass2 = o.getClass();
   
   ```

4. 通过类加载器ClassLoader.loadClass()传入类路径获取

   ```java
   Class clazz = ClassLoader.loadClass("cn.javaguide.TargetObject");
   
   ```

   

反射示例

```java
// 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
Class studentClass = Class.forName("com.test.reflection.Student");
// 2.通过类的class属性
Class studentClass2 = Student.class;
// 3.通过对象的getClass()函数
Student studentObject = new Student();
Class studentClass3 = studentObject.getClass();

// 1.获取所有声明的字段
Field[] declaredFieldList = studentClass.getDeclaredFields();
for (Field declaredField : declaredFieldList) {
    System.out.println("declared Field: " + declaredField);
}
// 2.获取所有公有的字段
Field[] fieldList = studentClass.getFields();
for (Field field : fieldList) {
    System.out.println("field: " + field);
}

// 1.获取所有声明的构造方法
Constructor[] declaredConstructorList = studentClass.getDeclaredConstructors();
for (Constructor declaredConstructor : declaredConstructorList) {
    System.out.println("declared Constructor: " + declaredConstructor);
}
// 2.获取所有公有的构造方法
Constructor[] constructorList = studentClass.getConstructors();
for (Constructor constructor : constructorList) {
    System.out.println("constructor: " + constructor);
}

// 1.获取所有声明的函数
Method[] declaredMethodList = studentClass.getDeclaredMethods();
for (Method declaredMethod : declaredMethodList) {
    System.out.println("declared Method: " + declaredMethod);
}
// 2.获取所有公有的函数
Method[] methodList = studentClass.getMethods();
for (Method method : methodList) {
    System.out.println("method: " + method);
}

// 1.通过字符串获取Class对象，这个字符串必须带上完整路径名
Class studentClass = Class.forName("com.test.reflection.Student");
// 2.获取声明的构造方法，传入所需参数的类名，如果有多个参数，用','连接即可
Constructor studentConstructor = studentClass.getDeclaredConstructor(String.class);
// 如果是私有的构造方法，需要调用下面这一行代码使其可使用，公有的构造方法则不需要下面这一行代码
studentConstructor.setAccessible(true);
// 使用构造方法的newInstance方法创建对象，传入构造方法所需参数，如果有多个参数，用','连接即可
Object student = studentConstructor.newInstance("NameA");
// 3.获取声明的字段，传入字段名
Field studentAgeField = studentClass.getDeclaredField("studentAge");
// 如果是私有的字段，需要调用下面这一行代码使其可使用，公有的字段则不需要下面这一行代码
// studentAgeField.setAccessible(true);
// 使用字段的set方法设置字段值，传入此对象以及参数值
studentAgeField.set(student,10);
// 4.获取声明的函数，传入所需参数的类名，如果有多个参数，用','连接即可
Method studentShowMethod = studentClass.getDeclaredMethod("show",String.class);
// 如果是私有的函数，需要调用下面这一行代码使其可使用，公有的函数则不需要下面这一行代码
studentShowMethod.setAccessible(true);
// 使用函数的invoke方法调用此函数，传入此对象以及函数所需参数，如果有多个参数，用','连接即可。函数会返回一个Object对象，使用强制类型转换转成实际类型即可
Object result = studentShowMethod.invoke(student,"message");
System.out.println("result: " + result);

```



### 注解

#### 什么是注解？

注解是放在Java源码的类、方法、字段、参数前的一种特殊“注释”

#### 使用注解的三个步骤

1. 定义注解

   - @interface
   - 定义参数与默认值
   - 添加元注解

2. 使用

3. 读取并执行

   因为注解定义后也是一种`class`，所有的注解都继承自`java.lang.annotation.Annotation`，因此，读取注解，需要使用反射API



### 字符编码

计算机中储存的信息都是用二进制数表示的；而我们在屏幕上看到的英文、汉字等字符是二进制数转换之后的结果。通俗的说，按照何种规则将字符存储在计算机中，如'a'用什么表示，称为"编码"；反之，将存储在计算机中的二进制数解析显示出来，称为"解码"，如同密码学中的加密和解密。在解码过程中，如果使用了错误的解码规则，则导致'a'解析成'b'或者乱码

#### 字符集

是一个系统支持的所有抽象字符的集合。字符是各种文字和符号的总称，包括各国家文字、标点符号、图形符号、数字等。

#### 字符编码

是一套法则，使用该法则能够对自然语言的字符的一个集合（如字母表或音节表），与其他东西的一个集合（如号码或电脉冲）进行配对。即在符号集合与数字系统之间建立对应关系，它是信息处理的一项基本技术。

而以计算机为基础的信息处理系统则是利用元件（硬件）不同状态的组合来存储和处理信息的。元件不同状态的组合能代表数字系统的数字，因此字符编码就是将符号转换为计算机可以接受的数字系统的数，称为数字代码。

#### 常见字符集

ASCII字符集、GB2312字符集、BIG5字符集、GB18030字符集、Unicode字符集等。计算机要准确的处理各种字符集文字，需要进行字符编码，以便计算机能够识别和存储各种文字。





### 代理模式

- 静态代理
- 动态代理



### java IO

1. BIO(blocking IO) 属于同步阻塞IO

   ![](E:\文档图片\bio.JPG)

2. NIO(NO blocking IO)  属于IO多路复用模型，对于高负载、高并发的网络应用可以使用NIO

   ![](E:\文档图片\NIO.JPG)

   

   ![](E:\文档图片\IO多路复用.JPG)

3. AIO(Asynchronous IO) 属于异步IO模型，基于事件和回调机制实现

   ![](E:\文档图片\异步IO.JPG)

### 异常

#### java异常类层次结构图

![](E:\文档图片\Java异常类层次结构图.png)

![](E:\文档图片\Java异常类层次结构图2.png)

java中的异常都有一个共同的祖先:Throwable类，Throwable有两个重要的子类Exception(异常)和Error(错误)。Exception能被程序本身处理(try-catch)，Error是无法处理的。

- Exception：程序本身可以处理的异常，可以通过catch进行捕获。Exception又可以分为受检查异常(必须处理)和不受检查异常(可以不处理)
- Error：属于程序无法处理的错误

#### Throwable类常用方法

- public string getMessage()：返回异常发生时的简要描述
- public string toString():返回异常发生时的详细信息
- public string getLocalizedMessage():返回异常对象的本地化信息
- public void printStackTrace():在控制台上打印Throwable对象封装的异常信息

#### try-catch-finally

- try块：用于捕获异常，其后可接0个或多个catch块，如果没有catch块，必须跟一个finally块

- catch块：用于处理try捕获到的异常

- finally块：无论是否捕获或处理异常，finally块里的语句都会被执行，当在try块或catch块中遇到return语句时，finally语句块将在方法返回之前被执行

  以下三种特殊情况下finally块不会被执行

  - 在try或finally块中使用了System.exit(int)退出程序，但是如果System.exit(int)在异常语句之后，finally还是会被执行
  - 程序所在的线程死亡
  - 关闭cpu

#### 使用try-with-resource来代替try-catch-finally

面对必须要关闭的资源，我们总是应该使用try-with-resource而不是try-finally，随之产生的代码更简短



### lambda表达式

#### 什么是lambda表达式

函数式编程（Functional Programming）是把函数作为基本运算单元，函数可以作为变量，可以接收函数，还可以返回函数。历史上研究函数式编程的理论是Lambda演算，所以我们经常把支持函数式编程的编码风格称为Lambda表达式

#### 为什么要使用lambda表达式

1. 避免匿名内部类定义过多
2. 可以让你的代码看起来简洁
3. 去掉了一堆没有意义的代码，只留下核心的逻辑

#### 什么是函数式接口

任何接口，如果只包含唯一一个抽象方法，那么它就是一个函数式接口，对于函数式接口，我们可以通过lambda表达式来创建该接口的对象

#### 演进过程

1. 外部类
2. 静态内部类
3. 局部内部类
4. 匿名局部内部类
5. lambda





### I/O流

#### 什么是序列化？什么是反序列化？

如果我们需要持久化Java对象，比如将Java对象保存在文件中或在网络中传输Java对象，这些场景中都需要用到序列化

**序列化**：将数据结构或对象转换成二进制字节流的过程

**反序列化**：将在序列化过程中所生成的二进制字节流的过程转换成数据结构或对象



![](E:\文档图片\对象序列化.png)

#### 获取用键盘输入常用的两种方法

1. Scanner

   ```java
   Scanner input = new Scanner(System.in);
   String s  = input.nextLine();
   input.close();
   
   ```

2. BufferedReader

   ```java
   BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
   String s = input.readLine();
   
   ```

#### java中IO流分为几种

- 按照流的方向：输入流和输出流
- 操作单元：字节流和字符流
- 流的角色：节点流和处理流

java IO流都是从4个抽象类基类派生的

- InputStream/Reader：所有输入流的基类，前者是字节输入流，后者是字符输入流
- OutputStream/Writer：所有输出流的基类，前者是字节输出流，后者是字符输出流

按操作方式分类

![](E:\文档图片\IO-操作方式分类.png)

按操作对象分类

![](E:\文档图片\IO-操作对象分类.png)

#### 既然有了字节流，为什么还要有字符流

字符流是由jvm将字节转换得到的，方便对字符进行流操作，音频文件，图片等媒体文件用字节流比较好，如果涉及到字符的话使用字符流比较好

#### java关键字

- final用来修饰类、方法、变量

  - 修饰类表示该类不能被继承，final类中的所有方法都被隐式的指定为final方法
  - 修饰方法表示该方法不能被重写
  - 修饰基本数据类型的变量表示该变量的值不能被改动，修饰引用类型的变量表示该变量不能再引用其他对象

  使用final主要有两个原因，一个是final防止方法被继承类修改，第二个原因是提升效率

- static

  - 修饰成员方法和成员变量
  - 静态代码块，在非静态代码块之前执行，且不论生成多少个对象，只执行一次
  - 静态内部类
  - 静态导包，格式为import static 类中的静态资源

- this

- super用于从子类访问父类的变量和方法

### 容器

Java整体分为Collection容器和Map容器，Collection代表单个元素的集合，Map表示键值对集合

Collection 单个元素集合

- List 集合内元素有序、可重复 接口
  - ArrayList 数组实现的可扩容集合 实现类
  
    底层数据结构：object[]
  
  - LinkedList 链表实现的集合 实现类
  
    底层数据结构：双向链表 https://blog.csdn.net/huangfan322/article/details/52756441
  
  - Vector 实现类
    
    底层数据结构：object[]
    
    - Stack 弃用
- Set 集合内元素无序、不可重复 接口
  - HashSet 散列表实现 实现类
  
    底层数据结构：HashMap
  
  - LinkedHashSet 实现类
  
  - SortedSet 接口
  
    - TreeSet 红黑树实现 实现类
  
      底层数据结构：红黑树
- Queue 接口
  - PriorityQueue 优先队列 实现类
  - Deque 接口
    - ArrayDeque 实现类
    - LinkedList 实现类

Map 键值对集合

- HashMap 散列表实现，适合查找

  底层数据结构：数组+链表，jdk1.8之前都是数组与链表结合，之后的版本有了变化，会判断链表的长度，当链表的长度大于阈值（默认为8）（将链表扩展成红黑树之前会进行判断，如果数组的长度小于64，会优先对数组进行扩容，而不是将链表转换成红黑树）会转成红黑树，减少搜索时间

- HashTable 

  底层数据结构：数组+链表

- TreeMap 红黑树实现，适合遍历

  底层数据结构：红黑树



![](E:\文档图片\JAVA容器.png)





### 并发编程

#### 并发与并行的定义及区别

并发：同一时间段，多个任务都在执行 (单位时间内不一定同时执行)；

并行：单位时间内，多个任务同时执行

#### 程序，进程，线程

程序是指令以及数据的集合，进程是资源分配的基本单位，线程就是独立的执行路径，是cpu调度的基本单位，每个线程都在自己的工作内存交互，如果内存控制不当，可能会造成数据不一致

#### java线程的三种创建方式

1. Thread Class 实现了Runnable接口
   1. 自定义线程类继承Thread类
   2. 重写run()方法
   3. 创建线程对象，调用start()方法启动线程
2. Runnable接口
   1. 实现runnable接口
   2. 重写run方法
   3. 执行线程需要丢入runnable接口实现类，调用start方法
3. Callable接口
   1. 实现Callable接口，需要返回值类型
   2. 重写call方法，需要抛出异常
   3. 创建目标对象
   4. 创建执行服务：ExecutorService ser = Executors.newFixedThreadPool(1);
   5. 提交执行：Future < Boolean>  result1 = ser.submit(t1);
   6. 获取结果：boolean r1 = result.get()
   7. 关闭服务：ser.shutdownNow();

![](E:\文档图片\java线程创建方式.png)





#### 为什么要使用多线程？

先从总体上来说：

- **从计算机底层来说：** 线程可以比作是轻量级的进程，是程序执行的最小单位,线程间的切换和调度的成本远远小于进程。另外，多核 CPU 时代意味着多个线程可以同时运行，这减少了线程上下文切换的开销。
- **从当代互联网发展趋势来说：** 现在的系统动不动就要求百万级甚至千万级的并发量，而多线程并发编程正是开发高并发系统的基础，利用好多线程机制可以大大提高系统整体的并发能力以及性能

计算机底层来说：

- **单核时代：** 在单核时代多线程主要是为了提高 CPU 和 IO 设备的综合利用率。举个例子：当只有一个线程的时候会导致 CPU 计算时，IO 设备空闲；进行 IO 操作时，CPU 空闲。我们可以简单地说这两者的利用率目前都是 50%左右。但是当有两个线程的时候就不一样了，当一个线程执行 CPU 计算时，另外一个线程可以进行 IO 操作，这样两个的利用率就可以在理想情况下达到 100%了。
- **多核时代:** 多核时代多线程主要是为了提高 CPU 利用率。举个例子：假如我们要计算一个复杂的任务，我们只用一个线程的话，CPU 只会一个 CPU 核心被利用到，而创建多个线程就可以让多个 CPU 核心被利用到，这样就提高了 CPU 的利用率

#### 线程的生命周期与状态

Java 线程在运行的生命周期中的指定时刻只可能处于下面 5种不同状态的其中一个状态



![](E:\文档图片\线程状态.png)

![](E:\文档图片\线程状态与操作.png)

![](E:\文档图片\线程方法.png)

#### 停止线程

- 不推荐使用jdk提供的stop()，destroy()方法
- 推荐线程自己停下来
- 建议使用一个标志位进行终止变量，当flag=false，则终止线程运行

```java
public class TestStop implements Runnable{
//1.线程中定义线程体使用的标识
private boolean flag = true;

@override
public void run(){
// 2.线程体使用该标识
	while(flag){
	System.out.println("run");
	}
}

// 3.对外提供方法改变标识
public void stop(){
this.flag = false;
}
}
```



#### 线程休眠

![](E:\文档图片\线程休眠.png)

#### 线程礼让

![](E:\文档图片\线程礼让.png)

#### Join

![](E:\文档图片\join.png)

#### jvm线程状态

![](E:\文档图片\java虚拟机线程状态.png)

#### 线程优先级

优先级低只是意味着获得调度的概率低，并不是优先级低就不会被调用了，这都要看cpu调度

![](E:\文档图片\线程优先级.png)

#### 守护线程

Thread.setDaemon(true)

![](E:\文档图片\守护线程.png)

#### 线程同步机制

处理多线程问题时，多个线程访问同一对象，并且还有线程要对这个对象进行修改，这时候就需要线程同步，线程同步其实就是一种等待机制，多个需要同时访问此对象的线程进入这个对象的等待池形成队列，等待前面线程使用完毕，下一个线程再使用

- 由于同一进程的多个线程共享同一块存储空间，在带来方便的同时，也带来了访问冲突问题，为了保证数据在方法中被访问时的正确性，在访问时加入锁机制**synchronized**，当一个线程获得对象的排他锁，独占资源，其他线程必须等待，使用后释放锁即可，存在以下问题：
  - 一个线程持有锁会导致其他所有需要此锁的线程挂起
  - 在多线程竞争下，加锁，释放锁会导致比较多的上下文切换和调度延时，引起性能问你题
  - 如果一个优先级高的线程等待一个优先级低的线程释放锁，会导致优先级倒置，引起性能问题

#### java线程同步方法

- 由于我们可以通过private关键字保证数据对象只能被方法访问，所以我们只需要针对方法提出一套机制，这套机制就是synchronized关键字，它包括两种用法：synchronized方法和synchronized块
- synchronized方法控制对对象的访问，每个对象对应一把锁，每个synchronized方法多必须获得调用该方法的对象的锁才能执行，否则线程会被阻塞，方法一旦执行，就独占该锁，直到该方法返回才释放锁，后面被阻塞的线程才能获得这个锁，继续执行
- 缺陷：若将一个大的方法申明为synchronized将会影响效率，只有需要修改的部分才应该加锁

##### synchronized同步块

形式：synchronized(Obj){}

- Obj称之为同步监视器
  - Obj可以是任何对象，但是推荐使用共享资源作为同步监视器
  - 同步方法中无需指定同步监视器，因为同步方法的同步监视器就是this
- 同步监视器的执行过程：
  1. 第一个线程访问：锁定同步监视器，执行其中代码
  2. 第二个线程访问，发现同步监视器被锁定，无法访问
  3. 第一个线程访问完毕，解锁同步监视器
  4. 第二线程访问，发现同步监视器没有锁，然后锁定并访问

#### 死锁

死锁的必要条件

- 互斥
- 不可剥夺
- 请求与保持
- 循环等待

```java

class Mirror{

}

class Lipstick{

}

class MakeUp extends Thread{
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    int choice;
    String girlName;

    MakeUp(int choice,String girlName){
        this.choice = choice;
        this.girlName = girlName;
    }

    @Override
    public void run(){
        try{
            makeup();

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void makeup() throws InterruptedException{
        if(choice == 0){
            synchronized (lipstick){
                System.out.println(this.girlName + "获得镜子的锁");
                Thread.sleep(1000);
            }
            synchronized (mirror){
                System.out.println(this.girlName + "获得镜子的锁");
            }
        }
        else{
            synchronized (mirror){
                System.out.println(this.girlName + "获得镜子的锁");
                Thread.sleep(2000);
            }
            synchronized (lipstick){
                System.out.println(this.girlName + "获得口红的锁");
            }
        }
    }

}
public class DeakLock {
    public static void main(String[] args) {
        MakeUp makeUp1 = new MakeUp(0,"灰姑娘");
        MakeUp makeUp2 = new MakeUp(1,"白雪公主");
        makeUp1.start();
        makeUp2.start();
    }
}

```

#### Lock锁

- 从jdk5.0开始，java提供了更强大的线程同步机制-通过显示定义同步锁对象来实现同步，同步锁使用Lock对象充当
- java.util.concurrent.locks.Lock接口是控制多个线程对共享资源进行访问的工具，锁提供了对共享资源的独占访问，每次只有一个线程对Lock对象加锁，线程开始访问共享资源之前应该先获得Lock对象
- ReentrantLock类实现了Lock，它拥有与synchronized相同的并发性和内存语义，在实现线程安全的控制中，比较常用的是ReentrantLock，可以显示加锁，释放锁

synchronized与Lock的对比

- Lock是显示锁，synchronized是隐式锁，出了作用于自动释放

- Lock只有代码块锁，synchronized有代码块锁和方法锁

- 使用Lock锁，JVM将花费较少的时间来调度线程

- 优先使用顺序

   Lock>同步代码块>同步方法

#### 线程协作

##### 线程通信

应用场景：生产者消费者问题

- 假设仓库中只能存放一件产品，生产者将生产出来的产品放入仓库，消费者将仓库中产品取走消费
- 如果仓库中没有产品，则生产者将产品放入仓库，否则生产者停止生产并等待，直到仓库中的产品被消费者取走为止
- 如果仓库中放有产品，则消费者取走，否则停止消费并等待

分析：

这是一个线程同步问题，生产者和消费者共享同一个资源，并且生产者和消费者之间相互依赖，互为条件

- 对于生产者，没有生产产品之前，需要通知消费者等待，而生产了产品之后，又需要马上通知消费者消费
- 对于消费者，在消费之后，要通知生产者已经结束消费，需要生产新的产品以供消费
- 在生产者消费者问题中，仅有synchronized是不够的
  - synchronized可阻止并发更新同一个共享资源，实现了同步
  - 但无法用于线程通信

java线程通信方式

- wait
- wait(timeout)
- notify
- notify(all)

解决方式：

1. 管程法 缓冲区
2. 信号灯

#### 线程池

背景：经常创建和销毁、使用量特别大的资源，比如并发情况下的线程，对性能影响很大

思路：提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中，可以避免频繁创建销毁，实现重复利用

好处：

- 提高响应速度
- 降低资源消耗
- 便于线程管理
  - corePoolSize：核心池的大小
  - maximumPoolSize：最大线程数
  - keepAliveTime：线程没有任务时最多保持多长时间后会终止

**使用线程池**

- 线程池相关API：ExecutorService和Executors
- ExecutorService：真正的线程池接口，常见子类ThreadPoolExecutor
  - void execute(Runnable command):执行命令
  - Future:执行任务，有返回值
  - shutdown：关闭连接池
- Executors：工具类、线程池的工厂类，用于创建并返回不同类型的线程池

### JVM

### 框架

spring：https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core

Mybatis：https://mybatis.org/mybatis-3/zh/index.html

springmvc：

