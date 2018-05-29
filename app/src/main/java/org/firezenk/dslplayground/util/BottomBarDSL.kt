package org.firezenk.dslplayground.util

import android.support.design.widget.BottomNavigationView

@DslMarker
annotation class BottomBarDSL

@BottomBarDSL
class BottomBarBuilder(val navigationView: BottomNavigationView) {

    var menu: Int = 0
    var default: Int = 0
    var menuItems: HashMap<Int, () -> Unit> = HashMap()

    fun item(setup: MenuItemBuilder.() -> Unit) {
        with(MenuItemBuilder()) {
            setup()
            menuItems.set(id, action)
        }
    }

    fun build() {
        navigationView.inflateMenu(menu)
        navigationView.selectedItemId = default
        navigationView.setOnNavigationItemSelectedListener(
                { item -> menuItems.get(item.itemId)?.invoke()
                    true
                }
        )
    }

}

class MenuItemBuilder {

    var id: Int = 0
    var action: () -> Unit = {}

}

fun BottomNavigationView.dsl(setup: BottomBarBuilder.() -> Unit) {
    with(BottomBarBuilder(this)) {
        setup()
        build()
    }
}