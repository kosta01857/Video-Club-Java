package entities;

import entities.City;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-16T00:50:22")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, City> city;
    public static volatile SingularAttribute<User, Character> sex;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, Integer> age;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}