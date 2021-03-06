/*
 * This file is generated by jOOQ.
 */
package lab.pguma.quoshelf.infrastructure.jooq.models.tables.records;


import lab.pguma.quoshelf.infrastructure.jooq.models.tables.Authors;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AuthorsRecord extends UpdatableRecordImpl<AuthorsRecord> implements Record2<Integer, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>AUTHORS.AUTHOR_ID</code>.
     */
    public void setAuthorId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>AUTHORS.AUTHOR_ID</code>.
     */
    public Integer getAuthorId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>AUTHORS.AUTHOR_NAME</code>.
     */
    public void setAuthorName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>AUTHORS.AUTHOR_NAME</code>.
     */
    public String getAuthorName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Authors.AUTHORS.AUTHOR_ID;
    }

    @Override
    public Field<String> field2() {
        return Authors.AUTHORS.AUTHOR_NAME;
    }

    @Override
    public Integer component1() {
        return getAuthorId();
    }

    @Override
    public String component2() {
        return getAuthorName();
    }

    @Override
    public Integer value1() {
        return getAuthorId();
    }

    @Override
    public String value2() {
        return getAuthorName();
    }

    @Override
    public AuthorsRecord value1(Integer value) {
        setAuthorId(value);
        return this;
    }

    @Override
    public AuthorsRecord value2(String value) {
        setAuthorName(value);
        return this;
    }

    @Override
    public AuthorsRecord values(Integer value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthorsRecord
     */
    public AuthorsRecord() {
        super(Authors.AUTHORS);
    }

    /**
     * Create a detached, initialised AuthorsRecord
     */
    public AuthorsRecord(Integer authorId, String authorName) {
        super(Authors.AUTHORS);

        setAuthorId(authorId);
        setAuthorName(authorName);
    }
}
