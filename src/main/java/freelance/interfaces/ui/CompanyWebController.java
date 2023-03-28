package freelance.interfaces.ui;

import freelance.application.command.CommandManager;
import freelance.application.command.command.company.CreateCompanyCommand;
import freelance.application.command.command.company.UpdateCompanyCommand;
import freelance.application.query.CompanyQueryService;
import freelance.application.query.model.Company;
import freelance.interfaces.WebException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyWebController {


    private final CommandManager commandManager;

    private final CompanyQueryService companyQueryService;

    public CompanyWebController(CommandManager commandManager, CompanyQueryService companyQueryService) {
        this.commandManager = commandManager;
        this.companyQueryService = companyQueryService;
    }


    @GetMapping
    public String showCompanies(Model model) {
        commandManager.process(CreateCompanyCommand
                .builder()
                .name("tk bs")
                .ribId(1L)
                .build());
        List<Company> companies = companyQueryService.getAll().toList();
                model.addAttribute("companies", companies);
        return "company/companies";
    }

    @GetMapping("/create")
    public String showCreateCompanyForm(Model model) {
        model.addAttribute("createCompanyCommand", new CreateCompanyCommand());
        return "company/create-company";
    }

    @PostMapping("/create")
    public String createCompany(@ModelAttribute("createCompanyCommand") CreateCompanyCommand command, Model model) {
        try {
            commandManager.process(command);
            return "redirect:/companies";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "company/create-company";
        }
    }

    @GetMapping("/{id}/edit")
    public String showUpdateCompanyForm(@PathVariable("id") Long companyId, Model model) {
        Company company = companyQueryService.getById(companyId).orElseThrow(WebException::new);
                UpdateCompanyCommand command = UpdateCompanyCommand.builder()
                .companyId(companyId)
                .name(company.getName())
                .ribId(company.getRibId())
                .build();
        model.addAttribute("updateCompanyCommand", command);
        return "company/edit-company";
    }

    @PostMapping("/{id}/edit")
    public String updateCompany(@ModelAttribute("command") UpdateCompanyCommand command, Model model) {
        try {
            commandManager.process(command);
            return "redirect:/companies";
        } catch (WebException ex) {
            model.addAttribute("error", ex.getMessage());
            return "company/edit-company";
        }
    }

}
