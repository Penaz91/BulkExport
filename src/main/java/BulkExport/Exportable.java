package BulkExport;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Exportable implements ConfigurationSerializable {
	int ID;
	ItemStack trade;
	int StackSize;
	int NumStacks;
	ItemStack traded;
	int NumTraded;
	public Exportable(){
		ID=0;
		trade=null;
		StackSize=0;
		NumStacks=0;
		traded=null;
		NumTraded=0;
	}
	public Exportable(int identifier,ItemStack totrade,int size,int stacks, ItemStack Traded, int numreturn){
		ID=identifier;
		trade=totrade;
		StackSize=size;
		NumStacks=stacks;
		traded=Traded;
		NumTraded=numreturn;
	}
	public int getID(){
		return ID;
	}
	public ItemStack getTrade(){
		return trade;
	}
	public int getNumStacks(){
		return NumStacks;
	}
	public int getStackSize(){
		return StackSize;
	}
	public ItemStack getTraded(){
		return traded;
	}
	public int getNumTraded(){
		return NumTraded;
	}
	public void setID(int identifier){
		this.ID=identifier;
	}
	public void setTrade(ItemStack totrade){
		this.trade=totrade;
	}
	public void setStackSize(int Size){
		this.StackSize=Size;
	}
	public void setNumStacks(int stacks){
		this.NumStacks=stacks;
	}
	public void setTraded(ItemStack itemtraded){
		this.traded=itemtraded;
	}
	public void setNumTraded(int numreturn){
		this.NumTraded=numreturn;
	}
	/*
	private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException{
		stream.defaultReadObject();
	}
	private void writeObject(ObjectOutputStream stream) throws IOException{
		stream.writeInt(ID);
		stream.writeObject(item);
		stream.writeInt(StackSize);
		stream.writeInt(NumStacks);
		stream.writeObject(trade);
		stream.writeInt(NumTraded);
		
	}*/
	@Override
	public Map<String, Object> serialize() {
		Map<String,Object> serial=new HashMap<String,Object>();
		serial.put("ID", this.ID);
		serial.put("trade", this.trade);
		serial.put("StackSize", this.StackSize);
		serial.put("NumStacks", this.NumStacks);
		serial.put("traded", this.traded);
		serial.put("NumTraded", this.NumTraded);
		return serial;
	}
	public Exportable(Map <String,Object> args){
		ID=(Integer.parseInt(args.get("ID").toString()));
		trade=((ItemStack) args.get("trade"));
		StackSize=(Integer.parseInt(args.get("StackSize").toString()));
		NumStacks=(Integer.parseInt(args.get("NumStacks").toString()));
		traded=((ItemStack) args.get("traded"));
		NumTraded=(Integer.parseInt(args.get("NumTraded").toString()));
	}
	public static Exportable deserialize(Map <String,Object> args){
		Exportable toret = new Exportable();
		toret.setID(Integer.parseInt(args.get("ID").toString()));
		toret.setTrade((ItemStack) args.get("trade"));
		toret.setStackSize(Integer.parseInt(args.get("StackSize").toString()));
		toret.setNumStacks(Integer.parseInt(args.get("NumStacks").toString()));
		toret.setTraded((ItemStack) args.get("traded"));
		toret.setNumTraded(Integer.parseInt(args.get("NumTraded").toString()));
		return toret;
	}
}
