package com.example.moviesapp.feature_movies.presentation.movie_details

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.moviesapp.feature_movies.presentation.movie_details.components.MovieDetailsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    navController: NavController, viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val movieState = viewModel.state.value

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val painter =
        rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/${movieState.movieDetails.poster_path}")
    val state = painter.state

    val transition by animateFloatAsState(
        targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f
    )

    Box {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radiusX = 10.dp, radiusY = 10.dp, edgeTreatment = BlurredEdgeTreatment.Rectangle
                ),
            painter = painter,
            contentDescription = "background_image",
            contentScale = ContentScale.FillBounds
        )
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = movieState.movieDetails.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent
        ) { paddingValues ->
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {

                ConstraintLayout {
                    val (poster, rate) = createRefs()

                    Card(
                        modifier = Modifier
                            .constrainAs(rate) {
                                top.linkTo(parent.top, margin = 8.dp)
                                absoluteLeft.linkTo(poster.absoluteRight, margin = 12.dp)
                                absoluteRight.linkTo(parent.absoluteRight)
                            }
                    ) {
                        Text(
                            text = "${movieState.movieDetails.vote_average}/10",
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }

                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(240.dp)
                            .width(screenWidth / 2)
                            .clip(RoundedCornerShape(12.dp))
                            .alpha(transition)
                            .constrainAs(poster) {
                                centerHorizontallyTo(parent)
                                top.linkTo(parent.top, margin = 8.dp)
                            }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                MovieDetailsBox(
                    release_date = movieState.movieDetails.release_date,
                    overview = movieState.movieDetails.overview
                )
            }
        }
    }
}