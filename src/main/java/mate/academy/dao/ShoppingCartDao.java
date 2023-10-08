package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;

public interface ShoppingCartDao {
    ShoppingCart add(ShoppingCart shoppingCart);

    ShoppingCart add(MovieSession movieSession, User user);

    Optional<ShoppingCart> getByUser(User user);

    void update(ShoppingCart shoppingCart);

    void clear(ShoppingCart shoppingCart);

    void create(User user);
}
