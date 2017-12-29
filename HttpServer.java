package cn.shinelon.HTTP;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
	public static int port=80;
	public ServerSocketChannel serverSocketChannel;
	public ExecutorService executorServer;
	public static int POOL_NUMBER=4;
	public Charset charset=Charset.forName("GBK");
	public HttpServer() throws IOException {
		//Runtime.getRuntime().availableProcessors()获得电脑CPU的核数
		executorServer=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_NUMBER);
		serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress( port));
		System.out.println("服务器启动");
	}
	public void service() {
		while(true){
		SocketChannel socketChannel=null;
		try {
			socketChannel=serverSocketChannel.accept();
			executorServer.execute(new Handler(socketChannel));
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		}
	
	public static void main(String[] args) {
		try {
			new HttpServer().service();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//内部类，处理客户端发来的请求
	class Handler implements Runnable{
		private SocketChannel socketChannel;
		public Handler(SocketChannel socketChannel) {
			this.socketChannel=socketChannel;
		}

		@Override
		public void run() {
			try {
				handler(socketChannel);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		private void handler(SocketChannel socketChannel) throws IOException {
			Socket socket=socketChannel.socket();
			System.out.println("接收到来自客户端："+socket.getInetAddress()+":"+socket.getPort()+"的请求");
			ByteBuffer buffer=ByteBuffer.allocate(1024);
//			InputStream is=socket.getInputStream();
			socketChannel.read(buffer);
			buffer.flip();
			String request=decode(buffer);		//解码
			System.out.println(request);		//打印请求正文
			
			//生成HTTP相应结果
			StringBuffer stringBuffer=new StringBuffer("HTTP/1.1 200 OK\r\n");
			stringBuffer.append("Content-Type:text/html\r\n\r\n");
			socketChannel.write(encode(stringBuffer.toString()));
			
			//分析请求的信息
			FileInputStream is;
			String firstLine=request.substring(0, request.indexOf("\r\n"));
			String path=HttpServer.class.getResource("").toString();
			if(firstLine.indexOf("login.html")!=-1){
      //这里的路径是静态的绝对路径
				is=new FileInputStream("G:/ProgramFiles/eclipsefile/Socket/src/cn/shinelon/HTTP/login.html");
			}else{
				is=new FileInputStream("G:/ProgramFiles/eclipsefile/Socket/src/cn/shinelon/HTTP/hello.html");
			}
			FileChannel fileChannel=is.getChannel();
			fileChannel.transferTo(0, fileChannel.size(), socketChannel);
			if(socketChannel!=null){
				socketChannel.close();
			}
			if(socket!=null){
				socket.close();
			}
		}
		private ByteBuffer encode(String string) {
			return charset.encode(string);
		}
		private String decode(ByteBuffer buffer) {
			CharBuffer charbuffer=charset.decode(buffer);
			return charbuffer.toString();
		}
	}
}
