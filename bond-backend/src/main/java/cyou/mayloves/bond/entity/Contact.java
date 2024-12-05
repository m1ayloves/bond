package cyou.mayloves.bond.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Contacts")
public class Contact {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer no;

    /**
     * 用户 ID
     */
    @JsonIgnore // 生成 Json 时忽略该字段
    @TableField("user_id")
    private Integer userId;

    /**
     * 联系人名称
     */
    @TableField("name")
    private String name;

    /**
     * 联系人标签
     */
    @TableField("tag")
    private String tag;

    /**
     * 性别（"M" 或 "F"）
     */
    @TableField("sex")
    private String sex;

    /**
     * 出生日期
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @TableField("birthday")
    private Date birthday;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱地址
     */
    @TableField("email")
    private String email;

    /**
     * 通信地址
     */
    @TableField("address")
    private String address;

    /**
     * 描述信息
     */
    @TableField("description")
    private String description;
}