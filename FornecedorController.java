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

import com.basica.ProjetoKellyLojaUnit.Fornecedor;
import com.repositorio.ProjetoKellyLojaunit.FornecedorRepositorio;

@Controller 
@RequestMapping(path="/fornecedor") 

public class FornecedorController {
	@Autowired
	private FornecedorRepositorio fornecedorRepository;
	  @PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewUser (@RequestParam String nomeFornecedor
	      , @RequestParam String telefone, @RequestParam String cnpj, @RequestParam String email ) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    Fornecedor f = new Fornecedor();
	    f.setNomeFornecedor(nomeFornecedor);
	    f.setTelefone(telefone);
	    f.setCnpj(cnpj);
	    f.setEmail(email);
	    
	   fornecedorRepository.save(f);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  public @ResponseBody Iterable<Fornecedor> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return fornecedorRepository.findAll();
	  }

	  @GetMapping(path="/procurar/{id}")
		public @ResponseBody Optional<Fornecedor> getFornecedorById(@PathVariable("id")Integer id){
			return fornecedorRepository.findById(id);
		}

		@DeleteMapping(path="/excluir/{id}")
		public @ResponseBody String deleteFornecedorById(@PathVariable("id")Integer id) {
			if(fornecedorRepository.existsById(id)) {
				fornecedorRepository.deleteById(id);
				return "Fornecedor apagado com sucesso";
			}
			return "Fornecedor não encontrado para deleção, verifique o ID";
		}
		
		@PutMapping(path="/atualizar/{id}")
		public @ResponseBody String updateFornecedorById(
				@RequestParam String nomeFornecedor
			      , @RequestParam String telefone, @RequestParam String cnpj, @RequestParam String email,
				@PathVariable("id")Integer id) {
			if(fornecedorRepository.existsById(id)) {
				Fornecedor f = new Fornecedor();
				    f.setNomeFornecedor(nomeFornecedor);
				    f.setTelefone(telefone);
				    f.setCnpj(cnpj);
				    f.setEmail(email);
				    
				   fornecedorRepository.save(f);
				   
				return "Fornecedor atualizado: ";
			}
			return "Fornecedor não atualizado";
		}
	}