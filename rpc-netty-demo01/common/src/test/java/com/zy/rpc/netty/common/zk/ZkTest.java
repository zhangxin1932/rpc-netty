package com.zy.rpc.netty.common.zk;

import com.zy.rpc.netty.demo01.common.zk.CuratorZkClient;
import com.zy.rpc.netty.demo01.common.zk.ZkUrl;

public class ZkTest {

    public static void main(String[] args) {
        ZkUrl url = ZkUrl.builder().connectionStrings(new String[]{"192.168.0.156:2181"}).build();
        CuratorZkClient client = new CuratorZkClient(url);
        client.delete("/hello");
    }
}
