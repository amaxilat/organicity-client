package eu.organicity.client.android;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.organicity.ErrorResponseDTO;
import eu.organicity.experiment.management.exception.AssetAlreadyExistsException;
import eu.organicity.experiment.management.exception.EntityNotFoundException;
import eu.organicity.experiment.management.exception.ExperimentNotRunningException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class OrganicityErrorHandler implements ResponseErrorHandler {
    
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return !response.getStatusCode().equals(HttpStatus.OK) && !response.getStatusCode().equals(HttpStatus.CREATED) && !response.getStatusCode().equals(HttpStatus.ACCEPTED) && !response.getStatusCode().equals(HttpStatus.NO_CONTENT);
    }
    
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        final String responseBody = tryReadResponseBody(response);
        System.out.println(responseBody);
        final ErrorResponseDTO responseDTO = new ObjectMapper().readValue(responseBody, ErrorResponseDTO.class);
        final String description = responseDTO.getDescription();
        switch (response.getStatusCode()) {
            case BAD_REQUEST:
                if (responseDTO.getDescription() != null) {
                    if (description.equals("This experiment is not running!")) {
                        throw new ExperimentNotRunningException();
                    } else if (description.equals("Already Exists")) {
                        throw new AssetAlreadyExistsException();
                    }
                }
                throw new IOException();
            case UNPROCESSABLE_ENTITY:
                if (responseDTO.getDescription() != null) {
                    if (description.equals("Already Exists")) {
                        throw new AssetAlreadyExistsException();
                    }
                }
                throw new IOException();
            case NOT_FOUND:
                if (description != null) {
                    if (description.equals("The requested entity has not been found. Check type and id")) {
                        throw new EntityNotFoundException();
                    }
                }
                throw new IOException();
            default:
                throw new IOException();
        }
    }
    
    private String tryReadResponseBody(ClientHttpResponse response) {
        try {
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            final StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(response.getBody(), "UTF-8");
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
            return out.toString();
        } catch (Exception e) {
            return "";
        }
    }
}