package tk.ubublik.pai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tk.ubublik.pai.dto.TransactionDTO;
import tk.ubublik.pai.dto.TransactionSearchDTO;
import tk.ubublik.pai.entity.TransactionStatus;
import tk.ubublik.pai.exception.TransactionException;
import tk.ubublik.pai.service.AccountService;
import tk.ubublik.pai.service.SecurityService;
import tk.ubublik.pai.service.TransactionService;
import tk.ubublik.pai.service.UserService;
import tk.ubublik.pai.validation.Errors;

@Controller
public class TransactionController {

	private UserService userService;
	private AccountService accountService;
	private TransactionService transactionService;
	private SecurityService securityService;

	@Autowired
	public TransactionController(UserService userService, AccountService accountService,
	                         TransactionService transactionService, SecurityService securityService) {
		this.userService = userService;
		this.accountService = accountService;
		this.transactionService = transactionService;
		this.securityService = securityService;
	}

	@RequestMapping("/accounts/{number}")
	public String showAccountTransactions(Model model, @PathVariable("number") String accountNumber,
	                                      @RequestParam(required = false) Integer page,
	                                      @RequestParam(required = false) Integer size,
	                                      @RequestParam(required = false) String desc){
		TransactionSearchDTO searchDTO = new TransactionSearchDTO(page, size,
				null, desc!=null, null, null, accountNumber, false, null, null);
		model.addAttribute("page", transactionService.search(searchDTO));
		model.addAttribute("accountNumber", accountNumber);
		model.addAttribute("transaction", new TransactionDTO());
		return "transactions";
	}

	@PostMapping("/transactions")
	public String addTransaction(Model model, @ModelAttribute TransactionDTO transactionDTO){
		try {
			transactionService.save(transactionDTO);
		} catch (TransactionException e){
			model.addAttribute("errors", e.getErrors());
		}
		return "/accounts/"+transactionDTO.sender;
	}

	@PostMapping("/transactions/{id}/{action}")
	public String setTransactionStatus(Model model, @PathVariable("id") long id, @PathVariable("action") String action){
		TransactionStatus status;
		switch (action.toLowerCase()){
			case "accept": status = TransactionStatus.ACCEPTED; break;
			case "cencel": status = TransactionStatus.CANCELED; break;
			default: throw new IllegalArgumentException("Bad action name");
		}
		model.addAttribute("errors", transactionService.setStatus(id, status));
		return getTransactions(model, null);
	}

	@GetMapping("/transactions")
	public String getTransactions(Model model, @RequestParam(required = false) String desc){
		model.addAttribute("page", transactionService.search(new TransactionSearchDTO(null,
				null, "status", desc!=null, null, null, null, false, null, null)));
		return "transactions";
	}
}
