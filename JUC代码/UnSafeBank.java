package pers.huangyuhui.sms.util;

import javax.sound.midi.SysexMessage;

public class UnSafeBank {
    public static void main() {
        Account account = new Account(100000,"结婚基金");
        Drawing you = new Drawing(account,50,"you");
        Drawing girlFriend   = new Drawing(account,50,"女朋友");

        you.start();
        girlFriend.start();

    }
}


class Account{
    int money;
    String name;

    public Account(int money,String name){
        this.money = money;
        this.name = name;
    }

}

// 银行，模拟取款

class Drawing extends Thread{
    Account account;
    int drawingMoney;
    int nowMoney;

    public Drawing(Account account,int drawingMoney,String name){
        super(name);
        this.drawingMoney = drawingMoney;
        this.nowMoney = nowMoney;

    }
    @Override
    public void run(){
        if(drawingMoney - account.money < 0){
            System.out.println(Thread.currentThread().getName() + "钱不够，取不了");
            return;
        }
        Thread.sleep(1000);
        account.money -= drawingMoney;
        nowMoney += drawingMoney;
        System.out.println(account.name + "余额为：" + account.money);
        System.out.println(this.getName() + "手里的钱为：" + nowMoney);
    }





}
