package com.amare.notez.core.di


import android.app.Application
import android.content.Context
import com.amare.notez.R
import com.amare.notez.core.data.repository.AuthRepositoryImpl
import com.amare.notez.core.domain.usecase.*
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseDatabase() = Firebase.database


    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        db: FirebaseDatabase
    ): AuthRepositoryImpl = AuthRepositoryImpl(
        auth = auth,
        db = db
    )

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideIdentitySignInClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideNoteUseCases(
        repository: AuthRepositoryImpl
    ) = NoteUseCases(
        SignInWithGoogle(repository),
        SignInWithEmail(repository),
        SignUpWithEmail(repository),
        CreateNote(repository),
        UpdateNote(repository),
        DeleteNoteById(repository),
        SignOut(repository),
        RevokeAccess(repository),
        GetUser(repository),
    )


}