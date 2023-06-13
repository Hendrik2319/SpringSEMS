package net.schwarzbaer.java.spring.sems.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String  street;
    private Integer housenumber;
    private String  town;
    private Integer zipcode;

    protected Address() {}

    public Address(String street, Integer housenumber, String town, Integer zipcode) {
        this.street = street;
        this.housenumber = housenumber;
        this.town = town;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return String.format(
            "Address[ id=%d, street=\"%s\", housenumber=%d, town=\"%s\", zipcode=%d ]",
             id, street, housenumber, town, zipcode
        );
    }

    public void setStreet      (String  street     ) { this.street      = street;      }
    public void setHousenumber (Integer housenumber) { this.housenumber = housenumber; }
    public void setTown        (String  town       ) { this.town        = town;        }
    public void setZipcode     (Integer zipcode    ) { this.zipcode     = zipcode;     }
    public String  getStreet     () { return street ;     }
    public Integer getHousenumber() { return housenumber; }
    public String  getTown       () { return town;        }
	public Integer getZipcode    () { return zipcode;     }
}
