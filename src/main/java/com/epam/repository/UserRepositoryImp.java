package com.epam.repository;

import com.epam.model.User;
import com.epam.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 */
@Repository
public class UserRepositoryImp extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImp() {
        super(User.class);
    }

    @Override
    public void add(final User user) {
        super.add(user);
    }

    @Override
    public List<User> findAll() {
        return super.findAll("lastName");
    }

    @Override
    public User findById(final int userId) {
        return super.findByProperty("id", userId);
    }

    @Override
    public User findByEmail(final String email) {
        return super.findByProperty("email", email);
    }

    private static final UserRole client = new UserRole();
    @Override
    public List<User> getAllClients() {
        Criteria criteria = getCriteria();
        return criteria.createAlias("userRoles", "u")
                .add( Restrictions.eq("u.role", "ROLE_CLIENT") )
                .list();
    }
}
