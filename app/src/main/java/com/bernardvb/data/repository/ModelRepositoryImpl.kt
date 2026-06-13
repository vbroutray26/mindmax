package com.bernardvb.data.repository

import android.content.Context
import com.bernardvb.data.local.dao.MentalModelDao
import com.bernardvb.data.local.entity.MentalModelEntity
import com.bernardvb.data.remote.BernardVBApiService
import com.bernardvb.domain.model.MentalModel
import com.bernardvb.domain.model.enums.Domain
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepositoryImpl @Inject constructor(
    private val dao: MentalModelDao,
    private val api: BernardVBApiService,
    private val moshi: Moshi,
    @ApplicationContext private val context: Context
) : ModelRepository {

    private val stringListAdapter = moshi.adapter<List<String>>(
        Types.newParameterizedType(List::class.java, String::class.java)
    )

    private fun parseJsonArray(json: String): List<String> =
        runCatching { stringListAdapter.fromJson(json) ?: emptyList() }.getOrDefault(emptyList())

    private fun MentalModelEntity.toDomain() = toDomain(::parseJsonArray)

    override fun getAllModels(): Flow<List<MentalModel>> =
        dao.getAllModels().map { it.map { e -> e.toDomain() } }

    override fun getModelsByDomain(domain: Domain): Flow<List<MentalModel>> =
        dao.getModelsByDomain(domain.name.lowercase().replace("_", "-"))
            .map { it.map { e -> e.toDomain() } }

    override fun getFreeModels(): Flow<List<MentalModel>> =
        dao.getFreeModels().map { it.map { e -> e.toDomain() } }

    override fun searchModels(query: String): Flow<List<MentalModel>> =
        dao.searchModels(query).map { it.map { e -> e.toDomain() } }

    override suspend fun getModelById(id: String): MentalModel? =
        dao.getModelById(id)?.toDomain()

    override suspend fun getModelsByIds(ids: List<String>): List<MentalModel> =
        dao.getModelsByIds(ids).map { it.toDomain() }

    override suspend fun syncModels(isPro: Boolean): Result<Int> = runCatching {
        val lastSync = 0L  // TODO: persist last sync timestamp in DataStore
        val response = api.syncModels(since = lastSync.takeIf { it > 0 }, tier = if (isPro) "pro" else "free")
        val entities = response.models.map { dto ->
            MentalModelEntity(
                id = dto.id,
                name = dto.name,
                domain = dto.domain,
                subdomain = dto.subdomain,
                origin = dto.origin,
                shortDesc = dto.shortDesc,
                fullDesc = dto.fullDesc,
                howToApply = dto.howToApply,
                whenToUse = stringListAdapter.toJson(dto.whenToUse),
                commonMistakes = stringListAdapter.toJson(dto.commonMistakes),
                pairingLogic = stringListAdapter.toJson(dto.pairingLogic),
                tags = stringListAdapter.toJson(dto.tags),
                thinkerProfiles = stringListAdapter.toJson(dto.thinkerProfiles),
                difficulty = dto.difficulty,
                isFree = dto.isFree,
                audioUrl = dto.audioUrl
            )
        }
        dao.upsertModels(entities)
        entities.size
    }

    override suspend fun seedInitialModels() {
        if (dao.getCount() > 0) return
        runCatching {
            val json = context.assets.open("models.json").bufferedReader().readText()
            val array = JSONArray(json)
            val entities = (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                fun jsonArrayToString(key: String): String {
                    val arr = obj.optJSONArray(key) ?: return "[]"
                    return arr.toString()
                }
                MentalModelEntity(
                    id = obj.getString("id"),
                    name = obj.getString("name"),
                    domain = obj.getString("domain"),
                    subdomain = obj.optString("subdomain", ""),
                    origin = obj.getString("origin"),
                    shortDesc = obj.getString("shortDesc"),
                    fullDesc = obj.getString("fullDesc"),
                    howToApply = obj.getString("howToApply"),
                    whenToUse = jsonArrayToString("whenToUse"),
                    commonMistakes = jsonArrayToString("commonMistakes"),
                    pairingLogic = jsonArrayToString("pairingLogic"),
                    tags = jsonArrayToString("tags"),
                    thinkerProfiles = jsonArrayToString("thinkerProfiles"),
                    difficulty = obj.optString("difficulty", "foundational"),
                    isFree = obj.optBoolean("isFree", false),
                    audioUrl = null
                )
            }
            dao.upsertModels(entities)
        }
    }
}
