package pers.huangyuhui.sms.util;

public class UnSafe {
    public static void main() {
        Buyticket buyticket = new Buyticket();

        new Thread(buyticket,"aaa").start();
        new Thread(buyticket,"bbb").start();
        new Thread(buyticket,"ccc").start();

    }
}


class Buyticket implements Runnable{
    private int ticketNum = 10;
    boolean flag = true;


    @Override
    public void run() {
        // 买票
        while(flag){
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void buy() throws InterruptedException {
        // 判断是否有票
        if(ticketNum <= 0){
            flag = false;
            return;
        }
        // 买票
        // 模拟延时
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + "拿到" + ticketNum--);

    }
}
