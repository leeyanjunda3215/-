package com.back.controller.FsicoBcos;

import com.back.fisco.HelloWorld;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fisco")
public class BcosController {
    private BcosSDK bcosSDK;
    private Client client;
    private HelloWorld helloWorld;

//    初始化 SDK
    private void initSDK() throws Exception {
        System.out.println("-----init BcosSDK:------");
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:fisco-config.xml");
        System.out.println("-----init config ok------");
        bcosSDK = context.getBean(BcosSDK.class);
        System.out.println("-----init BcosSDK ok------");
        // 为群组1初始化client
        client = bcosSDK.getClient(Integer.valueOf(1));
        System.out.println("-----init client ok------");
        // 向群组1部署HelloWorld合约
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        helloWorld = HelloWorld.deploy(client, cryptoKeyPair);
        System.out.println("-----deploy HelloWorld ok------");
    }

    @GetMapping("/get")
    public String getHelloworld() throws Exception {
        System.out.println("-----getHelloworld------");
        if(bcosSDK == null){
            initSDK();
        }
        if(bcosSDK == null || helloWorld == null){
            System.out.println("-----init BcosSDK failed------");
            return "-----init BcosSDK failed----";
        }
        // 调用HelloWorld合约的get接口
        String getValue = helloWorld.get();
        System.out.println("-----call HelloWorld get success------:" + getValue);
        return getValue;
    }

    @GetMapping("/set")
    public String setHelloworld(@RequestParam(value="val",required = false,defaultValue = "default val")String val) throws Exception {
        System.out.println("-----setHelloworld------");
        if(bcosSDK == null){
            initSDK();
        }
        if(bcosSDK == null || helloWorld == null){
            System.out.println("-----init BcosSDK failed------");
            return "-----init BcosSDK failed----";
        }
        // 调用HelloWorld合约的set接口
        TransactionReceipt receipt = helloWorld.set(val);
        System.out.println("-----call HelloWorld get success------:" + receipt.getMessage());
        return "setHelloworld success";
    }

    @GetMapping("/test")
    public String test(){
        System.out.println("-----test------");
        return "this is bcos demo";
    }

    @GetMapping("/block")
    public String getBlockNumber(){
        System.out.println("-----getBlockNumber getBlockNumber------");
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:fisco-config.xml");
        BcosSDK bcosSDK = context.getBean(BcosSDK.class);
        Client client = bcosSDK.getClient(Integer.valueOf(1));
        BlockNumber blockNumber = client.getBlockNumber();
        return "getBlockNumber: "+blockNumber.getBlockNumber().toString();
//        return "";
    }
}