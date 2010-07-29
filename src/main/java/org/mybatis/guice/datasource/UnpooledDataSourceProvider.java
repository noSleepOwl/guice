/*
 *    Copyright 2010 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice.datasource;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Provides the iBatis built-in UnpooledDataSource.
 *
 * @version $Id$
 */
@Singleton
public final class UnpooledDataSourceProvider implements Provider<DataSource> {

    /**
     * The UnpooledDataSource reference.
     */
    private final UnpooledDataSource unpooledDataSource;

    /**
     * Creates a new UnpooledDataSource using the needed parameter.
     *
     * @param driver The JDBC driver class.
     * @param url the database URL of the form <code>jdbc:subprotocol:subname</code>.
     * @param username the database user.
     * @param password the user's password.
     */
    @Inject
    public UnpooledDataSourceProvider(@Named("JDBC.driver") final String driver,
            @Named("JDBC.url") final String url,
            @Named("JDBC.username") final String username,
            @Named("JDBC.password") final String password) {
        this.unpooledDataSource = new UnpooledDataSource(this.getClass().getClassLoader(), driver, url, username, password);
    }

    /**
     * 
     *
     * @param autoCommit
     */
    @Inject(optional = true)
    public void setAutoCommit(@Named("JDBC.autoCommit") final boolean autoCommit) {
        this.unpooledDataSource.setAutoCommit(autoCommit);
    }

    /**
     * 
     *
     * @param loginTimeout
     */
    @Inject(optional = true)
    public void setLoginTimeout(@Named("JDBC.loginTimeout") final int loginTimeout) {
        try {
            this.unpooledDataSource.setLoginTimeout(loginTimeout);
        } catch (SQLException e) {
            throw new RuntimeException("Impossible to set login timeout '"
                    + loginTimeout
                    + "' to Unpooled Data Source", e);
        }
    }

    @Inject(optional = true)
    public void setDriverProperties(@Named("JDBC.driverProperties") final Properties driverProperties) {
        this.unpooledDataSource.setDriverProperties(driverProperties);
    }

    /**
     * {@inheritDoc}
     */
    public DataSource get() {
        return this.unpooledDataSource;
    }

}