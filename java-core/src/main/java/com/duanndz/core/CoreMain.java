package com.duanndz.core;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CoreMain {

    public static void main(String[] args) {
        String ids = "S0152263983260,S0152263983260,S0152263983260,S0152263983260,S0152263983260,2";
        Set<String> subNumSet = Arrays.stream(ids.split(","))
            .filter(s -> s != null && !s.trim().isEmpty())
            .collect(Collectors.toSet());
        if (subNumSet.size() != 1) {
            System.out.println("Invalid, many id");
        } else {
            System.out.println(subNumSet.iterator().next());
        }
    }

}
