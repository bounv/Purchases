package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by boun on 1/26/17.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
