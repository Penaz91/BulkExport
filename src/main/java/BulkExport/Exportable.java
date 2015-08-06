package BulkExport;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class Exportable implements Serializable, ConfigurationSerializable {
	private static final long serialVersionUID = 159754163929751635L;
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
	public void SetTraded(ItemStack itemtraded){
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
	public Exportable deserialize(Map <String,Object> input){
		return new Exportable(Integer.parseInt(input.get("ID").toString()),(ItemStack) input.get("trade"),Integer.parseInt(input.get("StackSize").toString()),Integer.parseInt(input.get("NumStacks").toString()),(ItemStack) input.get("traded"),Integer.parseInt(input.get("NumTraded").toString()));
	}
}
