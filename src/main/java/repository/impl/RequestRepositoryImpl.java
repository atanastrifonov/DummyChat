package repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Request;
import repository.capi.RequestRepository;

@Repository
@Transactional
public class RequestRepositoryImpl implements RequestRepository {

	@PersistenceContext
	private EntityManager em;

	public void addRequest(Request request) {
		em.persist(request);
	}

}
