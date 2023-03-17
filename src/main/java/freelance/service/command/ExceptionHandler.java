package freelance.service.command;

public interface ExceptionHandler {
    void handleException(Exception ex, Command command);
}
