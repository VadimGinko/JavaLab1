package by.ginko.projectalbum.controller;


import by.ginko.projectalbum.forms.AlbumForm;
import by.ginko.projectalbum.model.Album;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class AlbumController {
    private static List<Album> albums = new ArrayList<Album>();
    static {
        albums.add(new Album("Full Stack Development with JHipster", "Deepu K Sasidharan, Sendil Kumar"));
        albums.add(new Album("Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
    }
    //
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    //@GetMapping(value = {"/", "index"})
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called(GET)");
        return modelAndView;
    }

    @GetMapping(value = {"/allalbums"})
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        model.addAttribute("albums", albums);
        log.info("/allalbums was called(GET)");
        return modelAndView;
    }

    @GetMapping(value = {"/addalbum"})
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addalbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumform", albumForm);
        log.info("/addalbum was called(GET)");
        return modelAndView;
    }

    @PostMapping(value = {"/addalbum"})
    public ModelAndView savePerson(Model model, //
                                   @ModelAttribute("albumform") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String author = albumForm.getAuthor();
        log.info("/addalbum was called(POST)");
        if (title != null && title.length() > 0 //
                && author != null && author.length() > 0) {
            Album newAlbum = new Album(title, author);
            albums.add(newAlbum);
            model.addAttribute("albums",albums);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addalbum");
        return modelAndView;
    }

    @GetMapping(value = {"/deletealbum"})
    public ModelAndView showDeletePersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("deletealbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumformdelete", albumForm);
        log.info("/deletealbum was called(GET)");
        return modelAndView;
    }

    @PostMapping(value = {"/deletealbum"})
    public ModelAndView deletePerson(Model model, //
                                   @ModelAttribute("albumformdelete") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String author = albumForm.getAuthor();
        log.info("/deletealbum was called(POST)");
        if (title != null && title.length() > 0 //
                && author != null && author.length() > 0) {
            Album del = null;
            for (var album : albums)
            {
                String tt = album.getTitle();
                String au = album.getAuthor();
                if(tt.equals(title))
                    if(au.equals(author))
                        del = album;
            }
            if(del != null){
                albums.remove(del);
            }
            model.addAttribute("albums",albums);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("deletealbum");
        return modelAndView;
    }
    @GetMapping(value = {"/redactalbum"})
    public ModelAndView showRedactPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("redactalbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumformredact", albumForm);
        log.info("/redactalbum was called(GET)");
        return modelAndView;
    }

    @PostMapping(value = {"/redactalbum"})
    public ModelAndView redactPerson(Model model, //
                                     @ModelAttribute("albumformredact") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String newAuthor = albumForm.getAuthor();
        log.info("/redactalbum was called(POST)");
        if (title != null && title.length() > 0 //
                && newAuthor != null && newAuthor.length() > 0) {
            Album del = null;
            for (var album : albums)
            {
                String tt = album.getTitle();
                String au = album.getAuthor();
                if(tt.equals(title))
                    album.setAuthor(newAuthor);
            }
            model.addAttribute("albums",albums);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("redactalbum");
        return modelAndView;
    }
}
