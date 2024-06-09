package entities;

import entities.Rating;
import entities.User;
import entities.View;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-16T10:14:24")
@StaticMetamodel(Video.class)
public class Video_ { 

    public static volatile SingularAttribute<Video, View> view;
    public static volatile SingularAttribute<Video, Rating> rating;
    public static volatile SingularAttribute<Video, Integer> videoID;
    public static volatile SingularAttribute<Video, User> user;

}