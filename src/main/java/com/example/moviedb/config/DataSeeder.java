package com.example.moviedb.config;

import com.example.moviedb.domain.CastMember;
import com.example.moviedb.domain.Genre;
import com.example.moviedb.domain.Movie;
import com.example.moviedb.repository.CastMemberRepository;
import com.example.moviedb.repository.GenreRepository;
import com.example.moviedb.repository.MovieRepository;

import jakarta.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component

public class DataSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CastMemberRepository castMemberRepository;

    public DataSeeder(MovieRepository movieRepository, GenreRepository genreRepository,
            CastMemberRepository castMemberRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.castMemberRepository = castMemberRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {
            Genre action = new Genre(null, "Action");
            Genre sciFi = new Genre(null, "Sci-Fi");
            Genre drama = new Genre(null, "Drama");
            genreRepository.save(action);
            genreRepository.save(sciFi);
            genreRepository.save(drama);

            CastMember actor1 = new CastMember(null, "Keanu Reeves", "Actor");
            CastMember actor2 = new CastMember(null, "Laurence Fishburne", "Actor");
            castMemberRepository.save(actor1);
            castMemberRepository.save(actor2);

            Movie matrix = new Movie();
            matrix.setTitle("The Matrix");
            matrix.setDescription(
                    "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.");
            matrix.setReleaseDate(LocalDate.of(1999, 3, 31));
            matrix.setRating(8.7);
            matrix.setGenres(Set.of(action, sciFi));
            matrix.setCast(Set.of(actor1, actor2));
            movieRepository.save(matrix);

            Movie johnWick = new Movie();
            johnWick.setTitle("John Wick");
            johnWick.setDescription(
                    "An ex-hit-man comes out of retirement to track down the gangsters that killed his dog and took everything from him.");
            johnWick.setReleaseDate(LocalDate.of(2014, 10, 24));
            johnWick.setRating(7.4);
            johnWick.setGenres(Set.of(action, drama));
            johnWick.setCast(Set.of(actor1));
            movieRepository.save(johnWick);
        }
    }
}
