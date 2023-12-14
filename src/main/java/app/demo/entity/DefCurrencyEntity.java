package app.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "def_currency")
@Data
public class DefCurrencyEntity implements Serializable {

	private static final long serialVersionUID = 8526768950329584787L;

	@Id
	@Column(name = "currency_code", nullable = false)
	private String currencyCode;

	@Column(name = "description", nullable = false)
	private String description;

}
