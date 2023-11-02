package com.hostfully.reservation.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.hostfully.reservation.dto.ResponseDTO;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * This class is a utility class that has most frequently used method that can be shared amongst other classess
 * @author Frederick Wordie
 */

public class AppUtil {

    /**
     * Converts Date of format "yyyy-MM-dd" to an offsetDatetime of format yyyy-MM-dd'T'HH:mm:ss
     * @param str string of the date
     * @return {@link OffsetDateTime}
     * 
     */
    public static OffsetDateTime getOffSetDateTime(String str){
        if(str.strip().equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is wrong");
        }

        LocalDate localDate = localDateParser(str);
        OffsetDateTime offsetDateTime = localDate.atTime(0,0).atOffset(ZoneOffset.UTC);
        return offsetDateTime;
    }

    public static LocalDate localDateParser(String ds){
        try {
            // Parse the date string using the specified format
            LocalDate parsedDate = LocalDate.parse(ds, formatter());
            System.out.println("Parsed date: " + parsedDate);
            return parsedDate;
        } catch (Exception e) {
            // Date does not match the expected format
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date format must be yyyy-MM-dd");
        }
    }



    /**
     * This returns the specific format needed for the offsetdatetime
     * @return {@link DateTimeFormatter}
     */
    public static DateTimeFormatter formatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }


    /**
     * This method is used to validat that a value is
     * not an empty string, neither an empty list nor a null
     * @param str
     * @return
     */
    public static boolean isNotNullOrEmpty(Object str){
            try {
                if(str == null){
                    return false;
                }
                if(str instanceof Collection<?>){
                    return !((Collection<?>) str).isEmpty();
                }
                return !str.toString().trim().equalsIgnoreCase("");
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
    }

    /**
     * This checks if something is not null or zero
     * @param str object to be checked
     * @return
     */
    public static boolean isNotNullOrZero(Object str){
        if(str == null)
            return false;

        else return !(((Integer) str) == 0);

    }


    /**
     * This method is used to as a wrapper to return a well structured response from this service
     * @param message
     * @param status
     * @param data
     * @return
     */
    public static ResponseDTO getResponseDTO(String message, HttpStatus status, Object data){
        ResponseDTO responseDTO = new ResponseDTO();
        if(isNotNullOrEmpty(data)){
            responseDTO.setData(data);
        }
        responseDTO.setMessage(message);
        responseDTO.setStatus(status);
        responseDTO.setDate(OffsetDateTime.now());

        return responseDTO;
    }

    /**
     * This method makes use of the getResponseDTO which is used to structure reponses to all requests in the service.
     * This makes it simpler for debugging, and reading responses.
     * @param message
     * @param status
     * @return
     */
    public static ResponseDTO getResponseDTO(String message, HttpStatus status){
        return getResponseDTO(message, status, null);
    }

}
