package bzb.server.repository;

import bzb.server.model.Casopis;
import bzb.server.model.Rad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RadRepository  extends JpaRepository<Rad, Long> {

    Rad findOneById(Long Id);
    Rad findOneByNaziv(String naslov);
    List<Rad> findAll();
    void deleteById(Long aLong);
}
