/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ASUS
 */
@Controller
@RequestMapping("/client/user/homecontroller")
public class HomeController {
    String room_api_url = "http://localhost:9999/api/roomcontroller";
    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    String user_api_url = "http://localhost:9999/api/users";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/homepage")
    public String page(Model model , @RequestParam("userId") int userId) {
        
        return "/users/home";
    }
    
    
}
