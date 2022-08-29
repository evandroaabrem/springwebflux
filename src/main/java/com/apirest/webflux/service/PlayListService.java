package com.apirest.webflux.service;

import com.apirest.webflux.dto.PlayListRequestDto;
import com.apirest.webflux.dto.PlayListResponseDto;
import com.apirest.webflux.entity.Playlist;
import com.apirest.webflux.repository.PlayListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlayListService {
    private final PlayListRepository playListRepository;

    public Flux<Tuple2<Long, Playlist>> getTuple2Flux() {
        log.warn(("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now()));
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
        Flux<Playlist> playlistFlux = playListRepository.findAll();

        return Flux.zip(interval, playlistFlux);
    }

    public Flux<Tuple2<Long, Playlist>> savePlayListTupleFluxMock() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        Flux<Playlist> fluxPlayList = Flux.just(

                        Playlist.builder().nome("_10").build()
                )
                .flatMap(playListRepository::save);
        return Flux.zip(interval, fluxPlayList);
    }

    public Mono save(PlayListRequestDto playListDto) {
        return playListRepository.save(Playlist.builder().nome(playListDto.getNome()).build());
    }

    public Flux<Playlist> getAll() {
        return playListRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Playlist> getById(final String id) {
        return playListRepository.findById(id);
    }


    public Mono delete(final String id) {
        final Mono<Playlist> dbStudent = getById(id);
        if (Objects.isNull(dbStudent)) {
            return Mono.empty();
        }
        return getById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull).flatMap(studentToBeDeleted -> playListRepository
                .delete(studentToBeDeleted).then(Mono.just(studentToBeDeleted)));
    }

    public Mono<Playlist> update(String id, PlayListResponseDto  playListResponseDto) {
        final Mono<Playlist> playlistMono = getById(id);
        return playlistMono.flatMap(obj -> {
                    obj.setNome("teste");
                    return playListRepository.save(obj);
                }

        );
    }

}
