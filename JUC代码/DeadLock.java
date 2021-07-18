
class Mirror{

}

class Lipstick{

}

class MakeUp extends Thread{
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    int choice;
    String girlName;

    MakeUp(int choice,String girlName){
        this.choice = choice;
        this.girlName = girlName;
    }

    @Override
    public void run(){
        try{
            makeup();

        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void makeup() throws InterruptedException{
        if(choice == 0){
            synchronized (lipstick){
                System.out.println(this.girlName + "获得镜子的锁");
                Thread.sleep(1000);
            }
            synchronized (mirror){
                System.out.println(this.girlName + "获得镜子的锁");
            }
        }
        else{
            synchronized (mirror){
                System.out.println(this.girlName + "获得镜子的锁");
                Thread.sleep(2000);
            }
            synchronized (lipstick){
                System.out.println(this.girlName + "获得口红的锁");
            }
        }
    }

}
public class DeakLock {
    public static void main(String[] args) {
        MakeUp makeUp1 = new MakeUp(0,"灰姑娘");
        MakeUp makeUp2 = new MakeUp(1,"白雪公主");
        makeUp1.start();
        makeUp2.start();
    }
}
