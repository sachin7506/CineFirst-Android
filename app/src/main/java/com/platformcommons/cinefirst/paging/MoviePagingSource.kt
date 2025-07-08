package com.platformcommons.cinefirst.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.platformcommons.cinefirst.model.Movie
import com.platformcommons.cinefirst.remote.MovieApiService
import retrofit2.HttpException
import java.io.IOException
import com.platformcommons.cinefirst.BuildConfig

class MoviePagingSource(
    private val apiService: MovieApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val response = apiService.getTrendingMovies(
                page = page,
                apiKey = BuildConfig.TMDB_API_KEY
            )
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
