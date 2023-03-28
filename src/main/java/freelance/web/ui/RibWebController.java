package freelance.web.ui;

import freelance.service.command.CommandManager;
import freelance.service.command.command.rib.CreateRibCommand;
import freelance.service.command.command.rib.UpdateRibCommand;
import freelance.service.query.RibQueryService;
import freelance.service.query.model.Rib;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ribs")
public class RibWebController {

    private final CommandManager commandManager;
    private final RibQueryService ribQueryService;

    public RibWebController(CommandManager commandManager, RibQueryService ribQueryService) {
        this.commandManager = commandManager;
        this.ribQueryService = ribQueryService;
    }
    @GetMapping("/create")
    public String createRibForm(Model model) {
        model.addAttribute("rib", new CreateRibCommand());
        return "rib/createRib";
    }

    @PostMapping("/create")
    public String createRib(@Valid @ModelAttribute("rib") CreateRibCommand command,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "rib/createRib";
        }
        commandManager.process(command);
        return "redirect:/rib/view/" + command.getCreateRibId();
    }
    @GetMapping("/edit/{id}")
    public String updateRibForm(@PathVariable("id") Long id, Model model) {
        Rib ribResult = ribQueryService.getById(id)
                .orElseThrow(()->new RuntimeException("rib with id "+id+" not found "));
        UpdateRibCommand command = UpdateRibCommand.builder()
                .iban(ribResult.getIban())
                .bic(ribResult.getBic())
                .cleRib(ribResult.getCleRib())
                .ribId(id)
                .username(ribResult.getUsername())
                .build();
        model.addAttribute("updateRibCommand", command);
        return "rib/updateRib";
    }

    @PostMapping("/edit/{id}")
    public String updateRib(@Valid @ModelAttribute("updateRibCommand") UpdateRibCommand command,
                            BindingResult bindingResult,
                            @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("updateRibCommand", command);
            System.out.println(bindingResult.getFieldError());
            return "rib/updateRib";
        }
        command.setUpdatedRibId(id);
        commandManager.process(command);
        return "redirect:/ribs/show/" + id;
    }

    @GetMapping("/show/{id}")
    public String viewRib(@PathVariable("id") Long id, Model model) {
        Rib ribResult = ribQueryService.getById(id)
                .orElseThrow(()->new RuntimeException("rib with id "+id+" not found "));
        model.addAttribute("rib", ribResult);
        return "rib/viewRib";
    }
    @GetMapping
    public String listRibs(Model model) {
        commandManager.process(CreateRibCommand.builder()
                        .iban("FR7628521966142334508845009")
                        .bic("ABCDEFGH")
                        .cleRib("01")
                        .build());
       List<Rib> ribs=ribQueryService.getAll().toList();
       model.addAttribute("ribs",ribs);
        return "rib/ribs";
    }
}