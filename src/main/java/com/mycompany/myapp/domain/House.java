package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A House.
 */
@Entity
@Table(name = "house")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "size")
    private Float size;

    @Column(name = "nb_rooms")
    private Integer nbRooms;

    @Column(name = "address")
    private String address;

    @ManyToOne
    private Person owner;

    @OneToMany(mappedBy = "house")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ElectronicDevice> electronicDevices = new HashSet<>();

    @OneToMany(mappedBy = "house")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Heater> heaters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Integer getNbRooms() {
        return nbRooms;
    }

    public void setNbRooms(Integer nbRooms) {
        this.nbRooms = nbRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    public Set<ElectronicDevice> getElectronicDevices() {
        return electronicDevices;
    }

    public void setElectronicDevices(Set<ElectronicDevice> electronicDevices) {
        this.electronicDevices = electronicDevices;
    }

    public Set<Heater> getHeaters() {
        return heaters;
    }

    public void setHeaters(Set<Heater> heaters) {
        this.heaters = heaters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        House house = (House) o;
        if(house.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, house.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "House{" +
            "id=" + id +
            ", size='" + size + "'" +
            ", nbRooms='" + nbRooms + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
