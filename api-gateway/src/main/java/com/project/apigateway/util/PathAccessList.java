package com.project.apigateway.util;

import java.util.List;


public class PathAccessList {
    private static final List<String> publicPaths = List.of("/auth/login");

    public static boolean isPathOnPublicPaths(String path) {
        return publicPaths.stream().anyMatch(path::startsWith);
    }
}