package org.xiyu.spartanweaponryunofficial.inventory;

import net.minecraft.resources.Identifier;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xiyu.spartanweaponryunofficial.capability.IQuiverItemHandler;

import java.util.function.Predicate;

/**
 * A menu slot backed by a quiver's ammo storage.
 */
public class SlotFiltered extends Slot {
    private static final SimpleContainer EMPTY = new SimpleContainer(0);

    protected final IQuiverItemHandler handler;
    protected final int index;
    protected final Predicate<ItemStack> filter;
    protected final Identifier noItemIcon;

    public SlotFiltered(IQuiverItemHandler handlerIn, int indexIn, int xPositionIn, int yPositionIn, Predicate<ItemStack> filterIn) {
        this(handlerIn, indexIn, xPositionIn, yPositionIn, filterIn, null);
    }

    public SlotFiltered(IQuiverItemHandler handlerIn, int indexIn, int xPositionIn, int yPositionIn, Predicate<ItemStack> filterIn, @Nullable Identifier noItemIconIn) {
        super(EMPTY, indexIn, xPositionIn, yPositionIn);
        this.handler = handlerIn;
        this.index = indexIn;
        this.filter = filterIn;
        this.noItemIcon = noItemIconIn;
    }

    public IQuiverItemHandler getItemHandler() {
        return this.handler;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return !stack.isEmpty() && this.handler.isItemValid(this.index, stack) && this.filter.test(stack);
    }

    @Override
    public ItemStack getItem() {
        return this.handler.getStackInSlot(this.index);
    }

    @Override
    public void set(ItemStack stack) {
        this.handler.setStackInSlot(this.index, stack);
        this.setChanged();
    }

    @Override
    public void setChanged() {
    }

    @Override
    public void onQuickCraft(ItemStack picked, ItemStack original) {
    }

    @Override
    public int getMaxStackSize() {
        return this.handler.getSlotLimit(this.index);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        ItemStack maxAdd = stack.copyWithCount(stack.getMaxStackSize());
        ItemStack current = this.handler.getStackInSlot(this.index);
        // Measure how much would fit with the slot cleared, then put the old stack back.
        this.handler.setStackInSlot(this.index, ItemStack.EMPTY);
        ItemStack remainder = this.handler.insertItem(this.index, maxAdd, true);
        this.handler.setStackInSlot(this.index, current);
        return maxAdd.getCount() - remainder.getCount();
    }

    @Override
    public boolean mayPickup(Player player) {
        return !this.handler.extractItem(this.index, 1, true).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return this.handler.extractItem(this.index, amount, false);
    }

    @Override
    public boolean isSameInventory(Slot other) {
        return other instanceof SlotFiltered o && o.handler == this.handler;
    }

    @Override
    public @Nullable Identifier getNoItemIcon() {
        return this.noItemIcon;
    }
}
