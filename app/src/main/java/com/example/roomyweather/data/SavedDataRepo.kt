package com.example.roomyweather.data

class SavedDataRepo(
    private val dao: ForecastDao
) {
    suspend fun insertBookmarkedRepo(repo: FiveDayForecastEntity) = dao.insert(repo)
    suspend fun deleteBookmarkedRepo(repo: FiveDayForecastEntity) = dao.delete(repo)

    fun getAllBookmarkedRepos() = dao.getAllRepos()
    fun getBookmarkedRepoByName(city: String) = dao.getRepoByName(city)
}