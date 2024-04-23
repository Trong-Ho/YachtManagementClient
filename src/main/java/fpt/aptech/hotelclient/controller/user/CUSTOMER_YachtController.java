/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.user;

import fpt.aptech.hotelclient.dto.BookingDto;
import fpt.aptech.hotelclient.dto.YachtDto;
import fpt.aptech.hotelclient.dto.SearchDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/client/customer/yachtcontroller")
public class CUSTOMER_YachtController {
    
    String yacht_api_url = "http://localhost:9999/api/yachtcontroller";
    String booking_api_url = "http://localhost:9999/api/bookingcontroller";
    
    RestTemplate _restTemplate = new RestTemplate();
    
    @GetMapping("/all")
    public String page(Model model , @RequestParam("userId") int userId) {
        List<YachtDto> allYachtList = _restTemplate.getForObject(yacht_api_url+"/allyachtactive", List.class);
        model.addAttribute("userId", userId);
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("allYachtList", allYachtList);
        return "users/yachts";
    }
    
    @GetMapping("/details")
    public String yachtDetailsPage(Model model , @RequestParam("userId") int userId , @RequestParam("yachtId") int yachtId) {
        System.out.println(userId);
        System.out.println(yachtId);
        
        YachtDto yachtDetail = _restTemplate.getForObject(yacht_api_url+"/find/"+yachtId, YachtDto.class);
        model.addAttribute("userId", userId);
        model.addAttribute("yachtDetail", yachtDetail);
        return "users/yacht-details";
    }
    
    @PostMapping("/searchYacht")
    public String searchYachtPage(Model model , @RequestParam("userId") int userId , @ModelAttribute("searchDto") SearchDto searchDto) {
        System.out.println(searchDto);
        List<YachtDto> allYachtAvailable = _restTemplate.postForObject(booking_api_url+"/availableyachtforbooking", searchDto , List.class);
        model.addAttribute("userId", userId);
        model.addAttribute("searchDto", new SearchDto());
        model.addAttribute("allYachtList", allYachtAvailable);
        return "users/yachts";
    }
    
    @GetMapping("/gotobooking")
    public String customerGoToBooking(Model model , @RequestParam("userId") int userId , @RequestParam("yachtId") int yachtId) {
        BookingDto newBookingDto = new BookingDto();
        newBookingDto.setYacht_id(yachtId);
        newBookingDto.setCustomer_id(userId);
        
        model.addAttribute("userId", userId);
        model.addAttribute("newBookingDto", newBookingDto);
        return "users/yacht_booking";
    }
    
    @PostMapping("/confirmbookingdetail")
    public String customerConfirmBookingDetail(Model model, @RequestParam("userId") int userId , @ModelAttribute("newBookingDto") BookingDto newBookingDto) {
        if(userId == 0) {

            model.addAttribute("errorBooking", "Please Login To Booking");
            return "login";
        }
        
        newBookingDto.setIs_active(true);
        BookingDto bookingDetail = _restTemplate.postForObject(booking_api_url+"/confirmbookingdetail", newBookingDto, BookingDto.class);
        
        if(bookingDetail == null) {
            BookingDto latestBooking = _restTemplate.getForObject(booking_api_url+"/findthelatestbookingofayacht/"+newBookingDto.getYacht_id(), BookingDto.class);
            model.addAttribute("userId", userId);
            model.addAttribute("bookingErrMessage", "This yacht is already booked, Please choose date after: " + latestBooking.getBooking_to().toString());
            return "users/yacht_booking";
        }
        else {
            if(bookingDetail.getYacht_info().getYacht_capacity() < newBookingDto.getNumber_of_member()){
                model.addAttribute("userId", userId);
                model.addAttribute("bookingErrMessage", "This yacht capacity is only: " + bookingDetail.getYacht_info().getYacht_capacity() + " people!!!");
                return "users/yacht_booking";
            }
            else {
                model.addAttribute("userId", userId);
                model.addAttribute("newBookingDto", bookingDetail);
                return "users/yacht_booking_detail";
            }
        }
    }
    
    @PostMapping("/customercreatebooking")
    public String customerCreateNewBooking(Model model , @RequestParam("userId") int userId , @ModelAttribute("newBookingDto") BookingDto newBookingDto) {
        newBookingDto.setIs_active(true);
        _restTemplate.postForObject(booking_api_url+"/createbookingbycustomer", newBookingDto, BookingDto.class);
        return "redirect:http://localhost:8888/client/customer/profilecontroller/all?userId="+userId;
    }
    
}
