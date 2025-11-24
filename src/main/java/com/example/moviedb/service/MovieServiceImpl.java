package com.example.moviedb.service;

import com.example.moviedb.domain.CastMember;
import com.example.moviedb.domain.Genre;
import com.example.moviedb.domain.Movie;
import com.example.moviedb.repository.CastMemberRepository;
import com.example.moviedb.repository.GenreRepository;
import com.example.moviedb.repository.MovieRepository;
import com.example.moviedb.web.dto.CastMemberDTO;
import com.example.moviedb.web.dto.GenreDTO;
import com.example.moviedb.web.dto.MovieDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Component
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CastMemberRepository castMemberRepository;

    public MovieServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository,
            CastMemberRepository castMemberRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.castMemberRepository = castMemberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovieDTO> getMovies(java.time.LocalDate releaseDate, Double rating, Pageable pageable) {
        Specification<Movie> spec = Specification.where(null);

        if (releaseDate != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("releaseDate"), releaseDate));
        }
        if (rating != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("rating"), rating));
        }

        java.util.Objects.requireNonNull(pageable, "Pageable cannot be null");
        Page<Movie> moviePage = movieRepository.findAll(spec, pageable);
        List<MovieDTO> movieDTOs = new ArrayList<>();
        for (Movie movie : moviePage.getContent()) {
            movieDTOs.add(toDTO(movie));
        }
        return new PageImpl<>(movieDTOs, pageable, moviePage.getTotalElements());
    }

    @Override
    @Transactional
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setRating(movieDTO.getRating());

        if (movieDTO.getGenres() != null) {
            Set<Genre> genres = new HashSet<>();
            for (GenreDTO dto : movieDTO.getGenres()) {
                if (dto.getId() != null) {
                    long genreId = dto.getId();
                    Genre genre = genreRepository.findById(genreId)
                            .orElseThrow(() -> new EntityNotFoundException("Genre not found: " + genreId));
                    genres.add(genre);
                } else {
                    Genre genre = new Genre();
                    genre.setName(dto.getName());
                    genres.add(genre);
                }
            }
            movie.setGenres(genres);
        }

        if (movieDTO.getCast() != null) {
            Set<CastMember> cast = new HashSet<>();
            for (CastMemberDTO dto : movieDTO.getCast()) {
                if (dto.getId() != null) {
                    long memberId = dto.getId();
                    CastMember member = castMemberRepository.findById(memberId)
                            .orElseThrow(() -> new EntityNotFoundException("CastMember not found: " + memberId));
                    cast.add(member);
                } else {
                    CastMember member = new CastMember();
                    member.setName(dto.getName());
                    member.setRole(dto.getRole());
                    cast.add(member);
                }
            }
            movie.setCast(cast);
        }

        Movie savedMovie = movieRepository.save(movie);
        return toDTO(savedMovie);
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDTO getMovieById(Long id) {
        java.util.Objects.requireNonNull(id, "Movie ID cannot be null");
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
        return toDTO(movie);
    }

    private MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setReleaseDate(movie.getReleaseDate());
        dto.setRating(movie.getRating());

        if (movie.getGenres() != null) {
            Set<GenreDTO> genreDTOs = new HashSet<>();
            for (Genre genre : movie.getGenres()) {
                genreDTOs.add(toDTO(genre));
            }
            dto.setGenres(genreDTOs);
        }
        if (movie.getCast() != null) {
            Set<CastMemberDTO> castDTOs = new HashSet<>();
            for (CastMember member : movie.getCast()) {
                castDTOs.add(toDTO(member));
            }
            dto.setCast(castDTOs);
        }
        return dto;
    }

    private GenreDTO toDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    private CastMemberDTO toDTO(CastMember member) {
        CastMemberDTO dto = new CastMemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setRole(member.getRole());
        return dto;
    }
}
