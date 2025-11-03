package jp.ne.zaq.jcom.book_manager_app.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.ne.zaq.jcom.book_manager_app.auth.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	Optional<UserAccount> findByUserName(String userName);

}
