package entities;

import entities.Rating;
import entities.Subscribtion;
import entities.Video;
import entities.View;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-16T10:14:24")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, View> view;
    public static volatile ListAttribute<User, Video> videoList;
    public static volatile SingularAttribute<User, Rating> rating;
    public static volatile SingularAttribute<User, Subscribtion> subscribtion;
    public static volatile SingularAttribute<User, String> username;

}