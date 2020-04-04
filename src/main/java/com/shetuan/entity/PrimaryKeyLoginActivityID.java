package com.shetuan.entity;

import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/4/4 15:22
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */
@Data
public class PrimaryKeyLoginActivityID implements Serializable {
    private String loginId;

    private String activityId;
}
