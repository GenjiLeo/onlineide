from concurrent import futures
from typing import List

import chat_pb2
import chat_pb2_grpc
import grpc
from google.protobuf.empty_pb2 import Empty
from google.protobuf.timestamp_pb2 import Timestamp


class ChatServiceServicer(chat_pb2_grpc.ChatServiceServicer):
    # static counter for IDs
    counter = 0

    def __init__(self) -> None:
        # list of all chat messages
        self.chat_messages: List[chat_pb2.ChatMessageResponse] = []

    def StreamMessages(self, request, context):
        # for every client a infinite loop starts (in gRPC's own managed thread)
        # streams are implemented as generators (`yield` items instead of list) in python
        # TODO: add logic to yield new chat messages to every client
        pass

    def SendMessage(self, request, context):
        # increment counter and get current timestamp
        self.counter += 1
        now = Timestamp()
        now.GetCurrentTime()  # after this `now` contains a Python datetime
        # TODO: create chat message response with ID and timestamp (`createdAt`) and add to list
        return Empty()


def serve():
    # setup gRPC server
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))

    # setup server stub
    chat_pb2_grpc.add_ChatServiceServicer_to_server(
        ChatServiceServicer(), server)

    # start server
    server.add_insecure_port('127.0.0.1:8080')
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    serve()
