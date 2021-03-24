package wetayo.wetayoapi.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotFoundException extends RuntimeException implements GraphQLError {
    private static String errorCode = "404";

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
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
        map.put("classification","DataFetching Error");
        map.put("errorCode",errorCode);
        //return Collections.singletonMap("errorCode", errorCode);
        return map;
    }
}
