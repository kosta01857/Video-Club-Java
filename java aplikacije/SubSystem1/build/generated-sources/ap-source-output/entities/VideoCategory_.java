package entities;

import entities.Category;
import entities.Video;
import entities.VideoCategoryPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2024-01-15T23:36:03")
@StaticMetamodel(VideoCategory.class)
public class VideoCategory_ { 

    public static volatile SingularAttribute<VideoCategory, Video> video1;
    public static volatile SingularAttribute<VideoCategory, VideoCategoryPK> videoCategoryPK;
    public static volatile SingularAttribute<VideoCategory, Category> category1;

}