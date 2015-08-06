package BulkExport;

import org.bukkit.inventory.ItemStack;

public class Exportable {
	int ID;
	ItemStack item;
	int StackSize;
	int NumStacks;
	ItemStack trade;
	int NumTraded;
	public Exportable(int identifier,ItemStack totrade,int size,int stacks, ItemStack Traded, int numreturn){
		ID=identifier;
		item=totrade;
		StackSize=size;
		NumStacks=stacks;
		trade=Traded;
		NumTraded=numreturn;
	}
	public ItemStack getTrade(){
		return item;
	}
	public int getStackSize(){
		return StackSize;
	}
	public ItemStack getTraded(){
		return trade;
	}
}
