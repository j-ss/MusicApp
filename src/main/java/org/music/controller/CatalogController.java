package org.music.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
@WebServlet(urlPatterns = "/catalog",loadOnStartup = 1)
public class CatalogController extends HttpServlet {
}
