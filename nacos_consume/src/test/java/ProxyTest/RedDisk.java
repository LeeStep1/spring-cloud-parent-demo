package ProxyTest;

/**
 * @description:
 * @author: liyang
 * @date: 2020-02-27
 **/
public class RedDisk implements DiskFactory {
    @Override
    public void makeDisk() {
        System.out.println("制造红色光盘.....");
    }

    public void makeDVD(){
        System.out.println("制造红色DVD.........");
    }
}
