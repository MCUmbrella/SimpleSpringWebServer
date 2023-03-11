package vip.floatationdevice.simplespringwebserver;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
    @PostConstruct
    public void onStartup()
    {
        UserManager.load();
        if(!UserManager.hasUser("root")) UserManager.putUser("root", "123456", "Administrator");
        if(!UserManager.hasUser("user")) UserManager.putUser("user", "'i[l3P&SfYwHJ.6+jYuv?K.c)etsGDeh/\\V\\.v2tQEX8Sn]YJUR%\"ziEb_;E%\"0O^f-lyY|LWma^I-57M738]134WS6#$0%K8A6gA)g1kGt4\\G{0ZOm(c#y8S|Vc=={>", "MCUmbrella");
    }

    @PreDestroy
    public void onShutdown()
    {
        UserManager.save();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }
}
