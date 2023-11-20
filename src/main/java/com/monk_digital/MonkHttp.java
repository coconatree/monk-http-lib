package com.monk_digital;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 @author Emre Caniklioglu
 */

public class MonkHttp {
    public static List<String> get(
            String host,
            int    port,
            String path
    )
            throws IOException, InterruptedException {
        // Initializing the socket
        Socket socket = createSocket(host, port);
        OutputStream out = socket.getOutputStream();

        // Sending the required protocol texts
        PrintWriter writer = MonkRequestWriter.getRequestWriter(
                out,
                host,
                path
        );
        writer.flush();

        InputStream input = socket.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

        char[] data = new char[1024];
        buffer.read(data);

        List<String> response = Arrays
                .stream(new String(data).split("\n"))
                .map(MonkHttp::clearNullCharacters)
                .toList();

        // Cleaning up
        buffer.close();
        input.close();
        out.close();
        socket.close();

        return response;
    }

    public static List<String> head(
            String host,
            int    port,
            String path
    )
        throws IOException
    {
        // Initializing the socket
        Socket socket = createSocket(host, port);
        OutputStream out = socket.getOutputStream();

        // Sending the required protocol texts
        PrintWriter writer = MonkRequestWriter.getHeadRequestWriter(
                out,
                host,
                path
        );
        writer.flush();

        InputStream input = socket.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

        char[] data = new char[1024];
        buffer.read(data);

        List<String> response = Arrays
                .stream(new String(data).split("\n"))
                .map(MonkHttp::clearNullCharacters)
                .toList();

        // Cleaning up
        input.close();
        out.close();
        socket.close();

        return response;
    }

    public static List<String> range(
            String  host,
            int     port,
            String  path,
            long    lowerBound,
            long    upperBound
    )
            throws IOException
    {
        // Initializing the socket
        Socket socket = createSocket(host, port);
        OutputStream out = socket.getOutputStream();

        // Sending the required protocol texts
        PrintWriter writer = MonkRequestWriter.getRangeRequestWriter(
                out,
                host,
                path,
                lowerBound,
                upperBound
        );
        writer.flush();

        // Getting the input stream and creating the buffer
        InputStream input = socket.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

        char[] data = new char[1024];
        buffer.read(data);

        List<String> response = Arrays
                .stream(new String(data).split("\n"))
                .map(MonkHttp::clearNullCharacters)
                .toList();

        // Cleaning up
        input.close();
        out.close();
        socket.close();

        return response;
    }

    private static Socket createSocket(String host, int port) throws IOException {
        return new Socket(InetAddress.getByName(host), port);
    }

    private static String clearNullCharacters(String data) {
        return data.replaceAll("\u0000", "");
    }
}

