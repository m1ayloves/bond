package cyou.mayloves.bond.handler;

import cyou.mayloves.bond.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(10000)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleGlobalException(Exception e) {
        Class<? extends Exception> exception = e.getClass();
        String name = exception.getName();
        String message = String.format("%s: %s", name, e.getLocalizedMessage());
        log.error("全局异常信息 ex={}", message, e);
        return Result.error(message);
    }

}
