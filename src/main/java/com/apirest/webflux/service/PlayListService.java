package com.apirest.webflux.service;

import com.apirest.webflux.entity.Playlist;
import com.apirest.webflux.repository.PlayListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class PlayListService {
    @Autowired
    private PlayListRepository playListRepository;


    public Flux<Playlist> findAll() {
        return playListRepository.findAll();
    }

    public Mono<Playlist> findById(String id) {
        return playListRepository.findById(id);
    }

    public Mono<Playlist> save(Playlist playlist) {
        return playListRepository.save(playlist);
    }

    public Mono<Playlist> savePlay(){
        try {
            Playlist playlist = Playlist.builder().nome("fdadf")
                    .build();
             return playListRepository.save(playlist);

        } catch (Exception e ) {
            log.warn("erro");
        }

        return null;



    }


    public Flux<Tuple2<Long, Playlist>> getTuple2Flux() {
        log.warn(("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now()));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
        Flux<Playlist> playlistFlux = findAll();

        return Flux.zip(interval, playlistFlux);
    }

    public  Flux<Tuple2<Long, Playlist>> savePlayListTupleFluxMock() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        Flux<Playlist> fluxPlayList = Flux.just(

                        Playlist.builder().nome("_10").build()
                )
                .flatMap(playListRepository::save);
        return Flux.zip(interval, fluxPlayList);
    }


}
