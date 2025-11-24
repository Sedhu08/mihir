package com.example.moviedb.service;

//import com.example.moviedb.domain.Movie;
import com.example.moviedb.web.dto.MovieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MovieService {
    Page<MovieDTO> getMovies(LocalDate releaseDate, Double rating, Pageable pageable);

    MovieDTO createMovie(MovieDTO movieDTO);

    MovieDTO getMovieById(Long id);
}
