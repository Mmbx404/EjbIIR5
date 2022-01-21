package metier.userejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import metier.beans.User;

@Stateless(name = "UserSession")
public class UserSession implements UserLocal, UserRemote{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public int save(User u) {
		// TODO Auto-generated method stub
		em.persist(u);
		return 1;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from User").getResultList();
	}

	@Override
	@Transactional
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		User user = em.find(User.class, id);
		em.remove(user);
		return 1;
	}

	@Override
	@Transactional
	public int update(User u) {
		if (u.getId() == null && u.getEmail() == null) {
			return -1;
		}
		if (u.getId() != null) {
			User oldUser = em.find(User.class, u.getId());
			oldUser.setNom(u.getNom());
			oldUser.setPrenom(u.getPrenom());
			oldUser.setTelephone(u.getTelephone());
			oldUser.setDateNaissance(u.getDateNaissance());
			em.merge(oldUser);
			return 1;
		} else if (u.getEmail() != null) {
			User oldUser = findByEmail(u.getEmail());
			//test
			oldUser.setNom(u.getNom());
			oldUser.setPrenom(u.getPrenom());
			oldUser.setTelephone(u.getTelephone());
			oldUser.setDateNaissance(u.getDateNaissance());
			em.merge(oldUser);
			return 1;
		}
		return -1;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		List<User> allUsers = findAll();
		for (User u : allUsers) {
			if (u.getEmail().equals(email)) return u;
		}
		return null;
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return em.find(User.class, id);
	}

}
