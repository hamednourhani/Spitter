package com.springapp.mvc.model;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="SPITTER")
public class Spitter implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private int id;

    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9]+$", message="Username must be alphanumeric with no spaces.")
    @Size(min = 1, max = 30, message = "Username must be at least 1 character long.")
    @Column(name="USERNAME")
    private String userName;

    @NotEmpty
    @Size(min = 6, max = 30, message = "The password must be at least 6 characters long.")
    @Column(name="PASSWORD")
    private String password;

    @Size(min = 3, max = 50, message = "Your full name must be between 3 to 50 characters long.")
    @Column(name="FULLNAME")
    private String fullName;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}", message = "Invalid email address.")
    @Column(name="EMAIL")
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPITTER_ID")
    private List<Followee> followees = Lists.newArrayList();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPITTER_ID")
    private List<Spittle> spittles = Lists.newArrayList();

    public Spitter(){}

    public Spitter(String userName, String email, String password, String fullName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public List<Followee> getFollowees() {
        return followees;
    }

    public void addFollowee(Spitter followee){
        Followee follow = new Followee(this, followee);
        addFollowee(follow);
    }

    public void addFollowee(Followee followee){
        followees.add(followee);
    }

    public List<Spittle> getSpittles() {
        return spittles;
    }

    public void setSpittles(List<Spittle> spittles) {
        this.spittles = spittles;
    }

    public void addSpittle(Spittle spittle){
        spittles.add(spittle);
    }

    public boolean hasUserName(){
        return userName != null && !userName.isEmpty();
    }

    public boolean hasPassword(){
        return password != null && !password.isEmpty();
    }

    public boolean hasFullName(){
        return fullName != null && !fullName.isEmpty();
    }

    public boolean isEmpty(){
        return !hasUserName() || !hasPassword() || !hasFullName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spitter spitter = (Spitter) o;

        if (!password.equals(spitter.password)) return false;
        if (!userName.equals(spitter.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Spitter{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
