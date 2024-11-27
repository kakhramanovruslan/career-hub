//package com.project.notificationservice;
//
//
//import com.project.notificationservice.dto.EmailMessageDto;
//import com.project.notificationservice.producer.EmailProducer;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RequestMapping("/notification")
//@RestController
//public class Controller {
//
//    private final EmailProducer kafkaMessageProducer;
//
//    @GetMapping("")
//    public ResponseEntity<Void> getCompanyById(){
//        kafkaMessageProducer.send("email", EmailMessageDto.builder().email("kahramanovruslan085@gmail.com").subject("Loreeem ipsum").body("Ipsssum lorem").build());
//        return ResponseEntity.ok().build();
//    }
//}
