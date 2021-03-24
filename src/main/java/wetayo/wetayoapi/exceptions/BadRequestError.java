package wetayo.wetayoapi.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.validation.ValidationErrorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadRequestError implements GraphQLError {
    private final String message;
    private final List<SourceLocation> locations = new ArrayList<>();
    private final String description;
    private final ValidationErrorType validationErrorType;
    private final List<String> queryPath;
    private final Map<String, Object> extensions;

    public BadRequestError() {
        this.message = "Validation Error : Bad Request";
        description = null;
        validationErrorType = null;
        queryPath = null;
        extensions = null;
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
