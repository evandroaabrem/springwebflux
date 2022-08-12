package com.apirest.webflux.controller;

import com.apirest.webflux.entity.Playlist;
import com.apirest.webflux.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api-playlist")
public class PlayListController {
    @Autowired
    private PlayListService playListService;


    @GetMapping()
    public Flux<Playlist> getPlaylist(){
        return playListService.findAll();
    }


    @GetMapping(value="{id}")
    public Mono<Playlist> getPlaylistId(@PathVariable String id){
        return playListService.findById(id);
    }

    @PostMapping()
    public Mono<Playlist> savePoste() {
        return playListService.savePlay();
    }


    @GetMapping(value="webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByWebflux(){

        return playListService.getTuple2Flux();

    }

    @PostMapping(value="webflux/savePlayListWebflux")
    public  Flux<Tuple2<Long, Playlist>>  saveMock() {
        return playListService.savePlayListTupleFluxMock();
    }


}
