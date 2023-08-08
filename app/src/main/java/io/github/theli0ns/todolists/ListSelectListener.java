package io.github.theli0ns.todolists;

public interface ListSelectListener {
    void onItemClicked(ListRecord listRecord);
    boolean onItemLongClicked(ListRecord listRecord);
}
