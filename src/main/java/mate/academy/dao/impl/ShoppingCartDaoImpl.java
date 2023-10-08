package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.lib.Dao;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public ShoppingCart add(ShoppingCart shopingCart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(shopingCart);
            transaction.commit();
            return shopingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t add cart to Db:" + shopingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart add(MovieSession movieSession, User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            //я не розумію логіку(навіщо оце треба і як його реалізовувати)
            // коли у shoping cart навіть поля movieSession немає
            // і яку команду виконувати (insert,update?)
            //session.createQuery("insert into ShoppingCart ()");
            transaction.commit();
            return new ShoppingCart();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t add cart to Db:", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<ShoppingCart> getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select sc from ShoppingCart sc "
                    + "left join fetch sc.ticketList "
                    + "where sc.user.id = :user_id";
            Query<ShoppingCart> query = session.createQuery(hql, ShoppingCart.class);
            query.setParameter("user_id", user.getId());
            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            throw new RuntimeException("Can`t get cart by user id from DB: " + user, e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t update cart to Db:" + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        try (Session session = sessionFactory.openSession()) {
            session.createQuery("delete from ShoppingCart where id = :cart_id",ShoppingCart.class)
                    .setParameter("cart_id",shoppingCart.getId())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Can`t clear cart in DB: " + shoppingCart, e);
        }
    }

    @Override
    public void create(User user) {
        Session session = null;
        Transaction transaction = null;
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            shoppingCart.setUser(user);
            session.persist(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t add cart to user to Db: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
