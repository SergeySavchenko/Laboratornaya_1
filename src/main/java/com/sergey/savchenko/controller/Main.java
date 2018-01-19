package com.sergey.savchenko.controller;

import org.apache.log4j.Logger;

import com.sergey.savchenko.view.TmViewMain;

import java.io.IOException;
import java.text.ParseException;


/**
 * class "Main", start class of application
 *
 * Created by SergeySavchenko on 26.12.2017.
 */
public class Main {
    final static Logger logger = Logger.getLogger(Main.class);

    /**
     * entry point method to application
     */
    public static void main(String[] args) {
        try {
            TmViewMain view = new TmViewMain();
        } catch (IOException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }
    }
}

