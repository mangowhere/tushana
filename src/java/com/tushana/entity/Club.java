/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.TableName;
import java.util.Date;

/**
 *
 * @author hubin
 */
@TableName("tushana_club")
public class Club {

    private String id;
    private String name;
    private String title;
    private String first;
    private String second;
    private String third;
    private String fourth;
    private String fifth;
    private Date createDate;
    private Date lastDate;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the first
     */
    public String getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(String first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public String getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(String second) {
        this.second = second;
    }

    /**
     * @return the third
     */
    public String getThird() {
        return third;
    }

    /**
     * @param third the third to set
     */
    public void setThird(String third) {
        this.third = third;
    }

    /**
     * @return the lastDate
     */
    public Date getLastDate() {
        return lastDate;
    }

    /**
     * @param lastDate the lastDate to set
     */
    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    /**
     * @return the fourth
     */
    public String getFourth() {
        return fourth;
    }

    /**
     * @param fourth the fourth to set
     */
    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    /**
     * @return the fifth
     */
    public String getFifth() {
        return fifth;
    }

    /**
     * @param fifth the fifth to set
     */
    public void setFifth(String fifth) {
        this.fifth = fifth;
    }
}
