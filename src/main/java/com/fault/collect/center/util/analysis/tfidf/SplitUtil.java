package com.fault.collect.center.util.analysis.tfidf;

import java.util.Arrays;
import java.util.List;

public class SplitUtil {
    public static List<String> split(String content) {
        return Arrays.asList(content.split(" "));
    }
}
