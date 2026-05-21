package com.microgram.common;

import jakarta.servlet.http.HttpServletRequest;

public class UrlBuilder {

    public static String getSiteUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }
}