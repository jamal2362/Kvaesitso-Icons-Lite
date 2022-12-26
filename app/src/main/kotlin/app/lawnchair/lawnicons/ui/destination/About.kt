package app.lawnchair.lawnicons.ui.destination

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.lawnchair.lawnicons.BuildConfig
import app.lawnchair.lawnicons.R
import app.lawnchair.lawnicons.ui.component.*
import app.lawnchair.lawnicons.ui.util.Contributor
import app.lawnchair.lawnicons.ui.util.Destinations
import app.lawnchair.lawnicons.util.appIcon

private val coreContributors = listOf(


    Contributor(
        name = "Jamal",
        username = "jamal2362",
        photoUrl = "https://avatars.githubusercontent.com/u/15986930",
    ),
    Contributor(
        name = "daywalk3r666",
        username = "daywalk3r666",
        photoUrl = "https://avatars.githubusercontent.com/u/15938117",
    ),

)

private val specialThanks = listOf(
    Contributor(
        name = "U. N. Owen",
        username = "MM2-0",
        photoUrl = "https://avatars.githubusercontent.com/u/15646950?v=4",
        socialUrl = "https://kvaesitso.mm20.de/",
        descriptionRes = R.string.special_thanks_icon,
    ),
    Contributor(
        name = "MM2-0",
        photoUrl = "https://avatars.githubusercontent.com/u/15646950?v=4",
        socialUrl = "https://kvaesitso.mm20.de/",
        descriptionRes = R.string.special_thanks_name,
    ),
)

@Composable
@OptIn(ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)
fun About(navController: NavController) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarWithInsets(
                scrollBehavior = scrollBehavior,
                title = stringResource(id = R.string.about),
                navigationIcon = {
                    ClickableIcon(
                        onClick = { navController.popBackStack() },
                        imageVector = Icons.Rounded.ArrowBack,
                        size = 40.dp,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                },
            )
        },
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 8.dp,
                            bottom = 32.dp,
                        ),
                ) {
                    Image(
                        bitmap = context.appIcon().asImageBitmap(),
                        contentDescription = stringResource(id = R.string.app_name),
                        modifier = Modifier.size(72.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                    Text(
                        text = stringResource(id = R.string.version_x, BuildConfig.VERSION_NAME),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(
                            alpha = ContentAlpha.medium,
                        ),
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LinkButton(
                            text = stringResource(id = R.string.github_link),
                            url = "https://github.com/daywalk3r666/Kvaesitso-Icons",
                        )
                        LinkButton(
                            text = stringResource(id = R.string.request_icons_link),
                            url = "https://forms.gle/wKnY9HvGz9cfgqfL6",
                        )
                    }
                }
            }
            item {
                Card(label = stringResource(id = R.string.core_contributors)) {
                    coreContributors.mapIndexed { index, it ->
                        ContributorRow(
                            name = it.name,
                            photoUrl = it.photoUrl,
                            profileUrl = "https://github.com/${it.username}",
                            divider = index != coreContributors.lastIndex,
                        )
                    }
                }
            }
            item {
                Card(modifier = Modifier.padding(top = 16.dp)) {
                    SimpleListRow(
                        onClick = { navController.navigate(Destinations.CONTRIBUTORS) },
                        label = stringResource(id = R.string.see_all_contributors),
                        divider = false,
                    )
                }
            }
            item {
                Card(
                    label = stringResource(id = R.string.special_thanks),
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    specialThanks.mapIndexed { index, it ->
                        ContributorRow(
                            name = it.name,
                            photoUrl = it.photoUrl,
                            profileUrl = it.username?.let { "https://github.com/$it" },
                            description = it.descriptionRes?.let { stringResource(id = it) },
                            divider = index != specialThanks.lastIndex,
                            socialUrl = it.socialUrl,
                        )
                    }
                }
            }
            item {
                Card(modifier = Modifier.padding(top = 16.dp)) {
                    SimpleListRow(
                        onClick = { navController.navigate(Destinations.ACKNOWLEDGEMENTS) },
                        label = stringResource(id = R.string.acknowledgements),
                        divider = false,
                    )
                }
            }
        }
    }
}
