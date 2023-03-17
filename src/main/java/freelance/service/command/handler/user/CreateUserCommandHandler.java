package freelance.service.command.handler.user;
import freelance.service.command.Command.Usecase;
import freelance.domain.models.entity.User;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.user.CreateUserCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCommandHandler implements CommandHandler {
    UserRepository userRepository;
    CreateUserCommandHandler(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public void handle(Command command, HandlingContext handlingContext) {
        if(!(command instanceof CreateUserCommand cmd)){
            return;
        }
        User user= new User(cmd.getFirstName(),cmd.getLastName(),cmd.getEmail(),cmd.getPassWord(),cmd.isActive());
        User result= this.userRepository.save(user);
        cmd.setId(result.getId().id());

    }
}
