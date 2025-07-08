package com.platformcommons.cinefirst.remote

import com.platformcommons.cinefirst.model.AddUserRequest
import com.platformcommons.cinefirst.model.AddUserResponse
import com.platformcommons.cinefirst.model.MovieDetail
import com.platformcommons.cinefirst.model.UserResponse
import com.platformcommons.cinefirst.model.MovieResponse
import com.platformcommons.cinefirst.model.PendingUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int = 1): UserResponse
    @POST("users")
    suspend fun addUser(@Body user: AddUserRequest): Response<AddUserResponse>

}

interface MovieApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetail

}



