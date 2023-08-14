package com.example.newApi.controller;

import com.example.newApi.contract.User;
import com.example.newApi.exceptions.*;
import com.example.newApi.model.CreateUserRequest;
import com.example.newApi.model.UserRequest;
import com.example.newApi.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsApiController {
        @Autowired
        private NewsApiService newsApiService;

        @GetMapping("/hardCodedData")
        public ResponseEntity<?> readHardCodedData(){
                return ResponseEntity.ok(newsApiService.getHardCodedData());
        }

        @GetMapping("/csvCodedData")
        public ResponseEntity<?> readCsvCodedData(){
                return  ResponseEntity.ok(newsApiService.getCsvCodedData());
        }

        @PostMapping("/csvEdit")
        public ResponseEntity<String> addCountriesInCsvData(@RequestBody List<UserRequest> userRequest){
                return ResponseEntity.ok(newsApiService.addCountriesAndCategories(userRequest));
        }

        @PostMapping("/user")
        public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest){
                try{
                        return ResponseEntity.ok(newsApiService.createUser(createUserRequest));
                } catch(InvalidEmailException | InvalidCategoryException | InvalidCountryException |
                        InvalidCountryAndCategoryException | UserAlreadyExistException e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping("/headlines")
        public ResponseEntity<?> topHeadlines(@RequestParam String userId,
                                              @RequestParam(required = false, defaultValue = "99") int maxArticles,
                                              @RequestParam(required = false, defaultValue = "") String from,
                                              @RequestParam(required = false, defaultValue = "") String to,
                                              @RequestParam(required = false, defaultValue = "true") boolean matchToSources){
                try{
                        return ResponseEntity.ok(newsApiService.getTopHeadlines(userId, maxArticles, from, to, matchToSources));
                }
                catch(UserNotExistException | InvalidDateException e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @GetMapping("/sources")
        public ResponseEntity<?> getSources(@RequestParam String userId){
                try{
                        return ResponseEntity.ok(newsApiService.getSources(userId));
                }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        @PostMapping("email-top-headline")
        public ResponseEntity<?> emailTopHeadlines(@RequestParam String userId){
                try{
                        return ResponseEntity.ok(newsApiService.emailTopHeadlines(userId));
                }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }
        @PostMapping("leaderboard")
        public ResponseEntity<?> getLeaderboard(){
                try{
                        return ResponseEntity.ok(newsApiService.getLeaderboard());
                }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }
        @PostMapping("unsubscribe")
        public ResponseEntity<?> unsubscribe(@RequestParam String userId){
                try{
                        return ResponseEntity.ok(newsApiService.unsubscribe(userId));
                }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }
        @PostMapping("subscribe")
        public ResponseEntity<?> subscribe(@RequestParam String userId){
                try{
                        return ResponseEntity.ok(newsApiService.subscribe(userId));
                }
                catch(Exception e){
                        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

}
