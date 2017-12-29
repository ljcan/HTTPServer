# HTTPServer
基于socket实现一个简单的http服务器实现静态资源的交互
启动服务器，在浏览器输入网址127.0.0.1/login.html即可进入登录页面，提交信息后进入主页，
这里重点是服务器的编写，不必太注重HTML的编写，熟悉http的交互原理是关键。

接收到来自客户端：/0:0:0:0:0:0:0:1:64048的请求
GET /login.html HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate
Connection: keep-alive
Upgrade-Insecure-Requests: 1
Cache-Control: max-age=0


接收到来自客户端：/0:0:0:0:0:0:0:1:64050的请求
GET /hello.html?username=%E5%88%98%E5%86%9B%E5%BC%BA&pwd=123 HTTP/1.1
Host: localhost
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
Accept-Encoding: gzip, deflate
Referer: http://localhost/login.html
Connection: keep-alive
Upgrade-Insecure-Requests: 1

从服务器返回的请求信息可以看出tcp三次握手，每一次的请求都会建立一个新的连接.
