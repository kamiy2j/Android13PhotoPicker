package com.kamran.android13photopicker


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kamran.android13photopicker.ui.theme.Android13PhotoPickerTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android13PhotoPickerTheme {
                var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
                var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri ->
                        selectedImageUri = uri
                    }
                )

                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris ->
                        selectedImageUris = uris
                    }
                )

                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement
                    .spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    item {
                        Text(
                            text = stringResource(R.string.heading),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.fillMaxWidth().padding(16.dp)
                        )

                        Text(
                            text = stringResource(R.string.description),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        )



                        Divider(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                singlePhotoPickerLauncher.launch(
                                   PickVisualMediaRequest(
                                       ActivityResultContracts.PickVisualMedia.ImageOnly
                                   )
                                )
                            }) {
                                Text(text = stringResource(R.string.single_photo_button))
                            }
                            Button(onClick = {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }) {
                                Text(text = stringResource(R.string.multiple_photo_button))
                            }
                        }

                        Divider(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f)
                        )
                    }

                    item {
                        AsyncImage(model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    items(selectedImageUris) { uri ->
                        AsyncImage(model = uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
