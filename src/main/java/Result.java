/**
 * Класс описывает результат сверки данных пользователя, где
 *  isValid - эквивалентны ли объекты
 *  errCnt - количество не совпадающих символов в элементах объекта
 */
public class Result {

    private boolean isValid;

    private int errCnt;

    public Result(boolean isValid, int errCnt) {
        this.isValid = isValid;
        this.errCnt = errCnt;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getErrCnt() {
        return errCnt;
    }

    public void setErrCnt(int errCnt) {
        this.errCnt = errCnt;
    }
}
