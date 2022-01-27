package com.kuka.app.tmm.ui.login.loginWithWeb

import com.kuka.app.tmm.core.BaseViewModel
import com.kuka.app.tmm.utils.helper.SharedHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginWithWebViewModel @Inject constructor(
     val sharedHelper: SharedHelper
) : BaseViewModel() {


}