package com.example.roomyweather.data

/**
 * This is the Repository class that manages data operations associated with the bookmarked repos
 * database.
 *
 * @param dao A DAO instance for making queries over the database.
 */
class FiveDayForecastReposRepository(
    private val dao: ForecastDao
) {
    /**
     * This method inserts a new repo into the database.
     */
    suspend fun insertBookmarkedRepo(repo: FiveDayForecastEntity) = dao.insert(repo)

    /**
     * This method removed the specified repo from the database.
     */
    suspend fun deleteBookmarkedRepo(repo: FiveDayForecastEntity) = dao.delete(repo)

    /**
     * This method returns all the bookmarked repos from the database.
     *
     * @return Returns a list of all bookmarked repos wrapped in a Kotlin Flow.
     */
    fun getAllBookmarkedRepos() = dao.getAllRepos()

    /**
     * This method returns a single bookmarked repo from the database, based on its name.
     *
     * @return Returns the repo matching the specified `name`, wrapped in a Kotlin Flow.
     */
    fun getBookmarkedRepoByName(name: String) = dao.getRepoByName(name)
}