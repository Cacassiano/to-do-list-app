package dev.cassiano.to_do_api.controllers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import dev.cassiano.to_do_api.DTOs.UserReqDTO;
import dev.cassiano.to_do_api.DTOs.UserRespDTO;
import dev.cassiano.to_do_api.entitys.User;
import dev.cassiano.to_do_api.repositorys.UserRepository;

@Service
public class UserService {

    public List<UserRespDTO> showAll()
    {
        List<UserRespDTO> all = repository.findAll().stream().map(UserRespDTO::new).toList();
        return all;
    }

    @Autowired
    private UserRepository repository;

    public UserRespDTO getById(Long id)
    {
        if(repository.existsById(id))
        {
            return new UserRespDTO(repository.getReferenceById(id));
        }
        return new UserRespDTO(new User());
    }

    private boolean saveUser(User user)
    {
        try{
            repository.save(user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public User getByNomeAndEmail(String nome, String email)
    {
        return repository.findByNomeAndEmail(nome, email);
    }

    public boolean updateUser(UserReqDTO req)
    {
        if(repository.existsById(req.id()))
        {
            User nUser = new User(req);
            nUser.setId(req.id());
            return saveUser(nUser);
        }
        return false;   
    }

    public boolean createUser(UserReqDTO req)
    {
        if(!repository.existsByNomeAndEmail(req.nome(), req.email()))
        {
            User nUser = new User(req);
            return saveUser(nUser);
        }
        return false;
    }
    
    public boolean deleteUser(@RequestBody UserReqDTO req)
    {
        if(repository.existsByNomeAndEmail(req.nome(), req.email()))
        {
            try {

                User nUser = new User(req);
                nUser.setId(req.id());
                repository.delete(nUser);
                return true;

            }catch(Exception e) {
                return false;
            }
        }
        return false;
    }
}
