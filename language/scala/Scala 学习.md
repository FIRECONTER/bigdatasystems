# Scala 学习

## val 与var
var 定义的是可变的变量，声明之后的值可以任意改变，而val 定义的是不可变更的变量。声明同时定义，不可改变其值。
## 运行程序
相比于Java. Scala构建的程序并未要求文件名与类名一致。编译过程类似于javac编译器语法。执行的时候是scala 类名。

## 基本类型
可以这样理解scala 中的基本类型都是Java中的基本类型的包装类。scala中没有Java中的基本类型的概念(string,double,float)。所以操作的基本类型都是Double,String以及Float,Boolean等。
加减乘除取余操作与java略微不同。
调用方式多了一种(numberA).操作(numberB)同时还有一个地方不同,scala中没有++与--的自加自减的操作。
## Range
构建一个Range序列。语法是A to(until) B by C其中A 与 B是起始点与终点 C是步长。
```Scala
for(i <- 1 to 5 by 2) println(i);
```
## 打印
简单的print println 以及结构化字符串输出printf("%s %d",A,B)
```scala
var i =2;
var b = "sd";
printf("this is Int value %d and String value %s\n",i,b);
```
## I/O
I/O 本质上是Java 的IO那一套。比如PrintWriter。但是在REPL环境下可以执行的代码在脚本中却无法直接执行。
## 逻辑表达式if
与Java不同的地方在于scala中if表达式的值可以传递给scala变量
```Scala
var x = 1;
var k = if(x>0) 1 else -1;//k 赋值为1 
```
而且判断条件必须为Boolean 没JS 灵活

## while 与do while和其他语法一致
```scala
while(i>0){
println(i);
i-=1;
}
```
## for 循环
```Scala
for(变量 <- 表达式) 语句块
```
其中的表达式比较灵活，致使scala的for语法使用比较灵活
变量<- 表达式称为生成器。可以往生成器添加内容。
```scala
for(i<- 1 to 5 if i%2!=0) println(i);//打印奇数。
```
多个生成器输出
```scala
for(i<-1 to 5;j<-2 to 4 by 2) println(i*j);
```
## Array
array 用法与java不同。使用[]指定类型。访问的时候使用()
```scala
var k = new Array[Int](3);
k(0) = 12；
k(1) = 13;
k(2) = 14;
```



