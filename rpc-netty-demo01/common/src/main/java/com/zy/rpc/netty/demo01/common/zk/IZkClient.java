package com.zy.rpc.netty.demo01.common.zk;

import java.util.List;
import java.util.concurrent.Executor;

public interface IZkClient {

    void stateChanged(int state);

    void addStateListener(StateListener stateListener);

    void create(String path, boolean ephemeral, boolean sequential);

    void delete(String path);

    void setData(String path, String data);

    String getData(String path);

    boolean checkExists(String path);

    List<String> getChildren(String path);

    boolean isConnected();

    void close();

    List<String> addChildListener(String path, ChildListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addDataListener(String path, DataListener listener, Executor executor);

    void removeDataListener(String path, DataListener listener);
}
