package magazinOnline.repository;

import magazinOnline.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByNume(String nume);

}
