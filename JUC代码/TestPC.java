package pers.huangyuhui.sms.util;

import java.util.zip.CheckedInputStream;

public class TestPC {

    public static void main(String[] args) {
        SynContainer container = new SynContainer();
        new Producer(container).start();
        new Consumer(container).start();

    }
}

class Chicken{
    int id;
    Chicken(int id){
        this.id = id;
    }


}

class Producer extends Thread{
    SynContainer container;
    public Producer(SynContainer container){
        this.container = container;
    }

    @Override
    public void run(){
        for(int i = 0;i < 100;i++){

            container.push(new Chicken(i));
            System.out.println("生产了" + i + "只鸡");
        }
    }


}

class Consumer extends Thread{
    SynContainer container;
    public Consumer(SynContainer container){
        this.container = container;
    }

    @Override
    public void run(){
        System.out.println("消费了" + container.pop() + "只鸡");
    }

}


class SynContainer{
    Chicken[] chickens = new Chicken[10];
    int count = 0;

    public synchronized void push(Chicken chicken){
        if(count == chickens.length){
            try{
                this.wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }


        }
        chickens[count++] = chicken;
        this.notifyAll();


    }

    public synchronized Chicken pop(){
        if(count == 0){
            try{
                this.wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        Chicken chicken = chickens[count--];
        this.notifyAll();
        return chicken;

    }

}


