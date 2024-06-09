/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kosta01856
 */
@Entity
@Table(name = "View")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "View.findAll", query = "SELECT v FROM View v"),
    @NamedQuery(name = "View.findByStartTime", query = "SELECT v FROM View v WHERE v.startTime = :startTime"),
    @NamedQuery(name = "View.findByWatchTime", query = "SELECT v FROM View v WHERE v.watchTime = :watchTime"),
    @NamedQuery(name = "View.findByVideo", query = "SELECT v FROM View v WHERE v.viewPK.video = :video"),
    @NamedQuery(name = "View.findByUser", query = "SELECT v FROM View v WHERE v.viewPK.user = :user"),})
public class View implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ViewPK viewPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_time")
    private int startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "watch_time")
    private int watchTime;
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

    public View() {
    }

    public View(ViewPK viewPK) {
        this.viewPK = viewPK;
    }

    public View(ViewPK viewPK, int startTime, int watchTime) {
        this.viewPK = viewPK;
        this.startTime = startTime;
        this.watchTime = watchTime;
    }

    public View(int video, String user) {
        this.viewPK = new ViewPK(video, user);
    }

    public ViewPK getViewPK() {
        return viewPK;
    }

    public void setViewPK(ViewPK viewPK) {
        this.viewPK = viewPK;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(int watchTime) {
        this.watchTime = watchTime;
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
        hash += (viewPK != null ? viewPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof View)) {
            return false;
        }
        View other = (View) object;
        if ((this.viewPK == null && other.viewPK != null) || (this.viewPK != null && !this.viewPK.equals(other.viewPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.View[ viewPK=" + viewPK + " ]";
    }
    
}
