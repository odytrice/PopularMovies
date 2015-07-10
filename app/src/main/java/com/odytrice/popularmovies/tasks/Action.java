package com.odytrice.popularmovies.tasks;

public interface Action<T>{
    void Invoke(T result);
}
