package com.lzx.uniserver.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author alex wong
 * @since 2020-08-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Course implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private LocalDate date;

    private String name;

    private LocalTime begintime;

    private LocalTime endtime;


}
