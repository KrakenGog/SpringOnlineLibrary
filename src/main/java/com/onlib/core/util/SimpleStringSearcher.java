package com.onlib.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;


public class SimpleStringSearcher extends StringSearcher {

    @Override
    public <T> List<T> Search(Iterable<T> list, String query, Function<T, String> getString) {
        List<T> result = new LinkedList<>();
         
        for(var item : list){
            String s =  getString.apply(item);
            if(s.contains(query))
                result.add(item);
        }

        return result;
    }
    
}
