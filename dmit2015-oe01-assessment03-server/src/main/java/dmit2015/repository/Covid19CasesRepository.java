package dmit2015.repository;

import dmit2015.entity.Covid19Cases;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class Covid19CasesRepository {

    @PersistenceContext
    private EntityManager _entityManager;

    public void create(Covid19Cases newCovid19Cases) {
        _entityManager.persist(newCovid19Cases);
    }

    public Optional<Covid19Cases> findOneById(Long id) {
        Optional<Covid19Cases> optionalCovid19Cases = Optional.empty();
        try {
            Covid19Cases existingCovid19Cases = _entityManager.find(Covid19Cases.class, id);
            if (existingCovid19Cases != null) {
                optionalCovid19Cases = Optional.of(existingCovid19Cases);
            }
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
        return optionalCovid19Cases;
    }

    public List<Covid19Cases> findAll() {
        String jqpl = "SELECT d FROM Covid19Cases d";
        TypedQuery<Covid19Cases> query = _entityManager.createQuery(jqpl, Covid19Cases.class);
        return query.getResultList();
    }

    public void update(Covid19Cases updatedCovid19Cases) {
        Optional<Covid19Cases> optionalCovid19Cases = findOneById(updatedCovid19Cases.getId());
        if (optionalCovid19Cases.isPresent()) {
            Covid19Cases existingCovid19Cases = optionalCovid19Cases.get();
            existingCovid19Cases.setActiveCases(updatedCovid19Cases.getActiveCases());
            existingCovid19Cases.setReportDate(updatedCovid19Cases.getReportDate());
            _entityManager.merge(existingCovid19Cases);
            _entityManager.flush();
        }
    }

    public void delete(Long id) {
        Optional<Covid19Cases> optionalCovid19Cases = findOneById(id);
        if (optionalCovid19Cases.isPresent()) {
            Covid19Cases existingCovid19Cases = optionalCovid19Cases.get();
            _entityManager.remove(existingCovid19Cases);
            _entityManager.flush();
        }
    }
}
