package freelance.service.command.handler.user;
import freelance.service.command.Command.Usecase;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.user.ChangePassWordCommand;
import freelance.service.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordCommandHandler implements CommandHandler {
    UserRepository userRepository;
    AuthProvider authProvider;
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof ChangePassWordCommand cmd)){
            return;
        }
        User user=userRepository.getById(new UserId(cmd.getUserId()));
        user.setPassWord(cmd.getNewPassWord(),authProvider.getCurrentAuth());
        userRepository.save(user);
    }
}
