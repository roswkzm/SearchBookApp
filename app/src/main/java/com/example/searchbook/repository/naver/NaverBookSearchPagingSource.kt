package com.example.searchbook.repository.naver

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.network.NaverBookSearchService
import com.example.searchbook.util.Constants.PAGING_SIZE
import java.lang.Exception

class NaverBookSearchPagingSource(
    private val api : NaverBookSearchService,
    private val query : String,
    private val sort : String
) : PagingSource<Int, NaverBook>() {

    override fun getRefreshKey(state: PagingState<Int, NaverBook>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NaverBook> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = api.searchBook(query, params.loadSize, pageNumber, sort)

            val data = response.body()?.items!!
            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
            val nextKey =
                pageNumber + (params.loadSize / PAGING_SIZE)

            LoadResult.Page(
                data,
                prevKey,
                nextKey
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}