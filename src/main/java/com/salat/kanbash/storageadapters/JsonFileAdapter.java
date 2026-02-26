package com.salat.kanbash.storageadapters;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

public abstract class JsonFileAdapter<T> implements StorageAdapter {
    private final String fileName;
    protected final Class<T> dataClass;
    private final Supplier<T> dataSupplier;

    protected File file;
    protected T data;

    public JsonFileAdapter(
            String fileName,
            Class<T> dataClass,
            Supplier<T> dataSupplier
    ) {
        this.fileName = fileName;
        this.dataClass = dataClass;
        this.dataSupplier = dataSupplier;
    }

    @Override
    public void load() {
        try {
            file = new File(fileName);
            file.createNewFile(); // internally checks if file already exists

            FileReader fileReader = new FileReader(file);
            data = new Gson().fromJson(fileReader, dataClass);
            fileReader.close();

            if (data == null) {
                data = dataSupplier.get();
                store();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store() {
        String jsonString = new Gson().toJson(data);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
