package freelance.application.command;

public interface ExceptionHandler {
    void handleException(Exception ex, Command command);
}
