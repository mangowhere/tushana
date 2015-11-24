/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zlfun.framework.table;

import com.zlfun.framework.Token;
import com.zlfun.framework.WebEntity;
import com.zlfun.framework.WebParam;

/**
 *
 * @author hubin
 */
@WebEntity
public class TableRequest extends Token {

    @WebParam("size")
    private int size;
    @WebParam("offset")
    private int offset;
    @WebParam("search")
    private String search;
    @WebParam("filter")
    private String filter;
    @WebParam("value")
    private String value;
    @WebParam("sort")
    private String sort;
    @WebParam("asc")
    private boolean asc;

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the filterAll
     */
    public String getSearch() {
        return search;
    }

    /**
     * @param search the filterAll to set
     */
    public void setSearch(String search) {
        this.search = search;
    }

    /**
     * @return the filterName
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filterName to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @return the fitlerValue
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the fitlerValue to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the sortName
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort the sortName to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return the asc
     */
    public boolean isAsc() {
        return asc;
    }

    /**
     * @param asc the asc to set
     */
    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
