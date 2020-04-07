package tech.college.termproject.other;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Register extends RealmObject {


    public static final String PROPERTY_Email = "email";

    @PrimaryKey
    @Required
    public String email;
    public String name;
    public String password;
    public String phone;
    public String age;

}
