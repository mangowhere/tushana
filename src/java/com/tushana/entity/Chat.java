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
@TableName("tushana_chat")
public class Chat {

    @FieldName("id")
    private String id;
    @FieldName("club_id")
    private String clubId;
    @FieldName("create_date")
    private Date createDate;
    @FieldName("content")
    private String content;
    @FieldName("player_id")
    private String playerId;
    @FieldName("player_name")
    private String playerName;
    @FieldName("picture_id")
    private String pictureId;
    @FieldName("trade_id")
    private String tradeId;

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
     * @return the clubId
     */
    public String getClubId() {
        return clubId;
    }

    /**
     * @param clubId the clubId to set
     */
    public void setClubId(String clubId) {
        this.clubId = clubId;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the playerId
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * @param playerId the playerId to set
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the pictureId
     */
    public String getPictureId() {
        return pictureId;
    }

    /**
     * @param pictureId the pictureId to set
     */
    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    /**
     * @return the tradeId
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * @param tradeId the tradeId to set
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

}
