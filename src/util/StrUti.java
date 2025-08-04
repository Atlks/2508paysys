package util;

public class StrUti {




    public static String extractLastPathSegment(String urlPath) {
        if (urlPath == null || urlPath.isEmpty()) return null;

        // 去除尾部可能的斜杠
        urlPath = urlPath.endsWith("/") ? urlPath.substring(0, urlPath.length() - 1) : urlPath;

        // 按斜杠分割路径
        String[] segments = urlPath.split("/");

        // 返回最后一个非空段
        return segments.length > 0 ? segments[segments.length - 1] : null;
    }

}
