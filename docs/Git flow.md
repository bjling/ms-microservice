## Git flow 工作流指南

![git flow1](images/git-model@2x.png)

#### 分支
1. master
2. hotfix
3. release
4. develop
5. feature

#### 开发流程
1. 项目初始化为master分支
2. 从master分支迁出develop分支作为开发主干（可设置为default，防止被删除）
3. 产品提出需求后，产品列表->冲刺规划->冲刺列表，
   此时大家根据需求创建各自feature-user
4. 每天晚上大家从develop pull到本地feature-user，
    解决冲突后push到remote feature-user，通过控制台merge到develop分支
    可以compare此次提交的内容是否正确

~~~
    notice：每次提交的代码尽量保证项目是可运行的
~~~
    

#### 测试流程
1. 开发完成一次迭代开发，测试人员对此次任务进行测试，测试分支为release
2. 出现bug后通知相关的开发人员，在release分支进行bugfix
3. 修复bug后需要将release merge到develop分支
4. 测试人员如果仍发现bug，则重复2，3步骤
5. 测试通过后，灰度环境使用release分支进行灰度验证

#### 发布流程

1. 测试人员在灰度完成测试后，准备发布到生产环境
2. 将release分支merge到master分支，并且打上标签tag
3. 如果发布失败，则使用上个版本的tag，进行回滚
4. 上线完毕后，测试人员进行生产测试
5. 如果出现线上问题，则进行紧急hotfix，从release版本迁出hotfix-**，修复bug后，
   将hotfix分支merge到develop和master分支，并删除此hotfix分支

~~~
    notice：上线时间建议在每周四，merge工作由各个项目负责人和scrum master进行
~~~
    

#### reference

[A successful Git branching model](http://nvie.com/posts/a-successful-git-branching-model/)

[Git工作流指南：Gitflow工作流](http://blog.jobbole.com/76867/)