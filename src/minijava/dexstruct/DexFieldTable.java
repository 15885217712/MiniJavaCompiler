package minijava.dexstruct;

import java.util.*;

import minijava.dexstruct.items.FieldItem;

/**
 * FieldTable�ε����ݴ洢�ṹ
 */
public class DexFieldTable {
	private int size;
	private ArrayList<FieldItem> aFieldItems;
	private HashSet<String> hModifiedFieldItems;
	private HashMap<String, Integer> hModifiedFieldToFieldIdx;
	private HashMap<Integer, String> hFieldIdxToModifiedField;
	private DexTypeTable typeTable;
	private DexStringTable stringTable;

	/**
	 * ���캯������Ҫ�������table�����ã���Щ��Ϣ�ᱻʹ�õ�
	 * @param _typeTable
	 * @param _stringTable
	 */
	public DexFieldTable(DexTypeTable _typeTable, DexStringTable _stringTable) 
	{
		typeTable = _typeTable;
		stringTable = _stringTable;
		aFieldItems = new ArrayList<FieldItem>();
		hModifiedFieldItems = new HashSet<String>();
		hModifiedFieldToFieldIdx = new HashMap<String, Integer>();
		hFieldIdxToModifiedField = new HashMap<Integer, String>();
	}
	
	/**
	 * ���һ����Ա����
	 * @param _class �������
	 * @param _type ���͵�����
	 * @param _name ����������
	 */
	public void AddField(String _class, String _type, String _name)
	{
		String modFieldString = new String(_class + _type + _name);
		if (hModifiedFieldItems.add(modFieldString)) 
		{
			typeTable.AddType(_class);
			typeTable.AddType(_type);
			stringTable.AddString(_name);
			aFieldItems.add(new FieldItem(_class, _type, _name, modFieldString));
		}
	}

	/**
	 * ����Ҫ�� StringTable �� TypeTable ������ Serilize() �������
	 */
	public void Serilize()
	{
		for (int i = 0; i < aFieldItems.size(); i++) 
		{
			aFieldItems.get(i).classIdx = (short)typeTable.GetTypeIdx(aFieldItems.get(i).classString);
			aFieldItems.get(i).typeIdx = (short)typeTable.GetTypeIdx(aFieldItems.get(i).typeString);
			aFieldItems.get(i).nameIdx = stringTable.GetStringIdx(aFieldItems.get(i).nameString);
		}
		
		for (int i = 0; i < aFieldItems.size()-1; i++) 
		{
			for (int j = i; j < aFieldItems.size(); j++) 
			{
				if (aFieldItems.get(i).classIdx > aFieldItems.get(j).classIdx) 
				{
					FieldItem tmpFieldItem = aFieldItems.get(i);
					aFieldItems.set(i, aFieldItems.get(j));
					aFieldItems.set(j, tmpFieldItem);					
				}
				else if (aFieldItems.get(i).classIdx == aFieldItems.get(j).classIdx) 
				{
					if (aFieldItems.get(i).nameIdx > aFieldItems.get(j).nameIdx) 
					{
						FieldItem tmpFieldItem = aFieldItems.get(i);
						aFieldItems.set(i, aFieldItems.get(j));
						aFieldItems.set(j, tmpFieldItem);					
					}
					else if (aFieldItems.get(i).nameIdx == aFieldItems.get(j).nameIdx)
					{					
						if (aFieldItems.get(i).typeIdx > aFieldItems.get(j).typeIdx) 
						{
							FieldItem tmpFieldItem = aFieldItems.get(i);
							aFieldItems.set(i, aFieldItems.get(j));
							aFieldItems.set(j, tmpFieldItem);					
						}
					}
				}
			}
		}
		
		for (int i = 0; i < aFieldItems.size(); i++) 
		{
			hModifiedFieldToFieldIdx.put(aFieldItems.get(i).modifiedFieldString, i);
			hFieldIdxToModifiedField.put(i, aFieldItems.get(i).modifiedFieldString);
		}
		
		size = aFieldItems.size();
	}
	
	/**
	 * ��ȡ��Ŀ��
	 * @return ���е���Ŀ��
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * ͨ����Ա���Ե���Ϣ����ȡ��Ӧ��index
	 * @param _class ����������
	 * @param _type ��Ա���Ե���������
	 * @param _name ��Ա���Ե�����
	 * @return ���ض�Ӧ��index
	 */
	public int GetFieldIdxByClassAndTypeAndName(String _class, String _type, String _name)
	{
		String modFieldString = new String(_class + _type + _name);
		return GetFieldIdxByModifiedField(modFieldString);
	}
	
	/**
	 * ͨ�������˵ĳ�Ա������Ϣ��ȡ��Ӧ��index
	 * @param _modfield ��������ĳ�Ա������Ϣ
	 * @return ���ض�Ӧ��index
	 */
	public int GetFieldIdxByModifiedField(String _modfield)
	{
		return hModifiedFieldToFieldIdx.get(_modfield);
	}
	
	/**
	 * ͨ��field�е�index��ȡ��Ӧ��Ա���Զ�Ӧ�����index��TypeTable�еģ�
	 * @param _fieldidx field�е�index
	 * @return ��Ӧ�����index��TypeTable�еģ�
	 */
	public short GetFieldClassIdx(int _fieldidx)
	{
		return aFieldItems.get(_fieldidx).classIdx;
	}
	
	/**
	 * ͨ��field�е�index��ȡ��Ӧ��Ա���Ե�����index��TypeTable�еģ�
	 * @param _fieldidx field�е�index
	 * @return ��Ӧ��Ա���Ե�����index��TypeTable�еģ�
	 */
	public short GetFieldTypeIdx(int _fieldidx)
	{
		return aFieldItems.get(_fieldidx).typeIdx;
	}
	
	/**
	 * ͨ��field�е�index��ȡ��Ӧ��Ա���Ե����Ƶ�index��StringTable�еģ�
	 * @param _fieldidx field�е�index
	 * @return ��Ӧ��Ա���Ե����Ƶ�index��StringTable�еģ�
	 */
	public int GetFieldNameIdx(int _fieldidx)
	{
		return aFieldItems.get(_fieldidx).nameIdx;
	}
}
