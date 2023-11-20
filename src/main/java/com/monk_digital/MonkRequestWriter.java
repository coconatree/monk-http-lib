package com.monk_digital;


import java.io.OutputStream;
import java.io.PrintWriter;

/**
    @author Emre Caniklioglu
 */

class MonkRequestWriter {

    protected static PrintWriter getHeadRequestWriter(
            OutputStream out,
            String host,
            String path
    )
    {
        PrintWriter writer = new PrintWriter(out);

        writer.print(String.format("HEAD /%s HTTP/1.1\r\n", path));
        writer.print(String.format("Host: %s\r\n", host));
        writer.print("User-Agent: Console Http Client\r\n");
        writer.print("Accept: text/html\r\n");
        writer.print("Accept-Language: en-US\r\n");
        writer.print("Connection: close\r\n");
        writer.print("\r\n");

        return writer;
    }

    protected static PrintWriter getRangeRequestWriter(
            OutputStream out,
            String host,
            String path,
            long   lowerBound,
            long   upperBound
    )
    {
        PrintWriter writer = new PrintWriter(out);

        writer.print(String.format("GET /%s HTTP/1.1\r\n", path));
        writer.print(String.format("Host: %s\r\n", host));
        writer.print(String.format("Range: bytes=%s-%s\r\n", lowerBound, upperBound));
        writer.print("\r\n");

        return writer;
    }

    protected static PrintWriter getRequestWriter(
            OutputStream out,
            String host,
            String path
    )
    {
        PrintWriter writer = new PrintWriter(out);

        writer.print(String.format("GET /%s HTTP/1.1\r\n", path));
        writer.print(String.format("Host: %s\r\n", host));
        writer.print("\r\n");

        return writer;
    }
}
