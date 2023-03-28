package freelance.application.command;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Service
public class CommandManagerImpl implements CommandManager{
    CommandRouting commandRouting;
    ApplicationContext context;
    Logger logger;
    PlatformTransactionManager txManager;
    List<ExceptionHandler> exHandlers;
    CommandManagerImpl(CommandRouting commandRouting, ApplicationContext context, Logger logger, PlatformTransactionManager txManager,List<ExceptionHandler> exHandlers){
     this.commandRouting=commandRouting; this.context=context; this.logger=logger;
     this.txManager=txManager; this.exHandlers=exHandlers;
    }
    @Override
    @Transactional
    public <T extends Command> T process(T command) {
        logger.info(" command of type {} : {} is received for processing ",command.getClass().getSimpleName(),command.name());
        validateStateBeforeHandling(command);

        //open transaction with configured isolation
        var txDef= new DefaultTransactionDefinition();
        txDef.setIsolationLevel(this.commandRouting.getIsolation(command).value());
        TransactionStatus txStatus= this.txManager.getTransaction(txDef);

        CommandHandler.HandlingContext handlingContext=new CommandHandler.HandlingContext();
        List<CommandHandler> commandHandlerList= commandRouting.getHandlers(command);

        try {
            logger.info("{} handlers will deal with {}", commandHandlerList.size(), command.name());
            boolean sequentialHandling= !commandRouting.isParallelHandling(command);
            if(sequentialHandling){
                commandHandlerList .forEach(handler -> {
                    logger.info("ask {} to handle {}", handler.getClass().getName(), command.name());
                    handler.handle(command,handlingContext);
                });
            }else {
                CompletableFuture<?>[] workers= commandHandlerList.stream()
                        .map(handler -> CompletableFuture.runAsync(()->handler.handle(command,handlingContext)))
                        .toArray(CompletableFuture[]::new);
                logger.info("waiting for the {} handlers to complete", workers.length);
                CompletableFuture.allOf(workers).join();
                logger.info("command handling completed");
            }
            validateStateAfterHandling(command);
            this.txManager.commit(txStatus);
        }catch (Exception ex){
            ex.printStackTrace();
            this.exHandlers.forEach(eh->eh.handleException(ex,command));
        }
        return command;
    }

    private  void validateStateBeforeHandling(Command command){
        logger.info("Is {} valid before handling ?", command);
        command.validateStateBeforeHandling();
        logger.info("yes, {} is valid", command);
    }
   private void  validateStateAfterHandling(Command command){
       logger.info("Is {} valid after handling ?", command);
       command.validateStateAfterHandling();
       logger.info("yes, {} is valid", command);
   }
}
