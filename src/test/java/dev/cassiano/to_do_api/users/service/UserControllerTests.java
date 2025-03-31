package dev.cassiano.to_do_api.users.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.cassiano.to_do_api.users.User;
import dev.cassiano.to_do_api.users.controller.UserController;
import dev.cassiano.to_do_api.users.dtos.UserReqDTO;
import dev.cassiano.to_do_api.users.repository.UserRepository;
 
@SpringBootTest
public class UserControllerTests {
    
    UserReqDTO requisicao;

    @Autowired
    UserController controller;

    @Autowired
    UserRepository repository;

    @BeforeEach
    void setup()
    {
        if(!repository.existsByUsername("testandoNome"))
        {
            requisicao = new UserReqDTO("testeSenha", "testandoNome", "testão@email.com", "admin");
            User cassiano = new User(requisicao);
            repository.save(cassiano);
            
        }
        
        if(repository.existsByUsername("admin"))
        {
            requisicao = new UserReqDTO("admin", "admin", "admin@gmail.com", "admin");
            User admin = repository.findByUsername("admin");
            repository.delete(admin);
        }

    } 


    @Test
    @DisplayName("A criação deve ser bem sucedido") 
    void createUserCase1() throws Exception
    {
        requisicao = new UserReqDTO("admin", "admin", "admin@gmail.com", "admin");
        assertEquals("Usuario criado com sucesso", controller.createUser(requisicao));
    }


    @Test
    @DisplayName("A criação não deve ser bem sucedida")
    void createUserCase2() 
    {
        requisicao = new UserReqDTO("testeSenha", "testandoNome", "testão@email.com", "admin");
        try {
            controller.createUser(requisicao);
        } catch (Exception e) {
            assertEquals("Usuário já existe", e.getMessage());
        }
    }

    @Test
    @DisplayName("a atualização deve dar certo")
    void updateUserCase1() throws Exception
    {
        requisicao = new UserReqDTO("testeSenha", "testandoNome", "testão@email.com", "user");
        assertEquals("Usuario atualizado", controller.updateUser(requisicao));
    }

    @Test
    @DisplayName("A atualização não deve ser bem sucedida")
    void updateUserCase2() 
    {
        requisicao = new UserReqDTO("testeSenha", "na", "testão@email.com", "admin");
        try {
            controller.updateUser(requisicao);
        } catch (Exception e) {
            assertEquals("Usuário não existe", e.getMessage());
        }
    }


    @Test
    @DisplayName("a deleção deve dar certo")
    void deleteUserCase1() throws Exception
    {
        requisicao = new UserReqDTO("testeSenha", "testandoNome", "testão@email.com", "admin");
        assertEquals("Usuario deletado com sucesso", controller.deleteUser(requisicao));
    }

    @Test
    @DisplayName("A deleçõa não deve ser bem sucedida")
    void deleteUserCase2() 
    {
        requisicao = new UserReqDTO("minhaSenha", "p", "cassiano@gmail.com", "user");
        try {
            controller.deleteUser(requisicao);
        } catch (Exception e) {
            assertEquals("Usuário não existe", e.getMessage());
        }
    }

}
