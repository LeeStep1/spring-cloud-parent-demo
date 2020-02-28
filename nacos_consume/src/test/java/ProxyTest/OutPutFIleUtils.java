package ProxyTest;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class OutPutFIleUtils {

    public static void outPutFIle(Class clazz,String proxyName){

        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName,new Class[]{clazz});

        FileOutputStream fop = null;
        File file;

        try {

            file = new File("D:/" +proxyName + ".class");
            try {
                fop = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // if file doesnt exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // get the content in bytes

            fop.write(proxyClassFile);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
