package unicom.hiber;
// default package

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for FeePolicyObj entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .FeePolicyObj
  * @author MyEclipse Persistence Tools 
 */

public class FeePolicyObjDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(FeePolicyObjDAO.class);
	

    
    public void save(FeePolicyObj transientInstance) {
        log.debug("saving FeePolicyObj instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(FeePolicyObj persistentInstance) {
        log.debug("deleting FeePolicyObj instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public FeePolicyObj findById( java.lang.Long id) {
        log.debug("getting FeePolicyObj instance with id: " + id);
        try {
            FeePolicyObj instance = (FeePolicyObj) getSession()
                    .get("FeePolicyObj", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(FeePolicyObj instance) {
        log.debug("finding FeePolicyObj instance by example");
        try {
            List results = getSession()
                    .createCriteria("FeePolicyObj")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding FeePolicyObj instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from FeePolicyObj as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}


	public List findAll() {
		log.debug("finding all FeePolicyObj instances");
		try {
			String queryString = "from FeePolicyObj";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public FeePolicyObj merge(FeePolicyObj detachedInstance) {
        log.debug("merging FeePolicyObj instance");
        try {
            FeePolicyObj result = (FeePolicyObj) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FeePolicyObj instance) {
        log.debug("attaching dirty FeePolicyObj instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(FeePolicyObj instance) {
        log.debug("attaching clean FeePolicyObj instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}