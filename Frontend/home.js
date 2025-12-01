
async function loadMovies() {
    try {
        const response = await fetch("http://localhost:8081/api/v1/movies");
        const movies = await response.json();

        const container = document.getElementById("movie-container");
        container.innerHTML = ""; 

        if (!movies.content || movies.content.length === 0) {
            container.innerHTML = "<p>No movies found</p>";
            return;
        }

        movies.content.forEach(movie => {
            const card = document.createElement("div");
            card.className = "movie-card";
            let posterImage = "https://via.placeholder.com/200x300";

        if(movie.title === "The Matrix") {
            posterImage = "https://m.media-amazon.com/images/M/MV5BMDMyNDIzYzMtZTMyMy00NjUyLWI3Y2MtYzYzOGE1NzQ1MTBiXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg";
        } else if(movie.title === "John Wick") {
            posterImage = "https://exotique.com.mt/wp-content/uploads/2023/06/John-Wick-4-Steelbook-4K-BLU-RAY.jpg";
        }

            card.innerHTML = `
                <img class="movie-poster" src="${posterImage}" alt="${movie.title}">
                <div class="movie-info">
                    <div class="movie-title">${movie.title}</div>
                    <div class="movie-rating">⭐ ${movie.rating}</div>
                    <div class="movie-description">${movie.description}</div>
                </div>
            `;

            container.appendChild(card);
        });

    } catch (error) {
        console.error("Error loading movies:", error);
        document.getElementById("movie-container").innerHTML =
            "<p>Error loading movies. Check console.</p>";
    }
}

loadMovies();
