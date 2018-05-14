package hr.incom.common.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PorezOstalo extends IncomEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected Long id;

	@Column
	protected String naziv;

	@Column
	protected String stopa;

	@Column
	protected String osnovica;

	@Column
	protected String iznos;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNaziv()
	{
		return naziv;
	}

	public void setNaziv(String naziv)
	{
		this.naziv = naziv;
	}

	public String getStopa()
	{
		return stopa;
	}

	public void setStopa(String stopa)
	{
		this.stopa = stopa;
	}

	public String getOsnovica()
	{
		return osnovica;
	}

	public void setOsnovica(String osnovica)
	{
		this.osnovica = osnovica;
	}

	public String getIznos()
	{
		return iznos;
	}

	public void setIznos(String iznos)
	{
		this.iznos = iznos;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iznos == null) ? 0 : iznos.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((osnovica == null) ? 0 : osnovica.hashCode());
		result = prime * result + ((stopa == null) ? 0 : stopa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PorezOstalo other = (PorezOstalo) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (iznos == null)
		{
			if (other.iznos != null)
				return false;
		}
		else if (!iznos.equals(other.iznos))
			return false;
		if (naziv == null)
		{
			if (other.naziv != null)
				return false;
		}
		else if (!naziv.equals(other.naziv))
			return false;
		if (osnovica == null)
		{
			if (other.osnovica != null)
				return false;
		}
		else if (!osnovica.equals(other.osnovica))
			return false;
		if (stopa == null)
		{
			if (other.stopa != null)
				return false;
		}
		else if (!stopa.equals(other.stopa))
			return false;
		return true;
	}
}
