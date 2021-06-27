package com.company;


import java.io.*;

public class HelloClassLoader extends ClassLoader {

    public static final String filePath = "/Users/bozhang/Downloads/Hello.xlass";

    public static void main(String[] args) throws Exception {
        Class hello = new HelloClassLoader().findClass("Hello");
        hello.getMethod("hello").invoke(hello.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = negation(getFile(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] getFile(String filePath) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len;
            while(-1 != (len = in.read(buffer,0,buf_size))){
                bos.write(buffer,0,len);
            }
            return bos.toByteArray();
        }
    }

    private byte[] negation(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ~bytes[i];
        }
        return bytes;
    }
}
