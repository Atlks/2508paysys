package util.msg;

public enum ChatType {
    PRIVATE("private"),
    GROUP("group"),
    SUPERGROUP("supergroup"),
    CHANNEL("channel");

    private final String value;

    ChatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ChatType fromValue(String value) {
        for (ChatType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown chat type: " + value);
    }
}
