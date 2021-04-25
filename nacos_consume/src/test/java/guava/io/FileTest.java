package guava.io;

import com.google.common.io.Files;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class FileTest {

    String sourcePath = "D:\\GitHubSource\\spring-cloud-parent-demo\\nacos_consume\\src\\main\\resources\\file\\source.text";
    String targetPath = "D:\\GitHubSource\\spring-cloud-parent-demo\\nacos_consume\\src\\main\\resources\\file\\target.text";

    /**
     * Files.copy
     * @param 	sourcePath 源路径
     * @param 	targetPath 目标路径
     * @return void
     * @Author LeeYoung
     * @Date 2021/4/25
     **/
    @Test
    public void copyFileTest() throws IOException {
        File targetFile = new File(targetPath);
        Files.copy(new File(sourcePath),targetFile);
        assertThat(targetFile.exists(),equalTo(true));
    }

    /**
     * java NIO 的 copy
     * @param 	Paths1 源路径
     * @param 	Paths2 目标路径
     *  @param 	CopyOption copy模式
     * @return void
     * @Author LeeYoung
     * @Date 2021/4/25
     **/
    @Test
    public void nioCopyFileTest() throws IOException {
        java.nio.file.Files.copy(Paths.get(sourcePath),
                Paths.get(targetPath),
                StandardCopyOption.COPY_ATTRIBUTES
                );
        File targetFile = new File(targetPath);
        assertThat(targetFile.exists(),equalTo(true));

    }


    @After
    public void tearDown(){
        File file = new File(targetPath);
        if(file.exists())
            file.delete();
    }
}
