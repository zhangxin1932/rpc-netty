package com.zy.grpc.proto.server.service;

import com.zy.grpc.proto.server.message.*;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * 这里的出入参 --&gt; 必须是 message, 不能单独用 int32, string 等基本类型
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.2)",
    comments = "Source: src/main/resources/proto/Stu.proto")
public final class StudentServiceGrpc {

  private StudentServiceGrpc() {}

  public static final String SERVICE_NAME = "StudentService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<SearchRequest,
          SearchResponse> getQueryStuByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryStuById",
      requestType = SearchRequest.class,
      responseType = SearchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<SearchRequest,
          SearchResponse> getQueryStuByIdMethod() {
    io.grpc.MethodDescriptor<SearchRequest, SearchResponse> getQueryStuByIdMethod;
    if ((getQueryStuByIdMethod = StudentServiceGrpc.getQueryStuByIdMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getQueryStuByIdMethod = StudentServiceGrpc.getQueryStuByIdMethod) == null) {
          StudentServiceGrpc.getQueryStuByIdMethod = getQueryStuByIdMethod =
              io.grpc.MethodDescriptor.<SearchRequest, SearchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryStuById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SearchResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("QueryStuById"))
              .build();
        }
      }
    }
    return getQueryStuByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<SearchRequest,
          StuResponse> getQueryStuByIdStream2RespMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryStuByIdStream2Resp",
      requestType = SearchRequest.class,
      responseType = StuResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<SearchRequest,
          StuResponse> getQueryStuByIdStream2RespMethod() {
    io.grpc.MethodDescriptor<SearchRequest, StuResponse> getQueryStuByIdStream2RespMethod;
    if ((getQueryStuByIdStream2RespMethod = StudentServiceGrpc.getQueryStuByIdStream2RespMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getQueryStuByIdStream2RespMethod = StudentServiceGrpc.getQueryStuByIdStream2RespMethod) == null) {
          StudentServiceGrpc.getQueryStuByIdStream2RespMethod = getQueryStuByIdStream2RespMethod =
              io.grpc.MethodDescriptor.<SearchRequest, StuResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryStuByIdStream2Resp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StuResponse.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("QueryStuByIdStream2Resp"))
              .build();
        }
      }
    }
    return getQueryStuByIdStream2RespMethod;
  }

  private static volatile io.grpc.MethodDescriptor<SearchRequest,
          StuResponseList> getQueryStuByIdReqByStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryStuByIdReqByStream",
      requestType = SearchRequest.class,
      responseType = StuResponseList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<SearchRequest,
          StuResponseList> getQueryStuByIdReqByStreamMethod() {
    io.grpc.MethodDescriptor<SearchRequest, StuResponseList> getQueryStuByIdReqByStreamMethod;
    if ((getQueryStuByIdReqByStreamMethod = StudentServiceGrpc.getQueryStuByIdReqByStreamMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getQueryStuByIdReqByStreamMethod = StudentServiceGrpc.getQueryStuByIdReqByStreamMethod) == null) {
          StudentServiceGrpc.getQueryStuByIdReqByStreamMethod = getQueryStuByIdReqByStreamMethod =
              io.grpc.MethodDescriptor.<SearchRequest, StuResponseList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryStuByIdReqByStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StuResponseList.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("QueryStuByIdReqByStream"))
              .build();
        }
      }
    }
    return getQueryStuByIdReqByStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<StuRequestStream,
          StuResponseStream> getQueryStuByIdReqAndResp2StreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryStuByIdReqAndResp2Stream",
      requestType = StuRequestStream.class,
      responseType = StuResponseStream.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<StuRequestStream,
          StuResponseStream> getQueryStuByIdReqAndResp2StreamMethod() {
    io.grpc.MethodDescriptor<StuRequestStream, StuResponseStream> getQueryStuByIdReqAndResp2StreamMethod;
    if ((getQueryStuByIdReqAndResp2StreamMethod = StudentServiceGrpc.getQueryStuByIdReqAndResp2StreamMethod) == null) {
      synchronized (StudentServiceGrpc.class) {
        if ((getQueryStuByIdReqAndResp2StreamMethod = StudentServiceGrpc.getQueryStuByIdReqAndResp2StreamMethod) == null) {
          StudentServiceGrpc.getQueryStuByIdReqAndResp2StreamMethod = getQueryStuByIdReqAndResp2StreamMethod =
              io.grpc.MethodDescriptor.<StuRequestStream, StuResponseStream>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryStuByIdReqAndResp2Stream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StuRequestStream.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  StuResponseStream.getDefaultInstance()))
              .setSchemaDescriptor(new StudentServiceMethodDescriptorSupplier("QueryStuByIdReqAndResp2Stream"))
              .build();
        }
      }
    }
    return getQueryStuByIdReqAndResp2StreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StudentServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentServiceStub>() {
        @java.lang.Override
        public StudentServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentServiceStub(channel, callOptions);
        }
      };
    return StudentServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StudentServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentServiceBlockingStub>() {
        @java.lang.Override
        public StudentServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentServiceBlockingStub(channel, callOptions);
        }
      };
    return StudentServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StudentServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StudentServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StudentServiceFutureStub>() {
        @java.lang.Override
        public StudentServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StudentServiceFutureStub(channel, callOptions);
        }
      };
    return StudentServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 这里的出入参 --&gt; 必须是 message, 不能单独用 int32, string 等基本类型
   * </pre>
   */
  public static abstract class StudentServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * 1.入参是 对象, 出参也是对象
     * </pre>
     */
    public void queryStuById(SearchRequest request,
                             io.grpc.stub.StreamObserver<SearchResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryStuByIdMethod(), responseObserver);
    }

    /**
     * <pre>
     * 2.入参是 对象, 出参是流
     * </pre>
     */
    public void queryStuByIdStream2Resp(SearchRequest request,
                                        io.grpc.stub.StreamObserver<StuResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryStuByIdStream2RespMethod(), responseObserver);
    }

    /**
     * <pre>
     * 3.入参是 流, 出参是对象: 异步实现
     * </pre>
     */
    public io.grpc.stub.StreamObserver<SearchRequest> queryStuByIdReqByStream(
        io.grpc.stub.StreamObserver<StuResponseList> responseObserver) {
      return asyncUnimplementedStreamingCall(getQueryStuByIdReqByStreamMethod(), responseObserver);
    }

    /**
     * <pre>
     * 4.入参是 流, 出参也是流: 异步实现
     * </pre>
     */
    public io.grpc.stub.StreamObserver<StuRequestStream> queryStuByIdReqAndResp2Stream(
        io.grpc.stub.StreamObserver<StuResponseStream> responseObserver) {
      return asyncUnimplementedStreamingCall(getQueryStuByIdReqAndResp2StreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getQueryStuByIdMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                      SearchRequest,
                      SearchResponse>(
                  this, METHODID_QUERY_STU_BY_ID)))
          .addMethod(
            getQueryStuByIdStream2RespMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                      SearchRequest,
                      StuResponse>(
                  this, METHODID_QUERY_STU_BY_ID_STREAM2RESP)))
          .addMethod(
            getQueryStuByIdReqByStreamMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                      SearchRequest,
                      StuResponseList>(
                  this, METHODID_QUERY_STU_BY_ID_REQ_BY_STREAM)))
          .addMethod(
            getQueryStuByIdReqAndResp2StreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                      StuRequestStream,
                      StuResponseStream>(
                  this, METHODID_QUERY_STU_BY_ID_REQ_AND_RESP2STREAM)))
          .build();
    }
  }

  /**
   * <pre>
   * 这里的出入参 --&gt; 必须是 message, 不能单独用 int32, string 等基本类型
   * </pre>
   */
  public static final class StudentServiceStub extends io.grpc.stub.AbstractAsyncStub<StudentServiceStub> {
    private StudentServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 1.入参是 对象, 出参也是对象
     * </pre>
     */
    public void queryStuById(SearchRequest request,
                             io.grpc.stub.StreamObserver<SearchResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryStuByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 2.入参是 对象, 出参是流
     * </pre>
     */
    public void queryStuByIdStream2Resp(SearchRequest request,
                                        io.grpc.stub.StreamObserver<StuResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getQueryStuByIdStream2RespMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 3.入参是 流, 出参是对象: 异步实现
     * </pre>
     */
    public io.grpc.stub.StreamObserver<SearchRequest> queryStuByIdReqByStream(
        io.grpc.stub.StreamObserver<StuResponseList> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getQueryStuByIdReqByStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * 4.入参是 流, 出参也是流: 异步实现
     * </pre>
     */
    public io.grpc.stub.StreamObserver<StuRequestStream> queryStuByIdReqAndResp2Stream(
        io.grpc.stub.StreamObserver<StuResponseStream> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getQueryStuByIdReqAndResp2StreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * 这里的出入参 --&gt; 必须是 message, 不能单独用 int32, string 等基本类型
   * </pre>
   */
  public static final class StudentServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<StudentServiceBlockingStub> {
    private StudentServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 1.入参是 对象, 出参也是对象
     * </pre>
     */
    public SearchResponse queryStuById(SearchRequest request) {
      return blockingUnaryCall(
          getChannel(), getQueryStuByIdMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * 2.入参是 对象, 出参是流
     * </pre>
     */
    public java.util.Iterator<StuResponse> queryStuByIdStream2Resp(
        SearchRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getQueryStuByIdStream2RespMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * 这里的出入参 --&gt; 必须是 message, 不能单独用 int32, string 等基本类型
   * </pre>
   */
  public static final class StudentServiceFutureStub extends io.grpc.stub.AbstractFutureStub<StudentServiceFutureStub> {
    private StudentServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StudentServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StudentServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 1.入参是 对象, 出参也是对象
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<SearchResponse> queryStuById(
        SearchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryStuByIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_STU_BY_ID = 0;
  private static final int METHODID_QUERY_STU_BY_ID_STREAM2RESP = 1;
  private static final int METHODID_QUERY_STU_BY_ID_REQ_BY_STREAM = 2;
  private static final int METHODID_QUERY_STU_BY_ID_REQ_AND_RESP2STREAM = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StudentServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StudentServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_STU_BY_ID:
          serviceImpl.queryStuById((SearchRequest) request,
              (io.grpc.stub.StreamObserver<SearchResponse>) responseObserver);
          break;
        case METHODID_QUERY_STU_BY_ID_STREAM2RESP:
          serviceImpl.queryStuByIdStream2Resp((SearchRequest) request,
              (io.grpc.stub.StreamObserver<StuResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_STU_BY_ID_REQ_BY_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.queryStuByIdReqByStream(
              (io.grpc.stub.StreamObserver<StuResponseList>) responseObserver);
        case METHODID_QUERY_STU_BY_ID_REQ_AND_RESP2STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.queryStuByIdReqAndResp2Stream(
              (io.grpc.stub.StreamObserver<StuResponseStream>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StudentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StudentServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Student.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StudentService");
    }
  }

  private static final class StudentServiceFileDescriptorSupplier
      extends StudentServiceBaseDescriptorSupplier {
    StudentServiceFileDescriptorSupplier() {}
  }

  private static final class StudentServiceMethodDescriptorSupplier
      extends StudentServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StudentServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StudentServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StudentServiceFileDescriptorSupplier())
              .addMethod(getQueryStuByIdMethod())
              .addMethod(getQueryStuByIdStream2RespMethod())
              .addMethod(getQueryStuByIdReqByStreamMethod())
              .addMethod(getQueryStuByIdReqAndResp2StreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
