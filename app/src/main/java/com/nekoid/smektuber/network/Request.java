package com.nekoid.smektuber.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class Request {

    private final URL mUrl;

    private String mMethod;

    private Map<String, String> mHeaders;

    private Map<String, String> mBody;

    private Map<String, File> mFile;

    private Map<String, Bitmap> mBitmap;

    private Map<String, Uri> mUri;

    private Response response = null;

//    private Async async;

    private final String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

    private final String newLine = "\r\n";

    private final String twoHyphens = "--";

    private Bitmap imageBitmap;

    /**
     * Constructor for the Request.
     *
     * @param u the URL
     */
    public Request(URL u, String method, Map<String, String> headers, Map<String, String> body,
                   @Nullable Map<String, File> file, @Nullable Map<String, Bitmap> bitmap, @Nullable Map<String, Uri> uri) {
        mUrl = u;
        mMethod = method;
        mHeaders = headers;
        mBody = body;
        mFile = file;
        mBitmap = bitmap;
        mUri = uri;
    }

    public Request(URL mUrl) {
        this.mUrl = mUrl;
    }

    public void setResponse(HttpURLConnection connection) throws IOException {
        connection.connect();
        response = new Response(connection);
    }

    public Response getResponse() {
        return response;
    }

    private void setProperty(HttpURLConnection connection) throws ProtocolException {
        connection.setUseCaches(false);
        connection.setRequestMethod(mMethod);
        if (!mMethod.equals("GET")) {
            connection.setDoOutput(true);
        }
        connection.setDoInput(true);
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("User-Agent", "Kateru-Riyu");
    }

    private void setHeaders(HttpURLConnection connection) {
        if (mHeaders != null) {
            for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addFilePart(String fieldName, File uploadFile, PrintWriter writer, OutputStream outputStream) throws IOException {
        if (uploadFile == null) {
            writer.append(twoHyphens + boundary).append(newLine);
            writer.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"").append(newLine);
            writer.append("Content-Type: text/plain; charset=UTF-8").append(newLine);
            writer.append(newLine);
            writer.flush();
            return;
        }

        String fileName = uploadFile.getName();
        writer.append(twoHyphens + boundary).append(newLine);
        writer.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(fileName).append("\"")
                .append(newLine);
        writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName)).append(newLine);
        writer.append("Content-Transfer-Encoding: binary").append(newLine);
        writer.append(newLine);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(newLine);
        writer.flush();
    }

    public void uploadFile() throws IOException {
        // open connection
        HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();

        // set property to connection
        setProperty(connection);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        // set header to connection
        setHeaders(connection);

        PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()), true);
        OutputStream outputStream = connection.getOutputStream();

        if (mFile != null) {
            for (Map.Entry<String, File> entry : this.mFile.entrySet()) {
                addFilePart(entry.getKey(), entry.getValue(), writer, outputStream);
            }
            writer.append(newLine).flush();
            writer.append(twoHyphens + boundary).append(newLine);
            writer.close();
            outputStream.close();
        }

        try {
            this.setResponse(connection);
        } finally {
            connection.disconnect();
        }
    }

    public void connect() throws IOException {
        if (this.mFile != null || this.mBitmap != null || this.mUri != null) {
            uploadFile();
            return;
        }

        HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        setProperty(connection);

            setHeaders(connection);

            if (this.mBody != null) {
                StringBuilder data = new StringBuilder();
                for (Map.Entry<String, String> entry : this.mBody.entrySet()) {
                    data.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                byte[] dataBytes = data.toString().getBytes();
                connection.setRequestProperty("Content-Length", String.valueOf(dataBytes.length));
                connection.getOutputStream().write(dataBytes);
            }

        try {
            this.setResponse(connection);
        } finally {
            connection.disconnect();
        }
    }

    public void loadImage() throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            imageBitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
}
