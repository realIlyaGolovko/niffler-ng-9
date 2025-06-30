package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.UserModel;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.UUID;

public class CategoryExtension implements BeforeEachCallback, AfterTestExecutionCallback,
        ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(
            CategoryExtension.class);
    public final SpendApiClient spendApiClient = new SpendApiClient();
    private final Faker faker = new Faker();

    @Override
    public void afterTestExecution(ExtensionContext context){
        CategoryJson createdCategory = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (!createdCategory.archived()) {
            spendApiClient.updateCategory(toArchiveCategory(createdCategory));
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .flatMap(userAnno ->
                        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)).ifPresent(
                        categoryAnno -> {
                            CategoryJson createdCategory = new CategoryJson(
                                    UUID.randomUUID(),
                                    getCategoryName(categoryAnno),
                                    getUserName(context),
                                    false
                            );
                            createdCategory = spendApiClient.addCategory(createdCategory);
                            if (categoryAnno.isArchived()) {
                                createdCategory = spendApiClient.updateCategory(toArchiveCategory(createdCategory));
                            }
                            context.getStore(NAMESPACE).put(
                                    context.getUniqueId(), createdCategory);
                        });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws
            ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public CategoryJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws
            ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }

    private String getCategoryName(Category category) {
        return category.name().isBlank() ? faker.name().name() : category.name();
    }

    private String getUserName(ExtensionContext context) {
        return context.getStore(UserExtension.NAMESPACE).get(context.getUniqueId(),
                UserModel.class).username();
    }

    private CategoryJson toArchiveCategory(CategoryJson categoryJson) {
        return new CategoryJson(categoryJson.id(), categoryJson.name(), categoryJson.username(), true);
    }
}
