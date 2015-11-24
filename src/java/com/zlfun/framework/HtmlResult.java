package com.zlfun.framework;

@WebEntity
public class HtmlResult extends WebResult {

    @WebParam("html")
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public HtmlResult(String html) {
       // html = html.replaceAll("\\\"", "\\\"");
       // html = html.replaceAll("\n", "\\\\n");
        this.html = html;
    }

}
