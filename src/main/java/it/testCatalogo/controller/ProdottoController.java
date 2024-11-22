package it.testCatalogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.testCatalogo.dao.ProdottoDao;
import it.testCatalogo.model.Prodotto;

@Controller
@RequestMapping("/")
public class ProdottoController {
	
	@Autowired
	private ProdottoDao service;

	@GetMapping("/")
	public ModelAndView index(@RequestParam(name = "id", required = false) String idProdotto, ModelMap mm) {
	    if (idProdotto != null && !idProdotto.isEmpty()) {
	        try {
	            Prodotto p = service.getById(Integer.parseInt(idProdotto));
	            mm.addAttribute("prodottoDaModificare", p);
	        } catch (NumberFormatException e) {
	            // Gestisci il caso in cui l'ID non è valido
	        }
	    }
	    return new ModelAndView("index", "listaProdotti", service.getAll());
	}
	
	@PostMapping("/add")
	public String add(@ModelAttribute("datiProdotto") Prodotto p) {
		
		service.add(p);
		
		return "redirect:/";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute("datiProdotto") Prodotto p) {
		
		service.update(p);
		
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") String idProdotto) { //la requestparam la chiamiamo come il nome che gli passiamo in query string con il button delete della vista
		if(idProdotto != null) {			
			service.delete(Integer.parseInt(idProdotto));
		}
		
		return "redirect:/";
	}
}
