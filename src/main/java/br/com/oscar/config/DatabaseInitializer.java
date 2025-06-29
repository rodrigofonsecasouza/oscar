package br.com.oscar.config;

import br.com.oscar.movie.model.Movie;
import br.com.oscar.movie.repository.MovieRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.Objects;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public DatabaseInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/movielist.csv")));

        var movies = new CsvToBeanBuilder<Movie>(reader)
                .withType(Movie.class)
                .withIgnoreEmptyLine(true)
                .withSeparator(';')
                .build()
                .parse();

        movies.parallelStream().forEach(movieRepository::save);
    }
}
