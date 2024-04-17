package fr.diginamic.utils;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.models.Show;

@Component
public class DataParser {

    private final ResourceLoader resourceLoader;

    public DataParser(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Show> parseShows(String location) throws IOException {
        Resource resource = resourceLoader.getResource(location);
        ObjectMapper mapper = new ObjectMapper();
        List<Show> shows = mapper.readValue(resource.getInputStream(), new TypeReference<List<Show>>() {});
        return shows;
    }
}
