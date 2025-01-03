package com.raredev.theblocklogics.editor.view.palette

import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.raredev.theblocklogics.R
import com.raredev.theblocklogics.editor.view.data.ViewData
import dev.trindadedev.theblocklogics.editor.view.type.ViewType

class LinearHPaletteItem() :
  PaletteItem(
    R.mipmap.ic_palette_linear_layout_horz,
    "LinearLayout (H)",
    LinearLayout::class.java.name,
  ) {
  override fun createViewData(): ViewData {
    return ViewData(ViewType.TYPE_LINEAR_LAYOUT).apply {
      width = LinearLayout.LayoutParams.MATCH_PARENT
      layout.orientation = LinearLayout.HORIZONTAL
    }
  }
}

class LinearVPaletteItem() :
  PaletteItem(
    R.mipmap.ic_palette_linear_layout_vert,
    "LinearLayout (V)",
    LinearLayout::class.java.name,
  ) {
  override fun createViewData(): ViewData {
    return ViewData(ViewType.TYPE_LINEAR_LAYOUT).apply {
      height = LinearLayout.LayoutParams.MATCH_PARENT
      layout.orientation = LinearLayout.VERTICAL
    }
  }
}

class HScrollViewPaletteItem() :
  PaletteItem(
    R.mipmap.ic_palette_scroll_view,
    "ScrollView (H)",
    HorizontalScrollView::class.java.name,
  ) {
  override fun createViewData(): ViewData {
    return ViewData(ViewType.TYPE_HSCROLL_VIEW).apply {
      width = LinearLayout.LayoutParams.MATCH_PARENT
    }
  }
}

class ScrollViewPaletteItem() :
  PaletteItem(R.mipmap.ic_palette_scroll_view, "ScrollView (V)", ScrollView::class.java.name) {
  override fun createViewData(): ViewData {
    return ViewData(ViewType.TYPE_VSCROLL_VIEW).apply {
      height = LinearLayout.LayoutParams.MATCH_PARENT
    }
  }
}

class FrameLayoutPaletteItem() :
  PaletteItem(R.mipmap.ic_palette_frame_layout, "FrameLayout", ScrollView::class.java.name) {
  override fun createViewData(): ViewData {
    return ViewData(ViewType.TYPE_FRAME_LAYOUT).apply {
      width = LinearLayout.LayoutParams.MATCH_PARENT
    }
  }
}
