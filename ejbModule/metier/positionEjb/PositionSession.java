package metier.positionEjb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import metier.beans.Position;
import metier.smartphoneejb.SmartPhoneLocal;

/**
 * Session Bean implementation class Position
 */
@Stateless(name = "PositionSession")
@LocalBean
public class PositionSession implements PositionRemote, PositionLocal {
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	private SmartPhoneLocal smartPhoneLocal;
	
    /**
     * Default constructor. 
     */
    public PositionSession() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public int save(Position p) {
		// TODO Auto-generated method stub
		if (p.getSmartPhone() != null) {
			if (p.getSmartPhone().getRef() == null && p.getSmartPhone().getRef() == null) return -1;
			else {
				if (p.getSmartPhone().getRef() != null) {
					p.setSmartPhone(smartPhoneLocal.findByRef(p.getSmartPhone().getRef()));
				} else {
					p.setSmartPhone(smartPhoneLocal.findById(p.getSmartPhone().getId()));
				}
			}
		}
		entityManager.persist(p);
		return 1;
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		Position p = findById(id);
		if (p == null) return -1;
		entityManager.remove(p);
		return 1;
	}

	@Override
	public List<Position> findAll() {
		// TODO Auto-generated method stub
		return entityManager.createQuery("from Position").getResultList();
	}

	@Override
	public int update(Position p) {
		if (p.getId() == null) {
			return -1;
		}
		Position oldPosition = findById(p.getId());
		if (oldPosition == null) {
			return -2;
		}
		oldPosition.setLatitude(p.getLatitude()==null?oldPosition.getLatitude():p.getLatitude());
		oldPosition.setLongitude(p.getLongitude()==null?oldPosition.getLongitude():p.getLongitude());
		oldPosition.setDate(p.getDate()==null?oldPosition.getDate():p.getDate());
		oldPosition.setSmartPhone(p.getSmartPhone()==null?oldPosition.getSmartPhone():p.getSmartPhone());
		entityManager.merge(oldPosition);
		return 1;
	}

	@Override
	public Position findById(Long id) {
		// TODO Auto-generated method stub
		return entityManager.find(Position.class, id);
	}

}
