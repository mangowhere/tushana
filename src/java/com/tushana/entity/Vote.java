/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.entity;

import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.TableName;
import java.util.Date;

/**
 *
 * @author hubin
 */
@TableName("tushana_vote")
public class Vote {

    @FieldName("id")
    private String id;
    @FieldName("reviewer_id")
    private String reviewerId;
    @FieldName("reviewer_name")
    private String reviewerName;
    @FieldName("pk_id")
    private String pkId;
    @FieldName("left")
    private boolean left;
    @FieldName("create_date")
    private Date createDate;

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
     * @return the reviewerId
     */
    public String getReviewerId() {
        return reviewerId;
    }

    /**
     * @param reviewerId the reviewerId to set
     */
    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     * @return the reviewerName
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * @param reviewerName the reviewerName to set
     */
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
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
     * @return the pkId
     */
    public String getPkId() {
        return pkId;
    }

    /**
     * @param pkId the pkId to set
     */
    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    /**
     * @return the left
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(boolean left) {
        this.left = left;
    }
}
