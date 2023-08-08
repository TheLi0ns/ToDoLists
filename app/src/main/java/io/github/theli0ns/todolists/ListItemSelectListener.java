package io.github.theli0ns.todolists;

public interface ListItemSelectListener {
    void onItemClicked(ListItemRecord listItemRecord);
    boolean onItemLongClicked(ListItemRecord listItemRecord);
}
