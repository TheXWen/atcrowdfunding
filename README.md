# 众筹网后台管理系统
### 系统的简单介绍：
##### 使用了SSM + Bootstrap写的一个众筹网后台管理系统，主要实现的功能有权限管理的用户维护、角色维护和许可维护，业务审核的实名认证审核，业务管理的分类管理、流程管理和广告管理。
##### 角色维护和许可维护的树状图使用了jQuery的zTree插件实现，实现了对角色权限的增删改查和许可树的增删改查。实名认证审核可以查询用户实名认证审核的信息，对其进行审核。分类管理实现了控制不同用户类型实名认证所需要提供的信息。流程管理实现了流程的增删查功能，上传的流程文件通过流程框架Activiti5启动（如邮箱验证码流程）。广告管理通过jQuery的jquery-form插件实现图片上传和保存功能。
##### 使用者分用户和管理两种，超级管理员给不同的管理员不同的权限，根据登入管理员的不同所展示的菜单列表有所变化。用户登录以后需要实名认证，实名认证流程通过流程框架Activiti5实现。
### 效果图：
#### 众筹网首页
![众筹网首页](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/index.JPG)
#### 登录页面
![登录页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/login.JPG)
#### 超级管理员主页面
![超级管理员主页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/main.JPG)
#### 用户主页面
![用户主页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/member.JPG)
#### 用户维护页面
![用户维护页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/user_index.JPG)
#### 用户维护添加页面
![用户维护添加页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/user_toAdd.JPG)
#### 用户维护修改页面
![用户维护修改页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/user_toUpdate.JPG)
#### 角色维护页面
![角色维护页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/role_index.JPG)
#### 角色权限分配页面
![角色权限分配页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/role_assignPermission.JPG)
#### 许可维护页面
![许可维护页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/permission_index.JPG)
#### 实名认证审核页面
![实名认证审核页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/authcert_index.JPG)
#### 实名认证审核查看页面
![实名认证审核查看页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/authcert_show.JPG)
#### 分类管理页面
![分类管理页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/certtype_index.JPG)
#### 流程管理页面
![流程管理页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/process_index.JPG)
#### 广告管理页面
![广告管理页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/advert_index.JPG)
#### 用户实名认证页面
![用户实名认证页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/member_apply.JPG)
![用户实名认证页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/member_basicinfo.JPG)
![用户实名认证页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/member_checkauchcode.JPG)
![用户实名认证页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/member_checkemail.JPG)
![用户实名认证页面](https://github.com/TheXWen/atcrowdfunding/blob/master/EffectMap/member_uploadCert.JPG)
