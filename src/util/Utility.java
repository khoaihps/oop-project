package util;

import java.io.IOException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Utility {
    public static String removeAccentsAndToLowercase(String text) {
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutAccents = pattern.matcher(normalizedText).replaceAll("");
        String replacedText = withoutAccents.replace("đ", "d").replace("Đ", "D");
        return replacedText.toLowerCase();
    }

    public static <T> ArrayList<T> loader(String filePath, TypeToken<ArrayList<T>> typeToken) {
        ArrayList<T> list = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader)) {
            // Read the JSON string from the file
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonString = jsonBuilder.toString();
    
            // Use Gson to deserialize the JSON string into an ArrayList of the specified type
            Gson gson = new GsonBuilder().create();
            list = gson.fromJson(jsonString, typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> void writer(String filePath, ArrayList<T> list) {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String json = gson.toJson(list);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
