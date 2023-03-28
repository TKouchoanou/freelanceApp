package freelance.interfaces.ui;

import freelance.application.command.CommandManager;
import freelance.application.command.command.freeLance.CreateFreeLanceCommand;
import freelance.application.command.command.freeLance.UpdateFreeLanceCommand;
import freelance.application.query.FreelanceQueryService;
import freelance.application.query.model.Freelance;
import freelance.application.query.model.FreelanceSummary;
import freelance.interfaces.WebException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/freelances")

public class FreelanceWebController {

    private final CommandManager commandManager;
    private final FreelanceQueryService freelanceQueryService;

    public FreelanceWebController(CommandManager commandManager, FreelanceQueryService freelanceQueryService) {
        this.commandManager = commandManager;
        this.freelanceQueryService = freelanceQueryService;
    }

    @GetMapping("{userId}/create")
    public String showCreateFreelanceForm(@PathVariable Long userId, Model model) {
        model.addAttribute("freelance", CreateFreeLanceCommand.builder().userId(userId).build());
        return "freelance/create-freelance";
    }

    @PostMapping("/create")
    public String createFreelance(@ModelAttribute("freelance") CreateFreeLanceCommand command) {
        commandManager.process(command);
        return "redirect:/freelances";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateFreelanceForm(@PathVariable("id") Long id, Model model) {
        Freelance freelance = freelanceQueryService.getFreelanceById(id)
                .orElseThrow(() -> new WebException("Freelance not found with id: " + id));
        UpdateFreeLanceCommand command = new UpdateFreeLanceCommand();
        command.setId(id);
        command.setCompanyId(freelance.getCompany().getId());
        command.setRibId(freelance.getRib().getId());
        model.addAttribute("freelance", command);
        return "freelance/edit-freelance";
    }

    @PostMapping("/{id}/edit")
    public String updateFreelance(@PathVariable("id") Long id, @ModelAttribute("freelance") UpdateFreeLanceCommand command) {
        command.setId(id);
        commandManager.process(command);
        return "redirect:/freelances";
    }

    @GetMapping
    public String showFreelances(Model model) {
        CreateFreeLanceCommand command=CreateFreeLanceCommand
                .builder()
                .ribId(1L)
                .companyId(1L)
                .userId(1L)
                .build();
        commandManager.process(command);
        List<FreelanceSummary> freelances = freelanceQueryService.getAllFreelances();
        model.addAttribute("freelances", freelances);
        return "freelance/freelances";
    }
}
