/*
 * eXist-db Open Source Native XML Database
 * Copyright (C) 2022 The eXist-db Authors
 *
 * info@exist-db.org
 * http://www.exist-db.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.exist.security.realm.jwt;

import org.exist.config.Configuration;
import org.exist.config.ConfigurationException;
import org.exist.security.AbstractRealm;
import org.exist.security.Account;
import org.exist.security.Group;
import org.exist.security.PermissionDeniedException;
import org.exist.security.internal.AccountImpl;
import org.exist.storage.DBBroker;

/**
 * @author <a href="mailto:loren.cahlander@gmail.com">Loren Cahlander</a>
 *
 */
public class JWTAccountImpl extends AccountImpl {
    /**
     *
     * @param realm
     * @param configuration
     * @throws ConfigurationException
     */
    public JWTAccountImpl(final AbstractRealm realm, final Configuration configuration) throws ConfigurationException {
        super(realm, configuration);
    }

    /**
     *
     * @param broker
     * @param realm
     * @param from_user
     * @throws ConfigurationException
     */
    public JWTAccountImpl(final DBBroker broker, final AbstractRealm realm, final AccountImpl from_user) throws ConfigurationException {
        super(broker, realm, from_user);
    }

    /**
     *
     * @param broker
     * @param realm
     * @param id
     * @param from_user
     * @throws ConfigurationException
     * @throws PermissionDeniedException
     */
    public JWTAccountImpl(final DBBroker broker, final AbstractRealm realm, final int id, final Account from_user) throws ConfigurationException, PermissionDeniedException {
        super(broker, realm, id, from_user);
    }

    /**
     *
     * @param broker
     * @param realm
     * @param name
     * @throws ConfigurationException
     */
    public JWTAccountImpl(final DBBroker broker, final AbstractRealm realm, final String name) throws ConfigurationException {
        super(broker, realm, name);
    }

    /**
     *
     * @param broker
     * @param realm
     * @param id
     * @param name
     * @param password
     * @throws ConfigurationException
     */
    public JWTAccountImpl(final DBBroker broker, final AbstractRealm realm, final int id, final String name, final String password) throws ConfigurationException {
        super(broker, realm, id, name, password);
    }

    /**
     *
     * @param realm
     * @param config
     * @param removed
     * @throws ConfigurationException
     */
    JWTAccountImpl(final AbstractRealm realm, final Configuration config, final boolean removed) throws ConfigurationException {
        super(realm, config, removed);
    }

    /**
     *
     * @param group The group to add the user to
     * @return
     * @throws PermissionDeniedException
     */
    @Override
    public Group addGroup(final Group group) throws PermissionDeniedException {
        if (group instanceof JWTGroupImpl) {
            //TODO
            //we dont support writes to JWT yet!
            return null;
        } else {
            //adds an JWT User to a group from a different Realm
            return super.addGroup(group);
        }
    }

    /**
     *
     * @param name The group to add the user to
     * @return
     * @throws PermissionDeniedException
     */
    @Override
    public Group addGroup(final String name) throws PermissionDeniedException {
        Group group = getRealm().getGroup(name);

        //allow JWT users to have groups from other realms
        if (group == null) {
            //if the group is not present in this realm, look externally
            group = getRealm().getExternalGroup(name);
        }
        return addGroup(group);
    }
}
