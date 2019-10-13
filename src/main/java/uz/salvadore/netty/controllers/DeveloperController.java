package uz.salvadore.netty.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.*;
import uz.salvadore.netty.annotations.RequestMapping;
import uz.salvadore.netty.annotations.RestController;
import uz.salvadore.netty.models.Developer;
import uz.salvadore.netty.services.DeveloperService;
import uz.salvadore.netty.services.impl.DeveloperServiceImpl;

import java.util.List;

import static io.netty.buffer.Unpooled.copiedBuffer;

@RestController
@RequestMapping(value = "developers")
public class DeveloperController {

    private final DeveloperService service;

    public DeveloperController() {
        this.service = new DeveloperServiceImpl();
    }

    @RequestMapping(value = "findAll")
    public FullHttpResponse findAll(FullHttpRequest request) {
        FullHttpResponse response;
        try {
            List<Developer> developers = service.findAll();
            developers.stream().forEach(System.out::println);
            String message = new ObjectMapper().writeValueAsString(developers);
            response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    copiedBuffer(message.getBytes()));
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, message.length());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST);
        }
        return response;
    }

}
