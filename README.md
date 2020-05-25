# TurnPageView
翻页布局。类似小说阅读器的仿真翻页动画。

效果图：
![image](https://github.com/ExistNotSee/TurnPageView/blob/master/Gif_20200502221622.gif)

感谢：[NovelReader小说阅读器](https://github.com/newbiechen1024/NovelReader "NovelReader小说阅读器")作者。TurnPageView项目代码是从该开源项目中分离出来，方便使用。


使用方法：
```xml
<com.yoy.turnpageview.widget.view.PageView
	android:id="@+id/mPageView"
	android:layout_width="match_parent"
	android:layout_height="match_parent" />
```
```java
//第一页
val bg1 = layoutInflater.inflate(R.layout.layout_bg1, null)
//第二页
val bg2 = layoutInflater.inflate(R.layout.layout_bg2, null)
//第三页
val bg3 = layoutInflater.inflate(R.layout.layout_bg3, null)
mPageView.pageLoader.addPage(bg1)
mPageView.pageLoader.addPage(bg2)
mPageView.pageLoader.addPage(bg3)
mPageView.pageLoader.openChapter()
```
添加：
使用PageLoader#addPage添加View，添加完成后调用PageLoader#openChapter开始绘制。
删除：
使用PageLoader#removePage删除View。