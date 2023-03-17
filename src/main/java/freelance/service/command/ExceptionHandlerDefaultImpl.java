package freelance.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionHandlerDefaultImpl implements ExceptionHandler{
    @Override
    public void handleException(Exception ex, Command command) {

    }
}
