# XIPC

RPC进程通信框架

## 同一APP中不同进程通信

### 1、定义接口

接口为客户端进程能够调用的方法，由服务端进程实现，指定`@Service`注解

```java
@Service("music")
public interface IMusicService {
    boolean play(String music);

    void stop();
}
```

### 2、定义Android Service

定义服务继承自`XIPCService`，同时指定注解`@Service`，注解参数需要和接口一致

```java
@Service("music")
public class MusicService extends XIPCService {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
```

实现接口:

```java
public class MusicServiceImpl implements IMusicService {

    @Override
    public boolean play(String music) {
        //......
        return true;
    }

    @Override
    public void stop() {

    }
}
```

### 3、初始化

#### 客户端进程初始化

```java
XIPCConfig xipcConfig = new XIPCConfig.Builder()
			.context(this)
			.addService(MusicService.class) //添加服务端Service
			.build();
XIPC.init(xipcConfig);
```

#### 服务端进程初始化

```java
xipcConfig = new XIPCConfig.Builder()
			.context(this)
			.build();
XIPC.init(xipcConfig);
XIPC.register(new MusicServiceImpl()); //注册服务实现
```

### 4、开始通信

```java
public class MainActivity extends AppCompatActivity {

    private XServiceCall<IMusicService> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        call = XIPC.newCall(IMusicService.class);
        
        XServiceCall.Listener<IMusicService> listener = 
            new XServiceCall.Listener<IMusicService>() {
            @Override
            public void create(IMusicService iMusicService) {
                boolean result = iMusicService.play("客户端传递数据！");
            }
        };
        call.connect(listener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开服务连接
        call.disconnect();
    }
}
```

## 不同APP通信

### 1、服务端App

**1.1、定义服务接口**

`Process`注解指定服务端进程名：

```java
@Service("music")
@Process("com.enjoy.xipc.servicesample")
public interface IMusicService {
    boolean play(String music);

    void stop();
}
```

**1.2、实现接口:**

```java
public class MusicServiceImpl implements IMusicService {

    @Override
    public boolean play(String music) {
        //......
        return true;
    }

    @Override
    public void stop() {

    }
}
```

**1.3、定义Android Service**

不再需要指定任何注解:

```java
public class MusicService extends XIPCService {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
```

**1.4、初始化**

```java
XIPCConfig xipcConfig = new XIPCConfig.Builder().context(this)
                .build();
XIPC.init(xipcConfig);
XIPC.register(new TestServiceImpl());
```

### 2、客户端App

**1、定义与服务端一致的接口，无需在意包名**：

```java
@Service("music")
@Process("com.enjoy.xipc.servicesample")
public interface IMusicService {
    boolean play(String music);

    void stop();
}
```

**2、初始化**

```java
XIPCConfig xipcConfig = new XIPCConfig.Builder().context(this)
    //注册服务 进程名（需要与接口Process一致）：Service全限定名
                .addService("com.enjoy.xipc.servicesample",
                        "com.enjoy.xipc.servicesample.MainService")
                .build();
XIPC.init(xipcConfig);
```

**3、通信**

```java
public class MainActivity extends AppCompatActivity {

    private XServiceCall<IMusicService> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        call = XIPC.newCall(IMusicService.class);
        
        XServiceCall.Listener<IMusicService> listener = 
            new XServiceCall.Listener<IMusicService>() {
            @Override
            public void create(IMusicService iMusicService) {
                boolean result = iMusicService.play("客户端传递数据！");
            }
        };
        call.connect(listener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开服务连接
        call.disconnect();
    }
}
```



## 自定义TypeAdapter

目前默认支持了基础数据类型（含基本数据类型的包装类型与数组）和实现了Parcelable接口的数据类型的自动序列化，如果需要支持其他数据类型，需要提供该数据类型的`TypeAdapter`实现类，并在XIPC初始化时完成注册。

**自定义数据类型User**

```java
public class User {

    String name;
    String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
}
```

**实现TypeAdapter接口**

`TypeAdapter`接口需要实现三个方法:

- **handles**:  是否支持对象类型的序列化
- **writeToParcel**：序列化对象
- **readFromParcel**：反序列化对象

无论是序列化还是反序列化需要通过Parcel记录或读取数据。甚至还可以将对象转成JSON字符串，只需要将JSON字符串交给Parcel操作就可以。

```java
public class UserAdapter implements TypeAdapter {

        @Override
        public boolean handles(Object o) {
            return o instanceof User;
        }

        @Override
        public void writeToParcel(Object o, Parcel dest) {
            User u = (User) o;
            dest.writeString(u.name);
            dest.writeString(u.pwd);
        }

        @Override
        public Object readFromParcel(Parcel in) {
            String name = in.readString();
            String pwd = in.readString();
            return new User(name,pwd);
        }
}
```

#### 初始化时将UserAdapter添加到XPIC中

```java
XIPCConfig config = new XIPCConfig.Builder()
        .context(this)
        .addAdapter(new UserAdapter())
        .build();
XIPC.init(config);
```

