package com.example.searchbook.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchbook.data.model.Book
import com.example.searchbook.network.RetrofitInstance.api
import com.example.searchbook.util.Constants.PAGING_SIZE
import retrofit2.HttpException
import java.io.IOException

class BookSearchPagingSource(
    private val query: String,
    private val sort: String
) : PagingSource<Int, Book>() {

    // 여러가지 이유로 페이지를 갱신해야할 때 수행되는 함수로 가장 최근에 접근한 페이지를 anchorPosition로 받고 그 주위의 페이지를 읽어오도록 키를 반환해주는 역활을
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // 페이저가 데이터를 호출할 때마다 불리는 함수
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = api.searchBook(query, sort, pageNumber, params.loadSize)
            val endOfPaginationReached = response.body()?.meta?.isEnd!!

            val data = response.body()?.documents!!

            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) {
                null
            } else {
                pageNumber - 1
            }

            val nextKey = if (endOfPaginationReached) {
                null
            } else {
                pageNumber + (params.loadSize / PAGING_SIZE)
            }

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception : IOException){
            LoadResult.Error(exception)
        } catch (exception : HttpException){
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}