package freelance.service.command;

import org.springframework.transaction.annotation.Isolation;

import java.util.List;

public interface CommandRouting {
    List<CommandHandler> getHandlers(Command cmd);
  boolean isParallelHandling(Command cmd);

    Isolation getIsolation(Command cmd);
}
