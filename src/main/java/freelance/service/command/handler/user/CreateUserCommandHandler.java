package freelance.service.command.handler.user;
import freelance.domain.models.objetValue.Email;
import freelance.domain.models.entity.User;
import freelance.domain.repository.UserRepository;
import freelance.service.command.Command;
import freelance.service.command.CommandException;
import freelance.service.command.CommandHandler;
import freelance.service.command.command.user.CreateUserCommand;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
       Optional<User> existing= this.userRepository.findByEmail(new Email(cmd.getEmail()));
       if(existing.isPresent()){
           throw new CommandException("email "+cmd.getEmail()+" already exist");
       }

        User user= new User(cmd.getFirstName(),cmd.getLastName(),cmd.getEmail(),cmd.getPassWord(),cmd.isActive());
        User result= this.userRepository.save(user);
        cmd.setId(result.getId().id());

    }
}
