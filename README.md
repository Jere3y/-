# curiosity 好奇心
## 项目简介:

#### 1. 使用第三方库：RxJava2 + Retrofit + Okhttp + ButterKnife

    - RxJava 实现异步加载，更加高效、优雅的利用系统资源。
    
    - Retrofit + Okhttp 实现缓存，保证无网络状态下软件的可用性。
    
    - ButterKnife 减少胶水代码 findViewById(id) 之类的使用，使代码结构异常清晰、可读性大大增加。
   
#### 2. 项目结构异常清晰：

    - 提供3个基类`(BaseActivity.java, BaseFragment.java, BasePresenter.java)`来实现MVP设计。   
    
    - MVP设计，实现业务逻辑与用户界面高度解耦，提高代码可维护性，可阅读性。
    
    - Activity 与 Fragment 子类只负责显示界面，不负责处理数据。数据处理全部交给 BasePresenter 子类。  
    
    - BasePresenter 通过接口获得 Activity 或者 Fragment 的界面组件，控制数据的显示与更新。
       
#### 3. 命名合理：
