package com.wp.finki.ukim.mk.catalogware.utils;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Borce on 01.09.2016.
 */
public class SetUtils {

    public static  <E> boolean equals(Set<E> set1, Set<E> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }
        for (E next1 : set1) {
            boolean found = false;
            for (E next2 : set2) {
                if (next1.equals(next2)) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
