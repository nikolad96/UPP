package bzb.server.repository;

import bzb.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

        User findOneById(Long Id);
        User findOneByEmail(String email);
        User findOneByUsername(String username);
        void deleteById(Long aLong);

}
