package com.buidRun.urlshortener.service;

import java.net.URI;
import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.buidRun.urlshortener.Entities.UrlEntity;
import com.buidRun.urlshortener.dto.ShortenUrlRequest;
import com.buidRun.urlshortener.dto.ShortenUrlResponse;
import com.buidRun.urlshortener.repository.UrlRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public ShortenUrlResponse shortenUrl(ShortenUrlRequest request, HttpServletRequest servletRequest){
        String identification;

        do {
            identification = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (urlRepository.existsById(identification));

        urlRepository.save(new UrlEntity(identification, request.url(), LocalDateTime.now().plusMinutes(1)));

        var redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", identification);
        return new ShortenUrlResponse(redirectUrl);
    }

    public HttpHeaders redirect(String id) {

        var url = urlRepository.findById(id);

        if (url.isEmpty()) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));

        return headers;
    }

}
