package com.example;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static <T> ArrayList<T> read_json(String FILE_PATH, Class<T> clazz) {
    try (FileReader reader = new FileReader(FILE_PATH)) {
        Type listType = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        ArrayList<T> list = gson.fromJson(reader, listType);
        return (list != null) ? list : new ArrayList<>();
    } catch (IOException e) {
        return new ArrayList<>(); 
    }
}

}
