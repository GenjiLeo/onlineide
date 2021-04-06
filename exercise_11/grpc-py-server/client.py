import sys
import threading
import uuid
from time import sleep

import grpc
from google.protobuf.empty_pb2 import Empty

import chat_pb2
import chat_pb2_grpc


def main():
    with grpc.insecure_channel('127.0.0.1:8080') as channel:
        stub = chat_pb2_grpc.ChatServiceStub(channel)

        def _listen_for_messages():
            # will wait for new messages from the server
            for message in stub.StreamMessages(Empty()):
                print("{} ({} - {}): {}".format(message.sender, message.id, message.createdAt.ToDatetime(),
                                                message.content))

        # create new listening thread for when new message streams come in
        threading.Thread(target=_listen_for_messages, daemon=True).start()

        # generate client ID
        sender = "Client<{}>".format(uuid.uuid4().__str__()[:4])

        # main loop
        while 1:
            # send a ping every 5 secs
            sleep(5)
            stub.SendMessage(chat_pb2.ChatMessage(content="Ping...", sender=sender))


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Exiting by user request.')
        sys.exit(0)
