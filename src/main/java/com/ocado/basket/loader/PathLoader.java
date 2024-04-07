package com.ocado.basket.loader;

import com.ocado.basket.exception.LoaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathLoader implements Loader<String, Path> {


    @Override
    public String load(Path path) throws LoaderException {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new LoaderException("Could not load path!",e);
        }
    }
}
