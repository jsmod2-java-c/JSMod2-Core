package cn.jsmod2;

import cn.jsmod2.core.Application;
import cn.jsmod2.core.annotations.ServerApplication;
import cn.jsmod2.core.utils.Utils;

import java.util.concurrent.CountDownLatch;

@ServerApplication(DefaultServer.class)
public class ServerStarter {

    public void start(String[] args){
        Utils.TryCatch(()->{
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(()->{
                Starter.run(args);
                latch.countDown();
            }).start();
            latch.await();
            CountDownLatch latch1 = new CountDownLatch(1);
            new Thread(()->{
                latch1.countDown();
                UIStarter.run(args);
            }).start();
            latch1.await();
            Application.run(this.getClass(),args);
        });
    }
}