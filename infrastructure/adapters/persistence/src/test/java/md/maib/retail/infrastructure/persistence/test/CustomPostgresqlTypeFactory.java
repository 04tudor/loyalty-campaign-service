package md.maib.retail.infrastructure.persistence.test;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

import java.sql.Types;

public class CustomPostgresqlTypeFactory extends PostgresqlDataTypeFactory {
    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        if (sqlType == Types.OTHER && "jsonb".equals(sqlTypeName)) {
            return new JsonbDataType();
        }
        return super.createDataType(sqlType, sqlTypeName);
    }
}
