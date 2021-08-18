package com.example.wonhoi_moive_review_service_app.presentation.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapterPosition = parent.getChildAdapterPosition(view)
        val gridLayoutManager = parent.layoutManager as GridLayoutManager
        val spanSize = gridLayoutManager.spanSizeLookup.getSpanSize(adapterPosition)

        // 추천 영화 용
        if (spanSize == spanCount) {    // spanCount 는 3으로 미리 지정했고, spanSize 3인경우는 추천 영화
            outRect.left = spacing
            outRect.right = spacing
            outRect.top = spacing
            outRect.bottom = spacing
            return
        }

        // 3열 영화 용 ( 위에꺼를 안쓰는 이유는 '왼쪽과 오른쪽 간격을 조정'해야하는 3열짜리이기 때문에 다시 정의해야한다.
        // view.layoutParams 를 LayoutParams 형태로 바꾸고, spanIndex 을 통해 해당 param 의 span 의 위치값을 가져온다.
        // 그래서 adapterPosition 의 경우 0,1,2,3,4,5 이런식으로 전부 순서가 있는 Position 값이지만,
        // column 은 첫번쨰줄  0, 두번쨰줄 0, 세번째 줄 0, 1, 2 이런식으로 index 값을 가지고 온다.
        val column = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
        val itemHorizontalSpacing = ((spanCount + 1) * spacing) / spanCount.toFloat()
        when (column) {
            0 -> {  // 맨 왼쪽
                outRect.left = spacing
                outRect.right = (itemHorizontalSpacing - spacing).toInt()
            }
            (spanCount - 1) -> { // 맨 오른쪽
                outRect.left = (itemHorizontalSpacing - spacing).toInt()
                outRect.right = spacing
            }
            else -> {
                outRect.left = (itemHorizontalSpacing / 2).toInt()
                outRect.right = (itemHorizontalSpacing / 2).toInt()
            }
        }
        outRect.top = spacing
        outRect.bottom = spacing
    }
}