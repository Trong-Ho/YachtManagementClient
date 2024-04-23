/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.YachtDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/user/yacht")
public class YachtController {

    String yacht_api_url = "http://localhost:9999/api/yachtcontroller";
    RestTemplate _restTemplate = new RestTemplate();

    @GetMapping("/all")
    public String page(Model model) {
        List<YachtDto> allRoomList = _restTemplate.getForObject(yacht_api_url + "/allyachtsortedbyactive", List.class);
        model.addAttribute("allRoomList", allRoomList);
        return "/users/yachts";
    }

    @GetMapping("/{yachtId}/details")
    public String details(@PathVariable("yachtId") int yachtId, Model model) {
        YachtDto room = _restTemplate.getForObject(yacht_api_url + "/find/" + yachtId, YachtDto.class);
        model.addAttribute("room", room);
        return "/users/yacht-details";
    }

}
