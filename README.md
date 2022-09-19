# FlyingLogFrame
安卓日志框架，为了更加安全高效的查看定位问题，设计了FlyingLogFrame这款本地&amp;远程操作的日志框架。

## 框架结构
#### Flying
- LogUtils (项目中需要输出的日志)
- CrashUtils （异常崩溃信息，由CrashHandle处理）
- JsonUtils （项目中网络请求信息与响应信息的输出）

## 框架功能点
### 自动化保存Log
设置BuildConfig.DEBUG,在Debug包模式下打印日志，且保存日志到文件
### 日志级别
通过定义日志等级0~5, 0=verbose,1=debug,2=info,3=warn,4=error 5=failtal，控制日志输出范围及保存范围
### 设备、系统及应用详细信息
在每次进入应用后会输出设备品牌、设备型号、设备类型、屏幕宽高、屏幕密度、密度像素、目标资源、最小宽度、安卓版本、API 版本、CPU 架构、
应用版本、安装时间、应用权限状态等等信息。
### 日志文件过期操作
自定义保存文件时长。单位：天 
每次进入应用后判断是否改删除过期日志，保持手机内存干净。
### 文件上传
获取文件列表遍历上传文件到服务器
### 文件加密解密
在上传文件过程中先进行DES加密，然后在上传加密后的文件。需要查看通过拉取服务器日志文件进行解密操作。

## LogFrameApi
### 输出日志
LogUtils.v("Hello Android");
### 初始化日志框架且设置日志是否输出   isDebug = true 打印 且 创建日志文件   false 不打印 但 创建日志文件
LogUtils.initialize(application,BuildConfig.DEBUG);
### 设置日志级别 0~5 值越大输出日志范围越小
LogUtils.setLogLevel(5);
### 输出设备、系统及应用详细信息
LogUtils.v(DeviceDetailInfo.getDevicesInfo(this, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
### 设置保存文件时长
LogToFile.setFileSaveDays(15);
### 是否超过日志文件保存时长
LogToFile.isDeleteLogFile() true 为超过
### 删除超过保存时长的所有文件及未超过的只剩下当前时间日的日志文件
LogToFile.deleteFile();
### 获取文件列表
LogToFile.getFileList();

### Usage
----

#### Gradle

```groovy
allprojects {
   repositories {
   maven { url 'https://jitpack.io' }
   }
}

dependencies {
   implementation 'com.github.yyds-zy:LogFrame:1.0.1'
}
```

#### Maven 

```xml

<repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
</repositories>
  
<dependency>
      <groupId>com.github.yyds-zy</groupId>
      <artifactId>LogFrame</artifactId>
      <version>1.0.0</version>
</dependency>

```

Use it in your own code:

Application:
```java
        // register本地异常捕捉
        CrashHandler.register(application);
        //初始化日志框架
        LogUtils.with(application,BuildConfig.DEBUG)
                .setLogLevel(5).systemOutPutDeviceInfo(DeviceDetailInfo.getDevicesInfo(
                application, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE))
                .setLogDeleteDays(5)
                .autoDeleteLogFile();

        //初始化异常日志框架
        CrashUtils.with(application,BuildConfig.DEBUG)
                .setCrashDeleteDays(5)
                .autoDeleteCrashFile();

        //初始化http请求数据框架
        JsonUtils.with(application,BuildConfig.DEBUG)
                .setJsonDeleteDays(5)
                .autoDeleteJsonFile();
        
        
        JsonUtils.v(json);
        
        CrashUtils.e(errorMsg);
```

```
        LogUtils.v("MainActivity");
        LogUtils.v("111111111111");
        LogUtils.d("222222222222");
        LogUtils.i("333333333333");
        LogUtils.e("444444444444");
        LogUtils.w("555555555555");
        LogUtils.wtf("666666666666");
```

Activity:
```
        JsonUtils.v(json);
```
        
```
        CrashUtils.e(errorMsg);
```
