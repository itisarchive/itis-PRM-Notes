package pl.edu.pja.kdudek.notes.data

import pl.edu.pja.kdudek.notes.domain.model.ExposedNote
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NotesService {

    @GET("notes")
    suspend fun getAll(): List<ExposedNote>

    @GET("notes/{id}")
    suspend fun getNote(@Path("id") id: Int): ExposedNote

    @POST("notes")
    suspend fun createNote(@Body note: ExposedNote)

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: Int)
}