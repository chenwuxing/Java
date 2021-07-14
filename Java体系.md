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

   

**这些框架也大量使用了动态代理，而动态代理的实现也依赖反射**

```java
public class DebugInvocationHandler implements InvocationHandler {
    /**
     * 代理类中的真实对象
     */
    private final Object target;

    public DebugInvocationHandler(Object target) {
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("before method " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("after method " + method.getName());
        return result;
    }
}

```



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
  
    底层数据结构：双向链表
  
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
