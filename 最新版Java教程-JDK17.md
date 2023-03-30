[TOC]



# 第01章_Java语言概述

***

## 1. Java知识脉络图

### 1.1 Java基础全程脉络图

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/Java%E5%9F%BA%E7%A1%80%E5%85%A8%E7%A8%8B%E8%84%89%E7%BB%9C%E5%9B%BE.png" alt="Java基础全程脉络图" style="zoom: 80%;" />

### 1.2 本章专题与脉络

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC1%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E5%9F%BA%E6%9C%AC%E8%AF%AD%E6%B3%95-%E7%AC%AC01%E7%AB%A0.png" alt="第1阶段：Java基本语法-第01章" style="zoom: 45%;" />

## 6. Java语言概述

### 6.1 Java概述

- 是`SUN`(Stanford University Network，斯坦福大学网络公司 ) `1995年`推出的一门高级编程语言。

- 是一种面向Internet的编程语言。Java一开始富有吸引力是因为Java程序可以在`Web浏览器`中运行。这些Java程序被称为`Java小程序`（applet），内嵌在HTML代码中。

- 伴随着互联网的迅猛发展，以及Java技术在web方面的不断成熟，已经成为Web应用程序的首选开发语言。

### 6.2 Java语言简史

**起步阶段：**

1991年，Sun公司的工程师小组想要设计一种语言，应用在`电视机`、`电话`、`闹钟`、`烤面包机`等家用电器的控制和通信。由于这些设备的`处理能力`和`内存`都很有限，并且不同的厂商会选择不同的中央处理器(CPU)，因此这种语言的关键是`代码短小`、`紧凑`且`与平台无关`（即不能与任何特定的体系结构捆绑在一起）。

Gosling团队率先创造了这个语言，并命名为“`Oak`"（起名的原因是因为他非常喜欢自己办公室外的橡树)。后因智能化家电的市场需求没有预期的高，Sun公司放弃了该项计划。

随着20世纪90年代互联网的发展，Sun公司发现该语言在互联网上应用的前景，于是改造了Oak，于1995年5月以Java的名称正式发布。（Java是印度尼西亚`爪哇岛`的英文名称，因盛产咖啡而闻名。）

![image-20220309230839100](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220309230839100.png)

**发展阶段：**

|   发行版本    |                           发行时间                           |                             备注                             |
| :-----------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|   Java 1.0    |                          1996.01.23                          |             Sun公司发布了Java的第一个开发工具包              |
|   Java 1.1    | x 1/**2 * Twitter的SnowFlake算法,使用SnowFlake算法生成一个整数，然后转化为62进制变成一个短地址URL3 *4 * https://github.com/beyondfengyu/SnowFlake5 *6 * Snowflake生成的是Long类型的ID，一个Long类型占8个字节，每个字节占8比特，也就是说一个Long类型占64个比特。7 * Snowflake ID组成结构：正数位（占1比特）+ 时间戳（占41比特）+ 机器ID（占5比特）+ 数据中心（占5比特）+ 自增值（占12比特），总共64比特组成的一个Long类型。8 * ● 第一个bit位（1bit）：Java中long的最高位是符号位代表正负，正数是0，负数是1，一般生成ID都为正数，所以默认为0。9 * ● 时间戳部分（41bit）：毫秒级的时间，不建议存当前时间戳，而是用（当前时间戳 - 固定开始时间戳）的差值，可以使产生的ID从更小的值开始；41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年10 * ● 工作机器id（10bit）：也被叫做workId，这个可以灵活配置，机房或者机器号组合都可以。11 * ● 序列号部分（12bit），自增值支持同一毫秒内同一个节点可以生成4096个ID12 * 根据这个算法的逻辑，只需要将这个算法用Java语言实现出来，封装为一个工具方法，那么各个业务应用可以直接使用该工具方法来获取分布式ID，只需保证每个业务应用有自己的工作机器id即可，而不需要单独去搭建一个获取分布式ID的应用。13 */14public class SnowFlakeUtil {15​16    /**17     * 起始的时间戳18     */19    private final static long START_TIMESTAMP = 1480166465631L;20​21    /**22     * 每一部分占用的位数23     */24    private final static long SEQUENCE_BIT = 12;   //序列号占用的位数25    private final static long MACHINE_BIT = 5;     //机器标识占用的位数26    private final static long DATA_CENTER_BIT = 5; //数据中心占用的位数27​28    /**29     * 每一部分的最大值30     */31    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);  // 409532    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT); //33    private final static long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);34​35    /**36     * 每一部分向左的位移37     */38    private final static long MACHINE_LEFT = SEQUENCE_BIT;39    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;40    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;41​42    private long dataCenterId;  //数据中心43    private long machineId;     //机器标识44    private long sequence = 0L; //序列号45    private long lastTimeStamp = -1L;  //上一次时间戳46​47    /**48     * 获取到下一毫秒的时间戳49     *50     * @return long51     */52    private long getNextMill() {53        long mill = getNewTimeStamp();54        while (mill <= lastTimeStamp) {55            mill = getNewTimeStamp();56        }57        return mill;58    }59​60    /**61     * 获取到当前时间戳62     *63     * @return long64     */65    private long getNewTimeStamp() {66        return System.currentTimeMillis();67    }68​69    /**70     * 根据指定的数据中心ID和机器标志ID生成指定的序列号71     *  机器id总共占5位,低12位序号id12位.因此要左移12位72     *  数据中心id(机房id)占5位,低17位为机器id5位和序号id12位,因此要左移17位73     * @param dataCenterId 数据中心ID74     * @param machineId    机器标志ID75     */76    public SnowFlakeUtil(long dataCenterId, long machineId) {77        if (dataCenterId > MAX_DATA_CENTER_NUM \|\| dataCenterId < 0) {78            throw new IllegalArgumentException("DtaCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0！");79        }80        if (machineId > MAX_MACHINE_NUM \|\| machineId < 0) {81            throw new IllegalArgumentException("MachineId can't be greater than MAX_MACHINE_NUM or less than 0！");82        }83        this.dataCenterId = dataCenterId;84        this.machineId = machineId;85    }86​87    /**88     * 产生下一个ID89     * id号只有12位.同一毫秒内最多生产4096个.90     * 超过时,申请下一个毫秒值91     * @return92     */93    public synchronized long nextId() {94//        1.获取到当前时间戳95        long currTimeStamp = getNewTimeStamp();96//        2.如果当前时间戳小于上一次ID生成的时间戳,说明系统时钟回退过,这个时候应当抛出异常97        if (currTimeStamp < lastTimeStamp) {98            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");99        }100​101//        3.在同一毫秒内102        if (currTimeStamp == lastTimeStamp) {103//        4.相同毫秒内，序列号自增   MAX_SEQUENCE=4095(12位,序列号最大值)104//          sequence + 1 =4096时sequence为0(相当于4095&4096)105            sequence = (sequence + 1) & MAX_SEQUENCE;106            //同一毫秒的序列数已经达到最大107            if (sequence == 0L) {108//         5.获取下一个毫秒并赋值给当前毫秒109                currTimeStamp = getNextMill();110            }111        } else {112            //不同毫秒内，序列号置为0113            sequence = 0L;114        }115​116        lastTimeStamp = currTimeStamp;117​118        return (currTimeStamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分 低22位 数据中心id5位,机器标识id5位,序号id12位,因此左移22位119                \| dataCenterId << DATA_CENTER_LEFT       //数据中心部分120                \| machineId << MACHINE_LEFT             //机器标识部分121                \| sequence;                             //序列号部分122    }123​124    public static void main(String[] args) {125        SnowFlakeUtil snowFlake = new SnowFlakeUtil(2L, 3L);126​127        for (int i = 0; i < (1 << 4); i++) {128            //10进制129            System.out.println(snowFlake.nextId());130        }131    }132}133​java |        JavaOne会议召开，创当时全球同类会议规模之最。         |
|   Java 1.2    |                          1998.12.08                          |  Java拆分成：J2SE（标准版）、J2EE（企业版）、J2ME（小型版）  |
|   Java 1.3    |                          2000.05.08                          |                                                              |
|    Java1.4    |                          2004.02.06                          |                                                              |
| **Java 5.0**  |                        **2004.09.30**                        | **①版本号从1.4直接更新至5.0；②平台更名为JavaSE、JavaEE、JavaME** |
|   Java 6.0    |                          2006.12.11                          |               2009.04.20 Oracle宣布收购SUN公司               |
|               |                          2009.04.20                          |           Oracle公司收购SUN，交易价格`74亿`美元。            |
|   Java 7.0    |                          2011.07.02                          |                                                              |
| **Java 8.0**  |                        **2014.03.18**                        | **此版本是继Java 5.0以来变化最大的版本。是长期支持版本（LTS）** |
|   Java 9.0    |                          2017.09.22                          | ①此版本开始，**每半年更新一次**；②Java 9.0开始不再支持windows 32位系统 |
|   Java 10.0   |                          2018.03.21                          |                                                              |
| **Java 11.0** |                        **2018.09.25**                        |    **JDK安装包取消独立JRE安装包，是长期支持版本（LTS）**     |
|   Java 12.0   |                          2019.03.19                          |                                                              |
|      ...      |                             ...                              |                                                              |
| **Java17.0**  |                         **2021.09**                          |    **发布Java 17.0，版本号也称为21.9，是长期支持版本。**     |
|      ...      |                             ...                              |                                                              |
|   Java19.0    |                           2022.09                            |               发布Java19.0，版本号也称为22.9。               |

### 6.3 Java之父

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310152923961.png" alt="image-20220310152923961" style="zoom:67%;" />

- `詹姆斯·高斯林(James Gosling)`先生以“Java 技术之父”而闻名于世。他是Java 技术的创始人，他亲手设计了Java语言，并开发了Java编译器和Java虚拟机，使Java成为了世界上最流行的开发语言。
- James Gosling于1984 年加入Sun公司，并一直服务于Sun公司，直至2010年前后，Sun被Oracle并购而加入Oracle，担任客户端软件集团的首席技术官; 2010年4月从Oracle离职。

### 6.4 公司八卦

- **SUN 与 Oracle**

> SUN是一家`极具创新能力`的公司，2001年 “9.11”以前，SUN公司市值`超过1000亿`美元。 但是没能利用Java构建一个强有力、可变现的生态系统，没打好Java这张牌。此后，互联网泡沫破裂，硬件需求大幅减少，它的市值在一个月之内跌幅超过90%。**SUN公司的成长用了20年，而衰落只用了1年！**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220309230923533.png" alt="image-20220309230923533" style="zoom: 67%;" />

- **Oracle 与 Google**

> Google和Oracle的侵权事件：
>
> 2010 年 8 月，Oracle 起诉 Google 的 Android 系统侵权，要求赔偿 26 亿美元。
>
> - Oracle认为Google的代码中使用了Java的`37个API`，并且认为Google是故意为之，因为这样做的好处是可以让更多的Java程序员更容易接受Android的代码。
> - Oracle认为Android 中有`9行代码`直接抄袭了Java的实现。这9行牛气哄哄的代码都出自一人之手，他就是Java 大牛------`Joshua Bloch`。
>
> 2018 年 3 月，美国联邦巡回上诉法院裁决，谷歌侵犯了甲骨文的版权，支付高达`88亿美元`的赔偿金。
>
> 2021 年 4 月，美国最高法院给出了最终裁决：谷歌胜诉，其代码属于“合理使用”的范畴。为期十多年的软件行业“第一版权案”落幕。

```java
//Code In OpenJDK / Android ：
1. private static void rangeCheck(int arrayLen, int fromIndex, int toIndex) {
2.    if (fromIndex > toIndex)
3.        throw new IllegalArgumentException("fromIndex(" + fromIndex +
4.                   ") > toIndex(" + toIndex+")");
5.    if (fromIndex < 0)
6.        throw new ArrayIndexOutOfBoundsException(fromIndex);
7.    if (toIndex > arrayLen)
8.        throw new ArrayIndexOutOfBoundsException(toIndex);
9. }
```

![image-20221027145908490](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221027145908490.png)

### 6.5 Java技术体系平台

- **Java SE(Java Standard Edition)标准版**
  - 支持面向`桌面级应用`（如Windows下的应用程序）的Java平台，即定位个人计算机的应用开发。
  - 包括用户界面接口AWT及Swing，网络功能与国际化、图像处理能力以及输入输出支持等。
  - 此版本以前称为J2SE
- **Java EE(Java Enterprise Edition)企业版**
  - 为开发企业环境下的应用程序提供的一套解决方案，即定位`在服务器端的Web应用开发`。
  - JavaEE是JavaSE的扩展，增加了用于服务器开发的类库。如：Servlet能够延伸服务器的功能，通过请求-响应的模式来处理客户端的请求；JSP是一种可以将Java程序代码内嵌在网页内的技术。
  - 版本以前称为J2EE
- **Java ME(Java Micro Edition)小型版**
  - 支持Java程序运行在`移动终端（手机、机顶盒）上的平台`，即定位在消费性电子产品的应用开发
  - JavaME是JavaSE的内伸，精简了JavaSE 的核心类库，同时也提供自己的扩展类。增加了适合微小装置的类库：javax.microedition.io.*等。
  - 此版本以前称为J2ME

![image-20211218093009884](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211218093009884.png)

> 注意：
>
> Android开发不等同于Java ME的开发

## 7. Java开发环境搭建（掌握）

### 7.1 什么是JDK、JRE

- **JDK**  (`J`ava `D`evelopment `K`it)：是Java程序开发工具包，包含`JRE` 和开发人员使用的工具。
- **JRE ** (`J`ava `R`untime `E`nvironment) ：是Java程序的运行时环境，包含`JVM` 和运行时所需要的`核心类库`。

如下是Java 8.0 Platform：

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/JDKJRE.jpg)

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310200731185.png" alt="image-20220310200731185" style="zoom:67%;" />

> 小结：
>
> JDK = JRE + 开发工具集（例如Javac编译工具等）
>
> JRE = JVM + Java SE标准类库

### 7.2 JDK版本选择

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221108221057540.png" alt="image-20221108221057540" style="zoom:80%;" />

***

![image-20220310201541841](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310201541841.png)

- 自Java 8版本发布以来，其后的每次更新，都会有小伙伴高呼：**Java8 YYDS！**
- 论坛的声音：“`你发任你发，我用Java 8！`”

**数据说话1：**

JRebel 于2022年4月前后发布了《2022 年Java生态系统状况报告》，报告中提到使用Java11 的占比最多，Java 8 紧随其后，如下图。而此前2020年的报告显示，Java8占比达到了`84.48%`。

![d315](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/d315.jpeg)

> 我的分析：
>
> G1是最受欢迎的GC算法。Java 11及更高版本的G1收集器是默认的GC，而Java 8中并不是。出于对G1的喜爱，很多开发者才会选择抛弃Java 8。

**数据说话2：**

此外，某美国软件开发商在对近千名专业的Java开发者调研后，发布的《2022年Java开发者生产力报告》称：八年前发布的Java 8依然是Java中应用最广泛的版本，占比 `37%`，其次是 Java 11，占比`29%` 。

**高斯林说话：**

***

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220428151952049.png" alt="image-20220428151952049" style="zoom: 67%;" />

**Spring框架说话：**

在Java 17正式发布之前，Java开发框架Spring率先在官博宣布，Spring Framework 6和Spring Boot 3计划在2022年第四季度实现总体可用性的高端基线：

- Java 17+(来自 Spring Framework 5.3.x 线中的 Java 8-17)


- Jakarta EE 9+（来自Spring框架5.3.x 线中的 Java EE 7-8）

Spring 官方说明：`https://spring.io/blog/2022/01/20/spring-boot-3-0-0-m1-is-now-available`

![image-20220527155439683](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527155439683.png)

> 意味着：springboot3.0 是需要用java17和spring6.0为基础建设。如果从企业选型最新springboot3.0作为架构来说，它搭配jdk17肯定是标配了。

### 7.3 JDK的下载

* 下载网址（Oracle公司官网）：www.oracle.com
* 下载步骤：如图所示，在官网底部选择Developers开发者

![1572254490435](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E4%B8%8B%E8%BD%BD1.png)

（1）在Developers页面中间的技术分类部分，选择`Java`，单击进入，如图所示：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211019110551338.png" alt="image-20211019110551338" style="zoom: 50%;" />

（2）这里展示的是最新Java版本，例如Java17。单击`Download Java`，然后选择具体的版本下载。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211019110843394.png" alt="image-20211019110843394" style="zoom:80%;" />

（3）选择Download Java按钮后

![image-20211019111110242](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211019111110242.png)

（4）如果想安装Java8 可以选择如下位置：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211019111150970.png" alt="image-20211019111150970" style="zoom:80%;" />

（5）选择Accept License Agreement，

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211019111252989.png" alt="image-20211019111252989" style="zoom:80%;" />

（6）注册或登录后下载：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211019111922387.png" alt="image-20211019111922387" style="zoom:50%;" />

（7）如果需要安装其它版本，可以选择Java archive：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801004606762.png" alt="image-20220801004606762" style="zoom: 67%;" />

接着进行选择下载即可：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801004648997.png" alt="image-20220801004648997" style="zoom:67%;" />

### 7.4 JDK的安装

- 安装说明
  - 傻瓜式安装，下一步即可。
  - **建议：安装路径不要有中文或者空格等特殊符号。**
  - **本套课程会同时安装JDK8 和 JDK17，并以JDK17为默认版本进行讲解。**

* 安装步骤：

（1）双击`jdk-17_windows-x64_bin.exe`文件，并单击下一步，如图所示：

![image-20220303083718546](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220303083718546.png)

（2）修改安装路径，单击更改，如图所示：

![image-20220303083759546](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220303083759546.png)

（3）将安装路径修改为`D:\develop_tools\jdk\jdk-17.0.2\`，并单击下一步，如图所示：

![image-20220303083845794](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220303083845794.png)

（4）稍后几秒，安装完成，如图所示：

![image-20220303083905277](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220303083905277.png)

![image-20220303083931309](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220303083931309.png)

### 7.5 配置path环境变量

#### 7.5.1 理解path环境变量

**什么是path环境变量？**

答：window操作系统执行命令时，所要搜寻的路径。

**为什么配置path？**

答：希望在命令行使用javac.exe等工具时，任意目录下都可以找到这个工具所在的目录。

**以JDK为例演示**

我们在`C:\Users\songhk`目录下使用javac命令，结果如下：

![image-20220310223852720](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310223852720.png)

我们在JDK的安装目录的bin目录下使用javac命令，结果如下：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607113708022.png" alt="image-20220607113708022" style="zoom:75%;" />

我们不可能每次使用java.exe，javac.exe等工具的时候都进入到JDK的安装目录下，太麻烦了。这时就需要配置path环境变量。

#### 7.5.2 JDK8配置方案1：只配置path

* 步骤：

（1）打开桌面上的计算机，进入后在左侧找到`此电脑`，单击鼠标`右键`，选择`属性`，如图所示：

![image-20220310224844837](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310224844837.png)

（2）选择`高级系统设置`，如图所示：

![image-20220310224912273](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310224912273.png)

（3）在`高级`选项卡，单击`环境变量`，如图所示：

![image-20220310224952745](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310224952745.png)

（4）在`系统变量`中，选中`Path` 环境变量，`双击`或者`点击编辑` ,如图所示：

![image-20220310225023208](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310225023208.png)

（5）点击`新建`，填入`D:\develop_tools\jdk\jdk1.8.0_271\bin`  ，并将此值`上移`到变量列表的首位。如图所示：

- 编辑模式1：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114102135.png" alt="image-20220607114102135" style="zoom:80%;" />

- 编辑模式2：（注意，结尾需要有英文模式下的;）

![image-20220929104547925](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220929104547925.png)

（6）环境变量配置完成，**重新开启**DOS命令行，在任意目录下输入`javac` 或`java`命令或`java -version`，运行成功。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114432286.png" alt="image-20220607114432286" style="zoom:80%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114517652.png" alt="image-20220607114517652" style="zoom:90%;" />



#### 7.5.3 JDK8配置方案2：配置JAVA_HOME+path（推荐）

* 步骤：

（1）打开桌面上的计算机，进入后在左侧找到`计算机`，单击鼠标`右键`，选择`属性`，如图所示：

![image-20220310224844837](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310224844837.png)

（2）选择`高级系统设置`，如图所示：

![image-20220310224912273](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310224912273.png)

（3）在`高级`选项卡，单击`环境变量`，如图所示：

![image-20220310224952745](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310224952745.png)

（4）在`系统变量`中，单击`新建` ，创建新的环境变量，如图所示：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310225245208.png" alt="image-20220310225245208" style="zoom:90%;" />

（5）变量名输入`JAVA_HOME`，变量值输入 `D:\develop_tools\jdk\jdk1.8.0_271` ，单击`确定`，如图所示：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114800372.png" alt="image-20220607114800372" style="zoom:80%;" />

（6）选中`Path` 环境变量，`双击`或者`点击编辑` ，如图所示：

![image-20220310225023208](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310225023208.png)

（7）在变量值的最前面，键入`%JAVA_HOME%\bin`。如图所示：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114953758.png" alt="image-20220607114953758" style="zoom:80%;" />

> 注意：强烈建议将%JAVA_HOME%\bin声明在path环境变量中所有变量的最前面！

（8）环境变量配置完成，**重启**DOS命令行，在任意目录下输入`javac` 或`java`命令或`java -version`，运行成功。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114432286.png" alt="image-20220607114432286" style="zoom:80%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607114517652.png" alt="image-20220607114517652" style="zoom:90%;" />



> 我想说：
>
> 有的书籍、论坛、视频上还提到配置classpath，用于指名class文件识别的路径。其实是没必要的，反而建议大家如果配置了classpath环境变量，要删除。对于初学者，反而不友好。

**小结如下：**

![image-20220607115255562](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607115255562.png)

#### 7.5.4 JDK17配置方案：自动配置

不管大家有没有提前安装JDK8或其它版本JDK，在我们安装完JDK17之后，理应按JDK8的方式配置path环境变量。但是，我们发现在安装完JDK17以后，配置环境变量之前，执行CMD指令：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607115700870.png" alt="image-20220607115700870" style="zoom:80%;" />

竟然成功了！而且是17.0.2版本。因为JDK17在安装之后，自动进行了环境变量的配置。如下：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220607115840388.png" alt="image-20220607115840388" style="zoom:80%;" />

这里建议，将JDK17安装的路径，设置为JAVA_HOME，并将`%JAVA_HOME%\bin`上移到首位。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221110142710972.png" alt="image-20221110142710972" style="zoom:80%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221110142734279.png" alt="image-20221110142734279" style="zoom:80%;" />

> 思考：如果你仍然希望在JDK8下开发Java程序？如何做呢？

## 8. 开发体验：HelloWorld（掌握）

JDK安装完毕，我们就可以开发第一个Java程序了，习惯性的称为：HelloWorld。

### 8.1 开发步骤

Java程序开发三步骤：**编写**、**编译**、**运行**。

- 将 Java 代码**编写**到扩展名为 .java 的源文件中
- 通过 javac.exe 命令对该 java 文件进行**编译**，生成一个或多个字节码文件
- 通过 java.exe 命令对生成的 class 文件进行**运行**

![image-20220310230210728](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310230210728.png)

### 8.2 编写

（1）在`D:\JavaSE\chapter01` 目录下新建文本文件，完整的文件名修改为`HelloWorld.java`，其中文件名为`HelloWorld`，后缀名必须为`.java`。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801010222222.png" alt="image-20220801010222222" style="zoom:90%;" />

（2）用记事本或editplus等文本编辑器打开（虽然记事本也可以，但是没有关键字颜色标识，不利于初学者学习）

（3）在文件中输入如下代码，并且保存：

```java
class HelloChina {
  	public static void main(String[] args) {
    	System.out.println("HelloWorld!!");
  	}
}
```

- **友情提示1：每个字母和符号必须与示例代码一模一样，包括大小写在内。**

- 友情提示2：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310230618659.png" alt="image-20220310230618659" style="zoom:67%;" />

或

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220521173215013.png" alt="image-20220521173215013" style="zoom:90%;" />

第一个`HelloWord` 源程序就编写完成了，但是这个文件是程序员编写的，JVM是看不懂的，也就不能运行，因此我们必须将编写好的`Java源文件` 编译成JVM可以看懂的`字节码文件` ，也就是`.class`文件。

### 8.3 编译

在DOS命令行中，进入`D:\JavaSE\chapter01`目录，使用`javac` 命令进行编译。

使用文件资源管理器打开`D:\JavaSE\chapter01`目录，然后在地址栏输入cmd。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801010435144.png" alt="image-20220801010435144" style="zoom:80%;" />

命令：

```java
javac Java源文件名.后缀名java
```

举例：

```
javac HelloWorld.java
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801010840640.png" alt="image-20220801010840640" style="zoom:80%;" />

编译成功后，命令行没有任何提示。打开`D:\JavaSE\chapter01`目录，发现产生了一个新的文件 `HelloChina.class`，该文件就是编译后的文件，是Java的可运行文件，称为**字节码文件**，有了字节码文件，就可以运行程序了。 

### 8.4 运行

在DOS命令行中，在字节码文件目录下，使用`java` 命令进行运行。

命令：

```java
java 主类名字
```

主类是指包含main方法的类，main方法是Java程序的入口：

```java
public static void main(String[] args){
    
}
```

举例：

```
java HelloChina
```

> 错误演示：
>
> java HelloChina.class

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801011104381.png" alt="image-20220801011104381" style="zoom:80%;" />

## 9. Java开发工具

### 9.1 都有哪些开发Java的工具

- 级别一：文本开发工具

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513185907022.png" alt="image-20220513185907022" style="zoom:50%;" />

- 级别二：集成开发环境（Integrated Development Environment，IDE）

> 把代码编写，编译，执行，调试等多种功能综合到一起的开发工具。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513190655773.png" alt="image-20220513190655773" style="zoom:50%;" />

### 9.2 如何选择

前期我们先使用文本开发工具，培养`代码感`，利于公司笔、面试。

后期我们使用IDE，提供`更强大的功能支持`。

## 10. HelloWorld案例常见错误

### 10.1 拼写问题

* 	单词拼写问题
   * 正确：class		         错误：Class
   * 正确：String                    错误：string
   * 正确：System                  错误：system
   * 正确：main		         错误：mian
* 	Java语言是一门严格区分大小写的语言
* 	标点符号使用问题
   * 不能用中文符号，英文半角的标点符号（正确）
   * 括号问题，成对出现

### 10.2 编译、运行路径问题

举例1：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310231657692.png" alt="image-20220310231657692" style="zoom:80%;" />

- 源文件名不存在或者写错
- 当前路径错误
- 后缀名隐藏问题

举例2：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310231717893.png" alt="image-20220310231717893" style="zoom:80%;" />

- 类文件名写错，尤其文件名与类名不一致时，要小心
- 类文件不在当前路径下，或者不在classpath指定路径下

### 10.3 语法问题

举例1：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310231746581.png" alt="image-20220310231746581" style="zoom:90%;" />

声明为public的类应与文件名一致，否知编译失败。

举例2：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310231815405.png" alt="image-20220310231815405" style="zoom:80%;" />

编译失败，注意错误出现的行数，再到源代码中指定位置改错

### 10.4 字符编码问题

当cmd命令行窗口的字符编码与.java源文件的字符编码不一致，如何解决？

 ![1557881223916](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E5%91%BD%E4%BB%A4%E8%A1%8C%E7%BC%96%E8%AF%91%E4%B9%B1%E7%A0%81.png)

解决方案一：

- xxxxxxxxxx7 1public static void main(String[] args) {2    List<Author> authors = getAuthors();3    authors.parallelStream()4            .map(author -> author.getAge())5            .mapToInt(age -> age + 10)6            .forEach(name -> System.out.println(name));7}java

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/Notepad%E4%BF%AE%E6%94%B9%E6%BA%90%E6%96%87%E4%BB%B6%E5%AD%97%E7%AC%A6%E7%BC%96%E7%A0%81.png" alt="1557881271819" style="zoom:80%;" />

- 在EditPlus中可以将Java源文件另存为ANSI编码方式（中文操作系统下即为GBK字符集）

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1658810752424.png" alt="1658810752424" style="zoom:80%;" />

解决方案二：

	在使用javac命令式，可以指定源文件的字符编码

```cmd
javac -encoding utf-8 Review01.java
```

### 10.5 建议

- 注意缩进!
  - 一定要有缩进。缩进就像人得体的衣着一样！

  - 只要遇到{}就缩进，缩进的快捷键tab键。

- 必要的空格

  - 变量类型、变量、赋值符号、变量值之间填充相应空格，更美观。比如： int num = 10;

## 11. HelloWorld小结

### 11.1 Java程序的结构与格式

结构：

```java
类{
    方法{
        语句;
    }
}
```

格式：

（1）每一级缩进一个Tab键

（2）{}的左半部分在行尾，右半部分单独一行，与和它成对的"{"的行首对齐

### 11.2 Java程序的入口

Java程序的入口是main方法

```java
public static void main(String[] args){
    
}
```

### 11.3 两种常见的输出语句

- **换行输出语句**：输出内容，完毕后进行换行，格式如下：

  ```java
  System.out.println(输出内容);
  ```

- **直接输出语句**：输出内容，完毕后不做任何处理，格式如下

  ```java
  System.out.print(输出内容);
  ```

> 注意事项：
>
> ​	换行输出语句，括号内可以什么都不写，只做换行处理
>
> ​	直接输出语句，括号内什么都不写的话，编译报错

### 11.4 源文件名与类名

（1）源文件名是否必须与类名一致？public呢？

```java
如果这个类不是public，那么源文件名可以和类名不一致。但是不便于代码维护。

如果这个类是public，那么要求源文件名必须与类名一致。否则编译报错。

我们建议大家，不管是否是public，都与源文件名保持一致，而且一个源文件尽量只写一个类，目的是为了好维护。
```

（2）一个源文件中是否可以有多个类？public呢？

```java
一个源文件中可以有多个类，编译后会生成多个.class字节码文件。

但是一个源文件只能有一个public的类。
```

## 12. 注释(comment)

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220610113151774.png" alt="image-20220610113151774" style="zoom: 67%;" />

- 什么是注释？

  - 源文件中用于解释、说明程序的文字就是注释。

- 注释是一个程序员必须要具有的良好编程习惯。实际开发中，程序员可以先将自己的`思想`通过注释整理出来，再用`代码`去体现。

  > 程序员最讨厌两件事：
  >
  > 一件是自己写代码被要求加注释
  >
  > 另一件是接手别人代码，发现没有注释

- 不加注释的危害

  ![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%96%B0%E6%9D%A5%E7%9A%84%E8%8F%9C%E9%B8%9F%E7%A8%8B%E5%BA%8F%E5%91%98%E9%97%AF%E7%A5%B8%E4%BA%86%E3%80%82%E3%80%82%E3%80%82%E8%BF%99%E6%AE%B5%E4%BB%A3%E7%A0%81%E5%A5%BD%E5%A4%9A%E5%B9%B4%E9%83%BD%E6%B2%A1%E4%BA%BA%E6%95%A2%E5%8A%A8%E8%BF%87%E7%9A%84.gif)

- Java中的注释类型：

  - 单行注释

    ```
    //注释文字
    ```

  - 多行注释

    ```
    /* 
    注释文字1 
    注释文字2
    注释文字3
    */
    ```

  - 文档注释 (Java特有)

    ```
    /**
      @author  指定java程序的作者
      @version  指定源文件的版本
    */ 
    ```

- 注释的作用

  - 它提升了程序的可阅读性。（不加注释的危害性，见图。）
  - 调试程序的重要方法。

- **具体使用1：单行注释、多行注释**

  - 对于单行和多行注释，被注释的文字，不会不会出现在字节码文件中，进而不会被JVM（java虚拟机）解释执行。

  - 多行注释里面不允许有多行注释嵌套。

  - 一个段子

    ```java
    A：嘿 //是什么意思啊？
    B：嘿.
    A：呃 我问你//是什么意思？
    B：问吧.
    A：我刚才不是问了么？
    B：啊？
    A：你再看看记录...
    B：看完了.
    A：......所以//是啥？
    B：所以什么？
    A：你存心耍我呢吧？
    B：没有啊 你想问什么？
    ……
    不断循环之后，A一气之下和B绝交，自己苦学程序。
    N年之后，A终于修成正果，回想起B，又把聊天记录翻出来看，这时，他突然发现B没有耍他……
    而他自己也不知道当年他问B的究竟是什么问题……
    ```

- **具体使用2：文档注释（Java特有）**

  - 文档注释内容可以被JDK提供的工具 javadoc 所解析，生成一套以网页文件形式体现的该程序的说明文档。

  - 操作方式。比如： 

    ```ini
    javadoc -d mydoc -author -version HelloWorld.java
    ```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310232527578.png" alt="image-20220310232527578" style="zoom:67%;" />

案例：

```java
//单行注释
/*
多行注释
*/
/**
文档注释演示。这是我的第一个Java程序！^_^
@author songhk
@version 1.0
*/
public class HelloWorld{
    
	/**
	Java程序的入口
	@param args main方法的命令参数
	*/
    public static void main(String[] args){
        System.out.println("hello");
    }
}
```

## 13. Java API文档

- API （Application Programming Interface，**应用程序编程接口**）是 Java 提供的基本编程接口。
- Java语言提供了大量的基础类，因此 Oracle 也为这些基础类提供了相应的说明文档，用于告诉开发者如何使用这些类，以及这些类里包含的方法。大多数Java书籍中的类的介绍都要参照它来完成，它是编程者经常查阅的资料。
- Java API文档，即为JDK使用说明书、帮助文档。类似于：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%96%B0%E5%8D%8E%E5%AD%97%E5%85%B8.jpg" alt="新华字典" style="zoom:28%;" />

- 下载API文档：
  - 在线看：https://docs.oracle.com/en/java/javase/17/docs/api/index.html
  - 离线下载：https://www.oracle.com/java/technologies/javase-jdk17-doc-downloads.html

## 14. Java核心机制：JVM

### 14.1 Java语言的优缺点

Java确实是从C语言和C++语言继承了许多成份，甚至可以将Java看成是类C语言发展和衍生的产物。“青出于蓝，而胜于蓝”。

#### 14.1.1 优点

* **跨平台性：**这是Java的核心优势。Java在最初设计时就很注重移植和跨平台性。比如：Java的int永远都是32位。不像C++可能是16，32，可能是根据编译器厂商规定的变化。
* 通过Java语言编写的应用程序在不同的系统平台上都可以运行。“`Write once , Run Anywhere`”。
* 原理：只要在需要运行 java 应用程序的操作系统上，先安装一个Java虚拟机 (`J`VM ，Java `V`irtual `M`achine) 即可。由JVM来负责Java程序在该系统中的运行。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/JVM%E7%9A%84%E8%B7%A8%E5%B9%B3%E5%8F%B0%E6%80%A7-1666858570703.jpg" alt="JVM的跨平台性" style="zoom:70%;" />

![image-20211217111217831](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211217111217831.png)

* **面向对象性：**

  面向对象是一种程序设计技术，非常`适合大型软件的设计和开发`。面向对象编程支持封装、继承、多态等特性，让程序更好达到`高内聚`，`低耦合`的标准。

* **健壮性：**吸收了C/C++语言的优点，但去掉了其影响程序健壮性的部分（如指针、内存的申请与释放等），提供了一个相对安全的内存管理和访问机制。

* **安全性高：**

  Java适合于网络/分布式环境，需要提供一个安全机制以防恶意代码的攻击。如：`安全防范机制`（ClassLoader类加载器），可以分配不同的命名空间以防替代本地的同名类、字节代码检查。

* **简单性：**

  Java就是C++语法的`简化版`，我们也可以将Java称之为“`C++--`”。比如：头文件，指针运算，结构，联合，操作符重载，虚基类等。

* **高性能：**

  - Java最初发展阶段，总是被人诟病“`性能低`”；客观上，高级语言运行效率总是低于低级语言的，这个无法避免。Java语言本身发展中通过虚拟机的优化提升了`几十倍运行效率`。比如，通过JIT(JUST IN TIME)即时编译技术提高运行效率。

  - `Java低性能的短腿，已经被完全解决了`。业界发展上，我们也看到很多C++应用转到Java开发，很多C++程序员转型为Java程序员。

#### 14.1.2 缺点

- `语法过于复杂、严谨`，对程序员的约束比较多，与python、php等相比入门较难。但是一旦学会了，就业岗位需求量大，而且`薪资待遇节节攀升`。
- 一般适用于大型网站开发，`整个架构会比较重`，对于初创公司开发和维护人员的成本比较高（即薪资高），选择用Java语言开发网站或应用系统的需要一定的经济实力。
- `并非适用于所有领域`。比如，Objective C、Swift在iOS设备上就有着无可取代的地位。浏览器中的处理几乎完全由JavaScript掌控。Windows程序通常都用C++或C#编写。Java在服务器端编程和跨平台客户端应用领域则很有优势。

### 14.2 JVM功能说明

**JVM**（`J`ava `V`irtual `M`achine ，Java虚拟机）：是一个虚拟的计算机，是Java程序的运行环境。JVM具有指令集并使用不同的存储区域，负责执行指令，管理数据、内存、寄存器。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310165805739.png" alt="image-20220310165805739" style="zoom:67%;" />

#### 14.2.1 功能1：实现Java程序的跨平台性

我们编写的Java代码，都运行在**JVM** 之上。正是因为有了JVM，才使得Java程序具备了跨平台性。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513191856727.png" alt="image-20220513191856727" style="zoom:50%;" />

使用JVM前后对比：

![image-20220514151716805](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514151716805.png)

#### 14.2.2 功能2：自动内存管理(内存分配、内存回收)

- Java程序在运行过程中，涉及到运算的`数据的分配`、`存储`等都由JVM来完成
- Java消除了程序员回收无用内存空间的职责。提供了一种系统级线程跟踪存储空间的分配情况，在内存空间达到相应阈值时，检查并释放可被释放的存储器空间。
- GC的自动回收，提高了内存空间的利用效率，也提高了编程人员的效率，很大程度上`减少了`因为没有释放空间而导致的`内存泄漏`。

> 面试题：
>
> Java程序还会出现内存溢出和内存泄漏问题吗？  Yes!

## 15. 企业真题

1. 一个".java”源文件中是否可以包括多个类?有什么限制(明*数据)

> 答：可以包含多个类，但是有且仅有一个类能够为public类，并且该类名要和源文件名一致。



2. **Java的优势(阿巴)**

> Java社区比较繁荣，有大量的第三方框架配置



3. **常用的几个命令行操作都有哪些? (北京数字**)

> 1.javac .java文件：将.java文件编译为.class文件
>
> 2.java .class文件名：运行.class文件

4. **Java中是否存在内存溢出、内存泄漏?如何解决?举例说明(拼*多)**

> 存在



5. **如何看待Java是一门半编译半解释型的语言(携*)**

> 因为java源码文件（.java）通过编译生成.class字节码文件后，可以通过解释器逐行解释运行，也可以通过JIT编译器(及时编译技术)运行。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230223202619539.png" alt="image-20230223202619539" style="zoom: 80%;" />







# 第02章_变量与运算符

## 1. 关键字（keyword）

- 定义：**被Java语言赋予了特殊含义，用做专门用途的字符串（或单词）**
  - HelloWorld案例中，出现的关键字有 `class`、`public `  、 `static` 、  `void`  等，这些单词已经被Java定义好了。
    - 特点：全部关键字都是`小写字母`。							
- 关键字比较多，不需要死记硬背，学到哪里记到哪里即可。
- 官方地址： https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html

![image-20220310234414849](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310234414849.png)

>  说明：
>
>  1. 关键字一共`50个`，其中`const`和`goto`是`保留字`(reserved word)。
>  2. `true`，`false`，`null`不在其中，它们看起来像关键字，其实是字面量，表示特殊的布尔值和空值。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220310234557454.png" alt="image-20220310234557454" style="zoom: 67%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220801143635356.png" alt="image-20220801143635356" style="zoom:66%;" />

## 2. 标识符( identifier)

Java中变量、方法、类等要素命名时使用的字符序列，称为标识符。

技巧：凡是自己可以起名字的地方都叫标识符。

**标识符的命名规则**（必须遵守的`硬性规定`）：

```
> 由26个英文字母大小写，0-9 ，_或 $ 组成  
> 数字不可以开头。
> 不可以使用关键字和保留字，但能包含关键字和保留字。
> Java中严格区分大小写，长度无限制。
> 标识符不能包含空格。
```

> 练习：miles、Test、a++、 --a、4#R、$4、 #44、apps、class、public、int、x、y、radius

**标识符的命名规范**（建议遵守的`软性要求`，否则工作时容易被鄙视）:

```
> 包名：多单词组成时所有字母都小写：xxxyyyzzz。
  例如：java.lang、com.atguigu.bean
  
> 类名、接口名：多单词组成时，所有单词的首字母大写：XxxYyyZzz
  例如：HelloWorld，String，System等
  
> 变量名、方法名：多单词组成时，第一个单词首字母小写，第二个单词开始每个单词首字母大写：xxxYyyZzz
  例如：age,name,bookName,main,binarySearch,getName
  
> 常量名：所有字母都大写。多单词时每个单词用下划线连接：XXX_YYY_ZZZ
  例如：MAX_VALUE,PI,DEFAULT_CAPACITY
```

注意：在起名字时，为了提高阅读性，要尽量有意义，“见名知意”。

> 更多细节详见《代码整洁之道_关于标识符.txt》《阿里巴巴Java开发手册-1.7.1-黄山版》

## 3. 变量

### 3.1 为什么需要变量

![image-20220513235020527](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513235020527.png)

一花一世界，如果把一个程序看做一个世界或一个社会的话，那么变量就是程序世界的花花草草、万事万物。即，**变量是程序中不可或缺的组成单位，最基本的存储单元**。

![image-20220513235828042](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513235828042.png)

### 3.2 初识变量

- 变量的概念：

  - 内存中的一个存储区域，该区域的数据可以在同一类型范围内不断变化

  - 变量的构成包含三个要素：`数据类型`、`变量名`、`存储的值`

  - Java中变量声明的格式：`数据类型 变量名 = 变量值`

    <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220311000744867.png" alt="image-20220311000744867" style="zoom:67%;" />

- 变量的作用：用于在内存中保存数据。

- 使用变量注意：

  - Java中每个变量必须先声明，后使用。
  - 使用变量名来访问这块区域的数据。
  - 变量的作用域：其定义所在的一对{ }内。
  - 变量只有在其`作用域`内才有效。出了作用域，变量不可以再被调用。
  - 同一个作用域内，不能定义重名的变量。

### 3.3 Java中变量的数据类型

Java中变量的数据类型分为两大类：

- **基本数据类型**：包括 `整数类型`、`浮点数类型`、`字符类型`、`布尔类型`。 

- **引用数据类型**：包括`数组`、 `类`、`接口`、`枚举`、`注解`、`记录`。 

  ![Java的数据类型](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/Java%E7%9A%84%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B.png)

### 3.4 变量的使用

#### 3.4.1 步骤1：变量的声明

```
格式：数据类型  变量名;
```

```java
//例如：
//存储一个整数类型的年龄
int age; 

//存储一个小数类型的体重
double weight;

//存储一个单字符类型的性别 
char gender;

//存储一个布尔类型的婚姻状态
boolean marry;

//存储一个字符串类型的姓名
String name;

//声明多个同类型的变量
int a,b,c; //表示a,b,c三个变量都是int类型。
```

> 注意：变量的数据类型可以是基本数据类型，也可以是引用数据类型。

#### 3.4.2 步骤2：变量的赋值

给变量赋值，就是把“值”存到该变量代表的内存空间中。同时，给变量赋的值类型必须与变量声明的类型一致或兼容。

**变量赋值的语法格式：**

```java
变量名 = 值;
```

举例1：可以使用合适类型的`常量值`给已经声明的变量赋值

```java
age = 18;
weight = 109;
gender = '女';
```

举例2：可以使用其他`变量`或者`表达式`给变量赋值

```java
int m = 1;
int n = m;
        
int x = 1;
int y = 2;
int z = 2 * x + y;
```

3：变量可以反复赋值

```java
//先声明，后初始化
char gender;
gender = '女';

//给变量重新赋值，修改gender变量的值
gender = '男';
System.out.println("gender = " + gender);//gender = 男
```

举例4：也可以将变量的声明和赋值一并执行

```java
boolean isBeauty = true;
String name = "迪丽热巴";
```

内存结构如图：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514152216148.png" alt="image-20220514152216148" style="zoom: 80%;" />



## 4. 基本数据类型介绍

### 4.1 整数类型：byte、short、int、long

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520111756274.png" alt="image-20220520111756274" style="zoom: 50%;" />

- Java各整数类型有固定的表数范围和字段长度，不受具体操作系统的影响，以保证Java程序的可移植性。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220311001553945.png" alt="image-20220311001553945" style="zoom:80%;" />

- 定义long类型的变量，赋值时需要以"`l`"或"`L`"作为后缀。

- Java程序中变量通常声明为int型，除非不足以表示较大的数，才使用long。

- Java的整型`常量默认为 int 型`。

#### 4.1.1 补充：计算机存储单位


- **字节（Byte）：**是计算机用于`计量存储容量`的`基本`单位，一个字节等于8 bit。

- **位（bit）：**是数据存储的`最小`单位。二进制数系统中，每个0或1就是一个位，叫做bit（比特），其中8 bit 就称为一个字节(Byte)。

- **转换关系：**
  - 8 bit = 1 Byte
  - 1024 Byte = 1 KB
  - 1024 KB = 1 MB
  - 1024 MB = 1 GB
  - 1024 GB = 1 TB

### 4.2 浮点类型：float、double

- 与整数类型类似，Java 浮点类型也有固定的表数范围和字段长度，不受具体操作系统的影响。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220311001749699.png" alt="image-20220311001749699" style="zoom:80%;" />

- 浮点型常量有两种表示形式：
  - 十进制数形式。如：5.12       512.0f        .512   (必须有小数点）
  - 科学计数法形式。如：5.12e2      512E2     100E-2
- float：`单精度`，尾数可以精确到7位有效数字。很多情况下，精度很难满足需求。    
- double：`双精度`，精度是float的两倍。通常采用此类型。
- 定义float类型的变量，赋值时需要以"`f`"或"`F`"作为后缀。
- Java 的浮点型`常量默认为double型`。

#### 4.2.1 关于浮点型精度的说明

- 并不是所有的小数都能可以精确的用二进制浮点数表示。二进制浮点数不能精确的表示0.1、0.01、0.001这样10的负次幂。

- 浮点类型float、double的数据不适合在`不容许舍入误差`的金融计算领域。如果需要`精确`数字计算或保留指定位数的精度，需要使用`BigDecimal类`。

- 测试用例：


```java
//测试1：（解释见章末企业真题：为什么0.1 + 0.2不等于0.3）
System.out.println(0.1 + 0.2);//0.30000000000000004

//测试2：
float ff1 = 123123123f;
float ff2 = ff1 + 1;
System.out.println(ff1);
System.out.println(ff2);
System.out.println(ff1 == ff2);

```

#### 4.2.2 应用举例

案例1：定义圆周率并赋值为3.14，现有3个圆的半径分别为1.2、2.5、6，求它们的面积。

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:36
 */
public class Exercise1 {
    public static void main(String[] args) {
        double PI = 3.14; //圆周率

        double radius1 = 1.2;
        double radius2 = 2.5;
        int radius3 = 6;

        System.out.println("第1个圆的面积：" + PI * radius1 * radius1);
        System.out.println("第2个圆的面积：" + PI * radius2 * radius2);
        System.out.println("第3个圆的面积：" + PI * radius3 * radius3);
    }
}
```

案例2：小明要到美国旅游，可是那里的温度是以华氏度为单位记录的。
它需要一个程序将华氏温度（80度）转换为摄氏度，并以华氏度和摄氏度为单位分别显示该温度。

```ini
℃ = (℉ - 32) / 1.8
```

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:51
 */
public class Exercise2 {
    public static void main(String[] args) {
        double hua = 80;
        double she = (hua-32)/1.8;
        System.out.println("华氏度" + hua+"℉转为摄氏度是" +she+"℃");
    }
}
```

### 4.3 字符类型：char

- char 型数据用来表示通常意义上“`字符`”（**占2字节**）

  - Java中的所有字符都使用Unicode编码，故一个字符可以存储一个字母，一个汉字，或其他书面语的一个字符。																								

- 字符型变量的三种表现形式：

  - **形式1：**使用单引号(' ')括起来的`单个字符`。

    例如：char c1 = 'a';   char c2 = '中'; char c3 =  '9';

  - **形式2：**直接使用 `Unicode值`来表示字符型常量：‘`\uXXXX`’。其中，XXXX代表一个十六进制整数。

    例如：\u0023 表示 '#'。 

  - **形式3：**Java中还允许使用`转义字符‘\’`来将其后的字符转变为特殊字符型常量。

    例如：char c3 = '\n';  // '\n'表示换行符

  | 转义字符 |  说明  | Unicode表示方式 |
  | :------: | :----: | :-------------: |
  |   `\n`   | 换行符 |     \u000a      |
  |   `\t`   | 制表符 |     \u0009      |
  |   `\"`   | 双引号 |     \u0022      |
  |   `\'`   | 单引号 |     \u0027      |
  |   `\\`   | 反斜线 |     \u005c      |
  |   `\b`   | 退格符 |     \u0008      |
  |   `\r`   | 回车符 |     \u000d      |

- char类型是可以进行运算的。因为它都对应有Unicode码，可以看做是一个数值。

### 4.4 布尔类型：boolean

- **boolean 类型用来判断逻辑条件，一般用于流程控制语句中：**
  - if条件控制语句；                  
  - while循环控制语句；     
  - for循环控制语句；
  - do-while循环控制语句； 

- **boolean类型数据只有两个值：true、false，无其它。**
  - 不可以使用0或非 0 的整数替代false和true，这点和C语言不同。
  - 拓展：Java虚拟机中没有任何供boolean值专用的字节码指令，Java语言表达所操作的boolean值，在编译之后都使用java虚拟机中的int数据类型来代替：true用1表示，false用0表示。——《java虚拟机规范 8版》
- 举例：

```java
boolean isFlag = true;

if(isFlag){
    //true分支
}else{  
    //false分支
}
```

> 经验之谈：
>
> Less is More！建议不要这样写：if ( isFlag = = true )，只有新手才如此。关键也很容易写错成if(isFlag = true)，这样就变成赋值isFlag为true而不是判断！`老鸟的写法`是if (isFlag)或者if ( !isFlag)。

## 5. 基本数据类型变量间运算规则

在Java程序中，不同的基本数据类型（只有7种，不包含boolean类型）变量的值经常需要进行相互转换。

转换的方式有两种：`自动类型提升`和`强制类型转换`。

### 5.1 自动类型提升

**规则：<font color='red'>将取值范围小（或容量小）的类型自动提升为取值范围大（或容量大）的类型 </font>。**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220523162200891.png" alt="image-20220523162200891" style="zoom:67%;" />

基本数据类型的转换规则如图所示：

![image-20220311002543688](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220311002543688.png)

（1）当把存储范围小的值（常量值、变量的值、表达式计算的结果值）赋值给了存储范围大的变量时

```java
int i = 'A';//char自动升级为int，其实就是把字符的编码值赋值给i变量了
double d = 10;//int自动升级为double
long num = 1234567; //右边的整数常量值如果在int范围呢，编译和运行都可以通过，这里涉及到数据类型转换

//byte bigB = 130;//错误，右边的整数常量值超过byte范围
long bigNum = 12345678912L;//右边的整数常量值如果超过int范围，必须加L，显式表示long类型。否则编译不通过
```

（2）当存储范围小的数据类型与存储范围大的数据类型变量一起混合运算时，会按照其中最大的类型运算。

```java
int i = 1;
byte b = 1;
double d = 1.0;

double sum = i + b + d;//混合运算，升级为double
```

（3）当byte,short,char数据类型的变量进行算术运算时，按照int类型处理。

```java
byte b1 = 1;
byte b2 = 2;
byte b3 = b1 + b2;//编译报错，b1 + b2自动升级为int

char c1 = '0';
char c2 = 'A';
int i = c1 + c2;//至少需要使用int类型来接收
System.out.println(c1 + c2);//113 
```

**练习：**

```java
设 x 为float型变量，y为double型变量，a为int型变量，b为long型变量，c为char型变量，则表达式
x + y * a / x + b / y + c的值类型为：

A. int   B. long  C. double  D. char
```

### 5.2 强制类型转换

将`3.14` 赋值到`int` 类型变量会发生什么？产生编译失败，肯定无法赋值。

```java
int i = 3.14; // 编译报错
```

想要赋值成功，只有通过`强制类型转换`，将`double` 类型强制转换成`int` 类型才能赋值。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220523162407722.png" alt="image-20220523162407722" style="zoom:67%;" />

**规则：<font color='red'>将取值范围大（或容量大）的类型强制转换成取值范围小（或容量小）的类型。</font>**

> 自动类型提升是Java自动执行的，而强制类型转换是自动类型提升的逆运算，需要我们自己手动执行。

**转换格式：**

```java
数据类型1 变量名 = (数据类型1)被强转数据值;  //()中的数据类型必须<=变量值的数据类型
```

（1）当把存储范围大的值（常量值、变量的值、表达式计算的结果值）强制转换为存储范围小的变量时，可能会`损失精度`或`溢出`。

```java
int i = (int)3.14;//损失精度

double d = 1.2;
int num = (int)d;//损失精度

int i = 200;
byte b = (byte)i;//溢出
```

（2）当某个值想要提升数据类型时，也可以使用强制类型转换。这种情况的强制类型转换是`没有风险`的，通常省略。

```java
int i = 1;
int j = 2;
double bigger = (double)(i/j);
```

（3）声明long类型变量时，可以出现省略后缀的情况。float则不同。

```java
long l1 = 123L;
long l2 = 123;//如何理解呢？ 此时可以看做是int类型的123自动类型提升为long类型

//long l3 = 123123123123; //报错，因为123123123123超出了int的范围。
long l4 = 123123123123L;


//float f1 = 12.3; //报错，因为12.3看做是double，不能自动转换为float类型
float f2 = 12.3F;
float f3 = (float)12.3;
```

**练习：判断是否能通过编译**

```java
1）short  s = 5;
   s = s-2;                     //判断：no
2） byte b = 3;
    b = b + 4;                  //判断：no
    b = (byte)(b+4);            //判断：yes
3）char c = ‘a’;
   int  i = 5;
   float d = .314F;
   double result = c+i+d;       //判断：yes
4） byte b = 5;
    short s = 3;
    short t = s + b;            //判断：no

```

**问答：为什么标识符的声明规则里要求不能数字开头？**

```java
//如果允许数字开头，则如下的声明编译就可以通过：
int 123L = 12;
//进而，如下的声明中l的值到底是123？还是变量123L对应的取值12呢？ 出现歧义了。
long l = 123L;
```

### 5.3 基本数据类型与String的运算

#### 5.3.1 字符串类型：String

- String不是基本数据类型，属于引用数据类型
- 使用一对`""`来表示一个字符串，内部可以包含0个、1个或多个字符。
- 声明方式与基本数据类型类似。例如：String str = “尚硅谷”;

#### 5.3.2 运算规则

1、任意八种基本数据类型的数据与String类型只能进行连接“+”运算，且结果一定也是String类型

```java
System.out.println("" + 1 + 2);//12

int num = 10;
boolean b1 = true;
String s1 = "abc";

String s2 = s1 + num + b1;
System.out.println(s2);//abc10true

//String s3 = num + b1 + s1;//编译不通过，因为int类型不能与boolean运算
String s4 = num + (b1 + s1);//编译通过
```

2、String类型不能通过强制类型()转换，转为其他的类型

```java
String str = "123";
int num = (int)str;//错误的

int num = Integer.parseInt(str);//正确的，后面才能讲到，借助包装类的方法才能转
```

#### 5.3.3 案例与练习

**案例：公安局身份登记**

要求填写自己的姓名、年龄、性别、体重、婚姻状况（已婚用true表示，单身用false表示）、联系方式等等。

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:34
 */
public class Info {
    public static void main(String[] args) {
        String name = "康师傅";
        int age = 37;
        char gender = '男';
        double weight = 145.6;
        boolean isMarried = true;
        String phoneNumber = "13112341234";

        System.out.println("姓名：" + name);
        System.out.println("年龄：" + age);
        System.out.println("性别：" + gender);
        System.out.println("体重：" + weight);
        System.out.println("婚否：" + isMarried);
        System.out.println("电话：" + phoneNumber);
		//或者
        System.out.println("name = " + name + ",age = " + age + "，gender = " + 
                           gender + ",weight = " + weight + ",isMarried = " + isMarried +
                           ",phoneNumber = " + phoneNumber);
    }
}
```

## 6. 计算机底层如何存储数据

计算机世界中只有二进制，所以计算机中存储和运算的`所有数据`都要转为`二进制`。包括数字、字符、图片、声音、视频等。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/010101.jpg" alt="010101" style="zoom: 33%;" />

**世界上有10种人 ，认识和不认识二进制的。**

### 6.1 进制的分类

- **十进制（decimal）**
  - 数字组成：0-9
  - 进位规则：满十进一

- **二进制（binary）**
  - 数字组成：0-1
  - 进位规则：满二进一，以`0b`或`0B`开头

- **八进制（octal）：很少使用**
  - 数字组成：0-7
  - 进位规则：满八进一，以数字`0`开头表示

- **十六进制**
  - 数字组成：0-9，a-f
  - 进位规则：满十六进一，以`0x`或`0X`开头表示。此处的 a-f 不区分大小写

代码演示：

```java
class BinaryTest {
	public static void main(String[] args) {
		
		int num1 = 123;		//十进制
		int num2 = 0b101;	//二进制
		int num3 = 0127;	//八进制
		int num4 = 0x12aF;	//十六进制

		System.out.println(num1);
		System.out.println(num2);
		System.out.println(num3);
		System.out.println(num4);

	}
}
```

### 6.2 进制的换算举例

| 十进制 | 二进制 | 八进制 | 十六进制 |
| ------ | ------ | ------ | -------- |
| 0      | 0      | 0      | 0        |
| 1      | 1      | 1      | 1        |
| 2      | 10     | 2      | 2        |
| 3      | 11     | 3      | 3        |
| 4      | 100    | 4      | 4        |
| 5      | 101    | 5      | 5        |
| 6      | 110    | 6      | 6        |
| 7      | 111    | 7      | 7        |
| 8      | 1000   | 10     | 8        |
| 9      | 1001   | 11     | 9        |
| 10     | 1010   | 12     | a或A     |
| 11     | 1011   | 13     | b或B     |
| 12     | 1100   | 14     | c或C     |
| 13     | 1101   | 15     | d或D     |
| 14     | 1110   | 16     | e或E     |
| 15     | 1111   | 17     | f或F     |
| 16     | 10000  | 20     | 10       |

### 6.3 二进制的由来

二进制，是计算技术中广泛采用的一种数制，由德国数理哲学大师`莱布尼茨`于1679年发明。

二进制数据是用0和1两个数码来表示的数。它的基数为2，进位规则是“`逢二进一`”。

二进制广泛应用于我们生活的方方面面。比如，广泛使用的摩尔斯电码（Morse Code），它由两种基本信号组成：短促的点信号“`·`”，读“`滴`”；保持一定时间的长信号“`—`”，读“`嗒`”。然后，组成了26个字母，从而拼写出相应的单词。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520105721126.png" alt="image-20220520105721126" style="zoom:67%;" />

记忆技巧：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/morsecode.jpg" alt="morsecode" style="zoom:50%;" />

我们偶尔会看到的：SOS，即为：

![image-20220520110206899](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520110206899.png)


### 6.4 二进制转十进制

**二进制如何表示整数？**

- <font color='red'>**计算机数据的存储使用二进制`补码`形式存储，并且`最高位是符号位`。**</font>
  - 正数：`最高位是0`
  - 负数：`最高位是1`


- 规 定
  - 正数的补码与反码、原码一样，称为`三码合一`
  - 负数的补码与反码、原码不一样：
    - 负数的`原码`：把十进制转为二进制，然后最高位设置为1
    - 负数的`反码`：在原码的基础上，最高位不变，其余位取反（0变1,1变0）
    - 负数的`补码`：反码+1

**二进制转十进制：权相加法**

针对于byte数据举例来说：

![image-20220523114701881](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220523114701881.png)


- 例如：byte类型（1个字节，8位）

  25 ==> 原码  0001 1001 ==> 反码  0001 1001 -->补码  0001 1001

  -25 ==>原码  1001 1001 ==> 反码1110 0110 ==>补码 1110 0111

```java
整数：
正数：25   00000000 00000000 000000000 00011001（原码）
正数：25   00000000 00000000 000000000 00011001（反码）
正数：25   00000000 00000000 000000000 00011001（补码）

负数：-25  10000000 00000000 000000000 00011001（原码）
负数：-25  11111111 11111111 111111111 11100110（反码）
负数：-25  11111111 11111111 111111111 11100111（补码）
```

**一个字节可以存储的整数范围是多少？**

```java
//1个字节：8位

0000 0001  ~  0111 111 ==> 1~127

1000 0001 ~ 1111 1111 ==> -127 ~ -1

0000 0000 ==>0

1000 0000 ==> -128（特殊规定）=-127-1
```

### 6.5 十进制转二进制

十进制转二进制：`除2取余的逆`

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312000042595.png" alt="image-20220312000042595" style="zoom:67%;" />

### 6.6 二进制与八进制、十六进制间的转换

**二进制转八进制**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312000233863.png" alt="image-20220312000233863" style="zoom:80%;" />

**二进制转十六进制**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312000251113.png" alt="image-20220312000251113" style="zoom:80%;" />

**八进制、十六进制转二进制**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312000341297.png" alt="image-20220312000341297" style="zoom: 67%;" />

### 6.7 各进制间的转换

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312000143438.png" alt="image-20220312000143438" style="zoom:80%;" />

**练习：**

```
1.将以下十进制数转换为十六进制和二进制
123  256  87  62


2.将以下十六进制数转换为十进制和二进制
0x123   0x25F  0x38  0x62
```

## 7. 运算符（Operator）

运算符是一种特殊的符号，用以表示数据的运算、赋值和比较等。

运算符的分类：

- 按照`功能`分为：算术运算符、赋值运算符、比较(或关系)运算符、逻辑运算符、位运算符、条件运算符、Lambda运算符

|           分类            |                        运算符                        |
| :-----------------------: | :--------------------------------------------------: |
|     算术运算符（7个）     |                +、-、*、/、%、++、--                 |
|    赋值运算符（12个）     | =、+=、-=、*=、/=、%=、>>=、<<=、>>>=、&=、\|=、^=等 |
| 比较(或关系)运算符（6个） |                 >、>=、<、<=、==、!=                 |
|     逻辑运算符（6个）     |                &、\|、^、!、&&、\|\|                 |
|      位运算符（7个）      |               &、\|、^、~、<<、>>、>>>               |
|     条件运算符（1个）     |               (条件表达式)?结果1:结果2               |
|    Lambda运算符（1个）    |                  ->（第18章时讲解）                  |

* 按照`操作数个数`分为：一元运算符（单目运算符）、二元运算符（双目运算符）、三元运算符 （三目运算符）

|           分类            |                  运算符                  |
| :-----------------------: | :--------------------------------------: |
| 一元运算符（单目运算符）  |    正号（+）、负号（-）、++、--、!、~    |
| 二元运算符（双目运算符）  | 除了一元和三元运算符剩下的都是二元运算符 |
| 三元运算符 （三目运算符） |         (条件表达式)?结果1:结果2         |

### 7.1 算术运算符

#### 7.1.1 基本语法

![image-20220312000848332](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312000848332.png)

举例1：加减乘除模

```java
public class ArithmeticTest1 {
	public static void main(String[] args) {
		int a = 3;
		int b = 4;
		
		System.out.println(a + b);// 7
		System.out.println(a - b);// -1
		System.out.println(a * b);// 12
		System.out.println(a / b);// 计算机结果是0，为什么不是0.75呢？
		System.out.println(a % b);// 3
        
        //结果与被模数符号相同
        System.out.println(5%2);//1
		System.out.println(5%-2);//1
		System.out.println(-5%2);//-1
		System.out.println(-5%-2);//-1		
		//商*除数 + 余数 = 被除数
		//5%-2  ==>商是-2，余数时1    (-2)*(-2)+1 = 5
		//-5%2  ==>商是-2，余数是-1   (-2)*2+(-1) = -4-1=-5
	}
}
```

举例2：“+”号的两种用法

* 第一种：对于`+`两边都是数值的话，`+`就是加法的意思
* 第二种：对于`+`两边至少有一边是字符串的话，`+`就是拼接的意思

```java
public class ArithmeticTest2 {
	public static void main(String[] args) {
		// 字符串类型的变量基本使用
		// 数据类型 变量名称 = 数据值;
		String str1 = "Hello";
		System.out.println(str1); // Hello
		
		System.out.println("Hello" + "World"); // HelloWorld
		
		String str2 = "Java";
		// String + int --> String
		System.out.println(str2 + 520); // Java520
		// String + int + int
		// String		+ int
		// String
		System.out.println(str2 + 5 + 20); // Java520
	}
}
```

举例3：自加自减运算

理解：`++`  运算，表示`自增1`。同理，`--` 运算，表示`自减1`，用法与++ 一致。

1、单独使用

* 变量在单独运算的时候，变量`前++`和变量`后++`，是没有区别的。
* 变量`前++`   ：例如 `++a` 。
* 变量`后++`   ：例如 `a++` 。

```java
public class ArithmeticTest3 {
	public static void main(String[] args) {
		// 定义一个int类型的变量a
		int a = 3;
		//++a;
		a++;
        // 无论是变量前++还是变量后++，结果都是4
		System.out.println(a);
	}
}
```

2、复合使用

* 和`其他变量放在一起使用`或者和`输出语句放在一起使用`，`前++`和`后++`就产生了不同。

- 变量`前++` ：变量先自增1，然后再运算。
- 变量`后++` ：变量先运算，然后再自增1。

```java
public class ArithmeticTest4 {
	public static void main(String[] args) {
		// 其他变量放在一起使用
		int x = 3;
		//int y = ++x; // y的值是4，x的值是4，
		int y = x++; // y的值是3，x的值是4
		
		System.out.println(x);
		System.out.println(y);
		System.out.println("==========");
        
		// 和输出语句一起
		int z = 5;
		//System.out.println(++z);// 输出结果是6，z的值也是6
		System.out.println(z++);// 输出结果是5，z的值是6
		System.out.println(z);
        
	} 
}
```

#### 7.1.2 案例与练习

**案例1：**

```ini
随意给出一个整数，打印显示它的个位数，十位数，百位数的值。
格式如下：
数字xxx的情况如下：
个位数：
十位数：
百位数：

例如：
数字153的情况如下：
个位数：3
十位数：5
百位数：1
```

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:20
 */
class ArithmeticExer1 {
	public static void main(String[] args) {
		
		int num = 187;
		
		int bai = num / 100;
		int shi = num % 100 / 10;//int shi = num / 10 % 10;
		int ge = num % 10;
		
		System.out.println("百位为：" + bai);
		System.out.println("十位为：" + shi);
		System.out.println("个位为：" + ge);

	}
}
```

拓展：获取一个四位数的个位，十位，百位，千位

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:39
 */
public class ArithmeticExer01 {
    public static void main (String [] args) {
        //1.定义一个变量，赋值为一个四位数整数，例如1234
        int num = 1234;

        //2.通过运算操作求出个位，十位，百位，千位
        int ge = num % 10;
        int shi = num /10 % 10;
        int bai = num /100 % 10;
        int qian = num / 1000 % 10;

        System.out.println("个位上的数字是：" + ge);
        System.out.println("十位上的数字是：" + shi);
        System.out.println("百位上的数字是：" + bai);
        System.out.println("千位上的数字是：" + qian);
    }
}
```

**案例2：**为抵抗洪水，战士连续作战89小时，编程计算共多少天零多少小时？

```java
/**
 * @author 尚硅谷-宋红康
 * @create 17:47
 */
public class ArithmeticExer2 {
    public static void main(String[] args){
        int hours = 89;
        int day = hours / 24;
        int hour = hours % 24;
        System.out.println("为抵抗洪水，战士连续作战89小时：");
        System.out.println(hours + "是" + day + "天" + hour +"小时");
    }
}
```

练习1：算术运算符：自加、自减

```java
public class ArithmeticExer3{
    public static void main(String[] args){
        int i1 = 10;
        int i2 = 20;
        int i = i1++;
        System.out.print("i="+i); //
        System.out.println("i1="+i1);//
        i = ++i1;
        System.out.print("i="+i);//
        System.out.println("i1="+i1);//
        i = i2--;
        System.out.print("i="+i);//
        System.out.println("i2="+i2);//
        i = --i2;
        System.out.print("i="+i);//
        System.out.println("i2="+i2);//
    }
}
```

练习2：

```java
System.out.println("5+5=" + 5 + 5); //打印结果是？ 5+5=55 ?
```

练习3：

```java
byte bb1 = 127;
bb1++;
System.out.println("bb1 = " + bb1);//-128
```

练习4：

```java
int i = 1;
int j = i++ + ++i * i++;
System.out.println("j = " + j);
```

练习5：（企业真题）写出下列程序的输出结果

```java
int i = 2;
int j = i++;
System.out.println(j);

int m = 2;
m = m++; //(1)先取m的值“2”放操作数栈 (2)m再自增,m=3 (3)再把操作数栈中的"2"赋值给m,m=2
System.out.println(m);
```

### 7.2 赋值运算符

#### 7.2.1 基本语法

- 符号：= 

  - 当“=”两侧数据类型不一致时，可以使用自动类型转换或使用强制类型转换原则进行处理。
  - 支持`连续赋值`。

- 扩展赋值运算符： +=、 -=、*=、 /=、%=

  | 赋值运算符 |                           符号解释                           |
  | :--------: | :----------------------------------------------------------: |
  |    `+=`    | 将符号`左边的值`和`右边的值`进行`相加`操作，最后将结果`赋值给左边的变量` |
  |    `-=`    | 将符号`左边的值`和`右边的值`进行`相减`操作，最后将结果`赋值给左边的变量` |
  |    `*=`    | 将符号`左边的值`和`右边的值`进行`相乘`操作，最后将结果`赋值给左边的变量` |
  |    `/=`    | 将符号`左边的值`和`右边的值`进行`相除`操作，最后将结果`赋值给左边的变量` |
  |    `%=`    | 将符号`左边的值`和`右边的值`进行`取余`操作，最后将结果`赋值给左边的变量` |


```java
public class SetValueTest1 {
	public static void main(String[] args) {
		int i1 = 10;
		long l1 = i1; //自动类型转换

		byte bb1 = (byte)i1; //强制类型转换

		int i2 = i1;

		//连续赋值的测试
		//以前的写法
		int a1 = 10;
		int b1 = 10;
		
		//连续赋值的写法
		int a2,b2;
		a2 = b2 = 10;
		
		int a3 = 10,b3 = 20;

		//举例说明+=  -=  *=  /=   %=  
		int m1 = 10;
		m1 += 5; //类似于 m1 = m1 + 5的操作，但不等同于。
		System.out.println(m1);//15
		
		//练习1：开发中，如何实现一个变量+2的操作呢？
		// += 的操作不会改变变量本身的数据类型。其他拓展的运算符也如此。
		//写法1：推荐
		short s1 = 10;
		s1 += 2; //编译通过，因为在得到int类型的结果后，JVM自动完成一步强制类型转换，将int类型强转成short
		System.out.println(s1);//12
		//写法2：
		short s2 = 10;
		//s2 = s2 + 2;//编译报错，因为将int类型的结果赋值给short类型的变量s时，可能损失精度
		s2 = (short)(s2 + 2);
		System.out.println(s2);


		//练习2：开发中，如何实现一个变量+1的操作呢？
		//写法1：推荐
		int num1 = 10;
		num1++;
		System.out.println(num1);

		//写法2：
		int num2 = 10;
		num2 += 1;
		System.out.println(num2);

		//写法3：
		int num3 = 10;
		num3 = num3 + 1;
		System.out.println(num3);

	}
}
```

#### 7.2.2 练习

练习1：

```java
short s = 3; 
s = s+2;  //① 编译报错
s += 2;   //② 正常执行

//①和②有什么区别？ 

```

练习2：

```java
int i = 1;
i *= 0.1;
System.out.println(i);//0
i++;
System.out.println(i);//1

```

练习3：

```java
int m = 2;
int n = 3;
n *= m++; 	//n = n * m++;
System.out.println("m=" + m);//3
System.out.println("n=" + n);//6

```

练习4：

```java
int n = 10;
n += (n++) + (++n);  //n = n + (n++) + (++n)
System.out.println(n);//32

```

练习5：你有几种办法实现变量值减1？变量值减2呢？

```java
/**
 * @author 尚硅谷-宋红康
 * @create 16:55
 */
public class MinusTest {
    public static void main(String[] args) {
        //练习①：变量值减1
        short s = 10;
        //方式1：
        //s = (short)(s - 1);
        //方式2：推荐
        s--; //或者 --s
        //方式3：
        s -= 1;

        //练习②：变量值减2
        short s1 = 10;
        //方式1：
        //s1 = (short)(s1 - 2);
        //方式2：推荐
        s1 -= 2;
    }
}
```

### 7.3 比较(关系)运算符

![image-20220312001742263](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312001742263.png)

- 比较运算符的结果都是boolean型，也就是要么是true，要么是false。

- \>   <   >=  <= ：只适用于基本数据类型（除boolean类型之外）

  ==   != ：适用于基本数据类型和引用数据类型

- 比较运算符“`==`”不能误写成“`=`” 

举例：

```java
class CompareTest {
	public static void main(String[] args) {
		int i1 = 10;
		int i2 = 20;
		
		System.out.println(i1 == i2);//false
		System.out.println(i1 != i2);//true
		System.out.println(i1 >= i2);//false


		int m = 10;
		int n = 20;
		System.out.println(m == n);//false
		System.out.println(m = n);//20

		boolean b1 = false;
		boolean b2 = true;
		System.out.println(b1 == b2);//false
		System.out.println(b1 = b2);//true
	}
}
```

思考：

```java
boolean b1 = false;
//区分好==和=的区别。
if(b1 == true)  //if(b1 = true)
	System.out.println("结果为真");
else
	System.out.println("结果为假");

```

### 7.4 逻辑运算符

#### 7.4.1 基本语法

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312001943403.png" alt="image-20220312001943403" style="zoom: 67%;" />

- 逻辑运算符，操作的都是boolean类型的变量或常量，而且运算得结果也是boolean类型的值。

- 运算符说明：
  - & 和 &&：表示"且"关系，当符号左右两边布尔值都是true时，结果才能为true。否则，为false。
  - | 和 || ：表示"或"关系，当符号两边布尔值有一边为true时，结果为true。当两边都为false时，结果为false
  - ! ：表示"非"关系，当变量布尔值为true时，结果为false。当变量布尔值为false时，结果为true。
  - ^ ：当符号左右两边布尔值不同时，结果为true。当两边布尔值相同时，结果为false。
    - 理解：`异或，追求的是“异”！`

- 逻辑运算符用于连接布尔型表达式，在Java中不可以写成 3 < x < 6，应该写成x > 3 & x < 6 。


- **区分“&”和“&&”：**

  - 相同点：如果符号左边是true，则二者都执行符号右边的操作

  - 不同点：& ： 如果符号左边是false,则继续执行符号右边的操作

  ​                                && ：如果符号左边是false,则不再继续执行符号右边的操作

  - 建议：开发中，推荐使用 && 

- **区分“|”和“||”：**


  - 相同点：如果符号左边是false，则二者都执行符号右边的操作

  - 不同点：| ： 如果符号左边是true，则继续执行符号右边的操作

    ​               || ：如果符号左边是true，则不再继续执行符号右边的操作

  - 建议：开发中，推荐使用 ||

 代码举例：

```java
public class LoginTest {
	public static void main(String[] args) {
		int a = 3;
		int b = 4;
		int c = 5;

		// & 与，且；有false则false
		System.out.println((a > b) & (a > c)); 
		System.out.println((a > b) & (a < c)); 
		System.out.println((a < b) & (a > c)); 
		System.out.println((a < b) & (a < c)); 
		System.out.println("===============");
		// | 或；有true则true
		System.out.println((a > b) | (a > c)); 
		System.out.println((a > b) | (a < c)); 
		System.out.println((a < b) | (a > c));
		System.out.println((a < b) | (a < c));
		System.out.println("===============");
		// ^ 异或；相同为false，不同为true
		System.out.println((a > b) ^ (a > c));
		System.out.println((a > b) ^ (a < c)); 
		System.out.println((a < b) ^ (a > c)); 
		System.out.println((a < b) ^ (a < c)); 
		System.out.println("===============");
		// ! 非；非false则true，非true则false
		System.out.println(!false);
		System.out.println(!true);
        
        //&和&&的区别
        System.out.println((a > b) & (a++ > c)); 
        System.out.println("a = " + a);
        System.out.println((a > b) && (a++ > c)); 
        System.out.println("a = " + a);
        System.out.println((a == b) && (a++ > c)); 
        System.out.println("a = " + a);
        
        //|和||的区别
        System.out.println((a > b) | (a++ > c)); 
        System.out.println("a = " + a);
        System.out.println((a > b) || (a++ > c)); 
        System.out.println("a = " + a);
        System.out.println((a == b) || (a++ > c)); 
        System.out.println("a = " + a);
	}
}
```

#### 7.4.2 案例与练习

**案例：**

```
1. 定义类 CompareLogicExer
2. 定义 main方法
3. 定义一个int类型变量a,变量b,都赋值为20
4. 定义boolean类型变量bo1 , 判断++a 是否被3整除,并且a++ 是否被7整除,将结果赋值给bo1
5. 输出a的值,bo1的值
6. 定义boolean类型变量bo2 , 判断b++ 是否被3整除,并且++b 是否被7整除,将结果赋值给bo2
7. 输出b的值,bo2的值
```

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:42
 */
public class CompareLogicExer {
    public static void main(String[] args){
        int a = 20;
        int b = 20;
        boolean bo1 = ((++a % 3) == 0) && ((a++ % 7) == 0);
        System.out.println("bo1的值：" + bo1);
        System.out.println("a的值：" + a);
        System.out.println("----------------------------");
        
        boolean bo2 = ((b++ % 3) == 0) && ((++b % 7) == 0); 
        System.out.println("bo2的值：" + bo2);
        System.out.println("b的值：" + b);
    }
}
```

**练习1：**区分 & 和 && 

```java
int x = 1;
int y = 1;

if(x++ == 2 & ++y == 2){
	x = 7;
}
System.out.println("x=" + x + ",y=" + y);

```

```java
int x = 1,y = 1;

if(x++ == 2 && ++y == 2){
	x =7;
}
System.out.println("x="+x+",y="+y);

```

**练习2：**区分 | 和 ||

```java
int x = 1,y = 1;

if(x++==1 | ++y==1){
	x =7;
}
System.out.println("x="+x+",y="+y);

```

```java
int x = 1,y = 1;

if(x++==1 || ++y==1){
	x =7;
}
System.out.println("x="+x+",y="+y);

```

**练习3：**程序输出

```java
class  Test  {
	public static void main (String []  args)  {
		boolean x = true;
        boolean y = false;
        short z = 42;
        
        if ((z++ == 42) && (y = true)) {
            z++;
        }
        if ((x = false) || (++z == 45)) {
            z++;
        }

        System.out.println("z=" + z);
	}
}
```

```java
//结果为：
//z= 46
```

### 7.5 位运算符（难点、非重点）

#### 7.5.1 基本语法

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220313174721111.png" alt="image-20220313174721111" style="zoom:75%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312002506339.png" alt="image-20220312002506339" style="zoom:67%;" />

- 位运算符的运算过程都是基于二进制的补码运算

**（1）左移：<<**

<font color='red'>运算规则：在一定范围内，数据每向左移动一位，相当于原数据*2。（正数、负数都适用）</font>

【注意】当左移的位数n超过该数据类型的总位数时，相当于左移（n-总位数）位

```java
3<<4  类似于  3*2的4次幂 => 3*16 => 48
```

![image-20200225113651675](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225113651675.png)

```java
-3<<4  类似于  -3*2的4次幂 => -3*16 => -48
```

![image-20200225114707524](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225114707524.png)

**（2）右移：>>**

<font color='red'>运算规则：在一定范围内，数据每向右移动一位，相当于原数据/2。（正数、负数都适用）</font>

【注意】如果不能整除，`向下取整`。 

```java
69>>4  类似于  69/2的4次 = 69/16 =4
```

![image-20200225115636844](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225115636844.png)

```
-69>>4  类似于  -69/2的4次 = -69/16 = -5
```

![image-20200225120112188](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225120112188.png)

**（3）无符号右移：>>>**

运算规则：往右移动后，左边空出来的位直接补0。（正数、负数都适用）

```
69>>>4  类似于  69/2的4次 = 69/16 =4
```

![image-20200225121104734](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225121104734.png)

```
-69>>>4   结果：268435451
```

![image-20200225121244290](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225121244290.png)

**（4）按位与：&**

运算规则：对应位都是1才为1，否则为0。

- 1 & 1 结果为1

- 1 & 0 结果为0

- 0 & 1 结果为0


- 0 & 0 结果为0


```java
9 & 7 = 1
```

![image-20200225122440953](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225122440953.png)

```java
-9 & 7 = 7
```

![image-20200225122221616](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225122221616.png)

**（5）按位或：|**

运算规则：对应位只要有1即为1，否则为0。

- 1 | 1 结果为1

- 1 | 0 结果为1

- 0 | 1 结果为1

- 0 & 0 结果为0


```java
9 | 7  //结果： 15
```

![image-20200225122758851](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225122758851.png)

```java
-9 | 7 //结果： -9
```

![image-20200225123409130](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225123409130.png)

**（6）按位异或：^**

运算规则：对应位一个为1一个为0，才为1，否则为0。

- 1 ^ 1 结果为0

- 1 ^ 0 结果为1

- 0 ^ 1 结果为1


- 0 ^ 0 结果为0


```java
9 ^ 7  //结果为14
```

![image-20200225123445305](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225123445305.png)

```java
-9 ^ 7 //结果为-16
```

![image-20200225133145727](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225133145727.png)

**（7）按位取反：~**

运算规则：对应位为1，则结果为0；对应位为0，则结果为1。

- ~0就是1  

- ~1就是0

```java
~9  //结果：-10
```

![image-20200225124112662](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225124112662.png)

```java
~-9  //结果：8
```

![image-20200225124156862](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200225124156862.png)

#### 7.5.2 举例

**举例1：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/snipaste_20220312_002549.jpg" alt="snipaste_20220312_002549" style="zoom: 67%;" />

**举例2：体会 m = k ^ n = (m ^ n) ^ n**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312002736157.png" alt="image-20220312002736157" style="zoom:67%;" />

#### 7.5.3 案例

**案例1：**高效的方式计算2 * 8的值（经典面试题）

```
答案：2 << 3 、  8  << 1
```

**案例2：**如何交换两个int型变量的值？String呢？

```java
/**
 * @author 尚硅谷-宋红康
 * @create 16:58
 */
public class BitExer {
    public static void main(String[] args) {
        int m = 10;
		int n = 5;

		System.out.println("m = " + m + ", n = " + n);

		//（推荐）实现方式1：优点：容易理解，适用于不同数据类型    缺点：需要额外定义变量
		//int temp = m;
		//m = n;
		//n = temp;

		//实现方式2：优点：没有额外定义变量    缺点：可能超出int的范围；只能适用于数值类型
		//m = m + n; //15 = 10 + 5
		//n = m - n;//10 = 15 - 5
		//m = m - n;//5 = 15 - 10
	
		//实现方式3：优点：没有额外定义变量    缺点：不易理解；只能适用于数值类型
		m = m ^ n; 
		n = m ^ n; //(m ^ n) ^ n
		m = m ^ n;

		System.out.println("m = " + m + ", n = " + n);
    }
}
```

### 7.6 条件运算符

#### 7.6.1 基本语法

- 条件运算符格式：

```java
(条件表达式)? 表达式1:表达式2
```

- 说明：条件表达式是boolean类型的结果，根据boolean的值选择表达式1或表达式2

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220312002841945.png" alt="image-20220312002841945" style="zoom:67%;" />

- 如果运算后的结果赋给新的变量，要求表达式1和表达式2为同种或兼容的类型

```java
public static void main(String[] args) {
    int i = (1==2 ? 100 : 200);
    System.out.println(i);//200
    
    boolean marry = false;
	System.out.println(marry ? "已婚" : "未婚"  );
    
    double d1 = (m1 > m2)? 1 : 2.0;
	System.out.println(d1);
    
    int num = 12;
    System.out.println(num > 0? true : "num非正数");
}
```

#### 7.6.2 案例

**案例1：**获取两个数中的较大值

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:40
 */
public class ConditionExer1 {
    public static void main(String[] args) {
        //获取两个数的较大值
        int m1 = 10;
        int m2 = 20;

        int max1 = (m1 > m2)? m1 : m2;
        System.out.println("m1和m2中的较大值为" + max1);
    }
}
```

**案例2：**获取三个数中的最大值

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:43
 */
public class ConditionExer2 {
    public static void main(String[] args) {
        int n1 = 23;
        int n2 = 13;
        int n3 = 33;
        //写法1：
        int tempMax = (n1 > n2)? n1:n2;
        int finalMax = (tempMax > n3)? tempMax : n3;
        System.out.println("三个数中最大值为：" + finalMax);

        //写法2：不推荐，可读性差
        int finalMax1 = (((n1 > n2)? n1:n2) > n3)? ((n1 > n2)? n1:n2) : n3;
        System.out.println("三个数中最大值为：" + finalMax1);
    }
}
```

**案例3：**今天是周2，10天以后是周几？

要求：控制台输出"今天是周2，10天以后是周x"。

```java
/**
 * @author 尚硅谷-宋红康
 * @create 12:46
 */
public class ConditionExer3 {

    public static void main(String[] args) {
        int week = 2;
        week += 10;
        week %= 7;
        System.out.println("今天是周2,10天以后是周" + (week == 0 ? "日" : week));
    }

}
```

#### 7.6.3 与if-else的转换关系

- 凡是可以使用条件运算符的地方，都可以改写为if-else结构。反之，不成立。


- 开发中，如果既可以使用条件运算符，又可以使用if-else，推荐使用条件运算符。因为执行效率稍高。

```java
//if-else实现获取两个数的较大值

int i1 = 10;
int i2 = 20;

int max;//声明变量max，用于记录i1和i2的较大值

if(i1 > i2){
    max = i1;
}else{
    max = i2;
}

System.out.println(max);
```



### 7.7 运算符优先级

运算符有不同的优先级，所谓优先级就是在表达式运算中的运算符顺序。

上一行中的运算符总是优先于下一行的。

| 优先级 |    运算符说明    |             Java运算符             |
| ------ | :--------------: | :--------------------------------: |
| 1      |       括号       |          `()`、`[]`、`{}`          |
| 2      |      正负号      |              `+`、`-`              |
| 3      |    单元运算符    |       `++`、`--`、`~`、`！`        |
| 4      | 乘法、除法、求余 |           `*`、`/`、`%`            |
| 5      |    加法、减法    |              `+`、`-`              |
| 6      |    移位运算符    |         `<<`、`>>`、`>>>`          |
| 7      |    关系运算符    | `<`、`<=`、`>=`、`>`、`instanceof` |
| 8      |    等价运算符    |             `==`、`!=`             |
| 9      |      按位与      |                `&`                 |
| 10     |     按位异或     |                `^`                 |
| 11     |      按位或      |                `|`                 |
| 12     |      条件与      |                `&&`                |
| 13     |      条件或      |                `||`                |
| 14     |    三元运算符    |               `? :`                |
| 15     |    赋值运算符    | `=`、`+=`、`-=`、`*=`、`/=`、`%=`  |
| 16     |   位赋值运算符   |  `&=`、`|=`、`<<=`、`>>=`、`>>>=`  |

> 开发建议：
>
> 1. 不要过多的依赖运算的优先级来控制表达式的执行顺序，这样可读性太差，尽量`使用()来控制`表达式的执行顺序。
> 2. 不要把一个表达式写得过于复杂，如果一个表达式过于复杂，则把它`分成几步`来完成。例如：
>    ​ (num1 + num2) * 2 > num3 && num2 > num3 ? num3 : num1 + num2;

## 8. 【拓展】关于字符集

### 8.1 字符集

- **编码与解码**

计算机中储存的信息都是用`二进制数`表示的，而我们在屏幕上看到的数字、英文、标点符号、汉字等字符是二进制数转换之后的结果。按照某种规则，将字符存储到计算机中，称为`编码` 。反之，将存储在计算机中的二进制数按照某种规则解析显示出来，称为`解码` 。

- **字符编码（Character Encoding）** : 就是一套自然语言的字符与二进制数之间的对应规则。

- **字符集**：也叫编码表。是一个系统支持的所有字符的集合，包括各国家文字、标点符号、图形符号、数字等。

### 8.2 ASCII码

- ASCII码（American Standard Code for Information Interchange，美国信息交换标准代码）：上个世纪60年代，美国制定了一套字符编码，对`英语字符`与二进制位之间的关系，做了统一规定。这被称为ASCII码。
- ASCII码用于显示现代英语，主要包括控制字符（回车键、退格、换行键等）和可显示字符（英文大小写字符、阿拉伯数字和西文符号）。
- 基本的ASCII字符集，使用7位（bits）表示一个字符（最前面的1位统一规定为0），共`128个`字符。比如：空格“SPACE”是32（二进制00100000），大写的字母A是65（二进制01000001）。
- 缺点：不能表示所有字符。

![image-20220513095907601](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513095907601.png)

### 8.3 ISO-8859-1字符集

* 拉丁码表，别名Latin-1，用于显示欧洲使用的语言，包括荷兰语、德语、意大利语、葡萄牙语等
* ISO-8859-1使用单字节编码，兼容ASCII编码。

### 8.4 GBxxx字符集

* GB就是国标的意思，是为了`显示中文`而设计的一套字符集。
* **GB2312**：简体中文码表。一个小于127的字符的意义与原来相同，即向下兼容ASCII码。但两个大于127的字符连在一起时，就表示一个汉字，这样大约可以组合了包含`7000多个简体汉字`，此外数学符号、罗马希腊的字母、日文的假名们都编进去了，这就是常说的"全角"字符，而原来在127号以下的那些符号就叫"半角"字符了。
* **GBK**：最常用的中文码表。是在GB2312标准基础上的扩展规范，使用了`双字节`编码方案，共收录了`21003个`汉字，完全兼容GB2312标准，同时支持`繁体汉字`以及日韩汉字等。
* **GB18030**：最新的中文码表。收录汉字`70244个`，采用`多字节`编码，每个字可以由1个、2个或4个字节组成。支持中国国内少数民族的文字，同时支持繁体汉字以及日韩汉字等。

### 8.5 Unicode码

- Unicode编码为表达`任意语言的任意字符`而设计，也称为统一码、标准万国码。Unicode 将世界上所有的文字用`2个字节`统一进行编码，为每个字符设定唯一的二进制编码，以满足跨语言、跨平台进行文本处理的要求。

- Unicode 的缺点：这里有三个问题：
  - 第一，英文字母只用一个字节表示就够了，如果用更多的字节存储是`极大的浪费`。
  - 第二，如何才能`区别Unicode和ASCII`？计算机怎么知道两个字节表示一个符号，而不是分别表示两个符号呢？
  - 第三，如果和GBK等双字节编码方式一样，用最高位是1或0表示两个字节和一个字节，就少了很多值无法用于表示字符，`不够表示所有字符`。
- Unicode在很长一段时间内无法推广，直到互联网的出现，为解决Unicode如何在网络上传输的问题，于是面向传输的众多 UTF（UCS Transfer Format）标准出现。具体来说，有三种编码方案，UTF-8、UTF-16和UTF-32。

### 8.6 UTF-8

* Unicode是字符集，UTF-8、UTF-16、UTF-32是三种`将数字转换到程序数据`的编码方案。顾名思义，UTF-8就是每次8个位传输数据，而UTF-16就是每次16个位。其中，UTF-8 是在互联网上`使用最广`的一种 Unicode 的实现方式。
* 互联网工程工作小组（IETF）要求所有互联网协议都必须支持UTF-8编码。所以，我们开发Web应用，也要使用UTF-8编码。UTF-8 是一种`变长的编码方式`。它可以使用 1-4 个字节表示一个符号它使用一至四个字节为每个字符编码，编码规则：
  1. 128个US-ASCII字符，只需一个字节编码。
  2. 拉丁文等字符，需要二个字节编码。 
  3. 大部分常用字（含中文），使用三个字节编码。
  4. 其他极少使用的Unicode辅助字符，使用四字节编码。


- 举例

Unicode符号范围  | UTF-8编码方式

```
(十六进制)           | （二进制）

————————————————————|—–—–—–—–—–—–—–—–—–—–—–—–—–—–

0000 0000-0000 007F | 0xxxxxxx（兼容原来的ASCII）

0000 0080-0000 07FF | 110xxxxx 10xxxxxx

0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx

0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
```

![image-20220525164636164](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220525164636164.png)

### 8.7 小结

![字符集](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E5%AD%97%E7%AC%A6%E9%9B%86.jpg)

> 注意：在中文操作系统上，ANSI（美国国家标准学会、AMERICAN NATIONAL STANDARDS INSTITUTE: ANSI）编码即为GBK；在英文操作系统上，ANSI编码即为ISO-8859-1。																	





## 9.第二章企业真题

1. **使用高效的方式计算2 * 8**

> 2 << 3，即2左移三位

2. **&和&&的区别**

> &是逻辑与，&&是短路与
>
> 当前第一个条件是ture的时候，两者都会判断第二个条件
>
> 当前第一个条件是flase的时候，&会判断第二个条件，而&&直接短路，并不会计算

3. **Java中的基本数据类型有哪些？String是基本数据类型吗？**

> Java中的基本数据类型一共有8种，分别是
>
> 整型：byte、short、int、long
>
> 浮点型：float、double
>
> 字符型：chat
>
> 布尔型：boolean
>
> String不是基本数据类型，而是引用数据类型（String类）。

4. **java开发中计算金额时应该使用什么数据类型？**

> 金额不能使用浮点类型，而应该使用`BigDecimal`类型

5. **char型变量中能不能存储一个汉字，为什么？**

> 可以，因为char使用的是unicode字符集，其包括了全世界内的所有字符

6. **代码分析**

> short s1 = 1;
>
> s1 = s1 + 1
>
> 错误，计算结果右边时int类型，要转为short类型需要进行强制类型转化

> short s1 = 1;
>
> s1 += 1
>
> 正确，因为赋值运算符+=不会改变数据类型



# 第03章_流程控制语句

## 1.流程控制语句概述

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC1%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E5%9F%BA%E6%9C%AC%E8%AF%AD%E6%B3%95-%E7%AC%AC03%E7%AB%A0.png" alt="第1阶段：Java基本语法-第03章" style="zoom:45%;" />

***

- 流程控制语句是用来控制程序中各`语句执行顺序`的语句，可以把语句组合成能`完成一定功能`的小逻辑模块。

- 程序设计中规定的`三种`流程结构，即：
  - **顺序结构**
    - 程序从上到下逐行地执行，中间没有任何判断和跳转。
  - **分支结构**
    - 根据条件，选择性地执行某段代码。
    - 有`if…else`和`switch-case`两种分支语句。
  - **循环结构**
    - 根据循环条件，重复性的执行某段代码。
    - 有`for`、`while`、`do-while`三种循环语句。
    - 补充：JDK5.0 提供了`foreach`循环，方便的遍历集合、数组元素。（第12章集合中讲解）
- 生活中、工业生产中流程控制举例

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%B4%97%E8%A1%A3%E6%B5%81%E7%A8%8B.jpg" alt="洗衣流程" style="zoom:100%;" />

## 2. 顺序结构

顺序结构就是程序`从上到下逐行`地执行。表达式语句都是顺序执行的。并且上一行对某个变量的修改对下一行会产生影响。

![image-20211218093256771](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211218093256771.png )

```java
public class StatementTest{
	public static void main(String[] args){
		int x = 1;
		int y = 2;
		System.out.println("x = " + x);		
        System.out.println("y = " + y);	
        //对x、y的值进行修改
        x++;
        y = 2 * x + y;
        x = x * 10;	
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }
}
```

Java中定义变量时采用合法的`前向引用`。如：

```java
public static void main(String[] args) {
	int num1 = 12;
	int num2 = num1 + 2;
}
```

错误形式：

```java
public static void main(String[] args) {
	int num2 = num1 + 2;
	int num1 = 12;
}
```

## 3. 分支语句

### 3.1 if-else条件判断结构

#### 3.1.1 基本语法

**结构1：单分支条件判断：if**

`格式：`

```java
if(条件表达式)｛
  	语句块;
｝
```

`说明：`条件表达式必须是布尔表达式（关系表达式或逻辑表达式）或 布尔变量。

`执行流程：`

1. 首先判断条件表达式看其结果是true还是false
2. 如果是true就执行语句块
3. 如果是false就不执行语句块

![image-20220514160139926](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514160139926.png)

**结构2：双分支条件判断：if...else**

`格式：`

```java
if(条件表达式) { 
  	语句块1;
}else {
  	语句块2;
}
```

`执行流程：`

1. 首先判断条件表达式看其结果是true还是false
2. 如果是true就执行语句块1
3. 如果是false就执行语句块2

![image-20220514160243421](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514160243421.png)

**结构3：多分支条件判断：if...else if...else**

`格式：`

```java
if (条件表达式1) {
  	语句块1;
} else if (条件表达式2) {
  	语句块2;
}
...
}else if (条件表达式n) {
 	语句块n;
} else {
  	语句块n+1;
}
```

`说明：`一旦条件表达式为true，则进入执行相应的语句块。执行完对应的语句块之后，就跳出当前结构。

`执行流程：`

1. 首先判断关系表达式1看其结果是true还是false
2. 如果是true就执行语句块1，然后结束当前多分支
3. 如果是false就继续判断关系表达式2看其结果是true还是false
4. 如果是true就执行语句块2，然后结束当前多分支
5. 如果是false就继续判断关系表达式…看其结果是true还是false

​    …

   n.  如果没有任何关系表达式为true，就执行语句块n+1，然后结束当前多分支。

![image-20220514160538651](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514160538651.png)

#### 2.1.2 应用举例

**案例1：**成年人心率的正常范围是每分钟60-100次。体检时，如果心率不在此范围内，则提示需要做进一步的检查。

```java
public class IfElseTest1 {
    public static void main(String[] args){
        int heartBeats = 89;

        if(heartBeats < 60 || heartBeats > 100){
            System.out.println("你需要做进一步的检查");
        }

        System.out.println("体检结束");
    }
}
```

**案例2：**定义一个整数，判定是偶数还是奇数    

```java
public class IfElseTest2 {
    public static void main(String[] args){
        int a = 10;

        if(a % 2 == 0) {
            System.out.println(a + "是偶数");
        } else{
            System.out.println(a + "是奇数");
        }
    }
}
```

**案例3：**

```
岳小鹏参加Java考试，他和父亲岳不群达成承诺：
如果：
成绩为100分时，奖励一辆跑车；
成绩为(80，99]时，奖励一辆山地自行车；
当成绩为[60,80]时，奖励环球影城一日游；
其它时，胖揍一顿。

说明：默认成绩是在[0,100]范围内
```

```java
public class IfElseTest3 {
    public static void main(String[] args) {

        int score = 67;//岳小鹏的期末成绩
        //写法一：默认成绩范围为[0,100]
        if(score == 100){
            System.out.println("奖励一辆跑车");
        }else if(score > 80 && score <= 99){    //错误的写法：}else if(80 < score <= 99){
            System.out.println("奖励一辆山地自行车");
        }else if(score >= 60 && score <= 80){
            System.out.println("奖励环球影城玩一日游");
        }
        //else{
        //	System.out.println("胖揍一顿");
        //}


        //写法二：
        // 默认成绩范围为[0,100]
        if(score == 100){
            System.out.println("奖励一辆跑车");
        }else if(score > 80){
            System.out.println("奖励一辆山地自行车");
        }else if(score >= 60){
            System.out.println("奖励环球影城玩一日游");
        }else{
            System.out.println("胖揍一顿");
        }
    }
}
```

![image-20221027185234087](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221027185234087.png)

![image-20221027185500389](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221027185500389.png)

> 当条件表达式之间是“`互斥`”关系时（即彼此没有交集），条件判断语句及执行语句间顺序无所谓。
>
> 当条件表达式之间是“`包含`”关系时，“`小上大下 / 子上父下`”，否则范围小的条件表达式将不可能被执行。

#### 2.1.3 if...else嵌套

在 if 的语句块中，或者是在else语句块中，又包含了另外一个条件判断（可以是单分支、双分支、多分支），就构成了`嵌套结构`。

**执行的特点：**
（1）如果是嵌套在if语句块中的，只有当外部的if条件满足，才会去判断内部的条件
（2）如果是嵌套在else语句块中的，只有当外部的if条件不满足，进入else后，才会去判断内部的条件

**案例4：**由键盘输入三个整数分别存入变量num1、num2、num3，对它们进行排序(使用 if-else if-else)，并且从小到大输出。

```java
class IfElseTest4 {
	public static void main(String[] args) {
		
			//声明num1,num2,num3三个变量并赋值
			int num1 = 23,num2 = 32,num3 = 12;

			if(num1 >= num2){
				
				if(num3 >= num1)
					System.out.println(num2 + "-" + num1 + "-" + num3);
				else if(num3 <= num2)
					System.out.println(num3 + "-" + num2 + "-" + num1);
				else
					System.out.println(num2 + "-" + num3 + "-" + num1);
			}else{ //num1 < num2
				
				if(num3 >= num2){
					System.out.println(num1 + "-" + num2 + "-" + num3);
				}else if(num3 <= num1){
					System.out.println(num3 + "-" + num1 + "-" + num2);
				}else{
					System.out.println(num1 + "-" + num3 + "-" + num2);
				}
			}
	}
}
```

#### 2.1.4 其它说明

- 语句块只有一条执行语句时，一对`{}可以省略`，但建议保留
- 当if-else结构是“多选一”时，最后的`else是可选的`，根据需要可以省略

#### 2.1.5 练习

**练习1：**

```java
//1)对下列代码，若有输出，指出输出结果。
int x = 4;
int y = 1;
if (x > 2) {
       if (y > 2) 
            System.out.println(x + y);
       		System.out.println("atguigu");
} else
       System.out.println("x is " + x);

输出结果：atguigu
```

**练习2：**

```java
boolean b = true;
//如果写成if(b=false)能编译通过吗？如果能，结果是？
if(b == false) 	 //建议：if(!b)
	System.out.println("a");
else if(b)
	System.out.println("b");
else if(!b)
	System.out.println("c");
else
	System.out.println("d");
```

**练习3：**

定义两个整数，分别为small 和 big，如果第一个整数small大于第二个整数big，就交换。输出显示small和big变量的值。

```java
public class IfElseExer3 {
    public static void main(String[] args) {
        int small = 10;
        int big = 9;

        if (small > big) {
            int temp = small;
            small = big;
            big = temp;
        }
        System.out.println("small=" + small + ",big=" + big);
    }
}
```

**练习4：**小明参加期末Java考试，通过考试成绩，判断其Java等级，成绩范围[0,100]

- 90-100      优秀
- 80-89        好
- 70-79        良
- 60-69        及格
- 60以下    不及格

```java
import java.util.Scanner;
//写法一：
public class IfElseExer4 {
    public static void main(String[] args) {
        System.out.print("小明的期末Java成绩是：[0,100]");
        int score = 89;

        if (score < 0 || score > 100) {
            System.out.println("你的成绩是错误的");
        } else if (score >= 90 && score <= 100) {
            System.out.println("你的成绩属于优秀");
        } else if (score >= 80 && score < 90) {
            System.out.println("你的成绩属于好");
        } else if (score >= 70 && score < 80) {
            System.out.println("你的成绩属于良");
        } else if (score >= 60 && score < 70) {
            System.out.println("你的成绩属于及格");
        } else {
            System.out.println("你的成绩属于不及格");
        }
    }
}

```

```java
import java.util.Scanner;
//写法二：
public class IfElseExer4 {
    public static void main(String[] args) {
        System.out.print("小明的期末Java成绩是：[0,100]");
        int score = 89;

        if (score < 0 || score > 100) {
            System.out.println("你的成绩是错误的");
        } else if (score >= 90) {
            System.out.println("你的成绩属于优秀");
        } else if (score >= 80) {
            System.out.println("你的成绩属于好");
        } else if (score >= 70) {
            System.out.println("你的成绩属于良");
        } else if (score >= 60) {
            System.out.println("你的成绩属于及格");
        } else {
            System.out.println("你的成绩属于不及格");
        }

    }
}

```

**练习5：**

```
编写程序，声明2个int型变量并赋值。判断两数之和，如果大于等于50，打印“hello world!”
```

```java
public class IfElseExer5 {

    public static void main(String[] args) {
        int num1 = 12, num2 = 32;
        
        if (num1 + num2 >= 50) {
            System.out.println("hello world!");
        }
    }
}
```

**练习6：**

```
编写程序，声明2个double型变量并赋值。判断第一个数大于10.0，且第2个数小于20.0，打印两数之和。否则，打印两数的乘积。
```

```java
public class IfElseExer6 {

    public static void main(String[] args) {
        double d1 = 21.2,d2 = 12.3;
        
        if(d1 > 10.0 && d2 < 20.0){
            System.out.println("两数之和为：" + (d1 + d2));
        }else{
            System.out.println("两数乘积为：" + (d1 * d2));
        }
    }

}
```

**练习7：判断水的温度**

```
如果大于95℃，则打印“开水”；

如果大于70℃且小于等于95℃，则打印“热水”；

如果大于40℃且小于等于70℃，则打印“温水”；

如果小于等于40℃，则打印“凉水”。
```

```java
public class IfElseExer7 {

    public static void main(String[] args) {
        int waterTemperature = 85;
        
        if(waterTemperature > 95){
            System.out.println("开水");
        }else if(waterTemperature > 70 && waterTemperature <= 95){
            System.out.println("热水");
        }else if(waterTemperature > 40 && waterTemperature <= 70){
            System.out.println("温水");
        }else{
            System.out.println("凉水");
        }
    }

}
```

### 2.2 switch-case选择结构

#### 2.2.1 基本语法

**语法格式：**

```java
switch(表达式){
    case 常量值1:
        语句块1;
        //break;
    case 常量值2:
        语句块2;
        //break; 
    // ...
   [default:
        语句块n+1;
        break;
   ]
}
```

**执行流程图：**

![image-20220514101841710](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514101841710.png)

**执行过程：**

第1步：根据switch中表达式的值，依次匹配各个case。如果表达式的值等于某个case中的常量值，则执行对应case中的执行语句。

第2步：执行完此case的执行语句以后，
​              情况1：如果遇到break,则执行break并跳出当前的switch-case结构
​		        情况2：如果没有遇到break，则会继续执行当前case之后的其它case中的执行语句。--->case穿透
​		         ...
​		        直到遇到break关键字或执行完所有的case及default的执行语句，跳出当前的switch-case结构

**使用注意点：**

- **switch(表达式)中表达式的值必须是下述几种类型之**一：==整数，字符，枚举和字符串（自Java 7以来）类型==

- case子句中的值必须是常量，不能是变量名或不确定的表达式值或范围；

- 同一个switch语句，所有case子句中的常量值互不相同；

- break语句用来在执行完一个case分支后使程序跳出switch语句块；

  如果没有break，程序会顺序执行到switch结尾；

- default子句是可选的。同时，位置也是灵活的。当没有匹配的case时，执行default语句。

#### 2.2.2 应用举例

**案例1：**

```java
public class SwitchCaseTest1 {
    public static void main(String args[]) {
        int num = 1;
		switch(num){
			case 0:
				System.out.println("zero");
				break;
			case 1:
				System.out.println("one");
				break;
			case 2:
				System.out.println("two");
				break;
			case 3:
				System.out.println("three");
				break;
			default:
				System.out.println("other");
				//break;
		}
    }
}
```

**案例2：**

```java
public class SwitchCaseTest2 {
    public static void main(String args[]) {
        String season = "summer";
        switch (season) {
            case "spring":
                System.out.println("春暖花开");
                break;
            case "summer":
                System.out.println("夏日炎炎");
                break;
            case "autumn":
                System.out.println("秋高气爽");
                break;
            case "winter":
                System.out.println("冬雪皑皑");
                break;
            default:
                System.out.println("季节输入有误");
                break;
        }
    }
}
```

错误举例：

```java
int key = 10;
switch(key){
	case key > 0 :
        System.out.println("正数");
        break;
    case key < 0:
        System.out.println("负数");
        break;
    default:
        System.out.println("零");
        break;
}
```

**案例3：**使用switch-case实现：对学生成绩大于60分的，输出“合格”。低于60分的，输出“不合格”。

```java
class SwitchCaseTest3 {
	public static void main(String[] args) {
		
		int score = 67;
		/*
		写法1：极不推荐
		switch(score){
			case 0:
				System.out.println("不及格");
				break;
			case 1:
				System.out.println("不及格");
				break;
			//...

			case 60:
				System.out.println("及格");
				break;
			//...略...
		
		}
		*/

		//写法2：
		switch(score / 10){
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				System.out.println("不及格");
				break;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				System.out.println("及格");
				break;
			default:
				System.out.println("输入的成绩有误");
				break;
		}

		//写法3：
		switch(score / 60){
			case 0:
				System.out.println("不及格");
				break;
			case 1:
				System.out.println("及格");
				break;
			default:
				System.out.println("输入的成绩有误");
				break;
		}
	}
}
```

#### 2.2.3 利用case的穿透性

在switch语句中，如果case的后面不写break，将出现穿透现象，也就是一旦匹配成功，不会在判断下一个case的值，直接向后运行，直到遇到break或者整个switch语句结束，执行终止。

**案例4：**编写程序：从键盘上输入2023年的“month”和“day”，要求通过程序输出输入的日期为2023年的第几天。

```java
import java.util.Scanner;

class SwitchCaseTest4 {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		System.out.println("请输入2023年的month:");
		int month = scan.nextInt();

		System.out.println("请输入2023年的day:");
		int day = scan.nextInt();

		//这里就不针对month和day进行合法性的判断了，以后可以使用正则表达式进行校验。

		int sumDays = 0;//记录总天数
		
		//写法1 ：不推荐（存在冗余的数据）
		/*
		switch(month){
			case 1:
				sumDays = day;
				break;
			case 2:
				sumDays = 31 + day;
				break;
			case 3:
				sumDays = 31 + 28 + day;
				break;
			//....
		
			case 12:
				//sumDays = 31 + 28 + ... + 30 + day;
				break;
		}
		*/

		//写法2：推荐
		switch(month){
			case 12:
				sumDays += 30;//这个30是代表11月份的满月天数
			case 11:
				sumDays += 31;//这个31是代表10月份的满月天数
			case 10:
				sumDays += 30;//这个30是代表9月份的满月天数
			case 9:
				sumDays += 31;//这个31是代表8月份的满月天数
			case 8:
				sumDays += 31;//这个31是代表7月份的满月天数
			case 7:
				sumDays += 30;//这个30是代表6月份的满月天数
			case 6:
				sumDays += 31;//这个31是代表5月份的满月天数
			case 5:
				sumDays += 30;//这个30是代表4月份的满月天数
			case 4:
				sumDays += 31;//这个31是代表3月份的满月天数
			case 3:
				sumDays += 28;//这个28是代表2月份的满月天数
			case 2:
				sumDays += 31;//这个31是代表1月份的满月天数
			case 1:
				sumDays += day;//这个day是代表当月的第几天
		}
		
		System.out.println(month + "月" + day + "日是2023年的第" + sumDays + "天");
        //关闭资源
		scan.close();
	}
}
```

**拓展：**

```ini
从键盘分别输入年、月、日，判断这一天是当年的第几天
 
注：判断一年是否是闰年的标准：
   1）可以被4整除，但不可被100整除
	  或
   2）可以被400整除
   
例如：1900，2200等能被4整除，但同时能被100整除，但不能被400整除，不是闰年
```

```java
import java.util.Scanner;

public class SwitchCaseTest04 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入year:");
        int year = scanner.nextInt();

        System.out.print("请输入month:");
        int month = scanner.nextInt();

        System.out.print("请输入day:");
        int day = scanner.nextInt();

        //判断这一天是当年的第几天==>从1月1日开始，累加到xx月xx日这一天
        //(1)[1,month-1]个月满月天数
        //(2)单独考虑2月份是否是29天（依据是看year是否是闰年）
        //(3)第month个月的day天

        //声明一个变量days，用来存储总天数
        int sumDays = 0;

        //累加[1,month-1]个月满月天数
        switch (month) {
            case 12:
                //累加的1-11月
                sumDays += 30;//这个30是代表11月份的满月天数
                //这里没有break，继续往下走
            case 11:
                //累加的1-10月
                sumDays += 31;//这个31是代表10月的满月天数
                //这里没有break，继续往下走
            case 10:
                sumDays += 30;//9月
            case 9:
                sumDays += 31;//8月
            case 8:
                sumDays += 31;//7月
            case 7:
                sumDays += 30;//6月
            case 6:
                sumDays += 31;//5月
            case 5:
                sumDays += 30;//4月
            case 4:
                sumDays += 31;//3月
            case 3:
                sumDays += 28;//2月
                //在这里考虑是否可能是29天
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    sumDays++;//多加1天
                }
            case 2:
                sumDays += 31;//1月
            case 1:
                sumDays += day;//第month月的day天
        }

        //输出结果
        System.out.println(year + "年" + month + "月" + day + "日是这一年的第" + sumDays + "天");

        scanner.close();
    }
}
```

**案例5：**根据指定的月份输出对应季节

```java
import java.util.Scanner;

/*
 * 需求：指定一个月份，输出该月份对应的季节。一年有四季:
 * 		3,4,5	春季
 * 		6,7,8	夏季
 * 		9,10,11	秋季
 * 		12,1,2	冬季
 */
public class SwitchCaseTest5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("请输入月份：");
        int month = input.nextInt();

        /*
		switch(month) {
            case 1:
                System.out.println("冬季");
                break;
            case 2:
                System.out.println("冬季");
                break;
            case 3:
                System.out.println("春季");
                break;
            case 4:
                System.out.println("春季");
                break;
            case 5:
                System.out.println("春季");
                break;
            case 6:
                System.out.println("夏季");
                break;
            case 7:
                System.out.println("夏季");
                break;
            case 8:
                System.out.println("夏季");
                break;
            case 9:
                System.out.println("秋季");
                break;
            case 10:
                System.out.println("秋季");
                break;
            case 11:
                System.out.println("秋季");
                break;
            case 12:
                System.out.println("冬季");
                break;
            default:
                System.out.println("你输入的月份有误");
                break;
		}
		*/

        // 改进版
        switch(month) {
            case 1:
            case 2:
            case 12:
                System.out.println("冬季");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("春季");
                break;
            case 6:
            case 7:
            case 8:
                System.out.println("夏季");
                break;
            case 9:
            case 10:
            case 11:
                System.out.println("秋季");
                break;
            default:
                System.out.println("你输入的月份有误");
                break;
        }

        input.close();
    }
}

```

常见错误实现：

```java
switch(month){
    case 3|4|5://3|4|5 用了位运算符，11 | 100 | 101结果是 111是7
        System.out.println("春季");
        break;
    case 6|7|8://6|7|8用了位运算符，110 | 111 | 1000结果是1111是15
        System.out.println("夏季");
        break;
    case 9|10|11://9|10|11用了位运算符，1001 | 1010 | 1011结果是1011是11
        System.out.println("秋季");
        break;
    case 12|1|2://12|1|2 用了位运算符，1100 | 1 | 10 结果是1111，是15
        System.out.println("冬季");
        break;
    default:
        System.out.println("输入有误");
}
```

使用if-else实现：

```java
if ((month == 1) || (month == 2) || (month == 12)) {
    System.out.println("冬季");
} else if ((month == 3) || (month == 4) || (month == 5)) {
    System.out.println("春季");
} else if ((month == 6) || (month == 7) || (month == 8)) {
    System.out.println("夏季");
} else if ((month == 9) || (month == 10) || (month == 11)) {
    System.out.println("秋季");
} else {
    System.out.println("你输入的月份有误");
}
```

#### 2.2.4 if-else语句与switch-case语句比较

- 结论：凡是使用switch-case的结构都可以转换为if-else结构。反之，不成立。

- 开发经验：如果既可以使用switch-case，又可以使用if-else，建议使用switch-case。因为效率稍高。

- 细节对比：

  - if-else语句优势
    - if语句的条件是一个布尔类型值，if条件表达式为true则进入分支，可以用于范围的判断，也可以用于等值的判断，`使用范围更广`。
    - switch语句的条件是一个常量值（byte,short,int,char,枚举,String），只能判断某个变量或表达式的结果是否等于某个常量值，`使用场景较狭窄`。
  - switch语句优势
    - 当条件是判断某个变量或表达式是否等于某个固定的常量值时，使用if和switch都可以，习惯上使用switch更多。因为`效率稍高`。当条件是区间范围的判断时，只能使用if语句。
    - 使用switch可以利用`穿透性`，同时执行多个分支，而if...else没有穿透性。

- **案例：只能使用 if-else**

  从键盘输入一个整数，判断是正数、负数、还是零。

```java
import java.util.Scanner;

public class IfOrSwitchDemo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("请输入整数：");
        int num = input.nextInt();

        if (num > 0) {
            System.out.println(num + "是正整数");
        } else if (num < 0) {
            System.out.println(num + "是负整数");
        } else {
            System.out.println(num + "是零");
        }

        input.close();
    }
}
```

#### 2.2.5 练习

**练习1：**从键盘输入星期的整数值，输出星期的英文单词

```java
import java.util.Scanner;

public class SwitchCaseExer1 {
    public static void main(String[] args) {
        //定义指定的星期
        Scanner input = new Scanner(System.in);
        System.out.print("请输入星期值：");
        int weekday = input.nextInt();

        //switch语句实现选择
        switch(weekday) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("你输入的星期值有误！");
                break;
        }

        input.close();
    }
}
```

**练习2：**

```java
使用 switch 把小写类型的 char型转为大写。只转换 a, b, c, d, e. 其它的输出 “other”。
```

```java
public class SwitchCaseExer2 {

    public static void main(String[] args) {

        char word = 'c';
        switch (word) {
            case 'a':
                System.out.println("A");
                break;
            case 'b':
                System.out.println("B");
                break;
            case 'c':
                System.out.println("C");
                break;
            case 'd':
                System.out.println("D");
                break;
            case 'e':
                System.out.println("E");
                break;
            default :
                System.out.println("other");
        }
    }
}
```

**练习3：**

```
编写程序：从键盘上读入一个学生成绩，存放在变量score中，根据score的值输出其对应的成绩等级：

score>=90           等级:  A
70<=score<90        等级:  B    
60<=score<70        等级:  C
score<60            等级:  D

方式一：使用if-else
方式二：使用switch-case:  score / 10:   0 - 10

```

```java
public class SwitchCaseExer3 {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("请输入学生成绩：");
        int score = scan.nextInt();

        char grade;//记录学生等级
        //方式1：
//        if(score >= 90){
//            grade = 'A';
//        }else if(score >= 70 && score < 90){
//            grade = 'B';
//        }else if(score >= 60 && score < 70){
//            grade = 'C';
//        }else{
//            grade = 'D';
//        }

        //方式2：
        switch(score / 10){
            case 10:
            case 9:
                grade = 'A';
                break;
            case 8:
            case 7:
                grade = 'B';
                break;
            case 6:
                grade = 'C';
                break;
            default :
                grade = 'D';
        }

        System.out.println("学生成绩为" + score + ",对应的等级为" + grade);

        scan.close();
    }
}
```

**练习4：**

```
编写一个程序，为一个给定的年份找出其对应的中国生肖。中国的生肖基于12年一个周期，每年用一个动物代表：rat、ox、tiger、rabbit、dragon、snake、horse、sheep、monkey、rooster、dog、pig。

提示：2022年：虎   2022 % 12 == 6 

```



```java
/**
 * @author 尚硅谷-宋红康
 * @create 18:55
 */
public class SwitchCaseExer4 {
    public static void main(String[] args){
        //从键盘输入一个年份
        Scanner input = new Scanner(System.in);
        System.out.print("请输入年份：");
        int year = input.nextInt();
        input.close();

        //判断
        switch(year % 12){
            case 0:
                System.out.println(year + "是猴年");
                break;
            case 1:
                System.out.println(year + "是鸡年");
                break;
            case 2:
                System.out.println(year + "是狗年");
                break;
            case 3:
                System.out.println(year + "是猪年");
                break;
            case 4:
                System.out.println(year + "是鼠年");
                break;
            case 5:
                System.out.println(year + "是牛年");
                break;
            case 6:
                System.out.println(year + "是虎年");
                break;
            case 7:
                System.out.println(year + "是兔年");
                break;
            case 8:
                System.out.println(year + "是龙年");
                break;
            case 9:
                System.out.println(year + "是蛇年");
                break;
            case 10:
                System.out.println(year + "是马年");
                break;
            case 11:
                System.out.println(year + "是羊年");
                break;
            default:
                System.out.println(year + "输入错误");
        }
    }
}
```

**练习5：押宝游戏**

```
随机产生3个1-6的整数，如果三个数相等，那么称为“豹子”，如果三个数之和大于9，称为“大”，如果三个数之和小于等于9，称为“小”，用户从键盘输入押的是“豹子”、“大”、“小”，并判断是否猜对了

提示：随机数  Math.random()产生 [0,1)范围内的小数
     如何获取[a,b]范围内的随机整数呢？(int)(Math.random() * (b - a + 1)) + a
```



```java
import java.util.Scanner;

public class SwitchCaseExer5 {
    public static void main(String[] args) {
        //1、随机产生3个1-6的整数
        int a = (int)(Math.random()*6 + 1);
        int b = (int)(Math.random()*6 + 1);
        int c = (int)(Math.random()*6 + 1);

        //2、押宝
        Scanner input = new Scanner(System.in);
        System.out.print("请押宝（豹子、大、小）：");
        String ya = input.next();
        input.close();

        //3、判断结果
        boolean result = false;
        //switch支持String类型
        switch (ya){
            case "豹子": result = a == b && b == c; break;
            case "大": result = a + b + c > 9; break;
            case "小": result = a + b + c <= 9; break;
            default:System.out.println("输入有误！");
        }

        System.out.println("a,b,c分别是：" + a +"," + b +"," + c );
        System.out.println(result ? "猜中了" : "猜错了");
    }
}
```

**练习6：**

```java
使用switch语句改写下列if语句：

int a = 3;
int x = 100;
if(a==1)
	x+=5;
else if(a==2)
	x+=10;
else if(a==3)
	x+=16;
else
	x+=34;
```

```java
int a = 3;
int x = 100;

switch(a){
    case 1:
        x += 5;
        break;
    case 2:
        x += 10;
        break;
    case 3:
        x += 16;
        break;
    default :
        x += 34;

}
```

## 3. 循环语句

- 理解：循环语句具有在`某些条件`满足的情况下，`反复执行`特定代码的功能。

- 循环结构分类：
  - for 循环
  - while 循环
  - do-while 循环 

- 循环结构`四要素`：

  - 初始化部分
  - 循环条件部分
  - 循环体部分
  - 迭代部分


### 3.1  for循环

#### 3.1.1 基本语法

**语法格式：**


```java
for (①初始化部分; ②循环条件部分; ④迭代部分)｛
         	③循环体部分;
｝
```

**执行过程：**①-②-③-④-②-③-④-②-③-④-.....-②

**说明：**

- for(;;)中的两个；不能多也不能少
- ①初始化部分可以声明多个变量，但必须是同一个类型，用逗号分隔
- ②循环条件部分为boolean类型表达式，当值为false时，退出循环
- ④可以有多个变量更新，用逗号分隔

#### 3.1.2 应用举例

**案例1：使用for循环重复执行某些语句**

题目：输出5行HelloWorld

```java
public class ForTest1 {
    public static void main(String[] args) {
        //需求1：控制台输出5行Hello World!
		//写法1：
		//System.out.println("Hello World!");
		//System.out.println("Hello World!");
		//System.out.println("Hello World!");
		//System.out.println("Hello World!");
		//System.out.println("Hello World!");

		//写法2：
		for(int i = 1;i <= 5;i++){
			System.out.println("Hello World!");
		}
    }
}
```

**案例2：格式的多样性**

题目：写出输出的结果

```java
public class ForTest2 {
	public static void main(String[] args) {
        int num = 1;
        for(System.out.print("a");num < 3;System.out.print("c"),num++){
            System.out.print("b");

        }
    }
}
输出结果：abcbc
```

**案例3：累加的思想**

题目：遍历1-100以内的偶数，并获取偶数的个数，获取所有的偶数的和

```java
public class ForTest3 {
	public static void main(String[] args) {
        int count = 0;//记录偶数的个数
        int sum = 0;//记录偶数的和

        for(int i = 1;i <= 100;i++){

            if(i % 2 == 0){
                System.out.println(i);
                count++;
                sum += i;
            }	

            //System.out.println("偶数的个数为：" + count);
        }

        System.out.println("偶数的个数为：" + count);	
        System.out.println("偶数的总和为：" + sum);
    }
}
```

**案例4：结合分支结构使用**

题目：输出所有的水仙花数，所谓水仙花数是指一个3位数，其各个位上数字立方和等于其本身。例如： `153 = 1*1*1 + 3*3*3 + 5*5*5`

```java
public class ForTest4 {
	public static void main(String[] args) {
		//定义统计变量，初始化值是0
		int count = 0;
		
		//获取三位数，用for循环实现
		for(int x = 100; x < 1000; x++) {
			//获取三位数的个位，十位，百位
			int ge = x % 10;
			int shi = x / 10 % 10;
			int bai = x / 100;
			
			//判断这个三位数是否是水仙花数，如果是，统计变量++
			if((ge*ge*ge+shi*shi*shi+bai*bai*bai) == x) {
                System.out.println("水仙花数：" + x);
				count++;
			}
		}
		
		//输出统计结果就可以了
		System.out.println("水仙花数共有"+count+"个");
	}
}
```

拓展：

```ini
打印出四位数字中“个位+百位”等于“十位+千位”并且个位数为偶数，千位数为奇数的数字，并打印符合条件的数字的个数。
```

**案例5：结合break的使用**

说明：输入两个正整数m和n，求其最大公约数和最小公倍数。

比如：12和20的最大公约数是4，最小公倍数是60。

```java
/**
 * @author 尚硅谷-宋红康
 * @create 17:43
 */
public class ForTest5 {
    public static void main(String[] args) {
        //需求1：最大公约数
        int m = 12, n = 20;
        //取出两个数中的较小值
        int min = (m < n) ? m : n;

        for (int i = min; i >= 1; i--) {//for(int i = 1;i <= min;i++){

            if (m % i == 0 && n % i == 0) {
                System.out.println("最大公约数是：" + i); //公约数

                break; //跳出当前循环结构
            }
        }


        //需求2：最小公倍数
        //取出两个数中的较大值
        int max = (m > n) ? m : n;

        for (int i = max; i <= m * n; i++) {

            if (i % m == 0 && i % n == 0) {

                System.out.println("最小公倍数是：" + i);//公倍数

                break;
            }
        }

    }
}
```

> 说明：
>
> 1、我们可以在循环中使用break。一旦执行break，就跳出当前循环结构。
>
> 2、小结：如何结束一个循环结构？
>
> ​      结束情况1：循环结构中的循环条件部分返回false
>
> ​      结束情况2：循环结构中执行了break。
>
> 3、如果一个循环结构不能结束，那就是一个死循环！我们开发中要避免出现死循环。

#### 3.1.3 练习

**练习1：**打印1~100之间所有奇数的和

```java
public class ForExer1 {

    public static void main(String[] args) {

        int sum = 0;//记录奇数的和
        for (int i = 1; i < 100; i++) {
            if(i % 2 != 0){
                sum += i;
            }
        }
        System.out.println("奇数总和为：" + sum);
    }
}
```

**练习2：**打印1~100之间所有是7的倍数的整数的个数及总和（体会设置计数器的思想）

```java
public class ForExer2 {

    public static void main(String[] args) {

        int sum = 0;//记录总和
        int count = 0;//记录个数
        for (int i = 1; i < 100; i++) {
            if(i % 7 == 0){
                sum += i;
                count++;
            }
        }
        System.out.println("1~100之间所有是7的倍数的整数的和为：" + sum);
        System.out.println("1~100之间所有是7的倍数的整数的个数为：" + count);
    }
}
```

**练习3：**

编写程序从1循环到150，并在每行打印一个值，另外在每个3的倍数行上打印出“foo”,在每个5的倍数行上打印“biz”,在每个7的倍数行上打印输出“baz”。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220315013722962.png" alt="image-20220315013722962" style="zoom:67%;" />

```java
public class ForExer3 {

    public static void main(String[] args) {

        for (int i = 1; i < 150; i++) {
            System.out.print(i + "\t");
            if(i % 3 == 0){
                System.out.print("foo\t");
            }
            if(i % 5 == 0){
                System.out.print("biz\t");
            }
            if(i % 7 == 0){
                System.out.print("baz\t");
            }

            System.out.println();
        }
    }
}
```

### 3.2 while循环

#### 3.2.1 基本语法

**语法格式：**

```java
①初始化部分
while(②循环条件部分)｛
    ③循环体部分;
    ④迭代部分;
}
```

**执行过程：**①-②-③-④-②-③-④-②-③-④-...-②



**说明：**

- while(循环条件)中循环条件必须是boolean类型。
- 注意不要忘记声明④迭代部分。否则，循环将不能结束，变成死循环。
- for循环和while循环可以相互转换。二者没有性能上的差别。实际开发中，根据具体结构的情况，选择哪个格式更合适、美观。
- for循环与while循环的区别：初始化条件部分的作用域不同。

#### 3.2.2 应用举例

**案例1：**输出5行HelloWorld!

```java
class WhileTest1 {
	public static void main(String[] args) {
		
		int i = 1;
		while(i <= 5){
			System.out.println("Hello World!");
			i++;
		}
	}
}
```

**案例2：**遍历1-100的偶数，并计算所有偶数的和、偶数的个数（累加的思想）

```java
class WhileTest2 {
	public static void main(String[] args) {
		//遍历1-100的偶数，并计算所有偶数的和、偶数的个数（累加的思想）
		int num = 1;

		int sum = 0;//记录1-100所有的偶数的和
		int count = 0;//记录1-100之间偶数的个数

		while(num <= 100){
			
			if(num % 2 == 0){
				System.out.println(num);
				sum += num;
				count++;
			}
			
			//迭代条件
			num++;
		}
	
		System.out.println("偶数的总和为：" + sum);
		System.out.println("偶数的个数为：" + count);
	}
}
```

**案例3：**猜数字游戏

```
随机生成一个100以内的数，猜这个随机数是多少？

从键盘输入数，如果大了，提示大了；如果小了，提示小了；如果对了，就不再猜了，并统计一共猜了多少次。

提示：生成一个[a,b] 范围的随机数的方式：(int)(Math.random() * (b - a + 1) + a)
```

```java
/**
 * @author 尚硅谷-宋红康
 * @create 16:42
 */
public class GuessNumber {
    public static void main(String[] args) {
        //获取一个随机数
        int random = (int) (Math.random() * 100) + 1;

        //记录猜的次数
        int count = 1;

        //实例化Scanner
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入一个整数(1-100):");
        int guess = scan.nextInt();

        while (guess != random) {

            if (guess > random) {
                System.out.println("猜大了");
            } else if (guess < random) {
                System.out.println("猜小了");
            }

            System.out.println("请输入一个整数(1-100):");
            guess = scan.nextInt();
			//累加猜的次数
            count++;

        }

        System.out.println("猜中了！");
        System.out.println("一共猜了" + count + "次");
    }
}
```

**案例4：折纸珠穆朗玛峰**

```
世界最高山峰是珠穆朗玛峰，它的高度是8848.86米，假如我有一张足够大的纸，它的厚度是0.1毫米。
请问，我折叠多少次，可以折成珠穆朗玛峰的高度?
```

```java
/**
 * @author 尚硅谷-宋红康
 * @create 19:08
 */
public class ZFTest {
    public static void main(String[] args) {
        //定义一个计数器，初始值为0
        int count = 0;

        //定义珠穆朗玛峰的高度
        int zf = 8848860;//单位：毫米

        double paper = 0.1;//单位：毫米

        while(paper < zf){
            //在循环中执行累加，对应折叠了多少次
            count++;
            paper *= 2;//循环的执行过程中每次纸张折叠，纸张的厚度要加倍
        }

        //打印计数器的值
        System.out.println("需要折叠：" + count + "次");
        System.out.println("折纸的高度为" + paper/1000 + "米，超过了珠峰的高度");
    }
}
```

#### 3.2.3 练习

**练习：**从键盘输入整数，输入0结束，统计输入的正数、负数的个数。

```java
import java.util.Scanner;

public class Test05While {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int positive = 0; //记录正数的个数
        int negative = 0;  //记录负数的个数
        int num = 1; //初始化为特殊值，使得第一次循环条件成立
        while(num != 0){
            System.out.print("请输入整数（0表示结束）：");
            num = input.nextInt();

            if(num > 0){
                positive++;
            }else if(num < 0){
                negative++;
            }
        }
        System.out.println("正数个数：" + positive);
        System.out.println("负数个数：" + negative);

        input.close();
    }
}

```

### 3.3 do-while循环

#### 3.3.1 基本语法

**语法格式：**

```java
①初始化部分;
do{
	③循环体部分
	④迭代部分
}while(②循环条件部分); 
```

**执行过程：**①-③-④-②-③-④-②-③-④-...-②

**图示：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220512165558698.png" alt="image-20220512165558698" style="zoom:80%;" />

**说明：**

- 结尾while(循环条件)中循环条件必须是boolean类型
- do{}while();最后有一个分号
- **<font color='red'>do-while结构的循环体语句是至少会执行一次，这个和for和while是不一样的<font>**
- 循环的三个结构for、while、do-while三者是可以相互转换的。

#### 3.3.2 应用举例

**案例1：**遍历1-100的偶数，并计算所有偶数的和、偶数的个数（累加的思想）

```java
class DoWhileTest1 {
	public static void main(String[] args) {

		//遍历1-100的偶数，并计算所有偶数的和、偶数的个数（累加的思想）
		//初始化部分
		int num = 1;
		
		int sum = 0;//记录1-100所有的偶数的和
		int count = 0;//记录1-100之间偶数的个数

		do{
			//循环体部分
			if(num % 2 == 0){
				System.out.println(num);
				sum += num;
				count++;
			}
			
			num++;//迭代部分


		}while(num <= 100); //循环条件部分


		System.out.println("偶数的总和为：" + sum);
		System.out.println("偶数的个数为：" + count);
	}
}

```

**案例2：**体会do-while至少会执行一次循环体

```java
class DoWhileTest2 {
	public static void main(String[] args) {
        //while循环:
		int num1 = 10;
		while(num1 > 10){
			System.out.println("hello:while");
			num1--;
		}

		//do-while循环:
		int num2 = 10;
		do{
			System.out.println("hello:do-while");
			num2--;
		}while(num2 > 10);

	}
}
```

**案例3：ATM取款**

```
声明变量balance并初始化为0，用以表示银行账户的余额，下面通过ATM机程序实现存款，取款等功能。

=========ATM========
   1、存款
   2、取款
   3、显示余额
   4、退出
请选择(1-4)：
```

```java
import java.util.Scanner;

/**
 * @author 尚硅谷-宋红康
 * @create 19:12
 */
public class ATM {
	public static void main(String[] args) {

		//初始化条件
		double balance = 0.0;//表示银行账户的余额
		Scanner scan = new Scanner(System.in);
		boolean isFlag = true;//用于控制循环的结束

		do{
			System.out.println("=========ATM========");
			System.out.println("\t1、存款");
			System.out.println("\t2、取款");
			System.out.println("\t3、显示余额");
			System.out.println("\t4、退出");
			System.out.print("请选择(1-4)：");

			int selection = scan.nextInt();
			
			switch(selection){
				case 1:
					System.out.print("要存款的额度为：");
					double addMoney = scan.nextDouble();
					if(addMoney > 0){
						balance += addMoney;
					}
					break;
				case 2:
					System.out.print("要取款的额度为：");
					double minusMoney = scan.nextDouble();
					if(minusMoney > 0 && balance >= minusMoney){
						balance -= minusMoney;
					}else{
						System.out.println("您输入的数据非法或余额不足");
					}
					break;
				case 3:
					System.out.println("当前的余额为：" + balance);
					break;
				case 4:
					System.out.println("欢迎下次进入此系统。^_^");
					isFlag = false;
					break;
				default:
					System.out.println("请重新选择！");
					break;	
			}
		
		}while(isFlag);

		//资源关闭
		scan.close();
		
	}
}
```

#### 3.3.3 练习

**练习1：**随机生成一个100以内的数，猜这个随机数是多少？

从键盘输入数，如果大了提示，大了；如果小了，提示小了；如果对了，就不再猜了，并统计一共猜了多少次。

```java
import java.util.Scanner;

public class DoWhileExer {
    public static void main(String[] args) {
        //随机生成一个100以内的整数
		/*
		Math.random() ==> [0,1)的小数
		Math.random()* 100 ==> [0,100)的小数
		(int)(Math.random()* 100) ==> [0,100)的整数
		*/
        int num = (int)(Math.random()* 100);
        //System.out.println(num);

        //声明一个变量，用来存储猜的次数
        int count = 0;

        Scanner input = new Scanner(System.in);
        int guess;//提升作用域
        do{
            System.out.print("请输入100以内的整数：");
            guess = input.nextInt();

            //输入一次，就表示猜了一次
            count++;

            if(guess > num){
                System.out.println("大了");
            }else if(guess < num){
                System.out.println("小了");
            }
        }while(num != guess);

        System.out.println("一共猜了：" + count+"次");

        input.close();
    }
}
```

### 3.4 对比三种循环结构

- **三种循环结构都具有四个要素：**
  - 循环变量的初始化条件
  - 循环条件
  - 循环体语句块
  - 循环变量的修改的迭代表达式

* **从循环次数角度分析**
  * do-while循环至少执行一次循环体语句。
  * for和while循环先判断循环条件语句是否成立，然后决定是否执行循环体。
* **如何选择**
  * 遍历有明显的循环次数（范围）的需求，选择for循环
  * 遍历没有明显的循环次数（范围）的需求，选择while循环
  * 如果循环体语句块至少执行一次，可以考虑使用do-while循环
  * 本质上：三种循环之间完全可以互相转换，都能实现循环的功能

### 3.5 "无限"循环

![类似：死循环](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%B1%BB%E4%BC%BC%EF%BC%9A%E6%AD%BB%E5%BE%AA%E7%8E%AF.jpg)

#### 3.5.1 基本语法

**语法格式：**

- 最简单"无限"循环格式：`while(true)` , `for(;;)` 

**适用场景：**

- 开发中，有时并不确定需要循环多少次，需要根据循环体内部某些条件，来控制循环的结束（使用break）。
- 如果此循环结构不能终止，则构成了死循环！开发中要避免出现死循环。

#### 3.5.2 应用举例

**案例1：**实现爱你到永远...

```java
public class EndlessFor1 {
    public static void main(String[] args) {
        for (;;){
            System.out.println("我爱你！");
        }
//        System.out.println("end");//永远无法到达的语句，编译报错
    }
}
```

```java
public class EndlessFor2 {
    public static void main(String[] args) {
        for (; true;){ //条件永远成立，死循环
            System.out.println("我爱你！");
        }
    }
}
```

```java
public class EndlessFor3 {
    public static void main(String[] args) {
        for (int i=1; i<=10; ){ //循环变量没有修改，条件永远成立，死循环
            System.out.println("我爱你！");
        }
    }
}
```

思考：如下代码执行效果

```java
public class EndlessFor4 {
    public static void main(String[] args) {
        for (int i=1; i>=10; ){ //一次都不执行
            System.out.println("我爱你！");
        }
    }
}
```

**案例2：**从键盘读入个数不确定的整数，并判断读入的正数和负数的个数，输入为0时结束程序。

```java
import java.util.Scanner;

class PositiveNegative {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        
		int positiveNumber = 0;//统计正数的个数
		int negativeNumber = 0;//统计负数的个数
		for(;;){  //while(true){
			System.out.println("请输入一个整数：(输入为0时结束程序)");
			int num = scanner.nextInt();
			if(num > 0){
				 positiveNumber++;
            }else if(num < 0){
				 negativeNumber++;
        	}else{
                System.out.println("程序结束");
				break; 
            }
         }
		 System.out.println("正数的个数为："+ positiveNumber);
		 System.out.println("负数的个数为："+ negativeNumber);  
        
         scanner.close();
	} 
}

```

### 3.6 嵌套循环（或多重循环）

#### 3.6.1 使用说明

- **所谓嵌套循环**，是指一个循环结构A的循环体是另一个循环结构B。比如，for循环里面还有一个for循环，就是嵌套循环。其中，for ,while ,do-while均可以作为外层循环或内层循环。
  - 外层循环：循环结构A
  - 内层循环：循环结构B
- 实质上，`嵌套循环就是把内层循环当成外层循环的循环体`。只有当内层循环的循环条件为false时，才会完全跳出内层循环，才可结束外层的当次循环，开始下一次的外层循环。
- 设外层循环次数为`m`次，内层为`n`次，则内层循环体实际上需要执行`m*n`次。
- **技巧：**从二维图形的角度看，外层循环控制`行数`，内层循环控制`列数`。
- **开发经验：**实际开发中，我们最多见到的嵌套循环是两层。一般不会出现超过三层的嵌套循环。如果将要出现，一定要停下来重新梳理业务逻辑，重新思考算法的实现，控制在三层以内。否则，可读性会很差。

例如：两个for嵌套循环格式	

```java
for(初始化语句①; 循环条件语句②; 迭代语句⑦) {
    for(初始化语句③; 循环条件语句④; 迭代语句⑥) {
      	循环体语句⑤;
    }
}

//执行过程：① - ② - ③ - ④ - ⑤ - ⑥ - ④ - ⑤ - ⑥ - ... - ④ - ⑦ - ② - ③ - ④ - ⑤ - ⑥ - ④..
```

**执行特点：**外层循环执行一次，内层循环执行一轮。

#### 3.6.2 应用举例

**案例1：**打印5行6个*

```java
class ForForTest1 {
	public static void main(String[] args) {
		/*
		
		******
		******
		******
		******
		******
		
		*/
		
		for(int j = 1;j <= 5;j++){

			for(int i = 1;i <= 6;i++){
				System.out.print("*");
			}
			
			System.out.println();
		}
    }
}
```

**案例2：**打印5行直角三角形

```
*
**
***
****
*****
```

```java
public class ForForTest2 {
    public static void main(String[] args){
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}	
```

**案例3：**打印5行倒直角三角形

```
*****
****
***
**
*
```

```java
public class ForForTest3 {
    public static void main(String[] args){
        for(int i = 1;i <= 5;i++){
			for(int j = 1;j <= 6 - i;j++){
				System.out.print("*");
			
			}
			System.out.println();
		
		}
    }
}
```

**案例4：打印"菱形"形状的图案**

```
        * 
      * * * 
    * * * * * 
  * * * * * * * 
* * * * * * * * * 
  * * * * * * * 
    * * * * * 
      * * * 
        * 	
```

```java
public class ForForTest4 {

    public static void main(String[] args) {
    /*
        上半部分		i		m(表示-的个数)    n(表示*的个数)关系式：2*i + m = 10 --> m = 10 - 2*i
    --------*		   1	   8			   1							n = 2 * i - 1
    ------* * *		   2	   6			   3
    ----* * * * *	   3	   4			   5
    --* * * * * * *	   4	   2		       7
    * * * * * * * * *  5	   0			   9

        下半部分         i      m                n              关系式： m = 2 * i
    --* * * * * * *    1       2                7                     n = 9 - 2 * i
    ----* * * * *      2       4                5
    ------* * *        3       6                3
    --------*          4       8                1

            */
        //上半部分
        for (int i = 1; i <= 5; i++) {
            //-
            for (int j = 1; j <= 10 - 2 * i; j++) {
                System.out.print(" ");
            }
            //*
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        //下半部分
        for (int i = 1; i <= 4; i++) {
            //-
            for (int j = 1; j <= 2 * i; j++) {
                System.out.print(" ");
            }

            //*
            for (int k = 1; k <= 9 - 2 * i; k++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }

}
```

**案例5：九九乘法表**

![image-20221113193013204](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221113193013204.png)

```java
public class ForForTest5 {
    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "*" + j + "=" + (i * j) + "\t");
            }
            System.out.println();
        }
    }
}
```

#### 3.6.3 练习

**练习1：**将一天中的时间打印到控制台

```java
public class ForForDemo {
	public static void main (String[] args) {
		for (int hour = 0;hour < 24 ;hour++ ) {
			for (int min = 0; min < 60 ; min++) {
				System.out.println(hour + "时" + min +"分");
			}
		}	
	}
}
```

## 4. 关键字break和continue的使用

### 4.1 break和continue的说明

|          | 适用范围            | 在循环结构中使用的作用               | 相同点                       |
| -------- | ------------------- | ------------------------------------ | ---------------------------- |
| break    | switch-case循环结构 | 一旦执行，就结束(或跳出)当前循环结构 | 此关键字的后面，不能声明语句 |
| continue | 循环结构            | 一旦执行，就结束(或跳出)当次循环结构 | 此关键字的后面，不能声明语句 |

此外，很多语言都有goto语句，goto语句可以随意将控制转移到程序中的任意一条语句上，然后执行它，但使程序容易出错。Java中的break和continue是不同于goto的。

### 4.2 应用举例

```java
class BreakContinueTest1 {
	public static void main(String[] args) {
	
		for(int i = 1;i <= 10;i++){
			
			if(i % 4 == 0){
				//break;//123
				continue;//123567910
				//如下的语句不可能被执行，编译不通过
				//System.out.println("今晚迪丽热巴要约我吃饭");
			}

			System.out.print(i);
		}

		System.out.println("####");

		//嵌套循环中的使用
		for(int i = 1;i <= 4;i++){
		
			for(int j = 1;j <= 10;j++){
				if(j % 4 == 0){
					//break; //结束的是包裹break关键字的最近的一层循环！
					continue;//结束的是包裹break关键字的最近的一层循环的当次！
				}
				System.out.print(j);
			}
			System.out.println();
		}

	}
}
```

### 4.3 带标签的使用

```java
break语句用于终止某个语句块的执行
{    ……	 
	break;
	 ……
}

break语句出现在多层嵌套的语句块中时，可以通过标签指明要终止的是哪一层语句块 
	label1: {   ……        
	label2:	     {   ……
	label3:			 {   ……
				           break label2;
				           ……
					 }
			     }
			} 

```

- continue语句出现在多层嵌套的循环语句体中时，也可以通过标签指明要跳过的是哪一层循环。
- 标号语句必须紧接在循环的头部。标号语句不能用在非循环语句的前面。

- 举例：


```java
class BreakContinueTest2 {
	public static void main(String[] args) {
		l:for(int i = 1;i <= 4;i++){
		
			for(int j = 1;j <= 10;j++){
				if(j % 4 == 0){
					//break l;
					continue l;
				}
				System.out.print(j);
			}
			System.out.println();
		}
	}
}
```

### 4.4 经典案例(打印质数)

**题目：找出100以内所有的素数（质数）？100000以内的呢？**

目的：不同的代码的实现方式，可以效率差别很大。

分析：素数（质数）：只能被1和它本身整除的自然数。  ---> 从2开始，到这个数-1为止，此范围内没有这个数的约数。则此数是一个质数。
比如：2、3、5、7、11、13、17、19、23、...

**实现方式：**

```java
package com.xha;


public class zhiShu {
    public static void main(String[] args) {
        getZhiShu(100000);
    }

    /**
     * 打印出一定范围内的质数
     * 质数：只能够被1和本身能够除尽的数
     */
    public static void getZhiShu(int range) {
        int count = 0;
        long start = System.currentTimeMillis();
        for (int i = 2; i < range; i++) {
            boolean flag = true;
            for (int j = 2; j < i / 2; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                count++;
                System.out.println("质数为：" + i);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("质数的总个数为：" + count);
        System.out.println("花费的总时间为：" + (end - start));
    }
}
```



## 5. Scanner：键盘输入功能的实现

- 如何从键盘获取不同类型（基本数据类型、String类型）的变量：使用Scanner类。

- 键盘输入代码的四个步骤：
  1. 导包：`import java.util.Scanner;`
  2. 创建Scanner类型的对象：`Scanner scan = new Scanner(System.in);`
  3. 调用Scanner类的相关方法（`next() / nextXxx()`），来获取指定类型的变量
  4. 释放资源：`scan.close();`
- 注意：需要根据相应的方法，来输入指定类型的值。如果输入的数据类型与要求的类型不匹配时，会报异常 导致程序终止。

### 5.1 各种类型的数据输入

**案例：**小明注册某交友网站，要求录入个人相关信息。如下：

请输入你的网名、你的年龄、你的体重、你是否单身、你的性别等情况。

```java
//① 导包
import java.util.Scanner;

public class ScannerTest1 {

    public static void main(String[] args) {
        //② 创建Scanner的对象
        //Scanner是一个引用数据类型，它的全名称是java.util.Scanner
        //scanner就是一个引用数据类型的变量了，赋给它的值是一个对象（对象的概念我们后面学习，暂时先这么叫）
        //new Scanner(System.in)是一个new表达式，该表达式的结果是一个对象
        //引用数据类型  变量 = 对象;
        //这个等式的意思可以理解为用一个引用数据类型的变量代表一个对象，所以这个变量的名称又称为对象名
        //我们也把scanner变量叫做scanner对象
        Scanner scanner = new Scanner(System.in);//System.in默认代表键盘输入
        
        //③根据提示，调用Scanner的方法，获取不同类型的变量
        System.out.println("欢迎光临你好我好交友网站！");
        System.out.print("请输入你的网名：");
        String name = scanner.next();

        System.out.print("请输入你的年龄：");
        int age = scanner.nextInt();

        System.out.print("请输入你的体重：");
        double weight = scanner.nextDouble();

        System.out.print("你是否单身（true/false)：");
        boolean isSingle = scanner.nextBoolean();

        System.out.print("请输入你的性别：");
        char gender = scanner.next().charAt(0);//先按照字符串接收，然后再取字符串的第一个字符（下标为0）

        System.out.println("你的基本情况如下：");
        System.out.println("网名：" + name + "\n年龄：" + age + "\n体重：" + weight + 
                           "\n单身：" + isSingle + "\n性别：" + gender);
        
        //④ 关闭资源
        scanner.close();
    }
}
```

### 5.2 练习

**练习1：**

```ini
大家都知道，男大当婚，女大当嫁。那么女方家长要嫁女儿，当然要提出一定的条件：高：180cm以上；富：财富1千万以上；帅：是。

如果这三个条件同时满足，则：“我一定要嫁给他!!!”
如果三个条件有为真的情况，则：“嫁吧，比上不足，比下有余。”
如果三个条件都不满足，则：“不嫁！”

提示：
System.out.println(“身高: (cm));
scanner.nextInt();

System.out.println(“财富: (千万));
scanner.nextDouble();

System.out.println(“帅否: (true/false));   
scanner.nextBoolean();  



System.out.println(“帅否: (是/否));
scanner.next();   "是".equals(str)  
```

```java
import java.util.Scanner;

class ScannerExer1 {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		System.out.println("请输入你的身高：(cm)");
		int height = scan.nextInt();

		System.out.println("请输入你的财富：(以千万为单位)");
		double wealth = scan.nextDouble();

		/*
		
		方式1：关于是否帅问题，我们使用boolean类型接收

		System.out.println("帅否？(true/false)");
		boolean isHandsome = scan.nextBoolean();

		//判断
		if(height >= 180 && wealth >= 1.0 && isHandsome){ //不建议isHandsome == true
			System.out.println("我一定要嫁给他!!!");
		}else if(height >= 180 || wealth >= 1.0 || isHandsome){
			System.out.println("嫁吧，比上不足，比下有余。");
		}else{
			System.out.println("不嫁");
		}

		*/

		//方式2：关于是否帅问题，我们使用String类型接收
		System.out.println("帅否？(是/否)");
		String isHandsome = scan.next();
		
		//判断
		if(height >= 180 && wealth >= 1.0 && isHandsome == "是"){  //知识点：判断两个字符串是否相等，使用String的equals()
			System.out.println("我一定要嫁给他!!!");
		}else if(height >= 180 || wealth >= 1.0 || isHandsome == "是"){
			System.out.println("嫁吧，比上不足，比下有余。");
		}else{
			System.out.println("不嫁");
		}

		//关闭资源
		scan.close();
	}
}
```

**练习2：**

```
我家的狗5岁了，5岁的狗相当于人类多大呢？其实，狗的前两年每一年相当于人类的10.5岁，之后每增加一年就增加四岁。那么5岁的狗相当于人类多少年龄呢？应该是：10.5 + 10.5 + 4 + 4 + 4 = 33岁。

编写一个程序，获取用户输入的狗的年龄，通过程序显示其相当于人类的年龄。如果用户输入负数，请显示一个提示信息。

```

```java
import java.util.Scanner;

class ScannerExer2 {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		System.out.println("请输入狗狗的年龄：");
		int dogAge = scan.nextInt();

		//通过分支语句，判断狗狗相当于人的年龄
		if(dogAge < 0){
			System.out.println("你输入的狗狗的年龄不合法");
		}else if(dogAge <= 2){
			System.out.println("相当于人的年龄：" + (dogAge * 10.5));
		}else{
			System.out.println("相当于人的年龄：" + (2 * 10.5 + (dogAge - 2) * 4));
		}

		//关闭资源
		scan.close();

	}
}
```

## 6. 如何获取一个随机数

如何产生一个指定范围的随机整数？

1、Math类的random()的调用，会返回一个[0,1)范围的一个double型值

2、Math.random() * 100  --->  [0,100)
      (int)(Math.random() * 100)	---> [0,99]
      (int)(Math.random() * 100) + 5  ----> [5,104]

3、如何获取`[a,b]`范围内的随机整数呢？`(int)(Math.random() * (b - a + 1)) + a`

4、举例

```java
class MathRandomTest {
	public static void main(String[] args) {
		double value = Math.random();
		System.out.println(value);

		//[1,6]
		int number = (int)(Math.random() * 6) + 1; //
		System.out.println(number);
	}
}

```

## 7.第三章企业真题

### 1. break和continue的作用(智*图)

略

### 2. if分支语句和switch分支语句的异同之处(智*图)

- if-else语句优势
  - if语句的条件是一个布尔类型值，if条件表达式为true则进入分支，可以用于范围的判断，也可以用于等值的判断，`使用范围更广`。
  - switch语句的条件是一个常量值（byte,short,int,char,枚举,String），只能判断某个变量或表达式的结果是否等于某个常量值，`使用场景较狭窄`。
- switch语句优势
  - 当条件是判断某个变量或表达式是否等于某个固定的常量值时，使用if和switch都可以，习惯上使用switch更多。因为`效率稍高`。当条件是区间范围的判断时，只能使用if语句。
  - 使用switch可以利用`穿透性`，同时执行多个分支，而if...else没有穿透性。



### 3. 什么时候用语句if，什么时候选用语句switch(灵伴*来科技)

同上

### 4. switch语句中忘写break会发生什么(北京*蓝)

case穿透



### 5. Java支持哪些类型循环(上海*睿)

- for;while;do-while
- 增强for （或foreach），放到集合中讲解



### 6. while和do while循环的区别(国*科技研究院)

- do-while至少会执行一次。







# 第04章_数组

## 1. 数组的概述

### 1.1 为什么需要数组

**需求分析1：**

需要统计某公司50个员工的工资情况，例如计算平均工资、找到最高工资等。用之前知识，首先需要声明`50个变量`来分别记录每位员工的工资，这样会很麻烦。因此我们可以将所有的数据全部存储到一个容器中统一管理，并使用容器进行计算。

**需求分析2：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/snipaste_20220317_000101.jpg" alt="snipaste_20220317_000101" style="zoom: 50%;" />

容器的概念：

- **生活中的容器：**水杯（装水等液体），衣柜（装衣服等物品），集装箱（装货物等）。
- **程序中的容器：**将多个数据存储到一起，每个数据称为该容器的元素。

### 1.2 数组的概念

- 数组(Array)：<font color='red'>是多个相同类型数据按一定顺序排列的集合，并使用一个名字命名，并通过编号的方式对这些数据进行统一管理。</font>

- 数组中的概念
  - 数组名
  - 下标（或索引）
  - 元素
  - 数组的长度

![image-20220317000952499](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317000952499.png)

**数组的特点：**

- 数组本身是`引用数据类型`，而数组中的元素可以是`任何数据类型`，包括基本数据类型和引用数据类型。
- 创建数组对象会在内存中开辟一整块`连续的空间`。占据的空间的大小，取决于数组的长度和数组中元素的类型。
- 数组中的元素在内存中是依次紧密排列的，有序的。
- 数组，一旦初始化完成，其长度就是确定的。数组的`长度一旦确定，就不能修改`。
- 我们可以直接通过下标(或索引)的方式调用指定位置的元素，速度很快。
- 数组名中引用的是这块连续空间的首地址。

### 1.3 数组的分类

**1、按照元素类型分：**

- 基本数据类型元素的数组：每个元素位置存储基本数据类型的值
- 引用数据类型元素的数组：每个元素位置存储对象（本质是存储对象的首地址）（在面向对象部分讲解）

**2、按照维度分：**

- 一维数组：存储一组数据
- 二维数组：存储多组数据，相当于二维表，一行代表一组数据，只是这里的二维表每一行长度不要求一样。

![image-20211221164709624](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211221164709624.png)



## 2. 一维数组的使用

### 2.1 一维数组的声明

**格式：**


```java
//推荐
元素的数据类型[] 一维数组的名称;

//不推荐
元素的数据类型  一维数组名[];
```

**举例：**

```java
int[] arr;
int arr1[];
double[] arr2;
String[] arr3;  //引用类型变量数组
```

**数组的声明，需要明确：**

（1）数组的维度：在Java中数组的符号是[]，[]表示一维，\[]\[]表示二维。

（2）数组的元素类型：即创建的数组容器可以存储什么数据类型的数据。元素的类型可以是任意的Java的数据类型。例如：int、String、Student等。

（3）数组名：就是代表某个数组的标识符，数组名其实也是变量名，按照变量的命名规范来命名。数组名是个引用数据类型的变量，因为它代表一组数据。

**举例：**

```java
public class ArrayTest1 {
    public static void main(String[] args) {
        //比如，要存储一个小组的成绩
        int[] scores;
        int grades[];
//        System.out.println(scores);//未初始化不能使用

        //比如，要存储一组字母
        char[] letters;

        //比如，要存储一组姓名
        String[] names;

        //比如，要存储一组价格
        double[] prices;

    }
}
```

注意：Java语言中声明数组时不能指定其长度(数组中元素的个数)。 例如： int a[5]; //非法

### 2.2 一维数组的初始化

#### 2.2.1 静态初始化

- 如果数组变量的初始化和数组元素的赋值操作同时进行，那就称为静态初始化。

- 静态初始化，本质是用静态数据（编译时已知）为数组初始化。此时数组的长度由静态数据的个数决定。

- **一维数组声明和静态初始化格式1：**

  ```java
  数据类型[] 数组名 = new 数据类型[]{元素1,元素2,元素3,...};
  
  或
      
  数据类型[] 数组名;
  数组名 = new 数据类型[]{元素1,元素2,元素3,...};
  ```

  - new：关键字，创建数组使用的关键字。因为数组本身是引用数据类型，所以要用new创建数组实体。

例如，定义存储1，2，3，4，5整数的数组容器。

```java
int[] arr = new int[]{1,2,3,4,5};//正确
//或
int[] arr;
arr = new int[]{1,2,3,4,5};//正确
```

- **一维数组声明和静态初始化格式2：**

```java
数据类型[] 数组名 = {元素1,元素2,元素3...};//必须在一个语句中完成，不能分成两个语句写
```

例如，定义存储1，2，3，4，5整数的数组容器

```java
int[] arr = {1,2,3,4,5};//正确

int[] arr;
arr = {1,2,3,4,5};//错误
```

**举例：**

```java
public class ArrayTest2 {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};//右边不需要写new int[]

        int[] nums;
        nums = new int[]{10,20,30,40}; //声明和初始化在两个语句完成，就不能使用new int[]

        char[] word = {'h','e','l','l','o'};

        String[] heros = {"袁隆平","邓稼先","钱学森"};

        System.out.println("arr数组：" + arr);//arr数组：[I@1b6d3586
        System.out.println("nums数组：" + nums);//nums数组：[I@4554617c
        System.out.println("word数组：" + word);//word数组：[C@74a14482
        System.out.println("heros数组：" + heros);//heros数组：[Ljava.lang.String;@1540e19d
    }
}
```

#### 2.2.2 动态初始化

数组变量的初始化和数组元素的赋值操作分开进行，即为动态初始化。


动态初始化中，只确定了元素的个数（即数组的长度），而元素值此时只是默认值，还并未真正赋自己期望的值。真正期望的数据需要后续单独一个一个赋值。

**格式：**

```java
数组存储的元素的数据类型[] 数组名字 = new 数组存储的元素的数据类型[长度];

或

数组存储的数据类型[] 数组名字;
数组名字 = new 数组存储的数据类型[长度];
```

- [长度]：数组的长度，表示数组容器中可以最多存储多少个元素。

- **注意：数组有定长特性，长度一旦指定，不可更改。**和水杯道理相同，买了一个2升的水杯，总容量就是2升是固定的。

**举例1：正确写法**


```java
int[] arr = new int[5];

int[] arr;
arr = new int[5];

```

**举例2：错误写法**

```java
int[] arr = new int[5]{1,2,3,4,5};//错误的，后面有{}指定元素列表，就不需要在[]中指定元素个数了。
```

### 2.3 一维数组的使用

#### 2.3.1  数组的长度

- 数组的元素总个数，即数组的长度
- 每个数组都有一个属性length指明它的长度，例如：arr.length 指明数组arr的长度(即元素个数)
- 每个数组都具有长度，而且一旦初始化，其长度就是确定，且是不可变的。

#### 2.3.2 数组元素的引用

**如何表示数组中的一个元素？**


每一个存储到数组的元素，都会自动的拥有一个编号，从0开始，这个自动编号称为`数组索引(index)或下标`，可以通过数组的索引/下标访问到数组中的元素。

```java
数组名[索引/下标]
```

**数组的下标范围？**

Java中数组的下标从[0]开始，下标范围是[0, 数组的长度-1]，即[0, 数组名.length-1]

数组元素下标可以是整型常量或整型表达式。如a[3] , b[i] , c[6*i];

**举例**

```java
public class ArrayTest3 {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};

        System.out.println("arr数组的长度：" + arr.length);
        System.out.println("arr数组的第1个元素：" + arr[0]);//下标从0开始
        System.out.println("arr数组的第2个元素：" + arr[1]);
        System.out.println("arr数组的第3个元素：" + arr[2]);
        System.out.println("arr数组的第4个元素：" + arr[3]);
        System.out.println("arr数组的第5个元素：" + arr[4]);

        //修改第1个元素的值
        //此处arr[0]相当于一个int类型的变量
        arr[0] = 100;
        System.out.println("arr数组的第1个元素：" + arr[0]);
    }
}
```

### 2.4 一维数组的遍历

将数组中的每个元素分别获取出来，就是`遍历`。for循环与数组的遍历是绝配。

**举例1**

```java
public class ArrayTest4 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        //打印数组的属性，输出结果是5
        System.out.println("数组的长度：" + arr.length);

        //遍历输出数组中的元素
        System.out.println("数组的元素有：");
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
```

**举例2**


```java
public class ArrayTest5 {
    public static void main(String[] args) {
        int[] arr = new int[5];

        System.out.println("arr数组的长度：" + arr.length);
        System.out.print("存储数据到arr数组之前：[");
        for (int i = 0; i < arr.length; i++) {
            if(i==0){
                System.out.print(arr[i]);
            }else{
                System.out.print("," + arr[i]);
            }
        }
        System.out.println("]");

        //初始化
 		/* 
 		arr[0] = 2;
        arr[1] = 4;
        arr[2] = 6;
        arr[3] = 8;
        arr[4] = 10;
        */

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (i+1) * 2;
        }

        System.out.print("存储数据到arr数组之后：[");
        for (int i = 0; i < arr.length; i++) {
            if(i==0){
                System.out.print(arr[i]);
            }else{
                System.out.print("," + arr[i]);
            }
        }
        System.out.println("]");
    }
}
```

### 2.5 数组元素的默认值

数组是引用类型，当我们使用动态初始化方式创建数组时，元素值只是默认值。例如：

```java
public class ArrayTest6 {
	public static void main(String argv[]){
		int a[]= new int[5]; 
		System.out.println(a[3]); //a[3]的默认值为0
	}
} 
```

对于基本数据类型而言，默认初始化值各有不同。

对于引用数据类型而言，默认初始化值为null（注意与0不同！)

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1561509460135.png)

```java
public class ArrayTest7 {
    public static void main(String[] args) {
        //存储26个字母
        char[] letters = new char[26];
        System.out.println("letters数组的长度：" + letters.length);
        System.out.print("存储字母到letters数组之前：[");
        for (int i = 0; i < letters.length; i++) {
            if(i==0){
                System.out.print(letters[i]);
            }else{
                System.out.print("," + letters[i]);
            }
        }
        System.out.println("]");

       //存储5个姓名
        String[] names = new String[5];
        System.out.println("names数组的长度：" + names.length);
        System.out.print("存储姓名到names数组之前：[");
        for (int i = 0; i < names.length; i++) {
            if(i==0){
                System.out.print(names[i]);
            }else{
                System.out.print("," + names[i]);
            }
        }
        System.out.println("]");
    }
}
```

## 3. 一维数组内存分析

### 3.1 Java虚拟机的内存划分

为了提高运算效率，就对空间进行了不同区域的划分，因为每一片区域都有特定的处理数据方式和内存管理方式。

![JVM架构-简图](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/JVM%E6%9E%B6%E6%9E%84-%E7%AE%80%E5%9B%BE.png)

| 区域名称   | 作用                                                         |
| ---------- | ------------------------------------------------------------ |
| `虚拟机栈` | <font color='red'>以栈帧为单位，有入栈和出栈操作。每个栈帧入栈操作对应一个方法的执行。用于存储正在执行的每个Java方法的局部变量表等</font>。局部变量表存放了编译期可知长度<br/>的各种基本数据类型、对象引用，方法执行完，自动释放。 |
| `堆内存`   | <font color='red'>存储对象（包括数组对象），new来创建的，都存储在堆内存。</font> |
| `方法区`   | 存储已被虚拟机加载的类信息、常量、（静态变量）、即时编译器编译后的代码等数据。 |
| 本地方法栈 | 当程序中调用了native的本地方法时，本地方法执行期间的内存区域 |
| 程序计数器 | 程序计数器是CPU中的寄存器，它包含每一个线程下一条要执行的指令的地址 |

### 3.2 一维数组在内存中的存储 

#### 1、一个一维数组内存图

```java
public static void main(String[] args) {
  	int[] arr = new int[3];
  	System.out.println(arr);//[I@5f150435
}

```

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%95%B0%E7%BB%84%E5%86%85%E5%AD%98%E5%9B%BE1.jpg)

#### 2、数组下标为什么是0开始

因为第一个元素距离数组首地址间隔0个单元格。

#### 3、两个一维数组内存图

两个数组独立

```java
public static void main(String[] args) {
    int[] arr = new int[3];
    int[] arr2 = new int[2];
    System.out.println(arr);
    System.out.println(arr2);
}

```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%95%B0%E7%BB%84%E5%86%85%E5%AD%98%E5%9B%BE2.jpg" style="zoom:67%;" />

#### 4、两个变量指向一个一维数组

两个数组变量本质上代表同一个数组。

```java
public static void main(String[] args) {
    // 定义数组，存储3个元素
    int[] arr = new int[3];
    //数组索引进行赋值
    arr[0] = 5;
    arr[1] = 6;
    arr[2] = 7;
    //输出3个索引上的元素值
    System.out.println(arr[0]);
    System.out.println(arr[1]);
    System.out.println(arr[2]);
    //定义数组变量arr2，将arr的地址赋值给arr2
    int[] arr2 = arr;
    arr2[1] = 9;
    System.out.println(arr[1]);
}
```

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%95%B0%E7%BB%84%E5%86%85%E5%AD%98%E5%9B%BE3.jpg" style="zoom:67%;" />

## 4. 一维数组的应用

**案例1：**升景坊单间短期出租4个月，550元/月（水电煤公摊，网费35元/月），空调、卫生间、厨房齐全。屋内均是IT行业人士，喜欢安静。所以要求来租者最好是同行或者刚毕业的年轻人，爱干净、安静。

```java
public class ArrayTest {
      public static void main(String[] args) {
      int[] arr = new int[]{8,2,1,0,3};
      int[] index = new int[]{2,0,3,2,4,0,1,3,2,3,3};
      String tel = "";
      for(int i = 0;i < index.length;i++){
            tel += arr[index[i]];
      }
      System.out.println("联系方式：" + tel);
      }
}

```

案例2：输出英文星期几

用一个数组，保存星期一到星期天的7个英语单词，从键盘输入1-7，显示对应的单词
{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"}

```java
import java.util.Scanner;

/**
 * @author 尚硅谷-宋红康
 * @create 14:37
 */
public class WeekArrayTest {
    public static void main(String[] args) {

        //1. 声明并初始化星期的数组
        String[] weeks = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

        //2. 使用Scanner从键盘获取1-7范围的整数
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入[1-7]范围的整数：");
        int number = scanner.nextInt();

        if(number < 1 || number > 7){
            System.out.println("你输入的输入非法");
        }else{

            //3. 根据输入的整数，到数组中相应的索引位置获取指定的元素（即：星期几）
            System.out.println("对应的星期为：" + weeks[number - 1]);

        }
        
        scanner.close();

    }
}
```

**案例3：**从键盘读入学生成绩，找出最高分，并输出学生成绩等级。

- 成绩>=最高分-10  等级为’A’  

- 成绩>=最高分-20  等级为’B’

- 成绩>=最高分-30  等级为’C’  

- 其余            等级为’D’

提示：先读入学生人数，根据人数创建int数组，存放学生成绩。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317004637748.png" alt="image-20220317004637748" style="zoom:80%;" />

```java
/**
 * @author 尚硅谷-宋红康
 * @create 14:55
 */
public class ScoreTest1 {
    public static void main(String[] args) {

        //1. 根据提示，获取学生人数
        System.out.print("请输入学生人数：");
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        //2. 根据学生人数，创建指定长度的数组 (使用动态初始化)
        int[] scores = new int[count];

        //3. 使用循环，依次给数组的元素赋值
        int maxScore = 0; //记录最高分
        System.out.println("请输入" + count + "个成绩");
        for (int i = 0; i < scores.length; i++) {
            scores[i] = scanner.nextInt();
            //4. 获取数组中元素的最大值，即为最高分
            if(maxScore < scores[i]){
                maxScore = scores[i];
            }
        }

        System.out.println("最高分是：" + maxScore);

        //5. 遍历数组元素，输出各自的分数，并根据其分数与最高分的差值，获取各自的等级
        char grade;
        for (int i = 0; i < scores.length; i++) {

            if(scores[i] >= maxScore - 10){
                grade = 'A';
            }else if(scores[i] >= maxScore - 20){
                grade = 'B';
            }else if(scores[i] >= maxScore - 30){
                grade = 'C';
            }else{
                grade = 'D';
            }
            System.out.println("student " + i + " socre is " + scores[i] + ", grade is " + grade);
        }
        //关闭资源
        scanner.close();

    }
}
```

## 5. 多维数组的使用

### 5.1 概述

- Java 语言里提供了支持多维数组的语法。


- 如果说可以把一维数组当成几何中的`线性图形`，那么二维数组就相当于是`一个表格`，像Excel中的表格、围棋棋盘一样。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317004810263.png" alt="image-20220317004810263" style="zoom:80%;" />

- **应用举例1：**

  某公司2022年全年各个月份的销售额进行登记。按月份存储，可以使用一维数组。如下：

  ```java
  int[] monthData = new int[]{23,43,22,34,55,65,44,67,45,78,67,66};
  ```

  如果改写为按`季度`为单位存储怎么办呢？

  ```java
  int[][] quarterData = new int[][]{{23,43,22},{34,55,65},{44,67,45},{78,67,66}};
  ```


  - **应用举例2：**

高一年级三个班级均由多个学生姓名构成一个个数组。如下：

```java
String[] class1 = new String[]{"段誉","令狐冲","任我行"};

String[] class2 = new String[]{"张三丰","周芷若"};

String[] class3 = new String[]{"赵敏","张无忌","韦小宝","杨过"};

```

那从整个年级看，我们可以声明一个二维数组。如下：

```java
String[][] grade = new String[][]{{"段誉","令狐冲","任我行"},{"张三丰","周芷若"},{"赵敏","张无忌","韦小宝","杨过"}};
```


  - **应用举例3：**

  ![image-20220516095701345](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220516095701345.png)

蓝框的几个元素，可以使用一维数组来存储。但现在发现每个元素下还有下拉框，其内部还有元素，那就需要使用二维数组来存储：

  ![image-20220516095829526](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220516095829526.png)

- 使用说明

  ![1561524724397](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1561524724397-1647707344971.png)

> - 对于二维数组的理解，可以看成是一维数组array1又作为另一个一维数组array2的元素而存在。
> - 其实，从数组底层的运行机制来看，其实没有多维数组。

### 5.2 声明与初始化

#### 5.2.1 声明

二维数组声明的语法格式：

```java
//推荐
元素的数据类型[][] 二维数组的名称;

//不推荐
元素的数据类型  二维数组名[][];
//不推荐
元素的数据类型[]  二维数组名[];
```

例如：

```java
public class Test20TwoDimensionalArrayDefine {
    public static void main(String[] args) {
        //存储多组成绩
        int[][] grades;

        //存储多组姓名
        String[][] names;
    }
}
```

面试：

```
int[] x, y[];
//x是一维数组，y是二维数组
```

#### 5.2.2 静态初始化

**格式：**

```java
int[][] arr = new int[][]{{3,8,2},{2,7},{9,0,1,6}};
```

定义一个名称为arr的二维数组，二维数组中有三个一维数组

- 每一个一维数组中具体元素也都已初始化
  - 第一个一维数组 arr[0] = {3,8,2};
  - 第二个一维数组 arr[1] = {2,7};
  - 第三个一维数组 arr[2] = {9,0,1,6};
- 第三个一维数组的长度表示方式：arr[2].length;

> - 注意特殊写法情况：int[] x,y[]; x是一维数组，y是二维数组。

- 举例1：


```java
int[][] arr = {{1,2,3},{4,5,6},{7,8,9,10}};//声明与初始化必须在一句完成

int[][] arr = new int[][]{{1,2,3},{4,5,6},{7,8,9,10}};

int[][] arr;
arr = new int[][]{{1,2,3},{4,5,6},{7,8,9,10}};

arr = new int[3][3]{{1,2,3},{4,5,6},{7,8,9,10}};//错误，静态初始化右边new 数据类型[][]中不能写数字
```

- 举例2：


```java
public class TwoDimensionalArrayInitialize {
    public static void main(String[] args) {
        //存储多组成绩
        int[][] grades = {
                    {89,75,99,100},
                    {88,96,78,63,100,86},
                    {56,63,58},
                    {99,66,77,88}
                };

        //存储多组姓名
        String[][] names = {
            {"张三","李四", "王五", "赵六"},
            {"刘备","关羽","张飞","诸葛亮","赵云","马超"},
            {"曹丕","曹植","曹冲"},
            {"孙权","周瑜","鲁肃","黄盖"}
        };
    }
}
```

#### 5.2.3 动态初始化

如果二维数组的每一个数据，甚至是每一行的列数，需要后期单独确定，那么就只能使用动态初始化方式了。动态初始化方式分为两种格式：

**格式1：规则二维表：每一行的列数是相同的**

```java
//（1）确定行数和列数
元素的数据类型[][] 二维数组名 = new 元素的数据类型[m][n];
	//其中，m:表示这个二维数组有多少个一维数组。或者说一共二维表有几行
	//其中，n:表示每一个一维数组的元素有多少个。或者说每一行共有一个单元格

//此时创建完数组，行数、列数确定，而且元素也都有默认值

//（2）再为元素赋新值
二维数组名[行下标][列下标] = 值;
```

举例：

```java
int[][] arr = new int[3][2];
```

-  定义了名称为arr的二维数组

-  二维数组中有3个一维数组

-  每一个一维数组中有2个元素

-  一维数组的名称分别为arr[0], arr[1], arr[2]，打印**<font color='red'>一维数组就是存储的首地址值</font>**。

-  给第一个一维数组1脚标位赋值为78写法是：`arr[0][1] = 78;`

**格式2：不规则：每一行的列数不一样**

```java
//（1）先确定总行数
元素的数据类型[][] 二维数组名 = new 元素的数据类型[总行数][];

//此时只是确定了总行数，每一行里面现在是null

//（2）再确定每一行的列数，创建每一行的一维数组
二维数组名[行下标] = new 元素的数据类型[该行的总列数];

//此时已经new完的行的元素就有默认值了，没有new的行还是null

//(3)再为元素赋值
二维数组名[行下标][列下标] = 值;
```

举例：

```java
int[][] arr = new int[3][];
```

-  二维数组中有3个一维数组。
-  每个一维数组都是默认初始化值null (注意：区别于格式1）
-  可以对这个三个一维数组分别进行初始化：arr[0] = new int[3];    arr[1] = new int[1];   arr[2] = new int[2];
-  注：`int[][]arr = new int[][3]; ` //非法

练习：

```java
/*
 1
 2 2
 3 3 3
 4 4 4 4
 5 5 5 5 5
 */
public class Test25DifferentElementCount {
    public static void main(String[] args){
        //1、声明一个二维数组，并且确定行数
        //因为每一行的列数不同，这里无法直接确定列数
        int[][]  arr = new int[5][];

        //2、确定每一行的列数
        for(int i=0; i<arr.length; i++){
			/*
			arr[0] 的列数是1
			arr[1] 的列数是2
			arr[2] 的列数是3
			arr[3] 的列数是4
			arr[4] 的列数是5
			*/
            arr[i] = new int[i+1];
        }

        //3、确定元素的值
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                arr[i][j] = i+1;
            }
        }

        //4、遍历显示
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }
}
```

### 5.3 数组的长度和角标

- 二维数组的长度/行数：二维数组名.length
- 二维数组的某一行：二维数组名[行下标]，此时相当于获取其中一组数据。它本质上是一个一维数组。行下标的范围：[0, 二维数组名.length-1]。此时把二维数组看成一维数组的话，元素是行对象。
- 某一行的列数：二维数组名[行下标].length，因为二维数组的每一行是一个一维数组。
- 某一个元素：二维数组名\[行下标\]\[列下标\]，即先确定行/组，再确定列。

```java
public class Test22TwoDimensionalArrayUse {
    public static void main(String[] args){
        //存储3个小组的学员的成绩，分开存储，使用二维数组。
		/*
		int[][] scores1;
		int scores2[][];
		int[] scores3[];*/

        int[][] scores = {
                {85,96,85,75},
                {99,96,74,72,75},
                {52,42,56,75}
        };

        System.out.println(scores);//[[I@15db9742
        System.out.println("一共有" + scores.length +"组成绩.");

        //[[：代表二维数组，I代表元素类型是int
        System.out.println(scores[0]);//[I@6d06d69c
        //[：代表一维数组，I代表元素类型是int
        System.out.println(scores[1]);//[I@7852e922
        System.out.println(scores[2]);//[I@4e25154f
        //System.out.println(scores[3]);//ArrayIndexOutOfBoundsException: 3

        System.out.println("第1组有" + scores[0].length +"个学员.");
        System.out.println("第2组有" + scores[1].length +"个学员.");
        System.out.println("第3组有" + scores[2].length +"个学员.");

        System.out.println("第1组的每一个学员成绩如下：");
        //第一行的元素
        System.out.println(scores[0][0]);//85
        System.out.println(scores[0][1]);//96
        System.out.println(scores[0][2]);//85
        System.out.println(scores[0][3]);//75
        //System.out.println(scores[0][4]);//java.lang.ArrayIndexOutOfBoundsException: 4
    }
}
```

### 5.4 二维数组的遍历

- 格式：

```java
for(int i=0; i<二维数组名.length; i++){ //二维数组对象.length
    for(int j=0; j<二维数组名[i].length; j++){//二维数组行对象.length
        System.out.print(二维数组名[i][j]);
    }
    System.out.println();
}
```

- 举例：

```java
public class arrayTwo {
    public static void main(String[] args) {

        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    } 
}
```

![image-20230314131057899](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230314131057899.png)

### 5.5 二维数组内存解析

**<font color='red'>二维数组本质上是元素类型是一维数组的一维数组。</font>**

![image-20230314131916620](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230314131916620.png)

![image-20230314132611152](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230314132611152.png)

```java
int[][] arr = {
    {1},
    {2,2},
    {3,3,3},
    {4,4,4,4},
    {5,5,5,5,5}
}; 
```

![1562112672215](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1562112672215.png)

```java
//1、声明二维数组，并确定行数和列数
int[][] arr = new int[4][5];

//2、确定元素的值
for (int i = 0; i < arr.length; i++) {
    for (int j = 0; j < arr.length; j++) {
        arr[i][j] = i + 1;
    }
}
```

![1562113179785](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1562113179785.png)

```java
//1、声明一个二维数组，并且确定行数
//因为每一行的列数不同，这里无法直接确定列数
int[][]  arr = new int[5][];

//2、确定每一行的列数
for(int i=0; i<arr.length; i++){
    /*
			arr[0] 的列数是1
			arr[1] 的列数是2
			arr[2] 的列数是3
			arr[3] 的列数是4
			arr[4] 的列数是5
			*/
    arr[i] = new int[i+1];
}

//3、确定元素的值
for(int i=0; i<arr.length; i++){
    for(int j=0; j<arr[i].length; j++){
        arr[i][j] = i+1;
    }
}
```

![1562113981079](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1562113981079.png)

### 5.6 应用举例

**案例1：**获取arr数组中所有元素的和。

提示：使用for的嵌套循环即可。

![image-20220317005436209](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317005436209.png)

```java
public static void main(String[] args) {

    int[][] arr = {{3, 5, 8}, {12, 9}, {7,0,6,4}};
    int sum = 0;
    for (int i = 0; i < arr.length; i++) {
        for (int j = 0; j < arr[i].length; j++) {
            sum += arr[i][j];
        }
    }
    System.out.println(sum);
}
```

**案例2：**声明：int[] x,y[]; 在给x,y变量赋值以后，以下选项允许通过编译的是： 

```java
声明：int[] x,y[]; 在给x,y变量赋值以后，以下选项允许通过编译的是：
a)    x[0] = y;                 //no
b)    y[0] = x;                 //yes
c)    y[0][0] = x;              //no
d)    x[0][0] = y;              //no
e)    y[0][0] = x[0];           //yes
f)    x = y;                    //no

提示：
一维数组：int[] x  或者int x[]   
二维数组：int[][] y 或者  int[] y[]  或者 int  y[][]

```

**案例3：**使用二维数组打印一个 10 行杨辉三角。

提示：

1. 第一行有 1 个元素, 第 n 行有 n 个元素

2. 每一行的第一个元素和最后一个元素都是 1

3. 从第三行开始, 对于非第一个元素和最后一个元素的元素。即：

   ```
   yanghui[i][j] = yanghui[i-1][j-1] + yanghui[i-1][j];
   ```

![image-20220317005549522](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317005549522.png)

```java
    public static void main(String[] args) {
//        1.创建二维数组
        int[][] yangHui = new int[10][];
        for (int i = 0; i < yangHui.length; i++) {
            yangHui[i] = new int[i + 1];
//        2.二维数组的首末元素赋值
            yangHui[i][0] = yangHui[i][i] = 1;
//		  3.除首末元素外，其他元素都是yangHui[i-1][j] + yangHui[i-1][j-1]相加所得
            for (int j = 1; j < yangHui[i].length - 1; j++) {
                yangHui[i][j] = yangHui[i-1][j] + yangHui[i-1][j-1];
            }
        }

        for (int i = 0; i < yangHui.length; i++) {
            for (int j = 0; j < yangHui[i].length; j++) {

                System.out.print(yangHui[i][j] + "\t");
            }
            System.out.println();
        }
    }
```

## 6. 数组的常见算法

### 6.1 数值型数组特征值统计

- 这里的特征值涉及到：平均值、最大值、最小值、总和等

**举例1：**数组统计：求总和、均值

```java
public class TestArrayElementSum {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9};
        //求总和、均值
        int sum = 0;//因为0加上任何数都不影响结果
        for(int i=0; i<arr.length; i++){
            sum += arr[i];
        }
        double avg = (double)sum/arr.length;

        System.out.println("sum = " + sum);
        System.out.println("avg = " + avg);
    }
}
```

**举例2：**求数组元素的总乘积

```java
public class TestArrayElementMul {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9};

        //求总乘积
        long result = 1;//因为1乘以任何数都不影响结果
        for(int i=0; i<arr.length; i++){
            result *= arr[i];
        }

        System.out.println("result = " + result);
    }
}
```

**举例3：**求数组元素中偶数的个数

```java
public class TestArrayElementEvenCount {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9};
        //统计偶数个数
        int evenCount = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i]%2==0){
                evenCount++;
            }
        }

        System.out.println("evenCount = " + evenCount);
    }
}
```

**举例4：**求数组元素的最大值

![1574577970893](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1574577970893.png)

```java
public class TestArrayMax {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9};
        //找最大值
        int max = arr[0];
        for(int i=1; i<arr.length; i++){//此处i从1开始，是max不需要与arr[0]再比较一次了
            if(arr[i] > max){
                max = arr[i];
            }
        }

        System.out.println("max = " + max);
    }
}
```

**举例5：**找最值及其第一次出现的下标

```java
public class TestMaxIndex {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9};
        //找最大值以及第一个最大值下标
        int max = arr[0];
        int index = 0;
        for(int i=1; i<arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
                index = i;
            }
        }

        System.out.println("max = " + max);
        System.out.println("index = " + index);
    }
}
```

**举例6：**找最值及其所有最值的下标

```java
public class Test13AllMaxIndex {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9,9,3};
        //找最大值
        int max = arr[0];
        for(int i=1; i<arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        System.out.println("最大值是：" + max);
        System.out.print("最大值的下标有：");

        //遍历数组，看哪些元素和最大值是一样的
        for(int i=0; i<arr.length; i++){
            if(max == arr[i]){
                System.out.print(i+"\t");
            }
        }
        System.out.println();
    }
}
```

优化

```java
public class Test13AllMaxIndex2 {
    public static void main(String[] args) {
        int[] arr = {4,5,6,1,9,9,3};
        //找最大值
        int max = arr[0];
        String index = "0";
        for(int i=1; i<arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
                index = i + "";
            }else if(arr[i] == max){
                index += "," + i;
            }
        }

        System.out.println("最大值是" + max);
        System.out.println("最大值的下标是[" + index+"]");
    }
}
```

**举例7(难)：**输入一个整形数组，数组里有正数也有负数。数组中连续的一个或多个整数组成一个子数组，每个子数组都有一个和。求所有子数组的和的最大值。要求时间复杂度为O(n)。
例如：输入的数组为1, -2, 3, -10, -4, 7, 2, -5，和最大的子数组为3, 10, -4, 7, 2，因此输出为该子数组的和18。

```java
public class Test5 {
	public static void main(String[] args) {
		int[] arr = new int[]{1, -2, 3, 10, -4, 7, 2, -5};
		int i = getGreatestSum(arr);
		System.out.println(i);
	}
	
	public static int getGreatestSum(int[] arr){
		int greatestSum = 0;
		if(arr == null || arr.length == 0){
			return 0;
		}
		int temp = greatestSum;
		for(int i = 0;i < arr.length;i++){
			temp += arr[i];
			
			if(temp < 0){
				temp = 0;
			}
			
			if(temp > greatestSum){
				greatestSum = temp;
			}
		}
		if(greatestSum == 0){
			greatestSum = arr[0];
			for(int i = 1;i < arr.length;i++){
				if(greatestSum < arr[i]){
					greatestSum = arr[i];
				}
			}
		}
		return greatestSum;
	}
}
```

**举例8：评委打分**

分析以下需求，并用代码实现：

（1）在编程竞赛中，有10位评委为参赛的选手打分，分数分别为：5，4，6，8，9，0，1，2，7，3

（2）求选手的最后得分（去掉一个最高分和一个最低分后其余8位评委打分的平均值）

```java
/**
 * @author 尚硅谷-宋红康
 * @create 10:03
 */
public class ArrayExer {
    public static void main(String[] args) {
        int[] scores = {5,4,6,8,9,0,1,2,7,3};

        int max = scores[0];
        int min = scores[0];
        int sum = 0;
        for(int i = 0;i < scores.length;i++){
            if(max < scores[i]){
                max = scores[i];
            }

            if(min > scores[i]){
                min = scores[i];
            }

            sum += scores[i];
        }

        double avg = (double)(sum - max - min) / (scores.length - 2);

        System.out.println("选手去掉最高分和最低分之后的平均分为：" + avg);
    }
}
```

### 6.2 数组元素的赋值与数组复制

**举例1：**杨辉三角（见二维数组课后案例）

**举例2：**使用简单数组

(1)创建一个名为ArrayTest的类，在main()方法中声明array1和array2两个变量，他们是int[]类型的数组。

(2)使用大括号{}，把array1初始化为8个素数：2,3,5,7,11,13,17,19。

(3)显示array1的内容。

(4)赋值array2变量等于array1，修改array2中的偶索引元素，使其等于索引值(如array[0]=0,array[2]=2)。打印出array1。  array2 = array1;

> **思考：**array1和array2是什么关系？
>
> array1和array2指向同一个内存地址
>
> **拓展：**修改题目，实现array2对array1数组的复制

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/snipaste_20220317_225346.jpg" alt="snipaste_20220317_225346" style="zoom:80%;" />![snipaste_20220317_225359](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/snipaste_20220317_225359.jpg)

**举例3：**一个数组，让数组的每个元素去除第一个元素，得到的商作为被除数所在位置的新值。

```java
public class Test3 {
	public static void main(String[] args) {
		int[] arr = new int[]{12,43,65,3,-8,64,2};
		
//		for(int i = 0;i < arr.length;i++){
//			arr[i] = arr[i] / arr[0];
//		}
		for(int i = arr.length -1;i >= 0;i--){
			arr[i] = arr[i] / arr[0];
		}
		//遍历arr
		for(int i = 0;i < arr.length;i++){
			System.out.print(arr[i] + " ");
		}
	}
}
```

**举例4：**创建一个长度为6的int型数组，要求数组元素的值都在1-30之间，且是随机赋值。同时，要求元素的值各不相同。 

```java
public class Test4 {
	// 5-67 Math.random() * 63 + 5;
	@Test
	public void test1() {
		int[] arr = new int[6];
		for (int i = 0; i < arr.length; i++) {// [0,1) [0,30) [1,31)
			arr[i] = (int) (Math.random() * 30) + 1;

			boolean flag = false;
			while (true) {
				for (int j = 0; j < i; j++) {
					if (arr[i] == arr[j]) {
						flag = true;
						break;
					}
				}
				if (flag) {
					arr[i] = (int) (Math.random() * 30) + 1;
					flag = false;
					continue;
				}
				break;
			}
		}

		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
	//更优的方法
	@Test
	public void test2(){
		int[] arr = new int[6];
		for (int i = 0; i < arr.length; i++) {// [0,1) [0,30) [1,31)
			arr[i] = (int) (Math.random() * 30) + 1;
			
				for (int j = 0; j < i; j++) {
					if (arr[i] == arr[j]) {
						i--;
						break;
					}
				}
			}

		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}
}
```

**举例5：**扑克牌

案例：遍历扑克牌

遍历扑克牌，效果如图所示：

![1659199523932](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1659199523932.png)

提示：使用两个字符串数组，分别保存花色和点数，再用一个字符串数组保存最后的扑克牌。
String[] hua = {"黑桃","红桃","梅花","方片"};
String[] dian = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

```java
package com.atguigu3.common_algorithm.exer5;

/**
 * @author 尚硅谷-宋红康
 * @create 17:16
 */
public class ArrayExer05 {
    public static void main(String[] args) {
        String[] hua = {"黑桃","红桃","梅花","方片"};
        String[] dian = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};


        String[] pai = new String[hua.length * dian.length];
        int k = 0;
        for(int i = 0;i < hua.length;i++){
            for(int j = 0;j < dian.length;j++){
                pai[k++] = hua[i] + dian[j];
            }
        }

        for (int i = 0; i < pai.length; i++) {
            System.out.print(pai[i] + "  ");
            if(i % 13 == 12){
                System.out.println();
            }
        }

    }
}

```


拓展：在上述基础上，增加大王、小王。

**举例6：**回形数

从键盘输入一个整数（1~20） ，则以该数字为矩阵的大小，把1,2,3…n*n 的数字按照顺时针螺旋的形式填入其中。

例如： 输入数字2，则程序输出： 
1 2 
4 3 

输入数字3，则程序输出： 
1 2 3 
8 9 4 
7 6 5 
输入数字4， 则程序输出： 
1    2    3    4 
12  13  14  5 
11  16  15  6 
10   9   8    7

```java
//方式1
public class RectangleTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("输入一个数字");
		int len = scanner.nextInt();
		int[][] arr = new int[len][len];
		
		int s = len * len;
		/*
		 * k = 1:向右
		 * k = 2:向下
		 * k = 3:向左
		 * k = 4:向上
		 */
		int k = 1;
		int i = 0,j = 0;
		for(int m = 1;m <= s;m++){
			if(k == 1){
				if(j < len && arr[i][j] == 0){
					arr[i][j++] = m;
				}else{
					k = 2;
					i++;  
					j--;
					m--;
				}
			}else if(k == 2){
				if(i < len && arr[i][j] == 0){
					arr[i++][j] = m;
				}else{
					k = 3;
					i--;
					j--;
					m--;
				}
			}else if(k == 3){
				if(j >= 0 && arr[i][j] == 0){
					arr[i][j--] = m;
				}else{
					k = 4;
					i--;
					j++;
					m--;
				}
			}else if(k == 4){
				if(i >= 0 && arr[i][j] == 0){
					arr[i--][j] = m;
				}else{
					k = 1;
					i++;
					j++;
					m--;
				}
			}
		}
		
		//遍历
		for(int m = 0;m < arr.length;m++){
			for(int n = 0;n < arr[m].length;n++){
				System.out.print(arr[m][n] + "\t");
			}
			System.out.println();
		}
	}
}
```

```java
//方式2
/*
	01 02 03 04 05 06 07 
	24 25 26 27 28 29 08 
	23 40 41 42 43 30 09 
	22 39 48 49 44 31 10 
	21 38 47 46 45 32 11 
	20 37 36 35 34 33 12 
	19 18 17 16 15 14 13 
 */
public class RectangleTest1 {

	public static void main(String[] args) {
		int n = 7;
		int[][] arr = new int[n][n];
		
		int count = 0; //要显示的数据
		int maxX = n-1; //x轴的最大下标
		int maxY = n-1; //Y轴的最大下标
		int minX = 0; //x轴的最小下标
		int minY = 0; //Y轴的最小下标
		while(minX<=maxX) {
			for(int x=minX;x<=maxX;x++) {
				arr[minY][x] = ++count;
			}
			minY++;
			for(int y=minY;y<=maxY;y++) {
				arr[y][maxX] = ++count;
			}
			maxX--;
			for(int x=maxX;x>=minX;x--) {
				arr[maxY][x] = ++count;
			}
			maxY--;
			for(int y=maxY;y>=minY;y--) {
				arr[y][minX] = ++count;
			}
			minX++;
		}
		
		
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr.length;j++) {
				String space = (arr[i][j]+"").length()==1 ? "0":"";
				System.out.print(space+arr[i][j]+" ");
			}
			System.out.println();
		}
	}
}
```

### 6.3 数组元素的反转

**实现思想：**数组对称位置的元素互换。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221117195931777.png" alt="image-20221117195931777" style="zoom:67%;" />

```java
    public static void main(String[] args) {
        int[] number = {1,2,3,4,5,6,7,8,9,10};

        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }

        for (int i = 0; i < number.length / 2; i++) {
            int temp = number[i];
            number[i] = number[number.length - i - 1];
            number[number.length - i - 1] = temp;
        }
        System.out.println();
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
    }
```

或

![1561469087319](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1561469087319.png)

```java
public class TestArrayReverse2 {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};
        System.out.println("反转之前：");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        //反转
        //左右对称位置交换
        for(int left=0,right=arr.length-1; left<right; left++,right--){
            //首  与  尾交换
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
        }

        System.out.println("反转之后：");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
```

### 6.4 数组的扩容与缩容

**数组的扩容**

题目：现有数组 int[] arr = new int[]{1,2,3,4,5}; ，现将数组长度扩容1倍，并将10,20,30三个数据添加到arr数组中，如何操作？

```java
public class ArrTest1 {
    public static void main(String[] args) {

        int[] arr = new int[]{1,2,3,4,5};
        int[] newArr = new int[arr.length << 1];

        for(int i = 0;i < arr.length;i++){
            newArr[i] = arr[i];
        }

        newArr[arr.length] = 10;
        newArr[arr.length + 1] = 20;
        newArr[arr.length + 2] = 30;

        arr = newArr;

        //遍历arr
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
```

**数组的缩容**

题目：现有数组 int[] arr={1,2,3,4,5,6,7}。现需删除数组中索引为4的元素。

```java
public class ArrTest2 {
    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        //删除数组中索引为4的元素
        int delIndex = 4;
        //方案1：
        /*//创建新数组
        int[] newArr = new int[arr.length - 1];

        for (int i = 0; i < delIndex; i++) {
            newArr[i] = arr[i];
        }
        for (int i = delIndex + 1; i < arr.length; i++) {
            newArr[i - 1] = arr[i];
        }

        arr = newArr;
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }*/

        //方案2：
        for (int i = delIndex; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[arr.length - 1] = 0;

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
```

### 6.5 数组的元素查找

**1、顺序查找**

顺序查找：挨个查看

要求：对数组元素的顺序没要求

```java
public class TestArrayOrderSearch {
    //查找value第一次在数组中出现的index
    public static void main(String[] args){
        int[] arr = {4,5,6,1,9};
        int value = 1;
        int index = -1;

        for(int i=0; i<arr.length; i++){
            if(arr[i] == value){
                index = i;
                break;
            }
        }

        if(index==-1){
            System.out.println(value + "不存在");
        }else{
            System.out.println(value + "的下标是" + index);
        }
    }
}
```

**2、二分查找**

**<font color='red'>二分法查找的前提是数组元素是有序的。</font>**

举例：

![image-20220317230955644](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317230955644.png)

实现步骤：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220623210601915.png" alt="image-20220623210601915" style="zoom:67%;" />

```java
//二分法查找：要求此数组必须是有序的。
int[] arr3 = new int[]{-99,-54,-2,0,2,33,43,256,999};
boolean isFlag = true;
int value = 256;
//int value = 25;
int head = 0;//首索引位置
int end = arr3.length - 1;//尾索引位置
while(head <= end){
    int middle = (head + end) / 2;
    if(arr3[middle] == value){
        System.out.println("找到指定的元素，索引为：" + middle);
        isFlag = false;
        break;
    }else if(arr3[middle] > value){
        end = middle - 1;
    }else{//arr3[middle] < value
        head = middle + 1;
    }
}

if(isFlag){
    System.out.println("未找打指定的元素");
}

```

### 6.6 数组元素排序

#### 6.6.1 算法概述

- **定义**

  - 排序：假设含有n个记录的序列为{R1，R2，...,Rn},其相应的关键字序列为{K1，K2，...,Kn}。将这些记录重新排序为{Ri1,Ri2,...,Rin},使得相应的关键字值满足条Ki1<=Ki2<=...<=Kin,这样的一种操作称为排序。
  - 通常来说，排序的目的是快速查找。

- **衡量排序算法的优劣：**

  - `时间复杂度`：分析关键字的比较次数和记录的移动次数

  - 常见的算法时间复杂度由小到大依次为：Ο(1)＜Ο(log2n)＜Ο(n)＜Ο(nlog2n)＜Ο(n<sup>2</sup>)＜Ο(n<sup>3</sup>)＜…＜Ο(2<sup>n</sup>)＜Ο(n!)<O(n<sup>n</sup>)

  - `空间复杂度`：分析排序算法中需要多少辅助内存

    ```
    一个算法的空间复杂度S(n)定义为该算法所耗费的存储空间，它也是问题规模n的函数。
    ```

  - `稳定性`：若两个记录A和B的关键字值相等，但排序后A、B的先后次序保持不变，则称这种排序算法是稳定的。

    ![image-20211222113701365](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211222113701365.png)


#### 6.6.2 排序算法概述

- **排序算法分类：内部排序和外部排序**
  - `内部排序`：整个排序过程不需要借助于外部存储器（如磁盘等），所有排序操作都在内存中完成。
  - `外部排序`：参与排序的数据非常多，数据量非常大，计算机无法把整个排序过程放在内存中完成，必须借助于外部存储器（如磁盘）。外部排序最常见的是多路归并排序。可以认为外部排序是由多次内部排序组成。

- **十大内部排序算法**

​       数组的排序算法很多，实现方式各不相同，时间复杂度、空间复杂度、稳定性也各不相同：

![image-20211222111142684](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211222111142684.png)

常见时间复杂度所消耗的时间从小到大排序：

**O(1) < O(logn) < O(n) < O(nlogn) < O(n^2) < O(n^3) < O(2^n) < O(n!) < O(n^n)**

注意，经常将以2为底n的对数简写成logn。

![image-20220824003440106](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220824003440106.png)

#### 6.6.3 冒泡排序（Bubble Sort）

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220516094637228.png" alt="image-20220516094637228" style="zoom:67%;" />

**排序思想：**

1. 比较相邻的元素。如果第一个比第二个大（升序），就交换他们两个。

2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大的数。

3. 针对所有的元素重复以上的步骤，除了最后一个。

4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较为止。

![BubbleSort](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/BubbleSort.png)

**动态演示：**https://visualgo.net/zh/sorting

```java
/*
1、冒泡排序（最经典）
思想：每一次比较“相邻（位置相邻）”元素，如果它们不符合目标顺序（例如：从小到大），
     就交换它们，经过多轮比较，最终实现排序。
	 （例如：从小到大）	 每一轮可以把最大的沉底，或最小的冒顶。
	 
过程：arr{6,9,2,9,1}  目标：从小到大

第一轮：
	第1次，arr[0]与arr[1]，6>9不成立，满足目标要求，不交换
	第2次，arr[1]与arr[2]，9>2成立，不满足目标要求，交换arr[1]与arr[2] {6,2,9,9,1}
	第3次，arr[2]与arr[3]，9>9不成立，满足目标要求，不交换
	第4次，arr[3]与arr[4]，9>1成立，不满足目标要求，交换arr[3]与arr[4] {6,2,9,1,9}
	第一轮所有元素{6,9,2,9,1}已经都参与了比较，结束。
	第一轮的结果：第“一”最大值9沉底（本次是后面的9沉底），即到{6,2,9,1,9}元素的最右边

第二轮：
	第1次，arr[0]与arr[1]，6>2成立，不满足目标要求，交换arr[0]与arr[1] {2,6,9,1,9}
	第2次，arr[1]与arr[2]，6>9不成立，满足目标要求，不交换
	第3次：arr[2]与arr[3]，9>1成立，不满足目标要求，交换arr[2]与arr[3] {2,6,1,9,9}
	第二轮未排序的所有元素 {6,2,9,1}已经都参与了比较，结束。
	第二轮的结果：第“二”最大值9沉底（本次是前面的9沉底），即到{2,6,1,9}元素的最右边
第三轮：
	第1次，arr[0]与arr[1]，2>6不成立，满足目标要求，不交换
	第2次，arr[1]与arr[2]，6>1成立，不满足目标要求，交换arr[1]与arr[2] {2,1,6,9,9}
	第三轮未排序的所有元素{2,6,1}已经都参与了比较，结束。
	第三轮的结果：第三最大值6沉底，即到 {2,1,6}元素的最右边
第四轮：
	第1次，arr[0]与arr[1]，2>1成立，不满足目标要求，交换arr[0]与arr[1] {1,2,6,9,9}
	第四轮未排序的所有元素{2,1}已经都参与了比较，结束。
	第四轮的结果：第四最大值2沉底，即到{1,2}元素的最右边

*/
   public static void main(String[] args) {

        int[] number = {12, 45, 23, 2, 5, 80, 19, 20, 68};

        for (int i = 0; i < number.length - 1; i++) {
            for (int j = 0; j < number.length - 1 - i ; j++) {
                if (number[j] > number[j + 1]) {
                    int temp = number[j];
                    number[j] = number[j + 1];
                    number[j + 1] = temp;
                }
            }

        }
        for (int i = 0; i < number.length; i++) {
            System.out.print(number[i] + " ");
        }
    }
```

**冒泡排序优化（选讲）**

```java
/*
思考：冒泡排序是否可以优化
*/
class Test19BubbleSort2{
	public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9};

        //从小到大排序
        for (int i = 0; i < arr.length - 1; i++) {
            boolean flag = true;//假设数组已经是有序的
            for (int j = 0; j < arr.length - 1 - i; j++) {
                //希望的是arr[j] < arr[j+1]
                if (arr[j] > arr[j + 1]) {
                    //交换arr[j]与arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    flag = false;//如果元素发生了交换，那么说明数组还没有排好序
                }
            }
            if (flag) {
                break;
            }
        }

        //完成排序，遍历结果
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "  ");
        }
    }
}
```

#### 6.6.4 快速排序

快速排序（Quick Sort）由`图灵奖`获得者`Tony Hoare`发明，被列为`20世纪十大算法之一`，是迄今为止所有内排序算法中速度最快的一种，快速排序的时间复杂度为O(nlog(n))。

快速排序通常明显比同为O(nlogn)的其他算法更快，因此常被采用，而且快排采用了分治法的思想，所以在很多笔试面试中能经常看到快排的影子。

排序思想：

1. 从数列中挑出一个元素，称为"基准"（pivot），

2. 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。

3. 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。

4. 递归的最底部情形，是数列的大小是零或一，也就是永远都已经被排序好了。虽然一直递归下去，但是这个算法总会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。

**动态演示：**https://visualgo.net/zh/sorting

图示1：

![image-20220317235922776](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220317235922776.png)

图示2：

第一轮操作：

![image-20221117205612230](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221117205612230.png)

第二轮操作：

![image-20221117205719427](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221117205719427.png)



#### 6.6.5 内部排序性能比较与选择

- 性能比较
  - **从平均时间而言**：快速排序最佳。但在最坏情况下时间性能不如堆排序和归并排序。
  - **从算法简单性看**：由于直接选择排序、直接插入排序和冒泡排序的算法比较简单，将其认为是简单算法。对于Shell排序、堆排序、快速排序和归并排序算法，其算法比较复杂，认为是复杂排序。
  - **从稳定性看**：直接插入排序、冒泡排序和归并排序时稳定的；而直接选择排序、快速排序、 Shell排序和堆排序是不稳定排序
  - **从待排序的记录数n的大小看**，n较小时，宜采用简单排序；而n较大时宜采用改进排序。

- 选择
  - 若n较小(如n≤50)，可采用直接插入或直接选择排序。
    当记录规模较小时，直接插入排序较好；否则因为直接选择移动的记录数少于直接插入，应选直接选择排序为宜。
  - 若文件初始状态基本有序(指正序)，则应选用直接插入、冒泡或随机的快速排序为宜；
  - 若n较大，则应采用时间复杂度为O(nlgn)的排序方法：快速排序、堆排序或归并排序。

## 7. Arrays工具类的使用

java.util.Arrays类即为操作数组的工具类，包含了用来操作数组（比如排序和搜索）的各种方法。 比如：

* `数组元素拼接`
  * static String toString(int[] a) ：字符串表示形式由数组的元素列表组成，括在方括号（"[]"）中。相邻元素用字符 ", "（逗号加空格）分隔。形式为：[元素1，元素2，元素3。。。]
  * static String toString(Object[] a) ：字符串表示形式由数组的元素列表组成，括在方括号（"[]"）中。相邻元素用字符 ", "（逗号加空格）分隔。元素将自动调用自己从Object继承的toString方法将对象转为字符串进行拼接，如果没有重写，则返回类型@hash值，如果重写则按重写返回的字符串进行拼接。
* `数组排序`
  * static void sort(int[] a) ：将a数组按照从小到大进行排序
  * static void sort(int[] a, int fromIndex, int toIndex) ：将a数组的[fromIndex, toIndex)部分按照升序排列
  * static void sort(Object[] a) ：根据元素的自然顺序对指定对象数组按升序进行排序。
  * static <T> void sort(T[] a, Comparator<? super T> c) ：根据指定比较器产生的顺序对指定对象数组进行排序。
* `数组元素的二分查找`
  * static int binarySearch(int[] a, int key)  、static int binarySearch(Object[] a, Object key) ：要求数组有序，在数组中查找key是否存在，如果存在返回第一次找到的下标，不存在返回负数。
* `数组的复制`
  * static int[] copyOf(int[] original, int newLength)  ：根据original原数组复制一个长度为newLength的新数组，并返回新数组
  * static <T> T[] copyOf(T[] original,int newLength)：根据original原数组复制一个长度为newLength的新数组，并返回新数组
  * static int[] copyOfRange(int[] original, int from, int to) ：复制original原数组的[from,to)构成新数组，并返回新数组
  * static <T> T[] copyOfRange(T[] original,int from,int to)：复制original原数组的[from,to)构成新数组，并返回新数组
* `比较两个数组是否相等`
  * static boolean equals(int[] a, int[] a2) ：比较两个数组的长度、元素是否完全相同
  * static boolean equals(Object[] a,Object[] a2)：比较两个数组的长度、元素是否完全相同
* `填充数组`
  * static void fill(int[] a, int val) ：用val值填充整个a数组
  * static void fill(Object[] a,Object val)：用val对象填充整个a数组
  * static void fill(int[] a, int fromIndex, int toIndex, int val)：将a数组[fromIndex,toIndex)部分填充为val值
  * static void fill(Object[] a, int fromIndex, int toIndex, Object val) ：将a数组[fromIndex,toIndex)部分填充为val对象

举例：java.util.Arrays类的sort()方法提供了数组元素排序功能：

```java
import java.util.Arrays;
public class SortTest {
	public static void main(String[] args) {
		int[] arr = {3, 2, 5, 1, 6};
        System.out.println("排序前" + Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println("排序后" + Arrays.toString(arr));
	}
}

```

##  8. 数组中的常见异常

### 8.1 数组角标越界异常

当访问数组元素时，下标指定超出[0, 数组名.length-1]的范围时，就会报数组下标越界异常：ArrayIndexOutOfBoundsException。

```java
public class TestArrayIndexOutOfBoundsException {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
       // System.out.println("最后一个元素：" + arr[3]);//错误，下标越界
      //  System.out.println("最后一个元素：" + arr[arr.length]);//错误，下标越界
        System.out.println("最后一个元素：" + arr[arr.length-1]);//对
    }
}

```

创建数组，赋值3个元素，数组的索引就是0，1，2，没有3索引，因此我们不能访问数组中不存在的索引，程序运行后，将会抛出 `ArrayIndexOutOfBoundsException`  数组越界异常。在开发中，数组的越界异常是**不能出现**的，一旦出现了，就必须要修改我们编写的代码。

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%95%B0%E7%BB%84%E8%B6%8A%E7%95%8C%E5%BC%82%E5%B8%B8.jpg)

### 8.2 空指针异常

观察一下代码，运行后会出现什么结果。

```java
public class TestNullPointerException {
    public static void main(String[] args) {
        //定义数组
        int[][] arr = new int[3][];

        System.out.println(arr[0][0]);//NullPointerException
    }
}
```

因为此时数组的每一行还未分配具体存储元素的空间，此时arr\[0\]是null，此时访问arr\[0\]\[0\]会抛出`NullPointerException` 空指针异常。

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%A9%BA%E6%8C%87%E9%92%88%E5%BC%82%E5%B8%B8-1647708157677.jpg)

**空指针异常在内存图中的表现**

![1572338767825](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1572338767825-1647708157678.png)

**小结：空指针异常情况**

```java
		//举例一：
//		int[] arr1 = new int[10];
//		arr1 = null;
//		System.out.println(arr1[9]);
		
		//举例二：
//		int[][] arr2 = new int[5][];
//		//arr2[3] = new int[10];
//		System.out.println(arr2[3][3]);
		
		//举例三：
		String[] arr3 = new String[10];
		System.out.println(arr3[2].toString());
```

## 9.随堂复习

### 1. 数组的概述

- 数组，就可以理解为多个数据的组合。
- 是程序中的容器：数组、集合框架（第12章，List、Set、Map）
- 数组存储的数据的特点：依次紧密排列的、有序的、可以重复的
- 此时的数组、集合框架都是在内存中对多个数据的存储。
- 数组的其它特点：一旦初始化，其长度就是确定的、不可更改的

### 2. 一维数组的使用（重要）

```
> 数组的声明和初始化
	int[] arr = new int[10];
	String[] arr1 = new String[]{"Tom","Jerry"};
> 调用数组的指定元素:使用角标、索引、index
	>index从0开始！
> 数组的属性：length,表示数组的长度
> 数组的遍历
> 数组元素的默认初始化值
> 一维数组的内存解析（难）
	前提：在main()中声明变量：int[] arr = new int[]{1,2,3};
	> 虚拟机栈：main()作为一个栈帧，压入栈空间中。在main()栈帧中，存储着arr变量。arr记录着数组实体的首地址值。
	> 堆：数组实体存储在堆空间中。
```

### 3. 二维数组的使用（难点）

- 二维数组：一维数组的元素，又是一个唯一数组，则构成了二维数组。

```
> 数组的声明和初始化
> 调用数组的指定元素
> 数组的属性：length,表示数组的长度
> 数组的遍历
> 数组元素的默认初始化值
> 二维数组的内存解析（难）
```

### 4. 数组的常用算法（重要）

- 数值型数组的特征值的计算：最大值、最小值、总和、平均值等
- 数组元素的赋值。比如：杨辉三角；彩票随机生成数（6位；1-30；不能重复）；回形数
- 数组的复制、赋值
- 数组的反转
- 数组的扩容、缩容
- 数组的查找
  - 线性查找
  - 二分法查找（前提：数组有序）
- 数组的排序
  - 冒泡排序：最简单
  - 快速排序：最常用

### 5. Arrays工具类的使用

- 熟悉一下内部的常用的方法
  - toString() / sort() / binarySearch()

### 6. 数组中的常见异常

- ArrayIndexOutOfBoundsException
- NullPointerException



## 10.第四章企业真题

### 1. 数组有没有length()这个方法? String有没有length()这个方法？（*蓝）

数组没有length()，是length属性。

String有length()

### 2. 有数组int[] arr，用Java代码将数组元素顺序颠倒（闪*购）

略

### 3. 为什么数组要从0开始编号，而不是1(中*支付)

数组的索引，表示了数组元素距离首地址的偏离量。因为第1个元素的地址与首地址相同，所以偏移量就是0。所以从0开始。

### 4. 数组有什么排序的方式，手写一下（平*保险）

冒泡。

快排。（讲完递归方法以后，大家就可以练习一下）

### 5. 常见排序算法，说下快排过程，时间复杂度？（5*到家）

见课件。

快排：O(nlogn)

### 6. 二分算法实现数组的查找（神舟*天软件）

略

### 7. 怎么求数组的最大子序列和（携*）

```java
/*
 * 输入一个整形数组，数组里有正数也有负数。数组中连续的一个或多个整数组成一个子数组，每个子数组都有一个和。
 * 求所有子数组的和的最大值。要求时间复杂度为O(n)。
 例如：输入的数组为1, -2, 3, 10, -4, 7, 2, -5，和最大的子数组为3, 10, -4, 7, 2，
 因此输出为该子数组的和18。
 * @author 尚硅谷-宋红康
 */
public class ArrDemo {
	public static void main(String[] args) {
		int[] arr = new int[]{1, -2, 3, 10, -4, 7, 2, -5};
		int i = getGreatestSum(arr);
		System.out.println(i);
	}
	
	public static int getGreatestSum(int[] arr){
		int greatestSum = 0;
		if(arr == null || arr.length == 0){
			return 0;
		}
		int temp = greatestSum;
		for(int i = 0;i < arr.length;i++){
			temp += arr[i];
			
			if(temp < 0){
				temp = 0;
			}
			
			if(temp > greatestSum){
				greatestSum = temp;
			}
		}
		if(greatestSum == 0){
			greatestSum = arr[0];
			for(int i = 1;i < arr.length;i++){
				if(greatestSum < arr[i]){
					greatestSum = arr[i];
				}
			}
		}
		return greatestSum;
	}
}
```



### 8. Arrays 类的排序方法是什么？如何实现排序的？（阿\*、阿*校招）

略



# 第05章_面向对象编程（基础）

![第2阶段：Java面向对象编程-第06章](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC2%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1%E7%BC%96%E7%A8%8B-%E7%AC%AC06%E7%AB%A0.png)

***

**学习面向对象内容的三条主线**

- Java类及类的成员：（重点）属性、方法、构造器；（熟悉）代码块、内部类
- 面向对象的特征：封装、继承、多态、（抽象）
- 其他关键字的使用：this、super、package、import、static、final、interface、abstract等

## 1. 面向对象编程概述 

### 1.1 程序设计的思路

面向对象，是软件开发中的一类编程风格、开发范式。除了`面向对象`，还有`面向过程`、`指令式编程`和`函数式编程`。在所有的编程范式中，我们接触最多的还是面向过程和面向对象两种。

> 类比：史书类型
>
> - 纪传体：以人物传记为中心，“本纪”叙述帝王，“世家”记叙王侯封国和特殊人物，“列传”记叙民间人物。
> - 编年体：按年、月、日顺序编写。
> - 国别体：是一部分国记事的历史散文，分载多国历史。

早期先有面向过程思想，随着软件规模的扩大，问题复杂性的提高，面向过程的`弊端`越来越明显，出现了面向对象思想并成为目前主流的方式。

**1. 面向过程的程序设计思想（Process-Oriented Programming）**，简称`POP`

- 关注的焦点是`过程`：过程就是操作数据的步骤。如果某个过程的实现代码重复出现，那么就可以把这个过程抽取为一个`函数`。这样就可以大大简化冗余代码，便于维护。
- 典型的语言：C语言
- 代码结构：以`函数`为组织单位。
- 是一种“`执行者思维`”，适合解决简单问题。扩展能力差、后期维护难度较大。

**2. 面向对象的程序设计思想（ Object Oriented Programming）**，简称`OOP`

- 关注的焦点是`类`：在计算机程序设计过程中，参照现实中事物，**<font color='red'>将事物的属性特征、行为特征抽象出来，用类来表示。</font>**
- 典型的语言：Java、C#、C++、Python、Ruby和PHP等
- 代码结构：以`类`为组织单位。每种事物都具备自己的`属性`和`行为/功能`。
- 是一种“`设计者思维`”，适合解决复杂问题。代码扩展性强、可维护性高。

### 1.2 由实际问题考虑如何设计程序

**思考1：如何开车？**

面向过程思想思考问题时，我们首先思考“`怎么按步骤实现？`”并将步骤对应成方法，一步一步，最终完成。 这个适合`简单任务`，不需要`过多协作`的情况。针对如何开车，可以列出步骤：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E9%9D%A2%E5%90%91%E8%BF%87%E7%A8%8B%E7%9A%84%E6%80%9D%E8%80%83.png" alt="image-20220520154055429" style="zoom: 90%;" />

面向过程适合简单、不需要协作的事务，重点关注如何执行。 

**思考2：如何造车？**

造车太复杂，需要`很多协作`才能完成。此时我们思考的是“`车怎么设计？`”，而不是“怎么按特定步骤造车的问题”。这就是思维方式的转变，前者就是面向对象思想。所以，面向对象(Oriented-Object)思想更契合人的思维模式。

用面向对象思想思考“如何设计车”：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/a49bd1fc8152291f0c5b43ee3322672d.png" alt="a49bd1fc8152291f0c5b43ee3322672d" style="zoom: 25%;" />

自然地，我们就会从“车由什么组成”开始思考。发现，车由如下结构组成：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520155357634.png" alt="image-20220520155357634" style="zoom: 67%;" />

我们找轮胎厂完成制造轮胎的步骤，发动机厂完成制造发动机的步骤，...；这样，大家可以同时进行车的制造，最终进行组装，大大提高了效率。但是，具体到轮胎厂的一个流水线操作，仍然是有步骤的，还是离不开面向过程思维！

因此，**面向对象可以帮助我们从宏观上把握、从整体上分析整个系统。** 但是，具体到实现部分的微观操作（就是一个个方法），仍然需要面向过程的思路去处理。

> 注意：
>
> 我们千万不要把面向过程和面向对象对立起来。他们是相辅相成的。面向对象离不开面向过程！

**类比举例1：**

![tuandui](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/tuandui.jpg)

> 当需求单一，或者简单时，我们一步步去操作没问题，并且效率也挺高。
>
> 可随着需求的更改，功能的增多，发现需要面对每一个步骤很麻烦了，这时就开始思索，**能不能把这些步骤和功能进行封装，封装时根据不同的功能，进行不同的封装，功能类似的封装在一起。**这样结构就清晰了很多。用的时候，找到对应的类就可以了。这就是面向对象的思想。

**类比举例2：人把大象装进冰箱**

- 面向过程

  ```
  1.打开冰箱
  
  2.把大象装进冰箱
  
  3.把冰箱门关住
  
  ```

  面向对象

  ```
  人{
      打开（冰箱）{
  		冰箱.开门();	
      }
      操作(大象){
               大象.进入(冰箱);
      }
      关闭(冰箱){   
            冰箱.关门();     
      }
  }
  
  冰箱{
       开门(){ }  
       关门(){ }
  }
  
  大象{
       进入(冰箱){  }
  }
  
  ```

**练习：**抽象出下面系统中的“类”及其关系

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319180839009.png" alt="image-20220319180839009" style="zoom:80%;" />

### 1.3 如何掌握这种思想？

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319180301856.png" alt="image-20220319180301856" style="zoom: 67%;" />

## 2. Java语言的基本元素：类和对象 

### 2.1 引入

人认识世界，其实就是面向对象的。比如，我们认识一下美人鱼（都没见过）

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520162643547.png" alt="image-20220520162643547" style="zoom:67%;" />

经过“仔细学习”，发现美人鱼通常具备一些特征：

- 女孩
- 有鱼尾
- 美丽

这个总结的过程，其实是`抽象化`的过程。抽象出来的美人鱼的特征，可以归纳为一个`美人鱼类`。而图片中的都是这个类呈现出来的`具体的对象`。

### 2.2 类和对象概述

`类(Class)`和`对象(Object)`是面向对象的核心概念。

**1、什么是类**

**类**：<font color='red'>**具有相同特征的事物的抽象描述，是`抽象的`、概念上的定义。**</font>

**2、什么是对象**

**对象**：<font color='red'>**是类的`一个实例`，是`具体的`。**</font>

![image-20220319210405983](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319210405983.png)

可以理解为：`类 => 抽象概念的人`；`对象 => 实实在在的某个人`

***

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/iphone13promax.jpeg" style="zoom:85%;" />

***

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503101940126.png" alt="image-20220503101940126" style="zoom:67%;" />



**3、类与对象的关系错误理解**

```
曰：“白马非马，可乎？”
曰：“可。”
曰：“何哉？”
曰：“马者，所以命形也。白者，所以命色也。命色者，非命形也，故曰白马非马。”
```

![image-20220319214213898](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319214213898.png)

### 2.3 类的成员概述

> 面向对象程序设计的重点是`类的设计`
>
> 类的设计，其实就是`类的成员的设计`

- 现实世界的生物体，大到鲸鱼，小到蚂蚁，都是由最基本的`细胞`构成的。同理，Java代码世界是由诸多个不同功能的`类`构成的。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319211527249.png" alt="image-20220319211527249" style="zoom:67%;" />

- 现实生物世界中的细胞又是由什么构成的呢？细胞核、细胞质、… 

  Java中用类class来描述事物也是如此。类，是一组相关`属性`和`行为`的集合，这也是类最基本的两个成员。

  - **属性**：该类事物的状态信息。对应类中的`成员变量`
    - **成员变量 <=> 属性 <=> Field**
  - **行为**：该类事物要做什么操作，或者基于事物的状态能做什么。对应类中的`成员方法`
    - **(成员)方法 <=> 函数 <=> Method**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/20220319_211611.jpg" alt="20220319_211611" style="zoom: 67%;" />

- 举例：

![image-20220319211859026](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319211859026.png)

### 2.4 面向对象完成功能的三步骤（重要）

#### 步骤1：类的定义

类的定义使用关键字：class。格式如下：

```java
[修饰符] class 类名{
	属性声明;
    方法声明;
}
```

举例1：

```java
public class Person{
    //声明属性age
    int age ;	                   
    
    //声明方法showAge()
    public void eat() {        
	    System.out.println("人吃饭");
    }
}

```

举例2：

```java
public class Dog{
    //声明属性
	String type; //种类
	String nickName; //昵称
	String hostName; //主人名称
	
    //声明方法
	public void eat(){ //吃东西
		System.out.println("狗狗进食");		
	}
}
```

```java
public class Person{
    String name;
    char gender;
    Dog dog;
    
    //喂宠物
    public void feed(){
        dog.eat();
    }
}
```

#### 步骤2：对象的创建

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319213201568.png" alt="image-20220319213201568" style="zoom: 50%;" />

- 创建对象，使用关键字：**new**
- 创建对象语法： 

```java
//方式1：给创建的对象命名
//把创建的对象用一个引用数据类型的变量保存起来，这样就可以反复使用这个对象了
类名 对象名 = new 类名();

//方式2：
new 类名()//也称为匿名对象

```

- 举例：

```java
class PersonTest{
	public static void main(String[] args){
		//创建Person类的对象
		Person per = new Person();
		//创建Dog类的对象
		Dog dog = new Dog();
	}
}
```

#### 步骤3：对象调用属性或方法

- 对象是类的一个**实例**，必然具备该类事物的属性和行为（即方法）。

- 使用"`对象名.属性`" 或 "`对象名.方法`"的方式访问对象成员（包括属性和方法）

举例1：

```java
//声明Animal类
public class Animal { //动物类
    public int legs;

    public void eat() {
        System.out.println("Eating.");
    }

    public void move() {
        System.out.println("Move.");
    }
}
```

```java
//声明测试类
public class AnimalTest {
    public static void main(String args[]) {
        //创建对象
        Animal xb = new Animal();
        xb.legs = 4;//访问属性
        System.out.println(xb.legs);
        xb.eat();//访问方法
        xb.move();//访问方法
    }
}
```

图示理解：![image-20220319213753668](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319213753668.png)

举例2：针对前面步骤1的举例2：类的实例化（创建类的对象）

```java
public class Game{
    public static void main(String[] args){
        Person p = new Person();
        //通过Person对象调用属性
        p.name = "康师傅";
        p.gender = '男';
        p.dog = new Dog(); //给Person对象的dog属性赋值
        
        //给Person对象的dog属性的type、nickname属性赋值
        p.dog.type = "柯基犬";
        p.dog.nickName = "小白";
        
        //通过Person对象调用方法
        p.feed();
    }
}
```

### 2.5 匿名对象 (anonymous object)

- 我们也可以不定义对象的句柄，而直接调用这个对象的方法。这样的对象叫做匿名对象。
  - 如：new Person().shout(); 

- 使用情况
  - 如果一个对象只需要进行一次方法调用，那么就可以使用匿名对象。 
  - 我们经常将匿名对象作为实参传递给一个方法调用。 

## 3. 对象的内存解析

### 3.1 JVM内存结构划分

HotSpot Java虚拟机的架构图如下。其中我们主要关心的是运行时数据区部分（Runtime Data Area）。

![JVM架构-简图](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/JVM%E6%9E%B6%E6%9E%84-%E7%AE%80%E5%9B%BE.png)

其中：

`栈（Stack）`：是指虚拟机栈。虚拟机栈用于存储局部变量等。局部变量表存放了编译期可知长度的各种基本数据类型（boolean、byte、char、short、int、float、long、double）、对象引用（reference类型，它不等同于对象本身，是对象在堆内存的首地址）。 方法执行完，自动释放。

`堆（Heap）`：此内存区域的唯一目的就是存放对象实例，几乎所有的对象实例都在这里分配内存。这一点在Java虚拟机规范中的描述是：所有的对象实例以及数组都要在堆上分配。

`方法区（Method Area）`：用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

### 3.2 对象内存解析 

举例：

```java
class Person { //类：人
    String name;
    int age;
    boolean isMale;
}

public class PersonTest { //测试类
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.name = "赵同学";
        p1.age = 20;
        p1.isMale = true;

        Person p2 = new Person();
        p2.age = 10;

        Person p3 = p1;
        p3.name = "郭同学";
    }
}
```

内存解析图：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319215723183.png" alt="image-20220319215723183" style="zoom:67%;" />

> 说明：
>
> - 堆：凡是new出来的结构(对象、数组)都放在堆空间中。
> - 对象的属性存放在堆空间中。
> - 创建一个类的多个对象（比如p1、p2），则每个对象都拥有当前类的一套"副本"（即属性）。当通过一个对象修改其属性时，不会影响其它对象此属性的值。
> - 当声明一个新的变量使用现有的对象进行赋值时（比如p3 = p1），此时并没有在堆空间中创建新的对象。而是两个变量共同指向了堆空间中同一个对象。当通过一个对象修改属性时，会影响另外一个对象对此属性的调用。

**面试题：对象名中存储的是什么呢？**

答：对象地址

```java
public class StudentTest{
    public static void main(String[] args){
        System.out.println(new Student());//Student@7852e922

        Student stu = new Student();
        System.out.println(stu);//Student@4e25154f
        
        int[] arr = new int[5];
		System.out.println(arr);//[I@70dea4e
    }
}
```

直接打印对象名和数组名都是显示“类型@对象的hashCode值"，所以说**类、数组都是引用数据类型，引用数据类型的变量中存储的是对象的地址，或者说指向堆中对象的首地址。**

![1561597909862](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1561597909862.png)

### 3.3 练习

根据代码，画出内存图

```java
class Car {
    String color = "red";
    int num = 4;

    void show() {
        System.out.println("color=" + color + ",num=" + num);
    }
}

class CarTest {
    public static void main(String[] args) {
        Car c1 = new Car();   //建立对象c1
        Car c2 = new Car();   //建立对象c2
        c1.color = "blue";   //对对象的属性进行修改
        c1.show();   //使用对象的方法
        c2.show();
    }
}
```

## 4. 类的成员之一：成员变量(field)

### 4.1 如何声明成员变量

- 语法格式：

```java
[修饰符1] class 类名{
    [修饰符2] 数据类型 成员变量名 [= 初始化值]; 
}
```

- 说明：
  - 位置要求：必须在类中，方法外
  - 修饰符2(暂不考虑)
    - 常用的权限修饰符有：private、缺省、protected、public
    - 其他修饰符：static、final 
  - 数据类型
    - 任何基本数据类型(如int、Boolean) 或 任何引用数据类型。
  - 成员变量名
    - 属于标识符，符合命名规则和规范即可。
  - 初始化值
    - 根据情况，可以显式赋值；也可以不赋值，使用默认值

示例：

```java
public class Person{
	private int age;             //声明private变量 age
	public String name = “Lila”;    //声明public变量 name
}

```

### 4.2 成员变量 vs 局部变量

**1、变量的分类：成员变量与局部变量**

- 在方法体外，类体内声明的变量称为成员变量。
- 在方法体内部等位置声明的变量称为局部变量。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220511101608038.png" alt="image-20220511101608038" style="zoom:67%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319230744617.png" alt="image-20220319230744617" style="zoom:80%;" />

> 其中，static可以将成员变量分为两大类，静态变量和非静态变量。其中静态变量又称为类变量，非静态变量又称为实例变量或者属性。接下来先学习实例变量。

**2、成员变量 与 局部变量 的对比**

- 相同点

  - 变量声明的格式相同： 数据类型  变量名 = 初始化值

   * 			变量必须先声明、后初始化、再使用。

   - 变量都有其对应的作用域。只在其作用域内是有效的

- 不同点

1、声明位置和方式
（1）实例变量：在类中方法外
（2）局部变量：在方法体{}中或方法的形参列表、代码块中

2、在内存中存储的位置不同
（1）实例变量：堆
（2）局部变量：栈

3、生命周期
（1）实例变量：和对象的生命周期一样，随着对象的创建而存在，随着对象被GC回收而消亡，
			而且每一个对象的实例变量是独立的。
（2）局部变量：和方法调用的生命周期一样，每一次方法被调用而在存在，随着方法执行的结束而消亡，
			而且每一次方法调用都是独立。

4、作用域
（1）实例变量：通过对象就可以使用，本类中直接调用，其他类中“对象.实例变量”
（2）局部变量：出了作用域就不能使用

5、修饰符（后面来讲）
（1）实例变量：public,protected,private,final,volatile,transient等
（2）局部变量：final

6、默认值
（1）实例变量：有默认值
（2）局部变量：没有，必须手动初始化。其中的形参比较特殊，靠实参给它初始化。

**3、对象属性的默认初始化赋值**

当一个对象被创建时，会对其中各种类型的成员变量自动进行初始化赋值。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319231821747.png" alt="image-20220319231821747" style="zoom:80%;" />

**4、举例**

```java
class Person {//人类
    //1.属性
    String name;//姓名
    int age = 1;//年龄
    boolean isMale;//是否是男性

    public void show(String nation) {
        //nation:局部变量
        String color;//color:局部变量
        color = "yellow";
    }
}

//测试类
class PersonTest {
    public static void main(String[] args) {
        Person p = new Person();
        p.show("CHN");
    }
}
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220319231703240.png" alt="image-20220319231703240" style="zoom:80%;" />

## 5. 类的成员之二：方法(method)

### 5.1 方法的引入

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E8%A1%97%E5%A4%B4%E9%9C%B8%E7%8E%8B&%E8%B6%85%E7%BA%A7%E7%8E%9B%E4%B8%BD.jpg"/>

《街霸》游戏中，每次人物出拳、出脚或跳跃等动作都需要编写50-80行的代码，在每次出拳、出脚或跳跃的地方都需要重复地编写这50-80行代码，这样程序会变得`很臃肿`，可读性也非常差。为了解决代码重复编写的问题，可以将出拳、出脚或跳跃的代码提取出来放在一个{}中，并为这段代码起个名字，这样在每次的出拳、出脚或跳跃的地方通过这个名字来调用这个{}的代码就可以了。

上述过程中，所提取出来的代码可以被看作是程序中定义的一个方法，程序在需要出拳、出脚或跳跃时调用该方法即可。

### 5.2 方法(method、函数)的理解

- `方法`是类或对象行为特征的抽象，用来完成某个功能操作。在某些语言中也称为`函数`或`过程`。 

- 将功能封装为方法的目的是，可以`实现代码重用，减少冗余，简化代码`

- Java里的方法`不能独立存在`，所有的方法必须定义在类里。

- 举例1：

  - Math.random()的random()方法
  - Math.sqrt(x)的sqrt(x)方法
  - System.out.println(x)的println(x)方法
  - new Scanner(System.in).nextInt()的nextInt()方法
  - Arrays类中的binarySearch()方法、sort()方法、equals()方法

- 举例2：

  ```java
  public class Person{
      private int age;
      public int getAge()  {  //声明方法getAge()
  		return age; 
      }
      public void setAge(int i) {  //声明方法setAge
  		age = i;        //将参数i的值赋给类的成员变量age
      }
  }
  
  ```

### 5.3 如何声明方法

**1、声明方法的语法格式**

```java
[修饰符] 返回值类型 方法名([形参列表])[throws 异常列表]{
        方法体的功能代码
}
```

（1）一个完整的方法 = 方法头 + 方法体。

- 方法头就是`[修饰符] 返回值类型 方法名([形参列表])[throws 异常列表]`，也称为`方法签名`。通常调用方法时只需要关注方法头就可以，从方法头可以看出这个方法的功能和调用格式。
- 方法体就是方法被调用后要执行的代码。对于调用者来说，不了解方法体如何实现的，并不影响方法的使用。

**（2）方法头可能包含5个部分**

- **修饰符**：可选的。方法的修饰符也有很多，例如：public、protected、private、static、abstract、native、final、synchronized等，后面会一一学习。
  - 其中，权限修饰符有public、protected、private。在讲封装性之前，我们先默认使用pulbic修饰方法。
  - 其中，根据是否有static，可以将方法分为静态方法和非静态方法。其中静态方法又称为类方法，非静态方法又称为实例方法。咱们在讲static前先学习实例方法。

- **返回值类型**： 表示方法运行的结果的数据类型，方法执行后将结果返回到调用者。
  - 无返回值，则声明：void
  - 有返回值，则声明出返回值类型（可以是任意类型）。与方法体中“`return 返回值`”搭配使用

- **方法名**：属于标识符，命名时遵循标识符命名规则和规范，“见名知意”

- **形参列表**：表示完成方法体功能时需要外部提供的数据列表。可以包含零个，一个或多个参数。
  - 无论是否有参数，()不能省略
  - 如果有参数，每一个参数都要指定数据类型和参数名，多个参数之间使用逗号分隔，例如：
    - 一个参数： (数据类型  参数名)
    - 二个参数： (数据类型1  参数1,  数据类型2  参数2) 
  - 参数的类型可以是基本数据类型、引用数据类型

- **throws 异常列表**：可选，在【第09章-异常处理】章节再讲

**（3）方法体**：方法体必须有{}括起来，在{}中编写完成方法功能的代码

**（4）关于方法体中return语句的说明：**

- return语句的作用是结束方法的执行，并将方法的结果返回去

- 如果返回值类型不是void，方法体中必须保证一定有 return 返回值; 语句，并且要求该返回值结果的类型与声明的返回值类型一致或兼容。
- 如果返回值类型为void时，方法体中可以没有return语句，如果要用return语句提前结束方法的执行，那么return后面不能跟返回值，直接写return ; 就可以。
- return语句后面就不能再写其他代码了，否则会报错：Unreachable code

补充：方法的分类：按照是否有形参及返回值

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320000047155.png" alt="image-20220320000047155" style="zoom: 50%;" />

**2、类比举例**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503102323689.png" alt="image-20220503102323689" style="zoom:67%;" />

**3、代码示例：**

```java
package com.atguigu.test04.method;

/**
 * 方法定义案例演示
 */
public class MethodDefineDemo {
    /**
     * 无参无返回值方法的演示
     */
    public void sayHello(){
        System.out.println("hello");
    }

    /**
     * 有参无返回值方法的演示
     * @param length int 第一个参数，表示矩形的长
     * @param width int 第二个参数，表示矩形的宽
     * @param sign char 第三个参数，表示填充矩形图形的符号
     */
    public void printRectangle(int length, int width, char sign){
        for (int i = 1; i <= length ; i++) {
            for(int j=1; j <= width; j++){
                System.out.print(sign);
            }
            System.out.println();
        }
    }

    /**
     * 无参有返回值方法的演示
     * @return
     */
    public int getIntBetweenOneToHundred(){
        return (int)(Math.random()*100+1);
    }
    
    /**
     * 有参有返回值方法的演示
     * @param a int 第一个参数，要比较大小的整数之一
     * @param b int 第二个参数，要比较大小的整数之二
     * @return int 比较大小的两个整数中较大者的值
     */
    public int max(int a, int b){
        return a > b ? a : b;
    }
}

```

### 5.4 如何调用实例方法

方法通过方法名被调用，且只有被调用才会执行。

**1、方法调用语法格式**

```java
对象.方法名([实参列表])
```

**2、示例**

举例1：

```java
package com.atguigu.test04.method;

/**
 * 方法调用案例演示
 */
public class MethodInvokeDemo {
    public static void main(String[] args) {
        //创建对象
        MethodDefineDemo md = new MethodDefineDemo();

        System.out.println("-----------------------方法调用演示-------------------------");

        //调用MethodDefineDemo类中无参无返回值的方法sayHello
        md.sayHello();
        md.sayHello();
        md.sayHello();
        //调用一次，执行一次，不调用不执行

        System.out.println("------------------------------------------------");
        //调用MethodDefineDemo类中有参无返回值的方法printRectangle
        md.printRectangle(5,10,'@');

        System.out.println("------------------------------------------------");
        //调用MethodDefineDemo类中无参有返回值的方法getIntBetweenOneToHundred
        md.getIntBetweenOneToHundred();//语法没问题，就是结果丢失

        int num = md.getIntBetweenOneToHundred();
        System.out.println("num = " + num);

        System.out.println(md.getIntBetweenOneToHundred());
        //上面的代码调用了getIntBetweenOneToHundred三次，这个方法执行了三次

        System.out.println("------------------------------------------------");
        //调用MethodDefineDemo类中有参有返回值的方法max
        md.max(3,6);//语法没问题，就是结果丢失
        
        int bigger = md.max(5,6);
        System.out.println("bigger = " + bigger);

        System.out.println("8,3中较大者是：" + md.max(8,9));
    }
}

```

举例2：

```java
//1、创建Scanner的对象
Scanner input = new Scanner(System.in);//System.in默认代表键盘输入

//2、提示输入xx
System.out.print("请输入一个整数："); //对象.非静态方法(实参列表)

//3、接收输入内容
int num = input.nextInt();  //对象.非静态方法()
```

### 5.5  使用的注意点

（1）必须先声明后使用，且方法必须定义在类的内部

（2）调用一次就执行一次，不调用不执行。

（3）方法中可以调用类中的方法或属性，不可以在方法内部定义方法。

正确示例：

```java
类{
    方法1(){
        
    }
    方法2(){
        
    }
}
```

错误示例：

```java
类{
    方法1(){
        方法2(){  //位置错误
        
   		}
    }
}
```

### 5.6 关键字return的使用

- return在方法中的作用：
  - 作用1：结束一个方法
  - 作用2：结束一个方法的同时，可以返回数据给方法的调用者 
- 注意点：在return关键字的直接后面不能声明执行语句

### 5.7 方法调用内存分析

- 方法`没有被调用`的时候，都在`方法区`中的字节码文件(.class)中存储。
- 方法`被调用`的时候，需要进入到`栈内存`中运行。方法每调用一次就会在栈中有一个`入栈`动作，即给当前方法开辟一块独立的内存区域，用于存储当前方法的局部变量的值。
- 当方法执行结束后，会释放该内存，称为`出栈`，如果方法有返回值，就会把结果返回调用处，如果没有返回值，就直接结束，回到调用处继续执行下一条指令。

- 栈结构：先进后出，后进先出。

举例分析：

```java
/**
 * @author 尚硅谷-宋红康
 * @create 9:21
 */
public class Person {
    public static void main(String[] args) {
        Person p1 = new Person();
        p1.eat();

    }
    public static void eat() {
        sleep();
        System.out.println("人：吃饭");
    }
    public static void sleep(){
        System.out.println("人：睡觉");
        doSport();
    }
    public static void doSport(){
        System.out.println("人：运动");
    }
}
```

内存分析：

![image-20220524104951449](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220524104951449.png)

### 5.8 练习													

**练习1：**创建一个Person类，其定义如下：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320001337145.png" alt="image-20220320001337145" style="zoom:67%;" />

要求：

(1)创建Person类的对象，设置该对象的name、age和sex属性，调用study方法，输出字符串“studying”，调用showAge()方法显示age值，调用addAge()方法给对象的age属性值增加2岁。
(2)创建第二个对象，执行上述操作，体会同一个类的不同对象之间的关系。

**练习2：**利用面向对象的编程方法，设计圆类Circle，包含属性（半径）和计算圆面积的方法。定义测试类，创建该Circle类的对象，并进行测试。

**练习3：**

3.1 编写程序，声明一个method方法，在方法中打印一个`10*8的*型矩形`，在main方法中调用该方法。

3.2 修改上一个程序，在method方法中，除打印一个`10*8的*型矩形`外，再计算该矩形的面积，并将其作为方法返回值。在main方法中调用该方法，接收返回的面积值并打印。

3.3 修改上一个程序，在method方法提供m和n两个参数，方法中打印一个`m*n的*型矩形`，并计算该矩形的面积， 将其作为方法返回值。在main方法中调用该方法，接收返回的面积值并打印。

**练习4：**声明一个日期类型MyDate：有属性：年year,月month，日day。创建2个日期对象，分别赋值为：你的出生日期，你对象的出生日期，并显示信息。

**练习5（课下练习）：**用面向对象的方式编写用户登录程序。

用户类：

- 属性：用户名，密码
- 方法：登录

界面类：

- 在界面类中添加main方法，接受用户输入，并调用用户类的登录方法进行验证。
  - 输出：
    - 登录失败：用户名或密码错误！
    - 登录成功：欢迎你，用户名！

参考代码：

```java
public class User {
    String name;
    String password;//密码

    /**
     * 实现用户登录的判断
     *
     * @param inputName 输入的用户名
     * @param inputPwd  输入的密码
     */
    public void login(String inputName,String inputPwd){
        if(name.equals(inputName) && password.equals(inputPwd)){
            System.out.println("登录成功：欢迎你，" + name);
        }else{
            System.out.println("登录失败：用户名或密码错误！");
        }
    }

    /**
     *  实现用户登录的判断
     * @param inputName 输入的用户名
     * @param inputPwd 输入的密码
     * @return true:登录成功  false:登录失败
     */
    public boolean login1(String inputName,String inputPwd){
//        if(name.equals(inputName) && password.equals(inputPwd)){
//            return true;
//        }else{
//            return false;
//        }

        //简化为：
        return name.equals(inputName) && password.equals(inputPwd);
    }

}
```

```java
/**
 *
 * 用户界面类UserInterface：
 *
 * - 在用户界面类中添加main方法，接受用户输入，并调用用户类的登录方法进行验证。
 * - 输出：
 *     - 登录失败：用户名或密码错误！
 *     - 登录成功：欢迎你，用户名！
 *
 * @author 尚硅谷-宋红康
 * @create 9:58
 */
public class UserInterface {
    public static void main(String[] args) {

        User u1 = new User();
        u1.name = "Tom";
        u1.password = "abc123";


        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = scanner.next();
        System.out.print("请输入密码：");
        String pwd = scanner.next();

        //演示1：
//        u1.login(name,pwd);

        //演示2：
        boolean isLogin = u1.login1(name, pwd);
        if(isLogin){
            System.out.println("登录成功：欢迎你，" + u1.name);
        }else{
            System.out.println("登录失败：用户名或密码错误！");
        }

        scanner.close();
    }
}
```

## 6. 对象数组

数组的元素可以是基本数据类型，也可以是引用数据类型。当元素是引用类型中的类时，我们称为对象数组。

**1、案例**

定义类Student，包含三个属性：学号number(int)，年级state(int)，成绩score(int)。 创建20个学生对象，学号为1到20，年级和成绩都由随机数确定。

问题一：打印出3年级(state值为3）的学生信息。

问题二：使用冒泡排序按学生成绩排序，并遍历所有学生信息

提示：

1) 生成随机数：Math.random()，返回值类型double; 

2) 四舍五入取整：Math.round(double d)，返回值类型long。

```java
/*
 * 定义类Student，包含三个属性：学号number(int)，年级state(int)，成绩score(int)。
 */
public class Student {
	
	int number;//学号
	int state;//年级
	int score;//成绩
	
	
	public void info(){
		System.out.println("number : " + number 
				+ ",state : " + state + ",score : " + score);
	}
	
}
```

```java
public class StudentTest {

	public static void main(String[] args) {

		// Student s1 = new Student();
		// s1.number = 1;
		// s1.state = (int)(Math.random() * 6 + 1);//[1,6]
		// s1.score = (int)(Math.random() * 101);//[0,100]
		//
		// Student s2 = new Student();
		// s2.number = 2;
		// s2.state = (int)(Math.random() * 6 + 1);//[1,6]
		// s2.score = (int)(Math.random() * 101);//[0,100]
		//
		// //....
		// 对象数组
		// String[] arr = new String[10];
		// 数组的创建
		Student[] students = new Student[20];
		// 通过循环结构给数组的属性赋值
		for (int i = 0; i < students.length; i++) {
			// 数组元素的赋值
			students[i] = new Student();
			// 数组元素是一个对象，给对象的各个属性赋值
			students[i].number = (i + 1);
			students[i].state = (int) (Math.random() * 6 + 1);// [1,6]
			students[i].score = (int) (Math.random() * 101);// [0,100]
		}

		// 问题一：打印出3年级(state值为3）的学生信息。
		for (int i = 0; i < students.length; i++) {

			if (students[i].state == 3) {
//				System.out.println(
//						"number:" + students[i].number + ",state:" + students[i].state + ",score:" + students[i].score);
				students[i].info();
				
			}

		}
		System.out.println("******************************");
		// 问题二：使用冒泡排序按学生成绩排序，并遍历所有学生信息
		// 排序前
		for (int i = 0; i < students.length; i++) {
//			System.out.println(
//					"number:" + students[i].number + ",state:" + 
//							students[i].state + ",score:" + students[i].score);
			
			students[i].info();
		}

		System.out.println();
		// 排序：
		for (int i = 0; i < students.length - 1; i++) {
			for (int j = 0; j < students.length - 1 - i; j++) {
				if (students[j].score > students[j + 1].score) {
					Student temp = students[j];
					students[j] = students[j + 1];
					students[j + 1] = temp;
				}
			}
		}

		// 排序后：
		for (int i = 0; i < students.length; i++) {
//			System.out.println(
//					"number:" + students[i].number + ",state:" + 
//							students[i].state + ",score:" + students[i].score);
			
			students[i].info();
		}

	}

}
```

内存解析：

![image-20220320001627445](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320001627445.png)

**2、注意点**

对象数组，首先要创建数组对象本身，即确定数组的长度，然后再创建每一个元素对象，如果不创建，数组的元素的默认值就是`null`，所以很容易出现`空指针异常NullPointerException`。

**3、练习**

（1）定义矩形类Rectangle，包含长、宽属性，area()返回矩形面积的方法，perimeter()返回矩形周长的方法，String getInfo()返回圆对象的详细信息（如：长、宽、面积、周长等数据）的方法

（2）在测试类中创建长度为3的Rectangle[]数组，用来装3个矩形对象，并给3个矩形对象的长分别赋值为10,20,30，宽分别赋值为5,15,25，遍历输出

```java
package com.atguigu.test08.array;

public class Rectangle {
    double length;
    double width;

    public double area(){//面积
        return length * width;
    }

    public double perimeter(){//周长
        return 2 * (length + width);
    }

    public String getInfo(){
        return "长：" + length +
                "，宽：" + width +
                "，面积：" + area() +
                "，周长：" + perimeter();
    }
}

```

```java
package com.atguigu.test08.array;

public class ObjectArrayTest {
    public static void main(String[] args) {
        //声明并创建一个长度为3的矩形对象数组
        Rectangle[] array = new Rectangle[3];

        //创建3个矩形对象，并为对象的实例变量赋值，
        //3个矩形对象的长分别是10,20,30
        //3个矩形对象的宽分别是5,15,25
        //调用矩形对象的getInfo()返回对象信息后输出
        for (int i = 0; i < array.length; i++) {
            //创建矩形对象
            array[i] = new Rectangle();

            //为矩形对象的成员变量赋值
            array[i].length = (i+1) * 10;
            array[i].width = (2*i+1) * 5;

            //获取并输出对象对象的信息
            System.out.println(array[i].getInfo());
        }
    }
}
```

内存解析：

![image-20211228153827819](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211228153827819.png)



## 7. 再谈方法

### 7.1 方法的重载（overload）

#### 7.1.1 概念及特点

- **方法重载**：<font color='red'>在同一个类中，允许存在一个以上的同名方法，只要它们的参数列表不同即可。</font>
  - 参数列表不同，意味着参数个数或参数类型的不同
- **重载的特点**：<font color='red'>与修饰符、返回值类型无关，只看参数列表，且参数列表必须不同。</font>(参数个数或参数类型)。调用时，根据方法参数列表的不同来区别。
- **重载方法调用**：JVM通过方法的参数列表，调用匹配的方法。
  - 先找个数、类型最匹配的
  - 再找个数和类型可以兼容的，如果同时多个方法可以兼容将会报错

#### 7.1.2多态和重载的区别

多态和重载是面向对象编程中的两个重要概念，它们的区别在于==多态是指同一种操作作用于不同的对象上面，可以产生不同的执行结果==，==而重载是指在同一个类中可以定义多个同名的方法，但这些方法的参数列表不同。简单来说，多态是运行时的概念，而重载是编译时的概念==。

#### 7.1.3 示例

举例1：

```java
//System.out.println()方法就是典型的重载方法，其内部的声明形式如下：
public class PrintStream {
    public void println(byte x)
	public void println(short x)
	public void println(int x)
	public void println(long x)
	public void println(float x)
	public void println(double x)
	public void println(char x)
	public void println(double x)
	public void println()

}

public class HelloWorld{
    public static void main(String[] args) {
        System.out.println(3);
        System.out.println(1.2f);
        System.out.println("hello!");
    }
}

```

面试题：

下面代码的输出结果是什么：

```java
public static void main(String[] args) {

    int[] number = new int[]{1,2,3,4,5,6};
    System.out.println(number);

    char[] charNum = new char[]{'1','2','3','4','5','6'};
    System.out.println(charNum);

    boolean[] booleans = new boolean[]{true,true,false,false};
    System.out.println(booleans);
}
```

​	答案：

> 1. 地址值
> 2. 123456
> 3. 地址值

> 因为`System.out.println()`中的众多重载方法有如下两个：
>
> ```java
> public void println(Object x)
> public void println(char[] x)    
> ```
>
> 当是`char`类型的数组时就直接遍历输出，而其他类型都为`Object`类型

![image-20230315162546445](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230315162546445.png)

举例2：

```java
//返回两个整数的和
public int add(int x,int y){
    return x+y;
}

//返回三个整数的和
public int add(int x,int y,int z){
    return x+y+z;
}
//返回两个小数的和
public double add(double x,double y){
    return x+y;
}

```

​	举例3：<font color='red'>方法的重载和返回值类型无关</font>

```java
public class MathTools {
    //以下方法不是重载，会报错
    public int getOneToHundred(){
    	return (int)(Math.random()*100);
    }
    
    public double getOneToHundred(){
    	return Math.random()*100;
    }
}

```

#### 7.1.4 练习

**练习1：**判 断与`void show(int a,char b,double c){}`构成重载的有：

```java
a)void show(int x,char y,double z){}     // no

b)int show(int a,double c,char b){}      // yes

c) void show(int a,double c,char b){}    // yes

d) boolean show(int c,char b){}          // yes

e) void show(double c){}                 // yes

f) double show(int x,char y,double z){}  // no

g) void shows(){double c}                // no
```



### 7.2 可变个数的形参

在**JDK 5.0 **中提供了**Varargs(**variable number of arguments**)**机制。即当定义一个方法时，形参的类型可以确定，但是形参的个数不确定，那么可以考虑使用可变个数的形参。

**格式：**

```
方法名(参数的类型名... 参数名)
```

**举例：**

```java
//JDK 5.0以前：采用数组形参来定义方法，传入多个同一类型变量
public static void test(int a ,String[] books);

//JDK5.0：采用可变个数形参来定义方法，传入多个同一类型变量
public static void test(int a ,String... books);

```

**特点：**

1. <font color='red'>方法参数部分指定类型的参数个数是可变多个：0个，1个或多个</font>

2. <font color='red'>可变个数形参的方法与同名的方法之间，彼此构成重载</font>

3. <font color='red'>可变参数方法的使用与方法参数部分使用数组是一致的，二者不能同时声明，否则报错。</font>

4. <font color='red'>方法的参数部分有可变形参，需要放在形参声明的最后</font>

5. <font color='red'>在一个方法的形参中，最多只能声明一个可变个数的形参</font>

简单实现：

```java
public static void main(String[] args) {
    num1(1,2,3,4);
}

public static void num1(int... number){
    for (int i = 0; i < number.length; i++) {
        System.out.println(number[i]);
    }
}
```



**案例分析：** 

**案例1：**n个字符串进行拼接，每一个字符串之间使用某字符进行分割，如果没有传入字符串，那么返回空字符串""

```java
public class StringTools {
    String concat(char seperator, String... args){
        String str = "";
        for (int i = 0; i < args.length; i++) {
            if(i==0){
                str += args[i];
            }else{
                str += seperator + args[i];
            }
        }
        return str;
    }
}
```

```java
package com.atguigu.test05.param;

public class StringToolsTest {
    public static void main(String[] args) {
        StringTools tools = new StringTools();

        System.out.println(tools.concat('-'));
        System.out.println(tools.concat('-',"hello"));
        System.out.println(tools.concat('-',"hello","world"));
        System.out.println(tools.concat('-',"hello","world","java"));
    }
}
```

**案例2**：求n个整数的和

```java
public class NumberTools {
    public int total(int[] nums){
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return sum;
    }

    public int sum(int... nums){
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return sum;
    }
}
```

```java
public class TestVarParam {
    public static void main(String[] args) {
        NumberTools tools = new NumberTools();

        System.out.println(tools.sum());//0个实参
        System.out.println(tools.sum(5));//1个实参
        System.out.println(tools.sum(5,6,2,4));//4个实参
        System.out.println(tools.sum(new int[]{5,6,2,4}));//传入数组实参

        System.out.println("------------------------------------");
        System.out.println(tools.total(new int[]{}));//0个元素的数组
        System.out.println(tools.total(new int[]{5}));//1个元素的数组
        System.out.println(tools.total(new int[]{5,6,2,4}));//传入数组实参
    }
}
```

**案例3**：如下的方法彼此构成重载

```java
public class MathTools {
    //求两个整数的最大值
    public int max(int a,int b){
        return a>b?a:b;
    }

    //求两个小数的最大值
    public double max(double a, double b){
        return a>b?a:b;
    }

    //求三个整数的最大值
    public int max(int a, int b, int c){
        return max(max(a,b),c);
    }
    
    //求n个整数的最大值
    public int max(int... nums){
        int max = nums[0];//如果没有传入整数，或者传入null，这句代码会报异常
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] > max){
                max = nums[i];
            }
        }
        return max;
    }
    /*    //求n整数的最大值
    public int max(int[] nums){  //编译就报错，与(int... nums)无法区分
        int max = nums[0];//如果没有传入整数，或者传入null，这句代码会报异常
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] > max){
                max = nums[i];
            }
        }
        return max;
    }*/

/*    //求n整数的最大值
    public int max(int first, int... nums){  //当前类不报错，但是调用时会引起多个方法同时匹配
        int max = first;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] > max){
                max = nums[i];
            }
        }
        return max;
    }*/
}
```

### 7.3 方法的参数传递机制

#### 7.3.1 形参和实参

* **形参（formal parameter）**：<font color='red'>在定义方法时，方法名后面括号()中声明的变量称为形式参数，简称形参</font>。
* **实参（actual parameter）**：<font color='red'>在调用方法时，方法名后面括号()中的使用的值/变量/表达式称为实际参数，简称实参</font>。

#### 7.3.2 参数传递机制：值传递

Java里方法的参数传递方式只有一种：`值传递`。 即将实际参数值的副本（复制品）传入方法内，而参数本身不受影响。 

- ==**形参是基本数据类型：将实参基本数据类型变量的“<font color='red'>数据值</font>”传递给形参**==

- ==**形参是引用数据类型：将实参引用数据类型变量的“<font color='red'>地址值</font>”传递给形参**==

#### 7.3.3 举例

**1、形参是基本数据类型**

案例：编写方法，交换两个整型变量的值

```java
public class Test {
	public static void main(String[] args) {
		int m = 10;
		int n = 20;
		
		System.out.println("m = " + m + ", n = " + n);
		//交换m和n的值
//		int temp = m;
//		m = n;
//		n = temp;
		
		ValueTransferTest1 test = new ValueTransferTest1();
		test.swap(m, n);
		
		System.out.println("m = " + m + ", n = " + n);
	}
	
	public void swap(int m,int n){
		int temp = m;
		m = n;
		n = temp;
	}
	
}
```

内存解析：

![image-20221120153322455](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221120153322455.png)

**2、形参是引用数据类型**

```java
public class Test {
	public static void main(String[] args) {
		
		Data d1 = new Data();
		d1.m = 10;
		d1.n = 20;
		
		System.out.println("m = " + d1.m + ", n = " + d1.n);
		
		//实现 换序
		
		ValueTransferTest2 test = new ValueTransferTest2();
		test.swap(d1);
		
		System.out.println("m = " + d1.m + ", n = " + d1.n);
		
	}
	
	public void swap(Data data){
		int temp = data.m;
		data.m = data.n;
		data.n = temp;
	}
}
```

```java
class Data{
	int m;
	int n;
}
```

内存解析：

![image-20221120153442447](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221120153442447.png)

#### 7.3.4 练习

练习1：判断如下程序输出的结果

```java
public class AssignNewObject {
    public void swap(MyData my){
        my = new MyData(); //考虑堆空间此新创建的对象，和main中的data对象是否有关
        int temp = my.x;
        my.x = my.y;
        my.y = temp;
     
    }

    public static void main(String[] args) {
        AssignNewObject tools = new AssignNewObject();
        
        MyData data = new MyData();
        data.x = 1;
        data.y = 2;
        System.out.println("交换之前：x = " + data.x +",y = " + data.y);//
        tools.swap(data);//调用完之后，x与y的值交换？
        System.out.println("交换之后：x = " + data.x +",y = " + data.y);//
    }
}

class MyData{
    int x ;
    int y;
}
```

练习2：如下操作是否可以实现数组排序

```java
public class ArrayTypeParam {

    //冒泡排序，实现数组从小到大排序
    public void sort(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
    //打印数组的元素
    public void print(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayTypeParam tools = new ArrayTypeParam();

        int[] nums = {4,3,1,6,7};
        System.out.println("排序之前：");
        tools.print(nums);

        tools.sort(nums);//对nums数组进行排序

        System.out.println("排序之后：");
        tools.print(nums);//输出nums数组的元素

    }
}
```

练习3：通过内存结构图，写出如下程序的输出结果

```java
//栈：每个方法在调用时，都会有以栈帧的方法压入栈中。栈帧中保存了当前方法中声明的变量：方法内声明的，形参
//堆：存放new出来的"东西"：对象（成员变量在对象中）、数组实体（数组元素）。 
//注意：变量前如果声明有类型，那么这就是一个新的刚要定义的变量。如果变量前没有声明类型，那就说明此变量在之前已经声明过。
public class TransferTest3 {
    public static void main(String args[]) {
        TransferTest3 test = new TransferTest3();
        test.first();
    }
    public void first() {
        int i = 5;
        Value v = new Value();
        v.i = 25;
        second(v, i);
        System.out.println(v.i);
    }
    public void second(Value v, int i) {
        i = 0;
        v.i = 20;
        Value val = new Value();
        v = val;
        System.out.println(v.i + " " + i);
    }
}

class Value {
    int i = 15;
}

```

内存解析：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/20220320_233145.jpg" alt="20220320_233145" style="zoom:80%;" />

练习4：`貌似是`考查方法的参数传递

![image-20220320233402122](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320233402122.png)

```java
	//法一：
    public static void method(int a, int b) {
        // 在不改变原本题目的前提下，如何写这个函数才能在main函数中输出a=100，b=200？ 
        a = a * 10;
        b = b * 20;
        System.out.println(a);
        System.out.println(b);
        System.exit(0);
    }

    //法二：
    public static void method(int a, int b) {

        PrintStream ps = new PrintStream(System.out) {
            @Override
            public void println(String x) {

                if ("a=10".equals(x)) {
                    x = "a=100";
                } else if ("b=10".equals(x)) {
                    x = "b=200";
                }
                super.println(x);
            }
        };

        System.setOut(ps);

    }
```

练习5：将对象作为参数传递给方法

（1）定义一个Circle类，包含一个double型的radius属性代表圆的半径，一个findArea()方法返回圆的面积。
（2）定义一个类PassObject，在类中定义一个方法printAreas()，该方法的定义如下：public void printAreas(Circle c, int time)，在printAreas方法中打印输出1到time之间的每个整数半径值，以及对应的面积。例如，times为5，则输出半径1，2，3，4，5，以及对应的圆面积。
（3）在main方法中调用printAreas()方法，调用完毕后输出当前半径值。程序运行结果如图所示。

![image-20220320233626003](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320233626003.png)

### 7.4 递归(recursion)方法

**递归方法调用**：**<font color='red'>方法自己调用自己的现象就称为递归。</font>**

举例1：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220521160246138.png" alt="image-20220521160246138" style="zoom: 67%;" />

举例2：

```ini
从前有座山,山上有座庙,庙里有个老和尚,老和尚在给小和尚讲故事,讲的啥?
      从前有座山,山上有座庙,庙里有个老和尚,老和尚在给小和尚讲故事,讲的啥?
          从前有座山,山上有座庙,庙里有个老和尚,老和尚在给小和尚讲故事,讲的啥?
              从前有座山,山上有座庙,庙里有个老和尚,老和尚在给小和尚讲故事,讲的啥?...
    			...
老和尚没了,庙塌了,小和尚还俗结婚了。
```

**递归的分类:**直接递归、间接递归。

* **直接递归**：方法自身调用自己。

  ```java
  public void methodA(){
  	methodA();
  }
  ```

* **间接递归**：可以理解为A()方法调用B()方法，B()方法调用C()方法，C()方法调用A()方法。

  ```java
  public static void A(){
  	B();
  }
  
  public static void B(){
  	C();
  }
  
  public static void C(){
  	A();
  }
  ```

**说明**：

- 递归方法包含了一种`隐式的循环`。
- 递归方法会`重复执行`某段代码，但这种重复执行无须循环控制。
- 递归一定要向`已知方向`递归，否则这种递归就变成了无穷递归，停不下来，类似于`死循环`。最终发生`栈内存溢出`。

**举例：**

举例1：计算1 ~ n的和

```java
public class RecursionDemo {
	public static void main(String[] args) {
        RecursionDemo demo = new RecursionDemo();
		//计算1~num的和，使用递归完成
		int num = 5;
      	// 调用求和的方法
		int sum = demo.getSum(num);
      	// 输出结果
		System.out.println(sum);
		
	}
  	/*
  	  通过递归算法实现.
  	  参数列表:int 
  	  返回值类型: int 
  	*/
	public int getSum(int num) {
      	/* 
      	   num为1时,方法返回1,
      	   相当于是方法的出口,num总有是1的情况
      	*/
		if(num == 1){
			return 1;
		}
      	/*
          num不为1时,方法返回 num +(num-1)的累和
          递归调用getSum方法
        */
		return num + getSum(num-1);
	}
}
```

代码执行图解：

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E9%80%92%E5%BD%92.png)

举例2：递归方法计算n!

```java
public class arrayTwo {

    public static void main(String[] args) {
        arrayTwo arrayTwo = new arrayTwo();
        int result = arrayTwo.jieCheng(5);
        System.out.println(result);
    }
    /**
     * 计算一个数字的阶乘
     *
     * @return int
     */
    public int jieCheng(int number){
        if (number == 1){
            return 1;
        }else {
            return number * jieCheng(number - 1);
        }
    }
} 
```

![image-20220520150018655](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520150018655.png)

举例3：已知有一个数列：f(0) = 1，f(1) = 4，f(n+2)=2*f(n+1) + f(n)，其中n是大于0的整数，求f(10)的值。

```java
public int f(int num){
	if(num == 0){
		return 1;
	}else if(num == 1){
		return 4;
	}else{
		return 2 * f(num - 1) + f(num - 2);
	}
}
```

举例4：已知一个数列：f(20) = 1,f(21) = 4,f(n+2) = 2*f(n+1)+f(n)，其中n是大于0的整数，求f(10)的值。

```java
public int func(int num){
	if(num == 20){
		return 1;
	}else if(num == 21){
		return 4;
	}else{
		return func(num + 2) - 2 * func(num + 1);
	}
}
```

举例5：计算斐波那契数列（Fibonacci）的第n个值，斐波那契数列满足如下规律，

```java
1,1,2,3,5,8,13,21,34,55,....
```

即从第三个数开始，一个数等于前两个数之和。假设f(n)代表斐波那契数列的第n个值，那么f(n)满足：
f(n) = f(n-2) + f(n-1); 

```java
public class arrayTwo {

    public static void main(String[] args) {
        arrayTwo arrayTwo = new arrayTwo();
        int result = arrayTwo.feiBoNaQie(5);
        System.out.println(result);
    }

    public int feiBoNaQie(int number) {
        if (number == 1 || number == 2) {
            return 1;
        } else {
            return feiBoNaQie(number - 1) + feiBoNaQie(number - 2);
        }
    }
}
/*
* feiBoNaQie(5)(5) = feiBoNaQie(4)(3) + feiBoNaQie(3)(2)
* feiBoNaQie(4)(3) = feiBoNaQie(3)(2) + feiBoNaQie(2)(1)
* feiBoNaQie(3)(2) = feiBoNaQie(2)(1) + feiBoNaQie(1) (1)
* feiBoNaQie(3)(2) = feiBoNaQie(2)(1) + feiBoNaQie(1) (1)
 * */
```



举例6：面试题

> 宋老师，我今天去百度面试，遇到一个一个双重递归调用的问题，我琢磨了一下，完全不知道为什么。打断点了，也还是没看懂为什么程序会那样走。您有空可以看一下，求指教。

![image-20220320235105406](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320235105406.png)

```java
	private int count = 0;

    public int recursion(int k) {
        count++;
        System.out.println("count1:" + count + "  k:" + k);
        if (k <= 0) {
            return 0;
        }
        return recursion(k - 1) + recursion(k - 2);//287
        //return recursion(k - 1);//11
        //return recursion(k - 1) + recursion(k - 1);//2047
    }
```

剖析：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220320235229941.png" alt="image-20220320235229941" style="zoom:67%;" />

> 最后说两句：
>
> 1. 递归调用会占用大量的系统堆栈，内存耗用多，在递归调用层次多时速度要比循环`慢的多`，所以在使用递归时要慎重。
>
> 2. 在要求高性能的情况下尽量避免使用递归，递归调用既花时间又`耗内存`。考虑使用循环迭代

## 8. 关键字：package、import

### 8.1 package(包)

package，称为包，用于指明该文件中定义的类、接口等结构所在的包。

#### 8.1.1 语法格式

```java
package 顶层包名.子包名 ;
```

举例：pack1\pack2\PackageTest.java

```java
package pack1.pack2;    //指定类PackageTest属于包pack1.pack2

public class PackageTest{
	public void display(){
		System.out.println("in  method display()");
	}
}
```

说明：

- 一个源文件只能有一个声明包的package语句
- package语句作为Java源文件的第一条语句出现。若缺省该语句，则指定为无名包。
- 包名，属于标识符，满足标识符命名的规则和规范（全部小写）、见名知意
  - 包通常使用所在公司域名的倒置：com.atguigu.xxx。
  - 大家取包名时不要使用"`java.xx`"包
- 包对应于文件系统的目录，package语句中用 “.” 来指明包(目录)的层次，每.一次就表示一层文件目录。
- 同一个包下可以声明多个结构（类、接口），但是不能定义同名的结构（类、接口）。不同的包下可以定义同名的结构（类、接口）

#### 8.1.2 包的作用

- 包可以包含类和子包，划分`项目层次`，便于管理
- 帮助`管理大型软件`系统：将功能相近的类划分到同一个包中。比如：MVC的设计模式
- 解决`类命名冲突`的问题
- 控制`访问权限`

#### 8.1.3 应用举例

举例1：某航运软件系统包括：一组域对象、GUI和reports子系统

![image-20220321233025157](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220321233025157.png)

**举例2：MVC设计模式**

MVC是一种软件构件模式，目的是为了降低程序开发中代码业务的耦合度。

MVC设计模式将整个程序分为三个层次：`视图模型(Viewer)层`，`控制器(Controller)层`，与`数据模型(Model)层`。这种将程序输入输出、数据处理，以及数据的展示分离开来的设计模式使程序结构变的灵活而且清晰，同时也描述了程序各个对象间的通信方式，降低了程序的耦合性。

```java
视图层viewer：显示数据,为用户提供使用界面，与用户直接进行交互。
 >相关工具类   view.utils
 >自定义view  view.ui

控制层controller：解析用户请求，处理业务逻辑，给予用户响应
 >应用界面相关    controller.activity
 >存放fragment   controller.fragment
 >显示列表的适配器 controller.adapter
 >服务相关的        controller.service
 >抽取的基类        controller.base
    
模型层model：主要承载数据、处理数据
 >数据对象封装 model.bean/domain
 >数据库操作类 model.dao
 >数据库      model.db

```

![image-20220321233153448](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220321233153448.png)

#### 8.1.4 JDK中主要的包介绍

`java.lang`----包含一些Java语言的核心类，如String、Math、Integer、 System和Thread，提供常用功能
		 `java.net`----包含执行与网络相关的操作的类和接口。
		 `java.io`   ----包含能提供多种输入/输出功能的类。
		 `java.util`----包含一些实用工具类，如定义系统特性、接口的集合框架类、使用与日期日历相关的函数。
		 `java.text`----包含了一些java格式化相关的类
		 `java.sql`----包含了java进行JDBC数据库编程的相关类/接口
		 `java.awt`----包含了构成抽象窗口工具集（abstract window toolkits）的多个类，这些类被用来构建和管理应用程序的图形用户界面(GUI)。  

### 8.2 import(导入)	

为了使用定义在其它包中的Java类，需用import语句来显式引入指定包下所需要的类。相当于`import语句告诉编译器到哪里去寻找这个类`。

#### 8.2.1 语法格式

```
import 包名.类名;
```

#### 8.2.2 应用举例

```java
import pack1.pack2.Test;   //import pack1.pack2.*;表示引入pack1.pack2包中的所有结构

public class PackTest{
	public static void main(String args[]){
		Test t = new Test();          //Test类在pack1.pack2包中定义
		t.display();
	}
}
```

#### 8.2.3 注意事项

- import语句，声明在包的声明和类的声明之间。

- 如果需要导入多个类或接口，那么就并列显式多个import语句即可

- 如果使用`a.*`导入结构，表示可以导入a包下的所有的结构。举例：可以使用java.util.*的方式，一次性导入util包下所有的类或接口。

- 如果导入的类或接口是java.lang包下的，或者是当前包下的，则可以省略此import语句。

- 如果已经导入java.a包下的类，那么如果需要使用a包的子包下的类的话，仍然需要导入。

- 如果在代码中使用不同包下的同名的类，那么就需要使用类的全类名的方式指明调用的是哪个类。

- （了解）`import static`组合的使用：调用指定类或接口下的静态的属性或方法

## 9. 面向对象特征一：封装性(encapsulation)

### 9.1 为什么需要封装？

* 我要用洗衣机，只需要按一下开关和洗涤模式就可以了。有必要了解洗衣机内部的结构吗？有必要碰电动机吗？
* 我要开车，我不需要懂离合、油门、制动等原理和维修也可以驾驶。

- 客观世界里每一个事物的内部信息都隐藏在其内部，外界无法直接操作和修改，只能通过指定的方式进行访问和修改。

随着我们系统越来越复杂，类会越来越多，那么类之间的访问边界必须把握好，面向对象的开发原则要遵循“`高内聚、低耦合`”。

> 高内聚、低耦合是软件工程中的概念，也是UNIX 操作系统设计的经典原则。
>
> 内聚，**<font color='red'>指一个模块内各个元素彼此结合的紧密程度</font>**；**<font color='red'>耦合指一个软件结构内不同模块之间的关联程度</font>**。内聚意味着重用和独立，耦合意味着多米诺效应牵一发动全身。

而“高内聚，低耦合”的体现之一：

* `高内聚`：类的内部数据操作细节自己完成，不允许外部干涉；
* `低耦合`：仅暴露少量的方法给外部使用，尽量方便外部调用。

### 9.2 何为封装性？

封装，**<font color='red'>使用权限修饰符来修饰类或者类的内部成员，当类或类的内部成员被调用时，体现了可见性的大小。java一共提供了4中权限修饰，分别为`public`、`protected`、`缺省`、`private`</font>**

**通俗的讲，把该隐藏的隐藏起来，该暴露的暴露出来。这就是封装性的设计思想。**

### 9.3 Java如何实现数据封装

- ==实现封装就是控制类或类的内部成员的可见性范围。这就需要依赖访问控制修饰符，也称为权限修饰符来控制。==


- 权限修饰符：`public`、`protected`、`缺省`、`private`。具体访问范围如下：


| 修饰符    | 本类内部 | 本包内 | 其他包的子类 | 其他包非子类 |
| --------- | -------- | ------ | ------------ | ------------ |
| private   | √        | ×      | ×            | ×            |
| 缺省      | √        | √      | ×            | ×            |
| protected | √        | √      | √            | ×            |
| public    | √        | √      | √            | √            |

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220321222327616.png" alt="image-20220321222327616" style="zoom:67%;" />

- **具体修饰的结构**：
  - **外部类**：==public、缺省==
  - **成员变量、成员方法、构造器、成员内部类**：==public、protected、缺省、private==



### 9.4 封装性的体现

* ==私有化类的属性，提供公共的get和set方法，对此属性进行获取或修改。==
* ==将类中不需要对外暴露的方法设置为private。==
* ==单例模式中构造器private的了，避免载类的外部创建实例。==

**①** 使用 `private` 修饰成员变量

```java
private 数据类型 变量名 ；
```

代码如下：

```java
public class Person {
    private String name;
  	private int age;
    private boolean marry;
}
```

**②** 提供 `getXxx`方法 / `setXxx` 方法，可以访问成员变量，代码如下：

```java
public class Person {
    private String name;
  	private int age;
    private boolean marry;

	public void setName(String n) {
		name = n;
    }

    public String getName() {
        return name;
	}

    public void setAge(int a) {
        age = a;
    }

    public int getAge() {
        return age;
    }
    
    public void setMarry(boolean m){
        marry = m;
    }
    
    public boolean isMarry(){
        return marry;
    }
}
```

**③** 测试：

```java
public class PersonTest {
    public static void main(String[] args) {
        Person p = new Person();

        //实例变量私有化，跨类是无法直接使用的
		/* p.name = "张三";
        p.age = 23;
        p.marry = true;*/

        p.setName("张三");
        System.out.println("p.name = " + p.getName());

        p.setAge(23);
        System.out.println("p.age = " + p.getAge());

        p.setMarry(true);
        System.out.println("p.marry = " + p.isMarry());
    }
}
```

### 9.5**封装的好处：**

* 让使用者只能通过事先预定的方法来`访问数据`，从而可以在该方法里面加入控制逻辑，限制对成员变量的不合理访问。还可以进行数据检查，从而有利于保证对象信息的完整性。
* `便于修改`，提高代码的可维护性。主要说的是隐藏的部分，在内部修改了，如果其对外可以的访问方式不变的话，外部根本感觉不到它的修改。例如：Java8->Java9，String从char[]转为byte[]内部实现，而对外的方法不变，我们使用者根本感觉不到它内部的修改。

> 注意：
>
> 开发中，一般成员实例变量都习惯使用private修饰，再提供相应的public权限的get/set方法访问。
>
> 对于final的实例变量，不提供set()方法。（后面final关键字的时候讲）
>
> 对于static final的成员变量，习惯上使用public修饰。



## 10. 类的成员之三：构造器(Constructor)

我们new完对象时，所有成员变量都是默认值，如果我们需要赋别的值，需要挨个为它们再赋值，太麻烦了。我们能不能在new对象时，直接为当前对象的某个或所有成员变量直接赋值呢？

可以，Java给我们提供了`构造器（Constructor)`，也称为`构造方法`。

### 10.1 构造器的作用

==**搭配new关键字，创建类的对象，并在new对象的时候为对象实例赋值。**==

举例：Person p = new `Person(“Peter”,15)`;

解释：如同我们规定每个“人”一出生就必须先洗澡，我们就可以在“人”的构造器中加入完成“洗澡”的程序代码，于是每个“人”一出生就会自动完成“洗澡”，程序就不必再在每个人刚出生时一个一个地告诉他们要“洗澡”了。

### 10.2 构造器的语法格式

```java
[修饰符] class 类名{
    [修饰符] 构造器名(){
    	// 实例初始化代码
    }
    [修饰符] 构造器名(参数列表){
        // 实例初始化代码
    }
}
```

说明：

1. <font color='red'>构造器名必须与它所在的类名必须相同。</font>
2. <font color='red'>它没有返回值，所以不需要返回值类型，也不需要void。</font>
3. <font color='red'>构造器的修饰符只能是权限修饰符，不能被其他任何修饰。比如，不能被static、final、synchronized、abstract、native修饰，不能有return语句返回值。</font>

代码如下：

```java
public class Student {
    private String name;
    private int age;

    // 无参构造
    public Student() {}

    // 有参构造
    public Student(String n,int a) {
        name = n;
        age = a;
    }

    public String getName() {
        return name;
    }
    public void setName(String n) {
        name = n;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int a) {
        age = a;
    }

    public String getInfo(){
        return "姓名：" + name +"，年龄：" + age;
    }
}

```

```java
public class TestStudent {
    public static void main(String[] args) {
        //调用无参构造创建学生对象
        Student s1 = new Student();

        //调用有参构造创建学生对象
        Student s2 = new Student("张三",23);

        System.out.println(s1.getInfo());
        System.out.println(s2.getInfo());
    }
}
```

### 10.3 使用说明

1. ==<font color='red'>当我们没有显式的声明类中的构造器时，系统会默认提供一个无参的构造器并且该构造器的修饰符默认与类的修饰符相同</font>==

   查看编译后的字节码文件，默认创建了空参构造器。

   ![image-20230316090100386](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230316090100386.png)

2. <font color='red'>当我们显式的定义类的构造器以后，系统就不再提供默认的无参的构造器了。</font>

3. <font color='red'>在类中，至少会存在一个构造器。</font>

4. <font color='red'>构造器是可以重载的。</font>

### 10.4 练习

**练习1：**编写两个类，TriAngle和TriAngleTest，其中TriAngle类中声明私有的底边长base和高height，同时声明公共方法访问私有变量。此外，提供类必要的构造器。另一个类中使用这些公共方法，计算三角形的面积。

**练习2：**

(1)定义Student类,有4个属性：
  String name; 
  int age; 
  String school; 
  String major;

(2)定义Student类的3个构造器:

- 第一个构造器Student(String n, int a)设置类的name和age属性；
- 第二个构造器Student(String n, int a, String s)设置类的name, age 和school属性；
- 第三个构造器Student(String n, int a, String s, String m)设置类的name, age ,school和major属性；

(3)在main方法中分别调用不同的构造器创建的对象，并输出其属性值。

**练习3：**

1、写一个名为Account的类模拟账户。该类的属性和方法如下图所示。

该类包括的属性：账号id，余额balance，年利率annualInterestRate；

包含的方法：访问器方法（getter和setter方法），取款方法withdraw()，存款方法deposit()。

 ![image-20220321235241752](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220321235241752.png)

**提示：**在提款方法withdraw中，需要判断用户余额是否能够满足提款数额的要求，如果不能，应给出提示。

2. 创建Customer类。

 ![image-20220321235324156](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220321235324156.png)

a. 声明三个私有对象属性：firstName、lastName和account。
b. 声明一个公有构造器，这个构造器带有两个代表对象属性的参数（f和l）
c. 声明两个公有存取器来访问该对象属性，方法getFirstName和getLastName返回相应的属性。
d. 声明setAccount 方法来对account属性赋值。
e. 声明getAccount 方法以获取account属性。

3.写一个测试程序。

（1）创建一个Customer ，名字叫 Jane Smith, 他有一个账号为1000，余额为2000元，年利率为 1.23％ 的账户。
（2）对Jane Smith操作。
存入 100 元，再取出960元。再取出2000元。
打印出Jane Smith 的基本信息

```
成功存入 ：100.0
成功取出：960.0
余额不足，取款失败
Customer [Smith, Jane] has a account: id is 1000, annualInterestRate is 1.23％, balance is 1140.0
```

## 11. 阶段性知识补充

### 11.1 类中属性赋值过程

**1、在类的属性中，可以有哪些位置给属性赋值？**

① 默认初始化

② 显式初始化

③ 构造器中初始化

④ 通过"对象.属性"或"对象.方法"的方式，给属性赋值



**2、这些位置执行的先后顺序是怎样？**

顺序：① - ② - ③ - ④

3、说明：

- 上述中的①、②、③在对象创建过程中，只执行一次。

 *    ④ 是在对象创建后执行的，可以根据需求多次执行。

### 11.2 JavaBean

- JavaBean是一种Java语言写成的可重用组件。

  - 好比你做了一个扳手，这个扳手会在很多地方被拿去用。这个扳手也提供多种功能(你可以拿这个扳手扳、锤、撬等等)，而这个扳手就是一个组件。

- 所谓JavaBean，是指符合如下标准的Java类：

  - 类是公共的
  - 有一个无参的公共的构造器
  - 有属性，且有对应的get、set方法

- 用户可以使用JavaBean将功能、处理、值、数据库访问和其他任何可以用Java代码创造的对象进行打包，并且其他的开发者可以通过内部的JSP页面、Servlet、其他JavaBean、applet程序或者应用来使用这些对象。用户可以认为JavaBean提供了一种随时随地的复制和粘贴的功能，而不用关心任何改变。

- 《Think in Java》中提到，JavaBean最初是为Java GUI的可视化编程实现的。你拖动IDE构建工具创建一个GUI 组件（如多选框），其实是工具给你创建Java类，并提供将类的属性暴露出来给你修改调整，将事件监听器暴露出来。

- 示例

  ```java
  public class JavaBean {
      private String name; // 属性一般定义为private
      private int age;
      public JavaBean() {
      }
      public int getAge() {
          return age;
      }
      public void setAge(int a) {
          age = a;
      }
      public String getName() {
          return name;
      }
      public void setName(String n) {
          name = n;
      }
  }
  
  ```

### 11.3 UML类图

- UML（Unified Modeling Language，==统一建模语言==），用来描述`软件模型`和`架构`的图形化语言。

- 常用的UML工具软件有`PowerDesinger`、`Rose`和`Enterprise Architect`。

- UML工具软件不仅可以绘制软件开发中所需的各种图表，还可以生成对应的源代码。

- 在软件开发中，使用`UML类图`可以更加直观地描述类内部结构（类的属性和操作）以及类之间的关系（如关联、依赖、聚合等）。
  - +表示 public 类型， - 表示 private 类型，#表示protected类型
  - 方法的写法: 
    方法的类型(+、-)  方法名(参数名： 参数类型)：返回值类型
  - 斜体表示抽象方法或类。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220321232230831.png" alt="image-20220321232230831" style="zoom:80%;" />

![image-20220503111301602](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503111301602.png)

## 12.随堂复习

### 1. （了解）面向过程 vs 面向对象

- 不管是面向过程、面向对象，都是程序设计的思路。
- 面向过程：以函数为基本单位，适合解决简单问题。比如：开车
- 面向对象：以类为基本单位，适合解决复杂问题。比如：造车

### 2. 类、对象

- 类：抽象的，概念上的定义
- 对象：具体的，类的一个一个的实例。
- 面向对象完成具体功能的操作的三步流程（非常重要）

```
步骤1：创建类，并设计类的内部成员（属性、方法）
步骤2：创建类的对象。比如：Phone p1 = new Phone();
步骤3：通过对象，调用其内部声明的属性或方法，完成相关的功能
```

- 对象的内存解析
  - 创建类的一个对象；创建类的多个对象；方法的调用的内存解析
- Java中内存结构的划分
  - Java中内存结构划分为：`虚拟机栈、堆、方法区`；程序计数器、本地方法栈
  - 虚拟机栈：以栈帧为基本单位，有入栈和出栈操作；每个栈帧入栈操作对应一个方法的执行；方法内的局部变量会存储在栈帧中。
  - 堆空间：new 出来的结构（数组、对象）：① 数组，数组的元素在堆中 ② 对象的成员变量在堆中。
  - 方法区：加载的类的模板结构。

### 3. 类的成员之一：属性（或成员变量）

- 属性 vs 局部变量
  - 声明的位置
  - 内存中存放的位置
  - 作用域
  - 权限修饰符
  - 初始化值
  - 生命周期
- 属性 <=> 成员变量 <=>field <=> 字段、域

### 4. 类的成员之二：方法

- 方法的声明：权限修饰符 返回值类型 方法名(形参列表){ // 方法体}
  - 重点：返回值类型、形参列表
- return关键字的使用

### 5. 再谈方法

#### 5.1 方法的重载(overload)

- 方法的重载的要求：“两同一不同”
- 调用方法时，如何确定调用的是某个指定的方法呢？① 方法名 ② 形参列表

#### 5.2 可变个数形参的方法

- 格式：(int ... args)

#### 5.3 方法的参数传递机制：值传递(重点、难点)

```
> 如果形参是基本数据类型的变量，则将实参保存的数据值赋给形参。
> 如果形参是引用数据类型的变量，则将实参保存的地址值赋给形参。
```

#### 5.4 递归方法

- 递归方法构成了隐式的循环
- 对比：相较于循环结构，递归方法效率稍低，内存占用偏高。

### 6. 对象数组

- String[] ；Person[] ; Customer[] 

### 7. package、import关键字的使用

- package：指明声明的类所属的包。
- import：当前类中，如果使用其它包下的类（除java.lang包），原则上就需要导入。

### 8. oop的特征之一：封装性

```
Java规定了4种权限修饰，分别是：private、缺省、protected、public。
我们可以使用4种权限修饰来修饰类及类的内部成员。当这些成员被调用时，体现可见性的大小。
```

举例：

```
> 场景1：私有化(private)类的属性，提供公共(public)的get和set方法，对此属性进行获取或修改
> 场景2：将类中不需要对外暴露的方法，设置为private
> 场景3：单例模式中构造器private的了，避免在类的外部创建实例。（放到static关键字后讲）
```

上理论：程序设计的原则之一

```
理论上：
  -`高内聚`：类的内部数据操作细节自己完成，不允许外部干涉；
    （Java程序通常以类的形态呈现，相关的功能封装到方法中。）
  -`低耦合`：仅暴露少量的方法给外部使用，尽量方便外部调用。
    （给相关的类、方法设置权限，把该隐藏的隐藏起来，该暴露的暴露出去）
```

### 9. 类的成员之三：构造器

- 如何定义：权限修饰符 类名(形参列表){}
- 构造器的作用：① 搭配上new，用来创建对象 ② 初始化对象的成员变量

### 10. 三个小知识

#### 10.1 类的实例变量的赋值过程（重要）

```
1. 在类的属性中，可以有哪些位置给属性赋值？
① 默认初始化；
② 显式初始化；
③ 构造器中初始化；
**********************************
④ 通过"对象.方法"的方式赋值；
⑤ 通过"对象.属性"的方式赋值；

2. 这些位置执行的先后顺序是怎样？
① - ② - ③ - ④/⑤
```

#### 10.2 JavaBean

```
所谓JavaBean，是指符合如下标准的Java类：

- 类是公共的
- 有一个无参的公共的构造器
- 有属性，且有对应的get、set方法
```

#### 10.3 UML类图

熟悉。

## 13.企业真题

### 2.1 类与对象

#### 1. 面向对象，面向过程的理解？（平*金服、英**达）

略。

#### 2. Java 的引用类型有哪几种（阿*校招）

类、数组、接口；枚举、注解、记录

#### 3. 类和对象的区别（凡*科技、上\*银行）

略。

#### 4. 面向对象，你解释一下，项目中哪些地方用到面向对象？（燕*金融）

“万事万物皆对象”。

### 2.2 Java内存结构

#### 1. Java虚拟机中内存划分为哪些区域，详细介绍一下（神**岳、数\*互融）

略。

#### 2. 对象存在Java内存的哪块区域里面？（阿*）

堆内存。

### 2.3 权限修饰符（封装性）

#### 1. private 、缺省、protected、public的表格化作用区域（爱*信、拓\*思、中\*瑞飞）

略

#### 2. main方法的public能不能换成private？为什么？（凡*科技、顺\*）

能。但是改以后就不能作为程序的入口了，就只是一个普通的方法。

### 2.4 构造器

#### 1. 构造方法和普通方法的区别（凡\*科技、软\*动力、中*软）

编写代码的角度：没有共同点。声明格式、作用都不同。

字节码文件的角度：构造器会以`<init>()方法`的形态呈现，用以初始化对象。

#### 2. 构造器Constructor是否可被overload?（鸿*网络）

可以。

#### 3. 无参构造器和有参构造器的的作用和应用（北京楚*龙）

略

### 2.5 属性及属性赋值顺序

#### 1. 成员变量与局部变量的区别（艾*软件）

6个点。

#### 2. 变量赋值和构造方法加载的优先级问题（凡*科技、博\*软件）

变量显式赋值先于构造器中的赋值。

如何证明？我看的字节码文件。





# 第06章_面向对象编程(进阶)

***

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC2%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1%E7%BC%96%E7%A8%8B-%E7%AC%AC07%E7%AB%A0.png" alt="第2阶段：Java面向对象编程-第07章" style="zoom: 67%;" />

## 1. 关键字：this

### 1.1 this是什么？

- ==**它在方法（准确的说是实例方法或非static的方法）内部使用，<font color='red'>表示调用该方法的对象</font>**==
- ==**它在构造器内部使用，<font color='red'>表示该构造器正在初始化的对象</font>**==

- ==this可以调用的结构：成员变量、方法和构造器==

### 1.2 什么时候使用this

#### 1.2.1 实例方法或构造器中使用当前对象的成员

在实例方法或构造器中，如果使用当前类的成员变量或成员方法可以在其前面添加this，增强程序的可读性。不过，通常我们都习惯省略this。

但是，当形参与成员变量同名时，如果在方法内或构造器内需要使用成员变量，必须添加this来表明该变量是类的成员变量。即：我们可以用this来区分`成员变量`和`局部变量`。比如：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503102947013.png" alt="image-20220503102947013" style="zoom:67%;" />

另外，使用this访问属性和方法时，如果在本类中未找到，会从父类中查找。这个在继承中会讲到。

举例1：

```java
class Person{		// 定义Person类
	private String name ;	
	private int age ;			
	public Person(String name,int age){	
		this.name = name ;   
		this.age = age ;  
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
	public void getInfo(){	
		System.out.println("姓名：" + name) ;
		this.speak();
	}
	public void speak(){
		System.out.println(“年龄：” + this.age);	
	}
}

```

举例2：

```java
public class Rectangle {
    int length;
    int width;

    public int area() {
        return this.length * this.width;
    }

    public int perimeter(){
        return 2 * (this.length + this.width);
    }

    public void print(char sign) {
        for (int i = 1; i <= this.width; i++) {
            for (int j = 1; j <= this.length; j++) {
                System.out.print(sign);
            }
            System.out.println();
        }
    }

    public String getInfo(){
        return "长：" + this.length + "，宽：" + this.width +"，面积：" + this.area() +"，周长：" + this.perimeter();
    }
}

```

测试类：

```java
public class TestRectangle {
    public static void main(String[] args) {
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle();

        System.out.println("r1对象：" + r1.getInfo());
        System.out.println("r2对象：" + r2.getInfo());

        r1.length = 10;
        r1.width = 2;
        System.out.println("r1对象：" + r1.getInfo());
        System.out.println("r2对象：" + r2.getInfo());

        r1.print('#');
        System.out.println("---------------------");
        r1.print('&');

        System.out.println("---------------------");
        r2.print('#');
        System.out.println("---------------------");
        r2.print('%');
    }
}
```

#### 1.2.2 同一个类中构造器互相调用

<font color='cornflowerblue'>**this可以作为一个类中构造器相互调用的特殊格式**。</font>

- **this()：**调用本类的无参构造器
- **this(实参列表)：**调用本类的有参构造器

```java
public class Student {
    private String name;
    private int age;

    // 无参构造
    public Student() {
//        this("",18);//调用本类有参构造器
    }

    // 有参构造
    public Student(String name) {
        this();//调用本类无参构造器
        this.name = name;
    }
    // 有参构造
    public Student(String name,int age){
        this(name);//调用本类中有一个String参数的构造器
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo(){
        return "姓名：" + name +"，年龄：" + age;
    }
}
```

注意：

- 不能出现递归调用。比如，调用自身构造器。
  - 推论：如果一个类中声明了n个构造器，则最多有 n - 1个构造器中使用了"this(形参列表)"
- this()和this(实参列表)只能声明在构造器首行。
  - 推论：在类的一个构造器中，最多只能声明一个"this(参数列表)"

### 1.3 练习

**练习1：**添加必要的构造器，综合应用构造器的重载，this关键字。

![image-20220808191154534](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220808191154534.png)

**练习2：**

（1）按照如下的UML类图，创建相应的类，提供必要的结构：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323211412409.png" alt="image-20220323211412409" style="zoom:50%;" />

在提款方法withdraw()中，需要判断用户余额是否能够满足提款数额的要求，如果不能，应给出提示。deposit()方法表示存款。

（2）按照如下的UML类图，创建相应的类，提供必要的结构

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323211454372.png" alt="image-20220323211454372" style="zoom:50%;" />

（3）按照如下的UML类图，创建相应的类，提供必要的结构

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323211521808.png" alt="image-20220323211521808" style="zoom:50%;" />

- addCustomer 方法必须依照参数（姓，名）构造一个新的 Customer对象，然后把它放到 customer 数组中。还必须把 numberOfCustomer 属性的值加 1。

- getNumOfCustomers 方法返回 numberofCustomers 属性值。

- getCustomer方法返回与给出的index参数相关的客户。

（4）创建BankTest类，进行测试。

内存解析图：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323211653907.png" alt="image-20220323211653907" style="zoom:80%;" />

## 2. 面向对象特征二：继承(Inheritance)

### 2.1 继承的概述

#### 2.1.1 生活中的继承

* 财产继承：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323215921441.png" alt="image-20220323215921441" style="zoom: 50%;" />

- 绿化：前人栽树，后人乘凉

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520171727778.png" alt="image-20220520171727778" style="zoom: 50%;" />

“绿水青山，就是金山银山”

* 样貌：

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323220246746.png" alt="image-20220323220246746" style="zoom: 50%;" />

* 继承之外，是不是还可以"`进化`"：

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323220537644.png" alt="image-20220323220537644" style="zoom: 50%;" />

> 继承有延续（下一代延续上一代的基因、财富）、扩展（下一代和上一代又有所不同）的意思。

#### 2.1.2 Java中的继承

==**<font color='red'>继承就是子类继承父类，子类拥有父类的属性和方法。</font>**==但是由于封装性的影响，子类不能直接访问父类当中的私有属性和私有方法。

**角度一：从上而下**

为描述和处理**个人**信息，定义类Person：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323220923386.png" alt="image-20220323220923386" style="zoom:67%;" />

为描述和处理**学生**信息，定义类Student：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323221001495.png" alt="image-20220323221001495" style="zoom:67%;" />

通过继承，简化Student类的定义：

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323221050791.png" alt="image-20220323221050791" style="zoom:67%;" />

> 说明：Student类继承了父类Person的所有属性和方法，并增加了一个属性school。Person中的属性和方法，Student都可以使用。

**角度二：从下而上**

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%8C%AB%E7%8B%97%E7%BB%A7%E6%89%BF1.jpg)

多个类中存在相同属性和行为时，将这些内容抽取到单独一个类中，那么多个类中无需再定义这些属性和行为，只需要和抽取出来的类构成`继承关系`。如图所示：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%8C%AB%E7%8B%97%E7%BB%A7%E6%89%BF2.jpg" style="zoom: 67%;" />

再举例：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323221436571.png" alt="image-20220323221436571" style="zoom:67%;" />

#### 2.1.3 继承的好处

- ==继承的出现减少了代码冗余，提高了代码的复用性。==

- ==继承的出现，更有利于功能的扩展。==

- ==继承的出现让类与类之间产生了`is-a`的关系，为多态的使用提供了前提。==
  - 继承描述事物之间的所属关系，这种关系是：`is-a` 的关系。可见，父类更通用、更一般，子类更具体。

> 注意：不要仅为了获取其他类中某个功能而去继承！

### 2.2 继承的语法

#### 2.2.1 继承中的语法格式

通过 `extends` 关键字，可以声明一个类B继承另外一个类A，定义格式如下：

```java
[修饰符] class 类A {
	...
}

[修饰符] class 类B extends 类A {
	...
}

```

#### 2.2.2 继承中的基本概念

类B，称为子类、派生类(derived class)、SubClass

类A，称为父类、超类、基类(base class)、SuperClass

### 2.3 代码举例

**1、父类**

```java
package com.atguigu.inherited.grammar;

/*
 * 定义动物类Animal，做为父类
 */
public class Animal {
    // 定义name属性
    String name;
    // 定义age属性
    int age;

    // 定义动物的吃东西方法
    public void eat() {
        System.out.println(age + "岁的"
                + name + "在吃东西");
    }
}

```

**2、子类**

```java
package com.atguigu.inherited.grammar;

/*
 * 定义猫类Cat 继承 动物类Animal
 */
public class Cat extends Animal {
    int count;//记录每只猫抓的老鼠数量

    // 定义一个猫抓老鼠的方法catchMouse
    public void catchMouse() {
        count++;
        System.out.println("抓老鼠，已经抓了"
                + count + "只老鼠");
    }
}
```

**3、测试类**

```java
package com.atguigu.inherited.grammar;

public class TestCat {
    public static void main(String[] args) {
        // 创建一个猫类对象
        Cat cat = new Cat();
        // 为该猫类对象的name属性进行赋值
        cat.name = "Tom";
        // 为该猫类对象的age属性进行赋值
        cat.age = 2;
        // 调用该猫继承来的eat()方法
        cat.eat();
        // 调用该猫的catchMouse()方法
        cat.catchMouse();
        cat.catchMouse();
        cat.catchMouse();
    }
}
```

### 2.4 继承性的细节说明

**1、子类会继承父类所有的实例变量和实例方法**

从类的定义来看，类是一类具有相同特性的事物的抽象描述。父类是所有子类共同特征的抽象描述。而实例变量和实例方法就是事物的特征，那么父类中声明的实例变量和实例方法代表子类事物也有这个特征。

- 当子类对象被创建时，在堆中给对象申请内存时，就要看子类和父类都声明了什么实例变量，这些实例变量都要分配内存。
- 当子类对象调用方法时，编译器会先在子类模板中看该类是否有这个方法，如果没找到，会看它的父类甚至父类的父类是否声明了这个方法，遵循`从下往上`找的顺序，找到了就停止，一直到根父类都没有找到，就会报编译错误。

所以继承意味着子类的对象除了看子类的类模板还要看父类的类模板。

![image-20211230090255997](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230090255997.png)

**2、子类不能直接访问父类中私有的(private)的成员变量和方法**

子类虽会继承父类私有(private)的成员变量，但子类不能对继承的私有成员变量直接进行访问，可通过继承的get/set方法进行访问。如图所示：

![image-20220323224757212](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323224757212.png)

**3、<font color='red'>在Java 中，继承的关键字用的是“extends”，即子类不是父类的子集，而是对父类的“扩展”</font>**

子类在继承父类以后，还可以定义自己特有的方法，这就可以看做是<font color='red'>对父类功能上的扩展</font>。 

**4、Java支持多层继承(继承体系)**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323225441417.png" alt="image-20220323225441417" style="zoom:67%;" />

```java
class A{}
class B extends A{}
class C extends B{}
```

> 说明：
>
> - 子类和父类是一种相对的概念
>
> - **顶层父类是Object类。所有的类默认继承Object，作为父类。**

**5、一个父类可以同时拥有多个子类**

```java
class A{}
class B extends A{}
class D extends A{}
class E extends A{}
```

**6、Java只支持单继承，不支持多重继承**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514162507692.png" alt="image-20220514162507692" style="zoom:67%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220523201405061.png" alt="image-20220523201405061" style="zoom:67%;" />

```java
public class A{}
class B extends A{}

//一个类只能有一个父类，不可以有多个直接父类。
class C extends B{} 	//ok
class C extends A,B...	//error
```

### 2.5 练习

**练习1：**定义一个学生类Student，它继承自Person类

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323231804928.png" alt="image-20220323231804928" style="zoom:67%;" />

**练习2：**

(1)定义一个ManKind类，包括

- 成员变量int sex和int salary；
- 方法void manOrWoman()：根据sex的值显示“man”(sex==1)或者“woman”(sex==0)；

- 方法void employeed()：根据salary的值显示“no job”(salary==0)或者“ job”(salary!=0)。

(2)定义类Kids继承ManKind，并包括

- 成员变量int yearsOld；
- 方法printAge()打印yearsOld的值。

(3)定义类KidsTest，在类的main方法中实例化Kids的对象someKid，用该对象访问其父类的成员变量及方法。

**练习3：**根据下图实现类。在CylinderTest类中创建Cylinder类的对象，设置圆柱的底面半径和高，并输出圆柱的体积。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220323231942361.png" alt="image-20220323231942361" style="zoom:67%;" />

## 3. 方法的重写（override/overwrite）

**<font color='red'>父类的所有方法子类都会继承，但是当某个方法被继承到子类之后，子类觉得父类原来的实现不适合于自己当前的类，该怎么办呢？</font>**==子类可以对从父类中继承来的方法进行改造，我们称为方法的`重写 (override、overwrite)`。也称为方法的`重置`、`覆盖`。==

在程序执行时，子类的方法将覆盖父类的方法。

### 3.1 方法重写举例

比如新的手机增加来电显示头像的功能，代码如下：

```java
package com.atguigu.inherited.method;

public class Phone {
    public void sendMessage(){
        System.out.println("发短信");
    }
    public void call(){
        System.out.println("打电话");
    }
    public void showNum(){
        System.out.println("来电显示号码");
    }
}

```

```java
package com.atguigu.inherited.method;

//SmartPhone：智能手机
public class SmartPhone extends Phone{
    //重写父类的来电显示功能的方法
	@Override
    public void showNum(){
        //来电显示姓名和图片功能
        System.out.println("显示来电姓名");
        System.out.println("显示头像");
    }
    //重写父类的通话功能的方法
    @Override
    public void call() {
        System.out.println("语音通话 或 视频通话");
    }
}
```

```java
package com.atguigu.inherited.method;

public class TestOverride {
    public static void main(String[] args) {
        // 创建子类对象
        SmartPhone sp = new SmartPhone();

        // 调用父类继承而来的方法
        sp.call();

        // 调用子类重写的方法
        sp.showNum();
    }
}
```

> @Override使用说明：
>
> 写在方法上面，用来检测是不是满足重写方法的要求。这个注解就算不写，只要满足要求，也是正确的方法覆盖重写。建议保留，这样编译器可以帮助我们检查格式，另外也可以让阅读源代码的程序员清晰的知道这是一个重写的方法。

### 3.2 方法重写的要求

1. **子类重写的方法`必须`和父类被重写的方法具有相同的`方法名称`、`参数列表`。**

2. **子类重写的方法使用的访问权限`不能小于`父类被重写的方法的访问权限。（public > protected > 缺省 > private）**

3. **父类被重写方法的返回值是基本数据类型，则子类重写的方法的返回值类型必须与父类被重写的方法的返回值类型相同。**

4. **父类被重写方法的返回值是引用数据类型，则子类重写的方法的返回值类型可以与父类被重写的方法的返回值类型相同，也可以是被重写方法返回值类型的子类。（例如：Student < Person）。**

> 注意：① 父类私有方法不能重写   ② 跨包的父类缺省的方法也不能重写

5. **子类方法抛出的异常不能大于父类被重写方法的异常**



此外，子类与父类中同名同参数的方法必须同时声明为非static的(即为重写)，或者同时声明为static的（不是重写）。因为static方法是属于类的，子类无法覆盖父类的方法。

### 3.3 方法的重载与重写的区别

方法的重载：==在同一个类中，可以有具有多个相同方法名的方法，但是参数类型或者是参数个数不相同。==

方法的重写：==子类继承父类，子类可以对父类的方法进行覆写。==

（1）同一个类中

```java
package com.atguigu.inherited.method;

public class TestOverload {
    public int max(int a, int b){
        return a > b ? a : b;
    }
    public double max(double a, double b){
        return a > b ? a : b;
    }
    public int max(int a, int b,int c){
        return max(max(a,b),c);
    }
}
```

（2）父子类中

```java
package com.atguigu.inherited.method;

public class TestOverloadOverride {
    public static void main(String[] args) {
        Son s = new Son();
        s.method(1);//只有一个形式的method方法

        Daughter d = new Daughter();
        d.method(1);
        d.method(1,2);//有两个形式的method方法
    }
}

class Father{
    public void method(int i){
        System.out.println("Father.method");
    }
}
class Son extends Father{
    public void method(int i){//重写
        System.out.println("Son.method");
    }
}
class Daughter extends Father{
    public void method(int i,int j){//重载
        System.out.println("Daughter.method");
    }
}
```

### 3.4 练习

**练习1：**如果现在父类的一个方法定义成private访问权限，在子类中将此方法声明为default访问权限，那么这样还叫重写吗？ (NO)

**练习2：**修改继承内容的练习2中定义的类Kids，在Kids中重新定义employeed()方法，覆盖父类ManKind中定义的employeed()方法，输出“Kids should study and no job.”

## 4. 再谈封装性中的4种权限修饰

权限修饰符：public,protected,缺省,private

| 修饰符    | 本类 | 本包                      | 其他包子类                  | 其他包非子类 |
| --------- | ---- | ------------------------- | --------------------------- | ------------ |
| private   | √    | ×                         | ×                           | ×            |
| 缺省      | √    | √（本包子类非子类都可见） | ×                           | ×            |
| protected | √    | √（本包子类非子类都可见） | √（其他包仅限于子类中可见） | ×            |
| public    | √    | √                         | √                           | √            |

外部类：public和缺省

成员变量、成员方法等：public,protected,缺省,private

**1、外部类要跨包使用必须是public，否则仅限于本包使用**

（1）外部类的权限修饰符如果缺省，本包使用没问题

![image-20211230093627763](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230093627763.png)

（2）外部类的权限修饰符如果缺省，跨包使用有问题

![image-20211230094236974](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230094236974.png)

**2、成员的权限修饰符问题**

（1）本包下使用：成员的权限修饰符可以是public、protected、缺省

![image-20211230095320646](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230095320646.png)

（2）跨包下使用：要求严格

![image-20211230095817784](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230095817784.png)

（3）跨包使用时，如果类的权限修饰符缺省，成员权限修饰符>类的权限修饰符也没有意义

![image-20211230100219840](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230100219840.png)

## 5. 关键字：super

### 5.1 super的理解

在Java类中使用super来调用父类中的指定操作：

- super可用于访问父类中定义的属性
- super可用于调用父类中定义的成员方法
- super可用于在子类构造器中调用父类的构造器

注意：

- 尤其当子父类出现同名成员时，可以用super表明调用的是父类中的成员
- super的追溯不仅限于直接父类
- super和this的用法相像，this代表本类对象的引用，super代表父类的内存空间的标识

### 5.2 super的使用场景

#### 5.2.1 子类中调用父类被重写的方法

- ==如果子类没有重写父类的方法，只要权限修饰符允许，在子类中完全可以直接调用父类的方法；==
- ==如果子类重写了父类的方法，在子类中需要通过`super.`才能调用父类被重写的方法，否则默认调用的子类重写的方法==

举例：	

```java
package com.atguigu.inherited.method;

public class Phone {
    public void sendMessage(){
        System.out.println("发短信");
    }
    public void call(){
        System.out.println("打电话");
    }
    public void showNum(){
        System.out.println("来电显示号码");
    }
}

//smartphone：智能手机
public class SmartPhone extends Phone{
    //重写父类的来电显示功能的方法
    public void showNum(){
        //来电显示姓名和图片功能
        System.out.println("显示来电姓名");
        System.out.println("显示头像");

        //保留父类来电显示号码的功能
        super.showNum();//此处必须加super.，否则就是无限递归，那么就会栈内存溢出
    }
}
```

总结：

* **方法前面没有super.和this.**
  * 先从子类找匹配方法，如果没有，再从直接父类找，再没有，继续往上追溯

* **方法前面有this.**
  * 先从子类找匹配方法，如果没有，再从直接父类找，再没有，继续往上追溯
    * **方法前面有super.**	
  * 从当前子类的直接父类找，如果没有，继续往上追溯


#### 5.2.2 子类中调用父类中同名的成员变量

- 如果实例变量与局部变量重名，可以在实例变量前面加this.进行区别
- 如果子类实例变量和父类实例变量重名，并且父类的该实例变量在子类仍然可见，在子类中要访问父类声明的实例变量需要在父类实例变量前加super.，否则默认访问的是子类自己声明的实例变量
- 如果父子类实例变量没有重名，只要权限修饰符允许，在子类中完全可以直接访问父类中声明的实例变量，也可以用this.实例访问，也可以用super.实例变量访问

举例：

```java
class Father{
	int a = 10;
	int b = 11;
}
class Son extends Father{
	int a = 20;
    
    public void test(){
		//子类与父类的属性同名，子类对象中就有两个a
		System.out.println("子类的a：" + a);//20  先找局部变量找，没有再从本类成员变量找
        System.out.println("子类的a：" + this.a);//20   先从本类成员变量找
        System.out.println("父类的a：" + super.a);//10    直接从父类成员变量找
		
		//子类与父类的属性不同名，是同一个b
		System.out.println("b = " + b);//11  先找局部变量找，没有再从本类成员变量找，没有再从父类找
		System.out.println("b = " + this.b);//11   先从本类成员变量找，没有再从父类找
		System.out.println("b = " + super.b);//11  直接从父类局部变量找
	}
	
	public void method(int a, int b){
		//子类与父类的属性同名，子类对象中就有两个成员变量a，此时方法中还有一个局部变量a		
		System.out.println("局部变量的a：" + a);//30  先找局部变量
        System.out.println("子类的a：" + this.a);//20  先从本类成员变量找
        System.out.println("父类的a：" + super.a);//10  直接从父类成员变量找

        System.out.println("b = " + b);//13  先找局部变量
		System.out.println("b = " + this.b);//11  先从本类成员变量找
		System.out.println("b = " + super.b);//11  直接从父类局部变量找
    }
}
class Test{
    public static void main(String[] args){
        Son son = new Son();
		son.test();
		son.method(30,13);  
    }
}
```

总结：起点不同（就近原则）

* **变量前面没有super.和this.**
  * 在构造器、代码块、方法中如果出现使用某个变量，先查看是否是当前块声明的`局部变量`，
  * 如果不是局部变量，先从当前执行代码的`本类去找成员变量`
  * 如果从当前执行代码的本类中没有找到，会往上找`父类声明的成员变量`（权限修饰符允许在子类中访问的）

* **变量前面有this.** 
  * 通过this找成员变量时，先从当前执行代码的==本类去找成员变量==
  * 如果从当前执行代码的本类中没有找到，会往上找==父类声明的成员变量（==权限修饰符允许在子类中访问的）

* **变量前面super.** 
  * 通过super找成员变量，直接从当前执行代码的直接父类去找成员变量（权限修饰符允许在子类中访问的）
  * 如果直接父类没有，就去父类的父类中找（权限修饰符允许在子类中访问的）

**<font color='red'>特别说明：应该避免子类声明和父类重名的成员变量</font>**

在阿里的开发规范等文档中都做出明确说明：

![image-20211230110411580](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211230110411580.png)

#### 5.2.3 子类构造器中调用父类构造器

① **子类继承父类时，不会继承父类的构造器。只能通过“super(形参列表)”的方式调用父类指定的构造器。**

② **规定：“super(形参列表)”，必须声明在构造器的首行。**

③ 我们前面讲过，在构造器的首行可以使用"this(形参列表)"，调用本类中重载的构造器，
     结合②，结论：在构造器的首行，"this(形参列表)" 和 "super(形参列表)"只能二选一。

④ **如果在子类构造器的首行既没有显示调用"this(形参列表)"，也没有显式调用"super(形参列表)"，**
**​     则子类此构造器默认调用"super()"，即调用父类中空参的构造器。**

⑤ 由③和④得到结论：子类的任何一个构造器中，要么会调用本类中重载的构造器，要么会调用父类的构造器。
     只能是这两种情况之一。

⑥ 由⑤得到：一个类中声明有n个构造器，最多有n-1个构造器中使用了"this(形参列表)"，则剩下的那个一定使用"super(形参列表)"。

> 开发中常见错误：
>
> 如果子类构造器中既未显式调用父类或本类的构造器，且父类中又没有空参的构造器，则`编译出错`。

情景举例1：

```java
class A{

}
class B extends A{

}

class Test{
    public static void main(String[] args){
        B b = new B();
        //A类和B类都是默认有一个无参构造，B类的默认无参构造中还会默认调用A类的默认无参构造
        //但是因为都是默认的，没有打印语句，看不出来
    }
}
```

情景举例2：

```java
class A{
	A(){
		System.out.println("A类无参构造器");
	}
}
class B extends A{

}
class Test{
    public static void main(String[] args){
        B b = new B();
        //A类显示声明一个无参构造，
		//B类默认有一个无参构造，
		//B类的默认无参构造中会默认调用A类的无参构造
        //可以看到会输出“A类无参构造器"
    }
}
```

情景举例3：

```java
class A{
	A(){
		System.out.println("A类无参构造器");
	}
}
class B extends A{
	B(){
		System.out.println("B类无参构造器");
	}
}
class Test{
    public static void main(String[] args){
        B b = new B();
        //A类显示声明一个无参构造，
		//B类显示声明一个无参构造，        
		//B类的无参构造中虽然没有写super()，但是仍然会默认调用A类的无参构造
        //可以看到会输出“A类无参构造器"和"B类无参构造器")
    }
}
```

情景举例4：

```java
class A{
	A(){
		System.out.println("A类无参构造器");
	}
}
class B extends A{
	B(){
        super();
		System.out.println("B类无参构造器");
	}
}
class Test{
    public static void main(String[] args){
        B b = new B();
        //A类显示声明一个无参构造，
		//B类显示声明一个无参构造，        
		//B类的无参构造中明确写了super()，表示调用A类的无参构造
        //可以看到会输出“A类无参构造器"和"B类无参构造器")
    }
}
```

情景举例5：

```java
class A{
	A(int a){
		System.out.println("A类有参构造器");
	}
}
class B extends A{
	B(){
		System.out.println("B类无参构造器");
	}
}
class Test05{
    public static void main(String[] args){
        B b = new B();
        //A类显示声明一个有参构造，没有写无参构造，那么A类就没有无参构造了
		//B类显示声明一个无参构造，        
		//B类的无参构造没有写super(...)，表示默认调用A类的无参构造
        //编译报错，因为A类没有无参构造
    }
}
```

![image-20200227141228450](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200227141228450.png)

情景举例6：

```java
class A{
	A(int a){
		System.out.println("A类有参构造器");
	}
}
class B extends A{
	B(){
		super();
		System.out.println("B类无参构造器");
	}
}
class Test06{
    public static void main(String[] args){
        B b = new B();
        //A类显示声明一个有参构造，没有写无参构造，那么A类就没有无参构造了
		//B类显示声明一个无参构造，        
		//B类的无参构造明确写super()，表示调用A类的无参构造
        //编译报错，因为A类没有无参构造
    }
}
```

![image-20200303183542807](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20200303183542807.png)

情景举例7：

```java
class A{
	A(int a){
		System.out.println("A类有参构造器");
	}
}
class B extends A{
	B(int a){
		super(a);
		System.out.println("B类有参构造器");
	}
}
class Test07{
    public static void main(String[] args){
        B b = new B(10);
        //A类显示声明一个有参构造，没有写无参构造，那么A类就没有无参构造了
		//B类显示声明一个有参构造，        
		//B类的有参构造明确写super(a)，表示调用A类的有参构造
        //会打印“A类有参构造器"和"B类有参构造器"
    }
}
```

情景举例8：

```java
class A{
    A(){
        System.out.println("A类无参构造器");
    }
	A(int a){
		System.out.println("A类有参构造器");
	}
}
class B extends A{
    B(){
        super();//可以省略，调用父类的无参构造
        System.out.println("B类无参构造器");
    }
	B(int a){
		super(a);//调用父类有参构造
		System.out.println("B类有参构造器");
	}
}
class Test8{
    public static void main(String[] args){
        B b1 = new B();
        B b2 = new B(10);
    }
}
```

### 5.3 小结：this与super

**1、this和super的意义**

this：当前对象

- 在构造器和非静态代码块中，表示正在new的对象
- 在实例方法中，表示调用当前方法的对象

super：引用父类声明的成员

**2、this和super的使用格式**

- this
  - this.成员变量：表示当前对象的某个成员变量，而不是局部变量
  - this.成员方法：表示当前对象的某个成员方法，完全可以省略this.
  - this()或this(实参列表)：调用另一个构造器协助当前对象的实例化，只能在构造器首行，只会找本类的构造器，找不到就报错
- super
  - super.成员变量：表示当前对象的某个成员变量，该成员变量在父类中声明的
  - super.成员方法：表示当前对象的某个成员方法，该成员方法在父类中声明的
  - super()或super(实参列表)：调用父类的构造器协助当前对象的实例化，只能在构造器首行，只会找直接父类的对应构造器，找不到就报错

### 5.4 练习

**练习1：**修改方法重写的练习2中定义的类Kids中employeed()方法，在该方法中调用父类ManKind的employeed()方法，然后再输出“but Kids should study and no job.”



**练习2：**修改继承中的练习3中定义的Cylinder类，在Cylinder类中覆盖findArea()方法，计算圆柱的表面积。考虑：findVolume方法怎样做相应的修改？

在CylinderTest类中创建Cylinder类的对象，设置圆柱的底面半径和高，并输出圆柱的表面积和体积。

附加题：在CylinderTest类中创建一个Circle类的对象，设置圆的半径，计算输出圆的面积。体会父类和子类成员的分别调用。

**练习3：**

1、写一个名为Account的类模拟账户。该类的属性和方法如下图所示。该类包括的属性：账号id，余额balance，年利率annualInterestRate；包含的方法：访问器方法（getter和setter方法），返回月利率的方法getMonthlyInterest()，取款方法withdraw()，存款方法deposit()。

![image-20220324003430464](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324003430464.png)

写一个用户程序测试Account类。在用户程序中，创建一个账号为1122、余额为20000、年利率4.5%的Account对象。使用withdraw方法提款30000元，并打印余额。
再使用withdraw方法提款2500元，使用deposit方法存款3000元，然后打印余额和月利率。

提示：在提款方法withdraw中，需要判断用户余额是否能够满足提款数额的要求，如果不能，应给出提示。
运行结果如图所示：

![image-20220324003447242](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324003447242.png)

2、创建Account类的一个子类CheckAccount代表可透支的账户，该账户中定义一个属性overdraft代表可透支限额。在CheckAccount类中重写withdraw方法，其算法如下：

```
如果（取款金额<账户余额），
	可直接取款
如果（取款金额>账户余额），
	计算需要透支的额度
	判断可透支额overdraft是否足够支付本次透支需要，如果可以
		将账户余额修改为0，冲减可透支金额
	如果不可以
		提示用户超过可透支额的限额
```

 

要求：写一个用户程序测试CheckAccount类。在用户程序中，创建一个账号为1122、余额为20000、年利率4.5%，可透支限额为5000元的CheckAccount对象。

使用withdraw方法提款5000元，并打印账户余额和可透支额。

再使用withdraw方法提款18000元，并打印账户余额和可透支额。

再使用withdraw方法提款3000元，并打印账户余额和可透支额。

提示：

（1）子类CheckAccount的构造方法需要将从父类继承的3个属性和子类自己的属性全部初始化。

（2）父类Account的属性balance被设置为private，但在子类CheckAccount的withdraw方法中需要修改它的值，因此应修改父类的balance属性，定义其为protected。

运行结果如下图所示：

![image-20220324003616767](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324003616767.png)

## 6. 子类对象实例化全过程

![image-20220324003713230](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324003713230.png)

```java
Dog dog = new Dog("小花","小红");
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324003735416.png" alt="image-20220324003735416" style="zoom:80%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324003813163.png" alt="image-20220324003813163" style="zoom:67%;" />

举例：

```java
class Creature {
    public Creature() {
        System.out.println("Creature无参数的构造器");
	}
}
class Animal extends Creature {
    public Animal(String name) {
        System.out.println("Animal带一个参数的构造器，该动物的name为" + name);
    }
    public Animal(String name, int age) {
        this(name);
        System.out.println("Animal带两个参数的构造器，其age为" + age);
	}
}
public class Dog extends Animal {
    public Dog() {
        super("汪汪队阿奇", 3);
        System.out.println("Dog无参数的构造器");
    }
    public static void main(String[] args) {
        new Dog();
	}
}

```

## 7. 面向对象特征三：多态性

> 一千个读者眼中有一千个哈姆雷特。

### 7.1 多态的形式和体现

#### 7.1.1 对象的多态性

多态性，就是子类对象的多态性，==**表示父类的引用指向子类的对象**==

格式：（父类类型：指子类继承的父类类型，或者实现的接口类型）

```ini
父类类型 变量名 = 子类对象；
```

举例：

```java
Person p = new Student();

Object o = new Person();//Object类型的变量o，指向Person类型的对象

o = new Student(); //Object类型的变量o，指向Student类型的对象
```

对象的多态：在Java中，子类的对象可以替代父类的对象使用。所以，一个引用类型变量可能指向(引用)多种不同类型的对象。

#### 7.1.2 多态的理解

Java引用变量有两个类型：`编译时类型`和`运行时类型`。编译时类型由`声明`该变量时使用的类型决定，运行时类型由`实际赋给该变量的对象`决定。简称：**编译时，看左边；运行时，看右边。**

- 若编译时类型和运行时类型不一致，就出现了对象的多态性(Polymorphism)
- 多态情况下，“看左边”：看的是父类的引用（父类中不具备子类特有的方法）
      “看右边”：看的是子类的对象（实际运行的是子类重写父类的方法）

多态的使用前提：==① 类的继承关系  ② 方法的重写==

#### 7.1.3 举例

```java
package com.atguigu.polymorphism.grammar;

public class Pet {
    private String nickname; //昵称

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void eat(){
        System.out.println(nickname + "吃东西");
    }
}
```

```java
package com.atguigu.polymorphism.grammar;

public class Cat extends Pet {
    //子类重写父类的方法
    @Override
    public void eat() {
        System.out.println("猫咪" + getNickname() + "吃鱼仔");
    }

    //子类扩展的方法
    public void catchMouse() {
        System.out.println("抓老鼠");
    }
}
```

```java
package com.atguigu.polymorphism.grammar;

public class Dog extends Pet {
    //子类重写父类的方法
    @Override
    public void eat() {
        System.out.println("狗子" + getNickname() + "啃骨头");
    }

    //子类扩展的方法
    public void watchHouse() {
        System.out.println("看家");
    }
}
```





### 7.2 为什么需要多态性(polymorphism)？

开发中，有时我们在设计一个数组、或一个成员变量、或一个方法的形参、返回值类型时，无法确定它具体的类型，只能确定它是某个系列的类型。

案例：

（1）声明一个Animal类，包含public void eat()方法和public void sleep()方法

（2）声明一个Dog类，继承Animal类，重写public void eat()方法和public void sleep()方法

（3）声明一个Cat类，继承Animal类，重写public void eat()方法和public void sleep()方法



```java
public class Animal {

    public void eat(){
        System.out.println("动物觅食");
    }

    public void sleep(){
        System.out.println("动物睡觉");
    }
}
```

```java
public class Dog extends Animal{

    @Override
    public void eat() {
        System.out.println("dog吃骨头");
    }

    @Override
    public void sleep() {
        System.out.println("dog睡觉");
    }

    public void watchDoor(){
        System.out.println("dog看家");
    }
}
```

```java
public class Cat extends Animal{
    @Override
    public void eat() {
        System.out.println("cat吃🐟");
    }

    @Override
    public void sleep() {
        System.out.println("cat睡觉");
    }

    public void captureMouse(){
        System.out.println("cat抓老鼠");
    }
}
```

```java
public class animalTest {
    public static void main(String[] args) {
        animalTest animalTest = new animalTest();
        Animal dog = new Dog();
        Animal cat = new Cat();

        animalTest.adopt(dog);
        animalTest.adopt(cat);
    }

    public void adopt(Animal animal){
        animal.eat();
        animal.sleep();
    }
}
```

![image-20230316165746105](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230316165746105.png)



### 7.3 多态的好处和弊端

**好处**：==减少大量的重载方法的定义。变量引用的子类对象不同，执行的方法就不同，实现动态绑定。代码编写更灵活、功能更强大，可维护性和扩展性更好了。==

**弊端**：<font color='red'>一个引用类型变量如果声明为父类的类型，但实际引用的是子类对象，那么该变量就不能再访问子类中添加的属性和方法</font>。如上述程序所示，Animal为父类，其引用指向子类对象dog和cat，但是子类对象dog和cat中的独有方法不能被调用。

```java
Student m = new Student();
m.school = "pku"; 	//合法,Student类有school成员变量
Person e = new Student(); 
e.school = "pku";	//非法,Person类没有school成员变量

// 属性是在编译时确定的，编译时e为Person类型，没有school成员变量，因而编译错误。
```

> 开发中：
>
> 使用父类做方法的形参，是多态使用最多的场合。即使增加了新的子类，方法也无需改变，提高了扩展性，符合开闭原则。
>
> 【开闭原则OCP】
>
> - 对扩展开放，对修改关闭
> - 通俗解释：软件系统中的各种组件，如模块（Modules）、类（Classes）以及功能（Functions）等，应该在不修改现有代码的基础上，引入新功能

### 7.4 向上转型与向下转型

对于由于多态的原因—==父类引用指向子类对象，声明的为父类类型，但是引用的却是子类对象，导致子类中的特有方法不能够被调用。==的问题，可以使用类的向上、向下转型。

#### 7.4.1 为什么要类型转换

因为多态，就一定会有把子类对象赋值给父类变量的时候，这个时候，在`编译期间`，就会出现类型转换的现象。

但是，使用父类变量接收了子类对象之后，我们就`不能调用`子类拥有，而父类没有的方法了。这也是多态给我们带来的一点"小麻烦"。所以，想要调用子类特有的方法，必须做类型转换，使得`编译通过`。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324235337563.png" alt="image-20220324235337563" style="zoom:80%;" />

* **向上转型**：当左边的变量的类型（父类） > 右边对象/变量的类型（子类），我们就称为向上转型
  * 此时，编译时按照左边变量的类型处理，就只能调用父类中有的变量和方法，不能调用子类特有的变量和方法了
  * 但是，**运行时，仍然是对象本身的类型**，所以执行的方法是子类重写的方法体。
  * 此时，一定是安全的，而且也是自动完成的

* **向下转型**：当左边的变量的类型（子类）<右边对象/变量的编译时类型（父类），我们就称为向下转型
  * 此时，编译时按照左边变量的类型处理，就可以调用子类特有的变量和方法了
  * 但是，**运行时，仍然是对象本身的类型**
  * <font color='red'>不是所有通过编译的向下转型都是正确的，可能会发生ClassCastException，为了安全，可以通过isInstanceof关键字进行判断</font>

#### 7.4.2 如何向上或向下转型

向上转型：自动完成

向下转型：（子类类型）父类变量

```java
package com.atguigu.polymorphism.grammar;

public class ClassCastTest {
    public static void main(String[] args) {
        //没有类型转换
        Dog dog = new Dog();//dog的编译时类型和运行时类型都是Dog

        //向上转型
        Pet pet = new Dog();//pet的编译时类型是Pet，运行时类型是Dog
        pet.setNickname("小白");
        pet.eat();//可以调用父类Pet有声明的方法eat，但执行的是子类重写的eat方法体
//        pet.watchHouse();//不能调用父类没有的方法watchHouse

        Dog d = (Dog) pet;
        System.out.println("d.nickname = " + d.getNickname());
        d.eat();//可以调用eat方法
        d.watchHouse();//可以调用子类扩展的方法watchHouse

        Cat c = (Cat) pet;//编译通过，因为从语法检查来说，pet的编译时类型是Pet，Cat是Pet的子类，所以向下转型语法正确
        //这句代码运行报错ClassCastException，因为pet变量的运行时类型是Dog，Dog和Cat之间是没有继承关系的
    }
}
```

#### 7.4.3 instanceof关键字

==`instanceof`关键字是为了判断一个对象是否为一个类的实例。==

为了避免ClassCastException的发生，Java提供了 `instanceof` 关键字，给引用变量做类型的校验。如下代码格式：

```java
//检验对象a是否是数据类型A的对象，返回值为boolean型
对象a instanceof 数据类型A 
```

- 说明：
  - 只要用instanceof判断返回true的，那么强转为该类型就一定是安全的，不会报ClassCastException异常。
  - 如果对象a属于类A的子类B，a instanceof A值也为true。
  - 要求对象a所属的类与类A必须是子类和父类的关系，否则编译错误。

代码：

```java
public class animalTest {
    public static void main(String[] args) {
        animalTest animalTest = new animalTest();
        Animal dog = new Dog();
        Animal cat = new Cat();

        animalTest.adopt(dog);
        animalTest.adopt(cat);

        if (dog instanceof Dog) {
            Dog littleDog = (Dog) dog;
            littleDog.watchDoor();
        }
    }

    public void adopt(Animal animal) {
        animal.eat();
        animal.sleep();
    }
}
```

![image-20230316172302867](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230316172302867.png)





### 7.5 虚方法调用(Virtual Method Invocation)

在Java中虚方法是指在编译阶段不能确定方法的调用入口地址，在运行阶段才能确定的方法，即可能被重写的方法。

```java
Person e = new Student();
e.getInfo();	//调用Student类的getInfo()方法
```

子类中定义了与父类同名同参数的方法，在多态情况下，将此时父类的方法称为虚方法，父类根据赋给它的不同子类对象，动态调用属于子类的该方法。这样的方法调用在编译期是无法确定的。

举例：

![image-20220324234208997](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324234208997.png)

前提：Person类中定义了welcome()方法，各个子类重写了welcome()。

![image-20220324234214932](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220324234214932.png)

执行：多态的情况下，调用对象的welcome()方法，实际执行的是子类重写的方法。

> 拓展：
>
> `静态链接（或早起绑定）`：当一个字节码文件被装载进JVM内部时，如果被调用的目标方法在编译期可知，且运行期保持不变时。这种情况下将调用方法的符号引用转换为直接引用的过程称之为静态链接。那么调用这样的方法，就称为非虚方法调用。比如调用静态方法、私有方法、final方法、父类构造器、本类重载构造器等。
>
> `动态链接（或晚期绑定）`：如果被调用的方法在编译期无法被确定下来，也就是说，只能够在程序运行期将调用方法的符号引用转换为直接引用，由于这种引用转换过程具备动态性，因此也就被称之为动态链接。调用这样的方法，就称为虚方法调用。比如调用重写的方法（针对父类）、实现的方法（针对接口）。

### 7.6 成员变量没有多态性

- 若子类重写了父类方法，就意味着子类里定义的方法彻底覆盖了父类里的同名方法，系统将不可能把父类里的方法转移到子类中。

- 对于实例变量则不存在这样的现象，即使子类里定义了与父类完全相同的实例变量，这个实例变量依然不可能覆盖父类中定义的实例变量

```java
package com.atguigu.polymorphism.grammar;

public class TestVariable {
    public static void main(String[] args) {
        Base b = new Sub();
        System.out.println(b.a);
        System.out.println(((Sub)b).a);

        Sub s = new Sub();
        System.out.println(s.a);
        System.out.println(((Base)s).a);
    }
}
class Base{
    int a = 1;
}
class Sub extends Base{
    int a = 2;
}
```

### 7.7 练习

**练习1：笔试&面试**

题目1：继承成员变量和继承方法的区别

```java
class Base {
    int count = 10;
    public void display() {
        System.out.println(this.count);
    }
}

class Sub extends Base {
    int count = 20;
    public void display() {
        System.out.println(this.count);
    }
}

public class FieldMethodTest {
    public static void main(String[] args){
        Sub s = new Sub();
        System.out.println(s.count);
        s.display();
        Base b = s;
        System.out.println(b == s);
        System.out.println(b.count);
        b.display();
    }
}

```

题目2：

```java
//考查多态的笔试题目：
public class InterviewTest1 {

	public static void main(String[] args) {
		Base base = new Sub();
		base.add(1, 2, 3);

//		Sub s = (Sub)base;
//		s.add(1,2,3);
	}
}

class Base {
	public void add(int a, int... arr) {
		System.out.println("base");
	}
}

class Sub extends Base {

	public void add(int a, int[] arr) {
		System.out.println("sub_1");
	}

//	public void add(int a, int b, int c) {
//		System.out.println("sub_2");
//	}

}

```

题目3：

```java
//getXxx()和setXxx()声明在哪个类中，内部操作的属性就是哪个类里的。
public class InterviewTest2 {
	public static void main(String[] args) {
		Father f = new Father();
		Son s = new Son();
		System.out.println(f.getInfo());//atguigu
		System.out.println(s.getInfo());//尚硅谷
		s.test();//尚硅谷  atguigu
		System.out.println("-----------------");
		s.setInfo("大硅谷");
		System.out.println(f.getInfo());//atguigu
		System.out.println(s.getInfo());//大硅谷
		s.test();//大硅谷  atguigu
	}
}

class Father {
	private String info = "atguigu";

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}

class Son extends Father {
	private String info = "尚硅谷";
	
	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
	
	public void test() {
		System.out.println(this.getInfo());
		System.out.println(super.getInfo());
	}
}
```

题目4：多态是编译时行为还是运行时行为？

```java
//证明如下：
class Animal  {
	protected void eat() {
		System.out.println("animal eat food");
	}
}

class Cat  extends Animal  {
	protected void eat() {
		System.out.println("cat eat fish");
	}
}

class Dog  extends Animal  {
	public void eat() {
		System.out.println("Dog eat bone");
	}
}

class Sheep  extends Animal  {
	public void eat() {
		System.out.println("Sheep eat grass");
	}
}

public class InterviewTest {
	public static Animal  getInstance(int key) {
		switch (key) {
		case 0:
			return new Cat ();
		case 1:
			return new Dog ();
		default:
			return new Sheep ();
		}

	}

	public static void main(String[] args) {
		int key = new Random().nextInt(3);
		System.out.println(key);

		Animal  animal = getInstance(key);
		animal.eat(); 
	}
}
```

**练习2：**

```java
class Person {
	protected String name="person";
	protected int age=50;
	public String getInfo() {
	          return "Name: "+ name + "\n" +"age: "+ age;
	}
}
class Student extends Person {
	protected String school="pku";
	public String getInfo() {
  	          return  "Name: "+ name + "\nage: "+ age 
	          + "\nschool: "+ school;
	}	
}
class Graduate extends Student{
	public String major="IT";
	public String getInfo()
	{
		return  "Name: "+ name + "\nage: "+ age 
	          + "\nschool: "+ school+"\nmajor:"+major;
	}
}

```

建立InstanceTest 类，在类中定义方法method(Person e);
在method中:
(1)根据e的类型调用相应类的getInfo()方法。
(2)根据e的类型执行：
如果e为Person类的对象，输出：
“a person”;
如果e为Student类的对象，输出：
“a student”
“a person ” 
如果e为Graduate类的对象，输出： 
“a graduated student”
“a student”
“a person”

**练习3**：定义三个类，父类GeometricObject代表几何形状，子类Circle代表圆形，MyRectangle代表矩形。定义一个测试类GeometricTest，编写equalsArea方法测试两个对象的面积是否相等（注意方法的参数类型，利用动态绑定技术），编写displayGeometricObject方法显示对象的面积（注意方法的参数类型，利用动态绑定技术）。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325000034619.png" alt="image-20220325000034619" style="zoom:67%;" />

## 8. Object 类的使用

### 8.1 如何理解根父类

类 `java.lang.Object`是类层次结构的根类，即所有其它类的父类。每个类都使用 `Object` 作为超类。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503104750655.png" alt="image-20220503104750655" style="zoom:67%;" />

* Object类型的变量与除Object以外的任意引用数据类型的对象都存在多态引用

  ```java
  method(Object obj){…} //可以接收任何类作为其参数
  
  Person o = new Person();  
  method(o);
  
  ```

* 所有对象（包括数组）都实现这个类的方法。

* 如果一个类没有特别指定父类，那么默认则继承自Object类。例如：

  ```java
  public class Person {
  	...
  }
  //等价于：
  public class Person extends Object {
  	...
  }
  ```

### 8.2 Object类的方法

根据JDK源代码及Object类的API文档，Object类当中包含的方法有11个。这里我们主要关注其中的6个：

#### 1、(重点)equals()

**= =：** 

- 基本类型比较值:只要两个变量的值相等，即为true。

  ```java
  int a=5; 
  if(a==6){…}
  ```

- 引用类型比较引用(是否指向同一个对象)：只有指向同一个对象时（在堆内存中是同一块内存地址），==才返回true。

  ```java
  Person p1=new Person();  	    
  Person p2=new Person();
  if (p1==p2){…}
  ```

  - 用“==”进行比较时，符号两边的`数据类型必须兼容`(可自动转换的基本数据类型除外)，否则编译出错

**equals()：**所有类都继承了Object，也就获得了equals()方法。还可以重写。

- <font color='red'>只能比较引用类型，Object类源码中equals()的作用与“==”相同：比较是否指向同一个对象。</font>	 

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220226101655293.png" alt="image-20220503104750655" style="zoom:67%;" />

- 格式:obj1.equals(obj2)

- 特例：当用equals()方法进行比较时，对类File、String、Date及包装类（Wrapper Class）来说，是比较类型及内容而不考虑引用的是否是同一个对象；

  - 原因：在这些类中重写了Object类的equals()方法。

- 当自定义使用equals()时，可以重写。用于比较两个对象的“内容”是否都相等

- 重写equals()方法的原则

  - `对称性`：如果x.equals(y)返回是“true”，那么y.equals(x)也应该返回是“true”。

  - `自反性`：x.equals(x)必须返回是“true”。

  - `传递性`：如果x.equals(y)返回是“true”，而且y.equals(z)返回是“true”，那么z.equals(x)也应该返回是“true”。

  - `一致性`：如果x.equals(y)返回是“true”，只要x和y内容一直不变，不管你重复x.equals(y)多少次，返回都是“true”。

  - 任何情况下，x.equals(null)，永远返回是“false”；

    ​    x.equals(和x不同类型的对象)永远返回是“false”。

- 重写举例：

```java
class User{
	private String host;
	private String username;
	private String password;
	public User(String host, String username, String password) {
		super();
		this.host = host;
		this.username = username;
		this.password = password;
	}
	public User() {
		super();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [host=" + host + ", username=" + username + ", password=" + password + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
```

**面试题：**==和equals的区别

> 从我面试的反馈，85%的求职者“理直气壮”的回答错误…

- ====既可以比较基本类型也可以比较引用类型。对于基本类型就是比较值，对于引用类型就是比较内存地址==

- equals的话，它是属于java.lang.Object类里面的方法，如果该方法没有被重写过默认也是==;我们可以看到String等类的equals方法是被重写过的，而且String类在日常开发中用的比较多，久而久之，形成了equals是比较值的错误观点。

- 具体要看自定义类里有没有重写Object的equals方法来判断。 

- 通常情况下，重写equals方法，会比较类中的相应属性是否都相等。

**练习1：**

```java
int it = 65;
float fl = 65.0f;
System.out.println(“65和65.0f是否相等？” + (it == fl)); //

char ch1 = 'A'; char ch2 = 12;
System.out.println("65和'A'是否相等？" + (it == ch1));//
System.out.println("12和ch2是否相等？" + (12 == ch2));//

String str1 = new String("hello");
String str2 = new String("hello");
System.out.println("str1和str2是否相等？"+ (str1 == str2));//

System.out.println("str1是否equals str2？"+(str1.equals(str2)));//

System.out.println(“hello” == new java.util.Date()); //

```

**练习2：**

编写Order类，有int型的orderId，String型的orderName，相应的getter()和setter()方法，两个参数的构造器，重写父类的equals()方法：public boolean equals(Object obj)，并判断测试类中创建的两个对象是否相等。

**练习3：**

请根据以下代码自行定义能满足需要的MyDate类,在MyDate类中覆盖equals方法，使其判断当两个MyDate类型对象的年月日都相同时，结果为true，否则为false。  public boolean equals(Object o)

```java
public class EqualsTest {
    public static void main(String[] args) {
        MyDate m1 = new MyDate(14, 3, 1976);
        MyDate m2 = new MyDate(14, 3, 1976);
        if (m1 == m2) {
            System.out.println("m1==m2");
        } else {
            System.out.println("m1!=m2"); // m1 != m2
        }

        if (m1.equals(m2)) {
            System.out.println("m1 is equal to m2");// m1 is equal to m2
        } else {
            System.out.println("m1 is not equal to m2");
        }
    }
}

```

#### 2、(重点)toString()

方法签名：public String toString()

① 默认情况下，<font color='red'>toString()返回的是“对象的运行时类型 @ 对象的hashCode值的十六进制形式"</font>font>

② 在进行String与其它类型数据的连接操作时，自动调用toString()方法

```java
Date now=new Date();
System.out.println(“now=”+now);  //相当于
System.out.println(“now=”+now.toString()); 
```

③ 如果我们直接System.out.println(对象)，默认会自动调用这个对象的toString()

> 因为Java的引用数据类型的变量中存储的实际上时对象的内存地址，但是Java对程序员隐藏内存地址信息，所以不能直接将内存地址显示出来，所以当你打印对象时，JVM帮你调用了对象的toString()。

④ 可以根据需要在用户自定义类型中重写toString()方法，如String 类重写了toString()方法，返回字符串的值。

```java
s1="hello";
System.out.println(s1);//相当于System.out.println(s1.toString());
```

例如自定义的Person类：

```java
public class Person {  
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
```

**练习**：定义两个类，父类GeometricObject代表几何形状，子类Circle代表圆形。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325002959156.png" alt="image-20220325002959156" style="zoom:67%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325002932102.png" alt="image-20220325002932102" style="zoom:67%;" />

#### 3、clone()

```java
//Object类的clone()的使用
public class CloneTest {
	public static void main(String[] args) {
		Animal a1 = new Animal("花花");
		try {
			Animal a2 = (Animal) a1.clone();
			System.out.println("原始对象：" + a1);
			a2.setName("毛毛");
			System.out.println("clone之后的对象：" + a2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}

class Animal implements Cloneable{
	private String name;

	public Animal() {
		super();
	}

	public Animal(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Animal [name=" + name + "]";
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
}
```

#### 4、finalize()

- <font color='red'>当对象被回收时，系统自动调用该对象的 finalize() 方法。</font>（不是垃圾回收器调用的，是本类对象调用的）
  - 永远不要主动调用某个对象的finalize方法，应该交给垃圾回收机制调用。
- 什么时候被回收：当某个对象没有任何引用时，JVM就认为这个对象是垃圾对象，就会在之后不确定的时间使用垃圾回收机制来销毁该对象，在销毁该对象前，会先调用 finalize()方法。 
- 子类可以重写该方法，目的是在对象被清理之前执行必要的清理操作。比如，在方法内断开相关连接资源。
  - 如果重写该方法，让一个新的引用变量重新引用该对象，则会重新激活对象。
- 在JDK 9中此方法已经被`标记为过时`的。

```java
public class FinalizeTest {
	public static void main(String[] args) {
		Person p = new Person("Peter", 12);
		System.out.println(p);
		p = null;//此时对象实体就是垃圾对象，等待被回收。但时间不确定。
		System.gc();//强制性释放空间
	}
}

class Person{
	private String name;
	private int age;

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	//子类重写此方法，可在释放对象前进行某些操作
	@Override
	protected void finalize() throws Throwable {
		System.out.println("对象被释放--->" + this);
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}
```

#### 5、getClass()

public final Class<?> getClass()：获取对象的运行时类型

> 因为Java有多态现象，所以一个引用数据类型的变量的编译时类型与运行时类型可能不一致，因此如果需要查看这个变量实际指向的对象的类型，需要用getClass()方法

```java
public static void main(String[] args) {
	Object obj = new Person();
	System.out.println(obj.getClass());//运行时类型
}
```

结果：

```java
class com.atguigu.java.Person
```

#### 6、hashCode()

public int hashCode()：返回每个对象的hash值。(后续在集合框架章节重点讲解)

```java
public static void main(String[] args) {
	System.out.println("AA".hashCode());//2080
    System.out.println("BB".hashCode());//2112
}
```

### 8.3 native关键字的理解

使用native关键字说明这个方法是原生函数，也就是这个方法是用`C/C++`等非Java语言实现的，并且`被编译成了DLL`，由Java去调用。

- 本地方法是有方法体的，用c语言编写。由于本地方法的方法体源码没有对我们开源，所以我们看不到方法体

- 在Java中定义一个native方法时，并不提供实现体。

**1. 为什么要用native方法**

Java使用起来非常方便，然而有些层次的任务用java实现起来不容易，或者我们对程序的效率很在意时，例如：Java需要与一些底层操作系统或某些硬件交换信息时的情况。native方法正是这样一种交流机制：它为我们提供了一个非常简洁的接口，而且我们无需去了解Java应用之外的繁琐的细节。

**2. native声明的方法，对于调用者，可以当做和其他Java方法一样使用**

native method的存在并不会对其他类调用这些本地方法产生任何影响，实际上调用这些方法的其他类甚至不知道它所调用的是一个本地方法。JVM将控制调用本地方法的所有细节。



## 9.随堂复习

### 1. this关键字的使用

- this调用的结构：属性、方法；构造器

- this调用属性或方法时，理解为：当前对象或当前正在创建的对象。

  ```java
  public void setName(String name){ //当属性名和形参名同名时，必须使用this来区分
  	this.name = name;
  }
  
  public Person(String name){
      this.name = name;
  }
  ```

- this(形参列表)的方式，表示调用当前类中其他的重载的构造器。

### 2. 面向对象的特征二：继承性

- 继承性的好处
  - 减少了代码的冗余，提高了复用性；
  - 提高了扩展性
  - 为多态的使用，提供了前提。
- Java中继承性的特点
  - 局限性：类的单继承性。后续我们通过类实现接口的方式，解决单继承的局限性。
  - 支持多层继承，一个父类可以声明多个子类。
- 基础：class A extends B{}
- 理解：子类就获取了父类中声明的全部的属性、方法。可能受封装性的影响，不能直接调用。

### 3. 方法的重写（override / overwrite）

- 面试题：方法的重载与重写的区别？
  - 方法的重载：“两同一不同”
  - 方法的重写：
    - 前提：类的继承关系
    - 子类对父类中同名同参数方法的覆盖、覆写。

### 4. super关键字的使用

- super可以调用的结构：属性、方法；构造器
- super：父类的
- super调用父类的属性、方法：
  - 如果子父类中出现了同名的属性，此时使用super.的方式，表明调用的是父类中声明的属性。
  - 子类重写了父类的方法。如果子类的任何一个方法中需要调用父类被重写的方法时，需要使用super.
- super调用构造器：
  - 在子类的构造器中，首行要么使用了"this(形参列表)"，要么使用了"super(形参列表)"。

### 5. （熟悉）子类对象实例化的全过程

- 结果上来说：体现为继承性
- 过程上来说：子类调用构造器创建对象时，一定会直接或间接的调用其父类的构造器，以及父类的父类的构造器，...，直到调用到Object()的构造器。

### 6. 面向对象的特征三：多态性

- 广义上的理解：子类对象的多态性、方法的重写；方法的重载

  狭义上的理解：子类对象的多态性。

- 格式：Object obj = new String("hello");   父类的引用指向子类的对象。

- 多态的好处：减少了大量的重载的方法的定义；开闭原则

  - 举例：public boolean equals(Object obj)
  - 多态，无处不在！讲了抽象类、接口以后，会有更好的理解。

- 多态的使用：虚拟方法调用。“编译看左边，运行看右边”。属性，不存在多态性。

- 多态的逆过程：向下转型，使用强转符()。

  - 为了避免出现强转时的ClassCastException，建议()之前使用instanceOf进行判断。

### 7. Object类的使用

- 根父类
- equals()的使用
  - 重写和不重写的区别
  - 面试题： == 和 equals()
- toString()的使用
  - Object中toString()调用后，返回当前对象所属的类和地址值。
  - 开发中常常重写toString()，用于返回当前对象的属性信息。

### 8. 项目二：拼电商客户管理系统

- 编写两个类：Customer 、 CustomerList类（封装了对数组的增删改查操作）




## 10.企业真题

### 2.1 继承性

#### 1. 父类哪些成员可以被继承，属性可以被继承吗？可以或者不可以，请举下例子。（北京明**信）

父类的属性、方法可以被继承。构造器可以被子类调用。

### 2.2 重写

#### 1. 什么是Override，与Overload的区别（顺\*、软\*\*力、明\*数据、阳\*科技、中*软）

重写就是子类继承父类，子类可以对继承父类的方法进行覆写。重载就是在一个类中存在多个具有相同方法名的方法，但是参数类型或者参数个数不相同。

#### 2. Overload的方法是否可以改变返回值的类型?（新*陆）

public void method(int i){}

public int method(int j,int k){}

#### 3. 构造器Constructor是否可被override?（鸿*网络、深圳德**技、航\*\*普）

不能！构造器可以重载

#### 4. 为什么要有重载，我随便命名一个别的函数名不行吗？谈谈你是怎么理解的。（腾*）

见名知意。

### 2.3 super关键字

#### 1. super和this的区别(蚂**服)

把两个关键字各自的特点说清楚。

#### 2. this、super关键字分别代表什么?以及他们各自的使用场景和作用。（北京楚*龙）

略

### 2.4 多态

#### 1. 谈谈你对多态的理解(三*重工、江\*智能、银\*数据、君\*科技)

```
类似问法：
> Java中实现多态的机制是什么(国*电网)
> 什么是多态？（上*银行）
> Java中的多态是什么意思？（贝*）
```

略



#### 2. 多态new出来的对象跟不多态new出来的对象区别在哪？（万*智能）

Person p = new Man();  //虚方法调用。屏蔽了子类Man类特有的属性和方法。

Man m = new Man(); 



#### 3. 说说你认为多态在代码中的体现（楚*龙）

无处不在！

略

### 2.5 Object类

#### 1. ==与equals的区别（拓*思）

```
类似问法：
> 两个对象A和B，A==B，A.equals(B)有什么区别（华油**普）
```

略



#### 2. 重写equals方法要注意什么？（安**网络科技）

- 明确判定两个对象实体equals()的标准。是否需要所有的属性参与。
- 对象的属性，又是自定义的类型，此属性也需要重写equals()

#### 3. Java中所有类的父类是什么？他都有什么方法？（阿*校招）

```
相关问题：
> Object类有哪些方法？（恒*电子）
```



# 第07章_面向对象编程(高级)

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC2%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%9D%A2%E5%90%91%E5%AF%B9%E8%B1%A1%E7%BC%96%E7%A8%8B-%E7%AC%AC08%E7%AB%A0.png" alt="第2阶段：Java面向对象编程-第08章" style="zoom: 40%;" />

## 1. 关键字：static

**回顾类中的实例变量（即非static的成员变量）**

```java
class Circle{
	private double radius;
	public Circle(double radius){
        this.radius=radius;
	}
	public double findArea(){
        return Math.PI*radius*radius;
    }
}
```

创建两个Circle对象：

```java
Circle c1=new Circle(2.0);	//c1.radius=2.0
Circle c2=new Circle(3.0);	//c2.radius=3.0
```

Circle类中的变量radius是一个实例变量(instance variable)，它属于类的每一个对象，c1中的radius变化不会影响c2的radius，反之亦然。

<font color='red'>**如果想让一个成员变量被类的所有实例所共享，就用static修饰即可，称为类变量（或类属性）！**</font>

### 1.1 类属性、类方法的设计思想

当我们编写一个类时，其实就是在描述其对象的属性和行为，而并没有产生实质上的对象，只有通过new关键字才会产出对象，这时系统才会分配内存空间给对象，其方法才可以供外部调用。我们有时候希望无论是否产生了对象或无论产生了多少对象的情况下，`某些特定的数据在内存空间里只有一份`。例如，所有的中国人都有个国家名称，每一个中国人都共享这个国家名称，不必在每一个中国人的实例对象中都单独分配一个用于代表国家名称的变量。

![image-20220325213629311](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325213629311.png)



此外，==在类中声明的实例方法，在类的外面必须要先创建对象，才能调用。但是有些方法的调用者和当前类的对象无关，这样的方法通常被声明为`类方法`，由于不需要创建对象就可以调用类方法，从而简化了方法的调用。==

这里的类变量、类方法，只需要使用`static`修饰即可。所以也称为静态变量、静态方法。

### 1.2 static关键字

- 使用范围：
  - 在Java类中，可用static修饰属性、方法、代码块、内部类

- 被修饰后的成员具备以下特点：
  - **随着类的加载而加载**
  - **优先于对象存在**
  - **修饰的成员，被类内的所有对象所共享**
  - **访问权限允许时，可不创建对象，直接被类调用**

### 1.3 静态变量

#### 1.3.1 语法格式

使用static修饰的成员变量就是静态变量（或类变量、类属性）

```java
[修饰符] class 类{
	[其他修饰符] static 数据类型 变量名;
}
```

#### 1.3.2 静态变量的特点

- 静态变量的默认值规则和实例变量一样。
- 静态变量值是所有对象共享。
- 静态变量在本类中，可以在任意方法、代码块、构造器中直接使用。
- 如果权限修饰符允许，在其他类中可以通过“`类名.静态变量`”直接访问，也可以通过“`对象.静态变量`”的方式访问（但是更推荐使用类名.静态变量的方式）。
- 静态变量的get/set方法也静态的，当局部变量与静态变量`重名时`，使用“`类名.静态变量`”进行区分。

#### 1.3.3静态变量和实例变量的区别

|          | 静态变量                                       | 实例变量                             |
| -------- | ---------------------------------------------- | ------------------------------------ |
| 个数     | 在内存中只有一份，被类的多个对象所共享         | 类的每一个实例对象都保存一份实例变量 |
| 内存位置 | JDK6之前存放在方法区。JDK7之后存放在堆内存空间 | 存放在堆内存当前的对象实体中         |
| 加载时机 | 随着类的加载而加载，只加载一次                 | 随着对象的加载而加载                 |
| 调用者   | 可以被类调用，也可以被对象调用                 | 只能被对象调用                       |
| 消亡时机 | 随着类的消亡而消亡                             | 随着对象的消亡而消亡                 |



#### 1.3.4 举例

举例1：

```java
class Chinese{
    //实例变量
    String name;
    int age;
    //类变量
    static String nation;//国籍

    public Chinese() {
    }

    public Chinese(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Chinese{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nation='" + nation + '\'' +
                '}';
    }
}
public class StaticTest {
    public static void main(String[] args) {
        Chinese c1 = new Chinese("康师傅",36);
        c1.nation = "中华人民共和国";

        Chinese c2 = new Chinese("老干妈",66);

        System.out.println(c1);
        System.out.println(c2);

        System.out.println(Chinese.nation);
    }
}
```

对应的内存结构：（以经典的JDK6内存解析为例，此时静态变量存储在方法区）

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514183814514.png" alt="image-20220514183814514" style="zoom:67%;" />

举例2：

```java
package com.atguigu.keyword;

public class Employee {
    private static int total;//这里私有化，在类的外面必须使用get/set方法的方式来访问静态变量
    static String company; //这里缺省权限修饰符，是为了方便类外以“类名.静态变量”的方式访问
    private int id;
    private String name;

    public Employee() {
        total++;
        id = total;//这里使用total静态变量的值为id属性赋值
    }

    public Employee(String name) {
        this();
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        Employee.total = total;
    }

    @Override
    public String toString() {
        return "Employee{company = " + company + ",id = " + id + " ,name=" + name +"}";
    }
}
```

```java
package com.atguigu.keyword;

public class TestStaticVariable {
    public static void main(String[] args) {
        //静态变量total的默认值是0
        System.out.println("Employee.total = " + Employee.getTotal());

        Employee e1 = new Employee("张三");
        Employee e2 = new Employee("李四");
        System.out.println(e1);//静态变量company的默认值是null
        System.out.println(e2);//静态变量company的默认值是null
        System.out.println("Employee.total = " + Employee.getTotal());//静态变量total值是2

        Employee.company = "尚硅谷";
        System.out.println(e1);//静态变量company的值是尚硅谷
        System.out.println(e2);//静态变量company的值是尚硅谷

        //只要权限修饰符允许,虽然不推荐，但是也可以通过“对象.静态变量”的形式来访问
        e1.company = "超级尚硅谷";

        System.out.println(e1);//静态变量company的值是超级尚硅谷
        System.out.println(e2);//静态变量company的值是超级尚硅谷
    }
}
```

#### 1.3.4 内存解析

![image-20220104100145059](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220104100145059.png)

### 1.4 静态方法

#### 1.4.1 语法格式

用static修饰的成员方法就是静态方法。

```java
[修饰符] class 类{
	[其他修饰符] static 返回值类型 方法名(形参列表){
        方法体
    }
}
```

#### 1.4.2 静态方法的特点

- **静态方法在本类的任意方法、代码块、构造器中都可以直接被调用。**
- 只要权限修饰符允许，静态方法在其他类中可以通过“类名.静态方法“的方式调用。也可以通过”对象.静态方法“的方式调用（但是更推荐使用类名.静态方法的方式）。
- **<font color='red'>在static方法内部只能访问类的static修饰的属性或方法，不能访问类的非static的结构。</font>**
- <font color='red'>静态方法可以被子类继承，但不能被子类重写。</font>
- 静态方法的调用都只看编译时类型。
- <font color='red'>因为不需要实例就可以访问static方法，因此static方法内部不能有this，也不能有super。如果有重名问题，使用“类名.”进行区别。</font>



#### 1.4.3静态属性和静态方法的使用场景

1. 静态属性：

   ==判断当前类的多个对象实例是否共享一个成员变量，且此成员变量的值在多个对象实例都是相同的。==

   实际开发中，常将常量设置为静态的

2. 静态方法：

   ==如果一个方法中操作的是静态变量，则该方法也应该是静态的==

   实际开发中，常将工具类中的方法设置为静态的。

#### 1.4.3 举例

```java
package com.atguigu.keyword;

public class Father {
    public static void method(){
        System.out.println("Father.method");
    }

    public static void fun(){
        System.out.println("Father.fun");
    }
}
```

```java
package com.atguigu.keyword;

public class Son extends Father{
//    @Override //尝试重写静态方法，加上@Override编译报错，去掉Override不报错，但是也不是重写
    public static void fun(){
        System.out.println("Son.fun");
    }
}
```

```java
package com.atguigu.keyword;

public class TestStaticMethod {
    public static void main(String[] args) {
        Father.method();
        Son.method();//继承静态方法

        Father f = new Son();
        f.method();//执行Father类中的method
    }
}
```

### 1.5 练习

笔试题：如下程序执行会不会报错

```java
/**
 * @author 尚硅谷-宋红康
 * @create 14:30
 */
public class StaticTest {
    public static void main(String[] args) {
        Demo test = null;
        test.hello();
    }
}

class Demo{
    public static void hello(){
        System.out.println("hello!");
    }
}
```

因为静态方法不随着对象的加载而加载，所以正常输出`hello!`



练习：

编写一个类实现银行账户的概念，包含的属性有“帐号”、“密码”、“存款余额”、“利率”、“最小余额”，定义封装这些属性的方法。`账号要自动生成。`

编写主类，使用银行账户类，输入、输出3个储户的上述信息。

考虑：哪些属性可以设计成static属性。

## 2. 单例(Singleton)设计模式

### 2.1 设计模式概述

**设计模式**是在大量的`实践中总结`和`理论化`之后优选的代码结构、编程风格、以及解决问题的思考方式。设计模式免去我们自己再思考和摸索。就像是经典的棋谱，不同的棋局，我们用不同的棋谱。"套路"

经典的设计模式共有23种。每个设计模式均是特定环境下特定问题的处理方法。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520174508815.png" alt="image-20220520174508815" style="zoom: 80%;" />

> 简单工厂模式并不是23中经典模式的一种，是其中工厂方法模式的简化版

> 对软件设计模式的研究造就了一本可能是面向对象设计方面最有影响的书籍：《设计模式》：《Design Patterns: Elements of Reusable Object-Oriented Software》（即后述《设计模式》一书），由 Erich Gamma、Richard Helm、Ralph Johnson 和 John Vlissides 合著（Addison-Wesley，1995）。这几位作者常被称为"四人组（Gang of Four）"，而这本书也就被称为"四人组（或 GoF）"书。

### 2.2 何为单例模式

==所谓类的单例设计模式，就是采取一定的方法保证在整个的软件系统中，对某个类**只能存在一个对象实例**，并且该类只提供一个取得其对象实例的方法。==

### 2.3 实现思路

如果我们要让类在一个虚拟机中只能产生一个对象，我们首先必须将`类的构造器的访问权限设置为private`，这样，就不能用new操作符在类的外部产生类的对象了，但在类内部仍可以产生该类的对象。因为在类的外部开始还无法得到类的对象，`只能调用该类的某个静态方法`以返回类内部创建的对象，静态方法只能访问类中的静态成员变量，所以，指向类内部产生的`该类对象的变量也必须定义成静态的`。

### 2.4 单例模式的两种实现方式

#### 2.4.1 饿汉式

```java
class Singleton {
    // 1.私有化构造器
    private Singleton() {
    }

    // 2.内部提供一个当前类的对象
    // 4.此对象也必须静态化
    private static Singleton single = new Singleton();

    // 3.提供公共的静态的方法，返回当前类的对象
    public static Singleton getInstance() {
        return single;
    }
}

```

#### 2.4.2 懒汉式

```java
class Singleton {
    // 1.私有化构造器
    private Singleton() {
    }
    // 2.内部提供一个当前类的实例
    // 4.此实例也必须静态化
    private static Singleton single = null ;
    // 3.提供公共的静态的方法，返回当前类的对象
    public static Singleton getInstance() {
        if(single == null) {
            single = new Singleton();
        }
        return single;
    }
}

```

#### 2.4.3 饿汉式 vs 懒汉式

饿汉式：

- 特点：`立即加载`，即在使用类的时候已经将对象创建完毕。
- 优点：实现起来`简单`；没有多线程安全问题。
- 缺点：当类被加载的时候，会初始化static的实例，静态变量被创建并分配内存空间，从这以后，这个static的实例便一直占着这块内存，直到类被卸载时，静态变量被摧毁，并释放所占有的内存。因此在某些特定条件下会`耗费内存`。

懒汉式：

- 特点：`延迟加载`，即在调用静态方法时实例才被创建。
- 优点：实现起来比较简单；当类被加载的时候，static的实例未被创建并分配内存空间，当静态方法第一次被调用时，初始化实例变量，并分配内存，因此在某些特定条件下会`节约内存`。
- 缺点：在多线程环境中，这种实现方法是完全错误的，`线程不安全，可能会出现线程安全问题`，根本不能保证单例的唯一性。
  - 说明：在多线程章节，会将懒汉式改造成线程安全的模式。

### 2.5 单例模式的优点及应用场景

由于单例模式只生成一个实例，减少了`系统性能开销`，当一个对象的产生需要比较多的资源时，如读取配置、产生其他依赖对象时，则可以通过在应用启动时直接产生一个单例对象，然后永久驻留内存的方式来解决。

举例：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325222541203.png" alt="image-20220325222541203" style="zoom:67%;" />

**应用场景**

- Windows的Task Manager (任务管理器)就是很典型的单例模式

- Windows的Recycle Bin (回收站)也是典型的单例应用。在整个系统运行过程中，回收站一直维护着仅有的一个实例。

- Application 也是单例的典型应用

- 应用程序的日志应用，一般都使用单例模式实现，这一般是由于共享的日志文件一直处于打开状态，因为只

  能有一个实例去操作，否则内容不好追加。

- 数据库连接池的设计一般也是采用单例模式，因为数据库连接是一种数据库资源。

## 3. 理解main方法的语法

由于JVM需要调用类的main()方法，所以该方法的访问权限必须是public，又因为JVM在执行main()方法时不必创建对象，所以该方法必须是static的，该方法接收一个String类型的数组参数，该数组中保存执行Java命令时传递给所运行的类的参数。 

又因为main() 方法是静态的，我们不能直接访问该类中的非静态成员，必须创建该类的一个实例对象后，才能通过这个对象去访问类中的非静态成员，这种情况，我们在之前的例子中多次碰到。

**命令行参数用法举例**

```java
public class CommandPara {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }
    }
}

```

```java
//运行程序CommandPara.java
java CommandPara "Tom" "Jerry" "Shkstart"
```

```java
//输出结果
args[0] = Tom
args[1] = Jerry
args[2] = Shkstart

```

![image-20220325223215924](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325223215924.png)

IDEA工具：

（1）配置运行参数

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211228101828718.png" alt="image-20211228101828718" style="zoom: 80%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211228102022216.png" alt="image-20211228102022216" style="zoom:67%;" />

（2）运行程序

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211228102059327.png" alt="image-20211228102059327" style="zoom: 80%;" />

笔试题：

```java
//此处，Something类的文件名叫OtherThing.java
class Something {
    public static void main(String[] something_to_do) {        
        System.out.println("Do something ...");
    }
}


//上述程序是否可以正常编译、运行？ 

```

## 4. 类的成员之四：代码块

### 4.1 静态代码块

==如果成员变量想要初始化的值不是一个硬编码的常量值，而是需要通过复杂的计算或读取文件、或读取运行环境信息等方式才能获取的一些值，该怎么办呢？此时，可以考虑代码块（或初始化块）。==

- 代码块(或初始化块)的`作用`：
  * <font color='cornflowerblue'>初始化类的信息</font>
  * <font color='cornflowerblue'>如果想要为静态变量初始化，可以直接在静态变量的声明后面直接赋值，也可以使用静态代码块。</font>
- 代码块(或初始化块)的`分类`：

  - 一个类中代码块若有修饰符，则只能被static修饰，称为静态代码块(static block)

  - 没有使用static修饰的，为非静态代码块。
- 静态代码块随着类的加载而执行



#### 4.1.1 语法格式

在代码块的前面加static，就是静态代码块。

```java
【修饰符】 class 类{
	static{
        静态代码块
    }
}
```

#### 4.1.2 静态代码块的特点

1. 可以有输出语句。
2. 可以对类的属性、类的声明进行初始化操作。
3. <font color='red'>不可以对非静态的属性初始化。即：不可以调用非静态的属性和方法。</font>
4. 若有多个静态的代码块，那么按照从上到下的顺序依次执行。
5. 静态代码块的执行要先于非静态代码块。
6. 静态代码块随着类的加载而加载，且只执行一次。

```java
package com.atguigu.keyword;

public class Chinese {
//    private static String country = "中国";

    private static String country;
    private String name;

    {
        System.out.println("非静态代码块，country = " + country);
    }

    static {
        country = "中国";
        System.out.println("静态代码块");
    }

    public Chinese(String name) {
        this.name = name;
    }
}
```

```java
package com.atguigu.keyword;

public class TestStaticBlock {
    public static void main(String[] args) {
        Chinese c1 = new Chinese("张三");
        Chinese c2 = new Chinese("李四");
    }
}

```

### 4.2 非静态代码块

#### 4.2.1 语法格式

```java
【修饰符】 class 类{
    {
        非静态代码块
    }
    【修饰符】 构造器名(){
    	// 实例初始化代码
    }
    【修饰符】 构造器名(参数列表){
        // 实例初始化代码
    }
}
```

#### 4.2.2 非静态代码块的作用

<font color='cornflowerblue'>和构造器一样，也是用于实例变量的初始化等操作。</font>

#### 4.2.3 非静态代码块的意义

<font color='cornflowerblue'>如果多个重载的构造器有公共代码，并且这些代码都是先于构造器其他代码执行的，那么可以将这部分代码抽取到非静态代码块中，减少冗余代码。</font>

#### 4.2.4 非静态代码块的执行特点

1. 可以有输出语句。

   2. 可以对类的属性、类的声明进行初始化操作。

   3. 除了调用非静态的结构外，还可以调用静态的变量或方法。

   4. 若有多个非静态的代码块，那么按照从上到下的顺序依次执行。

   5. 每次创建对象的时候，都会执行一次。且先于构造器执行。

### 4.3 举例

**举例1：**

（1）声明User类，

- 包含属性：username（String类型），password（String类型），registrationTime（long类型），私有化

- 包含get/set方法，其中registrationTime没有set方法

- 包含无参构造，
  - 输出“新用户注册”，
  - registrationTime赋值为当前系统时间，
  - username就默认为当前系统时间值，
  - password默认为“123456”

- 包含有参构造(String username, String password)，
  - 输出“新用户注册”，
  - registrationTime赋值为当前系统时间，
  - username和password由参数赋值

- 包含public String getInfo()方法，返回：“用户名：xx，密码：xx，注册时间：xx”

（2）编写测试类，测试类main方法的代码如下：

```java
    public static void main(String[] args) {
        User u1 = new User();
        System.out.println(u1.getInfo());

        User u2 = new User("song","8888");
        System.out.println(u2.getInfo());
    }
```

如果不用非静态代码块，User类是这样的：

```java
package com.atguigu.block.no;

public class User {
    private String username;
    private String password;
    private long registrationTime;

    public User() {
        System.out.println("新用户注册");
        registrationTime = System.currentTimeMillis();
        username = registrationTime+"";
        password = "123456";
    }

    public User(String username,String password) {
        System.out.println("新用户注册");
        registrationTime = System.currentTimeMillis();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }
    public String getInfo(){
        return "用户名：" + username + "，密码：" + password + "，注册时间：" + registrationTime;
    }
}
```

如果提取构造器公共代码到非静态代码块，User类是这样的：

```java
package com.atguigu.block.use;

public class User {
    private String username;
    private String password;
    private long registrationTime;

    {
        System.out.println("新用户注册");
        registrationTime = System.currentTimeMillis();
    }

    public User() {
        username = registrationTime+"";
        password = "123456";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRegistrationTime() {
        return registrationTime;
    }
    public String getInfo(){
        return "用户名：" + username + "，密码：" + password + "，注册时间：" + registrationTime;
    }
}
```

**举例2：**

```java
private static DataSource dataSource = null;

static{
	InputStream is = null;
	try {
		is = DBCPTest.class.getClassLoader().getResourceAsStream("dbcp.properties");
		Properties pros = new Properties();
		pros.load(is);
		//调用BasicDataSourceFactory的静态方法，获取数据源。
		dataSource = BasicDataSourceFactory.createDataSource(pros);
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}		
	}		
}
```

### 4.4 小结：实例变量赋值顺序

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325230208941.png" alt="image-20220325230208941" style="zoom:67%;" />

### 4.5 练习

练习1：分析加载顺序

```java
class Root{
	static{
		System.out.println("Root的静态初始化块");
	}
	{
		System.out.println("Root的普通初始化块");
	}
	public Root(){
		System.out.println("Root的无参数的构造器");
	}
}
class Mid extends Root{
	static{
		System.out.println("Mid的静态初始化块");
	}
	{
		System.out.println("Mid的普通初始化块");
	}
	public Mid(){
		System.out.println("Mid的无参数的构造器");
	}
	public Mid(String msg){
		//通过this调用同一类中重载的构造器
		this();
		System.out.println("Mid的带参数构造器，其参数值："
			+ msg);
	}
}
class Leaf extends Mid{
	static{
		System.out.println("Leaf的静态初始化块");
	}
	{
		System.out.println("Leaf的普通初始化块");
	}	
	public Leaf(){
		//通过super调用父类中有一个字符串参数的构造器
		super("尚硅谷");
		System.out.println("Leaf的构造器");
	}
}
public class LeafTest{
	public static void main(String[] args){
		new Leaf(); 
		//new Leaf();
	}
}
```

练习2：分析加载顺序

```java
class Father {
	static {
		System.out.println("11111111111");
	}
	{
		System.out.println("22222222222");
	}

	public Father() {
		System.out.println("33333333333");

	}

}

public class Son extends Father {
	static {
		System.out.println("44444444444");
	}
	{
		System.out.println("55555555555");
	}
	public Son() {
		System.out.println("66666666666");
	}


	public static void main(String[] args) { 
		System.out.println("77777777777");
		System.out.println("************************");
		new Son();
		System.out.println("************************");

		new Son();
		System.out.println("************************");
		new Father();
	}

}

```

练习3：

```java
package com.atguigu05.field.interview;

/**
 * @author 尚硅谷-宋红康
 * @create 16:04
 */
public class Test04 {
    public static void main(String[] args) {
        Zi zi = new Zi();
    }
}
class Fu{
    private static int i = getNum("（1）i");
    private int j = getNum("（2）j");
    static{
        print("（3）父类静态代码块");
    }
    {
        print("（4）父类非静态代码块，又称为构造代码块");
    }
    Fu(){
        print("（5）父类构造器");
    }
    public static void print(String str){
        System.out.println(str + "->" + i);
    }
    public static int getNum(String str){
        print(str);
        return ++i;
    }
}
class Zi extends Fu{
    private static int k = getNum("（6）k");
    private int h = getNum("（7）h");
    static{
        print("（8）子类静态代码块");
    }
    {
        print("（9）子类非静态代码块，又称为构造代码块");
    }
    Zi(){
        print("（10）子类构造器");
    }
    public static void print(String str){
        System.out.println(str + "->" + k);
    }
    public static int getNum(String str){
        print(str);
        return ++k;
    }
}

```



## 5. final关键字

### 5.1 final的意义

final：最终的，不可更改的

### 5.2 final的使用

#### 5.2.1 final修饰类

<font color='red'>表示这个类不能被继承，没有子类。内存地址不能改变。提高安全性，提高程序的可读性。</font>

例如：String类、System类、StringBuffer类

```java
final class Eunuch{//太监类
	
}
class Son extends Eunuch{//错误
	
}
```

#### 5.2.2 final修饰方法

<font color='red'>表示这个方法不能被子类重写。</font>

例如：Object类中的getClass()

```java
class Father{
	public final void method(){
		System.out.println("father");
	}
}
class Son extends Father{
	public void method(){//错误
		System.out.println("son");
	}
}
```

#### 5.2.3 final修饰变量

<font color='red'>final修饰某个变量（成员变量或局部变量），一旦赋值，它的值就不能被修改，即常量</font>。常量名建议使用大写字母。

例如：final double MY_PI = 3.14;

> 如果某个成员变量用final修饰后，没有set方法，并且必须初始化（可以显式赋值、或在初始化块赋值、实例变量还可以在构造器中赋值）

- 修饰成员变量

```java
public final class Test {
    public static int totalNumber = 5;
    public final int ID;

    public Test() {
        ID = ++totalNumber; // 可在构造器中给final修饰的“变量”赋值
    }
    public static void main(String[] args) {
        Test t = new Test();
        System.out.println(t.ID);
    }
}

```

- 修饰局部变量：

```java
public class TestFinal {
    public static void main(String[] args){
        final int MIN_SCORE ;
        MIN_SCORE = 0;
        final int MAX_SCORE = 100;
        MAX_SCORE = 200; //非法
    }
}
```

- 错误演示：

```java
class A {
    private final String INFO = "atguigu";  //声明常量

    public void print() {
        //The final field A.INFO cannot be  assigned
        //INFO = "尚硅谷"; 
    }
}

```

### 5.3 笔试题

题1：排错

```java
public class Something {
    public int addOne(final int x) {
        return ++x;
        // return x + 1;
    }
}
```

题2：排错

```java
public class Something {
    public static void main(String[] args) {
        Other o = new Other();
        new Something().addOne(o);
    }
    public void addOne(final Other o) {
        // o = new Other();
        o.i++;
    }
}
class Other {
    public int i;
}

```

## 6. 抽象类与抽象方法(或abstract关键字)

### 6.1 由来

举例1：

随着继承层次中一个个新子类的定义，类变得越来越具体，而父类则更一般，更通用。类的设计应该保证父类和子类能够共享特征。==有时将一个父类设计得非常抽象，以至于它没有具体的实例，这样的类叫做抽象类。==

![image-20220325231608838](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325231608838.png)

举例2：

我们声明一些几何图形类：圆、矩形、三角形类等，发现这些类都有共同特征：求面积、求周长。那么这些共同特征应该抽取到一个共同父类：几何图形类中。但是这些方法在父类中又`无法给出具体的实现`，而是应该交给子类各自具体实现。那么父类在声明这些方法时，`就只有方法签名，没有方法体`，<font color='red'>我们把没有方法体的方法称为**抽象方法**</font>。Java语法规定，<font color='red'>包含抽象方法的类必须是**抽象类**。</font>

### 6.2 语法格式

* **抽象类**：被abstract修饰的类。
* **抽象方法**：被abstract修饰没有方法体的方法。

抽象类的语法格式

```java
[权限修饰符] abstract class 类名{
    
}
[权限修饰符] abstract class 类名 extends 父类{
    
}
```

抽象方法的语法格式

```java
[其他修饰符] abstract 返回值类型 方法名([形参列表]);
```

> 注意：抽象方法没有方法体

![image-20220517204707255](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220517204707255.png)

代码举例：

```java
public abstract class Animal {
    public abstract void eat();
}
```

```java
public class Cat extends Animal {
    public void eat (){
      	System.out.println("小猫吃鱼和猫粮"); 
    }
}
```

```java
public class CatTest {
 	 public static void main(String[] args) {
        // 创建子类对象
        Cat c = new Cat(); 
       
        // 调用eat方法
        c.eat();
  	}
}
```

此时的方法重写，是子类对父类抽象方法的完成实现，我们将这种方法重写的操作，也叫做**实现方法**。

### 6.3 使用说明

1. <font color='red'>抽象类**不能创建对象**，如果创建，编译无法通过而报错。只能创建其非抽象子类的对象。</font>

   > 理解：假设创建了抽象类的对象，调用抽象的方法，而抽象方法没有具体的方法体，没有意义。
   >
   > 抽象类是用来被继承的，抽象类的子类必须重写父类的抽象方法，并提供方法体。若没有重写全部的抽象方法，仍为抽象类。

2. <font color='red'>抽象类中，也有构造方法，是供子类创建对象时，初始化父类成员变量使用的。</font>

   > 理解：子类的构造方法中，有默认的super()或手动的super(实参列表)，需要访问父类构造方法。

3. <font color='red'>抽象类中，不一定包含抽象方法，但是有抽象方法的类必定是抽象类。</font>

   > 理解：未包含抽象方法的抽象类，目的就是不想让调用者创建该类对象，通常用于某些特殊的类结构设计。

4. <font color='red'>抽象类的子类，必须重写抽象父类中**所有的**抽象方法，否则，编译无法通过而报错。除非该子类也是抽象类。 </font>

   > 理解：假设不重写所有抽象方法，则类中可能包含抽象方法。那么创建对象后，调用抽象的方法，没有意义。

### 6.4 注意事项

* abstract只能修饰类和方法，即抽象类的抽象方法。

- <font color='red'>不能用abstract修饰变量、代码块、构造器；</font>

- <font color='red'>不能用abstract修饰私有方法、静态方法、final的方法、final的类。</font>

### 6.5 应用举例1

![image-20220325232847872](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325232847872.png)

在航运公司系统中，Vehicle类需要定义两个方法分别`计算运输工具的燃料效率`和`行驶距离`。

**问题：**卡车(Truck)和驳船(RiverBarge)的燃料效率和行驶距离的计算方法完全不同。Vehicle类不能提供计算方法，但子类可以。

**解决方案：**Java允许类设计者指定：超类声明一个方法但不提供实现，该方法的实现由子类提供。这样的方法称为抽象方法。有一个或更多抽象方法的类称为抽象类。

```java
//Vehicle是一个抽象类，有两个抽象方法。
public abstract class Vehicle{
	public abstract double calcFuelEfficiency();	//计算燃料效率的抽象方法
	public abstract double calcTripDistance();	//计算行驶距离的抽象方法
}
public class Truck extends Vehicle{
	public double calcFuelEfficiency( )   { //写出计算卡车的燃料效率的具体方法   }
	public double calcTripDistance( )    {  //写出计算卡车行驶距离的具体方法   }
}
public class RiverBarge extends Vehicle{
	 public double calcFuelEfficiency( ) { //写出计算驳船的燃料效率的具体方法  }
	 public double calcTripDistance( )  {  //写出计算驳船行驶距离的具体方法}
}

```

### 6.6 应用举例2：模板方法设计模式(TemplateMethod)

抽象类体现的就是一种模板模式的设计，抽象类作为多个子类的通用模板，子类在抽象类的基础上进行扩展、改造，但子类总体上会保留抽象类的行为方式。

**解决的问题**：

- 当功能内部一部分实现是确定的，另一部分实现是不确定的。这时可以把不确定的部分暴露出去，让子类去实现。

- 换句话说，在软件开发中实现一个算法时，整体步骤很固定、通用，这些步骤已经在父类中写好了。但是某些部分易变，易变部分可以抽象出来，供不同子类实现。这就是一种模板模式。

**类比举例：**英语六级模板

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503145003315.png" alt="image-20220503145003315" style="zoom: 80%;" />

制作月饼的模板：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220517205013997.png" alt="image-20220517205013997" style="zoom: 50%;" />

举例1：

```java
abstract class Template {
    public final void getTime() {
        long start = System.currentTimeMillis();
        code();
        long end = System.currentTimeMillis();
        System.out.println("执行时间是：" + (end - start));
    }

    public abstract void code();
}

class SubTemplate extends Template {
    public void code() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(i);
        }
    }
}

```

举例2：

```java
package com.atguigu.java;
//抽象类的应用：模板方法的设计模式
public class TemplateMethodTest {

	public static void main(String[] args) {
		BankTemplateMethod btm = new DrawMoney();
		btm.process();

		BankTemplateMethod btm2 = new ManageMoney();
		btm2.process();
	}
}
abstract class BankTemplateMethod {
	// 具体方法
	public void takeNumber() {
		System.out.println("取号排队");
	}

	public abstract void transact(); // 办理具体的业务 //钩子方法

	public void evaluate() {
		System.out.println("反馈评分");
	}

	// 模板方法，把基本操作组合到一起，子类一般不能重写
	public final void process() {
		this.takeNumber();

		this.transact();// 像个钩子，具体执行时，挂哪个子类，就执行哪个子类的实现代码

		this.evaluate();
	}
}

class DrawMoney extends BankTemplateMethod {
	public void transact() {
		System.out.println("我要取款！！！");
	}
}

class ManageMoney extends BankTemplateMethod {
	public void transact() {
		System.out.println("我要理财！我这里有2000万美元!!");
	}
}

```

模板方法设计模式是编程中经常用得到的模式。各个框架、类库中都有他的影子，比如常见的有：

- 数据库访问的封装

- Junit单元测试

- JavaWeb的Servlet中关于doGet/doPost方法调用

- Hibernate中模板程序

- Spring中JDBCTemlate、HibernateTemplate等

### 6.7 思考与练习

**思考：**

问题1：为什么抽象类不可以使用final关键字声明？

问题2：一个抽象类中可以定义构造器吗？

问题3：是否可以这样理解：抽象类就是比普通类多定义了抽象方法，除了不能直接进行类的实例化操作之外，并没有任何的不同？

**练习1：**

编写一个Employee类，声明为抽象类，包含如下三个属性：name，id，salary。提供必要的构造器和抽象方法：work()。

对于Manager类来说，他既是员工，还具有奖金(bonus)的属性。

请使用继承的思想，设计CommonEmployee类和Manager类，要求类中提供必要的方法进行属性访问。

**练习2：软件外包公司外派管理**

有一家软件外包公司，可以外派开发人员，该公司有两个角色：普通开发人员Developer和项目经理Manager。他们的关系如下图：

![image-20220504164925878](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220504164925878.png)

普通开发人员的工作内容是“开发项目”，项目经理的工作内容是“项目管理”。对外的报价是普通开发人员每天500,元，超过60天每天400元。项目经理每天800元，超过60天每天700元。

有一家银行需要1名项目经理、2名开发人员，现场开发90天，计算银行需要付给软件公司的总金额。

提示：创建数组 Employee[] emps = new Employee[3]。其中存储驻场的3名员工。

**练习3：**

创建父类Shape，包含绘制形状的抽象方法draw()。

创建Shape的子类Circle和Rectangle，重写draw()方法，绘制圆形和矩形。

绘制多个圆形和矩形。

**练习4：**

1、声明抽象父类Person，包含抽象方法public abstract void eat();
2、声明子类中国人Chinese，重写抽象方法，打印用筷子吃饭
3、声明子类美国人American，重写抽象方法，打印用刀叉吃饭
4、声明子类印度人Indian，重写抽象方法，打印用手抓饭
5、声明测试类PersonTest，创建Person数组，存储各国人对象，并遍历数组，调用eat()方法

**练习5：工资系统设计**

编写工资系统，实现不同类型员工(多态)的按月发放工资。如果当月出现某个Employee对象的生日，则将该雇员的工资增加100元。

实验说明：

（1）定义一个Employee类，该类包含：

private成员变量name,number,birthday，其中birthday 为MyDate类的对象；

abstract方法earnings()；

toString()方法输出对象的name,number和birthday。

（2）MyDate类包含:

private成员变量year,month,day ；

toDateString()方法返回日期对应的字符串：xxxx年xx月xx日

（3）定义SalariedEmployee类继承Employee类，实现按月计算工资的员工处理。该类包括：private成员变量monthlySalary；

实现父类的抽象方法earnings(),该方法返回monthlySalary值；toString()方法输出员工类型信息及员工的name，number,birthday。

（4）参照SalariedEmployee类定义HourlyEmployee类，实现按小时计算工资的员工处理。该类包括：

private成员变量wage和hour；

实现父类的抽象方法earnings(),该方法返回wage*hour值；

toString()方法输出员工类型信息及员工的name，number,birthday。

（5）定义PayrollSystem类，创建Employee变量数组并初始化，该数组存放各类雇员对象的引用。利用循环结构遍历数组元素，输出各个对象的类型,name,number,birthday,以及该对象生日。当键盘输入本月月份值时，如果本月是某个Employee对象的生日，还要输出增加工资信息。

```java
//提示：
//定义People类型的数组People c1[]=new People[10];
//数组元素赋值
c1[0]=new People("John","0001",20);
c1[1]=new People("Bob","0002",19);
//若People有两个子类Student和Officer，则数组元素赋值时，可以使父类类型的数组元素指向子类。
c1[0]=new Student("John","0001",20,85.0);
c1[1]=new Officer("Bob","0002",19,90.5);

```

## 7. 接口(interface)

### 7.1 类比

生活中大家每天都在用USB接口，那么USB接口与我们今天要学习的接口有什么相同点呢？

 	USB，（Universal Serial Bus，通用串行总线）是Intel公司开发的总线架构，使得在计算机上添加串行设备（鼠标、键盘、打印机、扫描仪、摄像头、充电器、MP3机、手机、数码相机、移动硬盘等）非常容易。

其实，不管是电脑上的USB插口，还是其他设备上的USB插口都只是`遵循了USB规范`的一种具体设备而已。

![bbcc80f541000c71b81650cfaa770c86](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/bbcc80f541000c71b81650cfaa770c86.png)

只要设备遵循USB规范的，那么就可以与电脑互联，并正常通信。至于这个设备、电脑是哪个厂家制造的，内部是如何实现的，我们都无需关心。

Java的软件系统会有很多模块组成，那么各个模块之间也应该采用这种`面向接口`的`低耦合`，为系统提供更好的可扩展性和可维护性。

### 7.2 概述

==接口就是定义了一组规则的规范。接口定义了一组方法或属性，而不提供实现细节。这使得不同的类或对象可以实现同一个接口，从而实现多态性和代码重用。==

* 例如：电脑都预留了可以插入USB设备的USB接口，USB接口具备基本的数据传输的开启功能和关闭功能。你能不能用USB进行连接，或是否具备USB通信功能，就看你能否遵循USB接口规范

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220517211517846.png" alt="image-20220517211517846" style="zoom:67%;" />

- 例如：Java程序是否能够连接使用某种数据库产品，那么要看该数据库产品能否实现Java设计的JDBC规范

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325235434103.png" alt="image-20220325235434103" style="zoom:67%;" />



> 接口的本质是契约、标准、规范，就像我们的法律一样。制定好后大家都要遵守。

### 7.3 定义格式

接口的定义，它与定义类方式相似，但是使用 `interface` 关键字。它也会被编译成.class文件，但一定要明确它并不是类，而是另外一种引用数据类型。

> 引用数据类型：数组，类，枚举，接口，注解，记录。

#### 7.3.1 接口的声明格式

```java
[修饰符] interface 接口名{
    //接口的成员列表：
    // 公共的静态常量
    // 公共的抽象方法
    
    // 公共的默认方法（JDK1.8以上）
    // 公共的静态方法（JDK1.8以上）
    // 私有方法（JDK1.9以上）
}
```

示例代码：

```java
package com.atguigu.interfacetype;

public interface USB3{
    //静态常量
    long MAX_SPEED = 500*1024*1024;//500MB/s

    //抽象方法
    void in();
    void out();

    //默认方法
    default void start(){
        System.out.println("开始");
    }
    default void stop(){
        System.out.println("结束");
    }

    //静态方法
    static void show(){
        System.out.println("USB 3.0可以同步全速地进行读写操作");
    }
}
```

#### 7.3.2 接口的成员说明

**在JDK8.0 之前**，接口中只允许出现：

（1）公共的静态的常量：其中`public static final`可以省略

（2）公共的抽象的方法：其中`public abstract`可以省略

> 理解：接口是从多个相似类中抽象出来的规范，不需要提供具体实现

**在JDK8.0 时**，接口中允许声明`默认方法`和`静态方法`：

（3）公共的默认的方法：其中public 可以省略，建议保留，但是default不能省略

（4）公共的静态的方法：其中public 可以省略，建议保留，但是static不能省略

==在接口中定义的方法默认情况下是抽象的，即没有方法体。但是在 Java 8 中，可以使用默认方法和静态方法来为接口中的方法添加方法体。默认方法使用 default 关键字定义，并提供实现。静态方法使用 static 关键字定义，也可以提供具体的实现。这些方法在实现接口时可以被继承或覆盖，从而提高了接口的灵活性。==

==接口中的静态方法可以不被实现，但这样做没有任何意义。在接口中声明静态方法时，它们的实现是和接口绑定的（随着接口的加载而加载），所有实现该接口的类都可以调用接口中定义的静态方法，但是不能在类中覆盖接口中的静态方法，因为它们是静态的。因此，实现类中可以不去重写这些静态方法。实现接口时需要实现的，是接口中定义的抽象方法。==

**在JDK9.0 时**，接口又增加了：

（5）私有方法

​		除此之外，接口中没有构造器，没有初始化块，因为接口中没有成员变量需要动态初始化。

### 7.4 接口的使用规则

**1、类实现接口（implements）**

接口**不能创建对象**，但是可以被类实现（`implements` ，类似于被继承）。

类与接口的关系为实现关系，即**类实现接口**，该类可以称为接口的实现类。实现的动作类似继承，格式相仿，只是关键字不同，实现使用 ` implements`关键字。

```java
【修饰符】 class 实现类  implements 接口{
	// 重写接口中抽象方法【必须】，当然如果实现类是抽象类，那么可以不重写
  	// 重写接口中默认方法【可选】
}

【修饰符】 class 实现类 extends 父类 implements 接口{
    // 重写接口中抽象方法【必须】，当然如果实现类是抽象类，那么可以不重写
  	// 重写接口中默认方法【可选】
}
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514163212312.png" alt="image-20220514163212312" style="zoom:80%;" />

注意：

1. 如果接口的实现类是非抽象类，那么必须`重写接口中所有抽象方法`。

2. 默认方法可以选择保留，也可以重写。

   > 重写时，default单词就不要再写了，它只用于在接口中表示默认方法，到类中就没有默认方法的概念了

3. 接口中的静态方法不能被继承也不能被重写

举例：

```java
interface USB{		// 
	public void start() ;
	public void stop() ;	
}
class Computer{
	public static void show(USB usb){	
		usb.start() ;
		System.out.println("=========== USB 设备工作 ========") ;
		usb.stop() ;
	}
};
class Flash implements USB{
	public void start(){	// 重写方法
		System.out.println("U盘开始工作。") ;
	}
	public void stop(){		// 重写方法
		System.out.println("U盘停止工作。") ;
	}
};
class Print implements USB{
	public void start(){	// 重写方法
		System.out.println("打印机开始工作。") ;
	}
	public void stop(){		// 重写方法
		System.out.println("打印机停止工作。") ;
	}
};
public class InterfaceDemo{
	public static void main(String args[]){
		Computer.show(new Flash()) ;
		Computer.show(new Print()) ;

		c.show(new USB(){
			public void start(){
				System.out.println("移动硬盘开始运行");
			}
			public void stop(){
				System.out.println("移动硬盘停止运行");
			}
		});
	}
};
```

**2、接口的多实现（implements）**

之前学过，在继承体系中，一个类只能继承一个父类。而对于接口而言，一个类是可以实现多个接口的，这叫做接口的`多实现`。并且，一个类能继承一个父类，同时实现多个接口。

实现格式：

```java
【修饰符】 class 实现类  implements 接口1，接口2，接口3。。。{
	// 重写接口中所有抽象方法【必须】，当然如果实现类是抽象类，那么可以不重写
  	// 重写接口中默认方法【可选】
}

【修饰符】 class 实现类 extends 父类 implements 接口1，接口2，接口3。。。{
    // 重写接口中所有抽象方法【必须】，当然如果实现类是抽象类，那么可以不重写
  	// 重写接口中默认方法【可选】
}
```

> 接口中，有多个抽象方法时，实现类必须重写所有抽象方法。**如果抽象方法有重名的，只需要重写一次**。

举例：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514163311418.png" alt="image-20220514163311418" style="zoom:80%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220325235321778.png" alt="image-20220325235321778" style="zoom:67%;" />

![1562216188519](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1562216188519.png)

定义多个接口：

```java
package com.atguigu.interfacetype;

public interface A {
    void showA();
}
```

```java
package com.atguigu.interfacetype;

public interface B {
    void showB();
}
```

定义实现类：

```java
package com.atguigu.interfacetype;

public class C implements A,B {
    @Override
    public void showA() {
        System.out.println("showA");
    }

    @Override
    public void showB() {
        System.out.println("showB");
    }
}

```

测试类

```java
package com.atguigu.interfacetype;

public class TestC {
    public static void main(String[] args) {
        C c = new C();
        c.showA();
        c.showB();
    }
}
```

**3、接口的多继承(extends)**

<font color='red'>一个接口能继承另一个或者多个接口，接口的继承也使用 `extends` 关键字，子接口继承父接口的方法。</font>

定义父接口：

```java
package com.atguigu.interfacetype;

public interface Chargeable {
    void charge();
    void in();
    void out();
}
```

定义子接口：

```java
package com.atguigu.interfacetype;

public interface UsbC extends Chargeable,USB3 {
    void reverse();
}
```

定义子接口的实现类：

```java
package com.atguigu.interfacetype;

public class TypeCConverter implements UsbC {
    @Override
    public void reverse() {
        System.out.println("正反面都支持");
    }

    @Override
    public void charge() {
        System.out.println("可充电");
    }

    @Override
    public void in() {
        System.out.println("接收数据");
    }

    @Override
    public void out() {
        System.out.println("输出数据");
    }
}
```

>所有父接口的抽象方法都有重写。
>
>方法签名相同的抽象方法只需要实现一次。

**4、接口与实现类对象构成多态引用**

<font color='red'>实现类实现接口，类似于子类继承父类，因此，接口类型的变量与实现类的对象之间，也可以构成多态引用</font>。通过接口类型的变量调用方法，最终执行的是你new的实现类对象实现的方法体。

接口的不同实现类：

```java
package com.atguigu.interfacetype;

public class Mouse implements USB3 {
    @Override
    public void out() {
        System.out.println("发送脉冲信号");
    }

    @Override
    public void in() {
        System.out.println("不接收信号");
    }
}
```

```java
package com.atguigu.interfacetype;

public class KeyBoard implements USB3{
    @Override
    public void in() {
        System.out.println("不接收信号");
    }

    @Override
    public void out() {
        System.out.println("发送按键信号");
    }
}

```

测试类

```java
package com.atguigu.interfacetype;

public class TestComputer {
    public static void main(String[] args) {
        Computer computer = new Computer();
        USB3 usb = new Mouse();
        computer.setUsb(usb);
        usb.start();
        usb.out();
        usb.in();
        usb.stop();
        System.out.println("--------------------------");

        usb = new KeyBoard();
        computer.setUsb(usb);
        usb.start();
        usb.out();
        usb.in();
        usb.stop();
        System.out.println("--------------------------");

        usb = new MobileHDD();
        computer.setUsb(usb);
        usb.start();
        usb.out();
        usb.in();
        usb.stop();
    }
}
```

**5、使用接口的静态成员**

接口不能直接创建对象，但是可以通过接口名直接调用接口的静态方法和静态常量。

```java
package com.atguigu.interfacetype;

public class TestUSB3 {
    public static void main(String[] args) {
        //通过“接口名.”调用接口的静态方法 (JDK8.0才能开始使用)
        USB3.show();
        //通过“接口名.”直接使用接口的静态常量
        System.out.println(USB3.MAX_SPEED);
    }
}
```

**6、使用接口的非静态方法**

* 对于接口的静态方法，直接使用“`接口名.`”进行调用即可
  * 也只能使用“接口名."进行调用，不能通过实现类的对象进行调用
* 对于接口的抽象方法、默认方法，只能通过实现类对象才可以调用
  * 接口不能直接创建对象，只能创建实现类的对象

```java
package com.atguigu.interfacetype;

public class TestMobileHDD {
    public static void main(String[] args) {
        //创建实现类对象
        MobileHDD b = new MobileHDD();

        //通过实现类对象调用重写的抽象方法，以及接口的默认方法，如果实现类重写了就执行重写的默认方法，如果没有重写，就执行接口中的默认方法
        b.start();
        b.in();
        b.stop();

        //通过接口名调用接口的静态方法
//        MobileHDD.show();
//        b.show();
        Usb3.show();
    }
}
```

### 7.5 JDK8中相关冲突问题

#### 7.5.1 默认方法冲突问题

**（1）类优先原则**

当一个类，既继承一个父类，又实现若干个接口时，父类中的成员方法与接口中的抽象方法重名，子类就近选择执行父类的成员方法。代码如下：

定义接口：

```java
package com.atguigu.interfacetype;

public interface Friend {
    default void date(){//约会
        System.out.println("吃喝玩乐");
    }
}
```

定义父类：

```java
package com.atguigu.interfacetype;

public class Father {
    public void date(){//约会
        System.out.println("爸爸约吃饭");
    }
}
```

定义子类：

```java
package com.atguigu.interfacetype;

public class Son extends Father implements Friend {
    @Override
    public void date() {
        //(1)不重写默认保留父类的
        //(2)调用父类被重写的
//        super.date();
        //(3)保留父接口的
//        Friend.super.date();
        //(4)完全重写
        System.out.println("跟康师傅学Java");
    }
}
```

定义测试类：

```java
package com.atguigu.interfacetype;

public class TestSon {
    public static void main(String[] args) {
        Son s = new Son();
        s.date();
    }
}
```

**（2）接口冲突（左右为难）**

- 当一个类同时实现了多个父接口，而多个父接口中包含方法签名相同的默认方法时，怎么办呢？

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E9%80%89%E6%8B%A9%E5%9B%B0%E9%9A%BE.jpg)

无论你多难抉择，最终都是要做出选择的。

声明接口：

```java
package com.atguigu.interfacetype;

public interface BoyFriend {
    default void date(){//约会
        System.out.println("神秘约会");
    }
}
```

选择保留其中一个，通过“`接口名.super.方法名`"的方法选择保留哪个接口的默认方法。

```java
package com.atguigu.interfacetype;

public class Girl implements Friend,BoyFriend{

    @Override
    public void date() {
        //(1)保留其中一个父接口的
//        Friend.super.date();
//        BoyFriend.super.date();
        //(2)完全重写
        System.out.println("跟康师傅学Java");
    }

}
```

测试类

```java
package com.atguigu.interfacetype;

public class TestGirl {
    public static void main(String[] args) {
        Girl girl = new Girl();
        girl.date();
    }
}
```

- 当一个子接口同时继承了多个接口，而多个父接口中包含方法签名相同的默认方法时，怎么办呢？

另一个父接口：

```java
package com.atguigu.interfacetype;

public interface USB2 {
    //静态常量
    long MAX_SPEED = 60*1024*1024;//60MB/s

    //抽象方法
    void in();
    void out();

    //默认方法
    public default void start(){
        System.out.println("开始");
    }
    public default void stop(){
        System.out.println("结束");
    }

    //静态方法
    public static void show(){
        System.out.println("USB 2.0可以高速地进行读写操作");
    }
}
```

子接口：

```java
package com.atguigu.interfacetype;

public interface USB extends USB2,USB3 {
    @Override
    default void start() {
        System.out.println("Usb.start");
    }

    @Override
    default void stop() {
        System.out.println("Usb.stop");
    }
}

```

> 小贴士：
>
> 子接口重写默认方法时，default关键字可以保留。
>
> 子类重写默认方法时，default关键字不可以保留。

#### 7.5.2 常量冲突问题

- 当子类继承父类又实现父接口，而父类中存在与父接口常量同名的成员变量，并且该成员变量名在子类中仍然可见。
- 当子类同时实现多个接口，而多个接口存在相同同名常量。

此时在子类中想要引用父类或父接口的同名的常量或成员变量时，就会有冲突问题。

父类和父接口：

```java
package com.atguigu.interfacetype;

public class SuperClass {
    int x = 1;
}
```

```java
package com.atguigu.interfacetype;

public interface SuperInterface {
    int x = 2;
    int y = 2;
}
```

```java
package com.atguigu.interfacetype;

public interface MotherInterface {
    int x = 3;
}
```

子类：

```java
package com.atguigu.interfacetype;

public class SubClass extends SuperClass implements SuperInterface,MotherInterface {
    public void method(){
//        System.out.println("x = " + x);//模糊不清
        System.out.println("super.x = " + super.x);
        System.out.println("SuperInterface.x = " + SuperInterface.x);
        System.out.println("MotherInterface.x = " + MotherInterface.x);
        System.out.println("y = " + y);//没有重名问题，可以直接访问
    }
}
```

### 7.6 接口的总结与面试题

- 接口本身不能创建对象，只能创建接口的实现类对象，接口类型的变量可以与实现类对象构成多态引用。
- 声明接口用interface，接口的成员声明有限制：
  - （1）公共的静态常量
  - （2）公共的抽象方法
  - （3）公共的默认方法（JDK8.0 及以上）
  - （4）公共的静态方法（JDK8.0 及以上）
  - （5）私有方法（JDK9.0 及以上）
- 类可以实现接口，关键字是implements，而且支持多实现。如果实现类不是抽象类，就必须实现接口中所有的抽象方法。如果实现类既要继承父类又要实现父接口，那么继承（extends）在前，实现（implements）在后。
- 接口可以继承接口，关键字是extends，而且支持多继承。
- 接口的默认方法可以选择重写或不重写。如果有冲突问题，另行处理。子类重写父接口的默认方法，要去掉default，子接口重写父接口的默认方法，不要去掉default。
- 接口的静态方法不能被继承，也不能被重写。接口的静态方法只能通过“接口名.静态方法名”进行调用。

**面试题**

**1、为什么接口中只能声明公共的静态的常量？**

因为接口是标准规范，那么在规范中需要声明一些底线边界值，当实现者在实现这些规范时，不能去随意修改和触碰这些底线，否则就有“危险”。

例如：USB1.0规范中规定最大传输速率是1.5Mbps，最大输出电流是5V/500mA

​           USB3.0规范中规定最大传输速率是5Gbps(500MB/s)，最大输出电流是5V/900mA

例如：尚硅谷学生行为规范中规定学员，早上8:25之前进班，晚上21:30之后离开等等。

**2、为什么JDK8.0 之后允许接口定义静态方法和默认方法呢？因为它违反了接口作为一个抽象标准定义的概念。**

`静态方法`：因为之前的标准类库设计中，有很多Collection/Colletions或者Path/Paths这样成对的接口和类，后面的类中都是静态方法，而这些静态方法都是为前面的接口服务的，那么这样设计一对API，不如把静态方法直接定义到接口中使用和维护更方便。

`默认方法`：（1）我们要在已有的老版接口中提供新方法时，如果添加抽象方法，就会涉及到原来使用这些接口的类就会有问题，那么为了保持与旧版本代码的兼容性，只能允许在接口中定义默认方法实现。比如：Java8中对Collection、List、Comparator等接口提供了丰富的默认方法。（2）当我们接口的某个抽象方法，在很多实现类中的实现代码是一样的，此时将这个抽象方法设计为默认方法更为合适，那么实现类就可以选择重写，也可以选择不重写。

**3、为什么JDK1.9要允许接口定义私有方法呢？因为我们说接口是规范，规范是需要公开让大家遵守的。**

**私有方法**：因为有了默认方法和静态方法这样具有具体实现的方法，那么就可能出现多个方法由共同的代码可以抽取，而这些共同的代码抽取出来的方法又只希望在接口内部使用，所以就增加了私有方法。

### 7.7 接口与抽象类之间的对比

抽象类和接口都是用于实现多态性的机制，但它们有一些区别。抽象类可以包含成员变量和非抽象方法的实现，而接口只能包含常量和抽象方法的声明。此外，一个类只能继承一个抽象类，但可以实现多个接口。

![image-20220328002053452](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220328002053452.png)

> 在开发中，常看到一个类不是去继承一个已经实现好的类，而是要么继承抽象类，要么实现接口。

### 7.8 练习

**笔试题：**排错

```java
interface A {
    int x = 0;
}
class B {
    int x = 1;
}
class C extends B implements A {
    public void pX() {
        System.out.println(x);
    }
    public static void main(String[] args) {
        new C().pX();
    }
}

```

**笔试题：**排错

```java
interface Playable {
    void play();
}

interface Bounceable {
    void play();
}
 
interface Rollable extends Playable, Bounceable {
    Ball ball = new Ball("PingPang");

}

class Ball implements Rollable {
    private String name;

    public String getName() {
        return name;
    }

    public Ball(String name) {
        this.name = name;
    }

    public void play() {
        ball = new Ball("Football");
        System.out.println(ball.getName());
    }
}

```

**练习1：**

定义一个接口用来实现两个对象的比较。

```java
interface CompareObject{
	//若返回值是 0 , 代表相等; 若为正数，代表当前对象大；负数代表当前对象小
	public int compareTo(Object o);  
}
```

定义一个Circle类，声明redius属性，提供getter和setter方法

定义一个ComparableCircle类，继承Circle类并且实现CompareObject接口。在ComparableCircle类中给出接口中方法compareTo的实现体，用来比较两个圆的半径大小。

定义一个测试类InterfaceTest，创建两个ComparableCircle对象，调用compareTo方法比较两个类的半径大小。

思考：参照上述做法定义矩形类Rectangle和ComparableRectangle类，在ComparableRectangle类中给出compareTo方法的实现，比较两个矩形的面积大小。

**练习2：交通工具案例**

阿里的一个工程师，声明的属性和方法如下：

![image-20220504172547709](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220504172547709.png)

其中，有一个乘坐交通工具的方法takingVehicle()，在此方法中调用交通工具的run()。为了出行方便，他买了一辆捷安特自行车、一辆雅迪电动车和一辆奔驰轿车。这里涉及到的相关类及接口关系如下：

![image-20220504172918861](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220504172918861.png)

其中，电动车增加动力的方式是充电，轿车增加动力的方式是加油。在具体交通工具的run()中调用其所在类的相关属性信息。

请编写相关代码，并测试。

提示：创建Vehicle[]数组，保存阿里工程师的三辆交通工具，并分别在工程师的takingVehicle()中调用。

## 8. 内部类（InnerClass)

### 8.1 概述

#### 8.1.1 什么是内部类

将一个类A定义在另一个类B里面，里面的那个类A就称为`内部类（InnerClass）`，类B则称为`外部类（OuterClass）`。

#### 8.1.2 为什么要声明内部类呢

具体来说，当一个事物A的内部，还有一个部分需要一个完整的结构B进行描述，而这个内部的完整的结构B又只为外部事物A提供服务，不在其他地方单独使用，那么整个内部的完整结构B最好使用内部类。

总的来说，遵循`高内聚、低耦合`的面向对象开发原则。

#### 8.1.3 内部类的分类

根据内部类声明的位置（如同变量的分类），我们可以分为：

* **成员内部类**：==直接声明在外部类里面。==
* **局部内部类**：==声明在方法内、构造器内或代码块中的内部类==

![image-20221124223912529](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221124223912529.png)

### 8.2 成员内部类

#### 8.2.1 概述

如果成员内部类中不使用外部类的非静态成员，那么通常将内部类声明为静态内部类，否则声明为非静态内部类。

**语法格式：**

```java
[修饰符] class 外部类{
    [其他修饰符] [static] class 内部类{
    }
}
```

**成员内部类的使用特征，概括来讲有如下两种角色：**

- 成员内部类作为`类的成员的角色`：
  - 和外部类不同，Inner class还可以声明为private或protected；
  - 可以调用外部类的结构。（注意：在静态内部类中不能使用外部类的非静态成员）
  - Inner class 可以声明为static的，但此时就不能再使用外层类的非static的成员变量；
- 成员内部类作为`类的角色`：
  - 可以在内部定义属性、方法、构造器等结构
  - 可以继承自己的想要继承的父类，实现自己想要实现的父接口们，和外部类的父类和父接口无关
  - 可以声明为abstract类 ，因此可以被其它的内部类继承
  - 可以声明为final的，表示不能被继承
  - 编译以后生成OuterClass$InnerClass.class字节码文件（也适用于局部内部类）

注意点：

2. 外部类访问成员内部类的成员，需要“内部类.成员”或“内部类对象.成员”的方式

3. 成员内部类可以直接使用外部类的所有成员，包括私有的数据

4. 当想要在外部类的静态成员部分使用内部类时，可以考虑内部类声明为静态的

#### 8.2.2 创建成员内部类对象

- 实例化静态内部类

```
外部类名.静态内部类名 变量 = 外部类名.静态内部类名();
变量.非静态方法();
```

- 实例化非静态内部类

```
外部类名 变量1 = new 外部类();
外部类名.非静态内部类名 变量2 = 变量1.new 非静态内部类名();
变量2.非静态方法();
```

#### 8.2.3 举例

```java
public class TestMemberInnerClass {
    public static void main(String[] args) {
        //创建静态内部类实例，并调用方法
        Outer.StaticInner inner = new Outer.StaticInner();
        inner.inFun();
        //调用静态内部类静态方法
        Outer.StaticInner.inMethod();

        System.out.println("*****************************");
        
        //创建非静态内部类实例（方式1），并调用方法
        Outer outer = new Outer();
        Outer.NoStaticInner inner1 = outer.new NoStaticInner();
        inner1.inFun();

        //创建非静态内部类实例（方式2）
        Outer.NoStaticInner inner2 = outer.getNoStaticInner();
        inner1.inFun();
    }
}
class Outer{
    private static String a = "外部类的静态a";
    private static String b  = "外部类的静态b";
    private String c = "外部类对象的非静态c";
    private String d = "外部类对象的非静态d";

    static class StaticInner{
        private static String a ="静态内部类的静态a";
        private String c = "静态内部类对象的非静态c";
        public static void inMethod(){
            System.out.println("Inner.a = " + a);
            System.out.println("Outer.a = " + Outer.a);
            System.out.println("b = " + b);
        }
        public void inFun(){
            System.out.println("Inner.inFun");
            System.out.println("Outer.a = " + Outer.a);
            System.out.println("Inner.a = " + a);
            System.out.println("b = " + b);
            System.out.println("c = " + c);
//            System.out.println("d = " + d);//不能访问外部类的非静态成员
        }
    }

    class NoStaticInner{
        private String a = "非静态内部类对象的非静态a";
        private String c = "非静态内部类对象的非静态c";

        public void inFun(){
            System.out.println("NoStaticInner.inFun");
            System.out.println("Outer.a = " + Outer.a);
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println("Outer.c = " + Outer.this.c);
            System.out.println("c = " + c);
            System.out.println("d = " + d);
        }
    }


    public NoStaticInner getNoStaticInner(){
        return new NoStaticInner();
    }
}
```

### 8.3 局部内部类

#### 8.3.1 非匿名局部内部类

语法格式：

```java
[修饰符] class 外部类{
    [修饰符] 返回值类型  方法名(形参列表){
            [final/abstract] class 内部类{
    	}
    }    
}
```

- 编译后有自己的独立的字节码文件，只不过在内部类名前面冠以外部类名、$符号、编号。
  - 这里有编号是因为同一个外部类中，不同的方法中存在相同名称的局部内部类

* 和成员内部类不同的是，它前面不能有权限修饰符等
* 局部内部类如同局部变量一样，有作用域
* 局部内部类中是否能访问外部类的非静态的成员，取决于所在的方法

举例：

```java
/**
 * ClassName: TestLocalInner
 * @Author 尚硅谷-宋红康
 * @Create 17:19
 * @Version 1.0
 */
public class TestLocalInner {
    public static void main(String[] args) {
        Outer.outMethod();
        System.out.println("-------------------");

        Outer out = new Outer();
        out.outTest();
        System.out.println("-------------------");

        Runner runner = Outer.getRunner();
        runner.run();

    }
}
class Outer{

    public static void outMethod(){
        System.out.println("Outer.outMethod");
        final String c = "局部变量c";
        class Inner{
            public void inMethod(){
                System.out.println("Inner.inMethod");
                System.out.println(c);
            }
        }

        Inner in = new Inner();
        in.inMethod();
    }

    public void outTest(){
        class Inner{
            public void inMethod1(){
                System.out.println("Inner.inMethod1");
            }
        }

        Inner in = new Inner();
        in.inMethod1();
    }

    public static Runner getRunner(){
        class LocalRunner implements Runner{
            @Override
            public void run() {
                System.out.println("LocalRunner.run");
            }
        }
        return new LocalRunner();
    }

}
interface Runner{
    void run();
}
```

#### 8.3.2 匿名内部类

匿名内部类是在Java中创建一个没有名称的内部类的方式。通常，内部类是嵌套在另一个类中的，并且可以像普通类一样访问另一个类的成员。但是，在某些情况下，您可能想创建一个只需要使用一次的类。这时候，就可以使用匿名内部类。

```java
new 父类([实参列表]){
    重写方法...
}
```

```java
new 父接口(){
    重写方法...
}
```

举例1：使用匿名内部类的对象直接调用方法：

```java
interface A{
	void a();
}
public class Test{
    public static void main(String[] args){
    	new A(){
			@Override
			public void a() {
				System.out.println("aaaa");
			}
    	}.a();
    }
}
```

举例2：通过父类或父接口的变量多态引用匿名内部类的对象

```java
interface A{
	void a();
}
public class Test{
    public static void main(String[] args){
    	A obj = new A(){
			@Override
			public void a() {
				System.out.println("aaaa");
			}
    	};
    	obj.a();
    }
}
```

举例3：匿名内部类的对象作为实参

```java
interface A{
	void method();
}
public class Test{
    public static void test(A a){
    	a.method();
    }
    
    public static void main(String[] args){
    	test(new A(){

			@Override
			public void method() {
				System.out.println("aaaa");
			}
    	});
    }   
}
```

### 8.4 练习

练习：判断输出结果为何？

```java
public class Test {
    public Test() {
        Inner s1 = new Inner();
        s1.a = 10;
        Inner s2 = new Inner();
        s2.a = 20;
        Test.Inner s3 = new Test.Inner();
        System.out.println(s3.a);
    }
    class Inner {
        public int a = 5;
    }
    public static void main(String[] args) {
        Test t = new Test();
        Inner r = t.new Inner();
        System.out.println(r.a);
    }
}

```

练习2：

编写一个匿名内部类，它继承Object，并在匿名内部类中，声明一个方法public void test()打印尚硅谷。

请编写代码调用这个方法。

```java
package com.atguigu.test01;

public class Test01 {
	public static void main(String[] args) {
		new Object(){
			public void test(){
				System.out.println("尚硅谷");
			}
		}.test();
	}
}

```

## 9. 枚举类

### 9.1 概述

- 枚举类型本质上也是一种类，只不过是这个类的对象是有限的、固定的几个，不能让用户随意创建。

- 枚举类的例子举不胜举：
  - `星期`：Monday(星期一)......Sunday(星期天)
  - `性别`：Man(男)、Woman(女)
  - `月份`：January(1月)......December(12月)
  - `季节`：Spring(春节)......Winter(冬天)
  - `三原色`：red(红色)、green(绿色)、blue(蓝色)
  - `支付方式`：Cash（现金）、WeChatPay（微信）、Alipay(支付宝)、BankCard(银行卡)、CreditCard(信用卡)
  - `就职状态`：Busy(忙碌)、Free(空闲)、Vocation(休假)、Dimission(离职)
  - `订单状态`：Nonpayment（未付款）、Paid（已付款）、Fulfilled（已配货）、Delivered（已发货）、Checked（已确认收货）、Return（退货）、Exchange（换货）、Cancel（取消）
  - `线程状态`：创建、就绪、运行、阻塞、死亡
- **若枚举只有一个对象, 则可以作为一种单例模式的实现方式。**
- 枚举类的实现：
  - 在JDK5.0 之前，需要程序员自定义枚举类型。
  - 在JDK5.0 之后，Java支持`enum`关键字来快速定义枚举类型。

### 9.2 定义枚举类（JDK5.0 之前）

在JDK5.0 之前如何声明枚举类呢？

* `私有化`类的构造器，保证不能在类的外部创建其对象
* 在类的内部创建枚举类的实例。声明为：`public static final` ，对外暴露这些常量对象
* 对象如果有`实例变量`，应该声明为`private final`（建议，不是必须），并在构造器中初始化

示例代码：

```java
class Season{
    private final String SEASONNAME;//季节的名称
    private final String SEASONDESC;//季节的描述
    private Season(String seasonName,String seasonDesc){
        this.SEASONNAME = seasonName;
        this.SEASONDESC = seasonDesc;
    }
    public static final Season SPRING = new Season("春天", "春暖花开");
    public static final Season SUMMER = new Season("夏天", "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天", "秋高气爽");
    public static final Season WINTER = new Season("冬天", "白雪皑皑");

    @Override
    public String toString() {
        return "Season{" +
                "SEASONNAME='" + SEASONNAME + '\'' +
                ", SEASONDESC='" + SEASONDESC + '\'' +
                '}';
    }
}
class SeasonTest{
    public static void main(String[] args) {
        System.out.println(Season.AUTUMN);
    }
}
```

### 9.3 定义枚举类（JDK5.0 之后）

#### 9.3.1 enum关键字声明枚举

```java
【修饰符】 enum 枚举类名{
    常量对象列表
}

【修饰符】 enum 枚举类名{
    常量对象列表;
    
    对象的实例变量列表;
}
```

举例1：

```java
package com.atguigu.enumeration;

public enum Week {
    MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
}
```

```java
public class TestEnum {
	public static void main(String[] args) {
		Season spring = Season.SPRING;
		System.out.println(spring);
	}
}
```

#### 9.3.2 enum方式定义的要求和特点

* <font color='red'>枚举类的常量对象列表必须在枚举类的首行，因为是常量，所以建议大写。<
* 列出的实例系统会自动添加 public static final 修饰。
* 如果常量对象列表后面没有其他代码，那么“；”可以省略，否则不可以省略“；”。
* 编译器给枚举类默认提供的是private的无参构造，如果枚举类需要的是无参构造，就不需要声明，写常量对象列表时也不用加参数
* 如果枚举类需要的是有参构造，需要手动定义，有参构造的private可以省略，调用有参构造的方法就是在常量对象名后面加(实参列表)就可以。
* 枚举类默认继承的是java.lang.Enum类，因此不能再继承其他的类型。
* JDK5.0 之后switch，提供支持枚举类型，case后面可以写枚举常量名，无需添加枚举类作为限定。

举例2：

```java
public enum SeasonEnum {
    SPRING("春天","春风又绿江南岸"),
    SUMMER("夏天","映日荷花别样红"),
    AUTUMN("秋天","秋水共长天一色"),
    WINTER("冬天","窗含西岭千秋雪");

    private final String seasonName;
    private final String seasonDesc;
    
    private SeasonEnum(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }
    public String getSeasonName() {
        return seasonName;
    }
    public String getSeasonDesc() {
        return seasonDesc;
    }
}

```

举例3：

```java
package com.atguigu.enumeration;

public enum Week {
    MONDAY("星期一"),
    TUESDAY("星期二"),
    WEDNESDAY("星期三"),
    THURSDAY("星期四"),
    FRIDAY("星期五"),
    SATURDAY("星期六"),
    SUNDAY("星期日");

    private final String description;

    private Week(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() +":"+ description;
    }
}
```

```java
package com.atguigu.enumeration;

public class TestWeek {
    public static void main(String[] args) {
        Week week = Week.MONDAY;
        System.out.println(week);

        switch (week){
            case MONDAY:
                System.out.println("怀念周末，困意很浓");break;
            case TUESDAY:
                System.out.println("进入学习状态");break;
            case WEDNESDAY:
                System.out.println("死撑");break;
            case THURSDAY:
                System.out.println("小放松");break;
            case FRIDAY:
                System.out.println("又信心满满");break;
            case SATURDAY:
                System.out.println("开始盼周末，无心学习");break;
            case SUNDAY:
                System.out.println("一觉到下午");break;
        }
    }
}
```

> 经验之谈：
>
> 开发中，当需要定义一组常量时，强烈建议使用枚举类。

### 9.4 enum中常用方法

```
String toString(): 默认返回的是常量名（对象名），可以继续手动重写该方法！
    
static 枚举类型[] values():返回枚举类型的对象数组。该方法可以很方便地遍历所有的枚举值，是一个静态方法
    
static 枚举类型 valueOf(String name)：可以把一个字符串转为对应的枚举类对象。要求字符串必须是枚举类对象的“名字”。如不是，会有运行时异常：IllegalArgumentException。
    
String name():得到当前枚举常量的名称。建议优先使用toString()。
    
int ordinal():返回当前枚举常量的次序号，默认从0开始

```

举例：

```java
package com.atguigu.enumeration;

import java.util.Scanner;

public class TestEnumMethod {
    public static void main(String[] args) {
        //values()
        Week[] values = Week.values();
        for (int i = 0; i < values.length; i++) {
            //ordinal()、name()
            System.out.println((values[i].ordinal()+1) + "->" + values[i].name());
        }
        System.out.println("------------------------");

        Scanner input = new Scanner(System.in);
        System.out.print("请输入星期值：");
        int weekValue = input.nextInt();
        Week week = values[weekValue-1];
        //toString()
        System.out.println(week);

        System.out.print("请输入星期名：");
        String weekName = input.next();
        //valueOf()
        week = Week.valueOf(weekName);
        System.out.println(week);

        input.close();
    }
}
```

### 9.5 实现接口的枚举类

- 和普通 Java 类一样，枚举类可以实现一个或多个接口
- 若每个枚举值在调用实现的接口方法呈现相同的行为方式，则只要统一实现该方法即可。
- 若需要每个枚举值在调用实现的接口方法呈现出不同的行为方式，则可以让每个枚举值分别来实现该方法

语法：

```java
//1、枚举类可以像普通的类一样，实现接口，并且可以多个，但要求必须实现里面所有的抽象方法！
enum A implements 接口1，接口2{
	//抽象方法的实现
}

//2、如果枚举类的常量可以继续重写抽象方法!
enum A implements 接口1，接口2{
    常量名1(参数){
        //抽象方法的实现或重写
    },
    常量名2(参数){
        //抽象方法的实现或重写
    },
    //...
}
```

举例：

```java
interface Info{
	void show();
}

//使用enum关键字定义枚举类
enum Season1 implements Info{
	//1. 创建枚举类中的对象,声明在enum枚举类的首位
	SPRING("春天","春暖花开"){
		public void show(){
			System.out.println("春天在哪里？");
		}
	},
	SUMMER("夏天","夏日炎炎"){
		public void show(){
			System.out.println("宁静的夏天");
		}
	},
	AUTUMN("秋天","秋高气爽"){
		public void show(){
			System.out.println("秋天是用来分手的季节");
		}
	},
	WINTER("冬天","白雪皑皑"){
		public void show(){
			System.out.println("2002年的第一场雪");
		}
	};
	
	//2. 声明每个对象拥有的属性:private final修饰
	private final String SEASON_NAME;
	private final String SEASON_DESC;
	
	//3. 私有化类的构造器
	private Season1(String seasonName,String seasonDesc){
		this.SEASON_NAME = seasonName;
		this.SEASON_DESC = seasonDesc;
	}
	
	public String getSEASON_NAME() {
		return SEASON_NAME;
	}

	public String getSEASON_DESC() {
		return SEASON_DESC;
	}
}
```

## 10. 注解(Annotation)

### 10.1 注解概述

#### 10.1.1 什么是注解

注解（Annotation）是从`JDK5.0`开始引入，以“`@注解名`”在代码中存在。例如：

```java
@Override
```

```java
@Deprecated
```

```java
@SuppressWarnings(value=”unchecked”)
```

Annotation 可以像修饰符一样被使用，可用于修饰包、类、构造器、方法、成员变量、参数、局部变量的声明。还可以添加一些参数值，这些信息被保存在 Annotation 的 “name=value” 对中。

注解可以在类编译、运行时进行加载，体现不同的功能。

#### 10.1.2 注解与注释

注解也可以看做是一种注释，通过使用 Annotation，程序员可以在不改变原有逻辑的情况下，在源文件中嵌入一些补充信息。但是，注解，不同于单行注释和多行注释。

- 对于单行注释和多行注释是给程序员看的。
- 而注解是可以被编译器或其他程序读取的。程序还可以根据注解的不同，做出相应的处理。

#### 10.1.3 注解的重要性

在JavaSE中，注解的使用目的比较简单，例如标记过时的功能，忽略警告等。在`JavaEE/Android中注解占据了更重要的角色`，例如用来配置应用程序的任何切面，代替JavaEE旧版中所遗留的`繁冗代码`和`XML配置`等。

未来的开发模式都是基于注解的，JPA是基于注解的，Spring2.5以上都是基于注解的，Hibernate3.x以后也是基于注解的，Struts2有一部分也是基于注解的了。`注解是一种趋势`，一定程度上可以说：`框架 = 注解 + 反射 + 设计模式`。

### 10.2 常见的Annotation作用

**示例1：生成文档相关的注解**

```java
@author 标明开发该类模块的作者，多个作者之间使用,分割
@version 标明该类模块的版本
@see 参考转向，也就是相关主题
@since 从哪个版本开始增加的
@param 对方法中某参数的说明，如果没有参数就不能写
@return 对方法返回值的说明，如果方法的返回值类型是void就不能写
@exception 对方法可能抛出的异常进行说明 ，如果方法没有用throws显式抛出的异常就不能写
```

```java
package com.annotation.javadoc;
/**
 * @author 尚硅谷-宋红康
 * @version 1.0
 * @see Math.java
 */
public class JavadocTest {
	/**
	 * 程序的主方法，程序的入口
	 * @param args String[] 命令行参数
	 */
	public static void main(String[] args) {
	}
	
	/**
	 * 求圆面积的方法
	 * @param radius double 半径值
	 * @return double 圆的面积
	 */
	public static double getArea(double radius){
		return Math.PI * radius * radius;
	}
}

```

**示例2：在编译时进行格式检查(JDK内置的三个基本注解)**

`@Override`: 限定重写父类方法，该注解只能用于方法

`@Deprecated`: 用于表示所修饰的元素(类，方法等)已过时。通常是因为所修饰的结构危险或存在更好的选择

`@SuppressWarnings`: 抑制编译器警告

```java
package com.annotation.javadoc;
 
public class AnnotationTest{
 
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		int a = 10;
	}
	@Deprecated
	public void print(){
		System.out.println("过时的方法");
	}
 
	@Override
	public String toString() {
		return "重写的toString方法()";
	}
}

```

**示例3：跟踪代码依赖性，实现替代配置文件功能**

- Servlet3.0提供了注解(annotation)，使得不再需要在web.xml文件中进行Servlet的部署。

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) { }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
	}  
}

```

```xml
 <servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.servlet.LoginServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>

```

- Spring框架中关于“事务”的管理

```java
@Transactional(propagation=Propagation.REQUIRES_NEW,isolation=Isolation.READ_COMMITTED,readOnly=false,timeout=3)
public void buyBook(String username, String isbn) {
	//1.查询书的单价
    int price = bookShopDao.findBookPriceByIsbn(isbn);
    //2. 更新库存
    bookShopDao.updateBookStock(isbn);	
    //3. 更新用户的余额
    bookShopDao.updateUserAccount(username, price);
}

```

```xml
<!-- 配置事务属性 -->
<tx:advice transaction-manager="dataSourceTransactionManager" id="txAdvice">
       <tx:attributes>
       <!-- 配置每个方法使用的事务属性 -->
       <tx:method name="buyBook" propagation="REQUIRES_NEW" 
	 isolation="READ_COMMITTED"  read-only="false"  timeout="3" />
       </tx:attributes>
</tx:advice>

```

### 10.3 三个最基本的注解

#### 10.3.1 @Override

- 用于检测被标记的方法为有效的重写方法，如果不是，则报编译错误！


- 只能标记在方法上。


- 它会被编译器程序读取。


#### 10.3.2 @Deprecated

- 用于表示被标记的数据已经过时，不推荐使用。


- 可以用于修饰 属性、方法、构造、类、包、局部变量、参数。


- 它会被编译器程序读取。


#### 10.3.3 @SuppressWarnings

- 抑制编译警告。当我们不希望看到警告信息的时候，可以使用 SuppressWarnings 注解来抑制警告信息


- 可以用于修饰类、属性、方法、构造、局部变量、参数


- 它会被编译器程序读取。

- 可以指定的警告类型有（了解）
  - all，抑制所有警告
  - unchecked，抑制与未检查的作业相关的警告
  - unused，抑制与未用的程式码及停用的程式码相关的警告
  - deprecation，抑制与淘汰的相关警告
  - nls，抑制与非 nls 字串文字相关的警告
  - null，抑制与空值分析相关的警告
  - rawtypes，抑制与使用 raw 类型相关的警告
  - static-access，抑制与静态存取不正确相关的警告
  - static-method，抑制与可能宣告为 static 的方法相关的警告
  - super，抑制与置换方法相关但不含 super 呼叫的警告
  - ...

示例代码：

```java
package com.atguigu.annotation;

import java.util.ArrayList;

public class TestAnnotation {
    @SuppressWarnings("all")
    public static void main(String[] args) {
        int i;

        ArrayList list = new ArrayList();
        list.add("hello");
        list.add(123);
        list.add("world");

        Father f = new Son();
        f.show();
        f.methodOl();
    }
}

class Father{
    @Deprecated
    void show() {
        System.out.println("Father.show");
    }
    void methodOl() {
        System.out.println("Father Method");
    }
}

class Son extends Father{
/*	@Override
	void method01() {
		System.out.println("Son Method");
	}*/
}
```

### 10.4 元注解

JDK1.5在java.lang.annotation包定义了4个标准的meta-annotation类型，它们被用来提供对其它 annotation类型作说明。

（1）**@Target：**用于描述注解的使用范围

* 可以通过枚举类型ElementType的10个常量对象来指定
* TYPE，METHOD，CONSTRUCTOR，PACKAGE.....

（2）**@Retention：**用于描述注解的生命周期

* 可以通过枚举类型RetentionPolicy的3个常量对象来指定
* SOURCE（源代码）、CLASS（字节码）、RUNTIME（运行时）
* `唯有RUNTIME阶段才能被反射读取到`。

（3）**@Documented**：表明这个注解应该被 javadoc工具记录。

（4）**@Inherited：**允许子类继承父类中的注解

示例代码：

```java
package java.lang;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}
```

```java
package java.lang;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressWarnings {
    String[] value();
}
```

```java
package java.lang;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface Deprecated {
}
```

> 拓展：元数据
>
> String name = "Tom";

### 10.5 自定义注解的使用

一个完整的注解应该包含三个部分：
（1）声明
（2）使用
（3）读取

#### 10.5.1 声明自定义注解

```java
【元注解】
【修饰符】 @interface 注解名{
    【成员列表】
}
```

* 自定义注解可以通过四个元注解@Retention,@Target，@Inherited,@Documented，分别说明它的声明周期，使用位置，是否被继承，是否被生成到API文档中。
* Annotation 的成员在 Annotation 定义中以无参数有返回值的抽象方法的形式来声明，我们又称为配置参数。返回值类型只能是八种基本数据类型、String类型、Class类型、enum类型、Annotation类型、以上所有类型的数组
* 可以使用 default 关键字为抽象方法指定默认返回值
* 如果定义的注解含有抽象方法，那么使用时必须指定返回值，除非它有默认值。格式是“方法名 = 返回值”，如果只有一个抽象方法需要赋值，且方法名为value，可以省略“value=”，所以如果注解只有一个抽象方法成员，建议使用方法名value。

```java
package com.atguigu.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String value();
}
```

```java
package com.atguigu.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String columnName();
    String columnType();
}
```

#### 10.5.2 使用自定义注解

```java
package com.atguigu.annotation;

@Table("t_stu")
public class Student {
    @Column(columnName = "sid",columnType = "int")
    private int id;
    @Column(columnName = "sname",columnType = "varchar(20)")
    private String name;

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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

```

#### 10.5.3 读取和处理自定义注解

自定义注解必须配上注解的信息处理流程才有意义。

我们自己定义的注解，只能使用反射的代码读取。所以自定义注解的声明周期必须是RetentionPolicy.RUNTIME。

具体的使用见`《尚硅谷_宋红康_第17章_反射机制.md》`。

### 10.6 JUnit单元测试

#### 10.6.1 测试分类

**黑盒测试：**不需要写代码，给输入值，看程序是否能够输出期望的值。 

**白盒测试：**需要写代码的。关注程序具体的执行流程。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220511181800694.png" alt="image-20220511181800694" style="zoom: 50%;" />

![image-20220524102038600](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220524102038600.png)

#### 10.6.2 JUnit单元测试介绍

JUnit 是由 Erich Gamma 和 Kent Beck 编写的一个测试框架（regression testing framework），供Java开发人员编写单元测试之用。

**JUnit测试是程序员测试，即所谓白盒测试，因为程序员知道被测试的软件如何（How）完成功能和完成什么样（What）的功能。**

要使用JUnit，必须在项目的编译路径中`引入JUnit的库`，即相关的.class文件组成的jar包。jar就是一个压缩包，压缩包都是开发好的第三方（Oracle公司第一方，我们自己第二方，其他都是第三方）工具类，都是以class文件形式存在的。

#### 10.6.3 引入本地JUnit.jar

第1步：在项目中File-Project Structure中操作：添加Libraries库

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211228180938922.png" alt="image-20211228180938922" style="zoom:80%;" />

![image-20221002195547325](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221002195547325.png)

其中，junit-libs包内容如下：

![image-20220813005206452](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813005206452.png)

第2步：选择要在哪些module中应用JUnit库

![image-20220813005511062](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813005511062.png)

第3步：检查是否应用成功

![image-20220813005729233](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813005729233.png)

**注意Scope：选择Compile，否则编译时，无法使用JUnit。**

第4步：下次如果有新的模块要使用该libs库，这样操作即可

![image-20220813005944022](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813005944022.png)

![image-20220813010018152](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813010018152.png)

![image-20220813010055217](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813010055217.png)

![image-20220813010124381](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813010124381.png)

#### 10.6.4 编写和运行@Test单元测试方法

JUnit4版本，要求@Test标记的方法必须满足如下要求：

- 所在的类必须是public的，非抽象的，包含唯一的无参构造器。
- @Test标记的方法本身必须是public，非抽象的，非静态的，void无返回值，()无参数的。

```java
package com.atguigu.junit;

import org.junit.Test;

public class TestJUnit {
    @Test
    public void test01(){
        System.out.println("TestJUnit.test01");
    }

    @Test
    public void test02(){
        System.out.println("TestJUnit.test02");
    }

    @Test
    public void test03(){
        System.out.println("TestJUnit.test03");
    }
}
```

![image-20220106152412245](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220106152412245.png)

#### 10.6.5 设置执行JUnit用例时支持控制台输入

**1. 设置数据：**

默认情况下，在单元测试方法中使用Scanner时，并不能实现控制台数据的输入。需要做如下设置：

在`idea64.exe.vmoptions配置文件`中加入下面一行设置，重启idea后生效。

```properties
-Deditable.java.test.console=true
```

**2. 配置文件位置：**

![image-20220813011625546](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813011625546.png)

![image-20220813011642180](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813011642180.png)

添加完成之后，重启IDEA即可。

**3. 如果上述位置设置不成功，需要继续修改如下位置**

修改位置1：IDEA安装目录的bin目录（例如：`D:\develop_tools\IDEA\IntelliJ IDEA 2022.1.2\bin`）下的idea64.exe.vmoptions文件。 

修改位置2：C盘的用户目录`C:\Users\用户名\AppData\Roaming\JetBrains\IntelliJIdea2022.1` 下的idea64.exe.vmoptions`件。

#### 10.6.6 定义test测试方法模板

选中自定义的模板组，点击”+”（1.Live Template）来定义模板。

![image-20211229100040505](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20211229100040505.png)

## 11. 包装类

### 11.1 为什么需要包装类

Java提供了两个类型系统，`基本数据类型`与`引用数据类型`，如果要使基本数据类型具备引用数据类型的相关特征（封装、继承、多态），就可以使用基本数据类型的包装类。

```java
//情况1：方法形参
Object类的equals(Object obj)

//情况2：方法形参
ArrayList类的add(Object obj)
//没有如下的方法：
add(int number)
add(double d)
add(boolean b)

//情况3：泛型
Set<T>
List<T>
Cllection<T>
Map<K,V>
```

### 11.2 有哪些包装类

Java针对八种基本数据类型定义了相应的引用类型：包装类（封装类）。有了类的特点，就可以调用类中的方法，Java才是真正的面向对象。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220329001912486.png" alt="image-20220329001912486" style="zoom:80%;" />

封装以后的，内存结构对比：

```java
public static void main(String[] args){
	int num = 520;
	Integer obj = new Integer(520);
}
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514163725830.png" alt="image-20220514163725830" style="zoom:80%;" />

### 11.3 自定义包装类

```java
public class MyInteger {
    int value;

    public MyInteger() {
    }

    public MyInteger(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

```

### 11.4 包装类与基本数据类型间的转换

#### 11.4.1 装箱

 **装箱：把基本数据类型转为包装类对象**

> 转为包装类的对象，是为了使用专门为对象设计的API和特性

基本数值---->包装对象

```java
Integer obj1 = new Integer(4);//使用构造函数函数
Float f = new Float(“4.56”);
Long l = new Long(“asdf”);  //NumberFormatException

Integer obj2 = Integer.valueOf(4);//使用包装类中的valueOf方法
```

#### 11.4.2 拆箱

**拆箱：把包装类对象拆为基本数据类型**

> 转为基本数据类型，一般是因为需要运算，Java中的大多数运算符是为基本数据类型设计的。比较、算术等

包装对象---->基本数值

```java
Integer obj = new Integer(4);
int num1 = obj.intValue();
```

**自动装箱与拆箱：**

由于我们经常要做基本类型与包装类之间的转换，从`JDK5.0 `开始，基本类型与包装类的装箱、拆箱动作可以自动完成。例如：

```java
//自动装箱。相当于Integer i = Integer.valueOf(4);
Integer i = 4;
//等号右边：将i对象转成基本数值(自动拆箱) i.intValue() + 5;
i = i + 5;
//加法运算完成后，再次装箱，把基本数值转成对象。
```

> 注意：只能与自己对应的类型之间才能实现自动装箱与拆箱。

```java
Integer i = 1;
Double d = 1;//错误的，1是int类型
```

### 11.5 基本数据类型、包装类与字符串间的转换

**（1）<font color='red'>基本数据类型转为字符串</font>**

**方式1：**调用字符串重载的valueOf()方法

```java
int a = 10;
//String str = a;//错误的

String str = String.valueOf(a);
```

**方式2：**更直接的方式

```java
int a = 10;

String str = a + "";
```

**（2）<font color='red'>字符串转为基本数据类型</font>**

**方式1：**除了Character类之外，其他所有包装类都具有parseXxx静态方法可以将字符串参数转换为对应的基本类型，例如：

* `public static int parseInt(String s)`：将字符串参数转换为对应的int基本类型。
* `public static long parseLong(String s)`：将字符串参数转换为对应的long基本类型。
* `public static double parseDouble(String s)`：将字符串参数转换为对应的double基本类型。

**方式2：**字符串转为包装类，然后可以自动拆箱为基本数据类型

* ```public static Integer valueOf(String s)```：将字符串参数转换为对应的Integer包装类，然后可以自动拆箱为int基本类型
* ```public static Long valueOf(String s)```：将字符串参数转换为对应的Long包装类，然后可以自动拆箱为long基本类型
* ```public static Double valueOf(String s)```：将字符串参数转换为对应的Double包装类，然后可以自动拆箱为double基本类型

注意:如果字符串参数的内容无法正确转换为对应的基本类型，则会抛出`java.lang.NumberFormatException`异常。

**方式3：**通过包装类的构造器实现

```java
int a = Integer.parseInt("整数的字符串");
double d = Double.parseDouble("小数的字符串");
boolean b = Boolean.parseBoolean("true或false");

int a = Integer.valueOf("整数的字符串");
double d = Double.valueOf("小数的字符串");
boolean b = Boolean.valueOf("true或false");

int i = new Integer(“12”);

```

其他方式小结：

![image-20220813012801907](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220813012801907.png)

### 11.6 包装类的其它API

#### 11.6.1 数据类型的最大最小值

```java
Integer.MAX_VALUE和Integer.MIN_VALUE
    
Long.MAX_VALUE和Long.MIN_VALUE
    
Double.MAX_VALUE和Double.MIN_VALUE
```

#### 11.6.2 字符转大小写

```java
Character.toUpperCase('x');

Character.toLowerCase('X');
```

#### 11.6.3 整数转进制

```java
Integer.toBinaryString(int i) 
    
Integer.toHexString(int i)
    
Integer.toOctalString(int i)
```

#### 11.6.4 比较的方法

```java
Double.compare(double d1, double d2)
    
Integer.compare(int x, int y) 
```

### 11.7 包装类对象的特点

#### 11.7.1 包装类缓存对象

> <font color='cornflowerblue'>包装类缓存对象是一种使用缓存机制来优化性能的设计模式(享元设计模式)。它通常用于将一些开销较大的操作结果进行缓存，以便下一次需要使用这个操作结果时不需要重新计算。</font>在Java中，包装类缓存对象主要是指Integer、Byte、Short、Long、Character这些基本类型的封装类，它们在实例化的时候会自动进行缓存。
>
> 这些封装类的对象是不可变的，所以它们的值是固定的。因此，如果两个对象的值相等，那么它们所引用的实例也一定是相等的。这种特性使得Java官方推荐对于某些简单类型的封装类，创建相同值的多个对象，使用缓存来减少对象创建的数量，从而提高性能。
>
> <font color='red'>需要注意的是，虽然使用包装类缓存对象可以提高性能，但是需要注意在使用时避免将缓存的对象进行修改，因为它们的值是不可变的，任何修改操作都是无效的。此外，对于缓存对象的使用需要谨慎，因为缓存变量作为静态变量，可能会对多线程环境下的线程安全性产生影响。</font>

| 包装类    | 缓存对象范围 |
| --------- | ------------ |
| Byte      | -128~127     |
| Short     | -128~127     |
| Integer   | -128~127     |
| Long      | -128~127     |
| Float     | 没有         |
| Double    | 没有         |
| Character | 0~127        |
| Boolean   | true和false  |

```java
Integer a = 1;
Integer b = 1;
System.out.println(a == b);//true

Integer i = 128;
Integer j = 128;
System.out.println(i == j);//false

Integer m = new Integer(1);//新new的在堆中
Integer n = 1;//这个用的是缓冲的常量对象，在方法区
System.out.println(m == n);//false

Integer x = new Integer(1);//新new的在堆中
Integer y = new Integer(1);//另一个新new的在堆中
System.out.println(x == y);//false
```

```java
Double d1 = 1.0;
Double d2 = 1.0;
System.out.println(d1==d2);//false 比较地址，没有缓存对象，每一个都是新new的
```

#### 11.7.2 类型转换问题

```java
Integer i = 1000;
double j = 1000;
System.out.println(i==j);//true  会先将i自动拆箱为int，然后根据基本数据类型“自动类型转换”规则，转为double比较
```

```java
Integer i = 1000;
int j = 1000;
System.out.println(i==j);//true 会自动拆箱，按照基本数据类型进行比较
```

```java
Integer i = 1;
Double d = 1.0
System.out.println(i==d);//编译报错
```

#### 11.7.3 包装类对象不可变

```java
public class TestExam {
	public static void main(String[] args) {
		int i = 1;
		Integer j = new Integer(2);
		Circle c = new Circle();
		change(i,j,c);
		System.out.println("i = " + i);//1
		System.out.println("j = " + j);//2
		System.out.println("c.radius = " + c.radius);//10.0
	}
	
	/*
	 * 方法的参数传递机制：
	 * （1）基本数据类型：形参的修改完全不影响实参
	 * （2）引用数据类型：通过形参修改对象的属性值，会影响实参的属性值
	 * 这类Integer等包装类对象是“不可变”对象，即一旦修改，就是新对象，和实参就无关了
	 */
	public static void change(int a ,Integer b,Circle c ){
		a += 10;
//		b += 10;//等价于  b = new Integer(b+10);
		c.radius += 10;
		/*c = new Circle();
		c.radius+=10;*/
	}
}
class Circle{
	double radius;
}
```

### 11.8 练习

笔试题：如下两个题目输出结果相同吗？各是什么。

```java
Object o1 = true ? new Integer(1) : new Double(2.0);
System.out.println(o1);//1.0

```

```java
Object o2;
if (true)
    o2 = new Integer(1);
else
    o2 = new Double(2.0);
System.out.println(o2);//1

```

面试题：

```java
public void method1() {
    Integer i = new Integer(1);
    Integer j = new Integer(1);
    System.out.println(i == j);

    Integer m = 1;
    Integer n = 1;
    System.out.println(m == n);//

    Integer x = 128;
    Integer y = 128;
    System.out.println(x == y);//
}

```

练习：

利用Vector代替数组处理：从键盘读入学生成绩（以负数代表输入结束），找出最高分，并输出学生成绩等级。

- 提示：数组一旦创建，长度就固定不变，所以在创建数组前就需要知道它的长度。而向量类java.util.Vector可以根据需要动态伸缩。

- 创建Vector对象：Vector v=new Vector();

- 给向量添加元素：v.addElement(Object obj);  //obj必须是对象

- 取出向量中的元素：Object obj=v.elementAt(0);
  - 注意第一个元素的下标是0，返回值是Object类型的。

- 计算向量的长度：v.size();

- 若与最高分相差10分内：A等；20分内：B等；30分内：C等；其它：D等



## 12.随堂复习

### 1. 关键字：static

- static：静态的，随着类的加载而加载、执行。

- static用来修饰：属性、方法、代码块、内部类
- 熟悉：static修饰的类变量、类方法与不使用static修饰的区别。
  - 类变量：类的生命周期内，只有一个。被类的多个实例共享。
- 掌握：我们遇到属性或方法时，需要考虑是否声明为static的。

### 2. 单例模式（或单子模式）

- 经典的设计模式有23种
- 解决的问题：在整个软件系统中，只存在当前类的唯一实例。
- 实现方式：饿汉式、懒汉式、枚举类等
- 对比饿汉式和懒汉式
  - 饿汉式：“立即加载”，线程安全的。
  - 懒汉式："延迟加载"，线程不安全。
- 需要会手写饿汉式和懒汉式

### 3. 理解main()方法

- public static void main(String[] args){}
- 理解1：作为程序的入口；普通的静态方法
- 理解2：如何使用main()与控制台进行数据的交互。
  - 命令行：java 类名 "Tom" "Jerry" "123"

### 4. 类的成员之四：代码块

- 分类：静态代码块、非静态代码块
- 使用频率上来讲：用的比较少。
- 静态代码块：随着类的加载而执行
- 非静态代码块：随着对象的创建而执行

> 总结：对象的实例变量可以赋值的位置及先后顺序
>
> ① 默认初始化
> ② 显式初始化  或 ⑤ 代码块中初始化
> ③ 构造器中初始化
>
> ④ 有了对象以后，通过"对象.属性"或"对象.方法"的方法进行赋值
>
> 执行的先后顺序：
> ① - ②/⑤ - ③ - ④



### 5. 关键字：final

- 最终的

- 用来修饰：类、方法、变量（成员变量、局部变量）
  - 类：不能被继承
  - 方法：不能被重写
  - 变量：是一个“常量”，一旦赋值不能修改。

### 6. 关键字：abstract

- 抽象的
- 用来修饰：类、方法
  - 类：抽象类：不能实例化。
  - 方法：抽象方法：没有方法体，必须由子类实现此方法。

### 7. 关键字：interface

- interface：接口，用来定义一组规范、一种标准。

- 掌握：接口中可以声明的结构。

  - 属性：使用public static final修饰

  - 方法：jdk8之前：只能声明抽象方法，使用public abstract修饰

    ​            jdk8中：声明static方法、default方法。

    ​            jdk9中：声明private方法。

- 笔试题：抽象类、接口的对比。

### 8. 类的成员之五：内部类

```
> 成员内部类的理解
> 如何创建成员内部类的实例
> 如何在成员内部类中调用外部类的结构
> 局部内部类的基本使用（关注：如何在方法内创建匿名局部内部类的对象）
```

### 9. 枚举类：enum

- 掌握：使用enum关键字定义枚举类即可。

### 10. 注解:Annotation

- 框架 = 注解 + 反射 + 设计模式
- Java基础阶段：简单。@Override  、 @Deprecated、@SuppressWarnings
- 元注解：对现有的注解进行解释说明。
  - @Target：表明可以用来修饰的结构
  - @Retation：表明生命周期
- 如何自定义注解。

### 11. 包装类的使用

- 掌握：基本数据类型对应的包装类都有哪些？
- 掌握：基本数据类型、包装类、String三者之间的转换
  - 基本数据类型 <-> 包装类：自动装箱、自动拆箱
  - 基本数据类型、包装类 <-> String
    - String的valueOf(xxx)
    - 包装类的parseXxx(String str)

### 12. IDEA:快捷键和debug





## 13.企业真题

### 13.1 static关键字

* **静态变量和实例变量的区别？（保\*丰、\*软国际、\*软华*、北京明**信）**

```
类似问题：
> 说明静态变量和实例变量之间的区别和使用场景（上海*动）
```

​		静态变量被关键字static修饰，其随着类的加载而加载，而实例变量是随着对象的加载而加载。静态变量在		堆内存中存储，而实例变量在堆内存中的对象当中c

* **静态属性和静态方法是否可以被继承？是否可以被重写？以及原因？（*度）**

```
类似问题：
> 在java中，可以重载一个static方法吗？可以覆盖一个static方法吗？（Vi*o）
```

​		静态属性和静态方法可以被继承，但是静态方法不能被重写。不存在多态性。

* **是否可以从一个static方法内部发出对非static方法的调用？（同*顺）**

​		只能通过对象来对非静态方法的调用。

* 被static修饰的成员(类、方法、成员变量)能否再使用private进行修饰？（联*优势）

​		完全可以。除了代码块。



### 13.2 设计模式

* **知道哪些设计模式？（*通快递、蚂**服）**

​		单例模式、模板方法、享元设计模式

* **开发中都用到了那些设计模式?用在什么场合? （久*国际物流）**

​		略

### 13.3main()

* **main()方法的public能不能换成private，为什么（凡*科技、顺\*）**

​		可以改。但是改完以后就不是程序入口了。

* **main()方法中是否可以调用非静态方法？（浩*科技）**

​		只能通过对象来对非静态方法的调用。

### 13.4 代码块

* **类的组成和属性赋值执行顺序?（航*拓普）**

```
类似问题：
> Java中类的变量初始化的顺序？（*壳）
```

​		略。

* **静态代码块，普通代码块，构造方法，从类加载开始的执行顺序？（恒*电子）**

```
类似问题：
> 类加载成员变量、静态代码块、构造器的加载顺序（*科软、软**力、同*顺）
> static代码块(静态代码块)是否在类的构造函数之前被执行（联*优势）

```

​		静态代码块 --> 普通代码块 --> 构造器

### 13.5 final关键字

* **描述一下对final理解（华**博普）**

​		略

* **判断题：使用final修饰一个变量时，是引用不能改变，引用指向的对象可以改变？（*米）**

​		引用不能改变。

​		引用指向的对象实体中的属性，如果没有使用final修饰，则可以改变。

* **判断题：final不能用于修饰构造方法？（联*优势）**

​		是的。

*  **final或static final 修饰成员变量，能不能进行++操作？（佳*贸易）**

​		不能。

### 13.6 抽象类与接口

* **什么是抽象类？如何识别一个抽象类？（易*支付）**

​		使用abstract修饰。

* **为什么不能用abstract修饰属性、私有方法、构造器、静态方法、final的方法？（止**善）**

略。 为了语言的自洽。

* **接口与抽象类的区别？（字\*跳动、阿\*校招、\*度校招、\*\*计算机技术及应用研究所、航*拓普、纬\*、招**晟、汇\*云通、数信\*\*科技、北京永\*鼎力、上海\*连科技）**

​		略。

* **接口是否可继承接口？抽象类是否可实现（implements）接口？抽象类是否可继承实现类（concrete class）？（航\*拓普、\*蝶、深圳德*科技）**

```
类似问题：
> 接口A可以继承接口B吗?接口A可以实现接口B吗?（久*国际物流）
```

​		是；是；是；

* **接口可以有自己属性吗？（华*中盛）**

​		可以。必须是public static final的

* **访问接口的默认方法如何使用(上海*思)**

​		使用实现类的对象进行调用。而且实现还可以重写此默认方法。

### 13.7 内部类

* **内部类有哪几种？（华油**普、来\*科技）**

​		略。

* **内部类的特点说一下（招通**）**

```
类似问题：
> 说一下内部类的好处（北京楚*龙）
> 使用过内部类编程吗，有什么作用（软**力）
```

* **匿名类说一下（阿*校招、上海立\*网络）**

​		略

### 13.8 枚举类

* **枚举可以继承吗?（顺*）**

​		使用enum定义的，其父类就是Enum类，就不要再继承其他的类了。

### 13.9 包装类

* **Java基本类型与包装类的区别（凡*科技）**

​		略。

### 13.10 综合

* **谈谈你对面向对象的理解(君*科技、航\*拓普、...)**
  * 面向对象的两个要素：类、对象  ---> 面向对象编程。“万事万物皆对象”。
  * 面向对象的三大特征
  * 接口，与类并列的结构，作为一个补充：类可以实现多个接口。

* **面向对象的特征有哪些方面? （北京楚\*龙、深圳德*科技、直\*科技、米\*奇网络、航\*拓普）**

```
类似问题：
> 面向对象核心是什么？（平**服）
```



# 第08章_异常处理

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC09%E7%AB%A0.png" alt="第3阶段：Java高级应用-第09章" style="zoom: 50%;" />

## 1. 异常概述

### 1.1 什么是生活的异常

男主角小明每天开车上班，正常车程1小时。但是，不出意外的话，可能会出现意外。

![image-20220814203918560](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220814203918560.png)

出现意外，即为异常情况。我们会做相应的处理。如果不处理，到不了公司。处理完了，就可以正常开车去公司。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220520210320301.png" alt="image-20220520210320301" style="zoom:70%;" />

### 1.2 什么是程序的异常

在使用计算机语言进行项目开发的过程中，即使程序员把代码写得`尽善尽美`，在系统的运行过程中仍然会遇到一些问题，因为很多问题不是靠代码能够避免的，比如：`客户输入数据的格式问题`，`读取文件是否存在`，`网络是否始终保持通畅`等等。

* **异常** ：==指的是程序在执行过程中，出现的非正常情况，如果不处理最终会导致JVM的非正常停止。==

> 异常指的并不是语法错误和逻辑错误。语法错了，编译不通过，不会产生字节码文件，根本不能运行。
>
> 代码逻辑错误，只是没有得到想要的结果，例如：求a与b的和，你写成了a-b

### 1.3 异常的抛出机制

Java中是如何表示不同的异常情况，又是如何让程序员得知，并处理异常的呢？

Java中把不同的异常用不同的类表示，一旦发生某种异常，就`创建该异常类型的对象`，并且抛出（throw）。然后程序员可以捕获(catch)到这个异常对象，并处理；如果没有捕获(catch)这个异常对象，那么这个异常对象将会导致程序终止。

举例：

运行下面的程序，程序会产生一个数组角标越界异常`ArrayIndexOfBoundsException`。我们通过图解来解析下异常产生和抛出的过程。

~~~java
public class ArrayTools {
    // 对给定的数组通过给定的角标获取元素。
    public static int getElement(int[] arr, int index) {
        int element = arr[index];
        return element;
    }
}
~~~

 测试类

~~~java
public class ExceptionDemo {
    public static void main(String[] args) {
        int[] arr = { 34, 12, 67 };
        intnum = ArrayTools.getElement(arr, 4)
        System.out.println("num=" + num);
        System.out.println("over");
    }
}
~~~

上述程序执行过程图解：

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E5%BC%82%E5%B8%B8%E4%BA%A7%E7%94%9F%E8%BF%87%E7%A8%8B.png)

### 1.4 如何对待异常

 对于程序出现的异常，一般有两种解决方法：一是遇到错误就终止程序的运行。另一种方法是程序员在编写程序时，就充分考虑到各种可能发生的异常和错误，极力预防和避免。实在无法避免的，要编写相应的代码进行异常的检测、以及`异常的处理`，提高代码的`健壮性`。

## 2. Java异常体系

### 2.1 Throwable

`java.lang.Throwable` 类是Java程序执行过程中发生的异常事件对应的类的根父类。

**Throwable中的常用方法：**

* `public void printStackTrace()`：打印异常的详细信息。

  包含了异常的类型、异常的原因、异常出现的位置、在开发和调试阶段都得使用printStackTrace。

* `public String getMessage()`：获取发生异常的原因。

### 2.2 Error 和 Exception

Throwable可分为两类：Error和Exception。分别对应着`java.lang.Error`与`java.lang.Exception`两个类。

**Error：**<font color='red'>Java虚拟机无法解决的严重问题。如：JVM系统内部错误、资源耗尽等严重情况。一般不编写针对性的代码进行处理。</font>

- 例如：StackOverflowError（栈内存溢出）和OutOfMemoryError（堆内存溢出，简称OOM）。

**Exception:** <font color='red'>其它因编程错误或偶然的外在因素导致的一般性问题，需要使用针对性的代码进行处理，使程序继续运行。否则一旦发生异常，程序也会挂掉。</font>例如：

- 空指针访问
- 试图读取不存在的文件
- 网络连接中断
- 数组角标越界

> 说明：
>
> 1. 无论是Error还是Exception，还有很多子类，异常的类型非常丰富。当代码运行出现异常时，特别是我们不熟悉的异常时，不要紧张，把异常的简单类名，拷贝到API中去查去认识它即可。
> 2. 我们本章讲的异常处理，其实针对的就是Exception。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220511161910951.png" alt="image-20220511161910951" style="zoom:67%;" />

### 2.3 编译时异常和运行时异常

Java程序的执行分为编译时过程和运行时过程。有的错误只有在`运行时`才会发生。比如：除数为0，数组下标越界等。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220330002449526.png" alt="image-20220330002449526" style="zoom:80%;" />

因此，根据异常可能出现的阶段，可以将异常分为：

* **编译时期异常**（即checked异常、受检异常）：在代码编译阶段，编译器就能明确`警示`当前代码`可能发生（不是一定发生）`xx异常，并`明确督促`程序员提前编写处理它的代码。如果程序员`没有编写`对应的异常处理代码，则编译器就会直接判定编译失败，从而不能生成字节码文件。通常，这类异常的发生不是由程序员的代码引起的，或者不是靠加简单判断就可以避免的，例如：FileNotFoundException（文件找不到异常）。
* **运行时期异常**（即runtime异常、unchecked异常、非受检异常）：在代码编译阶段，编译器完全不做任何检查，无论该异常是否会发生，编译器都不给出任何提示。只有等代码运行起来并确实发生了xx异常，它才能被发现。通常，这类异常是由程序员的代码编写不当引起的，只要稍加判断，或者细心检查就可以避免。
  * **java.lang.RuntimeException**类及它的子类都是运行时异常。比如：ArrayIndexOutOfBoundsException数组下标越界异常，ClassCastException类型转换异常。

![1562771528807](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1562771528807.png)

## 3. 常见的错误和异常

### 3.1 Error

最常见的就是VirtualMachineError，它有两个经典的子类：StackOverflowError、OutOfMemoryError。

```java
package com.atguigu.exception;

import org.junit.Test;

public class TestStackOverflowError {
    @Test
    public void test01(){
        //StackOverflowError
        recursion();
    }

    public void recursion(){ //递归方法
        recursion(); 
    }
}

```

```java
package com.atguigu.exception;

import org.junit.Test;

public class TestOutOfMemoryError {
    @Test
    public void test02(){
        //OutOfMemoryError
        //方式一：
        int[] arr = new int[Integer.MAX_VALUE];
    }
    @Test
    public void test03(){
        //OutOfMemoryError
        //方式二：
        StringBuilder s = new StringBuilder();
        while(true){
            s.append("atguigu");
        }
    }
}

```

### 3.2 运行时异常

```java
package com.atguigu.exception;

import org.junit.Test;

import java.util.Scanner;

public class TestRuntimeException {
    @Test
    public void test01(){
        //NullPointerException
        int[][] arr = new int[3][];
        System.out.println(arr[0].length);
    }

    @Test
    public void test02(){
        //ClassCastException
        Object obj = 15;
        String str = (String) obj;
    }

    @Test
    public void test03(){
        //ArrayIndexOutOfBoundsException
        int[] arr = new int[5];
        for (int i = 1; i <= 5; i++) {
            System.out.println(arr[i]);
        }
    }

    @Test
    public void test04(){
        //InputMismatchException
        Scanner input = new Scanner(System.in);
        System.out.print("请输入一个整数：");//输入非整数
        int num = input.nextInt();
        input.close();
    }

    @Test
    public void test05(){
        int a = 1;
        int b = 0;
        //ArithmeticException
        System.out.println(a/b);
    }
}

```

### 3.3 编译时异常

```java
package com.atguigu.exception;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestCheckedException {
    @Test
    public void test06() {
        Thread.sleep(1000);//休眠1秒  InterruptedException
    }

    @Test
    public void test07(){
        Class c = Class.forName("java.lang.String");//ClassNotFoundException
    }

    @Test
    public void test08() {
        Connection conn = DriverManager.getConnection("....");  //SQLException
    }
    @Test
    public void test09()  {
        FileInputStream fis = new FileInputStream("尚硅谷Java秘籍.txt"); //FileNotFoundException
    }
    @Test
    public void test10() {
        File file = new File("尚硅谷Java秘籍.txt");
		FileInputStream fis = new FileInputStream(file);//FileNotFoundException
		int b = fis.read();//IOException
		while(b != -1){
			System.out.print((char)b);
			b = fis.read();//IOException
		}
		
		fis.close();//IOException
    }
}
```

## 4. 异常的处理

### 4.1 异常处理概述

在编写程序时，经常要在可能出现错误的地方加上检测的代码，如进行x/y运算时，要`检测分母为0`，`数据为空`，`输入的不是数据而是字符`等。过多的if-else分支会导致程序的`代码加长`、`臃肿`，`可读性差`，程序员需要花很大的精力“`堵漏洞`”。因此采用异常处理机制。

**Java异常处理**

Java采用的异常处理机制，是`将异常处理的程序代码集中在一起`，与正常的程序代码分开，使得程序简洁、优雅，并易于维护。

**Java异常处理的方式：**

方式一：try-catch-finally

方式二：throws + 异常类型

![image-20220331111051496](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220331111051496.png)

### 4.2 方式1：捕获异常（try-catch-finally）

Java提供了异常处理的**抓抛模型**。

- 前面提到，Java程序的执行过程中如出现异常，会生成一个异常类对象，该异常对象将被提交给Java运行时系统，这个过程称为`抛出(try)异常`。
- 如果一个方法内抛出异常，该异常对象会被抛给调用者方法中处理。如果异常没有在调用者方法中处理，它继续被抛给这个调用方法的上层方法。这个过程将一直继续下去，直到异常被处理。这一过程称为`捕获(catch)异常`。
- 如果一个异常回到main()方法，并且main()也不处理，则程序运行终止。

#### 4.2.1 try-catch-finally基本格式

捕获异常语法如下：

~~~java
try{
	......	//可能产生异常的代码
}
catch( 异常类型1 e ){
	......	//当产生异常类型1型异常时的处置措施
}
catch( 异常类型2 e ){
	...... 	//当产生异常类型2型异常时的处置措施
}  
finally{
	...... //无论是否发生异常，都无条件执行的语句
} 

~~~

**1、整体执行过程：**

当某段代码可能发生异常，不管这个异常是编译时异常（受检异常）还是运行时异常（非受检异常），我们都可以使用try块将它括起来，并在try块下面编写catch分支尝试捕获对应的异常对象。

- 如果在程序运行时，try块中的代码没有发生异常，那么catch所有的分支都不执行。
- 如果在程序运行时，try块中的代码发生了异常，根据异常对象的类型，将从上到下选择第一个匹配的catch分支执行。此时try中发生异常的语句下面的代码将不执行，而整个try...catch之后的代码可以继续运行。
- 如果在程序运行时，try块中的代码发生了异常，但是所有catch分支都无法匹配（捕获）这个异常，那么JVM将会终止当前方法的执行，并把异常对象“抛”给调用者。如果调用者不处理，程序就挂了。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503122722605.png" alt="image-20220503122722605" style="zoom:67%;" />

**2、try**

- 捕获异常的第一步是用`try{…}语句块`选定捕获异常的范围，将可能出现异常的业务逻辑代码放在try语句块中。

**3、catch (Exceptiontype e)**

- catch分支，分为两个部分，catch()中编写异常类型和异常参数名，{}中编写如果发生了这个异常，要做什么处理的代码。

- 如果明确知道产生的是何种异常，可以用该异常类作为catch的参数；也可以用其父类作为catch的参数。

  比如：可以用ArithmeticException类作为参数的地方，就可以用RuntimeException类作为参数，或者用所有异常的父类Exception类作为参数。但不能是与ArithmeticException类无关的异常，如NullPointerException（catch中的语句将不会执行）。

- 每个try语句块可以伴随一个或多个catch语句，用于处理可能产生的不同类型的异常对象。

- 如果有多个catch分支，并且多个异常类型有父子类关系，必须保证小的子异常类型在上，大的父异常类型在下。否则，报错。

- catch中常用异常处理的方式

  - `public String getMessage()`：获取异常的描述信息，返回字符串

  - `public void printStackTrace()`：打印异常的跟踪栈信息并输出到控制台。包含了异常的类型、异常的原因、还包括异常出现的位置，在开发和调试阶段，都得使用printStackTrace()。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220331180736381.png" alt="image-20220331180736381" style="zoom:80%;" />

#### 4.2.2 使用举例

举例1：

```java
public class IndexOutExp {
    public static void main(String[] args) {
        String friends[] = { "lisa", "bily", "kessy" };
        try {
            for (int i = 0; i < 5; i++) {
            System.out.println(friends[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("index err");
        }
        System.out.println("\nthis is the end");
    }
}

```

举例2：

```java
public class DivideZero1 {
    int x;
    public static void main(String[] args) {
        int y;
        DivideZero1 c = new DivideZero1();
        try {
            y = 3 / c.x;
        } catch (ArithmeticException e) {
            System.out.println("divide by zero error!");
        }
        System.out.println("program ends ok!");
    }
}

```

举例3：

```java
@Test
public void test1(){
	try{
		String str1 = "atguigu.com";
		str1 = null;
		System.out.println(str1.charAt(0));
	}catch(NullPointerException e){
		//异常的处理方式1
		System.out.println("不好意思，亲~出现了小问题，正在加紧解决...");	
	}catch(ClassCastException e){
		//异常的处理方式2
		System.out.println("出现了类型转换的异常");
	}catch(RuntimeException e){
		//异常的处理方式3
		System.out.println("出现了运行时异常");
	}
	//此处的代码，在异常被处理了以后，是可以正常执行的
	System.out.println("hello");
}
```

#### 4.2.3finally中常见的声明的代码

<font color='red'>finally代码块中，常常进行资源的关闭，以防止内存泄露。一些资源（输入输出流，数据库连接，scoket连接等资源），在使用完之后，必须显式的进行关闭操作，否则，GC不会自动回收这些资源，进而导致内存泄漏。</font>

==为了保证这些资源在使用完以后，不管是否出现了未被处理的异常的情况下，这些资源能被关闭。我们必须将这些操作声明在finally中!==

#### 4.2.4 finally使用及举例

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220331215517077.png" alt="image-20220331215517077" style="zoom: 80%;" />

- 因为异常会引发程序跳转，从而会导致有些语句执行不到。而程序中有一些特定的代码无论异常是否发生，都`需要执行`。例如，数据库连接、输入流输出流、Socket连接、Lock锁的关闭等，这样的代码通常就会放到finally块中。所以，我们通常将一定要被执行的代码声明在finally中。

  - 唯一的例外，使用 `System.exit(0)` 来终止当前正在运行的 Java 虚拟机。

- 不论在try代码块中是否发生了异常事件，catch语句是否执行，catch语句是否有异常，catch语句中是否有return，finally块中的语句都会被执行。

- finally语句和catch语句是可选的，但finally不能单独使用。

  ```java
  try{
       
  }finally{
      
  } 
  ```

举例1：确保资源关闭

```java
package com.atguigu.keyword;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestFinally {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("请输入第一个整数：");
            int a = input.nextInt();
            System.out.print("请输入第二个整数：");
            int b = input.nextInt();
            int result = a/b;
            System.out.println(a + "/" + b +"=" + result);
        } catch (InputMismatchException e) {
            System.out.println("数字格式不正确，请输入两个整数");
        }catch (ArithmeticException e){
            System.out.println("第二个整数不能为0");
        } finally {
            System.out.println("程序结束，释放资源");
            input.close();
        }
    }
    
    @Test
    public void test1(){
        FileInputStream fis = null;
        try{
            File file = new File("hello1.txt");
            fis = new FileInputStream(file);//FileNotFoundException
            int b = fis.read();//IOException
            while(b != -1){
                System.out.print((char)b);
                b = fis.read();//IOException
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(fis != null)
                    fis.close();//IOException
            } catch (IOException e) {
                e.printStackTrace();
            }	
        }
    }
}
```

举例2：从try回来

```java
public class FinallyTest1 {
    public static void main(String[] args) {
        int result = test("12");
        System.out.println(result);
    }

    public static int test(String str){
        try{
            Integer.parseInt(str);
            return 1;
        }catch(NumberFormatException e){
            return -1;
        }finally{
            System.out.println("test结束");
        }
    }
}
```

举例3：从catch回来

```java
public class FinallyTest2 {
    public static void main(String[] args) {
        int result = test("a");
        System.out.println(result);
    }

    public static int test(String str) {
        try {
            Integer.parseInt(str);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        } finally {
            System.out.println("test结束");
        }
    }
}
```

举例4：从finally回来

```java
public class FinallyTest3 {
    public static void main(String[] args) {
        int result = test("a");
        System.out.println(result);
    }

    public static int test(String str) {
        try {
            Integer.parseInt(str);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        } finally {
            System.out.println("test结束");
            return 0;
        }
    }
}
```

笔试题：

```java
public class ExceptionTest {
    public static void main(String[] args) {
        int result = test();
        System.out.println(result); //100
    }

    public static int test(){
        int i = 100;
        try {
            return i;
        } finally {
            i++;
        }
    }
}
```

> 笔试题：final、finally、finalize有什么区别？

#### 4.2.5 练习

编写一个类ExceptionTest，在main方法中使用try、catch、finally，要求：

- 在try块中，编写被零除的代码。

- 在catch块中，捕获被零除所产生的异常，并且打印异常信息

- 在finally块中，打印一条语句。

#### 4.2.6 异常处理的体会

- 前面使用的异常都是`RuntimeException类`或是它的`子类`，这些类的异常的特点是：即使没有使用try和catch捕获，Java自己也能捕获，并且编译通过 ( 但运行时会发生异常使得程序运行终止 )。所以，对于这类异常，可以不作处理，因为这类异常很普遍，若全处理可能会对程序的可读性和运行效率产生影响。

- 如果抛出的异常是IOException等类型的`非运行时异常`，则必须捕获，否则`编译错误`。也就是说，我们必须处理编译时异常，将异常进行捕捉，转化为运行时异常。

### 4.3 方式2：声明抛出异常类型（throws）

- 如果在编写方法体的代码时，某句代码可能发生某个`编译时异常`，不处理编译不通过，但是在当前方法体中可能`不适合处理`或`无法给出合理的处理方式`，则此方法应`显示地`声明抛出异常，表明该方法将不对这些异常进行处理，而由该方法的调用者负责处理。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220331112000671.png" alt="image-20220331112000671" style="zoom:67%;" />

- 具体方式：在方法声明中用`throws语句`可以声明抛出异常的列表，throws后面的异常类型可以是方法中产生的异常类型，也可以是它的父类。

#### 4.3.1 throws基本格式

**声明异常格式：**

~~~
修饰符 返回值类型 方法名(参数) throws 异常类名1,异常类名2…{   }	
~~~

在throws后面可以写多个异常类型，用逗号隔开。

举例：

```java
public void readFile(String file)  throws FileNotFoundException,IOException {
	...
	// 读文件的操作可能产生FileNotFoundException或IOException类型的异常
	FileInputStream fis = new FileInputStream(file);
    //...
}

```

#### 4.3.2 throws 使用举例

**举例：针对于编译时异常**

```java
package com.atguigu.keyword;

public class TestThrowsCheckedException {
    public static void main(String[] args) {
        System.out.println("上课.....");
        try {
            afterClass();//换到这里处理异常
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("准备提前上课");
        }
        System.out.println("上课.....");
    }

    public static void afterClass() throws InterruptedException {
        for(int i=10; i>=1; i--){
            Thread.sleep(1000);//本来应该在这里处理异常
            System.out.println("距离上课还有：" + i + "分钟");
        }
    }
}

```

**举例：针对于运行时异常：**

throws后面也可以写运行时异常类型，只是运行时异常类型，写或不写对于编译器和程序执行来说都没有任何区别。如果写了，唯一的区别就是调用者调用该方法后，使用try...catch结构时，IDEA可以获得更多的信息，需要添加哪种catch分支。

~~~java
package com.atguigu.keyword;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestThrowsRuntimeException {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("请输入第一个整数：");
            int a = input.nextInt();
            System.out.print("请输入第二个整数：");
            int b = input.nextInt();
            int result = divide(a,b);
            System.out.println(a + "/" + b +"=" + result);
        } catch (ArithmeticException | InputMismatchException e) {
            e.printStackTrace();
        } finally {
            input.close();
        }
    }

    public static int divide(int a, int b)throws ArithmeticException{
        return a/b;
    }
}

~~~

#### 4.3.3 方法重写中throws的要求

方法重写时，对于方法签名是有严格要求的。复习：

```
（1）方法名必须相同
（2）形参列表必须相同
（3）返回值类型
	- 基本数据类型和void：必须相同
	- 引用数据类型：<=
（4）权限修饰符：>=，而且要求父类被重写方法在子类中是可见的
（5）不能是static，final修饰的方法
```

此外，对于throws异常列表要求：

- 如果父类被重写方法的方法签名后面没有 “throws  编译时异常类型”，那么重写方法时，方法签名后面也不能出现“throws  编译时异常类型”。
- 如果父类被重写方法的方法签名后面有 “`throws  编译时异常类型`”，那么重写方法时，throws的编译时异常类型必须 <= 被重写方法throws的编译时异常类型，或者不throws编译时异常。
- 方法重写，对于“`throws 运行时异常类型`”没有要求。

```java
package com.atguigu.keyword;

import java.io.IOException;

class Father{
    public void method()throws Exception{
        System.out.println("Father.method");
    }
}
class Son extends Father{
    @Override
    public void method() throws IOException,ClassCastException {
        System.out.println("Son.method");
    }
}
```

### 4.4 两种异常处理方式的选择

前提：对于异常，使用相应的处理方式。此时的异常，主要指的是编译时异常。

- ==如果程序代码中，涉及到资源的调用（流、数据库连接、网络连接等），则必须考虑使用try-catch-finally来处理，保证不出现内存泄漏。==
- ==如果父类被重写的方法没有throws异常类型，则子类重写的方法中如果出现异常，只能考虑使用try-catch-finally进行处理，不能throws。==
- ==开发中，方法a中依次调用了方法b,c,d等方法，方法b,c,d之间是递进关系。此时，如果方法b,c,d中有异常，我们通常选择使用throws，而方法a中通常选择使用try-catch-finally。==

## 5. 手动抛出异常对象：throw

Java 中异常对象的生成有两种方式：

- 由虚拟机**自动生成**：程序运行过程中，虚拟机检测到程序发生了问题，那么针对当前代码，就会在后台自动创建一个对应异常类的实例对象并抛出。

- 由开发人员**手动创建**：`new 异常类型([实参列表]);`，如果创建好的异常对象不抛出对程序没有任何影响，和创建一个普通对象一样，但是一旦throw抛出，就会对程序运行产生影响了。

### 5.1 使用格式

~~~java
throw new 异常类名(参数);
~~~

throw语句抛出的异常对象，和JVM自动创建和抛出的异常对象一样。

- 如果是编译时异常类型的对象，同样需要使用throws或者try...catch处理，否则编译不通过。

- 如果是运行时异常类型的对象，编译器不提示。

- 可以抛出的异常必须是Throwable或其子类的实例。下面的语句在编译时将会产生语法错误：

  ```java
  throw new String("want to throw");
  ```

### 5.2 使用注意点：

无论是编译时异常类型的对象，还是运行时异常类型的对象，如果没有被try..catch合理的处理，都会导致程序崩溃。

throw语句会导致程序执行流程被改变，throw语句是明确抛出一个异常对象，因此它`下面的代码将不会执行`。

如果当前方法没有try...catch处理这个异常对象，throw语句就会`代替return语句`提前终止当前方法的执行，并返回一个异常对象给调用者。

~~~java
package com.atguigu.keyword;

public class TestThrow {
    public static void main(String[] args) {
        try {
            System.out.println(max(4,2,31,1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(max(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(max());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int max(int... nums){
        if(nums == null || nums.length==0){
            throw new IllegalArgumentException("没有传入任何整数，无法获取最大值");
        }
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] > max){
                max = nums[i];
            }
        }
        return max;
    }
}

~~~

## 6. 自定义异常

### 6.1 为什么需要自定义异常类

Java中不同的异常类，分别表示着某一种具体的异常情况。那么在开发中总是有些异常情况是核心类库中没有定义好的，此时我们需要根据自己业务的异常情况来定义异常类。例如年龄负数问题，考试成绩负数问题，某员工已在团队中等。

### 6.2 如何自定义异常类

（1）要继承一个异常类型

​			自定义一个编译时异常类型：自定义类继承`java.lang.Exception`。

​			自定义一个运行时异常类型：自定义类继承`java.lang.RuntimeException`。

（2）建议大家提供至少两个构造器，一个是无参构造，一个是(String message)构造器。

（3）自定义异常需要提供`serialVersionUID`

### 6.3 注意点

1. 自定义的异常只能通过throw抛出。
2. 自定义异常最重要的是异常类的名字和message属性。当异常出现时，可以根据名字判断异常类型。比如：`TeamException("成员已满，无法添加"); `、 `TeamException("该员工已是某团队成员");`

3. 自定义异常对象只能手动抛出。抛出后由try..catch处理，也可以甩锅throws给调用者处理。

### 6.4 举例

举例1：

```java
class MyException extends Exception {
    static final long serialVersionUID = 23423423435L;
    private int idnumber;

    public MyException(String message, int id) {
        super(message);
        this.idnumber = id;
    }

    public int getId() {
        return idnumber;
    }
}

```

```java
public class MyExpTest {
    public void regist(int num) throws MyException {
        if (num < 0)
            throw new MyException("人数为负值，不合理", 3);
        else
            System.out.println("登记人数" + num);
    }
    public void manager() {
        try {
            regist(100);
        } catch (MyException e) {
            System.out.print("登记失败，出错种类" + e.getId());
        }
        System.out.print("本次登记操作结束");
    }
    public static void main(String args[]) {
        MyExpTest t = new MyExpTest();
        t.manager();
    }
}

```

举例2：

~~~java
package com.atguigu.define;
//自定义异常：
public class NotTriangleException extends Exception{
    static final long serialVersionUID = 13465653435L;

    public NotTriangleException() {
    }

    public NotTriangleException(String message) {
        super(message);
    }
}

~~~

```java
package com.atguigu.define;

public class Triangle {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) throws NotTriangleException {
        if(a<=0 || b<=0 || c<=0){
            throw new NotTriangleException("三角形的边长必须是正数");
        }
        if(a+b<=c || b+c<=a || a+c<=b){
            throw new NotTriangleException(a+"," + b +"," + c +"不能构造三角形，三角形任意两边之后必须大于第三边");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) throws NotTriangleException{
        if(a<=0){
            throw new NotTriangleException("三角形的边长必须是正数");
        }
        if(a+b<=c || b+c<=a || a+c<=b){
            throw new NotTriangleException(a+"," + b +"," + c +"不能构造三角形，三角形任意两边之后必须大于第三边");
        }
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) throws NotTriangleException {
        if(b<=0){
            throw new NotTriangleException("三角形的边长必须是正数");
        }
        if(a+b<=c || b+c<=a || a+c<=b){
            throw new NotTriangleException(a+"," + b +"," + c +"不能构造三角形，三角形任意两边之后必须大于第三边");
        }
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) throws NotTriangleException {
        if(c<=0){
            throw new NotTriangleException("三角形的边长必须是正数");
        }
        if(a+b<=c || b+c<=a || a+c<=b){
            throw new NotTriangleException(a+"," + b +"," + c +"不能构造三角形，三角形任意两边之后必须大于第三边");
        }
        this.c = c;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
```

```java
package com.atguigu.define;

public class TestTriangle {
    public static void main(String[] args) {
        Triangle t = null;
        try {
            t = new Triangle(2,2,3);
            System.out.println("三角形创建成功：");
            System.out.println(t);
        } catch (NotTriangleException e) {
            System.err.println("三角形创建失败");
            e.printStackTrace();
        }

        try {
            if(t != null) {
                t.setA(1);
            }
            System.out.println("三角形边长修改成功");
        } catch (NotTriangleException e) {
            System.out.println("三角形边长修改失败");
            e.printStackTrace();
        }
    }
}
```

## 7. 练习

**练习1：**

```java
public class ReturnExceptionDemo {
    static void methodA() {
        try {
            System.out.println("进入方法A");
            throw new RuntimeException("制造异常");
        }finally {
            System.out.println("用A方法的finally");
        }
    }

    static void methodB() {
        try {
            System.out.println("进入方法B");
            return;
        } finally {
            System.out.println("调用B方法的finally");
        }
    }
	public static void main(String[] args) {
    	try {
    	    methodA();
   		} catch (Exception e) {
    	  	System.out.println(e.getMessage());
    	}
    	methodB();
  	}
}

```

**练习2：**

从键盘接收学生成绩，成绩必须在0~100之间。

自定义成绩无效异常。

编写方法接收成绩并返回该成绩，如果输入无效，则抛出自定义异常。

**练习3：**

编写应用 程序EcmDef.java，接收命令行的两个参数，要求不能输入负数，计算两数相除。
对数据类型不一致(NumberFormatException)、缺少命令行参数(ArrayIndexOutOfBoundsException、
除0(ArithmeticException)及输入负数(BelowZeroException 自定义的异常)进行异常处理。

提示： 
(1)在主类(EcmDef)中定义异常方法(ecm)完成两数相除功能。

(2)在main()方法中使用异常处理语句进行异常处理。

(3)在程序中，自定义对应输入负数的异常类(EcDef)。

(4)运行时接受参数 java EcmDef 20 10   //args[0]=“20” args[1]=“10”

(5)Interger类的static方法parseInt(String s)将s转换成对应的int值。
     如：int a=Interger.parseInt(“314”);	//a=314;

## 8. 小结与小悟

### 8.1 小结：异常处理5个关键字

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220330003738109.png" alt="image-20220330003738109" style="zoom: 67%;" />

> 类比：上游排污，下游治污

### 8.2 感悟

**小哲理：**

世界上最遥远的`距离`，是我在`if`里你在`else`里，似乎一直相伴又永远分离；

世界上最痴心的`等待`，是我当`case`你是`switch`，或许永远都选不上自己；

世界上最真情的`相依`，是你在`try`我在`catch`。无论你发神马脾气，我都默默承受，静静处理。到那时，再来期待我们的`finally`。

**歌词：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220511163035280.png" alt="image-20220511163035280" style="zoom:67%;" />



## 9.随堂复习

### 1. 异常的概述

```
1. 什么是异常？
指的是程序在执行过程中，出现的非正常情况，如果不处理最终会导致JVM的非正常停止。

2. 异常的抛出机制
Java中把不同的异常用不同的类表示，一旦发生某种异常，就`创建该异常类型的对象`，并且抛出（throw）。
然后程序员可以捕获(catch)到这个异常对象，并处理；如果没有捕获(catch)这个异常对象，那么这个异常
对象将会导致程序终止。

3. 如何对待异常
 对于程序出现的异常，一般有两种解决方法：一是遇到错误就终止程序的运行。另一种方法是程序员在编写程序时，
 就充分考虑到各种可能发生的异常和错误，极力预防和避免。实在无法避免的，要编写相应的代码进行异常的检测、
 以及`异常的处理`，保证代码的`健壮性`。
```

### 2. 异常的体系结构及常见的异常

```
java.lang.Throwable:异常体系的根父类
    |---java.lang.Error:错误。Java虚拟机无法解决的严重问题。如：JVM系统内部错误、资源耗尽等严重情况。
                         一般不编写针对性的代码进行处理。
               |---- StackOverflowError、OutOfMemoryError

    |---java.lang.Exception:异常。我们可以编写针对性的代码进行处理。
               |----编译时异常：(受检异常)在执行javac.exe命令时，出现的异常。
                    |----- ClassNotFoundException
                    |----- FileNotFoundException
                    |----- IOException
               |----运行时异常：(非受检异常)在执行java.exe命令时，出现的异常。
                    |---- ArrayIndexOutOfBoundsException
                    |---- NullPointerException
                    |---- ClassCastException
                    |---- NumberFormatException
                    |---- InputMismatchException
                    |---- ArithmeticException
```

```java
【面试题】说说你在开发中常见的异常都有哪些？

开发1-2年：
|----编译时异常：(受检异常)在执行javac.exe命令时，出现的异常。
    |----- ClassNotFoundException
    |----- FileNotFoundException
    |----- IOException
|----运行时异常：(非受检异常)在执行java.exe命令时，出现的异常。
    |---- ArrayIndexOutOfBoundsException
    |---- NullPointerException
    |---- ClassCastException
    |---- NumberFormatException
    |---- InputMismatchException
    |---- ArithmeticException

开发3年以上：
OOM。
```

### 3. 异常处理的方式

```
过程1：“抛”
 >"自动抛" ： 程序在执行的过程当中，一旦出现异常，就会在出现异常的代码处，自动生成对应异常类的对象，并将此对象抛出。

 >"手动抛" ：程序在执行的过程当中，不满足指定条件的情况下，我们主动的使用"throw + 异常类的对象"方式抛出异常对象。


过程2：“抓”
    狭义上讲：try-catch的方式捕获异常，并处理。
    广义上讲：把“抓”理解为“处理”。则此时对应着异常处理的两种方式：① try-catch-finally ② throws
```

#### 3.1 try-catch-finally

```
1. 基本结构：
try{
   ...... //可能产生异常的代码
}
catch( 异常类型1 e ){
   ...... //当产生异常类型1型异常时的处置措施
}
catch( 异常类型2 e ){
   ......     //当产生异常类型2型异常时的处置措施
}
finally{
   ...... //无论是否发生异常，都无条件执行的语句
}

2. 使用细节：
> 将可能出现异常的代码声明在try语句中。一旦代码出现异常，就会自动生成一个对应异常类的对象。并将此对象抛出。
> 针对于try中抛出的异常类的对象，使用之后的catch语句进行匹配。一旦匹配上，就进入catch语句块进行处理。
  一旦处理接触，代码就可继续向下执行。
> 如果声明了多个catch结构，不同的异常类型在不存在子父类关系的情况下，谁声明在上面，谁声明在下面都可以。
  如果多个异常类型满足子父类的关系，则必须将子类声明在父类结构的上面。否则，报错。
> catch中异常处理的方式：
   ① 自己编写输出的语句。
   ② printStackTrace()：打印异常的详细信息。 （推荐）
   ③ getMessage()：获取发生异常的原因。
> try中声明的变量，出了try结构之后，就不可以进行调用了。
> try-catch结构是可以嵌套使用的。
```

```
3. finally的使用说明：
3.1 finally的理解
> 我们将一定要被执行的代码声明在finally结构中。
> 更深刻的理解：无论try中或catch中是否存在仍未被处理的异常，无论try中或catch中是否存在return语句等，finally
  中声明的语句都一定要被执行。

> finally语句和catch语句是可选的，但finally不能单独使用。

3.2 什么样的代码我们一定要声明在finally中呢？
> 我们在开发中，有一些资源（比如：输入流、输出流，数据库连接、Socket连接等资源），在使用完以后，必须显式的进行
关闭操作，否则，GC不会自动的回收这些资源。进而导致内存的泄漏。
  为了保证这些资源在使用完以后，不管是否出现了未被处理的异常的情况下，这些资源能被关闭。我们必须将这些操作声明
在finally中！
```

#### 3.2 throws

```
1. 格式：在方法的声明除，使用"throws 异常类型1,异常类型2,..."

2. 举例：

public void test() throws 异常类型1,异常类型2,.. {
    //可能存在编译时异常
}


3. 是否真正处理了异常？
> 从编译是否能通过的角度看，看成是给出了异常万一要是出现时候的解决方案。此方案就是，继续向上抛出(throws)。
> 但是，此throws的方式，仅是将可能出现的异常抛给了此方法的调用者。此调用者仍然需要考虑如何处理相关异常。
  从这个角度来看，throws的方式不算是真正意义上处理了异常。


4. 方法的重写的要求：(针对于编译时异常来说的)
子类重写的方法抛出的异常类型可以与父类被重写的方法抛出的异常类型相同，或是父类被重写的方法抛出的异常类型的子类。
```

开发中的经验之谈：

```
开发中，如何选择异常处理的两种方式？(重要、经验之谈)
- 如果程序代码中，涉及到资源的调用（流、数据库连接、网络连接等），则必须考虑使用try-catch-finally来处理，
  保证不出现内存泄漏。
- 如果父类被重写的方法没有throws异常类型，则子类重写的方法中如果出现异常，只能考虑使用try-catch-finally
  进行处理，不能throws。
- 开发中，方法a中依次调用了方法b,c,d等方法，方法b,c,d之间是递进关系。此时，如果方法b,c,d中有异常，
  我们通常选择使用throws，而方法a中通常选择使用try-catch-finally。
```

### 4. 手动throw异常对象

```
在方法内部，满足指定条件的情况下，使用"throw 异常类的对象"的方式抛出。
```

### 5. 如何自定义异常类

```
① 继承于现有的异常体系。通常继承于RuntimeException \ Exception
② 通常提供几个重载的构造器
③ 提供一个全局常量，声明为：static final long serialVersionUID;
```

```
为什么需要自定义异常类？
我们其实更关心的是，通过异常的名称就能直接判断此异常出现的原因。既然如此，我们就有必要在实际开发场景中，
不满足我们指定的条件时，指明我们自己特有的异常类。通过此异常类的名称，就能判断出具体出现的问题。
```

## 10.企业真题

### 2.1 异常概述

#### 1. Java的异常体系简单介绍下（网*）

```
包含问题：
> 4.异常的顶级接口是什么（软**力）
> 异常类的继承关系,exception下都有哪些类？（上海*冉信息）
```

略

#### 2. Java异常处理机制（*科软）

两种处理方案：try-catch-finally ；throws

#### 3. 异常的两种类型，Error和Exception的区别(上海冠*新创、北京中\*\*译、\*度)

略

#### 4. 运行时异常与一般异常有何异同？（华*思为）

运行时异常：RuntimeException

- 编译可以通过。在运行时可能抛出。出现的概率高一些；一般针对于运行时异常，都不处理。

一般异常：Exception

- 编译不能通过。要求必须在编译之前，考虑异常的处理。不处理编译不通过。

#### 5. 说几个你常见到的异常（华油**普）

```
类似问题：
> 请列出Java中常见的几种异常？（百*园）
> 给我一个你最常见到的runtime exception。（*蝶）
```

略

###  2.2 try-catch-finally

#### 1. 说说final、finally、finalize的区别（北京中\*\*译、艾\*软件、拓*思、\*科软）

```
类似问题：
> 1. finally和final的区别（*科软）
```

略。

#### 2. 如果不使用try-catch，程序出现异常会如何？(上海冠*新创科技)

对于当前方法来讲，如果不使用try-catch，则在出现异常对象以后会抛出此对象。如果没有处理方案，就会终止程序的执行。

#### 3. try ... catch捕捉的是什么异常？（北京亿*东方）

Exception。非Error

#### 4. 如果执行finally代码块之前方法返回了结果或者jvm退出了，这时finally块中的代码还会执行吗?(恒*电子)

特别的：System.exit(0);

#### 5. 在try语句中有return语句，最后写finally语句，finally语句中的code会不会执行？何时执行？如果执行是在return前还是后（拓*思、华**为）

略

#### 6. 捕获异常在catch块里一定会进入finally吗？catch里能return吗？catch里return还会进finally吗？在try里return是什么情况？（*蓝）

略

### 2.3 throw与throws

#### 1. throw和throws的区别？（北京亿**方、北京新\*阳光）

角度1："形"，即使用的格式

```
throw:使用在方法内部，“throw 异常类的对象”
throws:使用在方法的声明处，"throws 异常类1,异常类2,..."
```

角度2："角色"或作用不同。

```
上游排污，下游治污。

过程1：“抛”
	>throw
过程2：“抓”
    > try-catch ; throws
```



#### 2. 子类重写父类抛出异常的方法，能否抛出比父类更高级别的异常类（顺*）

不能！

### 2.4 自定义异常

#### 1. 如何自定义一个异常？（*软国际）

略

# 第09章_多线程

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC10%E7%AB%A0.png" alt="第3阶段：Java高级应用-第10章" style="zoom: 50%;" />

***

我们之前学习的程序在没有跳转语句的情况下，都是由上至下沿着一条路径依次执行。现在想要设计一个程序，可以同时有多条执行路径同时执行。比如，`一边游戏，一边qq聊天，一边听歌`，怎么设计？

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/c3dcb61e3b.png" alt="c3dcb61e3b" style="zoom:50%;" />

要解决上述问题，需要使用`多进程`或者`多线程`来解决。

## 1. 相关概念

### 1.1 程序、进程与线程

* **程序（program）**：==为完成特定任务，用某种语言编写的`一组指令的集合`。即指`一段静态的代码`，静态对象。==

* **进程（process）**：==程序的一次执行过程，或是正在内存中运行的应用程序。==如：运行中的QQ，运行中的网易音乐播放器。

  * 每个进程都有一个独立的内存空间，系统运行一个程序即是一个进程从创建、运行到消亡的过程。（生命周期）
  * 程序是静态的，进程是动态的
  * 进程作为`操作系统调度和分配资源的最小单位`（亦是系统运行程序的基本单位），系统在运行时会为每个进程分配不同的内存区域。
  * 现代的操作系统，大都是支持多进程的，支持同时运行多个程序。比如：现在我们上课一边使用编辑器，一边使用录屏软件，同时还开着画图板，dos窗口等软件。

* **线程（thread）**：==进程可进一步细化为线程，是程序内部的`一条执行路径`。一个进程中至少有一个线程。== 

  - 一个进程同一时间若`并行`执行多个线程，就是支持多线程的。

    <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220331233204504.png" alt="image-20220331233204504" style="zoom:67%;" />

  - 线程作为`CPU调度和执行的最小单位`。

  - 一个进程中的多个线程共享相同的内存单元，它们从同一个堆中分配对象，可以访问相同的变量和对象。这就使得线程间通信更简便、高效。但多个线程操作共享的系统资源可能就会带来`安全的隐患`。

  - 下图中，红框的蓝色区域为线程独享，黄色区域为线程共享。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514175737426.png" alt="image-20220514175737426" style="zoom:80%;" />

  > 注意：
  >
  > 不同的进程之间是不共享内存的。
  >
  > 进程之间的数据交换和通信的成本很高。

### 1.2 查看进程和线程

我们可以在电脑底部任务栏，右键----->打开任务管理器，可以查看当前任务的进程：

1、每个应用程序的运行都是一个进程

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E8%BF%9B%E7%A8%8B%E6%A6%82%E5%BF%B5.png" style="zoom:80%;" />

2、一个应用程序的多次运行，就是多个进程

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1563267431480.png" alt="1563267431480" style="zoom:80%;" />

3、==一个进程中包含多个线程==

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1563270525077.png" alt="1563270525077" style="zoom:80%;" />

### 1.3 线程调度

- **分时调度**

  所有线程`轮流使用` CPU 的使用权，并且平均分配每个线程占用 CPU 的时间。

- **抢占式调度**

  让`优先级高`的线程以`较大的概率`优先使用 CPU。如果线程的优先级相同，那么会随机选择一个(线程随机性)，Java使用的为抢占式调度。

  ![抢占式调度](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E6%8A%A2%E5%8D%A0%E5%BC%8F%E8%B0%83%E5%BA%A6.bmp)

### 1.4 多线程程序的优点

**背景：**以单核CPU为例，只使用单个线程先后完成多个任务（调用多个方法），肯定比用多个线程来完成用的时间更短，为何仍需多线程呢？

**多线程程序的优点：**

1. ==提高应用程序的响应。对图形化界面更有意义，可增强用户体验。==

2. ==提高计算机系统CPU的利用率==

3. ==改善程序结构。将既长又复杂的进程分为多个线程，独立运行，利于理解和修改==



### 1.5单核CPU和多核CPU

单核CPU，在一个时间单元内，只能执行一个线程的任务。例如，可以把CPU看成是医院的医生诊室，在一定时间内只能给一个病人诊断治疗。所以单核CPU就是，代码经过前面一系列的前导操作（类似于医院挂号，比如有10个窗口挂号），然后到cpu处执行时发现，就只有一个CPU（对应一个医生），大家排队执行。

这时候想要提升系统性能，只有两个办法，要么提升CPU性能（让医生看病快点），要么多加几个CPU（多整几个医生），即为多核的CPU。

`问题：多核的效率是单核的倍数吗？`譬如4核A53的cpu，性能是单核A53的4倍吗？理论上是，但是实际不可能，至少有两方面的损耗。

- `一个是多个核心的其他共用资源限制`。譬如，4核CPU对应的内存、cache、寄存器并没有同步扩充4倍。这就好像医院一样，1个医生换4个医生，但是做B超检查的还是一台机器，性能瓶颈就从医生转到B超检查了。
- `另一个是多核CPU之间的协调管理损耗`。譬如多个核心同时运行两个相关的任务，需要考虑任务同步，这也需要消耗额外性能。好比公司工作，一个人的时候至少不用开会浪费时间，自己跟自己商量就行了。两个人就要开会同步工作，协调分配，所以工作效率绝对不可能达到2倍。

### 1.6 并行与并发

* **并行（parallel）**：指两个或多个事件在`同一时刻`发生（同时发生）。==指在同一时刻，有`多条指令`在`多个CPU`上`同时`执行。比如：多个人同时做不同的事。==

  ![image-20220401000804242](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220401000804242.png)

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513181758585.png" alt="image-20220513181758585" style="zoom: 50%;" />

* **并发（concurrency）**：指两个或多个事件在`同一个时间段内`发生。==即在一段时间内，有`多条指令`在`单个CPU`上`快速轮换、交替`执行。==

  ![image-20220401000515678](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220401000515678.png)

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220513181815978.png" alt="image-20220513181815978" style="zoom:50%;" />

**<font color='red'>简单来说，如果在多个处理器上同时完成任务，则称为并行；如果在一个处理器上在不同时间点完成多个任务，则称为并发。</font>**

## 2.创建和启动线程

### 2.1 概述

- Java语言的JVM允许程序运行多个线程，使用`java.lang.Thread`类代表**线程**，所有的线程对象都必须是Thread类或其子类的实例。
- Thread类的特性
  - 每个线程都是通过某个特定Thread对象的run()方法来完成操作的，因此把run()方法体称为`线程执行体`。
  - 通过该Thread对象的start()方法来启动这个线程，而非直接调用run()
  - 要想实现多线程，必须在主线程中创建新的线程对象。



### 2.2 初始化线程的4种方式

1. **继承 Thread** 
2. **实现 Runnable 接口** 
3. **实现 Callable 接口 + FutureTask （可以拿到返回结果，可以处理异常）** 
4. **线程池** 

对于以上初始化线程的方式：

* 方式 1 和方式 2：<font color='red'>主进程无法获取线程的运算结果。 </font>
* 方式 3：主进程可以获取线程的运算结果，但是不利于控制服务器中的线程资源。可以导致服务器资源耗尽。 
* 方式 4：通过如下两种方式初始化线程池

```java
Executors.newFiexedThreadPool(3);
//或者
new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit unit, workQueue, threadFactory, handler);
```



### 2.3 方式1：继承Thread类

Java通过继承Thread类来**创建**并**启动多线程**的步骤如下：

> 1. **定义Thread类的子类，并重写该类的run()方法，该run()方法的方法体就代表了线程需要完成的任务**
> 2. **创建Thread子类的实例，即创建了线程对象**
> 3. **调用线程对象的start()方法来启动该线程**



以内部类的方式遍历输出100以内的偶数，代码如下：

~~~java
public class NumberOne {

    public static void main(String[] args) {
        AddNumber addNumber = new AddNumber();
        addNumber.start();
    }

    public static class AddNumber extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        }
    }
}
~~~

以匿名内部类的方式遍历输出100以内的偶数，代码如下：

```java
public class NumberOne {

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0) {
                    System.out.println(i);
                }
            }
        }).start();
    }
}
```

> **<font color='red'>注意：</font>**
>
> 1. <font color='red'>如果自己手动调用run()方法，那么就只是普通方法，没有启动多线程模式。</font>
>
> 2. <font color='red'>run()方法由JVM调用，什么时候调用，执行的过程控制都有操作系统的CPU调度决定。</font>
>
> 3. <font color='red'>想要启动多线程，必须调用start方法。</font>
>
> 4. <font color='red'>一个线程对象只能调用一次start()方法启动，如果重复调用了，则将抛出以上的异常“`IllegalThreadStateException`”。</font>

### 2.4 方式2：实现Runnable接口

<font color='red'>Java有单继承的限制，当我们无法继承Thread类时，那么该如何做呢？在核心类库中提供了Runnable接口，我们可以实现Runnable接口，重写run()方法，然后再通过Thread类的对象代理启动和执行我们的线程体run()方法</font>

步骤如下：

> 1. **定义Runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。**
>
> 2. **创建Runnable实现类的实例，并以此实例作为Thread的构造方法的参数target，创建Thread对象，该Thread对象才是真正的线程对象。**
>
>    ![image-20230323160256280](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230323160256280.png)
>
>    ![image-20230323160325900](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230323160325900.png)
>
> 3. **调用Thread对象的start()方法，启动线程。调用Runnable接口实现类的run方法。**



代码如下：

```java
public class NumberOne {

    public static void main(String[] args) {
        EvenNumber evenNumber = new EvenNumber();
        new Thread(evenNumber).start();
}

    public static class EvenNumber implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0){
                    System.out.println(i);
                }
            }
        }
    }

}
```

匿名方式实现：

```java
public class NumberOne {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                for (int i = 1; i <= 100; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                    }
                }
            }
        }).start();
    }
}
```

 通过实现Runnable接口，使得该类有了多线程类的特征。所有的分线程要执行的代码都在run方法里面。

在启动的多线程的时候，需要先通过Thread类的构造方法Thread(Runnable target) 构造出对象，然后调用Thread对象的start()方法来运行多线程代码。

实际上，所有的多线程代码都是通过运行Thread的start()方法来运行的。因此，不管是继承Thread类还是实现
Runnable接口来实现多线程，最终还是通过Thread的对象的API来控制线程的，熟悉Thread类的API是进行多线程编程的基础。

说明：Runnable对象仅仅作为Thread对象的target，Runnable实现类里包含的run()方法仅作为线程执行体。
而实际的线程对象依然是Thread实例，只是该Thread线程负责执行其target的run()方法。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220401222212377.png" alt="image-20220401222212377"  />



### 2.5 对比Thread和Runnable两种方式

**联系**

Thread类实际上也是实现了Runnable接口的类。即：

```java
public class Thread extends Object implements Runnable
```

**区别**

- 继承Thread：线程代码存放Thread子类run方法中。

- 实现Runnable：线程代码存在接口的子类的run方法。

**实现Runnable接口比继承Thread类所具有的优势**

- 避免了单继承的局限性
- 多个线程可以共享同一个接口实现类的对象，非常适合多个相同线程来处理同一份资源。
- 增加程序的健壮性，实现解耦操作，代码可以被多个线程共享，代码和线程独立。

### 2.6 方式3：实现Callable接口 + FutureTask

- 与使用Runnable相比， Callable的优点如下：

  > - 相比run()方法，可以有返回值
  > - 方法可以抛出异常
  > - 支持泛型的返回值（需要借助FutureTask类，获取返回结果）

  缺点如下：

  > 如果在主线程中获取到分线程的call方法的返回值，此时主线程处于阻塞状态。

- Future接口（了解）

  - 可以对具体Runnable、Callable任务的执行结果进行取消、查询是否完成、获取结果等。
  - FutureTask是Futrue接口的唯一的实现类
  - FutureTask 同时实现了Runnable, Future接口。它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值

- 缺点：在获取分线程执行结果的时候，当前线程（或是主线程）受阻塞，效率较低。

- 代码举例

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallMe callMe = new CallMe();
        FutureTask futureTask = new FutureTask<>(callMe);
        Thread thread = new Thread(futureTask);
        thread.start();

        Object result = futureTask.get();
        System.out.println(result);
    }

}

class CallMe implements Callable {

    @Override
    public String call() throws Exception {
        int number = 0;
        for (int i = 1; i <= 100; i++) {
            System.out.println(number);
            number += i;
        }
        return "1-100的相加结果是：" + number;
    }
}
```

### 2.7 方式4：使用线程池

**现有问题：**

<font color='red'>如果并发的线程数量很多，并且每个线程都是执行一个时间很短的任务就结束了，这样频繁创建线程就会大大降低系统的效率，因为频繁创建线程和销毁线程需要时间。</font>

那么有没有一种办法使得线程可以复用，即执行完一个任务，并不被销毁，而是可以继续执行其他的任务？

**思路：**<font color='cornflowerblue'>提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池中。可以避免频繁创建销毁、实现重复利用。</font>

![线程池的理解](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%BA%BF%E7%A8%8B%E6%B1%A0%E7%9A%84%E7%90%86%E8%A7%A3.jpg)

**优点：**

> - 提高响应速度（减少了创建新线程的时间）
>
> - 降低资源消耗（重复利用线程池中线程，不需要每次都创建）
>
> - 便于线程管理
>   - corePoolSize：核心池的大小
>   - maximumPoolSize：最大线程数
>   - keepAliveTime：线程没有任务时最多保持多长时间后会终止
>   - …



**线程池相关API**

- JDK5.0之前，我们必须手动自定义线程池。从JDK5.0开始，Java内置线程池相关的API。在java.util.concurrent包下提供了线程池相关API：`ExecutorService` 和 `Executors`。
- `ExecutorService`：真正的线程池接口。常见子类ThreadPoolExecutor
  - `void execute(Runnable command)` ：执行任务/命令，没有返回值，一般用来执行Runnable
  - `<T> Future<T> submit(Callable<T> task)`：执行任务，有返回值，一般又来执行Callable
  - `void shutdown()` ：关闭连接池
- `Executors`：一个线程池的工厂类，通过此类的静态工厂方法可以创建多种类型的线程池对象。
  - `Executors.newCachedThreadPool()`：创建一个可根据需要创建新线程的线程池
  - `Executors.newFixedThreadPool(int nThreads)`; 创建一个可重用固定线程数的线程池
  - `Executors.newSingleThreadExecutor()` ：创建一个只有一个线程的线程池
  - `Executors.newScheduledThreadPool(int corePoolSize)`：创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。



### 2.8 线程池详解

1. **创建线程池的方式**

> * Executors.newFiexedThreadPool(3)
> * new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit unit, workQueue, threadFactory, handler);



2. **ThreadPoolExecutor方法的参数**

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)
```

> 1. int corePoolSize：核心线程数，线程会一直存在。
> 2. int maximumPoolSize：最大线程数，控制资源。
> 3. long keepAliveTime：存活时间，如果当前线程数量大于corePoolSize指定的线程数，并且已超过存活时间，就会释放除核心线程数之外的空闲线程。
> 4. TimeUnit unit：时间单位
> 5. BlockingQueue<Runnable> workQueue：阻塞队列。该队列是当核心线程没有空闲时，再来的请求放入队列中先保存任务。
> 6. ThreadFactory threadFactory：线程的创建工厂。
> 7. RejectedExecutionHandler handler：如果队列满了，按照拒绝策略拒绝执行任务。 



3. **ThreadPoolExecutor方法执行流程**

* 线程池创建，准备好 core 数量的核心线程，准备接受任务。新的任务进来，用 core 准备好的空闲线程执行。 
* core 满了，就将再进来的任务放入阻塞队列中。空闲的 core 就会自己去阻塞队列获取任务执行 。
* 阻塞队列满了，就直接开新线程执行，最大只能开到 max 指定的数量。 
* max 都执行好了。Max-core 数量空闲的线程会在 keepAliveTime 指定的时间后自动销毁。最终保持到 core 大小。
* 如果线程数开到了 max 的数量，还有新任务进来，就会使用 reject 指定的拒绝策略进行处理。
* 所有的线程创建都是由指定的 factory 创建的。



面试题：

>  一个线程池 core 7，max 20 ，queue：50，100 并发进来怎么分配的

<font color='red'>**7个被核心线程数执行，50个放入阻塞队列，开启新的线程执行，到达最大线程数时执行13个，大于最大线程数的30个被拒绝策略拒绝。**</font>

4. **ThreadPoolExecutor线程池配置**

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程配置
 *
 * @author Xu Huaiang
 * @date 2023/02/02
 */
@Configuration
public class ThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties threadPool) {
        return new ThreadPoolExecutor(
                threadPool.getCoreSize(),
                threadPool.getMaxSize(),
                threadPool.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
```

```java
/**
 * 线程池配置属性
 *
 * @author Xu Huaiang
 * @date 2023/02/02
 */
@Data
@Component
@ConfigurationProperties(prefix = "gulimall.thread")
public class ThreadPoolConfigProperties {
    private Integer coreSize;

    private Integer maxSize;

    private Integer keepAliveTime;


}
```

```yaml
#线程池配置
gulimall:
  thread:
    core-size: 20
    max-size: 200
    keep-alive-time: 10
```



### 2.9 为什么要使用线程池

* **降低资源的消耗**

​		通过重复利用已经创建好的线程降低线程的创建和销毁带来的损耗

* **提高响应速度**

​		因为线程池中的线程数没有超过线程池的最大上限时，有的线程处于等待分配任务的状态，当任务来时无		需创建新的线程就能执行。

* **提高线程的可管理性**

 		线程池会根据当前系统特点对池内的线程进行优化处理，减少创建和销毁线程带来 的系统开销。无限的			创建和销毁线程不仅消耗系统资源，还降低系统的稳定性，使 用线程池进行统一分配





## 3. Thread类的常用结构

### 3.1 构造器

> - `public Thread()` :分配一个新的线程对象。
> - `public Thread(String name) `:分配一个指定名字的新的线程对象。
> - `public Thread(Runnable target)` :指定创建线程的目标对象，它实现了Runnable接口中的run方法
> - `public Thread(Runnable target,String name)` :分配一个带有指定目标新的线程对象并指定名字。



### 3.2 常用方法系列1

> * `public void run() `:此线程要执行的任务在此处定义代码。
> * `public void start() `:导致此线程开始执行; Java虚拟机调用此线程的run方法。
> * `public String getName() `:获取当前线程名称。
> * `public void setName(String name)`：设置该线程名称。
> * `public static Thread currentThread()` :返回对当前正在执行的线程对象的引用。在Thread子类中就是this，通常用于主线程和Runnable实现类
> * `public static void sleep(long millis)` :使当前正在执行的线程以指定的毫秒数暂停（暂时停止执行）。
> * `public static void yield()`：==yield只是让当前线程暂停一下，让系统的线程调度器重新调度一次，希望优先级与当前线程相同或更高的其他线程能够获得执行机会==，但是这个不能保证，完全有可能的情况是，当某个线程调用了yield方法暂停之后，线程调度器又将其调度出来重新执行。



### 3.3 常用方法系列2

> * `public final boolean isAlive()`：测试线程是否处于活动状态。如果线程已经启动且尚未终止，则为活动状态。 
>
> * `void join() `：等待该线程执行终止才能执行上一个线程。 
>
> * `void join(long millis)` ：等待该线程终止的时间最长为 millis 毫秒。如果millis时间到，将不再等待。 
>
> * `void join(long millis, int nanos)` ：等待该线程终止的时间最长为 millis 毫秒 + nanos 纳秒。  
>
> * `public final void stop()`：<font color='red'>已过时</font>，不建议使用。强行结束一个线程的执行，直接进入死亡状态。run()即刻停止，可能会导致一些清理性的工作得不到完成，如文件，数据库等的关闭。同时，会立即释放该线程所持有的所有的锁，导致数据得不到同步的处理，出现数据不一致的问题。
>
> * `void suspend() / void resume() `: 这两个操作就好比播放器的暂停和恢复。二者必须成对出现，否则非常容易发生死锁。suspend()调用会导致线程暂停，但不会释放任何锁资源，导致其它线程都无法访问被它占用的锁，直到调用resume()。<font color='red'>已过时</font>，不建议使用。



### 3.4 常用方法（线程优先级）

<font color='red'>每个线程都有一定的优先级，同优先级线程组成先进先出队列（先到先服务），使用**分时调度策略**。</font><font color='cornflowerblue'>优先级高的线程采用**抢占式策略**，获得较多的执行机会。</font>每个线程默认的优先级都与创建它的父线程具有相同的优先级。

> - Thread类的三个优先级常量：
>   - `MAX_PRIORITY`（10）：最高优先级 
>   - `MIN _PRIORITY `（1）：最低优先级
>   - `NORM_PRIORITY `（5）：普通优先级，默认情况下main线程具有普通优先级。
>
> * `public final int getPriority()` ：返回线程优先级 
> * `public final void setPriority(int newPriority) `：改变线程的优先级，范围在[1,10]之间。

```java
public class NumberOne {

    public static void main(String[] args) {
        OddNumber oddNumber = new OddNumber();
        oddNumber.setName("线程：获取奇数");
        oddNumber.setPriority(Thread.MIN_PRIORITY);
        oddNumber.start();

        EvenNumber evenNumber = new EvenNumber();
        evenNumber.setName("线程：获取偶数");
        evenNumber.setPriority(Thread.MAX_PRIORITY);
        evenNumber.start();


    }
}

class EvenNumber extends Thread{
    @Override
    public void run() {
        System.out.println("偶数，当前线程的优先级为：" + Thread.currentThread().getPriority());
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0){
                System.out.println("获取偶数" + i);
            }
        }
    }
}

class OddNumber extends Thread{
    @Override
    public void run() {
        System.out.println("奇数，当前线程的优先级为：" + Thread.currentThread().getPriority());
        for (int i = 1; i <= 100; i++) {
            if (i % 2 != 0){
                System.out.println("获取奇数" + i);
            }
        }
    }
}
```

![image-20230323171947756](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230323171947756.png)

案例：

- 声明一个匿名内部类继承Thread类，重写run方法，实现打印[1,100]之间的偶数，要求每隔1秒打印1个偶数。

- 声明一个匿名内部类继承Thread类，重写run方法，实现打印[1,100]之间的奇数，
  - 当打印到5时，让奇数线程暂停一下，再继续。
  - 当打印到5时，让奇数线程停下来，让偶数线程执行完再打印。


```java
package com.atguigu.api;

public class TestThreadStateChange {
    public static void main(String[] args) {
        Thread te = new Thread() {
            @Override
            public void run() {
                for (int i = 2; i <= 100; i += 2) {
                    System.out.println("偶数线程：" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        te.start();

        Thread to = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i += 2) {
                    System.out.println("奇数线程：" + i);
                    if (i == 5) {
//                        Thread.yield();
                        try {
                            te.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        to.start();
    }
}
```

生产实践中的趣事：

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E8%BF%99%E6%98%AF%E8%A6%81%E9%97%B9%E5%93%AA%E6%A0%B7.jpg)

### 3.5 守护线程（了解）

有一种线程，它是在后台运行的，它的任务是为其他线程提供服务的，这种线程被称为“守护线程”。JVM的垃圾回收线程就是典型的守护线程。

守护线程有个特点，就是如果所有非守护线程都死亡，那么守护线程自动死亡。形象理解：`兔死狗烹`，`鸟尽弓藏`

调用setDaemon(true)方法可将指定线程设置为守护线程。必须在线程启动之前设置，否则会报IllegalThreadStateException异常。

调用isDaemon()可以判断线程是否是守护线程。

```java
public class TestThread {
	public static void main(String[] args) {
		MyDaemon m = new MyDaemon();
		m.setDaemon(true);
		m.start();

		for (int i = 1; i <= 100; i++) {
			System.out.println("main:" + i);
		}
	}
}

class MyDaemon extends Thread {
	public void run() {
		while (true) {
			System.out.println("我一直守护者你...");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
```

## 4. 多线程的生命周期

Java语言使用Thread类及其子类的对象来表示线程，在它的一个完整的生命周期中通常要经历如下一些状态：

### 4.1 JDK1.5之前：5种状态

线程的生命周期有五种状态：

* **新建（New）**

* **就绪（Runnable）**

* **运行（Running）**

* **阻塞（Blocked）**

* **死亡（Dead）**

CPU需要在多条线程之间切换，于是线程状态会多次在运行、阻塞、就绪之间切换。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220401002307038.png" alt="image-20220401002307038" style="zoom: 67%;" />



**1.新建**

当一个Thread类或其子类的对象被声明并创建时，新生的线程对象处于新建状态。此时它和其他Java对象一样，仅仅由JVM为其分配了内存，并初始化了实例变量的值。此时的线程对象并没有任何线程的动态特征，程序也不会执行它的线程体run()。

**2.就绪**

但是当线程对象调用了start()方法之后，就不一样了，线程就从新建状态转为就绪状态。JVM会为其创建方法调用栈和程序计数器，当然，处于这个状态中的线程并没有开始运行，只是表示已具备了运行的条件，随时可以被调度。至于什么时候被调度，取决于JVM里线程调度器的调度。

> 注意：
>
> 程序只能对新建状态的线程调用start()，并且只能调用一次，如果对非新建状态的线程，如已启动的线程或已死亡的线程调用start()都会报错IllegalThreadStateException异常。

**3.运行**

如果处于就绪状态的线程获得了CPU资源时，开始执行run()方法的线程体代码，则该线程处于运行状态。如果计算机只有一个CPU核心，在任何时刻只有一个线程处于运行状态，如果计算机有多个核心，将会有多个线程并行(Parallel)执行。

当然，美好的时光总是短暂的，而且CPU讲究雨露均沾。对于抢占式策略的系统而言，系统会给每个可执行的线程一个小时间段来处理任务，当该时间用完，系统会剥夺该线程所占用的资源，让其回到就绪状态等待下一次被调度。此时其他线程将获得执行机会，而在选择下一个线程时，系统会适当考虑线程的优先级。

**4.阻塞**

当在运行过程中的线程遇到如下情况时，会让出 CPU 并临时中止自己的执行，进入阻塞状态：

* 线程调用了sleep()方法，主动放弃所占用的CPU资源；
* 线程试图获取一个同步监视器，但该同步监视器正被其他线程持有；
* 线程执行过程中，同步监视器调用了wait()，让它等待某个通知（notify）；
* 线程执行过程中，同步监视器调用了wait(time)
* 线程执行过程中，遇到了其他线程对象的加塞（join）；
* 线程被调用suspend方法被挂起（已过时，因为容易发生死锁）；

当前正在执行的线程被阻塞后，其他线程就有机会执行了。针对如上情况，当发生如下情况时会解除阻塞，让该线程重新进入就绪状态，等待线程调度器再次调度它：

* 线程的sleep()时间到；
* 线程成功获得了同步监视器；
* 线程等到了通知(notify)；
* 线程wait的时间到了
* 加塞的线程结束了；
* 被挂起的线程又被调用了resume恢复方法（已过时，因为容易发生死锁）；

**5.死亡**

线程会以以下三种方式之一结束，结束后的线程就处于死亡状态：

* run()方法执行完成，线程正常结束
* 线程执行过程中抛出了一个未捕获的异常（Exception）或错误（Error）
* 直接调用该线程的stop()来结束该线程（已过时）

### 4.2 JDK1.5及之后：6种状态


在java.lang.Thread.State的枚举类中这样定义：

```java
public enum State {
	NEW,
	RUNNABLE,
	BLOCKED,
	WAITING,
	TIMED_WAITING,
	TERMINATED;
}
```

- `NEW（新建）`：线程刚被创建，但是并未启动。还没调用start方法。

- `RUNNABLE（可运行）`：这里没有区分就绪和运行状态。因为对于Java对象来说，只能标记为可运行，至于什么时候运行，不是JVM来控制的了，是OS来进行调度的，而且时间非常短暂，因此对于Java对象的状态来说，无法区分。

- `Teminated（被终止）`：表明此线程已经结束生命周期，终止运行。

- 重点说明，根据Thread.State的定义，**阻塞状态分为三种**：`BLOCKED`、`WAITING`、`TIMED_WAITING`。
  - `BLOCKED（锁阻塞）`：在API中的介绍为：一个正在阻塞、等待一个监视器锁（锁对象）的线程处于这一状态。只有获得锁对象的线程才能有执行机会。
    - 比如，线程A与线程B代码中使用同一锁，如果线程A获取到锁，线程A进入到Runnable状态，那么线程B就进入到Blocked锁阻塞状态。
  - `TIMED_WAITING（计时等待）`：在API中的介绍为：一个正在限时等待另一个线程执行一个（唤醒）动作的线程处于这一状态。
    - 当前线程执行过程中遇到Thread类的`sleep`或`join`，Object类的`wait`，LockSupport类的`park`方法，并且在调用这些方法时，`设置了时间`，那么当前线程会进入TIMED_WAITING，直到时间到，或被中断。
  - `WAITING（无限等待）`：在API中介绍为：一个正在无限期等待另一个线程执行一个特别的（唤醒）动作的线程处于这一状态。
    - 当前线程执行过程中遇到遇到Object类的`wait`，Thread类的`join`，LockSupport类的`park`方法，并且在调用这些方法时，`没有指定时间`，那么当前线程会进入WAITING状态，直到被唤醒。
      - 通过Object类的wait进入WAITING状态的要有Object的notify/notifyAll唤醒；
      - 通过Condition的await进入WAITING状态的要有Condition的signal方法唤醒；
      - 通过LockSupport类的park方法进入WAITING状态的要有LockSupport类的unpark方法唤醒
      - 通过Thread类的join进入WAITING状态，只有调用join方法的线程对象结束才能让当前线程恢复；

说明：当从WAITING或TIMED_WAITING恢复到Runnable状态时，如果发现当前线程没有得到监视器锁，那么会立刻转入BLOCKED状态。

![image-20220524203355448](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220524203355448.png)

或

![线程的生命周期Thread.State](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%BA%BF%E7%A8%8B%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9FThread.State.jpg)

> 我们在翻阅API的时候会发现Timed Waiting（计时等待） 与 Waiting（无限等待） 状态联系还是很紧密的，
> 比如Waiting（无限等待） 状态中wait方法是空参的，而timed waiting（计时等待） 中wait方法是带参的。
> 这种带参的方法，其实是一种倒计时操作，相当于我们生活中的小闹钟，我们设定好时间，到时通知，可是
> 如果提前得到（唤醒）通知，那么设定好时间在通知也就显得多此一举了，那么这种设计方案其实是一举两
> 得。如果没有得到（唤醒）通知，那么线程就处于Timed Waiting状态，直到倒计时完毕自动醒来；如果在倒
> 计时期间得到（唤醒）通知，那么线程从Timed Waiting状态立刻唤醒。

举例：

```java
/**
 * @author 尚硅谷-宋红康
 * @create 22:15
 */
public class ThreadStateTest {
    public static void main(String[] args) throws InterruptedException {
        SubThread t = new SubThread();
        System.out.println(t.getName() + " 状态 " + t.getState());
        t.start();

        while (Thread.State.TERMINATED != t.getState()) {
            System.out.println(t.getName() + " 状态 " + t.getState());
            Thread.sleep(500);
        }
        System.out.println(t.getName() + " 状态 " + t.getState());
    }
}

class SubThread extends Thread {
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println("打印：" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }
}

```

命令行演示：

![image-20220524195035355](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220524195035355.png)

## 5. 线程安全问题及解决

### 5.1线程安全问题概述

当我们使用多个线程访问**同一资源**（可以是同一个变量、同一个文件、同一条记录等）的时候，若多个线程`只有读操作`，那么不会发生线程安全问题。但是如果多个线程中对资源有`读和写`的操作，就容易出现线程安全问题。

举例：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220401233218326.png" alt="image-20220401233218326" style="zoom:67%;" />



### 5.2 同一个资源问题和线程安全问题

案例：

火车站要卖票，我们模拟火车站的卖票过程。因为疫情期间，本次列车的座位共100个（即，只能出售100张火车票）。我们来模拟车站的售票窗口，实现多个窗口同时售票的过程。注意：不能出现错票、重票。

示例代码：

```javascript
public class NumberOne {
    public static void main(String[] args) {
        SaveTicket saveTicket = new SaveTicket();
        Thread t1 = new Thread(saveTicket);
        Thread t2 = new Thread(saveTicket);
        Thread t3 = new Thread(saveTicket);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class SaveTicket implements Runnable{
    int ticket = 100;
    @Override
    public void run() {
        while (true){
            if (ticket > 0){
                System.out.println("卖出一张票：" + ticket);
                ticket--;
            }else {
                break;
            }
        }
    }
}
```

结果：发现卖出同一张票的情况。

![image-20230323192052555](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230323192052555.png)

### 5.3 同步机制解决线程安全问题

#### 5.3.1同步机制概述

要解决上述多线程并发访问一个资源的安全性问题:也就是解决重复票与不存在票问题，Java中提供了同步机制(`synchronized`)来解决。

![1563372934332](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1563372934332.png)

根据案例简述：

窗口1线程进入操作的时候，窗口2和窗口3线程只能在外等着，窗口1操作结束，窗口1和窗口2和窗口3才有机会进入代码去执行。==也就是说在某个线程修改共享资源的时候，其他线程不能修改该资源，等待修改完毕同步之后，才能去抢夺CPU资源，完成对应的操作，保证了数据的同步性，解决了线程不安全的现象。==

==为了保证每个线程都能正常执行原子操作，Java引入了线程同步机制。注意:在任何时候,最多允许一个线程拥有同步锁，谁拿到锁就进入代码块，其他的线程只能在外等着(BLOCKED)。==

#### 5.3.1 同步机制的原理

==同步机制的原理，其实就相当于给某段代码加“锁”，任何线程想要执行这段代码，都要先获得“锁”，我们称它为同步锁。==

<font color='cornflowerblue'>哪个线程获得了“同步锁”对象之后，”同步锁“对象就会记录这个线程的ID，这样其他线程就只能等待了，除非这个线程”释放“了锁对象，其他线程才能重新获得/占用”同步锁“对象。</font>

![GIF 2023-3-23 19-41-52](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/GIF%202023-3-23%2019-41-52.gif)

#### 5.3.2 同步代码块

**同步代码块**：`synchronized` 关键字可以用于某个区块前面，表示只对这个区块的资源实行互斥访问。
格式:

```java
synchronized(同步锁){
     需要同步操作的代码
}
```

> 同步锁对象可以是任意对象类型，但是必须保证竞争“同一个共享资源”的多个线程必须使用同一个“同步锁对象”。
>
> 对于同步代码块来说，同步锁对象是由程序员手动指定的（很多时候也是指定为this或类名.class），但是对于同步方法来说，同步锁对象只能是默认的：
>
> - 静态方法：当前类的Class对象（类名.class）
>
> - 非静态方法：this

为什么不建议使用`this`作为锁对象

> 从语法上来讲，可以使用this作为同步代码块的同步锁，也可以使用其他对象作为参数。 但是，使用this的方式可能会导致一些问题。
>
> 如果在一个类中，既有同步方法，又有同步代码块，并且同步代码块使用的是this对象作为锁，那么同步方法和同步代码块将同时争夺this对象的锁，这可能会导致死锁问题。
>
> 为了避免这些问题，建议使用专门的对象作为锁，而不是使用this。 例如，可以创建一个私有对象来充当锁，然后在需要同步的代码块中使用该锁。 这将避免锁竞争和死锁问题，保证程序正确、高效地运行。

```java
public class NumberOne {
    public static void main(String[] args) {
        SaveTicket saveTicket = new SaveTicket();
        Thread t1 = new Thread(saveTicket);
        Thread t2 = new Thread(saveTicket);
        Thread t3 = new Thread(saveTicket);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class SaveTicket implements Runnable {
    int ticket = 100;

    static Object object = new Object();

    @Override
    public void run() {
        while (true){
            synchronized(object){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (ticket > 0){
                    System.out.println(Thread.currentThread().getName() + "卖出一张票：" + ticket);
                    ticket--;
                }else{
                    break;
                }
            }
        }
    }
}
```

#### 5.3.3 同步方法

**同步方法：**`synchronized`关键字直接修饰方法，表示同一时刻只有一个线程能进入这个方法，其他线程在外面等着。

```java
public synchronized void method(){
    可能会产生线程安全问题的代码
}
```

```java
public class NumberOne {
    public static void main(String[] args) {
        SaveTicket saveTicket = new SaveTicket();
        Thread t1 = new Thread(saveTicket);
        Thread t2 = new Thread(saveTicket);
        Thread t3 = new Thread(saveTicket);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class SaveTicket implements Runnable {
    int ticket = 100;
    static boolean isFlag = true;
    @Override
    public void run() {
        while (isFlag){
            show();
        }
    }

    public synchronized void show() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出一张票：" + ticket);
            ticket--;
        }else{
            isFlag = false;
        }
    }
}
```



## 6. 单例设计模式的线程安全问题

### 6.1 单例设计模式的线程安全问题

#### 6.1.1 饿汉式没有线程安全问题

饿汉式：在类初始化时就直接创建单例对象，而类初始化过程是没有线程安全问题的

形式一：

```java
package com.atguigu.single.hungry;

public class HungrySingle {
    private static HungrySingle INSTANCE = new HungrySingle(); //对象是否声明为final 都可以
    
    private HungrySingle(){}
    
    public static HungrySingle getInstance(){
        return INSTANCE;
    }
}
```

形式二：

```java
/*
public class HungryOne{
    public static final HungryOne INSTANCE = new HungryOne();
    private HungryOne(){}
}*/

public enum HungryOne{
    INSTANCE
}
```

测试类：

```java
package com.atguigu.single.hungry;

public class HungrySingleTest {

    static HungrySingle hs1 = null;
    static HungrySingle hs2 = null;

    //演示存在的线程安全问题
    public static void main(String[] args) {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                hs1 = HungrySingle.getInstance();
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                hs2 = HungrySingle.getInstance();
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(hs1);
        System.out.println(hs2);
        System.out.println(hs1 == hs2);//true
    }

}
```

#### 6.1.2 懒汉式线程安全问题

懒汉式：延迟创建对象，第一次调用getInstance方法再创建对象

形式一：

```java
package com.atguigu.single.lazy;

public class LazyOne {
    private static LazyOne instance;

    private LazyOne(){}

    //方式1：同步方法
    public static synchronized LazyOne getInstance1(){
        if(instance == null){
            instance = new LazyOne();
        }
        return instance;
    }
    //方式2：同步代码块
    public static LazyOne getInstance2(){
        synchronized(LazyOne.class) {
            if (instance == null) {
                instance = new LazyOne();
            }
            return instance;
        }
    }
    //方式3：先判断对象是否为null,如果（多个）线程判断为空则进入，如果此时已经有线程已经创建好了，则第二个判断语句为false
    public static LazyOne getInstance3(){
        if(instance == null){
            synchronized (LazyOne.class) {
                if(instance == null){
                try {
                    Thread.sleep(10);//加这个代码，暴露问题
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    instance = new LazyOne();
                }
            }
        }

        return instance;
    }
    /*
    注意：上述方式3中，有指令重排问题
    mem = allocate(); 为单例对象分配内存空间
    instance = mem;   instance引用现在非空，但还未初始化
    ctorSingleton(instance); 为单例对象通过instance调用构造器
    从JDK2开始，分配空间、初始化、调用构造器会在线程的工作存储区一次性完成，然后复制到主存储区。但是需要   
    volatile关键字，避免指令重排。
    */
    
}

```

形式二：使用内部类

```java
package com.atguigu.single.lazy;

public class LazySingle {
    private LazySingle(){}
    
    public static LazySingle getInstance(){
        return Inner.INSTANCE;
    }
    
    private static class Inner{
        static final LazySingle INSTANCE = new LazySingle();
    }
    
}
```

> 内部类只有在外部类被调用才加载，产生INSTANCE实例；又不用加锁。
>
> 此模式具有之前两个模式的优点，同时屏蔽了它们的缺点，是最好的单例模式。
>
> 此时的内部类，使用enum进行定义，也是可以的。

测试类：

```java
package com.atguigu.single.lazy;

import org.junit.Test;

public class TestLazy {
    @Test
    public void test01(){
        LazyOne s1 = LazyOne.getInstance();
        LazyOne s2 = LazyOne.getInstance();

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1 == s2);
    }

    //把s1和s2声明在外面，是想要在线程的匿名内部类中为s1和s2赋值
    LazyOne s1;
    LazyOne s2;
    @Test
    public void test02(){
        Thread t1 = new Thread(){
            public void run(){
                s1 = LazyOne.getInstance();
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                s2 = LazyOne.getInstance();
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1 == s2);
    }


    LazySingle obj1;
    LazySingle obj2;
    @Test
    public void test03(){
        Thread t1 = new Thread(){
            public void run(){
                obj1 = LazySingle.getInstance();
            }
        };
        Thread t2 = new Thread(){
            public void run(){
                obj2 = LazySingle.getInstance();
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj1 == obj2);
    }
}

```

## 8.死锁

### 8.1什么是死锁

==不同的线程分别占用对方需要的同步资源不释放，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁。==

![thread-lock](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/thread-lock.png)

> 【小故事】
>
> 面试官：你能解释清楚什么是死锁，我就录取你！
> 面试者：你录取我，我就告诉你什么是死锁！
> ….
> 恭喜你，面试通过了

一旦出现死锁，整个程序既不会发生异常，也不会给出任何提示，只是所有线程处于阻塞状态，无法继续。

举例1：

```java
public class DeadLockTest {
	public static void main(String[] args) {

		StringBuilder s1 = new StringBuilder();
		StringBuilder s2 = new StringBuilder();

		new Thread() {
			public void run() {
				synchronized (s1) {
					s1.append("a");
					s2.append("1");
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (s2) {
						s1.append("b");
						s2.append("2");
						System.out.println(s1);
						System.out.println(s2);
					}
				}
			}
		}.start();

		new Thread() {
			public void run() {
				synchronized (s2) {
					s1.append("c");
					s2.append("3");

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					synchronized (s1) {
						s1.append("d");
						s2.append("4");

						System.out.println(s1);
						System.out.println(s2);

					}

				}
			}
		}.start();

	}
}
```

举例2：

```java
class A {
	public synchronized void foo(B b) {
		System.out.println("当前线程名: " + Thread.currentThread().getName()
				+ " 进入了A实例的foo方法"); // ①
		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("当前线程名: " + Thread.currentThread().getName()
				+ " 企图调用B实例的last方法"); // ③
		b.last();
	}

	public synchronized void last() {
		System.out.println("进入了A类的last方法内部");
	}
}

class B {
	public synchronized void bar(A a) {
		System.out.println("当前线程名: " + Thread.currentThread().getName()
				+ " 进入了B实例的bar方法"); // ②
		try {
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("当前线程名: " + Thread.currentThread().getName()
				+ " 企图调用A实例的last方法"); // ④
		a.last();
	}

	public synchronized void last() {
		System.out.println("进入了B类的last方法内部");
	}
}

public class DeadLock implements Runnable {
	A a = new A();
	B b = new B();

	public void init() {
		Thread.currentThread().setName("主线程");
		// 调用a对象的foo方法
		a.foo(b);
		System.out.println("进入了主线程之后");
	}

	public void run() {
		Thread.currentThread().setName("副线程");
		// 调用b对象的bar方法
		b.bar(a);
		System.out.println("进入了副线程之后");
	}

	public static void main(String[] args) {
		DeadLock dl = new DeadLock();
		new Thread(dl).start();
		dl.init();
	}
}
```

举例3：

```java
public class TestDeadLock {
	public static void main(String[] args) {
		Object g = new Object();
		Object m = new Object();
		Owner s = new Owner(g,m);
		Customer c = new Customer(g,m);
		new Thread(s).start();
		new Thread(c).start();
	}
}
class Owner implements Runnable{
	private Object goods;
	private Object money;

	public Owner(Object goods, Object money) {
		super();
		this.goods = goods;
		this.money = money;
	}

	@Override
	public void run() {
		synchronized (goods) {
			System.out.println("先给钱");
			synchronized (money) {
				System.out.println("发货");
			}
		}
	}
}
class Customer implements Runnable{
	private Object goods;
	private Object money;

	public Customer(Object goods, Object money) {
		super();
		this.goods = goods;
		this.money = money;
	}

	@Override
	public void run() {
		synchronized (money) {
			System.out.println("先发货");
			synchronized (goods) {
				System.out.println("再给钱");
			}
		}
	}
}
```

### 8.2诱发死锁的原因

- **互斥条件**
- **占用且等待**
- **循环等待**
- **不可抢夺**

以上4个条件，同时出现就会触发死锁。

### 8.3解决死锁

死锁一旦出现，基本很难人为干预，只能尽量规避。可以考虑打破上面的诱发条件。

针对条件1：互斥条件基本上无法被破坏。因为线程需要通过互斥解决安全问题。

针对条件2：可以考虑一次性申请所有所需的资源，这样就不存在等待的问题。

针对条件3：占用部分资源的线程在进一步申请其他资源时，如果申请不到，就主动释放掉已经占用的资源。

针对条件4：可以将资源改为线性顺序。申请资源时，先申请序号较小的，这样避免循环等待问题。

## 9.JDK5.0新特性：Lock(锁)

- JDK5.0的新增功能，保证线程的安全。与采用synchronized相比，Lock可提供多种锁方案，更灵活、更强大。Lock通过显式定义同步锁对象来实现同步。同步锁使用Lock对象充当。
- java.util.concurrent.locks.Lock接口是控制多个线程对共享资源进行访问的工具。锁提供了对共享资源的独占访问，每次只能有一个线程对Lock对象加锁，线程开始访问共享资源之前应先获得Lock对象。
- 在实现线程安全的控制中，比较常用的是`ReentrantLock`，可以显式加锁、释放锁。
  - `ReentrantLock`类实现了 Lock 接口，它拥有与 synchronized 相同的并发性和内存语义，但是添加了类似锁投票、定时锁等候和可中断锁等候的一些特性。此外，它还提供了在激烈争用情况下更佳的性能。
- Lock锁也称同步锁，加锁与释放锁方法，如下：

  * public void lock() :加同步锁。
  * public void unlock() :释放同步锁。
- 代码结构

```java
class A{
    //1. 创建Lock的实例，必须确保多个线程共享同一个Lock实例
	private final ReentrantLock lock = new ReenTrantLock();
	public void m(){
        //2. 调动lock()，实现需共享的代码的锁定
		lock.lock();
		try{
			//保证线程安全的代码;
		}
		finally{
            //3. 调用unlock()，释放共享代码的锁定
			lock.unlock();  
		}
	}
}

```

> 注意：如果同步代码有异常，要将unlock()写入finally语句块。

举例：

```java
import java.util.concurrent.locks.ReentrantLock;

public class HappyNewYear {

    public static void main(String[] args) {
        GetNumber getNumber = new GetNumber();
        Thread thread1 = new Thread(getNumber);
        Thread thread2 = new Thread(getNumber);

        thread1.setName("线程1");
        thread2.setName("线程2");

        thread1.start();
        thread2.start();
    }

}

class GetNumber implements Runnable {
    static int number = 100;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (number >= 1) {
                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number--;
                } else {
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}

```

**synchronized与Lock的对比**

1. Lock是显式锁（手动开启和关闭锁，别忘记关闭锁），synchronized是隐式锁，出了作用域、遇到异常等自动解锁
2. Lock只有代码块锁，synchronized有代码块锁和方法锁
3. 使用Lock锁，JVM将花费较少的时间来调度线程，性能更好。并且具有更好的扩展性（提供更多的子类），更体现面向对象。
4. （了解）Lock锁可以对读不加锁，对写加锁，synchronized不可以
5. （了解）Lock锁可以有多种获取锁的方式，可以从sleep的线程中抢到锁，synchronized不可以

> 说明：开发建议中处理线程安全问题优先使用顺序为：
>
> •    Lock ----> 同步代码块 ----> 同步方法

## 10.线程的通信

### 7.1 线程间通信

**为什么要处理线程间通信：**

==当我们`需要多个线程`来共同完成一件任务，并且我们希望他们`有规律的执行`，那么多线程之间需要一些通信机制，可以协调它们的工作，以此实现多线程共同操作一份数据。==

比如：线程A用来生产包子的，线程B用来吃包子的，包子可以理解为同一资源，线程A与线程B处理的动作，一个是生产，一个是消费，此时B线程必须等到A线程完成后才能执行，那么线程A与线程B之间就需要线程通信，即— **等待唤醒机制。**

### 7.2 等待唤醒机制

这是多个线程间的一种`协作机制`。谈到线程我们经常想到的是线程间的`竞争（race）`，比如去争夺锁，但这并不是故事的全部，线程间也会有协作机制。

在一个线程满足某个条件时，就进入等待状态（`wait() / wait(time)`）， 等待其他线程执行完他们的指定代码过后再将其唤醒（`notify()`）;或可以指定wait的时间，等时间到了自动唤醒；在有多个线程进行等待时，如果需要，可以使用 `notifyAll()`来唤醒所有的等待线程。wait/notify 就是线程间的一种协作机制。

1. wait：线程不再活动，不再参与调度，进入 `wait set` 中，因此不会浪费 CPU 资源，也不会去竞争锁了，这时的线程状态是 WAITING 或 TIMED_WAITING。它还要等着别的线程执行一个`特别的动作`，也即“`通知（notify）`”或者等待时间到，在这个对象上等待的线程从wait set 中释放出来，重新进入到调度队列（`ready queue`）中
2. notify：则选取所通知对象的 wait set 中的一个线程释放；
3. notifyAll：则释放所通知对象的 wait set 上的全部线程。

> 注意：
>
> 被通知的线程被唤醒后也不一定能立即恢复执行，因为它当初中断的地方是在同步块内，而此刻它已经不持有锁，所以它需要再次尝试去获取锁（很可能面临其它线程的竞争），成功后才能在当初调用 wait 方法之后的地方恢复执行。
>
> 总结如下：
>
> - 如果能获取锁，线程就从 WAITING 状态变成 RUNNABLE（可运行） 状态；
> - 否则，线程就从 WAITING 状态又变成 BLOCKED（等待锁） 状态

### 7.3 举例

例题：使用两个线程打印 1-100。线程1, 线程2 交替打印

```java
public class HappyNewYear {

    public static void main(String[] args) {
        GetNumber getNumber = new GetNumber();
        Thread thread1 = new Thread(getNumber);
        Thread thread2 = new Thread(getNumber);

        thread1.setName("线程1");
        thread2.setName("线程2");

        thread1.start();
        thread2.start();
    }
}

class GetNumber implements Runnable {
    static int number = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                notify();
                if (number >= 1) {
                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number--;
//                    线程进入阻塞状态，同时释放对    锁监视器的调用
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    break;
                }
            }
        }
    }
}
```

### 7.4 调用wait和notify需注意的细节

1. wait方法与notify方法必须要由`同一个锁对象调用`。因为：对应的锁对象可以通过notify唤醒使用同一个锁对象调用的wait方法后的线程。
2. wait方法与notify方法是属于Object类的方法的。因为：锁对象可以是任意对象，而任意对象的所属类都是继承了Object类的。
3. <font color='red'>**wait方法与notify方法的调用者必须是同步监视器**</font>。wait方法与notify方法必须要在`同步代码块`或者是`同步函数`中使用。因为：必须要`通过锁对象`调用这2个方法。否则会报java.lang.IllegalMonitorStateException异常。

### 7.5 生产者与消费者问题

等待唤醒机制可以解决经典的“生产者与消费者”的问题。生产者与消费者问题（英语：Producer-consumer problem），也称有限缓冲问题（英语：Bounded-buffer problem），是一个多线程同步问题的经典案例。该问题描述了两个（多个）`共享固定大小缓冲区的线程`——即所谓的“生产者”和“消费者”——在实际运行时会发生的问题。

生产者的主要作用是生成一定量的数据放到缓冲区中，然后重复此过程。与此同时，消费者也在缓冲区消耗这些数据。**该问题的关键就是要保证生产者不会在缓冲区满时加入数据，消费者也不会在缓冲区中空时消耗数据。**

**举例：**

生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品(比如:20），如果生产者试图生产更多的产品，店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品。

类似的场景，比如厨师和服务员等。

**生产者与消费者问题中其实隐含了两个问题：**

* 线程安全问题：因为生产者与消费者共享数据缓冲区，产生安全问题。不过这个问题可以使用同步解决。
* 线程的协调工作问题：
  * 要解决该问题，就必须让生产者线程在缓冲区满时等待(wait)，暂停进入阻塞状态，等到下次消费者消耗了缓冲区中的数据的时候，通知(notify)正在等待的线程恢复到就绪状态，重新开始往缓冲区添加数据。同样，也可以让消费者线程在缓冲区空时进入等待(wait)，暂停进入阻塞状态，等到生产者往缓冲区添加数据之后，再通知(notify)正在等待的线程恢复到就绪状态。通过这样的通信机制来解决此类问题。

**代码实现：**

```java
public class ProducerConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Producer producer = new Producer(clerk);
        Consumer consumer = new Consumer(clerk);

        producer.setName("生产者");
        consumer.setName("消费者");

        producer.start();
        consumer.start();
    }

}

class Clerk {
    private int productNum = 0;

    /**
     * 生产产品
     */
    public synchronized void product() {
        if (productNum >= 20) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        productNum++;
        System.out.println("生产第" + productNum + "个产品");
        notify();
    }

    /**
     * 消费产品
     */
    public synchronized void consume() {
        if (productNum <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("消费第" + productNum + "个产品");
        productNum--;
        notify();
    }
}

class Producer extends Thread {

    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clerk.product();
        }
    }
}

class Consumer extends Thread {
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clerk.consume();
        }
    }
}

```



### 7.6 sleep()和wait()的区别

> 相同点：
>
> * 一旦执行，都会使得当前线程结束执行状态，进入阻塞状态。

> 不同点：
>
> * **定义方法所属的类**：sleep():Thread中定义。  wait():Object中定义
> * **使用范围的不同**：sleep()可以在任何需要使用的位置被调用； wait():必须使用在同步代码块或同步方法中
> * **都在同步结构中使用的时候，是否释放同步监视器**：sleep():不会释放同步监视器 ;wait():会释放同步监视器
> * **结束等待的方式不同**：sleep()：指定时间一到就结束阻塞。 wait():可以指定时间也可以无限等待直到notify或notifyAll。

### 7.7 是否释放锁的操作

任何线程进入同步代码块、同步方法之前，必须先获得对同步监视器的锁定，那么何时会释放对同步监视器的锁定呢？

#### 7.7.1 释放锁的操作

当前线程的同步方法、同步代码块执行结束。

当前线程在同步代码块、同步方法中遇到break、return终止了该代码块、该方法的继续执行。

当前线程在同步代码块、同步方法中出现了未处理的Error或Exception，导致当前线程异常结束。

当前线程在同步代码块、同步方法中执行了锁对象的wait()方法，当前线程被挂起，并释放锁。

#### 7.7.2 不会释放锁的操作

线程执行同步代码块或同步方法时，程序调用Thread.sleep()、Thread.yield()方法暂停当前线程的执行。

线程执行同步代码块时，其他线程调用了该线程的suspend()方法将该该线程挂起，该线程不会释放锁（同步监视器）。

- 应尽量避免使用suspend()和resume()这样的过时来控制线程。



## 11.随堂复习

### 1. 几个概念

```
程序(program)：为完成特定任务，用某种语言编写的`一组指令的集合`。即指一段静态的代码。

进程(process)：程序的一次执行过程，或是正在内存中运行的应用程序。程序是静态的，进程是动态的。
              进程作为操作系统调度和分配资源的最小单位。

线程(thread)：进程可进一步细化为线程，是程序内部的一条执行路径。线程作为CPU调度和执行的最小单位
```

```
线程调度策略
分时调度：所有线程`轮流使用` CPU 的使用权，并且平均分配每个线程占用 CPU 的时间。

抢占式调度：让`优先级高`的线程以`较大的概率`优先使用 CPU。如果线程的优先级相同，那么会随机选择一个(线程随机性)，Java使用的为抢占式调度。
```

```
> 单核CPU与多核CPU
> 并行与并发
```

### 2. 如何创建多线程（重点）

- 方式1：继承Thread类
- 方式2：实现Runnable接口
- 方式3：实现Callable接口 + FutureTask （jdk5.0新增）
- 方式4：使用线程池（jdk5.0新增）

### 3. Thread类的常用方法、线程的生命周期

```
熟悉常用的构造器和方法：
1. 线程中的构造器
- public Thread() :分配一个新的线程对象。
- public Thread(String name) :分配一个指定名字的新的线程对象。
- public Thread(Runnable target) :指定创建线程的目标对象，它实现了Runnable接口中的run方法
- public Thread(Runnable target,String name) :分配一个带有指定目标新的线程对象并指定名字。

2.线程中的常用方法：
> start():①启动线程 ②调用线程的run()
> run():将线程要执行的操作，声明在run()中。
> currentThread():获取当前执行代码对应的线程
> getName(): 获取线程名
> setName(): 设置线程名
> sleep(long millis):静态方法，调用时，可以使得当前线程睡眠指定的毫秒数
> yield():静态方法，一旦执行此方法，就释放CPU的执行权
> join(): 在线程a中通过线程b调用join()，意味着线程a进入阻塞状态，直到线程b执行结束，线程a才结束阻塞状态，继续执行。
> isAlive():判断当前线程是否存活

3. 线程的优先级：
getPriority():获取线程的优先级
setPriority():设置线程的优先级。范围[1,10]


Thread类内部声明的三个常量：
- MAX_PRIORITY（10）：最高优先级
- MIN _PRIORITY （1）：最低优先级
- NORM_PRIORITY （5）：普通优先级，默认情况下main线程具有普通优先级。
```

线程的生命周期：

jdk5.0之前：

![image-20221203142900528](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221203142900528.png)

jdk5.0及之后：Thread类中定义了一个内部类State

```java
public enum State {
        
        NEW,
    
        RUNNABLE,

        BLOCKED,

        WAITING,

        TIMED_WAITING,
 
        TERMINATED;
}
```

![image-20221203143046926](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221203143046926.png)

### 4. 如何解决线程安全问题(重点、难点)

- 什么是线程的安全问题？多个线程操作共享数据，就有可能出现安全问题。

- 如何解决线程的安全问题？有几种方式？

  - 同步机制：① 同步代码块 ② 同步方法

    - 重点关注两个事：共享数据及操作共享数据的代码；同步监视器（保证唯一性）

    ```
    在实现Runnable接口的方式中，同步监视器可以考虑使用：this。
    在继承Thread类的方式中，同步监视器要慎用this，可以考虑使用：当前类.class。
    ```

    ```
    非静态的同步方法，默认同步监视器是this
    静态的同步方法，默认同步监视器是当前类本身。
    ```

  - jdk5.0新增：Lock接口及其实现类。（保证多个线程共用同一个Lock的实例）

### 5. 同步机制相关的问题

- 懒汉式的线程安全的写法
- 同步机制会带来的问题：死锁
  - 死锁产生的条件及规避方式

### 6. 线程间的通信

- 在同步机制下，考虑线程间的通信
- wait() 、notify() 、notifyAll() 都需要使用在同步代码块或同步方法中。

- 高频笔试题：wait() / sleep()


## 12.企业真题

### 2.1 线程概述

#### 1. 什么是线程(*云网络)

略

#### 2. 线程和进程有什么区别(*团、腾\*、\*云网络、神**岳、言\*有物、直\*科技)

进程：对应一个运行中的程序。

线程：运行中的进程的一条或多条执行路径。

#### 3. 多线程使用场景（嘉*医疗）

- 手机app应用的图片的下载
- 迅雷的下载
- Tomcat服务器上web应用，多个客户端发起请求，Tomcat针对多个请求开辟多个线程处理

### 2.2 如何实现多线程

#### 1. 如何在Java中出实现多线程？(阿\*校招、当\*置业、鸿\*网络、奥\*医药、\*科软、慧\*、上海驿\*软件、海\*科)

```
类似问题：
> 创建多线程用Runnable还是Thread(北京中*瑞飞)
> 多线程有几种实现方法，都是什么？（锐*（上海）企业管理咨询）
```

四种。

#### 2. Thread类中的start()和run()有什么区别？(北京中油\*\*、爱\*信、神\*泰岳、直\*科技，\*软国际，上海\*学网络)

start()：① 开启线程 ② 调用线程的run()

#### 3. 启动一个线程是用run()还是start()?（*度）

start()

#### 4. Java中Runnable和Callable有什么不同？(平\*金服、银\*数据、好\*在、亿\*征信、花儿**网络)

```
与之前的方式的对比：与Runnable方式的对比的好处
> call()可以有返回值，更灵活
> call()可以使用throws的方式处理异常，更灵活
> Callable使用了泛型参数，可以指明具体的call()的返回值类型，更灵活

有缺点吗？如果在主线程中需要获取分线程call()的返回值，则此时的主线程是阻塞状态的。
```

#### 5. 什么是线程池，为什么要使用它？(上海明*物联网科技)

```
此方式的好处：
> 提高了程序执行的效率。（因为线程已经提前创建好了）
> 提高了资源的复用率。（因为执行完的线程并未销毁，而是可以继续执行其他的任务）
> 可以设置相关的参数，对线程池中的线程的使用进行管理
```

### 2.3 常用方法、生命周期

#### 1. sleep() 和 yield()区别？(神*泰岳)

sleep()：一旦调用，就进入“阻塞”（或TIMED_WAITING状态）

yield():释放cpu的执行权，处在RUNNABLE的状态

#### 2. 线程创建的中的方法、属性情况？(招通**、数\*互融)

略

#### 3. 线程的生命周期？(中国**电子商务中心、\*科软、慧\*)

略

#### 4. 线程的基本状态以及状态之间的关系？(直*科技)

```
类似问题：
> 线程有哪些状态？如何让线程进入阻塞？（华*中*，*兴）
> 线程有几个状态，就绪和阻塞有什么不同。（美*）
> Java的线程都有哪几种状态（字*跳动、*东、*手）
```

略

#### 5. stop()和suspend()方法为何不推荐使用？（上海驿*软件）

stop()：一旦执行，线程就结束了，导致run()有未执行结束的代码。stop()会导致释放同步监视器，导致线程安全问题。

suspend()：与resume()搭配使用，导致死锁。

#### 6. Java 线程优先级是怎么定义的？（软*动力）

三个常量。[1,10]

### 2.4 线程安全与同步机制

#### 1. 你如何理解线程安全的？线程安全问题是如何造成的？（*软国际）

```
类似问题：
> 线程安全说一下？（奥*医药）
> 对线程安全的理解（*度校招）
> 什么是线程安全？(银*数据)
```

略

#### 2. 多线程共用一个数据变量需要注意什么？（史*夫软件）

线程安全问题

#### 3. 多线程保证线程安全一般有几种方式？（来*科技、北京\*信天\*）

```
类似问题：
> 如何解决其线程安全问题，并且说明为什么这样子去解决？（北京联合**）
> 请说出你所知道的线程同步的方法。（天*伟业）
> 哪些方法实现线程安全？（阿*）   
> 同步有几种实现方法，都是什么? （锐*企业管理咨询）
> 你在实际编码过程中如何避免线程安全问题？（*软国际）
> 如何让线程同步?（*手）
> 多线程下有什么同步措施（阿*校招）
> 同步有几种实现方法，都是什么？（海*科）
```

- 同步机制
- Lock接口

#### 4. 用什么关键字修饰同步方法?（上海驿*软件） 

synchronized

#### 5. synchronized加在静态方法和普通方法区别（来*科技）

同步监视器不同。静态：当前类本身   非静态：this

#### 6. Java中synchronized和ReentrantLock有什么不同(三*重工)

```
类似问题：
> 多线程安全机制中 synchronized和lock的区别（中*国际、*美、鸿*网络）
> 怎么实现线程安全，各个实现方法有什么区别？（美*、字*跳动）
> synchronized 和 lock 区别（阿*、*壳）
```

```
synchronized不管是同步代码块还是同步方法，都需要在结束一对{}之后，释放对同步监视器的调用。
Lock是通过两个方法控制需要被同步的代码，更灵活一些。
Lock作为接口，提供了多种实现类，适合更多更复杂的场景，效率更高。
```



#### 7. 当一个线程进入一个对象的一个synchronized方法后，其它线程是否可进入此对象的其它方法?（鸿*网络）

需要看其他方法是否使用synchronized修饰，同步监视器的this是否是同一个。

只有当使用了synchronized，且this是同一个的情况下，就不能访问了。



#### 8. 线程同步与阻塞的关系？同步一定阻塞吗？阻塞一定同步吗？（阿*校招、西安\*创佳\*）

同步一定阻塞；阻塞不一定同步。

### 2.5 死锁

#### 1. 什么是死锁，产生死锁的原因及必要条件（腾*、阿\*）

```
1. 如何看待死锁？
不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁。
我们编写程序时，要避免出现死锁。

2. 诱发死锁的原因？
- 互斥条件
- 占用且等待
- 不可抢夺（或不可抢占）
- 循环等待

以上4个条件，同时出现就会触发死锁。


3. 如何避免死锁？
针对条件1：互斥条件基本上无法被破坏。因为线程需要通过互斥解决安全问题。
针对条件2：可以考虑一次性申请所有所需的资源，这样就不存在等待的问题。
针对条件3：占用部分资源的线程在进一步申请其他资源时，如果申请不到，就主动释放掉已经占用的资源。
针对条件4：可以将资源改为线性顺序。申请资源时，先申请序号较小的，这样避免循环等待问题。
```

#### 2. 如何避免死锁？(阿\*、北京\*蓝、*手)

见上。

### 2.6 线程通信

#### 1. Java中notify()和notifyAll()有什么区别(汇*天下)

```
notify():一旦执行此方法，就会唤醒被wait()的线程中优先级最高的那一个线程。（如果被wait()的多个线程的优先级相同，则
         随机唤醒一个）。被唤醒的线程从当初被wait的位置继续执行。
notifyAll():一旦执行此方法，就会唤醒所有被wait的线程。
```

#### 2. 为什么wait()和notify()方法要在同步块中调用(北京*智)

因为调用者必须是同步监视器。

#### 3. 多线程：生产者，消费者代码（同步、wait、notifly编程）（猫*娱乐）

```
类似问题：
> 如何写代码来解决生产者消费者问题(上海明*物联网)
> 多线程中生产者和消费者如何保证同步（*为）
> 消费者生产者，写写伪代码（字*）
```

略

#### 4. wait()和sleep()有什么区别？调用这两个函数后，线程状态分别作何改变？（字\*、*东）

```
类似问题：
> 线程中sleep()和wait()有什么区别？（外派*度）
> Java线程阻塞调用 wait 函数和 sleep 区别和联系（阿*）
> wait和sleep的区别，他们两个谁会释放锁（软*动力、*创）
```

```
相同点：一旦执行，当前线程都会进入阻塞状态

不同点：
> 声明的位置：wait():声明在Object类中
            sleep():声明在Thread类中，静态的
> 使用的场景不同：wait():只能使用在同步代码块或同步方法中
               sleep():可以在任何需要使用的场景
> 使用在同步代码块或同步方法中：wait():一旦执行，会释放同步监视器
                          sleep():一旦执行，不会释放同步监视器
> 结束阻塞的方式：wait(): 到达指定时间自动结束阻塞 或 通过被notify唤醒，结束阻塞
               sleep(): 到达指定时间自动结束阻塞
```



### 2.7 单例模式（线程安全）

#### 1. 手写一个单例模式(Singleton)，还要安全的（\*通快递、君*科技）

饿汉式；安全的懒汉式；内部类；

#### 2. 手写一个懒汉式的单例模式&解决其线程安全问题，并且说明为什么这样子去解决（5*）

```
类似问题：
> 手写一个懒汉式的单例模式（北京联合**）
```

同上。



# 第10章_常用类和基础API



<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC11%E7%AB%A0.png" alt="第3阶段：Java高级应用-第11章" style="zoom:50%;" />

## 1. 字符串相关类之不可变字符序列：String

### 1.1 String的特性

- `java.lang.String` 类代表字符串。Java程序中所有的字符串文字（例如`"hello"` ）都可以看作是实现此类的实例。

- <font color='red'>字符串是常量，被final修饰，用双引号引起来表示。它们的值在创建之后不能更改</font>。

- 字符串String类型本身是final声明的，意味着我们不能继承String。

- String对象的字符内容是存储在一个字符数组value[]中的。`"abc"` 等效于 `char[] data={'h','e','l','l','o'}`。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514184404024.png" alt="image-20220514184404024" style="zoom:80%;" />

  ```java
  //jdk8中的String源码：
  public final class String
      implements java.io.Serializable, Comparable<String>, CharSequence {
      /** The value is used for character storage. */
      private final char value[]; //String对象的字符内容是存储在此数组中
   
      /** Cache the hash code for the string */
      private int hash; // Default to 0
  
  ```

  - private意味着外面无法直接获取字符数组，而且String没有提供value的get和set方法。

  - final意味着字符数组的引用不可改变，而且String也没有提供方法来修改value数组某个元素值

  - <font color='red'>因此字符串的字符数组内容也不可变的，即String代表着不可变的字符序列。一旦对字符串进行修改，就会产生新对象。</font>

  - JDK9只有，底层将char[]数组更换为了byte[]数组。

    ```java
    public final class String implements java.io.Serializable, Comparable<String>, CharSequence { 
        @Stable
        private final byte[] value;
    }
    
    //官方说明：... that most String objects contain only Latin-1 characters. Such characters require only one byte of storage, hence half of the space in the internal char arrays of such String objects is going unused.
    大多数String对象只包含Latin-1字符。这样的字符只需要一个字节的存储空间，因此这样的String对象的内部char数组中有一半的空间未被使用。
    
    //细节：... The new String class will store characters encoded either as ISO-8859-1/Latin-1 (one byte per character), or as UTF-16 (two bytes per character), based upon the contents of the string. The encoding flag will indicate which encoding is used.
    ```

- Java 语言提供对字符串串联符号（"+"）以及将其他对象转换为字符串的特殊支持（toString()方法）。

### 1.2 String的内存结构

#### 1.2.1 概述

==因为字符串对象设计为不可变，那么所以`字符串有常量池`来保存很多常量对象。==<font color='red'>字符串常量池中不允许存放两个相同的字符串常量。</font>

JDK6中，字符串常量池在方法区。JDK7开始，就移到堆空间，直到目前JDK17版本。

举例内存结构分配：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1562945799274.png" alt="image-20220405160036240" />

#### 1.2.2 练习类型1：拼接

String类型的连接操作：

> * 常量 + 常量：结果仍然存储在字符串常量池中
> * 常量 + 变量/变量 + 变量：都会通过new关键字创建一个新的字符串，返回堆空间中此字符串对象的地址。

> String s1 = "hello"，其中，s1是常量还是变量？
>
> 在 Java 中，“hello”是一个字符串常量，而 String s1 是一个字符串引用变量。当 Java 程序执行到 String s1 = "hello" 时，Java 编译器会在常量池中查找是否有值为 “hello” 的字符串常量，如果没有则新建并存储在常量池中，然后将该常量池中的引用赋值给 s1 引用变量。也就是说 s1 引用变量并不是常量，它可以被重新赋值或者指向其他的字符串对象。

```java
String s1 = "hello";
String s2 = "hello";
System.out.println(s1 == s2);
// 内存中只有一个"hello"对象被创建，同时被s1和s2共享。
```

对应内存结构为：（以下内存结构以`JDK6为例`绘制）：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405152839525.png" alt="image-20220405152839525" style="zoom:67%;" />

进一步：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405152941599.png" alt="image-20220405152941599" style="zoom:67%;" />

```java
Person p1 = new Person();
p1.name = “Tom";

Person p2 = new Person();
p2.name = “Tom";

System.out.println(p1.name.equals( p2.name)); //
System.out.println(p1.name == p2.name); //
System.out.println(p1.name == "Tom"); //
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405153027693.png" alt="image-20220405153027693" style="zoom:67%;" />

#### 1.2.3 练习类型2：new

String str1 = “abc”; 与 String str2 = new String(“abc”);的区别？

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405160149200.png" alt="image-20220405160149200" style="zoom:67%;" />

str2 首先指向堆中的一个字符串对象，然后堆中字符串的value数组指向常量池中常量对象的value数组。

> - 字符串常量存储在字符串常量池，目的是共享。
>
> - 字符串非常量对象存储在堆中。

String str2 = new String(“abc”);创建了几个对象？

> 创建了两个，一个是堆内存当中的字符串对象，一个是字符串常量池当中的字面量。

练习：

```java
String s1 = "javaEE";
String s2 = "javaEE";
String s3 = new String("javaEE");
String s4 = new String("javaEE");

System.out.println(s1 == s2);//true
System.out.println(s1 == s3);//false
System.out.println(s1 == s4);//false
System.out.println(s3 == s4);//false
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405160321172.png" alt="image-20220405160321172" style="zoom:67%;" />

#### 1.2.4 练习类型3：intern()

- **String s1 = "a";** 

说明：在字符串常量池中创建了一个字面量为"a"的字符串。

- **s1 = s1 + "b";** 

说明：实际上原来的“a”字符串对象已经丢弃了，现在在堆空间中产生了一个字符串s1+"b"（也就是"ab")。如果多次执行这些改变串内容的操作，会导致大量副本字符串对象存留在内存中，降低效率。如果这样的操作放到循环中，会极大影响程序的性能。

- **String s2 = "ab";**

说明：直接在字符串常量池中创建一个字面量为"ab"的字符串。

- **String s3 = "a" + "b";**

说明：s3指向字符串常量池中已经创建的"ab"的字符串。

- **String s4 = s1.intern();**

说明：堆空间的s1对象在调用intern()之后，会将常量池中已经存在的"ab"字符串赋值给s4。

练习：

```java
String s1 = "hello";
String s2 = "world";
String s3 = "hello" + "world";
String s4 = s1 + "world";
String s5 = s1 + s2;
String s6 = (s1 + s2).intern();

System.out.println(s3 == s4);
System.out.println(s3 == s5);
System.out.println(s4 == s5);
System.out.println(s3 == s6);
```

> **结论：**
>
> （1）常量+常量：结果是常量池。且常量池中不会存在相同内容的常量。
>
> （2）常量与变量 或 变量与变量：结果在堆中
>
> （3）拼接后调用intern方法：返回值在常量池中

练习：

```java
@Test
public void test01(){
	String s1 = "hello";
	String s2 = "world";
	String s3 = "helloworld";
		
	String s4 = s1 + "world";//s4字符串内容也helloworld，s1是变量，"world"常量，变量 + 常量的结果在堆中
	String s5 = s1 + s2;//s5字符串内容也helloworld，s1和s2都是变量，变量 + 变量的结果在堆中
	String s6 = "hello" + "world";//常量+ 常量 结果在常量池中，因为编译期间就可以确定结果
		
	System.out.println(s3 == s4);//false
	System.out.println(s3 == s5);//false
	System.out.println(s3 == s6);//true
}

@Test
public void test02(){
	final String s1 = "hello";
	final String s2 = "world";
	String s3 = "helloworld";
	
	String s4 = s1 + "world";//s4字符串内容也helloworld，s1是常量，"world"常量，常量+常量结果在常量池中
	String s5 = s1 + s2;//s5字符串内容也helloworld，s1和s2都是常量，常量+ 常量 结果在常量池中
	String s6 = "hello" + "world";//常量+ 常量 结果在常量池中，因为编译期间就可以确定结果
		
	System.out.println(s3 == s4);//true
	System.out.println(s3 == s5);//true
	System.out.println(s3 == s6);//true
}

@Test
public void test01(){
	String s1 = "hello";
	String s2 = "world";
	String s3 = "helloworld";
		
	String s4 = (s1 + "world").intern();//把拼接的结果放到常量池中
	String s5 = (s1 + s2).intern();
		
	System.out.println(s3 == s4);//true
	System.out.println(s3 == s5);//true
}
```

练习：下列程序运行的结果：

```java
public class TestString {
	public static void main(String[] args) {
		String str = "hello";
		String str2 = "world";
		String str3 ="helloworld";
		
		String str4 = "hello".concat("world");
		String str5 = "hello"+"world";
		
		System.out.println(str3 == str4);//false
		System.out.println(str3 == str5);//true
	}
}
```

> concat方法拼接，哪怕是两个常量对象拼接，结果也是在堆。

练习：下列程序运行的结果：

```java
public class StringTest {

    String str = new String("good");
    char[] ch = { 't', 'e', 's', 't' };

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
    }
    public static void main(String[] args) {
        StringTest ex = new StringTest();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str + " and ");//
        System.out.println(ex.ch);
    }
}

```



### 1.3 String的常用API-1

#### 1.3.1 构造器

* `public String() ` ：初始化新创建的 String对象，以使其表示空字符序列。
* ` String(String original)`： 初始化一个新创建的 `String` 对象，使其表示一个与参数相同的字符序列；换句话说，新创建的字符串是该参数字符串的副本。
* `public String(char[] value) ` ：通过当前参数中的字符数组来构造新的String。
* `public String(char[] value,int offset, int count) ` ：通过字符数组的一部分来构造新的String。
* `public String(byte[] bytes) ` ：通过使用平台的**默认字符集**解码当前参数中的字节数组来构造新的String。
* `public String(byte[] bytes,String charsetName) ` ：通过使用指定的字符集解码当前参数中的字节数组来构造新的String。

举例：

```java
//字面量定义方式：字符串常量对象
String str = "hello";

//构造器定义方式：无参构造
String str1 = new String();

//构造器定义方式：创建"hello"字符串常量的副本
String str2 = new String("hello");

//构造器定义方式：通过字符数组构造
char chars[] = {'a', 'b', 'c','d','e'};     
String str3 = new String(chars);
String str4 = new String(chars,0,3);

//构造器定义方式：通过字节数组构造
byte bytes[] = {97, 98, 99 };     
String str5 = new String(bytes);
String str6 = new String(bytes,"GBK");
```

```java
public static void main(String[] args) {
	char[] data = {'h','e','l','l','o','j','a','v','a'};
	String s1 = String.copyValueOf(data);
	String s2 = String.copyValueOf(data,0,5);
	int num = 123456;
	String s3 = String.valueOf(num);
	
    System.out.println(s1);
	System.out.println(s2);
	System.out.println(s3);
}
```

#### 1.3.2 String与其他结构间的转换

> **字符串 --> 基本数据类型、包装类：**
>
> - Integer包装类的public static int parseInt(String s)：可以将由“数字”字符组成的字符串转换为整型。
> - 类似地，使用java.lang包中的Byte、Short、Long、Float、Double类调相应的类方法可以将由“数字”字符组成的字符串，转化为相应的基本数据类型。
>
> **基本数据类型、包装类 --> 字符串：**
>
> - 调用String类的public String valueOf(int n)可将int型转换为字符串
> - 相应的valueOf(byte b)、valueOf(long l)、valueOf(float f)、valueOf(double d)、valueOf(boolean b)可由参数的相应类型到字符串的转换。
>
>  **字符数组 -->  字符串：**
>
> - String 类的构造器：String(char[]) 和 String(char[]，int offset，int length) 分别用字符数组中的全部字符和部分字符创建字符串对象。 
>
>  **字符串 -->  字符数组：**
>
> - public char[] toCharArray()：将字符串中的全部字符存放在一个字符数组中的方法。
>
> - public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)：提供了将指定索引范围内的字符串存放到数组中的方法。
>
> **字符串 --> 字节数组：（编码）**
>
> - public byte[] getBytes() ：使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
> - public byte[] getBytes(String charsetName) ：使用指定的字符集将此 String 编码到 byte 序列，并将结果存储到新的 byte 数组。
>
>  **字节数组 --> 字符串：（解码）**
>
> - String(byte[])：通过使用平台的默认字符集解码指定的 byte 数组，构造一个新的 String。
> - String(byte[]，int offset，int length) ：用指定的字节数组的一部分，即从数组起始位置offset开始取length个字节构造一个字符串对象。
> - String(byte[], String charsetName ) 或 new String(byte[], int, int,String charsetName )：解码，按照指定的编码方式进行解码。

代码示例：

```java
@Test
public void test01() throws Exception {
    String str = "中国";
    System.out.println(str.getBytes("ISO8859-1").length);// 2
    // ISO8859-1把所有的字符都当做一个byte处理，处理不了多个字节
    System.out.println(str.getBytes("GBK").length);// 4 每一个中文都是对应2个字节
    System.out.println(str.getBytes("UTF-8").length);// 6 常规的中文都是3个字节

    /*
     * 不乱码：（1）保证编码与解码的字符集名称一样（2）不缺字节
     */
    System.out.println(new String(str.getBytes("ISO8859-1"), "ISO8859-1"));// 乱码
    System.out.println(new String(str.getBytes("GBK"), "GBK"));// 中国
    System.out.println(new String(str.getBytes("UTF-8"), "UTF-8"));// 中国
}
```

### 1.4 String的常用API-2

`String` 类包括的方法可用于检查序列的单个字符、比较字符串、搜索字符串、提取子字符串、创建字符串副本并将所有字符全部转换为大写或小写。 

#### 1.4.1 系列1：常用方法

> （1）`boolean isEmpty()`：字符串是否为空
> 		  （2）`int length()`：返回字符串的长度
>       	  （3）`String concat(xx)`：拼接
>            （4）`boolean equals(Object obj)`：比较字符串是否相等，区分大小写
>            （5）`boolean equalsIgnoreCase(Object obj)`：比较字符串是否相等，不区分大小写
>            （6）`int compareTo(String other)`：比较字符串大小，区分大小写，按照Unicode编码值比较大小
>            （7）`int compareToIgnoreCase(String other)`：比较字符串大小，不区分大小写
>            （8）`String toLowerCase()`：将字符串中大写字母转为小写
>            （9）`String toUpperCase()`：将字符串中小写字母转为大写
>           （10）`String trim()`：去掉字符串前后空白符
>           （11）`public String intern()`：结果在常量池中共享

```java
	@Test
	public void test01(){
		//将用户输入的单词全部转为小写，如果用户没有输入单词，重新输入
		Scanner input = new Scanner(System.in);
		String word;
		while(true){
			System.out.print("请输入单词：");
			word = input.nextLine();
			if(word.trim().length()!=0){
				word = word.toLowerCase();
				break;
			}
		}
		System.out.println(word);
	}

	@Test
	public void test02(){
        //随机生成验证码，验证码由0-9，A-Z,a-z的字符组成
		char[] array = new char[26*2+10];
		for (int i = 0; i < 10; i++) {
			array[i] = (char)('0' + i);
		}
		for (int i = 10,j=0; i < 10+26; i++,j++) {
			array[i] = (char)('A' + j);
		}
		for (int i = 10+26,j=0; i < array.length; i++,j++) {
			array[i] = (char)('a' + j);
		}
		String code = "";
		Random rand = new Random();
		for (int i = 0; i < 4; i++) {
			code += array[rand.nextInt(array.length)];
		}
		System.out.println("验证码：" + code);
		//将用户输入的单词全部转为小写，如果用户没有输入单词，重新输入
		Scanner input = new Scanner(System.in);
		System.out.print("请输入验证码：");
		String inputCode = input.nextLine();
		
		if(!code.equalsIgnoreCase(inputCode)){
			System.out.println("验证码输入不正确");
		}
	}
```

#### 1.4.2 系列2：查找

> （11）`boolean contains(xx)`：是否包含xx
> 		  （12）`int indexOf(xx)`：从前往后找当前字符串中xx，即如果有返回第一次出现的下标，要是没有返回-1
> 		  （13）`int indexOf(String str, int fromIndex)`：返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
> 		  （14）`int lastIndexOf(xx)`：从后往前找当前字符串中xx，即如果有返回最后一次出现的下标，要是没有返回-1
> 		  （15）`int lastIndexOf(String str, int fromIndex)`：返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。

```java
	@Test
	public void test01(){
		String str = "尚硅谷是一家靠谱的培训机构，尚硅谷可以说是IT培训的小清华，JavaEE是尚硅谷的当家学科，尚硅谷的大数据培训是行业独角兽。尚硅谷的前端和UI专业一样独领风骚。";
		System.out.println("是否包含清华：" + str.contains("清华"));
		System.out.println("培训出现的第一次下标：" + str.indexOf("培训"));
		System.out.println("培训出现的最后一次下标：" + str.lastIndexOf("培训"));
	}
```

#### 1.4.3 系列3：字符串截取

> （16）`String substring(int beginIndex)` ：返回一个新的字符串，它是此字符串的从beginIndex开始截取到最后的一个子字符串。 
> 		  （17）`String substring(int beginIndex, int endIndex) `：返回一个新字符串，它是此字符串从beginIndex开始截取到endIndex(不包含)的一个子字符串。 

```java
public class StringTest {
    private static int count = 0;
    public static void main(String[] args) {
        String str1 = "abrfabsad";
        String str2 = "ab";
        int number = getCount(str1, str2);
        System.out.println(str2 + "在" + str1 + "中出现的次数为：" + number);
    }

    public static int getCount(String str, String key) {
        while (true) {
            if (str.indexOf(key) >= 0) {
                count++;
                //截取字符串
                str = str.substring(str.indexOf(key) + 2);
            } else {
                break;
            }
        }
        return count;
    }
}
```

![image-20230324221737191](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230324221737191.png)

#### 1.4.4 系列4：和字符/字符数组相关

> （18）`char charAt(index)`：返回[index]位置的字符
> 		  （19）`char[] toCharArray()`： 将此字符串转换为一个新的字符数组返回
> 		  （20）`static String valueOf(char[] data) ` ：返回指定数组中表示该字符序列的 String
> 		  （21）`static String valueOf(char[] data, int offset, int count) `： 返回指定数组中表示该字符序列的 String
> 		  （22）`static String copyValueOf(char[] data)`： 返回指定数组中表示该字符序列的 String
> 		  （23）`static String copyValueOf(char[] data, int offset, int count)`：返回指定数组中表示该字符序列的 String

```java
	@Test
	public void test01(){
		//将字符串中的字符按照大小顺序排列
		String str = "helloworldjavaatguigu";
		char[] array = str.toCharArray();
		Arrays.sort(array);
		str = new String(array);
		System.out.println(str);
	}
	
	@Test
	public void test02(){
		//将首字母转为大写
		String str = "jack";
		str = Character.toUpperCase(str.charAt(0))+str.substring(1);
		System.out.println(str);
	}
	@Test
	public void test03(){
		char[] data = {'h','e','l','l','o','j','a','v','a'};
		String s1 = String.copyValueOf(data);
		String s2 = String.copyValueOf(data,0,5);
		int num = 123456;
		String s3 = String.valueOf(num);
	
    	System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
	}
```

#### 1.4.5 系列5：开头与结尾

> （24）`boolean startsWith(xx)`：测试此字符串是否以指定的前缀开始 
> 		  （25）`boolean startsWith(String prefix, int toffset)`：测试此字符串从指定索引开始的子字符串是否以指定前缀开始
> 		  （26）`boolean endsWith(xx)`：测试此字符串是否以指定的后缀结束 

```java
	@Test
	public void test1(){
		String name = "张三";
		System.out.println(name.startsWith("张"));
	}
	
	@Test
	public void test2(){
		String file = "Hello.txt";
		if(file.endsWith(".java")){
			System.out.println("Java源文件");
		}else if(file.endsWith(".class")){
			System.out.println("Java字节码文件");
		}else{
			System.out.println("其他文件");
		}
	}
```

#### 1.4.6 系列6：替换

> （27）`String replace(char oldChar, char newChar)`：返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的。 不支持正则。
> 		  （28）`String replace(CharSequence target, CharSequence replacement)`：使用指定的字面值替换序列替换此字符串所有匹配字面值目标序列的子字符串。 
> 		  （29）`String replaceAll(String regex, String replacement)`：使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。 
> 		  （30）`String replaceFirst(String regex, String replacement)`：使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。 

```java
@Test
public void test1(){
    String str1 = "hello244world.java;887";
    //把其中的非字母去掉
    str1 = str1.replaceAll("[^a-zA-Z]", "");
    System.out.println(str1);

    String str2 = "12hello34world5java7891mysql456";
    //把字符串中的数字替换成,，如果结果中开头和结尾有，的话去掉
    String string = str2.replaceAll("\\d+", ",").replaceAll("^,|,$", "");
    System.out.println(string);

}
```

### 1.5 常见算法题目

**题目1：**模拟一个trim方法，去除字符串两端的空格。

```java
	public String myTrim(String str) {
		if (str != null) {
			int start = 0;// 用于记录从前往后首次索引位置不是空格的位置的索引
			int end = str.length() - 1;// 用于记录从后往前首次索引位置不是空格的位置的索引

			while (start < end && str.charAt(start) == ' ') {
				start++;
			}

			while (start < end && str.charAt(end) == ' ') {
				end--;
			}
			if (str.charAt(start) == ' ') {
				return "";
			}

			return str.substring(start, end + 1);
		}
		return null;
	}

	@Test
	public void testMyTrim() {
		String str = "   a   ";
		// str = " ";
		String newStr = myTrim(str);
		System.out.println("---" + newStr + "---");
	}
```

**题目2：**将一个字符串进行反转。将字符串中指定部分进行反转。比如“ab`cdef`g”反转为”ab`fedc`g”

```java
	// 方式一：
	public String reverse1(String str, int start, int end) {// start:2,end:5
		if (str != null) {
			// 1.
			char[] charArray = str.toCharArray();
			// 2.
			for (int i = start, j = end; i < j; i++, j--) {
				char temp = charArray[i];
				charArray[i] = charArray[j];
				charArray[j] = temp;
			}
			// 3.
			return new String(charArray);

		}
		return null;

	}

	// 方式二：
	public String reverse2(String str, int start, int end) {
		// 1.
		String newStr = str.substring(0, start);// ab
		// 2.
		for (int i = end; i >= start; i--) {
			newStr += str.charAt(i);
		} // abfedc
			// 3.
		newStr += str.substring(end + 1);
		return newStr;
	}

	// 方式三：推荐 （相较于方式二做的改进）
	public String reverse3(String str, int start, int end) {// ArrayList list = new ArrayList(80);
		// 1.
		StringBuffer s = new StringBuffer(str.length());
		// 2.
		s.append(str.substring(0, start));// ab
		// 3.
		for (int i = end; i >= start; i--) {
			s.append(str.charAt(i));
		}

		// 4.
		s.append(str.substring(end + 1));

		// 5.
		return s.toString();

	}

	@Test
	public void testReverse() {
		String str = "abcdefg";
		String str1 = reverse3(str, 2, 5);
		System.out.println(str1);// abfedcg

	}
```

**题目3：**获取一个字符串在另一个字符串中出现的次数。
              比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数

```java
	// 第3题
	// 判断str2在str1中出现的次数
	public int getCount(String mainStr, String subStr) {
		if (mainStr.length() >= subStr.length()) {
			int count = 0;
			int index = 0;
			// while((index = mainStr.indexOf(subStr)) != -1){
			// count++;
			// mainStr = mainStr.substring(index + subStr.length());
			// }
			// 改进：
			while ((index = mainStr.indexOf(subStr, index)) != -1) {
				index += subStr.length();
				count++;
			}

			return count;
		} else {
			return 0;
		}

	}

	@Test
	public void testGetCount() {
		String str1 = "cdabkkcadkabkebfkabkskab";
		String str2 = "ab";
		int count = getCount(str1, str2);
		System.out.println(count);
	}
```

**题目4：**获取两个字符串中最大相同子串。比如：
              str1 = "abcwerthelloyuiodef“;str2 = "cvhellobnm"
              提示：将短的那个串进行长度依次递减的子串与较长的串比较。

```java
	// 第4题
	// 如果只存在一个最大长度的相同子串
	public String getMaxSameSubString(String str1, String str2) {
		if (str1 != null && str2 != null) {
			String maxStr = (str1.length() > str2.length()) ? str1 : str2;
			String minStr = (str1.length() > str2.length()) ? str2 : str1;

			int len = minStr.length();

			for (int i = 0; i < len; i++) {// 0 1 2 3 4 此层循环决定要去几个字符

				for (int x = 0, y = len - i; y <= len; x++, y++) {

					if (maxStr.contains(minStr.substring(x, y))) {

						return minStr.substring(x, y);
					}

				}

			}
		}
		return null;
	}

	// 如果存在多个长度相同的最大相同子串
	// 此时先返回String[]，后面可以用集合中的ArrayList替换，较方便
	public String[] getMaxSameSubString1(String str1, String str2) {
		if (str1 != null && str2 != null) {
			StringBuffer sBuffer = new StringBuffer();
			String maxString = (str1.length() > str2.length()) ? str1 : str2;
			String minString = (str1.length() > str2.length()) ? str2 : str1;

			int len = minString.length();
			for (int i = 0; i < len; i++) {
				for (int x = 0, y = len - i; y <= len; x++, y++) {
					String subString = minString.substring(x, y);
					if (maxString.contains(subString)) {
						sBuffer.append(subString + ",");
					}
				}
				System.out.println(sBuffer);
				if (sBuffer.length() != 0) {
					break;
				}
			}
			String[] split = sBuffer.toString().replaceAll(",$", "").split("\\,");
			return split;
		}

		return null;
	}
	// 如果存在多个长度相同的最大相同子串：使用ArrayList
//	public List<String> getMaxSameSubString1(String str1, String str2) {
//		if (str1 != null && str2 != null) {
//			List<String> list = new ArrayList<String>();
//			String maxString = (str1.length() > str2.length()) ? str1 : str2;
//			String minString = (str1.length() > str2.length()) ? str2 : str1;
//
//			int len = minString.length();
//			for (int i = 0; i < len; i++) {
//				for (int x = 0, y = len - i; y <= len; x++, y++) {
//					String subString = minString.substring(x, y);
//					if (maxString.contains(subString)) {
//						list.add(subString);
//					}
//				}
//				if (list.size() != 0) {
//					break;
//				}
//			}
//			return list;
//		}
//
//		return null;
//	}

	@Test
	public void testGetMaxSameSubString() {
		String str1 = "abcwerthelloyuiodef";
		String str2 = "cvhellobnmiodef";
		String[] strs = getMaxSameSubString1(str1, str2);
		System.out.println(Arrays.toString(strs));
	}
```

**题目5：**对字符串中字符进行自然顺序排序。
提示：
1）字符串变成字符数组。
2）对数组排序，选择，冒泡，Arrays.sort();
3）将排序后的数组变成字符串。

```java
	// 第5题
	@Test
	public void testSort() {
		String str = "abcwerthelloyuiodef";
		char[] arr = str.toCharArray();
		Arrays.sort(arr);

		String newStr = new String(arr);
		System.out.println(newStr);
	}
```

## 2. 字符串相关类之可变字符序列：StringBuffer、StringBuilder

### 2.1 为什么要引入可变字符序列

<font color='red'>因为String对象是不可变对象，虽然可以共享常量对象，但是对于频繁字符串的修改和拼接操作，效率极低，空间消耗也比较高。</font>==因此，JDK又在java.lang包提供了可变字符序列StringBuffer和StringBuilder类型。==

### 2.2String、StringBuffer、StringBuilder的区别

> - `String`:不可变的字符序列； 底层使用char[]数组存储(JDK8.0中)
> - `StringBuffer`:可变的字符序列；线程安全（<font color='red'>方法有synchronized修饰</font>），效率低；底层使用char[]数组存储 (JDK8.0中)
> - `StringBuilder`:可变的字符序列； jdk1.5引入，线程不安全的，效率高；底层使用char[]数组存储(JDK8.0中)
> - `StringBuffer`和`StringBuilder`都是继承于`AbstractStringBuilder`



### 2.3 StringBuffer与StringBuilder的理解

- java.lang.StringBuffer代表`可变的字符序列`，JDK1.0中声明，可以对字符串内容进行增删，此时不会产生新的对象。比如：

  ```java
  //情况1:
  String s = new String("我喜欢学习"); 
  //情况2：
  StringBuffer buffer = new StringBuffer("我喜欢学习"); 
  buffer.append("数学"); 
  ```

  ![image-20220405221714261](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405221714261.png)

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220228153030902.png" alt="image-20220228153030902" style="zoom:80%;" />


- 继承结构：


 ![image-20220405174233055](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405174233055.png)

 <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405174414780.png" alt="image-20220405174414780" style="zoom:67%;" />

- StringBuilder 和 StringBuffer 非常类似，均代表可变的字符序列，而且提供相关功能的方法也一样。



### 2.4StringBuilder、StringBuffer是如何实现扩容的

> `StringBuilder`和`StringBuffer`都是可变字符串类，可以动态的增加或删除字符串的长度。在扩容时，它们内部的实现是类似的。
>
> 首先，它们会初始化一个数组作为字符串的存储容器，其默认长度为16个字符，称为“字符数组”（char array）。当字符串长度超过了这个字符数组的长度时，就需要对字符数组进行扩容。
>
> 这个扩容的过程涉及到以下几个关键参数：
>
> 1. “容量”（capacity）：字符数组的长度，即容器的大小。
> 2. “长度”（length）：字符串的长度，即实际使用的容器大小。
> 3. “增量”（increment）：数组容器每次扩容的大小，通常是一个固定的常数。
>
> 当字符串长度超过了容器大小，StringBuilder和StringBuffer会按照一定的公式计算出新容量，然后把扩容后的字符数组复制到一个新的、更大的字符数组中，并且更新容量值。如果需要的话，它们还会把字符串的内容复制到新的字符数组中。
>
> 具体来说，以StringBuilder为例，它的扩容过程如下：
>
> 1. 当要插入的字符串长度大于StringBuilder实例初始的内部字符数组容量时，StringBuilder会按照一定的公式计算出新容量newCapacity：
>
>    newCapacity = (oldCapacity * 2) + 2
>
> 2. 然后，StringBuilder会创建一个新的字符数组，长度为newCapacity，并把原字符数组中的内容复制到新的字符数组中。
>
> 3. 最后，StringBuilder会更新容器大小，将新的字符数组设置为内部存储容器。
>
> StringBuffer类的扩容过程类似，只是它会添加一个synchronized关键字保证线程安全。

### 2.5 StringBuilder、StringBuffer的API

StringBuilder、StringBuffer的API是完全一致的，并且很多方法与String相同。

**1、常用API**

> （1）`StringBuffer append(xx)`：提供了很多的append()方法，用于进行字符串追加的方式拼接
> 		  （2）`StringBuffer delete(int start, int end)`：删除[start,end)之间字符
> 		  （3）`StringBuffer deleteCharAt(int index)`：删除[index]位置字符
> 		  （4）`StringBuffer replace(int start, int end, String str)`：替换[start,end)范围的字符序列为str
> 		  （5）`void setCharAt(int index, char c)`：替换[index]位置字符
> 		  （6）`char charAt(int index)`：查找指定index位置上的字符
> 		  （7）`StringBuffer insert(int index, xx)`：在[index]位置插入xx
> 		  （8）`int length()`：返回存储的字符数据的长度
> 		  （9）`StringBuffer reverse()`：反转

> - 当append和insert时，如果原来value数组长度不够，可扩容。
>
> - 如上(1)(2)(3)(4)(9)这些方法支持`方法链操作`。原理：
>
>   ![image-20220405223542750](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405223542750.png)

**2、其它API**

> （1）`int indexOf(String str)`：在当前字符序列中查询str的第一次出现下标
> 		  （2）`int indexOf(String str, int fromIndex)`：在当前字符序列[fromIndex,最后]中查询str的第一次出现下标
> 		  （3）`int lastIndexOf(String str)`：在当前字符序列中查询str的最后一次出现下标
> 		  （4）`int lastIndexOf(String str, int fromIndex)`：在当前字符序列[fromIndex,最后]中查询str的最后一次出现下标
> 		  （5）`String substring(int start)`：截取当前字符序列[start,最后]
> 		  （6）`String substring(int start, int end)`：截取当前字符序列[start,end)
> 		  （7）`String toString()`：返回此序列中数据的字符串表示形式
> 		  （8）`void setLength(int newLength)` ：设置当前字符序列长度为newLength

```java
@Test
public void test1(){
    StringBuilder s = new StringBuilder();
    s.append("hello").append(true).append('a').append(12).append("atguigu");
    System.out.println(s);
    System.out.println(s.length());
}

@Test
public void test2(){
    StringBuilder s = new StringBuilder("helloworld");
    s.insert(5, "java");
    s.insert(5, "chailinyan");
    System.out.println(s);
}

@Test
public void test3(){
    StringBuilder s = new StringBuilder("helloworld");
    s.delete(1, 3);
    s.deleteCharAt(4);
    System.out.println(s);
}
@Test
public void test4(){
    StringBuilder s = new StringBuilder("helloworld");
    s.reverse();
    System.out.println(s);
}

@Test
public void test5(){
    StringBuilder s = new StringBuilder("helloworld");
    s.setCharAt(2, 'a');
    System.out.println(s);
}

@Test
public void test6(){
    StringBuilder s = new StringBuilder("helloworld");
    s.setLength(30);
    System.out.println(s);
}
```

### 2.6 效率测试

```java
//初始设置
long startTime = 0L;
long endTime = 0L;
String text = "";
StringBuffer buffer = new StringBuffer("");
StringBuilder builder = new StringBuilder("");

//开始对比
startTime = System.currentTimeMillis();
for (int i = 0; i < 20000; i++) {
    buffer.append(String.valueOf(i));
}
endTime = System.currentTimeMillis();
System.out.println("StringBuffer的执行时间：" + (endTime - startTime));

startTime = System.currentTimeMillis();
for (int i = 0; i < 20000; i++) {
    builder.append(String.valueOf(i));
}
endTime = System.currentTimeMillis();
System.out.println("StringBuilder的执行时间：" + (endTime - startTime));

startTime = System.currentTimeMillis();
for (int i = 0; i < 20000; i++) {
    text = text + i;
}
endTime = System.currentTimeMillis();
System.out.println("String的执行时间：" + (endTime - startTime));

```

### 2.7 练习

笔试题：程序输出：

```java
String str = null;
StringBuffer sb = new StringBuffer();
sb.append(str);

System.out.println(sb.length());//

System.out.println(sb);//

StringBuffer sb1 = new StringBuffer(str);
System.out.println(sb1);//

```

## 3. JDK8之前：日期时间API

### 3.1 java.lang.System类的方法

- System类提供的`public static long currentTimeMillis()`：用来返回当前时间与1970年1月1日0时0分0秒之间以毫秒为单位的时间差。

  - 此方法适于计算时间差。

- 计算世界时间的主要标准有：

  - UTC(Coordinated Universal Time)
  - GMT(Greenwich Mean Time)
  - CST(Central Standard Time)

  > 在国际无线电通信场合，为了统一起见，使用一个统一的时间，称为通用协调时(UTC, Universal Time Coordinated)。UTC与格林尼治平均时(GMT, Greenwich Mean Time)一样，都与英国伦敦的本地时相同。这里，UTC与GMT含义完全相同。 

### 3.2 java.util.Date和java.sql.Date

两者的区别：

> 1. 存储精度不同：java.sql.Date仅以年月日的形式存储日期，精确到毫秒级别，而java.util.Date存储日期和时间，并精确到毫秒级别。
> 2. 数据库和Java API的应用不同：java.sql.Date是为了处理数据库中的日期类型而设计的，而java.util.Date则是用于处理Java程序内部的日期。
> 3. 构造器不同：java.sql.Date()可以使用一个long型的毫秒数值来创建对象，其它构造器在日期和时间之间转换，以及对各种格式的日期字符串进行解析；而java.util.Date()则只有一个long型的毫秒值参数。
> 4. 引用不同：java.sql.Date是java.util.Date的一个子类，但不支持所有java.util.Date的操作（如getHours()、getMinutes()和getSeconds()等）。
>
> 总之，如果你需要处理数据库中的日期类型，应该使用java.sql.Date；如果你需要在Java程序中处理日期和时间，应该使用java.util.Date。

表示特定的瞬间，精确到毫秒。

- 构造器：
  - `Date()`：使用无参构造器创建的对象可以获取本地当前时间。
  - `Date(long 毫秒数)`：把该毫秒值换算成日期时间对象
- 常用方法
  - `getTime()`: 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
  - `toString()`: 把此 Date 对象转换为以下形式的 String： dow mon dd hh:mm:ss zzz yyyy 其中： dow 是一周中的某一天 (Sun, Mon, Tue, Wed, Thu, Fri, Sat)，zzz是时间标准。
  - 其它很多方法都过时了。
- 举例：

```java
@Test
public void test1(){
    Date d = new Date();
    System.out.println(d);
}

@Test
public void test2(){
    long time = System.currentTimeMillis();
    System.out.println(time);//1559806982971
    //当前系统时间距离1970-1-1 0:0:0 0毫秒的时间差，毫秒为单位
}

@Test
public void test3(){
    Date d = new Date();
    long time = d.getTime();
    System.out.println(time);//1559807047979
}

@Test
public void test4(){
    long time = 1559807047979L;
    Date d = new Date(time);
    System.out.println(d);
}

@Test
public void test5(){
    long time = Long.MAX_VALUE;
    Date d = new Date(time);
    System.out.println(d);
}
```

### 3.3 java.text.SimpleDateFormat

- ==`java.text.SimpleDateFormat`类是一个不与语言环境有关的方式来格式化和解析日期的具体类。==
- 可以进行格式化：日期 --> 文本
- 可以进行解析：文本 --> 日期
- **构造器：**
  - `SimpleDateFormat()`：默认的模式和语言环境创建对象
  - `public SimpleDateFormat(String pattern)`：该构造方法可以用参数pattern指定的格式创建一个对象
  
    ​	如
  
    ```java
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    ```
- **格式化：**
  
  - `public String format(Date date)`：方法格式化时间对象date
- **解析：**
  
  - `public Date parse(String source)`：从给定字符串的开始解析文本，以生成一个日期。

![1572599023197](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1572599023197.png)

```java
//格式化
@Test
public void test1(){
    Date d = new Date();

    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS毫秒  E Z");
    //把Date日期转成字符串，按照指定的格式转
    String str = sf.format(d);
    System.out.println(str);
}
//解析
@Test
public void test2() throws ParseException{
    String str = "2022年06月06日 16时03分14秒 545毫秒  星期四 +0800";
    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS毫秒  E Z");
    Date d = sf.parse(str);
    System.out.println(d);
}
```

### 3.4 java.util.Calendar(日历)

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220511105252261.png" alt="image-20220511105252261" style="zoom:67%;" />

- Date类的API大部分被废弃了，替换为Calendar。

- `Calendar` 类是一个抽象类，主用用于完成日期字段之间相互操作的功能。

- <font color='red'>获取Calendar实例的方法</font>

  - 使用`Calendar.getInstance()`方法

    ![image-20220123184906903](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220123184906903.png)

  - 调用它的子类`GregorianCalendar`（公历）的构造器。

    ![image-20220405225828816](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220405225828816.png)

- 一个Calendar的实例是系统时间的抽象表示，可以修改或获取 YEAR、MONTH、DAY_OF_WEEK、HOUR_OF_DAY 、MINUTE、SECOND等 `日历字段`对应的时间值。

  - `public int get(int field)`：返回给定日历字段的值
  - `public void set(int field,int value)` ：将给定的日历字段设置为指定的值
  - `public void add(int field,int amount)`：根据日历的规则，为给定的日历字段添加或者减去指定的时间量
  - `public final Date getTime()`：将Calendar转成Date对象
  - `public final void setTime(Date date)`：使用指定的Date对象重置Calendar的时间

- 常用字段

  ![1620277709044](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1620277709044.png)

- 注意：

  - 获取月份时：一月是0，二月是1，以此类推，12月是11
  - 获取星期时：周日是1，周二是2 ， 。。。。周六是7

- 示例代码：


```java
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

public class TestCalendar {
    @Test
    public void test1(){
        Calendar c = Calendar.getInstance();
        System.out.println(c);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        System.out.println(year + "-" + month + "-" + day + " " + hour + ":" + minute);
    }

    @Test
    public void test2(){
        TimeZone t = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar c = Calendar.getInstance(t);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        System.out.println(year + "-" + month + "-" + day + " " + hour + ":" + minute);
    }
    
    @Test
    public void test3(){
        Calendar calendar = Calendar.getInstance();
		// 从一个 Calendar 对象中获取 Date 对象
		Date date = calendar.getTime();
        
		// 使用给定的 Date 设置此 Calendar 的时间
		date = new Date(234234235235L);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 8);
		System.out.println("当前时间日设置为8后,时间是:" + calendar.getTime());
        
		calendar.add(Calendar.HOUR, 2);
		System.out.println("当前时间加2小时后,时间是:" + calendar.getTime());
        
		calendar.add(Calendar.MONTH, -2);
		System.out.println("当前日期减2个月后,时间是:" + calendar.getTime());  
    }
}
```

### 3.5 练习

输入年份和月份，输出该月日历。

闰年计算公式：年份可以被4整除但不能被100整除，或者可以被400整除。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503120722810.png" alt="image-20220503120722810" style="zoom:67%;" />

## 4. JDK8：新的日期时间API

如果我们可以跟别人说：“我们在1502643933071见面，别晚了！”那么就再简单不过了。但是我们希望时间与昼夜和四季有关，于是事情就变复杂了。JDK 1.0中包含了一个java.util.Date类，但是它的大多数方法已经在JDK 1.1引入Calendar类之后被弃用了。而Calendar并不比Date好多少。它们面临的问题是：

- 可变性：像日期和时间这样的类应该是不可变的。

- 偏移性：<font color='red'>Date中的年份是从1900开始的，而月份都从0开始</font>。

- 格式化：格式化只对Date有用，Calendar则不行。

- 此外，它们也不是线程安全的；不能处理闰秒等。

  > 闰秒，是指为保持协调世界时接近于世界时时刻，由国际计量局统一规定在年底或年中（也可能在季末）对协调世界时增加或减少1秒的调整。由于地球自转的不均匀性和长期变慢性（主要由潮汐摩擦引起的），会使世界时（民用时）和原子时之间相差超过到±0.9秒时，就把协调世界时向前拨1秒（负闰秒，最后一分钟为59秒）或向后拨1秒（正闰秒，最后一分钟为61秒）； 闰秒一般加在公历年末或公历六月末。
  >
  > 目前，全球已经进行了27次闰秒，均为正闰秒。

总结：`对日期和时间的操作一直是Java程序员最痛苦的地方之一`。

第三次引入的API是成功的，并且Java 8中引入的java.time API 已经纠正了过去的缺陷，将来很长一段时间内它都会为我们服务。

Java 8 以一个新的开始为 Java 创建优秀的 API。新的日期时间API包含：

* `java.time` – 包含值对象的基础包 
* `java.time.chrono` – 提供对不同的日历系统的访问。
* `java.time.format` – 格式化和解析时间和日期
* `java.time.temporal` – 包括底层框架和扩展特性
* `java.time.zone` – 包含时区支持的类

说明：新的 java.time 中包含了所有关于时钟（Clock），本地日期（LocalDate）、本地时间（LocalTime）、本地日期时间（LocalDateTime）、时区（ZonedDateTime）和持续时间（Duration）的类。

尽管有68个新的公开类型，但是大多数开发者只会用到基础包和format包，大概占总数的三分之一。

### 4.1 本地日期时间：LocalDate、LocalTime、LocalDateTime

| 方法                                                         | **描述**                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| `now() `/ now(ZoneId zone)                                   | 静态方法，根据当前时间创建对象/指定时区的对象                |
| `of(xx,xx,xx,xx,xx,xxx)`                                     | 静态方法，根据指定日期/时间创建对象                          |
| `getDayOfMonth()/getDayOfYear()`                             | 获得月份天数(1-31) /获得年份天数(1-366)                      |
| `getDayOfWeek()`                                             | 获得星期几(返回一个 DayOfWeek 枚举值)                        |
| `getMonth()`                                                 | 获得月份, 返回一个 Month 枚举值                              |
| `getMonthValue() / getYear()`                                | 获得月份(1-12) /获得年份                                     |
| `getHours()/getMinute()/getSecond()`                         | 获得当前对象对应的小时、分钟、秒                             |
| `withDayOfMonth()/withDayOfYear()/withMonth()/withYear()`    | 将月份天数、年份天数、月份、年份修改为指定的值并返回新的对象 |
| `with(TemporalAdjuster  t)`                                  | 将当前日期时间设置为校对器指定的日期时间                     |
| `plusDays(), plusWeeks(), plusMonths(), plusYears(),plusHours()` | 向当前对象添加几天、几周、几个月、几年、几小时               |
| `minusMonths()  minusWeeks()/minusDays()/minusYears()/minusHours()` | 从当前对象减去几月、几周、几天、几年、几小时                 |
| `plus(TemporalAmount t)/minus(TemporalAmount t)`             | 添加或减少一个 Duration 或 Period                            |
| `isBefore()/isAfter()`                                       | 比较两个 LocalDate                                           |
| `isLeapYear()`                                               | 判断是否是闰年（在LocalDate类中声明）                        |
| `format(DateTimeFormatter  t)`                               | 格式化本地日期、时间，返回一个字符串                         |
| `parse(Charsequence text)`                                   | 将指定格式的字符串解析为日期、时间                           |

```java
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestLocalDateTime {
    @Test
    public void test01(){
        LocalDate now = LocalDate.now();
        System.out.println(now);
    }
    @Test
    public void test02(){
        LocalTime now = LocalTime.now();
        System.out.println(now);
    }
    @Test
    public void test03(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
    }
    @Test
    public void test04(){
        LocalDate lai = LocalDate.of(2019, 5, 13);
        System.out.println(lai);
    }
	@Test
    public void test05(){
        LocalDate lai = LocalDate.of(2019, 5, 13);
        System.out.println(lai.getDayOfYear());
    }
	@Test
    public void test06(){
        LocalDate lai = LocalDate.of(2019, 5, 13);
        LocalDate go = lai.plusDays(160);
        System.out.println(go);//2019-10-20
    }
    @Test
    public void test7(){
        LocalDate now = LocalDate.now();
        LocalDate before = now.minusDays(100);
        System.out.println(before);//2019-02-26
    }   
}
```

### 4.2 瞬时：Instant

-  Instant：时间线上的一个瞬时点。 这可能被用来记录应用程序中的事件时间戳。
   -  时间戳是指格林威治时间1970年01月01日00时00分00秒(北京时间1970年01月01日08时00分00秒)起至现在的总秒数。
-  `java.time.Instant`表示时间线上的一点，而不需要任何上下文信息，例如，时区。概念上讲，`它只是简单的表示自1970年1月1日0时0分0秒（UTC）开始的秒数。`

| **方法**                        | **描述**                                                     |
| ------------------------------- | ------------------------------------------------------------ |
| `now()`                         | 静态方法，返回默认UTC时区的Instant类的对象                   |
| `ofEpochMilli(long epochMilli)` | 静态方法，返回在1970-01-01 00:00:00基础上加上指定毫秒数之后的Instant类的对象 |
| `atOffset(ZoneOffset offset)`   | 结合即时的偏移来创建一个 OffsetDateTime                      |
| `toEpochMilli()`                | 返回1970-01-01 00:00:00到当前时间的毫秒数，即为时间戳        |

> 中国大陆、中国香港、中国澳门、中国台湾、蒙古国、新加坡、马来西亚、菲律宾、西澳大利亚州的时间与UTC的时差均为+8，也就是UTC+8。
>
> instant.atOffset(ZoneOffset.ofHours(8));

![image-20220406000442908](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220406000442908.png)

> 整个地球分为二十四时区，每个时区都有自己的本地时间。北京时区是东八区，领先UTC八个小时，在电子邮件信头的Date域记为+0800。如果在电子邮件的信头中有这么一行： 
>
> Date: Fri, 08 Nov 2002 09:42:22 +0800 
>
> 说明信件的发送地的地方时间是二○○二年十一月八号，星期五，早上九点四十二分（二十二秒），这个地方的本地时领先UTC八个小时(+0800， 就是东八区时间)。电子邮件信头的Date域使用二十四小时的时钟，而不使用AM和PM来标记上下午。 

### 4.3 日期时间格式化：DateTimeFormatter

该类提供了三种格式化方法：

- (了解)预定义的标准格式。如：ISO_LOCAL_DATE_TIME、ISO_LOCAL_DATE、ISO_LOCAL_TIME


- (了解)本地化相关的格式。如：ofLocalizedDate(FormatStyle.LONG)

  ```java
  // 本地化相关的格式。如：ofLocalizedDateTime()
  // FormatStyle.MEDIUM / FormatStyle.SHORT :适用于LocalDateTime
  				
  // 本地化相关的格式。如：ofLocalizedDate()
  // FormatStyle.FULL / FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT : 适用于LocalDate
  ```

- 自定义的格式。如：ofPattern(“yyyy-MM-dd hh:mm:ss”)

| **方**   **法**                    | **描**   **述**                                     |
| ---------------------------------- | --------------------------------------------------- |
| **ofPattern(String**  **pattern)** | 静态方法，返回一个指定字符串格式的DateTimeFormatter |
| **format(TemporalAccessor** **t)** | 格式化一个日期、时间，返回字符串                    |
| **parse(CharSequence**  **text)**  | 将指定格式的字符序列解析为一个日期、时间            |

举例：

```java
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TestDatetimeFormatter {
    @Test
    public void test1(){
        // 方式一：预定义的标准格式。如：ISO_LOCAL_DATE_TIME;ISO_LOCAL_DATE;ISO_LOCAL_TIME
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // 格式化:日期-->字符串
        LocalDateTime localDateTime = LocalDateTime.now();
        String str1 = formatter.format(localDateTime);
        System.out.println(localDateTime);
        System.out.println(str1);//2022-12-04T21:02:14.808

        // 解析：字符串 -->日期
        TemporalAccessor parse = formatter.parse("2022-12-04T21:02:14.808");
        LocalDateTime dateTime = LocalDateTime.from(parse);
        System.out.println(dateTime);
    }

    @Test
    public void test2(){
        LocalDateTime localDateTime = LocalDateTime.now();
        // 方式二：
        // 本地化相关的格式。如：ofLocalizedDateTime()
        // FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT :适用于LocalDateTime
        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        
        // 格式化
        String str2 = formatter1.format(localDateTime);
        System.out.println(str2);// 2022年12月4日 下午09时03分55秒

        // 本地化相关的格式。如：ofLocalizedDate()
        // FormatStyle.FULL / FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT : 适用于LocalDate
        DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        // 格式化
        String str3 = formatter2.format(LocalDate.now());
        System.out.println(str3);// 2022年12月4日 星期日
    }

    @Test
    public void test3(){
        //方式三：自定义的方式（关注、重点）
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //格式化
        String strDateTime = dateTimeFormatter.format(LocalDateTime.now());
        System.out.println(strDateTime); //2022/12/04 21:05:42
        //解析
        TemporalAccessor accessor = dateTimeFormatter.parse("2022/12/04 21:05:42");
        LocalDateTime localDateTime = LocalDateTime.from(accessor);
        System.out.println(localDateTime); //2022-12-04T21:05:42
    }
}
```

### 4.4 其它API

**1、指定时区日期时间：ZondId和ZonedDateTime**

- ZoneId：该类中包含了所有的时区信息，一个时区的ID，如 Europe/Paris

- ZonedDateTime：一个在ISO-8601日历系统时区的日期时间，如 2007-12-03T10:15:30+01:00 Europe/Paris。
  - 其中每个时区都对应着ID，地区ID都为“{区域}/{城市}”的格式，例如：Asia/Shanghai等

- 常见时区ID：


```java
Asia/Shanghai
UTC
America/New_York
```

- 可以通过ZondId获取所有可用的时区ID：


```java
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class TestZone {
    @Test
    public void test01() {
        //需要知道一些时区的id
        //Set<String>是一个集合，容器
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        //快捷模板iter
        for (String availableZoneId : availableZoneIds) {
            System.out.println(availableZoneId);
        }
    }

    @Test
    public void test02(){
        ZonedDateTime t1 = ZonedDateTime.now();
        System.out.println(t1);

        ZonedDateTime t2 = ZonedDateTime.now(ZoneId.of("America/New_York"));
        System.out.println(t2);
    }
}

```

**2、持续日期/时间：Period和Duration**

- 持续时间：Duration，用于计算两个“时间”间隔
- 日期间隔：Period，用于计算两个“日期”间隔

```java
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class TestPeriodDuration {
    @Test
    public void test01(){
        LocalDate t1 = LocalDate.now();
        LocalDate t2 = LocalDate.of(2018, 12, 31);
        Period between = Period.between(t1, t2);
        System.out.println(between);

        System.out.println("相差的年数："+between.getYears());
        System.out.println("相差的月数："+between.getMonths());
        System.out.println("相差的天数："+between.getDays());
        System.out.println("相差的总数："+between.toTotalMonths());
    }

    @Test
    public void test02(){
        LocalDateTime t1 = LocalDateTime.now();
        LocalDateTime t2 = LocalDateTime.of(2017, 8, 29, 0, 0, 0, 0);
        Duration between = Duration.between(t1, t2);
        System.out.println(between);

        System.out.println("相差的总天数："+between.toDays());
        System.out.println("相差的总小时数："+between.toHours());
        System.out.println("相差的总分钟数："+between.toMinutes());
        System.out.println("相差的总秒数："+between.getSeconds());
        System.out.println("相差的总毫秒数："+between.toMillis());
        System.out.println("相差的总纳秒数："+between.toNanos());
        System.out.println("不够一秒的纳秒数："+between.getNano());
    }
    @Test
    public void test03(){
        //Duration:用于计算两个“时间”间隔，以秒和纳秒为基准
		LocalTime localTime = LocalTime.now();
		LocalTime localTime1 = LocalTime.of(15, 23, 32);
		//between():静态方法，返回Duration对象，表示两个时间的间隔
		Duration duration = Duration.between(localTime1, localTime);
		System.out.println(duration);

		System.out.println(duration.getSeconds());
		System.out.println(duration.getNano());

		LocalDateTime localDateTime = LocalDateTime.of(2016, 6, 12, 15, 23, 32);
		LocalDateTime localDateTime1 = LocalDateTime.of(2017, 6, 12, 15, 23, 32);

		Duration duration1 = Duration.between(localDateTime1, localDateTime);
		System.out.println(duration1.toDays());
    }
    
    @Test
    public void test4(){
        //Period:用于计算两个“日期”间隔，以年、月、日衡量
		LocalDate localDate = LocalDate.now();
		LocalDate localDate1 = LocalDate.of(2028, 3, 18);

		Period period = Period.between(localDate, localDate1);
		System.out.println(period);

		System.out.println(period.getYears());
		System.out.println(period.getMonths());
		System.out.println(period.getDays());

		Period period1 = period.withYears(2);
		System.out.println(period1);

    }
}

```

3、Clock：使用时区提供对当前即时、日期和时间的访问的时钟。

4、

TemporalAdjuster : 时间校正器。有时我们可能需要获取例如：将日期调整到“下一个工作日”等操作。
TemporalAdjusters : 该类通过静态方法(firstDayOfXxx()/lastDayOfXxx()/nextXxx())提供了大量的常用 TemporalAdjuster 的实现。

```java
@Test
public void test1(){
    // TemporalAdjuster:时间校正器
	// 获取当前日期的下一个周日是哪天？
	TemporalAdjuster temporalAdjuster = TemporalAdjusters.next(DayOfWeek.SUNDAY);
	LocalDateTime localDateTime = LocalDateTime.now().with(temporalAdjuster);
	System.out.println(localDateTime);
	// 获取下一个工作日是哪天？
	LocalDate localDate = LocalDate.now().with(new TemporalAdjuster() {
   	 	@Override
   	 	public Temporal adjustInto(Temporal temporal) {
        	LocalDate date = (LocalDate) temporal;
     	  	if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
           		return date.plusDays(3);
        	} else if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            	return date.plusDays(2);
        	} else {
            	return date.plusDays(1);
        	}
    	}
	});
	System.out.println("下一个工作日是：" + localDate);

}
```

### 4.5 与传统日期处理的转换

| **类**                                                       | **To** **遗留类**                     | **From** **遗留类**         |
| ------------------------------------------------------------ | ------------------------------------- | --------------------------- |
| **java.time.Instant与java.util.Date**                        | Date.from(instant)                    | date.toInstant()            |
| **java.time.Instant与java.sql.Timestamp**                    | Timestamp.from(instant)               | timestamp.toInstant()       |
| **java.time.ZonedDateTime与java.util.GregorianCalendar**     | GregorianCalendar.from(zonedDateTime) | cal.toZonedDateTime()       |
| **java.time.LocalDate与java.sql.Time**                       | Date.valueOf(localDate)               | date.toLocalDate()          |
| **java.time.LocalTime与java.sql.Time**                       | Date.valueOf(localDate)               | date.toLocalTime()          |
| **java.time.LocalDateTime与java.sql.Timestamp**              | Timestamp.valueOf(localDateTime)      | timestamp.toLocalDateTime() |
| **java.time.ZoneId与java.util.TimeZone**                     | Timezone.getTimeZone(id)              | timeZone.toZoneId()         |
| **java.time.format.DateTimeFormatter与java.text.DateFormat** | formatter.toFormat()                  | 无                          |

## 5. Java比较器

我们知道基本数据类型的数据（除boolean类型外）需要比较大小的话，之间使用比较运算符即可，但是引用数据类型是不能直接使用比较运算符来比较大小的。那么，如何解决这个问题呢？

![image-20220406001726285](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220406001726285.png)

- 在Java中经常会涉及到对象数组的排序问题，那么就涉及到对象之间的比较问题。


- Java实现对象排序的方式有两种：
  - 自然排序：`java.lang.Comparable`
  - 定制排序：`java.util.Comparator`

### 5.1 自然排序：java.lang.Comparable

- <font color='red'>Comparable接口强行对实现它的每个类的对象进行整体排序。这种排序被称为类的自然排序。</font>

- 实现 Comparable 的类必须实现 `compareTo(Object obj) `方法，两个对象即通过 compareTo(Object obj) 方法的返回值来比较大小。

  * 如果当前对象this大于形参对象obj，则返回正整数

  * 如果当前对象this小于形参对象obj，则返回负整数

  * 如果当前对象this等于形参对象obj，则返回零

```java
package java.lang;

public interface Comparable{
    int compareTo(Object obj);
}
```

- <font color='red'>实现Comparable接口的对象列表（和数组）可以通过 Collections.sort 或 Arrays.sort进行自动排序。</font>实现此接口的对象可以用作有序映射中的键或有序集合中的元素，无需指定比较器。
- 对于类 C 的每一个 e1 和 e2 来说，当且仅当 e1.compareTo(e2) == 0 与 e1.equals(e2) 具有相同的 boolean 值时，类 C 的自然排序才叫做与 equals 一致。建议（虽然不是必需的）`最好使自然排序与 equals 一致`。

- Comparable 的典型实现：(`默认都是从小到大排列的`)
  - String：按照字符串中字符的Unicode值进行比较
  - Character：按照字符的Unicode值来进行比较
  - 数值类型对应的包装类以及BigInteger、BigDecimal：按照它们对应的数值大小进行比较
  - Boolean：true 对应的包装类实例大于 false 对应的包装类实例
  - Date、Time等：后面的日期时间比前面的日期时间大

- 代码示例：


```java
package com.atguigu.api;

public class Student implements Comparable {
    private int id;
    private String name;
    private int score;
    private int age;

    public Student(int id, String name, int score, int age) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.age = age;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        //这些需要强制，将o对象向下转型为Student类型的变量，才能调用Student类中的属性
        //默认按照学号比较大小
        Student stu = (Student) o;
        return this.id - stu.id;
    }
}
```

测试类

```java
package com.atguigu.api;

public class TestStudent {
    public static void main(String[] args) {
        Student[] arr = new Student[5];
        arr[0] = new Student(3,"张三",90,23);
        arr[1] = new Student(1,"熊大",100,22);
        arr[2] = new Student(5,"王五",75,25);
        arr[3] = new Student(4,"李四",85,24);
        arr[4] = new Student(2,"熊二",85,18);

        //单独比较两个对象
        System.out.println(arr[0].compareTo(arr[1]));
        System.out.println(arr[1].compareTo(arr[2]));
        System.out.println(arr[2].compareTo(arr[2]));

        System.out.println("所有学生：");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println("按照学号排序：");
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length-i; j++) {
                if(arr[j].compareTo(arr[j+1])>0){
                    Student temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}

```

再举例：

```java
public class Student implements Comparable {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.score - ((Student)o).score;
    }
}
```

测试：

```java
@Test
public void test02() {
	Student[] students = new Student[3];
	students[0] = new Student("张三", 96);
	students[1] = new Student("李四", 85);
	students[2] = new Student("王五", 98);

	System.out.println(Arrays.toString(students));
	Arrays.sort(students);
	System.out.println(Arrays.toString(students));
}
```

再举例：

```java
class Goods implements Comparable {
    private String name;
    private double price;

    //按照价格，比较商品的大小
    @Override
    public int compareTo(Object o) {
        if(o instanceof Goods) {
            Goods other = (Goods) o;
            if (this.price > other.price) {
                return 1;
            } else if (this.price < other.price) {
                return -1;
            }
            return 0;
        }
        throw new RuntimeException("输入的数据类型不一致");
    }
    //构造器、getter、setter、toString()方法略
}


```

测试：

```java
public class ComparableTest{
    public static void main(String[] args) {

        Goods[] all = new Goods[4];
        all[0] = new Goods("《红楼梦》", 100);
        all[1] = new Goods("《西游记》", 80);
        all[2] = new Goods("《三国演义》", 140);
        all[3] = new Goods("《水浒传》", 120);

        Arrays.sort(all);

        System.out.println(Arrays.toString(all));

    }

}

```

### 5.2 定制排序：java.util.Comparator

- 思考
  - 当元素的类型没有实现java.lang.Comparable接口而又不方便修改代码（例如：一些第三方的类，你只有.class文件，没有源文件）
  - 如果一个类，实现了Comparable接口，也指定了两个对象的比较大小的规则，但是此时此刻我不想按照它预定义的方法比较大小，但是我又不能随意修改，因为会影响其他地方的使用，怎么办？
- JDK在设计类库之初，也考虑到这种情况，所以又增加了一个java.util.Comparator接口。强行对多个对象进行整体排序的比较。
  - ==重写compare(Object o1,Object o2)方法，比较o1和o2的大小：如果方法返回正整数，则表示o1大于o2；如果返回0，表示相等；返回负整数，表示o1小于o2。==
  - ==可以将 Comparator 传递给 sort 方法（如 Collections.sort 或 Arrays.sort），从而允许在排序顺序上实现精确控制。==

```java
package java.util;

public interface Comparator{
    int compare(Object o1,Object o2);
}
```

举例：

```java
package com.atguigu.api;

import java.util.Comparator;
//定义定制比较器类
public class StudentScoreComparator implements Comparator { 
    @Override
    public int compare(Object o1, Object o2) {
        Student s1 = (Student) o1;
        Student s2 = (Student) o2;
        int result = s1.getScore() - s2.getScore();
        return result != 0 ? result : s1.getId() - s2.getId();
    }
}
```

测试类

```java
package com.atguigu.api;

public class TestStudent {
    public static void main(String[] args) {
        Student[] arr = new Student[5];
        arr[0] = new Student(3, "张三", 90, 23);
        arr[1] = new Student(1, "熊大", 100, 22);
        arr[2] = new Student(5, "王五", 75, 25);
        arr[3] = new Student(4, "李四", 85, 24);
        arr[4] = new Student(2, "熊二", 85, 18);


        System.out.println("所有学生：");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        System.out.println("按照成绩排序");
        StudentScoreComparator sc = new StudentScoreComparator();
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (sc.compare(arr[j], arr[j + 1]) > 0) {
                    Student temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
```

再举例：

```java
@Test
public void test01() {
    Student[] students = new Student[5];
    students[0] = new Student(3, "张三", 90, 23);
    students[1] = new Student(1, "熊大", 100, 22);
    students[2] = new Student(5, "王五", 75, 25);
    students[3] = new Student(4, "李四", 85, 24);
    students[4] = new Student(2, "熊二", 85, 18);

    System.out.println(Arrays.toString(students));
    //定制排序
    StudentScoreComparator sc = new StudentScoreComparator();
    Arrays.sort(students, sc);
    System.out.println("排序之后：");
    System.out.println(Arrays.toString(students));
}
```

再举例：

```java
Goods[] all = new Goods[4];
all[0] = new Goods("War and Peace", 100);
all[1] = new Goods("Childhood", 80);
all[2] = new Goods("Scarlet and Black", 140);
all[3] = new Goods("Notre Dame de Paris", 120);

Arrays.sort(all, new Comparator() {

    @Override
    public int compare(Object o1, Object o2) {
        Goods g1 = (Goods) o1;
        Goods g2 = (Goods) o2;

        return g1.getName().compareTo(g2.getName());
    }
});

System.out.println(Arrays.toString(all));

```

### 5.3Comparable和Comparator的区别

> Comparable和Comparator都是用于对对象进行比较和排序的接口。两者的区别在于，Comparable是对象自身的比较器，它只能用于对一个类进行排序；而Comparator是一个外部比较器，它可以用于对任意类进行排序，并且可以定义多个比较器，具有更高的灵活性。

## 6. 系统相关类

### 6.1 java.lang.System类

- System类代表系统，系统级的很多属性和控制方法都放置在该类的内部。该类位于`java.lang包`。

- 由于该类的构造器是private的，所以无法创建该类的对象。其内部的成员变量和成员方法都是`static的`，所以也可以很方便的进行调用。

- 成员变量   Scanner scan = new Scanner(System.in);

  - System类内部包含`in`、`out`和`err`三个成员变量，分别代表标准输入流(键盘输入)，标准输出流(显示器)和标准错误输出流(显示器)。

- 成员方法

  - `native long currentTimeMillis()`：
    该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数。

  - `void exit(int status)`：
    该方法的作用是退出程序。其中status的值为0代表正常退出，非零代表异常退出。使用该方法可以在图形界面编程中实现程序的退出功能等。

  - `void gc()`：
    该方法的作用是请求系统进行垃圾回收。至于系统是否立刻回收，则取决于系统中垃圾回收算法的实现以及系统执行时的情况。

  - `String getProperty(String key)`：
    该方法的作用是获得系统中属性名为key的属性对应的值。系统中常见的属性名以及属性的作用如下表所示：

    ![image-20220406003340258](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220406003340258.png)

- 举例

```java
import org.junit.Test;

public class TestSystem {
    @Test
    public void test01(){
        long time = System.currentTimeMillis();
        System.out.println("现在的系统时间距离1970年1月1日凌晨：" + time + "毫秒");

        System.exit(0);

        System.out.println("over");//不会执行
    }

    @Test
    public void test02(){
        String javaVersion = System.getProperty("java.version");
		System.out.println("java的version:" + javaVersion);

		String javaHome = System.getProperty("java.home");
		System.out.println("java的home:" + javaHome);

		String osName = System.getProperty("os.name");
		System.out.println("os的name:" + osName);

		String osVersion = System.getProperty("os.version");
		System.out.println("os的version:" + osVersion);

		String userName = System.getProperty("user.name");
		System.out.println("user的name:" + userName);

		String userHome = System.getProperty("user.home");
		System.out.println("user的home:" + userHome);

		String userDir = System.getProperty("user.dir");
		System.out.println("user的dir:" + userDir);
    }

    @Test
    public void test03() throws InterruptedException {
        for (int i=1; i <=10; i++){
            MyDemo my = new MyDemo(i);
            //每一次循环my就会指向新的对象，那么上次的对象就没有变量引用它了，就成垃圾对象
        }

        //为了看到垃圾回收器工作，我要加下面的代码，让main方法不那么快结束，因为main结束就会导致JVM退出，GC也会跟着结束。
        System.gc();//如果不调用这句代码，GC可能不工作，因为当前内存很充足，GC就觉得不着急回收垃圾对象。
        			//调用这句代码，会让GC尽快来工作。
        Thread.sleep(5000);
    }
}

class MyDemo{
    private int value;

    public MyDemo(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyDemo{" + "value=" + value + '}';
    }

    //重写finalize方法，让大家看一下它的调用效果
    @Override
    protected void finalize() throws Throwable {
//        正常重写，这里是编写清理系统内存的代码
//        这里写输出语句是为了看到finalize()方法被调用的效果
        System.out.println(this+ "轻轻的我走了，不带走一段代码....");
    }
}
```

* `static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)`： 

  从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。常用于数组的插入和删除

```java
import org.junit.Test;

import java.util.Arrays;

public class TestSystemArrayCopy {
    @Test
    public void test01(){
        int[] arr1 = {1,2,3,4,5};
        int[] arr2 = new int[10];
        System.arraycopy(arr1,0,arr2,3,arr1.length);
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
    }

    @Test
    public void test02(){
        int[] arr = {1,2,3,4,5};
        System.arraycopy(arr,0,arr,1,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test03(){
        int[] arr = {1,2,3,4,5};
        System.arraycopy(arr,1,arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
```

### 6.2 java.lang.Runtime类

每个 Java 应用程序都有一个 `Runtime` 类实例，使应用程序能够与其运行的环境相连接。

`public static Runtime getRuntime()`： 返回与当前 Java 应用程序相关的运行时对象。应用程序不能创建自己的 Runtime 类实例。

`public long totalMemory()`：返回 Java 虚拟机中初始化时的内存总量。此方法返回的值可能随时间的推移而变化，这取决于主机环境。默认为物理电脑内存的1/64。

`public long maxMemory()`：返回 Java 虚拟机中最大程度能使用的内存总量。默认为物理电脑内存的1/4。

`public long freeMemory()`：回 Java 虚拟机中的空闲内存量。调用 gc 方法可能导致 freeMemory 返回值的增加。

```java
package com.atguigu.system;

public class TestRuntime {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory(); //获取虚拟机初始化时堆内存总量
        long maxMemory = runtime.maxMemory(); //获取虚拟机最大堆内存总量
        String str = "";
        //模拟占用内存
        for (int i = 0; i < 10000; i++) {
            str += i;
        }
        long freeMemory = runtime.freeMemory(); //获取空闲堆内存总量
        System.out.println("总内存：" + initialMemory / 1024 / 1024 * 64 + "MB");
        System.out.println("总内存：" + maxMemory / 1024 / 1024 * 4 + "MB");
        System.out.println("空闲内存：" + freeMemory / 1024 / 1024 + "MB") ;
        System.out.println("已用内存：" + (initialMemory-freeMemory) / 1024 / 1024 + "MB");
    }
}
```

## 7. 和数学相关的类

### 7.1 java.lang.Math

`java.lang.Math` 类包含用于执行基本数学运算的方法，如初等指数、对数、平方根和三角函数。类似这样的工具类，其所有方法均为静态方法，并且不会创建对象，调用起来非常简单。

* `public static double abs(double a) ` ：返回 double 值的绝对值。 

```java
double d1 = Math.abs(-5); //d1的值为5
double d2 = Math.abs(5); //d2的值为5
```

* `public static double ceil(double a)` ：返回大于等于参数的最小的整数。

```java
double d1 = Math.ceil(3.3); //d1的值为 4.0
double d2 = Math.ceil(-3.3); //d2的值为 -3.0
double d3 = Math.ceil(5.1); //d3的值为 6.0
```

* `public static double floor(double a) ` ：返回小于等于参数最大的整数。

```java
double d1 = Math.floor(3.3); //d1的值为3.0
double d2 = Math.floor(-3.3); //d2的值为-4.0
double d3 = Math.floor(5.1); //d3的值为 5.0
```

* `public static long round(double a)` ：返回最接近参数的 long。(相当于四舍五入方法)  

```java
long d1 = Math.round(5.5); //d1的值为6
long d2 = Math.round(5.4); //d2的值为5
long d3 = Math.round(-3.3); //d3的值为-3
long d4 = Math.round(-3.8); //d4的值为-4
```

* public static double pow(double a,double b)：返回a的b幂次方法
* public static double sqrt(double a)：返回a的平方根
* `public static double random()`：返回[0,1)的随机值
  * public static final double PI：返回圆周率																																																																																												

* public static double max(double x, double y)：返回x,y中的最大值
* public static double min(double x, double y)：返回x,y中的最小值
* 其它：acos,asin,atan,cos,sin,tan 三角函数

```java
double result = Math.pow(2,31);
double sqrt = Math.sqrt(256);
double rand = Math.random();
double pi = Math.PI;
```

### 7.2 java.math包

#### 7.2.1 BigInteger

<font color='red'>Integer类作为int的包装类，能存储的最大整型值为2^ 31-1，而long类型的范围是2^ 63-1。如果要表示再大的整数，不管是基本数据类型还是他们的包装类都无能为力，更不用说进行运算了。</font>

==java.math包的BigInteger可以表示`不可变的任意长度的整数`。BigInteger 提供所有 Java 的基本整数操作符的对应物，并提供 java.lang.Math 的所有相关方法。另外，BigInteger 还提供以下运算：模算术、GCD 计算、质数测试、素数生成、位操作以及一些其他操作==


- 构造器
  - BigInteger(String val)：根据字符串构建BigInteger对象

- 方法
  - public BigInteger `abs`()：返回此 BigInteger 的绝对值的 BigInteger。
  - BigInteger `add`(BigInteger val) ：返回其值为 (this + val) 的 BigInteger
  - BigInteger `subtract`(BigInteger val) ：返回其值为 (this - val) 的 BigInteger
  - BigInteger `multiply`(BigInteger val) ：返回其值为 (this * val) 的 BigInteger
  - BigInteger `divide`(BigInteger val) ：返回其值为 (this / val) 的 BigInteger。整数相除只保留整数部分。
  - BigInteger `remainder`(BigInteger val) ：返回其值为 (this % val) 的 BigInteger。
  - BigInteger[] `divideAndRemainder`(BigInteger val)：返回包含 (this / val) 后跟 (this % val) 的两个 BigInteger 的数组。
  - BigInteger `pow`(int exponent) ：返回其值为 (this^exponent) 的 BigInteger。 

```java
@Test
public void test01(){
    //long bigNum = 123456789123456789123456789L;

    BigInteger b1 = new BigInteger("12345678912345678912345678");
    BigInteger b2 = new BigInteger("78923456789123456789123456789");

    //System.out.println("和：" + (b1+b2));//错误的，无法直接使用+进行求和

    System.out.println("和：" + b1.add(b2));
    System.out.println("减：" + b1.subtract(b2));
    System.out.println("乘：" + b1.multiply(b2));
    System.out.println("除：" + b2.divide(b1));
    System.out.println("余：" + b2.remainder(b1));
}
```

#### 7.2.2 BigDecimal

- 一般的Float类和Double类可以用来做科学计算或工程计算，但在**商业计算中，要求数字精度比较高，故用到java.math.BigDecimal类。**

- ==BigDecimal类支持`不可变的、任意精度`的有符号十进制定点数。==

- 构造器
  - public BigDecimal(double val)
  - public BigDecimal(String val) --> 推荐

- 常用方法
  - public BigDecimal `add`(BigDecimal augend)
  - public BigDecimal `subtract`(BigDecimal subtrahend)
  - public BigDecimal `multiply`(BigDecimal multiplicand)
  - public BigDecimal `divide`(BigDecimal divisor, int scale, int roundingMode)：divisor是除数，scale指明保留几位小数，roundingMode指明舍入模式（ROUND_UP :向上加1、ROUND_DOWN :直接舍去、ROUND_HALF_UP:四舍五入）


- 举例

```java
@Test
public void test03(){
    BigInteger bi = new BigInteger("12433241123");
    BigDecimal bd = new BigDecimal("12435.351");
    BigDecimal bd2 = new BigDecimal("11");
    System.out.println(bi);
    // System.out.println(bd.divide(bd2));
    System.out.println(bd.divide(bd2, BigDecimal.ROUND_HALF_UP));
    System.out.println(bd.divide(bd2, 15, BigDecimal.ROUND_HALF_UP));
}

```

### 7.3 java.util.Random

用于产生随机数

* `boolean nextBoolean()`:返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 boolean 值。 

* `void nextBytes(byte[] bytes)`:生成随机字节并将其置于用户提供的 byte 数组中。 

* `double nextDouble()`:返回下一个伪随机数，它是取自此随机数生成器序列的、在 0.0 和 1.0 之间均匀分布的 double 值。 

* `float nextFloat()`:返回下一个伪随机数，它是取自此随机数生成器序列的、在 0.0 和 1.0 之间均匀分布的 float 值。 

* `double nextGaussian()`:返回下一个伪随机数，它是取自此随机数生成器序列的、呈高斯（“正态”）分布的 double 值，其平均值是 0.0，标准差是 1.0。 

* `int nextInt()`:返回下一个伪随机数，它是此随机数生成器的序列中均匀分布的 int 值。 

* `int nextInt(int n)`:返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）和指定值（不包括）之间均匀分布的 int 值。 

* `long nextLong()`:返回下一个伪随机数，它是取自此随机数生成器序列的均匀分布的 long 值。 

```java
@Test
public void test04(){
    Random r = new Random();
    System.out.println("随机整数：" + r.nextInt());
    System.out.println("随机小数：" + r.nextDouble());
    System.out.println("随机布尔值：" + r.nextBoolean());
}
```



## 8.随堂复习

### 1. String类

- String的声明：final修饰、实现了Comparable接口
- String的不可变性
- String的两种定义方式：① 字面量的定义方式 String s = "hello" ② new 的方式：String s = new String("hello");
  - String的内存解析：字符串常量池、堆内存的使用
  - String s = new String("hello");在内存中创建的对象的个数。
- String的连接操作：+
  - 常量 + 常量 、变量 + 常量 、变量+变量、concat(String otherString)
  - String intern()
- 熟悉String的构造器、与其他结构之间的转换、常用方法
  - 编码和解码
    - 编码：字符、字符串 --> 字节、字节数组。对应着编码集
    - 解码：字节、字节数组 --> 字符、字符串。对应着解码集
    - 规则：解码集必须使用当初编码时使用的编码集。只要不一致，就可能出现乱码！
- String相关的算法问题。

### 2. StringBuffer、StringBuilder类

- [面试题]String、StringBuffer、StringBuilder的区别
- 知道什么场景下使用StringBuffer、StringBuilder

### 3. jdk8之前的日期、时间API

- System的currentTimeMillis()
- 两个Date的使用
- SimpleDateFormat用于格式化、解析
- Calendar日历类的使用

### 4. jdk8中新的日期、时间API

- LocalDate、LocalTime、LocalDateTime -->类似于Calendar
- Instant  -->类似于Date
- DateTimeFormatter --->类似于SimpleDateFormat

### 5. 比较器(重点)

- 自然排序涉及到Comparable
  - compareTo(Object obj)
- 定制排序涉及到Comparator
  - compare(Object obj1,Object obj2)

### 6. 其它API

了解



## 9.企业真题

### 2.1 String

#### 1. 以下两种方式创建的String对象有什么不同？（*团）

```java
String str = new String("test");

String str = "test";
```

略

#### 2. String s = new String("xyz");创建了几个String Object? （新*陆）

两个

#### 3. String a="abc" String b="a"+"bc" 问a==b?（网*邮箱）

是！

#### 4. String 中 “+” 怎样实现?（阿*）

常量 + 常量 ：略

变量 + 常量 、变量+变量：创建一个StringBuilder的实例，通过append()添加字符串，最后调用toString()返回一个字符串。（toString()内部new 一个String的实例）



#### 5. Java中String是不是final的？（凡*科技）

```
类似问题：
> String被哪些类继承？（网*邮箱）
> 是否可以继承String类?（湖南*利软件）
> String 是否可以继承?（阿*）
```

是

#### 6. String为啥不可变，在内存中的具体形态？（阿*）

规定不可变。

String：提供字符串常量池。

#### 7. String 可以在 switch中使用吗?(上海*睿)

可以。从jdk7开始可以使用

#### 8. String中有哪些方法?列举几个（闪*购）

。。。

#### 9. subString()到底做了什么?(银*数据)

String str = "hello";

String subStr = str.subString(1,3);  //底层是new的方式返回一个subStr，实体内容是"el"

### 2.2 String、StringBuffer、StringBuilder

#### 1. Java中操作字符串有哪些类？他们之间有什么区别。（南*电网）

```
类似问题：
> String 和 StringBuffer区别？(亿*国际、天*隆、*团)
> StringBuilder和StrignBuffer的区别？（平*金服）
> StringBuilder和StringBuffer的区别以及实现？（*为）
```

```
> String:不可变的字符序列;底层使用char[] （jdk8及之前），底层使用byte[] （jdk9及之后）
> StringBuffer:可变的字符序列;JDK1.0声明，线程安全的，效率低;底层使用char[] （jdk8及之前），底层使用byte[] （jdk9及之后）
> StringBuilder:可变的字符序列;JDK5.0声明，线程不安全的,效率高;底层使用char[] （jdk8及之前），底层使用byte[] （jdk9及之后）
```

#### 2. String的线程安全问题（闪*购）

线程不安全的

#### 3. StringBuilder和StringBuffer的线程安全问题（润*软件）

略



###  2.3 Comparator与Comparable

#### 1. 简单说说 Comparable 和 Comparator 的区别和场景？（软**力）

略

#### 2. Comparable 接口和 Comparator 接口实现比较（阿*）

略



# 第11章_集合框架

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC12%E7%AB%A0.png" alt="第3阶段：Java高级应用-第12章" style="zoom:50%;" />

***

## 1. 集合框架概述

### 1.1 生活中的容器

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220523190743146.png" alt="image-20220523190743146" style="zoom: 67%;" />

### 1.2 数组的特点与弊端

- 一方面，面向对象语言对事物的体现都是以对象的形式，为了方便对多个对象的操作，就要对对象进行存储。
- 另一方面，使用数组存储对象方面具有`一些弊端`，而Java 集合就像一种容器，可以`动态地`把多个对象的引用放入容器中。
- 数组在内存存储方面的`特点`：
  - ==数组初始化以后，长度就确定了。==
  - ==数组中的添加的元素是依次紧密排列的，有序的，可以重复的。==
  - ==数组声明的类型，就决定了进行元素初始化时的类型。不是此类型的变量，就不能添加。==
  - ==可以存储基本数据类型值，也可以存储引用数据类型的变量==
- 数组在存储数据方面的`弊端`：
  - <font color='red'>数组初始化以后，长度就不可变了，不便于扩展</font>
  - <font color='red'>数组中提供的属性和方法少，不便于进行添加、删除、插入、获取元素个数等操作，且效率不高。</font>
  - <font color='red'>数组存储数据的特点单一，只能存储有序的、可以重复的数据</font>
  - <font color='red'>数组中元素的删除、插入性能较差</font>
- Java 集合框架中的类可以用于存储多个`对象`，还可用于保存具有`映射关系`的关联数组。

### 1.3 Java集合框架体系

Java 集合可分为 Collection 和 Map 两大体系：

- **<font color='red'>Collection接口</font>**：用于存储一个一个的数据，也称`单列数据集合`。

  - **List子接口**：==用来存储有序的、可以重复的数据（主要用来替换数组，"动态"数组）==

    - 实现类：ArrayList(主要实现类)、LinkedList、Vector

  - **Set子接口**：==用来存储无序的、不可重复的数据（类似于高中讲的"集合"）==

    - 实现类：HashSet(主要实现类)、LinkedHashSet、TreeSet

    

- **<font color='red'>Map接口</font>**：用于存储具有映射关系“key-value对”的集合，即一对一对的数据，也称`双列数据集合`。(类似于高中的函数、映射。(x1,y1),(x2,y2) ---> y = f(x) )

  - HashMap(主要实现类)、LinkedHashMap、TreeMap、Hashtable、Properties

- JDK提供的集合API位于java.util包内

- 图示：集合框架全图

![集合框架全图.png](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E9%9B%86%E5%90%88%E6%A1%86%E6%9E%B6%E5%85%A8%E5%9B%BE.png)

- 简图1：**Collection接口继承树**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220407203244029.png" alt="image-20220407203244029" style="zoom: 67%;" />

- 简图2：**Map接口继承树**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220407203412665.png" alt="image-20220407203412665" style="zoom: 67%;" />

### 1.4 集合的使用场景

![image-20220407202630027](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220407202630027.png)

![第12章_集合的使用场景](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC12%E7%AB%A0_%E9%9B%86%E5%90%88%E7%9A%84%E4%BD%BF%E7%94%A8%E5%9C%BA%E6%99%AF.png)

## 2. Collection接口及方法

- JDK不提供此接口的任何直接实现，而是提供更具体的子接口（如：Set和List）去实现。
- Collection 接口是 List和Set接口的父接口，该接口里定义的方法既可用于操作 Set 集合，也可用于操作 List 集合。方法如下：

### 2.1 添加

> （1）`add(E obj)`：添加元素对象到当前集合中
> 		  （2）`addAll(Collection other)`：添加other集合中的所有元素对象到当前集合中，即this = this ∪ other



注意：add和addAll的区别

~~~java
package com.atguigu.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TestCollectionAdd {
    @Test
    public void testAdd(){
        //ArrayList是Collection的子接口List的实现类之一。
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        System.out.println(coll);
    }

    @Test
    public void testAddAll(){
        Collection c1 = new ArrayList();
        c1.add(1);
        c1.add(2);
        System.out.println("c1集合元素的个数：" + c1.size());//2
        System.out.println("c1 = " + c1);

        Collection c2 = new ArrayList();
        c2.add(1);
        c2.add(2);
        System.out.println("c2集合元素的个数：" + c2.size());//2
        System.out.println("c2 = " + c2);

        Collection other = new ArrayList();
        other.add(1);
        other.add(2);
        other.add(3);
        System.out.println("other集合元素的个数：" + other.size());//3
        System.out.println("other = " + other);
        System.out.println();

        c1.addAll(other);
        System.out.println("c1集合元素的个数：" + c1.size());//5
        System.out.println("c1.addAll(other) = " + c1);

        c2.add(other);
        System.out.println("c2集合元素的个数：" + c2.size());//3
        System.out.println("c2.add(other) = " + c2);
    }
}
~~~

> 注意：coll.addAll(other);与coll.add(other);

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1563548078274.png)

### 2.2 判断

> （3）`int size()`：获取当前集合中实际存储的元素个数
> 		  （4）`boolean isEmpty()`：判断当前集合是否为空集合
> 		  （5）`boolean contains(Object obj)`：判断当前集合中是否存在一个与obj对象equals返回true的元素
> 		  （6）`boolean containsAll(Collection coll)`：判断coll集合中的元素是否在当前集合中都存在。即coll集合是否是当前集合的“子集”	
> 		  （7）`boolean equals(Object obj)`：判断当前集合与obj是否相等

```java
package com.atguigu.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TestCollectionContains {
    @Test
    public void test01() {
        Collection coll = new ArrayList();
        System.out.println("coll在添加元素之前，isEmpty = " + coll.isEmpty());
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
        System.out.println("coll的元素个数" + coll.size());

        System.out.println("coll在添加元素之后，isEmpty = " + coll.isEmpty());
    }

    @Test
    public void test02() {
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
        System.out.println("coll = " + coll);
        System.out.println("coll是否包含“小李广” = " + coll.contains("小李广"));
        System.out.println("coll是否包含“宋红康” = " + coll.contains("宋红康"));

        Collection other = new ArrayList();
        other.add("小李广");
        other.add("扫地僧");
        other.add("尚硅谷");
        System.out.println("other = " + other);

        System.out.println("coll.containsAll(other) = " + coll.containsAll(other));
    }

    @Test
    public void test03(){
        Collection c1 = new ArrayList();
        c1.add(1);
        c1.add(2);
        System.out.println("c1集合元素的个数：" + c1.size());//2
        System.out.println("c1 = " + c1);

        Collection c2 = new ArrayList();
        c2.add(1);
        c2.add(2);
        System.out.println("c2集合元素的个数：" + c2.size());//2
        System.out.println("c2 = " + c2);

        Collection other = new ArrayList();
        other.add(1);
        other.add(2);
        other.add(3);
        System.out.println("other集合元素的个数：" + other.size());//3
        System.out.println("other = " + other);
        System.out.println();

        c1.addAll(other);
        System.out.println("c1集合元素的个数：" + c1.size());//5
        System.out.println("c1.addAll(other) = " + c1);
        System.out.println("c1.contains(other) = " + c1.contains(other));
        System.out.println("c1.containsAll(other) = " + c1.containsAll(other));
        System.out.println();

        c2.add(other);
        System.out.println("c2集合元素的个数：" + c2.size());
        System.out.println("c2.add(other) = " + c2);
        System.out.println("c2.contains(other) = " + c2.contains(other));
        System.out.println("c2.containsAll(other) = " + c2.containsAll(other));
    }

}
```

### 2.3 删除

> （8）`void clear()`：清空集合元素
> 		  （9） `boolean remove(Object obj)` ：从当前集合中删除第一个找到的与obj对象equals返回true的元素。
> 		  （10）`boolean removeAll(Collection coll)`：==从当前集合中删除所有与coll集合中相同的元素。==即this = this - this ∩ coll
> 		  （11）`boolean retainAll(Collection coll)`：==从当前集合中删除两个集合中不同的元素，==使得当前集合仅保留与coll集合中的元素相同的元素，即当前集合中仅保留两个集合的交集，即this  = this ∩ coll；

注意几种删除方法的区别

```java
package com.atguigu.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class TestCollectionRemove {
    @Test
    public void test01(){
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
        System.out.println("coll = " + coll);

        coll.remove("小李广");
        System.out.println("删除元素\"小李广\"之后coll = " + coll);
        
        coll.clear();
        System.out.println("coll清空之后，coll = " + coll);
    }

    @Test
    public void test02() {
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
        System.out.println("coll = " + coll);

        Collection other = new ArrayList();
        other.add("小李广");
        other.add("扫地僧");
        other.add("尚硅谷");
        System.out.println("other = " + other);

        coll.removeAll(other);
        System.out.println("coll.removeAll(other)之后，coll = " + coll);
        System.out.println("coll.removeAll(other)之后，other = " + other);
    }

    @Test
    public void test03() {
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
        System.out.println("coll = " + coll);

        Collection other = new ArrayList();
        other.add("小李广");
        other.add("扫地僧");
        other.add("尚硅谷");
        System.out.println("other = " + other);

        coll.retainAll(other);
        System.out.println("coll.retainAll(other)之后，coll = " + coll);
        System.out.println("coll.retainAll(other)之后，other = " + other);
    }

}
```

### 2.4 其它

> （12）`Object[] toArray()`：返回包含当前集合中所有元素的数组
> 		 （13）`hashCode()`：获取集合对象的哈希值
> 		  （14）`iterator()`：返回迭代器对象，用于集合遍历

```java
public class TestCollectionContains {
    @Test
    public void test01() {
        Collection coll = new ArrayList();

        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
		//集合转换为数组：集合的toArray()方法
        Object[] objects = coll.toArray();
        System.out.println("用数组返回coll中所有元素：" + Arrays.toString(objects));
		
        //对应的，数组转换为集合：调用Arrays的asList(Object ...objs)
        Object[] arr1 = new Object[]{123,"AA","CC"};
        Collection list = Arrays.asList(arr1);
        System.out.println(list);
    }
}
```

## 3. Iterator(迭代器)接口

### 3.1 Iterator接口

- 在程序开发中，经常需要遍历集合中的所有元素。针对这种需求，JDK专门提供了一个接口`java.util.Iterator`。`Iterator`接口也是Java集合中的一员，但它与`Collection`、`Map`接口有所不同。
  - Collection接口与Map接口主要用于`存储`元素
  - `Iterator`，被称为迭代器接口，本身并不提供存储对象的能力，主要用于`遍历`Collection中的元素


- Collection接口继承了java.lang.Iterable接口，该接口有一个iterator()方法，那么所有实现了Collection接口的集合类都有一个iterator()方法，用以返回一个实现了Iterator接口的对象。
  - `public Iterator iterator()`: 获取集合对应的迭代器，用来遍历集合中的元素的。
  - <font color='red'>集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合的第一个元素之前。</font>

- Iterator接口的常用方法如下：
  - `public E next()`:返回迭代的下一个元素。
  - `public boolean hasNext()`:如果仍有元素可以迭代，则返回 true。

- 注意：在调用it.next()方法之前必须要调用it.hasNext()进行检测。若不调用，且下一条记录无效，直接调用it.next()会抛出`NoSuchElementException异常`。


举例：

~~~java
package com.atguigu.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TestIterator {
    @Test
    public void test01(){
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");

        Iterator iterator = coll.iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next()); //报NoSuchElementException异常
    }

    @Test
    public void test02(){
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");

        Iterator iterator = coll.iterator();//获取迭代器对象
        while(iterator.hasNext()) {//判断是否还有元素可迭代
            System.out.println(iterator.next());//取出下一个元素
        }
    }
}

~~~

### 3.2 迭代器的执行原理

Iterator迭代器对象在遍历集合时，内部采用指针的方式来跟踪集合中的元素，接下来通过一个图例来演示Iterator对象迭代元素的过程：

![image-20220407235130988](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220407235130988.png)

使用Iterator迭代器删除元素：java.util.Iterator迭代器中有一个方法：void remove() ;

```java
Iterator iter = coll.iterator();//回到起点
while(iter.hasNext()){
    Object obj = iter.next();
    if(obj.equals("Tom")){
        iter.remove();
    }
}
```

注意：

- Iterator可以删除集合的元素，但是遍历过程中通过迭代器对象的remove方法，不是集合对象的remove方法。
- 如果还未调用next()或在上一次调用 next() 方法之后已经调用了 remove() 方法，再调用remove()都会报IllegalStateException。

- Collection已经有remove(xx)方法了，为什么Iterator迭代器还要提供删除方法呢？因为迭代器的remove()可以按指定的条件进行删除。


例如：要删除以下集合元素中的偶数

```java
package com.atguigu.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TestIteratorRemove {
    @Test
    public void test01(){
        Collection coll = new ArrayList();
        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);
        coll.add(5);
        coll.add(6);

        Iterator iterator = coll.iterator();
        while(iterator.hasNext()){
            Integer element = (Integer) iterator.next();
            if(element % 2 == 0){
                iterator.remove();
            }
        }
        System.out.println(coll);
    }
}

```

在JDK8.0时，Collection接口有了removeIf 方法，即可以根据条件删除。（第18章中再讲）

```java
package com.atguigu.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class TestCollectionRemoveIf {
    @Test
    public void test01(){
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
        coll.add("佛地魔");
        System.out.println("coll = " + coll);

        coll.removeIf(new Predicate() {
            @Override
            public boolean test(Object o) {
                String str = (String) o;
                return str.contains("地");
            }
        });
        System.out.println("删除包含\"地\"字的元素之后coll = " + coll);
    }
}
```

### 3.3 foreach循环

- foreach循环（也称增强for循环）是 JDK5.0 中定义的一个高级for循环，专门用来`遍历数组和集合`的。==foreach循环依然使用的是迭代器==。<font color='red'>它用于遍历Collection和数组。通常只进行遍历元素，不要在遍历的过程中对集合元素进行增删操作</font>


- foreach循环的语法格式：


```java
for(元素的数据类型 局部变量 : Collection集合或数组){ 
  	//操作局部变量的输出操作
}
//这里局部变量就是一个临时变量，自己命名就可以
```

- 举例：


```java
package com.atguigu.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TestForeach {
    @Test
    public void test01(){
        Collection coll = new ArrayList();
        coll.add("小李广");
        coll.add("扫地僧");
        coll.add("石破天");
		//foreach循环其实就是使用Iterator迭代器来完成元素的遍历的。
        for (Object o : coll) {
            System.out.println(o);
        }
    }
    @Test
    public void test02(){
        int[] nums = {1,2,3,4,5};
        for (int num : nums) {
            System.out.println(num);
        }
        System.out.println("-----------------");
        String[] names = {"张三","李四","王五"};
        for (String name : names) {
            System.out.println(name);
        }
    }
}
```

- 对于集合的遍历，增强for的内部原理其实是个Iterator迭代器。如下图。

![image-20220128010114124](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220128010114124.png)

- 它用于遍历Collection和数组。通常只进行遍历元素，不要在遍历的过程中对集合元素进行增删操作。
  - 练习：判断输出结果为何？

```java
public class ForTest {
    public static void main(String[] args) {
        String[] str = new String[5];
        for (String myStr : str) {
            myStr = "atguigu";
            System.out.println(myStr);
        }
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
    }
}

```

## 4. Collection子接口1：List

### 4.1 List接口特点

- 鉴于Java中数组用来存储数据的局限性，我们通常使用`java.util.List`替代数组
- List集合类中`元素有序`、且`可重复`，集合中的每个元素都有其对应的顺序索引。

  - 举例：List集合存储数据，就像银行门口客服，给每一个来办理业务的客户分配序号：第一个来的是“张三”，客服给他分配的是0；第二个来的是“李四”，客服给他分配的1；以此类推，最后一个序号应该是“总人数-1”。

![1563549818689](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1563549818689.png)

- JDK API中List接口的实现类常用的有：`ArrayList`、`LinkedList`和`Vector`。

### 4.2 List接口方法

List除了从Collection集合继承的方法外，List 集合里添加了一些`根据索引`来操作集合元素的方法。

- 插入元素
  - `void add(int index, Object ele)`:在index位置插入ele元素
  - boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
- 获取元素
  - `Object get(int index)`:获取指定index位置的元素
  - List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的子集合
- 获取元素索引
  - int indexOf(Object obj):返回obj在集合中首次出现的位置
  - int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
- 删除和替换元素
  - `Object remove(int index)`:移除指定index位置的元素，并返回此元素

  - `Object set(int index, Object ele)`:设置指定index位置的元素为ele


举例：

```java
package com.atguigu.list;

import java.util.ArrayList;
import java.util.List;

public class TestListMethod {
    public static void main(String[] args) {
        // 创建List集合对象
        List<String> list = new ArrayList<String>();

        // 往 尾部添加 指定元素
        list.add("图图");
        list.add("小美");
        list.add("不高兴");

        System.out.println(list);
        // add(int index,String s) 往指定位置添加
        list.add(1,"没头脑");

        System.out.println(list);
        // String remove(int index) 删除指定位置元素  返回被删除元素
        // 删除索引位置为2的元素
        System.out.println("删除索引位置为2的元素");
        System.out.println(list.remove(2));

        System.out.println(list);

        // String set(int index,String s)
        // 在指定位置 进行 元素替代（改）
        // 修改指定位置元素
        list.set(0, "三毛");
        System.out.println(list);

        // String get(int index)  获取指定位置元素
        // 跟size() 方法一起用  来 遍历的
        for(int i = 0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        //还可以使用增强for
        for (String string : list) {
            System.out.println(string);
        }
    }
}
```

> 注意：在JavaSE中List名称的类型有两个，一个是java.util.List集合接口，一个是java.awt.List图形界面的组件，别导错包了。

### 4.3 List接口主要实现类：ArrayList

- ==ArrayList 是 List 接口的`主要实现类`，线程不安全，但是效率更高==

- 本质上，ArrayList是对象引用的一个”变长”数组

- Arrays.asList(…) 方法返回的 List 集合，既不是 ArrayList 实例，也不是 Vector 实例。 Arrays.asList(…) 返回值是一个固定长度的 List 集合

  ![image-20220408210743342](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220408210743342.png)

### 4.4 List的实现类之二：LinkedList

- ==**对于频繁的插入或删除元素的操作，建议使用LinkedList类，效率较高。这是由底层采用链表（双向链表）结构存储数据决定的**。==

![image-20220408225615829](C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第12章_集合框架/images/image-20220408225615829.png)

- 特有方法：

  - void addFirst(Object obj)
  - void addLast(Object obj)	
  - Object getFirst()
  - Object getLast()
  - Object removeFirst()
  - Object removeLast()

  

### 4.4ArrayList和LinkedList区别

> `ArrayList`和`LinkedList`都是Java集合框架中的List接口的实现，它们的主要区别如下：
>
> 1. **底层数据结构不同**
>
> `ArrayList`底层是基于数组实现的，它支持基于索引的访问。`LinkedList`底层是基于双向链表实现的，它没有快速访问索引的能力。当需要随意访问元素而不必遍历整个列表时，通常使用`ArrayList`；而需要在列表中插入或删除元素时，通常使用`LinkedList`。
>
> 2. **访问速度不同**
>
> 由于`ArrayList`实现了RandomAccess接口，因此它可以基于索引进行高效的随机访问，时间复杂度为O(1)。而`LinkedList`的随机访问，需要经过一次遍历才能到达目标节点，时间复杂度为O(n)。因此，当需要快速随机访问元素时，`ArrayList`更快；当需要频繁在中间插入或删除元素时，`LinkedList`更快。
>
> 3. **内存占用不同**
>
> `ArrayList`会预留一定的空间，因此当元素数量超过容量时，需要重新分配更大的的内存空间。而`LinkedList`则需要在每个元素中存储前驱和后继节点的引用，因此在存储大量元素时，`LinkedList`占用更多的内存空间。因此，当需要高效地利用内存时，`ArrayList`更优。而当需要轻量级的数据结构时，`LinkedList`则更适合。
>
> 综上所述，`ArrayList`适用于访问集合次数比修改和删除操作次数多的场合；`LinkedList`适用于频繁的插入和删除操作、对元素访问次数较少的场合。



### 4.5 List的实现类之三：Vector

- Vector 是一个`古老`的集合，JDK1.0就有了。大多数操作与ArrayList相同，区别之处在于Vector是`线程安全`的。
- 在各种List中，最好把`ArrayList作为默认选择`。当插入、删除频繁时，使用LinkedList；Vector总是比ArrayList慢，所以尽量避免使用。
- 特有方法：
  - void addElement(Object obj)
  - void insertElementAt(Object obj,int index)
  - void setElementAt(Object obj,int index)
  - void removeElement(Object obj)
  - void removeAllElements()

### 4.6 练习

**面试题：**

```java
@Test
public void testListRemove() {
    List list = new ArrayList();
    list.add(1);
    list.add(2);
    list.add(3);
    updateList(list);
    System.out.println(list);//[1,2]
}

private static void updateList(List list) {
    list.remove(2);  
}

```

**练习1：**

- 定义学生类，属性为姓名、年龄，提供必要的getter、setter方法，构造器，toString()，equals()方法。
- 使用ArrayList集合，保存录入的多个学生对象。
- 循环录入的方式，1：继续录入，0：结束录入。
- 录入结束后，用foreach遍历集合。

* 代码实现，效果如图所示：

  ![1559890098509](C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第12章_集合框架/images/1559890098509.png)

```java
package com.atguigu.test01;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentTest {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList stuList = new ArrayList();

        for (;;) {

            System.out.println("选择（录入 1 ；结束 0）");
            int x = scanner.nextInt();//根据x的值，判断是否需要继续循环

            if (x == 1) {
                System.out.println("姓名");
                String name = scanner.next();
                System.out.println("年龄");
                int age = scanner.nextInt();
                Student stu = new Student(age, name);
                stuList.add(stu);

            } else if (x == 0) {
                break;

            } else {

                System.out.println("输入有误，请重新输入");
            }
        }

        for (Object stu : stuList) {
            System.out.println(stu);
        }
    }
}

public class Student {

    private int age;
    private String name;

    public Student() {
    }

    
    public Student(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}


	public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

	@Override
	public String toString() {
		return "Student [age=" + age + ", name=" + name + "]";
	}

}
```

**练习2：**

​	1、请定义方法public static int listTest(Collection list,String s)统计集合中指定元素出现的次数

​	2、创建集合，集合存放随机生成的30个小写字母

​	3、用listTest统计，a、b、c、x元素的出现次数

​	4、效果如下

![1559896150606](C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第12章_集合框架/images/1559896150606.png)

```java
package com.atguigu.test02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Test02 {
	public static void main(String[] args) {
		Collection list = new ArrayList();
		Random rand = new Random();
		for (int i = 0; i < 30; i++) {
			list.add((char)(rand.nextInt(26)+97)+"");
		}
		System.out.println(list);
		System.out.println("a:"+listTest(list, "a"));	
		System.out.println("b:"+listTest(list, "b"));	
		System.out.println("c:"+listTest(list, "c"));
		System.out.println("x:"+listTest(list, "x"));	
	}

	public static int listTest(Collection list, String string) {
		int count = 0;
		for (Object object : list) {
			if(string.equals(object)){
				count++;
			}
		}
		return count;
	}
}

```

**练习3：KTV点歌系统**

**描述**

分别使用ArrayList和LinkedList集合，编写一个**`KTV点歌系统`**的程序。在程序中：

- 指令1代表添加歌曲
- 指令2代表将所选歌曲置顶
- 指令3代表将所选歌曲提前一位
- 指令4代表退出该系统

要求根据用户输入的指令和歌曲名展现歌曲列表。例如输入指令1，输入歌曲名"爱你一万年"，则输出“当前歌曲列表：[爱你一万年]”。

**提示**

* 为了指引用户操作，首先要将各个指令所表示的含义打印到控制台

  ```java
  System.out.println("-------------欢迎来到点歌系统------------");
  System.out.println("1.添加歌曲至列表");
  System.out.println("2.将歌曲置顶");
  System.out.println("3.将歌曲前移一位");
  System.out.println("4.退出");
  ```

* 程序中需要创建一个集合作为歌曲列表，并向其添加一部分歌曲

* 通过ArrayList或LinkedList集合定义的方法操作歌曲列表

**代码**

* 使用ArrayList集合模拟点歌系统的实现代码，如下所示：

  ```java
  /**
   * @author 尚硅谷-宋红康
   * @create 20:26
   */
  public class KTVByArrayList {
      private static ArrayList musicList = new ArrayList();// 创建歌曲列表
      private static Scanner sc = new Scanner(System.in);
  
      public static void main(String[] args) {
          addMusicList();// 添加一部分歌曲至歌曲列表
          boolean flag = true;
          while (flag) {
              System.out.println("当前歌曲列表：" + musicList);
              System.out.println("-------------欢迎来到点歌系统------------");
              System.out.println("1.添加歌曲至列表");
              System.out.println("2.将歌曲置顶");
              System.out.println("3.将歌曲前移一位");
              System.out.println("4.退出");
              System.out.print("请输入操作序号：");
              int key = sc.nextInt();// //接收键盘输入的功能选项序号
              // 执行序号对应的功能
              switch (key) {
                  case 1:// 添加歌曲至列表
                      addMusic();
                      break;
                  case 2:// 将歌曲置顶
                      setTop();
                      break;
                  case 3:// 将歌曲前移一位
                      setBefore();
                      break;
                  case 4:// 退出
                      System.out.println("----------------退出---------------");
                      System.out.println("您已退出系统");
                      flag = false;
                      break;
                  default:
                      System.out.println("----------------------------------");
                      System.out.println("功能选择有误，请输入正确的功能序号!");
                      break;
              }
  
          }
      }
  
      // 初始时添加歌曲名称
      private static void addMusicList() {
          musicList.add("本草纲目");
          musicList.add("你是我的眼");
          musicList.add("老男孩");
          musicList.add("白月光与朱砂痣");
          musicList.add("不谓侠");
          musicList.add("爱你");
      }
  
      // 执行添加歌曲
      private static void addMusic() {
          System.out.print("请输入要添加的歌曲名称：");
          String musicName = sc.next();// 获取键盘输入内容
          musicList.add(musicName);// 添加歌曲到列表的最后
          System.out.println("已添加歌曲：" + musicName);
      }
  
      // 执行将歌曲置顶
      private static void setTop() {
          System.out.print("请输入要置顶的歌曲名称：");
          String musicName = sc.next();// 获取键盘输入内容
          int musicIndex = musicList.indexOf(musicName);// 查找指定歌曲位置
          if (musicIndex < 0) {// 判断输入歌曲是否存在
              System.out.println("当前列表中没有输入的歌曲！");
          }else if(musicIndex == 0){
              System.out.println("当前歌曲默认已置顶！");
          }else {
              musicList.remove(musicName);// 移除指定的歌曲
              musicList.add(0, musicName);// 将指定的歌曲放到第一位
              System.out.println("已将歌曲《" + musicName + "》置顶");
          }
      }
  
      // 执行将歌曲置前一位
      private static void setBefore() {
          System.out.print("请输入要置前的歌曲名称：");
          String musicName = sc.next();// 获取键盘输入内容
          int musicIndex = musicList.indexOf(musicName);// 查找指定歌曲位置
          if (musicIndex < 0) {// 判断输入歌曲是否存在
              System.out.println("当前列表中没有输入的歌曲！");
          } else if (musicIndex == 0) {// 判断歌曲是否已在第一位
              System.out.println("当前歌曲已在最顶部！");
          } else {
              musicList.remove(musicName);// 移除指定的歌曲
              musicList.add(musicIndex - 1, musicName);// 将指定的歌曲放到前一位
              System.out.println("已将歌曲《" + musicName + "》置前一位");
          }
      }
  }
  ```

## 5. Collection子接口2：Set

### 5.1 Set接口概述

- Set接口是Collection的子接口，Set接口相较于Collection接口没有提供额外的方法
- <font color='red'>Set 集合不允许包含相同的元素，如果试把两个相同的元素加入同一个 Set 集合中，则添加操作失败。</font>
- Set集合支持的遍历方式和Collection集合一样：foreach和Iterator。
- Set的常用实现类有：HashSet、TreeSet、LinkedHashSet。

### 5.2 Set主要实现类：HashSet

#### 5.2.1 HashSet概述

- HashSet 是 Set 接口的主要实现类，大多数时候使用 Set 集合时都使用这个实现类。

- HashSet 按 Hash 算法来存储集合中的元素，因此具有很好的存储、查找、删除性能。

- HashSet 具有以下`特点`：
  - 元素的无序性的(<font color='cornflowerblue'>无序性：元素的顺序是不确定的没有规律的</font>)
  - HashSet 不是线程安全的
  - 集合元素可以是 null

- HashSet 集合`判断两个元素相等的标准`：两个对象通过 `hashCode()` 方法得到的哈希值相等，并且两个对象的 `equals() `方法返回值为true。

- 对于存放在Set容器中的对象，**<font color='red'>对应的类一定要重写hashCode()和equals(Object obj)方法</font>**，以实现对象相等规则。即：“相等的对象必须具有相等的散列码”。

- HashSet集合中元素的无序性，不等同于随机性。这里的无序性与元素的添加位置有关。具体来说：我们在添加每一个元素到数组中时，具体的存储位置是由元素的hashCode()调用后返回的hash值决定的。导致在数组中每个元素不是依次紧密存放的，表现出一定的无序性。


#### 5.2.2 HashSet中添加元素的过程：

- 第1步：当向 HashSet 集合中存入一个元素时，HashSet 会调用该对象的 hashCode() 方法得到该对象的 hashCode值，然后根据 hashCode值，通过某个散列函数决定该对象在 HashSet 底层数组中的存储位置。

- 第2步：如果要在数组中存储的位置上没有元素，则直接添加成功。

- 第3步：如果要在数组中存储的位置上有元素，则继续比较：

  - 如果两个元素的hashCode值不相等，则添加成功；
  - 如果两个元素的hashCode()值相等，则会继续调用equals()方法：
    - 如果equals()方法结果为false，则添加成功。
    - 如果equals()方法结果为true，则添加失败。

  > 第2步添加成功，元素会保存在底层数组中。
  >
  > 第3步两种添加成功的操作，由于该底层数组的位置已经有元素了，则会通过`链表`的方式继续链接，存储。

举例：

```java
package com.atguigu.set;

import java.util.Objects;

public class MyDate {
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyDate myDate = (MyDate) o;
        return year == myDate.year &&
                month == myDate.month &&
                day == myDate.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
```

```java
package com.atguigu.set;

import org.junit.Test;

import java.util.HashSet;

public class TestHashSet {
    @Test
    public void test01(){
        HashSet set = new HashSet();
        set.add("张三");
        set.add("张三");
        set.add("李四");
        set.add("王五");
        set.add("王五");
        set.add("赵六");

        System.out.println("set = " + set);//不允许重复，无序
    }

    @Test
    public void test02(){
        HashSet set = new HashSet();
        set.add(new MyDate(2021,1,1));
        set.add(new MyDate(2021,1,1));
        set.add(new MyDate(2022,2,4));
        set.add(new MyDate(2022,2,4));


        System.out.println("set = " + set);//不允许重复，无序
    }
}
```

#### 5.2.3为什么要重写equals方法和hashcode方法

> 在Java中，equals()方法和hashCode()方法都是用于比较对象的值和确定对象在哈希表中的位置的方法。
>
> equals()方法比较对象的值，返回布尔值，通常用于比较两个对象是否相等。如果在某个类中没有重写equals()方法，则默认使用Object类的equals()方法，此时equals()方法比较的是对象的引用地址。
>
> 而hashCode()方法确定对象在哈希表中的位置，返回一个整数值。在使用HashSet、HashMap等集合时，会根据该值确定对象的桶位置，不同的对象根据其哈希值会有不同的桶位置。如果在某个类中没有重写hashCode()方法，则默认使用Object类的hashCode()方法，此时每个对象的哈希值都不同，这样即使两个对象的值相同，它们也会被认为是不相等的。
>
> 因此，为了正确地比较对象的值和确定对象在哈希表中的位置，我们需要在某些情况下重写equals()方法和hashCode()方法。例如，在我们定义的一个类中，如果需要根据对象的某些属性来比较两个对象是否相等，则需要重写equals()方法和hashCode()方法，以保证两个对象在哈希表中的位置相同。

如下所示：

1. **没有重写equals方法和hashcode方法的时候**

```java
import java.util.*;


public class StringTest {

    public static void main(String[] args) {
        User user1 = new User();
        user1.setUsername("zhangsan");
        user1.setPassword("123");
        User user2 = new User();
        user2.setUsername("zhangsan");
        user2.setPassword("123");
        System.out.println(user1.equals(user2));

        HashSet<Object> set1 = new HashSet<>();
        set1.add(new User("zhangsan","zhangsan"));
        HashSet<Object> set2 = new HashSet<>();
        set2.add(new User("zhangsan","zhangsan"));

        System.out.println(set1.hashCode());
        System.out.println(set2.hashCode());
    }
}
```

当比较为对象类型时，使用的是默认的`equals`方法，比较的是两个对象的引用地址，所以即使两个对象的值相同，也为false。

当使用`hashCode()`方法确定对象在哈希表中的位置时，使用的是默认的`hashCode`方法，此时每个对象的哈希值都不同，这样即使两个对象的值相同，它们也会被认为是不相等的。

![image-20230325195425632](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230325195425632.png)

重写equals方法和hashcode方法之后：

![image-20230325195810404](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230325195810404.png)

#### 5.2.4 重写 hashCode() 方法的基本原则

- 在程序运行时，同一个对象多次调用 hashCode() 方法应该返回相同的值。
- 当两个对象的 equals() 方法比较返回 true 时，这两个对象的 hashCode() 方法的返回值也应相等。
- 对象中用作 equals() 方法比较的 Field，都应该用来计算 hashCode 值。


> 注意：如果两个元素的 equals() 方法返回 true，但它们的 hashCode() 返回值不相等，hashSet 将会把它们存储在不同的位置，但依然可以添加成功。

#### 5.2.5 重写equals()方法的基本原则

- 重写equals方法的时候一般都需要同时复写hashCode方法。通常参与计算hashCode的对象的属性也应该参与到equals()中进行计算。

- 推荐：开发中直接调用Eclipse/IDEA里的快捷键自动重写equals()和hashCode()方法即可。

  - 为什么用Eclipse/IDEA复写hashCode方法，有31这个数字？

  ```
  首先，选择系数的时候要选择尽量大的系数。因为如果计算出来的hash地址越大，所谓的“冲突”就越少，查找起来效率也会提高。（减少冲突）
  
  其次，31只占用5bits,相乘造成数据溢出的概率较小。
  
  再次，31可以 由i*31== (i<<5)-1来表示,现在很多虚拟机里面都有做相关优化。（提高算法效率）
  
  最后，31是一个素数，素数作用就是如果我用一个数字来乘以这个素数，那么最终出来的结果只能被素数本身和被乘数还有1来整除！(减少冲突)
  ```

#### 5.2.6	 练习

**练习1：**在List内去除重复数字值，要求尽量简单

```java
public static List duplicateList(List list) {
      HashSet set = new HashSet();
      set.addAll(list);
      return new ArrayList(set);
}
public static void main(String[] args) {
      List list = new ArrayList();
      list.add(new Integer(1));
      list.add(new Integer(2));
      list.add(new Integer(2));
      list.add(new Integer(4));
      list.add(new Integer(4));
      List list2 = duplicateList(list);
      for (Object integer : list2) {
          System.out.println(integer);
      }
}

```

**练习2：**获取随机数

编写一个程序，获取10个1至20的随机数，要求随机数不能重复。并把最终的随机数输出到控制台。

```java
/**
 * 
 * @Description 
 * @author 尚硅谷-宋红康
 * @date 2022年5月7日上午12:43:01
 *
 */
public class RandomValueTest {
	public static void main(String[] args) {
		HashSet hs = new HashSet(); // 创建集合对象
		Random r = new Random();
		while (hs.size() < 10) {
			int num = r.nextInt(20) + 1; // 生成1到20的随机数
			hs.add(num);
		}

		for (Integer integer : hs) { // 遍历集合
			System.out.println(integer); // 打印每一个元素
		}
	}
}

```

**练习3：**去重

使用Scanner从键盘读取一行输入，去掉其中重复字符，打印出不同的那些字符。比如：aaaabbbcccddd

```java
/**
 * 
 * @Description 
 * @author 尚硅谷-宋红康
 * @date 2022年5月7日上午12:44:01
 *
 */
public class DistinctTest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // 创建键盘录入对象
		System.out.println("请输入一行字符串:");
		String line = sc.nextLine(); // 将键盘录入的字符串存储在line中
		char[] arr = line.toCharArray(); // 将字符串转换成字符数组
        
		HashSet hs = new HashSet(); // 创建HashSet集合对象

		for (Object c : arr) { // 遍历字符数组
			hs.add(c); // 将字符数组中的字符添加到集合中
		}

		for (Object ch : hs) { // 遍历集合
			System.out.print(ch);
		}
	}
}

```

**练习4：**面试题

```java
HashSet set = new HashSet();
Person p1 = new Person(1001,"AA");
Person p2 = new Person(1002,"BB");

set.add(p1);
set.add(p2);
p1.name = "CC";
set.remove(p1);
System.out.println(set);

set.add(new Person(1001,"CC"));
System.out.println(set);

set.add(new Person(1001,"AA"));
System.out.println(set);

//其中Person类中重写了hashCode()和equal()方法

```

### 5.3 Set实现类之二：LinkedHashSet

- LinkedHashSet 是 HashSet 的子类，不允许集合元素重复。

- LinkedHashSet 根据元素的 hashCode 值来决定元素的存储位置，但它同时使用`双向链表`维护元素的次序，这使得元素看起来是以`添加顺序`保存的。

- LinkedHashSet`插入性能略低`于 HashSet，但在`迭代访问` Set 里的全部元素时有很好的性能。


<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220408235936404.png" alt="image-20220408235936404" style="zoom:67%;" />

举例：

```java
package com.atguigu.set;

import org.junit.Test;

import java.util.LinkedHashSet;

public class TestLinkedHashSet {
    @Test
    public void test01(){
        LinkedHashSet set = new LinkedHashSet();
        set.add("张三");
        set.add("张三");
        set.add("李四");
        set.add("王五");
        set.add("王五");
        set.add("赵六");

        System.out.println("set = " + set);//不允许重复，体现添加顺序
    }
}
```

### 5.4 Set实现类之三：TreeSet

#### 5.4.1 TreeSet概述

- ==TreeSet 是 SortedSet 接口的实现类，TreeSet 可以按照添加的元素的指定的属性的大小顺序进行遍历。==

- <font color='red'>TreeSet底层使用`红黑树`结构存储数据</font>

- 新增的方法如下： (了解) 

  > - Comparator comparator()
  > - Object first()
  > - Object last()
  > - Object lower(Object e)
  > - Object higher(Object e)
  > - SortedSet subSet(fromElement, toElement)
  > - SortedSet headSet(toElement)
  > - SortedSet tailSet(fromElement)

- TreeSet特点：<font color='red'>不允许重复、实现排序（自然排序或定制排序）</font>

- TreeSet 两种排序方法：`自然排序`和`定制排序`。默认情况下，TreeSet 采用自然排序。

  - `自然排序`：TreeSet 会调用集合元素的 compareTo(Object obj) 方法来比较元素之间的大小关系，然后将集合元素按升序(默认情况)排列。
    - 如果试图把一个对象添加到 TreeSet 时，则该对象的类必须实现 Comparable 接口。
    - 实现 Comparable 的类必须实现 compareTo(Object obj) 方法，两个对象即通过 compareTo(Object obj) 方法的返回值来比较大小。
  - `定制排序`：如果元素所属的类没有实现Comparable接口，或不希望按照升序(默认情况)的方式排列元素或希望按照其它属性大小进行排序，则考虑使用定制排序。定制排序，通过Comparator接口来实现。需要重写compare(T o1,T o2)方法。
    - 利用int compare(T o1,T o2)方法，比较o1和o2的大小：如果方法返回正整数，则表示o1大于o2；如果返回0，表示相等；返回负整数，表示o1小于o2。
    - 要实现定制排序，需要将实现Comparator接口的实例作为形参传递给TreeSet的构造器。

- 因为只有相同类的两个实例才会比较大小，所以向 TreeSet 中添加的应该是`同一个类的对象`。

- 对于 TreeSet 集合而言，它判断`两个对象是否相等的唯一标准`是：两个对象通过 `compareTo(Object obj) 或compare(Object o1,Object o2)`方法比较返回值。返回值为0，则认为两个对象相等。

#### 5.4.2 举例

举例1：

```java
package com.atguigu.set;


import org.junit.Test;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author 尚硅谷-宋红康
 * @create 14:22
 */
public class TreeSetTest {
    /*
    * 自然排序：针对String类的对象
    * */
    @Test
    public void test1(){
        TreeSet set = new TreeSet();

        set.add("MM");
        set.add("CC");
        set.add("AA");
        set.add("DD");
        set.add("ZZ");
        //set.add(123);  //报ClassCastException的异常

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    /*
    * 自然排序：针对User类的对象
    * */
    @Test
    public void test2(){
        TreeSet set = new TreeSet();

        set.add(new User("Tom",12));
        set.add(new User("Rose",23));
        set.add(new User("Jerry",2));
        set.add(new User("Eric",18));
        set.add(new User("Tommy",44));
        set.add(new User("Jim",23));
        set.add(new User("Maria",18));
        //set.add("Tom");

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println(set.contains(new User("Jack", 23))); //true
    }
}
```

其中，User类定义如下：

```java
/**
 * @author 尚硅谷-宋红康
 * @create 14:22
 */
public class User implements Comparable{
    String name;
    int age;
    
	public User() {
    }
    
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
    /*
    举例：按照age从小到大的顺序排列，如果age相同，则按照name从大到小的顺序排列
    * */
    public int compareTo(Object o) {
        if(this == o){
            return 0;
        }

        if(o instanceof User){
            User user = (User)o;
            int value = this.age - user.age;
            if(value != 0){
                return value;
            }
            return -this.name.compareTo(user.name);
        }
        throw new RuntimeException("输入的类型不匹配");
    }
}

```

举例2：

```java
/*
 * 定制排序
 * */
@Test
public void test3(){
    //按照User的姓名的从小到大的顺序排列
    Comparator comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            if(o1 instanceof User && o2 instanceof User){
                User u1 = (User)o1;
                User u2 = (User)o2;

                return u1.name.compareTo(u2.name);
            }
            throw new RuntimeException("输入的类型不匹配");
        }
    };
    TreeSet set = new TreeSet(comparator);

    set.add(new User("Tom",12));
    set.add(new User("Rose",23));
    set.add(new User("Jerry",2));
    set.add(new User("Eric",18));
    set.add(new User("Tommy",44));
    set.add(new User("Jim",23));
    set.add(new User("Maria",18));
    //set.add(new User("Maria",28));

    Iterator iterator = set.iterator();
    while(iterator.hasNext()){
        System.out.println(iterator.next());
    }
}
```

#### 5.4.3 练习

**练习1：**在一个List集合中存储了多个无大小顺序并且有重复的字符串，定义一个方法，让其有序(从小到大排序)，并且不能去除重复元素。

提示：考查ArrayList、TreeSet

```java
/**
 * 
 * @Description
 * @author 尚硅谷-宋红康
 * @date 2022年4月7日上午12:50:46
 *
 */
public class SortTest {
	public static void main(String[] args) {
		ArrayList list = new ArrayList();
		list.add("ccc");
		list.add("ccc");
		list.add("aaa");
		list.add("aaa");
		list.add("bbb");
		list.add("ddd");
		list.add("ddd");
		sort(list);
		System.out.println(list);
	}

	/*
	 * 对集合中的元素排序,并保留重复
	 */
	public static void sort(List list) {
		TreeSet ts = new TreeSet(new Comparator() { 
			@Override
			public int compare(Object o1, Object o2) { // 重写compare方法
                String s1 = (String)o1;
                String s2 = (String)o2;
				int num = s1.compareTo(s2); // 比较内容
				return num == 0 ? 1 : num; // 如果内容一样返回一个不为0的数字即可
			}
		});

		ts.addAll(list); // 将list集合中的所有元素添加到ts中
		list.clear(); // 清空list
		list.addAll(ts); // 将ts中排序并保留重复的结果在添加到list中
	}
}

```

**练习2：**TreeSet的自然排序和定制排序

1. 定义一个Employee类。
   该类包含：private成员变量name,age,birthday，其中 birthday 为 MyDate 类的对象；
   并为每一个属性定义 getter, setter 方法；
   并重写 toString 方法输出 name, age, birthday

2. MyDate类包含:
   private成员变量year,month,day；并为每一个属性定义 getter, setter 方法；

3. 创建该类的 5 个对象，并把这些对象放入 TreeSet 集合中（下一章：TreeSet 需使用泛型来定义）

4. 分别按以下两种方式对集合中的元素进行排序，并遍历输出：

   1). 使Employee 实现 Comparable 接口，并按 name 排序
   2). 创建 TreeSet 时传入 Comparator对象，按生日日期的先后排序。

代码实现：

```java
public class MyDate implements Comparable{
    private int year;
    private int month;
    private int day;

    public MyDate() {
    }

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
//        return "MyDate{" +
//                "year=" + year +
//                ", month=" + month +
//                ", day=" + day +
//                '}';
        return year + "年" + month + "月" + day + "日";
    }

    @Override
    public int compareTo(Object o) {
        if(this == o){
            return 0;
        }
        if(o instanceof MyDate){
            MyDate myDate = (MyDate) o;
            int yearDistance = this.getYear() - myDate.getYear();
            if(yearDistance != 0){
                return yearDistance;
            }
            int monthDistance = this.getMonth() - myDate.getMonth();
            if(monthDistance != 0){
                return monthDistance;
            }

            return this.getDay() - myDate.getDay();
        }
        throw new RuntimeException("输入的类型不匹配");
    }
}
```

```java
public class Employee implements Comparable{
    private String name;
    private int age;
    private MyDate birthday;


    public Employee() {
    }

    public Employee(String name, int age, MyDate birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o == this){
            return 0;
        }
        if(o instanceof Employee){
            Employee emp = (Employee) o;
            return this.name.compareTo(emp.name);
        }
        throw new RuntimeException("传入的类型不匹配");
    }
}
```

```java
public class EmployeeTest {
    /*
    自然排序：
    创建该类的 5 个对象，并把这些对象放入 TreeSet 集合中
    * 需求1：使Employee 实现 Comparable 接口，并按 name 排序
    * */
    @Test
    public void test1(){
        TreeSet set = new TreeSet();

        Employee e1 = new Employee("Tom",23,new MyDate(1999,7,9));
        Employee e2 = new Employee("Rose",43,new MyDate(1999,7,19));
        Employee e3 = new Employee("Jack",54,new MyDate(1998,12,21));
        Employee e4 = new Employee("Jerry",12,new MyDate(2002,4,21));
        Employee e5 = new Employee("Tony",22,new MyDate(2001,9,12));

        set.add(e1);
        set.add(e2);
        set.add(e3);
        set.add(e4);
        set.add(e5);

        //遍历
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    /*
    * 定制排序：
    * 创建 TreeSet 时传入 Comparator对象，按生日日期的先后排序。
    * */
    @Test
    public void test2(){
        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Employee && o2 instanceof Employee){
                    Employee e1 = (Employee) o1;
                    Employee e2 = (Employee) o2;
                    //对比两个employee的生日的大小
                    MyDate birth1 = e1.getBirthday();
                    MyDate birth2 = e2.getBirthday();
                    //方式1：
//                    int yearDistance = birth1.getYear() - birth2.getYear();
//                    if(yearDistance != 0){
//                        return yearDistance;
//                    }
//                    int monthDistance = birth1.getMonth() - birth2.getMonth();
//                    if(monthDistance != 0){
//                        return monthDistance;
//                    }
//
//                    return birth1.getDay() - birth2.getDay();

                    //方式2：
                    return birth1.compareTo(birth2);
                }

                throw new RuntimeException("输入的类型不匹配");

            }
        };
        TreeSet set = new TreeSet(comparator);

        Employee e1 = new Employee("Tom",23,new MyDate(1999,7,9));
        Employee e2 = new Employee("Rose",43,new MyDate(1999,7,19));
        Employee e3 = new Employee("Jack",54,new MyDate(1998,12,21));
        Employee e4 = new Employee("Jerry",12,new MyDate(2002,4,21));
        Employee e5 = new Employee("Tony",22,new MyDate(2001,9,12));

        set.add(e1);
        set.add(e2);
        set.add(e3);
        set.add(e4);
        set.add(e5);

        //遍历
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
}
```

## 6. Map接口

现实生活与开发中，我们常会看到这样的一类集合：用户ID与账户信息、学生姓名与考试成绩、IP地址与主机名等，这种一一对应的关系，就称作映射。Java提供了专门的集合框架用来存储这种映射关系的对象，即`java.util.Map`接口。

### 6.1 Map接口概述

- Map与Collection并列存在。用于保存具有`映射关系`的数据：key-value

  - `Collection`集合称为单列集合，元素是孤立存在的（理解为单身）。
  - `Map`集合称为双列集合，元素是成对存在的(理解为夫妻)。

- Map 中的 key 和  value 都可以是任何引用类型的数据。但常用String类作为Map的“键”。

- Map接口的常用实现类：`HashMap`、`LinkedHashMap`、`TreeMap`和``Properties`。其中，HashMap是 Map 接口使用`频率最高`的实现类。

  <img src="C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第12章_集合框架/images/image-20220409001015034.png" alt="image-20220409001015034" style="zoom:67%;" />

### 6.2 Map中key-value特点

这里主要以HashMap为例说明。HashMap中存储的key、value的特点如下：

<img src="C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第12章_集合框架/images/image-20220409001213720.png" alt="image-20220409001213720" style="zoom:67%;" />

- Map 中的 `key用Set来存放`，`不允许重复`，即同一个 Map 对象所对应的类，须重写hashCode()和equals()方法

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514190412763.png" alt="image-20220514190412763" style="zoom:67%;" />

- key 和 value 之间存在单向一对一关系，即通过指定的 key 总能找到唯一的、确定的 value，不同key对应的`value可以重复`。value所在的类要重写equals()方法。

- key和value构成一个entry。所有的entry彼此之间是`无序的`、`不可重复的`。

### 6.2 Map接口的常用方法

> - **添加、修改操作：**
>   - `Object put(Object key,Object value)`：将指定key-value添加到(或修改)当前map对象中
>   - `void putAll(Map m)`:将m中的所有key-value对存放到当前map中
> - **删除操作：**
>   - `Object remove(Object key)`：移除指定key的key-value对，并返回value
>   - `void clear()`：清空当前map中的所有数据
> - **元素查询的操作：**
>   - `Object get(Object key)`：获取指定key对应的value
>   - `boolean containsKey(Object key)`：是否包含指定的key
>   - `boolean containsValue(Object value)`：是否包含指定的value
>   - `int size()`：返回map中key-value对的个数
>   - `boolean isEmpty()`：判断当前map是否为空
>   - `boolean equals(Object obj)`：判断当前map和参数对象obj是否相等
> - **元视图操作的方法：**
>   - `Set keySet()`：返回所有key构成的Set集合
>   - `Collection values()`：返回所有value构成的Collection集合
>   - `Set entrySet()`：返回所有key-value对构成的Set集合

举例：

```java
package com.atguigu.map;

import java.util.HashMap;

public class TestMapMethod {
    public static void main(String[] args) {
        //创建 map对象
        HashMap map = new HashMap();

        //添加元素到集合
        map.put("黄晓明", "杨颖");
        map.put("李晨", "李小璐");
        map.put("李晨", "范冰冰");
        map.put("邓超", "孙俪");
        System.out.println(map);

        //删除指定的key-value
        System.out.println(map.remove("黄晓明"));
        System.out.println(map);

        //查询指定key对应的value
        System.out.println(map.get("邓超"));
        System.out.println(map.get("黄晓明"));

    }
}
```

举例：

```java
public static void main(String[] args) {
    HashMap map = new HashMap();
    map.put("许仙", "白娘子");
    map.put("董永", "七仙女");
    map.put("牛郎", "织女");
    map.put("许仙", "小青");

    System.out.println("所有的key:");
    Set keySet = map.keySet();
    for (Object key : keySet) {
        System.out.println(key);
    }

    System.out.println("所有的value:");
    Collection values = map.values();
    for (Object value : values) {
        System.out.println(value);
    }

    System.out.println("所有的映射关系:");
    Set entrySet = map.entrySet();
    for (Object mapping : entrySet) {
        //System.out.println(entry);
        Map.Entry entry = (Map.Entry) mapping;
        System.out.println(entry.getKey() + "->" + entry.getValue());
    }
}
```

### 6.3 Map的主要实现类：HashMap

#### 6.3.1 HashMap概述

- HashMap是 Map 接口`使用频率最高`的实现类。
- HashMap是线程不安全的。<font color='red'>允许添加 null 键和 null 值</font>。
- 存储数据采用的哈希表结构，底层使用`一维数组`+`单向链表`+`红黑树`进行key-value数据的存储。与HashSet一样，元素的存取顺序不能保证一致。
- HashMap `判断两个key相等的标准`是：两个 key 的hashCode值相等，通过 equals() 方法返回 true。
- HashMap `判断两个value相等的标准`是：两个 value 通过 equals() 方法返回 true。

#### 6.3.2 练习

**练习1：**添加你喜欢的歌手以及你喜欢他唱过的歌曲

例如：

![image-20220914190805362](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220914190805362.png)

```java
//方式1
/**
 * @author 尚硅谷-宋红康
 * @create 9:03
 */
public class SingerTest1 {
    public static void main(String[] args) {

        //创建一个HashMap用于保存歌手和其歌曲集

        HashMap singers = new HashMap();
        //声明一组key,value
        String singer1 = "周杰伦";

        ArrayList songs1 = new ArrayList();
        songs1.add("双节棍");
        songs1.add("本草纲目");
        songs1.add("夜曲");
        songs1.add("稻香");
        //添加到map中
        singers.put(singer1,songs1);
        //声明一组key,value
        String singer2 = "陈奕迅";
        List songs2 = Arrays.asList("浮夸", "十年", "红玫瑰", "好久不见", "孤勇者");
        //添加到map中
        singers.put(singer2,songs2);

        //遍历map
        Set entrySet = singers.entrySet();
        for(Object obj : entrySet){
            Map.Entry entry = (Map.Entry)obj;
            String singer = (String) entry.getKey();
            List songs = (List) entry.getValue();

            System.out.println("歌手：" + singer);
            System.out.println("歌曲有：" + songs);
        }

    }
}
```

```java
//方式2：改为HashSet实现
public class SingerTest2 {
	@Test
	public void test1() {

		Singer singer1 = new Singer("周杰伦");
		Singer singer2 = new Singer("陈奕迅");

		Song song1 = new Song("双节棍");
		Song song2 = new Song("本草纲目");
		Song song3 = new Song("夜曲");
		Song song4 = new Song("浮夸");
		Song song5 = new Song("十年");
		Song song6 = new Song("孤勇者");

		HashSet h1 = new HashSet();// 放歌手一的歌曲
		h1.add(song1);
		h1.add(song2);
		h1.add(song3);

		HashSet h2 = new HashSet();// 放歌手二的歌曲
		h2.add(song4);
		h2.add(song5);
		h2.add(song6);

		HashMap hashMap = new HashMap();// 放歌手和他对应的歌曲
		hashMap.put(singer1, h1);
		hashMap.put(singer2, h2);

		for (Object obj : hashMap.keySet()) {
			System.out.println(obj + "=" + hashMap.get(obj));
		}

	}
}

//歌曲
public class Song implements Comparable{
	private String songName;//歌名

	public Song() {
		super();
	}

	public Song(String songName) {
		super();
		this.songName = songName;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	@Override
	public String toString() {
		return "《" + songName + "》";
	}

	@Override
	public int compareTo(Object o) {
		if(o == this){
			return 0;
		}
		if(o instanceof Song){
			Song song = (Song)o;
			return songName.compareTo(song.getSongName());
		}
		return 0;
	}
	
	
}
//歌手
public class Singer implements Comparable{
	private String name;
	private Song song;
	
	public Singer() {
		super();
	}

	public Singer(String name) {
		super();
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Object o) {
		if(o == this){
			return 0;
		}
		if(o instanceof Singer){
			Singer singer = (Singer)o;
			return name.compareTo(singer.getName());
		}
		return 0;
	}
}
```

**练习2**：二级联动

将省份和城市的名称保存在集合中，当用户选择省份以后，二级联动，显示对应省份的地级市供用户选择。

效果演示：

![img](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/clip_image001.png)

```java
/**
 * 
 * @Description 
 * @author 尚硅谷-宋红康  Email:shkstart@126.com
 * @version 
 * @date 2021年5月7日上午12:26:59
 *
 */
class CityMap{
	
	public static Map model = new HashMap();
	
	static {
		model.put("北京", new String[] {"北京"});
		model.put("上海", new String[] {"上海"});
		model.put("天津", new String[] {"天津"});
		model.put("重庆", new String[] {"重庆"});
		model.put("黑龙江", new String[] {"哈尔滨","齐齐哈尔","牡丹江","大庆","伊春","双鸭山","绥化"});
		model.put("吉林", new String[] {"长春","延边","吉林","白山","白城","四平","松原"});
		model.put("河北", new String[] {"石家庄","张家口","邯郸","邢台","唐山","保定","秦皇岛"});
	}
	
}

public class ProvinceTest {
	public static void main(String[] args) {
		
		Set keySet = CityMap.model.keySet();
		for(Object s : keySet) {
			System.out.print(s + "\t");
		}
		System.out.println();
		System.out.println("请选择你所在的省份：");
		Scanner scan = new Scanner(System.in);
		String province = scan.next();
		
		String[] citys = (String[])CityMap.model.get(province);
		for(String city : citys) {
			System.out.print(city + "\t");
		}
		System.out.println();
		System.out.println("请选择你所在的城市：");
		String city = scan.next();
		
		System.out.println("信息登记完毕");
	}
	
}

```

**练习3**：WordCount统计

需求：统计字符串中每个字符出现的次数

String str = "aaaabbbcccccccccc";

提示：

char[] arr = str.toCharArray();   //将字符串转换成字符数组

HashMap hm = new HashMap();   //创建双列集合存储键和值，键放字符，值放次数

```java
/**
 * 
 * @author 尚硅谷-宋红康 
 * @date 2022年5月7日上午12:26:59
 *
 */
public class WordCountTest {
	public static void main(String[] args) {
        String str = "aaaabbbcccccccccc";
        char[] arr = str.toCharArray(); // 将字符串转换成字符数组
        HashMap map = new HashMap(); // 创建双列集合存储键和值

        for (char c : arr) { // 遍历字符数组
            if (!map.containsKey(c)) { // 如果不包含这个键
                map.put(c, 1); // 就将键和值为1添加
            } else { // 如果包含这个键
                map.put(c, (int)map.get(c) + 1); // 就将键和值再加1添加进来
            }

        }

        for (Object key : map.keySet()) { // 遍历双列集合
            System.out.println(key + "=" + map.get(key));
        }

    }
}

```

### 6.4 Map实现类之二：LinkedHashMap

- LinkedHashMap 是 HashMap 的子类
- 存储数据采用的哈希表结构+双向链表结构，在HashMap存储结构的基础上，使用了一对`双向链表`来`记录添加元素的先后顺序`，可以保证遍历元素时，与添加的顺序一致。
- 通过哈希表结构可以保证键的唯一、不重复，需要键所在类重写hashCode()方法、equals()方法。

```java
public class TestLinkedHashMap {
    public static void main(String[] args) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("王五", 13000.0);
        map.put("张三", 10000.0);
        //key相同，新的value会覆盖原来的value
        //因为String重写了hashCode和equals方法
        map.put("张三", 12000.0);
        map.put("李四", 14000.0);
        //HashMap支持key和value为null值
        String name = null;
        Double salary = null;
        map.put(name, salary);

        Set entrySet = map.entrySet();
        for (Object obj : entrySet) {
        	Map.Entry entry = (Map.Entry)obj;
            System.out.println(entry);
        }
    }
}
```

### 6.5 Map实现类之三：TreeMap

- TreeMap存储 key-value 对时，需要根据 key-value 对进行排序。TreeMap 可以保证所有的 key-value 对处于`有序状态`。
- TreeSet底层使用`红黑树`结构存储数据
- TreeMap 的 Key 的排序：
  - `自然排序`：**<font color='red'>TreeMap 的所有的 Key 必须实现 Comparable 接口，而且所有的 Key 应该是同一个类的对象，否则将会抛出 ClasssCastException</font>**
  - `定制排序`：创建 TreeMap 时，构造器传入一个 Comparator 对象，该对象负责对 TreeMap 中的所有 key 进行排序。此时不需要 Map 的 Key 实现 Comparable 接口
- TreeMap判断`两个key相等的标准`：**<font color='red'>两个key通过compareTo()方法或者compare()方法返回0。</font>**

```java
public class TreeMapTest {
    public static void main(String[] args) {
        // 创建一个TreeMap，使用默认的自然顺序进行排序
        TreeMap<String, Integer> defaultTreeMap = new TreeMap<>();
        defaultTreeMap.put("c", 30);
        defaultTreeMap.put("a", 10);
        defaultTreeMap.put("b", 20);
        defaultTreeMap.put("d", 40);
        System.out.println("默认自然顺序排序：" + defaultTreeMap);
        
    }
}
```

![image-20230326201949554](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230326201949554.png)

User类实现`Compareble`接口，重写`CompareTo`方法，根据用户年龄进行排序

Uer类

```java
import java.util.Objects;

public class User implements Comparable {
    private String username;

    private int age;

    public User() {
    }

    public User(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) {
            return 0;
        }
        if (o instanceof User) {
            User user = (User) o;
            int result = this.getAge() - user.getAge();
            return result;
        }
        throw new RuntimeException("类型不匹配");
    }
}
```

```java
import java.util.Set;
import java.util.TreeMap;


public class TreeMapTest {
    public static void main(String[] args) {

        TreeMap userTreeMap = new TreeMap<>();
        userTreeMap.put(new User("zhangsan", 18), 3);
        userTreeMap.put(new User("lisi", 19), 4);
        userTreeMap.put(new User("wangwu", 17), 5);
        userTreeMap.put(new User("zhaoliu", 22), 6);

        Set userSet = userTreeMap.entrySet();
        for (Object user : userSet) {
            System.out.println(user);
        }
    }
}
```

![image-20230326203356084](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20230326203356084.png)





### 6.6 Map实现类之四：Hashtable

- Hashtable是Map接口的`古老实现类`，JDK1.0就提供了。<font color='cornflowerblue'>不同于HashMap，Hashtable是线程安全的。</font>
- Hashtable实现原理和HashMap相同，功能相同。底层都使用哈希表结构（数组+单向链表），查询速度快。
- 与HashMap一样，Hashtable 也不能保证其中 Key-Value 对的顺序
- Hashtable判断两个key相等、两个value相等的标准，与HashMap一致。
- 与HashMap不同，<font color='red'>Hashtable 不允许使用 null 作为 key 或 value</font>。

面试题：Hashtable和HashMap的区别

```ini
HashMap:底层是一个哈希表（jdk7:数组+链表;jdk8:数组+链表+红黑树）,是一个线程不安全的集合,执行效率高
Hashtable:底层也是一个哈希表（数组+链表）,是一个线程安全的集合,执行效率低

HashMap集合:可以存储null的键、null的值
Hashtable集合,不能存储null的键、null的值

Hashtable和Vector集合一样,在jdk1.2版本之后被更先进的集合(HashMap,ArrayList)取代了。所以HashMap是Map的主要实现类，Hashtable是Map的古老实现类。

Hashtable的子类Properties（配置文件）依然活跃在历史舞台
Properties集合是一个唯一和IO流相结合的集合
```



### 6.7 Map实现类之五：Properties

- Properties 类是 Hashtable 的子类，该对象用于处理属性文件

- <font color='red'>由于属性文件里的 key、value 都是字符串类型，所以 Properties 中要求 key 和 value 都是字符串类型</font>

- 存取数据时，建议使用setProperty(String key,String value)方法和getProperty(String key)方法

```java
@Test
public void test01() {
    Properties properties = System.getProperties();
    String fileEncoding = properties.getProperty("file.encoding");//当前源文件字符编码
    System.out.println("fileEncoding = " + fileEncoding);
}
@Test
public void test02() {
    Properties properties = new Properties();
    properties.setProperty("user","songhk");
    properties.setProperty("password","123456");
    System.out.println(properties);
}

@Test
public void test03() throws IOException {
    Properties pros = new Properties();
    pros.load(new FileInputStream("jdbc.properties"));
    String user = pros.getProperty("user");
    System.out.println(user);
}
```

## 7. Collections工具类

参考操作数组的工具类：Arrays，Collections 是一个操作 Set、List 和 Map 等集合的工具类。

### 7.1 常用方法

Collections 中提供了一系列静态的方法对集合元素进行排序、查询和修改等操作，还提供了对集合对象设置不可变、对集合对象实现同步控制等方法（均为static方法）：

> **排序操作：**
>
> - `reverse(List)`：反转 List 中元素的顺序
> - `shuffle(List)`：对 List 集合元素进行随机排序
> - `sort(List)`：根据元素的自然顺序对指定 List 集合元素按升序排序
> - `sort(List，Comparator)`：根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
> - `swap(List，int， int)`：将指定 list 集合中的 i 处元素和 j 处元素进行交换
>
> **查找**
>
> - `Object max(Collection)`：根据元素的自然顺序，返回给定集合中的最大元素
> - `Object max(Collection，Comparator)`：根据 Comparator 指定的顺序，返回给定集合中的最大元素
> - `Object min(Collection)`：根据元素的自然顺序，返回给定集合中的最小元素
> - `Object min(Collection，Comparator)`：根据 Comparator 指定的顺序，返回给定集合中的最小元素
> - `int binarySearch(List list,T key)`在List集合中查找某个元素的下标，但是List的元素必须是T或T的子类对象，而且必须是可比较大小的，即支持自然排序的。而且集合也事先必须是有序的，否则结果不确定。
> - `int binarySearch(List list,T key,Comparator c)`在List集合中查找某个元素的下标，但是List的元素必须是T或T的子类对象，而且集合也事先必须是按照c比较器规则进行排序过的，否则结果不确定。
> - `int frequency(Collection c，Object o)`：返回指定集合中指定元素的出现次数
>
> **复制、替换**
>
> - `void copy(List dest,List src)`：将src中的内容复制到dest中
> - `boolean replaceAll(List list，Object oldVal，Object newVal)`：使用新值替换 List 对象的所有旧值
> - 提供了多个unmodifiableXxx()方法，该方法返回指定 Xxx的不可修改的视图。
>
> **添加**
>
> - `boolean addAll(Collection  c,T... elements)`将所有指定元素添加到指定 collection 中。
>
> **同步**
>
> - Collections 类中提供了多个 synchronizedXxx() 方法，该方法可使将指定集合包装成线程同步的集合，从而可以解决多线程并发访问集合时的线程安全问题：


![image-20220409003002526](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220409003002526.png)

### 7.2 举例

```java
package com.atguigu.collections;

import org.junit.Test;

import java.text.Collator;
import java.util.*;

public class TestCollections {
    @Test
    public void test01(){
        /*
        public static <T> boolean addAll(Collection<? super T> c,T... elements)
        将所有指定元素添加到指定 collection 中。Collection的集合的元素类型必须>=T类型
        */
        Collection<Object> coll = new ArrayList<>();
        Collections.addAll(coll, "hello","java");
        Collections.addAll(coll, 1,2,3,4);

        Collection<String> coll2 = new ArrayList<>();
        Collections.addAll(coll2, "hello","java");
        //Collections.addAll(coll2, 1,2,3,4);//String和Integer之间没有父子类关系
    }

	@Test
    public void test02(){
/*
 * public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll)
 * 在coll集合中找出最大的元素，集合中的对象必须是T或T的子类对象，而且支持自然排序
*  
*  public static <T> T max(Collection<? extends T> coll,Comparator<? super T> comp)
*  在coll集合中找出最大的元素，集合中的对象必须是T或T的子类对象，按照比较器comp找出最大者
*
*/
        List<Man> list = new ArrayList<>();
        list.add(new Man("张三",23));
        list.add(new Man("李四",24));
        list.add(new Man("王五",25));

        /*
         * Man max = Collections.max(list);//要求Man实现Comparable接口，或者父类实现
         * System.out.println(max);
         */

        Man max = Collections.max(list, new Comparator<Man>() {
            @Override
            public int compare(Man o1, Man o2) {
                return o2.getAge()-o2.getAge();
            }
        });
        System.out.println(max);
    }

	@Test
    public void test03(){
        /*
         * public static void reverse(List<?> list)
         * 反转指定列表List中元素的顺序。
         */
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"hello","java","world");
        System.out.println(list);

        Collections.reverse(list);
        System.out.println(list);
    }
	@Test
    public void test04(){
        /*
         * public static void shuffle(List<?> list) 
         * List 集合元素进行随机排序，类似洗牌，打乱顺序
         */
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"hello","java","world");

        Collections.shuffle(list);
        System.out.println(list);
    }
	@Test
    public void test05() {
        /*
         * public static <T extends Comparable<? super T>> void sort(List<T> list)
         * 根据元素的自然顺序对指定 List 集合元素按升序排序
         *
         * public static <T> void sort(List<T> list,Comparator<? super T> c)
         * 根据指定的 Comparator 产生的顺序对 List 集合元素进行排序
         */
        List<Man> list = new ArrayList<>();
        list.add(new Man("张三",23));
        list.add(new Man("李四",24));
        list.add(new Man("王五",25));

        Collections.sort(list);
        System.out.println(list);

        Collections.sort(list, new Comparator<Man>() {
            @Override
            public int compare(Man o1, Man o2) {
                return Collator.getInstance(Locale.CHINA).compare(o1.getName(),o2.getName());
            }
        });
        System.out.println(list);
    }
	@Test
    public void test06(){
        /*
         * public static void swap(List<?> list,int i,int j)
         * 将指定 list 集合中的 i 处元素和 j 处元素进行交换
         */
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"hello","java","world");

        Collections.swap(list,0,2);
        System.out.println(list);
    }
	@Test
    public void test07(){
        /*
         * public static int frequency(Collection<?> c,Object o)
         * 返回指定集合中指定元素的出现次数
         */
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"hello","java","world","hello","hello");
        int count = Collections.frequency(list, "hello");
        System.out.println("count = " + count);
    }
	@Test
    public void test08(){
        /*
         * public static <T> void copy(List<? super T> dest,List<? extends T> src)
         * 将src中的内容复制到dest中
         */
        List<Integer> list = new ArrayList<>();
        for(int i=1; i<=5; i++){//1-5
            list.add(i);
        }

        List<Integer> list2 = new ArrayList<>();
        for(int i=11; i<=13; i++){//11-13
            list2.add(i);
        }

        Collections.copy(list, list2);
        System.out.println(list);

        List<Integer> list3 = new ArrayList<>();
        for(int i=11; i<=20; i++){//11-20
            list3.add(i);
        }
		//java.lang.IndexOutOfBoundsException: Source does not fit in dest
        //Collections.copy(list, list3);
        //System.out.println(list);

    }
	
	@Test
    public void test09(){
        /*
         * public static <T> boolean replaceAll(List<T> list，T oldVal，T newVal)
         * 使用新值替换 List 对象的所有旧值
         */
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"hello","java","world","hello","hello");

        Collections.replaceAll(list, "hello","song");
        System.out.println(list);
    }
}

```



### 7.3 练习

**练习1：**

请从键盘随机输入10个整数保存到List中，并按倒序、从大到小的顺序显示出来

**练习2：**模拟斗地主洗牌和发牌，牌没有排序

效果演示：

![image-20220409011625061](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220409011625061.png)

提示：

```java
String[] num = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

String[] color = {"方片","梅花","红桃","黑桃"};

ArrayList<String> poker = new ArrayList<>();
```

代码示例：

```java
/**
 * 
 * @author 尚硅谷-宋红康
 * @date 2022年5月7日上午12:26:59
 *
 */
public class PokerTest {
	
	public static void main(String[] args) {
		String[] num = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		String[] color = {"方片","梅花","红桃","黑桃"};
		ArrayList poker = new ArrayList();
		//1. 生成54张扑克牌
		for (String s1 : color) {
			for (String s2 : num) {
				poker.add(s1.concat(" " + s2));
			}
		}

		poker.add("小王");
		poker.add("大王");
		//2. 洗牌
		Collections.shuffle(poker);
		//3. 发牌
		ArrayList tomCards = new ArrayList();
        ArrayList jerryCards = new ArrayList();
        ArrayList meCards = new ArrayList();
        ArrayList lastCards = new ArrayList();

        for (int i = 0; i < poker.size(); i++) {

            if(i >= poker.size() - 3){
                lastCards.add(poker.get(i));
            }else if(i % 3 == 0){
                tomCards.add(poker.get(i));
            }else if(i % 3 == 1){
                jerryCards.add(poker.get(i));
            }else {
                meCards.add(poker.get(i));
            }

        }

		//4. 看牌
		System.out.println("Tom:\n" + tomCards);
        System.out.println("Jerry:\n" + jerryCards);
        System.out.println("me:\n" + meCards);
        System.out.println("底牌:\n" + lastCards);
	}
}

```

**练习3：**模拟斗地主洗牌和发牌并对牌进行排序的代码实现。

![image-20220915002714578](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220915002714578.png)

提示：考查HashMap、TreeSet、ArrayList、Collections

代码示例：

```java
/**
 * @author 尚硅谷-宋红康
 * @create 0:23
 */
public class PokerTest1 {
    public static void main(String[] args) {
        String[] num = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        String[] color = {"方片", "梅花", "红桃", "黑桃"};
        HashMap map = new HashMap(); // 存储索引和扑克牌
        ArrayList list = new ArrayList(); // 存储索引
        int index = 0; // 索引的开始值
        for (String s1 : num) {
            for (String s2 : color) {
                map.put(index, s2.concat(s1)); // 将索引和扑克牌添加到HashMap中
                list.add(index); // 将索引添加到ArrayList集合中
                index++;
            }
        }
        map.put(index, "小王");
        list.add(index);
        index++;
        map.put(index, "大王");
        list.add(index);
        // 洗牌
        Collections.shuffle(list);
        // 发牌
        TreeSet Tom = new TreeSet();
        TreeSet Jerry = new TreeSet();
        TreeSet me = new TreeSet();
        TreeSet lastCards = new TreeSet();

        for (int i = 0; i < list.size(); i++) {
            if (i >= list.size() - 3) {
                lastCards.add(list.get(i)); // 将list集合中的索引添加到TreeSet集合中会自动排序
            } else if (i % 3 == 0) {
                Tom.add(list.get(i));
            } else if (i % 3 == 1) {
                Jerry.add(list.get(i));
            } else {
                me.add(list.get(i));
            }
        }

        // 看牌
        lookPoker("Tom", Tom, map);
        lookPoker("Jerry", Jerry, map);
        lookPoker("康师傅", me, map);
        lookPoker("底牌", lastCards, map);

    }

    public static void lookPoker(String name, TreeSet ts, HashMap map) {
        System.out.println(name + "的牌是:");
        for (Object index : ts) {
            System.out.print(map.get(index) + " ");
        }

        System.out.println();
    }
}
```

## 8.随堂复习

### 1. 数组存储数据方面的特点和弊端

```
   数组存储多个数据方面的特点：
   > 数组一旦初始化，其长度就是确定的。
   > 数组中的多个元素是依次紧密排列的，有序的，可重复的
   > (优点) 数组一旦初始化完成，其元素的类型就是确定的。不是此类型的元素，就不能添加到此数组中。
      int[] arr = new int[10];
      arr[0] = 1;
      arr[1] = "AA";//编译报错

      Object[] arr1 = new Object[10];
      arr1[0] = new String();
      arr1[1] = new Date();
   > (优点)元素的类型既可以是基本数据类型，也可以是引用数据类型。

   数组存储多个数据方面的弊端：
   > 数组一旦初始化，其长度就不可变了。
   > 数组中存储数据特点的单一性。对于无序的、不可重复的场景的多个数据就无能为力了。
   > 数组中可用的方法、属性都极少。具体的需求，都需要自己来组织相关的代码逻辑。
   > 针对于数组中元素的删除、插入操作，性能较差。
```

### 2. 集合框架概述

```
java.util.Collection:存储一个一个的数据
    |-----子接口：List:存储有序的、可重复的数据 ("动态"数组)
           |---- ArrayList(主要实现类)、LinkedList、Vector

    |-----子接口：Set:存储无序的、不可重复的数据(高中学习的集合)
           |---- HashSet(主要实现类)、LinkedHashSet、TreeSet


java.util.Map:存储一对一对的数据(key-value键值对，(x1,y1)、(x2,y2) --> y=f(x),类似于高中的函数)
    |---- HashMap(主要实现类)、LinkedHashMap、TreeMap、Hashtable、Properties
```

```
学习的程度把握：
层次1：针对于具体特点的多个数据，知道选择相应的适合的接口的主要实现类，会实例化，会调用常用的方法。
层次2：区分接口中不同的实现类的区别。
*****************
层次3：① 针对于常用的实现类，需要熟悉底层的源码 ② 熟悉常见的数据结构 （第14章讲）
```

### 3. Collection的常用方法

#### 3.1 常用方法

```
add(Object obj)
addAll(Collection coll)
clear()
isEmpty()
size()
contains(Object obj)
containsAll(Collection coll)
retainAll(Collection coll)
remove(Object obj)
removeAll(Collection coll)
hashCode()
equals()
toArray()
**************
iterator() ---> 引出了迭代器接口
```

```
向Collection中添加元素的要求：
> 要求元素所属的类一定要重写equals()!
```

```
集合与数组的相互转换：
集合 ---> 数组：toArray()
数组 ---> 集合：调用Arrays的静态方法asList(Object ... objs)，返回一个List
```

#### 3.2 迭代器接口

- 设计模式的一种
- 迭代器不负责数据的存储；负责对集合类的遍历

```java
1. 如何获取迭代器(Iterator)对象？
Iterator iterator = coll.iterator();

2. 如何实现遍历(代码实现)
while(iterator.hasNext()){
    System.out.println(iterator.next()); //next():①指针下移 ② 将下移以后集合位置上的元素返回
}
```



### 4. Collection的子接口：List

- 常用方法

```java
小结：
    增
        add(Object obj)
        addAll(Collection coll)
    删
        remove(Object obj)
        remove(int index)
    改
        set(int index, Object ele)
    查
        get(int index)
    插
        add(int index, Object ele)
        addAll(int index, Collection eles)
    长度
        size()
    遍历
        iterator() ：使用迭代器进行遍历
        增强for循环
        一般的for循环
```

```
List及其实现类特点
java.util.Collection:存储一个一个的数据
    |-----子接口：List:存储有序的、可重复的数据 ("动态"数组)
           |---- ArrayList:List的主要实现类；线程不安全的、效率高；底层使用Object[]数组存储
                           在添加数据、查找数据时，效率较高；在插入、删除数据时，效率较低
           |---- LinkedList:底层使用双向链表的方式进行存储；在对集合中的数据进行频繁的删除、插入操作时，建议							使用此类在插入、删除数据时，效率较高；在添加数据、查找数据时，效率较低；
           |---- Vector:List的古老实现类；线程安全的、效率低；底层使用Object[]数组存储

[面试题] ArrayList、Vector的区别？  ArrayList、LinkedList的区别？
```



### 5. Collection的子接口：Set

- Set中的常用的方法都是Collection中声明的方法，没有新增的方法
- 常见的实现类的对比

```
java.util.Collection:存储一个一个的数据
    |-----子接口：Set:存储无序的、不可重复的数据(高中学习的集合)
           |---- HashSet：主要实现类；底层使用的是HashMap，即使用数组+单向链表+红黑树结构进行存储。（jdk8中）
                |---- LinkedHashSet：是HashSet的子类；在现有的数组+单向链表+红黑树结构的基础上，又添加了
                                     一组双向链表，用于记录添加元素的先后顺序。即：我们可以按照添加元素的顺									序实现遍历。便于频繁的查询操作。
           |---- TreeSet：底层使用红黑树存储。可以按照添加的元素的指定的属性的大小顺序进行遍历。
```

- 难点： Set中无序性、不可重复性的理解（以HashSet及其子类为例说明）

```
>无序性： != 随机性。
         添加元素的顺序和遍历元素的顺序不一致,是不是就是无序性呢？ No!
         到底什么是无序性？与添加的元素的位置有关，不像ArrayList一样是依次紧密排列的。
         这里是根据添加的元素的哈希值，计算的其在数组中的存储位置。此位置不是依次排列的，表现为无序性。

>不可重复性：添加到Set中的元素是不能相同的。
          比较的标准，需要判断hashCode()得到的哈希值以及equals()得到的boolean型的结果。
          哈希值相同且equals()返回true，则认为元素是相同的。
```

```
添加到HashSet/LinkedHashSet中元素的要求:
>要求元素所在的类要重写两个方法：equals() 和 hashCode()。
>同时，要求equals() 和 hashCode()要保持一致性！我们只需要在IDEA中自动生成两个方法的重写即可，即能保证两个方法的一致性。
```

- 了解TreeSet的使用

### 6. Map接口

- 常用的方法

```
增：
    put(Object key,Object value)
    putAll(Map m)
删：
    Object remove(Object key)
改：
    put(Object key,Object value)
    putAll(Map m)
查：
    Object get(Object key)
长度：
    size()
遍历：
   遍历key集：Set keySet()
   遍历value集：Collection values()
   遍历entry集：Set entrySet()
```

- 常用的实现类

```
java.util.Map:存储一对一对的数据(key-value键值对，(x1,y1)、(x2,y2) --> y=f(x),类似于高中的函数)
    |---- HashMap:主要实现类;线程不安全的，效率高;可以添加null的key和value值;底层使用数组+单向链表+红黑树结构存储（jdk8）
        |---- LinkedHashMap:是HashMap的子类；在HashMap使用的数据结构的基础上，增加了一对双向链表，用于记录添加的元素的先后顺序，进而我们在遍历元素时，就可以按照添加的顺序显示。开发中，对于频繁的遍历操作，建议使用此类。
    |---- TreeMap:底层使用红黑树存储;可以按照添加的key-value中的key元素的指定的属性的大小顺序进行遍历。需要考虑使用①自然排序 ②定制排序。
    |---- Hashtable:古老实现类;线程安全的，效率低;不可以添加null的key或value值;底层使用数组+单向链表结构存储（jdk8）
        |---- Properties:其key和value都是String类型。常用来处理属性文件。


[面试题] 区别HashMap和Hashtable、区别HashMap和LinkedHashMap、HashMap的底层实现（① new HashMap() ② put(key,value)）
```

```
HashMap中元素的特点:
> HashMap中的所有的key彼此之间是不可重复的、无序的。所有的key就构成一个Set集合。--->key所在的类要重写hashCode()和equals()
> HashMap中的所有的value彼此之间是可重复的、无序的。所有的value就构成一个Collection集合。--->value所在的类要重写equals()
> HashMap中的一个key-value,就构成了一个entry。
> HashMap中的所有的entry彼此之间是不可重复的、无序的。所有的entry就构成了一个Set集合。
```

- （了解）TreeMap的使用

- （重要）Properties的使用

  ```java
  public class PropertiesTest {
      @Test
      public void test() throws IOException { //注意：因为设计到流的操作，为了确保流能关闭，建议使用try-catch-finally
          //方式1：数据和代码耦合度高；如果修改的话，需要重写的编译代码、打包发布，繁琐
          //数据
  //        String name = "Tom";
  //        String password = "abc123";
  
          //代码：用于操作name,password
          //...
  
          //方式2：将数据封装到具体的配置文件中，在程序中读取配置文件中的信息。实现了
          //数据和代码的解耦；由于我们没有修改代码，就省去了重新编译和打包的过程。
          File file = new File("info.properties"); //注意，要提前创建好
  //        System.out.println(file.getAbsolutePath());
          FileInputStream fis = new FileInputStream(file);
  
          Properties pros = new Properties();
          pros.load(fis); //加载流中的文件中的数据
  
          //读取数据
          String name = pros.getProperty("name");
          String pwd = pros.getProperty("password");
  
          System.out.println(name + ":" + pwd);
  
          fis.close();
      }
  
  }
  ```

### 7. Collections工具类的使用

```
区分Collection 和 Collections
Collection：集合框架中的用于存储一个一个元素的接口，又分为List和Set等子接口。
Collections：用于操作集合框架的一个工具类。此时的集合框架包括：Set、List、Map
```

- 熟悉常用的Collections中的方法即可。


## 9.企业真题

### 2.1 集合概述

#### 1. List，Set，Map是否继承自collection接口？（北京中\*译咨询、思\*贸易）

Map不是。

#### 2. 说说List,Set,Map三者的区别(民*银行)

```
类似问题：
> Map与Set、List的区别（纬*）
```

略

#### 3. 写出list、map、set接口的实现类，并说出其特点（华**为）

```
类似问题：
> 集合有哪些， 各自有哪些特点， 各自的API有哪些？（湖**利软件）
> List Map Set三个接口在存储元素时个有什么特点（*软）
```

略

#### 4. 常见集合类的区别和适用场景（饿**）

略

#### 5. 集合的父类是谁？哪些安全的？（北京中**信）

略。 不安全：ArrayList、HashMap、HashSet ； 安全：Vector、Hashtable

#### 6. 集合说一下哪些是线程不安全的（*科软）

略

#### 7. 遍历集合的方式有哪些？（恒*电子）

- 迭代器Iterator用来遍历Collection，不能用来遍历Map！

- 增强for
- 一般的for：可以用来遍历List

### 2.2 List接口

#### 1. List下面有哪些实现（软**力）

略

#### 2. ArrayList与LinkedList区别？(O\*\*O、滴\*、汇\*天下、拓\*软件、博纳\*\*软件、上海*进天下，北京永生**信息、\*联、在\*途游)

```
类似问题：
> ArrayList跟LinkedList的区别详细说出？（阿*校招、*东）
```

略。 补充上第14章中的源码（底层的数据结构）

#### 3. ArrayList与Vector区别呢？为什么要用ArrayList取代Vector呢？(湖**利软件)

Vector效率低。

#### 4. Java.util.ArrayList常用的方法有哪些？（华**为）

略

#### 5. Arraylist 是有序还是无序？为什么？（蜜*信息）

有序；底层使用数组：Object[]

### 2.3 Set接口

#### 1. Set集合有哪些实现类，分别有什么特点？（拓*软件）

```
类似问题：
> Set的实现类有哪些？（博*科技）
```

略

#### 2. List集合和Set集合的区别？（亚\*科技、\*海*翼科技，\*华电\*系统，达\*贷）

略

#### 3. Set里的元素是不能重复的，那么用什么方法来区分重复与否呢? 是用==还是equals()? 它们有何区别?（鸿*网络）

```
类似问题：
> 1.HashSet如何检查重复(创*科技)
> 3.Set使用哪个区分不能重复的元素的？（北京创**荣信息）
```

hashCode()  、 equals()

#### 4. TreeSet两种排序方式在使用的时候怎么起作用？（拓*软件）

在添加新的元素时，需要调用compareTo() 或 compare()

#### 5. TreeSet的数据结构（*米）

红黑树

### 2.4 Map接口

#### 1. 说一下Java的集合Map有哪些Map？（奥*医药）

略

#### 2. final怎么用，修饰Map可以继续添加数据吗？（*深蓝）

final HashMap map = new HashMap();

map.put("AA",123);

可以！

#### 3. Set和Map的比较（亚*科技）

HashSet底层就是HashMap

LinkedHashSet底层就是LinkedHashMap

TreeSet底层就是TreeMap

#### 4. HashMap说一下，线程安全吗？（*米）

```
类似问题：
> HashMap为什么线程不安全？（微*银行）
> HashMap是线程安全的吗？为什么不安全？（*团、*东、顺*）
```

不安全

#### 5. HashMap和Hashbable的区别？(银\*数据、阿\*\*巴芝麻信用、\*众银行、爱\*信、杭州*智公司)

```
类似问题：
> HashMap 和 HashTable 有什么区别，以及如何使用，以及他的一些方法？（阿*校招、*东、*度校招、顺*）
```

略

#### 6. Hashtable是怎么实现的，为什么线程安全？（迪*创新）

数组+单向链表；底层方法使用synchronized修饰

#### 7. HashMap和LinkedHashMap的区别（北京*晨阳光）

略。

#### 8. HashMap 和 TreeMap 的区别（*度，太极**、\*线途游、阿\*校招）

底层的数据结构截然不同。

#### 9. HashMap里面实际装的是什么？（惠*）

JDK7：HashMap内部声明了Entry，实现了Map中的Entry接口。（key，value作为Entry的两个属性出现）

JDK8：HashMap内部声明了Node，实现了Map中的Entry接口。（key，value作为Node的两个属性出现）

#### 10. HashMap的key存储在哪里？和value存储在一起吗？那么value存储在哪里？说具体点？（湖**利软件、天\*伟业）

数组+链表+红黑树。 key、value作为Node的属性出现

#### 11. 自定义类型可以作为Key么？（阿*）

可以！ 要重写hashCode() 和equals()

### Collections

#### 1. 集合类的工具类是谁？用过工具类哪些方法？（顺*）

Collections。略

#### 2. Collection 和 Collections的区别？（平*金服、\*软）

略

#### 3. ArrayList 如何实现排序（阿*）

Collections.sort(list) 或 Collections.sort(list,comparator)

#### 4. HashMap是否线程安全，怎样解决HashMap的线程不安全（中*卫星）

```
类似问题：
> 怎么实现HashMap线程安全？（*团、*东、顺*）
```

略



# 第12章_泛型(Generic)

***

## 1. 泛型概述

### 1.1 生活中的例子

- 举例1：中药店，每个抽屉外面贴着标签

![image-20220411000757577](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220411000757577.png)

- 举例2：超市购物架上很多瓶子，每个瓶子装的是什么，有标签

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220918230534965.png" alt="image-20220918230534965" style="zoom:67%;" />

- 举例3：家庭厨房中：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514191533296.png" alt="image-20220514191533296" style="zoom:67%;" />

> Java中的泛型，就类似于上述场景中的`标签`。

### 1.2 泛型的引入

在Java中，我们在声明方法时，当在完成方法功能时如果有`未知的数据`需要参与，这些未知的数据需要在调用方法时才能确定，那么我们把这样的数据通过`形参`表示。在方法体中，用这个形参名来代表那个未知的数据，而调用者在调用时，对应的传入`实参`就可以了。

![1563414367674](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1563414367674.png)

受以上启发，JDK1.5设计了泛型的概念。==**泛型即为“`类型参数`”，就是允许在定义类、接口时通过一个标识表示类中某个属性的类型，或者是某个方法的返回值或**==

**举例1：**

集合类在设计阶段/声明阶段不能确定这个容器到底实际存的是什么类型的对象，所以**在JDK5.0之前只能把元素类型设计为Object，JDK5.0时Java引入了“参数化类型（Parameterized type）”的概念，允许我们在创建集合时指定集合元素的类型**。比如：`List<String>`，这表明该List只能保存字符串类型的对象。

使用集合存储数据时，除了元素的类型不确定，其他部分是确定的（例如关于这个元素如何保存，如何管理等）。

**举例2：**

`java.lang.Comparable`接口和`java.util.Comparator`接口，是用于比较对象大小的接口。这两个接口只是限定了当一个对象大于另一个对象时返回正整数，小于返回负整数，等于返回0，但是并不确定是什么类型的对象比较大小。JDK5.0之前只能用Object类型表示，使用时既麻烦又不安全，因此 JDK5.0 给它们增加了泛型。

![image-20220923154058074](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220923154058074.png)

![image-20220923154426871](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220923154426871.png)

其中`<T>`就是类型参数，即泛型。

> 所谓泛型，就是允许在定义类、接口时通过一个`标识`表示类中某个`属性的类型`或者是某个方法的`返回值或参数的类型`。这个类型参数将在使用时（例如，继承或实现这个接口、创建对象或调用方法时）确定（即传入实际的类型参数，也称为类型实参）。

## 2. 使用泛型举例

自从JDK5.0引入泛型的概念之后，对之前核心类库中的API做了很大的修改，例如：JDK5.0改写了集合框架中的全部接口和类、java.lang.Comparable接口、java.util.Comparator接口、Class类等。为这些接口、类增加了泛型支持，从而可以在声明变量、创建对象时传入类型实参。

### 2.1 集合中使用泛型


#### 2.1.1 举例

**集合中没有使用泛型时：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220411001522636.png" alt="image-20220411001522636" style="zoom:80%;" />

**集合中使用泛型时：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220411001549747.png" alt="image-20220411001549747" style="zoom:80%;" />

> Java泛型可以保证如果程序在编译时没有发出警告，运行时就不会产生ClassCastException异常。即，把不安全的因素在编译期间就排除了，而不是运行期；既然通过了编译，那么类型一定是符合要求的，就避免了类型转换。
>
> 同时，代码更加简洁、健壮。
>
> **把一个集合中的内容限制为一个特定的数据类型，这就是generic背后的核心思想。**

举例：

```java
//泛型在List中的使用
@Test
public void test1(){
    //举例：将学生成绩保存在ArrayList中
    //标准写法：
    //ArrayList<Integer> list = new ArrayList<Integer>();
    //jdk7的新特性：类型推断
    ArrayList<Integer> list = new ArrayList<>();

    list.add(56); //自动装箱
    list.add(76);
    list.add(88);
    list.add(89);
    //当添加非Integer类型数据时，编译不通过
    //list.add("Tom");//编译报错

    Iterator<Integer> iterator = list.iterator();
    while(iterator.hasNext()){
        //不需要强转，直接可以获取添加时的元素的数据类型
        Integer score = iterator.next();
        System.out.println(score);
    }
}
```

举例：

```java
//泛型在Map中的使用
@Test
public void test2(){
    HashMap<String,Integer> map = new HashMap<>();

    map.put("Tom",67);
    map.put("Jim",56);
    map.put("Rose",88);
    //编译不通过
    //        map.put(67,"Jack");

    //遍历key集
    Set<String> keySet = map.keySet();
    for(String str:keySet){
        System.out.println(str);
    }

    //遍历value集
    Collection<Integer> values = map.values();
    Iterator<Integer> iterator = values.iterator();
    while(iterator.hasNext()){
        Integer value = iterator.next();
        System.out.println(value);
    }

    //遍历entry集
    Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
    Iterator<Map.Entry<String, Integer>> iterator1 = entrySet.iterator();
    while(iterator1.hasNext()){
        Map.Entry<String, Integer> entry = iterator1.next();
        String key = entry.getKey();
        Integer value = entry.getValue();
        System.out.println(key + ":" + value);
    }

}
```

#### 2.1.2 练习

练习1：

```
（1）创建一个ArrayList集合对象，并指定泛型为<Integer>

（2）添加5个[0,100)以内的整数到集合中

（3）使用foreach遍历输出5个整数

（4）使用集合的removeIf方法删除偶数，为Predicate接口指定泛型<Ineteger>

（5）再使用Iterator迭代器输出剩下的元素，为Iterator接口指定泛型<Integer>
```

```java
package com.atguigu.genericclass.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;

public class TestNumber {
    public static void main(String[] args) {
        ArrayList<Integer> coll = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 1; i <= 5 ; i++) {
            coll.add(random.nextInt(100));
        }

        System.out.println("coll中5个随机数是：");
        for (Integer integer : coll) {
            System.out.println(integer);
        }
		
        //方式1：使用集合的removeIf方法删除偶数
        coll.removeIf(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer % 2 == 0;
            }
        });
        //方式2：调用Iterator接口的remove()方法
        //Iterator<Integer> iterator1 = coll.iterator();
        //while(coll.hasNext()){
        //    Integer i = coll.next();
        //   if(i % 2 == 0){
        //       coll.remove();
        //    }
        //}

        System.out.println("coll中删除偶数后：");
        Iterator<Integer> iterator = coll.iterator();
        while(iterator.hasNext()){
            Integer number = iterator.next();
            System.out.println(number);
        }

    }
}
```

练习2：编写一个简单的同学通迅录

需求说明：

- 查询所有通讯录的同学信息。
- 输入姓名，根据姓名查询指定同学信息。如果该姓名不存在，输出提示信息。
- 添加同学，姓名重复的不能添加。
- 根据学员姓名删除学员。
- 按姓名排序查询学员。

分析：

- 使用HashMap<K,V>存储同学信息，使用同学姓名做key，同学对象做value。
- 同学对象包含的属性有：姓名、年龄、住址、爱好等。

### 2.2 比较器中使用泛型

#### 2.2.1 举例

```java
package com.atguigu.generic;

public class Circle{
    private double radius;

    public Circle(double radius) {
        super();
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle [radius=" + radius + "]";
    }

}
```

使用泛型之前：

```java
package com.atguigu.generic;

import java.util.Comparator;

class CircleComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        //强制类型转换
        Circle c1 = (Circle) o1;
        Circle c2 = (Circle) o2;
        return Double.compare(c1.getRadius(), c2.getRadius());
    }
}
//测试：
public class TestNoGeneric {
    public static void main(String[] args) {
        CircleComparator com = new CircleComparator();
        System.out.println(com.compare(new Circle(1), new Circle(2)));

        System.out.println(com.compare("圆1", "圆2"));//运行时异常：ClassCastException
    }
}
```

使用泛型之后：

```java
package com.atguigu.generic;

import java.util.Comparator;

class CircleComparator1 implements Comparator<Circle> {

    @Override
    public int compare(Circle o1, Circle o2) {
        //不再需要强制类型转换，代码更简洁
        return Double.compare(o1.getRadius(), o2.getRadius());
    }
}

//测试类
public class TestHasGeneric {
    public static void main(String[] args) {
        CircleComparator1 com = new CircleComparator1();
        System.out.println(com.compare(new Circle(1), new Circle(2)));

        //System.out.println(com.compare("圆1", "圆2"));
        //编译错误，因为"圆1", "圆2"不是Circle类型，是String类型，编译器提前报错，
        //而不是冒着风险在运行时再报错。
    }
}
```

#### 2.2.2 练习

（1）声明矩形类Rectangle，包含属性长和宽，属性私有化，提供有参构造、get/set方法、重写toString方法，提供求面积和周长的方法。

（2）矩形类Rectangle实现java.lang.Comparable<T>接口，并指定泛型为<Rectangle>，重写int compareTo(T t)方法，按照矩形面积比较大小，面积相等的，按照周长比较大小。

（3）在测试类中，创建Rectangle数组，并创建5个矩形对象

（4）调用Arrays的sort方法，给矩形数组排序，并显示排序前后的结果。

```java
package com.atguigu.genericclass.use;

public class Rectangle implements Comparable<Rectangle>{
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
	//获取面积
    public double area(){
        return length * width;
    }
    //获取周长
    public double perimeter(){
        return 2 * (length + width);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "length=" + length +
                ", width=" + width +
                ",area =" + area() +
                ",perimeter = " + perimeter() +
                '}';
    }

    @Override
    public int compareTo(Rectangle o) {
        int compare = Double.compare(area(), o.area());
        return compare != 0 ? compare : Double.compare(perimeter(),o.perimeter());
    }
}

```

```java
package com.atguigu.genericclass.use;

import java.util.Arrays;

public class TestRectangle {
    public static void main(String[] args) {
        Rectangle[] arr = new Rectangle[4];
        arr[0] = new Rectangle(6,2);
        arr[1] = new Rectangle(4,3);
        arr[2] = new Rectangle(12,1);
        arr[3] = new Rectangle(5,4);

        System.out.println("排序之前：");
        for (Rectangle rectangle : arr) {
            System.out.println(rectangle);
        }

        Arrays.sort(arr);

        System.out.println("排序之后：");
        for (Rectangle rectangle : arr) {
            System.out.println(rectangle);
        }
    }
}

```

### 2.3 相关使用说明

- 在创建集合对象的时候，可以指明泛型的类型。

  具体格式为：List<Integer> list = new ArrayList<Integer>();

- JDK7.0时，有新特性，可以简写为：

  List<Integer> list = new ArrayList<>(); //类型推断

- 泛型，也称为泛型参数，即参数的类型，只能使用引用数据类型进行赋值。（不能使用基本数据类型，可以使用包装类替换）

- 集合声明时，声明泛型参数。在使用集合时，可以具体指明泛型的类型。一旦指明，类或接口内部，凡是使用泛型参数的位置，都指定为具体的参数类型。如果没有指明的话，看做是Object类型。

## 3. 自定义泛型结构

### 3.1 泛型的基础说明

**1、<类型>这种语法形式就叫泛型。**

- <类型>的形式我们称为类型参数，这里的"类型"习惯上使用T表示，是Type的缩写。即：<T>。
- <T>：代表未知的数据类型，我们可以指定为<String>，<Integer>，<Circle>等。
  - 类比方法的参数的概念，我们把<T>，称为类型形参，将<Circle>称为类型实参，有助于我们理解泛型

- 这里的T，可以替换成K，V等任意字母。

**2、在哪里可以声明类型变量\<T>**

- 声明类或接口时，在类名或接口名后面声明泛型类型，我们把这样的类或接口称为`泛型类`或`泛型接口`。

```java
【修饰符】 class 类名<类型变量列表> 【extends 父类】 【implements 接口们】{
    
}
【修饰符】 interface 接口名<类型变量列表> 【implements 接口们】{
    
}

//例如：
public class ArrayList<E>    
public interface Map<K,V>{
    ....
}    
```

- 声明方法时，在【修饰符】与返回值类型之间声明类型变量，我们把声明了类型变量的方法，称为泛型方法。

```java
[修饰符] <类型变量列表> 返回值类型 方法名([形参列表])[throws 异常列表]{
    //...
}

//例如：java.util.Arrays类中的
public static <T> List<T> asList(T... a){
    ....
}
```

### 3.2 自定义泛型类或泛型接口

当我们在类或接口中定义某个成员时，该成员的相关类型是不确定的，而这个类型需要在使用这个类或接口时才可以确定，那么我们可以使用泛型类、泛型接口。

#### 3.2.1 说明

① 我们在声明完自定义泛型类以后，可以在类的内部（比如：属性、方法、构造器中）使用类的泛型。

② 我们在创建自定义泛型类的对象时，可以指明泛型参数类型。一旦指明，内部凡是使用类的泛型参数的位置，都具体化为指定的类的泛型类型。

③ 如果在创建自定义泛型类的对象时，没有指明泛型参数类型，那么泛型将被擦除，泛型对应的类型均按照Object处理，但不等价于Object。

- 经验：泛型要使用一路都用。要不用，一路都不要用。

④<font color='red'> 泛型的指定中必须使用引用数据类型。不能使用基本数据类型，此时只能使用包装类替换。</font>

⑤<font color='red'> 除创建泛型类对象外，子类继承泛型类时、实现类实现泛型接口时，也可以确定泛型结构中的泛型参数。</font>

如果我们在给泛型类提供子类时，子类也不确定泛型的类型，则可以继续使用泛型参数。

<font color='red'>我们还可以在现有的父类的泛型参数的基础上，新增泛型参数。</font>

#### 3.2.2 注意

> ① 泛型类可能有多个参数，此时应将多个参数一起放在尖括号内。比如：<E1,E2,E3>
>
> ② JDK7.0 开始，泛型的简化操作：ArrayList<Fruit> flist = new ArrayList<>();
>
> ③<font color='red'> 如果泛型结构是一个接口或抽象类，则不可创建泛型类的对象。</font>
>
> ④ 不能使用new E[]。但是可以：E[] elements = (E[])new Object[capacity];
>
> ​        参考：ArrayList源码中声明：Object[] elementData，而非泛型参数类型数组。
>
> ⑤ <font color='cornflowerblue'>在类/接口上声明的泛型，在本类或本接口中即代表某种类型，但不可以在静态方法中使用类的泛型。</font>
>
> ⑥ <font color='red'>异常类不能是带泛型的。</font>



#### 3.2.2 举例

举例1：

```java
class Person<T> {
    // 使用T类型定义变量
    private T info;
    // 使用T类型定义一般方法
    public T getInfo() {
        return info;
    }
    public void setInfo(T info) {
        this.info = info;
    }
    // 使用T类型定义构造器
    public Person() {
    }
    public Person(T info) {
        this.info = info;
    }
    // static的方法中不能声明泛型
    //public static void show(T t) {
    //
    //}
    // 不能在try-catch中使用泛型定义
    //public void test() {
        //try {
        //
        //} catch (MyException<T> ex) {
        //
        //}
    //}
}

```

举例2：

```java
class Father<T1, T2> {
}
// 子类不保留父类的泛型
// 1)没有类型 擦除
class Son1 extends Father {// 等价于class Son extends Father<Object,Object>{
}
// 2)具体类型
class Son2 extends Father<Integer, String> {
}
// 子类保留父类的泛型
// 1)全部保留
class Son3<T1, T2> extends Father<T1, T2> {
}
// 2)部分保留
class Son4<T2> extends Father<Integer, T2> {
}

```

举例3：

```java
class Father<T1, T2> {
}
// 子类不保留父类的泛型
// 1)没有类型 擦除
class Son<A, B> extends Father{//等价于class Son extends Father<Object,Object>{
}
// 2)具体类型
class Son2<A, B> extends Father<Integer, String> {
}
// 子类保留父类的泛型
// 1)全部保留
class Son3<T1, T2, A, B> extends Father<T1, T2> {
}
// 2)部分保留
class Son4<T2, A, B> extends Father<Integer, T2> {
}

```

#### 3.2.3 练习

**练习1：**

声明一个学生类，该学生包含姓名、成绩，而此时学生的成绩类型不确定，为什么呢，因为，语文老师希望成绩是“优秀”、“良好”、“及格”、“不及格”，数学老师希望成绩是89.5, 65.0，英语老师希望成绩是'A','B','C','D','E'。那么我们在设计这个学生类时，就可以使用泛型。

```java
package com.atguigu.genericclass.define;

class Student<T>{
    private String name;
    private T score;

    public Student() {
        super();
    }
    public Student(String name, T score) {
        super();
        this.name = name;
        this.score = score;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public T getScore() {
        return score;
    }
    public void setScore(T score) {
        this.score = score;
    }
    @Override
    public String toString() {
        return "姓名：" + name + ", 成绩：" + score;
    }
}

public class TestStudent {
    public static void main(String[] args) {
        //语文老师使用时：
        Student<String> stu1 = new Student<String>("张三", "良好");

        //数学老师使用时：
        //Student<double> stu2 = new Student<double>("张三", 90.5);//错误，必须是引用数据类型
        Student<Double> stu2 = new Student<Double>("张三", 90.5);

        //英语老师使用时：
        Student<Character> stu3 = new Student<Character>("张三", 'C');

        //错误的指定
        //Student<Object> stu = new Student<String>();//错误的
    }
}

```

**练习2：**

```
定义个泛型类 DAO<T>，在其中定义一个Map 成员变量，Map 的键为 String 类型，值为 T 类型。

分别创建以下方法：
public void save(String id,T entity)： 保存 T 类型的对象到 Map 成员变量中
public T get(String id)：从 map 中获取 id 对应的对象
public void update(String id,T entity)：替换 map 中key为id的内容,改为 entity 对象
public List<T> list()：返回 map 中存放的所有 T 对象
public void delete(String id)：删除指定 id 对象

定义一个 User 类：
该类包含：private成员变量（int类型） id，age；（String 类型）name。

定义一个测试类：
创建 DAO 类的对象， 分别调用其 save、get、update、list、delete 方法来操作 User 对象，
使用 Junit 单元测试类进行测试。
```

代码实现：

```java
/**
 * @author 尚硅谷-宋红康
 * @create 8:45
 */
public class DAO<T> {
    private Map<String,T> map ;

    {
        map = new HashMap<String,T>();
    }

    //保存 T 类型的对象到 Map 成员变量中
    public void save(String id,T entity){
        if(!map.containsKey(id)){
            map.put(id,entity);
        }

    }
    //从 map 中获取 id 对应的对象
    public T get(String id){
        return map.get(id);
    }
    //替换 map 中key为id的内容,改为 entity 对象
    public void update(String id,T entity){
        if(map.containsKey(id)){
            map.put(id,entity);
        }
    }
    //返回 map 中存放的所有 T 对象
    public List<T> list(){
        //错误的：
//        Collection<T> values = map.values();
//        System.out.println(values.getClass());
//        return (List<T>) values;
        //正确的方式1：
//        ArrayList<T> list = new ArrayList<>();
//        Collection<T> values = map.values();
//        list.addAll(values);
//        return list;
        //正确的方式2：
        Collection<T> values = map.values();
        ArrayList<T> list = new ArrayList<>(values);
        return list;
    }
    //删除指定 id 对象
    public void delete(String id){
        map.remove(id);
    }
}
```

```java
package com.atguigu02.selfdefine.exer1;

import java.util.Objects;

/**
 * 定义一个 User 类：
 * 该类包含：private成员变量（int类型） id，age；（String 类型）name。
 *
 * @author 尚硅谷-宋红康
 * @create 9:02
 */
public class User {
    private int id;
    private int age;
    private String name;

    public User() {
    }

    public User(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, name);
    }
}
```

```java
package com.atguigu02.selfdefine.exer1;

import java.util.List;

/**
 * @author 尚硅谷-宋红康
 * @create 9:04
 */
public class DAOTest {
    public static void main(String[] args) {
        DAO<User> dao = new DAO<>();

        dao.save("1001",new User(1,34,"曹操"));
        dao.save("1002",new User(2,33,"刘备"));
        dao.save("1003",new User(3,24,"孙权"));

        dao.update("1002",new User(2,23,"刘禅"));

        dao.delete("1003");

        List<User> list = dao.list();
        for(User u : list){
            System.out.println(u);
        }
    }
}
```

### 3.3 自定义泛型方法

==如果我们定义类、接口时没有使用<泛型参数>，但是某个方法形参类型不确定时，这个方法可以单独定义<泛型参数>。==

#### 3.3.1 说明

- 泛型方法的格式：

```java
[访问权限]  <泛型>  返回值类型  方法名([泛型标识 参数名称])  [抛出的异常]{
    
}
//如下：它接受 E 类型的对象并返回 E 类型的相同对象。该方法可以处理传入的任何类型的对象，只要对象的类型与泛型类型 E 相同即可。E是返回值类型，使用<E>标识的原因是因为不让编译器认为E为一个类，而是一个参数为E。
    public <E> E get(E e) {
        return e;
    }
```

- 方法，也可以被泛型化，与其所在的类是否是泛型类没有关系。
- 泛型方法中的泛型参数在方法被调用时确定。
- <font color='red'>泛型方法可以根据需要，声明为static的。</font>

#### 3.3.2 举例

```java
import java.util.ArrayList;

public class Generics {

    public <T> ArrayList<T> getList(T[] t){
        ArrayList<T> arrayList = new ArrayList<>();
        for (T t1 : t) {
            arrayList.add(t1);
        }
        return arrayList;
    }

    public static void main(String[] args) {
        Generics generics = new Generics();
        String str[] = {"zhangsan","lisi","wangwu"};
        ArrayList<String> list = generics.getList(str);
        System.out.println(list);
    }
} 
```



#### 3.3.3 练习

练习1: 泛型方法

编写一个泛型方法，实现任意引用类型数组指定位置元素交换。

public static <E> void method1( E[] e,int a,int b)

```java
/**
 * @author 尚硅谷-宋红康
 * @create 9:11
 */
public class Exer01 {

    //编写一个泛型方法，实现任意引用类型数组指定位置元素交换。
    public static <E> void method( E[] arr,int a,int b){
        E temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    @Test
    public void testMethod(){
        Integer[] arr = new Integer[]{10,20,30,40};
        method(arr,2,3);

        for(Integer i : arr){
            System.out.println(i);
        }
    }
}
```

练习2: 泛型方法

编写一个泛型方法，接收一个任意引用类型的数组，并反转数组中的所有元素

public static <E> void method2( E[] e)

```java
/**
 * @author 尚硅谷-宋红康
 * @create 9:11
 */
public class Exer01 {

	//编写一个泛型方法，接收一个任意引用类型的数组，并反转数组中的所有元素
    public static <E> void method1( E[] arr){
        for(int min = 0,max = arr.length - 1;min < max; min++,max--){
            E temp = arr[min];
            arr[min] = arr[max];
            arr[max] = temp;
        }
    }

    @Test
    public void testMethod1(){
        Integer[] arr = new Integer[]{10,20,30,40};
        method1(arr);
        for(Integer i : arr){
            System.out.println(i);
        }
    }
}
```

## 4. 泛型在继承上的体现

有如下问题：

> ​	1. 	**类SuperA是类A的父类，则G<SuperA>与G<A>的关系。**
>
> ​		G<SuperA>与G<A>没有关系，是并列的，如下：ArrayList<Project>和ArrayList<String>没有关系
>
>  2. **类SuperA是类A的父类或接口，则SuperA<G>与A<G>的关系。**
>
>     SuperA<G>与A<G>是有继承关系的，如：List<String>和ArrayList<String>
>
>  3. 综上：也就是说继承必须要泛型类型一致





<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220411003422259.png" alt="image-20220411003422259" style="zoom:67%;" />

```java
public void testGenericAndSubClass() {
    Person[] persons = null;
    Man[] mans = null;
    //Person[] 是 Man[] 的父类
    persons = mans;

    Person p = mans[0];

    // 在泛型的集合上
    List<Person> personList = null;
    List<Man> manList = null;
    //personList = manList;(报错)
}
```

**思考：对比如下两段代码有何不同：**

片段1：

```java
public void printCollection(Collection c) {
    Iterator i = c.iterator();
    for (int k = 0; k < c.size(); k++) {
        System.out.println(i.next());
    }
}
```

片段2：

```java
public void printCollection(Collection<Object> c) {
    for (Object e : c) {
        System.out.println(e);
    }
}
```

## 5. 通配符的使用

当我们声明一个变量/形参时，这个变量/形参的类型是一个泛型类或泛型接口，例如：Comparator<T>类型，但是我们仍然无法确定这个泛型类或泛型接口的类型变量<T>的具体类型，此时我们考虑使用类型通配符 ? 。

### 5.1 通配符的理解

使用类型通配符：？ 

比如：`List<?>`，`Map<?,?>`

​            <font color='red'>`List<?>`是`List<String>`、`List<Object>`等各种泛型List的父类。</font>

### 5.2 通配符的读与写

**写操作：**

将任意元素加入到其中不是类型安全的：

```java
Collection<?> c = new ArrayList<String>();

c.add(new Object()); // 编译时错误
```

因为我们不知道c的元素类型，我们不能向其中添加对象。add方法有类型参数E作为集合的元素类型。我们传给add的任何参数都必须是一个未知类型的子类。因为我们不知道那是什么类型，所以我们无法传任何东西进去。

唯一可以插入的元素是null，因为它是所有引用类型的默认值。

**读操作：**

另一方面，读取List<?>的对象list中的元素时，永远是安全的，因为不管 list 的真实类型是什么，它包含的都是Object。

举例1：

```java
public class TestWildcard {
    public static void m4(Collection<?> coll){
        for (Object o : coll) {
            System.out.println(o);
        }
    }
}
```

举例2：

```java
public static void main(String[] args) {
    List<?> list = null;
    list = new ArrayList<String>();
    list = new ArrayList<Double>();
    // list.add(3);//编译不通过
    list.add(null);

    List<String> l1 = new ArrayList<String>();
    List<Integer> l2 = new ArrayList<Integer>();
    l1.add("尚硅谷");
    l2.add(15);
    read(l1);
    read(l2);
}

public static void read(List<?> list) {
    for (Object o : list) {
        System.out.println(o);
    }
}

```

### 5.3 使用注意点

注意点1：编译错误：不能用在泛型方法声明上，返回值类型前面<>不能使用?

```java
public static <?> void test(ArrayList<?> list){
}
```

注意点2：编译错误：不能用在泛型类的声明上

```java
class GenericTypeClass<?>{
}
```

注意点3：编译错误：不能用在创建对象上，右边属于创建集合对象

```java
ArrayList<?> list2 = new ArrayList<?>();
```

### 5.4 有限制的通配符

- `<?>`

  - 允许所有泛型的引用调用

- 通配符指定上限：`<? extends 类/接口 >`

  - 使用时指定的类型必须是继承某个类，或者实现某个接口，即`<= `

- 通配符指定下限：`<? super 类/接口 >`

  - 使用时指定的类型必须是操作的类或接口，或者是操作的类的父类或接口的父接口，即	`>=`

- 说明：

  ```java
  <? extends Number>     //(无穷小 , Number]
  //只允许泛型为Number及Number子类的引用调用
  
  <? super Number>      //[Number , 无穷大)
  //只允许泛型为Number及Number父类的引用调用
  
  <? extends Comparable>
  //只允许泛型为实现Comparable接口的实现类的引用调用
  ```

- 举例1

  ```java
  class Creature{}
  class Person extends Creature{}
  class Man extends Person{}
  
  class PersonTest {
      public static <T extends Person> void test(T t){
          System.out.println(t);
      }
  
      public static void main(String[] args) {
          test(new Person());
          test(new Man());
          //The method test(T) in the type PersonTest is not 
          //applicable for the arguments (Creature)
          test(new Creature());
      }
  }
  
  ```

- 举例2：

  ```java
  public static void main(String[] args) {
      Collection<Integer> list1 = new ArrayList<Integer>();
      Collection<String> list2 = new ArrayList<String>();
      Collection<Number> list3 = new ArrayList<Number>();
      Collection<Object> list4 = new ArrayList<Object>();
      
      getElement1(list1);
      getElement1(list2);//报错
      getElement1(list3);
      getElement1(list4);//报错
    
      getElement2(list1);//报错
      getElement2(list2);//报错
      getElement2(list3);
      getElement2(list4);
    
  }
  // 泛型的上限：此时的泛型?，必须是Number类型或者Number类型的子类
  public static void getElement1(Collection<? extends Number> coll){}
  // 泛型的下限：此时的泛型?，必须是Number类型或者Number类型的父类
  public static void getElement2(Collection<? super Number> coll){}
  ```

- 举例3：

  ```java
  public static void printCollection1(Collection<? extends Person> coll) {
      //Iterator只能用Iterator<?>或Iterator<? extends Person>.why?
      Iterator<?> iterator = coll.iterator();
      while (iterator.hasNext()) {
          Person per = iterator.next();
          System.out.println(per);
      }
  }
  
  public static void printCollection2(Collection<? super Person> coll) {
      //Iterator只能用Iterator<?>或Iterator<? super Person>.why?
      Iterator<?> iterator = coll.iterator();
      while (iterator.hasNext()) {
          Object obj = iterator.next();
          System.out.println(obj);
      }
  }
  
  ```

举例4:

```java
@Test
public void test1(){
    //List<Object> list1 = null;
    List<Person> list2 = new ArrayList<Person>();
    //List<Student> list3 = null;

    List<? extends Person> list4 = null;

    list2.add(new Person());
    list4 = list2;

    //读取：可以读
    Person p1 = list4.get(0);

    //写入：除了null之外，不能写入
    list4.add(null);
    //        list4.add(new Person());
    //        list4.add(new Student());

}

@Test
public void test2(){
    //List<Object> list1 = null;
    List<Person> list2 = new ArrayList<Person>();
    //List<Student> list3 = null;

    List<? super Person> list5 = null;
    list2.add(new Person());

    list5 = list2;

    //读取：可以实现
    Object obj = list5.get(0);

    //写入:可以写入Person及Person子类的对象
    list5.add(new Person());
    list5.add(new Student());

}
```

### 5.5 泛型应用举例

**举例1：泛型嵌套**

```java
public static void main(String[] args) {
    HashMap<String, ArrayList<Citizen>> map = new HashMap<String, ArrayList<Citizen>>();
    ArrayList<Citizen> list = new ArrayList<Citizen>();
    list.add(new Citizen("赵又廷"));
    list.add(new Citizen("高圆圆"));
    list.add(new Citizen("瑞亚"));
    map.put("赵又廷", list);

    Set<Entry<String, ArrayList<Citizen>>> entrySet = map.entrySet();
    Iterator<Entry<String, ArrayList<Citizen>>> iterator = entrySet.iterator();
    while (iterator.hasNext()) {
        Entry<String, ArrayList<Citizen>> entry = iterator.next();
        String key = entry.getKey();
        ArrayList<Citizen> value = entry.getValue();
        System.out.println("户主：" + key);
        System.out.println("家庭成员：" + value);
    }
}

```

**举例2：个人信息设计**

用户在设计类的时候往往会使用类的关联关系，例如，一个人中可以定义一个信息的属性，但是一个人可能有各种各样的信息（如联系方式、基本信息等），所以此信息属性的类型就可以通过泛型进行声明，然后只要设计相应的信息类即可。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220411004301224.png" alt="image-20220411004301224" style="zoom:67%;" />

```java
interface Info{		// 只有此接口的子类才是表示人的信息
}
class Contact implements Info{	// 表示联系方式
	private String address ;	// 联系地址
	private String telephone ;	// 联系方式
	private String zipcode ;	// 邮政编码
	public Contact(String address,String telephone,String zipcode){
		this.address = address;
		this.telephone = telephone;
		this.zipcode = zipcode;
	}
	public void setAddress(String address){
		this.address = address ;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone ;
	}
	public void setZipcode(String zipcode){
		this.zipcode = zipcode;
	}
	public String getAddress(){
		return this.address ;
	}
	public String getTelephone(){
		return this.telephone ;
	}
	public String getZipcode(){
		return this.zipcode;
	}
	@Override
	public String toString() {
		return "Contact [address=" + address + ", telephone=" + telephone
				+ ", zipcode=" + zipcode + "]";
	}
}
class Introduction implements Info{
	private String name ;		// 姓名
	private String sex ;		// 性别
	private int age ;			// 年龄
	public Introduction(String name,String sex,int age){
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
	public void setName(String name){
		this.name = name ;
	}
	public void setSex(String sex){
		this.sex = sex ;
	}
	public void setAge(int age){
		this.age = age ;
	}
	public String getName(){
		return this.name ;
	}
	public String getSex(){
		return this.sex ;
	}
	public int getAge(){
		return this.age ;
	}
	@Override
	public String toString() {
		return "Introduction [name=" + name + ", sex=" + sex + ", age=" + age
				+ "]";
	}
}
class Person<T extends Info>{
	private T info ;
	public Person(T info){		// 通过构造器设置信息属性内容
		this.info = info;
	}
	public void setInfo(T info){
		this.info = info ;
	}
	public T getInfo(){
		return info ;
	}
	@Override
	public String toString() {
		return "Person [info=" + info + "]";
	}
	
}
public class GenericPerson{
	public static void main(String args[]){
		Person<Contact> per = null ;		// 声明Person对象
		per = new Person<Contact>(new Contact("北京市","01088888888","102206")) ;
		System.out.println(per);
		
		Person<Introduction> per2 = null ;		// 声明Person对象
		per2 = new Person<Introduction>(new Introduction("李雷","男",24));
		System.out.println(per2) ;
	}
}
```



## 6.随堂复习

### 1. 泛型的理解

- <E> ，泛型参数，使用引用数据类型来赋值。

### 2. 泛型在集合、比较器中的使用（重点）

- 集合：ArrayList、HashMap、Iterator
- 比较器：Comparable、Comparator

### 3. 自定义泛型类/泛型接口、泛型方法（熟悉）

- class Order<T>{ }
- public <E> 返回值类型 方法名(形参列表){}
- 具体的细节，见IDEA中的笔记。

### 4. 泛型在继承上的体现

```
1. 类SuperA是类A的父类，则G<SuperA> 与 G<A>的关系：G<SuperA> 和 G<A>是并列的两个类，没有任何子父类的关系。

比如：ArrayList<Object> 、ArrayList<String>没有关系

2. 类SuperA是类A的父类或接口，SuperA<G> 与 A<G>的关系：SuperA<G> 与A<G> 有继承或实现的关系。
即A<G>的实例可以赋值给SuperA<G>类型的引用（或变量）

比如：List<String> 与 ArrayList<String>
```

### 5. 通配符的使用

- ? 的使用 （重点）
  - 以集合为例：可以读取数据、不能写入数据（例外：null）
- ? extends A
  - 以集合为例：可以读取数据、不能写入数据（例外：null）
- ? super A
  - 以集合为例：可以读取数据、可以写入A类型或A类型子类的数据（例外：null）



## 7.企业真题

#### 1. Java 的泛型是什么？有什么好处和优点？JDK 不同版本的泛型有什么区别？（软*动力）

泛型，是程序中出现的不确定的类型。

以集合来举例：把一个集合中的内容限制为一个特定的数据类型，这就是generic背后的核心思想。



jdk7.0新特性：

```java
ArrayList<String> list = new ArrayList<>(); //类型推断
```

后续版本的新特性：

```java
Comparator<Employee> comparator = new Comparator<>(){} //类型推断
```



#### 2. 说说你对泛型的了解（*软国际）

略



# 第14章_File类与IO流

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC15%E7%AB%A0.png" alt="第3阶段：Java高级应用-第15章" style="zoom:67%;" />

***

## 1. java.io.File类的使用

### 1.1 概述

-  File类及本章下的各种流，都定义在java.io包下。
-  一个File对象代表硬盘或网络中可能存在的一个文件或者文件目录（俗称文件夹），与平台无关。（体会万事万物皆对象）
-  File 能新建、删除、重命名文件和目录，但 File 不能访问文件内容本身。如果需要访问文件内容本身，则需要使用输入/输出流。
   - File对象可以作为参数传递给流的构造器。
-  想要在Java程序中表示一个真实存在的文件或目录，那么必须有一个File对象，但是Java程序中的一个File对象，可能没有一个真实存在的文件或目录。


### 1.2 构造器

* `public File(String pathname) ` ：以pathname为路径创建File对象，可以是绝对路径或者相对路径，如果pathname是相对路径，则默认的当前路径在系统属性user.dir中存储。
* `public File(String parent, String child) ` ：以parent为父路径，child为子路径创建File对象。
* `public File(File parent, String child)` ：根据一个父File对象和子文件路径创建File对象

关于路径：

* **绝对路径：**从盘符开始的路径，这是一个完整的路径。
* **相对路径：**相对于`项目目录`的路径，这是一个便捷的路径，开发中经常使用。
  * IDEA中，main中的文件的相对路径，是相对于"`当前工程`"
  * IDEA中，单元测试方法中的文件的相对路径，是相对于"`当前module`"

举例：

```java
package com.atguigu.file;

import java.io.File;

public class FileObjectTest {
    public static void main(String[] args) {
        // 文件路径名
        String pathname = "D:\\aaa.txt";
        File file1 = new File(pathname);

        // 文件路径名
        String pathname2 = "D:\\aaa\\bbb.txt";
        File file2 = new File(pathname2);

        // 通过父路径和子路径字符串
        String parent = "d:\\aaa";
        String child = "bbb.txt";
        File file3 = new File(parent, child);

        // 通过父级File对象和子路径字符串
        File parentDir = new File("d:\\aaa");
        String childFile = "bbb.txt";
        File file4 = new File(parentDir, childFile);
    }
    
    @Test
    public void test01() throws IOException{
        File f1 = new File("d:\\atguigu\\javase\\HelloIO.java"); //绝对路径
        System.out.println("文件/目录的名称：" + f1.getName());
        System.out.println("文件/目录的构造路径名：" + f1.getPath());
        System.out.println("文件/目录的绝对路径名：" + f1.getAbsolutePath());
        System.out.println("文件/目录的父目录名：" + f1.getParent());
    }
    @Test
    public void test02()throws IOException{
        File f2 = new File("/HelloIO.java");//绝对路径，从根路径开始
        System.out.println("文件/目录的名称：" + f2.getName());
        System.out.println("文件/目录的构造路径名：" + f2.getPath());
        System.out.println("文件/目录的绝对路径名：" + f2.getAbsolutePath());
        System.out.println("文件/目录的父目录名：" + f2.getParent());
    }

    @Test
    public void test03() throws IOException {
        File f3 = new File("HelloIO.java");//相对路径
        System.out.println("user.dir =" + System.getProperty("user.dir"));
        System.out.println("文件/目录的名称：" + f3.getName());
        System.out.println("文件/目录的构造路径名：" + f3.getPath());
        System.out.println("文件/目录的绝对路径名：" + f3.getAbsolutePath());
        System.out.println("文件/目录的父目录名：" + f3.getParent());
    }
    @Test
    public void test04() throws IOException{
        File f5 = new File("HelloIO.java");//相对路径
        System.out.println("user.dir =" + System.getProperty("user.dir"));
        System.out.println("文件/目录的名称：" + f5.getName());
        System.out.println("文件/目录的构造路径名：" + f5.getPath());
        System.out.println("文件/目录的绝对路径名：" + f5.getAbsolutePath());
        System.out.println("文件/目录的父目录名：" + f5.getParent());
    }
}
```

> 注意：
>
> 1. 无论该路径下是否存在文件或者目录，都不影响File对象的创建。
>
> 2. window的路径分隔符使用“\”，而Java程序中的“\”表示转义字符，所以在Windows中表示路径，需要用“\\”。或者直接使用“/”也可以，Java程序支持将“/”当成平台无关的`路径分隔符`。或者直接使用File.separator常量值表示。比如：
>
>    File file2 = new File("d:" + File.separator + "atguigu" + File.separator + "info.txt");
>
> 3. 当构造路径是绝对路径时，那么getPath和getAbsolutePath结果一样
>
>    当构造路径是相对路径时，那么getAbsolutePath的路径 = user.dir的路径 + 构造路径

### 1.3 常用方法

#### 1、获取文件和目录基本信息

* `public String getName() `：获取名称
* `public String getPath()` ：获取路径
* `public String getAbsolutePath()`：获取绝对路径
* `public File getAbsoluteFile()`：获取绝对路径表示的文件
* `public String getParent()`：获取上层文件目录路径。若无，返回null
* `public long length() `：获取文件长度（即：字节数）。不能获取目录的长度。
* `public long lastModified()` ：获取最后一次的修改时间，毫秒值

> 如果File对象代表的文件或目录存在，则File对象实例初始化时，就会用硬盘中对应文件或目录的属性信息（例如，时间、类型等）为File对象的属性赋值，否则除了路径和名称，File对象的其他属性将会保留默认值。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412215446368.png" alt="image-20220412215446368" style="zoom:80%;" />

举例：

```java
package com.atguigu.file;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FileInfoMethod {
    public static void main(String[] args) {
        File f = new File("d:/aaa/bbb.txt");
        System.out.println("文件构造路径:"+f.getPath());
        System.out.println("文件名称:"+f.getName());
        System.out.println("文件长度:"+f.length()+"字节");
        System.out.println("文件最后修改时间：" + LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()),ZoneId.of("Asia/Shanghai")));

        File f2 = new File("d:/aaa");
        System.out.println("目录构造路径:"+f2.getPath());
        System.out.println("目录名称:"+f2.getName());
        System.out.println("目录长度:"+f2.length()+"字节");
        System.out.println("文件最后修改时间：" + LocalDateTime.ofInstant(Instant.ofEpochMilli(f.lastModified()),ZoneId.of("Asia/Shanghai")));
    }
}
```

```java
输出结果：
文件构造路径:d:\aaa\bbb.java
文件名称:bbb.java
文件长度:636字节
文件最后修改时间：2022-07-23T22:01:32.065

目录构造路径:d:\aaa
目录名称:aaa
目录长度:4096字节
文件最后修改时间：2022-07-23T22:01:32.065
```

#### 2、列出目录的下一级

* public String[] list() ：<font color='red'>返回一个String数组</font>，表示该File目录中的所有子文件或目录。
* public File[] listFiles() ：<font color='red'>返回一个File数组</font>，表示该File目录中的所有的子文件或目录。 

```java
package com.atguigu.file;

import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class DirListFiles {
    @Test
    public void test01() {
        File dir = new File("d:/atguigu");
        String[] subs = dir.list();
        for (String sub : subs) {
            System.out.println(sub);
        }
    }

}
```

#### 3、File类的重命名功能

- public boolean renameTo(File dest):把文件重命名为指定的文件路径。

#### 4、判断功能的方法

- `public boolean exists()` ：此File表示的文件或目录是否实际存在。
- `public boolean isDirectory()` ：此File表示的是否为目录。
- `public boolean isFile()` ：此File表示的是否为文件。
- `public boolean canRead() `：判断是否可读
- `public boolean canWrite()` ：判断是否可写
- `public boolean isHidden() `：判断是否隐藏


举例：

```java
package com.atguigu.file;

import java.io.File;

public class FileIs {
    public static void main(String[] args) {
        File f = new File("d:\\aaa\\bbb.java");
        File f2 = new File("d:\\aaa");
        // 判断是否存在
        System.out.println("d:\\aaa\\bbb.java 是否存在:"+f.exists());
        System.out.println("d:\\aaa 是否存在:"+f2.exists());
        // 判断是文件还是目录
        System.out.println("d:\\aaa 文件?:"+f2.isFile());
        System.out.println("d:\\aaa 目录?:"+f2.isDirectory());
    }
}
```

```
输出结果：
d:\aaa\bbb.java 是否存在:true
d:\aaa 是否存在:true
d:\aaa 文件?:false
d:\aaa 目录?:true
```

> 如果文件或目录不存在，那么exists()、isFile()和isDirectory()都是返回true

#### 5、创建、删除功能

- `public boolean createNewFile()` ：创建文件。若文件存在，则不创建，返回false。
- `public boolean mkdir()` ：创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，也不创建。
  - `public boolean mkdirs()` ：创建多层																																								文件目录。如果上层文件目录不存在，一并创建。

- `public boolean delete()` ：删除文件或者文件夹
  删除注意事项：① Java中的删除不走回收站。② 要删除一个文件目录，请注意该文件目录内不能包含文件或者文件目录。

举例：

```java
package com.atguigu.file;

import java.io.File;
import java.io.IOException;

public class FileCreateDelete {
    public static void main(String[] args) throws IOException {
        // 文件的创建
        File f = new File("aaa.txt");
        System.out.println("aaa.txt是否存在:"+f.exists()); 
        System.out.println("aaa.txt是否创建:"+f.createNewFile()); 
        System.out.println("aaa.txt是否存在:"+f.exists()); 

        // 目录的创建
        File f2= new File("newDir");
        System.out.println("newDir是否存在:"+f2.exists());
        System.out.println("newDir是否创建:"+f2.mkdir());
        System.out.println("newDir是否存在:"+f2.exists());

        // 创建一级目录
        File f3= new File("newDira\\newDirb");
        System.out.println("newDira\\newDirb创建：" + f3.mkdir());
        File f4= new File("newDir\\newDirb");
        System.out.println("newDir\\newDirb创建：" + f4.mkdir());
        // 创建多级目录
        File f5= new File("newDira\\newDirb");
        System.out.println("newDira\\newDirb创建：" + f5.mkdirs());

        // 文件的删除
        System.out.println("aaa.txt删除：" + f.delete());

        // 目录的删除
        System.out.println("newDir删除：" + f2.delete());
        System.out.println("newDir\\newDirb删除：" + f4.delete());
    }
}
```

```java
运行结果：
aaa.txt是否存在:false
aaa.txt是否创建:true
aaa.txt是否存在:true
newDir是否存在:false
newDir是否创建:true
newDir是否存在:true
newDira\newDirb创建：false
newDir\newDirb创建：true
newDira\newDirb创建：true
aaa.txt删除：true
newDir删除：false
newDir\newDirb删除：true
```

> API中说明：delete方法，如果此File表示目录，则目录必须为空才能删除。

### 1.4 练习

练习1：利用File构造器，new 一个文件目录file

​      1) 在其中创建多个文件和目录

​      2) 编写方法，实现删除file中指定文件的操作

练习2：判断指定目录下是否有后缀名为.jpg的文件。如果有，就输出该文件名称

```java
public class FindJPGFileTest {
	//方法1：
	@Test
	public void test1(){
		File srcFile = new File("d:\\code");
		
		String[] fileNames = srcFile.list();
		for(String fileName : fileNames){
			if(fileName.endsWith(".jpg")){
				System.out.println(fileName);
			}
		}
	}
    //方法2：
	@Test
	public void test2(){
		File srcFile = new File("d:\\code");
		
		File[] listFiles = srcFile.listFiles();
		for(File file : listFiles){
			if(file.getName().endsWith(".jpg")){
				System.out.println(file.getAbsolutePath());
			}
		}
	}
    //方法3：
	/*
	 * File类提供了两个文件过滤器方法
	 * public String[] list(FilenameFilter filter)
	 * public File[] listFiles(FileFilter filter)

	 */
	@Test
	public void test3(){
		File srcFile = new File("d:\\code");
		
		File[] subFiles = srcFile.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg");
			}
		});
		
		for(File file : subFiles){
			System.out.println(file.getAbsolutePath());
		}
	}
	
}

```

练习3：遍历指定目录所有文件名称，包括子文件目录中的文件。

  拓展1：并计算指定目录占用空间的大小

  拓展2：删除指定文件目录及其下的所有文件

```java
public class ListFilesTest {
	//练习3：(方式1)
    public static void printSubFile(File dir) {
        // 打印目录的子文件
        File[] subfiles = dir.listFiles();

        for (File f : subfiles) {
            if (f.isDirectory()) {// 文件目录
                printSubFile(f);
            } else {// 文件
                System.out.println(f.getAbsolutePath());
            }

        }
    }

    // //练习3：(方式2)
    public void listAllSubFiles(File file) {
        if (file.isFile()) {
            System.out.println(file);
        } else {
            File[] all = file.listFiles();
            // 如果all[i]是文件，直接打印
            // 如果all[i]是目录，接着再获取它的下一级
            for (File f : all) {
                listAllSubFiles(f);// 递归调用：自己调用自己就叫递归
            }
        }
    }
    @Test
    public void testListAllFiles(){
        // 1.创建目录对象
        File dir = new File("E:\\teach\\01_javaSE\\_尚硅谷Java编程语言\\3_软件");

        // 2.打印目录的子文件
        printSubFile(dir);
    }

    // 拓展1：求指定目录所在空间的大小
    public long getDirectorySize(File file) {
        // file是文件，那么直接返回file.length()
        // file是目录，把它的下一级的所有file大小加起来就是它的总大小
        long size = 0;
        if (file.isFile()) {
            size = file.length();
        } else {
            File[] all = file.listFiles();// 获取file的下一级
            // 累加all[i]的大小
            for (File f : all) {
                size += getDirectorySize(f);// f的大小;
            }
        }
        return size;
    }

    // 拓展2：删除指定的目录
    public void deleteDirectory(File file) {
        // 如果file是文件，直接delete
        // 如果file是目录，先把它的下一级干掉，然后删除自己
        if (file.isDirectory()) {
            File[] all = file.listFiles();
            // 循环删除的是file的下一级
            for (File f : all) {// f代表file的每一个下级
                deleteDirectory(f);
            }
        }
        // 删除自己
        file.delete();
    }

}
```

## 2. IO流原理及流的分类

![image-20220412224132724](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412224132724.png)

### 2.1 Java IO原理

- Java程序中，对于数据的输入/输出操作以“`流(stream)`” 的方式进行，可以看做是一种数据的流动。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503123117300.png" alt="image-20220503123117300" style="zoom: 80%;" />

- I/O流中的I/O是`Input/Output`的缩写， I/O技术是非常实用的技术，用于处理设备之间的数据传输。如读/写文件，网络通讯等。

  - `输入input`：读取外部数据（磁盘、光盘等存储设备的数据）到程序（内存）中。
  - `输出output`：将程序（内存）数据输出到磁盘、光盘等存储设备中。

![image-20220412224700133](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412224700133.png)

### 2.2 流的分类

`java.io`包下提供了各种“流”类和接口，用以获取不同种类的数据，并通过`标准的方法`输入或输出数据。

- 按数据的流向不同分为：**输入流**和**输出流**。

  - **输入流** ：把数据从`其他设备`上读取到`内存`中的流。 
    - 以InputStream、Reader结尾
  - **输出流** ：把数据从`内存` 中写出到`其他设备`上的流。
    - 以OutputStream、Writer结尾

- 按操作数据单位的不同分为：**字节流（8bit）**和**字符流（16bit）**。

  - **字节流** ：以字节为单位，读写数据的流。
    - 以InputStream、OutputStream结尾
  - **字符流** ：以字符为单位，读写数据的流。
    - 以Reader、Writer结尾

- 根据IO流的角色不同分为：**节点流**和**处理流**。

  - **节点流**：直接从数据源或目的地读写数据

    ![image-20220412230745170](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412230745170.png)

  - **处理流**：不直接连接到数据源或目的地，而是“连接”在已存在的流（节点流或处理流）之上，通过对数据的处理为程序提供更为强大的读写功能。

    ![image-20220412230751461](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412230751461.png)

小结：图解

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412225253349.png" alt="image-20220412225253349" style="zoom:67%;" />

### 2.3 流的API

- Java的IO流共涉及40多个类，实际上非常规则，都是从如下4个抽象基类派生的。

| （抽象基类） |   输入流    |    输出流    |
| :----------: | :---------: | :----------: |
|    字节流    | InputStream | OutputStream |
|    字符流    |   Reader    |    Writer    |

- 由这四个类派生出来的子类名称都是以其父类名作为子类名后缀。

![image-20220412230501953](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412230501953.png)



常用的输入输出流

| 抽象基类     | 4个节点流        | 4个缓冲流            |
| ------------ | ---------------- | -------------------- |
| Reader       | FileReader       | BufferedReader       |
| Writer       | FileWriter       | BufferedWriter       |
| InputStream  | FileInputStream  | BufferedInputStream  |
| OutputStream | FileOutputStream | BufferedOutputStream |



**常用的节点流：** 　

* 文件流： FileInputStream、FileOutputStrean、FileReader、FileWriter 
  * 字节/字符数组流： ByteArrayInput																															Stream、ByteArrayOutputStream、CharArrayReader、CharArrayWriter 
  * 对数组进行处理的节点流（对应的不再是文件，而是内存中的一个数组）。


**常用处理流：**

* 缓冲流：BufferedInputStream、BufferedOutputStream、BufferedReader、BufferedWriter
  * 作用：增加缓冲功能，避免频繁读写硬盘，进而提升读写效率。
* 转换流：InputStreamReader、OutputStreamReader
  * 作用：实现字节流和字符流之间的转换。
* 对象流：ObjectInputStream、ObjectOutputStream
  * 作用：提供直接读写Java对象功能

## 3. 节点流之一：FileReader\FileWriter

### 3.1 Reader与Writer

Java提供一些字符流类，以字符为单位读写数据，专门用于处理文本文件。不能操作图片，视频等非文本文件。

> 常见的文本文件有如下的格式：.txt、.java、.c、.cpp、.py等
>
> 注意：.doc、.xls、.ppt这些都不是文本文件。

#### 3.1.1 字符输入流：Reader

`java.io.Reader`抽象类是表示用于读取字符流的所有类的父类，可以读取字符信息到内存中。它定义了字符输入流的基本共性功能方法。

- `public int read()`： 从输入流读取一个字符。 虽然读取了一个字符，但是会自动提升为int类型。返回该字符的Unicode编码值。如果已经到达流末尾了，则返回-1。
- `public int read(char[] cbuf)`： 从输入流中读取一些字符，并将它们存储到字符数组 cbuf中 。每次最多读取cbuf.length个字符。返回实际读取的字符个数。如果已经到达流末尾，没有数据可读，则返回-1。 
- `public int read(char[] cbuf,int off,int len)`：从输入流中读取一些字符，并将它们存储到字符数组 cbuf中，从cbuf[off]开始的位置存储。每次最多读取len个字符。返回实际读取的字符个数。如果已经到达流末尾，没有数据可读，则返回-1。 
- `public void close()` ：关闭此流并释放与此流相关联的任何系统资源。    

> 注意：当完成流的操作时，必须调用close()方法，释放系统资源，否则会造成内存泄漏。

#### 3.1.2 字符输出流：Writer

`java.io.Writer `抽象类是表示用于写出字符流的所有类的超类，将指定的字符信息写出到目的地。它定义了字节输出流的基本共性功能方法。

- `public void write(int c)` ：写出单个字符。
- `public void write(char[] cbuf) `：写出字符数组。 
- `public void write(char[] cbuf, int off, int len) `：写出字符数组的某一部分。off：数组的开始索引；len：写出的字符个数。 
- `public void write(String str) `：写出字符串。 
- `public void write(String str, int off, int len)` ：写出字符串的某一部分。off：字符串的开始索引；len：写出的字符个数。
- `public void flush() `：刷新该流的缓冲。  
- `public void close()` ：关闭此流。

> 注意：当完成流的操作时，必须调用close()方法，释放系统资源，否则会造成内存泄漏。

### 3.2 FileReader 与 FileWriter

#### 3.2.1 FileReader

`java.io.FileReader `类用于读取字符文件，构造时使用系统默认的字符编码和默认字节缓冲区。

- `FileReader(File file)`： 创建一个新的 FileReader ，给定要读取的File对象。   
- `FileReader(String fileName)`： 创建一个新的 FileReader ，给定要读取的文件的名称。  

**举例：**读取hello.txt文件中的字符数据，并显示在控制台上

```java
/**
 * @author 尚硅谷-宋红康
 * @create 14:09
 */
public class FileReaderWriterTest {
    
    //实现方式1
    @Test
    public void test1() throws IOException {
        //1. 创建File类的对象，对应着物理磁盘上的某个文件
        File file = new File("hello.txt");
        //2. 创建FileReader流对象，将File类的对象作为参数传递到FileReader的构造器中
        FileReader fr = new FileReader(file);
        //3. 通过相关流的方法，读取文件中的数据
//        int data = fr.read(); //每调用一次读取一个字符
//        while (data != -1) {
//            System.out.print((char) data);
//            data = fr.read();
//        }
        int data;
        while ((data = fr.read()) != -1) {
            System.out.print((char) data);
        }

        //4. 关闭相关的流资源，避免出现内存泄漏
        fr.close();

    }

    //实现方式2：在方式1的基础上改进，使用try-catch-finally处理异常。保证流是可以关闭的
    @Test
    public void test2() {
        FileReader fr = null;
        try {
            //1. 创建File类的对象，对应着物理磁盘上的某个文件
            File file = new File("hello.txt");
            //2. 创建FileReader流对象，将File类的对象作为参数传递到FileReader的构造器中
            fr = new FileReader(file);
            //3. 通过相关流的方法，读取文件中的数据
            /*
             * read():每次从对接的文件中读取一个字符。并将此字符返回。
             * 如果返回值为-1,则表示文件到了末尾，可以不再读取。
             * */
//            int data = fr.read();
//            while(data != -1){
//                System.out.print((char)data);
//                data = fr.read();
//            }

            int data;
            while ((data = fr.read()) != -1) {
                System.out.println((char) data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4. 关闭相关的流资源，避免出现内存泄漏
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //实现方式3：调用read(char[] cbuf),每次从文件中读取多个字符
    @Test
    public void test3() {
        FileReader fr = null;
        try {
            //1. 创建File类的对象，对应着物理磁盘上的某个文件
            File file = new File("hello.txt");
            //2. 创建FileReader流对象，将File类的对象作为参数传递到FileReader的构造器中
            fr = new FileReader(file);
            //3. 通过相关流的方法，读取文件中的数据
            char[] cbuf = new char[5];
            /*
             * read(char[] cbuf) : 每次将文件中的数据读入到cbuf数组中，并返回读入到数组中的
             * 字符的个数。
             * */
            int len; //记录每次读入的字符的个数
            while ((len = fr.read(cbuf)) != -1) {
                //处理char[]数组即可
                //错误：
//                for(int i = 0;i < cbuf.length;i++){
//                    System.out.print(cbuf[i]);
//                }
                //错误：
//                String str = new String(cbuf);
//                System.out.print(str);
                //正确：
//                for(int i = 0;i < len;i++){
//                    System.out.print(cbuf[i]);
//                }
                //正确：
                String str = new String(cbuf, 0, len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4. 关闭相关的流资源，避免出现内存泄漏
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

不同实现方式的类比：

![image-20220518095907714](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220518095907714.png)

#### 3.2.2 FileWriter

`java.io.FileWriter `类用于写出字符到文件，构造时使用系统默认的字符编码和默认字节缓冲区。

- `FileWriter(File file)`： 创建一个新的 FileWriter，给定要读取的File对象。   
- `FileWriter(String fileName)`： 创建一个新的 FileWriter，给定要读取的文件的名称。  
- `FileWriter(File file,boolean append)`： 创建一个新的 FileWriter，指明是否在现有文件末尾追加内容。

举例：

```java
public class FWWrite {
    //注意：应该使用try-catch-finally处理异常。这里出于方便阅读代码，使用了throws的方式
    @Test
    public void test01()throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter(new File("fw.txt"));
        // 写出数据
        fw.write(97); // 写出第1个字符
        fw.write('b'); // 写出第2个字符
        fw.write('C'); // 写出第3个字符
        fw.write(30000); // 写出第4个字符，中文编码表中30000对应一个汉字。
		
        //关闭资源
        fw.close();
    }
	//注意：应该使用try-catch-finally处理异常。这里出于方便阅读代码，使用了throws的方式
    @Test
    public void test02()throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter(new File("fw.txt"));
        // 字符串转换为字节数组
        char[] chars = "尚硅谷".toCharArray();

        // 写出字符数组
        fw.write(chars); // 尚硅谷

        // 写出从索引1开始，2个字符。
        fw.write(chars,1,2); // 硅谷

        // 关闭资源
        fw.close();
    }
	//注意：应该使用try-catch-finally处理异常。这里出于方便阅读代码，使用了throws的方式
    @Test
    public void test03()throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter("fw.txt");
        // 字符串
        String msg = "尚硅谷";

        // 写出字符数组
        fw.write(msg); //尚硅谷

        // 写出从索引1开始，2个字符。
        fw.write(msg,1,2);	// 硅谷

        // 关闭资源
        fw.close();
    }
    
    @Test
    public void test04(){
        FileWriter fw = null;
        try {
            //1. 创建File的对象
            File file = new File("personinfo.txt");
            //2. 创建FileWriter的对象，将File对象作为参数传递到FileWriter的构造器中
            //如果输出的文件已存在，则会对现有的文件进行覆盖
            fw = new FileWriter(file);
//            fw = new FileWriter(file,false);
            //如果输出的文件已存在，则会在现有的文件末尾写入数据
//            fw = new FileWriter(file,true);

            //3. 调用相关的方法，实现数据的写出操作
            //write(String str) / write(char[] cbuf)
            fw.write("I love you,");
            fw.write("you love him.");
            fw.write("so sad".toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4. 关闭资源，避免内存泄漏
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
```

#### 3.2.3 小结

```
① 
因为出现流资源的调用，为了避免内存泄漏，需要使用try-catch-finally处理异常

② 
对于输入流来说，File类的对象必须在物理磁盘上存在，否则执行就会报FileNotFoundException。如果传入的是一个目录，则会报IOException异常。

对于输出流来说，File类的对象是可以不存在的。
   > 如果File类的对象不存在，则可以在输出的过程中，自动创建File类的对象
   > 如果File类的对象存在，
      > 如果调用FileWriter(File file)或FileWriter(File file,false)，输出时会新建File文件覆盖已有的文件
      > 如果调用FileWriter(File file,true)构造器，则在现有的文件末尾追加写出内容。
```

### 3.3  关于flush（刷新）

因为内置缓冲区的原因，如果FileWriter不关闭输出流，无法写出字符到文件中。但是关闭的流对象，是无法继续写出数据的。如果我们既想写出数据，又想继续使用流，就需要`flush()` 方法了。

- `flush()` ：刷新缓冲区，流对象可以继续使用。
- `close() `：先刷新缓冲区，然后通知系统释放资源。流对象不可以再被使用了。

注意：即便是flush()方法写出了数据，操作的最后还是要调用close方法，释放系统资源。

举例：

```java
public class FWWriteFlush {
    //注意：应该使用try-catch-finally处理异常。这里出于方便阅读代码，使用了throws的方式
    @Test
    public void test() throws IOException {
        // 使用文件名称创建流对象
        FileWriter fw = new FileWriter("fw.txt");
        // 写出数据，通过flush
        fw.write('刷'); // 写出第1个字符
        fw.flush();
        fw.write('新'); // 继续写出第2个字符，写出成功
        fw.flush();

        // 写出数据，通过close
        fw.write('关'); // 写出第1个字符
        fw.close();
        fw.write('闭'); // 继续写出第2个字符,【报错】java.io.IOException: Stream closed
        fw.close();
    }
}
```

## 4. 节点流之二：FileInputStream\FileOutputStream

如果我们读取或写出的数据是非文本文件，则Reader、Writer就无能为力了，必须使用字节流。

### 4.1 InputStream和OutputStream

#### 4.1.1 字节输入流：InputStream

`java.io.InputStream `抽象类是表示字节输入流的所有类的超类，可以读取字节信息到内存中。它定义了字节输入流的基本共性功能方法。

- `public int read()`： 从输入流读取一个字节。返回读取的字节值。虽然读取了一个字节，但是会自动提升为int类型。如果已经到达流末尾，没有数据可读，则返回-1。 
- `public int read(byte[] b)`： 从输入流中读取一些字节数，并将它们存储到字节数组 b中 。每次最多读取b.length个字节。返回实际读取的字节个数。如果已经到达流末尾，没有数据可读，则返回-1。 
- `public int read(byte[] b,int off,int len)`：从输入流中读取一些字节数，并将它们存储到字节数组 b中，从b[off]开始存储，每次最多读取len个字节 。返回实际读取的字节个数。如果已经到达流末尾，没有数据可读，则返回-1。 
- `public void close()` ：关闭此输入流并释放与此流相关联的任何系统资源。    

> 说明：close()方法，当完成流的操作时，必须调用此方法，释放系统资源。

####  4.1.2 字节输出流：OutputStream

`java.io.OutputStream `抽象类是表示字节输出流的所有类的超类，将指定的字节信息写出到目的地。它定义了字节输出流的基本共性功能方法。

- `public void write(int b)` ：将指定的字节输出流。虽然参数为int类型四个字节，但是只会保留一个字节的信息写出。
- `public void write(byte[] b)`：将 b.length字节从指定的字节数组写入此输出流。  
- `public void write(byte[] b, int off, int len)` ：从指定的字节数组写入 len字节，从偏移量 off开始输出到此输出流。  
- `public void flush() ` ：刷新此输出流并强制任何缓冲的输出字节被写出。  
- `public void close()` ：关闭此输出流并释放与此流相关联的任何系统资源。  

> 说明：close()方法，当完成流的操作时，必须调用此方法，释放系统资源。

### 4.2 FileInputStream 与 FileOutputStream

#### 4.2.1 FileInputStream

`java.io.FileInputStream `类是文件输入流，从文件中读取字节。

- `FileInputStream(File file)`： 通过打开与实际文件的连接来创建一个 FileInputStream ，该文件由文件系统中的 File对象 file命名。 
- `FileInputStream(String name)`： 通过打开与实际文件的连接来创建一个 FileInputStream ，该文件由文件系统中的路径名 name命名。  

举例：

```java
//read.txt文件中的内容如下：
abcde
```

读取操作

```java
public class FISRead {
    //注意：应该使用try-catch-finally处理异常。这里出于方便阅读代码，使用了throws的方式
    @Test
    public void test() throws IOException {
        // 使用文件名称创建流对象
        FileInputStream fis = new FileInputStream("read.txt");
        // 读取数据，返回一个字节
        int read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        read = fis.read();
        System.out.println((char) read);
        // 读取到末尾,返回-1
        read = fis.read();
        System.out.println(read);
        // 关闭资源
        fis.close();
        /*
        文件内容：abcde
        输出结果：
        a
        b
        c
        d
        e
        -1
         */
    }

    @Test
    public void test02()throws IOException{
        // 使用文件名称创建流对象
        FileInputStream fis = new FileInputStream("read.txt");
        // 定义变量，保存数据
        int b;
        // 循环读取
        while ((b = fis.read())!=-1) {
            System.out.println((char)b);
        }
        // 关闭资源
        fis.close();
    }
	
    @Test
    public void test03()throws IOException{
        // 使用文件名称创建流对象.
        FileInputStream fis = new FileInputStream("read.txt"); // 文件中为abcde
        // 定义变量，作为有效个数
        int len;
        // 定义字节数组，作为装字节数据的容器
        byte[] b = new byte[2];
        // 循环读取
        while (( len= fis.read(b))!=-1) {
            // 每次读取后,把数组变成字符串打印
            System.out.println(new String(b));
        }
        // 关闭资源
        fis.close();
        /*
        输出结果：
        ab
        cd
        ed
        最后错误数据`d`，是由于最后一次读取时，只读取一个字节`e`，数组中，
        上次读取的数据没有被完全替换，所以要通过`len` ，获取有效的字节
         */
    }

    @Test
    public void test04()throws IOException{
        // 使用文件名称创建流对象.
        FileInputStream fis = new FileInputStream("read.txt"); // 文件中为abcde
        // 定义变量，作为有效个数
        int len;
        // 定义字节数组，作为装字节数据的容器
        byte[] b = new byte[2];
        // 循环读取
        while (( len= fis.read(b))!=-1) {
            // 每次读取后,把数组的有效字节部分，变成字符串打印
            System.out.println(new String(b,0,len));//  len 每次读取的有效字节个数
        }
        // 关闭资源
        fis.close();
        /*
        输出结果：
        ab
        cd
        e
         */
    }
}
```

#### 4.2.2 FileOutputStream

`java.io.FileOutputStream `类是文件输出流，用于将数据写出到文件。

- `public FileOutputStream(File file)`：创建文件输出流，写出由指定的 File对象表示的文件。 
- `public FileOutputStream(String name)`： 创建文件输出流，指定的名称为写出文件。
- `public FileOutputStream(File file, boolean append)`：  创建文件输出流，指明是否在现有文件末尾追加内容。

举例：

```java
package com.atguigu.fileio;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class FOSWrite {
    //注意：应该使用try-catch-finally处理异常。这里出于方便阅读代码，使用了throws的方式
    @Test
    public void test01() throws IOException {
        // 使用文件名称创建流对象
        FileOutputStream fos = new FileOutputStream("fos.txt");
        // 写出数据
        fos.write(97); // 写出第1个字节
        fos.write(98); // 写出第2个字节
        fos.write(99); // 写出第3个字节
        // 关闭资源
        fos.close();
      /*  输出结果：abc*/
    }

    @Test
    public void test02()throws IOException {
        // 使用文件名称创建流对象
        FileOutputStream fos = new FileOutputStream("fos.txt");
        // 字符串转换为字节数组
        byte[] b = "abcde".getBytes();
        // 写出从索引2开始，2个字节。索引2是c，两个字节，也就是cd。
        fos.write(b,2,2);
        // 关闭资源
        fos.close();
    }
    //这段程序如果多运行几次，每次都会在原来文件末尾追加abcde
    @Test
    public void test03()throws IOException {
        // 使用文件名称创建流对象
        FileOutputStream fos = new FileOutputStream("fos.txt",true);
        // 字符串转换为字节数组
        byte[] b = "abcde".getBytes();
        fos.write(b);
        // 关闭资源
        fos.close();
    }
    
    //使用FileInputStream\FileOutputStream，实现对文件的复制
    @Test
    public void test05() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //1. 造文件-造流
            //复制图片：成功
//            fis = new FileInputStream(new File("pony.jpg"));
//            fos = new FileOutputStream(new File("pony_copy1.jpg"));

            //复制文本文件：成功
            fis = new FileInputStream(new File("hello.txt"));
            fos = new FileOutputStream(new File("hello1.txt"));

            //2. 复制操作（读、写）
            byte[] buffer = new byte[1024];
            int len;//每次读入到buffer中字节的个数
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
//                String str = new String(buffer,0,len);
//                System.out.print(str);
            }
            System.out.println("复制成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            //3. 关闭资源
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
```

### 4.3 练习

**练习：**实现图片加密操作。

提示：

 ![image-20220413002723838](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220413002723838.png)

```java
/**
 * @author 尚硅谷-宋红康
 * @create 8:59
 */
public class FileSecretTest {
    /*
    * 图片的加密
    * */
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File file1 = new File("pony.jpg");
            File file2 = new File("pony_secret.jpg");
            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);

            //方式1：每次读入一个字节，效率低
//            int data;
//            while((data = fis.read()) != -1){
//                fos.write(data ^ 5);
//            }
            //方式2：每次读入一个字节数组，效率高
            int len;
            byte[] buffer = new byte[1024];
            while((len = fis.read(buffer)) != -1){

                for(int i = 0;i < len;i++){
                    buffer[i] = (byte) (buffer[i] ^ 5);
                }

                fos.write(buffer,0,len);

            }


            System.out.println("加密成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 图片的解密
     * */
    @Test
    public void test2(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File file1 = new File("pony_secret.jpg");
            File file2 = new File("pony_unsecret.jpg");
            fis = new FileInputStream(file1);
            fos = new FileOutputStream(file2);

            //方式1：每次读入一个字节，效率低
//            int data;
//            while((data = fis.read()) != -1){
//                fos.write(data ^ 5);
//            }
            //方式2：每次读入一个字节数组，效率高
            int len;
            byte[] buffer = new byte[1024];
            while((len = fis.read(buffer)) != -1){

                for(int i = 0;i < len;i++){
                    buffer[i] = (byte) (buffer[i] ^ 5);
                }

                fos.write(buffer,0,len);

            }

            System.out.println("解密成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

## 5. 处理流之一：缓冲流

- `为了提高数据读写的速度`，Java API提供了带缓冲功能的流类：缓冲流。

- 缓冲流要“套接”在相应的节点流之上，根据数据操作单位可以把缓冲流分为：
  - **字节缓冲流**：`BufferedInputStream`，`BufferedOutputStream` 
  - **字符缓冲流**：`BufferedReader`，`BufferedWriter`

- 缓冲流的基本原理：在创建流对象时，内部会创建一个缓冲区数组（缺省使用`8192个字节(8Kb)`的缓冲区），通过缓冲区读写，减少系统IO次数，从而提高读写的效率。


![image-20220413002314063](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220413002314063.png)



<img src="C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第15章_File类与IO流/images/image-20220514183413011.png" alt="image-20220514183413011" style="zoom:80%;" />

### 5.1 构造器

* `public BufferedInputStream(InputStream in)` ：创建一个 新的字节型的缓冲输入流。 
* `public BufferedOutputStream(OutputStream out)`： 创建一个新的字节型的缓冲输出流。

代码举例：

```java
// 创建字节缓冲输入流
BufferedInputStream bis = new BufferedInputStream(new FileInputStream("abc.jpg"));
// 创建字节缓冲输出流
BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("abc_copy.jpg"));
```

* `public BufferedReader(Reader in)` ：创建一个 新的字符型的缓冲输入流。 
* `public BufferedWriter(Writer out)`： 创建一个新的字符型的缓冲输出流。

代码举例：

```java
// 创建字符缓冲输入流
BufferedReader br = new BufferedReader(new FileReader("br.txt"));
// 创建字符缓冲输出流
BufferedWriter bw = new BufferedWriter(new FileWriter("bw.txt"));
```

### 5.2 效率测试

查询API，缓冲流读写方法与基本的流是一致的，我们通过复制大文件（375MB），测试它的效率。

```java
//方法1：使用FileInputStream\FileOutputStream实现非文本文件的复制
public void copyFileWithFileStream(String srcPath,String destPath){
    FileInputStream fis = null;
    FileOutputStream fos = null;
    try {
        //1. 造文件-造流
        fis = new FileInputStream(new File(srcPath));
        fos = new FileOutputStream(new File(destPath));

        //2. 复制操作（读、写）
        byte[] buffer = new byte[100];
        int len;//每次读入到buffer中字节的个数
        while ((len = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        System.out.println("复制成功");
    } catch (IOException e) {
        throw new RuntimeException(e);
    } finally {
        //3. 关闭资源
        try {
            if (fos != null)
                fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (fis != null)
                fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

@Test
public void test1(){
    String srcPath = "C:\\Users\\shkstart\\Desktop\\01-复习.mp4";
    String destPath = "C:\\Users\\shkstart\\Desktop\\01-复习2.mp4";

    long start = System.currentTimeMillis();

    copyFileWithFileStream(srcPath,destPath);

    long end = System.currentTimeMillis();

    System.out.println("花费的时间为：" + (end - start));//7677毫秒

}

//方法2：使用BufferedInputStream\BufferedOuputStream实现非文本文件的复制
public void copyFileWithBufferedStream(String srcPath,String destPath){
    FileInputStream fis = null;
    FileOutputStream fos = null;
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
        //1. 造文件
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        //2. 造流
        fis = new FileInputStream(srcFile);
        fos = new FileOutputStream(destFile);

        bis = new BufferedInputStream(fis);
        bos = new BufferedOutputStream(fos);

        //3. 读写操作
        int len;
        byte[] buffer = new byte[100];
        while ((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        System.out.println("复制成功");
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        //4. 关闭资源(如果有多个流，我们需要先关闭外面的流，再关闭内部的流)
        try {
            if (bos != null)
                bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (bis != null)
                bis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
@Test
public void test2(){
    String srcPath = "C:\\Users\\shkstart\\Desktop\\01-复习.mp4";
    String destPath = "C:\\Users\\shkstart\\Desktop\\01-复习2.mp4";

    long start = System.currentTimeMillis();

    copyFileWithBufferedStream(srcPath,destPath);

    long end = System.currentTimeMillis();

    System.out.println("花费的时间为：" + (end - start));//415毫秒

}
```

### 5.3 字符缓冲流特有方法

字符缓冲流的基本方法与普通字符流调用方式一致，不再阐述，我们来看它们具备的特有方法。

* BufferedReader：`public String readLine()`: 读一行文字。 
* BufferedWriter：`public void newLine()`: 写一行行分隔符,由系统属性定义符号。 

```java
public class BufferedIOLine {
    @Test
    public void testReadLine()throws IOException {
        // 创建流对象
        BufferedReader br = new BufferedReader(new FileReader("in.txt"));
        // 定义字符串,保存读取的一行文字
        String line;
        // 循环读取,读取到最后返回null
        while ((line = br.readLine())!=null) {
            System.out.println(line);
        }
        // 释放资源
        br.close();
    }

    @Test
    public void testNewLine()throws IOException{
        // 创建流对象
        BufferedWriter bw = new BufferedWriter(new FileWriter("out.txt"));
        // 写出数据
        bw.write("尚");
        // 写出换行
        bw.newLine();
        bw.write("硅");
        bw.newLine();
        bw.write("谷");
        bw.newLine();
        // 释放资源
        bw.close();
    }
}

```

> 说明：
>
> 1. 涉及到嵌套的多个流时，如果都显式关闭的话，需要先关闭外层的流，再关闭内层的流
>
> 2. 其实在开发中，只需要关闭最外层的流即可，因为在关闭外层流时，内层的流也会被关闭。

### 5.4 练习

**练习1：**分别使用节点流：FileInputStream、FileOutputStream和缓冲流：BufferedInputStream、BufferedOutputStream实现文本文件/图片/视频文件的复制。并比较二者在数据复制方面的效率。

**练习2：**

姓氏统计：一个文本文件中存储着北京所有高校在校生的姓名，格式如下：

```
每行一个名字，姓与名以空格分隔：
张 三
李 四
王 小五
```

现在想统计所有的姓氏在文件中出现的次数，请描述一下你的解决方案。

```java
	public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File("e:/name.txt")));
            String value = null; // 临时接收文件中的字符串变量
            StringBuffer buffer = new StringBuffer();
            flag:
            while ((value = br.readLine()) != null) { // 开始读取文件中的字符
                char[] c = value.toCharArray();
                for (int i = 0; i < c.length; i++) {
                    if (c[i] != ' ') {
                        buffer.append(String.valueOf(c[i]));
                    } else {
                        if (map.containsKey(buffer.toString())) {
                            int count = map.get(buffer.toString());
                            map.put(buffer.toString(), count + 1);
                        } else {
                            map.put(buffer.toString(), 1);
                        }
                        buffer.delete(0, buffer.length());
                        continue flag;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        Iterator<Map.Entry<String, Integer>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> end = (Map.Entry<String, Integer>) it.next();
            System.out.println(end);
        }

    }
```

## 6. 处理流之二：转换流

### 6.1 问题引入

**引入情况1：**

使用`FileReader` 读取项目中的文本文件。由于IDEA设置中针对项目设置了UTF-8编码，当读取Windows系统中创建的文本文件时，如果Windows系统默认的是GBK编码，则读入内存中会出现乱码。

```java
package com.atguigu.transfer;

import java.io.FileReader;
import java.io.IOException;

public class Problem {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("E:\\File_GBK.txt");
        int data;
        while ((data = fileReader.read()) != -1) {
            System.out.print((char)data);
        }
        fileReader.close();
    }
}

输出结果：
���
```

那么如何读取GBK编码的文件呢？ 

**引入情况2：**

针对文本文件，现在使用一个字节流进行数据的读入，希望将数据显示在控制台上。此时针对包含中文的文本数据，可能会出现乱码。

### 6.2 转换流的理解

**作用：转换流是字节与字符间的桥梁！**

<img src="C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第15章_File类与IO流/images/2_zhuanhuan.jpg" style="zoom: 67%;" />

具体来说：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412231533768.png" alt="image-20220412231533768" style="zoom:85%;" />

### 6.3 InputStreamReader 与 OutputStreamWriter

- **InputStreamReader** 

  - 转换流`java.io.InputStreamReader`，是Reader的子类，是从字节流到字符流的桥梁。它读取字节，并使用指定的字符集将其解码为字符。它的字符集可以由名称指定，也可以接受平台的默认字符集。 

  - 构造器

    - `InputStreamReader(InputStream in)`: 创建一个使用默认字符集的字符流。 
    - `InputStreamReader(InputStream in, String charsetName)`: 创建一个指定字符集的字符流。

  - 举例

    ```java
    //使用默认字符集
    InputStreamReader isr1 = new InputStreamReader(new FileInputStream("in.txt"));
    //使用指定字符集
    InputStreamReader isr2 = new InputStreamReader(new FileInputStream("in.txt") , "GBK");
    ```

  - 示例代码：

    ```java
    package com.atguigu.transfer;
    
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.io.InputStreamReader;
    
    public class InputStreamReaderDemo {
        public static void main(String[] args) throws IOException {
            // 定义文件路径,文件为gbk编码
            String fileName = "E:\\file_gbk.txt";
            //方式1：
            // 创建流对象,默认UTF8编码
            InputStreamReader isr1 = new InputStreamReader(new FileInputStream(fileName));
            // 定义变量,保存字符
            int charData;
            // 使用默认编码字符流读取,乱码
            while ((charData = isr1.read()) != -1) {
                System.out.print((char)charData); // ��Һ�
            }
            isr1.close();
    		
            //方式2：
            // 创建流对象,指定GBK编码
            InputStreamReader isr2 = new InputStreamReader(new FileInputStream(fileName) , "GBK");
            // 使用指定编码字符流读取,正常解析
            while ((charData = isr2.read()) != -1) {
                System.out.print((char)charData);// 大家好
            }
            isr2.close();
        }
    }
    ```

- **OutputStreamWriter**

  - 转换流`java.io.OutputStreamWriter` ，是Writer的子类，是从字符流到字节流的桥梁。使用指定的字符集将字符编码为字节。它的字符集可以由名称指定，也可以接受平台的默认字符集。 

  - 构造器

    - `OutputStreamWriter(OutputStream in)`: 创建一个使用默认字符集的字符流。 
    - `OutputStreamWriter(OutputStream in,String charsetName)`: 创建一个指定字符集的字符流。

  - 举例：

    ```java
    //使用默认字符集
    OutputStreamWriter isr = new OutputStreamWriter(new FileOutputStream("out.txt"));
    //使用指定的字符集
    OutputStreamWriter isr2 = new OutputStreamWriter(new FileOutputStream("out.txt") , "GBK");
    ```

  - 示例代码：

    ```java
    package com.atguigu.transfer;
    
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.OutputStreamWriter;
    
    public class OutputStreamWriterDemo {
        public static void main(String[] args) throws IOException {
            // 定义文件路径
            String FileName = "E:\\out_utf8.txt";
            // 创建流对象,默认UTF8编码
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(FileName));
            // 写出数据
            osw.write("你好"); // 保存为6个字节
            osw.close();
    
            // 定义文件路径
            String FileName2 = "E:\\out_gbk.txt";
            // 创建流对象,指定GBK编码
            OutputStreamWriter osw2 = new OutputStreamWriter(new                     
                                                    FileOutputStream(FileName2),"GBK");
            // 写出数据
            osw2.write("你好");// 保存为4个字节
            osw2.close();
        }
    }
    ```

### 6.4 字符编码和字符集

#### 6.4.1 编码与解码

计算机中储存的信息都是用`二进制数`表示的，而我们在屏幕上看到的数字、英文、标点符号、汉字等字符是二进制数转换之后的结果。按照某种规则，将字符存储到计算机中，称为**编码** 。反之，将存储在计算机中的二进制数按照某种规则解析显示出来，称为**解码** 。

**字符编码（Character Encoding）** : 就是一套自然语言的字符与二进制数之间的对应规则。

**编码表**：生活中文字和计算机中二进制的对应规则

**乱码的情况**：按照A规则存储，同样按照A规则解析，那么就能显示正确的文本符号。反之，按照A规则存储，再按照B规则解析，就会导致乱码现象。

```
编码:字符(人能看懂的)--字节(人看不懂的)

解码:字节(人看不懂的)-->字符(人能看懂的)
```

#### 6.4.2 字符集

* **字符集Charset**：也叫编码表。是一个系统支持的所有字符的集合，包括各国家文字、标点符号、图形符号、数字等。

- 计算机要准确的存储和识别各种字符集符号，需要进行字符编码，一套字符集必然至少有一套字符编码。常见字符集有ASCII字符集、GBK字符集、Unicode字符集等。


可见，当指定了**编码**，它所对应的**字符集**自然就指定了，所以**编码**才是我们最终要关心的。

* **ASCII字符集** ：

  * ASCII码（American Standard Code for Information Interchange，美国信息交换标准代码）：上个世纪60年代，美国制定了一套字符编码，对`英语字符`与二进制位之间的关系，做了统一规定。这被称为ASCII码。
  * ASCII码用于显示现代英语，主要包括控制字符（回车键、退格、换行键等）和可显示字符（英文大小写字符、阿拉伯数字和西文符号）。
  * 基本的ASCII字符集，使用7位（bits）表示一个字符（最前面的1位统一规定为0），共`128个`字符。比如：空格“SPACE”是32（二进制00100000），大写的字母A是65（二进制01000001）。
  * 缺点：不能表示所有字符。

* **ISO-8859-1字符集**：

  * 拉丁码表，别名Latin-1，用于显示欧洲使用的语言，包括荷兰语、德语、意大利语、葡萄牙语等
  * ISO-8859-1使用单字节编码，兼容ASCII编码。

* **GBxxx字符集**：

  * GB就是国标的意思，是为了`显示中文`而设计的一套字符集。
  * **GB2312**：简体中文码表。一个小于127的字符的意义与原来相同，即向下兼容ASCII码。但两个大于127的字符连在一起时，就表示一个汉字，这样大约可以组合了包含`7000多个简体汉字`，此外数学符号、罗马希腊的字母、日文的假名们都编进去了，这就是常说的"全角"字符，而原来在127号以下的那些符号就叫"半角"字符了。
  * **GBK**：最常用的中文码表。是在GB2312标准基础上的扩展规范，使用了`双字节`编码方案，共收录了`21003个`汉字，完全兼容GB2312标准，同时支持`繁体汉字`以及日韩汉字等。
  * **GB18030**：最新的中文码表。收录汉字`70244个`，采用`多字节`编码，每个字可以由1个、2个或4个字节组成。支持中国国内少数民族的文字，同时支持繁体汉字以及日韩汉字等。

* **Unicode字符集** ：

  * Unicode编码为表达`任意语言的任意字符`而设计，也称为统一码、标准万国码。Unicode 将世界上所有的文字用`2个字节`统一进行编码，为每个字符设定唯一的二进制编码，以满足跨语言、跨平台进行文本处理的要求。

  - Unicode 的缺点：这里有三个问题：
    - 第一，英文字母只用一个字节表示就够了，如果用更多的字节存储是`极大的浪费`。
    - 第二，如何才能`区别Unicode和ASCII`？计算机怎么知道两个字节表示一个符号，而不是分别表示两个符号呢？
    - 第三，如果和GBK等双字节编码方式一样，用最高位是1或0表示两个字节和一个字节，就少了很多值无法用于表示字符，`不够表示所有字符`。
  - Unicode在很长一段时间内无法推广，直到互联网的出现，为解决Unicode如何在网络上传输的问题，于是面向传输的众多 UTF（UCS Transfer Format）标准出现。具体来说，有三种编码方案，UTF-8、UTF-16和UTF-32。

* **UTF-8字符集**：

  * Unicode是字符集，UTF-8、UTF-16、UTF-32是三种`将数字转换到程序数据`的编码方案。顾名思义，UTF-8就是每次8个位传输数据，而UTF-16就是每次16个位。其中，UTF-8 是在互联网上`使用最广`的一种 Unicode 的实现方式。
  * 互联网工程工作小组（IETF）要求所有互联网协议都必须支持UTF-8编码。所以，我们开发Web应用，也要使用UTF-8编码。UTF-8 是一种`变长的编码方式`。它使用1-4个字节为每个字符编码，编码规则：
    1. 128个US-ASCII字符，只需一个字节编码。
    2. 拉丁文等字符，需要二个字节编码。 
    3. 大部分常用字（含中文），使用三个字节编码。
    4. 其他极少使用的Unicode辅助字符，使用四字节编码。

- 举例

Unicode符号范围  | UTF-8编码方式

```
(十六进制)           | （二进制）

————————————————————|—–—–—–—–—–—–—–—–—–—–—–—–—–—–

0000 0000-0000 007F | 0xxxxxxx（兼容原来的ASCII）

0000 0080-0000 07FF | 110xxxxx 10xxxxxx

0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx

0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
```

![image-20220525164636164](C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第15章_File类与IO流/images/image-20220525164636164.png)

- 小结

![字符集](C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第15章_File类与IO流/images/字符集.jpg)

> 注意：在中文操作系统上，ANSI（美国国家标准学会、AMERICAN NATIONAL STANDARDS INSTITUTE: ANSI）编码即为GBK；在英文操作系统上，ANSI编码即为ISO-8859-1。

### 6.5 练习

把当前module下的《康师傅的话.txt》字符编码为GBK，复制到电脑桌面目录下的《寄语.txt》，
字符编码为UTF-8。

在当前module下的文本内容：

```
六项精进：
（一）付出不亚于任何人的努力
（二）要谦虚，不要骄傲
（三）要每天反省
（四）活着，就要感谢
（五）积善行、思利他
（六）不要有感性的烦恼
```

代码：

```java
/**
 * @author 尚硅谷-宋红康
 * @create 9:06
 */
public class InputStreamReaderDemo {

    @Test
    public void test() {
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        try {
            isr = new InputStreamReader(new FileInputStream("康师傅的话.txt"),"gbk");

            osw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\shkstart\\Desktop\\寄语.txt"),"utf-8");

            char[] cbuf = new char[1024];
            int len;
            while ((len = isr.read(cbuf)) != -1) {
                osw.write(cbuf, 0, len);
                osw.flush();
            }
            System.out.println("文件复制完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null)
                    isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (osw != null)
                    osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
```

## 7. 处理流之三/四：数据流、对象流

### 7.1 数据流与对象流说明

如果需要将内存中定义的变量（包括基本数据类型或引用数据类型）保存在文件中，那怎么办呢？

```java
int age = 300;
char gender = '男';
int energy = 5000;
double price = 75.5;
boolean relive = true;

String name = "巫师";
Student stu = new Student("张三",23,89);
```

Java提供了数据流和对象流来处理这些类型的数据：

- **数据流：DataOutputStream、DataInputStream**
  - DataOutputStream：<font color='red'>允许应用程序将基本数据类型、String类型的变量写入输出流中</font>
    - DataInputStream：允许应用程序以与机器无关的方式从底层输入流中读取基本数据																																																																类型、String类型的变量。

- 对象流DataInputStream中的方法：

```java
  byte readByte()                short readShort()
  int readInt()                  long readLong()
  float readFloat()              double readDouble()
  char readChar()				 boolean readBoolean()					
  String readUTF()               void readFully(byte[] b)
```

- 对象流DataOutputStream中的方法：将上述的方法的read改为相应的write即可。
- 数据流的弊端：只支持Java基本数据类型和字符串的读写，而不支持其它Java对象的类型。而ObjectOutputStream和ObjectInputStream既支持Java基本数据类型的数据读写，又支持Java对象的读写，所以重点介绍对象流ObjectOutputStream和ObjectInputStream。
- **对象流：ObjectOutputStream、ObjectInputStream**

  - ObjectOutputStream：将 Java 基本数据类型和对象写入字节输出流中。通过在流中使用文件可以实现Java各种基本数据类型的数据以及对象的持久存储。
  - ObjectInputStream：ObjectInputStream 对以前使用 ObjectOutputStream 写出的基本数据类型的数据和对象进行读入操作，保存在内存中。

> 说明：对象流的强大之处就是可以把Java中的对象写入到数据源中，也能把对象从数据源中还原回来。

### 7.2 对象流API

**ObjectOutputStream中的构造器：**

`public ObjectOutputStream(OutputStream out) `： 创建一个指定的ObjectOutputStream。

```java
FileOutputStream fos = new FileOutputStream("game.dat");
ObjectOutputStream oos = new ObjectOutputStream(fos);
```

**ObjectOutputStream中的方法：**

- public void writeBoolean(boolean val)：写出一个 boolean 值。
- public void writeByte(int val)：写出一个8位字节
- public void writeShort(int val)：写出一个16位的 short 值
- public void writeChar(int val)：写出一个16位的 char 值
- public void writeInt(int val)：写出一个32位的 int 值
- public void writeLong(long val)：写出一个64位的 long 值
- public void writeFloat(float val)：写出一个32位的 float 值。
- public void writeDouble(double val)：写出一个64位的 double 值
- public void writeUTF(String str)：将表示长度信息的两个字节写入输出流，后跟字符串 s 中每个字符的 UTF-8 修改版表示形式。根据字符的值，将字符串 s 中每个字符转换成一个字节、两个字节或三个字节的字节组。注意，将 String 作为基本数据写入流中与将它作为 Object 写入流中明显不同。 如果 s 为 null，则抛出 NullPointerException。
- `public void writeObject(Object obj)`：写出一个obj对象
- public void close() ：关闭此输出流并释放与此流相关联的任何系统资源

**ObjectInputStream中的构造器：**

`public ObjectInputStream(InputStream in) `： 创建一个指定的ObjectInputStream。

```java
FileInputStream fis = new FileInputStream("game.dat");
ObjectInputStream ois = new ObjectInputStream(fis);
```

**ObjectInputStream中的方法：**

- public boolean readBoolean()：读取一个 boolean 值
- public byte readByte()：读取一个 8 位的字节
- public short readShort()：读取一个 16 位的 short 值
- public char readChar()：读取一个 16 位的 char 值
- public int readInt()：读取一个 32 位的 int 值
- public long readLong()：读取一个 64 位的 long 值
- public float readFloat()：读取一个 32 位的 float 值
- public double readDouble()：读取一个 64 位的 double 值
- public String readUTF()：读取 UTF-8 修改版格式的 String
- `public void readObject(Object obj)`：读入一个obj对象
- public void close() ：关闭此输入流并释放与此流相关联的任何系统资源

### 7.3 认识对象序列化机制

**1、何为对象序列化机制？**

`对象序列化机制`允许把内存中的Java对象转换成平台无关的二进制流，从而允许把这种二进制流持久地保存在磁盘上，或通过网络将这种二进制流传输到另一个网络节点。//当其它程序获取了这种二进制流，就可以恢复成原来的Java对象。

- 序列化过程：用一个字节序列可以表示一个对象，该字节序列包含该`对象的类型`和`对象中存储的属性`等信息。字节序列写出到文件之后，相当于文件中`持久保存`了一个对象的信息。 


- 反序列化过程：该字节序列还可以从文件中读取回来，重构对象，对它进行`反序列化`。`对象的数据`、`对象的类型`和`对象中存储的数据`信息，都可以用来在内存中创建对象。

<img src="C:/Users/Tony贾维斯/Desktop/Java/尚硅谷_第15章_File类与IO流/images/image-20220503123328452.png" alt="image-20220503123328452" style="zoom:67%;" />

**2、序列化机制的重要性**

序列化是 RMI（Remote Method Invoke、远程方法调用）过程的参数和返回值都必须实现的机制，而 RMI 是 JavaEE 的基础。因此序列化机制是 JavaEE 平台的基础。

序列化的好处，在于可将任何实现了Serializable接口的对象转化为**字节数据**，使其在保存和传输时可被还原。

**3、实现原理**

- 序列化：用ObjectOutputStream类保存基本类型数据或对象的机制。方法为：
  - `public final void writeObject (Object obj)` : 将指定的对象写出。

- 反序列化：用ObjectInputStream类读取基本类型数据或对象的机制。方法为：
  - `public final Object readObject ()` : 读取一个对象。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/3_xuliehua.jpg" style="zoom:70%;" />

### 7.4 如何实现序列化机制

如果需要让某个对象支持序列化机制，则必须让对象所属的类及其属性是可序列化的，为了让某个类是可序列化的，该类必须实现`java.io.Serializable ` 接口。`Serializable` 是一个`标记接口`，不实现此接口的类将不会使任何状态序列化或反序列化，会抛出`NotSerializableException` 。

* 如果对象的某个属性也是引用数据类型，那么如果该属性也要序列化的话，也要实现`Serializable` 接口
* 该类的所有属性必须是可序列化的。如果有一个属性不需要可序列化的，则该属性必须注明是瞬态的，使用`transient` 关键字修饰。
* `静态（static）变量`的值不会序列化。因为静态变量的值不属于某个对象。

举例1：

```java
package com.atguigu.object;

import org.junit.Test;

import java.io.*;

public class ReadWriteDataOfAnyType {
    @Test
    public void save() throws IOException {
        String name = "巫师";
        int age = 300;
        char gender = '男';
        int energy = 5000;
        double price = 75.5;
        boolean relive = true;

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game.dat"));
        oos.writeUTF(name);
        oos.writeInt(age);
        oos.writeChar(gender);
        oos.writeInt(energy);
        oos.writeDouble(price);
        oos.writeBoolean(relive);
        oos.close();
    }
    @Test
    public void reload()throws IOException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game.dat"));
        String name = ois.readUTF();
        int age = ois.readInt();
        char gender = ois.readChar();
        int energy = ois.readInt();
        double price = ois.readDouble();
        boolean relive = ois.readBoolean();

        System.out.println(name+"," + age + "," + gender + "," + energy + "," + price + "," + relive);

        ois.close();
    }
}

```

举例2：

```java
package com.atguigu.object;

import java.io.Serializable;

public class Employee implements Serializable {
    //static final long serialVersionUID = 23234234234L;
    public static String company; //static修饰的类变量，不会被序列化
    public String name;
    public String address;
    public transient int age; // transient瞬态修饰成员,不会被序列化

    public Employee(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public static String getCompany() {
        return company;
    }

    public static void setCompany(String company) {
        Employee.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", company=" + company +
                '}';
    }
}

```

```java
package com.atguigu.object;

import org.junit.Test;

import java.io.*;

public class ReadWriteObject {
    @Test
    public void save() throws IOException {
        Employee.setCompany("尚硅谷");
        Employee e = new Employee("小谷姐姐", "宏福苑", 23);
        // 创建序列化流对象
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employee.dat"));
        // 写出对象
        oos.writeObject(e);
        // 释放资源
        oos.close();
        System.out.println("Serialized data is saved"); // 姓名，地址被序列化，年龄没有被序列化。
    }

    @Test
    public void reload() throws IOException, ClassNotFoundException {
        // 创建反序列化流
        FileInputStream fis = new FileInputStream("employee.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        // 读取一个对象
        Employee e = (Employee) ois.readObject();
        // 释放资源
        ois.close();
        fis.close();

        System.out.println(e);
    }
}
```

举例3：如果有多个对象需要序列化，则可以将对象放到集合中，再序列化集合对象即可。

```java
package com.atguigu.object;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class ReadWriteCollection {
    @Test
    public void save() throws IOException {
        ArrayList<Employee> list = new ArrayList<>();
        list.add(new Employee("张三", "宏福苑", 23));
        list.add(new Employee("李四", "白庙", 24));
        list.add(new Employee("王五", "平西府", 25));
        // 创建序列化流对象
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.dat"));
        // 写出对象
        oos.writeObject(list);
        // 释放资源
        oos.close();
    }

    @Test
    public void reload() throws IOException, ClassNotFoundException {
        // 创建反序列化流
        FileInputStream fis = new FileInputStream("employees.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        // 读取一个对象
        ArrayList<Employee> list = (ArrayList<Employee>) ois.readObject();
        // 释放资源
        ois.close();
        fis.close();

        System.out.println(list);
    }
}

```

### 7.5 反序列化失败问题

**问题1：**

对于JVM可以反序列化对象，它必须是能够找到class文件的类。如果找不到该类的class文件，则抛出一个 `ClassNotFoundException` 异常。  

**问题2：**

当JVM反序列化对象时，能找到class文件，但是class文件在序列化对象之后发生了修改，那么反序列化操作也会失败，抛出一个`InvalidClassException`异常。发生这个异常的原因如下：

* 该类的序列版本号与从流中读取的类描述符的版本号不匹配 
* 该类包含未知数据类型  

解决办法：

`Serializable` 接口给需要序列化的类，提供了一个序列版本号：`serialVersionUID` 。凡是实现 Serializable接口的类都应该有一个表示序列化版本标识符的静态变量：

```java
static final long serialVersionUID = 234242343243L; //它的值由程序员随意指定即可。
```

- serialVersionUID用来表明类的不同版本间的兼容性。简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体类的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常(InvalidCastException)。
- 如果类没有显示定义这个静态常量，它的值是Java运行时环境根据类的内部细节`自动生成`的。若类的实例变量做了修改，serialVersionUID `可能发生变化`。因此，建议显式声明。
- 如果声明了serialVersionUID，即使在序列化完成之后修改了类导致类重新编译，则原来的数据也能正常反序列化，只是新增的字段值是默认值而已。

```java
package com.atguigu.object;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1324234L; //增加serialVersionUID
    
    //其它结构：略
}
```

### 7.6 面试题&练习

面试题：谈谈你对java.io.Serializable接口的理解，我们知道它用于序列化，是空方法接口，还有其它认识吗？

```java
实现了Serializable接口的对象，可将它们转换成一系列字节，并可在以后完全恢复回原来的样子。这一过程亦可通过网络进行。这意味着序列化机制能自动补偿操作系统间的差异。换句话说，可以先在Windows机器上创建一个对象，对其序列化，然后通过网络发给一台Unix机器，然后在那里准确无误地重新“装配”。不必关心数据在不同机器上如何表示，也不必关心字节的顺序或者其他任何细节。
    
由于大部分作为参数的类如String、Integer等都实现了java.io.Serializable的接口，也可以利用多态的性质，作为参数使接口更灵活。

```

练习：

- 需求说明：
  - 网上购物时某用户填写订单，订单内容为产品列表，保存在“save.bin”中。
  - 运行时，如果不存在“save.bin”，则进行新订单录入，如果存在，则显示并计算客户所需付款。

- 分析：
  - 编写Save()方法保存对象到“save.bin”
  - 编写Load()方法获得对象，计算客户所需付款

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503123603551.png" alt="image-20220503123603551" style="zoom:80%;" />

##  8. 其他流的使用

### 8.1 标准输入、输出流

- System.in和System.out分别代表了系统标准的输入和输出设备
- 默认输入设备是：键盘，输出设备是：显示器
- System.in的类型是InputStream
- System.out的类型是PrintStream，其是OutputStream的子类FilterOutputStream 的子类
- 重定向：通过System类的setIn，setOut方法对默认设备进行改变。
  - public static void setIn(InputStream in)
  - public static void setOut(PrintStream out)

**举例：**

从键盘输入字符串，要求将读取到的整行字符串转成大写输出。然后继续进行输入操作，直至当输入“e”或者“exit”时，退出程序。

```java
System.out.println("请输入信息(退出输入e或exit):");
// 把"标准"输入流(键盘输入)这个字节流包装成字符流,再包装成缓冲流
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String s = null;
try {
    while ((s = br.readLine()) != null) { // 读取用户输入的一行数据 --> 阻塞程序
        if ("e".equalsIgnoreCase(s) || "exit".equalsIgnoreCase(s)) {
            System.out.println("安全退出!!");
            break;
        }
        // 将读取到的整行字符串转成大写输出
        System.out.println("-->:" + s.toUpperCase());
        System.out.println("继续输入信息");
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        if (br != null) {
            br.close(); // 关闭过滤流时,会自动关闭它包装的底层节点流
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

```

**拓展：**

System类中有三个常量对象：System.out、System.in、System.err

查看System类中这三个常量对象的声明：

```java
public final static InputStream in = null;
public final static PrintStream out = null;
public final static PrintStream err = null;
```

奇怪的是，

- 这三个常量对象有final声明，但是却初始化为null。final声明的常量一旦赋值就不能修改，那么null不会空指针异常吗？
- 这三个常量对象为什么要小写？final声明的常量按照命名规范不是应该大写吗？
- 这三个常量的对象有set方法？final声明的常量不是不能修改值吗？set方法是如何修改它们的值的？

```java
final声明的常量，表示在Java的语法体系中它们的值是不能修改的，而这三个常量对象的值是由C/C++等系统函数进行初始化和修改值的，所以它们故意没有用大写，也有set方法。
```

```java
public static void setOut(PrintStream out) {
    checkIO();
    setOut0(out);
}
public static void setErr(PrintStream err) {
    checkIO();
    setErr0(err);
}
public static void setIn(InputStream in) {
    checkIO();
    setIn0(in);
}
private static void checkIO() {
    SecurityManager sm = getSecurityManager();
    if (sm != null) {
        sm.checkPermission(new RuntimePermission("setIO"));
    }
}
private static native void setIn0(InputStream in);
private static native void setOut0(PrintStream out);
private static native void setErr0(PrintStream err);
```

**练习：**

Create a program named MyInput.java: Contain the methods for reading int, double, float, boolean, short, byte and String values from the keyboard.

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220412232254819.png" alt="image-20220412232254819" style="zoom:80%;" />

```java
package com.atguigu.java;
// MyInput.java: Contain the methods for reading int, double, float, boolean, short, byte and
// string values from the keyboard

import java.io.*;

public class MyInput {
    // Read a string from the keyboard
    public static String readString() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Declare and initialize the string
        String string = "";

        // Get the string from the keyboard
        try {
            string = br.readLine();

        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Return the string obtained from the keyboard
        return string;
    }

    // Read an int value from the keyboard
    public static int readInt() {
        return Integer.parseInt(readString());
    }

    // Read a double value from the keyboard
    public static double readDouble() {
        return Double.parseDouble(readString());
    }

    // Read a byte value from the keyboard
    public static double readByte() {
        return Byte.parseByte(readString());
    }

    // Read a short value from the keyboard
    public static double readShort() {
        return Short.parseShort(readString());
    }

    // Read a long value from the keyboard
    public static double readLong() {
        return Long.parseLong(readString());
    }

    // Read a float value from the keyboard
    public static double readFloat() {
        return Float.parseFloat(readString());
    }
}

```

### 8.2 打印流

- 实现将基本数据类型的数据格式转化为字符串输出。


- 打印流：`PrintStream`和`PrintWriter`

  - 提供了一系列重载的print()和println()方法，用于多种数据类型的输出

    ![image-20220131021502089](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220131021502089.png)

    ![image-20220131021528397](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220131021528397.png)

  - PrintStream和PrintWriter的输出不会抛出IOException异常

  - PrintStream和PrintWriter有自动flush功能

  - PrintStream 打印的所有字符都使用平台的默认字符编码转换为字节。在需要写入字符而不是写入字节的情况下，应该使用 PrintWriter 类。

  - System.out返回的是PrintStream的实例

- 构造器

  - PrintStream(File file) ：创建具有指定文件且不带自动行刷新的新打印流。 
  - PrintStream(File file, String csn)：创建具有指定文件名称和字符集且不带自动行刷新的新打印流。 
  - PrintStream(OutputStream out) ：创建新的打印流。 
  - PrintStream(OutputStream out, boolean autoFlush)：创建新的打印流。 autoFlush如果为 true，则每当写入 byte 数组、调用其中一个 println 方法或写入换行符或字节 ('\n') 时都会刷新输出缓冲区。
  - PrintStream(OutputStream out, boolean autoFlush, String encoding) ：创建新的打印流。 
  - PrintStream(String fileName)：创建具有指定文件名称且不带自动行刷新的新打印流。 
  - PrintStream(String fileName, String csn) ：创建具有指定文件名称和字符集且不带自动行刷新的新打印流。

- 代码举例1


```java
package com.atguigu.systemio;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TestPrintStream {
    public static void main(String[] args) throws FileNotFoundException {
        PrintStream ps = new PrintStream("io.txt");
        ps.println("hello");
        ps.println(1);
        ps.println(1.5);
        ps.close();
    }
}

```

- 代码举例2

```java
PrintStream ps = null;
try {
    FileOutputStream fos = new FileOutputStream(new File("D:\\IO\\text.txt"));
    // 创建打印输出流,设置为自动刷新模式(写入换行符或字节 '\n' 时都会刷新输出缓冲区)
    ps = new PrintStream(fos, true);
    if (ps != null) {// 把标准输出流(控制台输出)改成文件
        System.setOut(ps);
    }
    for (int i = 0; i <= 255; i++) { // 输出ASCII字符
        System.out.print((char) i);
        if (i % 50 == 0) { // 每50个数据一行
            System.out.println(); // 换行
        }
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
} finally {
    if (ps != null) {
        ps.close();
    }
}

```

- 代码举例3：自定义一个日志工具

```java
/*
日志工具
 */
public class Logger {
    /*
    记录日志的方法。
     */
    public static void log(String msg) {
        try {
            // 指向一个日志文件
            PrintStream out = new PrintStream(new FileOutputStream("log.txt", true));
            // 改变输出方向
            System.setOut(out);
            // 日期当前时间
            Date nowTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            String strTime = sdf.format(nowTime);

            System.out.println(strTime + ": " + msg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

```java
public class LogTest {
    public static void main(String[] args) {
        //测试工具类是否好用
        Logger.log("调用了System类的gc()方法，建议启动垃圾回收");
        Logger.log("调用了TeamView的addMember()方法");
        Logger.log("用户尝试进行登录，验证失败");
    }
}
```

### 8.3 Scanner类

构造方法

* Scanner(File source) ：构造一个新的 Scanner，它生成的值是从指定文件扫描的。 
* Scanner(File source, String charsetName) ：构造一个新的 Scanner，它生成的值是从指定文件扫描的。 
* Scanner(InputStream source) ：构造一个新的 Scanner，它生成的值是从指定的输入流扫描的。 
* Scanner(InputStream source, String charsetName) ：构造一个新的 Scanner，它生成的值是从指定的输入流扫描的。

常用方法：

* boolean hasNextXxx()： 如果通过使用nextXxx()方法，此扫描器输入信息中的下一个标记可以解释为默认基数中的一个 Xxx 值，则返回 true。
* Xxx nextXxx()： 将输入信息的下一个标记扫描为一个Xxx

```java
package com.atguigu.systemio;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class TestScanner {

    @Test
    public void test01() throws IOException {
        Scanner input = new Scanner(System.in);
        PrintStream ps = new PrintStream("1.txt");
        while(true){
            System.out.print("请输入一个单词：");
            String str = input.nextLine();
            if("stop".equals(str)){
                break;
            }
            ps.println(str);
        }
        input.close();
        ps.close();
    }
    
    @Test
    public void test2() throws IOException {
        Scanner input = new Scanner(new FileInputStream("1.txt"));
        while(input.hasNextLine()){
            String str = input.nextLine();
            System.out.println(str);
        }
        input.close();
    }
}
```

## 9. apache-common包的使用

### 9.1 介绍

IO技术开发中，代码量很大，而且代码的重复率较高，为此Apache软件基金会，开发了IO技术的工具类`commonsIO`，大大简化了IO开发。

Apahce软件基金会属于第三方，（Oracle公司第一方，我们自己第二方，其他都是第三方）我们要使用第三方开发好的工具，需要添加jar包。

### 9.2 导包及举例

- 在导入commons-io-2.5.jar包之后，内部的API都可以使用。


 ![image-20220416004246436](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220416004246436.png)

- IOUtils类的使用

```java
- 静态方法：IOUtils.copy(InputStream in,OutputStream out)传递字节流，实现文件复制。
- 静态方法：IOUtils.closeQuietly(任意流对象)悄悄的释放资源，自动处理close()方法抛出的异常。
```

```java
public class Test01 {
    public static void main(String[] args)throws Exception {
        //- 静态方法：IOUtils.copy(InputStream in,OutputStream out)传递字节流，实现文件复制。
        IOUtils.copy(new FileInputStream("E:\\Idea\\io\\1.jpg"),new FileOutputStream("E:\\Idea\\io\\file\\柳岩.jpg"));
        //- 静态方法：IOUtils.closeQuietly(任意流对象)悄悄的释放资源，自动处理close()方法抛出的异常。
       /* FileWriter fw = null;
        try {
            fw = new FileWriter("day21\\io\\writer.txt");
            fw.write("hahah");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
           IOUtils.closeQuietly(fw);
        }*/
    }
}
```

- FileUtils类的使用

```java
- 静态方法：void copyDirectoryToDirectory(File src,File dest)：整个目录的复制，自动进行递归遍历
          参数:
          src:要复制的文件夹路径
          dest:要将文件夹粘贴到哪里去
             
- 静态方法：void writeStringToFile(File file,String content)：将内容content写入到file中
- 静态方法：String readFileToString(File file)：读取文件内容，并返回一个String
- 静态方法：void copyFile(File srcFile,File destFile)：文件复制
```

```java
public class Test02 {
    public static void main(String[] args) {
        try {
            //- 静态方法：void copyDirectoryToDirectory(File src,File dest);
            FileUtils.copyDirectoryToDirectory(new File("E:\\Idea\\io\\aa"),new File("E:\\Idea\\io\\file"));


            //- 静态方法：writeStringToFile(File file,String str)
            FileUtils.writeStringToFile(new File("day21\\io\\commons.txt"),"柳岩你好");

            //- 静态方法：String readFileToString(File file)
            String s = FileUtils.readFileToString(new File("day21\\io\\commons.txt"));
            System.out.println(s);
            //- 静态方法：void copyFile(File srcFile,File destFile)
            FileUtils.copyFile(new File("io\\yangm.png"),new File("io\\yangm2.png"));
            System.out.println("复制成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```





## 10.随堂复习

### 1. File类的使用

- File类的一个实例对应着磁盘上的一个文件或文件目录。  ----> “万事万物皆对象”
- （熟悉）File的实例化、常用的方法
- File类中只有新建、删除、获取路径等方法，不包含读写文件的方法。此时需要使用IO流

### 2. IO流的概述

- IO流的分类
  - 流向：输入流、输出流
  - 处理数据单位：字节流、字符流
  - 流的角色：节点流、处理流
- IO的4个抽象基类：InputStream \ OutputStream \ Reader \ Writer

### 3. 节点流之：文件流

- FileInputStream \ FileOutputStream \ FileReader \ FileWriter
- （掌握）读写数据的过程。
  - 步骤1：创建File类的对象，作为读取或写出数据的端点
  - 步骤2：创建相关的流的对象
  - 步骤3：读取、写出数据的过程
  - 步骤4：关闭流资源

### 4. 处理流之一：缓冲流

- BufferedInputStream \ BufferedOutputStream \ BufferedReader \ BufferedWriter
- 作用：实现更高效的读写数据的操作

### 5. 处理流之二：转换流

- 层次1：熟悉转换流的使用
  - InputStreamReader、OutputStreamWriter
- 层次2：（掌握）字符的编码和解码的过程、常用的字符集
  - 解决相关的问题：读写字符出现乱码！本质问题：使用的解码集与编码集不一致。

### 6. 处理流之三：对象流

- 层次1：熟悉对象流的使用
  - ObjectInputStream:反序列化时需要使用的api
  - ObjectOutputStream:序列化时需要使用的api
- 层次2：对象的序列化机制
  - 使用场景：不同的进程之间通信、客户端（或浏览器端）与服务器端传输数据
  - 自定义类要想实现序列化机制需要满足的要求及注意点。

### 7. 其它流的使用

- 了解：数据流：DataInputStream 、DataOutputStream
- 了解：标准的输入流、标准的输出流：System.in 、System.out
- 了解：打印流：PrintStream、PrintWriter

## 11.企业真题

### 2.1 IO流概述

#### 1. 谈谈Java IO里面的常用类，字节流，字符流(银*数据)

略

#### 2. Java 中有几种类型的流？JDK为每种类型的流提供一些抽象类以供继承，请说出他们分别是哪些类？（上海*厦\*联网、极\*科技）

InputStream \ OutputStream \ Reader \ Writer

#### 3. 流一般需不需要关闭？如果关闭的话用什么方法？处理流是怎么关闭的？(银*数据)

需要。close()

处理流在关闭过程中，也会关闭内部的流。

#### 4. OutputStream里面的write()是什么意思?(君*科技)

数据写出的意思。

### 2.2 缓冲流

#### 1. BufferedReader属于哪种流？他主要是用来做什么的？(国*电网)

略

#### 2. 什么是缓冲区？有什么作用？(北京中油**)

内部提供了一个数组，将读取或要写出的数据，现在此数组中缓存。达到一定程度时，集中性的写出。

作用：减少与磁盘的交互，进而提升读写效率。

### 2.3 转换流

#### 1. 字节流和字符流是什么？怎么转换？(北京蓝\*、\*海*供应链管理）

![image-20221212161251100](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221212161251100.png)



### 2.4 序列化

#### 1. 什么是Java序列化，如何实现(君*科技、上海\*厦物联网)

```
对象序列化机制允许把内存中的Java对象转换成平台无关的二进制流，从而允许把这种二进制流持久地保存在磁盘上，
或通过网络将这种二进制流传输到另一个网络节点。//当其它程序获取了这种二进制流，就可以恢复成原来的Java对象。
```

#### 2. Java有些类中为什么需要实现Serializable接口？（阿*校招）

便于此类的对象实现序列化操作。



# 第15章_网络编程

***

## 1. 网络编程概述

Java是 Internet 上的语言，它从语言级上提供了对网络应用程序的支持，程序员能够很容易开发常见的网络应用程序。

Java提供的网络类库，可以实现无痛的网络连接，联网的底层细节被隐藏在 Java 的本机安装系统里，由 JVM 进行控制。并且 Java 实现了一个跨平台的网络库，`程序员面对的是一个统一的网络编程环境`。

### 1.1 软件架构

- **C/S架构** ：全称为Client/Server结构，是指客户端和服务器结构。常见程序有QQ、美团app、360安全卫士等软件。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1_cs.jpg" style="zoom:67%;" />

**B/S架构** ：全称为Browser/Server结构，是指浏览器和服务器结构。常见浏览器有IE、谷歌、火狐等。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/2_bs.jpg" style="zoom:67%;" />

两种架构各有优势，但是无论哪种架构，都离不开网络的支持。**网络编程**，就是在一定的协议下，实现两台计算机的通信的程序。

### 1.2 网络基础

- **计算机网络：**
  把分布在不同地理区域的计算机与专门的外部设备用通信线路互连成一个规模大、功能强的网络系统，从而使众多的计算机可以方便地互相传递信息、共享硬件、软件、数据信息等资源。

- **网络编程的目的：**<font color='red'>直接或间接地通过网络协议与其它计算机实现数据交换，进行通讯。</font>

- **网络编程中有三个主要的问题：**
  - 问题1：如何准确地定位网络上一台或多台主机
  - 问题2：如何定位主机上的特定的应用
  - 问题3：找到主机后，如何可靠、高效地进行数据传输

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220415001614195.png" alt="image-20220415001614195" style="zoom:67%;" />

## 2. 网络通信要素

### 2.1 如何实现网络中的主机互相通信

- 通信双方地址
  - IP
  - 端口号
- 一定的规则：不同的硬件、操作系统之间的通信，所有的这一切都需要一种规则。而我们就把这种规则称为协议，即网络通信协议。

**生活类比：**

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503144420721.png" alt="image-20220503144420721" style="zoom:67%;" />

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503144450241.png" alt="image-20220503144450241" style="zoom:67%;" />

### 2.2 通信要素一：IP地址和域名

#### 2.2.1 IP地址

**IP地址：指互联网协议地址（Internet Protocol Address）**，俗称IP。<font color='red'>IP地址用来给网络中的一台计算机设备做唯一的编号</font>。假如我们把“个人电脑”比作“一台电话”的话，那么“IP地址”就相当于“电话号码”。

**IP地址分类方式一：**

- `IPv4`：是一个32位的二进制数，通常被分为4个字节，表示成`a.b.c.d` 的形式，以点分`十进制`表示，例如`192.168.65.100` 。其中a、b、c、d都是0~255之间的十进制整数。

  - 

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220503145929568.png" alt="image-20220503145929568" style="zoom:80%;" />

  - 这种方式最多可以表示42亿个。其中，30亿都在北美，亚洲4亿，中国2.9亿。2011年初已经用尽。

  - IP地址 = 网络地址 +主机地址

    - 网络地址：标识计算机或网络设备所在的网段
    - 主机地址：标识特定主机或网络设备

    ![20220518_101503](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/20220518_101503.jpg)

    其中，E类用于科研。

- `IPv6`：由于互联网的蓬勃发展，IP地址的需求量愈来愈大，但是网络地址资源有限，使得IP的分配越发紧张。

  为了扩大地址空间，拟通过IPv6重新定义地址空间，采用128位地址长度，共16个字节，写成8个无符号整数，每个整数用四个十六进制位表示，数之间用冒号（：）分开。比如：`ABCD:EF01:2345:6789:ABCD:EF01:2345:6789`，按保守方法估算IPv6实际可分配的地址，整个地球的每平方米面积上仍可分配1000多个地址，这样就解决了网络地址资源数量不够的问题。2012年6月6日，国际互联网协会举行了世界IPv6启动纪念日，这一天，全球IPv6网络正式启动。多家知名网站，如Google、Facebook和Yahoo等，于当天全球标准时间0点（北京时间8点整）开始永久性支持IPv6访问。2018年6月，三大运营商联合阿里云宣布，将全面对外提供IPv6服务，并计划在2025年前助推中国互联网真正实现“IPv6 Only”。

  在IPv6的设计过程中除了一劳永逸地解决了地址短缺问题以外，还考虑了在IPv4中解决不好的其它问题，主要有端到端IP连接、服务质量（QoS）、安全性、多播、移动性、即插即用等。

**IP地址分类方式二：**

公网地址( 万维网使用）和 私有地址( 局域网使用）。192.168.开头的就是私有地址，范围即为192.168.0.0--192.168.255.255，专门为组织机构内部使用。

**常用命令：**

- 查看本机IP地址，在控制台输入：

```java
ipconfig
```

- 检查网络是否连通，在控制台输入：

```java
ping 空格 IP地址
ping 220.181.57.216
```

**特殊的IP地址：**

- 本地回环地址(hostAddress)：`127.0.0.1`  
- 主机名(hostName)：`localhost`

#### 2.2.2 域名

Internet上的主机有两种方式表示地址：

- 域名(hostName)：www.atguigu.com
- IP 地址(hostAddress)：202.108.35.210

**域名解析：**因为IP地址数字不便于记忆，因此出现了域名。域名容易记忆，当在连接网络时输入一个主机的域名后，域名服务器(DNS，Domain Name System，域名系统)负责将域名转化成IP地址，这样才能和主机建立连接。 

简单理解：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220415003729013.png" alt="image-20220415003729013" style="zoom:80%;" />

详细理解：

![image-20220131125934581](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220131125934581.png)

1. 在浏览器中输入www . qq .com 域名，操作系统会先检查自己本地的`hosts文件`是否有这个网址映射关系，如果有，就先调用这个IP地址映射，完成域名解析。
2. 如果hosts里没有这个域名的映射，则查找`本地DNS解析器缓存`，是否有这个网址映射关系，如果有，直接返回，完成域名解析。
3. 如果hosts与本地DNS解析器缓存都没有相应的网址映射关系，首先会找TCP/IP参数中设置的首选DNS服务器，在此我们叫它`本地DNS服务器`，此服务器收到查询时，如果要查询的域名，包含在本地配置区域资源中，则返回解析结果给客户机，完成域名解析，此解析具有权威性。
4. 如果要查询的域名，不由本地DNS服务器区域解析，但该服务器已`缓存`了此网址映射关系，则调用这个IP地址映射，完成域名解析，此解析不具有权威性。
5. 如果本地DNS服务器本地区域文件与缓存解析都失效，则根据本地DNS服务器的设置（是否设置转发器）进行查询，如果未用转发模式，本地DNS就把请求发至13台根DNS，根DNS服务器收到请求后会判断这个域名(.com)是谁来授权管理，并会返回一个负责该顶级域名服务器的一个IP。本地DNS服务器收到IP信息后，将会联系负责.com域的这台服务器。这台负责.com域的服务器收到请求后，如果自己无法解析，它就会找一个管理.com域的下一级DNS服务器地址(http://qq.com)给本地DNS服务器。当本地DNS服务器收到这个地址后，就会找（http://qq.com)域服务器，重复上面的动作，进行查询，直至找到www.qq.com主机。
6. 如果用的是转发模式，此DNS服务器就会把请求转发至上一级DNS服务器，由上一级服务器进行解析，上一级服务器如果不能解析，或找根DNS或把转请求转至上上级，以此循环。不管是本地DNS服务器用是是转发，还是根提示，最后都是把结果返回给本地DNS服务器，由此DNS服务器再返回给客户机。

### 2.3 通信要素二：端口号

网络的通信，本质上是两个进程（应用程序）的通信。每台计算机都有很多的进程，那么在网络通信时，如何区分这些进程呢？

如果说**IP地址**可以唯一标识网络中的设备，那么<font color='red'>**端口号**就可以唯一标识设备中的进程（应用程序）</font>。

不同的进程，设置不同的端口号。

- **端口号：用两个字节表示的整数，它的取值范围是0~65535**。
  - 公认端口：0~1023。被预先定义的服务通信占用，如：HTTP（80），FTP（21），Telnet（23）
  - 注册端口：1024~49151。分配给用户进程或应用程序。如：Tomcat（8080），MySQL（3306），Oracle（1521）。
  - 动态/ 私有端口：49152~65535。

如果端口号被另外一个服务或应用所占用，会导致当前程序启动失败。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220415004400166.png" alt="image-20220415004400166" style="zoom:80%;" />

### 2.4 通信要素三：网络通信协议

通过计算机网络可以使多台计算机实现连接，位于同一个网络中的计算机在进行连接和通信时需要遵守一定的规则，这就好比在道路中行驶的汽车一定要遵守交通规则一样。

- `网络通信协议`：在计算机网络中，这些连接和通信的规则被称为网络通信协议，它对数据的传输格式、传输速率、传输步骤、出错控制等做了统一规定，通信双方必须同时遵守才能完成数据交换。

**新的问题：网络协议涉及内容太多、太复杂。如何解决？**

计算机网络通信涉及内容很多，比如指定源地址和目标地址，加密解密，压缩解压缩，差错控制，流量控制，路由控制，如何实现如此复杂的网络协议呢？`通信协议分层思想`。

在制定协议时，把复杂成份分解成一些简单的成份，再将它们复合起来。最常用的复合方式是层次方式，即`同层间可以通信、上一层可以调用下一层，而与再下一层不发生关系`。各层互不影响，利于系统的开发和扩展。

这里有两套参考模型

- OSI参考模型：模型过于理想化，未能在因特网上进行广泛推广
- TCP/IP参考模型(或TCP/IP协议)：事实上的国际标准。

![image-20220514172130459](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514172130459.png)

上图中，OSI参考模型：模型`过于理想化`，未能在因特网上进行广泛推广。 TCP/IP参考模型(或TCP/IP协议)：事实上的`国际标准`。

- **TCP/IP协议：** 传输控制协议/因特网互联协议( Transmission Control Protocol/Internet Protocol)，TCP/IP 以其两个主要协议：传输控制协议(TCP)和网络互联协议(IP)而得名，实际上是一组协议，包括多个具有不同功能且互为关联的协议。是Internet最基本、最广泛的协议。

  <img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220512234904047.png" alt="image-20220512234904047" style="zoom:80%;" />

**TCP/IP协议中的四层介绍：**

- `应用层`：应用层决定了向用户提供应用服务时通信的活动。主要协议有：HTTP协议、FTP协议、SNMP（简单网络管理协议）、SMTP（简单邮件传输协议）和POP3（Post Office Protocol 3的简称,即邮局协议的第3个版）等。
- `传输层`：主要使网络程序进行通信，在进行网络通信时，可以采用TCP协议，也可以采用UDP协议。TCP（Transmission Control Protocol）协议，即传输控制协议，是一种面向连接的、可靠的、基于字节流的传输层通信协议。UDP(User Datagram Protocol，用户数据报协议)：是一个无连接的传输层协议、提供面向事务的简单不可靠的信息传送服务。
- `网络层`：网络层是整个TCP/IP协议的核心，支持网间互连的数据通信。它主要用于将传输的数据进行分组，将分组数据发送到目标计算机或者网络。而IP协议是一种非常重要的协议。IP（internet protocal）又称为互联网协议。IP的责任就是把数据从源传送到目的地。它在源地址和目的地址之间传送一种称之为数据包的东西，它还提供对数据大小的重新组装功能，以适应不同网络对包大小的要求。

* `物理+数据链路层`：链路层是用于定义物理传输通道，通常是对某些网络连接设备的驱动协议，例如针对光纤、网线提供的驱动。

![image-20220514172435398](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514172435398.png)

## 2. 谈传输层协议：TCP与UDP协议

通信的协议还是比较复杂的，`java.net` 包中包含的类和接口，它们提供低层次的通信细节。我们可以直接使用这些类和接口，来专注于网络程序开发，而不用考虑通信的细节。

`java.net` 包中提供了两种常见的网络协议的支持：

- **UDP**：用户数据报协议(User Datagram Protocol)。
- **TCP**：传输控制协议 (Transmission Control Protocol)。

### 2.1 TCP协议与UDP协议

**TCP协议：**

- TCP协议进行通信的两个应用进程：客户端、服务端。
- 使用TCP协议前，须先`建立TCP连接`，形成基于字节流的传输数据通道
- 传输前，采用“三次握手”方式，点对点通信，是`可靠的`
  - TCP协议使用`重发机制`，当一个通信实体发送一个消息给另一个通信实体后，需要收到另一个通信实体确认信息，如果没有收到另一个通信实体确认信息，则会再次重复刚才发送的消息。
- 在连接中可进行`大数据量的传输`
- 传输完毕，需`释放已建立的连接，效率低`

**UDP协议：**

- UDP协议进行通信的两个应用进程：发送端、接收端。
- 将数据、源、目的封装成数据包（传输的基本单位），`不需要建立连接`
- 发送不管对方是否准备好，接收方收到也不确认，不能保证数据的完整性，故是`不可靠的`
- 每个数据报的大小限制在`64K`内
- 发送数据结束时`无需释放资源，开销小，通信效率高`
- 适用场景：音频、视频和普通数据的传输。例如视频会议

> TCP生活案例：打电话
>
> UDP生活案例：发送短信、发电报

### 2.2 三次握手

TCP协议中，在发送数据的准备阶段，客户端与服务器之间的三次交互，以保证连接的可靠。

- 第一次握手，客户端向服务器端发起TCP连接的请求
- 第二次握手，服务器端发送针对客户端TCP连接请求的确认
- 第三次握手，客户端发送确认的确认

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220415010105484.png" alt="image-20220415010105484" style="zoom:80%;" />

> 1、客户端会随机一个初始序列号seq=x，设置SYN=1 ，表示这是SYN握手报文。然后就可以把这个 SYN 报文发送给服务端了，表示向服务端发起连接，之后客户端处于`同步已发送`状态。
>
> 2、服务端收到客户端的 SYN 报文后，也随机一个初始序列号(seq=y)，设置ack=x+1，表示收到了客户端的x之前的数据，希望客户端下次发送的数据从x+1开始。
> 设置 SYN=1 和 ACK=1。表示这是一个SYN握手和ACK确认应答报文。最后把该报文发给客户端，该报文也不包含应用层数据，之后服务端处于`同步已接收`状态。
>
> 3、客户端收到服务端报文后，还要向服务端回应最后一个应答报文，将ACK置为 1 ，表示这是一个应答报文
> ack=y+1 ，表示收到了服务器的y之前的数据，希望服务器下次发送的数据从y+1开始。
> 最后把报文发送给服务端，这次报文可以携带数据，之后客户端处于 连接已建立 状态。服务器收到客户端的应答报文后，也进入`连接已建立`状态。

完成三次握手，连接建立后，客户端和服务器就可以开始进行数据传输了。由于这种面向连接的特性，TCP协议可以保证传输数据的安全，所以应用十分广泛，例如下载文件、浏览网页等。

### 2.3 四次挥手

TCP协议中，在发送数据结束后，释放连接时需要经过四次挥手。

* 第一次挥手：客户端向服务器端提出结束连接，`让服务器做最后的准备工作`。此时，客户端处于半关闭状态，即表示不再向服务器发送数据了，但是还可以接受数据。
* 第二次挥手：服务器接收到客户端释放连接的请求后，`会将最后的数据发给客户端`。并告知上层的应用进程不再接收数据。
* 第三次挥手：服务器发送完数据后，会给客户端`发送一个释放连接的报文`。那么客户端接收后就知道可以正式释放连接了。
* 第四次挥手：客户端接收到服务器最后的释放连接报文后，要`回复一个彻底断开的报文`。这样服务器收到后才会彻底释放连接。这里客户端，发送完最后的报文后，会等待2MSL，因为有可能服务器没有收到最后的报文，那么服务器迟迟没收到，就会再次给客户端发送释放连接的报文，此时客户端在等待时间范围内接收到，会重新发送最后的报文，并重新计时。如果等待2MSL后，没有收到，那么彻底断开。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220415010205891.png" alt="image-20220415010205891" style="zoom:80%;" />

> 1、客户端打算断开连接，向服务器发送FIN报文(FIN标记位被设置为1，1表示为FIN，0表示不是)，FIN报文中会指定一个序列号，之后客户端进入FIN_WAIT_1状态。也就是客户端发出连接释放报文段(FIN报文)，指定序列号seq = u，主动关闭TCP连接，等待服务器的确认。
>
> 2、服务器收到连接释放报文段(FIN报文)后，就向客户端发送ACK应答报文，以客户端的FIN报文的序列号 seq+1 作为ACK应答报文段的确认序列号ack = seq+1 = u + 1。接着服务器进入CLOSE_WAIT(等待关闭)状态，此时的TCP处于半关闭状态(下面会说什么是半关闭状态)，客户端到服务器的连接释放。客户端收到来自服务器的ACK应答报文段后，进入FIN_WAIT_2状态。
>
> 3、服务器也打算断开连接，向客户端发送连接释放(FIN)报文段，之后服务器进入LASK_ACK(最后确认)状态，等待客户端的确认。服务器的连接释放(FIN)报文段的FIN=1，ACK=1，序列号seq=m，确认序列号ack=u+1。
>
> 4、客户端收到来自服务器的连接释放(FIN)报文段后，会向服务器发送一个ACK应答报文段，以连接释放(FIN)报文段的确认序号 ack 作为ACK应答报文段的序列号 seq，以连接释放(FIN)报文段的序列号 seq+1作为确认序号ack。
>
> 之后客户端进入TIME_WAIT(时间等待)状态，服务器收到ACK应答报文段后，服务器就进入CLOSE(关闭)状态，到此服务器的连接已经完成关闭。客户端处于TIME_WAIT状态时，此时的TCP还未释放掉，需要等待2MSL后，客户端才进入CLOSE状态。

## 3. 网络编程API

### 3.1 InetAddress类

InetAddress类主要表示IP地址，两个子类：Inet4Address、Inet6Address。

InetAddress 类没有提供公共的构造器，而是提供 了 如下几个 静态方法来获取InetAddress 实例

* public static InetAddress getLocalHost()
* public static InetAddress getByName(String host)
* public static InetAddress getByAddress(byte[] addr)

InetAddress 提供了如下几个常用的方法

* public String getHostAddress() ：返回 IP 地址字符串（以文本表现形式）
* public String getHostName() ：获取此 IP 地址的主机名
* public boolean isReachable(int timeout)：测试是否可以达到该地址

```java
package com.atguigu.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class TestInetAddress {
    @Test
    public void test01() throws UnknownHostException{
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);
    }

    @Test
    public void test02()throws UnknownHostException{
        InetAddress atguigu = InetAddress.getByName("www.atguigu.com");
        System.out.println(atguigu);
    }

    @Test
    public void test03()throws UnknownHostException{
//		byte[] addr = {112,54,108,98};
        byte[] addr = {(byte)192,(byte)168,24,56};
        InetAddress atguigu = InetAddress.getByAddress(addr);
        System.out.println(atguigu);
    }
}
```

![1564024137998](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/1564024137998.png)

### 3.2 Socket类

-  <font color='red'>网络上具有唯一标识的IP地址和端口号组合在一起构成唯一能识别的标识符套接字（Socket）</font>。
-  利用套接字(Socket)开发网络应用程序早已被广泛的采用，以至于成为事实上的标准。网络通信其实就是Socket间的通信。


- 通信的两端都要有Socket，是两台机器间通信的端点。

- Socket允许程序把网络连接当成一个流，数据在两个Socket间通过IO传输。

- 一般主动发起通信的应用程序属客户端，等待通信请求的为服务端。

- Socket分类：
  - 流套接字（stream socket）：使用TCP提供可依赖的字节流服务
    - ServerSocket：此类实现TCP服务器套接字。服务器套接字等待请求通过网络传入。
    - Socket：此类实现客户端套接字（也可以就叫“套接字”）。套接字是两台机器间通信的端点。
  - 数据报套接字（datagram socket）：使用UDP提供“尽力而为”的数据报服务
    - DatagramSocket：此类表示用来发送和接收UDP数据报包的套接字。 

### 3.3 Socket相关类API

#### 3.3.1 ServerSocket类

**ServerSocket类的构造方法：**

* ServerSocket(int port) ：创建绑定到特定端口的服务器套接字。

**ServerSocket类的常用方法：**

* Socket accept()：侦听并接受到此套接字的连接。 

#### 3.3.2 Socket类

**Socket类的常用构造方法**：

* public Socket(InetAddress address,int port)：创建一个流套接字并将其连接到指定 IP 地址的指定端口号。
* public Socket(String host,int port)：创建一个流套接字并将其连接到指定主机上的指定端口号。

**Socket类的常用方法**：

* public InputStream getInputStream()：返回此套接字的输入流，可以用于接收消息
* public OutputStream getOutputStream()：返回此套接字的输出流，可以用于发送消息
* public InetAddress getInetAddress()：此套接字连接到的远程 IP 地址；如果套接字是未连接的，则返回 null。
* public InetAddress getLocalAddress()：获取套接字绑定的本地地址。
* public int getPort()：此套接字连接到的远程端口号；如果尚未连接套接字，则返回 0。
* public int getLocalPort()：返回此套接字绑定到的本地端口。如果尚未绑定套接字，则返回 -1。
* public void close()：关闭此套接字。套接字被关闭后，便不可在以后的网络连接中使用（即无法重新连接或重新绑定）。需要创建新的套接字对象。 关闭此套接字也将会关闭该套接字的 InputStream 和 OutputStream。 
* public void shutdownInput()：如果在套接字上调用 shutdownInput() 后从套接字输入流读取内容，则流将返回 EOF（文件结束符）。 即不能在从此套接字的输入流中接收任何数据。
* public void shutdownOutput()：禁用此套接字的输出流。对于 TCP 套接字，任何以前写入的数据都将被发送，并且后跟 TCP 的正常连接终止序列。 如果在套接字上调用 shutdownOutput() 后写入套接字输出流，则该流将抛出 IOException。 即不能通过此套接字的输出流发送任何数据。

**注意：**先后调用Socket的shutdownInput()和shutdownOutput()方法，仅仅关闭了输入流和输出流，并不等于调用Socket的close()方法。在通信结束后，仍然要调用Scoket的close()方法，因为只有该方法才会释放Socket占用的资源，比如占用的本地端口号等。

#### 3.3.3 DatagramSocket类

**DatagramSocket 类的常用方法：**

* public DatagramSocket(int port)创建数据报套接字并将其绑定到本地主机上的指定端口。套接字将被绑定到通配符地址，IP 地址由内核来选择。
* public DatagramSocket(int port,InetAddress laddr)创建数据报套接字，将其绑定到指定的本地地址。本地端口必须在 0 到 65535 之间（包括两者）。如果 IP 地址为 0.0.0.0，套接字将被绑定到通配符地址，IP 地址由内核选择。 
* public void close()关闭此数据报套接字。 
* public void send(DatagramPacket p)从此套接字发送数据报包。DatagramPacket 包含的信息指示：将要发送的数据、其长度、远程主机的 IP 地址和远程主机的端口号。 
* public void receive(DatagramPacket p)从此套接字接收数据报包。当此方法返回时，DatagramPacket 的缓冲区填充了接收的数据。数据报包也包含发送方的 IP 地址和发送方机器上的端口号。 此方法在接收到数据报前一直阻塞。数据报包对象的 length 字段包含所接收信息的长度。如果信息比包的长度长，该信息将被截短。 
* public InetAddress getLocalAddress()获取套接字绑定的本地地址。
* public int getLocalPort()返回此套接字绑定的本地主机上的端口号。 
* public InetAddress getInetAddress()返回此套接字连接的地址。如果套接字未连接，则返回 null。
* public int getPort()返回此套接字的端口。如果套接字未连接，则返回 -1。

#### 3.3.4 DatagramPacket类

**DatagramPacket类的常用方法：**

* public DatagramPacket(byte[] buf,int length)构造 DatagramPacket，用来接收长度为 length 的数据包。 length 参数必须小于等于 buf.length。
* public DatagramPacket(byte[] buf,int length,InetAddress address,int port)构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。length 参数必须小于等于 buf.length。
* public InetAddress getAddress()返回某台机器的 IP 地址，此数据报将要发往该机器或者是从该机器接收到的。
* public int getPort()返回某台远程主机的端口号，此数据报将要发往该主机或者是从该主机接收到的。
* `public byte[] getData()`返回数据缓冲区。接收到的或将要发送的数据从缓冲区中的偏移量 offset 处开始，持续 length 长度。
* `public int getLength()`返回将要发送或接收到的数据的长度。

## 4. TCP网络编程

### 4.1 通信模型

Java语言的基于套接字TCP编程分为服务端编程和客户端编程，其通信模型如图所示：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514172833216.png" alt="image-20220514172833216" style="zoom:67%;" />

### 4.2 开发步骤

**客户端程序包含以下四个基本的步骤 ：**

* 创建 Socket ：根据指定服务端的 IP 地址或端口号构造 Socket 类对象。若服务器端响应，则建立客户端到服务器的通信线路。若连接失败，会出现异常。
* 打开连接到 Socket  的输入/ 出流： 使用 getInputStream()方法获得输入流，使用getOutputStream()方法获得输出流，进行数据传输
* 按照一定的协议对 Socket 进行读/ 写操作：通过输入流读取服务器放入线路的信息（但不能读取自己放入线路的信息），通过输出流将信息写入线路。
* 关闭 Socket ：断开客户端到服务器的连接，释放线路

**服务器端程序包含以下四个基本的 步骤：**

* 调用 ServerSocket(int port)  ：创建一个服务器端套接字，并绑定到指定端口上。用于监听客户端的请求。
* 调用 accept() ：监听连接请求，如果客户端请求连接，则接受连接，返回通信套接字对象。
* 调用  该Socket 类对象的 getOutputStream()  和 getInputStream () ：获取输出流和输入流，开始网络数据的发送和接收。
* 关闭Socket 对象：客户端访问结束，关闭通信套接字。

### 4.3 例题与练习

> 例题1：客户端发送内容给服务端，服务端将内容打印到控制台上。
>
> 例题2：客户端发送文件给服务端，服务端将文件保存在本地。
>
> 例题3：从客户端发送文件给服务端，服务端保存到本地。并返回“发送成功”给客户端。并关闭相应的连接。

练习1：服务端读取图片并发送给客户端，客户端保存图片到本地

练习2：客户端给服务端发送文本，服务端会将文本转成大写在返回给客户端。

**演示单个客户端与服务器单次通信：**

需求：客户端连接服务器，连接成功后给服务发送“lalala”，服务器收到消息后，给客户端返回“欢迎登录”，客户端接收消息后，断开连接

**1、服务器端示例代码**

```java
package com.atguigu.tcp.one;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args)throws Exception {
        //1、准备一个ServerSocket对象，并绑定8888端口
        ServerSocket server =  new ServerSocket(8888);
        System.out.println("等待连接....");

        //2、在8888端口监听客户端的连接，该方法是个阻塞的方法，如果没有客户端连接，将一直等待
        Socket socket = server.accept();
        InetAddress inetAddress = socket.getInetAddress();
        System.out.println(inetAddress.getHostAddress() + "客户端连接成功！！");

        //3、获取输入流，用来接收该客户端发送给服务器的数据
        InputStream input = socket.getInputStream();
        //接收数据
        byte[] data = new byte[1024];
        StringBuilder s = new StringBuilder();
        int len;
        while ((len = input.read(data)) != -1) {
            s.append(new String(data, 0, len));
        }
        System.out.println(inetAddress.getHostAddress() + "客户端发送的消息是：" + s);

        //4、获取输出流，用来发送数据给该客户端
        OutputStream out = socket.getOutputStream();
        //发送数据
        out.write("欢迎登录".getBytes());
        out.flush();

        //5、关闭socket，不再与该客户端通信
        //socket关闭，意味着InputStream和OutputStream也关闭了
        socket.close();

        //6、如果不再接收任何客户端通信，可以关闭ServerSocket
        server.close();
    }
}

```

**2、客户端示例代码**

```java
package com.atguigu.tcp.one;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        // 1、准备Socket，连接服务器，需要指定服务器的IP地址和端口号
        Socket socket = new Socket("127.0.0.1", 8888);

        // 2、获取输出流，用来发送数据给服务器
        OutputStream out = socket.getOutputStream();
        // 发送数据
        out.write("lalala".getBytes());
        //会在流末尾写入一个“流的末尾”标记，对方才能读到-1，否则对方的读取方法会一致阻塞
        socket.shutdownOutput();

        //3、获取输入流，用来接收服务器发送给该客户端的数据
        InputStream input = socket.getInputStream();
        // 接收数据
        byte[] data = new byte[1024];
        StringBuilder s = new StringBuilder();
        int len;
        while ((len = input.read(data)) != -1) {
            s.append(new String(data, 0, len));
        }
        System.out.println("服务器返回的消息是：" + s);

        //4、关闭socket，不再与服务器通信，即断开与服务器的连接
        //socket关闭，意味着InputStream和OutputStream也关闭了
        socket.close();
    }
}

```



**演示多个客户端与服务器之间的多次通信：**

通常情况下，服务器不应该只接受一个客户端请求，而应该不断地接受来自客户端的所有请求，所以Java程序通常会通过循环，不断地调用ServerSocket的accept()方法。

如果服务器端要“同时”处理多个客户端的请求，因此服务器端需要为**每一个客户端单独分配一个线程**来处理，否则无法实现“同时”。

咱们之前学习IO流的时候，提到过装饰者设计模式，该设计使得不管底层IO流是怎样的节点流：文件流也好，网络Socket产生的流也好，程序都可以将其包装成处理流，甚至可以多层包装，从而提供更多方便的处理。

案例需求：多个客户端连接服务器，并进行多次通信

* 每一个客户端连接成功后，从键盘输入英文单词或中国成语，并发送给服务器
* 服务器收到客户端的消息后，把词语“反转”后返回给客户端
* 客户端接收服务器返回的“词语”，打印显示
* 当客户端输入“stop”时断开与服务器的连接
* 多个客户端可以同时给服务器发送“词语”，服务器可以“同时”处理多个客户端的请求

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514173031651.png" alt="image-20220514173031651" style="zoom:80%;" />

**1、服务器端示例代码**

```java
package com.atguigu.tcp.many;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        // 1、准备一个ServerSocket
        ServerSocket server = new ServerSocket(8888);
        System.out.println("等待连接...");

        int count = 0;
        while(true){
            // 2、监听一个客户端的连接
            Socket socket = server.accept();
            System.out.println("第" + ++count + "个客户端"+socket.getInetAddress().getHostAddress()+"连接成功！！");

            ClientHandlerThread ct = new ClientHandlerThread(socket);
            ct.start();
        }

        //这里没有关闭server，永远监听
    }
    static class ClientHandlerThread extends Thread{
        private Socket socket;
        private String ip;

        public ClientHandlerThread(Socket socket) {
            super();
            this.socket = socket;
            ip = socket.getInetAddress().getHostAddress();
        }

        public void run(){
            try{
                //（1）获取输入流，用来接收该客户端发送给服务器的数据
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //（2）获取输出流，用来发送数据给该客户端
                PrintStream ps = new PrintStream(socket.getOutputStream());
                String str;
                // （3）接收数据
                while ((str = br.readLine()) != null) {
                    //（4）反转
                    StringBuilder word = new StringBuilder(str);
                    word.reverse();

                    //（5）返回给客户端
                    ps.println(word);
                }
                System.out.println("客户端" + ip+"正常退出");
            }catch(Exception  e){
                System.out.println("客户端" + ip+"意外退出");
            }finally{
                try {
                    //（6）断开连接
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

```

**2、客户端示例代码**

```java
package com.atguigu.tcp.many;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        // 1、准备Socket，连接服务器，需要指定服务器的IP地址和端口号
        Socket socket = new Socket("127.0.0.1", 8888);

        // 2、获取输出流，用来发送数据给服务器
        OutputStream out = socket.getOutputStream();
        PrintStream ps = new PrintStream(out);

        // 3、获取输入流，用来接收服务器发送给该客户端的数据
        InputStream input = socket.getInputStream();
        BufferedReader br;
        if(args!= null && args.length>0) {
            String encoding = args[0];
            br = new BufferedReader(new InputStreamReader(input,encoding));
        }else{
            br = new BufferedReader(new InputStreamReader(input));
        }

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("输入发送给服务器的单词或成语：");
            String message = scanner.nextLine();
            if(message.equals("stop")){
                socket.shutdownOutput();
                break;
            }

            // 4、 发送数据
            ps.println(message);
            // 接收数据
            String feedback  = br.readLine();
            System.out.println("从服务器收到的反馈是：" + feedback);
        }

        //5、关闭socket，断开与服务器的连接
        scanner.close();
        socket.close();
    }
}
```

### 4.4 案例：聊天室

服务端：

```java
package com.atguigu.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TestChatServer {
	//这个集合用来存储所有在线的客户端
	static ArrayList<Socket> online = new  ArrayList<Socket>();
	
	public static void main(String[] args)throws Exception {
		//1、启动服务器，绑定端口号
		ServerSocket server = new ServerSocket(8989);
		
		//2、接收n多的客户端同时连接
		while(true){
			Socket accept = server.accept();
			
			online.add(accept);//把新连接的客户端添加到online列表中
			
			MessageHandler mh = new MessageHandler(accept);
			mh.start();//
		}
	}
	
	static class MessageHandler extends Thread{
		private Socket socket;
		private String ip;
		
		public MessageHandler(Socket socket) {
			super();
			this.socket = socket;
		}

		public void run(){
			try {
				ip = socket.getInetAddress().getHostAddress();
				
				//插入：给其他客户端转发“我上线了”
				sendToOther(ip+"上线了");
				
				//(1)接收该客户端的发送的消息
				InputStream input = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(input);
				BufferedReader br = new BufferedReader(reader);
				
				String str;
				while((str = br.readLine())!=null){
					//(2)给其他在线客户端转发
					sendToOther(ip+":"+str);
				}
				
				sendToOther(ip+"下线了");
			} catch (IOException e) {
				try {
					sendToOther(ip+"掉线了");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}finally{
				//从在线人员中移除我
				online.remove(socket);
			}
		}
		
		//封装一个方法：给其他客户端转发xxx消息
		public void sendToOther(String message) throws IOException{
			//遍历所有的在线客户端，一一转发
			for (Socket on : online) {
				OutputStream every = on.getOutputStream();
				//为什么用PrintStream？目的用它的println方法，按行打印
				PrintStream ps = new PrintStream(every);
				
				ps.println(message);
			}
		}
	}
}


```

客户端：

```java
package com.atguigu.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TestChatClient {
	public static void main(String[] args)throws Exception {
		//1、连接服务器
		Socket socket = new Socket("127.0.0.1",8989);
		
		//2、开启两个线程
		//(1)一个线程负责看别人聊，即接收服务器转发的消息
		Receive receive = new Receive(socket);
		receive.start();
		
		//(2)一个线程负责发送自己的话
		Send send = new Send(socket);
		send.start();
		
		send.join();//等我发送线程结束了，才结束整个程序
		
		socket.close();
	}
}
class Send extends Thread{
	private Socket socket;
	
	public Send(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run(){
		try {
			OutputStream outputStream = socket.getOutputStream();
			//按行打印
			PrintStream ps = new PrintStream(outputStream);
			
			Scanner input = new Scanner(System.in);
			
			//从键盘不断的输入自己的话，给服务器发送，由服务器给其他人转发
			while(true){
				System.out.print("自己的话：");
				String str = input.nextLine();
				if("bye".equals(str)){
					break;
				}
				ps.println(str);
			}
			
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
class Receive extends Thread{
	private Socket socket;
	
	public Receive(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public void run(){
		try {
			InputStream inputStream = socket.getInputStream();
			Scanner input = new Scanner(inputStream);
			
			while(input.hasNextLine()){
				String line = input.nextLine();
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```



### 4.5 理解客户端、服务端

- 客户端：
  - 自定义
  - 浏览器(browser --- server)

- 服务端：
  - 自定义
  - Tomcat服务器

## 5. UDP网络编程

UDP(User Datagram Protocol，用户数据报协议)：是一个无连接的传输层协议、提供面向事务的简单不可靠的信息传送服务，类似于短信。

### 5.1 通信模型

UDP协议是一种**面向非连接**的协议，面向非连接指的是在正式通信前不必与对方先建立连接，不管对方状态就直接发送，至于对方是否可以接收到这些数据内容，UDP协议无法控制，因此说，UDP协议是一种**不可靠的**协议。无连接的好处就是快，省内存空间和流量，因为维护连接需要创建大量的数据结构。UDP会尽最大努力交付数据，但不保证可靠交付，没有TCP的确认机制、重传机制，如果因为网络原因没有传送到对端，UDP也不会给应用层返回错误信息。

UDP协议是面向数据报文的信息传送服务。UDP在发送端没有缓冲区，对于应用层交付下来的报文在添加了首部之后就直接交付于ip层，不会进行合并，也不会进行拆分，而是一次交付一个完整的报文。比如我们要发送100个字节的报文，我们调用一次send()方法就会发送100字节，接收方也需要用receive()方法一次性接收100字节，不能使用循环每次获取10个字节，获取十次这样的做法。

UDP协议没有拥塞控制，所以当网络出现的拥塞不会导致主机发送数据的速率降低。虽然UDP的接收端有缓冲区，但是这个缓冲区只负责接收，并不会保证UDP报文的到达顺序是否和发送的顺序一致。因为网络传输的时候，由于网络拥塞的存在是很大的可能导致先发的报文比后发的报文晚到达。如果此时缓冲区满了，后面到达的报文将直接被丢弃。这个对实时应用来说很重要，比如：视频通话、直播等应用。

因此UDP适用于一次只传送少量数据、对可靠性要求不高的应用环境，数据报大小限制在64K以下。

![image-20220131144712460](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220131144712460.png)

类 DatagramSocket 和 DatagramPacket 实现了基于 UDP 协议网络程序。

 UDP数据报通过数据报套接字 DatagramSocket 发送和接收，系统不保证  UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达。

 DatagramPacket 对象封装了UDP数据报，在数据报中包含了发送端的IP地址和端口号以及接收端的IP地址和端口号。

 UDP协议中每个数据报都给出了完整的地址信息，因此无须建立发送方和接收方的连接。如同发快递包裹一样。

### 5.2 开发步骤

**发送端程序包含以下四个基本的步骤：**

* 创建DatagramSocket ：默认使用系统随机分配端口号。
* 创建DatagramPacket：将要发送的数据用字节数组表示，并指定要发送的数据长度，接收方的IP地址和端口号。
* 调用  该DatagramSocket 类对象的 send方法 ：发送数据报DatagramPacket对象。
* 关闭DatagramSocket 对象：发送端程序结束，关闭通信套接字。

**接收端程序包含以下四个基本的步骤 ：**

* 创建DatagramSocket ：指定监听的端口号。
* 创建DatagramPacket：指定接收数据用的字节数组，起到临时数据缓冲区的效果，并指定最大可以接收的数据长度。
* 调用  该DatagramSocket 类对象的receive方法 ：接收数据报DatagramPacket对象。。
* 关闭DatagramSocket ：接收端程序结束，关闭通信套接字。

### 5.3 演示发送和接收消息

基于UDP协议的网络编程仍然需要在通信实例的两端各建立一个Socket，但这两个Socket之间并没有虚拟链路，这两个Socket只是发送、接收数据报的对象，Java提供了DatagramSocket对象作为基于UDP协议的Socket，使用DatagramPacket代表DatagramSocket发送、接收的数据报。

#### 举例1：

发送端：

```java
DatagramSocket ds = null;
try {
    ds = new DatagramSocket();
    byte[] by = "hello,atguigu.com".getBytes();
    DatagramPacket dp = new DatagramPacket(by, 0, by.length,     InetAddress.getByName("127.0.0.1"), 10000);
    ds.send(dp);
} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (ds != null)
        ds.close();
}

```

接收端：

```java
DatagramSocket ds = null;
try {
    ds = new DatagramSocket(10000);
    byte[] by = new byte[1024*64];
    DatagramPacket dp = new DatagramPacket(by, by.length);
    ds.receive(dp);
    String str = new String(dp.getData(), 0, dp.getLength());
    System.out.println(str + "--" + dp.getAddress());
} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (ds != null)
        ds.close();
}

```

#### 举例2：

**发送端：**

```java
package com.atguigu.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Send {

    public static void main(String[] args)throws Exception {
//		1、建立发送端的DatagramSocket
        DatagramSocket ds = new DatagramSocket();

        //要发送的数据
        ArrayList<String> all = new ArrayList<String>();
        all.add("尚硅谷让天下没有难学的技术！");
        all.add("学高端前沿的IT技术来尚硅谷！");
        all.add("尚硅谷让你的梦想变得更具体！");
        all.add("尚硅谷让你的努力更有价值！");

        //接收方的IP地址
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        //接收方的监听端口号
        int port = 9999;
        //发送多个数据报
        for (int i = 0; i < all.size(); i++) {
//			2、建立数据包DatagramPacket
            byte[] data = all.get(i).getBytes();
            DatagramPacket dp = new DatagramPacket(data, 0, data.length, ip, port);
//			3、调用Socket的发送方法
            ds.send(dp);
        }

//		4、关闭Socket
        ds.close();
    }
}
```

**接收端：**

```java
package com.atguigu.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receive {

    public static void main(String[] args) throws Exception {
//		1、建立接收端的DatagramSocket，需要指定本端的监听端口号
        DatagramSocket ds = new DatagramSocket(9999);

        //一直监听数据
        while(true){
            //2、建立数据包DatagramPacket
            byte[] buffer = new byte[1024*64];
            DatagramPacket dp = new DatagramPacket(buffer,buffer.length);

            //3、调用Socket的接收方法
            ds.receive(dp);

            //4、拆封数据
            String str = new String(dp.getData(),0,dp.getLength());
            System.out.println(str);
        }

//        ds.close();
    }
}
```

## 6. URL编程

### 6.1 URL类

- URL(Uniform Resource Locator)：**统一资源定位符**，它表示 Internet 上某一资源的地址。

- 通过 URL 我们可以访问 Internet 上的各种网络资源，比如最常见的 www，ftp 站点。浏览器通过解析给定的 URL 可以在网络上查找相应的文件或其他资源。 

- URL的基本结构由5部分组成：

```http
<传输协议>://<主机名>:<端口号>/<文件名>#片段名?参数列表
```

- 例如: http://192.168.1.100:8080/helloworld/index.jsp#a?username=shkstart&password=123

  - 片段名：即锚点，例如看小说，直接定位到章节
  - 参数列表格式：参数名=参数值&参数名=参数值....

- 为了表示URL，java.net 中实现了类 URL。我们可以通过下面的构造器来初始化一个 URL 对象：

  - public URL (String spec)：通过一个表示URL地址的字符串可以构造一个URL对象。例如：

    ```http
    URL url = new URL("http://www. atguigu.com/"); 
    ```

  - public URL(URL context, String spec)：通过基 URL 和相对 URL 构造一个 URL 对象。例如：

    ```http
    URL downloadUrl = new URL(url, “download.html")
    ```

    public URL(String protocol, String host, String file); 例如：

    ```http
    URL url = new URL("http", "www.atguigu.com", “download. html");
    ```

  - public URL(String protocol, String host, int port, String file); 例如: 

    ```java
    URL gamelan = new URL("http", "www.atguigu.com", 80, “download.html");
    ```

- URL类的构造器都声明抛出非运行时异常，必须要对这一异常进行处理，通常是用 try-catch 语句进行捕获。

![image-20220415013546074](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220415013546074.png)

### 6.2 URL类常用方法

一个URL对象生成后，其属性是不能被改变的，但可以通过它给定的方法来获取这些属性：

- public String getProtocol( )   获取该URL的协议名

- public String getHost( )      获取该URL的主机名

- public String getPort( )      获取该URL的端口号

- public String getPath( )      获取该URL的文件路径

- public String getFile( )       获取该URL的文件名

- public String getQuery(  )    获取该URL的查询名

```java
URL url = new URL("http://localhost:8080/examples/myTest.txt");
System.out.println("getProtocol() :"+url.getProtocol());
System.out.println("getHost() :"+url.getHost());
System.out.println("getPort() :"+url.getPort());
System.out.println("getPath() :"+url.getPath());
System.out.println("getFile() :"+url.getFile());
System.out.println("getQuery() :"+url.getQuery());
```

###  6.3 针对HTTP协议的URLConnection类

- URL的方法 openStream()：能从网络上读取数据
- 若希望输出数据，例如向服务器端的 CGI （公共网关接口-Common Gateway Interface-的简称，是用户浏览器和服务器端的应用程序进行连接的接口）程序发送一些数据，则必须先与URL建立连接，然后才能对其进行读写，此时需要使用 URLConnection 。
- URLConnection：表示到URL所引用的远程对象的连接。当与一个URL建立连接时，首先要在一个 URL 对象上通过方法 openConnection() 生成对应的 URLConnection 对象。如果连接过程失败，将产生IOException. 
  - URL netchinaren = new URL ("http://www.atguigu.com/index.shtml"); 
  - URLConnectonn u = netchinaren.openConnection( ); 

- 通过URLConnection对象获取的输入流和输出流，即可以与现有的CGI程序进行交互。
  - public Object getContent( ) throws IOException

  - public int getContentLength( )
  - public String getContentType( )
  - public long getDate( )
  - public long getLastModified( )
  - **public InputStream getInputStream ( ) throws IOException**
  - public OutputSteram getOutputStream( )throws IOException

### 6.4 小结

- 位于网络中的计算机具有唯一的IP地址，这样不同的主机可以互相区分。
- 客户端－服务器是一种最常见的网络应用程序模型。服务器是一个为其客户端提供某种特定服务的硬件或软件。客户机是一个用户应用程序，用于访问某台服务器提供的服务。端口号是对一个服务的访问场所，它用于区分同一物理计算机上的多个服务。套接字用于连接客户端和服务器，客户端和服务器之间的每个通信会话使用一个不同的套接字。TCP协议用于实现面向连接的会话。
- Java 中有关网络方面的功能都定义在 java.net 程序包中。Java 用 InetAddress 对象表示 IP 地址，该对象里有两个字段：主机名(String) 和 IP 地址(int)。
- 类 Socket 和 ServerSocket 实现了基于TCP协议的客户端－服务器程序。Socket是客户端和服务器之间的一个连接，连接创建的细节被隐藏了。这个连接提供了一个安全的数据传输通道，这是因为 TCP 协议可以解决数据在传送过程中的丢失、损坏、重复、乱序以及网络拥挤等问题，它保证数据可靠的传送。
- 类 URL 和 URLConnection 提供了最高级网络应用。URL 的网络资源的位置来同一表示 Internet 上各种网络资源。通过URL对象可以创建当前应用程序和 URL 表示的网络资源之间的连接，这样当前程序就可以读取网络资源数据，或者把自己的数据传送到网络上去。



## 7.随堂复习

### 1. 网络编程概述

- 计算机网络：略
- 网络编程的目的：直接或间接地通过网络协议与其它计算机实现数据交换，进行通讯。
- 需要解决的三个问题：
  - 问题1：如何准确地定位网络上一台或多台主机
  - 问题2：如何定位主机上的特定的应用
  - 问题3：找到主机后，如何可靠、高效地进行数据传输

### 2. 要素1：IP地址

- 使用具体的一个ip地址对应具体的一个互联网上的主机
- IP分类：
  - 角度一：IPv4、IPv6
  - 角度二：公网地址、私网地址（或局域网)
- 使用InetAddress类表示IP地址
  - 实例化：getByName(String host) 、getLocalHost()
  - 常用方法：getHostName() 、getHostAddress()

### 3. 要素2：端口号

- 用于区分同一台主机上的不同的进程
- 不同的进程分配不同的端口号
- 范围：0-65535

### 4. 要素3：网络通信协议

- 这里有两套参考模型
  - OSI参考模型：模型过于理想化，未能在因特网上进行广泛推广
  - TCP/IP参考模型(或TCP/IP协议)：事实上的国际标准。
- 在传输层中涉及到两个协议：TCP、UDP。二者的对比
  - TCP：可靠的连接（发送数据前，需要三次握手、四次挥手），进行大数据量的传输，效率低。
  - UDP：不可靠的连接（发送前，不需要确认对方是否在）、使用数据报传输（限制在64kb以内）、效率高。
- TCP的三次握手、四次挥手（了解）

### 5. TCP网络编程

> 例题1：客户端发送内容给服务端，服务端将内容打印到控制台上。
>
> 例题2：客户端发送文件给服务端，服务端将文件保存在本地。
>
> 例题3：从客户端发送文件给服务端，服务端保存到本地。并返回“发送成功”给客户端。并关闭相应的连接。

### 6. UDP网络编程

简单的理解UDP的数据传输。

### 7. URL编程

- Java后台：将写好的Java程序部署在Tomcat服务器。启动Tomcat服务器。

- 前台：使用浏览器进行访问。需要使用url。（HTML+CSS+JavaScript）
- URL的作用：定位互联网上某一资源的地址。
- URL的格式：

```
http://192.168.21.107:8080/examples/abcd.jpg?name=Tom   ---> "万事万物皆对象"
应用层协议 ip地址       端口号  资源地址   参数列表
```

- 使用URL实现数据的下载（了解）


## 8.企业真题

#### 1. TCP协议和UDP协议的区别（华**为）

略



#### 2. 简单说说TCP协议的三次握手与四次挥手机制 （*科软）

略



#  第16章_反射机制

![第3阶段：Java高级应用-第17章](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC17%E7%AB%A0.png)

***

## 1. 反射(Reflection)的概念

### 1.1 反射的出现背景

Java程序中，所有的对象都有两种类型：`编译时类型`和`运行时类型`，而很多时候对象的编译时类型和运行时类型`不一致`。 Object obj = new String("hello");    obj.getClass()

例如：某些变量或形参的声明类型是Object类型，但是程序却需要调用该对象运行时类型的方法，该方法不是Object中的方法，那么如何解决呢？

解决这个问题，有两种方案：

方案1：在编译和运行时都完全知道类型的具体信息，在这种情况下，我们可以直接先使用`instanceof`运算符进行判断，再利用强制类型转换符将其转换成运行时类型的变量即可。

方案2：编译时根本无法预知该对象和类的真实信息，程序只能依靠`运行时信息`来发现该对象和类的真实信息，这就必须使用反射。

### 1.2 反射概述

==Reflection（反射）是被视为`动态语言`的关键，反射机制允许程序在`运行期间`借助于Reflection API取得任何类的内部信息，并能直接操作任意对象的内部属性及方法。==

<font color='red'>加载完类之后，在堆内存的方法区中就产生了一个Class类型的对象（一个类只有一个Class对象），这个对象就包含了完整的类的结构信息。我们可以通过这个对象看到类的结构。`这个对象就像一面镜子，透过这个镜子看到类的结构，所以，我们形象的称之为：反射。`</font>

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417161529285.png" alt="image-20220417161529285" style="zoom:80%;" />

**从内存加载上看反射：**

![image-20220524101448625](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220524101448625.png)

### 1.3  Java反射机制研究及应用

Java反射机制提供的功能：

- 在运行时判断任意一个对象所属的类
- 在运行时构造任意一个类的对象
- 在运行时判断任意一个类所具有的成员变量和方法
- 在运行时获取泛型信息
- 在运行时调用任意一个对象的成员变量和方法
- 在运行时处理注解
- 生成动态代理

### 1.4 反射相关的主要API

`java.lang.Class`：代表一个类
java.lang.reflect.Method：代表类的方法
java.lang.reflect.Field：代表类的成员变量
java.lang.reflect.Constructor：代表类的构造器
… …

### 1.5 反射的优缺点

**优点：**

- 提高了Java程序的灵活性和扩展性，`降低了耦合性`，提高`自适应`能力

- 允许程序创建和控制任何类的对象，无需提前`硬编码`目标类

**缺点：**

- 反射的`性能较低`。
  - 反射机制主要应用在对灵活性和扩展性要求很高的系统框架上

- 反射会`模糊`程序内部逻辑，`可读性较差`。

## 2.  理解Class类并获取Class实例

要想`解剖`一个类，必须先要获取到该类的Class对象。而剖析一个类或用反射解决具体的问题就是使用相关API:

- java.lang.Class
- java.lang.reflect.*

所以，Class对象是反射的根源。

### 2.1 理解Class

#### 2.1.1 理论上

在Object类中定义了以下的方法，此方法将被所有子类继承：

```java
public final Class getClass()
```

以上的方法返回值的类型是一个Class类，此类是Java反射的源头，实际上所谓反射从程序的运行结果来看也很好理解，即：可以通过对象反射求出类的名称。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417162559217.png" alt="image-20220417162559217" style="zoom:80%;" />

对象照镜子后可以得到的信息：某个类的属性、方法和构造器、某个类到底实现了哪些接口。对于每个类而言，JRE 都为其保留一个不变的 Class 类型的对象。一个 Class 对象包含了特定某个结构(class/interface/enum/annotation/primitive type/void/[])的有关信息。

- Class本身也是一个类
- Class 对象只能由系统建立对象
- 一个加载的类在 JVM 中只会有一个Class实例 
- 一个Class对象对应的是一个加载到JVM中的一个.class文件
- 每个类的实例都会记得自己是由哪个 Class 实例所生成
- 通过Class可以完整地得到一个类中的所有被加载的结构 
- Class类是Reflection的根源，针对任何你想动态加载、运行的类，唯有先获得相应的Class对象

#### 2.1.2 内存结构上

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514180100176.png" alt="image-20220514180100176" style="zoom:80%;" />

说明：上图中字符串常量池在JDK6中存储在方法区；JDK7及以后，存储在堆空间。

### 2.2 获取Class类的实例(四种方法)

方式1：要求编译期间已知类型

前提：若已知具体的类，通过类的class属性获取，该方法最为安全可靠，程序性能最高

实例：

```java
Class clazz = String.class;
```

方式2：获取对象的运行时类型

前提：已知某个类的实例，调用该实例的getClass()方法获取Class对象

实例：

```java
Class clazz = "www.atguigu.com".getClass();
```

方式3：可以获取编译期间未知的类型

前提：已知一个类的全类名，且该类在类路径下，可通过Class类的静态方法forName()获取，可能抛出ClassNotFoundException

实例：

```java
Class clazz = Class.forName("java.lang.String");
```

方式4：其他方式(不做要求)

前提：可以用系统类加载对象或自定义加载器对象加载指定路径下的类型

实例：

```java
ClassLoader cl = this.getClass().getClassLoader();
Class clazz4 = cl.loadClass("类的全类名");
```

再举例：

```java
public class GetClassObject {
    @Test
    public void test01() throws ClassNotFoundException{
        Class c1 = GetClassObject.class;
        GetClassObject obj = new GetClassObject();
        Class c2 = obj.getClass();
        Class c3 = Class.forName("com.atguigu.classtype.GetClassObject");
        Class c4 = ClassLoader.getSystemClassLoader().loadClass("com.atguigu.classtype.GetClassObject");

        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);
        System.out.println("c3 = " + c3);
        System.out.println("c4 = " + c4);

        System.out.println(c1 == c2);
        System.out.println(c1 == c3);
        System.out.println(c1 == c4);
    }
}
```

### 2.3 哪些类型可以有Class对象

简言之，所有Java类型！

> （1）`class`：外部类，成员(成员内部类，静态内部类)，局部内部类，匿名内部类
> 		  （2）`interface`：接口
> 		  （3）`[]`：数组
> 		  （4）`enum`：枚举
> 		  （5）`annotation`：注解@interface
> 		  （6）`primitive type`：基本数据类型
> 		  （7）`void`



举例：	

```java
Class c1 = Object.class;
Class c2 = Comparable.class;
Class c3 = String[].class;
Class c4 = int[][].class;
Class c5 = ElementType.class;
Class c6 = Override.class;
Class c7 = int.class;
Class c8 = void.class;
Class c9 = Class.class;

int[] a = new int[10];
int[] b = new int[100];
Class c10 = a.getClass();
Class c11 = b.getClass();
// 只要元素类型与维度一样，就是同一个Class
System.out.println(c10 == c11);
```

### 2.4 Class类的常用方法

| **方法名**                                         | **功能说明**                                                 |
| -------------------------------------------------- | ------------------------------------------------------------ |
| static  Class forName(String  name)                | 返回指定类名  name  的  Class  对象                          |
| Object  newInstance()                              | 调用缺省构造函数，返回该Class对象的一个实例                  |
| getName()                                          | 返回此Class对象所表示的实体（类、接口、数组类、基本类型或void）名称 |
| Class  getSuperClass()                             | 返回当前Class对象的父类的Class对象                           |
| Class  [] getInterfaces()                          | 获取当前Class对象的接口                                      |
| ClassLoader  getClassLoader()                      | 返回该类的类加载器                                           |
| Class  getSuperclass()                             | 返回表示此Class所表示的实体的超类的Class                     |
| Constructor[]  getConstructors()                   | 返回一个包含某些Constructor对象的数组                        |
| Field[]  getDeclaredFields()                       | 返回Field对象的一个数组                                      |
| Method  getMethod(String  name,Class … paramTypes) | 返回一个Method对象，此对象的形参类型为paramType              |

举例：

```java
String str = "test4.Person";
Class clazz = Class.forName(str);

Object obj = clazz.newInstance();

Field field = clazz.getField("name");
field.set(obj, "Peter");
Object name = field.get(obj);
System.out.println(name);

//注：test4.Person是test4包下的Person类
```

## 3. 类的加载与ClassLoader的理解

### 3.1 类的生命周期

类在内存中完整的生命周期：加载-->使用-->卸载。其中加载过程又分为：装载、链接、初始化三个阶段。

![image-20220417173459849](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417173459849.png)

### 3.2 类的加载过程

当程序主动使用某个类时，如果该类还未被加载到内存中，系统会通过加载、链接、初始化三个步骤来对该类进行初始化。如果没有意外，JVM将会连续完成这三个步骤，所以有时也把这三个步骤统称为类加载。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417171411631.png" alt="image-20220417171411631" style="zoom: 50%;" />

类的加载又分为三个阶段：

（1）装载（Loading）

将类的class文件读入内存，并为之创建一个java.lang.Class对象。此过程由类加载器完成

（2）链接（Linking）

①验证Verify：确保加载的类信息符合JVM规范，例如：以cafebabe开头，没有安全方面的问题。

②准备Prepare：正式为类变量（static）分配内存并`设置类变量默认初始值`的阶段，这些内存都将在方法区中进行分配。

③解析Resolve：虚拟机常量池内的符号引用（常量名）替换为直接引用（地址）的过程。

（3）初始化（Initialization）

- 执行`类构造器<clinit>()方法`的过程。`类构造器<clinit>()方法`是由编译期自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的。（类构造器是构造类信息的，不是构造该类对象的构造器）。

- 当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类的初始化。

- 虚拟机会保证一个`类的<clinit>()方法`在多线程环境中被正确加锁和同步。

### 3.3 类加载器（classloader)

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417173647473.png" alt="image-20220417173647473" style="zoom:67%;" />

#### 3.3.1 类加载器的作用

将class文件字节码内容加载到内存中，并将这些静态数据转换成方法区的运行时数据结构，然后在堆中生成一个代表这个类的java.lang.Class对象，作为方法区中类数据的访问入口。

类缓存：标准的JavaSE类加载器可以按要求查找类，但一旦某个类被加载到类加载器中，它将维持加载（缓存）一段时间。不过JVM垃圾回收机制可以回收这些Class对象。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417173616344.png" alt="image-20220417173616344" style="zoom: 80%;" />

#### 3.3.2 类加载器的分类(JDK8为例)

JVM支持两种类型的类加载器，分别为`引导类加载器（Bootstrap ClassLoader）`和`自定义类加载器（User-Defined ClassLoader）`。

从概念上来讲，自定义类加载器一般指的是程序中由开发人员自定义的一类类加载器，但是Java虚拟机规范却没有这么定义，而是将所有派生于抽象类ClassLoader的类加载器都划分为自定义类加载器。无论类加载器的类型如何划分，在程序中我们最常见的类加载器结构主要是如下情况：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417173819953.png" alt="image-20220417173819953" style="zoom:80%;" />



**（1）启动类加载器（引导类加载器，Bootstrap ClassLoader）**

- 这个类加载使用`C/C++语言`实现的，嵌套在JVM内部。获取它的对象时往往返回null
- 它用来加载Java的核心库（JAVA_HOME/jre/lib/rt.jar或sun.boot.class.path路径下的内容）。用于提供JVM自身需要的类。
- 并不继承自java.lang.ClassLoader，没有父加载器。
- 出于安全考虑，Bootstrap启动类加载器只加载包名为java、javax、sun等开头的类
- 加载扩展类和应用程序类加载器，并指定为他们的父类加载器。

**（2）扩展类加载器（Extension ClassLoader）**

- Java语言编写，由sun.misc.Launcher$ExtClassLoader实现。
- 继承于ClassLoader类
- 父类加载器为启动类加载器
- 从java.ext.dirs系统属性所指定的目录中加载类库，或从JDK的安装目录的jre/lib/ext子目录下加载类库。如果用户创建的JAR放在此目录下，也会自动由扩展类加载器加载。

![image-20220417174032702](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417174032702.png)

**（3）应用程序类加载器（系统类加载器，AppClassLoader）**

- java语言编写，由sun.misc.Launcher$AppClassLoader实现
- 继承于ClassLoader类
- 父类加载器为扩展类加载器
- 它负责加载环境变量classpath或系统属性 java.class.path 指定路径下的类库 
- 应用程序中的类加载器默认是系统类加载器。
- 它是用户自定义类加载器的默认父加载器
- 通过ClassLoader的getSystemClassLoader()方法可以获取到该类加载器

**（4）用户自定义类加载器（了解）**

- 在Java的日常应用程序开发中，类的加载几乎是由上述3种类加载器相互配合执行的。在必要时，我们还可以自定义类加载器，来定制类的加载方式。
- 体现Java语言强大生命力和巨大魅力的关键因素之一便是，Java开发者可以自定义类加载器来实现类库的动态加载，加载源可以是本地的JAR包，也可以是网络上的远程资源。
- 同时，自定义加载器能够实现`应用隔离`，例如 Tomcat，Spring等中间件和组件框架都在内部实现了自定义的加载器，并通过自定义加载器隔离不同的组件模块。这种机制比C/C++程序要好太多，想不修改C/C++程序就能为其新增功能，几乎是不可能的，仅仅一个兼容性便能阻挡住所有美好的设想。
- 自定义类加载器通常需要继承于ClassLoader。

#### 3.3.3 查看某个类的类加载器对象

（1）获取默认的系统类加载器

```java
ClassLoader classloader = ClassLoader.getSystemClassLoader();
```

（2）查看某个类是哪个类加载器加载的

```java
ClassLoader classloader = Class.forName("exer2.ClassloaderDemo").getClassLoader();

//如果是根加载器加载的类，则会得到null
ClassLoader classloader1 = Class.forName("java.lang.Object").getClassLoader();
```

（3）获取某个类加载器的父加载器

```java
ClassLoader parentClassloader = classloader.getParent();
```

示例代码：

```java
package com.atguigu.loader;

import org.junit.Test;

public class TestClassLoader {
    @Test
    public void test01(){
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("systemClassLoader = " + systemClassLoader);
    }

    @Test
    public void test02()throws Exception{
        ClassLoader c1 = String.class.getClassLoader();
        System.out.println("加载String类的类加载器：" + c1);

        ClassLoader c2 = Class.forName("sun.util.resources.cldr.zh.TimeZoneNames_zh").getClassLoader();
        System.out.println("加载sun.util.resources.cldr.zh.TimeZoneNames_zh类的类加载器：" + c2);

        ClassLoader c3 = TestClassLoader.class.getClassLoader();
        System.out.println("加载当前类的类加载器：" + c3);
    }

    @Test
    public void test03(){
        ClassLoader c1 = TestClassLoader.class.getClassLoader();
        System.out.println("加载当前类的类加载器c1=" + c1);

        ClassLoader c2 = c1.getParent();
        System.out.println("c1.parent = " + c2);

        ClassLoader c3 = c2.getParent();
        System.out.println("c2.parent = " + c3);

    }
}
```

#### 3.3.4 使用ClassLoader获取流

关于类加载器的一个主要方法：getResourceAsStream(String str):获取类路径下的指定文件的输入流

```java
InputStream in = null;
in = this.getClass().getClassLoader().getResourceAsStream("exer2\\test.properties");
System.out.println(in);

```

举例：

```java
//需要掌握如下的代码
    @Test
    public void test5() throws IOException {
        Properties pros = new Properties();
        //方式1：此时默认的相对路径是当前的module
//        FileInputStream is = new FileInputStream("info.properties");
//        FileInputStream is = new FileInputStream("src//info1.properties");

        //方式2：使用类的加载器
        //此时默认的相对路径是当前module的src目录
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("info1.properties");


        pros.load(is);

        //获取配置文件中的信息
        String name = pros.getProperty("name");
        String password = pros.getProperty("password");
        System.out.println("name = " + name + ", password = " + password);
    }
```

## 4. 反射的基本应用

有了Class对象，能做什么？

### 4.1 应用1：创建运行时类的对象

这是反射机制应用最多的地方。创建运行时类的对象有两种方式：

**方式1：直接调用Class对象的newInstance()方法**

要 求： 1）类必须有一个无参数的构造器。2）类的构造器的访问权限需要足够。

**方式2：通过获取构造器对象来进行实例化**

方式一的步骤：

1）获取该类型的Class对象  2）调用Class对象的newInstance()方法创建对象

方式二的步骤：

1）通过Class类的getDeclaredConstructor(Class … parameterTypes)取得本类的指定形参类型的构造器
2）向构造器的形参中传递一个对象数组进去，里面包含了构造器中所需的各个参数。
3）通过Constructor实例化对象。

> 如果构造器的权限修饰符修饰的范围不可见，也可以调用setAccessible(true)

示例代码，使用反射构建BeanCopyUtils：

```java
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: BeanCopy工具类
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        //创建目标对象（V 创建的对象即使传过来的对象）
        V result = null;
        try {
           //利用反射创建对应类的实例 
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}

```

调用：

```java
//获取到Class类的实例并作为参数传入
copyBean(user,UserVO.class)
```



### 4.2 应用2：获取运行时类的完整结构

可以获取：包、修饰符、类型名、父类（包括泛型父类）、父接口（包括泛型父接口）、成员（属性、构造器、方法）、注解（类上的、方法上的、属性上的）。

#### 4.2.1 相关API

```java
//1.实现的全部接口
public Class<?>[] getInterfaces()   
//确定此对象所表示的类或接口实现的接口。 

//2.所继承的父类
public Class<? Super T> getSuperclass()
//返回表示此 Class 所表示的实体（类、接口、基本类型）的父类的 Class。

//3.全部的构造器
public Constructor<T>[] getConstructors()
//返回此 Class 对象所表示的类的所有public构造方法。
public Constructor<T>[] getDeclaredConstructors()
//返回此 Class 对象表示的类声明的所有构造方法。

//Constructor类中：
//取得修饰符: 
public int getModifiers();
//取得方法名称: 
public String getName();
//取得参数的类型：
public Class<?>[] getParameterTypes();

//4.全部的方法
public Method[] getDeclaredMethods()
//返回此Class对象所表示的类或接口的全部方法
public Method[] getMethods()  
//返回此Class对象所表示的类或接口的public的方法

//Method类中：
public Class<?> getReturnType()
//取得全部的返回值
public Class<?>[] getParameterTypes()
//取得全部的参数
public int getModifiers()
//取得修饰符
public Class<?>[] getExceptionTypes()
//取得异常信息

//5.全部的Field
public Field[] getFields() 
//返回此Class对象所表示的类或接口的public的Field。
public Field[] getDeclaredFields() 
//返回此Class对象所表示的类或接口的全部Field。

//Field方法中：
public int getModifiers()
//以整数形式返回此Field的修饰符
public Class<?> getType()  
//得到Field的属性类型
public String getName()  
//返回Field的名称。

//6. Annotation相关
get Annotation(Class<T> annotationClass) 
getDeclaredAnnotations() 

//7.泛型相关
//获取父类泛型类型：
Type getGenericSuperclass()
//泛型类型：ParameterizedType
//获取实际的泛型类型参数数组：
getActualTypeArguments()

//8.类所在的包
Package getPackage() 
```

#### 4.2.2 获取所有的属性及相关细节

```java
package com.atguigu.java2;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

import com.atguigu.java1.Person;

public class FieldTest {
	
	@Test
	public void test1(){
		
		Class clazz = Person.class;
		//getFields():获取到运行时类本身及其所有的父类中声明为public权限的属性
//		Field[] fields = clazz.getFields();
//
//		for(Field f : fields){
//			System.out.println(f);
//		}
		
		//getDeclaredFields():获取当前运行时类中声明的所有属性
		Field[] declaredFields = clazz.getDeclaredFields();
		for(Field f : declaredFields){
			System.out.println(f);
		}
	}
	
	//权限修饰符  变量类型  变量名
	@Test
	public void test2(){
		Class clazz = Person.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            //1.权限修饰符
            /*
         	* 0x是十六进制
         	* PUBLIC           = 0x00000001;  1    1
         	* PRIVATE          = 0x00000002;  2	10
         	* PROTECTED        = 0x00000004;  4	100
         	* STATIC           = 0x00000008;  8	1000
         	* FINAL            = 0x00000010;  16	10000
         	* ...
         	*
         	* 设计的理念，就是用二进制的某一位是1，来代表一种修饰符，整个二进制中只有一位是1，其余都是0
         	*
         	* mod = 17          0x00000011
         	* if ((mod & PUBLIC) != 0)  说明修饰符中有public
         	* if ((mod & FINAL) != 0)   说明修饰符中有final
         	*/
            int modifier = f.getModifiers();
            System.out.print(Modifier.toString(modifier) + "\t");

//            //2.数据类型
            Class type = f.getType();
            System.out.print(type.getName() + "\t");
//
//            //3.变量名
            String fName = f.getName();
            System.out.print(fName);
//
            System.out.println();
        }
	}
}

```

#### 4.2.3 获取所有的方法及相关细节

```java
package com.atguigu.java2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

import com.atguigu.java1.Person;

public class MethodTest {

	@Test
	public void test1() {

		Class clazz = Person.class;
		// getMethods():获取到运行时类本身及其所有的父类中声明为public权限的方法
		// Method[] methods = clazz.getMethods();
		//
		// for(Method m : methods){
		// System.out.println(m);
		// }

		// getDeclaredMethods():获取当前运行时类中声明的所有方法
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method m : declaredMethods) {
			System.out.println(m);
		}
		//
	}

	// 注解信息
	// 权限修饰符 返回值类型 方法名(形参类型1 参数1,形参类型2 参数2,...) throws 异常类型1,...{}
	@Test
	public void test2() {
		Class clazz = Person.class;
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method m : declaredMethods) {
			// 1.获取方法声明的注解
			Annotation[] annos = m.getAnnotations();
			for (Annotation a : annos) {
				System.out.println(a);
			}

			// 2.权限修饰符
			System.out.print(Modifier.toString(m.getModifiers()) + "\t");

			// 3.返回值类型
			System.out.print(m.getReturnType().getName() + "\t");

			// 4.方法名
			System.out.print(m.getName());
			System.out.print("(");
			// 5.形参列表
			Class[] parameterTypes = m.getParameterTypes();
			if (!(parameterTypes == null && parameterTypes.length == 0)) {
				for (int i = 0; i < parameterTypes.length; i++) {

					if (i == parameterTypes.length - 1) {
						System.out.print(parameterTypes[i].getName() + " args_" + i);
						break;
					}

					System.out.print(parameterTypes[i].getName() + " args_" + i + ",");
				}
			}

			System.out.print(")");

			// 6.抛出的异常
			Class[] exceptionTypes = m.getExceptionTypes();
			if (exceptionTypes.length > 0) {
				System.out.print("throws ");
				for (int i = 0; i < exceptionTypes.length; i++) {
					if (i == exceptionTypes.length - 1) {
						System.out.print(exceptionTypes[i].getName());
						break;
					}
					System.out.print(exceptionTypes[i].getName() + ",");
				}
			}
			System.out.println();
		}
	}
}

```

#### 4.2.4 获取其他结构(构造器、父类、接口、包、注解等)

```java
package com.atguigu.java2;

import com.atguigu.java1.Person;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 尚硅谷-宋红康
 * @create 2020 下午 2:47
 */
public class OtherTest {

    /*
    	获取当前类中的所有的构造器
     */
    @Test
    public void test1(){
        Class clazz = Person.class;
        Constructor[] cons = clazz.getDeclaredConstructors();
        for(Constructor c :cons){
            System.out.println(c);
        }
    }
    /*
    	获取运行时类的父类
     */
    @Test
    public void test2(){
        Class clazz = Person.class;
        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);//class com.atguigu.java1.Creature
    }
    /*
    	获取运行时类的所在的包
     */
    @Test
    public void test3(){
        Class clazz = Person.class;
        Package pack = clazz.getPackage();
        System.out.println(pack);

    }
    /*
    	获取运行时类的注解
     */
    @Test
    public void test4(){
        Class clazz = Person.class;
        Annotation[] annos = clazz.getAnnotations();
        for (Annotation anno : annos) {

            System.out.println(anno);
        }

    }

    /*
    	获取运行时类所实现的接口
     */
    @Test
    public void test5(){
        Class clazz = Person.class;
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {

            System.out.println(anInterface);
        }

    }
    /*
    	获取运行时类的带泛型的父类
     */
    @Test
    public void test6(){
        Class clazz = Person.class;
        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);//com.atguigu.java1.Creature<java.lang.String>
    }
}

```

#### 4.2.5 获取泛型父类信息（选讲）

示例代码获取泛型父类信息：

```java
/* Type：
 * （1）Class
 * （2）ParameterizedType   
 * 		例如：Father<String,Integer>
 * 			ArrayList<String>
 * （3）TypeVariable
 * 		例如：T，U,E,K,V
 * （4）WildcardType
 * 		例如：
 * 		ArrayList<?>
 * 		ArrayList<? super 下限>
 * 		ArrayList<? extends 上限>
 * （5）GenericArrayType
 * 		例如：T[]
 * 	
 */
public class TestGeneric {
	public static void main(String[] args) {
		//需求：在运行时，获取Son类型的泛型父类的泛型实参<String,Integer>
		
		//（1）还是先获取Class对象
		Class clazz = Son.class;//四种形式任意一种都可以
		
		//（2）获取泛型父类
//		Class sc = clazz.getSuperclass();
//		System.out.println(sc);
		/*
		 * getSuperclass()只能得到父类名，无法得到父类的泛型实参列表
		 */
		Type type = clazz.getGenericSuperclass();
		
		// Father<String,Integer>属于ParameterizedType
		ParameterizedType pt = (ParameterizedType) type;
		
		//（3）获取泛型父类的泛型实参列表
		Type[] typeArray = pt.getActualTypeArguments();
		for (Type type2 : typeArray) {
			System.out.println(type2);
		}
	}
}
//泛型形参：<T,U>
class Father<T,U>{
	
}
//泛型实参：<String,Integer>
class Son extends Father<String,Integer>{
	
}
```

#### 4.2.6 获取内部类或外部类信息（选讲）

public Class<?>[] getClasses()：返回所有公共内部类和内部接口。包括从超类继承的公共类和接口成员以及该类声明的公共类和接口成员。

public Class<?>[] getDeclaredClasses()：返回 Class 对象的一个数组，这些对象反映声明为此 Class 对象所表示的类的成员的所有类和接口。包括该类所声明的公共、保护、默认（包）访问及私有类和接口，但不包括继承的类和接口。

public Class<?> getDeclaringClass()：如果此 Class 对象所表示的类或接口是一个内部类或内部接口，则返回它的外部类或外部接口，否则返回null。

Class<?> getEnclosingClass() ：返回某个内部类的外部类

```java
	@Test
	public void test5(){
		Class<?> clazz = Map.class;
		Class<?>[] inners = clazz.getDeclaredClasses();
		for (Class<?> inner : inners) {
			System.out.println(inner);
		}
		
		Class<?> ec = Map.Entry.class;
		Class<?> outer = ec.getDeclaringClass();
		System.out.println(outer);
	}
```



#### 4.2.7 小 结

1. 在实际的操作中，取得类的信息的操作代码，并不会经常开发。

2. 一定要熟悉java.lang.reflect包的作用，反射机制。

### 4.3 应用3：调用运行时类的指定结构

#### 4.3.1 调用指定的属性

在反射机制中，可以直接通过Field类操作类中的属性，通过Field类提供的set()和get()方法就可以完成设置和取得属性内容的操作。

（1）获取该类型的Class对象

Class clazz = Class.forName("包.类名");

（2）获取属性对象

Field field = clazz.getDeclaredField("属性名");

（3）如果属性的权限修饰符不是public，那么需要设置属性可访问

field.setAccessible(true);

（4）创建实例对象：如果操作的是非静态属性，需要创建实例对象

Object obj = clazz.newInstance(); //有公共的无参构造

Object obj = 构造器对象.newInstance(实参...);//通过特定构造器对象创建实例对象

（4）设置指定对象obj上此Field的属性内容

field.set(obj,"属性值");

> 如果操作静态变量，那么实例对象可以省略，用null表示

（5）取得指定对象obj上此Field的属性内容

Object value = field.get(obj);

> 如果操作静态变量，那么实例对象可以省略，用null表示

示例代码：

```java
package com.atguigu.reflect;

public class Student {
    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

```

```java
package com.atguigu.reflect;

import java.lang.reflect.Field;

public class TestField {
    public static void main(String[] args)throws Exception {
        //1、获取Student的Class对象
        Class clazz = Class.forName("com.atguigu.reflect.Student");

        //2、获取属性对象，例如：id属性
        Field idField = clazz.getDeclaredField("id");

        //3、如果id是私有的等在当前类中不可访问access的，我们需要做如下操作
        idField.setAccessible(true);

        //4、创建实例对象，即，创建Student对象
        Object stu = clazz.newInstance();

        //5、获取属性值
        /*
         * 以前：int 变量= 学生对象.getId()
         * 现在：Object id属性对象.get(学生对象)
         */
        Object value = idField.get(stu);
        System.out.println("id = "+ value);

        //6、设置属性值
        /*
         * 以前：学生对象.setId(值)
         * 现在：id属性对象.set(学生对象,值)
         */
        idField.set(stu, 2);

        value = idField.get(stu);
        System.out.println("id = "+ value);
    }
}
```

**关于setAccessible方法的使用：**

- Method和Field、Constructor对象都有setAccessible()方法。
- setAccessible启动和禁用访问安全检查的开关。
- 参数值为true则指示反射的对象在使用时应该取消Java语言访问检查。
  - 提高反射的效率。如果代码中必须用反射，而该句代码需要频繁的被调用，那么请设置为true。
  - 使得原本无法访问的私有成员也可以访问
- 参数值为false则指示反射的对象应该实施Java语言访问检查。

#### 4.3.2 调用指定的方法

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220417181700813.png" alt="image-20220417181700813" style="zoom:80%;" />

（1）获取该类型的Class对象

Class clazz = Class.forName("包.类名");

（2）获取方法对象

Method method = clazz.getDeclaredMethod("方法名",方法的形参类型列表);

（3）创建实例对象

Object obj = clazz.newInstance();

（4）调用方法

Object result = method.invoke(obj, 方法的实参值列表);

> 如果方法的权限修饰符修饰的范围不可见，也可以调用setAccessible(true)
>
> 如果方法是静态方法，实例对象也可以省略，用null代替

示例代码：

```java
package com.atguigu.reflect;

import org.junit.Test;

import java.lang.reflect.Method;

public class TestMethod {
    @Test
    public void test()throws Exception {
        // 1、获取Student的Class对象
        Class<?> clazz = Class.forName("com.atguigu.reflect.Student");

        //2、获取方法对象
        /*
         * 在一个类中，唯一定位到一个方法，需要：（1）方法名（2）形参列表，因为方法可能重载
         *
         * 例如：void setName(String name)
         */
        Method setNameMethod = clazz.getDeclaredMethod("setName", String.class);

        //3、创建实例对象
        Object stu = clazz.newInstance();

        //4、调用方法
        /*
         * 以前：学生对象.setName(值)
         * 现在：方法对象.invoke(学生对象，值)
         */
        Object setNameMethodReturnValue = setNameMethod.invoke(stu, "张三");

        System.out.println("stu = " + stu);
        //setName方法返回值类型void，没有返回值，所以setNameMethodReturnValue为null
        System.out.println("setNameMethodReturnValue = " + setNameMethodReturnValue);

        Method getNameMethod = clazz.getDeclaredMethod("getName");
        Object getNameMethodReturnValue = getNameMethod.invoke(stu);
        //getName方法返回值类型String，有返回值，getNameMethod.invoke的返回值就是getName方法的返回值
        System.out.println("getNameMethodReturnValue = " + getNameMethodReturnValue);//张三
    }

    @Test
    public void test02()throws Exception{
        Class<?> clazz = Class.forName("com.atguigu.ext.demo.AtGuiguClass");
        Method printInfoMethod = clazz.getMethod("printInfo", String.class);
        //printInfo方法是静态方法
        printInfoMethod.invoke(null,"尚硅谷");
    }
}

```

#### 4.3.3 练习

读取user.properties文件中的数据，通过反射完成User类对象的创建及对应方法的调用。

配置文件：user.properties

```
className:com.atguigu.bean.User
methodName:show
```

User.java文件：

```java
package com.atguigu.bean;

/**
 * @author 尚硅谷-宋红康
 * @create 18:41
 */
public class User {
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println("我是一个脉脉平台的用户");
    }
}
```

ReflectTest.java文件：

```java
package com.atguigu.java4;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author 尚硅谷-宋红康
 * @create 18:43
 */
public class ReflectTest {
    @Test
    public void test() throws Exception {
        //1.创建Properties对象
        Properties pro = new Properties();

        //2.加载配置文件，转换为一个集合
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream("user.properties");
        pro.load(is);

        //3.获取配置文件中定义的数据
        String className = pro.getProperty("className");
        String methodName = pro.getProperty("methodName");

        //4.加载该类进内存
        Class clazz = Class.forName(className);

        //5.创建对象
        Object instance = clazz.newInstance();

        //6.获取方法对象
        Method showMethod = clazz.getMethod(methodName);

        //7.执行方法
        showMethod.invoke(instance);
    }
}

```



## 5. 应用4：读取注解信息

一个完整的注解应该包含三个部分：
（1）声明
（2）使用
（3）读取

### 5.1 声明自定义注解

```java
package com.atguigu.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String value();
}
```

```java
package com.atguigu.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String columnName();
    String columnType();
}
```

* 自定义注解可以通过四个元注解@Retention,@Target，@Inherited,@Documented，分别说明它的声明周期，使用位置，是否被继承，是否被生成到API文档中。
* Annotation 的成员在 Annotation 定义中以无参数有返回值的抽象方法的形式来声明，我们又称为配置参数。返回值类型只能是八种基本数据类型、String类型、Class类型、enum类型、Annotation类型、以上所有类型的数组
* 可以使用 default 关键字为抽象方法指定默认返回值
* 如果定义的注解含有抽象方法，那么使用时必须指定返回值，除非它有默认值。格式是“方法名 = 返回值”，如果只有一个抽象方法需要赋值，且方法名为value，可以省略“value=”，所以如果注解只有一个抽象方法成员，建议使用方法名value。

### 5.2 使用自定义注解

```java
package com.atguigu.annotation;

@Table("t_stu")
public class Student {
    @Column(columnName = "sid",columnType = "int")
    private int id;
    @Column(columnName = "sname",columnType = "varchar(20)")
    private String name;

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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

```

### 5.3 读取和处理自定义注解

自定义注解必须配上注解的信息处理流程才有意义。

我们自己定义的注解，只能使用反射的代码读取。所以自定义注解的声明周期必须是RetentionPolicy.RUNTIME。

```java
package com.atguigu.annotation;

import java.lang.reflect.Field;

public class TestAnnotation {
    public static void main(String[] args) {
        Class studentClass = Student.class;
        Table tableAnnotation = (Table) studentClass.getAnnotation(Table.class);
        String tableName = "";
        if(tableAnnotation != null){
            tableName = tableAnnotation.value();
        }

        Field[] declaredFields = studentClass.getDeclaredFields();
        String[] columns = new String[declaredFields.length];
        int index = 0;
        for (Field declaredField : declaredFields) {
            Column column = declaredField.getAnnotation(Column.class);
            if(column!= null) {
                columns[index++] = column.columnName();
            }
        }
        
        String sql = "select ";
        for (int i=0; i<index; i++) {
            sql += columns[i];
            if(i<index-1){
                sql += ",";
            }
        }
        sql += " from " + tableName;
        System.out.println("sql = " + sql);
    }
}

```

## 6. 体会反射的动态性

**体会1：**

```java
public class ReflectionTest {

    //体会反射的动态性：动态的创建给定字符串对应的类的对象
    public <T> T getInstance(String className) throws Exception {

        Class clazz = Class.forName(className);

        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return (T) constructor.newInstance();

    }

    @Test
    public void test1() throws Exception {
        String className = "com.atguigu.java1.Person";
        Person p1 = getInstance(className);
        System.out.println(p1);
    }
}
```

**体会2：**

```java
public class ReflectionTest {
    //体会反射的动态性：动态的创建指定字符串对应类的对象，并调用指定的方法
    public Object  invoke(String className,String methodName) throws Exception {
        Class clazz = Class.forName(className);
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        //动态的创建指定字符串对应类的对象
        Object obj = constructor.newInstance();

        Method method = clazz.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(obj);
    }

    @Test
    public void test2() throws Exception {
        String info = (String) invoke("com.atguigu.java1.Person", "show");

        System.out.println("返回值为：" + info);

    }
}
```

**体会3：**

```java
public class ReflectionTest {
	@Test
    public void test1() throws Exception {
        //1.加载配置文件，并获取指定的fruitName值
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
        pros.load(is);
        String fruitStr = pros.getProperty("fruitName");
        //2.创建指定全类名对应类的实例
        Class clazz = Class.forName(fruitStr);
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        Fruit fruit = (Fruit) constructor.newInstance();
        //3. 调用相关方法，进行测试
        Juicer juicer = new Juicer();
        juicer.run(fruit);

    }

}

interface Fruit {
	public void squeeze();
}

class Apple implements Fruit {
	public void squeeze() {
		System.out.println("榨出一杯苹果汁儿");
	}
}

class Orange implements Fruit {
	public void squeeze() {
		System.out.println("榨出一杯桔子汁儿");
	}
}

class Juicer {
	public void run(Fruit f) {
		f.squeeze();
	}
}

```

其中，配置文件【config.properties】存放在当前Module的src下

```
com.atguigu.java1.Orange
```



## 7.随堂复习

### 1. 反射的概述（熟悉）

- Java给我们提供了一套API，使用这套API我们可以在运行时动态的获取指定对象所属的类，创建运行时类的对象，调用指定的结构（属性、方法）等。
- API：
  - `java.lang.Class`：代表一个类
  - java.lang.reflect.Method：代表类的方法
  - java.lang.reflect.Field：代表类的成员变量
  - java.lang.reflect.Constructor：代表类的构造器
  - … …
- 反射的优点和缺点
  - **优点：**
    - 提高了Java程序的灵活性和扩展性，`降低了耦合性`，提高`自适应`能力

    - 允许程序创建和控制任何类的对象，无需提前`硬编码`目标类
  - **缺点：**
    - 反射的`性能较低`。
      - 反射机制主要应用在对灵活性和扩展性要求很高的系统框架上
    - 反射会`模糊`程序内部逻辑，`可读性较差`。
- 反射，平时开发中，我们使用并不多。主要是在框架的底层使用。

### 2. Class:反射的源头

- Class的理解 （掌握）

  ```
  针对于编写好的.java源文件进行编译(使用javac.exe)，会生成一个或多个.class字节码文件。接着，我们使用
  java.exe命令对指定的.class文件进行解释运行。这个解释运行的过程中，我们需要将.class字节码文件加载(使用类的加载器)到内存中(存放在方法区)。加载到内存中的.class文件对应的结构即为Class的一个实例。
  ```

- 获取Class的实例的几种方式（前三种）

  - 类.class
  - 对象.getClass()
  - （使用较多）Class调用静态方法forName(String className)
  - (了解)使用ClassLoader的方法loadClass(String className)

- Class 可以指向哪些结构。

  ```
  简言之，所有Java类型！
  （1）class：外部类，成员(成员内部类，静态内部类)，局部内部类，匿名内部类
  （2）interface：接口
  （3）[]：数组
  （4）enum：枚举
  （5）annotation：注解@interface
  （6）primitive type：基本数据类型
  （7）void
  ```

### 3. 类的加载过程、类的加载器（理解）

- 类的加载过程

  ```
  过程1：类的装载(loading)
  将类的class文件读入内存，并为之创建一个java.lang.Class对象。此过程由类加载器完成
  
  过程2：链接(linking)
  > 验证(Verify):确保加载的类信息符合JVM规范，例如：以cafebabe开头，没有安全方面的问题。
  > 准备(Prepare):正式为类变量（static）分配内存并设置类变量默认初始值的阶段，这些内存都将在方法区中进行分配。
  > 解析(Resolve):虚拟机常量池内的符号引用（常量名）替换为直接引用（地址）的过程。
  
  过程3：初始化(initialization)
  执行类构造器<clinit>()方法的过程。
  类构造器<clinit>()方法是由编译期自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的。
  ```

- 类的加载器

  ```
  5.1 作用：负责类的加载，并对应于一个Class的实例。
  
  5.2 分类（分为两种）：
  > BootstrapClassLoader:引导类加载器、启动类加载器
       > 使用C/C++语言编写的，不能通过Java代码获取其实例
       > 负责加载Java的核心库（JAVA_HOME/jre/lib/rt.jar或sun.boot.class.path路径下的内容）
  
  > 继承于ClassLoader的类加载器
      > ExtensionClassLoader:扩展类加载器
              > 负责加载从java.ext.dirs系统属性所指定的目录中加载类库，或从JDK的安装目录的jre/lib/ext子目录下加载类库
      > SystemClassLoader/ApplicationClassLoader:系统类加载器、应用程序类加载器
              > 我们自定义的类，默认使用的类的加载器。
      > 用户自定义类的加载器
              > 实现应用的隔离（同一个类在一个应用程序中可以加载多份）；数据的加密。
  ```

### 4. 反射的应用1：创建运行时类的对象（重点）

```
Class clazz = Person.class;

//创建Person类的实例
Person per = (Person) clazz.newInstance();

System.out.println(per);
```

```
要想创建对象成功，需要满足：
条件1：要求运行时类中必须提供一个空参的构造器
条件2：要求提供的空参的构造器的权限要足够。
```

### 5. 反射的应用2：获取运行时类所有的结构

```
（了解）获取运行时类的内部结构1：所有属性、所有方法、所有构造器
（熟悉）获取运行时类的内部结构2：父类、接口们、包、带泛型的父类、父类的泛型等
```

### 6. 反射的应用3：调用指定的结构（重点）

```
3.1 调用指定的属性（步骤）
步骤1.通过Class实例调用getDeclaredField(String fieldName)，获取运行时类指定名的属性
步骤2. setAccessible(true)：确保此属性是可以访问的
步骤3. 通过Filed类的实例调用get(Object obj) （获取的操作）
                  或 set(Object obj,Object value) （设置的操作）进行操作。

3.2 调用指定的方法（步骤）
步骤1.通过Class的实例调用getDeclaredMethod(String methodName,Class ... args),获取指定的方法
步骤2. setAccessible(true)：确保此方法是可访问的
步骤3.通过Method实例调用invoke(Object obj,Object ... objs),即为对Method对应的方法的调用。
     invoke()的返回值即为Method对应的方法的返回值
     特别的：如果Method对应的方法的返回值类型为void，则invoke()返回值为null

3.3 调用指定的构造器（步骤）
步骤1.通过Class的实例调用getDeclaredConstructor(Class ... args)，获取指定参数类型的构造器
步骤2.setAccessible(true)：确保此构造器是可以访问的
步骤3.通过Constructor实例调用newInstance(Object ... objs),返回一个运行时类的实例。
```

### 7. 反射的应用4：注解的使用（了解）

略

### 8. 体会：反射的动态性

```java
public class ReflectTest {

    //体会：静态性
    public Person getInstance(){
        return new Person();
    }

    //体会：反射的动态性
    //举例1：
    public <T> T getInstance(String className) throws Exception {

        Class clazz = Class.forName(className);

        Constructor con = clazz.getDeclaredConstructor();
        con.setAccessible(true);

        return (T) con.newInstance();

    }

    @Test
    public void test1() throws Exception {
        Person p1 = getInstance();
        System.out.println(p1);

        String className = "com.atguigu04.other.dynamic.Person";
        Person per1 = getInstance(className);
        System.out.println(per1);

        String className1 = "java.util.Date";
        Date date1 = getInstance(className1);
        System.out.println(date1);
    }

    //体会：反射的动态性
    //举例2：
    public Object invoke(String className,String methodName) throws Exception {
        //1. 创建全类名对应的运行时类的对象
        Class clazz = Class.forName(className);

        Constructor con = clazz.getDeclaredConstructor();
        con.setAccessible(true);

        Object obj = con.newInstance();

        //2. 获取运行时类中指定的方法，并调用
        Method method = clazz.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(obj);
    }

    @Test
    public void test2() throws Exception {
        String className = "com.atguigu04.other.dynamic.Person";
        String methodName = "show";

        Object returnValue = invoke(className,methodName);
        System.out.println(returnValue);
    }

}
```



## 8.企业真题

### 2.1 反射概述

#### 1. 对反射了解吗？反射有什么好处？为什么需要反射？（微*银行）

```
类似问题：
> Java反射的作用是什么？(三*重工、上海*和网络)
> Java反射机制的作用有什么？(上海明*物联网)
> 反射的具体用途？（阿***芝*信用项目组）
```

略

#### 2. 反射的使用场合和作用、及其优缺点（*软国际）

```
类似问题：
> 反射机制的优缺点(君*科技)
> Java反射你怎么用的？（吉*航空）
```

略

#### 3. 实现Java反射的类有什么？(君*科技)

```
类似问题：
> Java反射 API 有几类？(北京*蓝)
```

问API。

#### 4. 反射是怎么实现的？（上海立*网络）

从Class说起。



### 2.2 Class的理解

#### 1. Class类的作用？生成Class对象的方法有哪些？(顺*)

反射的源头。 主要有三种。

#### 2. Class.forName("全路径") 会调用哪些方法 ？ 会调用构造方法吗？加载的类会放在哪？（上*银行外包）

Class.forName() 会执行执行类构造器<clinit>()方法。

不会调用构造方法

加载的类放在方法区。

### 2.3 类的加载

#### 1. 类加载流程（汇\*\*通、同\*顺、凡*科技）

略

###  2.4 创建对象

#### 1. 说一下创建对象的几种方法？（华油***集团、\*科软、凡\*科技）

```
类似问题：
> 除了使用new创建对象之外，还可以用什么方法创建对象？(*云网络)
```

![image-20221214145240412](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221214145240412.png)



#### 2. 如何找到对象实际类的？（*度）

对象.getClass();

```
Object obj = new Date();

obj.getClass();// 获取到的是Date。
```



#### 3. Java反射创建对象效率高还是通过new创建对象的效率高？(三*重工)

new 的方式。

### 2.5 调用属性、方法

#### 1. 如何利用反射机制来访问一个类的方法？（神州**软件）

```
调用指定的方法（步骤）
步骤1.通过Class的实例调用getDeclaredMethod(String methodName,Class ... args),获取指定的方法
步骤2. setAccessible(true)：确保此方法是可访问的
步骤3.通过Method实例调用invoke(Object obj,Object ... objs),即为对Method对应的方法的调用。
     invoke()的返回值即为Method对应的方法的返回值
     特别的：如果Method对应的方法的返回值类型为void，则invoke()返回值为null
```



#### 2. 说一下Java反射获取私有属性，如何改变值？（阿\*\*\**麻信用项目组）

```
调用指定的属性（步骤）
步骤1.通过Class实例调用getDeclaredField(String fieldName)，获取运行时类指定名的属性
步骤2. setAccessible(true)：确保此属性是可以访问的
步骤3. 通过Filed类的实例调用get(Object obj) （获取的操作）
                       或 set(Object obj,Object value) （设置的操作）进行操作。
```

```
针对于核心源码的api，内部的私有的结构在jdk17中就不可以通过反射调用了。
```



# 第18章_JDK8-17新特性



![第3阶段：Java高级应用-第18章](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/%E7%AC%AC3%E9%98%B6%E6%AE%B5%EF%BC%9AJava%E9%AB%98%E7%BA%A7%E5%BA%94%E7%94%A8-%E7%AC%AC18%E7%AB%A0.png)



***

## 1. Java版本迭代概述

### 1.1 发布特点（小步快跑，快速迭代）

| 发行版本  |  发行时间  |                             备注                             |
| :-------: | :--------: | :----------------------------------------------------------: |
| Java 1.0  | 1996.01.23 |             Sun公司发布了Java的第一个开发工具包              |
| Java 5.0  | 2004.09.30 | ①版本号从1.4直接更新至5.0；②平台更名为JavaSE、JavaEE、JavaME |
| Java 8.0  | 2014.03.18 | 此版本是继Java 5.0以来变化最大的版本。是长期支持版本（`LTS`） |
| Java 9.0  | 2017.09.22 |                **此版本开始，每半年更新一次**                |
| Java 10.0 | 2018.03.21 |                                                              |
| Java 11.0 | 2018.09.25 |     JDK安装包取消独立JRE安装包，是长期支持版本（`LTS`）      |
| Java 12.0 | 2019.03.19 |                                                              |
|    ...    |    ...     |                                                              |
| Java17.0  |  2021.09   |   发布Java 17.0，版本号也称为21.9，是长期支持版本（`LTS`）   |
|    ...    |    ...     |                                                              |
| Java19.0  |  2022.09   |               发布Java19.0，版本号也称为22.9。               |

从Java 9 这个版本开始，Java 的计划发布周期是 `6个月`。

这意味着Java的更新从传统的以`特性驱动`的发布周期，转变为以`时间驱动`的发布模式，并且承诺不会跳票。通过这样的方式，开发团队可以把一些关键特性尽早合并到 JDK 之中，以快速得到开发者反馈，在一定程度上避免出现像 Java 9 两次被迫延迟发布的窘况。

针对企业客户的需求，Oracle 将以`三年`为周期发布长期支持版本（long term support）。

Oracle 的官方观点认为：**与 Java 7->8->9 相比，Java 9->10->11的升级和 8->8u20->8u40 更相似。**

新模式下的 Java 版本发布都会包含许多变更，包括`语言变更`和 `JVM 变更`，这两者都会对 IDE、字节码库和框架产生重大影响。此外，不仅会新增其他 API，还会有 `API被删除`（这在 Java 8 之前没有发生过）。

目前看这种发布策略是非常成功的，**解开了 Java/JVM 演进的许多枷锁，至关重要的是，OpenJDK 的权力中心，正在转移到开发社区和开发者手中。**在新的模式中，既可以利用 LTS 满足企业长期可靠支持的需求，也可以满足各种开发者对于新特性迭代的诉求。因为用 2-3 年的最小间隔粒度来试验一个特性，基本是不现实的。

### 1.2 名词解释

#### 名词解释：Oracle JDK和Open JDK

这两个JDK最大不同就是许可证不一样。**但是对于个人用户来讲，没区别。**

|              | Oracle JDK                                                   | Open JDK                  |
| ------------ | ------------------------------------------------------------ | ------------------------- |
| 来源         | Oracle团队维护                                               | Oracle和Open Java社区     |
| 授权协议     | Java 17及更高版本 Oracle Java SE 许可证<br>Java16及更低版本甲骨文免费条款和条件 （NFTC） 许可协议 | GPL v2许可证              |
| 关系         | 由Open JDK构建，增加了少许内容                               |                           |
| 是否收费     | 2021年9月起Java17及更高版本所有用户免费。 16及更低版本，个人用户、开发用户免费。 | 2017年9月起，所有版本免费 |
| 对语法的支持 | 一致                                                         | 一致                      |

#### 名词解释：JEP

JEP(JDK Enhancement Proposals)：jdk 改进提案，每当需要有新的设想时候，JEP可以提出非正式的规范(specification)，被正式认可的JEP正式写进JDK的发展路线图并分配版本号。

#### 名词解释：LTS

LTS（Long-term Support）即长期支持。Oracle官网提供了对Oracle JDK个别版本的长期支持，即使发发行了新版本，比如目前最新的JDK19，在结束日期前，LTS版本都会被长期支持。（出了bug，会被修复，非LTS则不会再有补丁发布）所以，一定要选一个LTS版本，不然出了漏洞没人修复了。

| 版本      | 开始日期  | 结束日期  | 延期结束日期 |
| --------- | --------- | --------- | ------------ |
| 7（LTS）  | 2011年7月 | 2019年7月 | 2022年7月    |
| 8（LTS）  | 2014年3月 | 2022年3月 | 2030年12月   |
| 11（LTS） | 2018年9月 | 2023年9月 | 2026年9月    |
| 17（LTS） | 2021年9月 | 2026年9月 | 2029年9月    |
| 21（LTS） | 2023年9月 | 2028年9月 | 2031年9月    |

如果要选择Oracle JDK，目前可选的LTS版本为8、11、17三个。

### 1.3 各版本支持时间路线图

![4428b288361296048](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/4428b288361296048.png)

### 1.4 各版本介绍

![jdkxintexing](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/jdkxintexing.jpg)

#### jdk 9

Java 9 提供了`超过150项`新功能特性，包括备受期待的模块化系统、可交互的 REPL 工具：jshell，JDK 编译工具，Java 公共 API 和私有代码，以及安全增强、扩展提升、性能管理改善等。

特性太多，查看链接：

https://openjdk.java.net/projects/jdk9/

#### jdk 10

https://openjdk.java.net/projects/jdk/10/

> 286: [Local-Variable Type Inference](http://openjdk.java.net/jeps/286) 局部变量类型推断
> 296: [Consolidate the JDK Forest into a Single Repository](http://openjdk.java.net/jeps/296) JDK库的合并
> 304: [Garbage-Collector Interface](http://openjdk.java.net/jeps/304) 统一的垃圾回收接口
> 307: [Parallel Full GC for G1](http://openjdk.java.net/jeps/307) 为G1提供并行的Full GC
> 310: [Application Class-Data Sharing](http://openjdk.java.net/jeps/310) 应用程序类数据（AppCDS）共享
> 312: [Thread-Local Handshakes](http://openjdk.java.net/jeps/312) ThreadLocal握手交互
> 313: [Remove the Native-Header Generation Tool (javah)](http://openjdk.java.net/jeps/313) 移除JDK中附带的javah工具
> 314: [Additional Unicode Language-Tag Extensions](http://openjdk.java.net/jeps/314) 使用附加的Unicode语言标记扩展
> 316: [Heap Allocation on Alternative Memory Devices](http://openjdk.java.net/jeps/316) 能将堆内存占用分配给用户指定的备用内存设备
> 317: [Experimental Java-Based JIT Compiler](http://openjdk.java.net/jeps/317) 使用Graal基于Java的编译器
>
> 319: [Root Certificates](http://openjdk.java.net/jeps/319) 根证书
> 322: [Time-Based Release Versioning](http://openjdk.java.net/jeps/322) 基于时间定于的发布版本

#### jdk 11

https://openjdk.java.net/projects/jdk/11/

> 181: [Nest-Based Access Control](https://openjdk.java.net/jeps/181)  基于嵌套的访问控制
> 309: [Dynamic Class-File Constants](https://openjdk.java.net/jeps/309) 动态类文件常量
> 315: [Improve Aarch64 Intrinsics](https://openjdk.java.net/jeps/315) 改进 Aarch64 Intrinsics
> 318: [Epsilon: A No-Op Garbage Collector](https://openjdk.java.net/jeps/318) Epsilon — 一个No-Op（无操作）的垃圾收集器
> 320: [Remove the Java EE and CORBA Modules](https://openjdk.java.net/jeps/320) 删除 Java EE 和 CORBA 模块
> 321: [HTTP Client (Standard)](https://openjdk.java.net/jeps/321)  HTTPClient API
> 323: [Local-Variable Syntax for Lambda Parameters](https://openjdk.java.net/jeps/323)  用于 Lambda 参数的局部变量语法
> 324: [Key Agreement with Curve25519 and Curve448](https://openjdk.java.net/jeps/324) Curve25519 和 Curve448 算法的密钥协议
> 327: [Unicode 10](https://openjdk.java.net/jeps/327)
> 328: [Flight Recorder](https://openjdk.java.net/jeps/328) 飞行记录仪
> 329: [ChaCha20 and Poly1305 Cryptographic Algorithms](https://openjdk.java.net/jeps/329) ChaCha20 和 Poly1305 加密算法
> 330: [Launch Single-File Source-Code Programs](https://openjdk.java.net/jeps/330) 启动单一文件的源代码程序
> 331: [Low-Overhead Heap Profiling](https://openjdk.java.net/jeps/331) 低开销的 Heap Profiling
> 332: [Transport Layer Security (TLS) 1.3](https://openjdk.java.net/jeps/332) 支持 TLS 1.3
> 333: [ZGC: A Scalable Low-Latency Garbage Collector
>    (Experimental)](https://openjdk.java.net/jeps/333) 可伸缩低延迟垃圾收集器
> 335: [Deprecate the Nashorn JavaScript Engine](https://openjdk.java.net/jeps/335) 弃用 Nashorn JavaScript 引擎
> 336: [Deprecate the Pack200 Tools and API](https://openjdk.java.net/jeps/336)  弃用 Pack200 工具和 API

#### jdk 12

https://openjdk.java.net/projects/jdk/12/

> 189：[Shenandoah: A Low-Pause-Time Garbage Collector (Experimental)](https://openjdk.java.net/jeps/189) 低暂停时间的GC
> 230: [Microbenchmark Suite](https://openjdk.java.net/jeps/230) 微基准测试套件
> 325: [Switch Expressions (Preview)](https://openjdk.java.net/jeps/325) switch表达式
> 334: [JVM Constants API ](https://openjdk.java.net/jeps/334) JVM常量API
> 340: [One AArch64 Port, Not Two](https://openjdk.java.net/jeps/340) 只保留一个AArch64实现
> 341: [Default CDS Archives](https://openjdk.java.net/jeps/341) 默认类数据共享归档文件
> 344: [Abortable Mixed Collections for G1](https://openjdk.java.net/jeps/344) 可中止的G1 Mixed GC
> 346: [Promptly Return Unused Committed Memory from G1](https://openjdk.java.net/jeps/346) G1及时返回未使用的已分配内存

#### jdk 13

https://openjdk.java.net/projects/jdk/13/

> 350: [Dynamic CDS Archives](https://openjdk.java.net/jeps/350) 动态CDS档案
> 351: [ZGC: Uncommit Unused Memory](https://openjdk.java.net/jeps/351) ZGC:取消使用未使用的内存
> 353: [Reimplement the Legacy Socket API](https://openjdk.java.net/jeps/353) 重新实现旧版套接字API
> 354: [Switch Expressions (Preview)](https://openjdk.java.net/jeps/354) switch表达式（预览）
> 355: [Text Blocks (Preview)](https://openjdk.java.net/jeps/355) 文本块（预览）

#### jdk 14

https://openjdk.java.net/projects/jdk/14/

> 305: [Pattern Matching for instanceof (Preview)](https://openjdk.java.net/jeps/305) instanceof的模式匹配
> 343: [Packaging Tool (Incubator)](https://openjdk.java.net/jeps/343) 打包工具
> 345: [NUMA-Aware Memory Allocation for G1](https://openjdk.java.net/jeps/345) G1的NUMA-Aware内存分配
> 349: [JFR Event Streaming](https://openjdk.java.net/jeps/349) JFR事件流
> 352: [Non-Volatile Mapped Byte Buffers](https://openjdk.java.net/jeps/352) 非易失性映射字节缓冲区
> 358: [Helpful NullPointerExceptions](https://openjdk.java.net/jeps/358) 实用的NullPointerExceptions
> 359: [Records (Preview)](https://openjdk.java.net/jeps/359) 
> 361: [Switch Expressions (Standard)](https://openjdk.java.net/jeps/361) Switch表达式
> 362: [Deprecate the Solaris and SPARC Ports](https://openjdk.java.net/jeps/362) 弃用Solaris和SPARC端口
> 363: [Remove the Concurrent Mark Sweep (CMS) Garbage Collector](https://openjdk.java.net/jeps/363) 删除并发标记扫描（CMS）垃圾回收器
> 364: [ZGC on macOS](https://openjdk.java.net/jeps/364) 
> 365: [ZGC on Windows](https://openjdk.java.net/jeps/365) 
> 366: [Deprecate the ParallelScavenge + SerialOld GC Combination](https://openjdk.java.net/jeps/366) 弃用ParallelScavenge + SerialOld GC组合
> 367: [Remove the Pack200 Tools and API](https://openjdk.java.net/jeps/367) 删除Pack200工具和API
> 368: [Text Blocks (Second Preview)](https://openjdk.java.net/jeps/368) 文本块
> 370: [Foreign-Memory Access API (Incubator)](https://openjdk.java.net/jeps/370) 外部存储器访问API

#### jdk 15

https://openjdk.java.net/projects/jdk/15/

> 339: [Edwards-Curve Digital Signature Algorithm (EdDSA)](https://openjdk.java.net/jeps/339) EdDSA 数字签名算法
> 360: [Sealed Classes (Preview)](https://openjdk.java.net/jeps/360) 密封类（预览）
> 371: [Hidden Classes](https://openjdk.java.net/jeps/371) 隐藏类
> 372: [Remove the Nashorn JavaScript Engine](https://openjdk.java.net/jeps/372) 移除 Nashorn JavaScript 引擎
> 373: [Reimplement the Legacy DatagramSocket API](https://openjdk.java.net/jeps/373) 重新实现 Legacy DatagramSocket API
> 374: [Disable and Deprecate Biased Locking](https://openjdk.java.net/jeps/374) 禁用偏向锁定
> 375: [Pattern Matching for instanceof (Second Preview)](https://openjdk.java.net/jeps/375) instanceof 模式匹配（第二次预览）
> 377: [ZGC: A Scalable Low-Latency Garbage Collector](https://openjdk.java.net/jeps/377) ZGC：一个可扩展的低延迟垃圾收集器
> 378: [Text Blocks](https://openjdk.java.net/jeps/378) 文本块
> 379: [Shenandoah: A Low-Pause-Time Garbage Collector](https://openjdk.java.net/jeps/379) Shenandoah:低暂停时间垃圾收集器
> 381: [Remove the Solaris and SPARC Ports](https://openjdk.java.net/jeps/381) 移除 Solaris 和 SPARC 端口
> 383: [Foreign-Memory Access API (Second Incubator)](https://openjdk.java.net/jeps/383) 外部存储器访问 API（第二次孵化版）
> 384: [Records (Second Preview)](https://openjdk.java.net/jeps/384) Records（第二次预览）
> 385: [Deprecate RMI Activation for Removal](https://openjdk.java.net/jeps/385) 废弃 RMI 激活机制

#### jdk 16

https://openjdk.java.net/projects/jdk/16/

> 338: [Vector API (Incubator)](https://openjdk.java.net/jeps/338) Vector API（孵化器）
> 347: [Enable C++14 Language Features](https://openjdk.java.net/jeps/347) JDK C++的源码中允许使用C++14的语言特性
> 357: [Migrate from Mercurial to Git](https://openjdk.java.net/jeps/357) OpenJDK源码的版本控制从Mercurial (hg) 迁移到git
> 369: [Migrate to GitHub](https://openjdk.java.net/jeps/369) OpenJDK源码的版本控制迁移到github上
> 376: [ZGC: Concurrent Thread-Stack Processing](https://openjdk.java.net/jeps/376) ZGC：并发线程处理
> 380: [Unix-Domain Socket Channels](https://openjdk.java.net/jeps/380) Unix域套接字通道
> 386: [Alpine Linux Port](https://openjdk.java.net/jeps/386) 将glibc的jdk移植到使用musl的alpine linux上
> 387: [Elastic Metaspace](https://openjdk.java.net/jeps/387) 弹性元空间
> 388: [Windows/AArch64 Port](https://openjdk.java.net/jeps/388) 移植JDK到Windows/AArch64
> 389: [Foreign Linker API (Incubator)](https://openjdk.java.net/jeps/389) 提供jdk.incubator.foreign来简化native code的调用
> 390: [Warnings for Value-Based Classes](https://openjdk.java.net/jeps/390) 提供基于值的类的警告
> 392: [Packaging Tool](https://openjdk.java.net/jeps/392) jpackage打包工具转正
> 393: [Foreign-Memory Access API (Third Incubator)](https://openjdk.java.net/jeps/393) 
> 394: [Pattern Matching for instanceof](https://openjdk.java.net/jeps/394) Instanceof的模式匹配转正
> 395: [Records](https://openjdk.java.net/jeps/395) Records转正
> 396: [Strongly Encapsulate JDK Internals by Default](https://openjdk.java.net/jeps/396) 默认情况下，封装了JDK内部构件
> 397: [Sealed Classes (Second Preview)](https://openjdk.java.net/jeps/397) 密封类

#### jdk 17

https://openjdk.java.net/projects/jdk/17/

> 306: [Restore Always-Strict Floating-Point Semantics](https://openjdk.java.net/jeps/306) 恢复始终严格的浮点语义
>
> 356: [Enhanced Pseudo-Random Number Generators](https://openjdk.java.net/jeps/356) 增强型伪随机数生成器
>
> 382: [New macOS Rendering Pipeline](https://openjdk.java.net/jeps/382) 新的macOS渲染管道
>
> 391: [macOS/AArch64 Port](https://openjdk.java.net/jeps/391) macOS/AArch64端口
>
> 398: [Deprecate the Applet API for Removal](https://openjdk.java.net/jeps/398) 弃用Applet API后续将进行删除
>
> 403: [Strongly Encapsulate JDK Internals](https://openjdk.java.net/jeps/403) 强封装JDK的内部API
>
> 406: [Pattern Matching for switch (Preview)](https://openjdk.java.net/jeps/406) switch模式匹配（预览）
>
> 407: [Remove RMI Activation](https://openjdk.java.net/jeps/407) 删除RMI激活机制
>
> 409: [Sealed Classes](https://openjdk.java.net/jeps/409) 密封类转正
>
> 410: [Remove the Experimental AOT and JIT Compiler](https://openjdk.java.net/jeps/410) 删除实验性的AOT和JIT编译器
>
> 411: [Deprecate the Security Manager for Removal](https://openjdk.java.net/jeps/411) 弃用即将删除的安全管理器
>
> 412: [Foreign Function & Memory API (Incubator)](https://openjdk.java.net/jeps/412) 外部函数和内存API（孵化特性）
>
> 414: [Vector API (Second Incubator)](https://openjdk.java.net/jeps/414) Vector API（第二次孵化特性）
>
> 415: [Context-Specific Deserialization Filters](https://openjdk.java.net/jeps/415) 上下文特定的反序列化过滤器

### 1.5 JDK各版本下载链接

```http
https://www.oracle.com/java/technologies/downloads/archive/
```

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220525200441935.png" alt="image-20220525200441935" style="zoom:80%;" />

> 链接：https://pan.baidu.com/s/15QrBUOvfE9vjlTzN_EeVLg 
> 提取码：yyds 

![image-20221213235332866](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221213235332866.png)

### 1.6 如何学习新特性

对于新特性，我们应该从哪几个角度学习新特性呢？

- 语法层面：

  - 比如JDK5中的自动拆箱、自动装箱、enum、泛型
  - 比如JDK8中的lambda表达式、接口中的默认方法、静态方法
  - 比如JDK10中局部变量的类型推断
  - 比如JDK12中的switch
  - 比如JDK13中的文本块

- API层面：

  - 比如JDK8中的Stream、Optional、新的日期时间、HashMap的底层结构
  - 比如JDK9中String的底层结构
  - 新的 / 过时的 API

- 底层优化

  - 比如JDK8中永久代被元空间替代、新的JS执行引擎

  - 比如新的垃圾回收器、GC参数、JVM的优化


## 2. Java8新特性：Lambda表达式

### 2.1 关于Java8新特性简介

Java 8 (又称为 JDK 8或JDK1.8) 是 Java 语言开发的一个主要版本。 Java 8 是oracle公司于2014年3月发布，可以看成是自Java 5 以来最具革命性的版本。Java 8为Java语言、编译器、类库、开发工具与JVM带来了大量新特性。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220525201653599.png" alt="image-20220525201653599" style="zoom:80%;" />

- 速度更快

- 代码更少(增加了新的语法：**Lambda** **表达式**)

- 强大的 **Stream API**

- 便于并行
  - **并行流**就是把一个内容分成多个数据块，并用不同的线程分别处理每个数据块的流。相比较串行的流，并行的流可以很大程度上提高程序的执行效率。
  - Java 8 中将并行进行了优化，我们可以很容易的对数据进行并行操作。Stream API 可以声明性地通过 parallel() 与 sequential() 在并行流与顺序流之间进行切换。

- 最大化减少空指针异常：Optional

- Nashorn引擎，允许在JVM上运行JS应用
  - 发音“nass-horn”，是德国二战时一个坦克的命名
  - javascript运行在jvm已经不是新鲜事了，Rhino早在jdk6的时候已经存在。现在替代Rhino，官方的解释是Rhino相比其他JavaScript引擎（比如google的V8）实在太慢了，改造Rhino还不如重写。所以Nashorn的性能也是其一个亮点。
  - Nashorn 项目在 JDK 9 中得到改进；在JDK11 中`Deprecated`，后续JDK15版本中`remove`。在JDK11中取以代之的是GraalVM。（GraalVM是一个运行时平台，它支持Java和其他基于Java字节码的语言，但也支持其他语言，如JavaScript，Ruby，Python或LLVM。性能是Nashorn的2倍以上。）

### 2.2 冗余的匿名内部类

当需要启动一个线程去完成任务时，通常会通过`java.lang.Runnable`接口来定义任务内容，并使用`java.lang.Thread`类来启动该线程。代码如下：

```java
package com.atguigu.fp;

public class UseFunctionalProgramming {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("多线程任务执行！");
            }
        }).start(); // 启动线程
    }
}
```

本着“一切皆对象”的思想，这种做法是无可厚非的：首先创建一个`Runnable`接口的匿名内部类对象来指定任务内容，再将其交给一个线程来启动。

**代码分析：**

对于`Runnable`的匿名内部类用法，可以分析出几点内容：

* `Thread`类需要`Runnable`接口作为参数，其中的抽象`run`方法是用来指定线程任务内容的核心；
* 为了指定`run`的方法体，**不得不**需要`Runnable`接口的实现类；
* 为了省去定义一个`RunnableImpl`实现类的麻烦，**不得不**使用匿名内部类；
* 必须覆盖重写抽象`run`方法，所以方法名称、方法参数、方法返回值**不得不**再写一遍，且不能写错；
* 而实际上，**似乎只有方法体才是关键所在**。

### 2.3 好用的lambda表达式

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/lambda%E8%A1%A8%E8%BE%BE%E5%BC%8F.jpg" alt="lambda表达式" style="zoom:67%;" />

![image-20221111213355625](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20221111213355625.png)

### 2.4 Lambda 及其使用举例

Lambda 是一个**匿名函数**，我们可以把 Lambda 表达式理解为是**一段可以传递的代码**（将代码像数据一样进行传递）。使用它可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。

- 从匿名类到 Lambda 的转换举例1

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527101737072.png" alt="image-20220527101737072" style="zoom:67%;" />

- 从匿名类到 Lambda 的转换举例2

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527101814203.png" alt="image-20220527101814203" style="zoom:80%;" />

### 2.5 语法

Lambda 表达式：在Java 8 语言中引入的一种新的语法元素和操作符。这个操作符为 “`->`” ， 该操作符被称为 `Lambda 操作符`或`箭头操作符`。它将 Lambda 分为两个部分：

- 左侧：指定了 Lambda 表达式需要的参数列表
- 右侧：指定了 Lambda 体，是抽象方法的实现逻辑，也即 Lambda 表达式要执行的功能。

**语法格式一：**无参，无返回值

```java
@Test
public void test1(){
    //未使用Lambda表达式
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("我爱北京天安门");
        }
    };

    r1.run();

    System.out.println("***********************");

    //使用Lambda表达式
    Runnable r2 = () -> {
        System.out.println("我爱北京故宫");
    };

    r2.run();
}
```



**语法格式二：**Lambda 需要一个参数，但是没有返回值。

```java
@Test
public void test2(){
    //未使用Lambda表达式
    Consumer<String> con = new Consumer<String>() {
        @Override
        public void accept(String s) {
            System.out.println(s);
        }
    };
    con.accept("谎言和誓言的区别是什么？");

    System.out.println("*******************");

    //使用Lambda表达式
    Consumer<String> con1 = (String s) -> {
        System.out.println(s);
    };
    con1.accept("一个是听得人当真了，一个是说的人当真了");

}
```



**语法格式三：**数据类型可以省略，因为可由编译器推断得出，称为“类型推断”

```java
@Test
public void test3(){
    //语法格式三使用前
    Consumer<String> con1 = (String s) -> {
        System.out.println(s);
    };
    con1.accept("一个是听得人当真了，一个是说的人当真了");

    System.out.println("*******************");
    //语法格式三使用后
    Consumer<String> con2 = (s) -> {
        System.out.println(s);
    };
    con2.accept("一个是听得人当真了，一个是说的人当真了");

}
```



**语法格式四：**Lambda 若只需要一个参数时，参数的小括号可以省略

```java
@Test
public void test4(){
    //语法格式四使用前
    Consumer<String> con1 = (s) -> {
        System.out.println(s);
    };
    con1.accept("一个是听得人当真了，一个是说的人当真了");

    System.out.println("*******************");
    //语法格式四使用后
    Consumer<String> con2 = s -> {
        System.out.println(s);
    };
    con2.accept("一个是听得人当真了，一个是说的人当真了");


}
```



**语法格式五：**Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值

```java
@Test
public void test5(){
    //语法格式五使用前
    Comparator<Integer> com1 = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        }
    };

    System.out.println(com1.compare(12,21));
    System.out.println("*****************************");
    //语法格式五使用后
    Comparator<Integer> com2 = (o1,o2) -> {
        System.out.println(o1);
        System.out.println(o2);
        return o1.compareTo(o2);
    };

    System.out.println(com2.compare(12,6));


}
```



**语法格式六：**当 Lambda 体只有一条语句时，return 与大括号若有，都可以省略

```java
@Test
public void test6(){
    //语法格式六使用前
    Comparator<Integer> com1 = (o1,o2) -> {
        return o1.compareTo(o2);
    };

    System.out.println(com1.compare(12,6));

    System.out.println("*****************************");
    //语法格式六使用后
    Comparator<Integer> com2 = (o1,o2) -> o1.compareTo(o2);

    System.out.println(com2.compare(12,21));

}

@Test
public void test7(){
    //语法格式六使用前
    Consumer<String> con1 = s -> {
        System.out.println(s);
    };
    con1.accept("一个是听得人当真了，一个是说的人当真了");

    System.out.println("*****************************");
    //语法格式六使用后
    Consumer<String> con2 = s -> System.out.println(s);

    con2.accept("一个是听得人当真了，一个是说的人当真了");

}
```

### 2.6 关于类型推断

在语法格式三 Lambda 表达式中的参数类型都是由编译器推断得出的。Lambda 表达式中无需指定类型，程序依然可以编译，这是因为 javac 根据程序的上下文，在后台推断出了参数的类型。Lambda 表达式的类型依赖于上下文环境，是由编译器推断出来的。这就是所谓的“`类型推断`”。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527103215741.png" alt="image-20220527103215741" style="zoom:67%;" />

举例：

```java
@Test
public void test() {
    //类型推断1
    ArrayList<String> list = new ArrayList<>();
    //类型推断2
    int[] arr = {1, 2, 3};

}
```



## 3. Java8新特性：函数式(Functional)接口

### 3.1 什么是函数式接口

- 只包含`一个抽象方法`（Single Abstract Method，简称SAM）的接口，称为函数式接口。当然该接口可以包含其他非抽象方法。
- 你可以通过 Lambda 表达式来创建该接口的对象。（若 Lambda 表达式抛出一个受检异常(即：非运行时异常)，那么该异常需要在目标接口的抽象方法上进行声明）。
- 我们可以在一个接口上使用 `@FunctionalInterface` 注解，这样做可以检查它是否是一个函数式接口。同时 javadoc 也会包含一条声明，说明这个接口是一个函数式接口。
- 在`java.util.function`包下定义了Java 8 的丰富的函数式接口

### 3.2 如何理解函数式接口

![](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/03-Overview.png)

- Java从诞生日起就是一直倡导“一切皆对象”，在Java里面面向对象(OOP)编程是一切。但是随着python、scala等语言的兴起和新技术的挑战，Java不得不做出调整以便支持更加广泛的技术要求，即Java不但可以支持OOP还可以支持OOF（面向函数编程）
  - Java8引入了Lambda表达式之后，Java也开始支持函数式编程。
  - Lambda表达式不是Java最早使用的。目前C++，C#，Python，Scala等均支持Lambda表达式。
- 面向对象的思想：
  * 做一件事情，找一个能解决这个事情的对象，调用对象的方法，完成事情。
- 函数式编程思想：
  * 只要能获取到结果，谁去做的，怎么做的都不重要，重视的是结果，不重视过程。
- 在函数式编程语言当中，函数被当做一等公民对待。在将函数作为一等公民的编程语言中，Lambda表达式的类型是函数。但是在Java8中，有所不同。在Java8中，Lambda表达式是对象，而不是函数，它们必须依附于一类特别的对象类型——函数式接口。
- 简单的说，在Java8中，Lambda表达式就是一个函数式接口的实例。这就是Lambda表达式和函数式接口的关系。也就是说，只要一个对象是函数式接口的实例，那么该对象就可以用Lambda表达式来表示。

### 3.3 举例

举例1：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527111442115.png" alt="image-20220527111442115" style="zoom: 80%;" />

举例2：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527111621424.png" alt="image-20220527111621424" style="zoom:80%;" />

作为参数传递 Lambda 表达式：

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527111751485.png" alt="image-20220527111751485" style="zoom:80%;" />

> 作为参数传递 Lambda 表达式：为了将 Lambda 表达式作为参数传递，接收Lambda 表达式的参数类型必须是与该 Lambda 表达式兼容的函数式接口的类型。

### 3.4 Java 内置函数式接口

#### 3.4.1 之前的函数式接口

之前学过的接口，有些就是函数式接口，比如：

- java.lang.Runnable
  - public void run()
- java.lang.Iterable<T>
  - public Iterator<T> iterate()
- java.lang.Comparable<T>
  - public int compareTo(T t)
- java.util.Comparator<T>
  - public int compare(T t1, T t2)

#### 3.4.2 四大核心函数式接口

| 函数式接口         | 称谓       | 参数类型 | 用途                                                         |
| ------------------ | ---------- | -------- | ------------------------------------------------------------ |
| `Consumer<T>  `    | 消费型接口 | T        | 对类型为T的对象应用操作，包含方法：  `void accept(T t)  `    |
| `Supplier<T>  `    | 供给型接口 | 无       | 返回类型为T的对象，包含方法：`T get()  `                     |
| `Function<T, R>  ` | 函数型接口 | T        | 对类型为T的对象应用操作，并返回结果。结果是R类型的对象。包含方法：`R apply(T t)  ` |
| `Predicate<T>  `   | 判断型接口 | T        | 确定类型为T的对象是否满足某约束，并返回 boolean 值。包含方法：`boolean test(T t)  ` |

#### 3.4.3 其它接口

**类型1：消费型接口**

消费型接口的抽象方法特点：有形参，但是返回值类型是void

| 接口名               | 抽象方法                       | 描述                       |
| -------------------- | ------------------------------ | -------------------------- |
| BiConsumer<T,U>      | void accept(T t, U u)          | 接收两个对象用于完成功能   |
| DoubleConsumer       | void accept(double value)      | 接收一个double值           |
| IntConsumer          | void accept(int value)         | 接收一个int值              |
| LongConsumer         | void accept(long value)        | 接收一个long值             |
| ObjDoubleConsumer<T> | void accept(T t, double value) | 接收一个对象和一个double值 |
| ObjIntConsumer<T>    | void accept(T t, int value)    | 接收一个对象和一个int值    |
| ObjLongConsumer<T>   | void accept(T t, long value)   | 接收一个对象和一个long值   |

**类型2：供给型接口**

这类接口的抽象方法特点：无参，但是有返回值

| 接口名          | 抽象方法               | 描述              |
| --------------- | ---------------------- | ----------------- |
| BooleanSupplier | boolean getAsBoolean() | 返回一个boolean值 |
| DoubleSupplier  | double getAsDouble()   | 返回一个double值  |
| IntSupplier     | int getAsInt()         | 返回一个int值     |
| LongSupplier    | long getAsLong()       | 返回一个long值    |

**类型3：函数型接口**

这类接口的抽象方法特点：既有参数又有返回值

| 接口名                  | 抽象方法                                        | 描述                                                |
| ----------------------- | ----------------------------------------------- | --------------------------------------------------- |
| UnaryOperator<T>        | T apply(T t)                                    | 接收一个T类型对象，返回一个T类型对象结果            |
| DoubleFunction<R>       | R apply(double value)                           | 接收一个double值，返回一个R类型对象                 |
| IntFunction<R>          | R apply(int value)                              | 接收一个int值，返回一个R类型对象                    |
| LongFunction<R>         | R apply(long value)                             | 接收一个long值，返回一个R类型对象                   |
| ToDoubleFunction<T>     | double applyAsDouble(T value)                   | 接收一个T类型对象，返回一个double                   |
| ToIntFunction<T>        | int applyAsInt(T value)                         | 接收一个T类型对象，返回一个int                      |
| ToLongFunction<T>       | long applyAsLong(T value)                       | 接收一个T类型对象，返回一个long                     |
| DoubleToIntFunction     | int applyAsInt(double value)                    | 接收一个double值，返回一个int结果                   |
| DoubleToLongFunction    | long applyAsLong(double value)                  | 接收一个double值，返回一个long结果                  |
| IntToDoubleFunction     | double applyAsDouble(int value)                 | 接收一个int值，返回一个double结果                   |
| IntToLongFunction       | long applyAsLong(int value)                     | 接收一个int值，返回一个long结果                     |
| LongToDoubleFunction    | double applyAsDouble(long value)                | 接收一个long值，返回一个double结果                  |
| LongToIntFunction       | int applyAsInt(long value)                      | 接收一个long值，返回一个int结果                     |
| DoubleUnaryOperator     | double applyAsDouble(double operand)            | 接收一个double值，返回一个double                    |
| IntUnaryOperator        | int applyAsInt(int operand)                     | 接收一个int值，返回一个int结果                      |
| LongUnaryOperator       | long applyAsLong(long operand)                  | 接收一个long值，返回一个long结果                    |
| BiFunction<T,U,R>       | R apply(T t, U u)                               | 接收一个T类型和一个U类型对象，返回一个R类型对象结果 |
| BinaryOperator<T>       | T apply(T t, T u)                               | 接收两个T类型对象，返回一个T类型对象结果            |
| ToDoubleBiFunction<T,U> | double applyAsDouble(T t, U u)                  | 接收一个T类型和一个U类型对象，返回一个double        |
| ToIntBiFunction<T,U>    | int applyAsInt(T t, U u)                        | 接收一个T类型和一个U类型对象，返回一个int           |
| ToLongBiFunction<T,U>   | long applyAsLong(T t, U u)                      | 接收一个T类型和一个U类型对象，返回一个long          |
| DoubleBinaryOperator    | double applyAsDouble(double left, double right) | 接收两个double值，返回一个double结果                |
| IntBinaryOperator       | int applyAsInt(int left, int right)             | 接收两个int值，返回一个int结果                      |
| LongBinaryOperator      | long applyAsLong(long left, long right)         | 接收两个long值，返回一个long结果                    |

**类型4：判断型接口**

这类接口的抽象方法特点：有参，但是返回值类型是boolean结果。

| 接口名           | 抽象方法                   | 描述             |
| ---------------- | -------------------------- | ---------------- |
| BiPredicate<T,U> | boolean test(T t, U u)     | 接收两个对象     |
| DoublePredicate  | boolean test(double value) | 接收一个double值 |
| IntPredicate     | boolean test(int value)    | 接收一个int值    |
| LongPredicate    | boolean test(long value)   | 接收一个long值   |

#### 3.4.4 内置接口代码演示

举例1：

```java
package com.atguigu.four;

import java.util.Arrays;
import java.util.List;

public class TestConsumer {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("java","c","python","c++","VB","C#");
        //遍历Collection集合，并将传递给action参数的操作代码应用在每一个元素上。
        list.forEach(s -> System.out.println(s));
    }
}
```

举例2：

```java
package com.atguigu.four;

import java.util.function.Supplier;

public class TestSupplier {
    public static void main(String[] args) {
        Supplier<String> supplier = () -> "尚硅谷";
        System.out.println(supplier.get());
    }
}
```

举例3：

```java
package com.atguigu.four;

import java.util.ArrayList;

public class TestPredicate {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("hello");
        list.add("java");
        list.add("atguigu");
        list.add("ok");
        list.add("yes");

        System.out.println("删除之前：");
        list.forEach(t-> System.out.println(t));
		
        //用于删除集合中满足filter指定的条件判断的。
        //删除包含o字母的元素
        list.removeIf(s -> s.contains("o"));

        System.out.println("删除包含o字母的元素之后：");
        list.forEach(t-> System.out.println(t));
    }
}
```

举例4：

```java
package com.atguigu.four;

import java.util.function.Function;

public class TestFunction {
    public static void main(String[] args) {
        //使用Lambda表达式实现Function<T,R>接口，可以实现将一个字符串首字母转为大写的功能。
        Function<String,String> fun = s -> s.substring(0,1).toUpperCase() + s.substring(1);
        System.out.println(fun.apply("hello"));
    }
}

```

#### 3.4.5 练习

**练习1：无参无返回值形式**

假如有自定义函数式接口Call如下：

```java
public interface Call {
    void shout();
}
```

在测试类中声明一个如下方法：

```java
public static void callSomething(Call call){
		call.shout();
}
```

在测试类的main方法中调用callSomething方法，并用Lambda表达式为形参call赋值，可以喊出任意你想说的话。

```java
public class TestLambda {
	public static void main(String[] args) {
		callSomething(()->System.out.println("回家吃饭"));
		callSomething(()->System.out.println("我爱你"));
		callSomething(()->System.out.println("滚蛋"));
		callSomething(()->System.out.println("回来"));
	}
	public static void callSomething(Call call){
		call.shout();
	}
}
interface Call {
    void shout();
}
```

**练习2：消费型接口**

代码示例：Consumer<T>接口

在JDK1.8中Collection集合接口的父接口Iterable接口中增加了一个默认方法：

`public default void forEach(Consumer<? super T> action) `遍历Collection集合的每个元素，执行“xxx消费型”操作。

在JDK1.8中Map集合接口中增加了一个默认方法：

`public default void forEach(BiConsumer<? super K,? super V> action)`遍历Map集合的每对映射关系，执行“xxx消费型”操作。

案例：

（1）创建一个Collection系列的集合，添加一些字符串，调用forEach方法遍历查看

（2）创建一个Map系列的集合，添加一些(key,value)键值对，调用forEach方法遍历查看

示例代码：

```java
	@Test
	public void test1(){
		List<String> list = Arrays.asList("hello","java","lambda","atguigu");
		list.forEach(s -> System.out.println(s));
    }
	@Test
	public void test2(){
		HashMap<Integer,String> map = new HashMap<>();
		map.put(1, "hello");
		map.put(2, "java");
		map.put(3, "lambda");
		map.put(4, "atguigu");
		map.forEach((k,v) -> System.out.println(k+"->"+v));
	}
```

**练习3：供给型接口**

代码示例：Supplier<T>接口

在JDK1.8中增加了StreamAPI，java.util.stream.Stream<T>是一个数据流。这个类型有一个静态方法：

`public static <T> Stream<T> generate(Supplier<T> s)`可以创建Stream的对象。而又包含一个forEach方法可以遍历流中的元素：`public void forEach(Consumer<? super T> action)`。

案例：

现在请调用Stream的generate方法，来产生一个流对象，并调用Math.random()方法来产生数据，为Supplier函数式接口的形参赋值。最后调用forEach方法遍历流中的数据查看结果。

```java
	@Test
	public void test2(){
		Stream.generate(() -> Math.random()).forEach(num -> System.out.println(num));
	}
```

**练习4：功能型接口**

代码示例：Function<T,R>接口

在JDK1.8时Map接口增加了很多方法，例如：

`public default void replaceAll(BiFunction<? super K,? super V,? extends V> function) `按照function指定的操作替换map中的value。

`public default void forEach(BiConsumer<? super K,? super V> action)`遍历Map集合的每对映射关系，执行“xxx消费型”操作。

案例：

（1）声明一个Employee员工类型，包含编号、姓名、薪资。

（2）添加n个员工对象到一个HashMap<Integer,Employee>集合中，其中员工编号为key，员工对象为value。

（3）调用Map的forEach遍历集合

（4）调用Map的replaceAll方法，将其中薪资低于10000元的，薪资设置为10000。

（5）再次调用Map的forEach遍历集合查看结果

Employee类：

```java
class Employee{
	private int id;
	private String name;
	private double salary;
	public Employee(int id, String name, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
	public Employee() {
		super();
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
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}
	
}
```

测试类：

```java
import java.util.HashMap;

public class TestLambda {
	public static void main(String[] args) {
		HashMap<Integer,Employee> map = new HashMap<>();
		Employee e1 = new Employee(1, "张三", 8000);
		Employee e2 = new Employee(2, "李四", 9000);
		Employee e3 = new Employee(3, "王五", 10000);
		Employee e4 = new Employee(4, "赵六", 11000);
		Employee e5 = new Employee(5, "钱七", 12000);
		
		map.put(e1.getId(), e1);
		map.put(e2.getId(), e2);
		map.put(e3.getId(), e3);
		map.put(e4.getId(), e4);
		map.put(e5.getId(), e5);
		
		map.forEach((k,v) -> System.out.println(k+"="+v));
		System.out.println();
		
		map.replaceAll((k,v)->{
			if(v.getSalary()<10000){
				v.setSalary(10000);
			}
			return v;
		});
		map.forEach((k,v) -> System.out.println(k+"="+v));
	}
}
```

**练习5：判断型接口**

代码示例：Predicate<T>接口

JDK1.8时，Collecton<E>接口增加了一下方法，其中一个如下：

`public default boolean removeIf(Predicate<? super E> filter)` 用于删除集合中满足filter指定的条件判断的。

`public default void forEach(Consumer<? super T> action) `遍历Collection集合的每个元素，执行“xxx消费型”操作。

案例：

（1）添加一些字符串到一个Collection集合中

（2）调用forEach遍历集合

（3）调用removeIf方法，删除其中字符串的长度<5的

（4）再次调用forEach遍历集合

```java
import java.util.ArrayList;

public class TestLambda {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("hello");
		list.add("java");
		list.add("atguigu");
		list.add("ok");
		list.add("yes");
		
		list.forEach(str->System.out.println(str));
		System.out.println();
		
		list.removeIf(str->str.length()<5);
		list.forEach(str->System.out.println(str));
	}
}
```

**练习6：判断型接口**

案例：

（1）声明一个Employee员工类型，包含编号、姓名、性别，年龄，薪资。

（2）声明一个EmployeeSerice员工管理类，包含一个ArrayList<Employee>集合的属性all，在EmployeeSerice的构造器中，创建一些员工对象，为all集合初始化。

（3）在EmployeeSerice员工管理类中，声明一个方法：ArrayList<Employee> get(Predicate<Employee> p)，即将满足p指定的条件的员工，添加到一个新的ArrayList<Employee> 集合中返回。

（4）在测试类中创建EmployeeSerice员工管理类的对象，并调用get方法，分别获取：

* 所有员工对象
* 所有年龄超过35的员工
* 所有薪资高于15000的女员工
* 所有编号是偶数的员工
* 名字是“张三”的员工
* 年龄超过25，薪资低于10000的男员工

示例代码：

Employee类：

```java
public class Employee{
	private int id;
	private String name;
	private char gender;
	private int age;
	private double salary;
	
	public Employee(int id, String name, char gender, int age, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.salary = salary;
	}
	public Employee() {
		super();
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
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", age=" + age + ", salary=" + salary
				+ "]";
	}
}
```

员工管理类：

```java
class EmployeeService{
	private ArrayList<Employee> all;
	public EmployeeService(){
		all = new ArrayList<Employee>();
		all.add(new Employee(1, "张三", '男', 33, 8000));
		all.add(new Employee(2, "翠花", '女', 23, 18000));
		all.add(new Employee(3, "无能", '男', 46, 8000));
		all.add(new Employee(4, "李四", '女', 23, 9000));
		all.add(new Employee(5, "老王", '男', 23, 15000));
		all.add(new Employee(6, "大嘴", '男', 23, 11000));
	}
	public ArrayList<Employee> get(Predicate<Employee> p){
		ArrayList<Employee> result = new ArrayList<Employee>();
		for (Employee emp : result) {
			if(p.test(emp)){
				result.add(emp);
			}
		}
		return result;
	}
}
```

测试类：

```java
public class TestLambda {
	public static void main(String[] args) {
		EmployeeService es = new EmployeeService();
		
		es.get(e -> true).forEach(e->System.out.println(e));
		System.out.println();
		es.get(e -> e.getAge()>35).forEach(e->System.out.println(e));
		System.out.println();
		es.get(e -> e.getSalary()>15000 && e.getGender()=='女').forEach(e->System.out.println(e));
		System.out.println();
		es.get(e -> e.getId()%2==0).forEach(e->System.out.println(e));
		System.out.println();
		es.get(e -> "张三".equals(e.getName())).forEach(e->System.out.println(e));
		System.out.println();
		es.get(e -> e.getAge()>25 && e.getSalary()<10000 && e.getGender()=='男').forEach(e->System.out.println(e));
	}
}
```

## 4. Java8新特性：方法引用与构造器引用

Lambda表达式是可以简化函数式接口的变量或形参赋值的语法。而方法引用和构造器引用是为了简化Lambda表达式的。

### 4.1 方法引用

当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用！

方法引用可以看做是Lambda表达式深层次的表达。换句话说，方法引用就是Lambda表达式，也就是函数式接口的一个实例，通过方法的名字来指向一个方法，可以认为是Lambda表达式的一个语法糖。

> 语法糖（Syntactic sugar），也译为糖衣语法，是由英国计算机科学家彼得·约翰·兰达（Peter J. Landin）发明的一个术语，指计算机语言中添加的某种语法，这种语法`对语言的功能并没有影响，但是更方便程序员使用`。通常来说使用语法糖能够增加程序的可读性，从而减少程序代码出错的机会。

#### 4.1.1 方法引用格式

- 格式：使用方法引用操作符 “`::`” 将类(或对象) 与 方法名分隔开来。
  - 两个:中间不能有空格，而且必须英文状态下半角输入

- 如下三种主要使用情况：
  - 情况1：`对象 :: 实例方法名`
  - 情况2：`类 :: 静态方法名`
  - 情况3：`类 :: 实例方法名`

#### 4.1.2 方法引用使用前提

**要求1：**Lambda体只有一句语句，并且是通过调用一个对象的/类现有的方法来完成的

例如：System.out对象，调用println()方法来完成Lambda体

​           Math类，调用random()静态方法来完成Lambda体

**要求2：**

针对情况1：函数式接口中的抽象方法a在被重写时使用了某一个对象的方法b。如果方法a的形参列表、返回值类型与方法b的形参列表、返回值类型都相同，则我们可以使用方法b实现对方法a的重写、替换。


针对情况2：函数式接口中的抽象方法a在被重写时使用了某一个类的静态方法b。如果方法a的形参列表、返回值类型与方法b的形参列表、返回值类型都相同，则我们可以使用方法b实现对方法a的重写、替换。

针对情况3：函数式接口中的抽象方法a在被重写时使用了某一个对象的方法b。如果方法a的返回值类型与方法b的返回值类型相同，同时方法a的形参列表中有n个参数，方法b的形参列表有n-1个参数，且方法a的第1个参数作为方法b的调用者，且方法a的后n-1参数与方法b的n-1参数匹配（类型相同或满足多态场景也可以）

例如：t->System.out.println(t)

​        () -> Math.random() 都是无参

#### 4.1.3 举例

```java
public class MethodRefTest {

	// 情况一：对象 :: 实例方法
	//Consumer中的void accept(T t)
	//PrintStream中的void println(T t)
	@Test
	public void test1() {
		Consumer<String> con1 = str -> System.out.println(str);
		con1.accept("北京");

		System.out.println("*******************");
		PrintStream ps = System.out;
		Consumer<String> con2 = ps::println;
		con2.accept("beijing");
	}
	
	//Supplier中的T get()
	//Employee中的String getName()
	@Test
	public void test2() {
		Employee emp = new Employee(1001,"Tom",23,5600);

		Supplier<String> sup1 = () -> emp.getName();
		System.out.println(sup1.get());

		System.out.println("*******************");
		Supplier<String> sup2 = emp::getName;
		System.out.println(sup2.get());

	}

	// 情况二：类 :: 静态方法
	//Comparator中的int compare(T t1,T t2)
	//Integer中的int compare(T t1,T t2)
	@Test
	public void test3() {
		Comparator<Integer> com1 = (t1,t2) -> Integer.compare(t1,t2);
		System.out.println(com1.compare(12,21));

		System.out.println("*******************");

		Comparator<Integer> com2 = Integer::compare;
		System.out.println(com2.compare(12,3));

	}
	
	//Function中的R apply(T t)
	//Math中的Long round(Double d)
	@Test
	public void test4() {
		Function<Double,Long> func = new Function<Double, Long>() {
			@Override
			public Long apply(Double d) {
				return Math.round(d);
			}
		};

		System.out.println("*******************");

		Function<Double,Long> func1 = d -> Math.round(d);
		System.out.println(func1.apply(12.3));

		System.out.println("*******************");

		Function<Double,Long> func2 = Math::round;
		System.out.println(func2.apply(12.6));
	}

	// 情况三：类 :: 实例方法  (有难度)
	// Comparator中的int comapre(T t1,T t2)
	// String中的int t1.compareTo(t2)
	@Test
	public void test5() {
		Comparator<String> com1 = (s1,s2) -> s1.compareTo(s2);
		System.out.println(com1.compare("abc","abd"));

		System.out.println("*******************");

		Comparator<String> com2 = String :: compareTo;
		System.out.println(com2.compare("abd","abm"));
	}

	//BiPredicate中的boolean test(T t1, T t2);
	//String中的boolean t1.equals(t2)
	@Test
	public void test6() {
		BiPredicate<String,String> pre1 = (s1,s2) -> s1.equals(s2);
		System.out.println(pre1.test("abc","abc"));

		System.out.println("*******************");
		BiPredicate<String,String> pre2 = String :: equals;
		System.out.println(pre2.test("abc","abd"));
	}
	
	// Function中的R apply(T t)
	// Employee中的String getName();
	@Test
	public void test7() {
		Employee employee = new Employee(1001, "Jerry", 23, 6000);


		Function<Employee,String> func1 = e -> e.getName();
		System.out.println(func1.apply(employee));

		System.out.println("*******************");
		Function<Employee,String> func2 = Employee::getName;
		System.out.println(func2.apply(employee));
	}

}
```

### 4.2 构造器引用

当Lambda表达式是创建一个对象，并且满足Lambda表达式形参，正好是给创建这个对象的构造器的实参列表，就可以使用构造器引用。

格式：`类名::new`

举例：

```java
public class ConstructorRefTest {
	//构造器引用
    //Supplier中的T get()
    //Employee的空参构造器：Employee()
    @Test
    public void test1(){

        Supplier<Employee> sup = new Supplier<Employee>() {
            @Override
            public Employee get() {
                return new Employee();
            }
        };
        System.out.println("*******************");

        Supplier<Employee>  sup1 = () -> new Employee();
        System.out.println(sup1.get());

        System.out.println("*******************");

        Supplier<Employee>  sup2 = Employee :: new;
        System.out.println(sup2.get());
    }

	//Function中的R apply(T t)
    @Test
    public void test2(){
        Function<Integer,Employee> func1 = id -> new Employee(id);
        Employee employee = func1.apply(1001);
        System.out.println(employee);

        System.out.println("*******************");

        Function<Integer,Employee> func2 = Employee :: new;
        Employee employee1 = func2.apply(1002);
        System.out.println(employee1);

    }

	//BiFunction中的R apply(T t,U u)
    @Test
    public void test3(){
        BiFunction<Integer,String,Employee> func1 = (id,name) -> new Employee(id,name);
        System.out.println(func1.apply(1001,"Tom"));

        System.out.println("*******************");

        BiFunction<Integer,String,Employee> func2 = Employee :: new;
        System.out.println(func2.apply(1002,"Tom"));

    }

}
```

```java
package com.atguigu.java2;

/**
 * @author 尚硅谷-宋红康 邮箱：shkstart@126.com
 */
public class Employee {

	private int id;
	private String name;
	private int age;
	private double salary;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Employee() {
		System.out.println("Employee().....");
	}

	public Employee(int id) {
		this.id = id;
		System.out.println("Employee(int id).....");
	}

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Employee(int id, String name, int age, double salary) {

		this.id = id;
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + ", salary=" + salary + '}';
	}

}

```

### 4.3 数组构造引用

当Lambda表达式是创建一个数组对象，并且满足Lambda表达式形参，正好是给创建这个数组对象的长度，就可以数组构造引用。

格式：`数组类型名::new`

举例：

```java
//数组引用
//Function中的R apply(T t)
@Test
public void test4(){
    Function<Integer,String[]> func1 = length -> new String[length];
    String[] arr1 = func1.apply(5);
    System.out.println(Arrays.toString(arr1));

    System.out.println("*******************");

    Function<Integer,String[]> func2 = String[] :: new;
    String[] arr2 = func2.apply(10);
    System.out.println(Arrays.toString(arr2));

}
```

## 5. Java8新特性：强大的Stream API

### 5.1 说明

- Java8中有两大最为重要的改变。第一个是 Lambda 表达式；另外一个则是 Stream API。
- Stream API ( java.util.stream) 把真正的函数式编程风格引入到Java中。这是目前为止对Java类库`最好的补充`，因为Stream API可以极大提供Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。
- Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。 **使用Stream API 对集合数据进行操作，就类似于使用 SQL 执行的数据库查询。**也可以使用 Stream API 来并行执行操作。简言之，Stream API 提供了一种高效且易于使用的处理数据的方式。

### 5.2 为什么要使用Stream API

实际开发中，项目中多数数据源都来自于MySQL、Oracle等。但现在数据源可以更多了，有MongDB，Radis等，而这些NoSQL的数据就需要Java层面去处理。

### 5.3 什么是Stream

Stream 是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。

Stream 和 Collection 集合的区别：**Collection 是一种静态的内存数据结构，讲的是数据，而 Stream 是有关计算的，讲的是计算。**前者是主要面向内存，存储在内存中，后者主要是面向 CPU，通过 CPU 实现计算。

注意：

①Stream 自己不会存储元素。

②Stream 不会改变源对象。相反，他们会返回一个持有结果的新Stream。

③Stream 操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。即一旦执行终止操作，就执行中间操作链，并产生结果。

④ Stream一旦执行了终止操作，就不能再调用其它中间操作或终止操作了。

### 5.4 Stream的操作三个步骤

**1- 创建 Stream**
一个数据源（如：集合、数组），获取一个流

**2- 中间操作**
每次处理都会返回一个持有结果的新Stream，即中间操作的方法返回值仍然是Stream类型的对象。因此中间操作可以是个`操作链`，可对数据源的数据进行n次处理，但是在终结操作前，并不会真正执行。

**3- 终止操作(终端操作)**
终止操作的方法返回值类型就不再是Stream了，因此一旦执行终止操作，就结束整个Stream操作了。一旦执行终止操作，就执行中间操作链，最终产生结果并结束Stream。

<img src="https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220514180803311.png" alt="image-20220514180803311" style="zoom: 50%;" />

#### 5.4.1 创建Stream实例

**方式一：通过集合**

Java8 中的 Collection 接口被扩展，提供了两个获取流的方法：

- default Stream<E> stream() : 返回一个顺序流

- default Stream<E> parallelStream() : 返回一个并行流

```java
@Test
public void test01(){
    List<Integer> list = Arrays.asList(1,2,3,4,5);

    //JDK1.8中，Collection系列集合增加了方法
    Stream<Integer> stream = list.stream();
}
```



**方式二：通过数组**

Java8 中的 Arrays 的静态方法 stream() 可以获取数组流：

- static <T> Stream<T> stream(T[] array): 返回一个流
- public static IntStream stream(int[] array)
- public static LongStream stream(long[] array)
- public static DoubleStream stream(double[] array)

```java
@Test
public void test02(){
    String[] arr = {"hello","world"};
    Stream<String> stream = Arrays.stream(arr); 
}

@Test
public void test03(){
    int[] arr = {1,2,3,4,5};
    IntStream stream = Arrays.stream(arr);
}
```



**方式三：通过Stream的of()**

可以调用Stream类静态方法 of(), 通过显示值创建一个流。它可以接收任意数量的参数。

- public static<T> Stream<T> of(T... values) : 返回一个流

```java
@Test
public void test04(){
    Stream<Integer> stream = Stream.of(1,2,3,4,5);
    stream.forEach(System.out::println);
}
```



**方式四：创建无限流(了解)**

可以使用静态方法 Stream.iterate() 和 Stream.generate(), 创建无限流。

- 迭代
  public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) 

- 生成
  public static<T> Stream<T> generate(Supplier<T> s) 

```java
// 方式四：创建无限流
@Test
public void test05() {
	// 迭代
	// public static<T> Stream<T> iterate(final T seed, final
	// UnaryOperator<T> f)
	Stream<Integer> stream = Stream.iterate(0, x -> x + 2);
	stream.limit(10).forEach(System.out::println);

	// 生成
	// public static<T> Stream<T> generate(Supplier<T> s)
	Stream<Double> stream1 = Stream.generate(Math::random);
	stream1.limit(10).forEach(System.out::println);
}

```

#### 5.4.2 一系列中间操作

多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，否则中间操作不会执行任何的处理！而在终止操作时一次性全部处理，称为“惰性求值”。

1-筛选与切片

| **方   法**             | **描   述**                                                  |
| ----------------------- | ------------------------------------------------------------ |
| **filter(Predicatep)**  | 接收  Lambda ， 从流中排除某些元素                           |
| **distinct()**          | 筛选，通过流所生成元素的  hashCode() 和 equals() 去除重复元素 |
| **limit(long maxSize)** | 截断流，使其元素不超过给定数量                               |
| **skip(long n)**        | 跳过元素，返回一个扔掉了前  n 个元素的流。<br>若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补 |

2-映 射

| **方法**                            | **描述**                                                     |
| ----------------------------------- | ------------------------------------------------------------ |
| **map(Function f)**                 | 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。 |
| **mapToDouble(ToDoubleFunction f)** | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。 |
| **mapToInt(ToIntFunction  f)**      | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的  IntStream。 |
| **mapToLong(ToLongFunction  f)**    | 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的  LongStream。 |
| **flatMap(Function  f)**            | 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流 |

3-排序

| **方法**                       | **描述**                           |
| ------------------------------ | ---------------------------------- |
| **sorted()**                   | 产生一个新流，其中按自然顺序排序   |
| **sorted(Comparator** **com)** | 产生一个新流，其中按比较器顺序排序 |

代码举例：

```java
package com.atguigu.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamMiddleOperate {
	@Test
    public void test01(){
        //1、创建Stream
        Stream<Integer> stream = Stream.of(1,2,3,4,5,6);

        //2、加工处理
        //过滤：filter(Predicate p)
        //把里面的偶数拿出来
        /*
         * filter(Predicate p)
         * Predicate是函数式接口，抽象方法：boolean test(T t)
         */
        stream = stream.filter(t -> t%2==0);

        //3、终结操作：例如：遍历
        stream.forEach(System.out::println);
    }
    @Test
    public void test02(){
        Stream.of(1,2,3,4,5,6)
                .filter(t -> t%2==0)
                .forEach(System.out::println);
    }
    @Test
    public void test03(){
        Stream.of(1,2,3,4,5,6,2,2,3,3,4,4,5)
                .distinct()
                .forEach(System.out::println);
    }
    @Test
    public void test04(){
        Stream.of(1,2,3,4,5,6,2,2,3,3,4,4,5)
                .limit(3)
                .forEach(System.out::println);
    }
    @Test
    public void test05(){
        Stream.of(1,2,2,3,3,4,4,5,2,3,4,5,6,7)
                .distinct()  //(1,2,3,4,5,6,7)
                .filter(t -> t%2!=0) //(1,3,5,7)
                .limit(3)
                .forEach(System.out::println);
    }
    @Test
    public void test06(){
        Stream.of(1,2,3,4,5,6,2,2,3,3,4,4,5)
                .skip(5)
                .forEach(System.out::println);
    }
    @Test
    public void test07(){
        Stream.of(1,2,3,4,5,6,2,2,3,3,4,4,5)
                .skip(5)
                .distinct()
                .filter(t -> t%3==0)
                .forEach(System.out::println);
    }
    @Test
    public void test08(){
        long count = Stream.of(1,2,3,4,5,6,2,2,3,3,4,4,5)
                .distinct()
                .peek(System.out::println)  //Consumer接口的抽象方法  void accept(T t)
                .count();
        System.out.println("count="+count);
    }
    @Test
    public void test09(){
        //希望能够找出前三个最大值，前三名最大的，不重复
        Stream.of(11,2,39,4,54,6,2,22,3,3,4,54,54)
                .distinct()
                .sorted((t1,t2) -> -Integer.compare(t1, t2))//Comparator接口  int compare(T t1, T t2)
                .limit(3)
                .forEach(System.out::println);
    }
    @Test
    public void test10(){
        Stream.of(1,2,3,4,5)
                .map(t -> t+=1)//Function<T,R>接口抽象方法 R apply(T t)
                .forEach(System.out::println);
    }
    @Test
    public void test11(){
        String[] arr = {"hello","world","java"};

        Arrays.stream(arr)
                .map(t->t.toUpperCase())
                .forEach(System.out::println);
    }
    @Test
    public void test12(){
        String[] arr = {"hello","world","java"};
        Arrays.stream(arr)
                .flatMap(t -> Stream.of(t.split("|")))//Function<T,R>接口抽象方法 R apply(T t)  现在的R是一个Stream
                .forEach(System.out::println);
    } 
}

```

#### 5.4.3 终止操作

- 终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是 void 。

- 流进行了终止操作后，不能再次使用。

1-匹配与查找

| **方法**                        | **描述**                                                     |
| ------------------------------- | ------------------------------------------------------------ |
| **allMatch(Predicate  p)**      | 检查是否匹配所有元素                                         |
| **anyMatch(Predicate  p)  **    | 检查是否至少匹配一个元素                                     |
| **noneMatch(Predicate**  **p)** | 检查是否没有匹配所有元素                                     |
| **findFirst()**                 | 返回第一个元素                                               |
| **findAny()**                   | 返回当前流中的任意元素                                       |
| **count()**                     | 返回流中元素总数                                             |
| **max(Comparator c)**           | 返回流中最大值                                               |
| **min(Comparator c)**           | 返回流中最小值                                               |
| **forEach(Consumer c)**         | 内部迭代(使用  Collection  接口需要用户去做迭代，称为外部迭代。<br>相反，Stream  API 使用内部迭代——它帮你把迭代做了) |

2-归约

| **方法**                                  | **描述**                                                 |
| ----------------------------------------- | -------------------------------------------------------- |
| **reduce(T  identity, BinaryOperator b)** | 可以将流中元素反复结合起来，得到一个值。返回  T          |
| **reduce(BinaryOperator  b)**             | 可以将流中元素反复结合起来，得到一个值。返回 Optional<T> |

备注：map 和 reduce 的连接通常称为 map-reduce 模式，因 Google 用它来进行网络搜索而出名。

3-收集

| **方   法**               | **描   述**                                                  |
| ------------------------- | ------------------------------------------------------------ |
| **collect(Collector  c)** | 将流转换为其他形式。接收一个  Collector接口的实现，<br>用于给Stream中元素做汇总的方法 |

Collector 接口中方法的实现决定了如何对流执行收集的操作(如收集到 List、Set、Map)。

另外， Collectors 实用类提供了很多静态方法，可以方便地创建常见收集器实例，具体方法与实例如下表：

| **方法**   | **返回类型**             | **作用**             |
| ---------- | ------------------------ | -------------------- |
| **toList** | Collector<T, ?, List<T>> | 把流中元素收集到List |

```java
List<Employee> emps= list.stream().collect(Collectors.toList());
```

| **方法**  | **返回类型**            | **作用**            |
| --------- | ----------------------- | ------------------- |
| **toSet** | Collector<T, ?, Set<T>> | 把流中元素收集到Set |

```java
Set<Employee> emps= list.stream().collect(Collectors.toSet());
```

| **方法**         | **返回类型**       | **作用**                   |
| ---------------- | ------------------ | -------------------------- |
| **toCollection** | Collector<T, ?, C> | 把流中元素收集到创建的集合 |

```java
Collection<Employee> emps =list.stream().collect(Collectors.toCollection(ArrayList::new));
```

| **方法**     | **返回类型**          | **作用**           |
| ------------ | --------------------- | ------------------ |
| **counting** | Collector<T, ?, Long> | 计算流中元素的个数 |

```java
long count = list.stream().collect(Collectors.counting());
```

| **方法**       | **返回类型**             | **作用**                 |
| -------------- | ------------------------ | ------------------------ |
| **summingInt** | Collector<T, ?, Integer> | 对流中元素的整数属性求和 |

```java
int total=list.stream().collect(Collectors.summingInt(Employee::getSalary));
```

| **方法**         | **返回类型**            | **作用**                        |
| ---------------- | ----------------------- | ------------------------------- |
| **averagingInt** | Collector<T, ?, Double> | 计算流中元素Integer属性的平均值 |

```java
double avg = list.stream().collect(Collectors.averagingInt(Employee::getSalary));
```

| **方法**           | **返回类型**                          | **作用**                                |
| ------------------ | ------------------------------------- | --------------------------------------- |
| **summarizingInt** | Collector<T, ?, IntSummaryStatistics> | 收集流中Integer属性的统计值。如：平均值 |

```java
int SummaryStatisticsiss= list.stream().collect(Collectors.summarizingInt(Employee::getSalary));
```

| **方法**    | **返回类型**                       | **作用**           |
| ----------- | ---------------------------------- | ------------------ |
| **joining** | Collector<CharSequence, ?, String> | 连接流中每个字符串 |

```java
String str= list.stream().map(Employee::getName).collect(Collectors.joining());
```

| **方法**  | **返回类型**                 | **作用**             |
| --------- | ---------------------------- | -------------------- |
| **maxBy** | Collector<T, ?, Optional<T>> | 根据比较器选择最大值 |

```java
Optional<Emp>max= list.stream().collect(Collectors.maxBy(comparingInt(Employee::getSalary)));
```

| **方法**  | **返回类型**                 | **作用**             |
| --------- | ---------------------------- | -------------------- |
| **minBy** | Collector<T, ?, Optional<T>> | 根据比较器选择最小值 |

```java
Optional<Emp> min = list.stream().collect(Collectors.minBy(comparingInt(Employee::getSalary)));
```

| **方法**     | **返回类型**                 | **作用**                                                     |
| ------------ | ---------------------------- | ------------------------------------------------------------ |
| **reducing** | Collector<T, ?, Optional<T>> | 从一个作为累加器的初始值开始，利用BinaryOperator与流中元素逐个结合，从而归约成单个值 |

```java
int total=list.stream().collect(Collectors.reducing(0, Employee::getSalar, Integer::sum));
```

| **方法**              | **返回类型**      | **作用**                           |
| --------------------- | ----------------- | ---------------------------------- |
| **collectingAndThen** | Collector<T,A,RR> | 包裹另一个收集器，对其结果转换函数 |

```java
int how= list.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
```

| **方法**       | **返回类型**                     | **作用**                               |
| -------------- | -------------------------------- | -------------------------------------- |
| **groupingBy** | Collector<T, ?, Map<K, List<T>>> | 根据某属性值对流分组，属性为K，结果为V |

```java
Map<Emp.Status, List<Emp>> map= list.stream().collect(Collectors.groupingBy(Employee::getStatus));
```

| **方法**           | **返回类型**                           | **作用**                |
| ------------------ | -------------------------------------- | ----------------------- |
| **partitioningBy** | Collector<T, ?, Map<Boolean, List<T>>> | 根据true或false进行分区 |

```java
Map<Boolean,List<Emp>> vd = list.stream().collect(Collectors.partitioningBy(Employee::getManage));
```

举例：

```java
package com.atguigu.stream;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamEndding {
    @Test
    public void test01(){
        Stream.of(1,2,3,4,5)
                .forEach(System.out::println);
    }
    @Test
    public void test02(){
        long count = Stream.of(1,2,3,4,5)
                .count();
        System.out.println("count = " + count);
    }
    @Test
    public void test03(){
        boolean result = Stream.of(1,3,5,7,9)
                .allMatch(t -> t%2!=0);
        System.out.println(result);
    }
	@Test
    public void test04(){
        boolean result = Stream.of(1,3,5,7,9)
                .anyMatch(t -> t%2==0);
        System.out.println(result);
    }
	@Test
    public void test05(){
        Optional<Integer> opt = Stream.of(1,3,5,7,9).findFirst();
        System.out.println(opt);
    }
	@Test
    public void test06(){
        Optional<Integer> opt = Stream.of(1,2,3,4,5,7,9)
                .filter(t -> t%3==0)
                .findFirst();
        System.out.println(opt);
    }
	@Test
    public void test07(){
        Optional<Integer> opt = Stream.of(1,2,4,5,7,8)
                .filter(t -> t%3==0)
                .findFirst();
        System.out.println(opt);
    }
    @Test
    public void test08(){
        Optional<Integer> max = Stream.of(1,2,4,5,7,8)
                .max((t1,t2) -> Integer.compare(t1, t2));
        System.out.println(max);
    }
    @Test
    public void test09(){
        Integer reduce = Stream.of(1,2,4,5,7,8)
                .reduce(0, (t1,t2) -> t1+t2);//BinaryOperator接口   T apply(T t1, T t2)
        System.out.println(reduce);
    }
    @Test
    public void test10(){
        Optional<Integer> max = Stream.of(1,2,4,5,7,8)
                .reduce((t1,t2) -> t1>t2?t1:t2);//BinaryOperator接口   T apply(T t1, T t2)
        System.out.println(max);
    }
    @Test
    public void test11(){
        List<Integer> list = Stream.of(1,2,4,5,7,8)
                .filter(t -> t%2==0)
                .collect(Collectors.toList());

        System.out.println(list);
    }   
}
```

### 5.5 Java9新增API

**新增1：Stream实例化方法**

ofNullable()的使用：

Java 8 中 Stream 不能完全为null，否则会报空指针异常。而 Java 9 中的 ofNullable 方法允许我们创建一个单元素 Stream，可以包含一个非空元素，也可以创建一个空 Stream。

```java
//报NullPointerException
//Stream<Object> stream1 = Stream.of(null);
//System.out.println(stream1.count());

//不报异常，允许通过
Stream<String> stringStream = Stream.of("AA", "BB", null);
System.out.println(stringStream.count());//3

//不报异常，允许通过
List<String> list = new ArrayList<>();
list.add("AA");
list.add(null);
System.out.println(list.stream().count());//2
//ofNullable()：允许值为null
Stream<Object> stream1 = Stream.ofNullable(null);
System.out.println(stream1.count());//0

Stream<String> stream = Stream.ofNullable("hello world");
System.out.println(stream.count());//1

```

iterator()重载的使用：

```java
//原来的控制终止方式：
Stream.iterate(1,i -> i + 1).limit(10).forEach(System.out::println);

//现在的终止方式：
Stream.iterate(1,i -> i < 100,i -> i + 1).forEach(System.out::println);

```

### 5.6 练习

现在有两个 ArrayList 集合存储队伍当中的多个成员姓名，要求使用传统的for循环（或增强for循环）依次进行以
下若干操作步骤：

1. 第一个队伍只要名字为3个字的成员姓名；存储到一个新集合中。
2. 第一个队伍筛选之后只要前3个人；存储到一个新集合中。
3. 第二个队伍只要姓张的成员姓名；存储到一个新集合中。
4. 第二个队伍筛选之后不要前2个人；存储到一个新集合中。
5. 将两个队伍合并为一个队伍；存储到一个新集合中。
6. 根据姓名创建 Person 对象；存储到一个新集合中。
7. 打印整个队伍的Person对象信息。

Person 类的代码为：

```java
public class Person {
    private String name;
    public Person() {}
    public Person(String name) {
        this.name = name;
    }    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Person{name='" + name + "'}";
    }
}
```

两个队伍（集合）的代码如下：

```java
public static void main(String[] args) {
       //第一支队伍
        ArrayList<String> one = new ArrayList<>();
        one.add("迪丽热巴");
        one.add("宋远桥");
        one.add("苏星河");
        one.add("石破天");
        one.add("石中玉");
        one.add("老子");
        one.add("庄子");
        one.add("洪七公");
        //第二支队伍
        ArrayList<String> two = new ArrayList<>();
        two.add("古力娜扎");
        two.add("张无忌");
        two.add("赵丽颖");
        two.add("张三丰");
        two.add("尼古拉斯赵四");
        two.add("张天爱");
        two.add("张二狗");
    
		// ....编写代码完成题目要求 
    }
```

参考答案：

```java
public static void main(String[] args) {
       //第一支队伍
        ArrayList<String> one = new ArrayList<>();
        one.add("迪丽热巴");
        one.add("宋远桥");
        one.add("苏星河");
        one.add("石破天");
        one.add("石中玉");
        one.add("老子");
        one.add("庄子");
        one.add("洪七公");
    
        //第二支队伍
        ArrayList<String> two = new ArrayList<>();
        two.add("古力娜扎");
        two.add("张无忌");
        two.add("赵丽颖");
        two.add("张三丰");
        two.add("尼古拉斯赵四");
        two.add("张天爱");
        two.add("张二狗");
        
		// 第一个队伍只要名字为3个字的成员姓名；
        // 第一个队伍筛选之后只要前3个人；
        Stream<String> streamOne = one.stream().filter(s ‐> s.length() == 3).limit(3);
    
        // 第二个队伍只要姓张的成员姓名；
        // 第二个队伍筛选之后不要前2个人；
        Stream<String> streamTwo = two.stream().filter(s ‐> s.startsWith("张")).skip(2);
    
        // 将两个队伍合并为一个队伍；
        // 根据姓名创建Person对象；
        // 打印整个队伍的Person对象信息。
        Stream.concat(streamOne, streamTwo).map(Person::new).forEach(System.out::println);
        
}
```



***

## 6. 新语法结构

新的语法结构，为我们勾勒出了 Java 语法进化的一个趋势，将开发者从`复杂、繁琐`的低层次抽象中逐渐解放出来，以更高层次、更优雅的抽象，既`降低代码量`，又避免意外编程错误的出现，进而提高代码质量和开发效率。

### 6.1 Java的REPL工具： jShell命令

**JDK9的新特性**

Java 终于拥有了像Python 和 Scala 之类语言的REPL工具（交互式编程环境，read - evaluate - print - loop）：`jShell`。以交互式的方式对语句和表达式进行求值。`即写即得`、`快速运行`。

利用jShell在没有创建类的情况下，在命令行里直接声明变量，计算表达式，执行语句。无需跟人解释”public static void main(String[] args)”这句"废话"。

**使用举例**

- 调出jShell


 ![image-20220527205410620](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205410620.png)

- 获取帮助


 ![image-20220527205418073](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205418073.png)

- 基本使用


 ![image-20220527205429720](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205429720.png)

- 导入指定的包


 ![image-20220527205440068](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205440068.png)

- 默认已经导入如下的所有包：（包含java.lang包）


 ![image-20220527205445267](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205445267.png)

- 只需按下 Tab 键，就能自动补全代码


 ![image-20220527205451352](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205451352.png)

- 列出当前 session 里所有有效的代码片段


 ![image-20220527205458774](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205458774.png)

- 查看当前 session 下所有创建过的变量


 ![image-20220527205506632](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205506632.png)

- 查看当前 session 下所有创建过的方法


 ![image-20220527205513459](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205513459.png)

> Tips：我们还可以重新定义相同方法名和参数列表的方法，即对现有方法的修改（或覆盖）。

- 使用外部代码编辑器来编写 Java 代码


 ![image-20220527205519915](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205519915.png)

从外部文件加载源代码【HelloWorld.java】

```java
/**
 * Created by songhongkang
 */
public void printHello() {
    System.out.println("马上2023年了，尚硅谷祝所有的谷粉元旦快乐！");
}
printHello();

```

- 使用/open命令调用


 ![image-20220527205827712](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205827712.png)

- 退出jShell


 ![image-20220527205904000](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527205904000.png)

### 6.2 异常处理之try-catch资源关闭

在JDK7 之前，我们这样处理资源的关闭：

```java
@Test
public void test01() {
    FileWriter fw = null;
    BufferedWriter bw = null;
    try {
        fw = new FileWriter("d:/1.txt");
        bw = new BufferedWriter(fw);

        bw.write("hello");
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (fw != null) {
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```

**JDK7的新特性**

在try的后面可以增加一个()，在括号中可以声明流对象并初始化。try中的代码执行完毕，会自动把流对象释放，就不用写finally了。

格式：

```java
try(资源对象的声明和初始化){
    业务逻辑代码,可能会产生异常
}catch(异常类型1 e){
    处理异常代码
}catch(异常类型2 e){
    处理异常代码
}
```

说明：

1、在try()中声明的资源，无论是否发生异常，无论是否处理异常，都会自动关闭资源对象，不用手动关闭了。

2、这些资源实现类必须实现AutoCloseable或Closeable接口，实现其中的close()方法。Closeable是AutoCloseable的子接口。Java7几乎把所有的“资源类”（包括文件IO的各种类、JDBC编程的Connection、Statement等接口…）都进行了改写，改写后资源类都实现了AutoCloseable或Closeable接口，并实现了close()方法。

3、写到try()中的资源类的变量默认是final声明的，不能修改。

举例：

```java
//举例1
@Test
public void test02() {
    try (
        FileWriter fw = new FileWriter("d:/1.txt");
        BufferedWriter bw = new BufferedWriter(fw);
    ) {
        bw.write("hello");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

//举例2
@Test
public void test03() {
    //从d:/1.txt(utf-8)文件中，读取内容，写到项目根目录下1.txt(gbk)文件中
    try (
        FileInputStream fis = new FileInputStream("d:/1.txt");
        InputStreamReader isr = new InputStreamReader(fis, "utf-8");
        BufferedReader br = new BufferedReader(isr);

        FileOutputStream fos = new FileOutputStream("1.txt");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "gbk");
        BufferedWriter bw = new BufferedWriter(osw);
    ) {
        String str;
        while ((str = br.readLine()) != null) {
            bw.write(str);
            bw.newLine();
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

**JDK9的新特性**

try的前面可以定义流对象，try后面的()中可以直接引用流对象的名称。在try代码执行完毕后，流对象也可以释放掉，也不用写finally了。

格式：

```java
A a = new A();
B b = new B();
try(a;b){
    可能产生的异常代码
}catch(异常类名 变量名){
    异常处理的逻辑
}
```

举例：

```java
@Test
public void test04() {
    InputStreamReader reader = new InputStreamReader(System.in);
    OutputStreamWriter writer = new OutputStreamWriter(System.out);
    try (reader; writer) {
        //reader是final的，不可再被赋值
        //   reader = null;

    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### 6.3 局部变量类型推断

**JDK 10的新特性**

局部变量的显示类型声明，常常被认为是不必须的，给一个好听的名字反而可以很清楚的表达出下面应该怎样继续。本新特性允许开发人员省略通常不必要的局部变量类型声明，以增强Java语言的体验性、可读性。

- 使用举例


```java
//1.局部变量的实例化
var list = new ArrayList<String>();

var set = new LinkedHashSet<Integer>();

//2.增强for循环中的索引
for (var v : list) {
    System.out.println(v);
}

//3.传统for循环中
for (var i = 0; i < 100; i++) {
    System.out.println(i);
}

//4. 返回值类型含复杂泛型结构
var iterator = set.iterator();
//Iterator<Map.Entry<Integer, Student>> iterator = set.iterator();

```

- 不适用场景
  - 声明一个成员变量
  - 声明一个数组变量，并为数组静态初始化（省略new的情况下）
  - 方法的返回值类型
  - 方法的参数类型
  - 没有初始化的方法内的局部变量声明
  - 作为catch块中异常类型
  - Lambda表达式中函数式接口的类型
  - 方法引用中函数式接口的类型

代码举例：

声明一个成员变量，并初始化值为null

 ![image-20220528155628337](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155628337.png)

声明一个数组变量，并为数组静态初始化（省略new的情况下）

 ![image-20220528155436304](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155436304.png)

没有初始化的方法内的局部变量声明

 ![image-20220528155525774](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155525774.png)

方法的返回值类型

 ![image-20220528155537713](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155537713.png)

方法的参数类型

 ![image-20220528155549303](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155549303.png)

构造器的参数类型

 ![image-20220528155614708](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155614708.png)

作为catch块中异常类型

 ![image-20220528155640498](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155640498.png)

Lambda表达式中函数式接口的类型

 ![image-20220528155423180](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155423180.png)

方法引用中函数式接口的类型

 ![image-20220528155429055](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528155429055.png)

**注意：**

- var不是一个关键字，而是一个类型名，将它作为变量的类型。不能使用var作为类名。

- 这不是JavaScript。var并不会改变 Java是一门静态类型语言的事实。编译器负责推断出类型，并把结果写入字节码文件，就好像是开发人员自己敲入类型一样。

### 6.4 instanceof的模式匹配

**JDK14中预览特性：**

instanceof 模式匹配通过提供更为简便的语法，来提高生产力。有了该功能，可以减少Java程序中显式强制转换的数量，实现更精确、简洁的类型安全的代码。

Java 14之前旧写法：

```java
if(obj instanceof String){
    String str = (String)obj; //需要强转
    .. str.contains(..)..
}else{
    ...
}
```

Java 14新特性写法：

```java
if(obj instanceof String str){
    .. str.contains(..)..
}else{
    ...
}
```

举例：

```java
/**
 * instanceof的模式匹配（预览）
 *
 * @author shkstart
 * @create 上午 11:32
 */
public class Feature01 {
    @Test
    public void test1(){

        Object obj = new String("hello,Java14");
        obj = null;//在使用null 匹配instanceof 时，返回都是false.
        if(obj instanceof String){
            String str = (String) obj;
            System.out.println(str.contains("Java"));
        }else{
            System.out.println("非String类型");
        }

        //举例1：
        if(obj instanceof String str){ //新特性：省去了强制类型转换的过程
            System.out.println(str.contains("Java"));
        }else{
            System.out.println("非String类型");
        }
    }
}

// 举例2
class InstanceOf{

    String str = "abc";

    public void test(Object obj){

        if(obj instanceof String str){//此时的str的作用域仅限于if结构内。
            System.out.println(str.toUpperCase());
        }else{
            System.out.println(str.toLowerCase());
        }

    }

}

//举例3：
class Monitor{
    private String model;
    private double price;

//    public boolean equals(Object o){
//        if(o instanceof Monitor other){
//            if(model.equals(other.model) && price == other.price){
//                return true;
//            }
//        }
//        return false;
//    }


    public boolean equals(Object o){
        return o instanceof Monitor other && model.equals(other.model) && price == other.price;
    }

}
```

**JDK15中第二次预览：**

没有任何更改。

**JDK16中转正特性：**

在Java16中转正。

### 6.5 switch表达式

传统switch声明语句的弊端：

- 匹配是自上而下的，如果忘记写break，后面的case语句不论匹配与否都会执行； --->case穿透
- 所有的case语句共用一个块范围，在不同的case语句定义的变量名不能重复；
- 不能在一个case里写多个执行结果一致的条件；
- 整个switch不能作为表达式返回值；

```java
//常见错误实现
switch(month){
    case 3|4|5://3|4|5 用了位运算符，11 | 100 | 101结果是 111是7
        System.out.println("春季");
        break;
    case 6|7|8://6|7|8用了位运算符，110 | 111 | 1000结果是1111是15
        System.out.println("夏季");
        break;
    case 9|10|11://9|10|11用了位运算符，1001 | 1010 | 1011结果是1011是11
        System.out.println("秋季");
        break;
    case 12|1|2://12|1|2 用了位运算符，1100 | 1 | 10 结果是1111，是15
        System.out.println("冬季");
        break;
    default:
        System.out.println("输入有误");
}
```

**JDK12中预览特性：**

- Java 12将会对switch声明语句进行扩展，使用`case L ->`来替代以前的`break;`，省去了 break 语句，避免了因少写 break 而出错。

- 同时将多个 case 合并到一行，显得简洁、清晰，也更加优雅的表达逻辑分支。

- 为了保持兼容性，case 条件语句中依然可以使用字符` :` ，但是同一个 switch 结构里不能混用` ->` 和` :` ，否则编译错误。


举例：

Java 12之前

```java
/**
 * @author shkstart
 * @create 下午 4:47
 */
public class SwitchTest {
    public static void main(String[] args) {
        int numberOfLetters;
        Fruit fruit = Fruit.APPLE;
        switch (fruit) {
            case PEAR:
                numberOfLetters = 4;
                break;
            case APPLE:
            case GRAPE:
            case MANGO:
                numberOfLetters = 5;
                break;
            case ORANGE:
            case PAPAYA:
                numberOfLetters = 6;
                break;
            default:
                throw new IllegalStateException("No Such Fruit:" + fruit);
        }
        System.out.println(numberOfLetters);

    }
}
enum Fruit {
    PEAR, APPLE, GRAPE, MANGO, ORANGE, PAPAYA;
}
```

switch 语句如果漏写了一个 break，那么逻辑往往就跑偏了，这种方式既繁琐，又容易出错。

Java 12中：

```java
/**
 * @author shkstart
 * @create 下午 10:38
 */
public class SwitchTest1 {
    public static void main(String[] args) {
        Fruit fruit = Fruit.GRAPE;
        switch(fruit){
            case PEAR -> System.out.println(4);
            case APPLE,MANGO,GRAPE -> System.out.println(5);
            case ORANGE,PAPAYA -> System.out.println(6);
            default -> throw new IllegalStateException("No Such Fruit:" + fruit);
        };
    }
}
```

更进一步：

```java
/**
 * @author shkstart
 * @create 2019 下午 10:44
 */
public class SwitchTest2 {
    public static void main(String[] args) {
        Fruit fruit = Fruit.GRAPE;
        int numberOfLetters = switch(fruit){
            case PEAR -> 4;
            case APPLE,MANGO,GRAPE -> 5;
            case ORANGE,PAPAYA -> 6;
            default -> throw new IllegalStateException("No Such Fruit:" + fruit);
        };
        System.out.println(numberOfLetters);
    }
}
```

**JDK13中二次预览特性：**

JDK13中引入了yield语句，用于返回值。这意味着，switch表达式(返回值)应该使用yield，switch语句(不返回值)应该使用break。

yield和return的区别在于：return会直接跳出当前循环或者方法，而yield只会跳出当前switch块。

在以前：

```java
@Test
public void testSwitch1(){
    String x = "3";
    int i;
    switch (x) {
        case "1":
            i=1;
            break;
        case "2":
            i=2;
            break;
        default:
            i = x.length();
            break;
    }
    System.out.println(i);
}
```

在JDK13中：

```java
@Test
public void testSwitch2(){
    String x = "3";
    int i = switch (x) {
        case "1" -> 1;
        case "2" -> 2;
        default -> {
            yield 3;
        }
    };
    System.out.println(i);
}
```

或者

```java
@Test
public void testSwitch3() {
    String x = "3";
    int i = switch (x) {
        case "1":
            yield 1;
        case "2":
            yield 2;
        default:
            yield 3;
    };
    System.out.println(i);
}
```

**JDK14中转正特性：**

这是JDK 12和JDK 13中的预览特性，现在是正式特性了。

**JDK17的预览特性：switch的模式匹配**

旧写法：

```java
static String formatter(Object o) {
    String formatted = "unknown";
    if (o instanceof Integer i) {
        formatted = String.format("int %d", i);
    } else if (o instanceof Long l) {
        formatted = String.format("long %d", l);
    } else if (o instanceof Double d) {
        formatted = String.format("double %f", d);
    } else if (o instanceof String s) {
        formatted = String.format("String %s", s);
    }
    return formatted;
}
```

模式匹配新写法：

```java
static String formatterPatternSwitch(Object o) {
    return switch (o) {
        case Integer i -> String.format("int %d", i);
        case Long l    -> String.format("long %d", l);
        case Double d  -> String.format("double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> o.toString();
    };
}
```

直接在 switch 上支持 Object 类型，这就等于同时支持多种类型，使用模式匹配得到具体类型，大大简化了语法量，这个功能很实用。

### 6.6 文本块

现实问题：

在Java中，通常需要使用String类型表达HTML，XML，SQL或JSON等格式的字符串，在进行字符串赋值时需要进行转义和连接操作，然后才能编译该代码，这种表达方式难以阅读并且难以维护。

**JDK13的新特性**

使用"""作为文本块的开始符和结束符，在其中就可以放置多行的字符串，不需要进行任何转义。因此，文本块将提高Java程序的可读性和可写性。

基本使用：

```java
"""
line1
line2
line3
"""
```

相当于：

```java
"line1\nline2\nline3\n"
```

或者一个连接的字符串：

```java
"line1\n" +
"line2\n" +
"line3\n"
```

如果字符串末尾不需要行终止符，则结束分隔符可以放在最后一行内容上。例如：

```java
"""
line1
line2
line3"""
```

相当于

```java
"line1\nline2\nline3"
```

文本块可以表示空字符串，但不建议这样做，因为它需要两行源代码：

```java
String empty = """
""";
```

举例1：普通文本

原有写法：

```java
 String text1 = "The Sound of silence\n" +
                "Hello darkness, my old friend\n" +
                "I've come to talk with you again\n" +
                "Because a vision softly creeping\n" +
                "Left its seeds while I was sleeping\n" +
                "And the vision that was planted in my brain\n" +
                "Still remains\n" +
                "Within the sound of silence";

System.out.println(text1);
```

使用新特性：

```java
String text2 = """
                The Sound of silence
                Hello darkness, my old friend
                I've come to talk with you again
                Because a vision softly creeping
                Left its seeds while I was sleeping
                And the vision that was planted in my brain
                Still remains
                Within the sound of silence
                """;
System.out.println(text2);
```

举例2：HTML语句

```html
<html>
  <body>
      <p>Hello, 尚硅谷</p>
  </body>
</html>
```

将其复制到Java的字符串中，会展示成以下内容：

```java
"<html>\n" +
"    <body>\n" +
"        <p>Hello, 尚硅谷</p>\n" +
"    </body>\n" +
"</html>\n";
```

即被自动进行了转义，这样的字符串看起来不是很直观，在JDK 13中：

```java
"""
<html>
  <body>
      <p>Hello, world</p>
  </body>
</html>
""";
```

举例3：SQL语句

```sql
select employee_id,last_name,salary,department_id
from employees
where department_id in (40,50,60)
order by department_id asc
```

原有方式：

```java
String sql = "SELECT id,NAME,email\n" +
                "FROM customers\n" +
                "WHERE id > 4\n" +
                "ORDER BY email DESC";
```

使用新特性：

```java
String sql1 = """
                SELECT id,NAME,email
                FROM customers
                WHERE id > 4
                ORDER BY email DESC
                """;
```

举例4：JSON字符串

原有方式：

```java
String myJson = "{\n" +
                "    \"name\":\"Song Hongkang\",\n" +
                "     \"address\":\"www.atguigu.com\",\n" +
                "    \"email\":\"shkstart@126.com\"\n" +
                "}";
System.out.println(myJson);
```

使用新特性：

```java
String myJson1 = """
                {
                    "name":"Song Hongkang",
                     "address":"www.atguigu.com",
                    "email":"shkstart@126.com"
                }""";
System.out.println(myJson1);
```

**JDK14中二次预览特性**

JDK14的版本主要增加了两个escape sequences，分别是` \ <line-terminator>`与`\s  escape sequence`。

举例：

```java
/**
 * @author shkstart
 * @create 下午 7:13
 */
public class Feature05 {
    //jdk14新特性
    @Test
    public void test5(){
        String sql1 = """
                SELECT id,NAME,email
                FROM customers
                WHERE id > 4
                ORDER BY email DESC
                """;
        System.out.println(sql1);

        // \:取消换行操作
        // \s:表示一个空格
        String sql2 = """
                SELECT id,NAME,email \
                FROM customers\s\
                WHERE id > 4 \
                ORDER BY email DESC
                """;
        System.out.println(sql2);
    }
}
```

**JDK15中功能转正**

### 6.7 Record

`背景`

早在2019年2月份，Java 语言架构师 Brian Goetz，曾写文抱怨“`Java太啰嗦`”或有太多的“繁文缛节”。他提到：开发人员想要创建纯数据载体类（plain data carriers）通常都必须编写大量低价值、重复的、容易出错的代码。如：构造函数、getter/setter、equals()、hashCode()以及toString()等。

以至于很多人选择使用IDE的功能来自动生成这些代码。还有一些开发会选择使用一些第三方类库，如Lombok等来生成这些方法。

**JDK14中预览特性：神说要用record，于是就有了。**实现一个简单的数据载体类，为了避免编写：构造函数，访问器，equals()，hashCode () ，toString ()等，Java 14推出record。

`record` 是一种全新的类型，它本质上是一个 `final` 类，同时所有的属性都是 `final` 修饰，它会自动编译出 `public get` 、`hashcode` 、`equals`、`toString`、构造器等结构，减少了代码编写量。

具体来说：当你用`record` 声明一个类时，该类将自动拥有以下功能：

- 获取成员变量的简单方法，比如例题中的 name() 和 partner() 。注意区别于我们平常getter()的写法。
- 一个 equals 方法的实现，执行比较时会比较该类的所有成员属性。
- 重写 hashCode() 方法。
- 一个可以打印该类所有成员属性的 toString() 方法。
- 只有一个构造方法。

此外：

- 还可以在record声明的类中定义静态字段、静态方法、构造器或实例方法。

- 不能在record声明的类中定义实例字段；类不能声明为abstract；不能声明显式的父类等。


举例1（旧写法）：

```java
class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x() {
        return x;
    }

    int y() {
        return y;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        Point other = (Point) o;
        return other.x == x && other.y == y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
```

举例1（新写法）：

```java
record Point(int x, int y) { }
```

举例1：

```java
public record Dog(String name, Integer age) {
}
```

```java
public class Java14Record {

    public static void main(String[] args) {
        Dog dog1 = new Dog("牧羊犬", 1);
        Dog dog2 = new Dog("田园犬", 2);
        Dog dog3 = new Dog("哈士奇", 3);
        System.out.println(dog1);
        System.out.println(dog2);
        System.out.println(dog3);
    }
}
```

举例2：

```java
/**
 *  Record类型的演示
 *
 * @author shkstart
 * @create 下午 6:13
 */
public class Feature07 {
    @Test
    public void test1(){
        //测试构造器
        Person p1 = new Person("罗密欧",new Person("zhuliye",null));
        //测试toString()
        System.out.println(p1);
        //测试equals():
        Person p2 = new Person("罗密欧",new Person("zhuliye",null));
        System.out.println(p1.equals(p2));

        //测试hashCode()和equals()
        HashSet<Person> set = new HashSet<>();
        set.add(p1);
        set.add(p2);

        for (Person person : set) {
            System.out.println(person);
        }

        //测试name()和partner():类似于getName()和getPartner()
        System.out.println(p1.name());
        System.out.println(p1.partner());

    }

    @Test
    public void test2(){
        Person p1 = new Person("zhuyingtai");

        System.out.println(p1.getNameInUpperCase());

        Person.nation = "CHN";
        System.out.println(Person.showNation());

    }
}
```

```java
/**
 * @author shkstart
 * @create 下午 6:20
 */
public record Person(String name,Person partner) {

    //还可以声明静态的属性、静态的方法、构造器、实例方法

    public static String nation;

    public static String showNation(){
        return nation;
    }

    public Person(String name){
        this(name,null);
    }

    public String getNameInUpperCase(){
        return name.toUpperCase();
    }
    //不可以声明非静态的属性
//    private int id;//报错
}

//不可以将record定义的类声明为abstract的
//abstract record Order(){
//
//}

//不可以给record定义的类声明显式的父类（非Record类）
//record Order() extends Thread{
//
//}
```

**JDK15中第二次预览特性**

**JDK16中转正特性**

最终到JDK16中转正。

`记录不适合哪些场景`

record的设计目标是提供一种将数据建模为数据的好方法。它也不是 JavaBeans 的直接替代品，因为record的方法不符合 JavaBeans 的 get 标准。另外 JavaBeans 通常是可变的，而记录是不可变的。尽管它们的用途有点像，但记录并不会以某种方式取代 JavaBean。

### 6.8 密封类

背景：

在 Java 中如果想让一个类不能被继承和修改，这时我们应该使用 `final` 关键字对类进行修饰。不过这种要么可以继承，要么不能继承的机制不够灵活，有些时候我们可能想让某个类可以被某些类型继承，但是又不能随意继承，是做不到的。Java 15 尝试解决这个问题，引入了 `sealed` 类，被 `sealed` 修饰的类可以指定子类。这样这个类就只能被指定的类继承。

**JDK15的预览特性：**

通过密封的类和接口来限制超类的使用，密封的类和接口限制其它可能继承或实现它们的其它类或接口。

具体使用：

- 使用修饰符`sealed`，可以将一个类声明为密封类。密封的类使用保留关键字`permits`列出可以直接扩展（即extends）它的类。


-  `sealed` 修饰的类的机制具有传递性，它的子类必须使用指定的关键字进行修饰，且只能是 `final`、`sealed`、`non-sealed` 三者之一。


举例：

```java
package com.atguigu.java;
public abstract sealed class Shape permits Circle, Rectangle, Square {...}

public final class Circle extends Shape {...} //final表示Circle不能再被继承了

public sealed class Rectangle extends Shape permits TransparentRectangle, FilledRectangle {...}

public final class TransparentRectangle extends Rectangle {...}

public final class FilledRectangle extends Rectangle {...}

public non-sealed class Square extends Shape {...} //non-sealed表示可以允许任何类继承
```

**JDK16二次预览特性**

**JDK17中转正特性**

## 7. API的变化

### 7.1 Optional类

**JDK8的新特性**

到目前为止，臭名昭著的空指针异常是导致Java应用程序失败的最常见原因。以前，为了解决空指针异常，Google在著名的Guava项目引入了Optional类，通过检查空值的方式避免空指针异常。受到Google的启发，Optional类已经成为Java 8类库的一部分。

`Optional<T>` 类(java.util.Optional) 是一个容器类，它可以保存类型T的值，代表这个值存在。或者仅仅保存null，表示这个值不存在。如果值存在，则isPresent()方法会返回true，调用get()方法会返回该对象。

Optional提供很多有用的方法，这样我们就不用显式进行空值检测。

- `创建Optional类对象的方法：`
- static <T> Optional<T> empty() ：用来创建一个空的Optional实例
  - static <T> Optional<T> of(T value) ：用来创建一个Optional实例，value必须非空
  - `static <T> Optional<T> ofNullable(T value)` ：用来创建一个Optional实例，value可能是空，也可能非空

- `判断Optional容器中是否包含对象：`

  - boolean isPresent() : 判断Optional容器中的值是否存在
  - void ifPresent(Consumer<? super T> consumer) ：判断Optional容器中的值是否存在，如果存在，就对它进行Consumer指定的操作，如果不存在就不做

- `获取Optional容器的对象：`
- T get(): 如果调用对象包含值，返回该值。否则抛异常。T get()与of(T value)配合使用

- `T orElse(T other) `：orElse(T other) 与ofNullable(T value)配合使用，如果Optional容器中非空，就返回所包装值，如果为空，就用orElse(T other)other指定的默认值（备胎）代替

- T orElseGet(Supplier<? extends T> other) ：如果Optional容器中非空，就返回所包装值，如果为空，就用Supplier接口的Lambda表达式提供的值代替

- T orElseThrow(Supplier<? extends X> exceptionSupplier) ：如果Optional容器中非空，就返回所包装值，如果为空，就抛出你指定的异常类型代替原来的NoSuchElementException

举例：

```java
package com.atguigu.optional;

import java.util.Optional;

import org.junit.Test;

public class TestOptional {
	@Test
    public void test1(){
        String str = "hello";
        Optional<String> opt = Optional.of(str);
        System.out.println(opt);
    }
    @Test
    public void test2(){
        Optional<String> opt = Optional.empty();
        System.out.println(opt);
    }
    @Test
    public void test3(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        System.out.println(opt);
    }
    @Test
    public void test4(){
        String str = "hello";
        Optional<String> opt = Optional.of(str);

        String string = opt.get();
        System.out.println(string);
    }
    @Test
    public void test5(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
//		System.out.println(opt.get());//java.util.NoSuchElementException: No value present
    }
    @Test
    public void test6(){
        String str = "hello";
        Optional<String> opt = Optional.ofNullable(str);
        String string = opt.orElse("atguigu");
        System.out.println(string);
    }
    @Test
    public void test7(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        String string = opt.orElseGet(String::new);
        System.out.println(string);
    }
    @Test
    public void test8(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        String string = opt.orElseThrow(()->new RuntimeException("值不存在"));
        System.out.println(string);
    }
    @Test
    public void test9(){
        String str = "Hello1";
        Optional<String> opt = Optional.ofNullable(str);
        //判断是否是纯字母单词，如果是，转为大写，否则保持不变
        String result = opt.filter(s->s.matches("[a-zA-Z]+"))
                .map(s -> s.toUpperCase()).orElse(str);
        System.out.println(result);
    }
}

```

**这是JDK9-11的新特性**

| **新增方法**                                                 | **描述**                                                     | **新增的版本** |
| ------------------------------------------------------------ | ------------------------------------------------------------ | -------------- |
| boolean isEmpty()                                            | 判断value是否为空                                            | JDK  11        |
| ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) | value非空，执行参数1功能；如果value为空，执行参数2功能       | JDK  9         |
| Optional<T> or(Supplier<? extends Optional<? extends T>> supplier) | value非空，返回对应的Optional；value为空，返回形参封装的Optional | JDK  9         |
| Stream<T> stream()                                           | value非空，返回仅包含此value的Stream；否则，返回一个空的Stream | JDK  9         |
| T orElseThrow()                                              | value非空，返回value；否则抛异常NoSuchElementException       | JDK  10        |

### 7.2 String存储结构和API变更

**这是JDK9的新特性。**

产生背景：

> Motivation
> The current implementation of the String class stores characters in a char array, using two bytes (sixteen bits) for each character. Data gathered from many different applications indicates that strings are a major component of heap usage and, moreover, that most String objects contain only Latin-1 characters. Such characters require only one byte of storage, hence half of the space in the internal char arrays of such String objects is going unused.

使用说明：

> ## Description
>
> We propose to change the internal representation of the String class from a UTF-16 char array to a byte array plus an encoding-flag field. The new String class will store characters encoded either as ISO-8859-1/Latin-1 (one byte per character), or as UTF-16 (two bytes per character), based upon the contents of the string. The encoding flag will indicate which encoding is used.

结论：String 再也不用 char[] 来存储啦，改成了 byte[] 加上编码标记，节约了一些空间。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    @Stable
    private final byte[] value;
	...
}
```

拓展：StringBuffer 与 StringBuilder

那StringBuffer 和 StringBuilder 是否仍无动于衷呢？

> String-related classes such as AbstractStringBuilder, StringBuilder, and StringBuffer will be updated to use the same representation, as will the HotSpot VM's intrinsic string operations.

**JDK11新特性：新增了一系列字符串处理方法**

| **描述**             | **举例**                                        |
| -------------------- | ----------------------------------------------- |
| 判断字符串是否为空白 | "  ".isBlank(); // true                         |
| 去除首尾空白         | "  Javastack ".strip(); // "Javastack"          |
| 去除尾部空格         | "  Javastack ".stripTrailing(); // " Javastack" |
| 去除首部空格         | "  Javastack ".stripLeading(); // "Javastack "  |
| 复制字符串           | "Java".repeat(3);//  "JavaJavaJava"             |
| 行数统计             | "A\nB\nC".lines().count();  // 3                |

**JDK12新特性：String 实现了 Constable 接口**

String源码：

```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence,Constable, ConstantDesc {
```

java.lang.constant.Constable接口定义了抽象方法：

```java
public interface Constable {
	Optional<? extends ConstantDesc> describeConstable();
}
```

Java 12 String 的实现源码：

```java
/**
 * Returns an {@link Optional} containing the nominal descriptor for this
 * instance, which is the instance itself.
 *
 * @return an {@link Optional} describing the {@linkplain String} instance
 * @since 12
 */
@Override
public Optional<String> describeConstable() {
	return Optional.of(this);
}
```

很简单，其实就是调用 Optional.of 方法返回一个 Optional 类型。

举例：

```java
private static void testDescribeConstable() {
	String name = "尚硅谷Java高级工程师";
	Optional<String> optional = name.describeConstable();
	System.out.println(optional.get());
}
```

结果输出：

```
尚硅谷Java高级工程师
```

**JDK12新特性：String新增方法**

String的transform(Function)

```java
var result = "foo".transform(input -> input + " bar");
System.out.println(result); //foo bar
```

或者

```java
var result = "foo".transform(input -> input + " bar").transform(String::toUpperCase)
System.out.println(result); //FOO BAR
```

对应的源码：

```java
/**
* This method allows the application of a function to {@code this}
* string. The function should expect a single String argument
* and produce an {@code R} result.
* @since 12
*/
public <R> R transform(Function<? super String, ? extends R> f) {
 return f.apply(this);
}
```

在某种情况下，该方法应该被称为map()。

举例：

```java
private static void testTransform() {
	System.out.println("======test java 12 transform======");
	List<String> list1 = List.of("Java", " Python", " C++ ");
	List<String> list2 = new ArrayList<>();
	list1.forEach(element -> list2.add(element.transform(String::strip)
								  .transform(String::toUpperCase)
								  .transform((e) -> "Hi," + e))
				 );
	list2.forEach(System.out::println);
}
```

结果输出：

```java
======test java 12 transform======
Hi,JAVA
Hi,PYTHON
Hi,C++
```

如果使用Java 8的Stream特性，可以如下实现：

```java
private static void testTransform1() {
        System.out.println("======test before java 12 ======");
        List<String> list1 = List.of("Java  ", " Python", " C++ ");

        Stream<String> stringStream = list1.stream().map(element -> element.strip()).map(String::toUpperCase).map(element -> "Hello," + element);
        List<String> list2 = stringStream.collect(Collectors.toList());
        list2.forEach(System.out::println);
    }
```

### 7.3 JDK17：标记删除Applet API

Applet API 提供了一种将 Java AWT/Swing 控件嵌入到浏览器网页中的方法。不过，目前 Applet 已经被淘汰。大部分人可能压根就没有用过 Applet。

Applet API 实际上是无用的，因为所有 Web 浏览器供应商都已删除或透露计划放弃对 Java 浏览器插件的支持。Java 9 的时候，Applet API 已经被标记为过时，Java 17 的时候终于标记为删除了。

具体如下：

```java
java.applet.Applet
java.applet.AppletStub
java.applet.AppletContext
java.applet.AudioClip
javax.swing.JApplet
java.beans.AppletInitializer
```

## 8.  其它结构变化

### 8.1 JDK9：UnderScore(下划线)使用的限制

在java 8 中，标识符可以独立使用“_”来命名：

```java
String _ = "hello";
System.out.println(_);
```

但是，在java 9 中规定“_”不再可以单独命名标识符了，如果使用，会报错：

![image-20220527233322495](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220527233322495.png)



### 8.2 JDK11：更简化的编译运行程序

看下面的代码。

```java
// 编译
javac JavaStack.java

// 运行
java JavaStack
```

我们的认知里，要运行一个 Java 源代码必须先编译，再运行。而在 Java 11 版本中，通过一个 java 命令就直接搞定了，如下所示：

```
java JavaStack.java
```

注意点：

- 执行源文件中的第一个类，第一个类必须包含主方法。


### 8.3 GC方面新特性

GC是Java主要优势之一。 然而，当GC停顿太长，就会开始影响应用的响应时间。随着现代系统中内存不断增长，用户和程序员希望JVM能够以高效的方式充分利用这些内存， 并且无需长时间的GC暂停时间。

#### 8.3.1 G1 GC

JDK9以后默认的垃圾回收器是G1GC。

**JDK10 : 为G1提供并行的Full GC**

G1最大的亮点就是可以尽量的避免full gc。但毕竟是“尽量”，在有些情况下，G1就要进行full gc了，比如如果它无法足够快的回收内存的时候，它就会强制停止所有的应用线程然后清理。

在Java10之前，一个单线程版的标记-清除-压缩算法被用于full gc。为了尽量减少full gc带来的影响，在Java10中，就把之前的那个单线程版的标记-清除-压缩的full gc算法改成了支持多个线程同时full gc。这样也算是减少了full gc所带来的停顿，从而提高性能。

你可以通过`-XX:ParallelGCThreads`参数来指定用于并行GC的线程数。

**JDK12：可中断的 G1 Mixed GC**

**JDK12：增强G1，自动返回未用堆内存给操作系统**

#### 8.3.2 Shenandoah GC

**JDK12：Shenandoah GC：低停顿时间的GC**

![image-20220528212254705](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528212254705.png)

Shenandoah 垃圾回收器是 Red Hat 在 2014 年宣布进行的一项垃圾收集器研究项目 Pauseless GC 的实现，旨在**针对 JVM 上的内存收回实现低停顿的需求**。

据 Red Hat 研发 Shenandoah 团队对外宣称，Shenandoah 垃圾回收器的暂停时间与堆大小无关，这意味着无论将堆设置为 200 MB 还是 200 GB，都将拥有一致的系统暂停时间，不过实际使用性能将取决于实际工作堆的大小和工作负载。

Shenandoah GC 主要目标是 99.9% 的暂停小于 10ms，暂停与堆大小无关等。

这是一个实验性功能，不包含在默认（Oracle）的OpenJDK版本中。

Shenandoah开发团队在实际应用中的测试数据：

![image-20220528212323729](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528212323729.png)

**JDK15：Shenandoah垃圾回收算法转正**

Shenandoah垃圾回收算法终于从实验特性转变为产品特性，这是一个从 JDK 12 引入的回收算法，该算法通过与正在运行的 Java 线程同时进行疏散工作来减少 GC 暂停时间。Shenandoah 的暂停时间与堆大小无关，无论堆栈是 200 MB 还是 200 GB，都具有相同的一致暂停时间。

Shenandoah在JDK12被作为experimental引入，在JDK15变为Production；之前需要通过`-XX:+UnlockExperimentalVMOptions` `-XX:+UseShenandoahGC`来启用，现在只需要`-XX:+UseShenandoahGC`即可启用

#### 8.3.3 革命性的 ZGC

**JDK11：引入革命性的 ZGC**

ZGC，这应该是JDK11最为瞩目的特性，没有之一。 

ZGC是一个并发、基于region、压缩型的垃圾收集器。

ZGC的设计目标是：支持TB级内存容量，暂停时间低（<10ms），对整个程序吞吐量的影响小于15%。 将来还可以扩展实现机制，以支持不少令人兴奋的功能，例如多层堆（即热对象置于DRAM和冷对象置于NVMe闪存），或压缩堆。

**JDK13：ZGC:将未使用的堆内存归还给操作系统**

**JDK14：ZGC on macOS和windows**

- JDK14之前，ZGC仅Linux才支持。现在mac或Windows上也能使用ZGC了，示例如下：

  ```
  -XX:+UnlockExperimentalVMOptions -XX:+UseZGC
  ```

- ZGC与Shenandoah目标高度相似，在尽可能对吞吐量影响不大的前提下，实现在任意堆内存大小下都可以把垃圾收集的停顿时间限制在`十毫秒以内`的低延迟。


![image-20220528212414676](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528212414676.png)

![image-20220528212421319](https://imagebed-xuhuaiang.oss-cn-shanghai.aliyuncs.com/typora/image-20220528212421319.png)

**JDK15：ZGC 功能转正**

ZGC是Java 11引入的新的垃圾收集器，经过了多个实验阶段，自此终于成为正式特性。

但是这并不是替换默认的GC，默认的GC仍然还是G1；之前需要通过`-XX:+UnlockExperimentalVMOptions`、` -XX:+UseZGC`来启用ZGC，现在只需要`-XX:+UseZGC`就可以。相信不久的将来它必将成为默认的垃圾回收器。

> ZGC的性能已经相当亮眼，用“令人震惊、革命性”来形容，不为过。未来将成为服务端、大内存、低延迟应用的首选垃圾收集器。

怎么形容Shenandoah和ZGC的关系呢？异同点大概如下：

- 相同点：性能几乎可认为是相同的
- 不同点：ZGC是Oracle JDK的，根正苗红。而Shenandoah只存在于OpenJDK中，因此使用时需注意你的JDK版本

**JDK16：ZGC 并发线程处理**

在线程的堆栈处理过程中，总有一个制约因素就是safepoints。在safepoints这个点，Java的线程是要暂停执行的，从而限制了GC的效率。

> 回顾：
>
> 我们都知道，在之前，需要 GC 的时候，为了进行垃圾回收，需要所有的线程都暂停下来，这个暂停的时间我们称为 **Stop The World**。
>
> 而为了实现 STW 这个操作， JVM 需要为每个线程选择一个点停止运行，这个点就叫做**安全点（Safepoints）**。

而ZGC的并发线程堆栈处理可以保证Java线程可以在GC safepoints的同时可以并发执行。它有助于提高所开发的Java软件应用程序的性能和效率。

## 9. 小结与展望

随着云计算和 AI 等技术浪潮，当前的计算模式和场景正在发生翻天覆地的变化，不仅对 Java 的发展速度提出了更高要求，也深刻影响着 Java 技术的发展方向。**传统的大型企业或互联网应用，正在被云端、容器化应用、模块化的微服务甚至是函数(FaaS， Function-as-a-Service)所替代。**

**Java 需要在新的计算场景下，改进开发效率。**比如，Java 代码虽然进行了一些类型推断等改进，更易用的集合 API 等，但仍然给开发者留下了过于刻板、形式主义的印象，这是一个长期的改进方向。

Java虽然标榜面向对象编程，却毫不顾忌的加入`面向接口编程思想`，又扯出`匿名对象`的概念，每增加一个新的东西，对Java的根本（面向对象思想）的一次冲击。

> 士，不可不弘毅，任重而道远。

## 10.随堂复习

### 1. JDK新特性的概述

- 几个重要的版本
  - jdk 5.0 / jdk 8.0 ：里程碑式的版本
  - jdk9.0 开始每6个月发布一个新的版本
  - LTS : jdk8 、 jdk 11 、 jdk 17

- 如何学习新特性

```
> 角度1：新的语法规则 （多关注）
  自动装箱、自动拆箱、注解、enum、Lambda表达式、方法引用、switch表达式、try-catch变化、record等

> 角度2：增加、过时、删除API
  StringBuilder、ArrayList、新的日期时间的API、Optional等

> 角度3：底层的优化、JVM参数的调整、GC的变化、内存结构（永久代--->元空间）
```

### 2. JDK8:lambda表达式

#### 2.1 什么情况下可以使用lambda表达式

- 在给函数式接口提供实例时，都可以考虑使用lambda表达式。
- 基本语法的使用（重要）

#### 2.2 函数式接口

- 常见的函数式接口。Comparator \ Runnable \ java.util.function下定义的丰富的函数式接口

  ```
  消费型接口：Consumer<T>     void accept(T t)
  供给型接口：Supplier<T>     T get()
  函数型接口：Function<T,R>   R apply(T t)
  判断型接口：Predicate<T>    boolean test(T t)
  ```

#### 2.3 方法引用、构造器引用、数组引用

- 方法引用、构造器引用、数组引用：看做是lambda表达式的进一步刻画、表达。

- （掌握）方法引用、构造器引用、数组引用的使用场景。

### 3. JDK8:Stream API的使用

- Stream关注于内存中的多个数据的运算。
- 使用步骤：① Stream 的实例化 ② 一系列的中间操作 ③ 终止操作

### 4. JDK8之后的新特性：语法层面

- jShell工具
- try-catch结构的变化。try(...){ }
- 局部变量的类型推断：var
- instanceof的模式匹配
- switch表达式、switch的模式匹配
- 文本块的使用："""文本块"""
- 新的引用数据类型：record (记录)
- 密封类：sealed class

### 5. JDK8之后的新特性：其它

- Optional类的使用
- 其他：了解



## 11.企业真题

### 2.1 JDK8新特性

#### 1. 谈谈java8新特性(京*旗下、时代\*宇，信必\*、招\*信诺，中\*外包，金\*软件、阿\*\*巴)

```
类似问题
> JDK1.8相较于JDK1.7有什么不一样？（惠*）
> JDK1.8的新特性有哪些？Stream API + Lambda表达式，还有吗？（久*国际物流）
```

- lambda表达式、Stream API
- jdk7的对比：元空间、HashMap、新的日期时间API、接口变化等。

#### 2. JDK1.8在数据结构上发生了哪些变化 ？（银*数据）

- 使用元空间替代永久代。  （方法区：jvm规范中提到的结构。  
  - HotSpot来讲，jdk7：方法区的落地体现：永久代。 jdk8：方法区的落地体现：元空间。
- HashMap底层结构

#### 3. 你说的了解 Java的新特性 ，你说说JDK8改进的地方？（银*科技）

略

#### 4. JDK1.8用的是哪个垃圾回收器？（O**O）

Parallel GC --> jdk9：默认使用G1GC  --> ZGC （低延迟）

### 2.2 Lambda表达式

#### 1. Lambda表达式有了解吗，说说如何使用的（O**O）

```
类似问题：
> Lambda的理解（国*）
```

略

#### 2. 什么是函数式接口？有几种函数式接口（阿**巴）

略。

java.util.function包下定义了丰富的好函数式接口。有4类基础的函数式接口：

消费型接口：Consumer<T>     void accept(T t)
供给型接口：Supplier<T>     T get()
函数型接口：Function<T,R>   R apply(T t)
判断型接口：Predicate<T>    boolean test(T t)

### 2.3 Stream API

#### 1. 创建Stream的方式（阿**巴）

三种。

#### 2. 你讲讲stream表达式是咋用的，干啥的？（中*国际，上海**网络）

```
> Stream API 关注的是多个数据的计算（排序、查找、过滤、映射、遍历等），面向CPU的。
  集合关注的数据的存储，面向内存的。
> Stream API 之于集合，类似于SQL之于数据表的查询。
```

#### 3. 集合用Stream流怎么实现过滤？（润*软件）

filter(Predicate predicate)

#### 4. 用Stream怎么选出List里想要的数据？（惠*）

略



### 2.4 其它版本新特性

#### 1. 说说JDK15、JDK16、JDK17中的新特性都有什么？(银*数据)

略
