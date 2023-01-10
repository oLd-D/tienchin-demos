package com.guo.repeat_submit.request;

import org.springframework.stereotype.Component;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


public class RepeatableRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] bytes;

    public RepeatableRequestWrapper(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super(request);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 请求体都是一行传来的, 和其他部分用LF分隔符隔开
        bytes=request.getReader().readLine().getBytes();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // 将Servlet字节流包装成字符流, 再包装成缓存字符流
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 将request的字节数组包装成字节输入流以便读取
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        // 返回一个Servlet字节流
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public int available() throws IOException {
                return bytes.length;
            }
        };
    }
}
