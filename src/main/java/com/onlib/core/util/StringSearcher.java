package com.onlib.core.util;

import java.util.List;
import java.util.function.Function;


public abstract class StringSearcher {

    public abstract <T> List<T> Search(Iterable<T> list, String query, Function<T,String> getString);    
}
