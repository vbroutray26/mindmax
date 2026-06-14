package com.bernardvb.ui.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bernardvb.ui.analysis.AnalysisViewModel
import com.bernardvb.ui.components.MoodCheckRow
import com.bernardvb.ui.theme.BernardColors
import com.bernardvb.ui.theme.BernardType

private const val MIN_WORDS = 10
private const val MAX_CHARS = 2000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SituationInputScreen(
    onBack: () -> Unit,
    onAnalysisReady: (String) -> Unit,
    viewModel: AnalysisViewModel = hiltViewModel()
) {
    val analysisState by viewModel.analysisState.collectAsState()
    var textValue by remember { mutableStateOf(TextFieldValue("")) }
    var isRecording by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    val wordCount = textValue.text.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }.size
    val isAnalyseEnabled = wordCount >= MIN_WORDS && textValue.text.length <= MAX_CHARS

    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    DisposableEffect(Unit) {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) { }
            override fun onBeginningOfSpeech() { }
            override fun onRmsChanged(rmsdB: Float) { }
            override fun onBufferReceived(buffer: ByteArray?) { }
            override fun onEndOfSpeech() { isRecording = false }
            override fun onError(error: Int) { isRecording = false }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val spoken = matches?.firstOrNull() ?: return
                val appended = if (textValue.text.isNotEmpty()) "${textValue.text} $spoken" else spoken
                textValue = TextFieldValue(appended.take(MAX_CHARS))
                isRecording = false
            }
            override fun onPartialResults(partialResults: Bundle?) { }
            override fun onEvent(eventType: Int, params: Bundle?) { }
        })
        onDispose { speechRecognizer.destroy() }
    }

    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
        }
        speechRecognizer.startListening(intent)
        isRecording = true
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startListening()
    }

    LaunchedEffect(analysisState) {
        if (analysisState is AnalysisViewModel.AnalysisState.Success) {
            onAnalysisReady((analysisState as AnalysisViewModel.AnalysisState.Success).analysis.id)
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isRecording) {
                            speechRecognizer.stopListening()
                            isRecording = false
                        } else {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }) {
                        Icon(
                            if (isRecording) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = "Voice input",
                            tint = if (isRecording) BernardColors.AccentBlue else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "What's your situation?",
                style = BernardType.DisplaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            BasicTextField(
                value = textValue,
                onValueChange = { if (it.text.length <= MAX_CHARS) textValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .focusRequester(focusRequester),
                textStyle = BernardType.BodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                cursorBrush = SolidColor(BernardColors.AccentBlue),
                decorationBox = { inner ->
                    Box {
                        if (textValue.text.isEmpty()) {
                            Text(
                                "Describe your situation in plain language. No jargon needed — just what's on your mind.",
                                style = BernardType.BodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        inner()
                    }
                }
            )

            MoodCheckRow(
                selectedMood = viewModel.selectedMood.collectAsState().value,
                onMoodSelected = viewModel::setMood
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${textValue.text.length} / $MAX_CHARS",
                    style = BernardType.LabelSmall,
                    color = if (textValue.text.length > MAX_CHARS) BernardColors.BlindSpot
                            else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$wordCount words",
                    style = BernardType.LabelSmall,
                    color = if (wordCount < MIN_WORDS) BernardColors.Mid
                            else BernardColors.StrategyGreen
                )
            }

            Button(
                onClick = {
                    viewModel.analyse(textValue.text)
                },
                enabled = isAnalyseEnabled && analysisState !is AnalysisViewModel.AnalysisState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                if (analysisState is AnalysisViewModel.AnalysisState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Analyse", style = BernardType.BodyMedium)
                }
            }

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}
