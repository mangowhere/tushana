/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.join;

import com.tushana.entity.Reviewer;
import com.tushana.entity.reviewer.ReviewerReader;

/**
 *
 * @author hubin
 */
public class ReviewerUtils {

    private static final ReviewerReader reviewerReader = new ReviewerReader();

    public static Reviewer getReviewerByUserId(String userId) throws NotFoundReviewerException {
        Reviewer reviewer = reviewerReader.getByUserId(userId);
        if (reviewer == null) {
            throw new NotFoundReviewerException();
        }
        return reviewer;
    }
}
