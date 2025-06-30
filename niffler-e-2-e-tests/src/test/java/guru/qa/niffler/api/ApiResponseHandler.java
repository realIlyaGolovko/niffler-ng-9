package guru.qa.niffler.api;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ApiResponseHandler {
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_OK = 200;

    public static <T> T executeRequest(Call<T> call) {
        return executeRequest(call, STATUS_OK);
    }

    public static <T> T executeRequest(Call<T> call, int expectedHttpStatus) {
        try {
            Response<T> response = call.execute();
            validateResponse(response, expectedHttpStatus);
            return response.body();
        } catch (IOException e) {
            throw new AssertionError("Ошибка сети при выполнении запроса.", e);
        }
    }

    private static void validateResponse(Response<?> response, int expectedHttpStatus) {
        if (!response.isSuccessful()) {
            throw new AssertionError("Запрос завершился неудачей, статус: " + response.code());
        }
        if (response.code() != expectedHttpStatus) {
            throw new AssertionError("Ожидался статус: " + expectedHttpStatus +
                    ", фактический: " + response.code());
        }
    }
}