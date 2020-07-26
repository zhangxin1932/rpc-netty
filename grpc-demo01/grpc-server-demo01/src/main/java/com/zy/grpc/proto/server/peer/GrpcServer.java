package com.zy.grpc.proto.server.peer;

import com.zy.grpc.proto.server.message.*;
import com.zy.grpc.proto.server.service.StudentServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * server 与 client 参考
 * https://www.grpc.io/docs/quickstart/java/ 生成的 HelloWorldServer 与 HelloWorldClient
 */
public class GrpcServer {

    private Server server;

    public static void main(String[] args) throws InterruptedException, IOException {
        GrpcServer grpcServer = new GrpcServer();
        grpcServer.start();
        grpcServer.blockUntilShutdown();
    }

    private void start() throws IOException {
        this.server = ServerBuilder.forPort(9090).addService(new StudentServiceImpl()).build().start();
        System.out.println("server started");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            GrpcServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (this.server != null) {
            this.server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }

    static class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
        /**
         * 1.入参是 对象, 出参也是对象
         *
         * @param request
         * @param responseObserver
         */
        @Override
        public void queryStuById(SearchRequest request, StreamObserver<SearchResponse> responseObserver) {
            System.out.println("客户端请求参数id为: " + request.getId());
            // 当发生异常时, 回调函数为:
            // responseObserver.onError(new RuntimeException("there is an error to request by id"));
            // 当成功时, 回调函数为:
            responseObserver.onNext(SearchResponse.newBuilder().setName("tom" + request.getId()).build());
            // 当完成时, 回调函数为:
            responseObserver.onCompleted();
        }

        /**
         * 2.入参是 对象, 出参是流
         *
         * @param request
         * @param responseObserver
         */
        @Override
        public void queryStuByIdStream2Resp(SearchRequest request, StreamObserver<StuResponse> responseObserver) {
            System.out.println("客户端请求参数id为: " + request.getId());
            responseObserver.onNext(StuResponse.newBuilder().setName("stream2Resp --> jerry" + request.getId()).setAge(request.getId()).setId(request.getId()).build());
            responseObserver.onNext(StuResponse.newBuilder().setName("stream2Resp --> john" + request.getId()).setAge(request.getId()).setId(request.getId()).build());
            responseObserver.onNext(StuResponse.newBuilder().setName("stream2Resp --> trump" + request.getId()).setAge(request.getId()).setId(request.getId()).build());
            responseObserver.onCompleted();
        }

        /**
         * 3.入参是 流, 出参是对象: 异步实现
         *
         * @param responseObserver
         * @return
         */
        @Override
        public StreamObserver<SearchRequest> queryStuByIdReqByStream(StreamObserver<StuResponseList> responseObserver) {
            return new StreamObserver<SearchRequest>() {
                @Override
                public void onNext(SearchRequest value) {
                    System.out.println("onNext --> " + value.getId());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t.getMessage());
                }

                @Override
                public void onCompleted() {
                    StuResponse response1 = StuResponse.newBuilder().setId("1").setName("tommy1").setAge("10").build();
                    StuResponse response2 = StuResponse.newBuilder().setId("2").setName("tommy2").setAge("20").build();
                    StuResponseList list = StuResponseList.newBuilder().addStuResponse(response1).addStuResponse(response2).build();
                    responseObserver.onNext(list);
                    responseObserver.onCompleted();
                }
            };
        }

        /**
         * 4.入参是 流, 出参也是流: 异步实现
         *
         * @param responseObserver
         * @return
         */
        @Override
        public StreamObserver<StuRequestStream> queryStuByIdReqAndResp2Stream(StreamObserver<StuResponseStream> responseObserver) {
            return new StreamObserver<StuRequestStream>() {
                @Override
                public void onNext(StuRequestStream value) {
                    System.out.println("receive request: " + value.getReq());
                    responseObserver.onNext(StuResponseStream.newBuilder().setResp("hello" + value.getReq()).build());
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t.getMessage());
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
    }
}
