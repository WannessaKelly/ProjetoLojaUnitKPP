package com.negocio.ProjetoKellyLojaUnit;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.basica.ProjetoKellyLojaUnit.Cliente;
import com.repositorio.ProjetoKellyLojaunit.ClienteRepositorio;

@RestController 
@RequestMapping(path="/clientes") 

public class ClienteController {
	@Autowired
	private ClienteRepositorio clienteRepository;
	
	 @PostMapping(path="/add") // Map ONLY POST Requests
	  @ResponseStatus(code=HttpStatus.CREATED)
	  public @ResponseBody String addNewCliente (@RequestParam String nome, @RequestParam String email, @RequestParam String nome_social, @RequestParam String apelido,
	      @RequestParam String cpf, @RequestParam String telefone,@RequestParam String sexo,
	      @RequestParam Date data_nascimento) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    Cliente c = new Cliente();
	    c.setNome(nome);
	    c.setEmail(email);
	    c.setNomeSocial(nome_social);
	    c.setApelido(apelido);
	    c.setSexo(sexo);
	    c.setDataNasc(data_nascimento);
	    c.setFone(telefone);
	    c.setCpf(cpf);
	  	    	    
	    clienteRepository.save(c);
	    return "Saved";
	  }

	  @GetMapping(path="/all")
	  @CrossOrigin(origins = "http://localhost:4200/")
	  public @ResponseBody Iterable<Cliente> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return clienteRepository.findAll();
	  }
	  @GetMapping(path = "/find/{id}")

		public @ResponseBody Optional<Cliente> getClienteById(@PathVariable("id") Integer id) {
			return clienteRepository.findById(id);
		}

		@DeleteMapping(path = "/delete/{id}")
		  @ResponseStatus(code=HttpStatus.ACCEPTED)
		public @ResponseBody String deleteClienteById(@PathVariable("id") Integer id) {
			if (clienteRepository.existsById(id)) {
				clienteRepository.deleteById(id);
				return "Cliente excluido com Sucesso";
			}
			return "Cliente não encontrado!";
		}

		@DeleteMapping(path = "/delete/all")
		public @ResponseBody String deleteAll() {
			clienteRepository.deleteAll();
			return "Clientes excluidos com Sucesso!";
		}

		@PutMapping(path = "/update/{id}")
		public @ResponseBody String updateClienteById(@RequestParam String nome, @RequestParam String cpf,
				@RequestParam String email, @RequestParam Date data_nascimento, @RequestParam String sexo,
				@RequestParam String nome_social, @RequestParam String apelido, @RequestParam String telefone,
				@PathVariable("id") Integer id) {

			if (clienteRepository.existsById(id)) {
								
				Cliente c = new Cliente();
				c.setIdCliente(id);
			    c.setNome(nome);
			    c.setEmail(email);
			    c.setNomeSocial(nome_social);
			    c.setApelido(apelido);
			    c.setSexo(sexo);
			    c.setDataNasc(data_nascimento);
			    c.setFone(telefone);
			    c.setCpf(cpf);
			    clienteRepository.save(c);
				return "Cliente atualizado com Sucesso!";
			}

			return "Cliente não encontrado";
		}
	

}
