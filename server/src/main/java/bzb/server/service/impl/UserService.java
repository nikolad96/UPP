package bzb.server.service.impl;

import bzb.server.model.User;
import bzb.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findOneById(Long id){
        return userRepository.findOneById(id);
    }

    public User findOneByEmail(String email){
        return userRepository.findOneByEmail(email);
    }

    public User findOneByUsername(String username){
        return userRepository.findOneByUsername(username);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }
    public User update(User user){
        return userRepository.save(user);
    }
    public void deleteById(Long i){
        userRepository.deleteById(i);
        return;
    }

}
