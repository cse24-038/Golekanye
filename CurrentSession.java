public class CurrentSession {
    private static String customerId;

    public static String getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(String id) {
        customerId = id;
    }
}
