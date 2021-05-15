package ir.mehdisp.routine.ui.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import ir.mehdisp.routine.databinding.LayoutAppDialogBinding
import ir.mehdisp.routine.ui.base.BaseBottomSheetDialog

class AppAlertDialog private constructor(
    private val builder: Builder
) : BaseBottomSheetDialog() {

    private lateinit var binding: LayoutAppDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        binding = LayoutAppDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = builder.title
        binding.message.text = builder.message

        binding.positiveButton.apply {
            text = builder.positiveButton.first
            setOnClickListener {
                dismissAllowingStateLoss()
                builder.positiveButton.second()
            }
        }

        binding.negativeButton.apply {
            text = builder.negativeButton.first
            setOnClickListener {
                dismissAllowingStateLoss()
                builder.negativeButton.second()
            }
        }

    }

    class Builder(private val activity: FragmentActivity) {
        var title: String? = null
        var message: String? = null
        var positiveButton: Pair<String, () -> Unit> = "بله" to {}
        var negativeButton: Pair<String, () -> Unit> = "خیر" to {}

        fun setTitle(title: String) = apply { this.title = title }
        fun setMessage(message: String) = apply { this.message = message }
        fun setPositiveButton(title: String, listener: () -> Unit) = apply {
            this.positiveButton = title to listener
        }

        fun setNegativeButton(title: String, listener: () -> Unit) = apply {
            this.negativeButton = title to listener
        }

        fun show() {
            AppAlertDialog(this).show(activity)
        }
    }

}