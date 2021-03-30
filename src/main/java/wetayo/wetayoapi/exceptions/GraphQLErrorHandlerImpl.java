package wetayo.wetayoapi.exceptions;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.validation.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wetayo.wetayoapi.exceptions.errors.BadRequestError;
import wetayo.wetayoapi.exceptions.errors.ForbiddenError;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GraphQLErrorHandlerImpl implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> graphQLErrors) {
        return graphQLErrors.stream().map(this::handleGraphQLError).collect(Collectors.toList());
    }

    private GraphQLError handleGraphQLError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching  ) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            if (exceptionError.getException() instanceof GraphQLError) {
                return (GraphQLError) exceptionError.getException();
            }else if(exceptionError.getException() instanceof RuntimeException){
                return new ForbiddenError();
            }
        }else if(error instanceof ValidationError){
            return new BadRequestError();
        }
        return error;
    }
}
