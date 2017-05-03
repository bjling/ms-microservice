#### 观察被占用的端口
netstat -ano|findstr "port"

#### 查看是哪个进程或者程序
tasklist | findstr "PID"

#### 结束该进程
taskkill /f /t /im Tencentdl.exe