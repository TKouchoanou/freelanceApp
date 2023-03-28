package freelance.interfaces.ui;

import freelance.application.command.CommandManager;
import freelance.application.command.command.employee.CreateEmployeeCommand;
import freelance.application.command.command.user.ChangePassWordCommand;
import freelance.application.command.command.user.CreateUserCommand;
import freelance.application.command.command.user.UpdateUserCommand;
import freelance.application.query.UserQueryService;
import freelance.application.query.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserWebController {
    private final CommandManager commandManager;
    private  final UserQueryService userQueryService;

    public UserWebController(CommandManager commandManager, UserQueryService userQueryService) {
        this.commandManager = commandManager;
        this.userQueryService = userQueryService;
    }


    @GetMapping
    public String getUsers(Model model) {
        // récupère la liste des utilisateurs à partir de la base de données
        commandManager.process(
                CreateUserCommand.builder()
                        .passWord("troP2chozl@")
                        .passWordConfirmation("troP2chozl@")
                        .firstName("theophane")
                        .lastName("Le boss")
                        .email("stepha@gmail.com")
                .isActive(true).build());
        commandManager.process(
                CreateUserCommand.builder()
                        .passWord("troP2chozl@")
                        .passWordConfirmation("troP2chozl@")
                        .firstName("Alcibiade")
                        .lastName("Gbaguidi")
                        .email("alcibiade@gmail.com")
                        .isActive(true).build());
        commandManager.process(
                CreateUserCommand.builder()
                        .passWord("troP2chozl@")
                        .passWordConfirmation("troP2chozl@")
                        .firstName("Faiszou")
                        .lastName("Aremou")
                        .email("aremouFeae@gmail.com")
                        .isActive(true).build());


        List<User> users = userQueryService.getAllUsers();
        commandManager.process(CreateEmployeeCommand
                .builder()
                .roles(List.of("ADMIN"))
                .userId(1L)
                .build());
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", CreateUserCommand.builder().build());
        return "user/create-user";
    }

    @PostMapping("/create")
    public String createUser(@Validated @ModelAttribute("user") CreateUserCommand command, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("user", command);
            return "user/create-user";
        }
        // traite la commande
        CreateUserCommand createdCommand = commandManager.process(command);


        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        // récupère l'utilisateur à partir de la base de données
        User user = userQueryService.geUserById(id).orElseThrow(()->new RuntimeException("Not found user with id "+id));
                UpdateUserCommand command = UpdateUserCommand.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .isActive(user.isActive())
                .build();

        model.addAttribute("user", command);

        return "user/edit-user";
    }

    @PostMapping("/{id}/edit")
    public String updateUser(@Validated @ModelAttribute("user") UpdateUserCommand command,BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("user", command);
            return "user/edit-user";
        }
        // traite la commande
        UpdateUserCommand updatedCommand = commandManager.process(command);

        return "redirect:/users";
    }

    @GetMapping("/changePassword/{userId}")
    public String showChangePasswordForm(@PathVariable Long userId, Model model) {
        model.addAttribute("changePasswordCommand", ChangePassWordCommand.builder().userId(userId).build());
        return "user/password-form";
    }

    @PostMapping("/changePassword")
    public String submitChangePasswordForm(ChangePassWordCommand command, Model model) {
        ChangePassWordCommand result = commandManager.process(command);
        if (result.hasSucceed()) {
            model.addAttribute("message", "Password updated successfully");
        } else {
            model.addAttribute("message", "Failed to update password");
        }
        return "user/password-result";
    }
}
