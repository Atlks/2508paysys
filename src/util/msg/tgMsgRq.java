package util.msg;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class tgMsgRq {
    private String chatId;
    private String text;
    private String parseMode; // 可选：Markdown 或 HTML

    public tgMsgRq(String chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    // 可选构造器
    public tgMsgRq(String chatId, String text, String parseMode) {
        this.chatId = chatId;
        this.text = text;
        this.parseMode = parseMode;
    }

    // Getter 和 Setter
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }
}
