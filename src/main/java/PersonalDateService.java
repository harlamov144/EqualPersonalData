import exception.BusinessException;

/**
 * Сервис который проверяет два объекта типа PersonalData на эквивалентность путем посимвольного сравнения элементов.
 * На выходе получить результат проверки и количество не совпадающих символов в элементах объекта.
 *
 * Условия:
 * - необходимо исключать знаки препинания, символы, не являющиеся буквами и цифрами (например, N@ ., -="+-/*;:?()}{[]|!'#$%^&_\<>~ и пр.);
 * - эквивалентными считаются строчные и прописные символы;
 * - эквивалентными считаются элементы, если количество не совпадающих символов в поле не превышает одного (включая один измененный,
 * или один пропущенный, или один добавленный символ) или в поле имеются не более двух переставленных символов;
 * - поддержка латиницы;
 * - для одного поля объекта длина не должна превышать 50 символов.
 */
public class PersonalDateService {

    public Result equalsPersonalDate(PersonalData firstPersonalData, PersonalData secondPersonalData) throws BusinessException {
        checkParamsLength(firstPersonalData);
        checkParamsLength(secondPersonalData);

        int firstNameErrorCount = equalStringPersonalDate(firstPersonalData.getFirstName(), secondPersonalData.getFirstName());
        int secondNameErrorCount = equalStringPersonalDate(firstPersonalData.getSecondName(), secondPersonalData.getSecondName());
        int patronymicNameErrorCount = equalStringPersonalDate(firstPersonalData.getPatronymicName(), secondPersonalData.getPatronymicName());

        return new Result(firstNameErrorCount <= 1 && secondNameErrorCount <= 1 && patronymicNameErrorCount <= 1,
                firstNameErrorCount + secondNameErrorCount + patronymicNameErrorCount);
    }

    public int equalStringPersonalDate(String firstDate, String secondDate) {
        firstDate = firstDate.replaceAll("[^A-Za-zА-Яа-я0-9]", "").toLowerCase();
        secondDate = secondDate.replaceAll("[^A-Za-zА-Яа-я0-9]", "").toLowerCase();
        int fdLength = firstDate.length();
        int sdLength = secondDate.length();

        int countOfErSym = 0;

        if (firstDate.equals(secondDate)) {
            return 0;
        }

        char[] firstMas = firstDate.toCharArray();
        char[] secondMas = secondDate.toCharArray();

        if (fdLength == sdLength) {
            for (int i = 0; i <= fdLength - 1; i++) {
                if (firstMas[i] != secondMas[i]) {
                    if (i + 1 < sdLength && firstMas[i] == secondMas[i + 1] && firstMas[i + 1] == secondMas[i]) {
                        char temp = firstMas[i];
                        firstMas[i] = firstMas[i + 1];
                        firstMas[i + 1] = temp;
                    }
                    countOfErSym++;
                }
            }
        } else {
            int length = Math.min(fdLength, sdLength);
            for (int i = 0; i <= length - 2; i++) {
                if (firstMas[i] != secondMas[i]) {
                    if (firstMas[i] == secondMas[i + 1] && firstMas[i + 1] == secondMas[i]) {
                        char temp = firstMas[i];
                        firstMas[i] = firstMas[i + 1];
                        firstMas[i + 1] = temp;
                        countOfErSym++;
                    }
                }
            }

            if(String.valueOf(firstMas).contains(String.valueOf(secondMas)) || String.valueOf(secondMas).contains(String.valueOf(firstMas)) ) {
                countOfErSym = countOfErSym + Math.abs(fdLength - sdLength);
            }else{
                if(fdLength > sdLength){
                    countOfErSym = getCountOfErSym(fdLength, countOfErSym, secondMas, firstMas);
                }else{
                    countOfErSym = getCountOfErSym(sdLength, countOfErSym, firstMas, secondMas);
                }
            }
        }
        return countOfErSym;
    }

    private int getCountOfErSym(int sdLength, int countOfErSym, char[] firstMas, char[] secondMas) {
        for(int i = 0; i < sdLength; i++){
            if(!String.valueOf(firstMas).contains(String.valueOf(secondMas[i]))){
                countOfErSym++;
            }else{
                firstMas[String.valueOf(firstMas).indexOf(String.valueOf(secondMas[i]))] = '*';
                secondMas[i] = '*';
            }
        }
        return countOfErSym;
    }

    private void checkParamsLength(PersonalData personalData) throws BusinessException {
        if (personalData.getFirstName().length() > 50 || personalData.getSecondName().length() > 50 ||
                personalData.getPatronymicName().length() > 50) {
            throw new BusinessException("Длина поля больше 50 символов.");
        }
    }
}
