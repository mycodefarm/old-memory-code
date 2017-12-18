# GoF 23种设计模式的java实现

## 1.Iterator 模式
遍历集合
## 2.Adapter 模式
用于连接接口不同的类

1. 使用继承方式
2. 使用委托方式

## 3.Template Method 模式
定义父类作为流程的模板类，子类对流程具体实现

## 4.Factory 模式
创建子类的方式交给工厂,有两个例子
1. 不带编号的IDCard
2. 带编号的IDCard

## 5.Singleton 模式
采用的饿汉式

## 6.Prototype 模式
不使用类名创建对象，使用clone方式

## 7.Builder 模式
builder定义了生成实例的方法，
具体的builder负责建造，
director作为监工，用于控制builder


## 10.Strategy 模式
通过整体替换算法来改变类的功能

例子
* 游戏AI水平的动态切换
* 游戏人物使用武器的替换

## 12.Decorator模式
装饰者模式，不改变被装饰者的接口来增加类的功能
关键：装饰者和被装饰者具有一致性（可看做继承同一父类或实现同一接口）

例子
* java.io包下的IO流
* 咖啡的各种搭配，冰淇淋的各种搭配
* swing包下的border包

## [13.Proxy模式](https://github.com/jimolonely/java-project/tree/master/design-pattern/src/org/jimo/proxy)
只是最容易理解的模式