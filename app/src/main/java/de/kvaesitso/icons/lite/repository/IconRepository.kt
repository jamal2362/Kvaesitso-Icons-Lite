package de.kvaesitso.icons.lite.repository

import android.app.Application
import de.kvaesitso.icons.lite.model.IconInfo
import de.kvaesitso.icons.lite.model.IconInfoModel
import de.kvaesitso.icons.lite.model.SearchInfo
import de.kvaesitso.icons.lite.util.getIconInfo
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IconRepository @Inject constructor(application: Application) {

    private var _iconInfo: List<IconInfo>? = null
    val iconInfoModel = MutableStateFlow<IconInfoModel?>(value = null)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            _iconInfo = application.getIconInfo()
                .associateBy { it.name }.values
                .sortedBy { it.name.lowercase() }
                .also {
                    iconInfoModel.value = IconInfoModel(
                        iconInfo = it,
                        iconCount = it.size,
                    )
                }
        }
    }

    suspend fun search(query: String) = withContext(Dispatchers.Default) {
        iconInfoModel.value = _iconInfo?.let {
            val filtered = it.mapNotNull { candidate ->
                val indexOfMatch =
                    candidate.name.indexOf(string = query, ignoreCase = true).also { index ->
                        if (index == -1) return@mapNotNull null
                    }
                val matchAtWordStart = indexOfMatch == 0 || candidate.name[indexOfMatch - 1] == ' '
                SearchInfo(
                    iconInfo = candidate,
                    indexOfMatch = indexOfMatch,
                    matchAtWordStart = matchAtWordStart,
                )
            }.sortedWith(
                compareBy(
                    { searchInfo -> !searchInfo.matchAtWordStart },
                    { searchInfo -> searchInfo.indexOfMatch },
                ),
            ).map { searchInfo ->
                searchInfo.iconInfo
            }
            IconInfoModel(
                iconCount = it.size,
                iconInfo = filtered,
            )
        }
    }
}
