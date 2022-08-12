package com.apirest.webflux.repository;

import com.apirest.webflux.entity.Playlist;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlayListRepository extends ReactiveMongoRepository<Playlist, String> {

}
