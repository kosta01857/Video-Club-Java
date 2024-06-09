/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kosta01856
 */
@Entity
@Table(name = "VideoCategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VideoCategory.findAll", query = "SELECT v FROM VideoCategory v"),
    @NamedQuery(name = "VideoCategory.findByCategory", query = "SELECT v FROM VideoCategory v WHERE v.videoCategoryPK.category = :category"),
    @NamedQuery(name = "VideoCategory.findByVideo", query = "SELECT v FROM VideoCategory v WHERE v.videoCategoryPK.video = :video")})
public class VideoCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VideoCategoryPK videoCategoryPK;
    @JoinColumns({
        @JoinColumn(name = "category", referencedColumnName = "name", insertable = false, updatable = false),
        @JoinColumn(name = "category", referencedColumnName = "name", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Category category1;
    @JoinColumns({
        @JoinColumn(name = "video", referencedColumnName = "videoID", insertable = false, updatable = false),
        @JoinColumn(name = "video", referencedColumnName = "videoID", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Video video1;

    public VideoCategory() {
    }

    public VideoCategory(VideoCategoryPK videoCategoryPK) {
        this.videoCategoryPK = videoCategoryPK;
    }

    public VideoCategory(String category, int video) {
        this.videoCategoryPK = new VideoCategoryPK(category, video);
    }

    public VideoCategoryPK getVideoCategoryPK() {
        return videoCategoryPK;
    }

    public void setVideoCategoryPK(VideoCategoryPK videoCategoryPK) {
        this.videoCategoryPK = videoCategoryPK;
    }

    public Category getCategory1() {
        return category1;
    }

    public void setCategory1(Category category1) {
        this.category1 = category1;
    }

    public Video getVideo1() {
        return video1;
    }

    public void setVideo1(Video video1) {
        this.video1 = video1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (videoCategoryPK != null ? videoCategoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VideoCategory)) {
            return false;
        }
        VideoCategory other = (VideoCategory) object;
        if ((this.videoCategoryPK == null && other.videoCategoryPK != null) || (this.videoCategoryPK != null && !this.videoCategoryPK.equals(other.videoCategoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.VideoCategory[ videoCategoryPK=" + videoCategoryPK + " ]";
    }
    
}
