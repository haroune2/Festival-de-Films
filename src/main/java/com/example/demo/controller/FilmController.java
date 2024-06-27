package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.example.demo.service.FilmService;
import com.example.demo.domain.dto.FilmDTO;
import com.example.demo.domain.model.Film;

/**
 * CONTROLLER FILM - LOGIQUE CRUD
 *
 *  /films -> GET : list of films
 *  /films/{id} -> GET : get a film by ID
 *  /films -> POST : create a new film
 *  /films/{id} -> PUT : update a film by ID
 *  /films/{id} -> DELETE : delete a film by ID
 */
@RestController
@RequestMapping("/films")
@CrossOrigin(origins = "https://didactic-space-carnival-9vvjwgrpv6jcrw4-4200.app.github.dev")

public class FilmController {

    @Autowired
    private FilmService filmService;

    /**
     * POST
     * Créer une nouvelle entité.
     */
    @PostMapping
    public ResponseEntity<FilmDTO> create(@RequestBody Film film) {
        FilmDTO createdFilmDTO = filmService.createFilm(film);
        return ResponseEntity.ok(createdFilmDTO);
    }

    /**
     * GET
     * Récupérer la liste de tous les films.
     */
    @GetMapping
    public ResponseEntity<List<FilmDTO>> list() {
        List<FilmDTO> allFilmDTOs = filmService.getAllFilms();
        return ResponseEntity.ok(allFilmDTOs);
    }

    /**
     * GET
     * Récupérer une entité par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> get(@PathVariable Long id) {
        FilmDTO foundFilmDTO = filmService.getFilmById(id).orElse(null);
        return ResponseEntity.ok(foundFilmDTO);
    }

    /**
     * PUT
     * Mettre à jour une entité.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> update(@PathVariable Long id, @RequestBody Film film) {
        FilmDTO updatedFilmDTO = filmService.updateFilm(id, film);
        return ResponseEntity.ok(updatedFilmDTO);
    }

    /**
     * DELETE
     * Supprimer une entité.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}

