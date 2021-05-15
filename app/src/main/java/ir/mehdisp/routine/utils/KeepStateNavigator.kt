package ir.mehdisp.routine.utils

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("keep_state_fragment")
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {

        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        if (navOptions != null)
            transaction.setCustomAnimations(navOptions.enterAnim, navOptions.exitAnim)

        var initialNavigate = false
        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null)
            transaction.detach(currentFragment)
        else
            initialNavigate = true

        var fragment = manager.findFragmentByTag(tag)
        if (fragment == null) {
            fragment = manager.fragmentFactory.instantiate(context.classLoader, destination.className)
            transaction.add(containerId, fragment, tag)
        } else
            transaction.attach(fragment)

//        transaction.setPrimaryNavigationFragment(fragment)
//        transaction.setReorderingAllowed(true)

//        if (!initialNavigate)
//            transaction.addToBackStack(tag)

        transaction.commitNow()

        return if (initialNavigate) destination else null
    }

}