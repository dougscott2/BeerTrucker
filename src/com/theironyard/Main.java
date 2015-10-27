package com.theironyard;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Spark.get(
                "/",//first agrument
                ((request, response) -> {//second argument
                    Session session = request.session();
                    String username = session.attribute("username");
                    if (username == null){
                        return new ModelAndView(new HashMap(), "not-logged-in.html");
                    }

                    HashMap m = new HashMap();
                    m.put("username", username);
                    return new ModelAndView(m, "logged-in.html");
                }),//third argument

                new MustacheTemplateEngine()

        );
        Spark.post(
                "/login",
                ((request, response) -> {
                    String userName = request.queryParams("username");
                    Session session = request.session();
                    session.attribute("username", userName);
                    response.redirect("/");
                    return "";

                })
        );

    }
}
