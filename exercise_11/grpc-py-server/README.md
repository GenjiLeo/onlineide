# gRPC Python Server + Sample Client

A simple Python (3.5+) gRPC server and sample client for distributed chat system.

## Setup

```bash
$ pip3 install grpcio grpcio-tools
```

## Generate Protobuf/gRPC sources

```bash
$ python3 -m grpc_tools.protoc -I ../chat-proto/ --python_out=. --grpc_python_out=. ../chat-proto/chat.proto
```

## Run Server

```bash
$ python3 server.py
```

## Run Client

```bash
$ python3 client.py
```