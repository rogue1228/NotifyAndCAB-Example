package kr.co.skoop.testapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.R.menu
import android.view.*
import kotlinx.android.synthetic.main.activity_cab.*


/**
 * Created by Administrator on 2017-10-09.
 */
class CabActivity : AppCompatActivity(), ActionMode.Callback {
    var mActionMode: ActionMode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cab)

        etText.setOnLongClickListener { view ->
            // if actionmode is null "not started"
            if (mActionMode != null) {
                return@setOnLongClickListener false
            }

            // Start the CAB
            mActionMode = this.startActionMode(this)
            view.isSelected = true
            return@setOnLongClickListener true
        }
    }

    // 4. Called when the action mode is created; startActionMode() was called
    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {

        // Inflate a menu resource providing context menu items
        val inflater = mode.getMenuInflater()
        inflater.inflate(R.menu.main, menu)
        return true
    }

    // 5. Called when the user click share item
    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_share -> {
                Toast.makeText(this, etText.text, Toast.LENGTH_SHORT).show()

                mode.finish() // Action picked, so close the CAB
                return true
            }
            else -> return false
        }
    }

    // 6. Called each time the action mode is shown. Always called after onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false // Return false if nothing is done
    }

    // 7. Called when the user exits the action mode
    override fun onDestroyActionMode(mode: ActionMode) {
        mActionMode = null
    }
}