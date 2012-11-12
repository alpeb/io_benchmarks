package io_benchmarks_oio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Handler implements Runnable {
    
    //private static Queue<OutputStream> outDestQueue = new ConcurrentLinkedQueue<>();
    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        System.setProperty("line.separator", "\r\n");
        
        try {
            InputStream in = socket.getInputStream();
            String req = getLine(in);
            if (req.length() < 38) {
                throw new RuntimeException("Bad request: " + req);
            }
            String md5 = req.substring(6, 38);
            while ((req = getLine(in)).indexOf("content-length") == -1);
            long totalBytes = Long.parseLong(req.substring(16));

            // get to the body payload
            while (!getLine(in).equals(""));

            int bytesRead;
            long totalBytesRead = 0;
            HashOutputStream outDest = new HashOutputStream();
            OutputStream outSrc = socket.getOutputStream();
            PrintWriter outSrcWriter = new PrintWriter(
                        new OutputStreamWriter(outSrc, "8859_1"), true);
            byte[] buffer = new byte[8 * 1024];
            try {
                // ab doesn't close the connection it seems, so gotta count the bytes to do it myself
                while (totalBytesRead < totalBytes) {
                    bytesRead = in.read(buffer);
                    outDest.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                if (outDest.getHash().equals(md5)) {
                    outSrcWriter.println("HTTP/1.0 200 OK");
                } else {
                    outSrcWriter.println("HTTP/1.0 500 Internal Error");
                }
                outSrcWriter.println("Content-Length: 0");
                outSrcWriter.println("");
                outSrcWriter.close();
                outSrc.close();
                outDest.close();
                in.close();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    private String getLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int intch;
        while ((intch = in.read()) != -1) {
            char c = (char) intch;
            if (c != '\n') {
                sb.append(c);
            } else {
                break;
            }
        }
        return sb.toString().toLowerCase().trim();
    }
}
