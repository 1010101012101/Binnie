package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemProduct extends Item implements IItemModelRegister {
	IItemEnum[] types;

	public ItemProduct(final IItemEnum[] types) {
		this.setMaxStackSize(64);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.types = types;
	}

	public IItemEnum get(final ItemStack stack) {
		final int i = stack.getItemDamage();
		if (i >= 0 && i < this.types.length) {
			return this.types[i];
		}
		return this.types[0];
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemstack) {
		return this.get(itemstack).getName(itemstack);
	}

	@Override
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
		for (final IItemEnum type : this.types) {
			if (type.isActive()) {
				subItems.add(new ItemStack(this, 1, type.ordinal()));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		for (IItemEnum type : types) {
			manager.registerItemModel(item, type.ordinal(), getRegistryName().getResourcePath());
		}
	}
}
