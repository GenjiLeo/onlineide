# Protocol Buffers 

This project is supposed to contain your Protocol buffer message types for the distributed chat system.

## Setup `protoc`

To generate sources, you will need to install `protoc` as described [here](https://developers.google.com/protocol-buffers/docs/downloads.html).


## Compiling Protocol Buffers

```bash
$ protoc --java_out=DST_DIR --python_out=DST_DIR chat.proto
```

Where `DST_DIR` is your target directory, where sources should be generated.