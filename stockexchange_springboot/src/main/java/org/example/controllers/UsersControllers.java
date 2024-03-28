package org.example.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.example.kafka.KafkaMessageProducer;
import org.example.model.SignInRequest;
import org.example.model.SignUpRequest;
import org.example.model.Users;
import org.example.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class UsersControllers {



    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @GetMapping("/users")
    public ResponseEntity<Users> getUserInfo(@AuthenticationPrincipal Users users){
        return ResponseEntity.ok(users);
    }



    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest){
        try{
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(signUpRequest.getEmail())
                    .setPassword(signUpRequest.getPassword())
                    .setDisplayName(signUpRequest.getName())
                    .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            String uid = userRecord.getUid();
            String name = userRecord.getDisplayName();
            String email = userRecord.getEmail();
            Users users = new Users(uid,name,email);
            System.out.println("==============user UID===================");
            System.out.println("==============user===================");
            System.out.println(users.getName()+ users.getEmail()+ users.getId());
            usersRepository.save(users);
            return  ResponseEntity.ok("User created with UID: "+userRecord);
        }
        catch (FirebaseAuthException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user " +e.getMessage());
        }
    }




}
