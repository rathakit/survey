package com.nimbl3.survey.models;

/**
 * Created by rathakit.nop on 9/23/2016 AD.
 */

/**
 * The SurveyParam Class
 */
public class SurveyParam extends BasedParam {

    // The page
    private int page;

    // The number of items per page
    private int perPage;

    // TODO Constructors
    public SurveyParam() {}

    /**
     * Creates the SurveyParam with access token provided.
     * @param accessToken
     */
    public SurveyParam(String accessToken) {
        super(accessToken);
    }

    /**
     * Creates the SurveyParam with access token, page and number of items provided.
     * @param accessToken
     * @param page - the page number
     * @param perPage - the number of items per page
     */
    public SurveyParam(String accessToken, int page, int perPage) {
        super(accessToken);
        this.page = page;
        this.perPage = perPage;
    }

    // TODO GETTER/SETTER
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
