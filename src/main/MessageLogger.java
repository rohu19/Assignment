package main;

public class MessageLogger {
    private int successCount = 0;
    private int errorCount = 0;

    public synchronized void logSuccess() {
        successCount++;
    }

    public synchronized void logError() {
        errorCount++;
    }

    public synchronized int getSuccessCount() {
        return successCount;
    }

    public synchronized int getErrorCount() {
        return errorCount;
    }
}
