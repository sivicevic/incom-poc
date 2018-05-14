package hr.incom.common.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class IncomEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Version
	private Timestamp updated;

	public Timestamp getUpdated()
	{
		return updated;
	}

	public void setUpdated(Timestamp updated)
	{
		this.updated = updated;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IncomEntity other = (IncomEntity) obj;
		if (updated == null)
		{
			if (other.updated != null)
				return false;
		}
		else if (!updated.equals(other.updated))
			return false;
		return true;
	}

}
