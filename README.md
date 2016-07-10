# RemoteADB
局域网内无线连接ADB，摆脱数据线连接的烦恼

*** 之前的步骤

```
adb tcpip 5555  // 需连接数据线执行
adb connect xxxx.xxxx.xxxx.xxxx
```

简化后在APP内点击start利用shell命令执行```adb tcpip 5555```
然后直接在PC上执行下面连接命令，无需连接数据线，将下面命令保存bat命令更快(ip固定)

```
adb connect xxxx.xxxx.xxxx.xxxx
```
