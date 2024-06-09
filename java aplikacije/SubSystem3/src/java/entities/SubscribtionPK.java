/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kosta01856
 */
@Embeddable
public class SubscribtionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "package")
    private int package1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user")
    private String user;

    public SubscribtionPK() {
    }

    public SubscribtionPK(int package1, String user) {
        this.package1 = package1;
        this.user = user;
    }

    public int getPackage1() {
        return package1;
    }

    public void setPackage1(int package1) {
        this.package1 = package1;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) package1;
        hash += (user != null ? user.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubscribtionPK)) {
            return false;
        }
        SubscribtionPK other = (SubscribtionPK) object;
        if (this.package1 != other.package1) {
            return false;
        }
        if ((this.user == null && other.user != null) || (this.user != null && !this.user.equals(other.user))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SubscribtionPK[ package1=" + package1 + ", user=" + user + " ]";
    }
    
}
