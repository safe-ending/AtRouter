

#### 框架错误集合 ####

### 一.UnknownServiceException: CLEARTEXT communication to gank.io not permitted by network security policy ###

有以下三种解决方案： 

1、APP改用https请求 

2、targetSdkVersion 降到27以下 

3、在application节点下添加cleartextTrafficPermitted="true" ，内容如下，大概意思就是允许开启http请求

### 二.ARouter::There is no route match the path [/xxx/xxx], in group [xxx][ ]" ###

这种情况是没有找到目标页面，

1、检查目标页面的注解是否配置正确，正确的注解形式应该是 (@Route(path="/test/test"), 如没有特殊需求，请勿指定group字段，废弃功能)

2、检查目标页面所在的模块的gradle脚本中是否依赖了 arouter-compiler sdk (需要注意的是，要使用apt依赖，而不是compile关键字依赖)

3、检查编译打包日志，是否出现了形如 ARouter:: Compiler >>> xxxxx 的日志，日志中会打印出发现的路由目标

4、启动App的时候，开启debug、log(openDebug/openLog), 查看映射表是否已经被扫描出来，形如 D/ARouter::: LogisticsCenter has already been loaded, GroupIndex[4]，GroupIndex > 0

### 三.新增页面之后，无法跳转？ ###

ARouter加载Dex中的映射文件会有一定耗时，所以ARouter会缓存映射文件，直到新版本升级(版本号或者versionCode变化)，而如果是开发版本(ARouter.openDebug())， ARouter 每次启动都会重新加载映射文件，开发阶段一定要打开 Debug 功能。

### 四、不影响程序运行的日志 ###

### .NoSuchMethodError: No static method getFont(Landroid/content/Context;ILandroid/util/TypedValue;ILandroid/widget/TextView;)Landroid/graphics/Typeface ###

编译版本和build下的版本号一定要一致,用的sdk27 就好了
compileSdkVersion 27 
buildToolsVersion ‘27.0.3’ 
minSdkVersion 16 
targetSdkVersion 27

implementation ‘com.android.support:design:27.1.1’ 
implementation ‘com.android.support:support-v4:27.1.1’ 
implementation ‘com.android.support:cardview-v7:27.1.1’ 
implementation ‘com.android.support:gridlayout-v7:27.1.1’ 
implementation ‘com.android.support:recyclerview-v7:27.1.1’

### 五、组件化项目的混淆方案 ###
组件化项目的Java代码混淆方案采用在集成模式下集中在app壳工程中混淆，各个业务组件不配置混淆文件。集成开发模式下在app壳工程中build.gradle文件的release构建类型中开启混淆属性，其他buildTypes配置方案跟普通项目保持一致，Java混淆配置文件也放置在app壳工程中，各个业务组件的混淆配置规则都应该在app壳工程中的混淆配置文件中添加和修改。

之所以不采用在每个业务组件中开启混淆的方案，是因为 组件在集成模式下都被 Gradle 构建成了 release 类型的arr包，一旦业务组件的代码被混淆，而这时候代码中又出现了bug，将很难根据日志找出导致bug的原因；另外每个业务组件中都保留一份混淆配置文件非常不便于修改和管理，这也是不推荐在业务组件的 build.gradle 文件中配置 buildTypes （构建类型）的原因。

