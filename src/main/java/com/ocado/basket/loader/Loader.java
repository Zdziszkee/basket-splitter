package com.ocado.basket.loader;

import com.ocado.basket.exception.LoaderException;

public interface Loader<RESOURCE,SOURCE> {

    RESOURCE load(SOURCE source) throws LoaderException;
}
