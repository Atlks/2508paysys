package model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "LoginSessions")
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
@FieldNameConstants
public class LoginSession {

    public String uname;
    @Id
    public  String token;
    public  String enable="yes";
}
