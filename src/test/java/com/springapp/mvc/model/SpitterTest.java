package com.springapp.mvc.model;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

public class SpitterTest {


    @Test
    public void hasFullName_shouldReturnFalseWhenNameIsNull(){
        Spitter spitter = new Spitter();
        spitter.setFullName(null);
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.hasFullName();
        assertThat(result,is(false));
    }

    @Test
    public void hasFullName_shouldReturnFalseWhenNameIsEmpty(){
        Spitter spitter = new Spitter();
        spitter.setFullName("");
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.hasFullName();
        assertThat(result,is(false));
    }

    @Test
    public void hasFullName_shouldReturnTrue(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.hasFullName();
        assertThat(result,is(true));
    }

    @Test
    public void hasUserName_shouldReturnFalseWhenUserNameIsNull(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName(null);
        spitter.setPassword("microsoft");

        boolean result = spitter.hasUserName();
        assertThat(result,is(false));
    }

    @Test
    public void hasUserName_shouldReturnFalseWhenUserNameIsEmpty(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("");
        spitter.setPassword("microsoft");

        boolean result = spitter.hasUserName();
        assertThat(result,is(false));
    }

    @Test
    public void hasUserName_shouldReturnTrue(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.hasUserName();
        assertThat(result,is(true));
    }

    @Test
    public void hasPassword_shouldReturnFalseWhenPasswordIsNull(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword(null);

        boolean result = spitter.hasPassword();
        assertThat(result, is(false));
    }

    @Test
    public void hasPassword_shouldReturnFalseWhenPasswordIsEmpty(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword("");

        boolean result = spitter.hasPassword();
        assertThat(result, is(false));
    }

    @Test
    public void hasPassword_shouldReturnTrue(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.hasPassword();
        assertThat(result,is(true));
    }

    @Test
    public void isEmpty_shouldReturnTrueWhenNameIsEmpty(){
        Spitter spitter = new Spitter();
        spitter.setFullName("");
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.isEmpty();
        assertThat(result, is(true));
    }

    @Test
     public void isEmpty_shouldReturnTrueWhenUserNameIsEmpty(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("");
        spitter.setPassword("microsoft");

        boolean result = spitter.isEmpty();
        assertThat(result, is(true));
    }

    @Test
    public void isEmpty_shouldReturnTrueWhenPasswordIsEmpty(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword("");

        boolean result = spitter.isEmpty();
        assertThat(result, is(true));
    }

    @Test
    public void isEmpty_shouldReturnFalse(){
        Spitter spitter = new Spitter();
        spitter.setFullName("Bill Gates");
        spitter.setUserName("BillGates");
        spitter.setPassword("microsoft");

        boolean result = spitter.isEmpty();
        assertThat(result, is(false));
    }



}
