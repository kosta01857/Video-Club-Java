/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kosta01856
 */
@Entity
@Table(name = "Rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rating.findAll", query = "SELECT r FROM Rating r"),
    @NamedQuery(name = "Rating.findByDate", query = "SELECT r FROM Rating r WHERE r.date = :date"),
    @NamedQuery(name = "Rating.findByGrade", query = "SELECT r FROM Rating r WHERE r.grade = :grade"),
    @NamedQuery(name = "Rating.findByVideo", query = "SELECT r FROM Rating r WHERE r.ratingPK.video = :video"),
    @NamedQuery(name = "Rating.findByUser", query = "SELECT r FROM Rating r WHERE r.ratingPK.user = :user"),
    @NamedQuery(name = "Rating.findByVideoAndUser", query = "SELECT r FROM Rating r WHERE r.ratingPK.user = :user and r.ratingPK.video = :video")
})
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RatingPK ratingPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grade")
    private int grade;
    @JoinColumns({
        @JoinColumn(name = "user", referencedColumnName = "username", insertable = false, updatable = false),
        @JoinColumn(name = "user", referencedColumnName = "username", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private User user1;
    @JoinColumns({
        @JoinColumn(name = "video", referencedColumnName = "videoID", insertable = false, updatable = false),
        @JoinColumn(name = "video", referencedColumnName = "videoID", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Video video1;

    public Rating() {
    }

    public Rating(RatingPK ratingPK) {
        this.ratingPK = ratingPK;
    }

    public Rating(RatingPK ratingPK, Date date, int grade) {
        this.ratingPK = ratingPK;
        this.date = date;
        this.grade = grade;
    }

    public Rating(int video, String user) {
        this.ratingPK = new RatingPK(video, user);
    }

    public RatingPK getRatingPK() {
        return ratingPK;
    }

    public void setRatingPK(RatingPK ratingPK) {
        this.ratingPK = ratingPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
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
        hash += (ratingPK != null ? ratingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingPK == null && other.ratingPK != null) || (this.ratingPK != null && !this.ratingPK.equals(other.ratingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rating[ ratingPK=" + ratingPK + " ]";
    }
    
}
