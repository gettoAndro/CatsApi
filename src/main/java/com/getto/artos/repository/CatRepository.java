package com.getto.artos.repository;

import com.getto.artos.domain.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat, Long> {

}
