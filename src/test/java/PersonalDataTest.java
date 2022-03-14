import exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Класс для покрытия тестами {@link PersonalDateService}
 */
public class PersonalDataTest {

    PersonalDateService service = new PersonalDateService();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void equalPersonalDateTest() throws BusinessException {
        PersonalData one = new PersonalData("Игорь", "Петров", "Иванович");
        PersonalData two = new PersonalData("Игорь", "Петров", "Иванович");
        Result result = service.equalsPersonalDate(one, two);

        assertEquals(0, result.getErrCnt());
        assertTrue( result.isValid());
    }

    @Test
    public void equalPersonalDateWithOneMistakeInFirstNameTest() throws BusinessException {
        PersonalData one = new PersonalData("Игорь", "Петров", "Иванович");
        PersonalData two = new PersonalData("Игор", "Петров", "Иванович");
        Result result = service.equalsPersonalDate(one, two);

        assertEquals(1, result.getErrCnt());
        assertTrue( result.isValid());
    }

    @Test
    public void notEqualPersonalDateWithTwoMistakeTest() throws BusinessException {
        PersonalData one = new PersonalData("Игорь", "Петров", "Иванович");
        PersonalData two = new PersonalData("Игор", "Петорв", "Иванович");
        Result result = service.equalsPersonalDate(one, two);

        assertEquals(2, result.getErrCnt());
        assertTrue( result.isValid());
    }

    @Test
    public void withParamEqualLengthTest(){

        assertEquals(2, service.equalStringPersonalDate("ohsre", "horse"));
        assertEquals(2, service.equalStringPersonalDate("horse", "ohsre"));

        assertEquals(1, service.equalStringPersonalDate("олшадь", "лошадь"));
        assertEquals(1, service.equalStringPersonalDate("лошадь", "олшадь"));

        assertEquals(2, service.equalStringPersonalDate("олашдь", "лошадь"));
        assertEquals(2, service.equalStringPersonalDate("лошадь", "олашдь"));

        assertEquals(1, service.equalStringPersonalDate("horse", "ohrse"));
        assertEquals(1, service.equalStringPersonalDate("ohrse", "horse"));

        assertEquals(0, service.equalStringPersonalDate("horse", "horse"));
        assertEquals(0, service.equalStringPersonalDate("", ""));

        assertEquals(2, service.equalStringPersonalDate("ty", "lz"));
        assertEquals(2, service.equalStringPersonalDate("lz", "ty"));

        assertEquals(1, service.equalStringPersonalDate("lz", "zl"));
        assertEquals(1, service.equalStringPersonalDate("zl", "lz"));

        assertEquals(6, service.equalStringPersonalDate("лошадь", "horses"));
    }

    @Test
    public void withParamDifferentLengthTest(){
        assertEquals(1, service.equalStringPersonalDate("horse", "horses"));
        assertEquals(1, service.equalStringPersonalDate("horses", "horse"));

        assertEquals(1, service.equalStringPersonalDate("orse", "horse"));
        assertEquals(1, service.equalStringPersonalDate("horse", "orse"));

        assertEquals(2, service.equalStringPersonalDate("ors", "horse"));
        assertEquals(2, service.equalStringPersonalDate("horse", "ors"));

        assertEquals(1, service.equalStringPersonalDate("h", ""));
        assertEquals(1, service.equalStringPersonalDate("", "h"));

        assertEquals(2, service.equalStringPersonalDate("ho", ""));
        assertEquals(2, service.equalStringPersonalDate("", "ho"));

        assertEquals(2, service.equalStringPersonalDate("zlo", "lz"));
        assertEquals(2, service.equalStringPersonalDate("zl", "lzo"));
    }

    @Test
    public void withDifficultCaseTest(){
        assertEquals(3, service.equalStringPersonalDate("horse", "ohsroe"));
        assertEquals(3, service.equalStringPersonalDate("ohsroe", "horse"));

        assertEquals(4, service.equalStringPersonalDate("ohsroee", "horse"));
        assertEquals(4, service.equalStringPersonalDate("horse", "ohsroee"));

        assertEquals(5, service.equalStringPersonalDate("ohsroeee", "horse"));
        assertEquals(5, service.equalStringPersonalDate("horse", "ohsroeee"));

        assertEquals(5, service.equalStringPersonalDate("лошадь", "игра"));
        assertEquals(5, service.equalStringPersonalDate("игра", "лошадь"));

    }

    @Test
    public void checkReplaceNotValidSymbolsTest(){
        assertEquals(0, service.equalStringPersonalDate("--horse!", "//horse&*"));

    }

    @Test
    public void checkTooLongFirstNameExceptionTest() throws BusinessException {
        exceptionRule.expect(BusinessException.class);
        exceptionRule.expectMessage("Длина поля больше 50 символов.");

        String firstName = StringUtils.repeat("a", 51);
        PersonalData firstDate = new PersonalData(firstName, "asd", "fgh");
        PersonalData secondDate = new PersonalData("qwe", "asd", "fgh");
        service.equalsPersonalDate(firstDate, secondDate);
    }

    @Test
    public void checkTooLongSecondNameExceptionTest() throws BusinessException {
        exceptionRule.expect(BusinessException.class);
        exceptionRule.expectMessage("Длина поля больше 50 символов.");

        String secondName = StringUtils.repeat("a", 51);
        PersonalData firstDate = new PersonalData("qwe", secondName, "fgh");
        PersonalData secondDate = new PersonalData("qwe", "asd", "fgh");
        service.equalsPersonalDate(firstDate, secondDate);
    }

    @Test
    public void checkTooLongPatronymicNameExceptionTest() throws BusinessException {
        exceptionRule.expect(BusinessException.class);
        exceptionRule.expectMessage("Длина поля больше 50 символов.");

        String patronymicName = StringUtils.repeat("a", 51);
        PersonalData firstDate = new PersonalData("qwe", "asd", patronymicName);
        PersonalData secondDate = new PersonalData("qwe", "asd", "fgh");
        service.equalsPersonalDate(firstDate, secondDate);
    }
}
