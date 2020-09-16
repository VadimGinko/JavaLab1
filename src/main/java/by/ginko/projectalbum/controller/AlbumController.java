package by.ginko.projectalbum.controller;


import by.ginko.projectalbum.forms.AlbumForm;
import by.ginko.projectalbum.model.Album;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        return modelAndView;
    }

    @RequestMapping(value = {"/allalbums"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        model.addAttribute("albums", albums);
        return modelAndView;
    }


    @RequestMapping(value = {"/addalbum"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addalbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumform", albumForm);
        return modelAndView;
    }
    // @PostMapping("/addalbum")
    //GetMapping("/")
    @RequestMapping(value = {"/addalbum"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, //
                                   @ModelAttribute("albumform") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String author = albumForm.getAuthor();
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

    @RequestMapping(value = {"/deletealbum"}, method = RequestMethod.GET)
    public ModelAndView showDeletePersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("deletealbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumformdelete", albumForm);
        return modelAndView;
    }
    @RequestMapping(value = {"/deletealbum"}, method = RequestMethod.POST)
    public ModelAndView deletePerson(Model model, //
                                   @ModelAttribute("albumformdelete") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String author = albumForm.getAuthor();
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

    @RequestMapping(value = {"/redactalbum"}, method = RequestMethod.GET)
    public ModelAndView showRedactPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("redactalbum");
        AlbumForm albumForm = new AlbumForm();
        model.addAttribute("albumformredact", albumForm);
        return modelAndView;
    }
    @RequestMapping(value = {"/redactalbum"}, method = RequestMethod.POST)
    public ModelAndView redactPerson(Model model, //
                                     @ModelAttribute("albumformredact") AlbumForm albumForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("albumlist");
        String title = albumForm.getTitle();
        String newAuthor = albumForm.getAuthor();
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
