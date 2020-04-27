package my.app.sportvideofeedapp.ui.containerFragment.settingsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import my.app.sportvideofeedapp.BaseApplication
import my.app.sportvideofeedapp.core.router.DefaultRouter
import my.app.sportvideofeedapp.databinding.FragmentSettingsBinding
import my.app.sportvideofeedapp.ui.BaseFragment
import my.app.sportvideofeedapp.viewmodels.SettingsViewModel

class SettingsFragment : BaseFragment<SettingsViewModel, DefaultRouter>() {

    private lateinit var mBinding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
        .getAppComponent()
            .getSettingsSubcomponentFactory()
            .create(this)
            .inject(this)
        mViewModel = ViewModelProvider(this,mViewModelFactory).get(SettingsViewModel::class.java)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun connectViewModel() = Unit

    override fun isContainedInsedeOtherFragment(): Boolean = true
}
