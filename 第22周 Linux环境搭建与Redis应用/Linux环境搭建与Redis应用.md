##  Linux基础

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



#### Linux用户与用户组管理

##### Linux用户与权限

```
<用户>
Linux是多用户多任务系统，包含两个概念：用户与用户组
用户与账户是同一概念，用于登录系统与区分资源权限
用户让系统变的更安全，同时也保护了用户的个人数字资产

<用户组>
用户组就是将用户分组，隶属用户自动拥有组权限
一个用户可隶属于多个组，用户可任意切换当前组
用户组的出现让用户权限管理变更轻松
```

##### 用户与用户组常用命令

| 命令     | 用途                            |
| -------- | ------------------------------- |
| useradd  | 创建新用户                      |
| passwd   | 修改密码                        |
| usermod  | 修改用户信息/分配组（覆盖原组） |
| groupadd | 创建新的用户组                  |
| chown    | 更改文件的属主或属组            |
| chmod    | 更改文件的访问权限              |
| newgrp   | 切换用户当前组                  |

##### 项目内部文件权限管理实践

```
[root@hadoop100 wei]# adduser d1
[root@hadoop100 wei]# adduser d2
[root@hadoop100 wei]# adduser d3
[root@hadoop100 wei]# adduser t1
d1 adduser11
d2 adduser22
d3 adduser33
t1 adduser11

添加用户组
[root@hadoop100 wei]# groupadd developer
[root@hadoop100 wei]# groupadd testor

添加用户到用户组
[root@hadoop100 wei]# usermod -g developer d1
[root@hadoop100 wei]# usermod -g developer d2
[root@hadoop100 wei]# usermod -g testor t1

查看用户隶属的用户组
[root@hadoop100 wei]# groups
root
```

#### Linux文件权限设置

```
/usr/local/share/ 是共享文件夹，即所有的用户都看到该文件
[root@hadoop100 wei]# cd /usr/local/share/
[root@hadoop100 share]# ls
applications  info  man
[root@hadoop100 share]# mkdir dev-document

drwxr-xr-x.  2 root root   6 6月  18 15:03 dev-document
第一个root 代表的是当前创建这个目录的是哪个用户，也就是这个目录的拥有者、宿主
第二个root 则是对应了这个目录所关联的用户组是哪个

drwx   r-x  r-x  代表root用户有完整的权限，其他组员/用户组以外 只有r-x，没有w写权限

[root@hadoop100 share]# chown d1:developer dev-document
[root@hadoop100 share]# ll
总用量 0
drwxr-xr-x.  2 d1   developer   6 6月  18 15:03 dev-document
目录所关联的用户组变成了developer，宿主变成了d1	但用户组之外的人还可以访问该文件，权限并没有变

chmod 750 dev-document/ 改变文件目录权限		（这里的750是指 主 7 = 4+2+1，组 5=4+1，其他 0 = 0+0+0 参考下图）
[t1@hadoop100 share]$ cd dev-document/
-bash: cd: dev-document/: 权限不够			此时非同一用户组的t1访问不了该文件

[d1@hadoop100 dev-document]$ ll
总用量 4
-rw-r--r--. 1 d1 developer 24 6月  18 15:34 code.md
带 -开头的是文件而非文件夹

文件授权
[d1@hadoop100 dev-document]$ chmod 770 code.md 
[d1@hadoop100 dev-document]$ ll
总用量 4
-rwxrwx---. 1 d1 developer 24 6月  18 15:34 code.md


如何让一个用户拥有两个组 ？
usermod -G developer,testor d1 (需要重新连接一下才能更新)
如果d1需要查看testor组的内容需要 切换用户组 newgrp testor
[d1@hadoop100 ~]$ groups
testor developer
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220618151150860.png" alt="image-20220618151150860" style="zoom:50%;" />

```html
<chmod命令>
chmod750:组用户可读写，其他用户不允许访问
chmod777:所有用户拥有完整权限
chmod700:只有属主拥有完整权限
```



#### sudo获取超级管理员权限

```html
<sudo>
sudo可以让普通用户拥有超级管理员的执行权限
普通用户要进行经过超级管理员授权才能使用
授权命令：visudo
    
root用户下：visudo 
在对齐root的情况下输入
d1    ALL(ALL)	ALL	（第一个all表示从哪个主机名能执行sudo命令。第二个ALL表示容许切换其他用户。第三个ALL表示容许执行所有命令）
普通模式下输入：100gg 	快速定位到100行
    
最后加上visudo -c （对这个文件进行格式检查）
d1此时还是不能创建新用户，但是加上sudo useradd d3 就ok
为了避免频繁的输入密码 可以加上	d1    ALL(ALL)	NOPASSWD:ALL
```



#### firewall防火墙设置实战

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
firewall-cmd --reload 防火墙进行配置重载		（上一条命令配置8080端口之后需要重载才能生效。remove和add都需要重载）
firewall-cmd --zone=public --permanent --remove-port=8080/tcp	移除8080端口
firewall-cmd --zone=public --permanent --add-port=8000-9000/tcp  放行一个区域的端口

[root@hadoop102 bin]# ./startup.sh  启动防火墙，但只能在虚拟机上访问。windows无法访问
systemctl start firewalld 	启动防火墙
```



#### Bash Shell

```shell
什么是Shell
Shell是一个用c语言编写的脚本解释器，是用户通过代码操作Linux的桥梁
Shell脚本描述要执行的任务，完成系列复杂操作，文件通常以.sh后缀
Shell脚本通过Shell解释器执行，按解释器分类分为多种类型
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220619201547785.png" alt="image-20220619201547785" style="zoom:50%;" />

##### 最常用的是bash  

##### 一键发布Tomcat应用程序

```
echo "准备下载Tomcat9"
wget https://mirrors.huaweicloud.com/apache/tomcat/tomcat-9/v9.0.64/bin/apache-tomcat-9.0.64.tar.gz	（从网络中下载东西）
echo "正在解压缩Tomcat9"
tar zxf apache-tomcat-9.0.64.tar.gz			（zxvf中的v表示显示解压缩的完整过程,在批处理中不需要）
echo "防火墙开放8080端口"
firewall-cmd --zone=public --permanent --add-port=8080/tcp
firewall-cmd --reload
echo "启动Tomcat"
cd ./apache-tomcat-9.0.34/bin
./startup.sh

如何运行shell脚本？  /bin/bash deploy_tomcat.sh 或者 ./deploy_tomcat.sh		(./表示当前目录下，必须要加)

[root@hadoop102 local]# ./deploy_tomcat.sh
bash: ./deploy_tomcat.sh: 权限不够					提示权限不够，需要用chmod赋予root用户执行权限
```



#### Linux部署OA项目-环境准备

##### 综合训练：Linux部署慕课网办公OA

部署架构

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220620233654102.png" alt="image-20220620233654102" style="zoom:50%;" />

```
需要最小化安装WEB服务器和DB数据服务器  安装参考https://www.likecs.com/show-204581261.html
用root用户登录之后，输入ifconfig ，提示命令没有找到，需要yum install -y net-tools.x86_64 安装
centos-db ip:192.168.10.131
centos-web ip:192.168.10.132
```

#### CentOS安装MySQL8

```html
先找到mysql 8的链接下载地址 https://dev.mysql.com/get/mysql80-community-release-el7-6.noarch.rpm
然后在linux 安装 wget 		yum install -y wget
wget https://dev.mysql.com/get/mysql80-community-release-el7-6.noarch.rpm
将下载到本地的rpm包安装，命令是:	yum localinstall -y mysql80-community-release-el7-6.noarch.rpm 
以上，mysql 8的安装源准备好了
<安装mysql的社区版> yum install -y mysql-community-server	(安装太慢了)
解决思路是 先在windows下载centos的完整安装包，然后通过xftp传到 /var/cache/yum/x86_64/7/mysql-connectors-community/packages  然后在yum install -y mysql-community-server (会自动下载一些其他依赖)

（遇到报错 [获取 GPG 密钥失败：[Errno 14] curl#37 - "Couldn't open file /etc/pki/rpm-gpg/RPM-GPG-KEY-mysql-2022"] 时需要 改一下 vi /etc/yum.repos.d/mysql-community.repo 不校验 令gpgcheck=0）

<启动mysql>
通过yum模式进行安装，mysql会以服务的方式在系统中进行驻留
[root@localhost packages]# systemctl start mysqld
[root@localhost packages]# netstat -tulpn | grep mysql	查看
[root@localhost packages]# systemctl status mysqld		查看服务状态
[root@localhost packages]# systemctl enable mysqld		设置mysql服务随系统自动启动
    
<初始化MySQL>
mysql -uroot -p		会提示 ERROR 1045 (28000): Access denied for user 'root'@'localhost' 
vi /var/log/mysqld.log	在这个日志文件中可以看到 mysql为我们提供的密码
再次登录mysql后，需要改变默认的root密码
mysql> alter user 'root'@'localhost' identified with mysql_native_password by 'Wwwei199905.'
    （加上mysql_native_password 能兼容5.0，兼容性的考虑）
   
默认的root用户只能从本机登录。如何让root从远程登录？ 就涉及到如何修改root用户容许登录的设备
    mysql> use mysql
    mysql> select host,user from user;
	mysql> update user set host='%' where user='root';		（%表示容许root在所有设备上登录）
    mysql> flush privileges;		（这条命令可以让上一条修改权限的数据立即生效）
    
    因为要从远程访问mysql，所以要放行防火墙3306端口
    firewall-cmd --zone=public --permanent --add-port=public --permanent --add-port=3306/tcp
    firewall-cmd --reload		(重载，使生效)
```



#### 部署配置Web应用服务器

```
什么是open jdk? (java-1.8.0-openjdk.x86_64)	开源的java JDK
[root@localhost ~]# yum install -y java-1.8.0-openjdk.x86_64		安装
[root@localhost ~]# java -version		查看java版本
[root@localhost ~]# which java		/usr/bin/java    查看安装目录

[root@localhost local]# tar zxf apache-tomcat-9.0.64.tar.gz 		拖入并解压tomcat

[root@localhost local]# tar zxf imooc-oa.war 解压war包	（这里报错，先安装 yum install unzip unzip 文件名）
将解压文件移动到  mv imooc_oa ./apache-tomcat-9.0.64/webapps/

先安装vim   yum install -y vim-common 公共基础包 和vim增强包 yum install -y vim-enhanced 对oa项目文件进行修改配置 mybatis-config.xml
vim /usr/local/apache-tomcat-9.0.64/webapps/imooc-oa/src/main/resources/mybatis-config.xml

修改tomcat文件 vi ./conf/server.xml
        <Context path="/" docBase="imooc_oa" />
      </Host>
将imooc-oa的地址映射到根目录

启动tomcat
[root@localhost apache-tomcat-9.0.64]# ./bin/startup.sh 
查看启动情况 netstat -tulpn
启动之后将80端口进行对外暴露	firewall-cmd --zone=public --permanent --add-port=80/tcp
	firewall-cmd --reload 还是重载
	
错误汇总：用unzip解压文件时用记得设置一个文件名

为了避免其他都连接这个mysql，保证一个安全性所以有必要做一个限制
firewall-cmd --zone=public --permanent --remove-port=80/tcp	先屏蔽3306端口
firewall-cmd --reload 还是重载

firewall-cmd  --permanent --zone=public --add-rich-rule="rule family="ipv4" source address="192.168.10.132" port protocol="tcp" port="3306" accept"
注释：这条命令当防火墙遇到 192.168.10.132 这个ip向本机3306端口发送的数据包则给予放行
firewall-cmd --reload
```



<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220624132639288.png" alt="image-20220624132639288" style="zoom: 50%;" />

```html
<主要知识点>
◆介绍Linux与CentOS
◆讲解Linux:基础命令
◆Linux文本工具与命令
◆yum应用安装与卸载
◆CentOS的权限与系统安全
◆部署OA项目至Linux服务器
```



## Redis

#### Redis介绍

```html
内存型数据库 no-sql数据库
课程内容
<Redis介绍与安装>
<掌握Redisl的常用命令与数据类型>
<掌握在ava中操作Redis>
    
redis最核心的特性就是把原本存在硬盘的那些数据，把它转移到内存中，利用内存的高性能来提高程序的性能
    
nosql : 不仅仅只有sql		（轻量级数据库）
```

```
<Redis介绍>
Redis是Key-Value型NoSQL数据库
Redis>将数据存储在内存中，同时也能持久化到磁盘
Redisi常用于缓存，利用内存的高效提高程序的处理速度	（模式：redis从mysql那拿到数据，再由java从redis读取速度就会快很多）

java中的map 的key与value。redis可以看做是一个超大型的Map对象？
```



#### Redis特点

```
速度快
广泛的语言支持
持久化
多种数据结构
主从复制	（备份，数台redis服务器存储的数据是相同的）
分布式与高可用 （高可用：随时打开随时可用）
```

#### 

#### Linux系统安装Redis

```html
<Redis的安装和启动>
在Linux系统中安装Redis
在Vindows系统中安装Redis

cd到/usr/local/redis/redis-5.0.14 要让源代码成为能够运行的文件，需要用make。（需要先安装gcc,编译安装的make就是来源于gcc）

启动文件：/usr/local/redis/redis-5.0.14/src/redis-server
redis的连接客户端： /usr/local/redis/redis-5.0.14/src/redis-cli
启动命令：[root@hadoop102 redis-5.0.14]# ./src/redis-server redis.conf
```



#### Window系统安装Redis

```
启动命令 ：redis-server.exe redis.windows.conf	（在redis目录下启动）
```



#### 守护进程方式启动

##### redis的常用基本配置

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220625100223007.png" alt="image-20220625100223007" style="zoom:50%;" />

```
如果想让redis以服务的形式在后台运行，需要修改 daemonize 
daemonize yes

杀死/退出进程：kill -9 pid (不推荐)
```



#### Redis常用基本配置

```
[root@hadoop102 redis-5.0.14]# ./src/redis-cli 	检测服务可以输入ping （响应是pong） ctrl c | exit 退出
[root@hadoop102 redis-5.0.14]# ./src/redis-cli shutdown   （更为合适的退出redis命令）
netstat -tulpn | grep redis

vim redis.conf  port改为6379 -> 6380
logfile "redis.log"  设置默认的日志文件，默认为空 （在redis-5.0.14下）

[root@hadoop102 redis-5.0.14]# ./src/redis-cli 
Could not connect to Redis at 127.0.0.1:6379: Connection refused		（端口更改引发的错误）
[root@hadoop102 redis-5.0.14]# ./src/redis-cli -p 6380		（更改客户端连接端口）

redis的数据库是没有名字的，以编号来区分 0-15，共16个（默认可更改）
databases 16	（16即为数据库编号上限）
[root@hadoop102 redis-5.0.14]# ./src/redis-cli -p 6380 shutdown		（关闭redis服务）

redis.cof 里关于设置密码的(要输入密码才能访问)	# requirepass foobared -> requirepass 1234
127.0.0.1:6380> ping
(error) NOAUTH Authentication required.
127.0.0.1:6380> auth 1234		（要输入密码才能访问）

 dump.rdb	redis目录下就能看到，用来备份的。防止突然带来的宕机，不会造成数据上的损失
```



#### redis通用命令

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220626095056717.png" alt="image-20220626095056717" style="zoom:50%;" />

```
keys * 用于匹配该数据库的所有键 
```



#### Redis数据类型

```
String-字符串类型
Hash-Hash类型
List-列表类型
Set-集合类型
Zset-有序集合类型
```

##### String字符串类型

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220626154417641.png" alt="image-20220626154417641" style="zoom:33%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220626154638291.png" alt="image-20220626154638291" style="zoom: 33%;" />

```
incr 只能对数字进行自增，对字符无效
decrby age 3 对age自减3
```



##### Hash键值类型

```
Hash类型用于存储结构化数据
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220626230826717.png" alt="image-20220626230826717" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220626230902101.png" alt="image-20220626230902101" style="zoom: 50%;" />

```
127.0.0.1:6380[1]> hget emp:1 age
"19"
127.0.0.1:6380[1]> hgetall emp:1		（getall 获取所有emp:1的值）
1) "name"
2) "xiaoming"	......
127.0.0.1:6380[1]> hmset emp:2 name wei age 23 birthday 1999-05-29 	（一次为emp:2设置多个键值）
del emp:1 是将整个key从redis中删除
hdel emp:1 age	是删除指定key中的某个属性
hlen emp:1 返回属性长度
hexists emp:1 age 查看属性是否存在
```



##### List列表类型

```
Lst列表就是一系列字符串的"数组”，按插入顺序排序
List列表最大长度为2的32次方-1，可以包含40亿个元素
```

```html
<List命令>
rpush listey c b a -右侧插入		（r是right，从右侧插入）
lpush listey f e d -左侧插入		（l是left，从左侧插入）
rpop listkey -右侧弹出
lpop listkey -左侧弹出

[root@hadoop102 ~]# cd /usr/local/redis/redis-5.0.14/
[root@hadoop102 redis-5.0.14]# ./src/redis-server redis.conf 
[root@hadoop102 redis-5.0.14]# ./src/redis-cli -p 6380

LRANGE listkey 0 -1  （查看listkey里的值， -1代表末尾。即将第一个到最后一个都）
```



##### Set与Zset集合类型

```
Set集合是字符串的无序集合，集合成员是唯一的
Zset集合是字符串的有序集合，集合成员是唯一的

sadd set1 a  设置key为set1的集合，值是 a
smembers set1	查看set1的值

集合的运算
sinter set1 set2	求set1和set2的交集
sunion set1 set2 	两个集合的并集
sdiff set1 set2 	两个集合的差集（set1中有，set2中没有的元素）

Zset
zadd zset1 100 a	设置key为zset1的Z集合，权值为100 值为a		(权值越小，越靠前面)
zrange zset1 0 -1	遍历zset1
zrange zset1 0 -1 withscores	遍历时连分数也一起显示 
zrangebyscore zset1 100 102		通过分数筛选
```



#### Jedis介绍与环境准备

#### Java客户端-Jedis

```
Jedis是Java语言开发的Redis客户端工具包
Jedis只是对Redisi命令的封装，掌握Redis命令便可轻易上手

redis配置
1.vim redis.conf
protected-mode yes		当开启这个的话，只容许指定的IP地址才能进行访问。先设置为no （88行）
bind 127.0.0.1			现在是只容许本机访问，改为0.0.0.0，容许其他ip访问 （69行）
以上设置完成之后，还需要放行6379端口，才能让其他ip连接本机redis
firewall-cmd --zone=public --add-port=6379/tcp --permanent
firwall-cmd --reload
本机ip是：192.168.10.102
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>4.2.0</version>
</dependency>

```



#### 利用Jedis缓存数据

```java
package com.imooc.jedis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CacheSample {
    //数据初始化的工作
    public CacheSample() {
        //list里面所有的对象都是 商品信息 Integer goodsId, String goodsName, String description, Float price
        List<Goods> goodsList = new ArrayList<Goods>();

        Jedis jedis = new Jedis("192.168.10.102", 6379);
        try {
            //思路：将这些javabean 序列化为一个个json字符串，将其保存到redis里。用的时候再还原为java对象
            goodsList.add(new Goods(808, "小米手环", "一款运动手环", 99.9f));
            goodsList.add(new Goods(809, "跑步机配饰", "运动器材", 89.9f));
            goodsList.add(new Goods(8010, "进口奶粉", "来自新西兰", 79.9f));
            goodsList.add(new Goods(8011, "百乐P500", "一款考试用笔", 69.9f));
            jedis.auth("1234");
            jedis.select(8);
            for (Goods goods : goodsList) {
                String json = JSON.toJSONString(goods);
                System.out.println(json);
                String key = "goods:" + goods.getGoodsId();
                jedis.set(key, json);
            }
            System.out.println("写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static void main(String[] args) {

        new CacheSample();
        System.out.println("请输入要查询的商品编号");
        String goodsId = new Scanner(System.in).next();

        Jedis jedis = new Jedis("192.168.10.102", 6379);
        try {
            jedis.auth("1234");
            jedis.select(8);
            String key = "goods:" + goodsId;
            if (jedis.exists(key)) {
                String json = jedis.get(key);
                System.out.println(json);
                //如何将json转回java对象  通过JSON.parseObject
                Goods g = JSON.parseObject(json, Goods.class);
                System.out.println(g.getGoodsName());
            } else {
                System.out.println("该商品不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
```

2022年7月3日23:48:52 	Linux与Redis告一段落
