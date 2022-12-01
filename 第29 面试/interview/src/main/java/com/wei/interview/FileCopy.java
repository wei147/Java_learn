package com.wei.interview;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;

/**
 * 编程题:复制文件到指定文件夹
 * <p>
 * 所谓文件复制其实就是对于原始文佳一边读一边向目标文件写入
 */
public class FileCopy {
    public static void main(String[] args) throws IOException {
        //利用Java IO
        File source = new File("D:/project/ginger.zip");
        System.out.println((source.exists()));
        File target = new File("D:/project/try/ginger.zip");
        //定义两个输入输出对象  (因为zip文件是二进制的所以这里定义的是)
        InputStream input = null;
        OutputStream output = null;
        try {
            //文件的字节输入流 FileInputStream
            input = new FileInputStream(source);
            output = new FileOutputStream(target);
            byte[] buf = new byte[1024]; //定义一个缓冲区 1024即1k字节
            int byteRead;   //代表读取的位置
            //每执行一次就会读取1k字节
            while ((byteRead = input.read(buf)) != -1) {
                ////将读取的字节数组写入到指定的流中。第一个参数是读取到的字符数组buf,第二个是偏移量从0开始到byteRead读取到的最大长度
                output.write(buf, 0, byteRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert input != null;
            input.close();
            assert output != null;
            output.close();
        }
    }
    //2.FileChannel 实现文件复制
    //3.Commons IO组件实现文件复制
//    FileUtils.copeFile(Source,Target);
}
