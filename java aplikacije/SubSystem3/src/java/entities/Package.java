/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kosta01856
 */
@Entity
@Table(name = "Package")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Package.findAll", query = "SELECT p FROM Package p"),
    @NamedQuery(name = "Package.findByPackageID", query = "SELECT p FROM Package p WHERE p.packageID = :packageID"),
    @NamedQuery(name = "Package.findByName", query = "SELECT p FROM Package p WHERE p.name = :name"),
    @NamedQuery(name = "Package.findByPrice", query = "SELECT p FROM Package p WHERE p.price = :price")})
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "packageID")
    private Integer packageID;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "package11")
    private Subscribtion subscribtion;

    public Package() {
    }

    public Package(Integer packageID) {
        this.packageID = packageID;
    }

    public Integer getPackageID() {
        return packageID;
    }

    public void setPackageID(Integer packageID) {
        this.packageID = packageID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Subscribtion getSubscribtion() {
        return subscribtion;
    }

    public void setSubscribtion(Subscribtion subscribtion) {
        this.subscribtion = subscribtion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packageID != null ? packageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Package)) {
            return false;
        }
        Package other = (Package) object;
        if ((this.packageID == null && other.packageID != null) || (this.packageID != null && !this.packageID.equals(other.packageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Package[ packageID=" + packageID + " ]";
    }
    
}
