package hanly.address.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import hanly.address.util.LocalDateAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 模型类 成员
 */
public class Person {

    private final StringProperty name;
    private final StringProperty identity;
    private final StringProperty street;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;

    /**
     * 默认构造函数
     */
    public Person() {
        this(null, null);
    }

    /**
     * 采用部分初始值构造
     *
     * @param name
     * @param identity
     */
    public Person(String name, String identity) {
        this.name = new SimpleStringProperty(name);
        this.identity = new SimpleStringProperty(identity);

        // 填充无效内容作为测试及提示
        this.street = new SimpleStringProperty("no street");
        this.postalCode = new SimpleIntegerProperty(261000);
        this.city = new SimpleStringProperty("no city");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1900, 1, 1));
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getIdentity() {
        return identity.get();
    }

    public void setIdentity(String identity) {
        this.identity.set(identity);
    }

    public StringProperty identityProperty() {
        return identity;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }
}
