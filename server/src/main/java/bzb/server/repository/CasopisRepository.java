package bzb.server.repository;

import bzb.server.model.Casopis;
import bzb.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasopisRepository extends JpaRepository<Casopis, Long> {
    Casopis findOneById(Long Id);
    List<Casopis> findAll();
    void deleteById(Long aLong);
}
