/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.BookingStatusDto;
import fpt.aptech.hotelclient.dto.YachtDto;
import fpt.aptech.hotelclient.dto.YachtTypeDto;
import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/client/admin/yachtcontroller")
public class ADMIN_YachtController {
    
    String yacht_api_url = "http://localhost:9999/api/yachtcontroller";
    String yachtType_api_url = "http://localhost:9999/api/yachttypecontroller";
    String bookingStatus_api_url = "http://localhost:9999/api/bookingstatuscontroller";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String allYachtPage(Model model) {
        List<YachtDto> allyachtList = _restTemplate.getForObject(yacht_api_url + "/allyachtsortedbyactive", List.class);
        model.addAttribute("allyachtList", allyachtList);
        return "admin/yacht/index";
    }
    
    @GetMapping("/gotocreate")
    public String createYachtPage(Model model) {
        List<YachtTypeDto> allYachtTypeList = _restTemplate.getForObject(yachtType_api_url+"/all", List.class);
        YachtDto newYachtDto = new YachtDto();
        newYachtDto.setBooking_status_id(1);
        model.addAttribute("allYachtTypeList", allYachtTypeList);
        model.addAttribute("newYachtDto", newYachtDto);
        return "admin/yacht/create";
    }
    
    @PostMapping("/create")
    public String createNewYacht(Model model , @ModelAttribute("newYachtDto") @Valid YachtDto newYachtDto , BindingResult bdResult) {
        if(bdResult.hasErrors()) {
            List<YachtTypeDto> allYachtTypeList = _restTemplate.getForObject(yachtType_api_url+"/all", List.class);
            model.addAttribute("allYachtTypeList", allYachtTypeList);
            return "admin/yacht/create";
        }
        
        newYachtDto.setIs_active(true);
        _restTemplate.postForObject(yacht_api_url+"/create", newYachtDto ,YachtDto.class);
        return "redirect:http://localhost:8888/client/admin/yachtcontroller/all";
    }
    
    @GetMapping("/gotoupdate/{yachtId}")
    public String updateYachtPage(Model model , @PathVariable("yachtId") int yachtId) {
        YachtDto yachtDtoInfo = _restTemplate.getForObject(yacht_api_url+"/find/"+yachtId , YachtDto.class);
        List<YachtTypeDto> allYachtTypeList = _restTemplate.getForObject(yachtType_api_url+"/all", List.class);
        List<BookingStatusDto> allBookingStatusList = _restTemplate.getForObject(bookingStatus_api_url+"/all", List.class);
        model.addAttribute("allYachtTypeList", allYachtTypeList);
        model.addAttribute("allBookingStatusList", allBookingStatusList);
        model.addAttribute("updateYachtDto", yachtDtoInfo);
        return "admin/yacht/update";
    }
    
    @PostMapping("/update")
    public String updateYacht(Model model , @ModelAttribute("updateYachtDto") @Valid YachtDto updateYachtDto , BindingResult bdResult) {
        if(bdResult.hasErrors()) {
            List<YachtTypeDto> allYachtTypeList = _restTemplate.getForObject(yachtType_api_url+"/all", List.class);
            model.addAttribute("allYachtTypeList", allYachtTypeList);
            return "admin/yacht/update";
        }
        
        _restTemplate.put(yacht_api_url+"/update", updateYachtDto);
        
        return "redirect:http://localhost:8888/client/admin/yachtcontroller/all";
    }
    
    @GetMapping("/activeyacht/{yachtId}")
    public String activeYacht(Model model , @PathVariable("yachtId") int yachtId) {
        YachtDto yachtDtoInfo = _restTemplate.getForObject(yacht_api_url+"/find/"+yachtId , YachtDto.class);
        yachtDtoInfo.setIs_active(true);
        
        _restTemplate.put(yacht_api_url+"/update", yachtDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/yachtcontroller/all";
    }
    
    @GetMapping("/disableyacht/{yachtId}")
    public String disableYacht(Model model , @PathVariable("yachtId") int yachtId) {
        YachtDto yachtDtoInfo = _restTemplate.getForObject(yacht_api_url+"/find/"+yachtId , YachtDto.class);
        yachtDtoInfo.setIs_active(false);
        
        _restTemplate.put(yacht_api_url+"/update", yachtDtoInfo);
        
        return "redirect:http://localhost:8888/client/admin/yachtcontroller/all";
    }
}
