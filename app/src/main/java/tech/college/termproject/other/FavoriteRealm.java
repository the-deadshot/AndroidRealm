package tech.college.termproject.other;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FavoriteRealm extends RealmObject {

    public String email;
    public String name;
    public String status;
    public int image;
}
