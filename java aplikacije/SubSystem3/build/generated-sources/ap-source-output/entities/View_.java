package entities;

import entities.User;
import entities.Video;
import entities.ViewPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-16T10:14:24")
@StaticMetamodel(View.class)
public class View_ { 

    public static volatile SingularAttribute<View, User> user1;
    public static volatile SingularAttribute<View, ViewPK> viewPK;
    public static volatile SingularAttribute<View, Video> video1;
    public static volatile SingularAttribute<View, Integer> watchTime;
    public static volatile SingularAttribute<View, Integer> startTime;

}