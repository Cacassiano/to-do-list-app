package dev.cassiano.to_do_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cassiano.to_do_api.DTOs.UserReqDTO;
import dev.cassiano.to_do_api.DTOs.UserRespDTO;
import dev.cassiano.to_do_api.controllers.service.UserService;
import dev.cassiano.to_do_api.entitys.User;

@RestController
@RequestMapping("/user")

public class UserController {
    
    @Autowired
    private UserService service = new UserService();

    @GetMapping("/{id}/get")
    public UserRespDTO getById(@PathVariable Long id)
    {
        return service.getById(id);
    }

    @PostMapping("/get")
    public UserRespDTO getUser(@RequestBody UserReqDTO requisicao)
    {
        
        User user = service.getByNomeAndEmail(requisicao.nome(), requisicao.email());
        if(user != null)
        {
            UserRespDTO DTO = new UserRespDTO(user);
            return DTO;
        }
        return new UserRespDTO(new User());
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody UserReqDTO requisicao)
    {
        if(service.updateUser(requisicao)){
            return "Usuario atualizado";
        }
        return "Erro ao realizar a operação";
    } 

    @PostMapping("/create")
    public String createUser(@RequestBody UserReqDTO requisicao)
    {
        if(service.createUser(requisicao))
        {
            return "Usuario criado com sucesso";
        }
        return "Erro ao realizar a criação de usuario";
    }


    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody UserReqDTO requisicao)
    {
        if(service.deleteUser(requisicao))
        {
            return "Usuario deletado com sucesso";
        }
        return "Erro ao realizar a deleção de usuario";
    }

    @GetMapping("/test/todos")
    public List<UserRespDTO> verTodos()
    {
        return service.showAll();
    }
}
