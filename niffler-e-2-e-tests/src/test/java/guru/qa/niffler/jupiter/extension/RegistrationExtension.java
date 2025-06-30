package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.niffler.jupiter.annotation.Registration;
import guru.qa.niffler.model.RegistrationModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

public class RegistrationExtension implements BeforeEachCallback, ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(
            RegistrationExtension.class);
    private final Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context){
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(),
                Registration.class).ifPresent(
                anno -> {
                    RegistrationModel registrationModel = new RegistrationModel(
                            faker.name().username(), faker.name().username());
                    context.getStore(NAMESPACE).put(context.getUniqueId(),
                            registrationModel);
                }
        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(RegistrationModel.class);
    }

    @Override
    public RegistrationModel resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(),
                RegistrationModel.class);
    }
}
