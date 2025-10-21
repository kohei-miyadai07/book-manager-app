package jp.ne.zaq.jcom.book_manager_app.book.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Book {
	@Id
	@GeneratedValue(generator = "uuid2")
    private UUID id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, length = 40)
    private String isbn;

    @Column(unique = true, length = 40)
    private String janCode;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updateAt;

    @Version
    @Column(nullable = false)
    private Integer version;

    public Book(String title, String description, String isbn, String janCode) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.janCode = janCode;
    }

}
