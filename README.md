Benchmarking file uploading to a web server
===========================================

For few concurrent users uploading files, it's clear the bottleneck is IO; the server's NIC bandwidth is distributed among the users bandwidth set by their ISPs.
For lots of concurrent users, synchronization, context switching and event handling start coming into play, so that IO stops being the bottleneck and the full server bandwidth is no longer available to its users.

*This non statistically-correct micro benchmark is an attempt to determine at what concurrency level this starts to happen*

The tests consists on concurrently uploading a file through POST requests using Apache Bench.
The server-side program being benchmarked pipes the bytes received into a class that progressively computes the file's MD5 in order to verify the upload.

Three minimal server implementations in Java are looked at:

### Plain Old Java IO Streams ###
Each request is processed on a separate thread spawned by Executors.newCachedThreadPool().

### NIO through Netty ###
Using Netty's default async NioServerSocketChannelFactory. In the pipeline I'm using an HttpRequestDecoder and an HttpResponseDecoder.

### NIO through Vert.x (using Java and just one event loop) ###
Vert.x uses Netty behind the hood, adding some nice abstractions.

Requirements
-----------------------
- JDK7
- ant
- ab
- vert.x http://vertx.io/downloads.html

If you're in Mac OS X, note that ab is misbehaving in Lion and Mountain Lion, so you need to patch it.
See http://simon.heimlicher.com/articles/2012/07/08/fix-apache-bench-ab-on-os-x-lion


How to compile
--------------
Just run "ant".


How to run the tests
--------------------

1. Launch the server-side program. Replace ```oio``` with ```netty``` or ```vertx``` to test the other methods.
```
cd oio
./run
```
2. Run the ```clients``` script. ```file``` denotes the file being posted, ```n``` denotes the concurrency level. This will run for 60 seconds:
```
./clients file n
```
