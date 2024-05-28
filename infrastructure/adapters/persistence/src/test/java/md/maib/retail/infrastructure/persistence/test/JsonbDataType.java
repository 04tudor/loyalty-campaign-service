package md.maib.retail.infrastructure.persistence.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dbunit.dataset.datatype.AbstractDataType;
import org.dbunit.dataset.datatype.TypeCastException;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class JsonbDataType extends AbstractDataType {

    public static final String TYPE = "jsonb";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public JsonbDataType() {
        super(TYPE, Types.OTHER, String.class, false);
    }

    @Override
    public Object typeCast(Object obj) {
        return obj.toString();
    }

    @Override
    public Object getSqlValue(int column, ResultSet resultSet) throws SQLException {
        return resultSet.getString(column);
    }

    @Override
    public void setSqlValue(Object value, int column, PreparedStatement statement) throws SQLException {
        final PGobject jsonObj = new PGobject();
        jsonObj.setType(TYPE);
        jsonObj.setValue(value == null ? null : value.toString());

        statement.setObject(column, jsonObj);
    }

    @Override
    public int compare(Object o1, Object o2) throws TypeCastException {
        try {
            JsonNode tree1 = objectMapper.readTree((String) o1);
            JsonNode tree2 = objectMapper.readTree((String) o2);
            return tree1.equals(tree2) ? 0 : 1;
        } catch (Exception e) {
            throw new TypeCastException(e);
        }
    }
}