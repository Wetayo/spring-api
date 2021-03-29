package wetayo.wetayoapi.exceptions.errors;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForbiddenError implements GraphQLError {
    private final String message;

    public ForbiddenError() {
        super();
        this.message = "Forbidden Error : 접근이 거부되었습니다.";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String,Object> map = new HashMap<>();
        map.put("classification","Forbidden Error");
        map.put("errorCode","403");
        return map;
    }
}
