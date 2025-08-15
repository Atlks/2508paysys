package secury.xss;


/**
 * 输入过滤
 */
/**
 * 输入过滤工具类，用于防止常见安全问题
 */
public class InputChkFiltUti {

    /**
     * 校验商家名称是否合法：允许汉字、字母、数字、下划线、空格
     * @param name 商家名称
     * @return true 表示合法，false 表示非法
     */
    public static boolean isValidMerchantName(String name) {
        if (name == null || name.isEmpty()) return false;

        // 正则说明：
        // \\u4e00-\\u9fa5 表示中文汉字
        // A-Za-z 表示英文字母
        // 0-9 表示数字
        // _ 表示下划线
        // 空格直接写即可
        return name.matches("^[\\u4e00-\\u9fa5A-Za-z0-9@_ ]+$");
    }


    /**
     * 过滤 HTML 标签，防止 XSS 攻击
     */
    public static String stripHtml(String input) {
        if (input == null) return null;
        return input.replaceAll("<[^>]*>", "");
    }

    /**
     * 过滤 SQL 注入关键字
     */
    public static String stripSqlInjection(String input) {
        if (input == null) return null;
        return input.replaceAll("(?i)(select|insert|delete|update|drop|truncate|exec|union|--|;)", "");
    }

    /**
     * 过滤路径穿越符号（如 ../）
     */
    public static String stripPathTraversal(String input) {
        if (input == null) return null;
        return input.replaceAll("\\.\\./", "").replaceAll("\\.\\.\\\\", "");
    }

    /**
     * 保留字母、数字、下划线（用于用户名、标识符等）
     */
    public static String keepAlphaNumUnderscore(String input) {
        if (input == null) return null;
        return input.replaceAll("[^A-Za-z0-9_]", "");
    }

    /**
     * 限制最大长度，防止超长输入
     */
    public static String limitLength(String input, int maxLength) {
        if (input == null) return null;
        return input.length() > maxLength ? input.substring(0, maxLength) : input;
    }
}
