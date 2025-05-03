package com.project.apigateway.util;

import java.util.List;

/**
 * Utility class that defines public (unauthenticated) API paths.
 */
public class PathAccessList {

    /**
     * List of public paths that can be accessed without authentication.
     */
    private static final List<String> publicPaths = List.of(
            "/auth/login",
            "/university/search",
            "/company/search",
            "/student/search",
            "/review/getAverageRating"
    );

    /**
     * Checks if the given path matches any of the public paths.
     *
     * @param path the request path
     * @return true if the path is public, false otherwise
     */
    public static boolean isPathOnPublicPaths(String path) {
        return publicPaths.stream().anyMatch(path::startsWith);
    }
}
