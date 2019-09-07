package com.anggit97.academy.academy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anggit97.academy.data.source.AcademyRepository

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class AcademyViewModel(private val mAcademyRepository: AcademyRepository) : ViewModel() {

    private var mLogin = MutableLiveData<String>()

    val courses = Transformations.switchMap(
        mLogin
    ) {
        mAcademyRepository.getAllCourses()
    }

    fun setUsername(username: String){
        mLogin.value = username
    }
}