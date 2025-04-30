package com.buidRun.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.buidRun.urlshortener.Entities.UrlEntity;

public interface UrlRepository extends MongoRepository<UrlEntity,String> {

}
