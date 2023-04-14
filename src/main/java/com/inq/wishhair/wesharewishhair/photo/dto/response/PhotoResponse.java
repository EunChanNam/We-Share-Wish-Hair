package com.inq.wishhair.wesharewishhair.photo.dto.response;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Getter
@AllArgsConstructor
public class PhotoResponse {

    private final String resource;

    public PhotoResponse(Photo photo) {
        String fullPath = FilePath.VALUE + photo.getStoreFilename();
        Path path = Path.of(fullPath);

        try {
            String stringUri = new UrlResource(path.toUri()).toString();
            this.resource = stringUri.substring(5, stringUri.length() - 1);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
