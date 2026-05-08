import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {

    private String notificationId;
    private String send;
    private String title;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Notification(String notificationId, String send, String title, String message) {
        this.notificationId = notificationId;
        this.send           = send;
        this.title          = title;
        this.message        = message;
        this.timestamp      = LocalDateTime.now();
        this.isRead         = false;
    }

    public void sendNotification(String userId, String title, String message) {
        System.out.println("📬 Notification to [" + userId + "]");
        System.out.println("   " + title + ": " + message);
        System.out.println("   🕐 " + timestamp.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void markAsRead() {
        this.isRead = true;
        System.out.println("✔ Notification marked as read: " + title);
    }

    public String getNotificationId(){
        return notificationId;
    }
    public String getSend(){
        return send;
    }
    public String getMessage(){
        return message;
    }
    public boolean isRead(){
        return isRead;
    }
}