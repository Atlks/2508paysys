package util.model;

import lombok.Data;

@Data
public class AdminLoginDto {

    public  String u;
    public  String pwd;
    public String cptch="";
}
