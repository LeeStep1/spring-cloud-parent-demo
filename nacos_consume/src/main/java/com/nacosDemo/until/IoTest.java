package com.nacosDemo.until;

import java.io.*;

public  class IoTest {

    public void FileTest() {
        OutputStream ot = null;
        InputStream it = null;
        try {
            ot= new FileOutputStream("D:"+ File.separator + "Test"+ File.separator + "Test.txt");
            it = new FileInputStream("D:" + File.separator + "log" + File.separator + "新建文本文档.txt");
            byte[] b = new byte[1024];
            while ( it.read(b)>= 0 ){
                ot.write("【".getBytes());
                ot.write(b);
                ot.write("】".getBytes());
            }

            ot.close();
            it.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void FileTestConsole() {
        OutputStream ot = null;
        InputStream it = null;
        try {
            File writeFile = new File("D:"+ File.separator + "Test"+ File.separator + "Test.txt");
            File readFile  = new File("D:" + File.separator + "log" + File.separator + "新建文本文档.txt");
            Writer writer = new FileWriter(writeFile);
            Reader reader = new FileReader(readFile);
            char[] b = new char[1024];
            int len = 0 ;
            while ((len = reader.read(b)) > 0){
                if(len != -1){
                    writer.write(b,0,len);
                }else {
                    writer.write(b,0,b.length );
                }


            }

            writer.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readerTest(){
        OutputStream ot = null;
        InputStream it = null;
        try {
            File fileReader = new File("D:" + File.separator + "log" + File.separator + "新建文本文档.txt");
            File fileWriter = new File("D:"+ File.separator + "Test"+ File.separator + "Test.txt");

            Writer writer = new FileWriter(fileWriter);
            Reader reader = new FileReader(fileReader);

            char[] chars = new char[1024];
            while (reader.read(chars) > 0){
                writer.write(chars);
            }

            writer.close();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public int maxInt(int i,int x, int y){
        int maxOne =  Math.max(i,x);
        int maxReturn = Math.max(maxOne,y);
        return maxReturn;
    }

    public static void main(String[] args) {
        IoTest test = new IoTest();
//        test.FileTestConsole();
        int a = test.maxInt(9,8,3);

        System.out.println(a);

    }


}
