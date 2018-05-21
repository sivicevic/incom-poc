package hr.incom.backend.information;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hr.incom.common.domain.model.old.Racun;

@Stateless
public class Information implements IInformation
{
	//@PersistenceContext(unitName = "incom-base2")
	//private EntityManager em;

	@Override
	public void storeInvoice()
	{
		Racun r = new Racun();
		r.setBrOznRac("AAA");
		//em.persist(r);
	}
}
