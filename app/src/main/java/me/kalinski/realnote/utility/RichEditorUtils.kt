package me.kalinski.realnote.utility

import android.view.View
import jp.wasabeef.richeditor.RichEditor
import me.kalinski.realnote.R

object RichEditorUtils {
    fun toolboxAction(view: View, editNote: RichEditor) {
        when (view.id) {
            R.id.action_undo -> editNote.undo()
            R.id.action_redo -> editNote.redo()
            R.id.action_bold -> editNote.setBold()
            R.id.action_italic -> editNote.setItalic()
            R.id.action_subscript -> editNote.setSubscript()
            R.id.action_superscript -> editNote.setSuperscript()
            R.id.action_strikethrough -> editNote.setStrikeThrough()
            R.id.action_underline -> editNote.setUnderline()
            R.id.action_heading1 -> editNote.setHeading(1)
            R.id.action_heading2 -> editNote.setHeading(2)
            R.id.action_heading3 -> editNote.setHeading(3)
            R.id.action_heading4 -> editNote.setHeading(4)
            R.id.action_heading5 -> editNote.setHeading(5)
            R.id.action_heading6 -> editNote.setHeading(6)
            R.id.action_indent -> editNote.setIndent()
            R.id.action_outdent -> editNote.setOutdent()
            R.id.action_align_left -> editNote.setAlignLeft()
            R.id.action_align_center -> editNote.setAlignCenter()
            R.id.action_align_right -> editNote.setAlignRight()
            R.id.action_blockquote -> editNote.setBlockquote()
            R.id.action_insert_bullets -> editNote.setBullets()
            R.id.action_insert_numbers -> editNote.setNumbers()
            R.id.action_insert_link -> editNote.insertLink("https://github.com/AlanKalinski", "")
            R.id.action_insert_checkbox -> editNote.insertTodo()
            R.id.action_txt_color -> { /*TODO*/
            }
            R.id.action_bg_color -> {/*TODO*/
            }
            R.id.action_insert_image -> {/*TODO*/
            }
        }
    }

}