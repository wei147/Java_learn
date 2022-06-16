 Linux基础

#### Linux操作系统介绍

```html
<什么是操作系统>
操作系统(Operating System)是应用程序运行的基础支撑环境
操作系统作用是管理和控制计算机系统的硬件与软件资源
Intel x86架构上常见的操作系统：Windows、Linux、Unix.（mac基于Unix开发）

<Linux为什么受欢迎>
免费使用，自由传播
支持多任务、多用户、多CPU
高效而灵活
兼容任意x86架构计算机
强大易用的系统命令
完整的应用软件生态
```

#### Linux发行版本介绍

```html
<Linux发行版本>
Linux系统内核(kernel)提供了Linux操作系统的核心功能
不同开发商在内核基础上扩展封装形成了不同发行版本
常见发行版：Red Hat Linux、CentOS、Ubuntu、SUSE.
    
<Linux发行版选择建议>
桌面系统：Ubuntu
服务器操作系统：CentOS(免费)、Red Hat Linux(收费)
特定需求：Debian(稳定性)、Fedora(新特性)、麒麟Linux(国产)
```

```html

```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220608230904587.png" alt="image-20220608230904587" style="zoom: 50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220608231545891.png" alt="image-20220608231545891" style="zoom:50%;" />



#### Linux文件操作命令

##### Linux命令格式

命令 [参数选项] [文件或路径]

Linux文件操作核心命令

| 命令   | 用途             |
| ------ | ---------------- |
| cd     | 切换目录         |
| pwd    | 查看当前目录     |
| ls、ll | 显示目录内容     |
| mkdir  | 创建目录         |
| cp     | 复制文件与目录   |
| mv     | 移动或重命名文件 |
| rm     | 删除文件或目录   |
| find   | 查找目录或文件   |

cd .. 返回上一级目录

```
mkdir ./imooc/sample/demo	（创建多级目录报错）
mkdir: 无法创建目录"./imooc/sample/demo": 没有那个文件或目录
mkdir -p  ./imooc/sample/demo		（创建成功，需要加参数-p，创建多级目录）
请善用 --help    mkdir --help

mkdir -p -v ./imooc/sample1	(加参数-v显示执行过程) （另一种写法直接 -pv）
mkdir: 已创建目录 "./imooc/sample1"	

cp text.txt sample （将text.txt复制到sample,只能复制一个文件）
cp -r  sample sample1	(-r参数把当前文件夹所有的文件都遍历复制)

mv text.txt Mytext.txt	（重命名）
mv text.txt demo		（移动文件）

rm 文件名  （有提示）
rm -f 文件名  （无提示）
rm -rf 文件名  （无提示，r是迭代删除）

find / -name *.exe	（按文件名查找所有后缀名为.exe的文件）

```



#### vim文本编译器

```
vim文本编辑器
vi是Linux重要的文字编辑工具，vim是它的增强版
vim用于在远程环境下用命令形式对文本进行在线编辑
用法格式：vim[选项][文件]

vim三种模式
普通模式：默认模式，文本只读，不可编辑
编辑模式：编辑文本模式，普通模式按键进入，Esc键退出
命令模式：执行保存、搜索、退出等操作
```

##### vim重要快捷键

| 命令         | 用途                                            |
| ------------ | ----------------------------------------------- |
| delete或x    | 删除单个字符                                    |
| dd           | 删除整行                                        |
| /str         | 全文查找str字符串，n下一个，N前一个             |
| :s/old/new/g | 替换文件内所有old字符串为new  （g代表全局替换） |
| u            | 撤销最近一次操作                                |
| :wq或者：wq! | 退出并保存，只读文件要额外加！                  |
| :q!          | 强制退出放弃保存                                |

当修改文档后，想回退的话可以在普通模式下按 u

全局替换 % s/一/两/g  把所有一换成两



#### 常用文本工具

| 命令 | 用途                   |
| ---- | ---------------------- |
| echo | 屏幕打印与文本输出     |
| cat  | 合并文件或查看文件内容 |
| tail | 显示文件内容尾部       |
| grep | 文本过滤工具           |

echo "hello" > hello.txt	可以在当前目录下创建一个hello.txt 文件，文本内容里面有hello字段（>符号代表的含义是将左侧命令产生的结果重写至右边的文件中）注：如果再执行一遍上条指令的话会将hello.txt的内容清空再写入

echo "hello" >> hello.txt 两个大于号是追加

cat -n hello.txt  查看的时候把行号加上

 cat -nE hello.txt  查看的时候显示空行

cat hello.txt name.txt >> full.txt	将两个tx的内容t合为一个txt

cat > test.txt << EOF  利用cat重写一个文件。<<为这个文件创建了一个输入流，输入的内容将写入。以EOF作为输入流的结束字符串

tail -n 2 test.txt  显示文件内容尾部 两行

tail -f test.txt  实时动态监听的文件内容尾部，有新增就打印		（ctrl c 退出）

grep hi test.txt  筛选这个文件本中有hi 字段的

grep hi test.txt > out.txt  筛选这个文件本中有hi 字段的并将含有该字段的行输出到out.txt

grep i...c test.txt 筛选以i开头以c结尾的行

ll | grep test.txt 筛选当前文件夹下名为 test.txt的文件  管道（ll将查询结果送入到grep作为数据源，之后按照后面的匹配规则进行文本行的筛选）

ll | grep -E "log[0-9]{1,5}.txt"    -E表示拓展的正则表达式，log后接的是0-9都行，至少有一位最多五位



#### 文件打包与压缩

文件打包 ： 文件的组织

压缩：磁盘的减少

```html
<Linux压缩程序-gzip>
gzip是Linux系统的文件压缩程序
gzip压缩包文件扩展名.gz
大流量的网站默认都在使用gzp进行数据压缩传输

<tar打包与压缩>
tar是Linux系统将多个文件打包和压缩的工具
tar本质是打包软件，扩展名.tar
tar可结合gzip或其他压缩工具实现打包压缩，扩展名.tar.gz
    
压缩命令：tar zcvf tomcat.tar.gz/usr/八ocal/tomcat	tar zcvf 压缩包名字 指明要压缩的目录	（zcvf中的c为create，创建压缩文件)
解压缩命令：tar zxvf tomcat.tar.gz -C /usr/local/tomcat	tar zxvf 指明解压缩包名字 -C（加这个可以指明解压路径；不加默认是当前目录） /usr/local/tomcat	
```

| 选项 | 用途               |
| ---- | ------------------ |
| z    | 通过gzip压缩或解压 |
| c    | 创建新的tar.gz文件 |
| v    | 显示执行过程       |
| f    | 指定压缩文件名称   |
| x    | 解压缩tar.gz文件   |
| -C   | 指定解压缩目录     |



#### yum 方式安装应用程序

```html
<为CentOS安装应用程序>
在CentOS中安装第三方应用程序包含两种方式：
rpm:Red Hats软件包管理器，相当于应用程序安装文件的执行者
编译安装：用户自己从网站下载程序源码进行编译安装
    
 <yum与rpm的关系>
rpm安装过程中，需要用户自己解决依赖问题
yum通过引入软件仓库，联网下载rpm包及依赖，并依次自动安装	(像maven？)
yum是rpm的前端程序，其目的就是简化rpm的安装过程
  
 <yum常用命令>
yum search应用名#在仓库中查询是否存在指定应用
yum install -y应用名	#全自动下载安装应用及其依赖
yum info应用名#查看应用详细信息
yum list installed应用名#查看已安装的应用程序
rpm-ql 应用名#查看安装后输出的文件清单
yum remove -y应用名#全自动卸载指定应用 
     
刚刚安装了tree它在哪个位置呢（需求）
     which tree	（tree是命令名）
刚刚额外增加了哪些文件，我需要了解 （需求）
     rpm -ql tree.x86_64 (tree.x86_64应用名)	
     
yum list installed 查看已安装的应用
yum list installed *tree*	筛选
yum remove tree.x86_64（卸载）
yum install -y tree.x86_64 (遇到所有的询问统一用y回答)
```



#### Centos编译安装redis

```
<编译方式安装应用程序>
如yum仓库未提供rpm,往往需要采用编译安装方式
编译安装是指从应用官网下载源码后，对源码进行编译后使用
编译命令：make#使用对应编译器对源码编译生成可执行文件
```

##### yum与编译安装比较

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220614143724871.png" alt="image-20220614143724871" style="zoom:50%;" />

```
编译redis之前需要下载gcc
编译安装只会在当前编译目录中生成应用程序（而不是/usr/bin）
在启动redis的时候需要启加载配置文件	[root@hadoop100 redis-5.0.14]# ./src/redis-server redis.con 
```



#### firewall 防火墙设置实战

```
什么是防火墙
防火墙是借助硬件和软件对内外部网络环境的保护措施
CentOS7基于firewall实现应用层防火墙，CentOS6基于iptables
firewall--cmd是firewalle的核心命令

对外开放Tomcat
启动Tomcat [root@hadoop102 bin]# ./startup.sh 
查看是否启动成功 netstat -tulpn|grep 8080	
在window系统不能通过虚拟机域名+8080端口直接访问到Tomcat，需要在防火墙上设置放行
查看防火墙是否已经启动 firewall-cmd --state
查看防火墙放行端口 firewall-cmd --list-ports
firewall-cmd --zone=public --permanent --add-port=8080/tcp	(zone为区域的意思，permanent为永久变更，--add-port=8080/tcp 采用tcp方式进行时会放行8080端口 )
firewall--cmd --reload 防火墙进行配置重载
firewall-cmd --zone=public --permanent --remove-port=8080/tcp	移除8080端口
firewall-cmd --zone=public --permanent --add-port=8000-9000/tcp  放行一个区域的端口
```



### Linux进阶应用

#### Linux系统管理命令

```html
<使用ifconfig查看网卡ip>
<netstat查看网络端口号>  netstat -tulpn 或者netstat -ano
```

##### netstat 常用选项

| 选项 | 用途                       |
| ---- | -------------------------- |
| t    | 显示tcp传输协议的连接状况  |
| u    | 显示udp传输协议的连接状况  |
| l    | 显示处于监听状态的网络连接 |
| p    | 显示应用PID和程序名称      |
| n    | 显示ip地址                 |
| a    | 显示所有连接               |
| o    | 显示计时器                 |

```html
<查看进程&杀死进程>	ps -ef （单机程序使用这个，联网程序则选择netstat） 	kill -9 PID	
    ps -ef | grep vim	筛选包含vim的进程（|代表通道）
     netstat -tulpn | grep 	一样可以筛选
```



#### 应用服务化

```html
<应用服务化>
应用服务化是指让应用程序以服务方式在系统后台运行
Linux系统对服务化应用进行统一管理
服务管理命令：<systemctl>	（系统 control）
```

##### systemctl

| 指令            | 用途             |
| --------------- | ---------------- |
| start           | 启动服务         |
| stop            | 停止服务         |
| restart         | 重启服务         |
| enable          | 设置开机启动     |
| disable         | 禁止开启启动     |
| status          | 查看服务状态     |
| daemon-reload   | 重载服务配置文件 |
| list-unit-files | 列出所有服务     |

```
find / -name *.pid 寻找本地上的所有.pid文件。pid是应用程序的进程id
redis pid的文件名以及存储路径 /run/redis_6379.pid
/usr/lib/systemd/system 该路径下存储的是文件服务描述文件

After=syslog.target network.target remote-fs.target nss-lookup.target	（说明：必须在这些文件启动之后才容许启动redis.service服务）

[Service]
Type=forking	描述服务类型，后台运行
PIDFile=/run/redis_6379.pid
ExecStart=/usr/local/redis-5.0.14/src/redis-server /usr/local/redis-5.0.14/redis.conf
(说明在执行redis service时，会加载这些文件)
ExecStop=/bin/kill -s QUIT $MAINPID	 利用kill命令对指定的进程进行关闭	（运行时会将/run/redis_6379.pid中的值代入到MAINPID中）	（-s QUIT 通知进程采用正常流程进行退出，-9是强制退出）
PrivateTmp=true 代表为每一个service设置私有的临时文件目录

[Install]
WantedBy=multi-user.target	将当前redis 服务分配到multi-user.target这个服务组上（muti-user是系统默认的服务组，系统默认该服务组的服务能和系统一起启动）

[Unit]
Description=Redis
After=syslog.target network.target remote-fs.target nss-lookup.target

[Service]
Type=forking
PIDFile=/run/redis_6379.pid
ExecStart=/usr/local/redis-5.0.14/src/redis-server /usr/local/redis-5.0.14/redis.conf
ExecStop=/bin/kill -s QUIT $MAINPID
PrivateTmp=true

[Install]
WantedBy=multi-user.target

systemctl start redis	启动redis
2022年6月16日11:53:54 把	Type=forking 删除掉能正确运行
软关联，类似window的快捷方式
shutdown -r now 立即重启

systemctl list-unit-files	查看当每一个服务的状态
2022年6月16日12:12:15 redis服务还是static状态
 




```





Linux部署OA项目



```html
<主要知识点>
◆介绍Linux与CentOS
◆讲解Linux:基础命令
◆Linux文本工具与命令
◆yum应用安装与卸载
◆CentOS的权限与系统安全
◆部署OA项目至Linux服务器
```

