package com.ocado.basket.loader;

import com.ocado.basket.exception.LoaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class PathLoaderTest {

    private final Loader<String, Path> loader = new PathLoader();

    @Test
    void notExistingPathText() {
        Assertions.assertThrows(LoaderException.class, () -> loader.load(Path.of("notexistingpath")));
    }
}