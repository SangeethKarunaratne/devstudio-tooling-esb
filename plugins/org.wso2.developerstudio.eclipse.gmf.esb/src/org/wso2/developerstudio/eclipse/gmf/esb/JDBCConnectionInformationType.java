/**
 * Copyright 2009-2012 WSO2, Inc. (http://wso2.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.developerstudio.eclipse.gmf.esb;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>JDBC Connection Information Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.wso2.developerstudio.eclipse.gmf.esb.EsbPackage#getJDBCConnectionInformationType()
 * @model
 * @generated
 */
public enum JDBCConnectionInformationType implements Enumerator {
    /**
     * The '<em><b>JDBC POOL</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #JDBC_POOL_VALUE
     * @generated
     * @ordered
     */
    JDBC_POOL(0, "JDBC_POOL", "JDBC_POOL"),

    /**
     * The '<em><b>JDBC CARBON DATASOURCE</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #JDBC_CARBON_DATASOURCE_VALUE
     * @generated
     * @ordered
     */
    JDBC_CARBON_DATASOURCE(1, "JDBC_CARBON_DATASOURCE", "JDBC_CARBON_DATASOURCE");

    /**
     * The '<em><b>JDBC POOL</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>JDBC POOL</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #JDBC_POOL
     * @model
     * @generated
     * @ordered
     */
    public static final int JDBC_POOL_VALUE = 0;

    /**
     * The '<em><b>JDBC CARBON DATASOURCE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>JDBC CARBON DATASOURCE</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #JDBC_CARBON_DATASOURCE
     * @model
     * @generated
     * @ordered
     */
    public static final int JDBC_CARBON_DATASOURCE_VALUE = 1;

    /**
     * An array of all the '<em><b>JDBC Connection Information Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static final JDBCConnectionInformationType[] VALUES_ARRAY = new JDBCConnectionInformationType[] {
            JDBC_POOL,
            JDBC_CARBON_DATASOURCE,
        };

    /**
     * A public read-only list of all the '<em><b>JDBC Connection Information Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final List<JDBCConnectionInformationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>JDBC Connection Information Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static JDBCConnectionInformationType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            JDBCConnectionInformationType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>JDBC Connection Information Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static JDBCConnectionInformationType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            JDBCConnectionInformationType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>JDBC Connection Information Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static JDBCConnectionInformationType get(int value) {
        switch (value) {
            case JDBC_POOL_VALUE: return JDBC_POOL;
            case JDBC_CARBON_DATASOURCE_VALUE: return JDBC_CARBON_DATASOURCE;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private JDBCConnectionInformationType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
      return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
      return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLiteral() {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} // JDBCConnectionInformationType
