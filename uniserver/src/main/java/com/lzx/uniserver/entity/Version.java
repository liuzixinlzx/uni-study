package com.lzx.uniserver.entity;

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
 * @since 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Version implements Serializable {

    private static final long serialVersionUID=1L;

    private String name;

    private String version;

    private String updateUrl;

    private String type;
}
