package io_benchmarks_netty;

import java.math.BigInteger;
import java.net.SocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelConfig;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;


public class HashChannel implements Channel {
    
    private MessageDigest md;
    
    public HashChannel() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFactory getFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Channel getParent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelConfig getConfig() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelPipeline getPipeline() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isBound() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isConnected() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SocketAddress getLocalAddress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SocketAddress getRemoteAddress() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture write(Object message) {
        ChannelBuffer buf = (ChannelBuffer) message;
        if (buf != ChannelBuffers.EMPTY_BUFFER) {
            md.update(buf.toByteBuffer());
        }
        return Channels.succeededFuture(this);
    }
    
    public String getHash() {
        BigInteger bigInt = new BigInteger(1,md.digest());
        String hashText = bigInt.toString(16);
        while(hashText.length() < 32 ){
            hashText = "0"+hashText;
        }
        return hashText;
    }

    @Override
    public ChannelFuture write(Object message, SocketAddress remoteAddress) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture disconnect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture unbind() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture close() {
        return Channels.succeededFuture(this);
    }

    @Override
    public ChannelFuture getCloseFuture() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getInterestOps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isReadable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isWritable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture setInterestOps(int interestOps) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChannelFuture setReadable(boolean readable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getAttachment() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAttachment(Object attachment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(Channel o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
