package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
    public static final String ERROR_VIEW = "/error";

    /**
     * エラー画面へ遷移する.
     * @return
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        //エラー画面へ遷移
        return ERROR_VIEW;
    }
}
