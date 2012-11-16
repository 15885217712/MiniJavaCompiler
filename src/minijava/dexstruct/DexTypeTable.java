package minijava.dexstruct;

import java.util.*;

/**
 * TypeTable�ε����ݴ洢�ṹ
 */
public class DexTypeTable {
	private int size;
	private HashSet<String> hTypeStrings;
	private ArrayList<Integer> aTypeStringIdxs;
	private HashMap<String, Integer> hTypeStringToStringIdx;
	private HashMap<Integer, String> hStringIdxToTypeString;
	
	private DexStringTable stringTable;
	
	/**
	 * ���캯������Ҫ����table��Ϣ
	 * @param _StringTable
	 */
	public DexTypeTable(DexStringTable _StringTable) 
	{
		stringTable = _StringTable;
		hTypeStrings = new HashSet<String>();
		aTypeStringIdxs = new ArrayList<Integer>();
		hTypeStringToStringIdx = new HashMap<String, Integer>();
		hStringIdxToTypeString = new HashMap<Integer, String>();
	}

	/**
	 * ���һ������
	 * @param _str �������ַ���
	 */
	public void AddType(String _str)
	{
		hTypeStrings.add(_str);
		stringTable.AddString(_str);
	}
	
	/**
	 * ��ȡ��Ŀ��
	 * @return ��Ŀ��
	 */
	public int GetSize()
	{
		return size;
	}
	
	/**
	 * Ҫ�ȵ�����stringTable��serilize()����ܵ��ô˺���
	 */
	public void Serilize()
	{
		Iterator<String> it = hTypeStrings.iterator();
		for (int i = 0; i < hTypeStrings.size(); i++) 
		{
			String tmpString = new String(it.next());
			hTypeStringToStringIdx.put(tmpString, stringTable.GetStringIdx(tmpString));
			hStringIdxToTypeString.put(stringTable.GetStringIdx(tmpString), tmpString);
			aTypeStringIdxs.add(stringTable.GetStringIdx(tmpString));
		}
		
		for (int i = 0; i < aTypeStringIdxs.size()-1; i++) 
		{
			for (int j = i; j < aTypeStringIdxs.size(); j++) 
			{
				if (aTypeStringIdxs.get(i) > (aTypeStringIdxs.get(j))) 
				{
					Integer tmpInteger = aTypeStringIdxs.get(i);
					aTypeStringIdxs.set(i, aTypeStringIdxs.get(j));
					aTypeStringIdxs.set(j, tmpInteger);
				}
			}
		}
		size = aTypeStringIdxs.size();
	}
	
	/**
	 * ���type���е�idx����stringtable�е�����ֵ
	 * @param idx
	 * @return
	 */
	public int GetTypeStringIdx(int idx)
	{
		return aTypeStringIdxs.get(idx);
	}
	
	/**
	 * ���type���е�idx����stringtable�е�String
	 * @param idx
	 * @return
	 */
	public String GetTypeString(int idx)
	{
		return stringTable.GetString(aTypeStringIdxs.get(idx));
	}
	
	/**
	 * �������Ϊstr��type��typetable�е�����ֵ
	 * @param str
	 * @return
	 */
	public int GetTypeIdx(String str)
	{
		Integer stridx = hTypeStringToStringIdx.get(str);
		return aTypeStringIdxs.indexOf(stridx);
	}
}
