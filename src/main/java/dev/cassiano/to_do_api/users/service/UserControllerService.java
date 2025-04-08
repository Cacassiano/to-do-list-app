package dev.cassiano.to_do_api.users.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import dev.cassiano.to_do_api.users.User;
import dev.cassiano.to_do_api.users.dtos.UserReqDTO;
import dev.cassiano.to_do_api.users.dtos.UserRespDTO;
import dev.cassiano.to_do_api.users.repository.UserRepository;

@Service
public class UserControllerService {

    @Autowired
    private UserRepository repository;
    
    public UserRespDTO getById(String id)
    {
        if(repository.existsById(id))
        {
            return new UserRespDTO(repository.getReferenceById(id));
        }
        return new UserRespDTO(new User());
    }

    private boolean saveUser(User user) throws Exception
    {
        repository.save(user); 
        return true;    
    }

    public User getByNome(String nome)
    {
        return repository.findByUsername(nome);
    }

    public boolean updateUser(@RequestBody UserReqDTO req) throws Exception
    {
        if(repository.existsByUsernameAndSenha(req.username(), req.senha()))
        {
            User nUser = repository.findByUsername(req.username());

            nUser.setEmail(req.email());
            nUser.setCargo(req.cargo());
            
            String encodeSenha = req.senha();
            nUser.setSenha(encodeSenha);
            
            return saveUser(nUser);
        }
        throw new Exception("Usuário não existe");
    }

    public boolean createUser(UserReqDTO req) throws Exception
    {
        if(!repository.existsByUsername(req.username()))
        {
            User nUser = new User(req);

            String encodeSenha =req.senha();
            nUser.setSenha(encodeSenha);
            
            return saveUser(nUser);
        }
        throw new Exception("Usuário já existe");
    }
    
    public boolean deleteUser(@RequestBody UserReqDTO req) throws Exception
    {
        if(repository.existsByUsername(req.username()))
        {
            try {

                User nUser = repository.findByUsername(req.username());
                repository.delete(nUser);
                return true;
            }catch(Exception e) {
                throw e;
            }
        }
        throw new Exception("Usuário não existe");
    }
}
