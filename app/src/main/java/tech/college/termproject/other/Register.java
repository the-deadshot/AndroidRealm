package tech.college.termproject.other;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Register extends RealmObject {


    public static final String PROPERTY_Email = "email";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_PASSWORD = "password";
    public static final String PROPERTY_PHONE = "phone";
    public static final String PROPERTY_AGE = "age";

    @PrimaryKey
    @Required
    public  String email;
    public String name;
    public String password;
    public String phone;
    public String age;

}
