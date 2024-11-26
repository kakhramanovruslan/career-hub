//package com.project.universityservice.config;
//
//import feign.Response;
//import feign.codec.ErrorDecoder;
//import org.springframework.http.HttpStatus;
//
//import java.sql.SQLException;
//
//public class CustomFeignErrorDecoder implements ErrorDecoder {
//
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        // Если статус ошибки 500 или другой, то создаем исключение, которое будет передаваться как есть
//        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
//            // Можно получать тело ошибки из response и возвращать более подробную информацию
//            return new SQLException("Error from student-service: " + response.body());
//        }
//
//        // Для других типов ошибок возвращаем стандартную обработку
//        return new Default().decode(methodKey, response);
//    }
//}
