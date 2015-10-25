package com.photoviewer.domain.mapper;

import java.util.Collection;

public interface LayerDataTransformer<F, T> {
    T transform(F from);
    Collection<T> transform(Collection<F> from);
}
