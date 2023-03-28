package freelance.application.command.handler.user;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.UserId;
import freelance.domain.output.repository.UserRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.user.ChangePassWordCommand;
import freelance.application.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordCommandHandler implements CommandHandler {
    UserRepository userRepository;
    AuthProvider authProvider;
    ChangePasswordCommandHandler(UserRepository userRepository,AuthProvider authProvider){
        this.userRepository=userRepository;
        this.authProvider=authProvider;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof ChangePassWordCommand cmd)){
            return;
        }
        User user=userRepository.getById(new UserId(cmd.getUserId()));
        user.setPassWord(cmd.getNewPassword(),authProvider.getCurrentAuth());
        userRepository.save(user);
        cmd.setSucceed(true);
    }
}
