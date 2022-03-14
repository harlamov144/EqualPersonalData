package exception;

/**
 * Бизнесовое исключение
 */
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}
