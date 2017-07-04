package com.spring.example.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;

@Entity(name = "site_details")  
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "list", procedureName = "bs_site_list", parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "name", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "price_low", type = BigDecimal.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "price_high", type = BigDecimal.class),
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "site_type", type = String.class),
            @StoredProcedureParameter(mode = ParameterMode.OUT, name = "site_id", type = Long.class) })
})
public class SiteDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    @Id()
    @Column(name = "site_id")
	private Long siteId;
}
