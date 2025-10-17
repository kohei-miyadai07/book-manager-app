package jp.ne.zaq.jcom.book_manager_app.book.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class BookForm {
	private UUID id;

    @NotBlank(message = "書籍名は必須です")
    @Size(min = 1, max = 300, message = "書籍名は1文字から300文字までです")
    private String title;

    @Size(max = 500, message = "説明は500文字までです")
    private String description;

    // ISBNコードの正規表現バリデーション
    @NotBlank(message = "ISBNコードは必須です")
    @Pattern(regexp = "^978-?\\d-?\\d{2}-?\\d{6}-?\\d$", message = "ISBNコードの形式が不正です")
    private String isbn;

    // 書籍JANコードの正規表現バリデーション
    @NotBlank(message = "書籍JANコードは必須です")
    @Pattern(regexp = "^(978|979)\\d{10}$", message = "書籍JANコードの形式が不正です")
    private String janCode;

    private Integer version; // 更新時に必要

}
