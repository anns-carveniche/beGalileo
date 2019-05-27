package com.carveniche.begalileo.api

import com.carveniche.begalileo.util.Constants
import com.carveniche.begalileo.models.*
import io.reactivex.Observable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiServiceInterface {

    @FormUrlEncoded
    @POST("app_parents/register")
    fun registerMobileNumber(@Field("mobile") mobile: String) : Observable<RegisterModel>

    @FormUrlEncoded
    @POST("app_parents/resend_otp")
    fun resendOTP(@Field("mobile") mobile: String) : Observable<RegisterModel>

    @FormUrlEncoded
    @POST("app_parents/add_child")
    fun addChild(   @Field("parent_id") parentId: Int,
                    @Field("first_name") firstName: String,
                    @Field("last_name") lastName: String,
                    @Field("gender") gender: String,
                    @Field("grade") grade: Int,
                    @Field("board") board: Int,
                    @Field("goal") goal: String,
                    @Field("school_name") schoolName: String,
                    @Field("school_city") schoolCity: String
                    ) : Observable<AddChildModel>

    @FormUrlEncoded
    @POST("app_parents/contact_details_with_nearby_centers")
    fun getNearbyLocation(   @Field("parent_id") parentId: Int,
                    @Field("address") address: String,
                    @Field("city") city: String,
                    @Field("state") state: String,
                    @Field("country") country: String,
                    @Field("latitude") latitude: Double,
                    @Field("longitude") longitude: Double
    ) : Observable<NearCenterModel>

    @FormUrlEncoded
    @POST("app_parents/update_email")
    fun updateEmail(@Field("mobile") mobile: String,
                    @Field("email") email: String) : Observable<RegisterModel>

    @FormUrlEncoded
    @POST("app_parents/concepts_list")
    fun getConceptList(@Field("grade_id") grade_id: Int,
                    @Field("board_id") board_id: Int) : Observable<ConceptListModel>

    @GET("app_parents/grade_boards")
    fun getGradeBoardDetails() : Observable<GradeBoardModel>

    @GET("app_parents/city_list")
    fun getCityName(name:String) : Observable<CitiesModel>

    @FormUrlEncoded
    @POST("app_students/game_levels")
    fun getGameLevels(@Field("student_id") student_id : Int) : Observable<GameLevelModel>

    @FormUrlEncoded
    @POST("app_students/start_game")
    fun getGameQuestions(@Field("level_id") level_id : Int,
                         @Field("play_with") play_with : String,
                         @Field("players[]") players : ArrayList<Int>
                         ) : Observable<GameRobotQuestionModel>


    companion object {
        fun create():ApiServiceInterface {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
            return retrofit.create(ApiServiceInterface::class.java)
        }
    }

}