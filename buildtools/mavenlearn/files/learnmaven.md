[toc]
## 系统的学习maven
自动化构建Java程序的工具，比较强大，现在很多Java项目都是基于maven构建的，所以有必要学习。而且，太多的hadoop项目也是采用maven添加依赖的。使用maven的另外一个原因是在java项目里面使用到的jar包越来越多，并且使用不同的框架引入的jar包
本身也可能存在冲突，以往需要开发人员对这些jar包进行人为的排查，解决冲突。其次为了项目编译运行没有问题，导致jar包过多的导入，最终导致项目中的依赖的无用的jar包太多，最终打包比较大。为了解决这些痛点，开发人员使用maven来构建java项目。

## Maven的基本安装与配置
解压缩，设置环境变量，设置PATH更改setting.xml里面的本地仓库的路径(默认.m2里面)。将这个默认的c盘本地仓库位置到自己设定的其他路径。

## 代理配置
针对公司使用防火墙，使用代理服务器让所有电脑不能直接连接internet。此时下载相关的依赖是无法连接到中央仓库的，所以需要在maven的配置文件中添加
proxy的这个代理配置项，实现从代理服务器上下载相关的依赖到达本地。

## 本地仓库
maven中存储所有项目依赖的本地文件夹，依赖内容需要从网上下载到本地。本地java项目的依赖直接和这个本地仓库挂钩，本地仓库与远程或者中央仓库同步。
## 中央仓库
maven构建项目时，首先更据项目的pom文件确定项目需要哪些依赖，然后先从本地库中获取，如果本地库需要更新或者没有相关的依赖再从远程中央仓库下载依赖到本地库，最后成功构建项目。其实就是一层一层查询是否有相关依赖过程，没有查到往高级查询，最后没查到相关的依赖包进行错误打印。

## 添加其他远程仓库
中央仓库并不是齐全的，有些依赖来自于其他仓库，所以构建项目的时候需要添加其他仓库，将其他远程仓库信息写在repository属性中。提供远程仓库的位置
```pom
<repositories>
    <repository>
      <id>java.net</id>
      <url>https://maven.java.net/content/repositories/public/</url>
    </repository>
 </repositories>
```
## Maven依赖机制
自动下载所需的依赖并保证依赖的版本更新。(根据pom中的依赖信息以及版本)。
pom中需要使用一个dependency的xml片段指明依赖库的maven坐标，即是groupId,artifactId 和version。如果没有version字段，那么maven会下载最新的版本放在本地仓库。当用maven构建或者编译这个项目时，根据这个xml片段maven会下载或者更新log4j
```
<dependencies>
    <dependency>
    	<groupId>log4j</groupId>
    	<artifactId>log4j</artifactId>
    	<version>1.2.14</version>
    </dependency>
</dependencies>
```
## 往本地maven仓库添加内容
有时候自己bulid的一些工具类可以封装成jar包(这些jar包一般不在中央仓库里面)在不同的项目中使用，规范起见可以将这些jar包放在maven本地仓库里面，让其他的项目能够使用这个依赖包。
首先创建一个jar包。
Testtool-1.0.jar 位置在F盘根目录
使用指令mvn install 将指定的jar包添加到本地仓库中。需要注意的是在添加过程中需要指定maven坐标，即是groupId,artifactId以及version，并且在pom文件中需要正确的声明，这样构建这个项目的时候这个jar包才能够被检索。
整个install指令如下:
```
mvn install:install file -Dfile=F:\Testtool-1.0.jar -DgroupId=aa.bb.cc -DartifactId=Testtool -Dversion=1.0 -Dpackaging=jar
```
此时可以在maven的本地仓库中看到这个jar包了
最后在其他项目pom.xml添加声明构建该项目即可
```
<dependencies>
    <dependency>
    	<groupId>log4j</groupId>
    	<artifactId>log4j</artifactId>
    	<version>1.2.14</version>
    </dependency>
</dependencies>
```

## 使用模板构建一个java项目
```maven
mvn archetype:generate -DgroupId={project-packaging} -DartifactId={project-name} -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
指定目录下执行上面的代码，可以利用模板创建一个简单的java项目。groupid填写包名 artifactid 为项目名称。
第一次构建因为需要将依赖下载到本地，所以比较慢。
这样一个简单的java项目通过maven构建出来了。/src/main/java/存放源文件 /src/test/java 存放的是测试文件
而且项目根目录中有一个pom.xml的文件，那是使用如上的maven指令生成的用于管理整个项目的配置文件。代表项目一切信息。如果需要添加其他的依赖那么在这个pom文件里面声明即可。
如果要转换为eclispe项目。那么在项目根目录下执行mvn eclipse:eclipse即可完全转换为eclipse项目。
如果要去eclipse使用mvn eclipse:clean

这个时候整个项目还是比较原始的，所有的依赖信息包含在pom.xml。更新或者添加依赖包，操作这个pom.xml文件，比如要更新junit版本
生成的junit为3.8.1版本比较低，现在改为4.11
```pom
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.11</version>
	<scope>test</scope>
</dependency>
```
同时制定java编译的jdk为1.8版本
```pom
<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-compiler-plugin</artifactId>
		<version>2.3.2</version>
		<configuration>
			<source>1.8</source>
			<target>1.8</target>
		</configuration>
	</plugin>
```
完整的更新的pom.xml文件如下
```pom
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.alex</groupId>
  <artifactId>number</artifactId>
  <packaging>jar</packaging>
  <version>1.0-SNAPSHOT</version>
  <name>number</name>
  <url>http://maven.apache.org</url>
  <dependencies>
    <dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.11</version>
	<scope>test</scope>
	</dependency>
  </dependencies>
  <build>
    <plugins>
    	<plugin>
    		<groupId>org.apache.maven.plugins</groupId>
    		<artifactId>maven-compiler-plugin</artifactId>
    		<version>2.3.2</version>
    		<configuration>
    			<source>1.8</source>
    			<target>1.8</target>
    		</configuration>
    	</plugin>
    </plugins>
  </build>
</project>

```
然后再pom所在文件夹下执行mvn install即可完成依赖的更新

## Maven坐标的概念
maven坐标指的是pom.xml中的代码片段，指定maven构建项目依赖或者插件的位置。格式正确才能够正常下载更新依赖或者插件。坐标一般包含groupId
groupdId 其实就是包名。约定下载的内容存储在本地库中的位置。artifactId 其实就是插件的名称在groupdId定义的文件夹下面。

## maven项目与eclipse项目互相转换
`mvn eclipse:eclipse`转换成eclipse项目 `mvn eclipse:clean`还原成maven项目

## 编写测试文件以及编写Main函数文件
使用测试驱动开发的方式，首先构建主函数类，然后构建测试类。

## 项目打包mvn package
因为在pom.xml文件中已经定义了项目的输出为jar包
```
<packaging>jar</packaging>
<version>1.0-SNAPSHOT</version>
```
所以使用mvn package即可完成打包。如果项目没有编译生成.class文件，使用mvn package也可以完成编译然后打包生成jar

## 总结常用的maven指令
mvn compile:只编译源代码
mvn test-compile:只是编译测试代码
mvn test:运行测试
mvn site:生成site
mvn package:生成打包文件，之前的生命周期，validate compile test 走完，然后打包packing 生成jar包或者war包。
mvn install: 安装或者更新jar到本地仓库,之前的生命周期都将要走完。
mvn clean:清除项目(输出target文件以及其中的内容)
mvn eclipse:eclipse 生成eclipse项目 mvn eclipse:clean 还原成标准maven项目
mvn idea:idea 同上生成idea项目 mvn idea:clean 还原成标准maven项目
mvn help:help 查看帮助信息
mvn -Dmaven.test.skip=true XXX 跳过测试运行XXX任务。比如在mvn package时默认是要对源代码，测试代码都进行编译并且运行测试后才进行打包。这里可以跳过测试打包，如果测试代码本身就没编译，那么生成的target里面也不会有测试代码编译的类

## maven 构建web项目
上面直到构建一般的java项目的时候使用了`maven-archetype-quickstart`模板，构建web项目的时候使用`maven-archetype-webapp`这个模板完成创建
`mvn archetype:generate -DgroupId={project-package} -DartifactId={projectname} -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false`

使用maven构建的这样一个web项目还是过于简单的，需要转换为eclipse项目。
`mvn eclipse:eclipse -Dwtpversion=2.0`
如果没有后面一个参数，那么将会转换成普通的Java 项目，而使用了后面的参数那么将会把它转换为一个Java web项目。

## maven项目指定外部的依赖
在某一些应用场景，需要一些外部的依赖jar包(不在各种远程仓库中)。可以使用之前的将该依赖库Install到本地仓库中，但是也可以将该依赖作为外部依赖添加到pom文件里面，完成项目的build。比如在原来项目的src目录下定义一个lib文件夹
文件夹下面存放的是外部依赖的jar包(Out.jar)，现在要把jar包运用到项目中，那么pom文件应该这样写这个依赖的dependency
```
<dependency>
  <groupId>Out</groupId>
  <artifactId>Out</artifactId>
  <version>1.0</version>
  <scope>system</scope>
  <systemPath>${basedir}/src/lib/Out.jar</systemPath>
</dependency>
```
这样这个外部的依赖包可以在程序里面正常的访问。
## mvn site 创建应用程序的项目文档
使用mvn site指令完成应用程序项目的文档创建，此时在target文件夹下会出现一个site文件夹,包含项目的详细的描述。

## maven 快照 snapshot
maven 中的库有两种类型，一种是snapshort快照库和另外一种正式发布版本的release库。关于maven 中的快照snapshot 一般用于如下的场景。开发过程中会遇到依赖一些并不稳定的库，这些库会不定期的被更新。当我们自己开发项目所需的库更新了那么我们会在自己的本地使用 `mvn install` 对依赖进行更新，但是maven对于库版本的控制是由版本号决定的，对于稳定版本即是在服务端中对当前稳定版本进行了更新(版本号不变)本地maven不会自动的下载依赖库到本地仓库。此时就需要使用snapshort了。
在pom.xml依赖的库的version标签中加入`version-SNAPSHORT(大写)`表示依赖的库为快照版，此时同一个版本的快照只要在远程仓库里面有更新，在本地使用更新的时候就会下载最新的依赖仓库。这样可以一直保持本地库与远程库的快照一致。如下的一个例子，假设开发团队依赖于数据团队提供的一个库文件，而数据团队提供的这个库文件提交频繁，比如每天都要进行bug的修复并且提交代码到远程仓库，此时可以按照如下的方式构建pom.xml文件
UI团队构建如下:
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
   http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>app-ui</groupId>
   <artifactId>app-ui</artifactId>
   <version>1.0</version>
   <packaging>jar</packaging>
   <name>health</name>
   <url>http://maven.apache.org</url>
   <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
   </properties>
   <dependencies>
      <dependency>
      <groupId>data-service</groupId>
         <artifactId>data-service</artifactId>
         <version>1.0-SNAPSHOT</version>
         <scope>test</scope>
      </dependency>
   </dependencies>
</project>
```
数据团队构建项目的pom.xml文件如下:
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
   http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>data-service</groupId>
   <artifactId>data-service</artifactId>
   <version>1.0-SNAPSHOT</version>
   <packaging>jar</packaging>
   <name>health</name>
   <url>http://maven.apache.org</url>
   <properties>
      <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
   </properties>
   </project>
```
只要UI团队使用这个1.0快照作为依赖，数据团队将这个1.0快照任何修改发布到远程仓库，UI团队本地的更新都可以获取到最新的快照库。但是如果没有`-SNAPSHORT`这个标签，无论数据团队做了什么更改，UI团队的本地依赖库不会更新。
## Intelij Idea中使用maven小结
本段详解在开发过程中使用Intelij Idea 使用maven。关于在Idea 中配置maven路径以及添加maven依赖路径将不再赘述。关于Idea中使用maven项目有两种做法。第一可以采用`mvn idea:idea`将标准的maven项目转换为idea可以识别的项目，然后将idea仅仅作为代码编译器使用，其他的测试，运行和打包使用命令行下的`mvn`相关指令完成。
还有一种使用方式就是不进行idea项目结构转换，在idea里面直接进行mvn项目的开发。这是最支持的一种方式。因为idea中已经对maven有集成，并且提供了更多的功能帮助进行纯maven项目的开发。
使用idea构建maven项目在new 项目的时候可以选择maven本身具有的模板进行构建。下一步会输入groupId 以及ArtifactId。 这两个值会被写入到pom.xml文件里面作为库的坐标。而且ArtifactId默认作为整个project的名字(在idea里面project 相当于eclipse的workspace而module相当于eclipse里面的project).
当创建好了第一个module后，需要向其中加入项目依赖，Idea里面添加项目依赖有如下的标准方式:
在module的pom.xml文件里面右键选择generate 就可以看到有dependency的选项，里面是本地maven仓库中具有的文件，选择需要的内容，add后再reimport 即可真正在项目里面成功添加依赖。
当需要一些额外的中央仓库的jar包，还是需要开发者先去重要仓库查询需要库文件的maven坐标,手动添加到pom.xml文件里面，然后使用maven download source 可以下载到本地库中。
查询需要的依赖的maven坐标的网站是:http://mvnrepository.com/ 查询相关坐标，贴入pom.xml 直接reimport maven 会完成依赖添加，下载依赖包到本地。
或者是当了解需要添加的依赖包的artifactId以及groupId 时可以直接在pom.xml里面写dependency idea会根据输入提示相关的依赖的信息，这样也可以不用去查询依赖库的maven坐标。
执行maven的各个生命周期：当需要添加的依赖全部写入项目的pom.xml文件的时候，就可以进行代码开发，之后运行整个maven的生命周期，构建项目
构建项目使用Idea 的view-tool window 里面有maven project 的window里面提供了快捷的maven周期相关的操作，帮助快速进行项目构建。
## Pom 文件结构详解
pom.xml是maven项目的核心，它反映了整个项目的信息，包含项目的结构，项目的依赖，项目的插件以及项目如何进行build等信息。

## maven项目的完整生命周期
我们知道maven操作项目有一个完整的生命周期：clean,validate,compile,test,package,install,site,deploy. 默认的情况下如果从中间的某一个周期运行其相关的指令，之前的生命周期中需要运行的过程默认都要运行，
但是可以选择跳过某些过程，比如test的过程可以选择性的跳过`mvn Dmaven.test.skip=true package` 可以在打包时跳过test的过程。
