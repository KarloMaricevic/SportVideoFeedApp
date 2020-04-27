package my.app.sportvideofeedapp.adapters

import androidx.collection.arrayMapOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.app.sportvideofeedapp.ui.containerFragment.feedFragment.FeedFragment
import my.app.sportvideofeedapp.ui.containerFragment.settingsFragment.SettingsFragment

const val FEED_PAGE_INDEX = 0
const val SETTINGS_PAGE_INDEX = 1

class ContainerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentCreator: Map<Int, () -> Fragment> = arrayMapOf(
        FEED_PAGE_INDEX to { FeedFragment() },
        SETTINGS_PAGE_INDEX to { SettingsFragment() }
    )

    override fun getItemCount(): Int = tabFragmentCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}
