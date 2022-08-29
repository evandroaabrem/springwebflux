package com.apirest.webflux.controller;

import com.apirest.webflux.dto.PlayListRequestDto;
import com.apirest.webflux.dto.PlayListResponseDto;
import com.apirest.webflux.entity.Playlist;
import com.apirest.webflux.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api-playlist")
public class PlayListController {
    private final PlayListService playListService;


    @GetMapping()
    public Flux<Playlist> getAll() {
        return playListService.getAll();
    }

    @DeleteMapping
    public Mono<Playlist> deletePlaylistId(@PathVariable String id) {
        return playListService.delete(id);
    }

    @GetMapping(value = "{id}")
    public Mono<Playlist> getPlaylistId(@PathVariable String id) {
        return playListService.getById(id);
    }

    @PostMapping()
    public Mono<Playlist> save(@RequestBody PlayListRequestDto playListDto) {
        return playListService.save(playListDto);
    }


    @GetMapping(value = "webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByWebflux() {

        return playListService.getTuple2Flux();

    }

    @PostMapping(value = "webflux/savePlayListWebflux")
    public Flux<Tuple2<Long, Playlist>> saveMock() {
        return playListService.savePlayListTupleFluxMock();
    }


    @PutMapping(path = "update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Playlist> update(@PathVariable String id, @RequestBody PlayListResponseDto playListResponseDto) {
        return playListService.update(id, playListResponseDto);
    }


}
