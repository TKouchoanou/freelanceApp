package freelance.service.command;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Component
public class DefaultCommandRouting implements CommandRouting{
     ApplicationContext context;
     public DefaultCommandRouting(ApplicationContext context){
         this.context=context;
     }

    @Override
    public List<CommandHandler> getHandlers(Command cmd) {
      Command.Usecase usecase= cmd.getClass().getAnnotation(Command.Usecase.class);
       return Stream.of(usecase.handlers()).map(context::getBean).collect(Collectors.toList());
    }

    @Override
    public boolean isParallelHandling(Command cmd) {
        return cmd.getClass().getAnnotation(Command.Usecase.class).parallelHandling();
    }

    @Override
    public Isolation getIsolation(Command cmd) {
        return cmd.getClass().getAnnotation(Command.Usecase.class).isolation();
    }


}

