package guru.qa.niffler.api;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Date;
import java.util.List;

public class SpendApiClient {
    private static final Config CFG = Config.getInstance();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CFG.spendUrl())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendApi spendApi = retrofit.create(SpendApi.class);

    public SpendJson addSpend(SpendJson spendJson) {
        return ApiResponseHandler.executeRequest(spendApi.addSpend(spendJson), ApiResponseHandler.STATUS_CREATED);
    }

    public SpendJson updateSpend(SpendJson spendJson) {
        return ApiResponseHandler.executeRequest(spendApi.updateSpend(spendJson), ApiResponseHandler.STATUS_CREATED);
    }

    public SpendJson getSpend(String id, String username) {
        return ApiResponseHandler.executeRequest(spendApi.getSpend(id, username));
    }

    public List<SpendJson> getAllSpends(String username, CurrencyValues currencyValues, Date from, Date to) {
        return ApiResponseHandler.executeRequest(spendApi.getAllSpends(username, currencyValues, from, to));
    }

    public void removeSpends(String username, List<String> ids) {
        ApiResponseHandler.executeRequest(spendApi.removeSpends(username, ids));
    }

    public CategoryJson addCategory(CategoryJson categoryJson) {
        return ApiResponseHandler.executeRequest(spendApi.addCategory(categoryJson));
    }

    public CategoryJson updateCategory(CategoryJson categoryJson) {
        return ApiResponseHandler.executeRequest(spendApi.updateCategory(categoryJson));
    }

    public List<CategoryJson> getAllCategories(String username, boolean excludeArchived) {
        return ApiResponseHandler.executeRequest(spendApi.getAllCategories(username, excludeArchived));
    }


}