### Mblog Java博客系统, 支持多用户, 支持切换主题
[![Author](https://img.shields.io/badge/author-landy-green.svg?style=flat-square)](http://mtons.com)
[![JDK](https://img.shields.io/badge/jdk-1.8-green.svg?style=flat-square)](#)
[![Release](https://img.shields.io/github/release/langhsu/mblog.svg?style=flat-square)](https://github.com/langhsu/mblog)
[![license](https://img.shields.io/badge/license-GPL--3.0-green.svg)](https://github.com/langhsu/mblog/blob/master/LICENSE)
[![Docker](https://img.shields.io/docker/automated/langhsu/mblog.svg?style=flat-square)](https://hub.docker.com/r/langhsu/mblog)
[![QQ群](https://img.shields.io/badge/chat-Mtons-green.svg)](https://jq.qq.com/?_wv=1027&k=521CRdF)

### 技术选型：
* JDK8
* MySQL
* Spring-boot
* Spring-data-jpa
* Shiro
* Lombok
* Freemarker
* Bootstrap
* SeaJs


# Future
* [栏目管理]新增栏目编号字段, 仅新增生成, 不可被修改, 作为栏目关联的唯一标示
* 邀请码
* 允许文章个性化定制评论
* 图片资源管理
* 任务肃清
* 默认头像管理
  + 头像默认样式 为  <a class="avt fl"..><img class="img-circle"></a>
  + 头像默认值StorageConsts.AVATAR https://static.codealy.com/images/default/default-none.jpeg，
  未实现配置化
  + 头像管理

### 版本(3.6-GA)更新内容：
* 允许登录按钮隐藏, 配置为 site.controls.login_show
* site.controls 配置优化为动态生效, 不需要项目重新启动
* yml 配置的进一步拆分, 区分dev和prod环境
* [栏目管理] 新增栏目对外释义列, 用于栏目的链接展示和查询
* [个人信息] 新增自定义个性化域名
* 允许匿名评论，评论前让用户选择是否登录
* classic 主题，增加时钟
* 是否允许匿名评论功能开发. site.controls.comment.allow.anonymous
* 为每一个用户增加固有盐值


### 版本(3.5)更新内容：
    1. 文件存储目录可配置, 见 site.location, 默认为 user.dir
    2. 支持在${site.location}/storage/templates 目录下扩展自己的主题(${site.location}具体位置见启动日志)
    3. 后台未配置对应第三方登录信息时, 前端不显示对应的按钮
    4. 模板优化
    5. 后台配置主题改为自动从目录中加载
    6. 新增markdown编辑器, 可在后台选择tinymce/markdown
    
### 版本(3.0)更新内容：
    1. 新增开关控制(注册开关, 发文开关, 评论开发)
    2. 后台重写, 替换了所有后台页面功能更完善
    3. 上传图片添加更多支持(本地/又拍云/阿里云/七牛云), 详情见后台系统配置
    4. 升级为spring-boot2
    5. 调整模板静态资源引用,方便扩展
    6. 表名调整, 旧版本升级时请自行在数据库重命名, 详情见change.log
    7. 重写了config(改为options), 可在applicaiton.yaml设置默认配置, 启动后将以options中配置为准
    8. 支持后台设置主题
    9. 去除了本地文件上传目录配置, 改为自动取项目运行目录(会在jar同级目录生成storeage和indexes目录)
    10. 替换表单验证插件, 评论表情改为颜文字
    11. 我的主页和Ta人主页合并
    12. 优化了图片裁剪功能
    13. 支持Docker, 详情见 https://hub.docker.com/r/langhsu/mblog
    14. 邮件服务后台可配
    15. 新增标签页
    16. 新增注册邮箱验证开关(需要手动删除之前的 mto_security_code 表)

### 这些用户在使用mblog(如需要在此展示您的博客请联系作者)：
[https://www.lyp82nlf.com/](https://www.lyp82nlf.com/)

[http://www.outshine.cn/](http://www.outshine.cn/)

[http://www.jiangxindc.com](http://www.jiangxindc.com)

[http://www.mhtclub.com/](http://www.mhtclub.com/)

[http://www.maocaoying.com/](http://www.maocaoying.com/)

