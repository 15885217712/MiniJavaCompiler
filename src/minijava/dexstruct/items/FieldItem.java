package minijava.dexstruct.items;

/**
 * FieldItem�����ݽṹ
 */
public class FieldItem
{
	public short classIdx;	//to type table
	public short typeIdx;  //to type table
	public int nameIdx;    //to string table
	public String nameString;
	public String classString;
	public String typeString;
	public String modifiedFieldString;
	/**
	 * ���캯������Ҫ������Ϣ
	 * @param _class ��������
	 * @param _type ������
	 * @param _name ������
	 * @param _modified ���������������Ϣ
	 */
	public FieldItem(String _class, String _type, String _name, String _modified)
	{
		classString = _class;
		typeString = _type;
		nameString = _name;
		modifiedFieldString = _modified;
	}
}