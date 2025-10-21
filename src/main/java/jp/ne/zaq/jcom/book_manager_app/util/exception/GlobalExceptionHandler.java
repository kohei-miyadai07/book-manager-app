package jp.ne.zaq.jcom.book_manager_app.util.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    // ResourceNotFoundExceptionは@ResponseStatusで処理されますが、
    // ここでその他のカスタム例外や予期しないエラーを処理し、/error.htmlへルーティングできます。

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex) {
        // ロギング処理など
        return "error"; // /error.html を表示
    }
}
