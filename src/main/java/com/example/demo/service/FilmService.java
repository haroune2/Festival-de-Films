package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.domain.model.Film;
import com.example.demo.domain.dto.FilmDTO;
import com.example.demo.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ModelMapper modelMapper;

    public FilmDTO createFilm(Film film) {
        Film createdFilm = filmRepository.save(film);
        return modelMapper.map(createdFilm, FilmDTO.class);
    }

    public List<FilmDTO> getAllFilms() {
        List<Film> allFilms = filmRepository.findAll();
        return convertListToDTOs(allFilms);
    }

    public Optional<FilmDTO> getFilmById(Long id) {
        Optional<Film> entity = filmRepository.findById(id);
        return entity.map(e -> modelMapper.map(e, FilmDTO.class));
    }

    public FilmDTO updateFilm(Long id, Film film) {
        if (!filmRepository.existsById(id)) {
            return null; // Gérer le cas où l'entité n'existe pas
        }
        Film updatedFilm = filmRepository.save(film);
        return modelMapper.map(updatedFilm, FilmDTO.class);
    }

    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    private List<FilmDTO> convertListToDTOs(List<Film> entities) {
        return entities.stream()
                .map(e -> modelMapper.map(e, FilmDTO.class))
                .toList();
    }
}

