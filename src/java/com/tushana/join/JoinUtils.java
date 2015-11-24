/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tushana.join;

import com.tushana.entity.Category;
import com.tushana.entity.Photo;
import com.tushana.entity.Picture;
import com.tushana.entity.category.CategoryReader;
import com.tushana.entity.category.CategoryWriter;
import com.tushana.entity.photo.PhotoReader;
import com.tushana.entity.photo.PhotoWriter;
import com.tushana.entity.picture.PictureReader;
import com.tushana.entity.picture.PictureWriter;
import com.zlfun.framework.db.Transaction;
import com.zlfun.framework.misc.UuidUtils;
import java.util.Date;

/**
 *
 * @author hubin
 */
public class JoinUtils {

    private final static CategoryReader categoryReader = new CategoryReader();
    private final static PictureReader pcitureReader = new PictureReader();
    private final static PhotoReader photoReader = new PhotoReader();

    public static boolean addPhoto(String userId, String userName, String gameId, String name, String photo_id, String tagstring) {
        Photo photo = photoReader.getById(photo_id);
        if (photo == null) {
            photo = new Photo();
            photo.game_id = gameId;
            photo.height = 300;
            photo.width = 300;
            photo.id = photo_id;
            photo.player_id = userId;
            photo.player_name = userName;
            photo.player_memo = "";
            Transaction ts = new Transaction();
            addCategories(ts, name, photo_id, tagstring);
            PhotoWriter photoWriter = new PhotoWriter(ts);
            photoWriter.add(photo);
            ts.save();

        }
        return false;
    }

    private static boolean addCategories(Transaction ts, String name, String photo_id, String tagstring) {
        String[] cates = tagstring.split("\\|");
        if (cates.length < 1) {
            return false;
        }
        for (String cate : cates) {
            addCategory(ts, name, photo_id, cate);
        }
        return true;
    }

    private static boolean addCategory(Transaction ts, String photoName, String photo_id, String category) {
        Category cate = categoryReader.getByName(category);
        if (cate == null) {
            cate = new Category();
            cate.click = 0;
            cate.createDate = new Date(System.currentTimeMillis());
            cate.id = UuidUtils.base58Uuid();
            cate.memo = "";
            cate.name = category;
            cate.pay = 0;
            cate.sum = 1;
            Picture pic = new Picture();
            pic.category = category;
            pic.create_date = new Date(System.currentTimeMillis());
            pic.id = UuidUtils.base58Uuid();
            pic.name = photoName;
            pic.photo_id = photo_id;

            CategoryWriter cw = new CategoryWriter(ts);
            PictureWriter pw = new PictureWriter(ts);
            pw.add(pic);
            cw.add(cate);

        } else {
            cate.sum = cate.sum + 1;
            Picture picture = pcitureReader.getByPhotoIdAndCategory(photo_id, category);
            if (picture == null) {
                Picture pic = new Picture();
                pic.category = category;
                pic.create_date = new Date(System.currentTimeMillis());
                pic.id = UuidUtils.base58Uuid();
                pic.name = photoName;
                pic.photo_id = photo_id;

                CategoryWriter cw = new CategoryWriter(ts);
                PictureWriter pw = new PictureWriter(ts);
                pw.add(pic);
                cw.add(cate);

            } else {

                CategoryWriter cw = new CategoryWriter(ts);
                cw.add(cate);

            }
        }
        return true;

    }
}
