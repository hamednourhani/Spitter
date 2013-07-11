package com.springapp.mvc.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FOLLOWER")
public class Follower implements Serializable {

    @EmbeddedId
    private FolloweeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPITTER_ID", insertable = false, updatable = false)
    private Spitter spitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOLLOWEE_ID", insertable = false, updatable = false)
    private Spitter follower;

    public Follower() {
    }

    public Follower(Spitter spitter, Spitter follower) {
        this.spitter = spitter;
        this.follower = follower;
        this.id = new FolloweeId(spitter, follower);
    }

    public FolloweeId getId() {
        return id;
    }

    public void setId(FolloweeId id) {
        this.id = id;
    }

    public Spitter getSpitter() {
        return spitter;
    }

    public void setSpitter(Spitter spitter) {
        this.spitter = spitter;
    }

    public Spitter getFollower() {
        return follower;
    }

    public void setFollower(Spitter follower) {
        this.follower = follower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Follower follower1 = (Follower) o;

        if (!follower.equals(follower1.follower)) return false;
        if (!id.equals(follower1.id)) return false;
        if (!spitter.equals(follower1.spitter)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + spitter.hashCode();
        result = 31 * result + follower.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Follower{" +
                "id=" + id +
                ", spitter=" + spitter +
                ", follower=" + follower +
                '}';
    }
}
