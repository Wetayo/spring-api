package wetayo.wetayoapi.exceptions.errors;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadRequestError implements GraphQLError {
    private final String message;

    public BadRequestError() {
        super();
        this.message = "Validation Error : Bad Request";
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
        map.put("classification","ValidationError");
        map.put("errorCode","400");
        return map;
    }
}
