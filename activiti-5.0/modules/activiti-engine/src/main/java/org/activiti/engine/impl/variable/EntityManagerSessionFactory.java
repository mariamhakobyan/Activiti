/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.impl.variable;

import javax.persistence.EntityManagerFactory;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

/**
 * @author Frederik Heremans
 */
public class EntityManagerSessionFactory implements SessionFactory {

  private EntityManagerFactory entityManagerFactory;
  private boolean handleTransactions;
  private boolean closeEntityManager;

  public EntityManagerSessionFactory(Object entityManagerFactory, boolean handleTransactions, boolean closeEntityManager) {
    if(entityManagerFactory == null) {
      throw new ActivitiException("entityManagerFactory is null");
    }
    if(!(entityManagerFactory instanceof EntityManagerFactory)) {
      throw new ActivitiException("EntityManagerFactory must implement 'javax.persistence.EntityManagerFactory'");
    }
    
    this.entityManagerFactory = (EntityManagerFactory) entityManagerFactory;
    this.handleTransactions = handleTransactions;
    this.closeEntityManager = closeEntityManager;
  }

  public Class< ? > getSessionType() {
    return EntityManagerSession.class;
  }

  public Session openSession() {
    return new EntityManagerSessionImpl(entityManagerFactory, handleTransactions, closeEntityManager);
  }

  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }
}