package com.example.moviedb.web.dto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Double rating;
    private Set<GenreDTO> genres;
    private Set<CastMemberDTO> cast;

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String description, LocalDate releaseDate, Double rating,
            Set<GenreDTO> genres, Set<CastMemberDTO> cast) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.genres = genres;
        this.cast = cast;
    }

    public Long getId() {
        if (id == null) {
            return null;
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDTO> genres) {
        this.genres = genres;
    }

    public Set<CastMemberDTO> getCast() {
        return cast;
    }

    public void setCast(Set<CastMemberDTO> cast) {
        this.cast = cast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(id, movieDTO.id) && Objects.equals(title, movieDTO.title)
                && Objects.equals(description, movieDTO.description)
                && Objects.equals(releaseDate, movieDTO.releaseDate) && Objects.equals(rating, movieDTO.rating)
                && Objects.equals(genres, movieDTO.genres) && Objects.equals(cast, movieDTO.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, releaseDate, rating, genres, cast);
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", genres=" + genres +
                ", cast=" + cast +
                '}';
    }
}
