package jp.ne.zaq.jcom.book_manager_app.book.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ne.zaq.jcom.book_manager_app.book.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
