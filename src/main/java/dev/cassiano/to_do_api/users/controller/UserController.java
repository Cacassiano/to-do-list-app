package dev.cassiano.to_do_api.users.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.users.User;
import dev.cassiano.to_do_api.users.dtos.CreateReturnDTO;
import dev.cassiano.to_do_api.users.dtos.UserReqDTO;
import dev.cassiano.to_do_api.users.dtos.UserRespDTO;
import dev.cassiano.to_do_api.users.repository.UserRepository;
import dev.cassiano.to_do_api.users.service.UserControllerService;

@RestController
@RequestMapping("/user")



public class UserController {
    
    @Autowired
    private UserControllerService service;
    @Autowired
    private UserRepository repository;

    @GetMapping("/{id}/get")
    public UserRespDTO getById(@PathVariable String id)
    {
        return service.getById(id);
    }

    @PostMapping("/get")
    public UserRespDTO getUser(@RequestBody UserReqDTO requisicao) throws Exception
    {
        
        User user = service.getByNome(requisicao.username());
        if(user != null)
        {
            UserRespDTO DTO = new UserRespDTO(user);
            return DTO;
        }
        throw new Exception("Usuário não existe");
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody UserReqDTO requisicao) throws Exception
    {
        if(service.updateUser(requisicao)){
            return "Usuario atualizado";
        }
        throw new Exception("Erro ao realizar a atualizacão do usuario");
    } 

    @PostMapping("/create")
    public CreateReturnDTO createUser(@RequestBody UserReqDTO requisicao) throws Exception
    {
        if(service.createUser(requisicao))
        {
            return new CreateReturnDTO(repository.findByUsername(requisicao.username()));
        }
        throw new Exception("Erro ao realizar a criação de usuario");
    }


    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody UserReqDTO requisicao) throws Exception
    {
        if(service.deleteUser(requisicao))
        {
            return "Usuario deletado com sucesso";
        }
        throw new Exception("Erro ao realizar a deleção de usuario");
    }
}
