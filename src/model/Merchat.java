package model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Merchats")
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
@FieldNameConstants



@TableName( "Merchats") // 映射数据库表名
public class Merchat {

    @Id
    @TableId
    public String mchtId;// 用户ID

    public  String name;
    public String privatekey;
}
