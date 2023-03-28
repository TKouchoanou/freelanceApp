package freelance.application.command;

public interface CommandManager {
    <T extends Command> T process(T command);
}
