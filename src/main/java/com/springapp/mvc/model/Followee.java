package com.springapp.mvc.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FOLLOWEE")
public class Followee implements Serializable {

    @EmbeddedId
    private FolloweeId id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FOLLOWEE_ID", insertable = false, updatable = false)
    private Spitter followee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPITTER_ID", insertable = false, updatable = false)
    private Spitter spitter;

    protected Followee(){
    }

    public Followee(Spitter spitter, Spitter followee) {
        this.followee = followee;
        this.spitter = spitter;
        this.id = new FolloweeId(spitter, followee);
    }

    public Spitter getFollowee() {
        return followee;
    }

    public void setFollowee(Spitter followee) {
        this.followee = followee;
    }

    public Spitter getSpitter() {
        return spitter;
    }

    public void setSpitter(Spitter spitter) {
        this.spitter = spitter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Followee followee1 = (Followee) o;

        if (followee != null ? !followee.equals(followee1.followee) : followee1.followee != null) return false;
        if (spitter != null ? !spitter.equals(followee1.spitter) : followee1.spitter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = followee != null ? followee.hashCode() : 0;
        result = 31 * result + (spitter != null ? spitter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Followee{" +
                ", followee=" + followee +
                ", spitter=" + spitter +
                '}';
    }
}
