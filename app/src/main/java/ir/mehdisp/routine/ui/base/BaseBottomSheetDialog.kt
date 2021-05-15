package ir.mehdisp.routine.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.mehdisp.routine.R

abstract class BaseBottomSheetDialog: BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_BottomSheet)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    fun show(activity: FragmentActivity) {
        show(activity.supportFragmentManager, javaClass.simpleName)
    }

}
