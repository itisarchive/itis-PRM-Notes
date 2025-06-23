package pl.edu.pja.kdudek.notes

import android.app.Application
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import pl.edu.pja.kdudek.notes.data.NotesRepository
import pl.edu.pja.kdudek.notes.data.NotesService
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class App : Application() {
    private val json = Json {
        prettyPrint = true
    }
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val service: NotesService = retrofit.create(NotesService::class.java)

    val notesRepository = NotesRepository(service)
}
