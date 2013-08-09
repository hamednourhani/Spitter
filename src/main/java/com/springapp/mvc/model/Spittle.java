package com.springapp.mvc.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="SPITTLE")
public class Spittle {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private int id;

    @ManyToOne
    @JoinColumn(name="SPITTER_ID")
    private Spitter spitter;

    @Column(name="TEXT")
    @NotNull
    @Size(min = 1, max = 140, message = "Must be between 1 and 140 characters.")
    private String text;

    @Column(name="POST_DATE")
    private String date;

    public Spittle() {
    }

    public Spittle(Spitter spitter, String text) {
        this.spitter = spitter;
        this.text = text;
        date = new Date().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(){
        this.date = new Date().toString();
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

        Spittle spittle = (Spittle) o;

        if (date != null ? !date.equals(spittle.date) : spittle.date != null) return false;
        if (text != null ? !text.equals(spittle.text) : spittle.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Spittle{" +
                "id=" + id +
                ", spitterUserName=" + spitter.getUserName() +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
