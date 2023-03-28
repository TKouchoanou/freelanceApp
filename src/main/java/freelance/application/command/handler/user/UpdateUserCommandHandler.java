package freelance.application.command.handler.user;
import freelance.domain.core.entity.User;
import freelance.domain.core.objetValue.UserId;
import freelance.domain.output.repository.UserRepository;
import freelance.application.command.Command;
import freelance.application.command.CommandException;
import freelance.application.command.CommandHandler;
import freelance.application.command.command.user.UpdateUserCommand;
import freelance.application.command.utils.AuthProvider;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserCommandHandler implements CommandHandler {
    UserRepository userRepository;
    AuthProvider authProvider;
    UpdateUserCommandHandler(UserRepository userRepository, AuthProvider authProvider){
        this.userRepository=userRepository;
        this.authProvider=authProvider;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof UpdateUserCommand cmd)){
            return;
        }
        User user= userRepository.findById(new UserId(cmd.getId()))
                .orElseThrow(()->new CommandException(" no user with provided id "+cmd.getId()));
        user.update(cmd.getFirstName(), cmd.getLastName(), cmd.getEmail(), authProvider.getCurrentAuth());

        user.setActive(cmd.isActive(), authProvider.getCurrentAuth());
        User result= this.userRepository.save(user);
        cmd.setId(result.getId().id());
    }
}
