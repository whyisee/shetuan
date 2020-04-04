package com.shetuan.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 12:03
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
public class PrimaryKeyLoginCommID implements Serializable {

    private String loginId;

    private String commId;
}
