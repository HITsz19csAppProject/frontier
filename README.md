# 多人合作代码指南
## 1. 代码规范篇
### 1.1 为变量/函数命名时："驼峰命名法(Carmel_Case)"
#### 1.1.0 驼峰命名法的操作
对于某一个变量/函数名，以变量`mynamelist`和函数`mynamelist()` 为例。

按照驼峰命名法，需要将变量命名为`myNameList`，将函数名命名为`MyNameList()`（JAVA规范）
#### 1.1.1 驼峰命名法的概述

驼峰式命名法（Camel-Case）是电脑程式编写时的一套命名规则（惯例）。

驼峰式命名法就是当变量名或函数名时由一个或多个单字连结在一起，而构成的唯一识别字时，**第一个单词**以**小写字母**开始；**第二个单词**的**首字母大写**或**每一个单词的首字母都采用大写字母**。

##### 小驼峰法
变量一般用小驼峰法标识。即除第一个单词外，其他单词的首字母大写。例如：`int myNameList = 0;`

在这个例子中，变量myNameList第一个单词是全部小写，后面的单词首字母大写。

##### 大驼峰法
对于类名(class)，函数名(function)，命名空间(namespace), 常使用大驼峰法。即把第一个单词的的首字母也大写。例如：`public class MyNameList;`

#### 1.1.2 tips:
在JAVA中，**类名的标识符**一般用**大驼峰式**书写格式，**方法和变量的标识符**则多用**小驼峰式**书写格式。

##### For example:
```java
public class MyNameList{
    private int myName;
    private int myStudentId;

    public int checkStuName(int myStudentId);
    public boolean absent(int myName);
}
```

### 1.2 编写代码时:注释的重要性
#### 1.2.1 注释的意义
在写代码时写注释的意义大概有以下三点：
1. 好的代码规范可以提高团队的开发效率，从而节省时间。
2. 正确的应用注释规范可以增加代码的可读性、理解性。
3. 尝试写注释可以使代码逻辑更加严谨，同时使日后阅读代码更加容易。

#### 1.2.2 注释的规范
一、单行注释: `//注释内容`
一般在代码后空4-8行，可以使用tab键来实现空格。(一般是1次tab键)

二、块状注释： `/*注释内容*/`
rt，将注释内容用 /* 和 */ 夹住。

三、文档注释： `/**.注.释.内.容.*/`
文档注释写在整个文档的最前面，由两部分组成：描述+块标记
tips：强烈建议每个java文档都写上文档注释。

#### 1.2.3 javadoc注释标签
`@author`
对类的说明，标明开发该类模块的作者

`@version`
对类的说明，标明该类模块的版本

`@see`
对类、属性、方法的说明。参考转向，也就是相关主题

`@param`
对方法的说明，对方法中某参数的说明

`@return`
对方法的说明，对方法返回值的说明

`@exception`
对方法的说明，对方法可能抛出的异常进行说明

## 2. Github && git 使用篇

### 2.1 个人使用Github（略）

### 2.2 多人合作使用Github

#### 2.2.1 建立分支
分支的意义：防止污染主程序，若某一个项目成员提交错误代码且未及时清理，可能导致项目的崩溃。
```
创建分支 git branch<分支名>
切换分支 git checkout 分支名
推送到远程 git push origin 分支名
```
tips: 目前市面上的主流IDE具有更高级的UI界面帮助我们完成git操作。但是此处不介绍(**但是推荐使用IDE，毕竟方便**)。
#### 2.2.2 关联分支
我们在本地克隆下来的项目中，默认是只能看见主分支master而看不到远程的其他分支。
如果我们要在本地进行多人分支开发（必定的），一般要在本地创建和远程分支对应的分支并做好**关联**，关联分支和远程分支最好相同。
```
创建对应分支 git checkout -b 分支名 origin/分支名
建立关联 git branch -u origin/分支名
或者使用 git branch --set-upstream-to origin/分支名

解除关联 git branch --unset-upstream-to origin/分支名
```
#### 2.2.3 一般操作步骤
1. 从网上仓库中克隆项目，在本地建立对应的分支
2. 将本地分支与远程分支关联
3. 在建好的本地分支中修改、更新代码（非master分支）
4. 在每次完成任务之后，向远程仓库中提交代码
```
git checkout 分支
git status
git add .
git commit -m ""
git push origin 分支
```
5. 若代码没问题，需要进行分支合并
6. 将本地分支合并到本地主分支上（即master分支）
7. 将本地主分支推送到远程主分支
```
git checkout master
git merge 分支
git pull //拉取最新代码，防止冲突
git push origin master
```