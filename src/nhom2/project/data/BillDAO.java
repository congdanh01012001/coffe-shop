package nhom2.project.data;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import nhom2.project.model.Bill;
import nhom2.project.util.HibernateUtil;

public class BillDAO {
	public void saveBill(Bill bill) {
		Transaction trans = null;
		try (Session ss = HibernateUtil.getSessionFactory().openSession()) {
			trans = ss.beginTransaction();
			ss.save(bill);
			trans.commit();
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void updateBill(Bill bill) {
		Transaction trans = null;
		try (Session ss = HibernateUtil.getSessionFactory().openSession()) {
			trans = ss.beginTransaction();
			String hql = "UPDATE Bill SET status = :status " + "WHERE id = :id";
			Query query = ss.createQuery(hql);
			query.setParameter("status", bill.getStatus());
			query.setParameter("id", bill.getId());
			query.executeUpdate();
			trans.commit();
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void deleteBill(int id) {
		Transaction trans = null;
		try (Session ss = HibernateUtil.getSessionFactory().openSession()) {
			trans = ss.beginTransaction();
			Bill bill = ss.get(Bill.class, id);
			if (bill != null) {
				String hql = "DELETE FROM Bill WHERE id = :id";
				Query query = ss.createQuery(hql);
				query.setParameter("id", id);
				query.executeUpdate();
			}
			trans.commit();

		} catch (Exception e) {
			if (trans != null)
				trans.commit();
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	public Bill getBill(int id) {
		Transaction trans = null;
		Bill bill = null;
		try (Session ss = HibernateUtil.getSessionFactory().openSession()) {
			trans = ss.beginTransaction();
			bill = ss.get(Bill.class, id);
			trans.commit();
		} catch (Exception e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			// TODO: handle exception
		}
		return bill;
	}

	@SuppressWarnings("unchecked")
	public List<Bill> getAllBill() {
		try {
			return HibernateUtil.getSessionFactory().openSession().createQuery("FROM Bill").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
}