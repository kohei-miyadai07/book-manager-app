package jp.ne.zaq.jcom.book_manager_app.book.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.zaq.jcom.book_manager_app.book.model.Book;
import jp.ne.zaq.jcom.book_manager_app.book.service.BookService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    // 書籍情報一覧表示
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    // 書籍情報詳細表示
    @GetMapping("/{bookId}/details")
    public String details(@PathVariable UUID bookId, Model model) {
        Book book = bookService.findById(bookId);
        model.addAttribute("book", book);
        return "book/details";
    }

    // 新規登録フォーム表示
    @GetMapping("/new")
    public String newForm(Model model) {
        if (!model.containsAttribute("bookForm")) {
            model.addAttribute("bookForm", new BookForm());
        }
        return "book/new";
    }

    // 新規登録実行
    @PostMapping("/new/register-book")
    public String registerBook(@Validated @ModelAttribute BookForm bookForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("bookForm", bookForm);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bookForm", result);
            return "redirect:/books/new";
        }

        Book newBook = new Book(
                bookForm.getTitle(),
                bookForm.getDescription(),
                bookForm.getIsbn(),
                bookForm.getJanCode()
        );
        bookService.create(newBook);
        return "redirect:/books"; // 書籍情報一覧へリダイレクト
    }

    // 編集フォーム表示
    @GetMapping("/{bookId}/edit")
    public String editForm(@PathVariable UUID bookId, Model model) {
        if (!model.containsAttribute("bookForm")) {
            Book book = bookService.findById(bookId);
            BookForm form = new BookForm();
            form.setId(book.getId());
            form.setTitle(book.getTitle());
            form.setDescription(book.getDescription());
            form.setIsbn(book.getIsbn());
            form.setJanCode(book.getJanCode());
            form.setVersion(book.getVersion());
            model.addAttribute("bookForm", form);
        }
        model.addAttribute("bookId", bookId);
        return "book/edit";
    }

    // 更新実行
    @PostMapping("/{bookId}/edit/update-book")
    public String updateBook(@PathVariable UUID bookId,
                             @Validated @ModelAttribute BookForm bookForm,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("bookForm", bookForm);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bookForm", result);
            return "redirect:/books/" + bookId + "/edit";
        }
        
        Book updatedBook = new Book(
                bookForm.getTitle(),
                bookForm.getDescription(),
                bookForm.getIsbn(),
                bookForm.getJanCode()
        );
        updatedBook.setVersion(bookForm.getVersion());
        
        bookService.update(bookId, updatedBook);
        return "redirect:/books/" + bookId + "/details"; // 詳細画面へリダイレクト
    }

    // 削除実行
    @PostMapping("/{bookId}/edit/delete-book")
    public String deleteBook(@PathVariable UUID bookId) {
        bookService.delete(bookId);
        return "redirect:/books"; // 一覧画面へリダイレクト
    }

}
