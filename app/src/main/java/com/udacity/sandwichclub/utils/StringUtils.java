package com.udacity.sandwichclub.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by pooya on 01/03/2018.
 */

public final class StringUtils {

    public static String join(Collection<String> elements) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = elements.iterator();
        boolean hasNext = it.hasNext();
        while (hasNext) {
            sb.append(it.next());
            hasNext = it.hasNext();
            if (hasNext) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
