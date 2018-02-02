[Git 教程](http://www.runoob.com/git/git-tutorial.html)

## Git 配置

Git 提供了一个叫做 git config 的工具，专门用来配置或读取相应的工作环境变量。

* /etc/gitconfig 文件：系统中对所有用户都普遍适用的配置。若使用 git config 时用 --system 选项，读写的就是这个文件。

* ~/.gitconfig 文件：用户目录下的配置文件只适用于该用户。若使用 git config 时用 --global 选项，读写的就是这个文件。

* 当前项目的 Git 目录中的配置文件（也就是工作目录中的 .git/config 文件）：这里的配置仅仅针对当前项目有效。每一个级别的配置都会覆盖上层的相同配置，所以 .git/config 里的配置会覆盖 /etc/gitconfig 中的同名变量。

#### 配置个人的用户名称和电子邮件地址：

~~~
$ git config --global user.name "runoob"
$ git config --global user.email test@runoob.com
~~~

#### 文本编辑器

~~~
$ git config --global core.editor emacs
~~~

#### 差异分析工具

~~~
$ git config --global merge.tool vimdiff
~~~

#### 查看配置信息

~~~
$ git config --list
http.postbuffer=2M
user.name=runoob
user.email=test@runoob.com

$ git config user.name
runoob
~~~

## Git 工作区、暂存区和版本库

* 作区：就是你在电脑里能看到的目录。
* 暂存区：英文叫stage, 或index。一般存放在 ".git目录下" 下的index文件（.git/index）中，所以我们把暂存区有时也叫作索引（index）。
* 版本库：工作区有一个隐藏目录.git，这个不算工作区，而是Git的版本库。

![](http://www.runoob.com/wp-content/uploads/2015/02/1352126739_7909.jpg)

图中左侧为工作区，右侧为版本库。在版本库中标记为 "index" 的区域是暂存区（stage, index），标记为 "master" 的是 master 分支所代表的目录树。

图中我们可以看出此时 "HEAD" 实际是指向 master 分支的一个"游标"。所以图示的命令中出现 HEAD 的地方可以用 master 来替换。

图中的 objects 标识的区域为 Git 的对象库，实际位于 ".git/objects" 目录下，里面包含了创建的各种对象及内容。

当对工作区修改（或新增）的文件执行 "git add" 命令时，暂存区的目录树被更新，同时工作区修改（或新增）的文件内容被写入到对象库中的一个新的对象中，而该对象的ID被记录在暂存区的文件索引中。

当执行提交操作（git commit）时，暂存区的目录树写到版本库（对象库）中，master 分支会做相应的更新。即 master 指向的目录树就是提交时暂存区的目录树。

当执行 "git reset HEAD" 命令时，暂存区的目录树会被重写，被 master 分支指向的目录树所替换，但是工作区不受影响。

当执行 "git rm --cached <file>" 命令时，会直接从暂存区删除文件，工作区则不做出改变。

当执行 "git checkout ." 或者 "git checkout -- <file>" 命令时，会用暂存区全部或指定的文件替换工作区的文件。这个操作很危险，会清除工作区中未添加到暂存区的改动。

当执行 "git checkout HEAD ." 或者 "git checkout HEAD <file>" 命令时，会用 HEAD 指向的 master 分支中的全部或者部分文件替换暂存区和以及工作区中的文件。这个命令也是极具危险性的，因为不但会清除工作区中未提交的改动，也会清除暂存区中未提交的改动。



git clone默认会把远程仓库整个给clone下来，但只会在本地默认创建一个master分支。要想单独clone某一分支方法如下：
方法一：
```
git clone -b 分支名字 URL

```
方法二：
git branch -a查看所有分支:
```
* master
remotes/origin/HEAD -> origin/master
remotes/origin/dev
```
假设我们现在想取dev分支到本地并自动建立tracking有两种方式:
git checkout -b dev origin/dev

-b dev表示在本地新建一个叫dev的分支，与远程的origin/dev对应。
git checkout -t origin/magicvoid
-t参数默认会在本地建立一个和远程分支一样名字的分支。

![error: RPC failed; curl 18 transfer closed with outstanding read data remaining](http://blog.csdn.net/drift_axe/article/details/54924359)

```
git config http.postBuffer 1524288000
```