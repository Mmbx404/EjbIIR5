package metier.smartphoneejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import metier.beans.SmartPhone;
import metier.beans.User;
import metier.userejb.UserLocal;
import metier.userejb.UserSession;

@Stateless
public class SmartPhoneSession implements SmartPhoneLocal, SmartPhoneRemote {

	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private UserLocal userLocal;

	@Override
	public int save(SmartPhone s) {
		if (s.getUser() != null) {
			User u = em.getReference(User.class, s.getUser().getId());
			s.setUser(u);
			em.persist(s);
			return 1;
		}
		// TODO Auto-generated method stub
		em.persist(s);
		return 1;
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		SmartPhone sm = em.find(SmartPhone.class, id);
		em.remove(sm);
		return 1;
	}

	@Override
	public List<SmartPhone> findAll() {
		Query query = em.createQuery("select s from SmartPhone s");
		return query.getResultList();
	}

	@Override
	public int update(SmartPhone s) {
		if (s.getId() == null && s.getRef() == null) {
			return -1;
		}
		if (s.getId() != null) {
			SmartPhone oldSmartPhone = em.find(SmartPhone.class, s.getId());
			oldSmartPhone.setMarque(s.getMarque()==null?null:s.getMarque());
			oldSmartPhone.setName(s.getName()==null?null:s.getName());
			oldSmartPhone.setUser(s.getUser()==null?null:s.getUser());
			oldSmartPhone.setPosition(s.getPosition() == null?null:s.getPosition());
			em.merge(oldSmartPhone);
			return 1;
		} else if (s.getRef() != null) {
			SmartPhone oldSmartPhone = findByRef(s.getRef());
			oldSmartPhone.setMarque(s.getMarque()==null?null:s.getMarque());
			oldSmartPhone.setName(s.getName()==null?null:s.getName());
			oldSmartPhone.setUser(s.getUser()==null?null:s.getUser());
			oldSmartPhone.setPosition(s.getPosition() == null?null:s.getPosition());
			em.merge(oldSmartPhone);
			return 1;
		}
		return -1;
	}

	@Override
	public SmartPhone findByRef(String ref) {
		// TODO Auto-generated method stub
		for (SmartPhone s : findAll()) {
			if (s.getRef().equals(ref)) {
				return s;
			}
		}
		return null;
	}

	@Override
	public SmartPhone findById(Long id) {
		// TODO Auto-generated method stub
		return em.find(SmartPhone.class, id);
	}

	@Override
	public List<SmartPhone> findPhonesByUserId(Long id) {
		User u = userLocal.findById(id);
		List<SmartPhone> result = new ArrayList<SmartPhone>();
		if (u == null) return null;
		for (SmartPhone sp : findAll()) {
			if (sp.getUser().equals(u)) {
				result.add(sp);
			}
		}
		return result;
	}

}
