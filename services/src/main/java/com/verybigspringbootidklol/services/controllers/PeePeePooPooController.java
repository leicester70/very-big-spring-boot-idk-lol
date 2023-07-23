package com.verybigspringbootidklol.services.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.HashMap;

@RestController
@RequestMapping("/peepeepoopoo")
public class PeePeePooPooController {
    @GetMapping("/test")
    public ResponseEntity<JsonObject> peePeePooPooTest() {
        JsonObject response = Json.createObjectBuilder().add("message","You've called the peepeepoopoo test").build();
        return ResponseEntity.ok(response.asJsonObject());
    }

    @GetMapping("/test0")
    public ResponseEntity<String> peePeePooPooTest0() {
        String message = "You've called the peepeepoopoo test";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/test1") public ResponseEntity<Object> peePeePooPooTest1() {
        return ResponseEntity.ok(new HashMap<String,String>(){{put("message","You've called the peepeepoopoo test");}});
    }
}
