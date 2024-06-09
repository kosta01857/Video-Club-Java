package entities;

import entities.Rating;
import entities.User;
import entities.VideoCategory;
import entities.View;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-15T23:36:03")
@StaticMetamodel(Video.class)
public class Video_ { 

    public static volatile SingularAttribute<Video, View> view;
    public static volatile SingularAttribute<Video, VideoCategory> videoCategory;
    public static volatile SingularAttribute<Video, Date> uploadDate;
    public static volatile SingularAttribute<Video, Double> length;
    public static volatile SingularAttribute<Video, Rating> rating;
    public static volatile SingularAttribute<Video, Integer> videoID;
    public static volatile SingularAttribute<Video, String> title;
    public static volatile SingularAttribute<Video, User> user;

}