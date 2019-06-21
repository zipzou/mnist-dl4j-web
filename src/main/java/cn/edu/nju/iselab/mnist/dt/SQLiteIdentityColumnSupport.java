package cn.edu.nju.iselab.mnist.dt;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;


public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {
    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentityColumnString(int i) throws MappingException {
        return "integer";
    }

    @Override
    public String getIdentitySelectString(String s, String s1, int i) throws MappingException {
        return "select last_insert_rowid()";
    }
}
