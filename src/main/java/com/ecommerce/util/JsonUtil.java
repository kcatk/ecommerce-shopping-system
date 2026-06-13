package com.ecommerce.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public final class JsonUtil {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtil() {
    }

    public static <T> List<T> readList(Path path, TypeToken<List<T>> token) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.writeString(path, "[]", StandardCharsets.UTF_8);
            }
            String json = Files.readString(path, StandardCharsets.UTF_8);
            Type type = token.getType();
            List<T> value = GSON.fromJson(json, type);
            return value == null ? Collections.emptyList() : value;
        } catch (IOException e) {
            throw new IllegalStateException("读取 JSON 文件失败: " + path, e);
        }
    }

    public static void writeObject(Path path, Object value) {
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, GSON.toJson(value), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("写入 JSON 文件失败: " + path, e);
        }
    }
}
