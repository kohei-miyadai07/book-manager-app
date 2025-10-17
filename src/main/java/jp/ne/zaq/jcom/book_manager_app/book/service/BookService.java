package jp.ne.zaq.jcom.book_manager_app.book.service;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import jp.ne.zaq.jcom.book_manager_app.book.model.Book;
import jp.ne.zaq.jcom.book_manager_app.book.repository.BookRepository;
import jp.ne.zaq.jcom.book_manager_app.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    /**
     * 全書籍情報を取得
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * IDで書籍情報を取得
     */
    public Book findById(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
    }

    /**
     * 新規書籍を登録
     */
    public Book create(Book book) {
        // ここで重複チェックなどのビジネスロジックを追加可能
        return bookRepository.save(book);
    }

    /**
     * 書籍情報を更新
     */
    public Book update(UUID bookId, Book updatedBook) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        // 楽観的ロックのバージョンチェックはJPAが自動で行う
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setJanCode(updatedBook.getJanCode());
        existingBook.setVersion(updatedBook.getVersion()); // バージョンを設定

        return bookRepository.save(existingBook);
    }

    /**
     * 書籍情報を削除
     */
    public void delete(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        bookRepository.delete(book);
    }

}
