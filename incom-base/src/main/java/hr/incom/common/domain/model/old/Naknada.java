package hr.incom.common.domain.model.old;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Naknada extends IncomEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	protected Long id;

	@Column
	protected String nazivN;

	@Column
	protected String iznosN;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNazivN()
	{
		return nazivN;
	}

	public void setNazivN(String nazivN)
	{
		this.nazivN = nazivN;
	}

	public String getIznosN()
	{
		return iznosN;
	}

	public void setIznosN(String iznosN)
	{
		this.iznosN = iznosN;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((iznosN == null) ? 0 : iznosN.hashCode());
		result = prime * result + ((nazivN == null) ? 0 : nazivN.hashCode());
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
		Naknada other = (Naknada) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (iznosN == null)
		{
			if (other.iznosN != null)
				return false;
		}
		else if (!iznosN.equals(other.iznosN))
			return false;
		if (nazivN == null)
		{
			if (other.nazivN != null)
				return false;
		}
		else if (!nazivN.equals(other.nazivN))
			return false;
		return true;
	}
}
