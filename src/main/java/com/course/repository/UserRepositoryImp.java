package com.course.repository;

import com.course.model.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fg on 7/24/2016.
 * Realisation of user DAO
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

    @Override
    public List<User> getAllClients() {
        return getCriteria().createAlias("userRoles", "u")
                .add( Restrictions.eq("u.role", "ROLE_CLIENT") )
                .list();
    }
}
