package freelance.service.command.handler.user;
import freelance.service.command.Command.Usecase;
import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.user.UpdateUserCommand;
import freelance.service.command.utils.AuthProvider;
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
