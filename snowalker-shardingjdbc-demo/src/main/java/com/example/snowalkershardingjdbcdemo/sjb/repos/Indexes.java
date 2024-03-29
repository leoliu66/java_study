/*
 * This file is generated by jOOQ.
 */
package com.example.snowalkershardingjdbcdemo.sjb.repos;


import com.example.snowalkershardingjdbcdemo.sjb.repos.tables.TOrder;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;

import javax.annotation.Generated;


/**
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index PRIMARY_KEY_A = Indexes0.PRIMARY_KEY_A;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index PRIMARY_KEY_A = Internal.createIndex("PRIMARY_KEY_A", TOrder.T_ORDER, new OrderField[] { TOrder.T_ORDER.ID }, true);
    }
}
