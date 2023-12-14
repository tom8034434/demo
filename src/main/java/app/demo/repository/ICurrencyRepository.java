package app.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.demo.entity.DefCurrencyEntity;

@Repository
public interface ICurrencyRepository extends JpaRepository<DefCurrencyEntity, String> {
}