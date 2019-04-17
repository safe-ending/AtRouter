# Arouter组件路由的应用 #

## 框架初解 ##




**实现原理：**路由控制跳转，用到阿里开源的Arouter框架，从外部URL映射到内部页面，以及参数传递与解析跨模块页面跳转，模块间解耦，拦截跳转过程，处理登陆、埋点等逻辑，跨模块API调用，通过控制反转来做组件解耦

本框架实现基于Rxjava2+Okhttp3.9.0+Retrofit2.3.0+DataBinding的 MVVM模式
 
**项目组成介绍：**

基础模块用lib_开头命名

组件模块用module_组件名命名


项目通过version.grade统一控制依赖版本，在project的build.gradle中加入引用

	apply from: 'versions.gradle'
    addRepos(repositories)

**模块介绍**

项目划分为基础模块和业务模块，业务模块代码功能相互独立。



**基础模块：**

**lib_opensource**->项目的依赖包，其中只管理项目的依赖，将在`version.gradle`中配置好版本的第三方依赖在`lib_opensource/build.gradle`中进行引入，所有的业务模块都需要依赖于它。


**lib_common**->封装好的各种Base基类（Application、Activity、Fragment、Model...）以及常规工具类（xxxUtils等）和项目组建公用的一些weight（自定义View、自定义组建等）、自定义监听器、自定义回调方法、数据库（sharepreference、database...）

**lib_coremodel**->网络请求模块，此中包含网络所需的部分工具类和请求主类。不同模块的请求实体类需要放在com.at.arouter.coremodel.http.entities下面，通过继承AndroidViewModel的com.at.arouter.coremodel.viewmodel.AndroidViewModel来处理网络请求结果，将请求的数据绑定在Activity/Fragment的生命周期上。


**业务模块**

**module_girls**->测试模块，放了几张图片提神。

**module_third**->工单模块，目前引入的界面是钱包的设计图样式，接口用的交易所接口。



在工程根目录下的gradle.properties文件中加入一个Boolean类型的变量，通过修改这个变量来识别编译模式：

>  每次更改“isModule”的值后，需要点击 "Sync Project" 按钮isModule是“集成开发模式”和“组件开发模式”的切换开关
> isModule=false

然后在 module_girls和module_news中的build.gradle文件中支持切换：

	if (isModule.toBoolean()) {
    //组件化编译时为application
    apply plugin: 'com.android.application'
	} else {
    //非组件化编译时为library
    apply plugin: 'com.android.library'
	}
	…
	…
	..
    sourceSets {
        main {
            if (isModule.toBoolean()) {
                //组件化编译时为app，在对应的AndroidManifest文件中需要写ndroid.intent.action.MAIN入口Activity
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                //集成开发模式下排除debug文件夹中的所有Java文件
                java {
                    //debug文件夹中放的是Application类，非组件化时不用有此类
                    exclude 'debug/**'
                }
            }
        }
    }
	}

	dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    api project(':lib_coremodel')
    api project(':lib_common')
    implementation 'com.android.support:support-v4:26.1.0'
    annotationProcessor deps.arouter.compiler
}

 

各个module或lib下需要添加混淆

	-keep public class com.alibaba.android.arouter.routes.**{*;}
	-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

	 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
	-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

	 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
	-keep class * implements com.alibaba.android.arouter.facade.template.IProvider

避免release下的打包后项目构建的异常错误，其余的各模块下的混淆也可单独在各模块写入，最后在主项目中进行一个汇总


## 项目应用 ##

目前接入的测试模块module_girls和工单模块module_third是可运行的，新建立的模块以module_(模块简称)命名，在build.gradle中对lib_common和lib_coremodel模块进行引用就可以进行开发。

模块跳转：

	 //组件化
            case R.id.btWork:
                ARouter.getInstance().build(ARouterPath.WorkListAty).navigation(MainActivity.this);
                break;

**模块的迁移注意事项:**

1、Activity/Fragment界面需要添加路由注解如：

    @Route(path = ARouterPath.GirlsListAty)

2、传值处理

传值-》

 	ARouter.getInstance()
                    .build(ARouterPath.DynaGirlsListAty)
                    .withString("fullUrl", "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1")
                    .withTransition(R.anim.activity_up_in, R.anim.activity_up_out)
                    .navigation(ActivityGirls.this, 3);

接收-》

	@Autowired(name = "fullUrl")//添加注解
    public String fullUrl;
	//inject需要注入后才可以读取到携带过来的参数
        ARouter.getInstance().inject(this);

3、请求示例

	//获取retrofit对象
 	    Observable<RequestResult<UserModel>> observer = APIManager.buildAPI(APIHostManager.Common_Url,UserService.class).login(mBinding.tvUserName.getText(), mBinding.tvPwd.getText());
	//获取ViewModel对象
        CommonViewModel viewModel = ViewModelProviders.of(LoginActivity.this).get(CommonViewModel.class);
	//调用下面的request请求方法
        viewModel.request(this,null, observer, new ObserverCallback<UserModel>() {

            @Override
            public void success(UserModel userModel) {
				//成功返回

            }

            @Override
            public void failure(Throwable e) {
				//错误返回-其中的错误提示已经展示服务器提示，特殊错误如500会提示网络不稳定
                dismissDialog();
            }
        });

4、状态页面属性配置

于lib_common下的LoadingCallback中修改加载样式（如需修改必须改）,此处主要是在BaseApplication下的Lodsir初始化用到。



//以下为公共回调类

于lib_coremodel下的LoadingCallback中修改加载样式（和上面同步就行）

于lib_coremodel下的其他callback中修改对应样式（和上面同步就行）

**新模块需要配置的地方：**


1、`build.gradle`可以直接复制工单模块中的build.gradle的文件

2、在`src/main/`下面添加`module`目录，并在下面添加`AndroidManifest.xml`文件，这个配置文件为模块单独运行时（debug）所需要,当项目被整体打包时会调用模块本身的`AndroidManifest.xml`，所以这俩个文件中都需要对各个**Activity**和**权限**以及其他的一些配置进行注册.

3、在`src/java/`下添加debug目录，添加集成BaseApplication的Applictaion，并在`module/AndroidManifest.xml`下引用，用于模块单独运行（debug）时的一些第三方的初始化。在这个里面需要注意添加以下代码：

 	if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
最终要**记得主项目中的Applition也加入这些初始化**。

4、对应的`value/colors`、`value/string`是可以直接添加在对应模块下的，语言切换时可以读取到下面的中英文值，不同模块colors下的重复名也没有影响（已测）；其余的文件（styles等...）待测。

资源重复问题尽量不要相同，用一下resourcePrefix "前缀_"方法处理

	android {
    compileSdkVersion 28
    resourcePrefix "me_"
 
    defaultConfig {
        minSdkVersion 15
        targetSdkVersion 28
        versionCode 1
        versionName "1.0"
    	}
	}
	


5、Activity/Fragment需要添加路由注解如：

    @Route(path = ARouterPath.GirlsListAty)

> 模块可以单独进行运行，不需要编译整个项目，在android studio的Edit Congfigurations进行模块切换就可运行。

## 项目进阶 ##

1、还需要对已有和将来复用公用的工具类以及网络请求模式进行优化、提取。

2、对不同项目的应用可以采取主题风格化配置，在不改变样式的情况下只改变主题的颜色，就可以实现快速切换主题。

3、其余的问题查阅项目下《支持文档》目录


