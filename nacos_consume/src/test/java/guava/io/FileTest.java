package guava.io;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;
import com.google.common.graph.SuccessorsFunction;
import com.google.common.graph.Traverser;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

    @Test
    public void fileAppendTest() throws IOException {
        File fileAppend = new File(sourcePath);
        CharSink charSink = Files.asCharSink(fileAppend, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write(" append Content");
        String read = Files.asCharSource(fileAppend, Charsets.UTF_8).read();
        assertThat(read,equalTo("hello  this is guava io test append Content"));
    }

    /**
     * guava - files  deque stack 非线程安全的双向队列实现
     * stopwatch : 22ms   文件数  545
     * @Author LeeYoung
     * @Date 2021/5/2
     **/
    @Test
    public void fileRecursionByGuavaTest(){
//        Stopwatch started = Stopwatch.createStarted();
        File source = new File("D:\\GitHubSource\\spring-cloud-parent-demo\\nacos_consume");
        Iterable<File> files = Files.fileTraverser()
//                .depthFirstPostOrder(source) //根据发现文件倒叙排列
                .depthFirstPreOrder(source) //根据发现文件顺序排序
                ;
//        FluentIterable<File> filter = FluentIterable.concat(files).filter(File::isFile);
//        filter.stream().forEach(System.out::println);

//        System.out.println(started.stop());

        files.forEach(System.out::println);
    }

    /**
     * Java - 文件递归
     * stopwatch : 100ms  文件数  545
     * @Author LeeYoung
     * @Date 2021/5/2
     **/
    @Test
    public void fileRecursionTest(){
//        Stopwatch started = Stopwatch.createStarted();
        List<File> files = Lists.newArrayList();
        File root = new File("D:\\GitHubSource\\spring-cloud-parent-demo\\nacos_consume");
        this.recursionFile(root,files);
//        System.out.println(started.stop());
        files.forEach(System.out::println);
    }

    private void recursionFile(File root, List<File> files) {
        if(root.isHidden()) return;
        if(root.isFile()){
            files.add(root);
        }else {
            File[] files1 = root.listFiles();
            for (File file : files1) {
                recursionFile(file,files);
            }
        }


    }

    @After
    public void tearDown(){
        File file = new File(targetPath);
        if(file.exists())
            file.delete();
    }
}
