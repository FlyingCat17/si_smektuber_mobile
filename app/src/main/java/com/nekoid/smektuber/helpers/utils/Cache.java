package com.nekoid.smektuber.helpers.utils;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * <p>Caching data to local storage</p>
 *
 * <p>Created by Kateru Riyu</p>
 */
public class Cache {

    /**
     * <p>Cache file</p>
     */
    private File cache;

    /**
     * <p>Constructor</p>
     *
     * <p>We need to pass cache file to this class</p>
     *
     * @param cache Cache file
     */
    public Cache(File cache) {
        this.cache = cache;
    }

    /**
     * <p>Add data to cache</p>
     *
     * @param key   Key of data
     * @param value Value of data
     * @return true if successfully added, false otherwise
     */
    public boolean add(String key, Object value) {
        try {
            // Read existing cache data
            FileInputStream stream = new FileInputStream(cache);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String buffer = removeUnnecessary(stringBuilder.toString());
            if (buffer.isEmpty()) {
                buffer = "{}";
            }
            JSONObject json = new JSONObject(buffer);

            // Add new data to cache
            json.put(key, value);

            // Write new cache data
            FileOutputStream streamOut = new FileOutputStream(cache);
            OutputStreamWriter writer = new OutputStreamWriter(streamOut);
            writer.write(json.toString());
            writer.close();
            streamOut.close();
            return true;
        } catch (IOException | JSONException ignored) {
            return false;
        }
    }

    /**
     * <p>Remove all data from cache</p>
     *
     * @return true if successfully removed, false otherwise
     */
    public boolean removeAll() {
        try {
            // Clear cache file
            FileOutputStream streamOut = new FileOutputStream(cache);
            OutputStreamWriter writer = new OutputStreamWriter(streamOut);
            writer.write("{}");
            writer.close();
            streamOut.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    /**
     * <p>Remove data with specific key from cache</p>
     *
     * @param key Key of data
     * @return true if successfully removed, false otherwise
     */
    public boolean remove(String key) {
        try {
            // Read existing cache data
            FileInputStream stream = new FileInputStream(cache);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String buffer = removeUnnecessary(stringBuilder.toString());
            if (buffer.isEmpty()) {
                buffer = "{}";
            }
            JSONObject json = new JSONObject(buffer);

            // Remove data with specific key from cache
            json.remove(key);

            // Write new cache data
            FileOutputStream streamOut = new FileOutputStream(cache);
            OutputStreamWriter writer = new OutputStreamWriter(streamOut);
            writer.write(json.toString());
            writer.close();
            streamOut.close();
            return true;
        } catch (IOException | JSONException ignored) {
            return false;
        }
    }

    /**
     * <p>Get data from cache</p>
     *
     * @param key Key of data
     * @return Value of data
     */
    @Nullable
    public Object get(String key) {
        try {
            FileInputStream stream = new FileInputStream(cache);
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return bufferToObject(stringBuilder, key);
        } catch (IOException ignored) {
            return null;
        }
    }

    /**
     * <p>Convert buffer to object</p>
     *
     * @param stringBuilder Buffer
     * @param key           Key of data
     * @return Value of data
     */
    @Nullable
    private Object bufferToObject(StringBuilder stringBuilder, String key) {
        String buffer = removeUnnecessary(stringBuilder.toString());
        String[] pairs = buffer.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue[0].equals(key)) {
                return keyValue[1];
            }
        }
        return null;
    }

    /**
     * <p>Remove unnecessary characters from buffer</p>
     *
     * @param buffer Buffer
     * @return Buffer without unnecessary characters
     */
    private String removeUnnecessary(String buffer) {
        buffer = buffer.replace("[", "");
        buffer = buffer.replace("]", "");
        buffer = buffer.replace("{", "");
        buffer = buffer.replace("}", "");
        buffer = buffer.replace("\"", "");
        buffer = buffer.replace(" ", "");
        return buffer;
    }
}
