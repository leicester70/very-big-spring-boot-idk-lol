package com.verybigspringbootidklol.services.controllers;

import com.verybigspringbootidklol.models.ForFun.World;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/forfun")
public class ForFunController {
    private World world;
    @GetMapping("/helloworld")
    public ResponseEntity<Map<String,Object>> CheckWorld(){
        Map<String,Object> data = new HashMap<>();
        if (world == null) {
            data.put("message","there is no world; world == null");
            return ResponseEntity.ok(data);
        }
        data.put("worldData",new HashMap<>(){{
            put("worldName",world.name);
            put("worldCurrentTime",world.currentTime);
        }});
        return ResponseEntity.ok(data);
    }


}
