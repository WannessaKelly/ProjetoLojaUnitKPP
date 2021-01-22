package com.negocio.ProjetoKellyLojaUnit;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basica.ProjetoKellyLojaUnit.FormaPagamento;
import com.repositorio.ProjetoKellyLojaunit.FormaPagamentoRepositorio;

@Controller
@RequestMapping(path="/forma") 
public class FormaPagamentoController {
@Autowired	
	
	private FormaPagamentoRepositorio formaPgtoRepository;
	  @PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewUser (@RequestParam String formaPagamento, @RequestParam String descricao,
	       @RequestParam boolean ativo) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    FormaPagamento f = new FormaPagamento();
	    f.setFormaPagamento(formaPagamento);
	    f.setDescricao(descricao);
	    f.setAtivo(ativo);
	    	  	    	    
	    formaPgtoRepository.save(f);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  public @ResponseBody Iterable<FormaPagamento> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return formaPgtoRepository.findAll();
	  }
	  @GetMapping(path="/procurar/{id}")
		public @ResponseBody Optional<FormaPagamento> getFormaPagamentoById(@PathVariable("id")Integer id){
			return formaPgtoRepository.findById(id);
		}
		
		
		
		@DeleteMapping(path="/excluir/{id}")
		public @ResponseBody String deleteFormaPagamentoById(@PathVariable("id")Integer id) {
			if(formaPgtoRepository.existsById(id)) {
				formaPgtoRepository.deleteById(id);
				return "Forma de Pagamento apagada com sucesso";
			}
			return "Forma de Pagamento não encontrada para deleção, verifique o ID";
		}
		
		@PutMapping(path="/atualizar/{id}")
		public @ResponseBody String updateFormaPagamentoById(
				@RequestParam String formaPagamento, @RequestParam String descricao,
			       @RequestParam boolean ativo,
				@PathVariable("id")Integer id) {
			if(formaPgtoRepository.existsById(id)) {
				 FormaPagamento f = new FormaPagamento();
				    f.setFormaPagamento(formaPagamento);
				    f.setDescricao(descricao);
				    f.setAtivo(ativo);
				    	  	    	    
				    formaPgtoRepository.save(f);
				return "Forma de Pagamento atualizado ";
			}
			return "Forma de pagamento não atualizado";
		}
	}