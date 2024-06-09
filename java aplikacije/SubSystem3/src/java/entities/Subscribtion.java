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
@Table(name = "Subscribtion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subscribtion.findAll", query = "SELECT s FROM Subscribtion s"),
    @NamedQuery(name = "Subscribtion.findByDate", query = "SELECT s FROM Subscribtion s WHERE s.date = :date"),
    @NamedQuery(name = "Subscribtion.findByPrice", query = "SELECT s FROM Subscribtion s WHERE s.price = :price"),
    @NamedQuery(name = "Subscribtion.findByPackage1", query = "SELECT s FROM Subscribtion s WHERE s.subscribtionPK.package1 = :package1"),
    @NamedQuery(name = "Subscribtion.findByUser", query = "SELECT s FROM Subscribtion s WHERE s.subscribtionPK.user = :user")})
public class Subscribtion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SubscribtionPK subscribtionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @JoinColumns({
        @JoinColumn(name = "package", referencedColumnName = "packageID", insertable = false, updatable = false),
        @JoinColumn(name = "package", referencedColumnName = "packageID", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Package package11;
    @JoinColumns({
        @JoinColumn(name = "user", referencedColumnName = "username", insertable = false, updatable = false),
        @JoinColumn(name = "user", referencedColumnName = "username", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private User user1;

    public Subscribtion() {
    }

    public Subscribtion(SubscribtionPK subscribtionPK) {
        this.subscribtionPK = subscribtionPK;
    }

    public Subscribtion(SubscribtionPK subscribtionPK, Date date, int price) {
        this.subscribtionPK = subscribtionPK;
        this.date = date;
        this.price = price;
    }

    public Subscribtion(int package1, String user) {
        this.subscribtionPK = new SubscribtionPK(package1, user);
    }

    public SubscribtionPK getSubscribtionPK() {
        return subscribtionPK;
    }

    public void setSubscribtionPK(SubscribtionPK subscribtionPK) {
        this.subscribtionPK = subscribtionPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Package getPackage11() {
        return package11;
    }

    public void setPackage11(Package package11) {
        this.package11 = package11;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subscribtionPK != null ? subscribtionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subscribtion)) {
            return false;
        }
        Subscribtion other = (Subscribtion) object;
        if ((this.subscribtionPK == null && other.subscribtionPK != null) || (this.subscribtionPK != null && !this.subscribtionPK.equals(other.subscribtionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Subscribtion[ subscribtionPK=" + subscribtionPK + " ]";
    }
    
}
