package com.nekoid.smektuber.network;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Process;

import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private final String mMethod;

    private final Map<String, String> mHeaders;

    private final Map<String, String> mBody;

    private final Map<String, File> mFile;

    private final Map<String, Bitmap> mBitmap;

    private final Map<String, Uri> mUri;

    private Response response = null;

    private Async async;

    private String charset = "UTF-8";

    private String boundary = "*R*I*Y*U*";

    private String newLine = "\r\n";

    private String twoHyphens = "--";

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

    public Async getAsync() {
        return async;
    }

    public void setAsync(Async async) {
        this.async = async;
    }

    public void setResponse(Response response) {
        this.response = response;
//        if (this.async != null) {
//            async.onResponse(response);
//        }
    }

    public Response getResponse() {
        return response;
    }

    private void setProperty(HttpURLConnection connection) throws ProtocolException {
        connection.setUseCaches(false);
        connection.setRequestMethod(mMethod);
        if (mMethod != "GET") {
            connection.setDoOutput(true);
        }
        connection.setDoInput(true);
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("User-Agent", "Kateru-riyu");
    }

    private void setHeaders(HttpURLConnection connection) {
        if (mHeaders != null) {
            for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private String getBoundary() {
        return "*****";
    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */
    public void addFormField(String name, String value, PrintWriter writer) {
        writer.append(twoHyphens + boundary).append(newLine);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(newLine);
        writer.append("Content-Type: text/plain; charset=" + charset).append(newLine);
        writer.append(newLine);
        writer.append(value).append(newLine);
        writer.flush();
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName  name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile, PrintWriter writer, OutputStream outputStream)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append(twoHyphens + getBoundary()).append(newLine);
        writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(newLine);
        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(newLine);
        writer.append("Content-Transfer-Encoding: binary").append(newLine);
        writer.append(newLine);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
        writer.append(newLine);
        writer.flush();
    }

    public void uploadFile() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
        setProperty(connection);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + getBoundary());

        setHeaders(connection);

        if (mFile != null) {
            for (Map.Entry<String, File> entry : this.mFile.entrySet()) {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()), true);

                addFilePart(entry.getKey(), entry.getValue(), writer, connection.getOutputStream());

                writer.append(newLine).flush();
                writer.append(twoHyphens + boundary + twoHyphens).append(newLine);
                writer.close();
            }
        }

//        if (mBitmap != null) {
//            for (Map.Entry<String, Bitmap> entry : mBitmap.entrySet()) {
//                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + getBoundary());
//                connection.setRequestProperty("Content-disposition",
//                        "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + entry.getValue().toString() + "\"");
//                connection.setRequestProperty("Content-Length", String.valueOf(entry.getValue().getByteCount()));
//                connection.getOutputStream().write(entry.getValue().toString().getBytes());
//                isContentType = true;
//            }
//        }
//
//        if (mUri != null) {
//            for (Map.Entry<String, Uri> entry : this.mUri.entrySet()) {
//                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + getBoundary());
//
//                File file = new File(entry.getValue().toString());
//                String fileName = file.getName();
//                String mimeType = URLConnection.guessContentTypeFromName(fileName);
//
//                connection.setRequestProperty("Content-Type", mimeType);
//                connection.setRequestProperty("Content-Disposition",
//                        "form-data; name=\"" + entry.getKey() + "\"; filename=\"" + fileName + "\"");
//
//                try (InputStream fileStream = new FileInputStream(file);
//                        OutputStream outputStream = connection.getOutputStream()) {
//
//                    byte[] buffer = new byte[4096];
//                    int bytesRead = -1;
//
//                    while ((bytesRead = fileStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//
//                    outputStream.flush();
//                }
//            }
//        }



        try {
            connection.connect();
            this.setResponse(new Response(connection));
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

        try {
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

            connection.connect();
            this.setResponse(new Response(connection));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }
}
