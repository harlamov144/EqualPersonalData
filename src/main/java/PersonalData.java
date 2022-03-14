/**
 * Класс описывает ФИО пользователя
 */
public class PersonalData {

    private String firstName;
    private String secondName;
    private String patronymicName;

    public PersonalData(String firstName, String secondName, String patronymicName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymicName = patronymicName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }
}
