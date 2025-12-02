package com.example.assignmenttrack.viewModel

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.UserRepository
import com.example.assignmenttrack.model.Stat
import com.example.assignmenttrack.model.User
import com.example.assignmenttrack.uiStateData.defaulStat
import com.example.assignmenttrack.uiStateData.defaultUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(defaultUser)
    val user: StateFlow<User> = _user

    private val _stat = MutableStateFlow(defaulStat)
    val stat: StateFlow<Stat> = _stat

    init {
        viewModelScope.launch {
            userRepository.initUser()
            refreshUser()
            refreshStat()
        }
    }

    fun refreshUser() {
        viewModelScope.launch {
            userRepository.getUser().collect { userData ->
                userData?.let { _user.value = it }
            }
        }
    }

    fun refreshStat() {
        viewModelScope.launch {
            userRepository.getStat(System.currentTimeMillis()).collect { statData ->
                _stat.value = statData
            }
        }
    }

    fun updatePhotoProfile(context: Context, uri: Uri) {
        _user.value = _user.value.copy(profilePicturePath = uri.toString())

        viewModelScope.launch {
            val path = saveProfilePhoto(context, uri)
            path?.let {
                userRepository.updatePhotoProfile(it)
            }
        }
    }


    fun updateName(name: String) {
        viewModelScope.launch {
            userRepository.updateName(name)
            _user.value = _user.value.copy(name = name)
        }
    }

    private fun saveProfilePhoto(context: Context, uri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null

        val mimeType = context.contentResolver.getType(uri) ?: return null
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"

        if (extension.lowercase() !in listOf("png", "jpg", "jpeg")) return null

        val file = File(context.filesDir, "profile_photo.$extension")
        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }
}