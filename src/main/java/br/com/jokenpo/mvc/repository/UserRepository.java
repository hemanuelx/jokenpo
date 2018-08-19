package br.com.jokenpo.mvc.repository;

import br.com.jokenpo.mvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
