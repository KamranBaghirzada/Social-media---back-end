package Socialmedia.UserManagementService.repository;

import Socialmedia.UserManagementService.dto.UserRequest;
import Socialmedia.UserManagementService.model.entity.QUser;
import Socialmedia.UserManagementService.model.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserCustomRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        super(User.class);
        this.entityManager = entityManager;
    }

    public List<User> findUsersByCriteria(UserRequest criteria) {
        QUser user = QUser.user;
        JPAQuery<User> query = new JPAQuery<>(entityManager);

        BooleanBuilder predicates = new BooleanBuilder();

        if (criteria.getFirstname() != null) {
            predicates.and(user.firstname.containsIgnoreCase(criteria.getFirstname()));
        }
        if (criteria.getLastname() != null) {
            predicates.and(user.lastname.containsIgnoreCase(criteria.getLastname()));
        }
        if (criteria.getBio() != null) {
            predicates.and(user.bio.containsIgnoreCase(criteria.getBio()));
        }
        if (criteria.getCity() != null) {
            predicates.and(user.city.containsIgnoreCase(criteria.getCity()));
        }
        if (criteria.getCountry() != null) {
            predicates.and(user.country.containsIgnoreCase(criteria.getCountry()));
        }
        if (criteria.getLegalAddress() != null) {
            predicates.and(user.legalAddress.containsIgnoreCase(criteria.getLegalAddress()));
        }
        if (criteria.getWorkplace() != null) {
            predicates.and(user.workplace.containsIgnoreCase(criteria.getWorkplace()));
        }
        if (criteria.getOccupation() != null) {
            predicates.and(user.occupation.containsIgnoreCase(criteria.getOccupation()));
        }

        return query.select(user)
                .from(user)
                .where(predicates)
                .fetch();
    }
}
