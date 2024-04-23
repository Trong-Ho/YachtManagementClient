/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.hotelclient.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class YachtDto {
    private int id;
    
    @NotBlank(message = "Yacht No must be fill!!!")
    private String yacht_no;
    
    @Min(value = 1 , message = "Yacht Price value must be greater than 0!!!")
    private double yacht_price;
    
    @NotBlank(message = "Yacht Image must be choose!!!")
    private String yacht_image;
    
    @Min(value = 1 , message = "Yacht Capacity value must be greater than 0!!!")
    private int yacht_capacity;
    
    @NotBlank(message = "Yacht Description must be fill!!!")
    private String yacht_description;
    
    private Boolean is_active;
    
    @Min(value = 1 , message = "Booking Status must be choose!!!")
    private int booking_status_id;
    private BookingStatusDto booking_status_info;
    
    @Min(value = 1 , message = "Yacht Type must be choose!!!")
    private int yacht_type_id;
    private YachtTypeDto yacht_type_info;
}
