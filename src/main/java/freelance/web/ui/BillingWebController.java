package freelance.web.ui;

import freelance.service.command.CommandManager;
import freelance.service.command.command.billing.CreateBillingCommand;
import freelance.service.query.BillingQueryService;
import freelance.service.query.model.BillingSummary;
import freelance.web.ui.dto.CreateBillingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/billings")
class BillingWebController {


    private final CommandManager commandManager;
    private final BillingQueryService billingQueryService;

    public BillingWebController(CommandManager commandManager, BillingQueryService billingQueryService) {
        this.commandManager = commandManager;
        this.billingQueryService = billingQueryService;
    }


    @GetMapping
    public String getAllBillings(Model model) {
        List<BillingSummary> billings =billingQueryService.getAllBillingsSummary();
        model.addAttribute("billings", billings);
        return "billing/billings"; //la vue Thymeleaf qui affiche tous les billings
    }

    @GetMapping("/new")
    public String showCreateBillingForm(Model model) {
        model.addAttribute("createBillingCommand", new CreateBillingCommand());
        return "billing/create-billing"; //la vue Thymeleaf qui affiche le formulaire de création de facturation
    }

    @PostMapping("/new")
    public String submitCreateBillingForm(@ModelAttribute("createBillingCommand") CreateBillingDto createBillingDto, Model model) {
        CreateBillingCommand createBillingCommand= createBillingDto.toCommand();
        CreateBillingCommand result = commandManager.process(createBillingCommand);

        result.validateStateAfterHandling();
        return "redirect:/billings"; //TODO retourner plutôt la vue Thymeleaf qui affiche les détails de la facturation créée
    }
}
