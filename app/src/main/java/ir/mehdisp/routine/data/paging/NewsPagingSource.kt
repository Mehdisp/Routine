package ir.mehdisp.routine.data.paging

import ir.mehdisp.routine.data.models.News
import ir.mehdisp.routine.data.remote.ApiRemoteDataSource

class NewsPagingSource(
    private val latest: Boolean = true,
    private val remote: ApiRemoteDataSource
): BasePagingSource<News>() {

    override suspend fun getRepos(position: Int, count: Int): List<News> {
        val repos = if (latest)
            remote.latestNews(position, count)
        else
            remote.topNews(position, count)

        return repos.data?.result?.items ?: emptyList()
    }

}
