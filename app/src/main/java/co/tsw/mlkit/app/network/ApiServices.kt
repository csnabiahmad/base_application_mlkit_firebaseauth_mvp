package co.tsw.checklist.network

 import co.tsw.mlkit.app.common.Constants
 import com.google.gson.JsonObject
 import io.reactivex.Observable
 import retrofit2.Response
 import retrofit2.http.*

interface ApiServices {


    @GET(Constants.GET_WAREHOUSES)
    fun _GET_REQUEST(): Observable<Response<JsonObject>>

    @Headers("Content-Type:application/json")
    @POST(Constants.GET_DRIVERS)
    fun _POST_REQUEST(@Body jsonObject: JsonObject): Observable<Response<JsonObject>>

}