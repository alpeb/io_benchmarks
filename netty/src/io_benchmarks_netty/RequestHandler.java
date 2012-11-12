package io_benchmarks_netty;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;


class RequestHandler extends SimpleChannelUpstreamHandler {

    private HashChannel clientChannel = new HashChannel();
    private boolean readingChunks = false;
    private String md5;
    
    @Override
    public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        if (!readingChunks) {
            HttpRequest request = (HttpRequest) e.getMessage();
            md5 = request.getUri().substring(1);
            if (request.isChunked()) {
                readingChunks = true;
            } else {
                throw new RuntimeException("File is too small, try with something bigger!");
            }
        } else {
            HttpChunk chunk = (HttpChunk) e.getMessage();
            
            ChannelFuture future = clientChannel.write(chunk.getContent());
            
            if (chunk.isLast()) {
                future.addListener(ChannelFutureListener.CLOSE);
                if (clientChannel.getHash().equals(md5)) {
                    e.getChannel().write(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK))
                        .addListener(ChannelFutureListener.CLOSE);
                } else {
                    e.getChannel().write(new DefaultHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.INTERNAL_SERVER_ERROR))
                        .addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        throw new RuntimeException(e.getCause());
    }
}
