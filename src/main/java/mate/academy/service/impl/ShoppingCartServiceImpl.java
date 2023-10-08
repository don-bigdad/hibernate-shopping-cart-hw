package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.dao.TicketDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shopingCartDao;
    @Inject
    private TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        shopingCartDao.add(movieSession,user);
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        ticketDao.add(ticket);
        return ticket;
    }

    @Override
    public ShoppingCart getbyUser(User user) {
        return Optional.of(shopingCartDao.getByUser(user).get()).get();
    }

    @Override
    public void registerNewShoppingCart(User user) {
        shopingCartDao.create(user);
    }

    @Override
    public void clear(ShoppingCart shopingCart) {
        shopingCartDao.clear(shopingCart);
    }
}
