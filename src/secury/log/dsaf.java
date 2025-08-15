package secury.log;

import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;

public class dsaf {
    public static void main(String[] args) {
        ChronicleQueue queue = ChronicleQueue.single("worm-logs");
        ExcerptAppender appender = queue.createAppender();
        appender.writeText("User admin transferred 1000 CNY");

        ExcerptTailer tailer = queue.createTailer();
        String log = tailer.readText(); // 读取日志
        System.out.println(log);

    }
}
