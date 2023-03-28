package freelance.application.command;

import org.springframework.stereotype.Service;

@Service
public class ExceptionHandlerDefaultImpl implements ExceptionHandler{
    @Override
    public void handleException(Exception ex, Command command) {

    }
}
