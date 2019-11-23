# 🚀 react-native-bootsplash
> 又一个splash-screen，完美解决安卓刘海屏问题。

### background
- `splash-screen`目前有一些问题无法解决。
- 1. 仅支持简单闪屏设置，如果有闪屏添加动效的需求它不能实现。
- 2. `splash-screen`在MainActivity#setContentView()之前显示，并且以`Dialog`形式展现。经常会发生应用启动后卡在闪屏页面，自己不会消失，同时`dev-menu`也不能显示出来。
- 3. 最重要的一点,`splash-screen`不支持适配刘海屏。如果你的应用在闪屏是单色背景，刘海位置以下才显示内容，那不会有更多的烦恼。否则你会发现`splash-screen`的支持残弱了。
- `splash-screen`实现思路：在MainActivity#setContentView()之前弹出一个全屏的`Dialog`，该对话框中显示闪屏，等到`ReactRootView`加载完成后由JS侧通知`splash-screen`来移除这个对话框。
- `splash-screen`的能力
```java
SplashScreen.show(this); // 此时刘海屏状态栏会有黑条。
SplashScreen.show(this, true); // 此时刘海屏状态栏会有灰条。
SplashScreen.show(this, R.style.SplashScreenTheme);// 此时只能自定义`statusBarColor`为白色来遮掩，不能解决根本问题
```

### 解决问题
- 根据[Android Splash Screen最佳实践，包含全面屏，刘海屏适配](https://www.jianshu.com/p/105885c44e49)文章的讲述，完美适配刘海屏使用`layer-list`的方式可以做到，再加上给`SplashActivity`添加启动时的主题，可以做到应用冷启动时也不会有白屏。
- 所以将上述方案移植到继承自`ReactActivity`的`MainActivity`怎么样呢？答案是可以适配，但是等到应用进入到RN页面时，`MainActivity`的theme中带着闪屏页面，所以该页面不会消失。
- 这样看起来适配刘海屏的闪屏页面必须单独作为一个Activity出现。
- 翻看[react-native-bootsplash](https://github.com/zoontek/react-native-bootsplash)代码实现，刚好利用上了SplashActivity，那么这里就可以任意大作文章而不会影响到RN页面了。