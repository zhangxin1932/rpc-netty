package com.zy.grpc.proto.server.peer;

import com.zy.grpc.proto.server.message.*;
import com.zy.grpc.proto.server.service.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private final ManagedChannel channel;
    private final StudentServiceGrpc.StudentServiceBlockingStub blockingStub;
    private final StudentServiceGrpc.StudentServiceStub stub;

    public GrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
    }

    GrpcClient(ManagedChannel channel) {
        this.channel = channel;
        this.blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        this.stub = StudentServiceGrpc.newStub(channel);
    }

    /**
     * 1.入参是 对象, 出参也是对象
     * @param id
     */
    private void queryStuById(String id) {
        try {
            SearchResponse response = this.blockingStub.queryStuById(SearchRequest.newBuilder().setId(id).build());
            System.out.println("response --> " + response.getName());
        } catch (Exception e) {
            System.out.println("failed to request queryStuById");
            e.printStackTrace();
        }
    }

    /**
     * 2.入参是 对象, 出参是流
     * @param id
     */
    private void queryStuByIdStream2Resp(String id) {
        try {
            Iterator<StuResponse> resp = this.blockingStub.queryStuByIdStream2Resp(SearchRequest.newBuilder().setId(id).build());
            while (resp.hasNext()) {
                StuResponse next = resp.next();
                System.out.println(next);
            }
        } catch (Exception e) {
            System.out.println("failed to request queryStuByIdStream2Resp");
            e.printStackTrace();
        }
    }

    /**
     * 3.入参是 流, 出参是对象: 异步实现
     * @param list
     */
    private void queryStuByIdReqByStream(List<String> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StreamObserver<StuResponseList> responseObserver = new StreamObserver<StuResponseList>() {
            @Override
            public void onNext(StuResponseList value) {
                value.getStuResponseList().forEach(System.out::println);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };

        StreamObserver<SearchRequest> requestObserver = stub.queryStuByIdReqByStream(responseObserver);
        list.forEach(id -> requestObserver.onNext(SearchRequest.newBuilder().setId(id).build()));
        requestObserver.onCompleted();
    }

    /**
     * 4.入参是 流, 出参也是流: 异步实现
     * @param list
     */
    private void queryStuByIdReqAndResp2Stream(List<String> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StreamObserver<StuResponseStream> responseStreamStreamObserver = new StreamObserver<StuResponseStream>() {
            @Override
            public void onNext(StuResponseStream value) {
                System.out.println(value.getResp());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
            }
        };
        StreamObserver<StuRequestStream> request = stub.queryStuByIdReqAndResp2Stream(responseStreamStreamObserver);
        list.forEach(req -> request.onNext(StuRequestStream.newBuilder().setReq(req).build()));
        request.onCompleted();
    }

    private void shutdown() throws InterruptedException {
        this.channel.shutdown().awaitTermination(5L, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient grpcClient = new GrpcClient("127.0.0.1", 9090);
        try {
            System.out.println("-----------------------1.入参是 对象, 出参也是对象----------------------");
            grpcClient.queryStuById("1");
            System.out.println("-----------------------2.入参是 对象, 出参是流----------------------");
            grpcClient.queryStuByIdStream2Resp("2");
            System.out.println("-----------------------3.入参是 流, 出参是对象: 异步实现-----------------------");
            List<String> list = new ArrayList<>();
            list.add("1");
            list.add("2");
            list.add("3");
            grpcClient.queryStuByIdReqByStream(list);
            System.out.println("-----------------------4.入参是 流, 出参也是流: 异步实现-----------------------");
            grpcClient.queryStuByIdReqAndResp2Stream(list);
        } finally {
            grpcClient.shutdown();
        }
    }

}
