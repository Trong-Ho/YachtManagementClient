/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package fpt.aptech.hotelclient.controller.admin;

import fpt.aptech.hotelclient.dto.PaymentDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author TuanNguyen
 */

@Controller
@RequestMapping("/client/admin/paymentcontroller")
public class ADMIN_PaymentController {
    String payment_api_url = "http://localhost:9999/api/paymentcontroller";
    RestTemplate rest = new RestTemplate();
    @GetMapping("/all")
    public String AdminPage(Model model) {
        List<PaymentDto> list = rest.getForObject(payment_api_url + "/all", List.class);
        model.addAttribute("paymentlist", list);
        return "admin/payment/index";
    }
    
}
