package freelance.interfaces.ui;

import freelance.application.command.CommandManager;
import freelance.application.command.command.billing.CreateBillingCommand;
import freelance.application.command.command.billing.UpdatePaymentStatusCommand;
import freelance.application.command.command.billing.UpdateValidationStatusCommand;
import freelance.application.query.BillingQueryService;
import freelance.application.query.model.Billing;
import freelance.application.query.model.BillingSummary;
import freelance.application.query.model.File;
import freelance.interfaces.ui.billing.dto.BillingSummaryDto;
import freelance.interfaces.ui.billing.dto.CreateBillingDto;
import freelance.interfaces.ui.billing.view.UpdatePaymentStatusViewModel;
import freelance.interfaces.ui.billing.view.UpdateValidationStatusViewModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static freelance.interfaces.ui.billing.view.UpdateValidationStatusViewModel.createUpdateStatusValidationViewModelFrom;

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
        List<BillingSummaryDto> billingsDto= billings.stream().map(BillingSummaryDto::new).toList();
        model.addAttribute("billings", billingsDto);
        return "billing/billings";
    }

    @GetMapping("/create")
    public String showCreateBillingForm(Model model) {
        model.addAttribute("createBillingCommand",new CreateBillingDto(preFill()));
        return "billing/create-billing"; //la vue Thymeleaf qui affiche le formulaire de création de facturation
    }

    @PostMapping("/create")
    public String submitCreateBillingForm(@ModelAttribute("createBillingCommand") CreateBillingDto createBillingDto, Model model) {
        CreateBillingCommand createBillingCommand= createBillingDto.toCommand();
        CreateBillingCommand result = commandManager.process(createBillingCommand);

        result.validateStateAfterHandling();
        return "redirect:/billings"; //TODO retourner plutôt la vue Thymeleaf qui affiche les détails de la facturation créée
    }
    @GetMapping("/{billingId}/edit")
    public String showUpdatePaymentStatusForm(@PathVariable Long billingId, Model model) {
        Billing billing=billingQueryService.getBillingById(billingId).orElseThrow();
        UpdatePaymentStatusViewModel viewModel=new UpdatePaymentStatusViewModel(billing);
        model.addAttribute("viewModel",viewModel);

        return "billing/edit-payment-status";
    }
    @GetMapping("validation/{billingId}/edit")
    public String showUpdateValidationStatusForm(@PathVariable Long billingId, Model model) {
        Billing billing=billingQueryService.getBillingById(billingId).orElseThrow();
        UpdateValidationStatusViewModel viewModel=createUpdateStatusValidationViewModelFrom(billing);
        model.addAttribute("viewModel",viewModel);
        return "billing/edit-validation-status";
    }
    @PostMapping("validation/edit")
    public String submitUpdateValidationStatusForm(@ModelAttribute("updateValidationStatusCommand") UpdateValidationStatusCommand updateValidationStatusCommand, Model model) {
        commandManager.process(updateValidationStatusCommand);
        return "redirect:/billings";
    }
    @PostMapping("/edit")
    public String submitUpdatePaymentStatusForm(@ModelAttribute("updatePaymentStatusCommand") UpdatePaymentStatusCommand updatePaymentStatusCommand, Model model) {
        commandManager.process(updatePaymentStatusCommand);
        return "redirect:/billings";
    }
    @GetMapping("/file/{billingId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long billingId)  {

        File file = billingQueryService.loadBillingFile(billingId);
        Resource resource;
        try {
            resource = new ByteArrayResource(file.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
    private CreateBillingCommand preFill(){
       return CreateBillingCommand.builder()
                .amountHT(new BigDecimal(300))
                .amountTTC(new BigDecimal(500))
                .ribId(1L)
                .userId(1L)
                .companyId(1L)
               .startedDate(LocalDate.now().minusMonths(2))
               .endedDate(LocalDate.now().minusMonths(1))
               .build();
    }
}
