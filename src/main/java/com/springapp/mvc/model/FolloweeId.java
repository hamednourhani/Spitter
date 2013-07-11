package com.springapp.mvc.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FolloweeId implements Serializable {

    @Column(name="FOLLOWEE_ID")
    private int followeeId;

    @Column(name="SPITTER_ID")
    private int spitterId;

    public FolloweeId() {
    }

    public FolloweeId(Spitter spitter, Spitter followee) {
        this.spitterId = spitter.getId();
        this.followeeId = followee.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolloweeId that = (FolloweeId) o;

        if (followeeId != that.followeeId) return false;
        if (spitterId != that.spitterId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = spitterId;
        result = 31 * result + followeeId;
        return result;
    }
}
